package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.ExceptionLog;
import com.ican.model.vo.query.LogQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 异常日志Mapper
 *
 * @author ican
 */
@Repository
public interface ExceptionLogMapper extends BaseMapper<ExceptionLog> {

    /**
     * 查询异常日志
     *
     * @param logQuery 异常日志查询条件
     * @return 异常日志列表
     */
    List<ExceptionLog> selectExceptionLogList(@Param("param") LogQuery logQuery);
}




