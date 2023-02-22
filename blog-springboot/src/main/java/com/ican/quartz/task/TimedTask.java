package com.ican.quartz.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ican.mapper.VisitLogMapper;
import com.ican.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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

    @Autowired
    private VisitLogMapper visitLogMapper;

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

    /**
     * 清除一周前的访问日志
     */
    public void clearVistiLog() {
        DateTime endTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        visitLogMapper.deleteVisitLog(endTime);
    }
}