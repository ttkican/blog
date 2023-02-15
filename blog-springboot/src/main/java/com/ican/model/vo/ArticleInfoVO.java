package com.ican.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 文章信息
 *
 * @author ican
 */
@Data
@ApiModel(description = "文章信息")
public class ArticleInfoVO {

    /**
     * 文章id
     */
    @ApiModelProperty(value = "文章id")
    private Integer id;

    /**
     * 分类id
     */
    @JsonIgnore
    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    /**
     * 文章缩略图
     */
    @ApiModelProperty(value = "文章缩略图")
    private String articleCover;

    /**
     * 文章标题
     */
    @ApiModelProperty(value = "文章标题")
    private String articleTitle;

    /**
     * 文章内容
     */
    @ApiModelProperty(value = "文章内容")
    private String articleContent;

    /**
     * 文章类型 (1原创 2转载 3翻译)
     */
    @ApiModelProperty(value = "文章类型 (1原创 2转载 3翻译)")
    private Integer articleType;

    /**
     * 是否置顶 (0否 1是)
     */
    @ApiModelProperty(value = "是否置顶 (0否 1是)")
    private Integer isTop;

    /**
     * 是否推荐 (0否 1是)
     */
    @ApiModelProperty(value = "是否推荐 (0否 1是)")
    private Integer isRecommend;

    /**
     * 状态 (1公开 2私密 3草稿)
     */
    @ApiModelProperty(value = "状态 (1公开 2私密 3草稿)")
    private Integer status;

    /**
     * 文章分类名
     */
    @ApiModelProperty(value = "文章分类名")
    private String categoryName;

    /**
     * 文章标签名
     */
    @ApiModelProperty(value = "文章标签名")
    private List<String> tagNameList;
}