package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务查询条件
 *
 * @author ican
 * @date 2023/07/29 19:08
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "任务查询条件")
public class TaskQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

    /**
     * 任务状态 (0运行 1暂停)
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 任务组名
     */
    @ApiModelProperty(value = "任务组名")
    private String taskGroup;
}