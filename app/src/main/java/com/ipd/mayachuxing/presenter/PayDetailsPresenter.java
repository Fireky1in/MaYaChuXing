package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.PayDetailsBean;
import com.ipd.mayachuxing.contract.PayDetailsContract;
import com.ipd.mayachuxing.model.PayDetailsModel;
import com.ipd.mayachuxing.progress.ObserverResponseListener;
import com.ipd.mayachuxing.utils.ExceptionHandle;
import com.ipd.mayachuxing.utils.ToastUtil;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class PayDetailsPresenter extends PayDetailsContract.Presenter {

    private PayDetailsModel model;
    private Context context;

    public PayDetailsPresenter(Context context) {
        this.model = new PayDetailsModel();
        this.context = context;
    }

    @Override
    public void getPayDetails(boolean isDialog, boolean cancelable) {
        model.getPayDetails(context, isDialog, cancelable, new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultPayDetails((PayDetailsBean) o);
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