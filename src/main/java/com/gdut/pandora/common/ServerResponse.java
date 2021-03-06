package com.gdut.pandora.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by buzheng on 18/3/31.
 * 统一响应类
 */
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess() {

        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());

    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), data);
    }


    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.NEED_LOGIN.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorWithoutResponse() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), "查询失败，服务端异常");
    }


    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage, T data) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMessage, data);
    }


    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode, errorMessage);
    }


}
