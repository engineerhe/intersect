package com.gl.intersect.Entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/11
 **/
public class Feature {
    private HashMap geometry;

    public Feature(ArrayList pointArr){
        this.geometry = new HashMap();
        ArrayList list = new ArrayList();
        list.add(pointArr);
        this.geometry.put("rings", list);
    }

    public HashMap getGeometry() {
        return geometry;
    }

    public void setGeometry(HashMap geometry) {
        this.geometry = geometry;
    }
}
