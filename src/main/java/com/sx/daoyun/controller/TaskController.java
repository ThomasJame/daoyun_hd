package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.TaskMapper;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Task;
import com.sx.daoyun.tool.RedisUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("task") //增加课程
    public Tool addTask(HttpServletRequest request) {
        Tool result = new Tool<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        int classid = Integer.parseInt(request.getParameter("classID"));
        int TaskExp = Integer.parseInt(request.getParameter("TaskExp"));
        int ParticipateNum = Integer.parseInt(request.getParameter("ParticipateNum"));
        String TaskTitle = request.getParameter("TaskTitle");
        String TaskPicture = request.getParameter("TaskPicture");
        String Content = request.getParameter("Content");
        String Information = request.getParameter("Information");
        int userid = Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        String creater = userMapper.queryUserById(userid).getUserName();
        Task task = new Task();
        try {
            task.setClassID(classid);
            task.setTaskTitle(TaskTitle);
            task.setTaskExp(TaskExp);
            task.setTaskPicture(TaskPicture);
            task.setCreateDate(new Date());
            task.setParticipateNum(ParticipateNum);
            task.setContent(Content);
            task.setInformation(Information);
            task.setCreateby(creater);
            task.setDate(simpleDateFormat.parse(request.getParameter("Date")));
            taskMapper.addTask(task);
            result.setMessage("新增任务成功");
            result.setFlag("true");
            result.setCode(2000);
        } catch (Exception e) {
            task.setClassID(classid);
            task.setTaskTitle(TaskTitle);
            task.setTaskExp(TaskExp);
            task.setTaskPicture(TaskPicture);
            task.setDate(new Date());
            task.setParticipateNum(ParticipateNum);
            task.setContent(Content);
            task.setInformation(Information);
            task.setCreateby(creater);
            task.setCreateDate(new Date());
            taskMapper.addTask(task);
            result.setMessage("日期格式异常 默认未 当前日期");
            result.setFlag("flase");
            result.setCode(2000);
        }
        return result;
    }

    @GetMapping("task/list")
    public Tool addUser(HttpServletRequest request) {
        Tool result = new Tool<>();
        int classid = Integer.parseInt(request.getParameter("id"));
        List<Task> taskList = taskMapper.queryTaskListByClassId(classid, 0);
        HashMap res = new HashMap();
        List restasklist = new ArrayList();
        for (Task task : taskList) {
//            任务ID、任务标题、任务经验值、截止时间
            HashMap map = new HashMap();
            map.put("id", task.getTaskID());
            map.put("title", task.getTaskTitle());
            map.put("exp", task.getTaskExp());
            map.put("date", task.getDate());
            restasklist.add(map);
        }
        res.put("total", taskList.size());
        res.put("res", restasklist);
        result.setData(res);
        result.setMessage("根据课程id获取任务列表成功");
        result.setFlag("true");
        result.setErrCode(200);
        return result;
    }

    @GetMapping("task2")
    public Tool taskDetail(HttpServletRequest request) {
        Tool result = new Tool<>();
        int classid = Integer.parseInt(request.getParameter("classid"));
        int taskid = Integer.parseInt(request.getParameter("taskid"));
        List<Task> taskList = taskMapper.queryTaskListByClassId(classid, taskid);
        HashMap map = new HashMap();
        map.put("title", taskList.get(0).getTaskTitle());
        map.put("exp", taskList.get(0).getTaskExp());
        map.put("content", taskList.get(0).getContent());
        result.setData(map);
        result.setMessage("任务详情查看成功");
        result.setFlag("true");
        result.setErrCode(200);
        return result;
    }

    @PutMapping("task")
    public Tool updateTask(HttpServletRequest request) {
        Tool result = new Tool<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        int classid = Integer.parseInt(request.getParameter("classid"));
        int taskid= Integer.parseInt(request.getParameter("taskid"));
        int TaskExp = Integer.parseInt(request.getParameter("TaskExp"));
        int ParticipateNum = Integer.parseInt(request.getParameter("ParticipateNum"));
        String TaskTitle = request.getParameter("TaskTitle");
        String TaskPicture = request.getParameter("TaskPicture");
        String Content = request.getParameter("Content");
        String Information = request.getParameter("Information");
        int userid = Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        String updater = userMapper.queryUserById(userid).getUserName();
        Task task = new Task();
        try {
            task.setClassID(classid);
            task.setTaskID(taskid);
            task.setTaskTitle(TaskTitle);
            task.setTaskExp(TaskExp);
            task.setTaskPicture(TaskPicture);
            task.setModifyDate(new Date());
            task.setParticipateNum(ParticipateNum);
            task.setContent(Content);
            task.setInformation(Information);
            task.setModifyby(updater);
            task.setDate(simpleDateFormat.parse(request.getParameter("Date")));
            taskMapper.updateTask(task);
            result.setMessage("修改任务成功");
            result.setFlag("true");
            result.setCode(2000);
        } catch (Exception e) {
            task.setClassID(classid);
            task.setTaskID(taskid);
            task.setTaskTitle(TaskTitle);
            task.setTaskExp(TaskExp);
            task.setTaskPicture(TaskPicture);
            task.setModifyDate(new Date());
            task.setParticipateNum(ParticipateNum);
            task.setContent(Content);
            task.setInformation(Information);
            task.setModifyby(updater);
            task.setDate(new Date());
            taskMapper.updateTask(task);
            result.setMessage("日期格式异常 默认未 当前日期");
            result.setFlag("flase");
            result.setCode(2000);
            result.setErrCode(500);
        }
        return result;
    }

    @DeleteMapping("task")
    public Tool deleteTask(HttpServletRequest request) {
        Tool result = new Tool<>();
        int taskid= Integer.parseInt(request.getParameter("taskid"));
        taskMapper.deleteTask(taskid);
        result.setMessage("删除任务成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
}
