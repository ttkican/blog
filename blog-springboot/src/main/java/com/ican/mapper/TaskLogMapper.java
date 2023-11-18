package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.TaskLog;
import com.ican.model.vo.query.TaskQuery;
import com.ican.model.vo.response.TaskLogResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时任务日志 Mapper
 *
 * @author ican
 */
@Repository
public interface TaskLogMapper extends BaseMapper<TaskLog> {

    /**
     * 查询定时任务日志数量
     *
     * @param taskQuery 条件
     * @return 定时任务日志数量
     */
    Long selectTaskLogCount(@Param("param") TaskQuery taskQuery);

    /**
     * 查询定时任务日志列表
     *
     * @param taskQuery 条件
     * @return 定时任务日志列表
     */
    List<TaskLogResp> selectTaskLogRespList(@Param("param") TaskQuery taskQuery);

}




