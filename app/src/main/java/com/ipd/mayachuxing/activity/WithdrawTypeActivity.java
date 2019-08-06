package com.ipd.mayachuxing.activity;

import android.content.Intent;

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
 * Description ：提现结果
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class WithdrawTypeActivity extends BaseActivity {

    @BindView(R.id.tv_withdraw_type)
    TopView tvWithdrawType;
    @BindView(R.id.tv_withdraw_type_tx)
    AppCompatTextView tvWithdrawTypeTx;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw_type;
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
        ImmersionBar.setTitleBar(this, tvWithdrawType);
    }

    @Override
    public void initData() {
        tvWithdrawTypeTx.setText("充值成功");
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.ll_top_back)
    public void onViewClicked() {
        if (isFastClick()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
