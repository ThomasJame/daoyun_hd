package com.sx.daoyun.pojo;

import java.util.Date;

public class SignRecord {
    private int QianDaoID;
    private int ClassID;
    private Date QianDaoDate;
    private Date QianDaoTime;
    private String Weekday;
    private String QianDaoType;
    private String GesturePwd;
    private String Createby;
    private Date CreateDate;
    private String Modifyby;
    private Date ModifyDate;

    public int getQianDaoID() {
        return QianDaoID;
    }

    public void setQianDaoID(int qianDaoID) {
        QianDaoID = qianDaoID;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int classID) {
        ClassID = classID;
    }

    public Date getQianDaoDate() {
        return QianDaoDate;
    }

    public void setQianDaoDate(Date qianDaoDate) {
        QianDaoDate = qianDaoDate;
    }

    public Date getQianDaoTime() {
        return QianDaoTime;
    }

    public void setQianDaoTime(Date qianDaoTime) {
        QianDaoTime = qianDaoTime;
    }

    public String getWeekday() {
        return Weekday;
    }

    public void setWeekday(String weekday) {
        Weekday = weekday;
    }

    public String getQianDaoType() {
        return QianDaoType;
    }

    public void setQianDaoType(String qianDaoType) {
        QianDaoType = qianDaoType;
    }

    public String getGesturePwd() {
        return GesturePwd;
    }

    public void setGesturePwd(String gesturePwd) {
        GesturePwd = gesturePwd;
    }

    public String getCreateby() {
        return Createby;
    }

    public void setCreateby(String createby) {
        Createby = createby;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getModifyby() {
        return Modifyby;
    }

    public void setModifyby(String modifyby) {
        Modifyby = modifyby;
    }

    public Date getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        ModifyDate = modifyDate;
    }
}
