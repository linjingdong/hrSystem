package com.lin.hr.im.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.dto.SysSettingDto;
import com.lin.hr.common.enums.PageSize;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.im.entity.po.UserContact;
import com.lin.hr.im.entity.query.UserContactQuery;
import com.lin.hr.im.enums.apply.UserContactApplyStatusEnum;
import com.lin.hr.im.mappers.UserContactMapper;
import org.springframework.stereotype.Service;


import com.lin.hr.im.entity.query.UserContactApplyQuery;
import com.lin.hr.im.entity.po.UserContactApply;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.query.SimplePage;
import com.lin.hr.im.mappers.UserContactApplyMapper;
import com.lin.hr.im.service.UserContactApplyService;
import org.springframework.transaction.annotation.Transactional;


/**
 * 联系人申请 业务接口实现
 */
@Service("userContactApplyService")
public class UserContactApplyServiceImpl implements UserContactApplyService {

    @Resource
    private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;
    @Resource
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Resource
    private RedisComponent redisComponent;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserContactApply> findListByParam(UserContactApplyQuery param) {
        return this.userContactApplyMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserContactApplyQuery param) {
        return this.userContactApplyMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserContactApply> list = this.findListByParam(param);
        PaginationResultVO<UserContactApply> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserContactApply bean) {
        return this.userContactApplyMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserContactApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactApplyMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserContactApply> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userContactApplyMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(UserContactApply bean, UserContactApplyQuery param) {
        StringTools.checkParam(param);
        return this.userContactApplyMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(UserContactApplyQuery param) {
        StringTools.checkParam(param);
        return this.userContactApplyMapper.deleteByParam(param);
    }

    /**
     * 根据ApplyId获取对象
     */
    @Override
    public UserContactApply getUserContactApplyByApplyId(Integer applyId) {
        return this.userContactApplyMapper.selectByApplyId(applyId);
    }

    /**
     * 根据ApplyId修改
     */
    @Override
    public Integer updateUserContactApplyByApplyId(UserContactApply bean, Integer applyId) {
        return this.userContactApplyMapper.updateByApplyId(bean, applyId);
    }

    /**
     * 根据ApplyId删除
     */
    @Override
    public Integer deleteUserContactApplyByApplyId(Integer applyId) {
        return this.userContactApplyMapper.deleteByApplyId(applyId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserIdAndContactId获取对象
     */
    @Override
    public UserContactApply getUserContactApplyByApplyUserIdAndReceiveUserIdAndContactId(String applyUserId, String receiveUserId, String contactId) {
        return this.userContactApplyMapper.selectByApplyUserIdAndReceiveUserIdAndContactId(applyUserId, receiveUserId, contactId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserIdAndContactId修改
     */
    @Override
    public Integer updateUserContactApplyByApplyUserIdAndReceiveUserIdAndContactId(UserContactApply bean, String applyUserId, String receiveUserId, String contactId) {
        return this.userContactApplyMapper.updateByApplyUserIdAndReceiveUserIdAndContactId(bean, applyUserId, receiveUserId, contactId);
    }

    /**
     * 根据ApplyUserIdAndReceiveUserIdAndContactId删除
     */
    @Override
    public Integer deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndContactId(String applyUserId, String receiveUserId, String contactId) {
        return this.userContactApplyMapper.deleteByApplyUserIdAndReceiveUserIdAndContactId(applyUserId, receiveUserId, contactId);
    }

    @Override
    public PaginationResultVO<UserContactApply> loadApply(String userId, Integer pageNo) {
        UserContactApplyQuery applyQuery = new UserContactApplyQuery();
        applyQuery.setPageNo(pageNo);
        applyQuery.setPageSize(PageSize.SIZE15.getSize());
        applyQuery.setReceiveUserId(userId);
        applyQuery.setOrderBy("last_apply_time desc");
        applyQuery.setQueryContactInfo(true);
        return this.findListByPage(applyQuery);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dealWithApply(String userId, Integer applyId, Integer status) {
        UserContactApplyStatusEnum statusEnum = UserContactApplyStatusEnum.getByStatus(status);
        if (statusEnum == null || UserContactApplyStatusEnum.INIT == statusEnum) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        // 防止非当前用户的申请处理
        UserContactApply applyInfo = userContactApplyMapper.selectByApplyId(applyId);
        if (null == applyInfo || !userId.equals(applyInfo.getReceiveUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        UserContactApply userContactApply = new UserContactApply();
        userContactApply.setStatus(statusEnum.getStatus());
        userContactApply.setLastApplyTime(System.currentTimeMillis());

        UserContactApplyQuery userContactApplyQuery = new UserContactApplyQuery();
        userContactApplyQuery.setApplyId(applyId);
        userContactApplyQuery.setStatus(UserContactApplyStatusEnum.INIT.getStatus()); // 只有初始状态才能修改，防重操作
        Integer count = userContactApplyMapper.updateByParam(userContactApply, userContactApplyQuery);
        // 防重操作
        if (0 == count) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        if (UserContactApplyStatusEnum.PASS == statusEnum) {
            addContact(applyInfo.getApplyUserId(), applyInfo.getReceiveUserId(), applyInfo.getContactId(), applyInfo.getContactType(), applyInfo.getApplyInfo());
            return;
        }

        if (UserContactApplyStatusEnum.BLACKLIST == statusEnum) {
            Date curTime = new Date();
            UserContact userContact = new UserContact();
            userContact.setUserId(applyInfo.getApplyUserId());
            userContact.setContactId(applyInfo.getContactId());
            userContact.setContactType(applyInfo.getContactType());
            userContact.setCreateTime(curTime);
            userContact.setStatus(UserContactStatusEnum.BLACKLIST_BE_FIRST.getStatus());
            userContact.setLastUpdateTime(curTime);
            userContactMapper.insertOrUpdate(userContact);
        }
    }

    @Override
    public void addContact(String applyUserId, String receiveUserId, String contactId, Integer contactType, String applyInfo) {
        // 判断群聊人数与系统群聊人数
        if (UserContactTypeEnum.GROUP.getType().equals(contactType)) {
            UserContactQuery userContactQuery = new UserContactQuery();
            userContactQuery.setContactId(contactId);
            userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            Integer count = userContactMapper.selectCount(userContactQuery);
            SysSettingDto sysSetting = redisComponent.getSysSetting();
            if (sysSetting.getMaxGroupCount() > count) {
                throw new BusinessException("成员已满，无法加入");
            }
        }
        Date curDate = new Date();
        // 同意，双方添加好友
        List<UserContact> userContactList = new ArrayList<>();
        // 申请人添加对方
        UserContact applyUserContact = new UserContact();
        applyUserContact.setUserId(applyUserId);
        applyUserContact.setContactId(contactId);
        applyUserContact.setContactType(contactType);
        applyUserContact.setCreateTime(curDate);
        applyUserContact.setLastUpdateTime(curDate);
        applyUserContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        userContactList.add(applyUserContact);
        // 如果是申请好友，接收人添加申请人，联系类型为群组的话就不用添加好友
        if (UserContactTypeEnum.USER.getType().equals(contactType)) {
            UserContact userContact = new UserContact();
            userContact.setUserId(receiveUserId);
            userContact.setContactId(applyUserId);
            userContact.setContactType(contactType);
            userContact.setCreateTime(curDate);
            userContact.setLastUpdateTime(curDate);
            userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
            userContactList.add(userContact);
        }
        // 批量插入
        userContactMapper.insertBatch(userContactList);
        // TODO 如果是好友，接受人也添加申请人为好友，添加缓存
        // TODO 创建会话
    }
}