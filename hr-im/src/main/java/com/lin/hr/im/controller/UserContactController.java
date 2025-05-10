package com.lin.hr.im.controller;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.enums.user.UserContactTypeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.im.service.UserContactApplyService;
import com.lin.hr.im.service.UserContactService;
import com.lin.hr.im.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/7 19:38
 **/
@RestController
@RequestMapping("/contact")
public class UserContactController extends ABaseController {
    @Autowired
    private UserContactService userContactService;
    @Autowired
    private UserContactApplyService userContactApplyService;

    /**
     * 搜索好友或群
     */
    @PostMapping("/search")
    @GlobalInterceptor
    public ResponseVO<Object> search(@NotBlank String contactId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        return getSuccessResponseVO(userContactService.searchContact(tokenUserInfo.getUserId(), contactId));
    }

    /**
     * 申请好友
     */
    @PostMapping("/applyAdd")
    @GlobalInterceptor
    public ResponseVO<Object> applyAdd(@NotBlank String contactId, String applyInfo) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        return getSuccessResponseVO(userContactApplyService.applyAdd(tokenUserInfo, contactId, applyInfo));
    }

    /**
     * 获取申请列表
     */
    @PostMapping("/loadApply")
    @GlobalInterceptor
    public ResponseVO<Object> loadApply(Integer pageNo) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        return getSuccessResponseVO(userContactApplyService.loadApply(tokenUserInfo.getUserId(), pageNo));
    }

    /**
     * 处理当前用户申请
     */
    @PostMapping("/dealWithApply")
    @GlobalInterceptor
    public ResponseVO<Object> dealWithApply(@NotBlank Integer applyId, @NotBlank Integer status) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        userContactApplyService.dealWithApply(tokenUserInfo.getUserId(), applyId, status);
        return getSuccessResponseVO(null);
    }

    /**
     * 获取联系人列表
     */
    @PostMapping("/loadContact")
    @GlobalInterceptor
    public ResponseVO<Object> loadContact(@NotNull String contactType) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        return getSuccessResponseVO(userContactService.loadContact(tokenUserInfo.getUserId(), contactType));
    }

    /**
     * 获取指定联系人信息
     */
    @PostMapping("/getContactInfo")
    @GlobalInterceptor
    public ResponseVO<Object> getContactInfo(@NotNull String contactId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        return getSuccessResponseVO(userContactService.getContactInfo(tokenUserInfo.getUserId(), contactId));
    }

    /**
     * 获取指定联系人用户信息
     */
    @PostMapping("/getContactUserInfo")
    @GlobalInterceptor
    public ResponseVO<Object> getContactUserInfo(@NotBlank String contactId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        return getSuccessResponseVO(userContactService.getUserContactInfo(tokenUserInfo.getUserId(), contactId));
    }

    /**
     * 删除联系人
     */
    @PostMapping("delContact")
    @GlobalInterceptor
    public ResponseVO<Object> delContact(@NotBlank String contactId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        userContactService.removeUserContact(tokenUserInfo.getUserId(), contactId, UserContactStatusEnum.DEL);
        return getSuccessResponseVO("删除联系人成功");
    }

    /**
     * 拉黑联系人
     */
    @PostMapping("addContact2BlackList")
    @GlobalInterceptor
    public ResponseVO<Object> addContact2BlackList(@NotBlank String contactId) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        userContactService.removeUserContact(tokenUserInfo.getUserId(), contactId, UserContactStatusEnum.BLACKLIST);
        return getSuccessResponseVO("拉黑联系人成功");
    }
}
