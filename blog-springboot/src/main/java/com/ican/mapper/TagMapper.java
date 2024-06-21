package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Tag;
import com.ican.model.vo.query.TagQuery;
import com.ican.model.vo.response.TagBackResp;
import com.ican.model.vo.response.TagOptionResp;
import com.ican.model.vo.response.TagResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签 Mapper
 *
 * @author ican
 * @date 2022/12/02 22:08
 **/
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 查询后台标签列表
     *
     * @param tagQuery 标签查询条件
     * @return 后台标签列表
     */
    List<TagBackResp> selectBackTagList(@Param("param") TagQuery tagQuery);

    /**
     * 批量保存新标签
     *
     * @param newTagList 新标签
     */
    void saveBatchTag(@Param("newTagList") List<Tag> newTagList);

    /**
     * 根据文章id查询文章标签名称
     *
     * @param articleId 文章id
     * @return 文章标签名称
     */
    List<String> selectTagNameByArticleId(@Param("articleId") Integer articleId);

    /**
     * 查询文章标签列表
     *
     * @return 文章标签列表
     */
    List<TagResp> selectTagVOList();

    /**
     * 查询标签列表
     *
     * @return 标签列表
     */
    List<TagOptionResp> selectTagOptionList();
}