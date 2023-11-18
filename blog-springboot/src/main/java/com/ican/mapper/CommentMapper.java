package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Comment;
import com.ican.model.vo.query.CommentQuery;
import com.ican.model.vo.response.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论Mapper
 *
 * @author ican
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询后台评论列表
     *
     * @param commentQuery 评论查询条件
     * @return 评论集合
     */
    List<CommentBackResp> selectBackCommentList(@Param("param") CommentQuery commentQuery);

    /**
     * 根据父评论id查询子评论id
     *
     * @param parentId 父评论id
     * @return 子评论id列表
     */
    List<Integer> selectCommentIdByParentId(@Param("parentId") Integer parentId);

    /**
     * 查询评论数量
     *
     * @param commentQuery 评论查询条件
     * @return 评论数量
     */
    Long countComment(@Param("param") CommentQuery commentQuery);

    /**
     * 根据评论类型id获取评论量
     *
     * @param typeIdList  类型id列表
     * @param commentType 评论类型
     * @return 说说评论量
     */
    List<CommentCountResp> selectCommentCountByTypeId(@Param("typeIdList") List<Integer> typeIdList, @Param("commentType") Integer commentType);

    /**
     * 查询最新评论
     *
     * @return 最新评论
     */
    List<RecentCommentResp> selectRecentComment();

    /**
     * 分页查询父评论
     *
     * @param commentQuery 条件
     * @return 评论集合
     */
    List<CommentResp> selectParentComment(@Param("param") CommentQuery commentQuery);

    /**
     * 查询每条父评论下的前三条子评论
     *
     * @param parentCommentIdList 父评论id集合
     * @return 回复集合
     */
    List<ReplyResp> selectReplyByParentIdList(@Param("parentCommentIdList") List<Integer> parentCommentIdList);

    /**
     * 根据父评论id查询回复数量
     *
     * @param parentCommentIdList 父评论id列表
     * @return 回复数量
     */
    List<ReplyCountResp> selectReplyCountByParentId(@Param("parentCommentIdList") List<Integer> parentCommentIdList);

    /**
     * 查询父评论下的子评论
     *
     * @param limit     页码
     * @param size      大小
     * @param commentId 父评论id
     * @return 回复评论集合
     */
    List<ReplyResp> selectReplyByParentId(@Param("limit") Long limit, @Param("size") Long size, @Param("commentId") Integer commentId);
}




