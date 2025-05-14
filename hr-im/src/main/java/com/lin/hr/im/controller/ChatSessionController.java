package com.lin.hr.im.controller;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.vo.ResponseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @since 2025/5/13 23:37
 **/
@RestController
@RequestMapping("/chatSession")
public class ChatSessionController {
    /**
     * 获取当前用户会话信息
     *
     * @param contactId
     * @return
     */
    @PostMapping("/search")
    @GlobalInterceptor
    public ResponseVO<Object> search(@NotBlank String contactId) {
        return null;
    }
}
