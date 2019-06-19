package com.gl.intersect.controller;

import com.alibaba.fastjson.JSONObject;
import com.gl.intersect.Entity.FeatureAttribute;
import com.gl.intersect.Entity.ResMsg;
import com.gl.intersect.handler.FeatuerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/18
 **/
@RestController
public class FeatureController {

    @Autowired
    private FeatuerHandler featuerHandler;


    /**
     * 添加要素
     * <p>未利用SpringMVC默认的的解析器，自行解析参数</p>
     * @param pointStr
     * @param attribute
     * @return
     */
    @RequestMapping("/addFeature")
    public ResMsg addFeature(@RequestParam String pointStr, @RequestParam String attribute){
        ResMsg resMsg = new ResMsg();
        try {
            ArrayList pointList = new ArrayList();
            if(pointStr != null){
                String[] poingStrArr = pointStr.split(";");
                for(int i=0;i<poingStrArr.length;i++){
                    String[] pointArr = poingStrArr[i].split(",");
                    pointList.add(pointArr);
                }
            }
            JSONObject featureAttribute = JSONObject.parseObject(attribute);
            System.out.println(featureAttribute);
            resMsg.setData(featuerHandler.addFeatures(pointList, featureAttribute));
        }catch (Exception e){
            e.printStackTrace();
            resMsg.setCode(500);
            resMsg.setMsg(e.getMessage());
        }
        return resMsg;
    }
}
