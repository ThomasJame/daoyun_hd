package com.sx.daoyun.pojo;

import java.util.Date;

public class SignHistory {
    private int ClassID;
    private int MemberID;
    private int QianDaoID;
    private int isQianDao;
    private String Createby;
    private Date CreateDate;
    private String Modifyby;
    private Date ModifyDate;

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int classID) {
        ClassID = classID;
    }

    public int getMemberID() {
        return MemberID;
    }

    public void setMemberID(int memberID) {
        MemberID = memberID;
    }

    public int getQianDaoID() {
        return QianDaoID;
    }

    public void setQianDaoID(int qianDaoID) {
        QianDaoID = qianDaoID;
    }

    public int getIsQianDao() {
        return isQianDao;
    }

    public void setIsQianDao(int isQianDao) {
        this.isQianDao = isQianDao;
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
