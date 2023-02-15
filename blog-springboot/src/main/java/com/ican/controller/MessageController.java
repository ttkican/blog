package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.annotation.AccessLimit;
import com.ican.annotation.OptLogger;
import com.ican.annotation.VisitLogger;
import com.ican.model.dto.CheckDTO;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.MessageDTO;
import com.ican.model.vo.MessageBackVO;
import com.ican.model.vo.MessageVO;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.Result;
import com.ican.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ican.constant.OptTypeConstant.DELETE;
import static com.ican.constant.OptTypeConstant.UPDATE;

/**
 * 留言控制器
 *
 * @author ican
 */
@Api(tags = "留言模块")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 查看留言列表
     *
     * @return {@link MessageVO} 留言列表
     */
    @VisitLogger(value = "留言")
    @ApiOperation(value = "查看留言列表")
    @GetMapping("/message/list")
    public Result<List<MessageVO>> listMessageVO() {
        return Result.success(messageService.listMessageVO());
    }

    /**
     * 查看后台留言列表
     *
     * @param condition 条件
     * @return {@link Result<MessageBackVO>} 留言列表
     */
    @ApiOperation(value = "查看后台留言列表")
    @SaCheckPermission("news:message:list")
    @GetMapping("/admin/message/list")
    public Result<PageResult<MessageBackVO>> listMessageBackVO(ConditionDTO condition) {
        return Result.success(messageService.listMessageBackVO(condition));
    }

    /**
     * 添加留言
     *
     * @param message 留言信息
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 3)
    @ApiOperation(value = "添加留言")
    @PostMapping("/message/add")
    public Result<?> addMessage(@Validated @RequestBody MessageDTO message) {
        messageService.addMessage(message);
        return Result.success();
    }

    /**
     * 删除留言
     *
     * @param messageIdList 留言Id列表
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除留言")
    @SaCheckPermission("news:message:delete")
    @DeleteMapping("/admin/message/delete")
    public Result<?> deleteMessage(@RequestBody List<Integer> messageIdList) {
        messageService.removeByIds(messageIdList);
        return Result.success();
    }

    /**
     * 审核留言
     *
     * @param check 审核信息
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "审核留言")
    @SaCheckPermission("news:message:pass")
    @PutMapping("/admin/message/pass")
    public Result<?> updateMessageCheck(@Validated @RequestBody CheckDTO check) {
        messageService.updateMessageCheck(check);
        return Result.success();
    }

}
