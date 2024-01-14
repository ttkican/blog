package com.ican.model.vo.response;

import com.ican.annotation.Desensitization;
import com.ican.enums.DesensitizationTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户后台Response
 *
 * @author ican
 * @date 2022/12/10 10:40
 **/
@Data
@ApiModel(description = "用户后台Response")
public class UserBackResp {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer id;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /**
     * 登录ip
     */
    @Desensitization(type = DesensitizationTypeEnum.IP4)
    @ApiModelProperty(value = "登录ip")
    private String ipAddress;

    /**
     * 登录地址
     */
    @Desensitization(type = DesensitizationTypeEnum.ADDRESS)
    @ApiModelProperty(value = "登录地址")
    private String ipSource;

    /**
     * 登录方式 (1邮箱 2QQ 3Gitee 4Github)
     */
    @ApiModelProperty(value = "登录方式 (1邮箱 2QQ 3Gitee 4Github)")
    private Integer loginType;

    /**
     * 用户角色
     */
    @ApiModelProperty(value = "用户角色")
    private List<UserRoleResp> roleList;

    /**
     * 是否禁用 (0否 1是)
     */
    @ApiModelProperty(value = "是否禁用 (0否 1是)")
    private Integer isDisable;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}