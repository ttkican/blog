package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.entity.ExceptionLog;
import com.ican.entity.VisitLog;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.Result;
import com.ican.model.vo.query.LogQuery;
import com.ican.model.vo.query.TaskQuery;
import com.ican.model.vo.response.OperationLogResp;
import com.ican.model.vo.response.TaskLogResp;
import com.ican.service.ExceptionLogService;
import com.ican.service.OperationLogService;
import com.ican.service.TaskLogService;
import com.ican.service.VisitLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 日志控制器
 *
 * @author ican
 */
@Api(tags = "日志模块")
@RestController
public class LogController {

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ExceptionLogService exceptionLogService;

    @Autowired
    private VisitLogService visitLogService;

    @Autowired
    private TaskLogService taskLogService;

    /**
     * 查看操作日志
     *
     * @param logQuery 条件
     * @return {@link OperationLogResp} 操作日志
     */
    @ApiOperation(value = "查看操作日志")
    @SaCheckPermission("log:operation:list")
    @GetMapping("/admin/operation/list")
    public Result<PageResult<OperationLogResp>> listOperationLogVO(LogQuery logQuery) {
        return Result.success(operationLogService.listOperationLogVO(logQuery));
    }

    /**
     * 删除操作日志
     *
     * @param logIdList 日志id集合
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除操作日志")
    @SaCheckPermission("log:operation:delete")
    @DeleteMapping("/admin/operation/delete")
    public Result<?> deleteOperationLog(@RequestBody List<Integer> logIdList) {
        operationLogService.removeByIds(logIdList);
        return Result.success();
    }

    /**
     * 查看异常日志
     *
     * @param logQuery 异常日志查询条件
     * @return {@link Result< OperationLogResp >} 异常日志列表
     */
    @ApiOperation(value = "查看异常日志")
    @SaCheckPermission("log:exception:list")
    @GetMapping("/admin/exception/list")
    public Result<PageResult<ExceptionLog>> listExceptionLog(LogQuery logQuery) {
        return Result.success(exceptionLogService.listExceptionLog(logQuery));
    }

    /**
     * 删除异常日志
     *
     * @param logIdList 日志id集合
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除异常日志")
    @SaCheckPermission("log:exception:delete")
    @DeleteMapping("/admin/exception/delete")
    public Result<?> deleteExceptionLog(@RequestBody List<Integer> logIdList) {
        exceptionLogService.removeByIds(logIdList);
        return Result.success();
    }

    /**
     * 查看访问日志
     *
     * @param logQuery 访问日志查询条件
     * @return {@link Result< OperationLogResp >} 访问日志列表
     */
    @ApiOperation(value = "查看访问日志")
    @SaCheckPermission("log:visit:list")
    @GetMapping("/admin/visit/list")
    public Result<PageResult<VisitLog>> listVisitLog(LogQuery logQuery) {
        return Result.success(visitLogService.listVisitLog(logQuery));
    }

    /**
     * 删除访问日志
     *
     * @param logIdList 日志id集合
     * @return {@link Result<>}
     */
    @ApiOperation(value = "删除访问日志")
    @SaCheckPermission("log:visit:delete")
    @DeleteMapping("/admin/visit/delete")
    public Result<?> deleteVisitLog(@RequestBody List<Integer> logIdList) {
        visitLogService.removeByIds(logIdList);
        return Result.success();
    }

    /**
     * 查看定时任务日志
     *
     * @param taskQuery 条件
     * @return {@link PageResult< TaskLogResp >} 后台定时任务日志
     */
    @ApiOperation("查看定时任务日志")
    @SaCheckPermission("log:task:list")
    @GetMapping("/admin/taskLog/list")
    public Result<PageResult<TaskLogResp>> listTaskLog(TaskQuery taskQuery) {
        return Result.success(taskLogService.listTaskLog(taskQuery));
    }

    /**
     * 删除定时任务日志
     *
     * @param logIdList 日志id集合
     * @return {@link Result<>}
     */
    @ApiOperation("删除定时任务的日志")
    @SaCheckPermission("log:task:delete")
    @DeleteMapping("/admin/taskLog/delete")
    public Result<?> deleteTaskLog(@RequestBody List<Integer> logIdList) {
        taskLogService.removeByIds(logIdList);
        return Result.success();
    }

    /**
     * 清空定时任务日志
     *
     * @return {@link Result<>}
     */
    @ApiOperation("清空定时任务日志")
    @SaCheckPermission("log:task:clear")
    @DeleteMapping("/admin/taskLog/clear")
    public Result<?> clearTaskLog() {
        taskLogService.clearTaskLog();
        return Result.success();
    }
}
