package com.lin.hr.im.controller;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.StringTools;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.im.constant.RegexpConstant;
import com.lin.hr.im.entity.po.UserInfo;
import com.lin.hr.im.entity.vo.account.UserInfoVo;
import com.lin.hr.im.service.UserInfoService;
import com.lin.hr.im.websocket.utils.ChannelContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/12 11:24
 **/
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends ABaseController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ChannelContextUtils channelContextUtils;

    @PostMapping("/getUserInfo")
    @GlobalInterceptor
    public ResponseVO<Object> getUserInfo() {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        return getSuccessResponseVO(userInfoService.getUserInfo(tokenUserInfo));
    }

    @PostMapping("/saveUserInfo")
    @GlobalInterceptor
    public ResponseVO<Object> saveUserInfo(UserInfo userInfo, MultipartFile avatarFile, MultipartFile avatarCover) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        userInfo.setUserId(tokenUserInfo.getUserId());
        userInfo.setPassword(null);
        userInfo.setStatus(null);
        userInfo.setCreateTime(null);
        userInfo.setLastLoginTime(null);
        userInfoService.updateUserInfo(userInfo, avatarFile, avatarCover);
        return getUserInfo();
    }

    @PostMapping("/updatePassword")
    @GlobalInterceptor
    public ResponseVO<Object> updatePassword(@NotBlank @Pattern(regexp = RegexpConstant.REGEX_PASSWORD) String newPassword, String password) {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        UserInfo updateUserInfo = new UserInfo();
        UserInfo userInfo = userInfoService.getUserInfoByUserId(tokenUserInfo.getUserId());
        if (!StringTools.verifyMd5(password, userInfo.getPassword())) {
            throw new BusinessException("旧密码不正确！");
        }
        updateUserInfo.setPassword(StringTools.encodeMd5(newPassword));
        userInfoService.updateUserInfoByUserId(updateUserInfo, tokenUserInfo.getUserId());
        // 强制退出，重新登录
        channelContextUtils.closeContext(tokenUserInfo.getUserId());
        return getSuccessResponseVO(null);
    }

    @PostMapping("/logout")
    @GlobalInterceptor
    public ResponseVO<Object> logout() {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        // 强制退出，重新登录，关闭ws连接
        channelContextUtils.closeContext(tokenUserInfo.getUserId());
        return getSuccessResponseVO(null);
    }
}