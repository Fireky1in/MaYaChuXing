package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：充值结果
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class RechargeTypeActivity extends BaseActivity {

    @BindView(R.id.tv_recharge_type)
    TopView tvRechargeType;
    @BindView(R.id.tv_recharge_type_tx)
    AppCompatTextView tvRechargeTypeTx;
    @BindView(R.id.tv_recharge_fee)
    AppCompatTextView tvRechargeFee;

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_type;
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
        ImmersionBar.setTitleBar(this, tvRechargeType);
    }

    @Override
    public void initData() {
        tvRechargeTypeTx.setText("充值成功");
        tvRechargeFee.setText(Html.fromHtml("您已充值成功 <font color=\"#FF5555\">" + 200 + "元</font>"));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick({R.id.ll_top_back, R.id.rv_recharge_type})
    public void onViewClicked(View view) {
        if (isFastClick()) {
            switch (view.getId()) {
                case R.id.ll_top_back:
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    break;
                case R.id.rv_recharge_type:
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    break;
            }
        }
    }
}
