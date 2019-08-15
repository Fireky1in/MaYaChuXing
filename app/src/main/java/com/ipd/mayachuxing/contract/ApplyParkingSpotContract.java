package com.ipd.mayachuxing.contract;

import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.bean.ApplyParkingSpotBean;
import com.ipd.mayachuxing.bean.UploadImgBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface ApplyParkingSpotContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultApplyParkingSpot(ApplyParkingSpotBean data);

        void resultUploadImg(UploadImgBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getApplyParkingSpot(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getUploadImg(TreeMap<String, RequestBody> map, boolean isDialog, boolean cancelable);
    }
}