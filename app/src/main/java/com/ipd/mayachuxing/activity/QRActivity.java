package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.QRDialog;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;
import com.xuexiang.xqrcode.ui.CaptureFragment;
import com.xuexiang.xqrcode.util.QRCodeAnalyzeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description ：扫码开锁
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/4.
 */
public class QRActivity extends BaseActivity {

    @BindView(R.id.tv_qr)
    TopView tvQr;
    @BindView(R.id.cb_flash)
    MaterialCheckBox cbFlash;
    @BindView(R.id.tv_flash)
    AppCompatTextView tvFlash;

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr;
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
        ImmersionBar.setTitleBar(this, tvQr);

        CaptureFragment captureFragment = XQRCode.getCaptureFragment(R.layout.activity_custom_capture);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        captureFragment.setCameraInitCallBack(cameraInitCallBack);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        //设置相机的自动聚焦间隔
        XQRCode.setAutoFocusInterval(1500L);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 照相机初始化监听
     */
    CaptureFragment.CameraInitCallBack cameraInitCallBack = new CaptureFragment.CameraInitCallBack() {
        @Override
        public void callBack(@Nullable Exception e) {
            if (e != null) {
                CaptureActivity.showNoPermissionTip(QRActivity.this);
            }
        }
    };

    /**
     * 二维码解析回调函数
     */
    QRCodeAnalyzeUtils.AnalyzeCallback analyzeCallback = new QRCodeAnalyzeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap bitmap, String result) {
            handleAnalyzeSuccess(bitmap, result);
        }

        @Override
        public void onAnalyzeFailed() {
            handleAnalyzeFailed();
        }
    };

    /**
     * 处理扫描成功
     *
     * @param bitmap
     * @param result
     */
    protected void handleAnalyzeSuccess(Bitmap bitmap, String result) {
        new QRDialog(this) {
            @Override
            public void unlock() {
                setResult(RESULT_OK, new Intent().putExtra("unlock", 1));
                finish();
            }
        }.show();
//        Intent resultIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putInt(XQRCode.RESULT_TYPE, XQRCode.RESULT_SUCCESS);
//        bundle.putString(XQRCode.RESULT_DATA, result);
//        resultIntent.putExtras(bundle);
//        setResult(RESULT_OK, resultIntent);
//        finish();
    }

    /**
     * 处理解析失败
     */
    protected void handleAnalyzeFailed() {
        ToastUtil.showLongToast("扫描失败，请重试");
//        Intent resultIntent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putInt(XQRCode.RESULT_TYPE, XQRCode.RESULT_FAILED);
//        bundle.putString(XQRCode.RESULT_DATA, "");
//        resultIntent.putExtras(bundle);
//        setResult(RESULT_OK, resultIntent);
//        finish();
    }

    @OnClick({R.id.bt_input_car_num, R.id.cb_flash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_input_car_num:
                break;
            case R.id.cb_flash:
                if (cbFlash.isChecked()) {
                    XQRCode.enableFlashLight(true);
                    tvFlash.setText("关灯");
                } else
                    try {
                        XQRCode.enableFlashLight(false);
                        tvFlash.setText("开灯");
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        ToastUtil.showLongToast("设备不支持闪光灯!");
                    }
                break;
        }
    }
}
