package com.lin.hr.im.service;

import java.util.List;

import com.lin.hr.im.entity.query.UserInfoQuery;
import com.lin.hr.im.entity.po.UserInfo;
import com.lin.hr.common.vo.PaginationResultVO;
import com.lin.hr.im.entity.vo.account.UserInfoVo;


/**
 * 用户基础表 业务接口
 */
public interface UserInfoService {

    /**
     * 根据条件查询列表
     */
    List<UserInfo> findListByParam(UserInfoQuery param);

    /**
     * 根据条件查询列表
     */
    Integer findCountByParam(UserInfoQuery param);

    /**
     * 分页查询
     */
    PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param);

    /**
     * 新增
     */
    Integer add(UserInfo bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<UserInfo> listBean);

    /**
     * 批量新增/修改
     */
    Integer addOrUpdateBatch(List<UserInfo> listBean);

    /**
     * 多条件更新
     */
    Integer updateByParam(UserInfo bean, UserInfoQuery param);

    /**
     * 多条件删除
     */
    Integer deleteByParam(UserInfoQuery param);

    /**
     * 根据UserId查询对象
     */
    UserInfo getUserInfoByUserId(String userId);


    /**
     * 根据UserId修改
     */
    Integer updateUserInfoByUserId(UserInfo bean, String userId);


    /**
     * 根据UserId删除
     */
    Integer deleteUserInfoByUserId(String userId);


    /**
     * 根据Username查询对象
     */
    UserInfo getUserInfoByUsername(String username);


    /**
     * 根据Username修改
     */
    Integer updateUserInfoByUsername(UserInfo bean, String username);


    /**
     * 根据Username删除
     */
    Integer deleteUserInfoByUsername(String username);


    /**
     * 根据Email查询对象
     */
    UserInfo getUserInfoByEmail(String email);


    /**
     * 根据Email修改
     */
    Integer updateUserInfoByEmail(UserInfo bean, String email);


    /**
     * 根据Email删除
     */
    Integer deleteUserInfoByEmail(String email);

    /**
     * 用户注册
     *
     * @param phone    手机号
     * @param username 用户名
     * @param password 密码
     * @param userType
     */
    void register(String phone, String username, String password, Integer userType);

    UserInfoVo login(String account, String password, Integer userType);
}