package com.gdut.pandora.anno;

import com.gdut.pandora.common.ReturnTypeEnum;

import java.lang.annotation.*;

/**
 * Created by buzheng on 18/4/14.
 * 切面判断返回类型
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface ReturnType {

    /**
     * 需要进行切面返回的时候具体的返回值 不想跟登陆绑定
     *
     * @return
     */
    ReturnTypeEnum type() default ReturnTypeEnum.DEFAULT;
}
