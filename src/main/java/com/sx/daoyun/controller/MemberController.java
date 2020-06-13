package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.*;
import com.sx.daoyun.pojo.*;
import com.sx.daoyun.tool.RedisUtil;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
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
    @Autowired
    private CourseMapper courseMapper;

    @PostMapping("member/{classID}/{userID}") //用户加入班课
    public Tool userAddCourse(@PathVariable("classID") int classID,
                              @PathVariable("userID") int userID) {
        Tool result = new Tool<>();
        int userid = userID;
        int courseid = classID;

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
            System.out.println("3");
            map.put("memberName", userMapper.queryUserById(userCourseTrue.getUserID()).getNickName());
            System.out.println("4");
            map.put("exp", userCourseMapper.querybystudentid(userCourseTrue.getUserID(),id).getExp());
            System.out.println("5");
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
        UserCourseTrue userCourseTrue=new UserCourseTrue();
        userCourseTrue.setUserID(userMapper.queryuserbynumber(stunumber).getId());
        userCourseTrue.setClassID(classid);
        userCourseTrueMapper.deletemember(userCourseTrue);
        result.setMessage("成员退出班课");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @PostMapping("member/qiandao/{ClassID}")///发起签到
    @Options(useGeneratedKeys=true, keyProperty="QianDaoID", keyColumn="QianDaoID")
    public Tool qiaodao(@PathVariable("ClassID") int ClassID,
                        @RequestBody Map<String,Object> code) {
        Tool result = new Tool<>();
        Date time=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String GesturePwd = (String) code.get("code");
        String method=(String)code.get("method");
        String Longitude=(String)code.get("longitude");
        String Latitude=(String)code.get("latitude");
        SignRecord signRecord = new SignRecord();
            signRecord.setClassID(ClassID);
            signRecord.setQianDaoDate(new Date());
                    Date  currdate = new Date();
            Calendar ca = Calendar.getInstance();
            ca.add(Calendar.MINUTE, 1);// num为增加的天数，可以改变的
            currdate = ca.getTime();
            signRecord.setQianDaoTime(currdate);
            signRecord.setGesturePwd(GesturePwd);
            signRecord.setCreateDate(time);
            signRecord.setQianDaoType(method);
            signRecord.setLatitude(Latitude);
            signRecord.setLongitude(Longitude);
            signRecordMapper.addRecord(signRecord);
            int myid=signRecord.getQianDaoID();
////////返回签到id
//        ArrayList <SignRecord> arrayList=signRecordMapper.getlist();
//        int myid=0;
//        for (SignRecord s :arrayList) {
//            System.out.println(s.getCreateDate());
//            System.out.println(time);
//            System.out.println(sdf.format(s.getCreateDate()).equals(sdf.format(time)));
//            if (sdf.format(s.getCreateDate()).equals(sdf.format(time))){
//                myid=s.getQianDaoID();
//            }
//        }
//        System.out.println(myid);
            //在所有成员的成员历史签到信息表中新增一条记录
            List<UserCourse> userCourseList = userCourseMapper.queryListbyCourseID(ClassID);
            for (UserCourse userCourse : userCourseList) {
                SignHistory signHistory = new SignHistory();
                signHistory.setQianDaoID(myid);
                signHistory.setMemberID(userCourse.getMemberID());
                signHistory.setClassID(ClassID);
                signHistory.setIsQianDao(0);
                signHistory.setCreateDate(new Date());
                signHistoryMapper.addSignHistory(signHistory);
            }
            result.setMessage("发起签到");
            result.setFlag("true");
            result.setCode(2000);
        return result;
    }

    @GetMapping("member/qiandao/getQianDaoCode/{classID}")
    public Tool getQianDaoCode(@PathVariable("classID") int classID) {
        Tool result = new Tool<>();
        SignRecord signRecord = signRecordMapper.getQianDaoCode(classID);
        HashMap map=new HashMap();
        map.put("code",signRecord.getGesturePwd());
        map.put("Longitude",signRecord.getLongitude());
        map.put("Latitude",signRecord.getLatitude());
        result.setData(map);
        result.setMessage("学生进行签到，获取到课程的签到码");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @GetMapping("member/qiandao/qiaodaoHistory/{ClassID}/{StudentID}")
    public Tool qiaodaoHistory(@PathVariable("ClassID") int ClassID,
                               @PathVariable("StudentID") int StudentID) {
        Tool result = new Tool<>();
        HashMap res = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<SignHistory> signHistoryList = signHistoryMapper.qiaodaoHistory(StudentID, ClassID);
        System.out.println(1);
        List signHistorys=new ArrayList();
        double wcout=0;
        double ycout=0;
        for (SignHistory signHistory : signHistoryList) {
            HashMap signs = new HashMap();
//            "QianDaoDate": "1998-10-31",
//                    "Weekday": "星期六",
//                    "QianDaoType": "数字签到",
//                    "isQianDao": "已签到"
            signs.put("QianDaoDate", sdf.format(signHistory.getCreateDate()));//用修改日期当作签到日期

            int Qiandaoid=signHistory.getQianDaoID();
            signs.put("QianDaoType",signRecordMapper.getQianDaoRecord(Qiandaoid).getQianDaoType());
            System.out.println(Qiandaoid);
            if (signHistory.getIsQianDao() == 0) {
                signs.put("isQianDao", "未签到");
                wcout++;
            } else {
                signs.put("isQianDao", "已签到");
                ycout++;
            }
            signHistorys.add(signs);
        }
        res.put("row", signHistorys);
       String rate=String.format("%.2f", ycout/(ycout+wcout)*100);
        res.put("rate", rate+"%");
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
        map.put("memberName",user.getNickName());
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

    @GetMapping("member/list/{id}")
    public Tool memberList(@PathVariable("id") int id) {
        Tool result = new Tool<>();
        List<UserCourseTrue> userCourseTrueList = userCourseTrueMapper.querylistbybid(id,null);//得到课程对应的所有学生id
        int count=userCourseTrueList.size();
        HashMap resmap = new HashMap();
        resmap.put("total", count);
        List users = new ArrayList();//用于放封装好的学生数组
        for (UserCourseTrue userCourseTrue : userCourseTrueList) {
            HashMap map = new HashMap();
            map.put("stuID", userMapper.queryUserById(userCourseTrue.getUserID()).getNumber());

            map.put("stuName", userMapper.queryUserById(userCourseTrue.getUserID()).getNickName());
            map.put("department",userMapper.queryUserById(userCourseTrue.getUserID()).getDepartment());
            map.put("major",userMapper.queryUserById(userCourseTrue.getUserID()).getSchool());
            map.put("exp", userCourseMapper.querybystudentid(userCourseTrue.getUserID(),id).getExp());

            users.add(map);
        }
        resmap.put("rows", users);
        result.setData(resmap);
        result.setMessage("根据班课id返回学生列表");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @PostMapping("member/qiandao/qianDaoSuccess/{classID}/{StudentID}")
    public Tool qiandaosuccess(@PathVariable("classID") int classID,
                               @PathVariable("StudentID") int StudentID,
                               @RequestBody Map<String,Object> map) {
        Tool result = new Tool<>();
        String flag=(String) map.get("flag");
        String longitude=(String) map.get("longitude");
        String latitude=(String) map.get("latitude");
        if (flag.equals("true")){
        signHistoryMapper.qiandaosuccess(StudentID,classID,longitude,latitude);
        result.setMessage("成功签到");
        result.setFlag("true");
        result.setCode(2000);}
        else {
            result.setMessage("成功失败");
            result.setFlag("false");
        }
        return result;
    }

    @GetMapping("member/qiandao/qiaodaoDetail/{classID}")
    public Tool qiandaodetail(@PathVariable("classID") int classID) {
        Tool result = new Tool<>();
        //获取课程最新一次签到的老师信息
        SignRecord signRecord=signRecordMapper.getQianDaoCode(classID);
        Course course=courseMapper.queryCourseById(classID);
        String teacher=course.getTeacher();
        String tlong=signRecord.getLongitude();
        String tlat=signRecord.getLatitude();
        ArrayList res=new ArrayList();
        HashMap map=new HashMap();
        map.put("latitude",tlat);
        map.put("longitude",tlong);
        map.put("iconPath","../../static/common/place.png");
        //label部分
        HashMap map1=new HashMap();
        map1.put("content",teacher);
        map1.put("color","#7201CA");
        map1.put("bgColor","#fff");
        map1.put("padding",5);
        map1.put("borderRadius",4);
        map.put("label",map1);
        res.add(map);

        ////学生部分
       List<SignHistory>signHistoryList= signHistoryMapper.qiaodaoHistorybyQiandaoid(signRecord.getQianDaoID());
       for(SignHistory signHistory:signHistoryList) {
           HashMap smap = new HashMap();
           smap.put("latitude", signHistory.getLatitude());
           smap.put("longitude",signHistory.getLongitude());
           smap.put("iconPath","../../static/common/place.png");
           User user=userMapper.queryUserById(signHistory.getMemberID());
           //label
           HashMap lmap=new HashMap();
           lmap.put("content",user.getNickName());
           lmap.put("color","#F76350");
           lmap.put("bgColor","#fff");
           lmap.put("padding",5);
           lmap.put("borderRadius",4);
           smap.put("label",lmap);
           //callout
           HashMap cmap=new HashMap();
           cmap.put("content","学生签到超过范围");
           cmap.put("color","#F76350");
           cmap.put("fontSize",12);
           cmap.put("borderRadius",5);
           smap.put("callout",cmap);
           res.add(smap);
       }
       HashMap ress=new HashMap();
       ress.put("covers",res);
       result.setData(ress);
        result.setMessage("成功获取详细数据");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
}
