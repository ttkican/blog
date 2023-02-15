package com.ican.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ican.entity.Task;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.StatusDTO;
import com.ican.model.dto.TaskDTO;
import com.ican.model.dto.TaskRunDTO;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.TaskBackVO;

import java.util.List;

/**
 * 定时任务业务接口
 *
 * @author ican
 */
public interface TaskService extends IService<Task> {

    /**
     * 查看定时任务列表
     *
     * @param condition 条件
     * @return 定时任务列表
     */
    PageResult<TaskBackVO> listTaskBackVO(ConditionDTO condition);

    /**
     * 添加定时任务
     *
     * @param task 定时任务
     */
    void addTask(TaskDTO task);

    /**
     * 修改定时任务
     *
     * @param task 定时任务信息
     */
    void updateTask(TaskDTO task);

    /**
     * 删除定时任务
     *
     * @param taskIdList 定时任务id集合
     */
    void deleteTask(List<Integer> taskIdList);

    /**
     * 修改定时任务状态
     *
     * @param taskStatus 定时任务状态
     */
    void updateTaskStatus(StatusDTO taskStatus);

    /**
     * 定时任务立即执行一次
     *
     * @param taskRun 定时任务
     */
    void runTask(TaskRunDTO taskRun);
}
