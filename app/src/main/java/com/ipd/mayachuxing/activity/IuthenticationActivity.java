package com.ipd.mayachuxing.activity;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description ：实名认证
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/12.
 */
public class IuthenticationActivity extends BaseActivity {

    @BindView(R.id.tv_iuthentication)
    TopView tvIuthentication;

    @Override
    public int getLayoutId() {
        return R.layout.activity_iuthentication;
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
        ImmersionBar.setTitleBar(this, tvIuthentication);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
}
