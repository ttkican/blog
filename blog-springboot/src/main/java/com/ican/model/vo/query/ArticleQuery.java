package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文章查询条件
 *
 * @author ican
 * @date 2023/07/29 17:13
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文章查询条件")
public class ArticleQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    /**
     * 标签id
     */
    @ApiModelProperty(value = "标签id")
    private Integer tagId;

    /**
     * 是否删除 (0否 1是)
     */
    @ApiModelProperty(value = "是否删除 (0否 1是)")
    private Integer isDelete;

    /**
     * 文章状态 (1公开 2私密 3草稿)
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 文章类型 (1原创 2转载 3翻译)
     */
    @ApiModelProperty(value = "文章类型 (1原创 2转载 3翻译)")
    private Integer articleType;

}