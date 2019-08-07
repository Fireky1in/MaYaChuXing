package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.CacheUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_setting)
    TopView tvSetting;
    @BindView(R.id.stv_cache_clear)
    SuperTextView stvCacheClear;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
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
        ImmersionBar.setTitleBar(this, tvSetting);

        try {
            stvCacheClear.setRightString(CacheUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.stv_cache_clear, R.id.rv_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.stv_cache_clear:
                if (isFastClick()) {
                    CacheUtil.clearAllCache(this);
                    try {
                        stvCacheClear.setRightString(CacheUtil.getTotalCacheSize(this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.rv_sign_out:
                if (isFastClick()) {
                    ApplicationUtil.getManager().finishActivity(MainActivity.class);
                    //清除所有临时储存
                    SPUtil.clear(ApplicationUtil.getContext());
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                break;
        }
    }
}
