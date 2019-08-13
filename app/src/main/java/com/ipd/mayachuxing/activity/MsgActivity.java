package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.MsgAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.MsgListBean;
import com.ipd.mayachuxing.common.view.SpacesItemDecoration;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.MsgListContract;
import com.ipd.mayachuxing.presenter.MsgListPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：我的消息
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class MsgActivity extends BaseActivity<MsgListContract.View, MsgListContract.Presenter> implements MsgListContract.View {

    @BindView(R.id.tv_msg)
    TopView tvMsg;
    @BindView(R.id.rv_msg)
    RecyclerView rvMsg;
    @BindView(R.id.srl_msg)
    SwipeRefreshLayout srlMsg;

    private List<MsgListBean.DataBean> msgListBeanList = new ArrayList<>();
    private MsgAdapter msgAdapter;
    private int pageNum = 1;//页数

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg;
    }

    @Override
    public MsgListContract.Presenter createPresenter() {
        return new MsgListPresenter(this);
    }

    @Override
    public MsgListContract.View createView() {
        return this;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMsg);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvMsg.setLayoutManager(layoutManager);
        rvMsg.addItemDecoration(new SpacesItemDecoration(50));
        rvMsg.setItemAnimator(new DefaultItemAnimator());//加载动画
        srlMsg.setColorSchemeResources(R.color.tx_bottom_navigation_select);//刷新圈颜色
    }

    @Override
    public void initData() {
        TreeMap<String, String> msgListMap = new TreeMap<>();
        msgListMap.put("page", pageNum + "");
        msgListMap.put("limit", "10");
        getPresenter().getMsgList(msgListMap, false, false);


    }

    @Override
    public void initListener() {
        //下拉刷新
        srlMsg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlMsg.setRefreshing(false);
            }
        });
    }

    @Override
    public void resultMsgList(MsgListBean data) {
        if (data.getCode() == 200) {
            if (data.getData().size() > 0) {
                if (pageNum == 1) {
                    msgListBeanList.clear();
                    msgListBeanList.addAll(data.getData());
                    msgAdapter = new MsgAdapter(msgListBeanList);
                    rvMsg.setAdapter(msgAdapter);
                    msgAdapter.bindToRecyclerView(rvMsg);
                    msgAdapter.setEnableLoadMore(true);
                    msgAdapter.openLoadAnimation();
                    msgAdapter.disableLoadMoreIfNotFullPage();

                    //上拉加载
                    msgAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                        @Override
                        public void onLoadMoreRequested() {
                            rvMsg.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            }, 1000);
                        }
                    }, rvMsg);

                    if (msgListBeanList.size() > 10) {
                        pageNum += 1;
                    } else {
                        msgAdapter.loadMoreEnd();
                    }
                } else {
                    if ((msgListBeanList.size() - pageNum * 10) > 0) {
                        pageNum += 1;
                        msgAdapter.addData(msgListBeanList);
                        msgAdapter.loadMoreComplete(); //完成本次
                    } else {
                        msgAdapter.addData(msgListBeanList);
                        msgAdapter.loadMoreEnd(); //完成所有加载
                    }
                }
            } else {
                msgListBeanList.clear();
                msgAdapter = new MsgAdapter(msgListBeanList);
                rvMsg.setAdapter(msgAdapter);
                msgAdapter.loadMoreEnd(); //完成所有加载
                msgAdapter.setEmptyView(R.layout.null_adopt_data, rvMsg);
            }
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
