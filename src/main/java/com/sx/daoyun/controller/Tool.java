package com.sx.daoyun.controller;


import java.util.Date;
public class Tool<T> {
    public static int NEED_RE_LOGIN = 1;
    public static int NEED_RETRY = 2;

    private int code;

    private String message;

    private String flag;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

