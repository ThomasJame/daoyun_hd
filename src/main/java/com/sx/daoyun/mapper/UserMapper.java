package com.sx.daoyun.mapper;


import com.sx.daoyun.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface UserMapper {
    List<User> queryUserList();
    User queryUserById(int id);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
    int resetPassword(String name);
    User queryUserByName(String name);
    User queryUseridByPhone(String phone);
}
