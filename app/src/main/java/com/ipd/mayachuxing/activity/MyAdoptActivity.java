package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.MyAdoptAdapter;
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
 * Description ：领养
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/4.
 */
public class MyAdoptActivity extends BaseActivity {

    @BindView(R.id.tv_my_adopt)
    TopView tvMyAdopt;
    @BindView(R.id.rv_my_adopt)
    RecyclerView rvMyAdopt;
    @BindView(R.id.srl_my_adopt)
    SwipeRefreshLayout srlMyAdopt;

    private List<TestBean> testBeanList = new ArrayList<>();
    private MyAdoptAdapter myAdoptAdapter;
    private int pageNum = 1;//页数

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_adopt;
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
        ImmersionBar.setTitleBar(this, tvMyAdopt);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvMyAdopt.setLayoutManager(layoutManager);
        rvMyAdopt.addItemDecoration(new SpacesItemDecoration(50));
        rvMyAdopt.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvMyAdopt.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlMyAdopt.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
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
                myAdoptAdapter = new MyAdoptAdapter(testBeanList);
                rvMyAdopt.setAdapter(myAdoptAdapter);
                myAdoptAdapter.bindToRecyclerView(rvMyAdopt);
                myAdoptAdapter.setEnableLoadMore(true);
                myAdoptAdapter.openLoadAnimation();
                myAdoptAdapter.disableLoadMoreIfNotFullPage();

                //上拉加载
                myAdoptAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        rvMyAdopt.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                            }
                        }, 1000);
                    }
                }, rvMyAdopt);

                if (5 > 10) {
                    pageNum += 1;
                } else {
                    myAdoptAdapter.loadMoreEnd();
                }
            } else {
                if ((5 - pageNum * 10) > 0) {
                    pageNum += 1;
                    for (int i = 0; i < 5; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    myAdoptAdapter.addData(testBeanList);
                    myAdoptAdapter.loadMoreComplete(); //完成本次
                } else {
                    for (int i = 0; i < 5; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    myAdoptAdapter.addData(testBeanList);
                    myAdoptAdapter.loadMoreEnd(); //完成所有加载
                }
            }
        } else {
            testBeanList.clear();
            myAdoptAdapter = new MyAdoptAdapter(testBeanList);
            rvMyAdopt.setAdapter(myAdoptAdapter);
            myAdoptAdapter.loadMoreEnd(); //完成所有加载
            myAdoptAdapter.setEmptyView(R.layout.null_adopt_data, rvMyAdopt);
        }
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlMyAdopt.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlMyAdopt.setRefreshing(false);
            }
        });
    }
}
