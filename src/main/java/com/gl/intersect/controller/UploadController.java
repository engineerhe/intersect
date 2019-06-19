package com.gl.intersect.controller;

import com.gl.intersect.Entity.ResMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/13
 **/
@Controller
public class UploadController {
    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    @CrossOrigin
    public Object upload(@RequestParam(value = "file",required = false) MultipartFile file){
        ResMsg resultObj = new ResMsg();
        BufferedReader bf = null;
        try {
            System.out.println(file.getOriginalFilename());
            bf = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String lineStr = null;
            ArrayList<Object> coordinatesList = new ArrayList<>();
            while ((lineStr=bf.readLine())!=null){
                System.out.println(lineStr);
                float[] coordinate = new float[2];
                coordinate[0] = Float.parseFloat(lineStr.split(",")[0]);
                coordinate[1] = Float.parseFloat(lineStr.split(",")[1]);
                coordinatesList.add(coordinate);
            }
            resultObj.setData(coordinatesList);

        }catch (Exception e){
            resultObj.setCode(500);
            resultObj.setMsg(e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if(bf != null){
                    bf.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultObj;
    }
}
