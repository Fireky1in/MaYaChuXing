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
 * Description ：我的消息
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class MsgActivity extends BaseActivity {

    @BindView(R.id.tv_msg)
    TopView tvMsg;
    @BindView(R.id.rv_msg)
    RecyclerView rvMsg;
    @BindView(R.id.srl_msg)
    SwipeRefreshLayout srlMsg;

    private List<TestBean> testBeanList = new ArrayList<>();
    private MsgAdapter msgAdapter;
    private int pageNum = 1;//页数

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg;
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
        if (5 > 0) {
            if (pageNum == 1) {
                testBeanList.clear();
                for (int i = 0; i < 5; i++) {
                    TestBean testBean = new TestBean();
                    testBeanList.add(testBean);
                }
//                testBean.addAll(data.getData().getMessageList());
                msgAdapter = new MsgAdapter(testBeanList);
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

                if (5 > 10) {
                    pageNum += 1;
                } else {
                    msgAdapter.loadMoreEnd();
                }
            } else {
                if ((5 - pageNum * 10) > 0) {
                    pageNum += 1;
                    for (int i = 0; i < 5; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    msgAdapter.addData(testBeanList);
                    msgAdapter.loadMoreComplete(); //完成本次
                } else {
                    for (int i = 0; i < 5; i++) {
                        TestBean testBean = new TestBean();
                        testBeanList.add(testBean);
                    }
                    msgAdapter.addData(testBeanList);
                    msgAdapter.loadMoreEnd(); //完成所有加载
                }
            }
        } else {
            testBeanList.clear();
            msgAdapter = new MsgAdapter(testBeanList);
            rvMsg.setAdapter(msgAdapter);
            msgAdapter.loadMoreEnd(); //完成所有加载
            msgAdapter.setEmptyView(R.layout.null_adopt_data, rvMsg);
        }
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
}
