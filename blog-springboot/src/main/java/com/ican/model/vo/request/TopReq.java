package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 置顶Request
 *
 * @author ican
 */
@Data
@ApiModel(description = "置顶Request")
public class TopReq {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    /**
     * 是否置顶 (0否 1是)
     */
    @NotNull(message = "置顶状态不能为空")
    @ApiModelProperty(value = "是否置顶 (0否 1是)", required = true)
    private Integer isTop;
}