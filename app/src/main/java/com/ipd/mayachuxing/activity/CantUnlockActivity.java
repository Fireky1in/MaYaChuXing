package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.CAMERA;
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_90;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：开不了锁
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class CantUnlockActivity extends BaseActivity {

    @BindView(R.id.tv_cant_unlock)
    TopView tvCantUnlock;
    @BindView(R.id.et_scanning)
    AppCompatEditText etScanning;
    @BindView(R.id.iv_scanning)
    AppCompatImageView ivScanning;
    @BindView(R.id.et_content)
    MultiLineEditText etContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cant_unlock;
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
        ImmersionBar.setTitleBar(this, tvCantUnlock);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    // 相机权限
    private void rxPermissionCamera() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    startActivityForResult(new Intent(CantUnlockActivity.this, QRActivity.class), REQUEST_CODE_90);
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
    }

    @OnClick({R.id.iv_scanning, R.id.rv_cant_unlock})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scanning:
                if (isFastClick()) {
                    rxPermissionCamera();
                }
                break;
            case R.id.rv_cant_unlock:
                if (isFastClick()) {
                    finish();
                }
                break;
        }
    }
}
