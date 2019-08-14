package com.ipd.mayachuxing.contract;

import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.bean.GetCarElectricityBean;
import com.ipd.mayachuxing.bean.OpenCarBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface GetCarElectricityContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultGetCarElectricity(GetCarElectricityBean data);

        void resultOpenCar(OpenCarBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getGetCarElectricity(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getOpenCar(TreeMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}