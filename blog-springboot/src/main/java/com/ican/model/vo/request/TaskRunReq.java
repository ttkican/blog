package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 定时任务运行Request
 *
 * @author ican
 */
@Data
@ApiModel(description = "定时任务运行Request")
public class TaskRunReq {

    /**
     * 任务id
     */
    @ApiModelProperty(value = "任务id")
    private Integer id;

    /**
     * 任务组别
     */
    @ApiModelProperty(value = "任务组别")
    private String taskGroup;
}