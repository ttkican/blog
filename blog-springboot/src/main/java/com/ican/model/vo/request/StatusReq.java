package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 状态Request
 *
 * @author ican
 */
@Data
@ApiModel(description = "状态Request")
public class StatusReq {
    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态", required = true)
    private Integer status;
}
