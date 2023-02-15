package com.ican.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.Friend;
import com.ican.mapper.FriendMapper;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.FriendDTO;
import com.ican.model.vo.FriendBackVO;
import com.ican.model.vo.FriendVO;
import com.ican.model.vo.PageResult;
import com.ican.service.FriendService;
import com.ican.utils.BeanCopyUtils;
import com.ican.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链业务接口实现类
 *
 * @author ican
 */
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Override
    public List<FriendVO> listFriendVO() {
        // 查询友链列表
        return friendMapper.selectFriendVOList();
    }

    @Override
    public PageResult<FriendBackVO> listFriendBackVO(ConditionDTO condition) {
        // 查询友链数量
        Long count = friendMapper.selectCount(new LambdaQueryWrapper<Friend>()
                .like(StringUtils.hasText(condition.getKeyword()), Friend::getName, condition.getKeyword())
        );
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台友链列表
        List<FriendBackVO> friendBackVOList = friendMapper.selectFriendBackVOList(PageUtils.getLimit(), PageUtils.getSize(), condition.getKeyword());
        return new PageResult<>(friendBackVOList, count);
    }

    @Override
    public void addFriend(FriendDTO friend) {
        // 新友链
        Friend newFriend = BeanCopyUtils.copyBean(friend, Friend.class);
        // 添加友链
        baseMapper.insert(newFriend);
    }

    @Override
    public void updateFriend(FriendDTO friend) {
        // 新友链
        Friend newFriend = BeanCopyUtils.copyBean(friend, Friend.class);
        // 更新友链
        baseMapper.updateById(newFriend);
    }
}




