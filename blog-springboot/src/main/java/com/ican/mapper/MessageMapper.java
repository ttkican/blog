package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.Message;
import com.ican.model.dto.ConditionDTO;
import com.ican.model.vo.MessageBackVO;
import com.ican.model.vo.MessageVO;
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
    List<MessageVO> selectMessageVOList();

    /**
     * 查询后台留言列表
     *
     * @param limit     页码
     * @param size      大小
     * @param condition 条件
     * @return 后台留言列表
     */
    List<MessageBackVO> selectMessageBackVOList(@Param("limit") Long limit, @Param("size") Long size, @Param("condition") ConditionDTO condition);
}




