package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.CaptchaBean;
import com.ipd.mayachuxing.bean.LoginBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.LoginContract;
import com.ipd.mayachuxing.presenter.LoginPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.common.config.IConstants.IS_LOGIN;
import static com.ipd.mayachuxing.common.config.IConstants.IUTHENTICATION;
import static com.ipd.mayachuxing.common.config.IConstants.TOKEN;
import static com.ipd.mayachuxing.utils.VerifyUtils.isMobileNumber;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：登录
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/3.
 */
public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_login_back)
    TopView tvLoginBack;
    @BindView(R.id.iv_top_back)
    ImageView ivTopBack;
    @BindView(R.id.et_phone)
    MaterialEditText etPhone;
    @BindView(R.id.et_captcha)
    MaterialEditText etCaptcha;
    @BindView(R.id.bt_captcha)
    SuperButton btCaptcha;
    @BindView(R.id.cb_login)
    MaterialCheckBox cbLogin;

    private CountDownButtonHelper mCountDownHelper; //倒计时

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.with(this).statusBarDarkFont(false).init();
        ImmersionBar.setTitleBar(this, tvLoginBack);

        ivTopBack.setImageResource(R.drawable.ic_back_white);
        mCountDownHelper = new CountDownButtonHelper(btCaptcha, 60);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onDestroy() {
        mCountDownHelper.cancel();
        super.onDestroy();
    }

    @OnClick({R.id.bt_captcha, R.id.bt_agreement, R.id.rv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_captcha:
                if (etPhone.getText().toString().trim().length() == 11 && isMobileNumber(etPhone.getText().toString().trim())) {
                    TreeMap<String, String> captchaMap = new TreeMap<>();
                    captchaMap.put("phone", etPhone.getText().toString().trim());
                    captchaMap.put("static", "1");
                    getPresenter().getCaptcha(captchaMap, false, false);
                    mCountDownHelper.start();
                } else
                    ToastUtil.showLongToast("请填写手机号码");
                break;
            case R.id.bt_agreement:
                startActivity(new Intent(LoginActivity.this, WebViewActivity.class).putExtra("h5Type", 3));
                break;
            case R.id.rv_login:
                if (isFastClick()) {
                    if (cbLogin.isChecked()) {
                        if (etPhone.getText().toString().trim().length() == 11 && isMobileNumber(etPhone.getText().toString().trim()) && etCaptcha.getText().toString().trim().length() == 6) {
                            TreeMap<String, String> loginMap = new TreeMap<>();
                            loginMap.put("phone", etPhone.getText().toString().trim());
                            loginMap.put("code", etCaptcha.getText().toString().trim());
                            getPresenter().getLogin(loginMap, false, false);
                        } else
                            ToastUtil.showLongToast("请填写正确的登录信息");
                    } else
                        ToastUtil.showLongToast("请同意用户协议!");
                }
                break;
        }
    }

    @Override
    public void resultCaptcha(CaptchaBean data) {
        ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultLogin(LoginBean data) {
        if (data.getCode() == 200) {
            ApplicationUtil.getManager().finishActivity(MainActivity.class);
            SPUtil.put(this, IS_LOGIN, "is_login");
            SPUtil.put(this, TOKEN, data.getData().getToken());
            SPUtil.put(this, IUTHENTICATION, "is_iuthentication");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (data.getCode() == 20006) {
            SPUtil.put(this, IS_LOGIN, "is_login");
            SPUtil.put(this, TOKEN, data.getData().getToken());
            startActivity(new Intent(this, IuthenticationActivity.class));
            finish();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
