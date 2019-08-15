package com.ipd.mayachuxing.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponListBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"list":[{"id":7,"static":0,"conditions":3,"end_time":"2019-08-23","num":1},{"id":8,"static":0,"conditions":5,"end_time":"2019-08-23","num":2}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 7
             * static : 0
             * conditions : 3
             * end_time : 2019-08-23
             * num : 1
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
}
