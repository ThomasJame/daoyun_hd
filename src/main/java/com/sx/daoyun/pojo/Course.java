package com.sx.daoyun.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int ClassID;
    private String ClassName;
    private String CourseName;
    private int CreatorID;
    private String Teacher;
    private String ClassPic;
    private String Term;
    private String School;
    private String Department;
    private String Textbook;
    private String Request;
    private String Schedule;
    private String Exam;
    private int isOpen;
    private int  isEnd;
    private String MemberNum;
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

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public int getCreatorID() {
        return CreatorID;
    }

    public void setCreatorID(int creatorID) {
        CreatorID = creatorID;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getClassPic() {
        return ClassPic;
    }

    public void setClassPic(String classPic) {
        ClassPic = classPic;
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
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

    public String getTextbook() {
        return Textbook;
    }

    public void setTextbook(String textbook) {
        Textbook = textbook;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public String getSchedule() {
        return Schedule;
    }

    public void setSchedule(String schedule) {
        Schedule = schedule;
    }

    public String getExam() {
        return Exam;
    }

    public void setExam(String exam) {
        Exam = exam;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public String getMemberNum() {
        return MemberNum;
    }

    public void setMemberNum(String memberNum) {
        MemberNum = memberNum;
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
