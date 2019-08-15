package com.ipd.mayachuxing.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PayDetailsBean implements Parcelable {
    /**
     * code : 200
     * message : 操作成功
     * data : {"time":"21分35秒","activity_money":"0.00","coupons":[{"id":1,"static":0,"conditions":5,"end_time":"2019-08-31","num":2},{"id":2,"static":0,"conditions":3,"end_time":"2019-08-23","num":1}],"money":"5.00","balance":"49888.00"}
     */

    private int code;
    private String message;
    private DataBean data;

    protected PayDetailsBean(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<PayDetailsBean> CREATOR = new Creator<PayDetailsBean>() {
        @Override
        public PayDetailsBean createFromParcel(Parcel in) {
            return new PayDetailsBean(in);
        }

        @Override
        public PayDetailsBean[] newArray(int size) {
            return new PayDetailsBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(code);
        parcel.writeString(message);
    }

    public static class DataBean implements Parcelable{
        /**
         * time : 21分35秒
         * activity_money : 0.00
         * coupons : [{"id":1,"static":0,"conditions":5,"end_time":"2019-08-31","num":2},{"id":2,"static":0,"conditions":3,"end_time":"2019-08-23","num":1}]
         * money : 5.00
         * balance : 49888.00
         */

        private String time;
        private String activity_money;
        private String money;
        private String balance;
        private List<CouponsBean> coupons;

        protected DataBean(Parcel in) {
            time = in.readString();
            activity_money = in.readString();
            money = in.readString();
            balance = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getActivity_money() {
            return activity_money;
        }

        public void setActivity_money(String activity_money) {
            this.activity_money = activity_money;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public List<CouponsBean> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<CouponsBean> coupons) {
            this.coupons = coupons;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(time);
            parcel.writeString(activity_money);
            parcel.writeString(money);
            parcel.writeString(balance);
        }

        public static class CouponsBean implements Parcelable{
            /**
             * id : 1
             * static : 0
             * conditions : 5
             * end_time : 2019-08-31
             * num : 2
             */

            private int id;
            @SerializedName("static")
            private int staticX;
            private int conditions;
            private String end_time;
            private int num;

            protected CouponsBean(Parcel in) {
                id = in.readInt();
                staticX = in.readInt();
                conditions = in.readInt();
                end_time = in.readString();
                num = in.readInt();
            }

            public static final Creator<CouponsBean> CREATOR = new Creator<CouponsBean>() {
                @Override
                public CouponsBean createFromParcel(Parcel in) {
                    return new CouponsBean(in);
                }

                @Override
                public CouponsBean[] newArray(int size) {
                    return new CouponsBean[size];
                }
            };

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getStaticX() {
                return staticX;
            }

            public void setStaticX(int staticX) {
                this.staticX = staticX;
            }

            public int getConditions() {
                return conditions;
            }

            public void setConditions(int conditions) {
                this.conditions = conditions;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(id);
                parcel.writeInt(staticX);
                parcel.writeInt(conditions);
                parcel.writeString(end_time);
                parcel.writeInt(num);
            }
        }
    }
}
