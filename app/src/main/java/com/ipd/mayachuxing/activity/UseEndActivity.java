package com.ipd.mayachuxing.activity;

import androidx.appcompat.widget.AppCompatTextView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description ：骑行结束
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class UseEndActivity extends BaseActivity {

    @BindView(R.id.tv_use_end)
    TopView tvUseEnd;
    @BindView(R.id.tv_use_fee)
    SuperTextView tvUseFee;
    @BindView(R.id.tv_account_balance)
    AppCompatTextView tvAccountBalance;
    @BindView(R.id.bt_todo)
    SuperButton btTodo;
    @BindView(R.id.tv_use_distance)
    SuperTextView tvUseDistance;
    @BindView(R.id.tv_use_time)
    SuperTextView tvUseTime;
    @BindView(R.id.tv_use_preferential)
    SuperTextView tvUsePreferential;
    @BindView(R.id.tv_use_coupon)
    SuperTextView tvUseCoupon;

    @Override
    public int getLayoutId() {
        return R.layout.activity_use_end;
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
        ImmersionBar.setTitleBar(this, tvUseEnd);
    }

    @Override
    public void initData() {
        tvUseFee.setCenterString("3.00");
        tvAccountBalance.setText("账户余额: -3.00");
        btTodo.setText("去充值");
        tvUseDistance.setRightString("1km");
        tvUseTime.setRightString("8分54秒");
        tvUsePreferential.setRightString("0元");
        tvUseCoupon.setRightString("-5元");
    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.bt_todo)
    public void onViewClicked() {

    }
}
