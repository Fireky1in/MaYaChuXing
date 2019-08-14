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
import com.ipd.mayachuxing.bean.GetCarElectricityBean;
import com.ipd.mayachuxing.bean.OpenCarBean;
import com.ipd.mayachuxing.common.view.QRDialog;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.GetCarElectricityContract;
import com.ipd.mayachuxing.presenter.GetCarElectricityPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;
import com.xuexiang.xqrcode.ui.CaptureFragment;
import com.xuexiang.xqrcode.util.QRCodeAnalyzeUtils;

import java.text.DecimalFormat;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.utils.StringUtils.identical;

/**
 * Description ：扫码开锁
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/4.
 */
public class QRActivity extends BaseActivity<GetCarElectricityContract.View, GetCarElectricityContract.Presenter> implements GetCarElectricityContract.View {

    @BindView(R.id.tv_qr)
    TopView tvQr;
    @BindView(R.id.cb_flash)
    MaterialCheckBox cbFlash;
    @BindView(R.id.tv_flash)
    AppCompatTextView tvFlash;

    private String carNum;//扫描的车辆编号

    @Override
    public int getLayoutId() {
        return R.layout.activity_qr;
    }

    @Override
    public GetCarElectricityContract.Presenter createPresenter() {
        return new GetCarElectricityPresenter(this);
    }

    @Override
    public GetCarElectricityContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvQr);

        CaptureFragment captureFragment = XQRCode.getCaptureFragment(R.layout.activity_custom_capture, true, 1000);
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
        carNum = identical(result, "IMEI:", " ").replaceAll("IMEI:", "").trim();
        TreeMap<String, String> getCarElectricityMap = new TreeMap<>();
        getCarElectricityMap.put("imei", carNum);
        getPresenter().getGetCarElectricity(getCarElectricityMap, false, false);
    }

    /**
     * 处理解析失败
     */
    protected void handleAnalyzeFailed() {
        ToastUtil.showLongToast("扫描失败，请重试");
    }

    @OnClick({R.id.bt_input_car_num, R.id.cb_flash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_input_car_num:
                break;
            case R.id.cb_flash:
                if (cbFlash.isChecked()) {
                    XQRCode.switchFlashLight(true);
                    tvFlash.setText("关灯");
                } else
                    try {
                        XQRCode.switchFlashLight(false);
                        tvFlash.setText("开灯");
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        ToastUtil.showLongToast("设备不支持闪光灯!");
                    }
                break;
        }
    }

    @Override
    public void resultGetCarElectricity(GetCarElectricityBean data) {
        if (data.getCode() == 200) {
            DecimalFormat df = new DecimalFormat("######0.00");
            double distance = Double.parseDouble(df.format(data.getData().getBatter() * 0.55));//满电量可骑行55km
            new QRDialog(this, carNum, distance) {
                @Override
                public void unlock() {
                    TreeMap<String, String> openCarMap = new TreeMap<>();
                    openCarMap.put("imei", carNum);
                    //上海市青浦区徐泾镇中国·梦谷
                    openCarMap.put("address", "上海市青浦区徐泾镇中国·梦谷");//SPUtil.get(QRActivity.this, ADDRESS, "") + "");
                    getPresenter().getOpenCar(openCarMap, false, false);
                }
            }.show();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultOpenCar(OpenCarBean data) {
        if (data.getCode() == 200) {
            setResult(RESULT_OK, new Intent().putExtra("unlock", 1));
            finish();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
