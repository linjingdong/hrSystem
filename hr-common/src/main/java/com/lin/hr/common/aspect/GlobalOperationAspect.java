package com.lin.hr.common.aspect;

import com.lin.hr.common.annotation.GlobalInterceptor;
import com.lin.hr.common.config.ThreadLocalConfig;
import com.lin.hr.common.constants.RedisKeyConstant;
import com.lin.hr.common.dto.TokenUserInfoDto;
import com.lin.hr.common.enums.ResponseCodeEnum;
import com.lin.hr.common.exception.BusinessException;
import com.lin.hr.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Lin_jd
 * @version V1.0.0
 * @Description 全局切面
 * @since 2025/3/30 16:35
 **/
@Slf4j
@Aspect
@Component("globalOperationAspect")
public class GlobalOperationAspect {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Resource
    private RedisUtils<Object> redisUtils;

    @Before("@annotation(com.lin.hr.common.annotation.GlobalInterceptor)")
    public void interceptorDo(JoinPoint joinpoint) {
        try {
            Method method = ((MethodSignature) joinpoint.getSignature()).getMethod();
            GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
            if (interceptor == null) {
                return;
            }
            if (interceptor.checkAdmin() || interceptor.checkLogin()) {
                checkLogin(interceptor.checkAdmin());
            }
        } catch (BusinessException e) {
            log.info("全局捕获异常", e);
            throw e;
        } catch (Exception e) {
            log.info("全局拦截异常", e);
            throw new BusinessException(ResponseCodeEnum.CODE_500);
        }
    }

    private void checkLogin(boolean checkAdmin) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        TokenUserInfoDto tokenUserInfo = (TokenUserInfoDto) redisUtils.get(RedisKeyConstant.REDIS_KEY_WS_TOKEN + token);
        if (null == token || token.isEmpty()) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        if (tokenUserInfo == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        if (checkAdmin && !tokenUserInfo.getAdmin()) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ThreadLocalConfig.setToken(token);
        threadLocal.set(token);
    }
}
