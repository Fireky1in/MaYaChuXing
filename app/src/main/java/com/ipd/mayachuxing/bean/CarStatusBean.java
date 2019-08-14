package com.ipd.mayachuxing.bean;

public class CarStatusBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"imei":"866039041842528","start":1563959912,"status":1,"battery":43.72,"has_order":true}
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
         * imei : 866039041842528
         * start : 1563959912
         * status : 1
         * battery : 43.72
         * has_order : true
         */

        private String imei;
        private int start;
        private int status;
        private double battery;
        private boolean has_order;

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getBattery() {
            return battery;
        }

        public void setBattery(double battery) {
            this.battery = battery;
        }

        public boolean isHas_order() {
            return has_order;
        }

        public void setHas_order(boolean has_order) {
            this.has_order = has_order;
        }
    }
}
