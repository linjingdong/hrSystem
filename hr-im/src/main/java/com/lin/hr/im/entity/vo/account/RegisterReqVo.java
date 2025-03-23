package com.lin.hr.im.entity.vo.account;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/22 19:37
 **/
@Data
public class RegisterReqVo {
    @NotBlank
    private String checkCodeKey;

    @NotEmpty(message = "请输入验证码")
    private String checkCode;

    @NotEmpty(message = "请输入手机号")
    private String phone;

    @NotEmpty(message = "密码不能为空")
    private String password;

    private String username;

    @NotNull(message = "用户类型不能为空")
    private Integer userType;
}
