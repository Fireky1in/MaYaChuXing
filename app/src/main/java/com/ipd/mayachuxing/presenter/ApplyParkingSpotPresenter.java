package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.ApplyParkingSpotBean;
import com.ipd.mayachuxing.bean.UploadImgBean;
import com.ipd.mayachuxing.contract.ApplyParkingSpotContract;
import com.ipd.mayachuxing.model.ApplyParkingSpotModel;
import com.ipd.mayachuxing.progress.ObserverResponseListener;
import com.ipd.mayachuxing.utils.ExceptionHandle;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.TreeMap;

import okhttp3.RequestBody;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class ApplyParkingSpotPresenter extends ApplyParkingSpotContract.Presenter {

    private ApplyParkingSpotModel model;
    private Context context;

    public ApplyParkingSpotPresenter(Context context) {
        this.model = new ApplyParkingSpotModel();
        this.context = context;
    }

    @Override
    public void getApplyParkingSpot(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getApplyParkingSpot(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultApplyParkingSpot((ApplyParkingSpotBean) o);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    //// TODO: 2017/12/28 自定义处理异常
                    ToastUtil.showShortToast(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }

    @Override
    public void getUploadImg(TreeMap<String, RequestBody> map, boolean isDialog, boolean cancelable) {
        model.getUploadImg(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultUploadImg((UploadImgBean) o);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    //// TODO: 2017/12/28 自定义处理异常
                    ToastUtil.showShortToast(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }
}