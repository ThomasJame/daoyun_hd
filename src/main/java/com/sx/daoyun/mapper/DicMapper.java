package com.sx.daoyun.mapper;


import com.sx.daoyun.pojo.Dic;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface DicMapper {
    List<Dic> searchlist(String  dictionaryName);
    int addDic(Dic dic);
    int update(Dic dic);
    Dic searchdictone(int id);
    int deletedic(int id);
}
