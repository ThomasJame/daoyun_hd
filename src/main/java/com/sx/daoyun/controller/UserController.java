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

    @PostMapping("user/list/search/{page}/{size}") ///显示用户列表 查询的
    public Tool queryUserList(@PathVariable("page") int page,@PathVariable("size") int size,
                              @RequestBody Map<String,Object> searchmap) {
        Map<String,Object> usermap=(Map<String, Object>) searchmap.get("searchMap");
        String username=(String)usermap.get("userAccount");
        String rolename=(String)usermap.get("roleType");
        String nickname=(String)usermap.get("nickname");
        int begin=(page-1)*size;
        int end=page*size-1;
        Tool result = new Tool<>();
        result.setMessage("用户管理列表查询成功");
        result.setFlag("true");
        result.setCode(2000);
        List<User> userList = userMapper.searmap(username,nickname,rolename);
        int count=userList.size();
        List<User> usersList2=new ArrayList<>();
        if (end>=count-1){
             usersList2=userList.subList(begin,count);
//            usersList2=userList.subList(0,2); 0,1两个
            System.out.println("1");
        }else {
             usersList2=userList.subList(begin,end+1);
            System.out.println("2");
        }
        List users = new ArrayList<>();///最终得到的一个个放入
        for (User user : usersList2) {
            List<String> rolenames = new ArrayList<>();
            int userId = user.getId();
            List<UserRole> userRoles = transition.getRoleIdByUserId(userId);//拿到用户ID 去查询 用户对应的角色ID
            for (UserRole userRole : userRoles) {
                int roleid = userRole.getRoleid();
                List<Role> roleList = roleMapper.queryRoleNamebyRoleId(roleid);///用角色ID 查询 用户的角色
                for (Role role : roleList) {
                    ///一层只查询一种角色
                    rolenames.add(role.getRolename());
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
        HashMap res=new HashMap();
        res.put("total",count);
        res.put("rows",users);
        result.setData(res);
        return result;
    }

    @PostMapping("/user") //添加用户
    public Tool addUser(@RequestBody Map<String, Object> rolemap) {
        Tool result = new Tool<>();
//        userAccount nickname roleType
        String name=(String) rolemap.get("nickname");
        String roleType=(String) rolemap.get("roleType");
        String userAccount=(String) rolemap.get("userAccount");
        System.out.println(name);
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
         User user = new User();
         user.setNickName(name);
         user.setUserName(userAccount);
         user.setPassword("123456");
         userMapper.addUser(user);
         result.setMessage("新增用户成功");
        //先根据角色名查询角色id  将角色id和新增用户id 加入到userrole标中
        Role role=roleMapper.queryRoleidByname(roleType);
        int roleid=role.getId();
        User user1=userMapper.queryUserByName(userAccount);
        int userid=user1.getId();
        UserRole userRole=new UserRole();
        userRole.setUserid(userid);
        userRole.setRoleid(roleid);
        transition.adduserrole(userRole);
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

//    @UserLoginToken
    @DeleteMapping("user/{id}")//删除用户
    public Tool deleteUser(@PathVariable int id) {
        Tool result = new Tool<>();
        userMapper.deleteUser(id);
        result.setMessage("删除成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
//
//    @UserLoginToken
    @PutMapping("user/{id}")//修改用户
    @Transactional
    public Tool updateUser(@PathVariable int id,@RequestBody Map<String,Object> userMap) {
        Tool result = new Tool<>();
        String Username = (String) userMap.get("userAccount");
        String NickName = (String)userMap.get("nickname");
        String role = (String)userMap.get("roleType");
        User user=new User();
        user.setUserName(Username);
        user.setNickName(NickName);
        user.setId(id);
        userMapper.updateUser(user);
        transition.deleteuserrole(id);
       Role role1= roleMapper.queryRoleidByname(role);
       UserRole userRole=new UserRole();
       userRole.setRoleid(role1.getId());
       userRole.setUserid(id);
       transition.adduserrole(userRole);
            result.setFlag("true");
            result.setCode(2000);
            return  result;

    }

    @GetMapping("user/{id}")//根据ID查询用户信息
    public Tool searchUserbyId(@PathVariable int id) {
    Tool result = new Tool<>();
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
            rolenames.add(role.getRolename());
        }
    }

    Map resuser = new HashMap();
    resuser.put("roleType", rolenames);
    resuser.put("id", userId);
    resuser.put("nickname",user.getNickName());
    resuser.put("userAccount", user.getUserName());
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
        resuser.put("roles", rolenames);
        resuser.put("id", userId);
        resuser.put("name", user.getUserName());
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

    @GetMapping("test")
    public Tool test(@RequestBody Map<String,Object> userMap) {
        Tool result = new Tool<>();
        String username=(String) userMap.get("username");
        String nickname=(String) userMap.get("nickname");
        String rolename=(String) userMap.get("rolename");
       List<User> userList=userMapper.searmap(username,nickname,rolename);
       result.setData(userList);
        return result;
    }
}
