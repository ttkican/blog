package com.ican.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志VO
 *
 * @author ican
 */
@Data
@ApiModel(description = "操作日志VO")
public class OperationLogVO {

    /**
     * 操作日志id
     */
    @ApiModelProperty(value = "操作日志id")
    private Integer id;

    /**
     * 操作模块
     */
    @ApiModelProperty(value = "操作模块")
    private String module;

    /**
     * 操作uri
     */
    @ApiModelProperty(value = "操作uri")
    private String uri;

    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型")
    private String type;

    /**
     * 操作方法
     */
    @ApiModelProperty(value = "操作方法")
    private String name;

    /**
     * 操作描述
     */
    @ApiModelProperty(value = "操作描述")
    private String description;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    private String method;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private String params;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private String data;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 操作ip
     */
    @ApiModelProperty(value = "操作ip")
    private String ipAddress;

    /**
     * 操作地址
     */
    @ApiModelProperty(value = "操作地址")
    private String ipSource;

    /**
     * 操作耗时 (毫秒)
     */
    @ApiModelProperty(value = "操作耗时 (毫秒)")
    private Long times;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}