//package com.ican.strategy.impl;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch._types.FieldValue;
//import co.elastic.clients.elasticsearch.core.SearchRequest;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import com.ican.constant.CommonConstant;
//import com.ican.constant.ElasticConstant;
//import com.ican.enums.ArticleStatusEnum;
//import com.ican.model.vo.response.ArticleSearchResp;
//import com.ican.strategy.SearchStrategy;
//import lombok.extern.log4j.Log4j2;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//
///**
// * ES搜索策略
// *
// * @author ican
// */
//@Log4j2
//@Service("esSearchStrategyImpl")
//public class EsSearchStrategyImpl implements SearchStrategy {
//
//    @Autowired
//    private ElasticsearchClient elasticsearchClient;
//
//    @Override
//    public List<ArticleSearchResp> searchArticle(String keyword) {
//        if (StringUtils.isBlank(keyword)) {
//            return new ArrayList<>();
//        }
//        try {
//            // 条件构造
//            SearchRequest searchRequest = SearchRequest.of(s -> s.index(ElasticConstant.ARTICLE_INDEX)
//                    .query(query -> query
//                            .bool(bool -> bool
//                                    .must(must -> must.match(m -> m.field("all").query(FieldValue.of(keyword))))
//                                    .must(must -> must.term(m -> m.field("isDelete").value(FieldValue.of(CommonConstant.FALSE))))
//                                    .must(must -> must.term(m -> m.field("status").value(FieldValue.of(ArticleStatusEnum.PUBLIC.getStatus())))))
//                    ).highlight(h -> h
//                            .fields(ElasticConstant.ARTICLE_TITLE,f -> f.preTags(ElasticConstant.PRE_TAG).postTags(ElasticConstant.POST_TAG))
//                            .fields(ElasticConstant.ARTICLE_CONTENT, f -> f.preTags(ElasticConstant.PRE_TAG).postTags(ElasticConstant.POST_TAG))
//                            .requireFieldMatch(false)
//                    ));
//            SearchResponse<ArticleSearchResp> search = elasticsearchClient.search(searchRequest, ArticleSearchResp.class);
//            // 解析结果
//            return handleResponse(search);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return new ArrayList<>();
//    }
//
//    private List<ArticleSearchResp> handleResponse(SearchResponse<ArticleSearchResp> response) {
//        // 解析结果并返回
//        return response.hits().hits().stream()
//                .map(hit -> {
//                    if (CollectionUtils.isNotEmpty(hit.highlight().get(ElasticConstant.ARTICLE_TITLE))) {
//                        Objects.requireNonNull(hit.source()).setArticleTitle(hit.highlight().get(ElasticConstant.ARTICLE_TITLE).get(0));
//                    }
//                    if (CollectionUtils.isNotEmpty(hit.highlight().get(ElasticConstant.ARTICLE_CONTENT))) {
//                        Objects.requireNonNull(hit.source()).setArticleContent(hit.highlight().get(ElasticConstant.ARTICLE_CONTENT).get(0));
//                    } else {
//                        Objects.requireNonNull(hit.source()).setArticleContent(hit.source().getArticleContent().substring(0, 300));
//                    }
//                    return hit.source();
//                })
//                .collect(Collectors.toList());
//    }
//}