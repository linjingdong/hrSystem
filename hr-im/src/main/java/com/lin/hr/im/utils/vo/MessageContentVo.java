package com.lin.hr.im.utils.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DeepSeek API 消息内容实体类
 * 
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/18 14:29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageContentVo {
    /**
     * 消息内容
     * 对于系统消息、用户消息和工具消息，必须提供内容
     * 对于助手消息，内容可以为null
     */
    private String content;
    
    /**
     * 消息的角色
     * 可选值: "system", "user", "assistant", "tool"
     */
    private String role;
    
    /**
     * 创建一个系统消息
     * @param content 系统消息内容
     * @return MessageContentVo 系统消息对象
     */
    public static MessageContentVo systemMessage(String content) {
        return new MessageContentVo(content, "system");
    }
    
    /**
     * 创建一个用户消息
     * @param content 用户消息内容
     * @return MessageContentVo 用户消息对象
     */
    public static MessageContentVo userMessage(String content) {
        return new MessageContentVo(content, "user");
    }
    
    /**
     * 创建一个助手消息
     * @param content 助手消息内容
     * @return MessageContentVo 助手消息对象
     */
    public static MessageContentVo assistantMessage(String content) {
        return new MessageContentVo(content, "assistant");
    }
}
