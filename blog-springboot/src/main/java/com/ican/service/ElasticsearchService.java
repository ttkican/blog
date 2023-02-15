package com.ican.service;

import com.ican.entity.Article;
import com.ican.model.vo.ArticleSearchVO;

/**
 * es文章业务接口
 *
 * @author ican
 */
public interface ElasticsearchService {

    /**
     * 添加文章
     *
     * @param article 文章
     */
    void addArticle(ArticleSearchVO article);

    /**
     * 更新文章
     *
     * @param article 文章
     */
    void updateArticle(ArticleSearchVO article);

    /**
     * 删除文章
     *
     * @param id 文章id
     */
    void deleteArticle(Integer id);

}
