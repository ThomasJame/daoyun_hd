package com.sx.daoyun.controller;


import java.util.Date;
public class Tool<T> {
    public static int NEED_RE_LOGIN = 1;
    public static int NEED_RETRY = 2;

    public int code;

    public String message;

    public String flag;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String flag() {
        return flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrCode() {
        return code;
    }

    public void setErrCode(int code) {
        this.code = code;
    }
}

