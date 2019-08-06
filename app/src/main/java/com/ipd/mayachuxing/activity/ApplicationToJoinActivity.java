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
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description ：申请加盟
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class ApplicationToJoinActivity extends BaseActivity {

    @BindView(R.id.tv_application_to_join)
    TopView tvApplicationToJoin;
    @BindView(R.id.tv_name)
    SuperTextView tvName;
    @BindView(R.id.tv_phone)
    SuperTextView tvPhone;
    @BindView(R.id.tv_location)
    SuperTextView tvLocation;
    @BindView(R.id.bt_region_master)
    SuperButton btRegionMaster;
    @BindView(R.id.bt_landscape_master)
    SuperButton btLandscapeMaster;

    @Override
    public int getLayoutId() {
        return R.layout.activity_application_to_join;
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
        ImmersionBar.setTitleBar(this, tvApplicationToJoin);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.tv_location, R.id.bt_region_master, R.id.bt_landscape_master, R.id.rv_application_to_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                break;
            case R.id.bt_region_master:
                break;
            case R.id.bt_landscape_master:
                break;
            case R.id.rv_application_to_join:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}
