package com.sx.daoyun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourse {
    private int ID;
    private int MemberID;
    private int ClassID;
    private String MemberName;
    private String Photo;
    private int StuID;
    private int Exp;
    private String QianDaoRate;
    private String TaskRate;
    private String GesturePwd;
    private Date EedTime;
    private String Createby;
    private Date CreateDate;
    private String Modifyby;
    private Date ModifyDate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMemberID() {
        return MemberID;
    }

    public void setMemberID(int memberID) {
        MemberID = memberID;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int classID) {
        ClassID = classID;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String memberName) {
        MemberName = memberName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public int getStuID() {
        return StuID;
    }

    public void setStuID(int stuID) {
        StuID = stuID;
    }

    public int getExp() {
        return Exp;
    }

    public void setExp(int exp) {
        Exp = exp;
    }

    public String getQianDaoRate() {
        return QianDaoRate;
    }

    public void setQianDaoRate(String qianDaoRate) {
        QianDaoRate = qianDaoRate;
    }

    public String getTaskRate() {
        return TaskRate;
    }

    public void setTaskRate(String taskRate) {
        TaskRate = taskRate;
    }

    public String  getGesturePwd() {
        return GesturePwd;
    }

    public void setGesturePwd(String gesturePwd) {
        GesturePwd = gesturePwd;
    }

    public Date getEedTime() {
        return EedTime;
    }

    public void setEedTime(Date eedTime) {
        EedTime = eedTime;
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
