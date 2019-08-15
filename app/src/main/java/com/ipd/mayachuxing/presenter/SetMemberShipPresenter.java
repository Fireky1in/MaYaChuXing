package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.SetMemberShipBean;
import com.ipd.mayachuxing.contract.SetMemberShipContract;
import com.ipd.mayachuxing.model.SetMemberShipModel;
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
public class SetMemberShipPresenter extends SetMemberShipContract.Presenter {

    private SetMemberShipModel model;
    private Context context;

    public SetMemberShipPresenter(Context context) {
        this.model = new SetMemberShipModel();
        this.context = context;
    }

    @Override
    public void getSetMemberShip(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getSetMemberShip(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultSetMemberShip((SetMemberShipBean) o);
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