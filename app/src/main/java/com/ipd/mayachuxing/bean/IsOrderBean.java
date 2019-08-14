package com.ipd.mayachuxing.bean;

public class IsOrderBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"has_order":true,"status":3}
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
         * has_order : true
         * status : 3
         */

        private boolean has_order;
        private int status;

        public boolean isHas_order() {
            return has_order;
        }

        public void setHas_order(boolean has_order) {
            this.has_order = has_order;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
