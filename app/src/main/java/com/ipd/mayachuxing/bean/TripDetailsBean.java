package com.ipd.mayachuxing.bean;

public class TripDetailsBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"open_address":"上海市青浦区汇龙路靠近法姬娜大厦","finish_address":"上海市青浦区汇龙路靠近法姬娜大厦","create_at":"2019-07-30 16:29:01","finish_at":"2019-07-30 16:50:36","time":"21分35秒","money":"5.00","coupon_money":"0.00","activity_money":"0.00","pay_money":"0.00","pay_status":"未支付"}
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
         * open_address : 上海市青浦区汇龙路靠近法姬娜大厦
         * finish_address : 上海市青浦区汇龙路靠近法姬娜大厦
         * create_at : 2019-07-30 16:29:01
         * finish_at : 2019-07-30 16:50:36
         * time : 21分35秒
         * money : 5.00
         * coupon_money : 0.00
         * activity_money : 0.00
         * pay_money : 0.00
         * pay_status : 未支付
         */

        private String open_address;
        private String finish_address;
        private String create_at;
        private String finish_at;
        private String time;
        private String money;
        private String coupon_money;
        private String activity_money;
        private String pay_money;
        private String pay_status;

        public String getOpen_address() {
            return open_address;
        }

        public void setOpen_address(String open_address) {
            this.open_address = open_address;
        }

        public String getFinish_address() {
            return finish_address;
        }

        public void setFinish_address(String finish_address) {
            this.finish_address = finish_address;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getFinish_at() {
            return finish_at;
        }

        public void setFinish_at(String finish_at) {
            this.finish_at = finish_at;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(String coupon_money) {
            this.coupon_money = coupon_money;
        }

        public String getActivity_money() {
            return activity_money;
        }

        public void setActivity_money(String activity_money) {
            this.activity_money = activity_money;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }
    }
}
