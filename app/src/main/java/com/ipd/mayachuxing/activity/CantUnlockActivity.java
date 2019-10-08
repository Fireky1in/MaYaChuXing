package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.CanUnlockBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.CanUnlockContract;
import com.ipd.mayachuxing.presenter.CanUnlockPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.CAMERA;
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_98;
import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：开不了锁
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class CantUnlockActivity extends BaseActivity<CanUnlockContract.View, CanUnlockContract.Presenter> implements CanUnlockContract.View {

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
    public CanUnlockContract.Presenter createPresenter() {
        return new CanUnlockPresenter(this);
    }

    @Override
    public CanUnlockContract.View createView() {
        return this;
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
                    startActivityForResult(new Intent(CantUnlockActivity.this, QRActivity.class).putExtra("qr_type", 2), REQUEST_CODE_98);
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case REQUEST_CODE_98:
                    etScanning.setText(data.getStringExtra("car_num"));
                    break;
            }
        }
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
                    if (!isEmpty(etScanning.getText().toString().trim()) && !isEmpty(etContent.getContentText().trim())) {
                        TreeMap<String, String> canUnlockMap = new TreeMap<>();
                        canUnlockMap.put("item_no", etScanning.getText().toString().trim());
                        canUnlockMap.put("supplement", etContent.getContentText().trim());
                        getPresenter().getCanUnlock(canUnlockMap, false, false);
                    } else
                        ToastUtil.showLongToast("请将信息填写完整");
                }
                break;
        }
    }

    @Override
    public void resultCanUnlock(CanUnlockBean data) {
        if (data.getCode() == 200)
            finish();
        else {
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
