package com.gdut.pandora.common;

/**
 * Created by buzheng on 18/3/30.
 * 服务返回码
 */
public enum ResponseCode {

    SUCCESS(1, "success"),
    ERROR(0, "error"),
    NEED_LOGIN(0, "请进行用户注册或者登陆再进行相关操作"),
    ILLEGAL_ARGUMENT(2, "illegal_argument");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
