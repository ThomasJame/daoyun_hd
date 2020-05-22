package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.RoleMapper;
import com.sx.daoyun.mapper.Transition;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Role;
import com.sx.daoyun.pojo.User;
import com.sx.daoyun.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class MyController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private Transition transition;
    @GetMapping("my/{userId}")//app根据用户ID获取到我的页面的用户相关信息
    public Tool searchUser(@PathVariable("userId") int userId) {
        Tool result = new Tool<>();
       User user= userMapper.queryUserById(userId);
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

    @GetMapping("my/myinfo/{userId}")//app的myinfo页根据用户ID获取用户信
    public Tool myinfo(@PathVariable("userId") int userId) {
        Tool result = new Tool<>();
        User user= userMapper.queryUserById(userId);
        System.out.println(user);
        List<String> rolenames = new ArrayList<>();
        List<UserRole> userRoles = transition.getRoleIdByUserId(userId);//拿到用户ID 去查询 用户对应的角色ID
        System.out.println(userId);
        for (UserRole userRole : userRoles) {
            int roleid = userRole.getRoleid();
            List<Role> roleList = roleMapper.queryRoleNamebyRoleId(roleid);///用角色ID 查询 用户的角色
            System.out.println(roleList);
            for (Role role : roleList) {
                ///一层只查询一种角色
                rolenames.add(role.getRolename());
            }
        }
        HashMap map=new HashMap();
        map.put("username",user.getUserName());
        map.put("mobile",user.getMobile());
        map.put("nickName",user.getNickName());
        map.put("birthday",user.getBirthday());
        map.put("sex",user.getSex());
        map.put("school",user.getSchool());
        map.put("department",user.getDepartment());
        map.put("userRole",rolenames.get(0));
        map.put("number",user.getNumber());
        result.setData(map);
        result.setMessage("app根据用户ID获取到我的页面的用户相关信息");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @PutMapping("my/myinfo/{userId}")
    public Tool myinfoupdate(@PathVariable("userId") int userId,
                             @RequestBody Map<String,Object> myinfos) {
        Tool result = new Tool<>();
        Map<String,Object> myinfo=(Map<String,Object>)myinfos.get("myInfo");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat();
        result.setMessage("myinfo页面点击保存更新页面");
        User user= userMapper.queryUserById(userId);
        user.setUserName((String) myinfo.get("username"));
        user.setNickName((String)myinfo.get("nickName"));
        user.setMobile((String)myinfo.get("mobile"));
        try {
            user.setBirthday((Date) myinfo.get("birthday"));
        }catch (Exception e){
            result.setMessage("生日格式错误");
        }
        user.setSex((String)myinfo.get("sex"));
        user.setSchool((String)myinfo.get("school"));
        user.setDepartment((String)myinfo.get("department"));
        user.setNumber(Integer.parseInt((String) myinfo.get("number")));
        String rolename=(String)myinfo.get("userRole");
        Role role=roleMapper.queryRoleidByname(rolename);
        int id=role.getId();
        UserRole userRole=new UserRole();
        int userroleid=transition.queryUserRoleId(userId);//原来的id
        userRole.setUserid(userId);
        userRole.setRoleid(id);
        userRole.setId(userroleid);
        transition.updateuserrole(userRole);
        userMapper.updateUser(user);
        ///这里的角色 信息修改没完成
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("my/jinyanzhi/{userId}")
    public Tool jinyanzhi(@PathVariable("userId") int userId) {
        Tool result = new Tool<>();
        User user= userMapper.queryUserById(userId);
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
