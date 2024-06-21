package com.ican.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * B站图片上传返回DTO
 *
 * @author ican
 * @date 2024/06/21 21:43
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "B站图片上传返回DTO")
public class BiliUploadDTO {

    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private String code;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String message;

    /**
     * ttl
     */
    @ApiModelProperty(value = "ttl")
    private Integer ttl;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private UploadData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    @ApiModel(description = "返回数据")
    public static class UploadData {
        /**
         * 图片地址
         */
        @ApiModelProperty(value = "图片地址")
        private String imageUrl;

        /**
         * 图片宽度
         */
        @ApiModelProperty(value = "图片宽度")
        private Integer imageWidth;

        /**
         * 图片高度
         */
        @ApiModelProperty(value = "图片高度")
        private Integer imageHeight;

        /**
         * 图片大小
         */
        @ApiModelProperty(value = "图片大小")
        private Double imgSize;
    }
}