package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日志查询条件
 *
 * @author ican
 * @date 2023/07/29 17:47
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "日志查询条件")
public class LogQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

    /**
     * 操作模块
     */
    @ApiModelProperty(value = "操作模块")
    private String optModule;

}