package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户浏览Response
 *
 * @author ican
 **/
@Data
@ApiModel(description = "用户浏览Response")
public class UserViewResp {

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期")
    private String date;

    /**
     * pv
     */
    @ApiModelProperty(value = "pv")
    private Integer pv;

    /**
     * uv
     */
    @ApiModelProperty(value = "uv")
    private Integer uv;
}