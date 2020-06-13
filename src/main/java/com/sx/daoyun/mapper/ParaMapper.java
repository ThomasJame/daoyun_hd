package com.sx.daoyun.mapper;

import com.sx.daoyun.pojo.Para;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper

@Repository///DAO层 使用的 也可以用com
public interface ParaMapper {
    int addpara(Para para);
    int updatePara(Para para);
    int deletePara(int id);
    Para  queryPara(int id);
    ArrayList<Para> queryParaList();
}
