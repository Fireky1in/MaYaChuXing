package com.ipd.mayachuxing.activity;

import android.view.View;
import android.widget.RadioButton;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.aliPay.AliPay;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.RechargeBean;
import com.ipd.mayachuxing.common.view.GridRadioGroup;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.RechargeContract;
import com.ipd.mayachuxing.presenter.RechargePresenter;
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
 * Description ：充值
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class RechargeActivity extends BaseActivity<RechargeContract.View, RechargeContract.Presenter> implements RechargeContract.View {

    @BindView(R.id.tv_recharge)
    TopView tvRecharge;
    @BindView(R.id.rg_fee)
    GridRadioGroup rgFee;
    @BindView(R.id.rb_fee_1)
    RadioButton rbFee1;
    @BindView(R.id.rb_fee_2)
    RadioButton rbFee2;
    @BindView(R.id.rb_fee_3)
    RadioButton rbFee3;
    @BindView(R.id.rb_fee_4)
    RadioButton rbFee4;
    @BindView(R.id.rb_fee_5)
    RadioButton rbFee5;
    @BindView(R.id.rb_fee_6)
    RadioButton rbFee6;
    @BindView(R.id.tv_ali_pay)
    SuperTextView tvAliPay;
    @BindView(R.id.tv_wechat_pay)
    SuperTextView tvWechatPay;

    private int money = 0;//充值金额

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public RechargeContract.Presenter createPresenter() {
        return new RechargePresenter(this);
    }

    @Override
    public RechargeContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvRecharge);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_ali_pay, R.id.tv_wechat_pay, R.id.rv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ali_pay:
                tvAliPay.setRightIcon(R.drawable.ic_check_yellow);
                tvWechatPay.setRightIcon(0);
                break;
            case R.id.tv_wechat_pay:
                tvAliPay.setRightIcon(0);
                tvWechatPay.setRightIcon(R.drawable.ic_check_yellow);
                break;
            case R.id.rv_recharge:
                if (isFastClick()) {
                    TreeMap<String, String> rechargeMap = new TreeMap<>();
                    switch (rgFee.flag) {
                        case 1:
                            money = 600;
                            break;
                        case 2:
                            money = 200;
                            break;
                        case 3:
                            money = 100;
                            break;
                        case 4:
                            money = 50;
                            break;
                        case 5:
                            money = 30;
                            break;
                        case 6:
                            money = 10;
                            break;
                    }
                    rechargeMap.put("num", money + "");
                    rechargeMap.put("type", tvAliPay.getRightIconIV().getDrawable() != null ? "1" : "2");
                    getPresenter().getRecharge(rechargeMap, false, false);
                }
                break;
        }
    }

    @Override
    public void resultRecharge(RechargeBean data) {
        if (data.getCode() == 200) {
            if (tvAliPay.getRightIconIV().getDrawable() != null)
                new AliPay(this, data.getData().getPay_string(), money);
            else {
                SPUtil.put(this, RECHARGE_MONEY, money);

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
