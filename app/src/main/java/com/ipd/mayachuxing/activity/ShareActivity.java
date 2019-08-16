package com.ipd.mayachuxing.activity;

import android.text.Html;
import android.text.Spanned;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.ShareBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.ShareContract;
import com.ipd.mayachuxing.presenter.SharePresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：马亚分享
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/3.
 */
public class ShareActivity extends BaseActivity<ShareContract.View, ShareContract.Presenter> implements ShareContract.View {

    @BindView(R.id.tv_share)
    TopView tvShare;
    @BindView(R.id.tv_share_num)
    AppCompatTextView tvShareNum;

    private String shareUrl = "";//分享链接

    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public ShareContract.Presenter createPresenter() {
        return new SharePresenter(this);
    }

    @Override
    public ShareContract.View createView() {
        return this;
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
        getPresenter().getShare(false, false);
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

    @Override
    public void resultShare(ShareBean data) {
        if (data.getCode() == 200) {
            shareUrl = data.getData().getShare_url();
            Spanned spanned = Html.fromHtml("<font color=\"#FFA500\">" + data.getData().getTotal() + "</font>人");
            tvShareNum.setText(spanned);
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
