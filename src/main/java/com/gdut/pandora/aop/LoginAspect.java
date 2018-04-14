package com.gdut.pandora.aop;

import com.gdut.pandora.anno.ReturnType;
import com.gdut.pandora.common.Constant;
import com.gdut.pandora.common.ReturnTypeEnum;
import com.gdut.pandora.common.ServerResponse;
import com.gdut.pandora.domain.User;
import com.gdut.pandora.domain.query.AddressQuery;
import com.gdut.pandora.domain.query.UserQuery;
import com.gdut.pandora.domain.result.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
            boolean flag = false;
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                if (joinPoint.getArgs()[i] instanceof UserQuery) {
                    UserQuery userQuery = (UserQuery) joinPoint.getArgs()[i];
                    flag = isLoginSuccess(userQuery.getPhone());
                    break;
                }
                if (joinPoint.getArgs()[i] instanceof AddressQuery) {
                    AddressQuery addressQuery = (AddressQuery) joinPoint.getArgs()[i];
                    flag = isLoginSuccess(String.valueOf(addressQuery.getPhone()));
                    if (addressQuery.getUid() != null) {
                        flag = true;
                    }
                    break;
                }
            }
            if (!flag) {
                //通过注解进行区分返回类型
                ReturnType returnType = getReturnTypeAnnotion(joinPoint);
                if (returnType != null) {
                    if(returnType.type().getType().equals(ReturnTypeEnum.BOOLEAN_TYPE.getType())){
                        return ServerResponse.createByErrorMessage("请进行用户注册或者登陆再进行相关操作", false);
                    }else {
                        return ServerResponse.createByErrorMessage("请进行用户注册或者登陆再进行相关操作", new ArrayList<>());
                    }
                }
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
    private boolean isLoginSuccess(String phone) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(phone);
        boolean result = user == null ? Boolean.FALSE : Boolean.TRUE;
        return result;
    }

    /**
     * 获取ReturnType Annotion 注解
     */
    private ReturnType getReturnTypeAnnotion(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null && method.getAnnotation(ReturnType.class) != null) {
            return method.getAnnotation(ReturnType.class);
        }
        return null;
    }

}
