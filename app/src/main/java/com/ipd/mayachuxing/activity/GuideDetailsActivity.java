package com.ipd.mayachuxing.activity;

import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;

import butterknife.BindView;

/**
 * Description ：用户指南详情
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/18.
 */
public class GuideDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_guide_details)
    TopView tvGuideDetails;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.tv_content)
    AppCompatTextView tvContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_details;
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
        ImmersionBar.setTitleBar(this, tvGuideDetails);

        tvTopTitle.setText(getIntent().getStringExtra("title"));
        tvContent.setText(getIntent().getStringExtra("content"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
