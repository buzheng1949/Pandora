package com.gdut.pandora.anno;

import java.lang.annotation.*;

/**
 * Created by buzheng on 18/4/4.
 * 用户登陆检测注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface NeedLogin {

}
