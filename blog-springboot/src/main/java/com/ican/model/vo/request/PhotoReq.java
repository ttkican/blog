package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 照片Request
 *
 * @author ican
 **/
@Data
@ApiModel(description = "照片Request")
public class PhotoReq {

    /**
     * 相册id
     */
    @NotNull(message = "相册id不能为空")
    @ApiModelProperty(value = "相册id", required = true)
    private Integer albumId;

    /**
     * 照片链接
     */
    @ApiModelProperty(value = "照片链接")
    private List<String> photoUrlList;

    /**
     * 照片id
     */
    @ApiModelProperty(value = "照片id")
    private List<Integer> photoIdList;
}