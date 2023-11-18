package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 分类Request
 *
 * @author ican
 */
@Data
@ApiModel(description = "分类Request")
public class CategoryReq {

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Integer id;

    /**
     * 分类名
     */
    @NotBlank(message = "分类名不能为空")
    @ApiModelProperty(value = "分类名", required = true)
    private String categoryName;

}