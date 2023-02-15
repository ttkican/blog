package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.OperationLog;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.OperationLogVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作日志 Mapper
 *
 * @author ican
 */
@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 查询操作日志
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 操作日志列表
     */
    List<OperationLogVO> selectOperationLogVOList(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);

}
