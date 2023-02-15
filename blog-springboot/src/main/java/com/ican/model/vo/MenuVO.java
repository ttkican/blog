package com.ican.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单VO
 *
 * @author ican
 * @date 2022/12/05 22:26
 **/
@Data
@ApiModel(description = "菜单VO")
public class MenuVO {

    /**
     * 菜单id
     */
    @ApiModelProperty(value = "菜单id")
    private Integer id;

    /**
     * 父菜单id
     */
    @ApiModelProperty(value = "父级菜单id")
    private Integer parentId;

    /**
     * 类型（M目录 C菜单 B按钮）
     */
    @ApiModelProperty(value = "类型（M目录 C菜单 B按钮）")
    private String menuType;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

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
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识")
    private String perms;

    /**
     * 是否隐藏 (0否 1是)
     */
    @ApiModelProperty(value = "是否隐藏 (0否 1是)")
    private Integer isHidden;

    /**
     * 是否禁用 (0否 1是)
     */
    @ApiModelProperty(value = "是否禁用 (0否 1是)")
    private Integer isDisable;

    /**
     * 菜单排序
     */
    @ApiModelProperty(value = "菜单排序")
    private Integer orderNum;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 子菜单列表
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "子菜单列表")
    private List<MenuVO> children;

}