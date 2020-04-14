package com.sx.daoyun.controller;



import com.sx.daoyun.mapper.RoleMapper;
import com.sx.daoyun.mapper.Transition;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Role;
import com.sx.daoyun.pojo.User;
import com.sx.daoyun.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private Transition transition;


    @GetMapping("user/list") ///显示用户列表
    public Tool  queryUserList(){
        Tool result = new Tool<>();
        result.setMessage("用户管理列表查询成功");
        result.setFlag("true");
        result.setCode(200);
        List<User> userList=userMapper.queryUserList();
        List<String> rolenames = new ArrayList<>();
        List users=new ArrayList<>();///最终得到的一个个放入
        for (User user:userList) {
            int userId=user.getId();
            List<UserRole> userRoles=transition.getRoleIdByUserId(userId);//拿到用户ID 去查询 用户对应的角色ID
            for (UserRole userRole:userRoles) {
               int roleid=userRole.getRoleid();
                List<Role> roleList=roleMapper.queryRoleNamebyRoleId(roleid);///用角色ID 查询 用户的角色
                for (Role role:roleList) {
                    ///一层只查询一种角色
                    rolenames.add(role.getName());
                }
            }
            ///得到用户角色数组 rolenames
            ///resuser 代表 返回中的data
            Map resuser=new HashMap();
            resuser.put("roleType",rolenames);
            resuser.put("id",userId);
            resuser.put("userAccount",user.getUserAccount());
            resuser.put("nickname",user.getNickname());

            users.add(resuser);
            ///获取用户对应角色 加入到roleType中

        }
       result.setData(users);
        return result;
    }

    @PostMapping("user") //添加用户
    public Tool  addUser(HttpServletRequest request){
        Tool result = new Tool<>();
        String userAccount=request.getParameter("userAccount");
        String sex=request.getParameter("sex");
        String  nickname=request.getParameter("nickname");
        String phone=request.getParameter("phone");
        String academy=request.getParameter("academy");
        String password=request.getParameter("password");
        String schoolid=request.getParameter("schoolid");//表示学号 或者教师工号
        ///当前操作用户
        String creater="郭星宇";
        Date date=new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        User user=new User();
        user.setAcademy(academy);
        user.setUserAccount(userAccount);
        user.setSex(sex);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setPassword(password);
        user.setSchoolid(schoolid);
        user.setCreater(creater);
        user.setCreatetime(date);
        userMapper.addUser(user);
        result.setMessage("新增用户成功");
        result.setFlag("true");
        result.setCode(200);
        return result;
    }

    @DeleteMapping("user")//删除用户
    public Tool deleteUser(HttpServletRequest request){
        Tool result = new Tool<>();
        String id=request.getParameter("id");
        userMapper.deleteUser(Integer.parseInt(id));
        result.setMessage("删除成功");
        result.setFlag("true");
        result.setCode(1200);
        return result;
    }

    @PutMapping("user")//修改用户
    @Transactional
    public Tool  updateUser(HttpServletRequest request){
        Tool result = new Tool<>();
        int id=Integer.parseInt(request.getParameter("id"));
//        User olduser=userMapper.queryUserById(Integer.parseInt(request.getParameter("id")));
//        String creater=olduser.getCreater();
//        Date createtime=olduser.getCreatetime();
        String userAccount=request.getParameter("userAccount");
        String sex=request.getParameter("sex");
        String  nickname=request.getParameter("nickname");
        String phone=request.getParameter("phone");
        String academy=request.getParameter("academy");
        String password=request.getParameter("password");
        String schoolid=request.getParameter("schoolid");
        ///当前操作用户
        String updater="郭星宇2";
        Date date=new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        User user=new User();
        user.setId(id);
//        user.setCreatetime(createtime);
//        user.setCreater(creater);
        user.setAcademy(academy);
        user.setUserAccount(userAccount);
        user.setSex(sex);
        user.setNickname(nickname);
        user.setPhone(phone);
        user.setPassword(password);
        user.setUpdater(updater);
        user.setUpdatetime(date);
        user.setSchoolid(schoolid);
        userMapper.updateUser(user);
        result.setMessage("更新用户成功");
        result.setFlag("true");
        result.setCode(200);
        return result;
    }

    @GetMapping("user")//根据id查询用户
    public Tool searchUser(HttpServletRequest request){
        Tool result = new Tool<>();
        result.setMessage("成功获取用户信息");
        result.setFlag("true");
        result.setCode(2000);
        String id=request.getParameter("id");
        User user=userMapper.queryUserById(Integer.parseInt(id));
        int userId=user.getId();
        List<String> rolenames = new ArrayList<>();
        List<UserRole> userRoles=transition.getRoleIdByUserId(userId);
        for (UserRole userRole:userRoles) {
            int roleid=userRole.getRoleid();
            List<Role> roleList=roleMapper.queryRoleNamebyRoleId(roleid);///用角色ID 查询 用户的角色
            for (Role role:roleList) {
                ///一层只查询一种角色
                rolenames.add(role.getName());
            }
        }

        Map resuser=new HashMap();
        resuser.put("roleType",rolenames);
        resuser.put("id",userId);
        resuser.put("userAccount",user.getUserAccount());
        resuser.put("nickname",user.getNickname());
        result.setData(resuser);
        return  result;
    }

    @PutMapping("user/pwd")
    public  Tool resetPassword(HttpServletRequest request){
        Tool result = new Tool<>();
        int id= Integer.parseInt(request.getParameter("id"));
        userMapper.resetPassword(id);
        result.setMessage("密码已重置:初始密码为123456");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
}
