package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_93;
import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：提现
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.tv_withdraw)
    TopView tvWithdraw;
    @BindView(R.id.tv_select_bank)
    SuperTextView tvSelectBank;
    @BindView(R.id.tv_service_fee)
    TextView tvServiceFee;
    @BindView(R.id.et_service_fee)
    EditText etServiceFee;

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
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
        ImmersionBar.setTitleBar(this, tvWithdraw);
    }

    @Override
    public void initData() {
        tvServiceFee.setText("提现金额（收取0.3%服务费）");
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case REQUEST_CODE_93:
                    tvSelectBank.setLeftTopString(data.getStringExtra("bank_name"));
                    tvSelectBank.setLeftBottomString(data.getStringExtra("bank_code"));
                    tvSelectBank.setLeftString("");
                    break;
            }
        }
    }

    @OnClick({R.id.tv_select_bank, R.id.rv_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_bank:
                startActivityForResult(new Intent(this, SelectBackActivity.class), REQUEST_CODE_93);
                break;
            case R.id.rv_withdraw:
                if (isFastClick()) {
                    if (!isEmpty(tvSelectBank.getLeftString()) && !isEmpty(etServiceFee.getText().toString().trim())) {
                        finish();
                    } else
                        ToastUtil.showLongToast("请选择银行卡或金额");
                }
                break;
        }
    }
}
