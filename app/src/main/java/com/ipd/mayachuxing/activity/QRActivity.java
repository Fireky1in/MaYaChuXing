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
import com.ipd.mayachuxing.utils.SPUtil;
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

import static com.ipd.mayachuxing.common.config.IConstants.ADDRESS;
import static com.ipd.mayachuxing.utils.StringUtils.identical;

/**
 * Description ：扫码开锁
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/4.
 */
public class QRActivity extends BaseActivity<GetCarElectricityContract.View, GetCarElectricityContract.Presenter> implements GetCarElectricityContract.View {//, AMapLocationListener {

    @BindView(R.id.tv_qr)
    TopView tvQr;
    @BindView(R.id.bt_input_car_num)
    AppCompatButton btInputCarNum;
    @BindView(R.id.cb_flash)
    MaterialCheckBox cbFlash;
    @BindView(R.id.tv_flash)
    AppCompatTextView tvFlash;

    //声明mLocationOption对象
//    public AMapLocationClientOption mLocationOption = null;
//    public AMapLocationClient mlocationClient = null;
    private String carNum;//扫描的车辆编号
    private int qrType;//1: 用车， 2: 获取qr code
//    private String address = "";

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

//        mlocationClient = new AMapLocationClient(this);
//        //初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
//        //设置返回地址信息，默认为true
//        mLocationOption.setNeedAddress(true);
//        //设置定位监听
//        mlocationClient.setLocationListener(this);
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
//        //设置定位参数
//        mlocationClient.setLocationOption(mLocationOption);
//        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
//        // 在定位结束后，在合适的生命周期调用onDestroy()方法
//        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
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
        L.i("result = " + result);
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
                new InputDialog(this) {
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
//            //启动定位
//            mlocationClient.startLocation();

            DecimalFormat df = new DecimalFormat("######0.00");
            double distance = Double.parseDouble(df.format(data.getData().getBatter() * 0.55));//满电量可骑行55km
            new QRDialog(this, carNum, distance) {
                @Override
                public void unlock() {
//                    L.i("address = " + address);
//                    if (!isEmpty(address)) {
                    TreeMap<String, String> openCarMap = new TreeMap<>();
                    openCarMap.put("imei", carNum);
                    //上海市青浦区徐泾镇中国·梦谷
                    openCarMap.put("address", SPUtil.get(QRActivity.this, ADDRESS, "") + "");//上海市青浦区徐泾镇中国·梦谷
                    getPresenter().getOpenCar(openCarMap, false, false);
//                    }
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

//    @Override
//    public void onLocationChanged(AMapLocation aMapLocation) {
//        if (aMapLocation != null) {
//            if (aMapLocation.getErrorCode() == 0) {
//                //定位成功回调信息，设置相关消息
////                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
////                aMapLocation.getLatitude();//获取纬度
////                aMapLocation.getLongitude();//获取经度
////                aMapLocation.getAccuracy();//获取精度信息
////                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                Date date = new Date(aMapLocation.getTime());
////                df.format(date);//定位时间
//                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
////                aMapLocation.getCountry();//国家信息
////                aMapLocation.getProvince();//省信息
////                aMapLocation.getCity();//城市信息
////                aMapLocation.getDistrict();//城区信息
////                aMapLocation.getStreet();//街道信息
////                aMapLocation.getStreetNum();//街道门牌号信息
////                aMapLocation.getCityCode();//城市编码
////                aMapLocation.getAdCode();//地区编码
////                aMapLocation.getAOIName();//获取当前定位点的AOI信息
//
//                address = aMapLocation.getAddress();
//            } else {
//                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
//                L.e("AmapError", "location Error, ErrCode:"
//                        + aMapLocation.getErrorCode() + ", errInfo:"
//                        + aMapLocation.getErrorInfo());
//            }
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mlocationClient.stopLocation();
//    }
}
