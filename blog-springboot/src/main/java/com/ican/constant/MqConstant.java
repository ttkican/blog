package com.ican.constant;

/**
 * MQ常量
 *
 * @author ican
 */
public class MqConstant {

    /**
     * 邮件交换机
     */
    public static final String EMAIL_EXCHANGE = "email.topic";

    /**
     * 邮件SIMPLE队列
     */
    public static final String EMAIL_SIMPLE_QUEUE = "email.simple.queue";

    /**
     * 邮件HTML队列
     */
    public static final String EMAIL_HTML_QUEUE = "email.html.queue";

    /**
     * 邮件Simple RoutingKey
     */
    public static final String EMAIL_SIMPLE_KEY = "email.simple.key";

    /**
     * 邮件Html RoutingKey
     */
    public static final String EMAIL_HTML_KEY = "email.html.key";

    /**
     * 文章交换机
     */
    public static final String ARTICLE_EXCHANGE = "article.topic";

    /**
     * 文章队列
     */
    public static final String ARTICLE_QUEUE = "article.queue";

    /**
     * 文章RoutingKey
     */
    public final static String ARTICLE_KEY = "article.key";

}
