package com.ipd.mayachuxing.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserBalanceBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"balance":"0.00","list":[{"time":"2019-07-22 14:56:19","static":" ","num":"1.00","reason":"充值"}]}
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
         * balance : 0.00
         * list : [{"time":"2019-07-22 14:56:19","static":" ","num":"1.00","reason":"充值"}]
         */

        private String balance;
        private List<ListBean> list;

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * time : 2019-07-22 14:56:19
             * static :
             * num : 1.00
             * reason : 充值
             */

            private String time;
            @SerializedName("static")
            private String staticX;
            private String num;
            private String reason;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStaticX() {
                return staticX;
            }

            public void setStaticX(String staticX) {
                this.staticX = staticX;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }
        }
    }
}
