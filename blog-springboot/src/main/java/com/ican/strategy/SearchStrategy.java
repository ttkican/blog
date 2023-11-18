package com.ican.strategy;

import com.ican.model.vo.response.ArticleSearchResp;

import java.util.List;

/**
 * 文章搜索策略
 *
 * @author ican
 */
public interface SearchStrategy {

    /**
     * 搜索文章
     *
     * @param keyword 关键字
     * @return {@link List< ArticleSearchResp >} 文章列表
     */
    List<ArticleSearchResp> searchArticle(String keyword);
}
