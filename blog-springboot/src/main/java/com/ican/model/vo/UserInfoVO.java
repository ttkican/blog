package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * 用户登录信息
 *
 * @author ican
 **/
@Data
@Builder
@ApiModel(description = "用户登录信息")
public class UserInfoVO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer id;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /**
     * 个人网站
     */
    @ApiModelProperty(value = "个人网站")
    private String webSite;

    /**
     * 个人简介
     */
    @ApiModelProperty(value = "个人简介")
    private String intro;

    /**
     * 点赞文章集合
     */
    @ApiModelProperty(value = "点赞文章集合")
    private Set<Object> articleLikeSet;

    /**
     * 点赞评论集合
     */
    @ApiModelProperty(value = "点赞评论集合")
    private Set<Object> commentLikeSet;

    /**
     * 点赞说说集合
     */
    @ApiModelProperty(value = "点赞说说集合")
    private Set<Object> talkLikeSet;

    /**
     * 登录类型
     */
    @ApiModelProperty(value = "登录类型")
    private Integer loginType;
}