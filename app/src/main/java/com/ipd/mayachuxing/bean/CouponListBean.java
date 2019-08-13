package com.ipd.mayachuxing.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponListBean {
    /**
     * code : 200
     * message : 操作成功
     * data : [{"id":2,"static":1,"conditions":5,"end_time":"2020-01-01","num":2}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * static : 1
         * conditions : 5
         * end_time : 2020-01-01
         * num : 2
         */

        private int id;
        @SerializedName("static")
        private int staticX;
        private int conditions;
        private String end_time;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStaticX() {
            return staticX;
        }

        public void setStaticX(int staticX) {
            this.staticX = staticX;
        }

        public int getConditions() {
            return conditions;
        }

        public void setConditions(int conditions) {
            this.conditions = conditions;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
