package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface ResourceMapper {
    int addResource(Resource resource);
    List<Resource> getResourceByClassId(int classid,int resourceid);
    int deleteResource(int id);
    int updateResource(Resource resource);
}
