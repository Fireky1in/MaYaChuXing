package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.WalletAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.ReturnDepositBean;
import com.ipd.mayachuxing.bean.UserBalanceBean;
import com.ipd.mayachuxing.common.view.CustomerReturnDialog;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.UserBalanceContract;
import com.ipd.mayachuxing.presenter.UserBalancePresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：钱包
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 919/8/5.
 */
public class WalletActivity extends BaseActivity<UserBalanceContract.View, UserBalanceContract.Presenter> implements UserBalanceContract.View {

    @BindView(R.id.tv_wallet)
    TopView tvWallet;
    @BindView(R.id.tv_balance_fee)
    SuperTextView tvBalanceFee;
    @BindView(R.id.tv_recharge)
    AppCompatTextView tvRecharge;
    @BindView(R.id.rv_wallet_detailed)
    RecyclerView rvWalletDetailed;
    @BindView(R.id.srl_wallet_detailed)
    SwipeRefreshLayout srlWalletDetailed;

    private WalletAdapter walletAdapter;
    private List<UserBalanceBean.DataBean.ListBean> userBalanceBeanList = new ArrayList<>();
    private int pageNum = 1;//页数
    private boolean isNextPage = false;//是否有下一页

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    public UserBalanceContract.Presenter createPresenter() {
        return new UserBalancePresenter(this);
    }

    @Override
    public UserBalanceContract.View createView() {
        return this;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvWallet);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvWalletDetailed.setLayoutManager(layoutManager);
        rvWalletDetailed.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvWalletDetailed.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlWalletDetailed.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
    }

    @Override
    public void initData() {
        TreeMap<String, String> userBalanceMap = new TreeMap<>();
        userBalanceMap.put("static", "0");
        userBalanceMap.put("page", pageNum + "");
        userBalanceMap.put("limit", "10");
        getPresenter().getUserBalance(userBalanceMap, false, false);
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlWalletDetailed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlWalletDetailed.setRefreshing(false);
            }
        });
    }

    @OnClick({R.id.tv_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recharge:
                if (isFastClick())
                    startActivity(new Intent(this, RechargeActivity.class));
                break;
        }
    }

    @Override
    public void resultReturnDeposit(ReturnDepositBean data) {
        ToastUtil.showLongToast(data.getMessage());
        if (data.getCode() == 200)
            finish();
    }

    @Override
    public void resultUserBalance(UserBalanceBean data) {
        tvBalanceFee.setLeftString(data.getData().getBalance());

        if (data.getData().getList().size() > 0 || isNextPage) {
            if (pageNum == 1) {
                userBalanceBeanList.clear();
                userBalanceBeanList.addAll(data.getData().getList());
                walletAdapter = new WalletAdapter(userBalanceBeanList);
                rvWalletDetailed.setAdapter(walletAdapter);
                walletAdapter.bindToRecyclerView(rvWalletDetailed);
                walletAdapter.setEnableLoadMore(true);
                walletAdapter.openLoadAnimation();
                walletAdapter.disableLoadMoreIfNotFullPage();

                //上拉加载
                walletAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {
                        rvWalletDetailed.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initData();
                            }
                        }, 1000);
                    }
                }, rvWalletDetailed);

                if (userBalanceBeanList.size() >= 10) {
                    isNextPage = true;
                    pageNum += 1;
                } else {
                    walletAdapter.loadMoreEnd();
                }
            } else {
                if (data.getData().getList().size() == 0)
                    walletAdapter.loadMoreEnd(); //完成所有加载
                else if (data.getData().getList().size() >= 10) {
                    isNextPage = true;
                    pageNum += 1;
                    walletAdapter.addData(data.getData().getList());
                    walletAdapter.loadMoreComplete(); //完成本次
                } else {
                    walletAdapter.addData(data.getData().getList());
                    walletAdapter.loadMoreEnd(); //完成所有加载
                }
            }
        } else {
            userBalanceBeanList.clear();
            walletAdapter = new WalletAdapter(userBalanceBeanList);
            rvWalletDetailed.setAdapter(walletAdapter);
            walletAdapter.loadMoreEnd(); //完成所有加载
            walletAdapter.setEmptyView(R.layout.null_data, rvWalletDetailed);
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
