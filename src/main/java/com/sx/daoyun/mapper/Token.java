package com.sx.daoyun.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface Token {
    int updateToken(com.sx.daoyun.pojo.Token token);
    int addToken(com.sx.daoyun.pojo.Token token);
    String queryTokenbyUserId(int userid);
    int setFlase(int Userid);
}
