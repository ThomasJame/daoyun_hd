package com.sx.daoyun.controller;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import com.sx.daoyun.annotation.UserLoginToken;
import com.sx.daoyun.mapper.RoleMapper;
import com.sx.daoyun.mapper.Token;
import com.sx.daoyun.mapper.Transition;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.User;
import com.sx.daoyun.pojo.UserRole;
import com.sx.daoyun.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserApi {
    @Autowired
    UserMapper userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Token tokenmapper;
    @Autowired
    private Transition transition;
    @Autowired
    private UserMapper userMapper;
    //登录
    @PostMapping("/login/login")
    public Object login(@RequestBody Map<String,Object> userMap) {
        String username = (String)userMap.get("username");
        String password = (String)userMap.get("password");
        JSONObject jsonObject = new JSONObject();
        System.out.println(jsonObject);
        User userForBase = userService.queryUserByName(username);
        if (userForBase == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject;
        } else {
            if (!userForBase.getPassword().equals(password)) {
                jsonObject.put("message", "登录失败,密码错误");
                return jsonObject;
            } else {
                Date date=new Date();
                String token = tokenService.getToken(userForBase);
                jsonObject.put("code", 2000);
                jsonObject.put("flag", "true");
                jsonObject.put("message", "验证成功");
                HashMap map=new HashMap();
                map.put("token",token);
                jsonObject.put("data", map);
                redisUtil.set(token, userForBase.getId(),259200);//设置3天
                com.sx.daoyun.pojo.Token token1= new com.sx.daoyun.pojo.Token();
                token1.setToken(token);
                token1.setUserID(userForBase.getId());
                token1.setIfOverTime(1);
                token1.setLoginTime(date);
                tokenmapper.setFlase(userForBase.getId());
                tokenmapper.addToken(token1);
                return jsonObject;
            }
        }
    }

    @GetMapping("/login/{telephone}")
    public Tool getMessage(@PathVariable("telephone") String  telephone) {
        Tool result = new Tool<>();
       User user=userService.queryUseridByPhone(telephone);

        if (user == null) {
            //在用户信息表新增一条记录，默认用户名称为手机号，角色默认学生。后续可以在我的那里进行角色切换
            System.out.println("sdahhsdna");
            User user1 =new User();
            user1.setUserName(telephone);
            user1.setMobile(telephone);
            user1.setPassword("123456");
            userService.addUser(user1);
            UserRole userRole=new UserRole();
            userRole.setRoleid(2);
            userRole.setUserid(userMapper.queryUseridByPhone(telephone).getId());
            userRole.setCreater("系统自动生成");
            userRole.setCreatetime(new Date());
            transition.adduserrole(userRole);

            User usernew=userService.queryUseridByPhone(telephone);
            String token = tokenService.getToken(usernew);
            redisUtil.set(token, usernew.getId(),259200);//设置3天
            com.sx.daoyun.pojo.Token token1= new com.sx.daoyun.pojo.Token();
            token1.setToken(token);
            token1.setUserID(usernew.getId());
            token1.setIfOverTime(1);
            token1.setLoginTime(new Date());
            tokenmapper.setFlase(usernew.getId());
            tokenmapper.addToken(token1);

            HashMap map = new HashMap();
            map.put("token", token);
            result.setData(map);
            result.setMessage("默认用户名称为手机号，角色默认学生密码为123");
            result.setFlag("true");
            result.setCode(2000);
            return  result;
        } else {
            int id = user.getId();
            String token = tokenmapper.queryTokenbyUserId(id);
            result.setMessage("根据手机号获取token值");
            result.setFlag("true");
            result.setCode(2000);
            HashMap map = new HashMap();
            map.put("token", token);
            result.setData(map);
            return result;
        }
    }

    @PostMapping("login/logout/{token}")
    public Tool logout(HttpServletRequest request) {
        Tool result = new Tool<>();
        return result;
    }
}