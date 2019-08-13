package com.ipd.mayachuxing.bean;

import com.google.gson.annotations.SerializedName;

public class RechargeBean {

    /**
     * code : 200
     * message : 操作成功
     * data : {"appid":"wx7f5f66aed474d3fb","noncestr":"42f5b43e30edf22dda81a50254a9736d","package":"Sign=WXPay","partnerid":"1547083491","prepayid":"wx131332317520404e9d276c121172663200","timestamp":1565674351,"sign":"5BF4FA13FB3EFA26A4C9290B5A56DB62"}
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
         * pay_string : partner="2088531608181829"&seller_id="2990514107@qq.com"&out_trade_no="MAYA201908131333147647"&subject="马亚出行"&body="马亚出行"&total_fee="0.01"&notify_url="http://zl.v-lionsafety.com/index/callback/alipayDeposit"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="30m"&return_url="m.alipay.com"&sign="XAazBMGqZFrbdi0D%2B1V9JObPMl0TLEe8%2FxgQqJQ0UtkA0rCwGXgebT9H9urjXT0oHEaco735pIyZ3%2BwT%2BAnknRHeFA5HviAw97s6ANOjDzgeBVHn8kKoqkgP63P69%2BVkxULXiB2idq404XMDsFfbpbhQYXieKKv82GXX9mPNc1cDAN2CJOd%2BmSRepPqkkREmXjq5tzAN7GqquD9%2F5YN5povvbkHqfIbZeUSQM%2FSJD7oyd61zz9wWUz6cI9Ye%2FMWVKdBqpQ9uWOY3hs73f7pNE74JDWOF8LEnwGrgJv%2Bbx9u0ud%2FfBMWu0JkYT%2BPL1Diq544oC4q5hH3AOM6QpSRcEA%3D%3D"&sign_type="RSA"
         * <p>
         * appid : wx7f5f66aed474d3fb
         * noncestr : 42f5b43e30edf22dda81a50254a9736d
         * package : Sign=WXPay
         * partnerid : 1547083491
         * prepayid : wx131332317520404e9d276c121172663200
         * timestamp : 1565674351
         * sign : 5BF4FA13FB3EFA26A4C9290B5A56DB62
         */

        private String pay_string;
        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private int timestamp;
        private String sign;

        public String getPay_string() {
            return pay_string;
        }

        public void setPay_string(String pay_string) {
            this.pay_string = pay_string;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
