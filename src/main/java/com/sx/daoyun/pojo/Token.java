package com.sx.daoyun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private int id;
    private String token;
    private int UserID;
    private Date LoginTime;
    private Date loginoutTime;
    private int IfOverTime;
    private String LoginMethod;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public Date getLoginTime() {
        return LoginTime;
    }

    public void setLoginTime(Date loginTime) {
        LoginTime = loginTime;
    }

    public Date getLoginoutTime() {
        return loginoutTime;
    }

    public void setLoginoutTime(Date loginoutTime) {
        this.loginoutTime = loginoutTime;
    }

    public int getIfOverTime() {
        return IfOverTime;
    }

    public void setIfOverTime(int ifOverTime) {
        IfOverTime = ifOverTime;
    }

    public String getLoginMethod() {
        return LoginMethod;
    }

    public void setLoginMethod(String loginMethod) {
        LoginMethod = loginMethod;
    }
}
