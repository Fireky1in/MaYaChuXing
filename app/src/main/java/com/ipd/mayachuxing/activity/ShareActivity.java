package com.ipd.mayachuxing.activity;

import android.text.Html;
import android.text.Spanned;
import android.view.View;

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

/**
 * Description ：马亚分享
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/3.
 */
public class ShareActivity extends BaseActivity {

    @BindView(R.id.tv_share)
    TopView tvShare;
    @BindView(R.id.tv_share_num)
    AppCompatTextView tvShareNum;

    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
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
        ImmersionBar.setTitleBar(this, tvShare);
    }

    @Override
    public void initData() {
        Spanned spanned = Html.fromHtml("<font color=\"#FFA500\">" + 4 + "</font>人");
        tvShareNum.setText(spanned);
    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_wechat, R.id.tv_moments, R.id.tv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat:
                break;
            case R.id.tv_moments:
                break;
            case R.id.tv_qq:
                break;
        }
    }
}
