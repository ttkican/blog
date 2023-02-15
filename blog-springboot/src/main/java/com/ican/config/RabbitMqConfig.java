package com.ican.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.ican.constant.MqConstant.*;

/**
 * RabbitMQ配置
 *
 * @author ican
 **/
@Slf4j
@Configuration
public class RabbitMqConfig {

    /**
     * 消息转换器
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 邮件交换机
     */
    @Bean
    public TopicExchange emailExchange() {
        return new TopicExchange(EMAIL_EXCHANGE, true, false);
    }

    /**
     * 邮件Simple队列
     */
    @Bean
    public Queue emailSimpleQueue() {
        return new Queue(EMAIL_SIMPLE_QUEUE, true);
    }

    /**
     * 邮件Html队列
     */
    @Bean
    public Queue emailHtmlQueue() {
        return new Queue(EMAIL_HTML_QUEUE, true);
    }

    /**
     * 绑定邮件Simple队列
     */
    @Bean
    public Binding simpleQueueBinding() {
        return BindingBuilder.bind(emailSimpleQueue()).to(emailExchange()).with(EMAIL_SIMPLE_KEY);
    }

    /**
     * 绑定邮件Html队列
     */
    @Bean
    public Binding htmlQueueBinding() {
        return BindingBuilder.bind(emailHtmlQueue()).to(emailExchange()).with(EMAIL_HTML_KEY);
    }

    /**
     * 文章交换机
     */
    @Bean
    public TopicExchange articleExchange() {
        return new TopicExchange(ARTICLE_EXCHANGE, true, false);
    }

    /**
     * 文章队列
     */
    @Bean
    public Queue articleQueue() {
        return new Queue(ARTICLE_QUEUE, true);
    }

    /**
     * 绑定文章队列
     */
    @Bean
    public Binding articleQueueBinding() {
        return BindingBuilder.bind(articleQueue()).to(articleExchange()).with(ARTICLE_KEY);
    }
}