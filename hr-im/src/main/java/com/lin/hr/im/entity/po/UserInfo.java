package com.lin.hr.im.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 用户基础表
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 用户唯一UUID
     */
    private String userId;

    /**
     * 账户
     */
    private String account;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 性别 0-女 1-男
     */
    private Integer sex;

    /**
     * 加密后密码
     */
    private String password;

    /**
     * 绑定邮箱
     */
    private String email;

    /**
     * 手机号（加密存储）
     */
    @JsonIgnore
    private String phone;

    /**
     * 用户类型 ENUM('patient','doctor','admin')
     */
    private String userType;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 地区
     */
    private String areaName;

    /**
     * 地区编号
     */
    private String areaCode;

    /**
     * ✨微信OpenID（加密存储）
     */
    private String openid;

    /**
     * ✨微信UnionID
     */
    private String unionId;

    /**
     * ✨最后登录设备ID
     */
    private Long lastLoginTime;

    /**
     * 最后离开时间
     */
    private Long lastOffTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Override
    public String toString() {
        return "用户唯一UUID:" + (userId == null ? "空" : userId) + "，账户:" + (account == null ? "空" : account) + "，用户名称:" + (username == null ? "空" : username) + "，性别 0-女 1-男:" + (sex == null ? "空" : sex) + "，加密后密码:" + (password == null ? "空" : password) + "，绑定邮箱:" + (email == null ? "空" : email) + "，手机号（加密存储）:" + (phone == null ? "空" : phone) + "，用户类型 ENUM('patient','doctor','admin'):" + (userType == null ? "空" : userType) + "，状态:" + (status == null ? "空" : status) + "，地区:" + (areaName == null ? "空" : areaName) + "，地区编号:" + (areaCode == null ? "空" : areaCode) + "，✨微信OpenID（加密存储）:" + (openid == null ? "空" : openid) + "，✨微信UnionID:" + (unionId == null ? "空" : unionId) + "，✨最后登录设备时间:" + (lastLoginTime == null ? "空" : lastLoginTime) + "，创建时间:" + (createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "，最后离开时间:" + (lastOffTime == null ? "空" : lastOffTime);
    }
}
