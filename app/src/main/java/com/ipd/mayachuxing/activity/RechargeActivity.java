package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;

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

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：充值
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class RechargeActivity extends BaseActivity {

    @BindView(R.id.tv_recharge)
    TopView tvRecharge;
    @BindView(R.id.rb_fee_1)
    RadioButton rbFee1;
    @BindView(R.id.rb_fee_2)
    RadioButton rbFee2;
    @BindView(R.id.rb_fee_3)
    RadioButton rbFee3;
    @BindView(R.id.rb_fee_4)
    RadioButton rbFee4;
    @BindView(R.id.rb_fee_5)
    RadioButton rbFee5;
    @BindView(R.id.rb_fee_6)
    RadioButton rbFee6;
    @BindView(R.id.tv_ali_pay)
    SuperTextView tvAliPay;
    @BindView(R.id.tv_wechat_pay)
    SuperTextView tvWechatPay;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
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
        ImmersionBar.setTitleBar(this, tvRecharge);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_ali_pay, R.id.tv_wechat_pay, R.id.rv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ali_pay:
                tvAliPay.setRightIcon(R.drawable.ic_check_yellow);
                tvWechatPay.setRightIcon(0);
                break;
            case R.id.tv_wechat_pay:
                tvAliPay.setRightIcon(0);
                tvWechatPay.setRightIcon(R.drawable.ic_check_yellow);
                break;
            case R.id.rv_recharge:
                if (isFastClick()) {
                    startActivity(new Intent(this, RechargeTypeActivity.class));
                    finish();
                }
                break;
        }
    }
}
