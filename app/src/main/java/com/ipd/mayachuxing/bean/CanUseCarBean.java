package com.ipd.mayachuxing.bean;

public class CanUseCarBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"can_use":true,"why":"","status":0}
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
         * can_use : true
         * why :
         * status : 0
         */

        private boolean can_use;
        private String why;
        private int status;

        public boolean isCan_use() {
            return can_use;
        }

        public void setCan_use(boolean can_use) {
            this.can_use = can_use;
        }

        public String getWhy() {
            return why;
        }

        public void setWhy(String why) {
            this.why = why;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
