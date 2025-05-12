package com.lin.hr.im.entity.query;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 聊天消息表参数
 */
@Setter
@Getter
public class ChatMessageQuery extends BaseParam {
    /**
     * 消息唯一ID
     */
    private Long messageId;

    /**
     * 会话ID
     */
    private String sessionId;

    private String sessionIdFuzzy;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 消息内容
     */
    private String messageContent;

    private String messageContentFuzzy;

    /**
     * 发送人ID
     */
    private String sendUserId;

    private String sendUserIdFuzzy;

    /**
     * 发送人昵称
     */
    private String sendUserNickName;

    private String sendUserNickNameFuzzy;

    /**
     * 发送时间
     */
    private Long sendTime;

    /**
     * 接收联系人ID
     */
    private String contactId;

    private String contactIdFuzzy;

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

    private String fileNameFuzzy;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 状态 0:正在发送 1:已发送
     */
    private Integer status;

    /**
     * 联系人id集合
     */
    private List<String> contactIdList;

    /**
     * 最后接收消息时间
     */
    private Long lastReceiveTime;
}
