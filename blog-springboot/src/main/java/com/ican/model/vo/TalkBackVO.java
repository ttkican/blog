package com.ican.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 说说后台VO
 *
 * @author ican
 */
@Data
@ApiModel(description = "说说后台VO")
public class TalkBackVO {

    /**
     * 说说id
     */
    @ApiModelProperty(value = "说说id")
    private Integer id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 说说内容
     */
    @ApiModelProperty(value = "说说内容")
    private String talkContent;

    /**
     * 图片
     */
    @JsonIgnore
    @ApiModelProperty(value = "图片")
    private String images;

    /**
     * 图片列表
     */
    @ApiModelProperty(value = "图片列表")
    private List<String> imgList;

    /**
     * 是否置顶 (0否 1是)
     */
    @ApiModelProperty(value = "是否置顶 (0否 1是)")
    private Integer isTop;

    /**
     * 说说状态 (1公开 2私密)
     */
    @ApiModelProperty(value = "说说状态 (1公开 2私密)")
    private Integer status;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    private LocalDateTime createTime;

}
