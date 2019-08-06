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
 * Description ：付款
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class PayActivity extends BaseActivity {

    @BindView(R.id.tv_pay)
    TopView tvPay;
    @BindView(R.id.tv_use_distance)
    SuperTextView tvUseDistance;
    @BindView(R.id.tv_use_time)
    SuperTextView tvUseTime;
    @BindView(R.id.tv_use_preferential)
    SuperTextView tvUsePreferential;
    @BindView(R.id.tv_use_coupon)
    SuperTextView tvUseCoupon;
    @BindView(R.id.tv_sum_fee)
    SuperTextView tvSumFee;
    @BindView(R.id.tv_balance_pay)
    SuperTextView tvBalancePay;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
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
        ImmersionBar.setTitleBar(this, tvPay);

    }

    @Override
    public void initData() {
        tvUseDistance.setRightString("1km");
        tvUseTime.setRightString("8分54秒");
        tvUsePreferential.setRightString("0元");
        tvUseCoupon.setRightString("-5元");
        tvSumFee.setRightString("3元");
        tvBalancePay.setCenterString("余额: ¥568.00");
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_use_coupon, R.id.tv_balance_pay, R.id.rv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_use_coupon:
                break;
            case R.id.tv_balance_pay:
                break;
            case R.id.rv_pay:
                startActivity(new Intent(this, UseEndActivity.class));
                finish();
                break;
        }
    }
}
