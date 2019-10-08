package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.ReturnDepositAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.ReturnDepositBean;
import com.ipd.mayachuxing.bean.ReturnDepositListBean;
import com.ipd.mayachuxing.bean.UserBalanceBean;
import com.ipd.mayachuxing.common.view.EditText_Clear;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.UserBalanceContract;
import com.ipd.mayachuxing.presenter.UserBalancePresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：退还押金
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 919/8/5.
 */
public class ReturnDepositActivity extends BaseActivity<UserBalanceContract.View, UserBalanceContract.Presenter> implements UserBalanceContract.View {

    @BindView(R.id.rv_return_deposit)
    RecyclerView rvReturnDeposit;
    @BindView(R.id.et_pay_code)
    EditText_Clear etPayCode;
    @BindView(R.id.et_pay_name)
    EditText_Clear etPayName;
    @BindView(R.id.tv_return_deposit)
    TopView tvReturnDeposit;

    private ReturnDepositAdapter returnDepositAdapter;
    private List<ReturnDepositListBean> returnDepositList = new ArrayList<>();
    private String[] str = new String[]{"试试是否能退款", "车费有点高", "车太少了", "车子骑着不舒服", "很少骑车了", "速度太慢了", "APP不稳定，总出错", "其他原因"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_return_deposit;
    }

    @Override
    public UserBalanceContract.Presenter createPresenter() {
        return new UserBalancePresenter(this);
    }

    @Override
    public UserBalanceContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvReturnDeposit);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvReturnDeposit.setLayoutManager(layoutManager);
        rvReturnDeposit.setItemAnimator(new DefaultItemAnimator());//加载动画
    }

    @Override
    public void initData() {
        for (String str : str) {
            ReturnDepositListBean returnDepositData = new ReturnDepositListBean();
            returnDepositData.setName(str);
            returnDepositData.setShow(false);
            returnDepositList.add(returnDepositData);
        }
        returnDepositAdapter = new ReturnDepositAdapter(returnDepositList);
        rvReturnDeposit.setAdapter(returnDepositAdapter);
        returnDepositAdapter.bindToRecyclerView(rvReturnDeposit);
        returnDepositAdapter.openLoadAnimation();
        returnDepositAdapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void initListener() {
        returnDepositAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (ReturnDepositListBean returnDepositData : returnDepositList) {
                    returnDepositData.setShow(false);
                }
                returnDepositList.get(position).setShow(true);
                returnDepositAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick(R.id.bt_return_deposit)
    public void onViewClicked() {
        if (isFastClick()) {
            if (!isEmpty(etPayName.getText().toString().trim()) && !isEmpty(etPayCode.getText().toString().trim())) {
                String reason = "";
                for (ReturnDepositListBean returnDepositData : returnDepositList) {
                    if (returnDepositData.isShow())
                        reason = returnDepositData.getName();
                }
                if (!isEmpty(reason)) {
                    TreeMap<String, String> returnDepositMap = new TreeMap<>();
                    returnDepositMap.put("ali_name", etPayName.getText().toString().trim());
                    returnDepositMap.put("ali_account", etPayCode.getText().toString().trim());
                    returnDepositMap.put("reason", reason);
                    getPresenter().getReturnDeposit(returnDepositMap, true, false);
                } else
                    ToastUtil.showShortToast("请选择退还押金原因!");
            } else
                ToastUtil.showShortToast("请填写账号信息！");
        }
    }

    @Override
    public void resultReturnDeposit(ReturnDepositBean data) {
        ToastUtil.showShortToast(data.getMessage());
        if (data.getCode() == 200)
            finish();
        else {
            if (data.getCode() == 203) {
                ApplicationUtil.getManager().finishActivity(MainActivity.class);
                //清除所有临时储存
                SPUtil.clear(ApplicationUtil.getContext());
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }
    }

    @Override
    public void resultUserBalance(UserBalanceBean data) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
