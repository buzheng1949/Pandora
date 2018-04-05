package com.gdut.pandora.aop;

import com.gdut.pandora.common.Constant;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by buzheng on 18/4/4.
 */
@Aspect
@Component
@Slf4j
public class LoginAspect {

    @Pointcut("@annotation(com.gdut.pandora.anno.NeedLogin)")
    public void loginPointCount() {
    }

    @Around("loginPointCount()")
    public Object actionLog(ProceedingJoinPoint joinPoint) {
        Object res = new Object();
        try {
            boolean flag = isLoginSuccess();
            if (!flag) {
                return ServerResponse.createByErrorMessage("请进行用户登陆");
            }
            res = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("use aop to login check failed", e);
        }
        return res;
    }


    /**
     * 用户是否登陆成功
     *
     * @return
     */
    private boolean isLoginSuccess() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constant.SESSION.CURRENT_USER);
        boolean result = user == null ? Boolean.FALSE : Boolean.TRUE;
        return result;
    }

}
