package com.ican.service;

import com.ican.model.dto.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 邮件服务
 *
 * @author ican
 **/
@Slf4j
@Service
public class EmailService {

    /**
     * 邮箱号
     */
    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleMail(MailDTO mailDTO) {
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom(email);
        simpleMail.setTo(mailDTO.getToEmail());
        simpleMail.setSubject(mailDTO.getSubject());
        simpleMail.setText(mailDTO.getContent());
        javaMailSender.send(simpleMail);
    }

    public void sendHtmlMail(MailDTO mailDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            Context context = new Context();
            context.setVariables(mailDTO.getContentMap());
            String process = templateEngine.process(mailDTO.getTemplate(), context);
            mimeMessageHelper.setFrom(email);
            mimeMessageHelper.setTo(mailDTO.getToEmail());
            mimeMessageHelper.setSubject(mailDTO.getSubject());
            mimeMessageHelper.setText(process, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("sendHtmlMail fail, {}", e.getMessage());
        }
    }
}