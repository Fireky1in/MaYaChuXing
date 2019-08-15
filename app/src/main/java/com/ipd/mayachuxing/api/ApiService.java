package com.ipd.mayachuxing.api;

import com.ipd.mayachuxing.bean.AddBankBean;
import com.ipd.mayachuxing.bean.ApplyParkingSpotBean;
import com.ipd.mayachuxing.bean.BankListBean;
import com.ipd.mayachuxing.bean.CanUnlockBean;
import com.ipd.mayachuxing.bean.CanUseCarBean;
import com.ipd.mayachuxing.bean.CaptchaBean;
import com.ipd.mayachuxing.bean.CarStatusBean;
import com.ipd.mayachuxing.bean.CloseCarBean;
import com.ipd.mayachuxing.bean.CouponListBean;
import com.ipd.mayachuxing.bean.DepositRechargeBean;
import com.ipd.mayachuxing.bean.FeedBackBean;
import com.ipd.mayachuxing.bean.GetCarElectricityBean;
import com.ipd.mayachuxing.bean.IsOrderBean;
import com.ipd.mayachuxing.bean.IuthenticationBean;
import com.ipd.mayachuxing.bean.LockCarBean;
import com.ipd.mayachuxing.bean.LoginBean;
import com.ipd.mayachuxing.bean.ModifyNameBean;
import com.ipd.mayachuxing.bean.MsgListBean;
import com.ipd.mayachuxing.bean.OpenCarBean;
import com.ipd.mayachuxing.bean.ParkBikeBean;
import com.ipd.mayachuxing.bean.PayDetailsBean;
import com.ipd.mayachuxing.bean.PayOrderBean;
import com.ipd.mayachuxing.bean.RechargeBean;
import com.ipd.mayachuxing.bean.SelectBikeBean;
import com.ipd.mayachuxing.bean.SetMemberShipBean;
import com.ipd.mayachuxing.bean.TripDetailsBean;
import com.ipd.mayachuxing.bean.TripListBean;
import com.ipd.mayachuxing.bean.UnlockCarBean;
import com.ipd.mayachuxing.bean.UploadHeadBean;
import com.ipd.mayachuxing.bean.UploadImgBean;
import com.ipd.mayachuxing.bean.UserBalanceBean;
import com.ipd.mayachuxing.bean.UserInfoBean;
import com.ipd.mayachuxing.bean.WithdrawBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

