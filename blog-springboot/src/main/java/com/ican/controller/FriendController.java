package com.ican.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.ican.annotation.OptLogger;
import com.ican.annotation.VisitLogger;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.Result;
import com.ican.model.vo.query.FriendQuery;
import com.ican.model.vo.request.FriendReq;
import com.ican.model.vo.response.FriendBackResp;
import com.ican.model.vo.response.FriendResp;
import com.ican.service.FriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ican.constant.OptTypeConstant.*;

/**
 * 友链控制器
 *
 * @author ican
 */
@Api(tags = "友链模块")
@RestController
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * 查看友链列表
     *
     * @return {@link Result<FriendResp>} 友链列表
     */
    @VisitLogger(value = "友链")
    @ApiOperation(value = "查看友链列表")
    @GetMapping("/friend/list")
    public Result<List<FriendResp>> listFriendVO() {
        return Result.success(friendService.listFriendVO());
    }

    /**
     * 查看友链后台列表
     *
     * @param friendQuery 查询条件
     * @return {@link PageResult<FriendBackResp>} 后台友链列表
     */
    @ApiOperation(value = "查看友链后台列表")
    @SaCheckPermission("web:friend:list")
    @GetMapping("/admin/friend/list")
    public Result<PageResult<FriendBackResp>> listFriendBackVO(FriendQuery friendQuery) {
        return Result.success(friendService.listFriendBackVO(friendQuery));
    }

    /**
     * 添加友链
     *
     * @param friend 友链
     * @return {@link Result<>}
     */
    @OptLogger(value = ADD)
    @ApiOperation(value = "添加友链")
    @SaCheckPermission("web:friend:add")
    @PostMapping("/admin/friend/add")
    public Result<?> addFriend(@Validated @RequestBody FriendReq friend) {
        friendService.addFriend(friend);
        return Result.success();
    }

    /**
     * 删除友链
     *
     * @param friendIdList 友链id集合
     * @return {@link Result<>}
     */
    @OptLogger(value = DELETE)
    @ApiOperation(value = "删除友链")
    @SaCheckPermission("web:friend:delete")
    @DeleteMapping("/admin/friend/delete")
    public Result<?> deleteFriend(@RequestBody List<Integer> friendIdList) {
        friendService.removeByIds(friendIdList);
        return Result.success();
    }

    /**
     * 修改友链
     *
     * @param friend 友链
     * @return {@link Result<>}
     */
    @OptLogger(value = UPDATE)
    @ApiOperation(value = "修改友链")
    @SaCheckPermission("web:friend:update")
    @PutMapping("/admin/friend/update")
    public Result<?> updateFriend(@Validated @RequestBody FriendReq friend) {
        friendService.updateFriend(friend);
        return Result.success();
    }

}