package com.sx.daoyun.controller;


import com.sx.daoyun.annotation.UserLoginToken;
import com.sx.daoyun.mapper.RoleMapper;
import com.sx.daoyun.mapper.Transition;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Role;
import com.sx.daoyun.pojo.User;
import com.sx.daoyun.pojo.UserRole;
import com.sx.daoyun.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private Transition transition;
    @Autowired
    private RedisUtil redisUtil;
//    @UserLoginToken
@GetMapping("user/list") ///显示用户列表 查询的
public Tool showlist(HttpServletRequest request) {
    Tool result = new Tool<>();
    result.setMessage("用户管理列表查询成功");
    result.setFlag("true");
    result.setCode(200);
    List<User> userList = userMapper.queryUserList();
    int count=userList.size();
    List<String> rolenames = new ArrayList<>();
    List users = new ArrayList<>();///最终得到的一个个放入
    for (User user : userList) {
        int userId = user.getId();
        List<UserRole> userRoles = transition.getRoleIdByUserId(userId);//拿到用户ID 去查询 用户对应的角色ID
        for (UserRole userRole : userRoles) {
            int roleid = userRole.getRoleid();
            List<Role> roleList = roleMapper.queryRoleNamebyRoleId(roleid);///用角色ID 查询 用户的角色
            for (Role role : roleList) {
                ///一层只查询一种角色
                rolenames.add(role.getName());
            }
        }
        ///得到用户角色数组 rolenames
        ///resuser 代表 返回中的data
        Map resuser = new HashMap();
        resuser.put("roleType", rolenames);
        resuser.put("id", userId);
        resuser.put("userAccount", user.getUserName());
        resuser.put("nickname", user.getNickName());

        users.add(resuser);
        ///获取用户对应角色 加入到roleType中

    }
    result.setData(users);
    return result;
}

    @GetMapping("user/list/search") ///显示用户列表 查询的
    public Tool queryUserList(HttpServletRequest request) {
        int page=Integer.parseInt(request.getParameter("page"));
        int size=Integer.parseInt(request.getParameter("size"));
        int begin=(page-1)*size+1;
        int end=page*size;
        Tool result = new Tool<>();
        result.setMessage("用户管理列表查询成功");
        result.setFlag("true");
        result.setCode(200);
        List<User> userList = userMapper.queryUserList();
        int count=userList.size();
        List<User> usersList2=new ArrayList<>();
        if (end>=count-1){
             usersList2=userList.subList(begin-1,count);
        }else {
             usersList2=userList.subList(begin-1,end);
        }
        List<String> rolenames = new ArrayList<>();
        List users = new ArrayList<>();///最终得到的一个个放入
        for (User user : usersList2) {
            int userId = user.getId();
            List<UserRole> userRoles = transition.getRoleIdByUserId(userId);//拿到用户ID 去查询 用户对应的角色ID
            for (UserRole userRole : userRoles) {
                int roleid = userRole.getRoleid();
                List<Role> roleList = roleMapper.queryRoleNamebyRoleId(roleid);///用角色ID 查询 用户的角色
                for (Role role : roleList) {
                    ///一层只查询一种角色
                    rolenames.add(role.getName());
                }
            }
            ///得到用户角色数组 rolenames
            ///resuser 代表 返回中的data
            Map resuser = new HashMap();
            resuser.put("roleType", rolenames);
            resuser.put("id", userId);
            resuser.put("userAccount", user.getUserName());
            resuser.put("nickname", user.getNickName());

            users.add(resuser);
            ///获取用户对应角色 加入到roleType中

        }
        result.setData(users);
        return result;
    }
//
//    @UserLoginToken
    @PostMapping("user") //添加用户
    public Tool addUser(HttpServletRequest request) {
        Tool result = new Tool<>();
//        userAccount nickname roleType
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat();
        String Username = request.getParameter("Username");
        String NickName = request.getParameter("NickName");
        String Sex = request.getParameter("Sex");
        int Number =Integer.parseInt(request.getParameter("Number"));
        String Mobile = request.getParameter("Mobile");
        String Email = request.getParameter("Email");
        String Password = request.getParameter("Password");
        String School = request.getParameter("School");
        String Department = request.getParameter("Department");
        String Photo = request.getParameter("Photo");
        int id=Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        User createrUser=userMapper.queryUserById(id);
        String creater=createrUser.getUserName();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        User user = new User();
        try {
            result.setMessage("新增用户成功");
            user.setBirthday(simpleDateFormat.parse(request.getParameter("Birthday")));
            user.setCreateby(creater);
            user.setCreateDate(new Date());
            user.setUserName(Username);
            user.setNickName(NickName);
            user.setSex(Sex);
            user.setNumber(Number);
            user.setMobile(Mobile);
            user.setEmail(Email);
            user.setPassword(Password);
            user.setSchool(School);
            user.setDepartment(Department);
            user.setPhoto(Photo);
            userMapper.addUser(user);
            result.setFlag("true");
            result.setCode(2000);
            return result;
        }catch (Exception e){
            result.setMessage("生日格式错误");
            result.setFlag("flase");
            result.setErrCode(200);
            return  result;
        }

    }

