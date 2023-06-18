package com.ican.model.dto;

import com.ican.entity.ChatRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 聊天记录DTO
 *
 * @author ican
 * @date 2023/06/12 10:50
 **/
@Data
@Builder
@ApiModel(description = "聊天记录DTO")
public class ChatRecordDTO {

    /**
     * 聊天记录
     */
    @ApiModelProperty(value = "聊天记录")
    private List<ChatRecord> chatRecordList;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ipAddress;

    /**
     * ip来源
     */
    @ApiModelProperty(value = "ip来源")
    private String ipSource;
}