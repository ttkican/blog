package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.ican.annotation.OptLogger;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.Result;
import com.ican.model.vo.query.TaskQuery;
import com.ican.model.vo.request.StatusReq;
import com.ican.model.vo.request.TaskReq;
import com.ican.model.vo.request.TaskRunReq;
import com.ican.model.vo.response.TaskBackResp;
import com.ican.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ican.constant.OptTypeConstant.*;

/**
 * 定时任务控制器
 *
 * @author ican
 */
@Api(tags = "定时任务模块")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 查看定时任务列表
     *
     * @param taskQuery 任务查询条件
     * @return {@link TaskBackResp} 定时任务列表
     */
    @ApiOperation("查看定时任务列表")
    @SaCheckPermission("monitor:task:list")
    @GetMapping("/admin/task/list")
    public Result<PageResult<TaskBackResp>> listTaskBackVO(TaskQuery taskQuery) {
        return Result.success(taskService.listTaskBackVO(taskQuery));
    }

    /**
     * 添加定时任务
     *
     * @param task 定时任务信息
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @ApiOperation("添加定时任务")
    @SaCheckPermission("monitor:task:add")
    @PostMapping("/admin/task/add")
    public Result<?> addTask(@Validated @RequestBody TaskReq task) {
        taskService.addTask(task);
        return Result.success();
    }

    /**
     * 修改定时任务
     *
     * @param task 定时任务信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation("修改定时任务")
    @SaCheckPermission(value = "monitor:task:update")
    @PutMapping("/admin/task/update")
    public Result<?> updateTask(@Validated @RequestBody TaskReq task) {
        taskService.updateTask(task);
        return Result.success();
    }

    /**
     * 删除定时任务
     *
     * @param taskIdList 定时任务id集合
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation("删除定时任务")
    @SaCheckPermission("monitor:task:delete")
    @DeleteMapping("/admin/task/delete")
    public Result<?> deleteTask(@RequestBody List<Integer> taskIdList) {
        taskService.deleteTask(taskIdList);
        return Result.success();
    }

    /**
     * 修改定时任务状态
     *
     * @param taskStatus 定时任务状态
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation("修改定时任务状态")
    @SaCheckPermission(value = {"monitor:task:update", "monitor:task:status"}, mode = SaMode.OR)
    @PutMapping("/admin/task/changeStatus")
    public Result<?> updateTaskStatus(@Validated @RequestBody StatusReq taskStatus) {
        taskService.updateTaskStatus(taskStatus);
        return Result.success();
    }

    /**
     * 执行定时任务
     *
     * @param taskRun 运行定时任务
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation("执行定时任务")
    @SaCheckPermission("monitor:task:run")
    @PutMapping("/admin/task/run")
    public Result<?> runTask(@RequestBody TaskRunReq taskRun) {
        taskService.runTask(taskRun);
        return Result.success();
    }

}
