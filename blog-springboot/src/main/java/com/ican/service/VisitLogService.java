package com.ican.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ican.entity.VisitLog;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.PageResult;

/**
 * 访问业务接口
 *
 * @author ican
 */
public interface VisitLogService extends IService<VisitLog> {

    /**
     * 保存访问日志
     *
     * @param visitLog 访问日志信息
     */
    void saveVisitLog(VisitLog visitLog);

    /**
     * 查看访问日志列表
     *
     * @param condition 条件
     * @return 日志列表
     */
    PageResult<VisitLog> listVisitLog(ConditionDTO condition);
}
