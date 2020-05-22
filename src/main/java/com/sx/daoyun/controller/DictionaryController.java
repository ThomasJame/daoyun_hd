package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.DicDatailMapper;
import com.sx.daoyun.mapper.DicMapper;
import com.sx.daoyun.pojo.Dic;
import com.sx.daoyun.pojo.DicDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class DictionaryController {

    @Autowired
    DicMapper dicMapper;
    @Autowired
    DicDatailMapper dicDatailMapper;

    @PostMapping("dictionary/list")
    public Tool searchDicList(@RequestBody Map<String, Object> searchmap) {
//        班课号、课程名称、教师，以及班课总数total
        Tool result = new Tool<>();
        String dictionaryName = (String) searchmap.get("dictionaryName");
        List<Dic> dics = dicMapper.searchlist(dictionaryName);
        List res = new ArrayList();
        for (Dic dic : dics) {
            Map recourse = new HashMap();
            recourse.put("id", dic.getDataID());
            recourse.put("dictionaryName", dic.getDataName());
            recourse.put("dictionaryDesc", dic.getDataType());
            res.add(recourse);
        }
        result.setData(res);
        result.setMessage("数据字典列表");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @GetMapping("dictionary/dataItemList/{id}")
    public Tool searchDicDetailList(@PathVariable("id") int id) {
        Tool result = new Tool<>();
        List<DicDetail> dicDetails = dicDatailMapper.searchlistbyDataId(id);
        List res = new ArrayList();
        for (DicDetail dicDetail : dicDetails) {
            Map recourse = new HashMap();
            recourse.put("id", dicDetail.getID());
            recourse.put("ItemValue", dicDetail.getItemValue());
            if (dicDetail.getIsDefault() == 0) {
                recourse.put("isDefault", 0);
            } else {
                recourse.put("isDefault", 1);
            }
            recourse.put("DataID", dicDetail.getDataID());
            res.add(recourse);
        }
        result.setData(res);
        result.setMessage("成功获取数据项信息");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @PostMapping("dictionary")
    public Tool addDic(@RequestBody Map<String, Object> addmap) {
        Tool result = new Tool<>();
        String dictionaryDesc = (String) addmap.get("dictionaryDesc");
        String dictionaryName = (String) addmap.get("dictionaryName");
        Dic dic = new Dic();
        dic.setCreateDate(new Date());
        dic.setDataName(dictionaryName);
        dic.setDataType(dictionaryDesc);
        dicMapper.addDic(dic);
        result.setMessage("新增数据类型成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @PostMapping("dictionary/item/{id}")
    public Tool addDicDetail(@PathVariable("id") int id, @RequestBody Map<String, Object> addmap) {
        Tool result = new Tool<>();
        String ItemValue = (String) addmap.get("ItemValue");
        String isDefault = (String) addmap.get("isDefault");
        DicDetail dicDetail = new DicDetail();
        dicDetail.setCreateDate(new Date());
        dicDetail.setItemValue(ItemValue);
        if (isDefault.equals("false")) {
            dicDetail.setIsDefault(0);
        } else {
            dicDetail.setIsDefault(1);
        }
        dicDetail.setDataID(id);
        dicDatailMapper.addDicDetail(dicDetail);
        result.setMessage("新增数据item成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @PutMapping("dictionary/{id}")
    public Tool updateDic(@PathVariable("id") int id, @RequestBody Map<String, Object> addmap) {
        Tool result = new Tool<>();
        String dictionaryDesc = (String) addmap.get("dictionaryDesc");
        String dictionaryName = (String) addmap.get("dictionaryName");
        Dic dic = new Dic();
        dic.setDataID(id);
        dic.setModifyDate(new Date());
        dic.setDataName(dictionaryName);
        dic.setDataType(dictionaryDesc);
        dicMapper.update(dic);
        result.setMessage("修改数据类型成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @PutMapping("dictionary/item/{id}")
    public Tool updateDicDetail(@PathVariable("id") int id, @RequestBody Map<String, Object> addmap) {
        Tool result = new Tool<>();
        String ItemValue = (String) addmap.get("ItemValue");
        String isDefault = (String) addmap.get("isDefault");
        int thisid=(int)addmap.get("id");
        DicDetail dicDetail = new DicDetail();
        dicDetail.setModifyDate(new Date());
        dicDetail.setItemValue(ItemValue);

        if (isDefault.equals("false")) {
            dicDetail.setIsDefault(0);
        } else {
            dicDetail.setIsDefault(1);
        }
        dicDetail.setDataID(id);
        dicDetail.setID(thisid);
        dicDatailMapper.updateDetail(dicDetail);
        result.setMessage("修改数据项item信息成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @GetMapping("dict/{id}")
    public Tool getdicone(@PathVariable("id") int id) {
        Tool result = new Tool<>();
        Dic dic = dicMapper.searchdictone(id);
        Map recourse = new HashMap();
        recourse.put("id", dic.getDataID());
        recourse.put("dictionaryName", dic.getDataName());
        recourse.put("dictionaryDesc", dic.getDataType());
        result.setMessage("根据数据类型id获取数据类型信息");
        result.setData(recourse);
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @GetMapping("dict/item/{id}")
    public Tool getdicdetailone(@PathVariable("id") int id) {
        Tool result = new Tool<>();
        DicDetail dicDetail = dicDatailMapper.searchone(id);
        Map recourse = new HashMap();
        recourse.put("id", dicDetail.getID());
        recourse.put("ItemValue", dicDetail.getItemValue());
        if (dicDetail.getIsDefault() == 0) {
            recourse.put("isDefault", false);
        } else {
            recourse.put("isDefault", true);
        }
        result.setMessage("成功获取item信息");
        result.setData(recourse);
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }

    @DeleteMapping("dictionary/{id}")
    public Tool deletedic(@PathVariable("id") int id) {
        Tool result = new Tool<>();
        dicMapper.deletedic(id);
        result.setMessage("删除数据类型成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
    @DeleteMapping("dictionary/item/{id}")
    public Tool deletedicdetail(@PathVariable("id") int id) {
        Tool result = new Tool<>();
        dicDatailMapper.deletedicdetail(id);
        result.setMessage("删除item信息成功");
        result.setFlag("true");
        result.setCode(2000);
        return result;
    }
}
