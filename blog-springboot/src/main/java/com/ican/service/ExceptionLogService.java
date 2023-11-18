package com.ican.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.ExceptionLog;
import com.ican.mapper.ExceptionLogMapper;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.LogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 异常日志服务
 *
 * @author ican
 */
@Service
public class ExceptionLogService extends ServiceImpl<ExceptionLogMapper, ExceptionLog> {

    @Autowired
    private ExceptionLogMapper exceptionLogMapper;

    public PageResult<ExceptionLog> listExceptionLog(LogQuery logQuery) {
        // 查询异常日志数量
        Long count = exceptionLogMapper.selectCount(new LambdaQueryWrapper<ExceptionLog>()
                .like(StringUtils.hasText(logQuery.getOptModule()), ExceptionLog::getModule, logQuery.getOptModule())
                .or()
                .like(StringUtils.hasText(logQuery.getKeyword()), ExceptionLog::getDescription, logQuery.getKeyword())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询异常日志列表
        List<ExceptionLog> operationLogVOList = exceptionLogMapper.selectExceptionLogList(logQuery);
        return new PageResult<>(operationLogVOList, count);
    }

    public void saveExceptionLog(ExceptionLog exceptionLog) {
        // 保存异常日志
        exceptionLogMapper.insert(exceptionLog);
    }
}




