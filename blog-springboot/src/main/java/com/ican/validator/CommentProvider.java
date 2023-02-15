package com.ican.validator;

import com.ican.model.dto.CommentDTO;
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
public class CommentProvider implements DefaultGroupSequenceProvider<CommentDTO> {
    @Override
    public List<Class<?>> getValidationGroups(CommentDTO commentDTO) {
        List<Class<?>> defaultGroupSequence = new ArrayList<>();
        defaultGroupSequence.add(CommentDTO.class);
        if (commentDTO != null) {
            if (commentDTO.getCommentType().equals(ARTICLE.getType()) || commentDTO.getCommentType().equals(TALK.getType())) {
                defaultGroupSequence.add(ArticleTalk.class);
            }
            if (commentDTO.getCommentType().equals(LINK.getType())) {
                defaultGroupSequence.add(Link.class);
            }
            if (Objects.isNull(commentDTO.getParentId())) {
                defaultGroupSequence.add(ParentIdNull.class);
            }
            if (Objects.nonNull(commentDTO.getParentId())) {
                defaultGroupSequence.add(ParentIdNotNull.class);
            }
        }
        return defaultGroupSequence;
    }
}