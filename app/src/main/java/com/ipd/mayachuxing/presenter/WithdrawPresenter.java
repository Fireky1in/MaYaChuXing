package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.WithdrawBean;
import com.ipd.mayachuxing.contract.WithdrawContract;
import com.ipd.mayachuxing.model.WithdrawModel;
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
public class WithdrawPresenter extends WithdrawContract.Presenter {

    private WithdrawModel model;
    private Context context;

    public WithdrawPresenter(Context context) {
        this.model = new WithdrawModel();
        this.context = context;
    }

    @Override
    public void getWithdraw(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getWithdraw(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultWithdraw((WithdrawBean) o);
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