package com.ican.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ican.entity.Message;
import com.ican.entity.SiteConfig;
import com.ican.mapper.MessageMapper;
import com.ican.model.dto.CheckDTO;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.MessageDTO;
import com.ican.model.vo.MessageBackVO;
import com.ican.model.vo.MessageVO;
import com.ican.model.vo.PageResult;
import com.ican.service.MessageService;
import com.ican.service.SiteConfigService;
import com.ican.utils.BeanCopyUtils;
import com.ican.utils.HTMLUtils;
import com.ican.utils.IpUtils;
import com.ican.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ican.constant.CommonConstant.FALSE;
import static com.ican.constant.CommonConstant.TRUE;

/**
 * 留言业务接口实现类
 *
 * @author ican
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SiteConfigService siteConfigService;

    @Override
    public List<MessageVO> listMessageVO() {
        // 查询留言列表
        return messageMapper.selectMessageVOList();
    }

    @Override
    public PageResult<MessageBackVO> listMessageBackVO(ConditionDTO condition) {
        // 查询留言数量
        Long count = messageMapper.selectCount(new LambdaQueryWrapper<Message>()
                .like(StringUtils.hasText(condition.getKeyword()), Message::getNickname, condition.getKeyword())
                .eq(Objects.nonNull(condition.getIsCheck()), Message::getIsCheck, condition.getIsCheck()));
        if (count == 0) {
            return new PageResult<>();
        }
        // 查询后台友链列表
        List<MessageBackVO> messageBackVOList = messageMapper.selectMessageBackVOList(PageUtils.getLimit(), PageUtils.getSize(), condition);
        return new PageResult<>(messageBackVOList, count);
    }

    @Override
    public void addMessage(MessageDTO message) {
        SiteConfig siteConfig = siteConfigService.getSiteConfig();
        Integer messageCheck = siteConfig.getMessageCheck();
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        Message newMessage = BeanCopyUtils.copyBean(message, Message.class);
        newMessage.setMessageContent(HTMLUtils.filter(message.getMessageContent()));
        newMessage.setIpAddress(ipAddress);
        newMessage.setIsCheck(messageCheck.equals(FALSE) ? TRUE : FALSE);
        newMessage.setIpSource(ipSource);
        messageMapper.insert(newMessage);
    }

    @Override
    public void updateMessageCheck(CheckDTO check) {
        // 修改留言审核状态
        List<Message> messageList = check.getIdList()
                .stream()
                .map(id -> Message.builder()
                        .id(id)
                        .isCheck(check.getIsCheck())
                        .build())
                .collect(Collectors.toList());
        this.updateBatchById(messageList);
    }
}




