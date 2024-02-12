package com.ican.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.Friend;
import com.ican.mapper.FriendMapper;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.FriendQuery;
import com.ican.model.vo.request.FriendReq;
import com.ican.model.vo.response.FriendBackResp;
import com.ican.model.vo.response.FriendResp;
import com.ican.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链服务
 *
 * @author ican
 */
@Service
public class FriendService extends ServiceImpl<FriendMapper, Friend> {

    @Autowired
    private FriendMapper friendMapper;

    public List<FriendResp> listFriendVO() {
        // 查询友链列表
        return friendMapper.selectFriendVOList();
    }

    public PageResult<FriendBackResp> listFriendBackVO(FriendQuery friendQuery) {
        // 查询友链数量
        Long count = friendMapper.selectCount(new LambdaQueryWrapper<Friend>()
                .like(StringUtils.hasText(friendQuery.getKeyword()), Friend::getName, friendQuery.getKeyword())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台友链列表
        List<FriendBackResp> friendBackVOList = friendMapper.selectFriendBackVOList(friendQuery);
        return new PageResult<>(friendBackVOList, count);
    }

    public void addFriend(FriendReq friend) {
        // 新友链
        Friend newFriend = BeanCopyUtils.copyBean(friend, Friend.class);
        // 添加友链
        baseMapper.insert(newFriend);
    }

    public void updateFriend(FriendReq friend) {
        // 新友链
        Friend newFriend = BeanCopyUtils.copyBean(friend, Friend.class);
        // 更新友链
        baseMapper.updateById(newFriend);
    }
}




