package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.SignRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface SignRecordMapper {
    int addRecord(SignRecord signRecord);
    SignRecord getQianDaoCode(int id);
    SignRecord getQianDaoRecord(int id);
    ArrayList<SignRecord> getlist();
}
