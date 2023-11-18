package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 留言Response
 *
 * @author ican
 */
@Data
@ApiModel(description = "留言Response")
public class MessageResp {

    /**
     * 留言id
     */
    @ApiModelProperty(value = "留言id")
    private Integer id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 留言内容
     */
    @ApiModelProperty(value = "留言内容")
    private String messageContent;
}