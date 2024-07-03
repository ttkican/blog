package com.ican.model.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

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
     * 父级ID
     */
    @ApiModelProperty(value = "父级ID")
    private Integer parentId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 子分类列表
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ApiModelProperty(value = "子分类列表")
    private List<CategoryBackResp> children;

}