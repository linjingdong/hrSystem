package com.lin.hr.im.controller;

import com.lin.hr.common.component.RedisComponent;
import com.lin.hr.common.constants.RedisKeyConstant;
import com.lin.hr.common.constants.TimeConstant;
import com.lin.hr.common.controller.ABaseController;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.utils.RedisUtils;
import com.lin.hr.common.vo.ResponseVO;
import com.lin.hr.im.entity.vo.account.LoginReqVo;
import com.lin.hr.im.entity.vo.account.RegisterReqVo;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.im.entity.vo.account.UserInfoVo;
import com.lin.hr.im.service.UserInfoService;
import com.wf.captcha.ArithmeticCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/3/20 23:56
 **/
@RestController("accountController")
@RequestMapping("/account")
@Validated
public class AccountController extends ABaseController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private RedisUtils redisUtil;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RedisComponent redisComponent;

    @PostMapping("/checkCode")
    public ResponseVO<Object> checkCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(100, 42);
        String checkCode = captcha.text();
        String checkCodeKey = UUID.randomUUID().toString();
        log.info("图形验证码键：{}，值：{}", checkCodeKey, checkCode);
        // 缓存验证码
        redisUtil.setEx(RedisKeyConstant.REDIS_KEY_CHECK_CODE + checkCodeKey, checkCode, TimeConstant.REDIS_TIME_ONE_MIN * 5L);

        Map<String, Object> result = new HashMap<>();
        result.put("checkCode", captcha.toBase64());
        result.put("checkCodeKey", checkCodeKey);
        return getSuccessResponseVO(result);
    }

    @PostMapping("/register")
    public ResponseVO<Object> register(@RequestBody @Valid RegisterReqVo registerReqVo) {
        try {
            validCheckCode(registerReqVo.getCheckCodeKey(), registerReqVo.getCheckCode());
            userInfoService.register(registerReqVo.getPhone(), registerReqVo.getUsername(), registerReqVo.getPassword(), registerReqVo.getUserType());
        } finally {
            redisUtil.delete(RedisKeyConstant.REDIS_KEY_CHECK_CODE + registerReqVo.getCheckCodeKey());
        }
        return getSuccessResponseVO("注册成功！");
    }

    @PostMapping("/login")
    public ResponseVO<Object> login(@RequestBody @Valid LoginReqVo loginReqVo) {
        try {
            validCheckCode(loginReqVo.getCheckCodeKey(), loginReqVo.getCheckCode());
            UserInfoVo userInfoVo = userInfoService.login(loginReqVo.getAccount(), loginReqVo.getPassword(), loginReqVo.getUserType());
            return getSuccessResponseVO(userInfoVo);
        } finally {
            redisUtil.delete(RedisKeyConstant.REDIS_KEY_CHECK_CODE + loginReqVo.getCheckCodeKey());
        }
    }

    @GetMapping("/getSysSetting")
    public ResponseVO<Object> getSysSetting() {
        return getSuccessResponseVO(redisComponent.getSysSetting());
    }

    private void validCheckCode(String checkCodeKey, String checkCode) {
        Object checkCodeObj = redisUtil.get(RedisKeyConstant.REDIS_KEY_CHECK_CODE + checkCodeKey);
        if (checkCodeObj == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600.getCode(), "请重新获取图形验证码！");
        }
        if (!checkCode.equalsIgnoreCase(checkCodeObj.toString())) {
            throw new BusinessException(ResponseCodeEnum.CODE_600.getCode(), "图形验证码不正确！");
        }
    }
}
