package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 最新评论Response
 *
 * @author ican
 **/
@Data
@ApiModel(description = "最新评论Response")
public class RecentCommentResp {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private Integer id;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户昵称")
    private String avatar;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    /**
     * 评论时间
     */
    @ApiModelProperty(value = "评论时间")
    private LocalDateTime createTime;
}