package com.ican.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * websocket聊天室DTO
 *
 * @author ican
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebsocketMessageDTO {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 数据
     */
    private Object data;

}
