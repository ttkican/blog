package com.ican.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 邮箱DTO
 *
 * @author ican
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "邮箱DTO")
public class MailDTO {

    /**
     * 接收者邮箱号
     */
    @ApiModelProperty(value = "接收者邮箱号")
    private String toEmail;

    /**
     * 主题
     */
    @ApiModelProperty(value = "主题")
    private String subject;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 内容信息
     */
    @ApiModelProperty(value = "内容信息")
    private Map<String, Object> contentMap;

    /**
     * 邮件模板
     */
    @ApiModelProperty(value = "邮件模板")
    private String template;
}
