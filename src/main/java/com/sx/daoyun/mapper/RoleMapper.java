package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface RoleMapper {
    List <Role> queryRoleNamebyRoleId(int id);
    List <Role> queryRoleList(String roletype);
    Role queryRoleById(int id);
    Role queryRoleidByname(String name);
    int addRole(Role role);
    int updateRole(Role role);
    int deleteRole(int id);
}
