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
        roleList=roleMapper.queryRoleList(null);
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

    @GetMapping("role/{id}")
    public Tool  queryRoleById(@PathVariable("id") int id){
        Tool result = new Tool<>();
        Role role=roleMapper.queryRoleById(id);
        Map res=new HashMap();
        res.put("id",role.getId());
        res.put("name",role.getRolename());
        result.setData(res);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色查询成功");
        return result;
    }

    @PostMapping("role")
    public Tool  addRole(@RequestBody Map<String,Object> rolemap){
        Tool result = new Tool<>();
        String roletype=(String)rolemap.get("name");
        Role role=new Role();
        role.setRolename(roletype);
        roleMapper.addRole(role);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色添加成功");
        return result;
    }

    @DeleteMapping("role/{id}")
    public Tool  DeleteRole(@PathVariable("id") int id){
        Tool result = new Tool<>();
        roleMapper.deleteRole(id);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色删除成功");
        return result;
    }

    @PutMapping("role/{id}")
    public Tool  updateRole(@RequestBody Map<String,Object> rolemap,@PathVariable("id") int id){
        Tool result = new Tool<>();
        String roletype=(String)rolemap.get("name");
        Role role=new Role();
        role.setId(id);
        role.setRolename(roletype);
        roleMapper.updateRole(role);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色修改成功");
        return result;
    }


    @PostMapping("role/list/search/{page}/{size}")
    public Tool  searchrolebypo(@PathVariable("page") int page,@PathVariable("size") int size,
            @RequestBody Map<String,Object> searchmap){
        Tool result = new Tool<>();
        Map<String,Object> rolemap=(Map<String, Object>) searchmap.get("searchMap");
        String roletype=(String)rolemap.get("roleType");
        int begin=(page-1)*size;
        int end=page*size-1;
        List<Role> roleList=roleMapper.queryRoleList(roletype);
        int count=roleList.size();
        List<Role> roleList1=new ArrayList<>();
        if (end>=count-1){
            roleList1=roleList.subList(begin,count);
        }else {
            roleList1=roleList.subList(begin,end+1);
        }
        List resroles=new ArrayList();
        for (Role role:roleList1
        ) {
            Map resrole=new HashMap();
            resrole.put("id",role.getId());
            resrole.put("name",role.getRolename());
            resroles.add(resrole);
        }
        HashMap res=new HashMap();
        res.put("rows",resroles);
        res.put("total",count);
        result.setData(res);
        result.setCode(2000);
        result.setFlag("true");
        result.setMessage("角色分页查询成功");
        return result;
    }
}
