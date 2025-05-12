package com.lin.hr.im.entity.po;

import lombok.Data;

import java.io.Serializable;


/**
 * 聊天消息表
 */
@Data
public class ChatMessage implements Serializable {
    /**
     * 消息唯一ID
     */
    private Long messageId;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 发送人ID
     */
    private String sendUserId;

    /**
     * 发送人昵称
     */
    private String sendUserNickName;

    /**
     * 发送时间
     */
    private Long sendTime;

    /**
     * 接收联系人ID
     */
    private String contactId;

    /**
     * 接收人类型 0:用户 1:群聊
     */
    private Integer contactType;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 状态 0:正在发送 1:已发送
     */
    private Integer status;

    @Override
    public String toString() {
        return "消息唯一ID:" + (messageId == null ? "空" : messageId) + "，会话ID:" + (sessionId == null ? "空" : sessionId) + "，消息类型:" + (messageType == null ? "空" : messageType) + "，消息内容:" + (messageContent == null ? "空" : messageContent) + "，发送人ID:" + (sendUserId == null ? "空" : sendUserId) + "，发送人昵称:" + (sendUserNickName == null ? "空" : sendUserNickName) + "，发送时间:" + (sendTime == null ? "空" : sendTime) + "，接收联系人ID:" + (contactId == null ? "空" : contactId) + "，接收人类型 0:用户 1:群聊:" + (contactType == null ? "空" : contactType) + "，文件大小:" + (fileSize == null ? "空" : fileSize) + "，文件名:" + (fileName == null ? "空" : fileName) + "，文件类型:" + (fileType == null ? "空" : fileType) + "，状态 0:正在发送 1:已发送:" + (status == null ? "空" : status);
    }
}
