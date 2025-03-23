package com.lin.hr.im.entity.vo.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/22 22:12
 **/
@Data
public class LoginReqVo {
    @NotBlank
    private String checkCodeKey;

    @NotBlank(message = "验证码不能为空！")
    private String checkCode;

    @NotBlank(message = "请填写账号信息！")
    private String account;

    @NotBlank(message = "请输入密码!")
    private String password;

    @NotBlank(message = "请输入用户类型！")
    private Integer userType;
}
