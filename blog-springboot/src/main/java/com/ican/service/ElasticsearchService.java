package com.ican.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.ican.model.vo.response.ArticleSearchResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.ican.constant.ElasticConstant.ARTICLE_INDEX;

/**
 * es文章服务
 *
 * @author ican
 **/
@Service
public class ElasticsearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public void addArticle(ArticleSearchResp article) {
        try {
            IndexRequest<ArticleSearchResp> indexRequest = IndexRequest.of(request -> request
                    .index(ARTICLE_INDEX)
                    .id(article.getId().toString())
                    .document(article));
            elasticsearchClient.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateArticle(ArticleSearchResp article) {
        try {
            elasticsearchClient.update(request -> request
                    .index(ARTICLE_INDEX)
                    .id(article.getId().toString())
                    .doc(article), ArticleSearchResp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteArticle(Integer id) {
        try {
            elasticsearchClient.delete(
                    deleteRequest -> deleteRequest
                            .index(ARTICLE_INDEX)
                            .id(id.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}