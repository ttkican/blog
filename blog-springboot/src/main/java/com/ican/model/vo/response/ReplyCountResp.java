package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 回复数Response
 *
 * @author ican
 */
@Data
@ApiModel(description = "回复数Response")
public class ReplyCountResp {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Integer commentId;

    /**
     * 回复数
     */
    @ApiModelProperty(value = "回复数")
    private Integer replyCount;

}
