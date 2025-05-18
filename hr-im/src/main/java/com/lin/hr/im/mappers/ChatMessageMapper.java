package com.lin.hr.im.mappers;

import com.lin.hr.im.entity.po.ChatMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天消息表 数据库操作接口
 */
public interface ChatMessageMapper<T, P> extends BaseMapper<T, P> {

    /**
     * 根据MessageId更新
     */
    Integer updateByMessageId(@Param("bean") T t, @Param("messageId") Long messageId);


    /**
     * 根据MessageId删除
     */
    Integer deleteByMessageId(@Param("messageId") Long messageId);


    /**
     * 根据MessageId获取对象
     */
    T selectByMessageId(@Param("messageId") Long messageId);

    /**
     * 根据sessionId获取聊天记录
     */
    List<ChatMessage> selectListBySessionId(@Param("sessionId") String sessionId, @Param("count") Integer count);
}
