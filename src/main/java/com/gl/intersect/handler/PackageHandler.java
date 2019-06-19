package com.gl.intersect.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/13
 **/
@Component
public class PackageHandler {
    /**
     * <p>为图斑地类叠加分析结果打包</p>
     * @param features
     * @return
     */
    public Map packageResult4DLTB(JSONArray features){
        Map resulstMap = new HashMap();
        Map gtMap = new HashMap();
        Map gyMap = new HashMap();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("耕地", 0.0f);
        jsonObject.put("园地", 0.0f);
        jsonObject.put("其它用农地", 0.0f);
        jsonObject.put("未利用地", 0.0f);
        jsonObject.put("原建设用地", 0.0f);
        for(int i=0;i<features.size();i++){
            Map feature = (Map)features.get(i);
            Map attributes = (Map) feature.get("attributes");
            String groundCode = attributes.get("DLBM").toString();
            String type = attributes.get("QSXZ").toString();
            String name = attributes.get("QSDWMC").toString();
            if(type.equals("30")){//集体
                if(!gtMap.keySet().contains(name)){
                    gtMap.put(name, jsonObject);
                }
                Map infoMap = (Map) gtMap.get(name);
                wrapData4ResutlMap(infoMap, groundCode, attributes);

            }else if(type.equals("20")){//国有
                if(!gyMap.keySet().contains(name)){
                    gyMap.put(name, jsonObject);
                }
                Map infoMap = (Map) gtMap.get(name);
                wrapData4ResutlMap(infoMap, groundCode, attributes);
            }
        }
        resulstMap.put("集体", gtMap);
        resulstMap.put("国有", gyMap);
        return resulstMap;
    }


    /**
     * <p>土地利用总体规划叠加分析结果打包</p>
     * @param features
     * @return
     */
    public Map packageResult4TDLYZTGH(JSONArray features){
        Map resMap = new HashMap();
        resMap.put("允许建设区",0.0f);
        resMap.put("有条件建设区",0.0f);
        resMap.put("限制建设区",0.0f);
        resMap.put("禁止建设区",0.0f);
        resMap.put("自然保留地",0.0f);
        for(int i=0;i<features.size();i++){
            Map feature = (Map)features.get(i);
            Map attributes = (Map) feature.get("attributes");
            String name = attributes.get("GZQLXMC").toString();
            resMap.put(name, Float.parseFloat(resMap.get(name).toString())+Float.parseFloat(attributes.get("Shape_Area").toString()));
        }
        return resMap;
    }

    /**
     * <p>为基本农田图斑叠加分析结果打包</p>
     * @return
     */
    public Float packageResult4JBNTBHTB(JSONArray features){
        Float area  = 0.0f;
        for(int i=0;i<features.size();i++){
            Map feature = (Map)features.get(i);
            Map attributes = (Map) feature.get("attributes");
            area+= Float.parseFloat(attributes.get("Shape_Area").toString());
        }
        return area;
    }



    /**
     * 为结果装填封装特定格式的数据
     * @param infoMap
     * @param groundCode
     * @param attributes
     */
    private void wrapData4ResutlMap(Map infoMap,String groundCode, Map attributes){
        if("011".equals(groundCode) || "012".equals(groundCode) || "013".equals(groundCode)){//耕地
            infoMap.put("耕地", Float.parseFloat( infoMap.get("耕地").toString())+Float.parseFloat(attributes.get("TBMJ").toString()));
        }
        if("021".equals(groundCode) || "022".equals(groundCode) || "023".equals(groundCode)){//园地
            infoMap.put("园地", Float.parseFloat( infoMap.get("园地").toString())+Float.parseFloat(attributes.get("TBMJ").toString()));
        }
        String [] qtCodeArr = {"031","032","033","041","042","104","114","117","122","123"};
        if(Arrays.asList(qtCodeArr).contains(groundCode)){//其它农用地
            infoMap.put("其它用农地", Float.parseFloat( infoMap.get("其它用农地").toString())+Float.parseFloat(attributes.get("TBMJ").toString()));
        }
        String [] wlyCodeArr = {"043","111","112","115","116","119","124","125","126","127"};
        if(Arrays.asList(wlyCodeArr).contains(groundCode)){//未利用地
            infoMap.put("未利用地", Float.parseFloat( infoMap.get("未利用地").toString())+Float.parseFloat(attributes.get("TBMJ").toString()));
        }
        String [] jsCodeArr = {"051","052","053","054","061","062","063","071","072","081","082","083","084","085","086","087","088","091","092","093","094","095","101","102","103","105","106","107","113","118","121"};
        if(Arrays.asList(jsCodeArr).contains(groundCode)){//建设用地
            infoMap.put("建设用地", Float.parseFloat( infoMap.get("建设用地").toString())+Float.parseFloat(attributes.get("TBMJ").toString()));
        }
    }
}
