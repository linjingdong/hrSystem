package com.lin.hr.im.entity.query;


import lombok.Getter;
import lombok.Setter;

/**
 * 用户基础表参数
 */
@Setter
@Getter
public class UserInfoQuery extends BaseParam {
    /**
     * 用户唯一UUID
     */
    private String userId;

    private String userIdFuzzy;

    /**
     * 账户
     */
    private String account;

    private String accountFuzzy;

    /**
     * 用户名称
     */
    private String username;

    private String usernameFuzzy;

    /**
     * 性别 0-女 1-男
     */
    private Integer sex;

    /**
     * 加密后密码
     */
    private String password;

    private String passwordFuzzy;

    /**
     * 绑定邮箱
     */
    private String email;

    private String emailFuzzy;

    /**
     * 手机号（加密存储）
     */
    private String phone;

    private String phoneFuzzy;

    /**
     * 用户类型 ENUM('patient','doctor','admin')
     */
    private String userType;

    private String userTypeFuzzy;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 地区
     */
    private String areaName;

    private String areaNameFuzzy;

    /**
     * 地区编号
     */
    private String areaCode;

    private String areaCodeFuzzy;

    /**
     * ✨微信OpenID（加密存储）
     */
    private String openid;

    private String openidFuzzy;

    /**
     * ✨微信UnionID
     */
    private String unionId;

    private String unionIdFuzzy;

    /**
     * ✨最后登录设备ID
     */
    private String lastLoginTime;

    private String lastLoginTimeFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 最后离开时间
     */
    private String lastOffTime;

    private String lastOffTimeStart;

    private String lastOffTimeEnd;
}
