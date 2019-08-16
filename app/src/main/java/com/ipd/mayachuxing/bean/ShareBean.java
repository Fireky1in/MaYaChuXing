package com.ipd.mayachuxing.bean;

import java.util.List;

public class ShareBean {
    /**
     * code : 200
     * message : 操作成功
     * data : {"total":1,"users":[{"avatar":"","phone":"13855347424","date":"2019-08-01"}],"share_url":"http://www.maya.com/app/register.html?invite=12"}
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
         * total : 1
         * users : [{"avatar":"","phone":"13855347424","date":"2019-08-01"}]
         * share_url : http://www.maya.com/app/register.html?invite=12
         */

        private int total;
        private String share_url;
        private List<UsersBean> users;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class UsersBean {
            /**
             * avatar :
             * phone : 13855347424
             * date : 2019-08-01
             */

            private String avatar;
            private String phone;
            private String date;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
