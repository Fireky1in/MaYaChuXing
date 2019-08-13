package com.ipd.mayachuxing.api;

import com.ipd.mayachuxing.bean.CanUseCarBean;
import com.ipd.mayachuxing.bean.CaptchaBean;
import com.ipd.mayachuxing.bean.DepositRechargeBean;
import com.ipd.mayachuxing.bean.IuthenticationBean;
import com.ipd.mayachuxing.bean.LoginBean;
import com.ipd.mayachuxing.bean.ParkBikeBean;
import com.ipd.mayachuxing.bean.RechargeBean;
import com.ipd.mayachuxing.bean.SelectBikeBean;
import com.ipd.mayachuxing.bean.UserBalanceBean;
import com.ipd.mayachuxing.bean.UserInfoBean;
import com.ipd.mayachuxing.bean.WithdrawBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import static com.ipd.mayachuxing.common.config.UrlConfig.BANK_LIST;
import static com.ipd.mayachuxing.common.config.UrlConfig.CAN_USE_CAR;
import static com.ipd.mayachuxing.common.config.UrlConfig.CAPTCHA;
import static com.ipd.mayachuxing.common.config.UrlConfig.DEPOSIT_RECHARGE;
import static com.ipd.mayachuxing.common.config.UrlConfig.IUTHENTICATION;
import static com.ipd.mayachuxing.common.config.UrlConfig.LOGIN;
import static com.ipd.mayachuxing.common.config.UrlConfig.PARK_BIKE;
import static com.ipd.mayachuxing.common.config.UrlConfig.RECHARGE;
import static com.ipd.mayachuxing.common.config.UrlConfig.SELECT_BIKE;
import static com.ipd.mayachuxing.common.config.UrlConfig.USER_BALANCE;
import static com.ipd.mayachuxing.common.config.UrlConfig.USER_INFO;
import static com.ipd.mayachuxing.common.config.UrlConfig.WITHDRAW;

/**
 * Description ：请求配置
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */
public interface ApiService {

    //接收验证码
    @GET(CAPTCHA)
    Observable<CaptchaBean> getCaptcha(@QueryMap Map<String, String> map);

    //用户登录
    @FormUrlEncoded
    @POST(LOGIN)
    Observable<LoginBean> getLogin(@FieldMap Map<String, String> map);

    //附近车辆
    @GET(SELECT_BIKE)
    Observable<SelectBikeBean> getSelectBike(@QueryMap Map<String, String> map);

    //附件停车场
    @GET(PARK_BIKE)
    Observable<ParkBikeBean> getParkBike(@QueryMap Map<String, String> map);

    //实名认证
    @FormUrlEncoded
    @POST(IUTHENTICATION)
    Observable<IuthenticationBean> getIuthentication(@FieldMap Map<String, String> map);

    //押金充值
    @GET(DEPOSIT_RECHARGE)
    Observable<DepositRechargeBean> getDepositRecharge(@QueryMap Map<String, String> map);

    //资料详情
    @POST(USER_INFO)
    Observable<UserInfoBean> getUserInfo();

    //用户钱包|账户
    @GET(USER_BALANCE)
    Observable<UserBalanceBean> getUserBalance(@QueryMap Map<String, String> map);

    //用户是否可以用车
    @GET(CAN_USE_CAR)
    Observable<CanUseCarBean> getCanUseCar();

    //用户充值
    @FormUrlEncoded
    @POST(RECHARGE)
    Observable<RechargeBean> getRecharge(@FieldMap Map<String, String> map);

    //用户提现
    @FormUrlEncoded
    @POST(WITHDRAW)
    Observable<WithdrawBean> getWithdraw(@FieldMap Map<String, String> map);

    //银行卡列表
    @FormUrlEncoded
    @POST(BANK_LIST)
    Observable<WithdrawBean> getWithdraw(@FieldMap Map<String, String> map);
}
