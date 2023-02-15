package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Task;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.TaskBackVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 定时任务 Mapper
 *
 * @author ican
 */
@Repository
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 查询定时任务数量
     *
     * @param condition 条件
     * @return 数量
     */
    Long countTaskBackVO(@Param("condition") ConditionDTO condition);

    /**
     * 查询定时任务列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 定时任务列表
     */
    List<TaskBackVO> selectTaskBackVO(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);
}




