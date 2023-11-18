package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类选项Response
 *
 * @author ican
 **/
@Data
@ApiModel(description = "分类选项Response")
public class CategoryOptionResp {

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Integer id;

    /**
     * 分类名
     */
    @ApiModelProperty(value = "分类名")
    private String categoryName;

}