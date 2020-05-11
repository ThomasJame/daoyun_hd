package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.SignHistoryMapper;
import com.sx.daoyun.mapper.SignRecordMapper;
import com.sx.daoyun.pojo.SignHistory;
import com.sx.daoyun.pojo.SignRecord;
import com.sx.daoyun.mapper.UserCourseMapper;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.User;
import com.sx.daoyun.pojo.UserCourse;
import com.sx.daoyun.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class MemberController {
    @Autowired
    UserCourseMapper userCourseMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SignRecordMapper signRecordMapper;
    @Autowired
    private SignHistoryMapper signHistoryMapper;

    @PostMapping("member") //用户加入班课
    public Tool userAddCourse(HttpServletRequest request) {
        Tool result = new Tool<>();
        int userid = Integer.parseInt(request.getParameter("userID"));
        int courseid = Integer.parseInt(request.getParameter("classID"));
        String MemberName = userMapper.queryUserById(userid).getUserName();
        int number = userMapper.queryUserById(userid).getNumber();
        UserCourse userCourse = new UserCourse();
        userCourse.setClassID(courseid);
        userCourse.setMemberID(userid);
        userCourse.setCreateDate(new Date());
        userCourse.setMemberName(MemberName);
        userCourse.setStuID(number);
        int id = Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        User createrUser = userMapper.queryUserById(id);
        String creater = createrUser.getUserName();
        userCourse.setCreateby(creater);
        userCourseMapper.userAddCourse(userCourse);
        result.setMessage("用户加入班课成功");
        result.setFlag("flase");
        result.setCode(2000);
        return result;
    }

    @GetMapping("member/list")
    public Tool memberList(HttpServletRequest request) {
        Tool result = new Tool<>();
        int courseid = Integer.parseInt(request.getParameter("courseid"));
        List<UserCourse> UserCourses = userCourseMapper.queryListbyCourseID(courseid);
        HashMap resmap = new HashMap();
        int count = UserCourses.size();
        resmap.put("total", count);
        List users = new ArrayList();//用于放封装好的学生数组
        for (UserCourse userCourse : UserCourses) {
            HashMap map = new HashMap();
            map.put("stuID", userCourse.getStuID());
            map.put("stuName", userCourse.getMemberName());
            map.put("exp", userCourse.getExp());
            users.add(map);
        }
        resmap.put("rows", users);
        result.setData(resmap);
        result.setMessage("根据班课id返回学生列表");
        result.setFlag("flase");
        result.setCode(2000);
        return result;
    }

    @DeleteMapping("member")
    public Tool deletemember(HttpServletRequest request) {
        Tool result = new Tool<>();
        int userid = Integer.parseInt(request.getParameter("userID"));
        int courseid = Integer.parseInt(request.getParameter("classID"));
        UserCourse userCourse = new UserCourse();
        userCourse.setMemberID(userid);
        userCourse.setClassID(courseid);
        userCourseMapper.deletemember(userCourse);
        result.setMessage("成员退出班课");
        result.setFlag("flase");
        result.setCode(2000);
        return result;
    }

    @PostMapping("member/qiandao")
    public Tool qiaodao(HttpServletRequest request) {
        Tool result = new Tool<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        int classid = Integer.parseInt(request.getParameter("ClassID"));
        String weekday = request.getParameter("Weekday");
        String QianDaoType = request.getParameter("QianDaoType");
        String GesturePwd = request.getParameter("GesturePwd");
        SignRecord signRecord = new SignRecord();
        try {
            signRecord.setClassID(classid);
            signRecord.setQianDaoDate(new Date());
            signRecord.setQianDaoTime(simpleDateFormat.parse(request.getParameter("QianDaoTime")));
            signRecord.setWeekday(weekday);
            signRecord.setQianDaoType(QianDaoType);
            signRecord.setGesturePwd(GesturePwd);
            int id = Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
            User createrUser = userMapper.queryUserById(id);
            String creater = createrUser.getUserName();
            signRecord.setCreateby(creater);
            signRecord.setCreateDate(new Date());
            signRecordMapper.addRecord(signRecord);
            //在所有成员的成员历史签到信息表中新增一条记录
            List<UserCourse> userCourseList = userCourseMapper.queryListbyCourseID(classid);
            for (UserCourse userCourse : userCourseList) {
                SignHistory signHistory = new SignHistory();
                signHistory.setMemberID(userCourse.getMemberID());
                signHistory.setClassID(classid);
                signHistory.setIsQianDao(0);
                signHistory.setCreateby(creater);
                signHistory.setCreateDate(new Date());
                signHistoryMapper.addSignHistory(signHistory);
            }
            result.setMessage("发起签到");
            result.setFlag("flase");
            result.setCode(2000);
        } catch (Exception e) {
            signRecord.setClassID(classid);
            signRecord.setQianDaoDate(new Date());
            signRecord.setQianDaoTime(new Date());
            signRecord.setWeekday(weekday);
            signRecord.setQianDaoType(QianDaoType);
            signRecord.setGesturePwd(GesturePwd);
            int id = Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
            User createrUser = userMapper.queryUserById(id);
            String creater = createrUser.getUserName();
            signRecord.setCreateby(creater);
            signRecord.setCreateDate(new Date());
            signRecordMapper.addRecord(signRecord);
            List<UserCourse> userCourseList = userCourseMapper.queryListbyCourseID(classid);
            for (UserCourse userCourse : userCourseList) {
                SignHistory signHistory = new SignHistory();
                signHistory.setMemberID(userCourse.getMemberID());
                signHistory.setClassID(classid);
                signHistory.setIsQianDao(0);
                signHistory.setCreateby(creater);
                signHistory.setCreateDate(new Date());
                signHistoryMapper.addSignHistory(signHistory);
            }
            result.setMessage("QianDaoTime格式错误 默认为当前时间");
            result.setFlag("flase");
            result.setCode(2000);
        }

        return result;
    }

    @GetMapping("member/qiandao/getQianDaoCode")
    public Tool getQianDaoCode(HttpServletRequest request) {
        Tool result = new Tool<>();
        int classID = Integer.parseInt(request.getParameter("classID"));
        int code = signRecordMapper.getQianDaoCode(classID);
        result.setData(code);
        result.setMessage("学生进行签到，获取到课程的签到码");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @GetMapping("member/qiandao/qiaodaoHistory")
    public Tool qiaodaoHistory(HttpServletRequest request) {
        Tool result = new Tool<>();
        int userid = Integer.parseInt(request.getParameter("StudentID"));
        int classID = Integer.parseInt(request.getParameter("ClassID"));
        HashMap res = new HashMap();
        List<SignHistory> signHistoryList = signHistoryMapper.qiaodaoHistory(userid, classID);
        List signHistorys=new ArrayList();
        double wcout=0;
        double ycout=0;
        for (SignHistory signHistory : signHistoryList) {
            HashMap signs = new HashMap();
//            "QianDaoDate": "1998-10-31",
//                    "Weekday": "星期六",
//                    "QianDaoType": "数字签到",
//                    "isQianDao": "已签到"
            signs.put("QianDaoDate", signHistory.getModifyDate());//用修改日期当作签到日期
            signs.put("Weekday", signHistory.getCreateDate());//用创建日期表示日期
            if (signHistory.getIsQianDao() == 0) {
                signs.put("isQianDao", "未签到");
                wcout++;
            } else {
                signs.put("isQianDao", "已签到");
                ycout++;
            }
            signHistorys.add(signs);
        }
        res.put("res", signHistorys);
        res.put("rate", ycout/(ycout+wcout)*100+"%");
        result.setData(res);
        result.setMessage("根据ClassID, StudentID返回历史签到记录列表");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

//    @GetMapping("member/detail")
//    public Tool memberDetail(HttpServletRequest request) {
//        Tool result = new Tool<>();
//
//        result.setMessage("查看成员信息成功");
//        result.setFlag("true");
//        result.setCode(2000);
//        return result;
//    }
}
