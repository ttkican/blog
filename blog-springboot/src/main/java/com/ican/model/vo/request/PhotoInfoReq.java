package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 照片信息Request
 *
 * @author ican
 **/
@Data
@ApiModel(description = "照片信息Request")
public class PhotoInfoReq {

    /**
     * 照片id
     */
    @NotNull(message = "照片id不能为空")
    @ApiModelProperty(value = "照片id", required = true)
    private Integer id;

    /**
     * 照片名
     */
    @NotBlank(message = "照片名不能为空")
    @ApiModelProperty(value = "照片名", required = true)
    private String photoName;

    /**
     * 照片描述
     */
    @ApiModelProperty(value = "照片描述")
    private String photoDesc;
}