package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.TripListBean;
import com.ipd.mayachuxing.contract.TripListContract;
import com.ipd.mayachuxing.model.TripListModel;
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
public class TripListPresenter extends TripListContract.Presenter {

    private TripListModel model;
    private Context context;

    public TripListPresenter(Context context) {
        this.model = new TripListModel();
        this.context = context;
    }

    @Override
    public void getTripList(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getTripList(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultTripList((TripListBean) o);
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