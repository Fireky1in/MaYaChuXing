package com.ipd.mayachuxing.bean;

public class GetCarElectricityBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"batter":57.01}
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
         * batter : 57.01
         */

        private double batter;

        public double getBatter() {
            return batter;
        }

        public void setBatter(double batter) {
            this.batter = batter;
        }
    }
}
