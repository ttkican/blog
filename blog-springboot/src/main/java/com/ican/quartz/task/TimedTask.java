package com.ican.quartz.task;

import com.ican.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ican.constant.RedisConstant.UNIQUE_VISITOR;

/**
 * 执行定时任务
 *
 * @author ican
 */
@SuppressWarnings(value = "all")
@Component("timedTask")
public class TimedTask {
    @Autowired
    private RedisService redisService;

    /**
     * 清除博客访问记录
     */
    public void clear() {
        redisService.deleteObject(UNIQUE_VISITOR);
    }

    /**
     * 测试任务
     */
    public void test() {
        System.out.println("测试任务");
    }
}