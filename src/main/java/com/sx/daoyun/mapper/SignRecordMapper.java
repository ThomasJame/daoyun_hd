package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.SignRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface SignRecordMapper {
    int addRecord(SignRecord signRecord);
    int getQianDaoCode(int id);
}
