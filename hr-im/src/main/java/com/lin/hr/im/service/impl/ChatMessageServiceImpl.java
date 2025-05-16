package com.lin.hr.im.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson2.util.DateUtils;
import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.config.AppConfig;
import com.lin.hr.common.constants.AccountConstant;
import com.lin.hr.common.constants.FileConstant;
import com.lin.hr.common.dto.SysSettingDto;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.controller.AccountController;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.enums.MessageStatusEnum;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.po.ChatSession;
import com.lin.hr.im.entity.po.UserContact;
import com.lin.hr.im.entity.query.ChatSessionQuery;
import com.lin.hr.im.entity.query.UserContactQuery;
import com.lin.hr.im.mappers.ChatSessionMapper;
import com.lin.hr.im.mappers.UserContactMapper;
import com.lin.hr.im.websocket.utils.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lin.hr.im.entity.query.ChatMessageQuery;
import com.lin.hr.im.entity.po.ChatMessage;

import com.lin.hr.im.entity.query.SimplePage;
import com.lin.hr.im.mappers.ChatMessageMapper;
import com.lin.hr.im.service.ChatMessageService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


/**
 * 聊天消息表 业务接口实现
 */
@Slf4j
@Service("chatMessageService")
public class ChatMessageServiceImpl implements ChatMessageService {
    @Resource
    private ChatMessageMapper<ChatMessage, ChatMessageQuery> chatMessageMapper;
    @Resource
    private ChatSessionMapper<ChatSession, ChatSessionQuery> chatSessionMapper;
    @Resource
    private MessageHandler messageHandler;
    @Resource
    private RedisComponent redisComponent;
    @Resource
    private AppConfig appConfig;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<ChatMessage> findListByParam(ChatMessageQuery param) {
        return this.chatMessageMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(ChatMessageQuery param) {
        return this.chatMessageMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<ChatMessage> findListByPage(ChatMessageQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ChatMessage> list = this.findListByParam(param);
        PaginationResultVO<ChatMessage> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(ChatMessage bean) {
        return this.chatMessageMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<ChatMessage> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatMessageMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<ChatMessage> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatMessageMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(ChatMessage bean, ChatMessageQuery param) {
        StringTools.checkParam(param);
        return this.chatMessageMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(ChatMessageQuery param) {
        StringTools.checkParam(param);
        return this.chatMessageMapper.deleteByParam(param);
    }

    /**
     * 根据MessageId获取对象
     */
    @Override
    public ChatMessage getChatMessageByMessageId(Long messageId) {
        return this.chatMessageMapper.selectByMessageId(messageId);
    }

    /**
     * 根据MessageId修改
     */
    @Override
    public Integer updateChatMessageByMessageId(ChatMessage bean, Long messageId) {
        return this.chatMessageMapper.updateByMessageId(bean, messageId);
    }

    /**
     * 根据MessageId删除
     */
    @Override
    public Integer deleteChatMessageByMessageId(Long messageId) {
        return this.chatMessageMapper.deleteByMessageId(messageId);
    }

    @Override
    public MessageSendDto<Object> saveMessage(ChatMessage chatMessage, TokenUserInfoDto tokenUserInfoDto) {
        // 1. 判断好友状态（过滤机器人）
        if (!AccountConstant.ROBOT_UID.equals(tokenUserInfoDto.getUserId())) {
            List<String> userContactIds = redisComponent.getUserContactIds(tokenUserInfoDto.getUserId());
            if (!userContactIds.contains(chatMessage.getContactId())) {
                UserContactTypeEnum userContactTypeEnum = UserContactTypeEnum.getByPrefix(chatMessage.getContactId());
                if (UserContactTypeEnum.USER == userContactTypeEnum) {
                    throw new BusinessException(ResponseCodeEnum.CODE_902);
                } else {
                    throw new BusinessException(ResponseCodeEnum.CODE_903);
                }
            }
        }

        String sessionId = null;
        String sendUserId = tokenUserInfoDto.getUserId();
        String contactId = chatMessage.getContactId();
        // 2. 记录sessionId
        UserContactTypeEnum userContactType = UserContactTypeEnum.getByPrefix(contactId);
        if (UserContactTypeEnum.USER == userContactType) {
            sessionId = StringTools.getChatSession4User(new String[]{sendUserId, contactId});
        } else {
            sessionId = StringTools.getChatSession4Group(contactId);
        }
        chatMessage.setSessionId(sessionId);

        // 3. 记录当前时间
        Long curTime = System.currentTimeMillis();
        chatMessage.setSendTime(curTime);

        // 4. 判断消息类型，记录消息发送状态
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByType(chatMessage.getMessageType());
        if (null == messageTypeEnum || !ArrayUtils.contains(new Integer[]{MessageTypeEnum.CHAT.getType(), MessageTypeEnum.MEDIA_CHAT.getType()}, chatMessage.getMessageType())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        Integer sendStatus = MessageTypeEnum.MEDIA_CHAT == messageTypeEnum ? MessageStatusEnum.SENDING.getStatus() : MessageStatusEnum.SENDED.getStatus();
        chatMessage.setStatus(sendStatus);

        // 5. 记录消息内容
        String messageContent = StringTools.cleanHtmlTag(chatMessage.getMessageContent());
        chatMessage.setMessageContent(messageContent);

        // 6. 更新会话信息
        ChatSession chatSession = new ChatSession();
        chatSession.setLastMessage(messageContent);
        if (UserContactTypeEnum.GROUP == userContactType) {
            chatSession.setLastMessage(tokenUserInfoDto.getUserName() + ":" + messageContent);
        }
        chatSession.setLastReceiveTime(curTime);
        chatSessionMapper.updateBySessionId(chatSession, sessionId);

        // 7. 补充消息信息
        chatMessage.setSendUserId(sendUserId);
        chatMessage.setSendUserNickName(tokenUserInfoDto.getUserName());
        chatMessage.setContactType(userContactType.getType());
        chatMessageMapper.insert(chatMessage);

        // 8.发送消息
        MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
        BeanUtils.copyProperties(chatMessage, messageSendDto);

        // 8.1. 判断是否是AI问答
        if (AccountConstant.ROBOT_UID.equals(contactId)) {
            SysSettingDto sysSetting = redisComponent.getSysSetting();
            TokenUserInfoDto robot = new TokenUserInfoDto();
            robot.setUserId(sysSetting.getRobotUId());
            robot.setUserName(sysSetting.getRobotNickName());
            ChatMessage robotChatMessage = new ChatMessage();
            robotChatMessage.setContactId(sendUserId);
            // TODO 对接deepSeek
            robotChatMessage.setMessageContent("我只是一个机器人无法识别你的消息");
            robotChatMessage.setMessageType(MessageTypeEnum.CHAT.getType());
            saveMessage(robotChatMessage, robot);
        } else {
            messageHandler.sendMessage(messageSendDto);
        }
        return messageSendDto;
    }

    @Override
    public void saveMessageFile(String userId, Long messageId, MultipartFile file, MultipartFile cover) {
        ChatMessage chatMessage = chatMessageMapper.selectByMessageId(messageId);
        // 1. 校验入参
        if (null == chatMessage) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (!userId.equals(chatMessage.getSendUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SysSettingDto sysSetting = redisComponent.getSysSetting();
        String fileSuffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        // 校验文件
        if (!StringTools.isEmpty(fileSuffix)
                && ArrayUtils.contains(FileConstant.IMAGE_SUFFIX_LIST, fileSuffix.toUpperCase())
                && file.getSize() > sysSetting.getMaxFileSize() * FileConstant.FILE_SIZE_MB) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        } else if (!StringTools.isEmpty(fileSuffix)
                && ArrayUtils.contains(FileConstant.VIDEO_SUFFIX_LIST, fileSuffix.toUpperCase())
                && file.getSize() > sysSetting.getMaxVideoSize() * FileConstant.FILE_SIZE_MB) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        } else if (!StringTools.isEmpty(fileSuffix)
                && !ArrayUtils.contains(FileConstant.IMAGE_SUFFIX_LIST, fileSuffix.toUpperCase())
                && !ArrayUtils.contains(FileConstant.VIDEO_SUFFIX_LIST, fileSuffix.toUpperCase())
                && file.getSize() > sysSetting.getMaxVideoSize() * FileConstant.FILE_SIZE_MB) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        // 2. 上传文件
        String fileName = file.getOriginalFilename();
        String fileExtName = StringTools.getFileSuffix(fileName);
        String fileRealName = messageId + fileExtName;
        String month = DateUtils.format(new Date(chatMessage.getSendTime()), DateTimePatternEnum.YYYYMM.getPattern());
        File folder = new File(appConfig.getProjectFolder() + FileConstant.FILE_FOLDER_FILE + month);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File uploadFile = new File(folder.getPath() + "/" + fileRealName);
        try {
            file.transferTo(uploadFile);
            cover.transferTo(new File(uploadFile.getPath() + FileConstant.COVER_IMAGE_SUFFIX));
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new BusinessException("上传文件失败");
        }

        // 3. 发送上传完成消息
        ChatMessage uploadInfo = new ChatMessage();
        uploadInfo.setStatus(MessageStatusEnum.SENDED.getStatus());
        ChatMessageQuery chatMessageQuery = new ChatMessageQuery();
        chatMessageQuery.setMessageId(messageId);
        chatMessageMapper.updateByParam(uploadInfo, chatMessageQuery);

        MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
        messageSendDto.setMessageId(messageId);
        messageSendDto.setStatus(MessageStatusEnum.SENDED.getStatus());
        messageSendDto.setMessageType(MessageTypeEnum.FILE_UPLOAD.getType());
        messageSendDto.setContactId(chatMessage.getContactId());
        messageHandler.sendMessage(messageSendDto);
    }

    @Override
    public File downloadFile(TokenUserInfoDto tokenUserInfoDto, Long messageId, Boolean showCover) {
        ChatMessage chatMessage = chatMessageMapper.selectByMessageId(messageId);
        String contactId = chatMessage.getContactId();
        UserContactTypeEnum userContactType = UserContactTypeEnum.getByPrefix(contactId);
        // 1. 校验该文件是否允许下载
        if (UserContactTypeEnum.USER == userContactType && !tokenUserInfoDto.getUserId().equals(chatMessage.getContactId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (UserContactTypeEnum.GROUP == userContactType) {
            UserContactQuery userContactQuery = new UserContactQuery();
            userContactQuery.setUserId(tokenUserInfoDto.getUserId());
            userContactQuery.setContactType(UserContactTypeEnum.GROUP.getType());
            userContactQuery.setContactId(contactId);
            userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            Integer contactCount = userContactMapper.selectCount(userContactQuery);
            if (contactCount > 0) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
        }

        String month = DateUtils.format(new Date(chatMessage.getSendTime()), DateTimePatternEnum.YYYYMM.getPattern());
        File folder = new File(appConfig.getProjectFolder() + FileConstant.FILE_FOLDER_FILE + month);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String fileName = chatMessage.getFileName();
        String fileExtName = StringTools.getFileSuffix(fileName);
        String fileRealName = messageId + fileExtName;
        if (showCover !=null && showCover) {
            fileRealName = fileRealName + FileConstant.COVER_IMAGE_SUFFIX;
        }
        File file = new File(folder.getPath() + "/" + fileRealName);
        if (!file.exists()) {
            log.info("文件不存在 -> {}", messageId);
            throw new BusinessException(ResponseCodeEnum.CODE_602);
        }
        return file;
    }
}