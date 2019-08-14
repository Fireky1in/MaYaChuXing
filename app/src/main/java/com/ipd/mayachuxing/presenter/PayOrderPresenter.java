package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.PayOrderBean;
import com.ipd.mayachuxing.contract.PayOrderContract;
import com.ipd.mayachuxing.model.PayOrderModel;
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
public class PayOrderPresenter extends PayOrderContract.Presenter {

    private PayOrderModel model;
    private Context context;

    public PayOrderPresenter(Context context) {
        this.model = new PayOrderModel();
        this.context = context;
    }

    @Override
    public void getPayOrder(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getPayOrder(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultPayOrder((PayOrderBean) o);
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