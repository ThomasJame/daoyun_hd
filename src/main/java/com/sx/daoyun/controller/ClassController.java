package com.sx.daoyun.controller;

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
    @GetMapping("class/join/list")  //查询 加入 课程列表
//    班课ID，班级名称、课程名称、教师
    public  Tool myAddCourse(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("id"));
        List<UserCourse> userCourses=new ArrayList<>();
        userCourses=userCourseMapper.queryCourseIdbyUserid(id);//得到usercourse表
        List courseList=new ArrayList<>();
        for (UserCourse usercourse:userCourses) {
            int courseid=usercourse.getClassID();//得到课程id  去课程表查询id 对应条目 加入到结果集courselidt中
            Course course=courseMapper.queryCourseById(courseid);
            Map rescourse=new HashMap();
            rescourse.put("ClassID",course.getClassID());
            rescourse.put("className",course.getClassName());
            rescourse.put("CourseName",course.getCourseName());
            rescourse.put("Teacher",course.getTeacher());
            //查询一条   封装 加入到rescourse 中
            courseList.add(rescourse);
        }
        result.setData(courseList);
        result.setMessage("列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("class/create/list")//查询 创建 课程列表
    public  Tool myBulitCourse(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("id"));
        List<Course> courseList=courseMapper.queryCourseByCreaterId(id);
//        班课ID,班级名称、课程名称、教师。
        System.out.println(courseList);
        List res=new ArrayList();
        for (Course course:courseList){
            Map recourses=new HashMap();
            recourses.put("ClassID",course.getClassID());
            recourses.put("ClassName",course.getClassName());
            recourses.put("CourseName",course.getCourseName());
            recourses.put("Teacher",course.getTeacher());
            res.add(recourses);
        }
        result.setData(res);
        result.setMessage("班课管理列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("class/list") //查询全部课程
    public Tool searchClassList(HttpServletRequest request){
//        班课号、课程名称、教师，以及班课总数total
        Tool result = new Tool<>();
        int page=Integer.parseInt(request.getParameter("page"));
        int size=Integer.parseInt(request.getParameter("size"));
        int begin=(page-1)*size;
        int end=page*size;
        List<Course> courseList=courseMapper.queryCourseList();
        int count=courseList.size();
        List<Course> courseList1=new ArrayList<>();
        if (end>=count-1){
            courseList1=courseList.subList(begin,count-1);
        }else {
            courseList1=courseList.subList(begin,end);
        }
        HashMap res=new HashMap();
        res.put("total",count);
        List recourses=new ArrayList();
        for (Course course:courseList1) {
            Map recourse=new HashMap();
            recourse.put("ClassID",course.getClassID());
            recourse.put("ClassName",course.getClassName());
            recourse.put("Teacher",course.getTeacher());
            recourse.put("term",course.getTerm());
            recourses.add(recourse);
        }
        res.put("res",recourses);
        result.setData(res);
        result.setMessage("成功获取班课信息");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
//
    @PostMapping("class") //增加课程
    public Tool addClass(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        Date date=new Date();
         String ClassName=request.getParameter("ClassName");
         String CourseName=request.getParameter("CourseName");
         int CreatorID=id;
         String Teacher=request.getParameter("Teacher");
         String ClassPic=request.getParameter("ClassPic");
         String Term=request.getParameter("Term");
         String School=request.getParameter("School");
         String Department=request.getParameter("Department");
         String Textbook=request.getParameter("Textbook");
         String Request=request.getParameter("Request");
         String Schedule=request.getParameter("Schedule");
         String Exam=request.getParameter("Exam");
         String MemberNum=request.getParameter("MemberNum");
         int isEnd=Integer.parseInt(request.getParameter("isEnd"));
        int isOpen=Integer.parseInt(request.getParameter("isOpen"));
         String Createby=userMapper.queryUserById(id).getUserName();
         Course course=new Course();
         course.setIsEnd(isEnd);
         course.setIsOpen(isOpen);
         course.setClassName(ClassName);
        course.setCourseName(CourseName);
        course.setCreatorID(CreatorID);
        course.setTeacher(Teacher);
        course.setClassPic(ClassPic);
        course.setTerm(Term);
        course.setSchool(School);
        course.setDepartment(Department);
        course.setTextbook(Textbook);
        course.setRequest(Request);
        course.setSchedule(Schedule);
        course.setExam(Exam);
        course.setMemberNum(MemberNum);
        course.setCreateby(Createby);
        course.setCreateDate(date);
        courseMapper.addCourse(course);
        List<Course> courseList=courseMapper.queryCourseByCreaterId(id);
        for (Course course1:courseList) {
            if (course1.getCreateDate().equals(date)){
                result.setData(course1.getClassID());
            }
        }
        result.setMessage("新增班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
//
    @DeleteMapping("class") //删除课程
    public Tool deleteClass(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("id"));
        courseMapper.deleteCourse(id);
        result.setMessage("删除班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
//
    @PutMapping("class") //修改课程
    public Tool updateClass(HttpServletRequest request){
        Tool result = new Tool<>();
        int ClassID=Integer.parseInt(request.getParameter("ClassID"));
        int modifyid=Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        Date date=new Date();
        String ClassName=request.getParameter("ClassName");
        String CourseName=request.getParameter("CourseName");
        String Teacher=request.getParameter("Teacher");
        String ClassPic=request.getParameter("ClassPic");
        String Term=request.getParameter("Term");
        String School=request.getParameter("School");
        String Department=request.getParameter("Department");
        String Textbook=request.getParameter("Textbook");
        String Request=request.getParameter("Request");
        String Schedule=request.getParameter("Schedule");
        String Exam=request.getParameter("Exam");
        String MemberNum=request.getParameter("MemberNum");
        int isEnd=Integer.parseInt(request.getParameter("isEnd"));
        int isOpen=Integer.parseInt(request.getParameter("isOpen"));
        String Modifyby=userMapper.queryUserById(modifyid).getUserName();
        Course course=new Course();
        course.setClassID(ClassID);
        course.setIsEnd(isEnd);
        course.setIsOpen(isOpen);
        course.setClassName(ClassName);
        course.setCourseName(CourseName);
        course.setTeacher(Teacher);
        course.setClassPic(ClassPic);
        course.setTerm(Term);
        course.setSchool(School);
        course.setDepartment(Department);
        course.setTextbook(Textbook);
        course.setRequest(Request);
        course.setSchedule(Schedule);
        course.setExam(Exam);
        course.setMemberNum(MemberNum);
        course.setModifyby(Modifyby);
        course.setModifyDate(date);
        courseMapper.updateCourse(course);
        result.setMessage("修改班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
//
    @GetMapping("class") //查询课程
    public Tool getClassById(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("id"));
        Course course=courseMapper.queryCourseById(id);
        Map recourse=new HashMap();;
//        返回班课图片、班级名称、课程名称、老师、学年、云教材、学校、院系、学习要求、教学进度、考试安排、班课号、是否已结束
        recourse.put("ClassPic",course.getClassPic());
        recourse.put("ClassName",course.getClassName());
        recourse.put("Teacher",course.getTeacher());
        recourse.put("Term",course.getTerm());
        recourse.put("Textbook",course.getTextbook());
        recourse.put("School",course.getSchool());
        recourse.put("Department",course.getDepartment());
        recourse.put("Request",course.getRequest());
        recourse.put("Schedule",course.getSchedule());
        recourse.put("Exam",course.getExam());
        recourse.put("ClassID",course.getClassID());
        recourse.put("isOpen",course.getIsEnd());
        result.setData(recourse);
        result.setMessage("班课管理列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @PutMapping("class/endclass")
    public Tool endClass(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("classID"));
        courseMapper.endCourse(id);
        result.setMessage("结束班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
}
