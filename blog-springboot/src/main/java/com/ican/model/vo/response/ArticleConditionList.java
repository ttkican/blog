package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 文章条件列表VO
 *
 * @author ican
 **/
@Data
@Builder
@ApiModel(description = "文章条件列表VO")
public class ArticleConditionList {

    /**
     * 文章列表
     */
    @ApiModelProperty(value = "文章列表")
    private List<ArticleConditionResp> articleConditionVOList;

    /**
     * 条件名
     */
    @ApiModelProperty(value = "条件名")
    private String name;
}