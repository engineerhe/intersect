package com.gl.intersect.service;

import com.alibaba.fastjson.JSONArray;
import com.gl.intersect.handler.IntersectHandler;
import com.gl.intersect.handler.PackageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/11
 **/
@Service
public class IntersectService {

    @Autowired
    private IntersectHandler intersectHandler;

    @Autowired
    private PackageHandler packageHandler;

    /**
     * <p>执行分析服务</p>
     * @param pointList
     * @return
     */
    public Object doAnalysis(ArrayList pointList){
        Map<String,Object> res = new HashMap<>();
        Object dltb = packageHandler.packageResult4DLTB(excuteGPService(pointList, "地类图斑"));
        Object tdlyztgh = packageHandler.packageResult4TDLYZTGH(excuteGPService(pointList, "土地利用总体规划"));
        Object jbntbhtb = packageHandler.packageResult4JBNTBHTB(excuteGPService(pointList, "基本农田保护图斑"));;
        res.put("地类图斑", dltb);
        res.put("土地利用总体规划", tdlyztgh);
        res.put("基本农田保护图斑", jbntbhtb);
        return res;
    }

    /**
     * <p>执行GP服务，返回要素集合</p>
     * @param pointList
     * @param intersectLayer
     * @return
     */
    public JSONArray excuteGPService(ArrayList pointList,String intersectLayer){
        Map resMap = intersectHandler.submitJob("GPServer/Intersect/submitJob?f=json", pointList,intersectLayer);
        String jobId = resMap.get("jobId").toString();
        String paramURL = intersectHandler.checkJobStatus(jobId);
        JSONArray features = intersectHandler.obtainResult(jobId, paramURL);
        return features;
    }

}
