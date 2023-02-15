package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.TaskLog;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.TaskLogVO;
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
     * @param condition 条件
     * @return 定时任务日志数量
     */
    Long selectTaskLogCount(@Param("condition") ConditionDTO condition);

    /**
     * 查询定时任务日志列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 定时任务日志列表
     */
    List<TaskLogVO> selectTaskLogVOList(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);
}




