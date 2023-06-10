package com.ican.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * QQ登录DTO
 *
 * @author ican
 * @date 2023/04/06 23:30
 **/
@Data
public class QqLoginDTO {

    /**
     * openId
     */
    private String openId;

    /**
     * clientId
     */
    private String clientId;
}