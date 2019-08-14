package com.ipd.mayachuxing.model;

import android.content.Context;

import com.ipd.mayachuxing.api.Api;
import com.ipd.mayachuxing.base.BaseModel;
import com.ipd.mayachuxing.progress.ObserverResponseListener;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class TripDetailsModel<T> extends BaseModel {

    public void getTripDetails(Context context, TreeMap<String, String> map, boolean isDialog, boolean cancelable,
                               ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().login(map), observerListener);
        paramSubscribe(context, Api.getApiService().getTripDetails(map), observerListener, transformer, isDialog, cancelable);
    }
    //// TODO: 2017/12/27 其他需要请求、数据库等等的操作
}
