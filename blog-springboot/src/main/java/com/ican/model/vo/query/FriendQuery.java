package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 友链查询条件
 *
 * @author ican
 * @date 2023/07/29 16:19
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "友链查询条件")
public class FriendQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

}