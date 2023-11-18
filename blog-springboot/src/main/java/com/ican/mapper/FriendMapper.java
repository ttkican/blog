package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Friend;
import com.ican.model.vo.query.FriendQuery;
import com.ican.model.vo.response.FriendBackResp;
import com.ican.model.vo.response.FriendResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 友链 Mapper
 *
 * @author ican
 */
@Repository
public interface FriendMapper extends BaseMapper<Friend> {

    /**
     * 查看友链列表
     *
     * @return 友链列表
     */
    List<FriendResp> selectFriendVOList();

    /**
     * 查看友链后台列表
     *
     * @param friendQuery 友链查询条件
     * @return 友链后台列表
     */
    List<FriendBackResp> selectFriendBackVOList(@Param("param") FriendQuery friendQuery);

}




