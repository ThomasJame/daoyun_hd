package com.sx.daoyun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private int TaskID;
    private int ClassID;
    private String TaskTitle;
    private String TaskPicture;
    private int TaskExp;
    private Date Date;
    private int ParticipateNum;
    private String Content;
    private String Information;
    private String Createby;
    private Date CreateDate;
    private String Modifyby;
    private Date ModifyDate;

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public int getTaskID() {
        return TaskID;
    }

    public void setTaskID(int taskID) {
        TaskID = taskID;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int classID) {
        ClassID = classID;
    }

    public String getTaskTitle() {
        return TaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        TaskTitle = taskTitle;
    }

    public String getTaskPicture() {
        return TaskPicture;
    }

    public void setTaskPicture(String taskPicture) {
        TaskPicture = taskPicture;
    }

    public int getTaskExp() {
        return TaskExp;
    }

    public void setTaskExp(int taskExp) {
        TaskExp = taskExp;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public int getParticipateNum() {
        return ParticipateNum;
    }

    public void setParticipateNum(int participateNum) {
        ParticipateNum = participateNum;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
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
