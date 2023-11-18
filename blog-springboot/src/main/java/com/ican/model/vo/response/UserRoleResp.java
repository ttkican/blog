package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户角色Response
 *
 * @author ican
 **/
@Data
@ApiModel(description = "用户角色Response")
public class UserRoleResp {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private String id;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;
}