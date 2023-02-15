package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 定时任务日志
 *
 * @author ican
 */
@Data
@ApiModel(description = "定时任务日志")
public class TaskLogVO {

    /**
     * 任务日志id
     */
    @ApiModelProperty(value = "任务日志id")
    private Integer id;

    /**
     * 任务名称
     */
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    /**
     * 任务组名
     */
    @ApiModelProperty(value = "任务组名")
    private String taskGroup;

    /**
     * 调用目标
     */
    @ApiModelProperty(value = "调用目标")
    private String invokeTarget;

    /**
     * 日志信息
     */
    @ApiModelProperty(value = "日志信息")
    private String taskMessage;

    /**
     * 任务状态 (0失败 1成功)
     */
    @ApiModelProperty(value = "任务状态 (0失败 1成功)")
    private Integer status;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误信息")
    private String errorInfo;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
