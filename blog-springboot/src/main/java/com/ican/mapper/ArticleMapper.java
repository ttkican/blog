package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Article;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章 Mapper
 *
 * @author ican
 * @date 2022/12/04 22:29
 **/
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询后台文章数量
     *
     * @param condition 条件
     * @return 文章数量
     */
    Long countArticleBackVO(@Param("condition") ConditionDTO condition);

    /**
     * 查询后台文章列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 后台文章列表
     */
    List<ArticleBackVO> selectArticleBackVO(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);

    /**
     * 根据id查询文章信息
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleInfoVO selectArticleInfoById(@Param("articleId") Integer articleId);

    /**
     * 文章搜索
     *
     * @param keyword 关键字
     * @return 文章列表
     */
    List<ArticleSearchVO> searchArticle(@Param("keyword") String keyword);

    /**
     * 查询首页文章
     *
     * @param limit 页码
     * @param size  大小
     * @return 首页文章
     */
    List<ArticleHomeVO> selectArticleHomeList(@Param("limit") Long limit, @Param("size") Long size);

    /**
     * 根据id查询首页文章
     *
     * @param articleId 文章id
     * @return 首页文章
     */
    ArticleVO selectArticleHomeById(Integer articleId);

    /**
     * 查询上一篇文章
     *
     * @param articleId 文章id
     * @return 上一篇文章
     */
    ArticlePaginationVO selectLastArticle(Integer articleId);

    /**
     * 查询下一篇文章
     *
     * @param articleId 文章id
     * @return 下一篇文章
     */
    ArticlePaginationVO selectNextArticle(Integer articleId);

    /**
     * 查询文章归档
     *
     * @param limit 页码
     * @param size  大小
     * @return 文章归档
     */
    List<ArchiveVO> selectArchiveList(@Param("limit") Long limit, @Param("size") Long size);

    /**
     * 查询文章统计
     *
     * @return 文章统计
     */
    List<ArticleStatisticsVO> selectArticleStatistics();

    /**
     * 查询推荐文章
     *
     * @return 推荐文章
     */
    List<ArticleRecommendVO> selectArticleRecommend();

    /**
     * 根据条件查询文章
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleConditionVO> listArticleByCondition(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);
}