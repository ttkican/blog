package com.ican.strategy.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ican.entity.Comment;
import com.ican.mapper.CommentMapper;
import com.ican.service.RedisService;
import com.ican.strategy.LikeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.ican.constant.CommonConstant.FALSE;
import static com.ican.constant.RedisConstant.COMMENT_LIKE_COUNT;
import static com.ican.constant.RedisConstant.USER_COMMENT_LIKE;

/**
 * 评论点赞策略
 *
 * @author ican
 */
@Service("commentLikeStrategyImpl")
public class CommentLikeStrategyImpl implements LikeStrategy {

    @Autowired
    private RedisService redisService;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void like(Integer commentId) {
        // 判断评论是否存在或是否通过或是否进入回收站
        Comment comment = commentMapper.selectOne(new LambdaQueryWrapper<Comment>()
                .select(Comment::getId, Comment::getIsCheck)
                .eq(Comment::getId, commentId).last("limit 1"));
        Assert.isFalse(Objects.isNull(comment) || comment.getIsCheck().equals(FALSE), "文章不存在");
        // 用户id作为键，评论id作为值，记录用户点赞记录
        String userLikeCommentKey = USER_COMMENT_LIKE + StpUtil.getLoginIdAsInt();
        if (redisService.hasSetValue(userLikeCommentKey, commentId)) {
            // 取消点赞则删除用户id中的评论id
            redisService.deleteSet(userLikeCommentKey, commentId);
            // 评论点赞量-1
            redisService.decrHash(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            // 点赞则在用户id记录评论id
            redisService.setSet(userLikeCommentKey, commentId);
            // 评论点赞量+1
            redisService.incrHash(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }
}