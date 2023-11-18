package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 相册后台Response
 *
 * @author ican
 * @date 2022/12/30 16:06
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "相册后台Response")
public class AlbumBackResp extends AlbumResp {

    /**
     * 状态 (1公开 2私密)
     */
    @ApiModelProperty(value = "状态 (1公开 2私密)")
    private Integer status;

    /**
     * 照片数量
     */
    @ApiModelProperty(value = "照片数量")
    private Long photoCount;

}