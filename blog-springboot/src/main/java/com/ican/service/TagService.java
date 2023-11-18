package com.ican.service;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.ArticleTag;
import com.ican.entity.Tag;
import com.ican.mapper.ArticleMapper;
import com.ican.mapper.ArticleTagMapper;
import com.ican.mapper.TagMapper;
import com.ican.model.vo.response.ArticleConditionList;
import com.ican.model.vo.response.ArticleConditionResp;
import com.ican.model.vo.PageResult;
import com.ican.model.vo.query.ArticleConditionQuery;
import com.ican.model.vo.query.TagQuery;
import com.ican.model.vo.request.TagReq;
import com.ican.model.vo.response.TagBackResp;
import com.ican.model.vo.response.TagOptionResp;
import com.ican.model.vo.response.TagResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 标签服务
 *
 * @author ican
 * @date 2022/12/02 22:06
 **/
@Service
public class TagService extends ServiceImpl<TagMapper, Tag> {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    public PageResult<TagBackResp> listTagBackVO(TagQuery tagQuery) {
        // 查询标签数量
        Long count = tagMapper.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.hasText(tagQuery.getKeyword()), Tag::getTagName,
                        tagQuery.getKeyword()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 分页查询标签列表
        List<TagBackResp> tagList = tagMapper.selectBackTagList(tagQuery);
        return new PageResult<>(tagList, count);
    }

    public void addTag(TagReq tag) {
        // 标签是否存在
        Tag existTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, tag.getTagName()));
        Assert.isNull(existTag, tag.getTagName() + "标签已存在");
        // 添加新标签
        Tag newTag = Tag.builder()
                .tagName(tag.getTagName())
                .build();
        baseMapper.insert(newTag);
    }

    public void deleteTag(List<Integer> tagIdList) {
        // 标签下是否有文章
        Long count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getTagId, tagIdList));
        Assert.isFalse(count > 0, "删除失败，标签下存在文章");
        // 批量删除标签
        tagMapper.deleteBatchIds(tagIdList);
    }

    public void updateTag(TagReq tag) {
        // 标签是否存在
        Tag existTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, tag.getTagName()));
        Assert.isFalse(Objects.nonNull(existTag) && !existTag.getId().equals(tag.getId()),
                tag.getTagName() + "标签已存在");
        // 修改标签
        Tag newTag = Tag.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .build();
        baseMapper.updateById(newTag);
    }

    public List<TagOptionResp> listTagOption() {
        return tagMapper.selectTagOptionList();
    }

    public List<TagResp> listTagVO() {
        return tagMapper.selectTagVOList();
    }

    public ArticleConditionList listArticleTag(ArticleConditionQuery articleConditionQuery) {
        List<ArticleConditionResp> articleConditionList = articleMapper.selectArticleListByCondition(articleConditionQuery);
        String name = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                        .select(Tag::getTagName)
                        .eq(Tag::getId, articleConditionQuery.getTagId()))
                .getTagName();
        return ArticleConditionList.builder()
                .articleConditionVOList(articleConditionList)
                .name(name)
                .build();
    }

}