package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 相册Response
 *
 * @author ican
 **/
@Data
@ApiModel(description = "相册Response")
public class AlbumResp {

    /**
     * 相册id
     */
    @ApiModelProperty(value = "相册id")
    private Integer id;

    /**
     * 相册名
     */
    @ApiModelProperty(value = "相册名")
    private String albumName;

    /**
     * 相册封面
     */
    @ApiModelProperty(value = "相册封面")
    private String albumCover;

    /**
     * 相册描述
     */
    @ApiModelProperty(value = "相册描述")
    private String albumDesc;

}