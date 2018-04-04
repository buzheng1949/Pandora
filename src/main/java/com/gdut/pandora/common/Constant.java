package com.gdut.pandora.common;

/**
 * Created by buzheng on 18/4/1.
 * 常量值
 */
public interface Constant {

    interface QueryParams {
        Integer START = 0;
        Integer PAGE_SIZE = 5;
    }

    interface SESSION{
        String CURRENT_USER = "current_user";
    }

}
