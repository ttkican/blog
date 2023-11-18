package com.ican.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 友链后台Response
 *
 * @author ican
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "友链后台Response")
public class FriendBackResp extends FriendResp {

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}