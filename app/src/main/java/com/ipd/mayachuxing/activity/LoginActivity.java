package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.utils.CountDownButtonHelper;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.common.config.IConstants.IS_LOGIN;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：登录
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/3.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_login_back)
    TopView tvLoginBack;
    @BindView(R.id.et_phone)
    MaterialEditText etPhone;
    @BindView(R.id.et_captcha)
    MaterialEditText etCaptcha;
    @BindView(R.id.bt_captcha)
    SuperButton btCaptcha;
    @BindView(R.id.cb_login)
    CheckBox cbLogin;

    private CountDownButtonHelper mCountDownHelper; //倒计时

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
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
        ImmersionBar.with(this).statusBarDarkFont(false).init();
        ImmersionBar.setTitleBar(this, tvLoginBack);

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
                mCountDownHelper.start();
                break;
            case R.id.bt_agreement:
                break;
            case R.id.rv_login:
                if (isFastClick()) {
                    if (cbLogin.isChecked()) {
                        SPUtil.put(this, IS_LOGIN, "is_login");
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else
                        ToastUtil.showLongToast("请同意用户协议!");
                }
                break;
        }
    }
}
