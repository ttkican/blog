package com.ican.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ican.constant.CommonConstant;
import com.ican.constant.RedisConstant;
import com.ican.entity.Article;
import com.ican.entity.SiteConfig;
import com.ican.enums.ArticleStatusEnum;
import com.ican.mapper.*;
import com.ican.model.vo.response.ArticleStatisticsResp;
import com.ican.model.vo.response.BlogBackInfoResp;
import com.ican.model.vo.response.BlogInfoResp;
import com.ican.model.vo.response.ArticleRankResp;
import com.ican.model.vo.response.CategoryResp;
import com.ican.model.vo.response.TagOptionResp;
import com.ican.model.vo.response.UserViewResp;
import com.ican.utils.IpUtils;
import com.ican.utils.UserAgentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 博客服务
 *
 * @author ican
 **/
@Service
public class BlogInfoService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SiteConfigService siteConfigService;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VisitLogMapper visitLogMapper;

    @Autowired
    private HttpServletRequest request;

    public void report() {
        // 获取用户ip
        String ipAddress = ServletUtil.getClientIP(request);
        Map<String, String> userAgentMap = UserAgentUtils.parseOsAndBrowser(request.getHeader("User-Agent"));
        // 获取访问设备
        String browser = userAgentMap.get("browser");
        String os = userAgentMap.get("os");
        // 生成唯一用户标识
        String uuid = ipAddress + browser + os;
        String md5 = DigestUtils.md5DigestAsHex(uuid.getBytes());
        // 判断是否访问
        if (!redisService.hasSetValue(RedisConstant.UNIQUE_VISITOR, md5)) {
            // 访问量+1
            redisService.incr(RedisConstant.BLOG_VIEW_COUNT, 1);
            // 保存唯一标识
            redisService.setSet(RedisConstant.UNIQUE_VISITOR, md5);
        }
    }

    public BlogInfoResp getBlogInfo() {
        // 文章数量
        Long articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus()).eq(Article::getIsDelete, CommonConstant.FALSE));
        // 分类数量
        Long categoryCount = categoryMapper.selectCount(null);
        // 标签数量
        Long tagCount = tagMapper.selectCount(null);
        // 博客访问量
        Integer count = redisService.getObject(RedisConstant.BLOG_VIEW_COUNT);
        String viewCount = Optional.ofNullable(count).orElse(0).toString();
        // 网站配置
        SiteConfig siteConfig = siteConfigService.getSiteConfig();
        return BlogInfoResp.builder()
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .viewCount(viewCount)
                .siteConfig(siteConfig)
                .build();
    }

    public BlogBackInfoResp getBlogBackInfo() {
        // 访问量
        Integer viewCount = redisService.getObject(RedisConstant.BLOG_VIEW_COUNT);
        // 留言量
        Long messageCount = messageMapper.selectCount(null);
        // 用户量
        Long userCount = userMapper.selectCount(null);
        // 文章量
        Long articleCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, CommonConstant.FALSE));
        // 分类数据
        List<CategoryResp> categoryRespList = categoryMapper.selectCategoryVO();
        // 标签数据
        List<TagOptionResp> tagVOList = tagMapper.selectTagOptionList();
        // 查询用户浏览
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        List<UserViewResp> userViewRespList = visitLogMapper.selectUserViewList(startTime, endTime);
        // 文章统计
        List<ArticleStatisticsResp> articleStatisticsList = articleMapper.selectArticleStatistics();
        // 查询redis访问量前五的文章
        Map<Object, Double> articleMap = redisService.zReverseRangeWithScore(RedisConstant.ARTICLE_VIEW_COUNT, 0, 4);
        BlogBackInfoResp blogBackInfoResp = BlogBackInfoResp.builder()
                .articleStatisticsList(articleStatisticsList)
                .tagVOList(tagVOList)
                .viewCount(viewCount)
                .messageCount(messageCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .categoryVOList(categoryRespList)
                .userViewVOList(userViewRespList)
                .build();
        if (CollectionUtils.isNotEmpty(articleMap)) {
            // 查询文章排行
            List<ArticleRankResp> articleRankRespList = listArticleRank(articleMap);
            blogBackInfoResp.setArticleRankVOList(articleRankRespList);
        }
        return blogBackInfoResp;
    }

    public String getAbout() {
        SiteConfig siteConfig = redisService.getObject(RedisConstant.SITE_SETTING);
        return siteConfig.getAboutMe();
    }

    /**
     * 查询文章排行
     *
     * @param articleMap 文章浏览量信息
     * @return {@link List< ArticleRankResp >} 文章排行
     */
    private List<ArticleRankResp> listArticleRank(Map<Object, Double> articleMap) {
        // 提取文章id
        List<Integer> articleIdList = new ArrayList<>(articleMap.size());
        articleMap.forEach((key, value) -> articleIdList.add((Integer) key));
        // 查询文章信息
        List<Article> articleList = articleMapper.selectList(new LambdaQueryWrapper<Article>()
                .select(Article::getId, Article::getArticleTitle)
                .in(Article::getId, articleIdList));
        return articleList.stream()
                .map(article -> ArticleRankResp.builder()
                        .articleTitle(article.getArticleTitle())
                        .viewCount(articleMap.get(article.getId()).intValue())
                        .build())
                .sorted(Comparator.comparingInt(ArticleRankResp::getViewCount).reversed())
                .collect(Collectors.toList());
    }
}
