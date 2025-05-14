package com.lin.hr.im.entity.po;

import com.lin.hr.common.enums.user.UserContactTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;


/**
 * 会话用户
 */
@Data
public class ChatSessionUser implements Serializable {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 联系人ID
     */
    private String contactId;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 最后接受的消息
     */
    private String lastMessage;

    /**
     * 最后接受消息的时间戳
     */
    private Long lastReceiveTime;

    /**
     * 群组人数
     */
    private Integer memberCount;

    /**
     * 联系人信息
     */
    private Integer contactType;

    public Integer getContactType() {
        return Objects.requireNonNull(UserContactTypeEnum.getByPrefix(contactId)).getType();
    }

    @Override
    public String toString() {
        return "用户ID:" + (userId == null ? "空" : userId) + "，联系人ID:" + (contactId == null ? "空" : contactId) + "，会话ID:" + (sessionId == null ? "空" : sessionId) + "，联系人名称:" + (contactName == null ? "空" : contactName);
    }
}
