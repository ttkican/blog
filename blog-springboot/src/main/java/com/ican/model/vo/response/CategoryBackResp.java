package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 分类后台Response
 *
 * @author ican
 * @date 2022/12/03 21:43
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "分类后台Response")
public class CategoryBackResp extends CategoryResp {

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}