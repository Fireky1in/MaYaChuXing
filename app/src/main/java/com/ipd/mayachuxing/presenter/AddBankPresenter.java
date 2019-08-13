package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.AddBankBean;
import com.ipd.mayachuxing.contract.AddBankContract;
import com.ipd.mayachuxing.model.AddBankModel;
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
public class AddBankPresenter extends AddBankContract.Presenter {

    private AddBankModel model;
    private Context context;

    public AddBankPresenter(Context context) {
        this.model = new AddBankModel();
        this.context = context;
    }

    @Override
    public void getAddBank(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getAddBank(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultAddBank((AddBankBean) o);
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