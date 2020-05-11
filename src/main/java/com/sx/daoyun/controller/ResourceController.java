package com.sx.daoyun.controller;


import com.sx.daoyun.mapper.ResourceMapper;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Resource;
import com.sx.daoyun.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class ResourceController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ResourceMapper resourceMapper;

    @PostMapping("resource")
    public  Tool addResource(HttpServletRequest request){
        Tool result = new Tool<>();
        int classid=Integer.parseInt(request.getParameter("classid"));
        int userid = Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        String creater = userMapper.queryUserById(userid).getUserName();
        Resource resource=new Resource();
        String Title=request.getParameter("Title");
        String Description=request.getParameter("Description");
        String url=request.getParameter("url");
        int DownloadNum=Integer.parseInt(request.getParameter("DownloadNum"));

        resource.setClassID(classid);
        resource.setTitle(Title);
        resource.setDescription(Description);
        resource.setUrl(url);
        resource.setDownloadNum(DownloadNum);
        resource.setCreateby(creater);
        resource.setCreateDate(new Date());

        resourceMapper.addResource(resource);
        result.setMessage("上传资源成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("res/list")
    public  Tool getResource(HttpServletRequest request){
        Tool result = new Tool<>();
        int classid=Integer.parseInt(request.getParameter("classid"));
        List<Resource> resourceList=resourceMapper.getResourceByClassId(classid,0);
       HashMap res=new HashMap();
        List resresource=new ArrayList();
        for (Resource resource: resourceList) {
            HashMap map=new HashMap();
            map.put("resourceID",resource.getResourceID());
            map.put("title",resource.getTitle());
            map.put("downloadNum",resource.getDownloadNum());
            map.put("url",resource.getUrl());
            resresource.add(map);
        }
        res.put("res",resresource);
        res.put("total",resourceList.size());
        result.setData(res);
        result.setMessage("资源列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("resource")
    public  Tool getResource1(HttpServletRequest request){
        Tool result = new Tool<>();
        int classid=Integer.parseInt(request.getParameter("classid"));
        int resourceid=Integer.parseInt(request.getParameter("resourceid"));
        List<Resource> resourceList=resourceMapper.getResourceByClassId(classid,resourceid);
        HashMap map=new HashMap();
        map.put("title",resourceList.get(0).getTitle());
        map.put("description",resourceList.get(0).getDescription());
        map.put("DownloadNum",resourceList.get(0).getDownloadNum());//没有经验值
        result.setData(map);
        result.setMessage("列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @DeleteMapping("resource")
    public  Tool deleteResource(HttpServletRequest request){
        Tool result = new Tool<>();
        int resourceid=Integer.parseInt(request.getParameter("resourceid"));
        resourceMapper.deleteResource(resourceid);
        result.setMessage("删除资源成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @PutMapping("resource")
    public  Tool myAddCourse(HttpServletRequest request){
        Tool result = new Tool<>();
        int classid=Integer.parseInt(request.getParameter("classid"));
        int resourceid=Integer.parseInt(request.getParameter("id"));
        int userid = Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        String updater= userMapper.queryUserById(userid).getUserName();
        Resource resource=new Resource();
        String Title=request.getParameter("Title");
        String Description=request.getParameter("Description");
        String url=request.getParameter("url");
        int DownloadNum=Integer.parseInt(request.getParameter("DownloadNum"));

        resource.setClassID(classid);
        resource.setTitle(Title);
        resource.setDescription(Description);
        resource.setUrl(url);
        resource.setDownloadNum(DownloadNum);
        resource.setResourceID(resourceid);
        resource.setModifyby(updater);
        resource.setModifyDate(new Date());
        resourceMapper.updateResource(resource);
        result.setMessage("资源更新成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
}
