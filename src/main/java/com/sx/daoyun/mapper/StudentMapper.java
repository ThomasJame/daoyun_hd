package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//这个注解表示了这是一个mybatis的mapper类 或者在主入口函数中使用@MapperScan("com.sx.daoyun.mapper")
@Mapper

@Repository///DAO层 使用的 也可以用com
public interface StudentMapper {
    List<Student> queryStudentList();
    Student queryStudentById(int id);
    int addStudent(Student student);
    int updateStudent(Student student);
    int deleteStudent(int id);
}
