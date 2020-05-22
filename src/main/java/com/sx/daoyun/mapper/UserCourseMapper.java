package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.UserCourse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface UserCourseMapper {
    List<UserCourse> queryCourseIdbyUserid(int uerid); //查询用户加入的课程
//    List<UserCourse> queryCourseIdbyCreater(String username);//查询用户创建的课程
    int userAddCourse(UserCourse userCourse);
    List<UserCourse> queryListbyCourseID(int courseid);
    int deletemember(UserCourse userCourse);
    UserCourse querybystudentid(int stuid,int clasid);
}
