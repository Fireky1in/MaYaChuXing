package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.L;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_96;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：申请还车点
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/3.
 */
public class ApplyParkingSpotActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.tv_apply_parking_spot)
    TopView tvApplyParkingSpot;
    @BindView(R.id.mv_apply_parking_spot)
    MapView mvApplyParkingSpot;
    @BindView(R.id.tv_location_title)
    AppCompatTextView tvLocationTitle;
    @BindView(R.id.tv_location)
    SuperTextView tvLocation;
    @BindView(R.id.iv_upload)
    RadiusImageView ivUpload;
    @BindView(R.id.et_content)
    MultiLineEditText etContent;

    private AMap aMap;
    private MyLocationStyle myLocationStyle = new MyLocationStyle();//定位小蓝点样式
    private double current_latitude, current_longitude;//经纬度
    private GeocodeSearch geocoderSearch;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_parking_spot;
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
        ImmersionBar.setTitleBar(this, tvApplyParkingSpot);

        mvApplyParkingSpot.onCreate(savedInstanceState);// 此方法必须重写

        if (aMap == null) {
            aMap = mvApplyParkingSpot.getMap();
        }
        rxPermissionLocation();
    }

    // 定位权限
    private void rxPermissionLocation() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
                    aMap.setMyLocationEnabled(true);
                    aMap.setOnMyLocationChangeListener(ApplyParkingSpotActivity.this);

                    // 默认模式，连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动，1秒1次定位
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
                    // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒
//                    myLocationStyle.interval(5000);

                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
                    myLocationStyle.myLocationIcon(bitmapDescriptor);
                    myLocationStyle.radiusFillColor(getResources().getColor(R.color.transparent));
                    myLocationStyle.strokeColor(getResources().getColor(R.color.transparent));
                    //设置覆盖物比例
                    myLocationStyle.anchor(0.5f, 0.5f);
                    aMap.setMyLocationStyle(myLocationStyle);

                    aMap.getUiSettings().setZoomControlsEnabled(false);
                    aMap.animateCamera(CameraUpdateFactory.zoomTo(19));
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    Glide.with(this)
                            .load(PictureSelector.obtainMultipleResult(data).get(0).getCompressPath())
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                    ivUpload.setImageDrawable(resource);
                                }
                            });
                    break;
                case REQUEST_CODE_96:
                    setCurrentLocationDetails(data.getDoubleExtra("lat", 0), data.getDoubleExtra("lng", 0));
                    cameraMove(data.getDoubleExtra("lat", 0), data.getDoubleExtra("lng", 0));
                    break;
            }
        }
    }

    /**
     * 移动地图
     *
     * @param lat
     * @param lng
     */
    private void cameraMove(double lat, double lng) {
        LatLng latlng = new LatLng(lat, lng);
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, 19, 0, 0));
        aMap.moveCamera(camera);
    }

    private void setCurrentLocationDetails(double lat, double lng) {
        LatLonPoint latLonPoint = new LatLonPoint(lat, lng);
        // 地址逆解析
        geocoderSearch = new GeocodeSearch(getApplicationContext());
        geocoderSearch.setOnGeocodeSearchListener(ApplyParkingSpotActivity.this);
        // 第一个参数表示一个Latlng(经纬度)，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 25, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mvApplyParkingSpot.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mvApplyParkingSpot.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mvApplyParkingSpot.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mvApplyParkingSpot.onDestroy();
    }

    @Override
    public void onMyLocationChange(Location location) {
        L.i("MyLocation=[" + location.getLongitude() + ", " + location.getLatitude() + "]");
        current_latitude = location.getLatitude();
        current_longitude = location.getLongitude();
        setCurrentLocationDetails(current_latitude, current_longitude);
    }

    @OnClick({R.id.tv_location, R.id.iv_upload, R.id.rv_apply_parking_spot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location:
                startActivityForResult(new Intent(this, SearchActivity.class), REQUEST_CODE_96);
                break;
            case R.id.iv_upload:
                PictureSelector.create(ApplyParkingSpotActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)// 最大图片选择数量 int
                        .isCamera(true)
                        .compress(true)
                        .minimumCompressSize(100)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.rv_apply_parking_spot:
                if (isFastClick()) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        String formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
        tvLocationTitle.setText("申请位置：中国梦谷");
        tvLocation.setLeftString(formatAddress);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
