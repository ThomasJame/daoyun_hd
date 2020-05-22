package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.*;
import com.sx.daoyun.pojo.*;
import com.sx.daoyun.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private UserCourseTrueMapper userCourseTrueMapper;
    @Autowired
    private TaskMapper taskMapper;

    @PostMapping("member/{classID}/{userID}") //用户加入班课
    public Tool userAddCourse(@PathVariable("classID") int classID,
                              @PathVariable("userID") int userID) {
        Tool result = new Tool<>();
        int userid = userID;
        int courseid = classID;
        String MemberName = userMapper.queryUserById(userid).getUserName();
        int number = userMapper.queryUserById(userid).getNumber();
        UserCourse userCourse = new UserCourse();
        userCourse.setClassID(courseid);
        userCourse.setCreateDate(new Date());
        userCourse.setMemberID(userid);
        userCourseMapper.userAddCourse(userCourse);

        UserCourseTrue userCoursetrue=new UserCourseTrue();
        userCoursetrue.setUserID(userid);
        userCoursetrue.setClassID(classID);
        userCoursetrue.setCreateDate(new Date());
        userCourseTrueMapper.adduserCourseTrue(userCoursetrue);
        result.setMessage("用户加入班课成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @PostMapping("member/{id}/{page}/{size}")
    public Tool memberList(@PathVariable("id") int id,
                           @PathVariable("page") int page,
                           @PathVariable("size") int size,
                           @RequestBody Map<String,Object> searchmap) {
        Tool result = new Tool<>();
        String studentnumber=(String) searchmap.get("info");//学号
        int begin=(page-1)*size;
        int end=page*size-1;
        List<UserCourseTrue> userCourseTrueList = userCourseTrueMapper.querylistbybid(id,studentnumber);//得到课程对应的所有学生id
        System.out.println(userCourseTrueList);
        int count=userCourseTrueList.size();
        List<UserCourseTrue> userCourseTrueList1=new ArrayList<>();
        if (end>=count-1){
            userCourseTrueList1=userCourseTrueList.subList(begin,count);
        }else {
            userCourseTrueList1=userCourseTrueList.subList(begin,end+1);
        }
        HashMap resmap = new HashMap();
        resmap.put("total", count);
        List users = new ArrayList();//用于放封装好的学生数组
        for (UserCourseTrue userCourseTrue : userCourseTrueList1) {
            HashMap map = new HashMap();
            map.put("stuID", userMapper.queryUserById(userCourseTrue.getUserID()).getNumber());
            map.put("memberName", userMapper.queryUserById(userCourseTrue.getUserID()).getUserName());
            map.put("exp", userCourseMapper.querybystudentid(userCourseTrue.getUserID(),id).getExp());
            System.out.println("3");
            users.add(map);
        }
        resmap.put("rows", users);
        result.setData(resmap);
        result.setMessage("根据班课id返回学生列表");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @DeleteMapping("member/{classid}/{stunumber}")
    public Tool deletemember(@PathVariable("classid") int classid,
                             @PathVariable("stunumber") int stunumber) {
        Tool result = new Tool<>();

        UserCourse userCourse = new UserCourse();
        userCourse.setMemberID(userMapper.queryuserbynumber(stunumber).getId());
        userCourse.setClassID(classid);
        userCourseMapper.deletemember(userCourse);
        result.setMessage("成员退出班课");
        result.setFlag("true");
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

    @GetMapping("member/detail/{classid}/{stunumber}")
    public Tool memberDetail(@PathVariable("classid") int classid,
                             @PathVariable("stunumber") int stunumber) {
        Tool result = new Tool<>();
       User user= userMapper.queryuserbynumber(stunumber);
        UserCourse userCourse=userCourseMapper.querybystudentid(user.getId(),classid);
        HashMap map=new HashMap();
        map.put("stuID",user.getNumber());
        map.put("memberName",user.getUserName());
        double ttotal=taskMapper.queryTaskListByClassId(classid,0).size();
        double min=taskMapper.queryminetask(user.getNumber()).size();
        map.put("taskDetail",(min/ttotal)*100+"%");
        map.put("exp",userCourse.getExp());
        double total=signHistoryMapper.qiaodaoHistory(user.getId(),classid).size();
        System.out.println(total);
        System.out.println(1);
        double attend=signHistoryMapper.qiaodaoHistory1(user.getId(),classid).size();
        double attendrate=(attend/total)*100;
        map.put("attendanceRate",attendrate+"%");
        result.setData(map);
        result.setMessage("查看成员信息成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
}
