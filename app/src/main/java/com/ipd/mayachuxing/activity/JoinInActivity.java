package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.common.config.UrlConfig.BASE_LOCAL_URL;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：招商加盟
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class JoinInActivity extends BaseActivity {

    @BindView(R.id.tv_join_in)
    TopView tvJoinIn;
    @BindView(R.id.wv_join_in)
    WebView wvJoinIn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_join_in;
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
        ImmersionBar.setTitleBar(this, tvJoinIn);

        WebSettings webSettings = wvJoinIn.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 排版适应屏幕
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wvJoinIn.loadUrl(BASE_LOCAL_URL + "app/join_group.html");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.rv_application_to_join)
    public void onViewClicked() {
        if (isFastClick())
            startActivity(new Intent(this, ApplicationToJoinActivity.class));
    }
}
