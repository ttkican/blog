package com.ican.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Token
 *
 * @author ican
 */
@Data
@ApiModel(description = "Token")
public class TokenDTO {

    /**
     * 访问令牌
     */
    @ApiModelProperty(value = "访问令牌")
    private String access_token;
}
