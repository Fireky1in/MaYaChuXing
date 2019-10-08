package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.PayDetailsBean;
import com.ipd.mayachuxing.bean.PayOrderBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.PayOrderContract;
import com.ipd.mayachuxing.presenter.PayOrderPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.ArrayList;
import java.util.List;
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
    private List<PayDetailsBean.DataBean.CouponsBean> couponsBeanList = new ArrayList<>();
    private double payFee;//最终支付金额

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

//        useTime = getIntent().getStringExtra("time");
//        useMoney = getIntent().getDoubleExtra("money", 0);
    }

    @Override
    public void initData() {
        getPresenter().getPayDetails(false, false);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case REQUEST_CODE_97:
                    couponId = data.getIntExtra("couponId", 0);
                    couponMoney = data.getIntExtra("couponMoney", 0);

                    tvUseCoupon.setRightString("-" + couponMoney + "元");
                    tvSumFee.setRightString(payFee - couponMoney + "元");
                    break;
            }
        }
    }

    @OnClick({R.id.tv_use_coupon, R.id.tv_balance_pay, R.id.rv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_use_coupon:
                startActivityForResult(new Intent(this, CouponActivity.class).putParcelableArrayListExtra("couponsBeanList", (ArrayList<? extends Parcelable>) couponsBeanList), REQUEST_CODE_97);
                break;
            case R.id.tv_balance_pay:
                break;
            case R.id.rv_pay:
                TreeMap<String, String> payOrderMap = new TreeMap<>();
                payOrderMap.put("coupon_id", couponId + "");
                getPresenter().getPayOrder(payOrderMap, false, false);
                break;
        }
    }

    @Override
    public void resultPayOrder(PayOrderBean data) {
        if (data.getCode() == 200) {
            finish();
        } else {
            ToastUtil.showLongToast(data.getMessage());
            if (data.getCode() == 203) {
                ApplicationUtil.getManager().finishActivity(MainActivity.class);
                //清除所有临时储存
                SPUtil.clear(ApplicationUtil.getContext());
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
    }

    @Override
    public void resultPayDetails(PayDetailsBean data) {
        if (data.getCode() == 200) {
            couponsBeanList.addAll(data.getData().getCoupons());
//        tvUseDistance.setRightString("1km");
            tvUseTime.setRightString(data.getData().getTime());
            tvUsePreferential.setRightString(data.getData().getActivity_money() + "元");
            tvUseCoupon.setRightString("-" + 0 + "元");
            payFee = Double.parseDouble(data.getData().getMoney()) - Double.parseDouble(data.getData().getActivity_money());
            tvSumFee.setRightString(Double.parseDouble(data.getData().getMoney()) - Double.parseDouble(data.getData().getActivity_money()) + "元");
            tvBalancePay.setCenterString("余额: ¥" + data.getData().getBalance());
        } else {
            ToastUtil.showLongToast(data.getMessage());
            if (data.getCode() == 203) {
                ApplicationUtil.getManager().finishActivity(MainActivity.class);
                //清除所有临时储存
                SPUtil.clear(ApplicationUtil.getContext());
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
