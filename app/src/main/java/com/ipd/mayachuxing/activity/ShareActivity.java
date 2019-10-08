package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.Spanned;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.ShareAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.ShareBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.ShareContract;
import com.ipd.mayachuxing.presenter.SharePresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

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
    @BindView(R.id.rv_share)
    RecyclerView rvShare;

    private List<ShareBean.DataBean.UsersBean> shareBeanList = new ArrayList<>();
    private ShareAdapter shareAdapter;
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

    @SuppressLint("WrongConstant")
    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvShare);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvShare.setLayoutManager(layoutManager);
        rvShare.setItemAnimator(new DefaultItemAnimator());//加载动画
    }

    @Override
    public void initData() {
        getPresenter().getShare(false, false);
    }

    @Override
    public void initListener() {

    }

    // 分享微信好友
    private void showWeChatShare(String url, String platform) {
        OnekeyShare oks = new OnekeyShare();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        oks.disableSSOWhenAuthorize();
        oks.setTitle(getString(R.string.app_name));
        oks.setText("用小马骑行，确保出行无忧。");
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_logo);//显示APP本身自带图片
        oks.setImageData(bitmap);//bitmap格式图片
        oks.setUrl(url);
        oks.setComment("很棒，值得分享！！");
        oks.show(this);
    }

    // 分享微信朋友圈
    private void showWechatMomentsShare(String url, String platform) {
        OnekeyShare oks = new OnekeyShare();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setTitle("用小马骑行，确保出行无忧。");
        // text是分享文本，所有平台都需要这个字段
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_logo);//显示APP本身自带图片
        oks.setImageData(bitmap);//bitmap格式图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("很棒，值得分享！！");
        // 启动分享GUI
        oks.show(this);
    }

    // 分享QQ好友
    private void showQQShare(String url, String platform) {
        OnekeyShare oks = new OnekeyShare();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("用小马骑行，确保出行无忧。");
        // 启动分享GUI
        oks.show(this);
    }

    @OnClick({R.id.tv_wechat, R.id.tv_moments, R.id.tv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat:
                if (!isEmpty(shareUrl) && isFastClick())
                    showWeChatShare(shareUrl, Wechat.NAME);
                break;
            case R.id.tv_moments:
                if (!isEmpty(shareUrl) && isFastClick())
                    showWechatMomentsShare(shareUrl, WechatMoments.NAME);
                break;
            case R.id.tv_qq:
                if (!isEmpty(shareUrl) && isFastClick())
                    showQQShare(shareUrl, QQ.NAME);
                break;
        }
    }

    @Override
    public void resultShare(ShareBean data) {
        if (data.getCode() == 200) {
            shareUrl = data.getData().getShare_url();
            Spanned spanned = Html.fromHtml("<font color=\"#FFA500\">" + data.getData().getTotal() + "</font>人");
            tvShareNum.setText(spanned);

            if (data.getData().getUsers().size() > 0) {
                shareBeanList.clear();
                shareBeanList.addAll(data.getData().getUsers());
                shareAdapter = new ShareAdapter(shareBeanList);
                rvShare.setAdapter(shareAdapter);
                shareAdapter.bindToRecyclerView(rvShare);
                shareAdapter.setEnableLoadMore(true);
                shareAdapter.openLoadAnimation();
                shareAdapter.disableLoadMoreIfNotFullPage();
            } else
                rvShare.setVisibility(View.GONE);
        } else {
            ToastUtil.showLongToast(data.getMessage());
            if (data.getCode() == 203) {
                ApplicationUtil.getManager().finishActivity(MainActivity.class);
                //清除所有临时储存
                SPUtil.clear(ApplicationUtil.getContext());
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
