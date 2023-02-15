package com.ican.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ican.entity.Message;
import com.ican.model.dto.CheckDTO;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.dto.MessageDTO;
import com.ican.model.vo.MessageBackVO;
import com.ican.model.vo.MessageVO;
import com.ican.model.vo.PageResult;

import java.util.List;

/**
 * 留言业务接口
 *
 * @author ican
 */
public interface MessageService extends IService<Message> {

    /**
     * 查看留言列表
     *
     * @return 留言列表
     */
    List<MessageVO> listMessageVO();

    /**
     * 查看后台留言列表
     *
     * @param condition 条件
     * @return 后台留言列表
     */
    PageResult<MessageBackVO> listMessageBackVO(ConditionDTO condition);

    /**
     * 添加留言
     *
     * @param message 留言
     */
    void addMessage(MessageDTO message);

    /**
     * 审核留言
     *
     * @param check 审核信息
     */
    void updateMessageCheck(CheckDTO check);
}
