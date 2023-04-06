package com.ican.model.vo;

import lombok.Data;

/**
 * QQ token信息
 *
 * @author ican
 * @date 2023/04/06 23:31
 **/
@Data
public class QqTokenVO {

    /**
     * openid
     */
    private String openid;

    /**
     * 客户端id
     */
    private String client_id;
}