package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.SidebarAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.CanUseCarBean;
import com.ipd.mayachuxing.bean.CarStatusBean;
import com.ipd.mayachuxing.bean.CloseCarBean;
import com.ipd.mayachuxing.bean.GeocodeBean;
import com.ipd.mayachuxing.bean.IsOrderBean;
import com.ipd.mayachuxing.bean.IsStopCarBean;
import com.ipd.mayachuxing.bean.LockCarBean;
import com.ipd.mayachuxing.bean.ParkBikeBean;
import com.ipd.mayachuxing.bean.SelectBikeBean;
import com.ipd.mayachuxing.bean.SidebarBean;
import com.ipd.mayachuxing.bean.UnlockCarBean;
import com.ipd.mayachuxing.bean.UserInfoBean;
import com.ipd.mayachuxing.common.view.CustomLinearLayoutManager;
import com.ipd.mayachuxing.common.view.CustomerReturnDialog;
import com.ipd.mayachuxing.common.view.CustomerServiceDialog;
import com.ipd.mayachuxing.common.view.StartAppDialog;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.MainContract;
import com.ipd.mayachuxing.presenter.MainPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.L;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static com.ipd.mayachuxing.common.config.IConstants.ADDRESS;
import static com.ipd.mayachuxing.common.config.IConstants.IS_LOGIN;
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_90;
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_91;
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_95;
import static com.ipd.mayachuxing.common.config.UrlConfig.BASE_LOCAL_URL;
import static com.ipd.mayachuxing.common.config.UrlConfig.GEOCODE;
import static com.ipd.mayachuxing.utils.DateUtils.StartTimeToEndTime;
import static com.ipd.mayachuxing.utils.DateUtils.timedate;
import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：首页
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/3.
 */
public class MainActivity extends BaseActivity<MainContract.View, MainContract.Presenter> implements MainContract.View, AMap.OnMyLocationChangeListener {

    @BindView(R.id.tv_main)
    TopView tvMain;
    @BindView(R.id.dl_main)
    DrawerLayout dlMain;
    @BindView(R.id.ll_sidebar_main)
    LinearLayout llSidebarMain;
    @BindView(R.id.mv_main)
    MapView mvMain;
    @BindView(R.id.rb_seek_car)
    RadioButton rbSeekCar;
    @BindView(R.id.cl_main_ad)
    ConstraintLayout clMainAd;
    @BindView(R.id.fab_stop)
    FloatingActionButton fabStop;
    @BindView(R.id.riv_user_head)
    RadiusImageView rivUserHead;
    @BindView(R.id.tv_sidebar_user_phone)
    TextView tvSidebarUserPhone;
    @BindView(R.id.bt_iuthentication)
    AppCompatButton btIuthentication;
    @BindView(R.id.rv_sidebar)
    RecyclerView rvSidebar;
    @BindView(R.id.tv_car_num)
    AppCompatTextView tvCarNum;
    @BindView(R.id.tv_remaining_distance)
    SuperTextView tvRemainingDistance;
    @BindView(R.id.tv_use_time)
    SuperTextView tvUseTime;
    @BindView(R.id.tv_use_fee)
    SuperTextView tvUseFee;
    @BindView(R.id.tv_car_type)
    AppCompatTextView tvCarType;
    @BindView(R.id.tv_lock_car)
    AppCompatTextView tvLockCar;
    @BindView(R.id.ll_car_details)
    LinearLayout llCarDetails;
    @BindView(R.id.tv_use_car)
    TextView tvUseCar;
    @BindView(R.id.cl_return_car_prompt)
    ConstraintLayout clReturnCarPrompt;

