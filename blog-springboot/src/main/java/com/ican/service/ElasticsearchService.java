//package com.ican.service;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch.core.IndexRequest;
//import com.ican.constant.ElasticConstant;
//import com.ican.model.vo.response.ArticleSearchResp;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
///**
// * es文章服务
// *
// * @author ican
// **/
//@Slf4j
//@Service
//public class ElasticsearchService {
//
//    @Autowired
//    private ElasticsearchClient elasticsearchClient;
//
//    public void addArticle(ArticleSearchResp article) {
//        try {
//            IndexRequest<ArticleSearchResp> indexRequest = IndexRequest.of(request -> request
//                    .index(ElasticConstant.ARTICLE_INDEX)
//                    .id(article.getId().toString())
//                    .document(article));
//            elasticsearchClient.index(indexRequest);
//        } catch (IOException e) {
//            log.error("ElasticsearchService.addArticle fail, {}", e.getMessage());
//        }
//    }
//
//    public void updateArticle(ArticleSearchResp article) {
//        try {
//            elasticsearchClient.update(request -> request
//                    .index(ElasticConstant.ARTICLE_INDEX)
//                    .id(article.getId().toString())
//                    .doc(article), ArticleSearchResp.class);
//        } catch (IOException e) {
//            log.error("ElasticsearchService.updateArticle fail, {}", e.getMessage());
//        }
//    }
//
//    public void deleteArticle(Integer id) {
//        try {
//            elasticsearchClient.delete(
//                    deleteRequest -> deleteRequest
//                            .index(ElasticConstant.ARTICLE_INDEX)
//                            .id(id.toString()));
//        } catch (IOException e) {
//            log.error("ElasticsearchService.deleteArticle fail, {}", e.getMessage());
//        }
//    }
//}