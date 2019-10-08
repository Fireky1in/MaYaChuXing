package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.CouponAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.CouponListBean;
import com.ipd.mayachuxing.bean.PayDetailsBean;
import com.ipd.mayachuxing.common.view.SpacesItemDecoration;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.CouponListContract;
import com.ipd.mayachuxing.presenter.CouponListPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：我的优惠
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class CouponActivity extends BaseActivity<CouponListContract.View, CouponListContract.Presenter> implements CouponListContract.View {

    @BindView(R.id.tv_coupon)
    TopView tvCoupon;
    @BindView(R.id.rv_coupon)
    RecyclerView rvCoupon;
    @BindView(R.id.srl_coupon)
    SwipeRefreshLayout srlCoupon;

    private static Handler handler = new Handler();
    private List<CouponListBean.DataBean.ListBean> couponListBeanList = new ArrayList<>();//侧边栏总的券数据
    private List<PayDetailsBean.DataBean.CouponsBean> couponsBeanList;//可用的券数据
    private CouponAdapter couponAdapter;
    private int pageNum = 1;//页数
    private boolean isNextPage = false;//是否有下一页

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public CouponListContract.Presenter createPresenter() {
        return new CouponListPresenter(this);
    }

    @Override
    public CouponListContract.View createView() {
        return this;
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

        couponsBeanList = getIntent().getParcelableArrayListExtra("couponsBeanList");
    }

    @Override
    public void initData() {
        if (couponsBeanList == null) {
            TreeMap<String, String> couponListMap = new TreeMap<>();
            couponListMap.put("page", pageNum + "");
            couponListMap.put("limit", "10");
            getPresenter().getCouponList(couponListMap, false, false);
        } else {
            couponListBeanList.clear();
            for (int i = 0; i < couponsBeanList.size(); i++) {
                CouponListBean.DataBean.ListBean dataBean = new CouponListBean.DataBean.ListBean();
                dataBean.setId(couponsBeanList.get(i).getId());
                dataBean.setNum(couponsBeanList.get(i).getNum());
                dataBean.setConditions(couponsBeanList.get(i).getConditions());
                dataBean.setEnd_time(couponsBeanList.get(i).getEnd_time());
                couponListBeanList.add(dataBean);
            }

            if (couponListBeanList.size() > 0) {
                couponAdapter = new CouponAdapter(couponListBeanList, 2);
                rvCoupon.setAdapter(couponAdapter);
                couponAdapter.bindToRecyclerView(rvCoupon);
                couponAdapter.setEnableLoadMore(true);
                couponAdapter.openLoadAnimation();
                couponAdapter.disableLoadMoreIfNotFullPage();

                couponAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 使用postDelayed方式修改UI组件tvMessage的Text属性值
                                // 并且延迟执行
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setResult(RESULT_OK, new Intent().putExtra("couponId", couponListBeanList.get(position).getId()).putExtra("couponMoney", couponListBeanList.get(position).getConditions()));
                                        finish();
                                    }
                                }, 500);
                            }
                        }).start();
                    }
                });

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
                couponAdapter.loadMoreEnd();
            } else {
                couponListBeanList.clear();
                couponAdapter = new CouponAdapter(couponListBeanList, 2);
                rvCoupon.setAdapter(couponAdapter);
                couponAdapter.loadMoreEnd(); //完成所有加载
                couponAdapter.setEmptyView(R.layout.null_data, rvCoupon);
            }
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

    @Override
    public void resultCouponList(CouponListBean data) {
        if (data.getCode() == 200)
            if (data.getData().getList().size() > 0 || isNextPage) {
                if (pageNum == 1) {
                    couponListBeanList.clear();
                    couponListBeanList.addAll(data.getData().getList());
                    couponAdapter = new CouponAdapter(couponListBeanList, 1);
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

                    if (couponListBeanList.size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                    } else {
                        couponAdapter.loadMoreEnd();
                    }
                } else {
                    if (data.getData().getList().size() == 0)
                        couponAdapter.loadMoreEnd(); //完成所有加载
                    else if (data.getData().getList().size() >= 10) {
                        isNextPage = true;
                        pageNum += 1;
                        couponAdapter.addData(data.getData().getList());
                        couponAdapter.loadMoreComplete(); //完成本次
                    } else {
                        couponAdapter.addData(data.getData().getList());
                        couponAdapter.loadMoreEnd(); //完成所有加载
                    }
                }
            } else {
                couponListBeanList.clear();
                couponAdapter = new CouponAdapter(couponListBeanList, 1);
                rvCoupon.setAdapter(couponAdapter);
                couponAdapter.loadMoreEnd(); //完成所有加载
                couponAdapter.setEmptyView(R.layout.null_data, rvCoupon);
            }
        else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
