package com.lin.hr.im.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import com.lin.hr.common.enums.DateTimePatternEnum;
import com.lin.hr.common.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 用户基础表
 */
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
    private String lastDeviceId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后离开时间
     */
    private Long lastOffTime;


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaCode() {
        return this.areaCode;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUnionId() {
        return this.unionId;
    }

    public void setLastDeviceId(String lastDeviceId) {
        this.lastDeviceId = lastDeviceId;
    }

    public String getLastDeviceId() {
        return this.lastDeviceId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setLastOffTime(Long lastOffTime) {
        this.lastOffTime = lastOffTime;
    }

    public Long getLastOffTime() {
        return this.lastOffTime;
    }

    @Override
    public String toString() {
        return "用户唯一UUID:" + (userId == null ? "空" : userId) + "，账户:" + (account == null ? "空" : account) + "，用户名称:" + (username == null ? "空" : username) + "，性别 0-女 1-男:" + (sex == null ? "空" : sex) + "，加密后密码:" + (password == null ? "空" : password) + "，绑定邮箱:" + (email == null ? "空" : email) + "，手机号（加密存储）:" + (phone == null ? "空" : phone) + "，用户类型 ENUM('patient','doctor','admin'):" + (userType == null ? "空" : userType) + "，状态:" + (status == null ? "空" : status) + "，地区:" + (areaName == null ? "空" : areaName) + "，地区编号:" + (areaCode == null ? "空" : areaCode) + "，✨微信OpenID（加密存储）:" + (openid == null ? "空" : openid) + "，✨微信UnionID:" + (unionId == null ? "空" : unionId) + "，✨最后登录设备ID:" + (lastDeviceId == null ? "空" : lastDeviceId) + "，创建时间:" + (createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "，最后离开时间:" + (lastOffTime == null ? "空" : lastOffTime);
    }
}
