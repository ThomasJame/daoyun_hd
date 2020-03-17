package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.StudentMapper;
import com.sx.daoyun.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/queryStudentList")
    public List<Student> queryStudentList(){
         List<Student> studentList=studentMapper.queryStudentList();
        for (Student student:studentList) {
            System.out.println(student);
        }
         return studentList;
    }
}
