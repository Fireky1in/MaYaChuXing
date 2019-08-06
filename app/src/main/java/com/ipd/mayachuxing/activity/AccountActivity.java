package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.AccountAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.bean.TestBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：现金账户
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/3.
 */
public class AccountActivity extends BaseActivity {

    @BindView(R.id.tv_account)
    TopView tvAccount;
    @BindView(R.id.tv_balance_fee)
    SuperTextView tvBalanceFee;
    @BindView(R.id.rv_account_detailed)
    RecyclerView rvAccountDetailed;
    @BindView(R.id.srl_account_detailed)
    SwipeRefreshLayout srlAccountDetailed;

    private AccountAdapter accountAdapter;
    private List<TestBean> testBeanList = new ArrayList<>();
    private int pageNum = 1;//页数

    @Override
    public int getLayoutId() {
        return R.layout.activity_account;
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
        ImmersionBar.setTitleBar(this, tvAccount);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvAccountDetailed.setLayoutManager(layoutManager);
        rvAccountDetailed.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvAccountDetailed.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlAccountDetailed.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
    }

    @Override
    public void initData() {
        tvBalanceFee.setLeftString("2,8666.00");

        if (9 > 0) {
            if (pageNum == 1) {
                testBeanList.clear();
                for (int i = 0; i < 9; i++) {
                    TestBean testBean = new TestBean();
                    testBeanList.add(testBean);
                }
//                testBean.addAll(data.getData().getMessageList());
                accountAdapter = new AccountAdapter(testBeanList);
                rvAccountDetailed.setAdapter(accountAdapter);
                accountAdapter.bindToRecyclerView(rvAccountDetailed);
                accountAdapter.setEnableLoadMore(true);
                accountAdapter.openLoadAnimation();
                accountAdapter.disableLoadMoreIfNotFullPage();

                //上拉加载
                accountAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        rvAccountDetailed.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                            }
                        }, 1000);
                    }
                }, rvAccountDetailed);

                if (9 > 10) {
                    pageNum += 1;
                } else {
                    accountAdapter.loadMoreEnd();
                }
            } else {
                if ((9 - pageNum * 10) > 0) {
                    pageNum += 1;
                    for (int i = 0; i < 9; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    accountAdapter.addData(testBeanList);
                    accountAdapter.loadMoreComplete(); //完成本次
                } else {
                    for (int i = 0; i < 9; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    accountAdapter.addData(testBeanList);
                    accountAdapter.loadMoreEnd(); //完成所有加载
                }
            }
        } else {
            testBeanList.clear();
            accountAdapter = new AccountAdapter(testBeanList);
            rvAccountDetailed.setAdapter(accountAdapter);
            accountAdapter.loadMoreEnd(); //完成所有加载
            accountAdapter.setEmptyView(R.layout.null_adopt_data, rvAccountDetailed);
        }
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlAccountDetailed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlAccountDetailed.setRefreshing(false);
            }
        });
    }

    @OnClick(R.id.tv_withdraw)
    public void onViewClicked() {
        if (isFastClick())
            startActivity(new Intent(this, WithdrawActivity.class));
    }
}
