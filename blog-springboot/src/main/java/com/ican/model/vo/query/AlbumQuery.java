package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 相册查询条件
 *
 * @author ican
 * @date 2023/07/29 19:22
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "相册查询条件")
public class AlbumQuery extends PageQuery {

    /**
     * 搜索内容
     */
    @ApiModelProperty(value = "搜索内容")
    private String keyword;

}