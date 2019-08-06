package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.TripAdapter;
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
 * Description ：我的行程
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class TripActivity extends BaseActivity {

    @BindView(R.id.tv_trip)
    TopView tvTrip;
    @BindView(R.id.rv_trip)
    RecyclerView rvTrip;
    @BindView(R.id.srl_trip)
    SwipeRefreshLayout srlTrip;

    private List<TestBean> testBeanList = new ArrayList<>();
    private TripAdapter tripAdapter;
    private int pageNum = 1;//页数

    @Override
    public int getLayoutId() {
        return R.layout.activity_trip;
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
        ImmersionBar.setTitleBar(this, tvTrip);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvTrip.setLayoutManager(layoutManager);
        rvTrip.addItemDecoration(new SpacesItemDecoration(50));
        rvTrip.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvTrip.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlTrip.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
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
                tripAdapter = new TripAdapter(testBeanList);
                rvTrip.setAdapter(tripAdapter);
                tripAdapter.bindToRecyclerView(rvTrip);
                tripAdapter.setEnableLoadMore(true);
                tripAdapter.openLoadAnimation();
                tripAdapter.disableLoadMoreIfNotFullPage();

                tripAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        startActivity(new Intent(TripActivity.this, TripDetailsActivity.class));
                    }
                });

                //上拉加载
                tripAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        rvTrip.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                            }
                        }, 1000);
                    }
                }, rvTrip);

                if (5 > 10) {
                    pageNum += 1;
                } else {
                    tripAdapter.loadMoreEnd();
                }
            } else {
                if ((5 - pageNum * 10) > 0) {
                    pageNum += 1;
                    for (int i = 0; i < 5; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    tripAdapter.addData(testBeanList);
                    tripAdapter.loadMoreComplete(); //完成本次
                } else {
                    for (int i = 0; i < 5; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    tripAdapter.addData(testBeanList);
                    tripAdapter.loadMoreEnd(); //完成所有加载
                }
            }
        } else {
            testBeanList.clear();
            tripAdapter = new TripAdapter(testBeanList);
            rvTrip.setAdapter(tripAdapter);
            tripAdapter.loadMoreEnd(); //完成所有加载
            tripAdapter.setEmptyView(R.layout.null_adopt_data, rvTrip);
        }
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlTrip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlTrip.setRefreshing(false);
            }
        });
    }
}
