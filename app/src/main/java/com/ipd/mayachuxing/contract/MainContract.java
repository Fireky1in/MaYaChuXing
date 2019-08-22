package com.ipd.mayachuxing.contract;

import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.bean.CanUseCarBean;
import com.ipd.mayachuxing.bean.CarStatusBean;
import com.ipd.mayachuxing.bean.CloseCarBean;
import com.ipd.mayachuxing.bean.IsOrderBean;
import com.ipd.mayachuxing.bean.IsStopCarBean;
import com.ipd.mayachuxing.bean.LockCarBean;
import com.ipd.mayachuxing.bean.ParkBikeBean;
import com.ipd.mayachuxing.bean.SelectBikeBean;
import com.ipd.mayachuxing.bean.UnlockCarBean;
import com.ipd.mayachuxing.bean.UserInfoBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface MainContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultSelectBike(SelectBikeBean data);

        void resultParkBike(ParkBikeBean data);

        void resultUserInfo(UserInfoBean data);

        void resultCanUseCar(CanUseCarBean data);

        void resultCarStatus(CarStatusBean data);

        void resultCloseCar(CloseCarBean data);

        void resultIsOrder(IsOrderBean data);

        void resultLockCar(LockCarBean data);

        void resultUnlockCar(UnlockCarBean data);

        void resultIsStopCar(IsStopCarBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getSelectBike(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getParkBike(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getUserInfo(boolean isDialog, boolean cancelable);

        public abstract void getCanUseCar(boolean isDialog, boolean cancelable);

        public abstract void getCarStatus(boolean isDialog, boolean cancelable);

        public abstract void getCloseCar(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getIsOrder(boolean isDialog, boolean cancelable);

        public abstract void getLockCar(boolean isDialog, boolean cancelable);

        public abstract void getUnlockCar(boolean isDialog, boolean cancelable);

        public abstract void getIsStopCar(TreeMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}