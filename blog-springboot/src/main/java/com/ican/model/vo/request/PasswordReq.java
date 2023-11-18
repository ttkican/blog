package com.ican.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户密码
 *
 * @author ican
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户密码")
public class PasswordReq {

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    /**
     * 新密码
     */
    @Size(min = 6, message = "新密码不能少于6位")
    @NotBlank(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
