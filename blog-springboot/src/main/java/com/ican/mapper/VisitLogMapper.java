package com.ican.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.VisitLog;
import com.ican.model.vo.UserViewVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 访问日志Mapper
 *
 * @author ican
 */
@Repository
public interface VisitLogMapper extends BaseMapper<VisitLog> {

    /**
     * 查询访问日志
     *
     * @param limit   页码
     * @param size    大小
     * @param keyword 关键字
     * @return 访问日志列表
     */
    List<VisitLog> selectVisitLogList(@Param("limit") Long limit, @Param("size") Long size, @Param("keyword") String keyword);

    /**
     * 获取7天用户访问结果
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 用户访问结果
     */
    List<UserViewVO> selectUserViewList(@Param("startTime") DateTime startTime, @Param("endTime") DateTime endTime);

    /**
     * 清除一周前的访问日志
     *
     * @param endTime 结束时间
     */
    void deleteVisitLog(@Param("endTime") DateTime endTime);
}




