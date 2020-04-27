package com.sx.daoyun.pojo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String UserName;
    private String NickName;
    private String Sex;
    private int Number;
    private String Mobile;
    private String Email;
    private String Password;
    private String School;
    private String Department;
    private String Photo;
    private int exp;
    private int CharmValue;
    private int LanDouNum;
    private int XinYi;
    private Date Birthday;
    private String Createby;
    private Date CreateDate;
    private String Modifyby;
    private Date ModifyDate;
    private int QianDaoExp;
    private int ResourceExp;
    private int taskExp;
    private int VideoExp;
    private int TimeExp;
    private int DiscussExp;
    private int DianZanExp;
    private int JoinClassExp;

    public String getModifyby() {
        return Modifyby;
    }

    public void setModifyby(String modifyby) {
        Modifyby = modifyby;
    }

    public int getQianDaoExp() {
        return QianDaoExp;
    }

    public void setQianDaoExp(int qianDaoExp) {
        QianDaoExp = qianDaoExp;
    }

    public int getResourceExp() {
        return ResourceExp;
    }

    public void setResourceExp(int resourceExp) {
        ResourceExp = resourceExp;
    }

    public int getTaskExp() {
        return taskExp;
    }

    public void setTaskExp(int taskExp) {
        this.taskExp = taskExp;
    }

    public int getVideoExp() {
        return VideoExp;
    }

    public void setVideoExp(int videoExp) {
        VideoExp = videoExp;
    }

    public int getTimeExp() {
        return TimeExp;
    }

    public void setTimeExp(int timeExp) {
        TimeExp = timeExp;
    }

    public int getDiscussExp() {
        return DiscussExp;
    }

    public void setDiscussExp(int discussExp) {
        DiscussExp = discussExp;
    }

    public int getDianZanExp() {
        return DianZanExp;
    }

    public void setDianZanExp(int dianZanExp) {
        DianZanExp = dianZanExp;
    }

    public int getJoinClassExp() {
        return JoinClassExp;
    }

    public void setJoinClassExp(int joinClassExp) {
        JoinClassExp = joinClassExp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String school) {
        School = school;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCharmValue() {
        return CharmValue;
    }

    public void setCharmValue(int charmValue) {
        CharmValue = charmValue;
    }

    public int getLanDouNum() {
        return LanDouNum;
    }

    public void setLanDouNum(int lanDouNum) {
        LanDouNum = lanDouNum;
    }

    public int getXinYi() {
        return XinYi;
    }

    public void setXinYi(int xinYi) {
        XinYi = xinYi;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
        Birthday = birthday;
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


    public Date getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        ModifyDate = modifyDate;
    }

    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(String.valueOf(user.getId()))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
