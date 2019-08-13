package com.ipd.mayachuxing.bean;

import java.util.List;

public class ParkBikeBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"list":[{"name":"梦谷停车场","lat":31.201102,"lng":121.267068,"address":"华徐公路888号中国梦谷"}]}
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
             * name : 梦谷停车场
             * lat : 31.201102
             * lng : 121.267068
             * address : 华徐公路888号中国梦谷
             */

            private String name;
            private double lat;
            private double lng;
            private String address;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
