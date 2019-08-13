package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.AddBankBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.AddBankContract;
import com.ipd.mayachuxing.presenter.AddBankPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;

/**
 * Description ：添加银行卡
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class AddBankActivity extends BaseActivity<AddBankContract.View, AddBankContract.Presenter> implements AddBankContract.View {

    @BindView(R.id.tv_add_bank)
    TopView tvAddBank;
    @BindView(R.id.tv_open_bank)
    SuperTextView tvOpenBank;
    @BindView(R.id.tv_city)
    SuperTextView tvCity;
    @BindView(R.id.tv_bank_code)
    SuperTextView tvBankCode;
    @BindView(R.id.tv_user_name)
    SuperTextView tvUserName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_bank;
    }

    @Override
    public AddBankContract.Presenter createPresenter() {
        return new AddBankPresenter(this);
    }

    @Override
    public AddBankContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAddBank);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_open_bank, R.id.rv_add_bank})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_open_bank:
                break;
            case R.id.rv_add_bank:
                if (!isEmpty(tvOpenBank.getCenterString()) && !isEmpty(tvBankCode.getCenterEditValue()) && !isEmpty(tvCity.getCenterEditValue()) && !isEmpty(tvUserName.getCenterEditValue())) {
                    TreeMap<String, String> addBankMap = new TreeMap<>();
                    addBankMap.put("bank", tvOpenBank.getCenterString());
                    addBankMap.put("card", tvBankCode.getCenterEditValue());
                    addBankMap.put("area", tvCity.getCenterEditValue());
                    addBankMap.put("name", tvUserName.getCenterEditValue());
                    getPresenter().getAddBank(addBankMap, false, false);
                }
                break;
        }
    }

    @Override
    public void resultAddBank(AddBankBean data) {
        if (data.getCode() == 200)
            startActivity(new Intent(this, WithdrawTypeActivity.class));
        else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
