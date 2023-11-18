package com.ican.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.VisitLog;
import com.ican.mapper.VisitLogMapper;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.LogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 访问服务
 *
 * @author ican
 */
@Service
public class VisitLogService extends ServiceImpl<VisitLogMapper, VisitLog> {

    @Autowired
    private VisitLogMapper visitLogMapper;

    public void saveVisitLog(VisitLog visitLog) {
        // 保存访问日志
        visitLogMapper.insert(visitLog);
    }

    public PageResult<VisitLog> listVisitLog(LogQuery logQuery) {
        // 查询访问日志数量
        Long count = visitLogMapper.selectCount(new LambdaQueryWrapper<VisitLog>()
                .like(StringUtils.hasText(logQuery.getKeyword()), VisitLog::getPage, logQuery.getKeyword())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询访问日志列表
        List<VisitLog> visitLogVOList = visitLogMapper.selectVisitLogList(logQuery);
        return new PageResult<>(visitLogVOList, count);
    }
}




