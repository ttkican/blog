package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 第三方账号信息
 *
 * @author ican
 */
@Data
@Builder
@ApiModel(description = "第三方账号信息")
public class SocialUserInfoVO {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;
}