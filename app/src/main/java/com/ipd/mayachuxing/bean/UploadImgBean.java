package com.ipd.mayachuxing.bean;

public class UploadImgBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"url":"20190731\\14b3de5ba64d32fb8e96dcfffc68bc9b.jpg"}
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
         * url : 20190731\14b3de5ba64d32fb8e96dcfffc68bc9b.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
