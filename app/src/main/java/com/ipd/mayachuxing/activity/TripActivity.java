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
import com.ipd.mayachuxing.bean.TripListBean;
import com.ipd.mayachuxing.common.view.SpacesItemDecoration;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.TripListContract;
import com.ipd.mayachuxing.presenter.TripListPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：我的行程
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class TripActivity extends BaseActivity<TripListContract.View, TripListContract.Presenter> implements TripListContract.View {

    @BindView(R.id.tv_trip)
    TopView tvTrip;
    @BindView(R.id.rv_trip)
    RecyclerView rvTrip;
    @BindView(R.id.srl_trip)
    SwipeRefreshLayout srlTrip;

    private List<TripListBean.DataBean.ListBean> tripListBeanList = new ArrayList<>();
    private TripAdapter tripAdapter;
    private int pageNum = 1;//页数
    private boolean isNextPage = false;//是否有下一页

    @Override
    public int getLayoutId() {
        return R.layout.activity_trip;
    }

    @Override
    public TripListContract.Presenter createPresenter() {
        return new TripListPresenter(this);
    }

    @Override
    public TripListContract.View createView() {
        return this;
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
        TreeMap<String, String> tripListMap = new TreeMap<>();
        tripListMap.put("page", pageNum + "");
        tripListMap.put("limit", "10");
        getPresenter().getTripList(tripListMap, false, false);
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

    @Override
    public void resultTripList(TripListBean data) {
        if (data.getCode() == 200) {
            if (data.getData().getList().size() > 0 || isNextPage) {
                if (pageNum == 1) {
                    tripListBeanList.clear();
                    tripListBeanList.addAll(data.getData().getList());
                    tripAdapter = new TripAdapter(tripListBeanList);
                    rvTrip.setAdapter(tripAdapter);
                    tripAdapter.bindToRecyclerView(rvTrip);
                    tripAdapter.setEnableLoadMore(true);
                    tripAdapter.openLoadAnimation();
                    tripAdapter.disableLoadMoreIfNotFullPage();

                    tripAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            startActivity(new Intent(TripActivity.this, TripDetailsActivity.class).putExtra("trip_id", tripListBeanList.get(position).getId()));
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

                    if (tripListBeanList.size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                    } else {
                        tripAdapter.loadMoreEnd();
                    }
                } else {
                    if (data.getData().getList().size() == 0)
                        tripAdapter.loadMoreEnd(); //完成所有加载
                   else if ((data.getData().getList().size() - pageNum * 10) >= 0) {
                        isNextPage = true;
                        pageNum += 1;
                        tripAdapter.addData(data.getData().getList());
                        tripAdapter.loadMoreComplete(); //完成本次
                    } else {
                        tripAdapter.addData(data.getData().getList());
                        tripAdapter.loadMoreEnd(); //完成所有加载
                    }
                }
            } else {
                tripListBeanList.clear();
                tripAdapter = new TripAdapter(tripListBeanList);
                rvTrip.setAdapter(tripAdapter);
                tripAdapter.loadMoreEnd(); //完成所有加载
                tripAdapter.setEmptyView(R.layout.null_data, rvTrip);
            }
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
