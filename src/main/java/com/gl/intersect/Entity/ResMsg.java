package com.gl.intersect.Entity;

/**
 * @author <a href="engineer_he@aliyun.com">john</a>
 * @see 2019/6/12
 **/
public class ResMsg {
    private int code = 200;
    private String Msg = "";
    private Object data = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
