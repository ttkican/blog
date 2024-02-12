package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 轮播图查询条件
 *
 * @author ican
 * @date 2024/02/03 12:07
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "轮播图查询条件")
public class CarouselQuery extends PageQuery {

    /**
     * 是否显示 (0否 1是)
     */
    @ApiModelProperty(value = "是否显示 (0否 1是)")
    private Integer status;
}