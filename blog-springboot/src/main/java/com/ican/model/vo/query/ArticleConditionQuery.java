package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 文章条件
 *
 * @author ican
 * @date 2023/07/29 19:31
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章条件")
public class ArticleConditionQuery extends PageQuery {

    /**
     * 分类id
     */
    @NotNull(message = "分类id不能为空")
    @ApiModelProperty(value = "分类id", required = true)
    private Integer categoryId;

    /**
     * 标签id
     */
    @NotNull(message = "标签id不能为空")
    @ApiModelProperty(value = "标签id", required = true)
    private Integer tagId;

}