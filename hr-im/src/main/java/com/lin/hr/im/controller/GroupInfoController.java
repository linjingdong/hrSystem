package com.lin.hr.im.controller;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.im.entity.po.GroupInfo;
import com.lin.hr.im.entity.query.GroupInfoQuery;
import com.lin.hr.im.service.GroupInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller
 */
@RestController("groupInfoController")
@RequestMapping("/group")
public class GroupInfoController extends ABaseController {

    @Resource
    private GroupInfoService groupInfoService;

    @PostMapping("/saveGroup")
    @GlobalInterceptor
    public ResponseVO<Object> saveGroup(String groupId,
                                        @NotEmpty String groupName,
                                        String groupNotice,
                                        Integer joinType,
                                        @NotNull MultipartFile avatarFile,
                                        MultipartFile avatarCover) throws IOException {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupId(groupId);
        groupInfo.setGroupName(groupName);
        groupInfo.setGroupOwnerId(tokenUserInfo.getUserId());
        groupInfo.setGroupNotice(groupNotice);
        groupInfo.setJoinType(joinType);

        groupInfoService.saveGroup(groupInfo, avatarFile, avatarCover);
        return getSuccessResponseVO(null);
    }

    @GlobalInterceptor
    @PostMapping("loadMyGroup")
    public ResponseVO<Object> loadMyGroup() {
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo();
        GroupInfoQuery groupInfoQuery = new GroupInfoQuery();
        groupInfoQuery.setGroupOwnerId(tokenUserInfo.getUserId());
        groupInfoQuery.setOrderBy("create_time");
        List<GroupInfo> groupInfos = groupInfoService.findListByParam(groupInfoQuery);
        return getSuccessResponseVO(groupInfos);
    }
}