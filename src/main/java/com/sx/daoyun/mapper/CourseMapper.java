package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface CourseMapper {
    List<Course> queryCourseByCreaterId(int id);//查询我创建的课程
    Course queryCourseById(int id);//查询课程 通过id
    int addCourse(Course user);
    int updateCourse(Course user);
    int deleteCourse(int id);
    List<Course> queryCourseList(String cname);//查询全部课程列表
    int endCourse(int id);
}
