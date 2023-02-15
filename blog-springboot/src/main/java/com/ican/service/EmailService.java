package com.ican.service;

import com.ican.model.dto.MailDTO;

/**
 * 邮件服务接口
 *
 * @author ican
 **/
public interface EmailService {

    /**
     * 发送简单邮件
     *
     * @param mailDTO 邮件信息
     */
    void sendSimpleMail(MailDTO mailDTO);

    /**
     * 发送HTML邮件
     *
     * @param mailDTO 邮件信息
     */
    void sendHtmlMail(MailDTO mailDTO);
}