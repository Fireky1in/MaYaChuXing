package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.CouponListBean;
import com.ipd.mayachuxing.contract.CouponListContract;
import com.ipd.mayachuxing.model.CouponListModel;
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
public class CouponListPresenter extends CouponListContract.Presenter {

    private CouponListModel model;
    private Context context;

    public CouponListPresenter(Context context) {
        this.model = new CouponListModel();
        this.context = context;
    }

    @Override
    public void getCouponList(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getCouponList(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultCouponList((CouponListBean) o);
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