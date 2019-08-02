package com.ipd.mayachuxing.activity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.L;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Description ：首页
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/1.
 */
public class MainActivity extends BaseActivity implements AMap.OnMyLocationChangeListener {

    @BindView(R.id.tv_main)
    TopView tvMain;
    @BindView(R.id.mv_main)
    MapView mvMain;

    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private double current_latitude, current_longitude;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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
        ImmersionBar.setTitleBar(this, tvMain);

        mvMain.onCreate(savedInstanceState);// 此方法必须重写

        if (aMap == null) {
            aMap = mvMain.getMap();
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
                    aMap.setOnMyLocationChangeListener(MainActivity.this);

                    myLocationStyle = new MyLocationStyle();
                    // 默认模式，连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动，1秒1次定位
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
                    // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒
                    myLocationStyle.interval(5000);

                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
                    myLocationStyle.myLocationIcon(bitmapDescriptor);
                    //设置覆盖物比例
                    myLocationStyle.anchor(0.0f, 1.0f);
                    aMap.setMyLocationStyle(myLocationStyle);

                    aMap.animateCamera(CameraUpdateFactory.zoomTo(19));

                    LatLng latLng = new LatLng(31.200932, 121.267142);
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                            .title("北京")
                            .snippet("简单描述")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my))
                            .draggable(true)
                            .visible(false)
                            .anchor(0.5f, 1f)
                            .alpha(0.8f);
                    Marker marker = aMap.addMarker(markerOptions);
                    Animation animation = new RotateAnimation(0, 360, 0, 0, 0);
                    animation.setDuration(1000);
                    animation.setInterpolator(new LinearInterpolator());
                    marker.setAnimation(animation);
                    marker.startAnimation();
                    marker.showInfoWindow();
//                    initLocationStyle();
//
//                    initLocationListener();
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
    }

    /**
     * 定位的监听回调
     */
    private void initLocationListener() {
        //初始化定位
        AMapLocationClient mLocationClient = new AMapLocationClient(getApplicationContext());
        //声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                L.i("aMapLocation:" + aMapLocation.getAddress());
                //获取纬度
                L.i("aMapLocation:" + aMapLocation.getLatitude());
                L.i("aMapLocation:" + aMapLocation.getLongitude());
                L.i("aMapLocation==null:" + (aMapLocation == null));
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        L.i("aMapLocation:" + aMapLocation.getPoiName());
                        current_latitude = aMapLocation.getLatitude();
                        current_longitude = aMapLocation.getLongitude();
                    } else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        L.i("location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 显示定位蓝点图标
     */
    private void initLocationStyle() {
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_my);//自定义蓝点图标
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationIcon(descriptor);
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.strokeColor(getResources().getColor(R.color.transparent));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.transparent));// 设置圆形的填充颜色
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //设置希望展示的地图缩放级别。 缩放等级为1-19级
        aMap.moveCamera(CameraUpdateFactory.zoomTo(19));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mvMain.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mvMain.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mvMain.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mvMain.onDestroy();
    }

    @OnClick({R.id.fab_location, R.id.rv_use_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_location:
                //启动定位
//                mlocationClient.startLocation();
                break;
            case R.id.rv_use_car:
                break;
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        L.i("MyLocation=[" + location.getLongitude() + ", " + location.getLatitude() + "]");
    }
}
