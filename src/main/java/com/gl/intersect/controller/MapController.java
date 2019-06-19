package com.gl.intersect.controller;

import com.alibaba.fastjson.JSONArray;
import com.gl.intersect.service.IntersectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/11
 **/
@Controller
public class MapController {

    @Autowired
    private IntersectService intersectService;

    @RequestMapping("/exportMap")
    public String exportMap(@RequestParam(value = "pointStr",required = false) String pointStr, Model model){
        if(pointStr != null){
            System.out.println(pointStr);
            String[] poingStrArr = pointStr.split(";");
            ArrayList pointArrList = new ArrayList();
            for(int i=0;i<poingStrArr.length;i++){
                String[] pointArr = poingStrArr[i].split(",");
                pointArrList.add(pointArr);
            }
            model.addAttribute("pointsArr", pointArrList);
        }
        return "index";
    }

    /**
     * <p>分析结果出图</p>
     * @param pointStr
     * @param model
     * @return
     */
    @RequestMapping("/resultMap")
    public String resultMap(@RequestParam(value = "pointStr") String pointStr, Model model){
        if(pointStr != null){
            String[] poingStrArr = pointStr.split(";");
            ArrayList pointArrList = new ArrayList();
            for(int i=0;i<poingStrArr.length;i++){
                String[] pointArr = poingStrArr[i].split(",");
                pointArrList.add(pointArr);
            }
            JSONArray features = intersectService.excuteGPService(pointArrList,"地类图斑");
            model.addAttribute("features", features);
        }
        return "result-map";
    }
}
