package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 路由其他信息Response
 *
 * @author ican
 * @date 2022/12/11 19:35
 **/
@Data
@Builder
@ApiModel(description = "路由其他信息Response")
public class MetaResp {

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String title;

    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    /**
     * 是否隐藏
     */
    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

}