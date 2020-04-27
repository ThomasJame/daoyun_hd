package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.Role;
import com.sx.daoyun.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface Transition {
    List<UserRole> getRoleIdByUserId(int id);
    int adduserrole(UserRole userRole);
}
