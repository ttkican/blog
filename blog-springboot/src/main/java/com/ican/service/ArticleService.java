package com.ican.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.constant.CommonConstant;
import com.ican.constant.RedisConstant;
import com.ican.entity.*;
import com.ican.enums.ArticleStatusEnum;
import com.ican.enums.FilePathEnum;
import com.ican.mapper.*;
import com.ican.model.vo.*;
import com.ican.model.vo.query.ArticleQuery;
import com.ican.model.vo.query.PageQuery;
import com.ican.model.vo.request.ArticleReq;
import com.ican.model.vo.request.DeleteReq;
import com.ican.model.vo.request.RecommendReq;
import com.ican.model.vo.request.TopReq;
import com.ican.model.vo.response.*;
import com.ican.strategy.context.SearchStrategyContext;
import com.ican.strategy.context.UploadStrategyContext;
import com.ican.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 文章服务
 *
 * @author ican
 * @date 2022/12/04 22:31
 **/
@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SearchStrategyContext searchStrategyContext;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Autowired
    private BlogFileService blogFileService;

    public PageResult<ArticleBackResp> listArticleBackVO(ArticleQuery articleQuery) {
        // 查询文章数量
        Long count = articleMapper.selectBackArticleCount(articleQuery);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询文章后台信息
        List<ArticleBackResp> articleBackRespList = articleMapper.selectBackArticleList(articleQuery);
        // 浏览量
        Map<Object, Double> viewCountMap = redisService.getZsetAllScore(RedisConstant.ARTICLE_VIEW_COUNT);
        // 点赞量
        Map<String, Integer> likeCountMap = redisService.getHashAll(RedisConstant.ARTICLE_LIKE_COUNT);
        // 封装文章后台信息
        articleBackRespList.forEach(item -> {
            Double viewCount = Optional.ofNullable(viewCountMap.get(item.getId())).orElse((double) 0);
            item.setViewCount(viewCount.intValue());
            Integer likeCount = likeCountMap.get(item.getId().toString());
            item.setLikeCount(Optional.ofNullable(likeCount).orElse(0));
        });
        return new PageResult<>(articleBackRespList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addArticle(ArticleReq article) {
        // 保存文章分类
        Integer categoryId = saveArticleCategory(article);
        // 添加文章
        Article newArticle = BeanCopyUtils.copyBean(article, Article.class);
        if (StringUtils.isBlank(newArticle.getArticleCover())) {
            SiteConfig siteConfig = redisService.getObject(RedisConstant.SITE_SETTING);
            newArticle.setArticleCover(siteConfig.getArticleCover());
        }
        newArticle.setCategoryId(categoryId);
        newArticle.setUserId(StpUtil.getLoginIdAsInt());
        baseMapper.insert(newArticle);
        // 保存文章标签
        saveArticleTag(article, newArticle.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(List<Integer> articleIdList) {
        // 删除文章标签
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getArticleId, articleIdList));
        // 删除文章
        articleMapper.deleteBatchIds(articleIdList);
    }

    public void updateArticleDelete(DeleteReq delete) {
        // 批量更新文章删除状态
        List<Article> articleList = delete.getIdList()
                .stream()
                .map(id -> Article.builder()
                        .id(id)
                        .isDelete(delete.getIsDelete())
                        .isTop(CommonConstant.FALSE)
                        .isRecommend(CommonConstant.FALSE)
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(articleList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(ArticleReq article) {
        // 保存文章分类
        Integer categoryId = saveArticleCategory(article);
        // 修改文章
        Article newArticle = BeanCopyUtils.copyBean(article, Article.class);
        newArticle.setCategoryId(categoryId);
        newArticle.setUserId(StpUtil.getLoginIdAsInt());
        baseMapper.updateById(newArticle);
        // 保存文章标签
        saveArticleTag(article, newArticle.getId());
    }

    public ArticleInfoResp editArticle(Integer articleId) {
        // 查询文章信息
        ArticleInfoResp articleInfoVO = articleMapper.selectArticleInfoById(articleId);
        Assert.notNull(articleInfoVO, "没有该文章");
        // 查询文章分类名称
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getCategoryName)
                .eq(Category::getId, articleInfoVO.getCategoryId()));
        // 查询文章标签名称
        List<String> tagNameList = tagMapper.selectTagNameByArticleId(articleId);
        articleInfoVO.setCategoryName(category.getCategoryName());
        articleInfoVO.setTagNameList(tagNameList);
        return articleInfoVO;
    }

    public void updateArticleTop(TopReq top) {
        // 修改文章置顶状态
        Article newArticle = Article.builder()
                .id(top.getId())
                .isTop(top.getIsTop())
                .build();
        articleMapper.updateById(newArticle);
    }

    public void updateArticleRecommend(RecommendReq recommend) {
        // 修改文章推荐状态
        Article newArticle = Article.builder()
                .id(recommend.getId())
                .isRecommend(recommend.getIsRecommend())
                .build();
        articleMapper.updateById(newArticle);
    }

    public List<ArticleSearchResp> listArticlesBySearch(String keyword) {
        return searchStrategyContext.executeSearchStrategy(keyword);
    }

    public PageResult<ArticleHomeResp> listArticleHomeVO(PageQuery pageQuery) {
        // 查询文章数量
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, CommonConstant.FALSE)
                .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询首页文章
        List<ArticleHomeResp> articleHomeVOList = articleMapper.selectArticleHomeList(pageQuery);
        return new PageResult<>(articleHomeVOList, count);
    }

    public ArticleResp getArticleHomeById(Integer articleId) {
        // 查询文章信息
        ArticleResp article = articleMapper.selectArticleHomeById(articleId);
        if (Objects.isNull(article)) {
            return null;
        }
        // 浏览量+1
        redisService.incrZet(RedisConstant.ARTICLE_VIEW_COUNT, articleId, 1D);
        // 查询上一篇文章
        ArticlePaginationResp lastArticle = articleMapper.selectLastArticle(articleId);
        // 查询下一篇文章
        ArticlePaginationResp nextArticle = articleMapper.selectNextArticle(articleId);
        article.setLastArticle(lastArticle);
        article.setNextArticle(nextArticle);
        // 查询浏览量
        Double viewCount = Optional.ofNullable(redisService.getZsetScore(RedisConstant.ARTICLE_VIEW_COUNT, articleId))
                .orElse((double) 0);
        article.setViewCount(viewCount.intValue());
        // 查询点赞量
        Integer likeCount = redisService.getHash(RedisConstant.ARTICLE_LIKE_COUNT, articleId.toString());
        article.setLikeCount(Optional.ofNullable(likeCount).orElse(0));
        return article;
    }

    public PageResult<ArchiveResp> listArchiveVO(PageQuery pageQuery) {
        // 查询文章数量
        Long count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getIsDelete, CommonConstant.FALSE)
                .eq(Article::getStatus, ArticleStatusEnum.PUBLIC.getStatus()));
        if (count == 0) {
            return new PageResult<>();
        }
        List<ArchiveResp> archiveList = articleMapper.selectArchiveList(pageQuery);
        return new PageResult<>(archiveList, count);
    }

    public List<ArticleRecommendResp> listArticleRecommendVO() {
        return articleMapper.selectArticleRecommend();
    }

    public String saveArticleImages(MultipartFile file) {
        // 上传文件
        String url = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.ARTICLE.getPath());
        blogFileService.saveBlogFile(file, url, FilePathEnum.ARTICLE.getFilePath());
        return url;
    }

    /**
     * 保存文章分类
     *
     * @param article 文章信息
     * @return 文章分类
     */
    private Integer saveArticleCategory(ArticleReq article) {
        // 查询分类
        Category category = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, article.getCategoryName()));
        // 分类不存在
        if (Objects.isNull(category)) {
            category = Category.builder()
                    .categoryName(article.getCategoryName())
                    .build();
            // 保存分类
            categoryMapper.insert(category);
        }
        return category.getId();
    }

    /**
     * 保存文章标签
     *
     * @param article   文章信息
     * @param articleId 文章id
     */
    private void saveArticleTag(ArticleReq article, Integer articleId) {
        // 删除文章标签
        articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId, articleId));
        // 标签名列表
        List<String> tagNameList = article.getTagNameList();
        if (CollectionUtils.isEmpty(tagNameList)) {
            return;
        }
        // 查询出已存在的标签
        List<Tag> existTagList = tagMapper.selectList(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId, Tag::getTagName)
                .in(Tag::getTagName, tagNameList));
        List<String> existTagNameList = existTagList.stream()
                .map(Tag::getTagName)
                .collect(Collectors.toList());
        List<Integer> existTagIdList = existTagList.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        // 移除已存在的标签列表
        tagNameList.removeAll(existTagNameList);
        // 含有新标签
        if (CollectionUtils.isNotEmpty(tagNameList)) {
            // 新标签列表
            List<Tag> newTagList = tagNameList.stream()
                    .map(item -> Tag.builder()
                            .tagName(item)
                            .build())
                    .collect(Collectors.toList());
            // 批量保存新标签
            tagService.saveBatch(newTagList);
            // 获取新标签id列表
            List<Integer> newTagIdList = newTagList.stream()
                    .map(Tag::getId)
                    .collect(Collectors.toList());
            // 新标签id添加到id列表
            existTagIdList.addAll(newTagIdList);
        }
        // 将所有的标签绑定到文章标签关联表
        articleTagMapper.saveBatchArticleTag(articleId, existTagIdList);
    }
}