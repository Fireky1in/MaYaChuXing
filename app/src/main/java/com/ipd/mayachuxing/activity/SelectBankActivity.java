package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.SelectBackAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.BankListBean;
import com.ipd.mayachuxing.bean.DelBankBean;
import com.ipd.mayachuxing.common.view.CustomerReturnDialog;
import com.ipd.mayachuxing.common.view.SpacesItemDecoration;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.BankListContract;
import com.ipd.mayachuxing.presenter.BankListPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_94;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：选择银行卡
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class SelectBankActivity extends BaseActivity<BankListContract.View, BankListContract.Presenter> implements BankListContract.View {

    @BindView(R.id.tv_select_bank)
    TopView tvSelectBank;
    @BindView(R.id.rv_select_bank)
    RecyclerView rvSelectBank;
    @BindView(R.id.srl_select_bank)
    SwipeRefreshLayout srlSelectBank;

    private static Handler handler = new Handler();
    private List<BankListBean.DataBean.ListBean> bankListBeanList = new ArrayList<>();
    private SelectBackAdapter selectBackAdapter;
    private int pageNum = 1;//页数
    private int removePosition;//要移除的position;
    private int bankType;//1: 需要返回数据的， 2: 仅查看

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_bank;
    }

    @Override
    public BankListContract.Presenter createPresenter() {
        return new BankListPresenter(this);
    }

    @Override
    public BankListContract.View createView() {
        return this;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvSelectBank);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvSelectBank.setLayoutManager(layoutManager);
        rvSelectBank.addItemDecoration(new SpacesItemDecoration(50));
        rvSelectBank.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvSelectBank.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlSelectBank.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色

        bankType = getIntent().getIntExtra("bank_type", 0);
    }

    @Override
    public void initData() {
        TreeMap<String, String> bankListMap = new TreeMap<>();
        bankListMap.put("page", pageNum + "");
        bankListMap.put("limit", "10");
        getPresenter().getBankList(bankListMap, false, false);
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlSelectBank.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlSelectBank.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case REQUEST_CODE_94:
                    pageNum = 1;
                    initData();
                    break;
            }
        }
    }

    @OnClick(R.id.rv_add_bank)
    public void onViewClicked() {
        startActivityForResult(new Intent(this, AddBankActivity.class), REQUEST_CODE_94);
    }

    @Override
    public void resultBankList(BankListBean data) {
        if (data.getCode() == 200) {
            if (data.getData().getList().size() > 0) {
                if (pageNum == 1) {
                    bankListBeanList.clear();
                    bankListBeanList.addAll(data.getData().getList());
                    selectBackAdapter = new SelectBackAdapter(bankListBeanList);
                    rvSelectBank.setAdapter(selectBackAdapter);
                    selectBackAdapter.bindToRecyclerView(rvSelectBank);
                    selectBackAdapter.setEnableLoadMore(true);
                    selectBackAdapter.openLoadAnimation();
                    selectBackAdapter.disableLoadMoreIfNotFullPage();

                    selectBackAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                            for (int i = 0; i < bankListBeanList.size(); i++) {
                                bankListBeanList.get(i).setShow(false);
                            }
                            bankListBeanList.get(position).setShow(true);
                            selectBackAdapter.notifyDataSetChanged();

                            if (bankType == 1) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 使用postDelayed方式修改UI组件tvMessage的Text属性值
                                        // 并且延迟执行
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                setResult(RESULT_OK, new Intent().putExtra("bank_id", bankListBeanList.get(position).getBid()).putExtra("bank_name", bankListBeanList.get(position).getBank()).putExtra("bank_code", bankListBeanList.get(position).getCard()));
                                                finish();
                                            }
                                        }, 500);
                                    }
                                }).start();
                            }
                        }
                    });

                    selectBackAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            switch (view.getId()) {
                                case R.id.bt_bank_del:
                                    if (isFastClick())
                                        new CustomerReturnDialog(SelectBankActivity.this, "是否删除银行卡") {
                                            @Override
                                            public void confirm() {
                                                removePosition = position;

                                                TreeMap<String, String> delBankMap = new TreeMap<>();
                                                delBankMap.put("bid", bankListBeanList.get(position).getBid() + "");
                                                getPresenter().getDelBank(delBankMap, false, false);
                                            }
                                        }.show();
                                    break;
                            }
                        }
                    });

                    //上拉加载
                    selectBackAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            rvSelectBank.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            }, 1000);
                        }
                    }, rvSelectBank);

                    if (bankListBeanList.size() >= 10) {
                        pageNum += 1;
                    } else {
                        selectBackAdapter.loadMoreEnd();
                    }
                } else {
                    if ((data.getData().getList().size() - pageNum * 10) >= 0) {
                        pageNum += 1;
                        selectBackAdapter.addData(data.getData().getList());
                        selectBackAdapter.loadMoreComplete(); //完成本次
                    } else {
                        selectBackAdapter.addData(data.getData().getList());
                        selectBackAdapter.loadMoreEnd(); //完成所有加载
                    }
                }
            } else {
                bankListBeanList.clear();
                selectBackAdapter = new SelectBackAdapter(bankListBeanList);
                rvSelectBank.setAdapter(selectBackAdapter);
                selectBackAdapter.loadMoreEnd(); //完成所有加载
                selectBackAdapter.setEmptyView(R.layout.null_data, rvSelectBank);
            }
        }
    }

    @Override
    public void resultDelBank(DelBankBean data) {
        ToastUtil.showLongToast(data.getMessage());
        if (data.getCode() == 200) {
            bankListBeanList.remove(removePosition);
            selectBackAdapter.notifyDataSetChanged();
            selectBackAdapter.setEmptyView(R.layout.null_data, rvSelectBank);
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
