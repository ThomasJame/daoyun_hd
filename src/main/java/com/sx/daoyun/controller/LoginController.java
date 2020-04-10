package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    @Autowired
    private UserMapper userMapper;

    @PostMapping("login/pwd")  //校验密码
    public  Tool verifyPassword(HttpServletRequest request){
        Tool result = new Tool<>();
        int id= Integer.parseInt(request.getParameter("id"));
        String password=request.getParameter("password");
        User user =userMapper.queryUserById(id);
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
    public  Tool updatePassword(HttpServletRequest request){
        Tool result = new Tool<>();
        String password=request.getParameter("password");
        int id= Integer.parseInt(request.getParameter("id"));
        User user=userMapper.queryUserById(id);
        user.setPassword(password);
        userMapper.updateUser(user);
        result.setMessage("修改密码成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
}
