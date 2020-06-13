package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.DicDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface DicDatailMapper {
    List<DicDetail> searchlistbyDataId(int id);
    int addDicDetail(DicDetail dicDetail);
    int updateDetail(DicDetail dicDetail);
    DicDetail searchone(int id);
    int deletedicdetail(int id);
}
