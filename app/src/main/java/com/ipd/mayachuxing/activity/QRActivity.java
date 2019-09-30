package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.GetCarElectricityBean;
import com.ipd.mayachuxing.bean.OpenCarBean;
import com.ipd.mayachuxing.common.view.InputDialog;
import com.ipd.mayachuxing.common.view.QRDialog;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.GetCarElectricityContract;
import com.ipd.mayachuxing.presenter.GetCarElectricityPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.L;
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
    @BindView(R.id.bt_input_car_num)
    AppCompatButton btInputCarNum;
    @BindView(R.id.cb_flash)
    MaterialCheckBox cbFlash;
    @BindView(R.id.tv_flash)
    AppCompatTextView tvFlash;

    private String carNum;//扫描的车辆编号
    private int qrType;//1: 用车， 2: 获取qr code
    private boolean isScanning = true;

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

        qrType = getIntent().getIntExtra("qr_type", 0);
        if (qrType == 1) {
            btInputCarNum.setVisibility(View.VISIBLE);
        } else {
            btInputCarNum.setVisibility(View.GONE);
        }
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
            if (isScanning)
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
        L.i("result = " + result);
        isScanning = false;
        if (result.indexOf("http") != -1)
            carNum = identical(result, "imei=", "&sn").replaceAll("imei=", "").replaceAll("&", "").trim();
        else
            carNum = identical(result, "IMEI:", " ").replaceAll("IMEI:", "").trim();

        if (qrType == 1) {
            TreeMap<String, String> getCarElectricityMap = new TreeMap<>();
            getCarElectricityMap.put("imei", carNum);
            getPresenter().getGetCarElectricity(getCarElectricityMap, false, false);
        } else {
            setResult(RESULT_OK, new Intent().putExtra("car_num", carNum));
            finish();
        }
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
                isScanning = false;
                new InputDialog(this) {
                    @Override
                    public void cancelUseCar() {
                        isScanning = true;
                    }

                    @Override
                    public void confirm(String str) {
                        TreeMap<String, String> getCarElectricityMap = new TreeMap<>();
                        getCarElectricityMap.put("imei", str);
                        getPresenter().getGetCarElectricity(getCarElectricityMap, false, false);
                    }
                }.show();
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
                public void cancelUseCar() {
                    isScanning = true;
                }

                @Override
                public void unlock() {
                    TreeMap<String, String> openCarMap = new TreeMap<>();
                    openCarMap.put("imei", carNum);
//                    //上海市青浦区徐泾镇中国·梦谷
//                    openCarMap.put("address", SPUtil.get(QRActivity.this, ADDRESS, "") + "");//上海市青浦区徐泾镇中国·梦谷
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
        } else {
            ToastUtil.showLongToast(data.getMessage());
            isScanning = true;
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
