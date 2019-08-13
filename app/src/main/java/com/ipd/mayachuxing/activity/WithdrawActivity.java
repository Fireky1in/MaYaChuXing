package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.WithdrawBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.WithdrawContract;
import com.ipd.mayachuxing.presenter.WithdrawPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_93;
import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：提现
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class WithdrawActivity extends BaseActivity<WithdrawContract.View, WithdrawContract.Presenter> implements WithdrawContract.View {

    @BindView(R.id.tv_withdraw)
    TopView tvWithdraw;
    @BindView(R.id.tv_select_bank)
    SuperTextView tvSelectBank;
    @BindView(R.id.tv_service_fee)
    TextView tvServiceFee;
    @BindView(R.id.et_service_fee)
    EditText etServiceFee;

    private int bankId; //银行卡id

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    public WithdrawContract.Presenter createPresenter() {
        return new WithdrawPresenter(this);
    }

    @Override
    public WithdrawContract.View createView() {
        return this;
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
                    bankId = data.getIntExtra("bank_id", 0);
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
                    if (!isEmpty(tvSelectBank.getLeftTopString()) && !isEmpty(etServiceFee.getText().toString().trim())) {
                        TreeMap<String, String> withdrawMap = new TreeMap<>();
                        withdrawMap.put("bid", bankId + "");
                        withdrawMap.put("num", etServiceFee.getText().toString().trim());
                        getPresenter().getWithdraw(withdrawMap, false, false);
                    } else
                        ToastUtil.showLongToast("请选择银行卡或金额");
                }
                break;
        }
    }

    @Override
    public void resultWithdraw(WithdrawBean data) {
        finish();
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
