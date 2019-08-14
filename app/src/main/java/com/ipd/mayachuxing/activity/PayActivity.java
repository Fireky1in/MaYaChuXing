package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.PayOrderBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.PayOrderContract;
import com.ipd.mayachuxing.presenter.PayOrderPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_97;

/**
 * Description ：付款
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class PayActivity extends BaseActivity<PayOrderContract.View, PayOrderContract.Presenter> implements PayOrderContract.View {

    @BindView(R.id.tv_pay)
    TopView tvPay;
    //    @BindView(R.id.tv_use_distance)
//    SuperTextView tvUseDistance;
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

    private int couponId = 0;//券ID
    private int couponMoney = 0;//券金额
    private String useTime;//用车时长
    private double useMoney;//用车金额

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public PayOrderContract.Presenter createPresenter() {
        return new PayOrderPresenter(this);
    }

    @Override
    public PayOrderContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvPay);

        useTime = getIntent().getStringExtra("time");
        useMoney = getIntent().getDoubleExtra("money", 0);
    }

    @Override
    public void initData() {
//        tvUseDistance.setRightString("1km");
        tvUseTime.setRightString(useTime);
        tvUsePreferential.setRightString("0元");
        tvUseCoupon.setRightString("-" + couponMoney + "元");
        tvSumFee.setRightString(useMoney - couponMoney + "元");
        tvBalancePay.setCenterString("余额: ¥" + 10.00);
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_use_coupon, R.id.tv_balance_pay, R.id.rv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_use_coupon:
                startActivityForResult(new Intent(this, CouponActivity.class), REQUEST_CODE_97);
                break;
            case R.id.tv_balance_pay:
                break;
            case R.id.rv_pay:
                TreeMap<String, String> payOrderMap = new TreeMap<>();
                payOrderMap.put("coupon_id", couponId + "");
                getPresenter().getPayOrder(payOrderMap, true, false);
                break;
        }
    }

    @Override
    public void resultPayOrder(PayOrderBean data) {
        if (data.getCode() == 200) {
            startActivity(new Intent(this, UseEndActivity.class));
            finish();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
