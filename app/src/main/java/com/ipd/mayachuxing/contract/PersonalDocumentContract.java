package com.ipd.mayachuxing.contract;

import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.bean.ModifyNameBean;
import com.ipd.mayachuxing.bean.UploadHeadBean;
import com.ipd.mayachuxing.bean.UserInfoBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface PersonalDocumentContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultUploadHead(UploadHeadBean data);

        void resultModifyName(ModifyNameBean data);

        void resultUserInfo(UserInfoBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getUploadHead(TreeMap<String, RequestBody> map, boolean isDialog, boolean cancelable);

        public abstract void getModifyName(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getUserInfo(boolean isDialog, boolean cancelable);
    }
}