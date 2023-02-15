package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Git用户信息
 *
 * @author ican
 */
@Data
@ApiModel(description = "Git用户信息")
public class GitUserInfoVO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private String id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar_url;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String name;

    /**
     * 登录
     */
    @ApiModelProperty(value = "登录")
    private String login;
}
