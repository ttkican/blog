package com.ican.annotation;

import java.lang.annotation.*;

/**
 * 访问日志注解
 *
 * @author ican
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisitLogger {

    /**
     * @return 访问页面
     */
    String value() default "";
}
