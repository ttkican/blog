package com.ican.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.TaskLog;
import com.ican.mapper.TaskLogMapper;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.TaskLogVO;
import com.ican.service.TaskLogService;
import com.ican.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务日志业务接口实现类
 *
 * @author ican
 */
@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements TaskLogService {

    @Autowired
    private TaskLogMapper taskLogMapper;

    @Override
    public PageResult<TaskLogVO> listTaskLog(ConditionDTO condition) {
        // 查询定时任务日志数量
        Long count = taskLogMapper.selectTaskLogCount(condition);
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询定时任务日志列表
        List<TaskLogVO> taskLogVOList = taskLogMapper.selectTaskLogVOList(PageUtils.getLimit(), PageUtils.getSize(), condition);
        return new PageResult<>(taskLogVOList, count);
    }

    @Override
    public void clearTaskLog() {
        taskLogMapper.delete(null);
    }
}




