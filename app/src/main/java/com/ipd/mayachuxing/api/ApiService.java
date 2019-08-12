package com.ipd.mayachuxing.api;

import com.ipd.mayachuxing.bean.CaptchaBean;
import com.ipd.mayachuxing.bean.LoginBean;
import com.ipd.mayachuxing.bean.ParkBikeBean;
import com.ipd.mayachuxing.bean.SelectBikeBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import static com.ipd.mayachuxing.common.config.UrlConfig.CAPTCHA;
import static com.ipd.mayachuxing.common.config.UrlConfig.LOGIN;
import static com.ipd.mayachuxing.common.config.UrlConfig.PARK_BIKE;
import static com.ipd.mayachuxing.common.config.UrlConfig.SELECT_BIKE;

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
}
