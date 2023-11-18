package com.ican.model.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件查询条件
 *
 * @author ican
 * @date 2023/11/18 19:44
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "文件查询条件")
public class FileQuery extends PageQuery {

    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String filePath;

}