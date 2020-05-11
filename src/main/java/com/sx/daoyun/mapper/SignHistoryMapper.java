package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.SignHistory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface SignHistoryMapper {
    int addSignHistory(SignHistory signHistory);
    List<SignHistory> qiaodaoHistory(int userid,int classid);
}
