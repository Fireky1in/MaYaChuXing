package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.IuthenticationBean;
import com.ipd.mayachuxing.contract.IuthenticationContract;
import com.ipd.mayachuxing.model.IuthenticationModel;
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
public class IuthenticationPresenter extends IuthenticationContract.Presenter {

    private IuthenticationModel model;
    private Context context;

    public IuthenticationPresenter(Context context) {
        this.model = new IuthenticationModel();
        this.context = context;
    }

    @Override
    public void getIuthentication(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getIuthentication(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultIuthentication((IuthenticationBean) o);
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