package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.User;
import com.sx.daoyun.tool.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @PostMapping("login/pwd")  //校验密码
    public  Tool verifyPassword(@RequestBody Map<String, Object> userMap){
        Tool result = new Tool<>();
        String name = (String) userMap.get("userId");
        String password=(String) userMap.get("password");
        User user =userMapper.queryUserByName(name);
         if (password.equals(user.getPassword())){
             result.setMessage("密码正确");
             result.setFlag("true");
             result.setCode(2000);
             return  result;
         }else{
             result.setMessage("密码错误");
             result.setFlag("true");
             result.setCode(2000);
             return  result;
         }

    }

    @PutMapping("login/pwd")  //修改密码
    public  Tool updatePassword(@RequestBody Map<String, Object> userMap){
        Tool result = new Tool<>();
        String name = (String) userMap.get("userId");
        String password=(String) userMap.get("password");
        User user=userMapper.queryUserByName(name);
        user.setPassword(password);
        userMapper.updateUser(user);
        result.setMessage("修改密码成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
}
