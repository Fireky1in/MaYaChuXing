package com.ipd.mayachuxing.presenter;

import android.content.Context;

import com.ipd.mayachuxing.bean.GetCarElectricityBean;
import com.ipd.mayachuxing.bean.OpenCarBean;
import com.ipd.mayachuxing.contract.GetCarElectricityContract;
import com.ipd.mayachuxing.model.GetCarElectricityModel;
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
public class GetCarElectricityPresenter extends GetCarElectricityContract.Presenter {

    private GetCarElectricityModel model;
    private Context context;

    public GetCarElectricityPresenter(Context context) {
        this.model = new GetCarElectricityModel();
        this.context = context;
    }

    @Override
    public void getGetCarElectricity(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getGetCarElectricity(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultGetCarElectricity((GetCarElectricityBean) o);
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
    public void getOpenCar(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getOpenCar(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultOpenCar((OpenCarBean) o);
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