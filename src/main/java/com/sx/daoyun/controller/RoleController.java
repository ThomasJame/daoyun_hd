package com.sx.daoyun.controller;

import com.sx.daoyun.annotation.UserLoginToken;
import com.sx.daoyun.mapper.RoleMapper;
import com.sx.daoyun.pojo.Role;
import com.sx.daoyun.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class RoleController {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("role2/list") ///显示用户列表
    public Tool  queryRoleList(){
        Tool result = new Tool<>();
        List<Role> roleList=new ArrayList<>();
        roleList=roleMapper.queryRoleList();
        List resroles=new ArrayList();
        for (Role role:roleList
             ) {
            Map resrole=new HashMap();
            resrole.put("id",role.getId());
            resrole.put("name",role.getRolename());
            resroles.add(resrole);
        }
        result.setData(resroles);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色列表查询成功");
        return result;
    }

    @GetMapping("role")
    public Tool  queryRoleById(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("id"));
        Role role=roleMapper.queryRoleById(id);
        Map res=new HashMap();
        res.put("id",role.getId());
        res.put("name",role.getName());
        result.setData(res);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色查询成功");
        return result;
    }

    @PostMapping("role")
    public Tool  addRole(HttpServletRequest request){
        Tool result = new Tool<>();
        Role role=new Role();
        String name=request.getParameter("name");
        String creater=String.valueOf(redisUtil.get(request.getHeader("token")));
        Date date=new Date();
        role.setName(name);
        role.setCreater(creater);
        role.setCreatetime(date);
        roleMapper.addRole(role);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色添加成功");
        return result;
    }

    @DeleteMapping("role")
    public Tool  DeleteRole(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("id"));
        roleMapper.deleteRole(id);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色删除成功");
        return result;
    }

    @UserLoginToken
    @PutMapping("role")
    public Tool  updateRole(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("id"));
        Role role=new Role();
        role.setId(id);
        String name=request.getParameter("name");
        String updater=String.valueOf(redisUtil.get(request.getHeader("token")));
        Date date=new Date();
        role.setName(name);
        role.setUpdater(updater);
        role.setUpdatetime(date);
        roleMapper.updateRole(role);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色修改成功");
        return result;
    }


    @GetMapping("role/list/search")
    public Tool  searchrolebypo(HttpServletRequest request){
        Tool result = new Tool<>();
        int page=Integer.parseInt(request.getParameter("page"));
        int size=Integer.parseInt(request.getParameter("size"));
        int begin=(page-1)*size;
        int end=page*size;
        List<Role> roleList=roleMapper.queryRoleList();
        int count=roleList.size();
        List<Role> roleList1=new ArrayList<>();
        if (end>=count-1){
            roleList1=roleList.subList(begin,count-1);
        }else {
            roleList1=roleList.subList(begin,end);
        }
        List resroles=new ArrayList();
        for (Role role:roleList1
        ) {
            Map resrole=new HashMap();
            resrole.put("id",role.getId());
            resrole.put("name",role.getName());
            resroles.add(resrole);
        }
        result.setData(resroles);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色分页查询成功");
        return result;
    }
}
