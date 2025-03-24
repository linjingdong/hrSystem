package com.lin.hr.im.controller;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.im.entity.vo.ResponseVO;
import com.lin.hr.common.exception.BusinessException;


public class ABaseController {

    protected static final String STATIC_SUCCESS = "success";

    protected static final String STATIC_ERROR = "error";

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
}
