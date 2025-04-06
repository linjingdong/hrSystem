package com.lin.hr.common.controller;

import com.lin.hr.common.config.ThreadLocalConfig;
import com.lin.hr.common.constants.RedisKeyConstant;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.RedisUtils;
import com.lin.hr.common.vo.ResponseVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


public class ABaseController {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    protected static final String STATIC_SUCCESS = "success";

    protected static final String STATIC_ERROR = "error";

    @Resource
    private RedisUtils<Object> redisUtils;

    protected <T> ResponseVO<T> getSuccessResponseVO(T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setStatus(STATIC_SUCCESS);
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }

    protected <T> ResponseVO<T> getBusinessErrorResponseVO(BusinessException e, T t) {
        ResponseVO<T> vo = new ResponseVO<>();
        vo.setStatus(STATIC_ERROR);
        if (e.getCode() == null) {
            vo.setCode(ResponseCodeEnum.CODE_600.getCode());
        } else {
            vo.setCode(e.getCode());
        }
        vo.setInfo(e.getMessage());
        vo.setData(t);
        return vo;
    }

    protected <T> ResponseVO<T> getServerErrorResponseVO(T t) {
        ResponseVO<T> vo = new ResponseVO<>();
        vo.setStatus(STATIC_ERROR);
        vo.setCode(ResponseCodeEnum.CODE_500.getCode());
        vo.setInfo(ResponseCodeEnum.CODE_500.getMsg());
        vo.setData(t);
        return vo;
    }

    /**
     * 获取请求头token
     */
    protected TokenUserInfoDto getTokenUserInfo() {
        String token = ThreadLocalConfig.getToken();
        return (TokenUserInfoDto) redisUtils.get(RedisKeyConstant.REDIS_KEY_WS_TOKEN + token);
    }
}
