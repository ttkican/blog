package com.ican.validator;

import com.ican.model.vo.request.CommentReq;
import com.ican.validator.groups.ArticleTalk;
import com.ican.validator.groups.Link;
import com.ican.validator.groups.ParentIdNotNull;
import com.ican.validator.groups.ParentIdNull;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ican.enums.CommentTypeEnum.*;

/**
 * 评论分组校验器
 *
 * @author ican
 **/
public class CommentProvider implements DefaultGroupSequenceProvider<CommentReq> {
    @Override
    public List<Class<?>> getValidationGroups(CommentReq commentReq) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(CommentReq.class);
        if (commentReq != null) {
            if (commentReq.getCommentType().equals(ARTICLE.getType()) || commentReq.getCommentType().equals(TALK.getType())) {
                defaultGroupSequence.add(ArticleTalk.class);
            }
            if (commentReq.getCommentType().equals(LINK.getType())) {
                defaultGroupSequence.add(Link.class);
            }
            if (Objects.isNull(commentReq.getParentId())) {
                defaultGroupSequence.add(ParentIdNull.class);
            }
            if (Objects.nonNull(commentReq.getParentId())) {
                defaultGroupSequence.add(ParentIdNotNull.class);
            }
        }
        return defaultGroupSequence;
    }
}