package com.ican.model.dto;

import com.ican.annotation.CommentType;
import com.ican.validator.CommentProvider;
import com.ican.validator.groups.ArticleTalk;
import com.ican.validator.groups.Link;
import com.ican.validator.groups.ParentIdNotNull;
import com.ican.validator.groups.ParentIdNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 评论DTO
 *
 * @author ican
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequenceProvider(value = CommentProvider.class)
@ApiModel(description = "评论DTO")
public class CommentDTO {

    /**
     * 类型id
     */
    @NotNull(message = "类型id不能为空", groups = {ArticleTalk.class})
    @Null(message = "类型id必须为空", groups = {Link.class})
    @ApiModelProperty(value = "类型id")
    private Integer typeId;

    /**
     * 评论类型 (1文章 2友链 3说说)
     */
    @CommentType(values = {1, 2, 3}, message = "评论类型只能为1、2、3")
    @NotNull(message = "评论类型不能为空")
    @ApiModelProperty(value = "评论类型 (1文章 2友链 3说说)")
    private Integer commentType;

    /**
     * 父评论id
     */
    @Null(groups = {ParentIdNull.class})
    @NotNull(groups = {ParentIdNotNull.class})
    @ApiModelProperty(value = "父评论id")
    private Integer parentId;

    /**
     * 被回复评论id
     */
    @Null(message = "reply_id、to_uid必须都为空", groups = {ParentIdNull.class})
    @NotNull(message = "回复评论id和回复用户id不能为空", groups = {ParentIdNotNull.class})
    @ApiModelProperty(value = "被回复评论id")
    private Integer replyId;

    /**
     * 被回复用户id
     */
    @Null(message = "reply_id、to_uid必须都为空", groups = {ParentIdNull.class})
    @NotNull(message = "回复评论id和回复用户id不能为空", groups = {ParentIdNotNull.class})
    @ApiModelProperty(value = "被回复用户id")
    private Integer toUid;

    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空")
    @ApiModelProperty(value = "评论内容")
    private String commentContent;

}
