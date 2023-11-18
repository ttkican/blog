package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 推荐Request
 *
 * @author ican
 **/
@Data
@ApiModel(description = "推荐Request")
public class RecommendReq {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id", required = true)
    private Integer id;

    /**
     * 是否推荐 (0否 1是)
     */
    @NotNull(message = "推荐状态不能为空")
    @ApiModelProperty(value = "是否推荐 (0否 1是)", required = true)
    private Integer isRecommend;
}