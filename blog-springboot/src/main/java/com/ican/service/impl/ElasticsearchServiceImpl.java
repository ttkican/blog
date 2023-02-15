package com.ican.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.ican.model.vo.ArticleSearchVO;
import com.ican.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.ican.constant.ElasticConstant.ARTICLE_INDEX;

/**
 * es文章业务接口实现类
 *
 * @author ican
 **/
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public void addArticle(ArticleSearchVO article) {
        try {
            IndexRequest<ArticleSearchVO> indexRequest = IndexRequest.of(request -> request
                    .index(ARTICLE_INDEX)
                    .id(article.getId().toString())
                    .document(article));
            elasticsearchClient.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateArticle(ArticleSearchVO article) {
        try {
            elasticsearchClient.update(request -> request
                    .index(ARTICLE_INDEX)
                    .id(article.getId().toString())
                    .doc(article), ArticleSearchVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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