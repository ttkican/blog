package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.OperationLog;
import com.ican.model.vo.query.LogQuery;
import com.ican.model.vo.response.OperationLogResp;
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
     * @param logQuery 条件
     * @return 操作日志列表
     */
    List<OperationLogResp> selectOperationLogVOList(@Param("param") LogQuery logQuery);

}
