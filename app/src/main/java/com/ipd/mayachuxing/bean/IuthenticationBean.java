package com.ipd.mayachuxing.bean;

public class IuthenticationBean {
    /**
     * data : {}
     * message : 用户未登录
     * code : 203
     */

    private DataBean data;
    private String message;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
    }
}
