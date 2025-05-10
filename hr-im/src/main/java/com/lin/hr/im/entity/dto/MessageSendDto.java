package com.lin.hr.im.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/17 21:33
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageSendDto<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 消息id
     */
    private Long messageId;

    /**
     * 会话id
     */
    private String sessionId;

    /**
     * 发送人
     */
    private String sendUserId;

    /**
     * 发送人名称
     */
    private String sendNickName;

    /**
     * 联系人id
     */
    private String contactId;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 消息内容
     */
    private String messageContent;

    /**
     * 最后的消息
     */
    private String lastMessage;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 发送时间
     */
    private Long sendTime;

    /**
     * 联系人类型
     */
    private Integer contactType;

    /**
     * 扩展信息
     */
    private T extendData;

    /**
     * 消息状态 0-发送中 1-已发送 对于文件是异步上传状态处理
     */
    private Integer status;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private Integer fileType;

    /**
     * 群员人数
     */
    private Integer memberCount;

    public String getLastMessage() {
        if (StringUtils.isEmpty(lastMessage)) {
            return messageContent;
        }
        return lastMessage;
    }
}
