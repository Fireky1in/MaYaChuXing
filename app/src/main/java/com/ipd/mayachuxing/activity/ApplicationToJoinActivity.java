package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;
import android.widget.RadioButton;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.SetMemberShipBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.SetMemberShipContract;
import com.ipd.mayachuxing.presenter.SetMemberShipPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：申请加盟
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class ApplicationToJoinActivity extends BaseActivity<SetMemberShipContract.View, SetMemberShipContract.Presenter> implements SetMemberShipContract.View {

    @BindView(R.id.tv_application_to_join)
    TopView tvApplicationToJoin;
    @BindView(R.id.tv_name)
    SuperTextView tvName;
    @BindView(R.id.tv_phone)
    SuperTextView tvPhone;
    @BindView(R.id.tv_location)
    SuperTextView tvLocation;
    @BindView(R.id.rb_region_master)
    RadioButton rbRegionMaster;
    @BindView(R.id.rb_landscape_master)
    RadioButton rbLandscapeMaster;

    @Override
    public int getLayoutId() {
        return R.layout.activity_application_to_join;
    }

    @Override
    public SetMemberShipContract.Presenter createPresenter() {
        return new SetMemberShipPresenter(this);
    }

    @Override
    public SetMemberShipContract.View createView() {
        return this;
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

    @OnClick({R.id.tv_location, R.id.rb_region_master, R.id.rb_landscape_master, R.id.rv_application_to_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                break;
            case R.id.rv_application_to_join:
                TreeMap<String, String> setMemberShipMap = new TreeMap<>();
                setMemberShipMap.put("name", tvName.getCenterEditValue());
                setMemberShipMap.put("phone", tvPhone.getCenterEditValue());
                setMemberShipMap.put("area", tvLocation.getCenterEditValue());
                setMemberShipMap.put("static", rbRegionMaster.isChecked() ? "1" : "2");
                getPresenter().getSetMemberShip(setMemberShipMap, false, false);
                break;
        }
    }

    @Override
    public void resultSetMemberShip(SetMemberShipBean data) {
        if (data.getCode() == 200) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else{
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
