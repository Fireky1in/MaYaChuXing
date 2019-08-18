package com.ipd.mayachuxing.contract;

import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.bean.BankListBean;
import com.ipd.mayachuxing.bean.DelBankBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：MemberCenterContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/4/2.
 */
public interface BankListContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void resultBankList(BankListBean data);

        void resultDelBank(DelBankBean data);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getBankList(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void getDelBank(TreeMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}