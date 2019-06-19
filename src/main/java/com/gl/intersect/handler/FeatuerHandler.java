package com.gl.intersect.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gl.intersect.Entity.Feature;
import com.gl.intersect.Entity.ParamObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/18
 **/
@Component
public class FeatuerHandler {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${arcserver.feature-service}")
    private String featureServiceURL;


    /**
     * 新增要素
     * @param pointsList
     * @param attribute
     * @return
     */
    public Object addFeatures(ArrayList pointsList, JSONObject attribute){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        attribute.put("st_length_shape_", 0);
        attribute.put("st_area_shape_", 0);
        Feature feature = new Feature(pointsList);
        JSONObject paraObj = new JSONObject();
        paraObj.put("attributes", attribute);
        paraObj.put("geometry", feature.getGeometry());
        paramMap.add("features", "["+JSONObject.toJSONString(paraObj)+"]");
        paramMap.add("f", "json");
        System.out.println("["+JSONObject.toJSONString(paraObj)+"]");
        ResponseEntity responseEntity =  restTemplate.postForEntity(featureServiceURL, paramMap, String.class);
        JSONObject res = JSONObject.parseObject(responseEntity.getBody().toString());
        System.out.println(res);
        return res;
    }
}
