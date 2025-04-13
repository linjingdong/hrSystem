package com.lin.hr.im.controller;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.im.entity.po.UserInfo;
import com.lin.hr.im.entity.query.UserInfoQuery;
import com.lin.hr.im.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.zip.Checksum;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/13 16:28
 * @Description 管理员接口
 **/
@RestController
@RequestMapping("/admin")
public class AdminUserInfoController extends ABaseController {
    @Resource
    private UserInfoService userInfoService;

    /**
     *  获取用户信息
     */
    @PostMapping("/loadUser")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO<Object> loadUser(UserInfoQuery userInfoQuery) {
        userInfoQuery.setOrderBy("create_time desc");
        PaginationResultVO<UserInfo> userInfos = userInfoService.findListByPage(userInfoQuery);
        return getSuccessResponseVO(userInfos);
    }

    /**
     * 更新用户状态
     */
    @PostMapping("/updateUserStatus")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO<Object> updateUserStatus(@NotBlank Integer status, @NotBlank String userId) {
        userInfoService.updateUserStatus(status, userId);
        return getSuccessResponseVO("更新用户状态成功");
    }

    /**
     * 强制下线
     */
    @PostMapping("/forceOffLine")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO<Object> forceOffLine(@NotBlank String userId) {
        userInfoService.forceOffLine(userId);
        return getSuccessResponseVO("用户强制下线");
    }
}
