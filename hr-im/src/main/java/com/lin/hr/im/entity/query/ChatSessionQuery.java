package com.lin.hr.im.entity.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 会话信息参数
 */
@Setter
@Getter
public class ChatSessionQuery extends BaseParam {
    /**
     * 会话ID
     */
    private String sessionId;

    private String sessionIdFuzzy;

    /**
     * 最后接受的消息
     */
    private String lastMessage;

    private String lastMessageFuzzy;

    /**
     * 最后接受消息的时间戳
     */
    private Long lastReceiveTime;


}
