package com.ipd.mayachuxing.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.ipd.mayachuxing.utils.L;
import com.ipd.mayachuxing.utils.ToastUtil;

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

    // 分享微信好友
    private void showShare(String url, String platform) {
        OnekeyShare oks = new OnekeyShare();
        if (platform != null) {
            oks.setPlatform(platform);
        }
        oks.disableSSOWhenAuthorize();
        oks.setTitle(getString(R.string.app_name));
        oks.setText("用小马骑行，确保出行无忧。");
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_logo);//显示APP本身自带图片
        oks.setImageData(bitmap);//bitmap格式图片
        oks.setUrl(url);
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
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_logo);//显示APP本身自带图片
        oks.setImageData(bitmap);//bitmap格式图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // 启动分享GUI
        oks.show(this);
    }

    private void showShare2(String url, String platform) {
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
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // 启动分享GUI
        oks.show(this);
    }

    @OnClick({R.id.tv_wechat, R.id.tv_moments, R.id.tv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat:
                if (!isEmpty(shareUrl) && isFastClick())
                    showShare(shareUrl, Wechat.NAME);
                break;
            case R.id.tv_moments:
                if (!isEmpty(shareUrl) && isFastClick())
                    showWechatMomentsShare(shareUrl, WechatMoments.NAME);
                break;
            case R.id.tv_qq:
                if (!isEmpty(shareUrl) && isFastClick())
                    showShare2(shareUrl, QQ.NAME);
                break;
        }
    }

    @Override
    public void resultShare(ShareBean data) {
        if (data.getCode() == 200) {
            shareUrl = data.getData().getShare_url();
            L.i("url = " + shareUrl); //http:\/\/zl.v-lionsafety.com\/app\/register.html?invite=26
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
