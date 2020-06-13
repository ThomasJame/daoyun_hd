package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.CourseMapper;
import com.sx.daoyun.mapper.TaskMapper;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Task;
import com.sx.daoyun.tool.RedisUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class TaskController {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;

    @PostMapping("task/{classID}")
    public Tool addTask(@PathVariable("classID") int classid,
                        @RequestBody Map<String,Object> addinfo) {
        Tool result = new Tool<>();
//        {"TaskTitle":"Token","TaskExp":"10","Content":"使用Token实现用户的身份验证"}
        int TaskExp = Integer.valueOf((String)addinfo.get("TaskExp"));
        String TaskTitle =(String) addinfo.get("TaskTitle");
        String Content = (String) addinfo.get("Content");
        String time=(String)addinfo.get("time");
        Task task = new Task();

            task.setClassID(classid);
            task.setTaskTitle(TaskTitle);
            task.setTaskExp(TaskExp);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
//            Date  currdate = new Date();
//            Calendar ca = Calendar.getInstance();
//            ca.add(Calendar.DATE, 7);// num为增加的天数，可以改变的
//            currdate = ca.getTime();
        try {
            task.setDate(simpleDateFormat.parse(time));
        }catch (Exception e){

        }
            task.setCreateDate(new Date());
            task.setContent(Content);
            taskMapper.addTask(task);
            result.setMessage("新增任务成功");
            result.setFlag("true");
            result.setCode(2000);
            return result;
    }

    @GetMapping("task/list/{classid}")
    public Tool queryTaskList(@PathVariable("classid") int classid) {
        Tool result = new Tool<>();
        List<Task> taskList = taskMapper.queryTaskListByClassId(classid, 0);
        System.out.println(taskList);
        HashMap res = new HashMap();
        List restasklist = new ArrayList();
        String teacher =courseMapper.queryCourseById(classid).getTeacher();
        for (Task task : taskList) {
//            任务ID、任务标题、任务经验值、截止时间
            HashMap map = new HashMap();
            map.put("id", task.getTaskID());
            map.put("title", task.getTaskTitle());
            map.put("exp", task.getTaskExp());
            map.put("date", task.getDate());
            map.put("teacher",teacher);
            restasklist.add(map);
        }
        res.put("total", taskList.size());
        res.put("rows", restasklist);
        result.setData(res);
        result.setMessage("根据课程id获取任务列表成功");
        result.setFlag("true");
        result.setErrCode(2000);
        return result;
    }

    @GetMapping("task2/{classid}/{taskid}")
    public Tool taskDetail(@PathVariable("classid") int classid,
                           @PathVariable("taskid") int taskid) {
        Tool result = new Tool<>();
        List<Task> taskList = taskMapper.queryTaskListByClassId(classid, taskid);
        HashMap map = new HashMap();
        map.put("title", taskList.get(0).getTaskTitle());
        map.put("exp", taskList.get(0).getTaskExp());
        map.put("content", taskList.get(0).getContent());
        map.put("id",taskList.get(0).getTaskID());
        result.setData(map);
        result.setMessage("任务详情查看成功");
        result.setFlag("true");
        result.setErrCode(2000);
        return result;
    }

    @PutMapping("task/{classid}/{taskid}")
    public Tool updateTask(@PathVariable("classid") int classid,
                           @PathVariable("taskid") int taskid,
                           @RequestBody Map<String,Object> upmapper) {
        Tool result = new Tool<>();
//        {"id":3,"title":"任务3","exp":3,"content":"2314124"}
        int TaskExp = (int)upmapper.get("exp");
        String TaskTitle = (String)upmapper.get("title");
        String Content = (String)upmapper.get("content");
        Task task = new Task();
            task.setTaskTitle(TaskTitle);
            task.setTaskExp(TaskExp);
            task.setContent(Content);
            task.setTaskID(taskid);
            task.setClassID(classid);
            taskMapper.updateTask(task);
            result.setMessage("修改任务成功");
            result.setFlag("true");
            result.setCode(2000);
            return result;
    }

    @DeleteMapping("task/{classID}/{taskID}")
    public Tool deleteTask(@PathVariable("classID") int classID
    ,@PathVariable("taskID") int taskID) {
        Tool result = new Tool<>();
        taskMapper.deleteTask(classID,taskID);
        result.setMessage("删除任务成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
}
