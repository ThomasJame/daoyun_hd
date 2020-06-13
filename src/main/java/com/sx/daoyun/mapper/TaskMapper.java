package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface TaskMapper {
    int addTask(Task task);
    List<Task> queryTaskListByClassId(int classid,int taskid);
    int updateTask(Task task);
    int deleteTask(int classid,int taskid);
    List<Task> queryminetask(int id);
}
