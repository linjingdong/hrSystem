package com.lin.hr.im.service;

import java.io.File;
import java.util.List;

import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.query.ChatMessageQuery;
import com.lin.hr.im.entity.po.ChatMessage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

/**
 * 聊天消息表 业务接口
 */
public interface ChatMessageService {

    /**
     * 根据条件查询列表
     */
    List<ChatMessage> findListByParam(ChatMessageQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(ChatMessageQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<ChatMessage> findListByPage(ChatMessageQuery param);

    /**
     * 新增
     */
    Integer add(ChatMessage bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<ChatMessage> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<ChatMessage> listBean);

    /**
     * 多条件更新
     */
    Integer updateByParam(ChatMessage bean, ChatMessageQuery param);

    /**
     * 多条件删除
     */
    Integer deleteByParam(ChatMessageQuery param);

    /**
     * 根据MessageId查询对象
     */
    ChatMessage getChatMessageByMessageId(Long messageId);


    /**
     * 根据MessageId修改
     */
    Integer updateChatMessageByMessageId(ChatMessage bean, Long messageId);


    /**
     * 根据MessageId删除
     */
    Integer deleteChatMessageByMessageId(Long messageId);

    /**
     * 记录消息
     *
     * @param chatMessage      消息
     * @param tokenUserInfoDto Token
     * @return 发送消息DTO
     */
    MessageSendDto<Object> saveMessage(ChatMessage chatMessage, TokenUserInfoDto tokenUserInfoDto);

    /**
     * 上传图片，并通知客户端上传成功
     *
     * @param userId    用户id
     * @param messageId 消息id
     * @param file      原文件
     * @param cover     缩略图
     */
    void saveMessageFile(String userId, Long messageId, MultipartFile file, MultipartFile cover);

    /**
     * 下载文件
     *
     * @param tokenUserInfoDto 用户Token信息
     * @param fileId           消息id
     * @param showCover        是否显示封面
     */
    File downloadFile(TokenUserInfoDto tokenUserInfoDto, Long fileId, Boolean showCover);

    /**
     * 获取会话聊天记录
     *
     * @param userId    用户id
     * @param sessionId 会话id
     * @return 聊天记录列表
     */
    List<ChatMessage> getMessageBySessionId(String userId, @NotEmpty String sessionId, Integer count);
}