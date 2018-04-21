package com.gdut.pandora.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by buzheng on 18/4/21.
 * 服务执行日志记录类
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Before("execution(public * com.gdut.pandora.controller.*.*(..)) )")
    public void around(JoinPoint joinPoint) {
        aspectHandler(joinPoint);
    }

    private void aspectHandler(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String classAndMethod = className + "的" + methodName + "执行了,";
        Object[] args = joinPoint.getArgs() == null ? new Object[0] : joinPoint.getArgs();
        log.warn("服务执行，执行参数分别是:{},arg:{}", classAndMethod, JSON.toJSONString(args));
    }
}
