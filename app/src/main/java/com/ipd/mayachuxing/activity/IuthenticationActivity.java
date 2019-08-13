package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.IuthenticationBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.IuthenticationContract;
import com.ipd.mayachuxing.presenter.IuthenticationPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.common.config.IConstants.IUTHENTICATION;
import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.VerifyUtils.isChineseCard;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：实名认证
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/12.
 */
public class IuthenticationActivity extends BaseActivity<IuthenticationContract.View, IuthenticationContract.Presenter> implements IuthenticationContract.View {

    @BindView(R.id.tv_iuthentication)
    TopView tvIuthentication;
    @BindView(R.id.et_name)
    MaterialEditText etName;
    @BindView(R.id.et_id_card)
    MaterialEditText etIdCard;

    @Override
    public int getLayoutId() {
        return R.layout.activity_iuthentication;
    }

    @Override
    public IuthenticationContract.Presenter createPresenter() {
        return new IuthenticationPresenter(this);
    }

    @Override
    public IuthenticationContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvIuthentication);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @OnClick({R.id.ll_top_back, R.id.rv_iuthentication})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_top_back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.rv_iuthentication:
                if (!isEmpty(etName.getText().toString().trim()) && isChineseCard(etIdCard.getText().toString().trim()) && isFastClick()) {
                    TreeMap<String, String> iuthenticationMap = new TreeMap<>();
                    iuthenticationMap.put("name", etName.getText().toString().trim());
                    iuthenticationMap.put("id_card", etIdCard.getText().toString().trim());
                    getPresenter().getIuthentication(iuthenticationMap, true, false);
                } else
                    ToastUtil.showLongToast("请填写真实资料信息");
                break;
        }
    }

    @Override
    public void resultIuthentication(IuthenticationBean data) {
        if (data.getCode() == 200) {
            SPUtil.put(this, IUTHENTICATION, "is_iuthentication");
            startActivity(new Intent(this, DepositRechargeActivity.class));
            finish();
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
