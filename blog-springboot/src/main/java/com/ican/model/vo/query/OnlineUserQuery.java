package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 在线用户查询条件
 *
 * @author ican
 * @date 2023/07/29 19:51
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "在线用户查询条件")
public class OnlineUserQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

}