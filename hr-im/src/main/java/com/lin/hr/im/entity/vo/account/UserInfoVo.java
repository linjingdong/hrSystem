package com.lin.hr.im.entity.vo.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/23 16:28
 **/
@Data
public class UserInfoVo implements Serializable {
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
    private Date createTime;

    /**
     * 最后离开时间
     */
    private Long lastOffTime;

    private String token;

    private Boolean admin;

    private Integer contactStatus;
}
