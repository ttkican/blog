package com.ican.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.OperationLog;
import com.ican.mapper.OperationLogMapper;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.LogQuery;
import com.ican.model.vo.response.OperationLogResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 操作日志业务接口实现类
 *
 * @author ican
 */
@Service
public class OperationLogService extends ServiceImpl<OperationLogMapper, OperationLog> {

    @Autowired
    private OperationLogMapper operationLogMapper;

    public PageResult<OperationLogResp> listOperationLogVO(LogQuery logQuery) {
        // 查询操作日志数量
        Long count = operationLogMapper.selectCount(new LambdaQueryWrapper<OperationLog>()
                .like(StringUtils.hasText(logQuery.getOptModule()), OperationLog::getModule, logQuery.getOptModule())
                .or()
                .like(StringUtils.hasText(logQuery.getKeyword()), OperationLog::getDescription, logQuery.getKeyword())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询操作日志列表
        List<OperationLogResp> operationLogRespList = operationLogMapper.selectOperationLogVOList(logQuery);
        return new PageResult<>(operationLogRespList, count);
    }

    public void saveOperationLog(OperationLog operationLog) {
        // 保存操作日志
        operationLogMapper.insert(operationLog);
    }

}