//    @UserLoginToken
    @DeleteMapping("user")//删除用户
    public Tool deleteUser(HttpServletRequest request) {
        Tool result = new Tool<>();
        String id = request.getParameter("id");
        userMapper.deleteUser(Integer.parseInt(id));
        result.setMessage("删除成功");
        result.setFlag("true");
        result.setCode(1200);
        return result;
    }
//
//    @UserLoginToken
    @PutMapping("user")//修改用户
    @Transactional
    public Tool updateUser(HttpServletRequest request) {
        Tool result = new Tool<>();
        int id = Integer.parseInt(request.getParameter("id"));

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat();
        String Username = request.getParameter("Username");
        String NickName = request.getParameter("NickName");
        String Sex = request.getParameter("Sex");
        int Number =Integer.parseInt(request.getParameter("Number"));
        String Mobile = request.getParameter("Mobile");
        String Email = request.getParameter("Email");
        String Password = request.getParameter("Password");
        String School = request.getParameter("School");
        String Department = request.getParameter("Department");
        String Photo = request.getParameter("Photo");
        int updaterid=Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        User updateruser=userMapper.queryUserById(updaterid);
        String Modifyby=updateruser.getUserName();
        System.out.println(Modifyby);
        User user = new User();
        user.setId(id);
//        user.setCreatetime(createtime);
//        user.setCreater(creater);
        try {
            result.setMessage("修改用户成功");
            user.setBirthday(simpleDateFormat.parse(request.getParameter("Birthday")));
            user.setModifyby(Modifyby);
            user.setModifyDate(new Date());
            user.setUserName(Username);
            user.setNickName(NickName);
            user.setSex(Sex);
            user.setNumber(Number);
            user.setMobile(Mobile);
            user.setEmail(Email);
            user.setPassword(Password);
            user.setSchool(School);
            user.setDepartment(Department);
            user.setPhoto(Photo);
            userMapper.updateUser(user);
            result.setFlag("true");
            result.setCode(2000);
            return result;
        }catch (Exception e){
            result.setMessage("生日格式错误");
            user.setModifyby(Modifyby);
            user.setModifyDate(new Date());
            user.setUserName(Username);
            user.setNickName(NickName);
            user.setSex(Sex);
            user.setNumber(Number);
            user.setMobile(Mobile);
            user.setEmail(Email);
            user.setPassword(Password);
            user.setSchool(School);
            user.setDepartment(Department);
            user.setPhoto(Photo);
            userMapper.updateUser(user);
            result.setFlag("flase");
            result.setCode(200);
            return  result;
        }

    }

    @GetMapping("user")//根据ID查询用户信息
    public Tool searchUserbyId(HttpServletRequest request) {
    Tool result = new Tool<>();
    int id=Integer.parseInt(request.getParameter("id"));
    result.setMessage("成功获取用户信息");
    result.setFlag("true");
    result.setCode(2000);
    User user = userMapper.queryUserById(id);
    int userId = user.getId();
    List<String> rolenames = new ArrayList<>();
    List<UserRole> userRoles = transition.getRoleIdByUserId(userId);
    for (UserRole userRole : userRoles) {
        int roleid = userRole.getRoleid();
        List<Role> roleList = roleMapper.queryRoleNamebyRoleId(roleid);///用角色ID 查询 用户的角色
        for (Role role : roleList) {
            ///一层只查询一种角色
            rolenames.add(role.getName());
        }
    }

    Map resuser = new HashMap();
    resuser.put("roles", rolenames);
    resuser.put("id", userId);
    resuser.put("name", user.getUserName());
    result.setData(resuser);
    return result;
}

    @GetMapping("login/info/{token}")//根据token 返回用户信息
    public Tool searchUser(@PathVariable String token) {
        Tool result = new Tool<>();
        int id=Integer.parseInt(String.valueOf(redisUtil.get(token)));
        result.setMessage("成功获取用户信息");
        result.setFlag("true");
        result.setCode(2000);
        User user = userMapper.queryUserById(id);
        int userId = user.getId();
        List<String> rolenames = new ArrayList<>();
        List<UserRole> userRoles = transition.getRoleIdByUserId(userId);
        for (UserRole userRole : userRoles) {
            int roleid = userRole.getRoleid();
            List<Role> roleList = roleMapper.queryRoleNamebyRoleId(roleid);///用角色ID 查询 用户的角色
            for (Role role : roleList) {
                ///一层只查询一种角色
                rolenames.add(role.getName());
            }
        }

        Map resuser = new HashMap();
        resuser.put("Roles", rolenames);
        resuser.put("ID", userId);
        resuser.put("UserName", user.getUserName());
        result.setData(resuser);
        return result;
    }

//    @UserLoginToken
    @PutMapping("user/pwd")
    public Tool resetPassword(HttpServletRequest request) {
        Tool result = new Tool<>();
        String username=request.getParameter("username");
        userMapper.resetPassword(username);
        result.setMessage("密码已重置:初始密码为123456");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
}
