package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.TripDetailsBean;
import com.ipd.mayachuxing.contract.TripDetailsContract;
import com.ipd.mayachuxing.model.TripDetailsModel;
import com.ipd.mayachuxing.progress.ObserverResponseListener;
import com.ipd.mayachuxing.utils.ExceptionHandle;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.TreeMap;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class TripDetailsPresenter extends TripDetailsContract.Presenter {

    private TripDetailsModel model;
    private Context context;

    public TripDetailsPresenter(Context context) {
        this.model = new TripDetailsModel();
        this.context = context;
    }

    @Override
    public void getTripDetails(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getTripDetails(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultTripDetails((TripDetailsBean) o);
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