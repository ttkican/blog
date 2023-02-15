package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评论数量VO
 *
 * @author ican
 **/
@Data
@ApiModel(description = "评论数量VO")
public class CommentCountVO {
    /**
     * 类型id
     */
    @ApiModelProperty(value = "类型id")
    private Integer id;

    /**
     * 评论数量
     */
    @ApiModelProperty(value = "评论数量")
    private Integer commentCount;
}