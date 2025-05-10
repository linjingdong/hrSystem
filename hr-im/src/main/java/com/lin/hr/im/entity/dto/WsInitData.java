package com.lin.hr.im.entity.dto;

import com.lin.hr.im.entity.po.ChatMessage;
import com.lin.hr.im.entity.po.ChatSession;
import com.lin.hr.im.entity.po.ChatSessionUser;
import lombok.Data;

import java.util.List;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/17 11:12
 **/
@Data
public class WsInitData {
    private List<ChatSessionUser> chatSessionList;
    private List<ChatMessage> chatMessageList;
    private Integer applyCount;
}
