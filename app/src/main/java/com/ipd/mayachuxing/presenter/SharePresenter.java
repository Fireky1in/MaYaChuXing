package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.ShareBean;
import com.ipd.mayachuxing.contract.ShareContract;
import com.ipd.mayachuxing.model.ShareModel;
import com.ipd.mayachuxing.progress.ObserverResponseListener;
import com.ipd.mayachuxing.utils.ExceptionHandle;
import com.ipd.mayachuxing.utils.ToastUtil;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class SharePresenter extends ShareContract.Presenter {

    private ShareModel model;
    private Context context;

    public SharePresenter(Context context) {
        this.model = new ShareModel();
        this.context = context;
    }

    @Override
    public void getShare(boolean isDialog, boolean cancelable) {
        model.getShare(context, isDialog, cancelable, new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultShare((ShareBean) o);
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