import static com.ipd.mayachuxing.common.config.UrlConfig.ADD_BANK;
import static com.ipd.mayachuxing.common.config.UrlConfig.APPLY_PARKING_SPOT;
import static com.ipd.mayachuxing.common.config.UrlConfig.BANK_LIST;
import static com.ipd.mayachuxing.common.config.UrlConfig.CAN_UNLOCK;
import static com.ipd.mayachuxing.common.config.UrlConfig.CAN_USE_CAR;
import static com.ipd.mayachuxing.common.config.UrlConfig.CAPTCHA;
import static com.ipd.mayachuxing.common.config.UrlConfig.CAR_STATUS;
import static com.ipd.mayachuxing.common.config.UrlConfig.CLOSE_CAR;
import static com.ipd.mayachuxing.common.config.UrlConfig.COUPON_LIST;
import static com.ipd.mayachuxing.common.config.UrlConfig.DEPOSIT_RECHARGE;
import static com.ipd.mayachuxing.common.config.UrlConfig.FEED_BACK;
import static com.ipd.mayachuxing.common.config.UrlConfig.GET_CAR_ELECTRICITY;
import static com.ipd.mayachuxing.common.config.UrlConfig.IS_ORDER;
import static com.ipd.mayachuxing.common.config.UrlConfig.IUTHENTICATION;
import static com.ipd.mayachuxing.common.config.UrlConfig.LOCK_CAR;
import static com.ipd.mayachuxing.common.config.UrlConfig.LOGIN;
import static com.ipd.mayachuxing.common.config.UrlConfig.MODIFY_NAME;
import static com.ipd.mayachuxing.common.config.UrlConfig.MSG_LIST;
import static com.ipd.mayachuxing.common.config.UrlConfig.OPEN_CAR;
import static com.ipd.mayachuxing.common.config.UrlConfig.PARK_BIKE;
import static com.ipd.mayachuxing.common.config.UrlConfig.PAY_DETAILS;
import static com.ipd.mayachuxing.common.config.UrlConfig.PAY_ORDER;
import static com.ipd.mayachuxing.common.config.UrlConfig.RECHARGE;
import static com.ipd.mayachuxing.common.config.UrlConfig.SELECT_BIKE;
import static com.ipd.mayachuxing.common.config.UrlConfig.SET_MEMVER_SHIP;
import static com.ipd.mayachuxing.common.config.UrlConfig.TRIP_DETAILS;
import static com.ipd.mayachuxing.common.config.UrlConfig.TRIP_LIST;
import static com.ipd.mayachuxing.common.config.UrlConfig.UNLOCK_CAR;
import static com.ipd.mayachuxing.common.config.UrlConfig.UPLOAD_HEAD;
import static com.ipd.mayachuxing.common.config.UrlConfig.UPLOAD_IMG;
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
    Observable<BankListBean> getBankList(@FieldMap Map<String, String> map);

    //添加银行卡
    @FormUrlEncoded
    @POST(ADD_BANK)
    Observable<AddBankBean> getAddBank(@FieldMap Map<String, String> map);

    //上传头像
    @Multipart
    @POST(UPLOAD_HEAD)
    Observable<UploadHeadBean> getUploadHead(@PartMap Map<String, RequestBody> map);

    //修改用户昵称
    @FormUrlEncoded
    @POST(MODIFY_NAME)
    Observable<ModifyNameBean> getModifyName(@FieldMap Map<String, String> map);

    //优惠券列表
    @FormUrlEncoded
    @POST(COUPON_LIST)
    Observable<CouponListBean> getCouponList(@FieldMap Map<String, String> map);

    //信息列表
    @FormUrlEncoded
    @POST(MSG_LIST)
    Observable<MsgListBean> getMsgList(@FieldMap Map<String, String> map);

    //获取车辆信息
    @GET(GET_CAR_ELECTRICITY)
    Observable<GetCarElectricityBean> getGetCarElectricity(@QueryMap Map<String, String> map);

    //开锁，开始骑车
    @FormUrlEncoded
    @POST(OPEN_CAR)
    Observable<OpenCarBean> getOpenCar(@FieldMap Map<String, String> map);

    //还车
    @FormUrlEncoded
    @POST(CLOSE_CAR)
    Observable<CloseCarBean> getCloseCar(@FieldMap Map<String, String> map);

    //获取车辆使用状态
    @GET(CAR_STATUS)
    Observable<CarStatusBean> getCarStatus();

    //查询用户是否有未完成订单
    @GET(IS_ORDER)
    Observable<IsOrderBean> getIsOrder();

    //支付订单详情
    @GET(PAY_DETAILS)
    Observable<PayDetailsBean> getPayDetails();

    //行程订单支付
    @GET(PAY_ORDER)
    Observable<PayOrderBean> getPayOrder(@QueryMap Map<String, String> map);

    //我的行程
    @GET(TRIP_LIST)
    Observable<TripListBean> getTripList(@QueryMap Map<String, String> map);

    //行程详情
    @GET(TRIP_DETAILS)
    Observable<TripDetailsBean> getTripDetails(@QueryMap Map<String, String> map);

    //临时锁车
    @GET(LOCK_CAR)
    Observable<LockCarBean> getLockCar();

    //临时锁车-开锁
    @GET(UNLOCK_CAR)
    Observable<UnlockCarBean> getUnlockCar();

    //开不了锁
    @FormUrlEncoded
    @POST(CAN_UNLOCK)
    Observable<CanUnlockBean> getCanUnlock(@FieldMap Map<String, String> map);

    //上报故障|我要举报
    @FormUrlEncoded
    @POST(FEED_BACK)
    Observable<FeedBackBean> getFeedBack(@FieldMap Map<String, String> map);

    //图片上传
    @Multipart
    @POST(UPLOAD_IMG)
    Observable<UploadImgBean> getUploadImg(@PartMap Map<String, RequestBody> map);

    //申请加盟
    @FormUrlEncoded
    @POST(SET_MEMVER_SHIP)
    Observable<SetMemberShipBean> getSetMemberShip(@FieldMap Map<String, String> map);

    //申请还车点
    @FormUrlEncoded
    @POST(APPLY_PARKING_SPOT)
    Observable<ApplyParkingSpotBean> getApplyParkingSpot(@FieldMap Map<String, String> map);
}
