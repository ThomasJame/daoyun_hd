package com.sx.daoyun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String Latitude;
    private String Longitude;

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

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
