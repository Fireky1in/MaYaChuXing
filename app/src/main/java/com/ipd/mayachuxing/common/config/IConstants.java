package com.ipd.mayachuxing.common.config;

/**
 * Description ：公共配置类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public interface IConstants {
    /**
     * 包名
     */
    String PACKAGE_NAME = "com.ipd.mayachuxing";

    /**
     * SharedPreferences
     * 共享参数
     */
    String FIRST_APP = "is_first"; //第一次进应用
    String IS_LOGIN = "is_login"; //已经登录
    String TOKEN = "is_token"; //token
    String IUTHENTICATION = "is_iuthentication"; //认证
    String USER_ID = "user_id"; //用户标识
    String NAME = "name"; //用户真实姓名
    String PHONE = "phone"; //用户手机号码
    String AVATAR = "avatar"; //头像
    String SEX = "sex"; //性别
    String AGE = "age"; //年龄
    String RECHARGE_MONEY = "recharge_money";
    String CITY = "city"; //城市
    String ADDRESS = "address"; //详细位置
    String SERVICE_PHONE = "4006520799"; //客服电话
    String SERVICE_E_MALL = "xmkefu8@qq.com"; //客服邮箱
    String HOW_PAGE = "how_page"; //未登录时点击了首页的哪个Fragment，登录后就跳到哪个Fragment
    int JPUSH_SEQUENCE = 100; //极光精准推送序列

    /**
     * requestCode
     * 请求码
     */
    int REQUEST_CODE_90 = 90;//扫描开锁的回跳
    int REQUEST_CODE_91 = 91;//修改个人资料的回跳
    int REQUEST_CODE_92 = 92;//修改昵称的回跳
    int REQUEST_CODE_93 = 93;//选择银行卡回跳
    int REQUEST_CODE_94 = 94;//添加银行卡回跳
    int REQUEST_CODE_95 = 95;//搜索回跳首页
    int REQUEST_CODE_96 = 96;//搜索回跳申请还车点
    int REQUEST_CODE_97 = 97;//选择优惠券回跳
    int REQUEST_CODE_98 = 98;//开不了锁扫码回跳
    int REQUEST_CODE_99 = 99;//上报故障扫码回跳
    int REQUEST_CODE_100 = 100;//我要举报扫码回跳
    int REQUEST_CODE_101 = 101;
    int REQUEST_CODE_102 = 102;
    int REQUEST_CODE_103 = 103;
    int REQUEST_CODE_104 = 104;
    int REQUEST_CODE_105 = 105;

    /**
     * resultCode
     * 返回码
     */
    int RESULT_CODE = 0;
}
