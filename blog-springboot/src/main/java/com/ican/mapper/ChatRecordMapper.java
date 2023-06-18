package com.ican.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ican.entity.ChatRecord;
import org.springframework.stereotype.Repository;

/**
 * 聊天记录 Mapper
 *
 * @author ican
 * @date 2023/06/12 10:55
 **/
@Repository
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {
}