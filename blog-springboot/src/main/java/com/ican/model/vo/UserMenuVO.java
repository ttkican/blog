package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户菜单
 *
 * @author ican
 * @date 2022/12/06 16:41
 **/
@Data
@ApiModel(description = "用户菜单")
public class UserMenuVO {

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Integer id;

    /**
     * 父菜单id
     */
    @ApiModelProperty(value = "父菜单id")
    private Integer parentId;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    /**
     * 类型（M目录 C菜单 B按钮）
     */
    @ApiModelProperty(value = "类型（M目录 C菜单 B按钮）")
    private String menuType;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String path;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /**
     * 菜单组件
     */
    @ApiModelProperty(value = "菜单组件")
    private String component;

    /**
     * 是否隐藏 (0否 1是)
     */
    @ApiModelProperty(value = "是否隐藏 (0否 1是)")
    private Integer isHidden;

}