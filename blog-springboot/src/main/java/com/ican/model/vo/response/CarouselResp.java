package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 轮播图Response
 *
 * @author ican
 * @date 2024/02/07 11:27
 **/
@Data
@ApiModel(value = "轮播图Response")
public class CarouselResp {

    /**
     * 轮播图id
     */
    @ApiModelProperty(value = "轮播图id")
    private Integer id;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String imgUrl;

}