package com.ipd.mayachuxing.bean;

public class UserInfoBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"headerUrl":"","name":"任梦阳","nickname":"MY_7eae1b42","phone":"18502994087","is_on":"已认证","balance":"0.00"}
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
         * headerUrl :
         * name : 任梦阳
         * nickname : MY_7eae1b42
         * phone : 18502994087
         * is_on : 已认证
         * balance : 0.00
         */

        private String headerUrl;
        private String name;
        private String nickname;
        private String phone;
        private String is_on;
        private String balance;

        public String getHeaderUrl() {
            return headerUrl;
        }

        public void setHeaderUrl(String headerUrl) {
            this.headerUrl = headerUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIs_on() {
            return is_on;
        }

        public void setIs_on(String is_on) {
            this.is_on = is_on;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
