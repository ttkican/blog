package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Message;
import com.ican.model.vo.query.MessageQuery;
import com.ican.model.vo.response.MessageBackResp;
import com.ican.model.vo.response.MessageResp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 留言 Mapper
 *
 * @author ican
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    /**
     * 查询留言列表
     *
     * @return 留言列表
     */
    List<MessageResp> selectMessageVOList();

    /**
     * 查询后台留言列表
     *
     * @param messageQuery 留言查询条件
     * @return 后台留言列表
     */
    List<MessageBackResp> selectBackMessageList(@Param("param") MessageQuery messageQuery);
}