    private long firstTime = 0;
    private SidebarAdapter sidebarAdapter;
    private List<SidebarBean> sidebarBeanList = new ArrayList<>();//侧边栏集合
    private AMap aMap;
    private MyLocationStyle myLocationStyle = new MyLocationStyle();//定位小蓝点样式
    private double current_longitude, current_latitude;//经纬度
    private String carNum;//车辆编号
    private Handler handler;//用车时间计数
    private List<PoiItem> pois = new ArrayList<>();//用来计算marker点的坐标集合
    private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();//marker点集合
    private int[] sidebarIconSelect = new int[]{R.drawable.ic_wallet_select, R.drawable.ic_account_select, R.drawable.ic_coupon_select, R.drawable.ic_trip_select, R.drawable.ic_join_in_select, R.drawable.ic_msg_select, R.drawable.ic_guide_select, R.drawable.ic_setting_select};
    private int[] sidebarIconUnselect = new int[]{R.drawable.ic_wallet, R.drawable.ic_account, R.drawable.ic_coupon, R.drawable.ic_trip, R.drawable.ic_join_in, R.drawable.ic_msg, R.drawable.ic_guide, R.drawable.ic_setting};
    private String[] sidebarName = new String[]{"我的钱包", "现金账户", "我的优惠", "我的行程", "招商加盟", "我的消息", "用户指南", "设置"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public MainContract.View createView() {
        return this;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMain);

        new StartAppDialog(this) {
        }.show();

        mvMain.onCreate(savedInstanceState);// 此方法必须重写

        if (aMap == null) {
            aMap = mvMain.getMap();
        }
        rxPermissionLocation();

        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvSidebar.setLayoutManager(layoutManager);
        rvSidebar.setNestedScrollingEnabled(false);
        rvSidebar.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvSidebar.setItemAnimator(new DefaultItemAnimator());//加载动画

        //初始化侧边栏
        dlMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        dlMain.setScrimColor(Color.TRANSPARENT);//侧滑菜单打开后主内容区域的颜色
        dlMain.addDrawerListener(drawerListener);
    }

    // 定位权限
    private void rxPermissionLocation() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    // 默认模式，连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动，1秒1次定位
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
                    myLocationStyle.radiusFillColor(getResources().getColor(R.color.transparent));
                    myLocationStyle.strokeColor(getResources().getColor(R.color.transparent));
                    myLocationStyle.showMyLocation(true);
                    // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒
                    myLocationStyle.interval(1000);

//                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
//                    myLocationStyle.myLocationIcon(bitmapDescriptor);
                    //设置覆盖物比例
                    myLocationStyle.anchor(0.5f, 0.5f);

