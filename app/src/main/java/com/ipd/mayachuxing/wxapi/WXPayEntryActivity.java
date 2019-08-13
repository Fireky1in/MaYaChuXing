package com.ipd.mayachuxing.wxapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatTextView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.activity.MainActivity;
import com.ipd.mayachuxing.activity.RechargeActivity;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.L;
import com.ipd.mayachuxing.utils.SPUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.common.config.IConstants.RECHARGE_MONEY;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    @BindView(R.id.tv_recharge_type)
    TopView tvRechargeType;
    @BindView(R.id.tv_recharge_type_tx)
    AppCompatTextView tvRechargeTypeTx;
    @BindView(R.id.tv_recharge_fee)
    AppCompatTextView tvRechargeFee;

    private int payStatus;//1 : 成功，2 :失败

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;

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

        api = WXAPIFactory.createWXAPI(this, "wx7f5f66aed474d3fb");
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onResp(BaseResp resp) {
        L.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.errCode == 0) {
            tvRechargeTypeTx.setText("充值成功");
            tvRechargeFee.setText(Html.fromHtml("您已充值成功 <font color=\"#FF5555\">" + SPUtil.get(this, RECHARGE_MONEY, "") + "元</font>"));
            payStatus = 1;
        } else {
            finish();
//            tvRechargeTypeTx.setText("充值失败");
//            payStatus = 2;
        }
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("支付结果");
//            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//            builder.show();
//        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @OnClick({R.id.ll_top_back, R.id.rv_recharge_type})
    public void onViewClicked(View view) {
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
