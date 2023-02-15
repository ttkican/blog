package com.ican.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 逻辑删除
 *
 * @author ican
 */
@Data
@ApiModel(description = "逻辑删除")
public class DeleteDTO {

    /**
     * id列表
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id列表")
    private List<Integer> idList;

    /**
     * 是否删除 (0否 1是)
     */
    @NotNull(message = "状态值不能为空")
    @ApiModelProperty(value = "是否删除 (0否 1是)")
    private Integer isDelete;
}
