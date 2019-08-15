package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.FeedBackBean;
import com.ipd.mayachuxing.bean.UploadImgBean;
import com.ipd.mayachuxing.contract.FeedBackContract;
import com.ipd.mayachuxing.model.FeedBackModel;
import com.ipd.mayachuxing.progress.ObserverResponseListener;
import com.ipd.mayachuxing.utils.ExceptionHandle;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.TreeMap;

import okhttp3.RequestBody;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class FeedBackPresenter extends FeedBackContract.Presenter {

    private FeedBackModel model;
    private Context context;

    public FeedBackPresenter(Context context) {
        this.model = new FeedBackModel();
        this.context = context;
    }

    @Override
    public void getUploadImg(TreeMap<String, RequestBody> map, boolean isDialog, boolean cancelable) {
        model.getUploadImg(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultUploadImg((UploadImgBean) o);
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

    @Override
    public void getFeedBack(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getFeedBack(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultFeedBack((FeedBackBean) o);
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