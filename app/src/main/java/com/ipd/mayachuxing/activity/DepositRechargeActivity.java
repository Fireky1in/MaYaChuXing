package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.aliPay.AliPay;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.DepositRechargeBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.DepositRechargeContract;
import com.ipd.mayachuxing.presenter.DepositRechargePresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.common.config.IConstants.RECHARGE_MONEY;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：账户充值
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/12.
 */
public class DepositRechargeActivity extends BaseActivity<DepositRechargeContract.View, DepositRechargeContract.Presenter> implements DepositRechargeContract.View {

    @BindView(R.id.tv_deposit_recharge)
    TopView tvDepositRecharge;
    @BindView(R.id.tv_ali_pay)
    SuperTextView tvAliPay;
    @BindView(R.id.tv_wechat_pay)
    SuperTextView tvWechatPay;
    @BindView(R.id.tv_recharge_protocol)
    SuperTextView tvRechargeProtocol;

    @Override
    public int getLayoutId() {
        return R.layout.activity_deposit_recharge;
    }

    @Override
    public DepositRechargeContract.Presenter createPresenter() {
        return new DepositRechargePresenter(this);
    }

    @Override
    public DepositRechargeContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvDepositRecharge);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        tvRechargeProtocol.setRightTvClickListener(new SuperTextView.OnRightTvClickListener() {
            @Override
            public void onClickListener() {
                startActivity(new Intent(DepositRechargeActivity.this, WebViewActivity.class).putExtra("h5Type", 0));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick({R.id.ll_top_back, R.id.tv_ali_pay, R.id.tv_wechat_pay, R.id.rv_deposit_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_top_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.tv_ali_pay:
                tvAliPay.setRightIcon(R.drawable.ic_check_yellow);
                tvWechatPay.setRightIcon(0);
                break;
            case R.id.tv_wechat_pay:
                tvAliPay.setRightIcon(0);
                tvWechatPay.setRightIcon(R.drawable.ic_check_yellow);
                break;
            case R.id.rv_deposit_recharge:
                if (isFastClick()) {
                    TreeMap<String, String> depositRechargeMap = new TreeMap<>();
                    depositRechargeMap.put("type", tvAliPay.getRightIconIV().getDrawable() != null ? "1" : "2");
                    getPresenter().getDepositRecharge(depositRechargeMap, true, false);
                }
                break;
        }
    }

    @Override
    public void resultDepositRecharge(DepositRechargeBean data) {
        if (data.getCode() == 200) {
            if (tvAliPay.getRightIconIV().getDrawable() != null)
                new AliPay(this, data.getData().getPay_string(), 299);
            else {
                SPUtil.put(this, RECHARGE_MONEY, 299);

                IWXAPI api = WXAPIFactory.createWXAPI(this, null);
                api.registerApp(data.getData().getAppid());
                PayReq req = new PayReq();
                req.appId = data.getData().getAppid();//你的微信appid
                req.partnerId = data.getData().getPartnerid();//商户号
                req.prepayId = data.getData().getPrepayid();//预支付交易会话ID
                req.nonceStr = data.getData().getNoncestr();//随机字符串
                req.timeStamp = data.getData().getTimestamp() + "";//时间戳
                req.packageValue = data.getData().getPackageX(); //扩展字段, 这里固定填写Sign = WXPay
                req.sign = data.getData().getSign();//签名
                //  req.extData         = "app data"; // optional
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            }
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
