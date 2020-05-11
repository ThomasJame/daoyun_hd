package com.sx.daoyun.controller;

import com.sx.daoyun.annotation.UserLoginToken;
import com.sx.daoyun.mapper.RoleMapper;
import com.sx.daoyun.mapper.Transition;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Role;
import com.sx.daoyun.pojo.User;
import com.sx.daoyun.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class MyController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private Transition transition;
    @UserLoginToken
    @GetMapping("my")//app根据用户ID获取到我的页面的用户相关信息
    public Tool searchUser(HttpServletRequest request) {
        Tool result = new Tool<>();
        int id= Integer.parseInt(request.getParameter("id"));
       User user= userMapper.queryUserById(id);
        HashMap map=new HashMap();
        map.put("username",user.getUserName());
        map.put("mobile",user.getMobile());
        map.put("exp",user.getExp());
        map.put("charmValue",user.getCharmValue());
        map.put("lanDouNum",user.getLanDouNum());
        map.put("xinyi",user.getXinYi());
        result.setData(map);
        result.setMessage("app根据用户ID获取到我的页面的用户相关信息");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("my/myinfo")//app的myinfo页根据用户ID获取用户信
    public Tool myinfo(HttpServletRequest request) {
        Tool result = new Tool<>();
        int id= Integer.parseInt(request.getParameter("id"));
        User user= userMapper.queryUserById(id);
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

        HashMap map=new HashMap();
        map.put("username",user.getUserName());
        map.put("mobile",user.getMobile());
        map.put("nickName",user.getExp());
        map.put("birthday",user.getCharmValue());
        map.put("sex",user.getLanDouNum());
        map.put("school",user.getXinYi());
        map.put("department",user.getDepartment());
        map.put("userRole",rolenames);
        map.put("number",user.getNumber());
        result.setData(map);
        result.setMessage("app根据用户ID获取到我的页面的用户相关信息");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @PutMapping("my/myinfo")
    public Tool myinfoupdate(HttpServletRequest request) {
        Tool result = new Tool<>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat();
        result.setMessage("myinfo页面点击保存更新页面");
        int id= Integer.parseInt(request.getParameter("id"));
        User user= userMapper.queryUserById(id);
        user.setUserName(request.getParameter("username"));
        user.setNickName(request.getParameter("nickName"));
        user.setMobile(request.getParameter("mobile"));
        try {
            user.setBirthday(simpleDateFormat.parse(request.getParameter("birthday")));
        }catch (Exception e){
            result.setMessage("生日格式错误");
        }
        user.setSex(request.getParameter("sex"));
        user.setSchool(request.getParameter("school"));
        user.setDepartment(request.getParameter("department"));
        user.setNumber(Integer.parseInt(request.getParameter("number")));
        userMapper.updateUser(user);
        ///这里的角色 信息修改没完成
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("my/jinyanzhi")
    public Tool jinyanzhi(HttpServletRequest request) {
        Tool result = new Tool<>();
        int id= Integer.parseInt(request.getParameter("id"));
        User user= userMapper.queryUserById(id);
        HashMap map=new HashMap();
        map.put("username",user.getUserName());
        map.put("exp",user.getExp());
        map.put("JoinClassExp",user.getJoinClassExp());
        map.put("QianDaoExp",user.getQianDaoExp());
        map.put("ResourceExp",user.getResourceExp());
        map.put("taskExp",user.getTaskExp());
        map.put("VideoExp",user.getVideoExp());
        map.put("TimeExp",user.getTimeExp());
        map.put("DiscussExp",user.getDiscussExp());
        map.put("DianZanExp",user.getDianZanExp());
        result.setData(map);
        result.setMessage("经验值页面获得相关信息");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
}
