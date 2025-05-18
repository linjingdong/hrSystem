package com.lin.hr.im.service.impl;

import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;

import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.dto.MessageSendDto;
import com.lin.hr.im.entity.enums.MessageTypeEnum;
import com.lin.hr.im.entity.po.UserContact;
import com.lin.hr.im.entity.query.UserContactQuery;
import com.lin.hr.im.mappers.UserContactMapper;
import com.lin.hr.im.service.UserContactService;
import com.lin.hr.im.websocket.utils.MessageHandler;
import org.springframework.stereotype.Service;

import com.lin.hr.im.entity.query.ChatSessionUserQuery;
import com.lin.hr.im.entity.po.ChatSessionUser;
import com.lin.hr.im.entity.query.SimplePage;
import com.lin.hr.im.mappers.ChatSessionUserMapper;
import com.lin.hr.im.service.ChatSessionUserService;

/**
 * 会话用户 业务接口实现
 */
@Service("chatSessionUserService")
public class ChatSessionUserServiceImpl implements ChatSessionUserService {
    @Resource
    private ChatSessionUserMapper<ChatSessionUser, ChatSessionUserQuery> chatSessionUserMapper;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private MessageHandler messageHandler;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<ChatSessionUser> findListByParam(ChatSessionUserQuery param) {
        return this.chatSessionUserMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(ChatSessionUserQuery param) {
        return this.chatSessionUserMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<ChatSessionUser> findListByPage(ChatSessionUserQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ChatSessionUser> list = this.findListByParam(param);
        PaginationResultVO<ChatSessionUser> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(ChatSessionUser bean) {
        return this.chatSessionUserMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<ChatSessionUser> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatSessionUserMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<ChatSessionUser> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.chatSessionUserMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(ChatSessionUser bean, ChatSessionUserQuery param) {
        StringTools.checkParam(param);
        return this.chatSessionUserMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(ChatSessionUserQuery param) {
        StringTools.checkParam(param);
        return this.chatSessionUserMapper.deleteByParam(param);
    }

    /**
     * 根据UserIdAndContactId获取对象
     */
    @Override
    public ChatSessionUser getChatSessionUserByUserIdAndContactId(String userId, String contactId) {
        return this.chatSessionUserMapper.selectByUserIdAndContactId(userId, contactId);
    }

    /**
     * 根据UserIdAndContactId修改
     */
    @Override
    public Integer updateChatSessionUserByUserIdAndContactId(ChatSessionUser bean, String userId, String contactId) {
        return this.chatSessionUserMapper.updateByUserIdAndContactId(bean, userId, contactId);
    }

    /**
     * 根据UserIdAndContactId删除
     */
    @Override
    public Integer deleteChatSessionUserByUserIdAndContactId(String userId, String contactId) {
        return this.chatSessionUserMapper.deleteByUserIdAndContactId(userId, contactId);
    }

    @Override
    public void updateRedundancyInfo(String contactName, String contactId) {
        ChatSessionUser updatechatSessionUser = new ChatSessionUser();
        updatechatSessionUser.setContactName(contactName);
        ChatSessionUserQuery chatSessionUserQuery = new ChatSessionUserQuery();
        chatSessionUserQuery.setContactId(contactId);
        chatSessionUserMapper.updateByParam(updatechatSessionUser, chatSessionUserQuery);
        // 发送昵称修改消息
        UserContactTypeEnum userContactType = UserContactTypeEnum.getByPrefix(contactId);
        if (userContactType == UserContactTypeEnum.GROUP) {
            sendUpdateMessage(contactName, contactId);
        } else {
            UserContactQuery userContactQuery = new UserContactQuery();
            userContactQuery.setContactType(UserContactTypeEnum.USER.getType());
            userContactQuery.setContactId(contactId);
            userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            List<UserContact> userContacts = userContactMapper.selectList(userContactQuery);
            for (UserContact userContact : userContacts) {
                sendUpdateMessage(contactName, userContact.getUserId()); // 发送给哪个和当前联系人为联系人的用户
            }
        }
    }

    private void sendUpdateMessage(String contactName, String contactId) {
        MessageSendDto<Object> messageSendDto = new MessageSendDto<>();
        messageSendDto.setContactId(contactId);
        messageSendDto.setContactType(Objects.requireNonNull(UserContactTypeEnum.getByPrefix(contactId)).getType());
        messageSendDto.setExtendData(contactName);
        messageSendDto.setMessageType(MessageTypeEnum.CONTACT_NAME_UPDATE.getType());
        messageSendDto.setSendUserId(contactId);
        messageSendDto.setSendUserNickName(contactName);
        messageHandler.sendMessage(messageSendDto);
    }
}