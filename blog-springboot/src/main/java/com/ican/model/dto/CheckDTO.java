package com.ican.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 审核DTO
 *
 * @author ican
 */
@Data
@ApiModel(description = "审核DTO")
public class CheckDTO {

    /**
     * id集合
     */
    @NotNull(message = "id不能为空")
    @ApiModelProperty(value = "id集合")
    private List<Integer> idList;

    /**
     * 是否通过 (0否 1是)
     */
    @NotNull(message = "状态值不能为空")
    @ApiModelProperty(value = "是否通过 (0否 1是)")
    private Integer isCheck;
}
