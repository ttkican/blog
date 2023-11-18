package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询条件
 *
 * @author ican
 * @date 2023/07/29 19:14
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "用户查询条件")
public class UserQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

    /**
     * 登录方式 (1邮箱 2QQ 3Gitee 4Github)
     */
    @ApiModelProperty(value = "登录方式 (1邮箱 2QQ 3Gitee 4Github)")
    private Integer loginType;

}