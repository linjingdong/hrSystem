package com.lin.hr.im.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.constants.AccountConstant;
import com.lin.hr.common.constants.FileConstant;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.enums.user.UserContactStatusEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.config.AppConfig;
import com.lin.hr.common.enums.user.UserStatusEnum;
import com.lin.hr.common.enums.user.UserTypeEnum;
import com.lin.hr.im.entity.po.UserContact;
import com.lin.hr.im.entity.query.UserContactQuery;
import com.lin.hr.im.entity.vo.account.UserInfoVo;
import com.lin.hr.im.mappers.UserContactMapper;
import com.lin.hr.im.service.UserContactService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.hr.common.enums.PageSize;
import com.lin.hr.im.entity.query.UserInfoQuery;
import com.lin.hr.im.entity.po.UserInfo;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.query.SimplePage;
import com.lin.hr.im.mappers.UserInfoMapper;
import com.lin.hr.im.service.UserInfoService;
import com.lin.hr.common.utils.StringTools;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户基础表 业务接口实现
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;
    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RedisComponent redisComponent;
    @Autowired
    private UserContactMapper<UserContact, UserContactQuery> userContactMapper;
    @Autowired
    private UserContactService userContactService;

    /**
     * 根据条件查询列表
     */
    @Override
    public List<UserInfo> findListByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectList(param);
    }

    /**
     * 根据条件查询列表
     */
    @Override
    public Integer findCountByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectCount(param);
    }

    /**
     * 分页查询方法
     */
    @Override
    public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserInfo> list = this.findListByParam(param);
        PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    @Override
    public Integer add(UserInfo bean) {
        return this.userInfoMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    @Override
    public Integer addBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或者修改
     */
    @Override
    public Integer addOrUpdateBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 多条件更新
     */
    @Override
    public Integer updateByParam(UserInfo bean, UserInfoQuery param) {
        StringTools.checkParam(param);
        return this.userInfoMapper.updateByParam(bean, param);
    }

    /**
     * 多条件删除
     */
    @Override
    public Integer deleteByParam(UserInfoQuery param) {
        StringTools.checkParam(param);
        return this.userInfoMapper.deleteByParam(param);
    }

    /**
     * 根据UserId获取对象
     */
    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        return this.userInfoMapper.selectByUserId(userId);
    }

    /**
     * 根据UserId修改
     */
    @Override
    public Integer updateUserInfoByUserId(UserInfo bean, String userId) {
        return this.userInfoMapper.updateByUserId(bean, userId);
    }

    /**
     * 根据UserId删除
     */
    @Override
    public Integer deleteUserInfoByUserId(String userId) {
        return this.userInfoMapper.deleteByUserId(userId);
    }

    /**
     * 根据Username获取对象
     */
    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return this.userInfoMapper.selectByUsername(username);
    }

    /**
     * 根据Username修改
     */
    @Override
    public Integer updateUserInfoByUsername(UserInfo bean, String username) {
        return this.userInfoMapper.updateByUsername(bean, username);
    }

    /**
     * 根据Username删除
     */
    @Override
    public Integer deleteUserInfoByUsername(String username) {
        return this.userInfoMapper.deleteByUsername(username);
    }

    /**
     * 根据Email获取对象
     */
    @Override
    public UserInfo getUserInfoByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }

    /**
     * 根据Email修改
     */
    @Override
    public Integer updateUserInfoByEmail(UserInfo bean, String email) {
        return this.userInfoMapper.updateByEmail(bean, email);
    }

    /**
     * 根据Email删除
     */
    @Override
    public Integer deleteUserInfoByEmail(String email) {
        return this.userInfoMapper.deleteByEmail(email);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(String phone, String username, String password, Integer userType) {
        UserInfo userInfo = this.userInfoMapper.selectByAccount(phone, Objects.requireNonNull(UserTypeEnum.getByCode(userType)).getValue());
        if (null != userInfo) {
            throw new BusinessException(ResponseCodeEnum.CODE_600.getCode(), "该手机号关联的用户信息已经存在！");
        }
        if (null == username || username.isEmpty()) {
            username = "匿名用户";
        }

        userInfo = new UserInfo();
        userInfo.setUserId(StringTools.getUserId());
        userInfo.setAccount(phone);
        userInfo.setPhone(phone);
        userInfo.setPassword(StringTools.encodeMd5(password));
        userInfo.setUsername(username);
        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setUserType(UserTypeEnum.getByCode(userType).getValue());
        userInfo.setLastOffTime(System.currentTimeMillis());
        userInfo.setCreateTime(new Date());
        this.userInfoMapper.insert(userInfo);

        userContactService.addContact4Robot(userInfo.getUserId());
    }

    @Override
    public UserInfoVo login(String account, String password, Integer userType) {
        UserInfo userInfo = this.userInfoMapper.selectByAccount(account, Objects.requireNonNull(UserTypeEnum.getByCode(userType)).getValue());
        if (null == userInfo) {
            throw new BusinessException(ResponseCodeEnum.CODE_600.getCode(), "该账号信息不存在！");
        }
        if (!userInfo.getPassword().equals(StringTools.encodeMd5(password))) {
            throw new BusinessException(ResponseCodeEnum.CODE_600.getCode(), "用户输入密码错误！");
        }
        if (userInfo.getStatus().equals(UserStatusEnum.DISABLED.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600.getCode(), "该用户账号已被禁用！");
        }
        Long lastHeartBeat = redisComponent.getUserHeartBeat(userInfo.getUserId());
        if (null != lastHeartBeat) {
            throw new BusinessException(ResponseCodeEnum.CODE_601.getCode(), "此账号已经在别处登录，请退出后再登录");
        }

        // 查询我的联系人
        UserContactQuery userContactQuery = new UserContactQuery();
        userContactQuery.setUserId(userInfo.getUserId());
        userContactQuery.setStatus(UserContactStatusEnum.FRIEND.getStatus());
        List<UserContact> userContacts = this.userContactMapper.selectList(userContactQuery);
        List<String> userContactIds = userContacts.stream().map(UserContact::getContactId).collect(Collectors.toList());
        redisComponent.cleanUserContactIds(userInfo.getUserId());
        if (!userContactIds.isEmpty()) {
            redisComponent.addUserContactBatch(userInfo.getUserId(), userContactIds);
        }

        // 查询我的群组
        TokenUserInfoDto tokenUserInfo = getTokenUserInfo(userInfo);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        userInfoVo.setToken(tokenUserInfo.getToken());
        userInfoVo.setAdmin(tokenUserInfo.getAdmin());

        return userInfoVo;
    }

    @Override
    public UserInfoVo getUserInfo(TokenUserInfoDto token) {
        UserInfoVo userInfoVo = new UserInfoVo();
        UserInfo userInfo = this.getUserInfoByUserId(token.getUserId());
        BeanUtils.copyProperties(userInfo, userInfoVo);
        userInfoVo.setAdmin(token.getAdmin());
        return userInfoVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserInfo userInfo, MultipartFile avatarFile, MultipartFile avatarCover) {
        if (null != avatarFile) {
            String baseFolder = appConfig.getProjectFolder() + FileConstant.FILE_FOLDER_FILE;
            File targetFileFolder = new File(baseFolder + FileConstant.FILE_FOLDER_AVATAR_NAME);
            if (!targetFileFolder.exists()) {
                targetFileFolder.mkdirs();
            }
            String filePath = targetFileFolder.getPath() + "/" + userInfo.getUserId() + FileConstant.IMAGE_SUFFIX;
            try {
                avatarFile.transferTo(new File(filePath));
                avatarCover.transferTo(new File(filePath + FileConstant.COVER_IMAGE_SUFFIX));
            } catch (IOException e) {
                throw new BusinessException("头像图片上传失败");
            }
        }
        UserInfo dbUserInfo = userInfoMapper.selectByUserId(userInfo.getUserId());
        userInfoMapper.updateByUserId(userInfo, userInfo.getUserId());
        String contactNameUpdate = null;
        if (!dbUserInfo.getUsername().equals(userInfo.getUsername())) {
            contactNameUpdate = userInfo.getUsername();
        }
        // TODO 更新会话信息中的昵称信息
    }

    @Override
    public void updateUserStatus(Integer status, String userId) {
        UserStatusEnum userStatusEnum = UserStatusEnum.getByStatus(status);
        if (userStatusEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(userStatusEnum.getStatus());
        userInfoMapper.updateByUserId(userInfo, userId);
    }

    @Override
    public void forceOffLine(String userId) {
        // TODO 强制下线，发送ws
    }

    private TokenUserInfoDto getTokenUserInfo(UserInfo userInfo) {
        TokenUserInfoDto tokenUserInfo = new TokenUserInfoDto();
        tokenUserInfo.setToken(StringTools.encodeMd5(userInfo.getUserId()) + StringTools.getRandomString(AccountConstant.LENGTH_20));
        tokenUserInfo.setUserId(userInfo.getUserId());
        tokenUserInfo.setUserName(userInfo.getUsername());
        String adminAccount = appConfig.getAdminAccount();
        if (StringUtils.isNotBlank(adminAccount) && ArrayUtils.contains(adminAccount.split(","), userInfo.getAccount())) {
            tokenUserInfo.setAdmin(true);
        } else {
            tokenUserInfo.setAdmin(false);
        }
        // 缓存登录信息
        redisComponent.saveTokenUserInfo(tokenUserInfo);
        return tokenUserInfo;
    }
}