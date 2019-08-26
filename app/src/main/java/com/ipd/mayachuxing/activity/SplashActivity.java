package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.appcompat.widget.AppCompatImageView;

import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.utils.Utils;

import butterknife.BindView;

/**
 * Description ：启动页
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/6/21.
 */
public class SplashActivity extends BaseActivity {

    /**
     * ----------Dragon be here!----------/
     * 　　　┏┓　　　┏┓
     * 　　┏┛┻━━━┛┻┓
     * 　　┃　　　　　　　┃
     * 　　┃　　　━　　　┃
     * 　　┃　┳┛　┗┳　┃
     * 　　┃　　　　　　　┃
     * 　　┃　　　┻　　　┃
     * 　　┃　　　　　　　┃
     * 　　┗━┓　　　┏━┛
     * 　　　　┃　　　┃神兽保佑
     * 　　　　┃　　　┃代码无BUG！
     * 　　　　┃　　　┗━━━┓
     * 　　　　┃　　　　　　　┣┓
     * 　　　　┃　　　　　　　┏┛
     * 　　　　┗┓┓┏━┳┓┏┛
     * 　　　　　┃┫┫　┃┫┫
     * 　　　　　┗┻┛　┗┻┛
     * ━━━━━━神兽出没━━━━━━
     */

    @BindView(R.id.iv_splash)
    AppCompatImageView ivSplash;
    /**
     * 默认启动页过渡时间
     */
    private static final int DEFAULT_SPLASH_DURATION_MILLIS = 1500;
    private long firstTime = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
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
        startSplash(true);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    //双击退出程序
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtil.showShortToast(getResources().getString(R.string.click_out_again));
            firstTime = secondTime;
        } else {
            ApplicationUtil.getManager().exitApp();
        }
    }

    /**
     * 开启过渡
     *
     * @param enableAlphaAnim 是否启用渐近动画
     */
    protected void startSplash(boolean enableAlphaAnim) {
        if (enableAlphaAnim)
            startSplashAnim(new AlphaAnimation(0.2F, 1.0F));
        else
            startSplashAnim(new AlphaAnimation(1.0F, 1.0F));
    }

    /**
     * 开启引导过渡动画
     *
     * @param anim
     */
    private void startSplashAnim(Animation anim) {
        Utils.checkNull(anim, "Splash Animation can not be null");
        anim.setDuration(DEFAULT_SPLASH_DURATION_MILLIS);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 启动完毕
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
        ivSplash.startAnimation(anim);
    }
}
