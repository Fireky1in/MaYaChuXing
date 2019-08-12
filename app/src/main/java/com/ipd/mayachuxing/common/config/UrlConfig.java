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

    /**
     * 首页
     */

    String SELECT_BIKE = "bike/bikes"; //附近车辆
    String PARK_BIKE = "bike/parks"; //附件停车场

    /**
     * 课程
     */

    /**
     * 发布
     */

    /**
     * 反馈
     */

    /**
     * 我的
     */
}
