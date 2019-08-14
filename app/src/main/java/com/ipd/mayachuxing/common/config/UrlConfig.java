package com.ipd.mayachuxing.common.config;

/**
 * Description ：URL 配置
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public interface UrlConfig {
    /**
     * 域名
     */
    String BASE_URL = "http://zl.v-lionsafety.com/index/";
    String BASE_LOCAL_URL = "http://zl.v-lionsafety.com/";

    /**
     * 登陆
     */
    String CAPTCHA = "Register/phoneCode"; //接收验证码
    String LOGIN = "Register/userLogin"; //用户登录
    String IUTHENTICATION = "Register/nameAuthentication"; //实名认证
    String DEPOSIT_RECHARGE = "User/userDeposit"; //押金充值

    /**
     * 首页
     */

    String SELECT_BIKE = "bike/bikes"; //附近车辆
    String PARK_BIKE = "bike/parks"; //附件停车场
    String USER_INFO = "User/userDetail"; //资料详情
    String CAN_USE_CAR = "user/account"; //用户是否可以用车
    String CAR_STATUS = "bike/usingBike"; //获取车辆使用状态
    String IS_ORDER = "bike/home"; //查询用户是否有未完成订单

    /**
     * 侧边栏
     */
    String USER_BALANCE = "User/userBalanceList"; //用户钱包|账户
    String RECHARGE = "user/userRecharge"; //用户充值
    String WITHDRAW = "user/userCashout"; //用户提现
    String BANK_LIST = "User/userBankList"; //银行卡列表
    String ADD_BANK = "User/userBankUpdate"; //添加银行卡
    String UPLOAD_HEAD = "User/userHeaderUrl"; //上传头像
    String MODIFY_NAME = "User/updUserNickname"; //修改用户昵称
    String COUPON_LIST = "User/userCouponList"; //优惠券列表
    String MSG_LIST = "User/userMessageList"; //信息列表
    String TRIP_LIST = "bike/orders"; //我的行程
    String TRIP_DETAILS = "bike/orderInfo"; //行程详情

    /**
     * 用车
     */
    String GET_CAR_ELECTRICITY = "bike/bikeInfo"; //获取车辆信息
    String OPEN_CAR = "bike/open"; //开锁，开始骑车
    String CLOSE_CAR = "bike/close"; //还车
    String PAY_ORDER = "bike/pay"; //行程订单支付
    String PAY_DETAILS = "bike/payOrderInfo"; //支付订单详情


    /**
     * 反馈
     */

    /**
     * 我的
     */
}
