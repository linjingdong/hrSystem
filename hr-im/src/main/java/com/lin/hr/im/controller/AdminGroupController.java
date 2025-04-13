package com.lin.hr.im.controller;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.im.entity.po.GroupInfo;
import com.lin.hr.im.entity.query.GroupInfoQuery;
import com.lin.hr.im.mappers.GroupInfoMapper;
import com.lin.hr.im.service.GroupInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/4/13 17:01
 **/
@RestController
@RequestMapping("/admin")
public class AdminGroupController extends ABaseController {
    @Resource
    private GroupInfoService groupInfoService;
    @Resource
    private GroupInfoMapper<GroupInfo, GroupInfoQuery> groupInfoMapper;

    @PostMapping("/loadGroup")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO<Object> loadGroup(GroupInfoQuery groupInfoQuery) {
        groupInfoQuery.setQueryGroupMemberCount(true);
        groupInfoQuery.setQueryGroupOwenNickName(true);
        groupInfoQuery.setOrderBy("create_time desc");
        PaginationResultVO<GroupInfo> listByPage = groupInfoService.findListByPage(groupInfoQuery);
        return getSuccessResponseVO(listByPage);
    }

    @PostMapping("/dissolutionGroup")
    @GlobalInterceptor(checkAdmin = true)
    public ResponseVO<Object> dissolutionGroup(@NotBlank String groupId) {
        GroupInfo groupInfo = groupInfoMapper.selectByGroupId(groupId);
        if (null == groupInfo) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String groupOwnerId = groupInfo.getGroupOwnerId();
        groupInfoService.dissolutionGroup(groupOwnerId, groupId);
        return getSuccessResponseVO("解散群组成功");
    }
}
