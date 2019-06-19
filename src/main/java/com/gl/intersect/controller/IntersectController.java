package com.gl.intersect.controller;

import com.gl.intersect.Entity.ResMsg;
import com.gl.intersect.service.IntersectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/11
 **/
@RestController
public class IntersectController {

    @Autowired
    private IntersectService intersectService;

    @RequestMapping("/intersectAnalysis")
    public Object intersectAnalysis(@RequestParam String pointStr){
        ResMsg resMsg = new ResMsg();
        try {
            ArrayList pointList = new ArrayList();
            if(pointStr != null){
                String[] poingStrArr = pointStr.split(";");
                for(int i=0;i<poingStrArr.length;i++){
                    String[] pointArr = poingStrArr[i].split(",");
                    pointList.add(pointArr);
                }
                resMsg.setData(intersectService.doAnalysis(pointList));
            }
        }catch (Exception e){
            e.printStackTrace();
            resMsg.setCode(500);
            resMsg.setMsg(e.getMessage());
        }
        return resMsg;
    }

    /**
     * 叠加分析
     * @param pointStr 叠加区坐标串
     * @param intersectLayer 被叠加图层:"地类图斑"，"土地利用总体规划"，"基本农田保护图斑"
     * @return
     */
    @RequestMapping("/intersect")
    public Object intersect(@RequestParam String pointStr,@RequestParam String intersectLayer){
        ResMsg resMsg = new ResMsg();
        try {
            ArrayList pointList = new ArrayList();
            if(pointStr != null){
                String[] poingStrArr = pointStr.split(";");
                for(int i=0;i<poingStrArr.length;i++){
                    String[] pointArr = poingStrArr[i].split(",");
                    pointList.add(pointArr);
                }
                resMsg.setData(intersectService.excuteGPService(pointList,intersectLayer));
            }
        }catch (Exception e){
            e.printStackTrace();
            resMsg.setCode(500);
            resMsg.setMsg(e.getMessage());
        }
        return resMsg;
    }
}
