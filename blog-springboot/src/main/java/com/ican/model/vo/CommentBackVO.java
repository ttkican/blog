package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论后台VO
 *
 * @author ican
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "评论后台VO")
public class CommentBackVO {

    /**
     * 评论id
     */
    @ApiModelProperty(value = "评论id")
    private String id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 评论用户昵称
     */
    @ApiModelProperty(value = "评论用户昵称")
    private String fromNickname;

    /**
     * 被回复用户昵称
     */
    @ApiModelProperty(value = "被回复用户昵称")
    private String toNickname;

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    /**
     * 评论类型
     */
    @ApiModelProperty(value = "评论类型")
    private Integer commentType;

    /**
     * 是否通过 (0否 1是)
     */
    @ApiModelProperty(value = "是否通过 (0否 1是)")
    private Integer isCheck;

    /**
     * 发表时间
     */
    @ApiModelProperty(value = "发表时间")
    private LocalDateTime createTime;

}