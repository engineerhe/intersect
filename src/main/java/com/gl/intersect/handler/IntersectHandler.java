package com.gl.intersect.handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gl.intersect.Entity.Feature;
import com.gl.intersect.Entity.ParamObject;
import com.gl.intersect.Entity.ResMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/11
 **/
@Component
public class IntersectHandler {

    private RestTemplate rst = new RestTemplate();

    @Value("${arcserver.gp.intersect.baseurl}")
    private String baseServerURL;

    /**
     * <p>提交叠加分析任务</p>
     * e.g.serverURL="GPServer/Intersect/submitJob?f=json"
     * @param serverURL
     * @param pointList 界址点数组
     * @param intersectLayer 相交图层
     * @return
     */
    public Map submitJob(String serverURL, ArrayList pointList,String intersectLayer){
        Feature feature = new Feature(pointList);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        HashMap<String, Object> paramMap = new HashMap<>();
        ParamObject paramObject = new ParamObject();
        ArrayList<Feature> features = new ArrayList<Feature>();
        features.add(feature);
        paramObject.setFeatures(features);
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("图斑", JSONObject.toJSONString(paramObject));
        paramMap.add("基础图层", intersectLayer);
//        paramMap.put("图斑", paramObject);
//        paramMap.put("基础图层", "地类图斑");
//        System.out.println(JSONObject.toJSONString(paramMap));
//        System.out.println("提交任务："+baseServerURL+serverURL);
//        HttpEntity<String> httpEntity = new HttpEntity<String>(JSONObject.toJSONString(paramMap),headers);
        ResponseEntity responseEntity = rst.postForEntity(baseServerURL+serverURL, paramMap, String.class);
        String responsBody = responseEntity.getBody().toString();
        System.out.println(responseEntity.getBody().toString());
        Map resultMap = JSONObject.parseObject(responsBody);
        return resultMap;
    }

    /**
     * <p>检测job任务执行状态</p>
     * @param jobId
     * @return
     */
    public String checkJobStatus(String jobId) {
        boolean status = false;
        Map responsMap = null;
        while (!status) {
            try {
//                System.out.println("检测状态："+baseServerURL + "GPServer/Intersect/jobs/"+jobId+"?f=json");
                String responsBody = rst.getForObject(baseServerURL + "GPServer/Intersect/jobs/"+jobId+"?f=json", String.class);
                responsMap = JSONObject.parseObject(responsBody);
                String jobStatus = responsMap.get("jobStatus").toString();
                if ("esriJobSucceeded".equals(jobStatus)) {
                    status = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        Map results = JSONObject.parseObject(responsMap.get("results").toString());
        Map result_intersect = JSONObject.parseObject(results.get("result_intersect").toString());
        String  paramURL = result_intersect.get("paramUrl").toString();
        return paramURL;
    }

    /**
     * <p>获取并分析结果</p>
     * @param jobId
     * @param paramURL
     * @return
     */
    public JSONArray obtainResult(String jobId,String paramURL){
        String url = baseServerURL+"GPServer/Intersect/jobs/"+jobId+"/"+paramURL+"?f=json";
//        System.out.println("获取结果："+url);
        String resultStr = rst.getForObject(url, String.class);
        Map resultMap = JSONObject.parseObject(resultStr);
        Map resultRecord = (Map)resultMap.get("value");
        JSONArray features = (JSONArray)resultRecord.get("features");
        return features;
    }



    public static void main(String[] args) {
       /* String pointStr = "31640746.4443,4448469.6462999992;31640852.277800001,4448477.0546000004;31640934.828000002,4448446.3629000001;31640957.052999999,4448320.4210000001;31640710.460900001,4448351.1127000004;31640746.4443,4448469.6462999992";
        ArrayList pointList = new ArrayList();
        String[] poingStrArr = pointStr.split(";");
        for(int i=0;i<poingStrArr.length;i++){
            String[] pointArr = poingStrArr[i].split(",");
            pointList.add(pointArr);
        }
        IntersectHandler intersectHandler = new IntersectHandler();
        Map resMap = intersectHandler.submitJob("GPServer/Intersect/submitJob?f=json", pointList,"地类图斑");
        String jobId = resMap.get("jobId").toString();
        String paramURL = intersectHandler.checkJobStatus(jobId);
        JSONArray jsonArray = intersectHandler.obtainResult(jobId, paramURL);
        Map res = new PackageHandler().packageResult4DLTB(jsonArray);
        System.out.println(res);*/
       String url = "http://localhost:7788/test/{1}/{2}";
       RestTemplate rt =new RestTemplate();
       rt.getForObject(url, String.class, 11101,88);
       ////////////////////////////////////////////////////////////////////////
       Map paraMap = new HashMap();
       paraMap.put("id", 11101);
       paraMap.put("num", 88);
       rt.getForObject(url, String.class, paraMap);

       MultiValueMap multiValueMap = new LinkedMultiValueMap();
       multiValueMap.add("test", "123");
       multiValueMap.add("test1", "我的测试");
        System.out.println(multiValueMap);
    }

}
