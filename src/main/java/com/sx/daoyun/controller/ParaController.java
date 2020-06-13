package com.sx.daoyun.controller;

import com.sx.daoyun.mapper.ParaMapper;
import com.sx.daoyun.pojo.Para;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ParaController {
    @Autowired
    private ParaMapper paraMapper;

    @PostMapping("systemPara") //增加课程
    public Tool addPara(@RequestBody Map<String,Object> Paramap){
        Tool result = new Tool<>();
        String description=(String)Paramap.get("description");
        int paraValue=Integer.parseInt((String)Paramap.get("paraValue"));
        String paraName=(String)Paramap.get("paraName");
        Para para=new Para();
        para.setDescription(description);
        para.setParaName(paraName);
        para.setParaValue(paraValue);
        System.out.println(para);
        paraMapper.addpara(para);
        result.setMessage("新增系统参数成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @PutMapping("systemPara/{id}")
    public Tool updatePara(@RequestBody Map<String,Object> Paramap, @PathVariable("id") int id){
        Tool result = new Tool<>();
        String description=(String)Paramap.get("description");
        int paraValue=Integer.parseInt((String)Paramap.get("paraValue"));
        String paraName=(String)Paramap.get("paraName");
        Para para=new Para();
        para.setId(id);
        para.setDescription(description);
        para.setParaName(paraName);
        para.setParaValue(paraValue);
        paraMapper.updatePara(para);
        result.setMessage("修改系统参数成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @DeleteMapping("systemPara/{id}")
    public Tool updatePara(@PathVariable("id") int id){
        Tool result = new Tool<>();
        paraMapper.deletePara(id);
        result.setMessage("删除系统参数成功");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
    @GetMapping("systemPara/{id}")
    public Tool getPara(@PathVariable("id") int id){
        Tool result = new Tool<>();
       Para para= paraMapper.queryPara(id);
//        "paraID": 901,
//                "paraName": "签到经验值",
//                "paraValue": "2",
//                "description": "学生签到一次获得的经验值"
        HashMap map=new HashMap();
        map.put("paraID",para.getId());
        map.put("paraName",para.getParaName());
        map.put("paraValue",para.getParaValue());
        map.put("description",para.getDescription());
        result.setData(map);
        result.setMessage("成功获取系统参数信息");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }

    @GetMapping("systemPara2/list")
    public Tool getParaList(){
        Tool result = new Tool<>();
        ArrayList<Para> arrayList = paraMapper.queryParaList();
        int count =arrayList.size();
//        "paraID": 901,
//                "paraName": "签到经验值",
//                "paraValue": "2",
//                "description": "学生签到一次获得的经验值"
        ArrayList res=new ArrayList();
        for (Para para:arrayList) {
            HashMap map=new HashMap();
            map.put("paraID",para.getId());
            map.put("paraName",para.getParaName());
            map.put("paraValue",para.getParaValue());
            map.put("description",para.getDescription());
            res.add(map);
        }
        HashMap resmap=new HashMap();
        resmap.put("total",count);
        resmap.put("rows",res);
        result.setData(resmap);
        result.setMessage("成功获取系统参数列表");
        result.setFlag("true");
        result.setCode(2000);
        return  result;
    }
}
