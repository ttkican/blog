package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 说说查询条件
 *
 * @author ican
 * @date 2023/07/29 19:01
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "说说查询条件")
public class TalkQuery extends PageQuery {

    /**
     * 状态 (1公开  2私密)
     */
    @ApiModelProperty(value = "状态 (1公开  2私密)")
    private Integer status;

}