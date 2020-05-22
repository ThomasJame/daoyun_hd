package com.sx.daoyun.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sx.daoyun.mapper.CourseMapper;
import com.sx.daoyun.mapper.UserCourseMapper;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Course;
import com.sx.daoyun.pojo.User;
import com.sx.daoyun.pojo.UserCourse;
import com.sx.daoyun.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;

@RestController
public class ClassController {

    @Autowired
    private UserCourseMapper userCourseMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @GetMapping("class/join/list/{id}")  //查询 加入 课程列表
//    班课ID，班级名称、课程名称、教师
    public  Tool myAddCourse(@PathVariable("id") int id){
        Tool result = new Tool<>();
        List<UserCourse> userCourses=new ArrayList<>();
        userCourses=userCourseMapper.queryCourseIdbyUserid(id);//得到usercourse表
        List courseList=new ArrayList<>();
        for (UserCourse usercourse:userCourses) {
            int courseid=usercourse.getClassID();//得到课程id  去课程表查询id 对应条目 加入到结果集courselidt中
            Course course=courseMapper.queryCourseById(courseid);
            Map rescourse=new HashMap();
            rescourse.put("id",course.getClassID());
            rescourse.put("className",course.getCourseName());
            rescourse.put("classStudent",course.getClassName());
            rescourse.put("classTeacher",course.getTeacher());
            //查询一条   封装 加入到rescourse 中
            courseList.add(rescourse);
        }
        result.setData(courseList);
        result.setMessage("列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("class/create/list/{id}")//查询 创建 课程列表
    public  Tool myBulitCourse(@PathVariable("id") int id){
        Tool result = new Tool<>();
        List<Course> courseList=courseMapper.queryCourseByCreaterId(id);
//        班课ID,班级名称、课程名称、教师。
        System.out.println(courseList);
        List res=new ArrayList();
        for (Course course:courseList){
            Map recourses=new HashMap();
            recourses.put("id",course.getClassID());
            recourses.put("className",course.getCourseName());
            recourses.put("classStudent",course.getClassName());
            recourses.put("classTeacher",course.getTeacher());
            res.add(recourses);
        }
        result.setData(res);
        result.setMessage("班课管理列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @PostMapping("class/list/{page}/{size}") //查询全部课程
    public Tool searchClassList(@PathVariable("page") int page,@PathVariable("size") int size
    ,@RequestBody Map<String,Object> searchmap){
//        班课号、课程名称、教师，以及班课总数total
        Tool result = new Tool<>();
        String searchclass=(String)searchmap.get("info");
        int begin=(page-1)*size;
        int end=page*size-1;
        List<Course> courseList=courseMapper.queryCourseList(searchclass);
        int count=courseList.size();
        List<Course> courseList1=new ArrayList<>();
        if (end>=count-1){
            courseList1=courseList.subList(begin,count);
        }else {
            courseList1=courseList.subList(begin,end+1);
        }
        HashMap res=new HashMap();
        res.put("total",count);
        List recourses=new ArrayList();
        for (Course course:courseList1) {
            Map recourse=new HashMap();
            recourse.put("classID",course.getClassID());
            recourse.put("className",course.getClassName());
            recourse.put("teacher",course.getTeacher());
            recourse.put("courseName",course.getCourseName());
            recourses.add(recourse);
        }

        res.put("rows",recourses);
        result.setData(res);
        result.setMessage("成功获取班课信息");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
//
    @PostMapping("class") //增加课程
    public Tool addClass(@RequestBody Map<String,Object> classmap){
        Tool result = new Tool<>();
        Date date=new Date();
        int userid=(int)classmap.get("userid");

        User creater=userMapper.queryUserById(userid);
        String creatername=creater.getUserName();
         String ClassName=(String)classmap.get("className");
         String CourseName=(String)classmap.get("courseName");
         String Term=(String)classmap.get("term");
         String School=(String)classmap.get("school");
         String Department=(String)classmap.get("department");
         Course course=new Course();
         course.setTeacher(creater.getNickName());
         course.setCreatorID(userid);
         course.setCreateby(creatername);
         course.setClassName(ClassName);
         course.setCourseName(CourseName);
         course.setTerm(Term);
        course.setSchool(School);
        course.setDepartment(Department);
        course.setCreateDate(date);
        courseMapper.addCourse(course);
        List<Course> courseList=courseMapper.queryCourseByCreaterId(creater.getId());
        for (Course course1:courseList) {
            if (course1.getCreateDate().equals(date)){
                HashMap res=new HashMap();
                res.put("ClassID",course1.getClassID());
                result.setData(res);
            }
        }
        result.setMessage("新增班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
//
    @DeleteMapping("class/{id}") //删除课程
    public Tool deleteClass(@PathVariable("id") int id){
        Tool result = new Tool<>();
        courseMapper.deleteCourse(id);
        result.setMessage("删除班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
//
    @PutMapping("class") //修改课程
    public Tool updateClass(@RequestBody Map<String,Object> classmap){
        Tool result = new Tool<>();
        int ClassID=(int) classmap.get("classID");
        String ClassName=(String) classmap.get("className");
        String CourseName=(String) classmap.get("courseName");
        String Teacher=(String) classmap.get("teacher");
        String Term=(String) classmap.get("term");
        String School=(String) classmap.get("school");
        String Department=(String) classmap.get("department");
        String Textbook=(String) classmap.get("textbook");
        String Request=(String) classmap.get("request");
        String Schedule=(String) classmap.get("schedule");
        String Exam=(String) classmap.get("exam");

        Boolean state1=(Boolean) classmap.get("state");
        int state;
        if (!state1){
            state=0;
        }else {
            state=1;
        }
        int isOpen;
        Boolean isOpen1=(Boolean) classmap.get("isOpen");
        if (!isOpen1)
        {
            isOpen=0;
        }else {
            isOpen=1;
        }
        Course course=new Course();
        course.setClassID(ClassID);
        course.setIsOpen(isOpen);
        course.setClassName(ClassName);
        course.setCourseName(CourseName);
        course.setTeacher(Teacher);
        course.setTerm(Term);
        course.setSchool(School);
        course.setDepartment(Department);
        course.setTextbook(Textbook);
        course.setRequest(Request);
        course.setSchedule(Schedule);
        course.setExam(Exam);
        course.setState(state);
        courseMapper.updateCourse(course);
        result.setMessage("修改班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
//
    @GetMapping("class/{id}") //查询课程
    public Tool getClassById(@PathVariable("id") int id){
        Tool result = new Tool<>();
        Course course=courseMapper.queryCourseById(id);
        Map recourse=new HashMap();
//        返回班课图片、班级名称、课程名称、老师、学年、云教材、学校、院系、学习要求、教学进度、考试安排、班课号、是否已结束
        recourse.put("classID",course.getClassID());
        recourse.put("school",course.getSchool());
        recourse.put("department",course.getDepartment());
        recourse.put("className",course.getClassName());
        recourse.put("courseName",course.getCourseName());
        recourse.put("teacher",course.getTeacher());
        recourse.put("term",course.getTerm());
        recourse.put("textbook",course.getTextbook());
        recourse.put("request",course.getRequest());
        recourse.put("schedule",course.getSchedule());
        recourse.put("exam",course.getExam());
        if (0==course.getIsOpen()){
            recourse.put("isOpen",false);
        }else {
            recourse.put("isOpen",true);
        }
        if (0==course.getState()){
            recourse.put("state",false);
        }else {
            recourse.put("state",true);
        }
        result.setData(recourse);
        result.setMessage("班课管理列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @PutMapping("class/endclass/{classID}")
    public Tool endClass(@PathVariable("classID") int classID){
        Tool result = new Tool<>();
        courseMapper.endCourse(classID);
        result.setMessage("结束班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
}
