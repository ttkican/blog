package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色 Mapper
 *
 * @author ican
 * @date 2022/12/07 11:09
 **/
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户id查询角色id
     *
     * @param userId 用户id
     * @return 角色id
     */
    List<String> selectRoleIdByUserId(@Param("userId") Integer userId);

    /**
     * 添加用户角色
     *
     * @param userId     用户id
     * @param roleIdList 角色id集合
     */
    void insertUserRole(@Param("userId") Integer userId, @Param("roleIdList") List<String> roleIdList);
}
