package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.User;
import com.ican.model.vo.query.UserQuery;
import com.ican.model.vo.response.UserBackResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户 Mapper
 *
 * @author ican
 * @date 2022/12/05 15:28
 **/
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户后台数量
     *
     * @param userQuery 用户查询条件
     * @return 用户数量
     */
    Long selectUserCount(@Param("param") UserQuery userQuery);

    /**
     * 查询后台用户列表
     *
     * @param userQuery 用户查询条件
     * @return 用户后台列表
     */
    List<UserBackResp> selectUserList(@Param("param") UserQuery userQuery);
}