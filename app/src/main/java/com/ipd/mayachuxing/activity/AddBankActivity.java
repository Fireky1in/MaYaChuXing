package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description ：添加银行卡
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class AddBankActivity extends BaseActivity {

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
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
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
                startActivity(new Intent(this, WithdrawTypeActivity.class));
                break;
        }
    }
}
