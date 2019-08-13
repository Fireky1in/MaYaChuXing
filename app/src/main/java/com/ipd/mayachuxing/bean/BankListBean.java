package com.ipd.mayachuxing.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankListBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"list":[{"bank":"中国银行","static":"信用卡","card":"6229********0113","bid":1}]}
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
             * bank : 中国银行
             * static : 信用卡
             * card : 6229********0113
             * bid : 1
             */

            private String bank;
            @SerializedName("static")
            private String staticX;
            private String card;
            private int bid;
            private boolean isShow;

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getStaticX() {
                return staticX;
            }

            public void setStaticX(String staticX) {
                this.staticX = staticX;
            }

            public String getCard() {
                return card;
            }

            public void setCard(String card) {
                this.card = card;
            }

            public int getBid() {
                return bid;
            }

            public void setBid(int bid) {
                this.bid = bid;
            }
        }
    }
}