                    aMap.setMyLocationStyle(myLocationStyle);
                    // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
                    aMap.setMyLocationEnabled(true);
                    aMap.setOnMyLocationChangeListener(MainActivity.this);
                    aMap.getUiSettings().setZoomControlsEnabled(false);
                    aMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
    }

    // 相机权限
    private void rxPermissionCamera() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    startActivityForResult(new Intent(MainActivity.this, QRActivity.class).putExtra("qr_type", 1), REQUEST_CODE_90);
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
    }

    /**
     * 添加Marker到地图中。
     */
    public void addToMap(int flag) {
        try {
            for (int i = 0; i < pois.size(); i++) {
                Marker marker = aMap.addMarker(flag == 1 ? getMarkerOptions1(i) : getMarkerOptions2(i));
                marker.setObject(i);
                mPoiMarks.add(marker);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private MarkerOptions getMarkerOptions1(int index) {
        return new MarkerOptions()
                .position(
                        new LatLng(pois.get(index).getLatLonPoint().getLatitude()
                                , pois.get(index).getLatLonPoint()
                                .getLongitude()))
                .draggable(false)
                .visible(true)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.ic_select_car_marker)));
    }

    private MarkerOptions getMarkerOptions2(int index) {
        return new MarkerOptions()
                .position(
                        new LatLng(pois.get(index).getLatLonPoint()
                                .getLatitude(), pois.get(index).getLatLonPoint()
                                .getLongitude()))
                .draggable(false)
                .visible(true)
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.ic_park_car_marker)));
    }

    /**
     * 去掉PoiOverlay上所有的Marker。
     */
    public void removeFromMap() {
        for (Marker mark : mPoiMarks) {
            mark.remove();
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
        CameraUpdate camera = CameraUpdateFactory.newCameraPosition(new CameraPosition(latlng, 15, 0, 0));
        aMap.moveCamera(camera);
    }

    /**
     * 侧边栏
     */
    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            // 得到contentView
            View content = dlMain.getChildAt(0);
            int offset = (int) (drawerView.getWidth() * slideOffset);
            content.setTranslationX(offset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {

        }

        @Override
        public void onDrawerClosed(View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    /**
     * mark 图标
     */
    private void mark(int flag) {
        removeFromMap();
        addToMap(flag);
    }

    @Override
    public void initData() {
        if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {
            getPresenter().getUserInfo(false, false);

            getPresenter().getIsOrder(false, false);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case REQUEST_CODE_90:
                    if (data.getIntExtra("unlock", 0) == 1) {
                        getPresenter().getCarStatus(false, false);
                    }
                    break;
                case REQUEST_CODE_91:
                    Glide.with(this)
                            .load(data.getStringExtra("modify_head"))
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                    rivUserHead.setImageDrawable(resource);
                                }
                            });
                    break;
                case REQUEST_CODE_95:
                    current_latitude = data.getDoubleExtra("lat", 0);
                    current_longitude = data.getDoubleExtra("lng", 0);

                    //是否能还车
                    TreeMap<String, String> isStopCarMap = new TreeMap<>();
                    isStopCarMap.put("lat", current_latitude + "");
                    isStopCarMap.put("lng", current_longitude + "");
                    getPresenter().getIsStopCar(isStopCarMap, false, false);

                    //重新搜索还车点
                    fabStop.setImageDrawable(getResources().getDrawable(R.mipmap.ic_stop_select));
                    rbSeekCar.setChecked(false);

                    TreeMap<String, String> parkBikeMap = new TreeMap<>();
                    parkBikeMap.put("lat", current_latitude + "");
                    parkBikeMap.put("lng", current_longitude + "");
                    getPresenter().getParkBike(parkBikeMap, false, false);

                    cameraMove(current_latitude, current_longitude);
                    break;
            }
        }
    }

    //双击退出程序
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtil.showShortToast(getResources().getString(R.string.click_out_again));
            firstTime = secondTime;
        } else {
            ApplicationUtil.getManager().exitApp();
        }
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

    @Override
    public void onMyLocationChange(Location location) {
        L.i("MyLocation main =[" + location.getLongitude() + ", " + location.getLatitude() + "]");
        //MyLocation=[121.267081, 31.201382]
        current_latitude = location.getLatitude();
        current_longitude = location.getLongitude();
//        current_latitude = 31.201382;
//        current_longitude = 121.267081;
//        doSearchQuery(current_longitude + "", current_latitude + "");
        if (mPoiMarks.size() <= 0) {
            TreeMap<String, String> parkBikeMap = new TreeMap<>();
            parkBikeMap.put("lat", current_latitude + "");
            parkBikeMap.put("lng", current_longitude + "");
            getPresenter().getParkBike(parkBikeMap, false, false);

            cameraMove(current_latitude, current_longitude);
        }
    }

    private void doSearchQuery(String lng, String lat) {
        String url = GEOCODE + "key=b3b6959b675bc65e0cd61c02d1c7a415&location=" + lng + "," + lat + "&extensions=all";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    final GeocodeBean jsonTopicsBean = new Gson().fromJson(response.body().string(), GeocodeBean.class);
                    if ("1".equals(jsonTopicsBean.getStatus())) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //此时已在主线程中，更新UI
                                SPUtil.put(MainActivity.this, ADDRESS, jsonTopicsBean.getRegeocode().getFormatted_address());
                            }
                        });
                    }
                } catch (IOException e) {
                    ToastUtil.showShortToast(e + "");
                }
            }
        }).start();
    }

    @OnClick({R.id.rb_seek_car, R.id.rb_adopt, R.id.ib_close, R.id.fab_stop, R.id.fab_customer_service, R.id.fab_location, R.id.rv_use_car, R.id.riv_user_head, R.id.bt_iuthentication, R.id.ll_top_my, R.id.cl_search, R.id.tv_lock_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_seek_car://找车
                fabStop.setImageDrawable(getResources().getDrawable(R.mipmap.ic_stop));
                rbSeekCar.setChecked(true);

                TreeMap<String, String> selectBikeMap = new TreeMap<>();
                selectBikeMap.put("lat", current_latitude + "");
                selectBikeMap.put("lng", current_longitude + "");
                getPresenter().getSelectBike(selectBikeMap, false, false);
                break;
            case R.id.rb_adopt://领养
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {
                        startActivity(new Intent(this, AdoptActivity.class));
                    } else
                        startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.ib_close://关闭广告
                clMainAd.setVisibility(View.GONE);
                break;
            case R.id.fab_stop://停车场
                fabStop.setImageDrawable(getResources().getDrawable(R.mipmap.ic_stop_select));
                rbSeekCar.setChecked(false);

                TreeMap<String, String> parkBikeMap = new TreeMap<>();
                parkBikeMap.put("lat", current_latitude + "");
                parkBikeMap.put("lng", current_longitude + "");
                getPresenter().getParkBike(parkBikeMap, false, false);
                break;
            case R.id.fab_customer_service://客服
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {
                        new CustomerServiceDialog(this) {
                        }.show();
                    } else
                        startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.fab_location://定位
                cameraMove(current_latitude, current_longitude);
                //启动定位
                rxPermissionLocation();
                break;
            case R.id.rv_use_car://立即用车
                if (isFastClick()) {
                    if ("立即用车".equals(tvUseCar.getText()))
                        if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {
                            getPresenter().getCanUseCar(false, false);
                        } else
                            startActivity(new Intent(this, LoginActivity.class));
                    else
                        new CustomerReturnDialog(this, "是否确认还车") {
                            @Override
                            public void confirm() {
                                TreeMap<String, String> closeCarMap = new TreeMap<>();
                                closeCarMap.put("imei", carNum);
//                                closeCarMap.put("address", SPUtil.get(MainActivity.this, ADDRESS, "") + "");//上海市青浦区徐泾镇中国·梦谷
//                                closeCarMap.put("lat", current_latitude + "");
//                                closeCarMap.put("lng", current_longitude + "");
                                getPresenter().getCloseCar(closeCarMap, false, false);
                            }
                        }.show();
                }
                break;
            case R.id.riv_user_head://个人资料
                startActivityForResult(new Intent(this, PersonalDocumentActivity.class), REQUEST_CODE_91);
                break;
            case R.id.bt_iuthentication://认证
                break;
            case R.id.ll_top_my://我的
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {
                        if (!dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.openDrawer(llSidebarMain);
                        }
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                }
                break;
            case R.id.cl_search://搜索
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {
                        startActivityForResult(new Intent(this, SearchActivity.class), REQUEST_CODE_95);
                    } else
                        startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.tv_lock_car: //锁车
                if (isFastClick()) {
                    if ("临时锁车".equals(tvLockCar.getText())) {
                        getPresenter().getLockCar(false, false);
                        tvCarType.setText("临时锁车中");
                        tvLockCar.setText("开锁");
                    } else {
                        getPresenter().getUnlockCar(false, false);
                        tvCarType.setText("车辆使用中");
                        tvLockCar.setText("临时锁车");
                    }
                }
                break;
        }
    }

    @Override
    public void resultSelectBike(SelectBikeBean data) {
        if (data.getCode() == 200) {
            pois.clear();
            for (int i = 0; i < data.getData().getList().size(); i++) {
                LatLonPoint latLonPoint = new LatLonPoint(data.getData().getList().get(i).getLat(), data.getData().getList().get(i).getLng());
                PoiItem poiItem = new PoiItem("", latLonPoint, "", "");
                pois.add(poiItem);
            }
            mark(1);
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultParkBike(ParkBikeBean data) {
        if (data.getCode() == 200) {
            pois.clear();
            for (int i = 0; i < data.getData().getList().size(); i++) {
                LatLonPoint latLonPoint = new LatLonPoint(data.getData().getList().get(i).getLat(), data.getData().getList().get(i).getLng());
                PoiItem poiItem = new PoiItem("", latLonPoint, "", "");
                pois.add(poiItem);
            }
            mark(2);
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultUserInfo(UserInfoBean data) {
        Glide.with(ApplicationUtil.getContext()).load(BASE_LOCAL_URL + data.getData().getHeaderUrl()).apply(new RequestOptions().placeholder(R.mipmap.ic_default_head)).into(rivUserHead);
        StringBuffer buffer = new StringBuffer(data.getData().getPhone());
        tvSidebarUserPhone.setText(buffer.replace(3, 7, "****"));
        btIuthentication.setText(data.getData().getIs_on());

        for (int i = 0; i < 8; i++) {
            SidebarBean sidebarBean = new SidebarBean();
            sidebarBean.setIconSelect(sidebarIconSelect[i]);
            sidebarBean.setIconUnselect(sidebarIconUnselect[i]);
            sidebarBean.setName(sidebarName[i]);
            if (i == 0)
                sidebarBean.setShow(true);
            if (i == 0 || i == 1)
                sidebarBean.setMoney(data.getData().getBalance());
            sidebarBeanList.add(sidebarBean);
        }
        rvSidebar.setAdapter(sidebarAdapter = new SidebarAdapter(sidebarBeanList));
        sidebarAdapter.bindToRecyclerView(rvSidebar);
        sidebarAdapter.openLoadAnimation();

        sidebarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < sidebarBeanList.size(); i++) {
                    sidebarBeanList.get(i).setShow(false);
                }
                sidebarBeanList.get(position).setShow(true);
                sidebarAdapter.notifyDataSetChanged();

                switch (position) {
                    case 0://我的钱包
                        startActivity(new Intent(MainActivity.this, WalletActivity.class));
                        if (dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.closeDrawer(llSidebarMain);
                        }
                        break;
                    case 1://现金账户
                        startActivity(new Intent(MainActivity.this, AccountActivity.class));
                        if (dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.closeDrawer(llSidebarMain);
                        }
                        break;
                    case 2://我的优惠
                        startActivity(new Intent(MainActivity.this, CouponActivity.class));
                        if (dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.closeDrawer(llSidebarMain);
                        }
                        break;
                    case 3://我的行程
                        startActivity(new Intent(MainActivity.this, TripActivity.class));
                        if (dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.closeDrawer(llSidebarMain);
                        }
                        break;
                    case 4://招商加盟
                        startActivity(new Intent(MainActivity.this, JoinInActivity.class));
                        if (dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.closeDrawer(llSidebarMain);
                        }
                        break;
                    case 5://我的消息
                        startActivity(new Intent(MainActivity.this, MsgActivity.class));
                        if (dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.closeDrawer(llSidebarMain);
                        }
                        break;
                    case 6://用户指南
                        startActivity(new Intent(MainActivity.this, GuideActivity.class).putExtra("h5Type", 1));
                        if (dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.closeDrawer(llSidebarMain);
                        }
                        break;
                    case 7://设置
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        if (dlMain.isDrawerOpen(llSidebarMain)) {
                            dlMain.closeDrawer(llSidebarMain);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void resultCanUseCar(CanUseCarBean data) {
        if (data.getCode() == 200) {
            switch (data.getData().getStatus()) { //0可以用车，1未实名，2余额不足，3未充值押金，4有未完成的订单
                case 0:
                    rxPermissionCamera();
                    break;
                case 1:
                    startActivity(new Intent(this, IuthenticationActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(this, RechargeActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(this, DepositRechargeActivity.class));
                    break;
                case 4:
                    getPresenter().getCarStatus(false, false);
                    break;
            }
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultCarStatus(final CarStatusBean data) {
        if (data.getCode() == 200) {
            switch (data.getData().getStatus()) {//1骑行中，2临时锁车中，3骑行结束但未支付
                case 2:
                    tvCarType.setText("临时锁车中");
                    tvLockCar.setText("开锁");
                case 1:
                    carNum = data.getData().getImei();
                    llCarDetails.setVisibility(View.VISIBLE);
                    clReturnCarPrompt.setVisibility(View.VISIBLE);
                    tvUseCar.setText("我要还车");
                    tvCarNum.setText(Html.fromHtml("车辆编号 <font color=\"#F5C636\">" + data.getData().getImei() + "</font>"));
                    DecimalFormat df = new DecimalFormat("######0.00");
                    double distance = Double.parseDouble(df.format(data.getData().getBattery() * 0.55));//满电量可骑行55km
                    tvRemainingDistance.setCenterTopString(Html.fromHtml(distance + " <font color=\"#000000\">km</font>"));

                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            int endTime = Integer.parseInt(String.format("%010d", System.currentTimeMillis() / 1000));
                            String useTime = StartTimeToEndTime(timedate(data.getData().getStart() + ""), timedate(endTime + ""), 1);
                            tvUseTime.setCenterTopString(useTime);

                            int min = Integer.parseInt(StartTimeToEndTime(timedate(data.getData().getStart() + ""), timedate(endTime + ""), 3));
                            double money = min / 5 + 1;//min % 5 == 0 ? min / 5 + 1 : min / 5 + 2;
                            tvUseFee.setCenterTopString(Html.fromHtml(money + "<font color=\"#000000\">元</font>"));
                        }
                    };
                    MyThread thread = new MyThread();
                    thread.start();
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, PayActivity.class));
                    break;
            }
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void resultCloseCar(CloseCarBean data) {
        if (data.getCode() == 200) {
            llCarDetails.setVisibility(View.GONE);
            clReturnCarPrompt.setVisibility(View.GONE);
            tvUseCar.setText("立即用车");
            startActivity(new Intent(MainActivity.this, PayActivity.class));
        }
    }

    @Override
    public void resultIsOrder(IsOrderBean data) {
        if (data.getCode() == 200) {
            if (data.getData().isHas_order()) {
                getPresenter().getCarStatus(false, false);
            }
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultLockCar(LockCarBean data) {

    }

    @Override
    public void resultUnlockCar(UnlockCarBean data) {

    }

    @Override
    public void resultIsStopCar(IsStopCarBean data) {
        if (data.getCode() == 200) {
            if (data.getData().isResult())
                ToastUtil.showLongToast("目标地点可以还车");
            else
                ToastUtil.showLongToast("目标地点不可以还车");
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
