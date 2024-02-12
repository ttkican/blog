package com.ican.websocket;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ican.entity.ChatRecord;
import com.ican.enums.ChatTypeEnum;
import com.ican.mapper.ChatRecordMapper;
import com.ican.model.dto.ChatRecordDTO;
import com.ican.model.dto.WebsocketMessageDTO;
import com.ican.utils.HTMLUtils;
import com.ican.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket服务
 *
 * @author ican
 * @date 2023/06/12 09:11
 **/
@Slf4j
@Component
@ServerEndpoint(value = "/websocket", configurator = WebsocketService.ChatConfigurator.class)
public class WebsocketService {

    private static ChatRecordMapper chatRecordMapper;

    @Autowired
    public void setChatRecordDao(ChatRecordMapper chatRecordMapper) {
        WebsocketService.chatRecordMapper = chatRecordMapper;
    }

    /**
     * 在线人数
     */
    private static final AtomicInteger ONLINE_NUM = new AtomicInteger();

    /**
     * 用户session
     */
    private static final ConcurrentHashMap<String, Session> WS_CONNECTIONS = new ConcurrentHashMap<>();

    /**
     * 获取客户端真实ip
     */
    public static class ChatConfigurator extends ServerEndpointConfig.Configurator {

        public static final String IP = "X-Real-IP";

        @Override
        public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
            try {
                String ipAddress = request.getHeaders().get(IP.toLowerCase()).get(0);
                config.getUserProperties().put(IP, ipAddress);
            } catch (Exception e) {
                config.getUserProperties().put(IP, "未知ip");
            }
        }
    }

    /**
     * 建立成功连接调用
     *
     * @param session        当前会话
     * @param endpointConfig 配置信息
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
        // 当前session加入连接
        String ipAddress = endpointConfig.getUserProperties().get(ChatConfigurator.IP).toString();
        WS_CONNECTIONS.put(ipAddress, session);
        // 更新在线人数
        ONLINE_NUM.incrementAndGet();
        updateOnlineCount();
        // 加载历史消息
        ChatRecordDTO chatRecordDTO = getChatRecordList(ipAddress);
        // 发送消息
        WebsocketMessageDTO websocketMessageDTO = WebsocketMessageDTO.builder()
                .type(ChatTypeEnum.HISTORY_RECORD.getType())
                .data(chatRecordDTO)
                .build();
        sendMessage(session, JSON.toJSONString(websocketMessageDTO));
    }

    /**
     * 接受到客户端发送的消息后调用
     *
     * @param session 当前会话
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        WebsocketMessageDTO messageDTO = JSON.parseObject(message, WebsocketMessageDTO.class);
        switch (Objects.requireNonNull(ChatTypeEnum.getChatType(messageDTO.getType()))) {
            case SEND_MESSAGE:
                // 发送消息
                ChatRecord chatRecord = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), ChatRecord.class);
                // 过滤html标签
                chatRecord.setContent(HTMLUtils.filter(chatRecord.getContent()));
                chatRecordMapper.insert(chatRecord);
                messageDTO.setData(chatRecord);
                // 广播消息
                broadcastMessage(JSON.toJSONString(messageDTO));
                break;
            case RECALL_MESSAGE:
                Integer id = JSON.parseObject(JSON.toJSONString(messageDTO.getData()), Integer.class);
                // 撤回消息
                chatRecordMapper.deleteById(id);
                // 广播消息
                broadcastMessage(JSON.toJSONString(messageDTO));
                break;
            case HEART_BEAT:
                // 心跳消息
                messageDTO.setData("pong");
                sendMessage(session, JSON.toJSONString(messageDTO));
            default:
                break;
        }
    }

    /**
     * 连接关闭时调用
     *
     * @param session 当前会话
     */
    @OnClose
    public void onClose(Session session) {
        // 移除会话
        String ipAddress = session.getUserProperties().get(ChatConfigurator.IP).toString();
        WS_CONNECTIONS.remove(ipAddress);
        // 更新在线人数
        ONLINE_NUM.decrementAndGet();
        updateOnlineCount();
    }

    /**
     * 保证只有一个线程可以操作session发送消息
     *
     * @param session 会话
     * @param message 消息
     * @throws IOException io异常
     */
    private void sendMessage(Session session, String message) throws IOException {
        if (session != null && session.isOpen()) {
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 群发消息
     *
     * @param message 消息
     */
    private void broadcastMessage(String message) {
        for (Session session : WS_CONNECTIONS.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

    /**
     * 更新在线人数
     */
    private void updateOnlineCount() {
        // 获取当前在线人数
        WebsocketMessageDTO messageDTO = WebsocketMessageDTO.builder()
                .type(ChatTypeEnum.ONLINE_COUNT.getType())
                .data(ONLINE_NUM)
                .build();
        // 广播消息
        broadcastMessage(JSON.toJSONString(messageDTO));
    }

    /**
     * 加载历史聊天记录
     *
     * @param ipAddress ip地址
     * @return 历史聊天记录
     */
    private ChatRecordDTO getChatRecordList(String ipAddress) {
        // 获取聊天历史记录
        List<ChatRecord> chatRecordList = chatRecordMapper.selectList(new LambdaQueryWrapper<ChatRecord>()
                .ge(ChatRecord::getCreateTime, DateUtil.offsetDay(new Date(), -1)));
        return ChatRecordDTO.builder()
                .chatRecordList(chatRecordList)
                .ipAddress(ipAddress)
                .ipSource(IpUtils.getIpSource(ipAddress))
                .build();
    }
}