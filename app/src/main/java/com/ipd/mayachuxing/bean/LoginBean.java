package com.ipd.mayachuxing.bean;

public class LoginBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"token":"ccfe5140df3f44d07696268fb07e3932","push_key":"ab9e2adb467c67074c519c94aad2b2d8"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * token : ccfe5140df3f44d07696268fb07e3932
         * push_key : ab9e2adb467c67074c519c94aad2b2d8
         */

        private String token;
        private String push_key;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPush_key() {
            return push_key;
        }

        public void setPush_key(String push_key) {
            this.push_key = push_key;
        }
    }
}
