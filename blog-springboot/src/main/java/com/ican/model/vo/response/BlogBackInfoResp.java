package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 网站后台信息Response
 *
 * @author ican
 **/
@Data
@Builder
@ApiModel(description = "网站后台信息Response")
public class BlogBackInfoResp {

    /**
     * 访问量
     */
    @ApiModelProperty(value = "访问量")
    private Integer viewCount;

    /**
     * 留言量
     */
    @ApiModelProperty(value = "留言量")
    private Long messageCount;

    /**
     * 用户量
     */
    @ApiModelProperty(value = "用户量")
    private Long userCount;

    /**
     * 文章量
     */
    @ApiModelProperty(value = "文章量")
    private Long articleCount;

    /**
     * 分类统计
     */
    @ApiModelProperty(value = "分类统计")
    private List<CategoryResp> categoryVOList;

    /**
     * 标签列表
     */
    @ApiModelProperty(value = "标签列表")
    private List<TagOptionResp> tagVOList;

    /**
     * 文章贡献统计
     */
    @ApiModelProperty(value = "文章贡献统计")
    private List<ArticleStatisticsResp> articleStatisticsList;

    /**
     * 文章浏览量排行
     */
    @ApiModelProperty(value = "文章浏览量排行")
    private List<ArticleRankResp> articleRankVOList;

    /**
     * 一周访问量
     */
    @ApiModelProperty(value = "一周访问量")
    private List<UserViewResp> userViewVOList;
}