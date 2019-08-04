package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.SidebarAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.bean.SidebarBean;
import com.ipd.mayachuxing.common.view.CustomLinearLayoutManager;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.L;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static com.ipd.mayachuxing.common.config.IConstants.IS_LOGIN;
import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：首页
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/3.
 */
public class MainActivity extends BaseActivity implements AMap.OnMyLocationChangeListener {

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

    private SidebarAdapter sidebarAdapter;
    private List<SidebarBean> sidebarBeanList = new ArrayList<>();
    private AMap aMap;
    private MyLocationStyle myLocationStyle = new MyLocationStyle();//定位小蓝点样式
    private View infoWindow = null;
    private double current_latitude, current_longitude;//经纬度
    private int[] sidebarIconSelect = new int[]{R.drawable.ic_wallet_select, R.drawable.ic_account_select, R.drawable.ic_coupon_select, R.drawable.ic_trip_select, R.drawable.ic_join_in_select, R.drawable.ic_msg_select, R.drawable.ic_guide_select, R.drawable.ic_setting_select};
    private int[] sidebarIconUnselect = new int[]{R.drawable.ic_wallet, R.drawable.ic_account, R.drawable.ic_coupon, R.drawable.ic_trip, R.drawable.ic_join_in, R.drawable.ic_msg, R.drawable.ic_guide, R.drawable.ic_setting};
    private String[] sidebarName = new String[]{"我的钱包", "现金账户", "我的优惠", "我的行程", "招商加盟", "我的消息", "用户指南", "设置"};

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

    @SuppressLint("WrongConstant")
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

//        mark();

        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvSidebar.setLayoutManager(layoutManager);
        rvSidebar.setNestedScrollingEnabled(false);
        rvSidebar.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvSidebar.setItemAnimator(new DefaultItemAnimator());//加载动画

        for (int i = 0; i < 8; i++) {
            SidebarBean sidebarBean = new SidebarBean();
            sidebarBean.setIconSelect(sidebarIconSelect[i]);
            sidebarBean.setIconUnselect(sidebarIconUnselect[i]);
            sidebarBean.setName(sidebarName[i]);
            if (i == 0)
                sidebarBean.setShow(true);
            sidebarBeanList.add(sidebarBean);
        }
        rvSidebar.setAdapter(sidebarAdapter = new SidebarAdapter(sidebarBeanList));
        sidebarAdapter.bindToRecyclerView(rvSidebar);
        sidebarAdapter.openLoadAnimation();

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
                    // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
                    aMap.setMyLocationEnabled(true);
                    aMap.setOnMyLocationChangeListener(MainActivity.this);

                    // 默认模式，连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动，1秒1次定位
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
                    // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒
//                    myLocationStyle.interval(5000);

                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);
                    myLocationStyle.myLocationIcon(bitmapDescriptor);
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

    // 相机权限
    private void rxPermissionCamera() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    startActivity(new Intent(MainActivity.this, QRActivity.class));
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
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
    private void mark() {
        LatLng latLng = new LatLng(31.17514623031188, 121.26881200508495);
        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                if (infoWindow == null) {
                    infoWindow = LayoutInflater.from(MainActivity.this).inflate(
                            R.layout.marker, null);
                }
                return infoWindow;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mark))
                .draggable(false)
                .visible(true)
                .anchor(0.5f, 0.5f)
                .alpha(0.8f);
        Marker marker = aMap.addMarker(markerOptions);
        Animation animation = new RotateAnimation(0, 0, 0, 0, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        marker.setAnimation(animation);
        marker.startAnimation();
        marker.showInfoWindow();
    }

    @Override
    public void initData() {
        rivUserHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_foreground));
        tvSidebarUserPhone.setText("18502994087");
        btIuthentication.setText("未认证");
    }

    @Override
    public void initListener() {
        sidebarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < sidebarBeanList.size(); i++) {
                    sidebarBeanList.get(i).setShow(false);
                }
                sidebarBeanList.get(position).setShow(true);
                sidebarAdapter.notifyDataSetChanged();
            }
        });
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
        L.i("MyLocation=[" + location.getLongitude() + ", " + location.getLatitude() + "]");
        current_latitude = location.getLatitude();
        current_longitude = location.getLongitude();
    }

    @OnClick({R.id.rb_seek_car, R.id.rb_adopt, R.id.fab_stop, R.id.fab_customer_service, R.id.fab_location, R.id.rv_use_car, R.id.riv_user_head, R.id.bt_iuthentication, R.id.ll_top_my, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_seek_car://找车
                fabStop.setImageDrawable(getResources().getDrawable(R.mipmap.ic_stop));
                rbSeekCar.setChecked(true);
                break;
            case R.id.rb_adopt://领养
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {
                        startActivity(new Intent(this, AdoptActivity.class));
                    } else
                        startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.fab_stop://停车场
                fabStop.setImageDrawable(getResources().getDrawable(R.mipmap.ic_stop_select));
                rbSeekCar.setChecked(false);
                break;
            case R.id.fab_customer_service://客服
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {

                    } else
                        startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.fab_location://定位
                //启动定位
                rxPermissionLocation();
                break;
            case R.id.rv_use_car://立即用车
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {
                        rxPermissionCamera();
                    } else
                        startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.riv_user_head://个人资料
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
            case R.id.ll_search://搜索
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(this, IS_LOGIN, "") + "")) {

                    } else
                        startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }
}
