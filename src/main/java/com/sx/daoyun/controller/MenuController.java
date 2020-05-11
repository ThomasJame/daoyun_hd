package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.MenuMapper;
import com.sx.daoyun.mapper.UserCourseMapper;
import com.sx.daoyun.mapper.UserMapper;
import com.sx.daoyun.pojo.Menu;
import com.sx.daoyun.tool.RedisUtil;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class MenuController {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("menu")
    public Tool addMenu(HttpServletRequest request) {
        Tool result = new Tool<>();
        String MenuName = request.getParameter("MenuName");
        String Icon = request.getParameter("Icon");
        String url = request.getParameter("url");
        int ParentMenuID = Integer.parseInt(request.getParameter("ParentMenuID"));
        String isPage = request.getParameter("isPage");
        String order = request.getParameter("Order");
        int id = Integer.parseInt(String.valueOf(redisUtil.get(request.getHeader("token"))));
        String Createby = userMapper.queryUserById(id).getUserName();
        Menu menu=new Menu();
        menu.setCreateby(Createby);
        menu.setCreateDate(new Date());
        menu.setMenuName(MenuName);
        menu.setIcon(Icon);
        menu.setUrl(url);
        menu.setParentMenuID(ParentMenuID);
        menu.setIsPage(isPage);
        menu.setOrder(order);
        menuMapper.addMenu(menu);
        result.setMessage("添加菜单成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
}
