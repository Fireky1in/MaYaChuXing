package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.CouponAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.bean.TestBean;
import com.ipd.mayachuxing.common.view.SpacesItemDecoration;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description ：我的优惠
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class CouponActivity extends BaseActivity {

    @BindView(R.id.tv_coupon)
    TopView tvCoupon;
    @BindView(R.id.rv_coupon)
    RecyclerView rvCoupon;
    @BindView(R.id.srl_coupon)
    SwipeRefreshLayout srlCoupon;

    private List<TestBean> testBeanList = new ArrayList<>();
    private CouponAdapter couponAdapter;
    private int pageNum = 1;//页数

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
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
        ImmersionBar.setTitleBar(this, tvCoupon);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvCoupon.setLayoutManager(layoutManager);
        rvCoupon.addItemDecoration(new SpacesItemDecoration(20));
        rvCoupon.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvCoupon.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlCoupon.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
    }

    @Override
    public void initData() {
        if (5 > 0) {
            if (pageNum == 1) {
                testBeanList.clear();
                for (int i = 0; i < 5; i++) {
                    TestBean testBean = new TestBean();
                    testBeanList.add(testBean);
                }
//                testBean.addAll(data.getData().getMessageList());
                couponAdapter = new CouponAdapter(testBeanList);
                rvCoupon.setAdapter(couponAdapter);
                couponAdapter.bindToRecyclerView(rvCoupon);
                couponAdapter.setEnableLoadMore(true);
                couponAdapter.openLoadAnimation();
                couponAdapter.disableLoadMoreIfNotFullPage();

                //上拉加载
                couponAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        rvCoupon.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                            }
                        }, 1000);
                    }
                }, rvCoupon);

                if (5 > 10) {
                    pageNum += 1;
                } else {
                    couponAdapter.loadMoreEnd();
                }
            } else {
                if ((5 - pageNum * 10) > 0) {
                    pageNum += 1;
                    for (int i = 0; i < 5; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    couponAdapter.addData(testBeanList);
                    couponAdapter.loadMoreComplete(); //完成本次
                } else {
                    for (int i = 0; i < 5; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    couponAdapter.addData(testBeanList);
                    couponAdapter.loadMoreEnd(); //完成所有加载
                }
            }
        } else {
            testBeanList.clear();
            couponAdapter = new CouponAdapter(testBeanList);
            rvCoupon.setAdapter(couponAdapter);
            couponAdapter.loadMoreEnd(); //完成所有加载
            couponAdapter.setEmptyView(R.layout.null_adopt_data, rvCoupon);
        }
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlCoupon.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlCoupon.setRefreshing(false);
            }
        });
    }
}
