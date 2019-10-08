package com.ipd.mayachuxing.activity;

import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.ModifyNameBean;
import com.ipd.mayachuxing.bean.UploadHeadBean;
import com.ipd.mayachuxing.bean.UserInfoBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.PersonalDocumentContract;
import com.ipd.mayachuxing.presenter.PersonalDocumentPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：昵称
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class NicknameActivity extends BaseActivity<PersonalDocumentContract.View, PersonalDocumentContract.Presenter> implements PersonalDocumentContract.View {

    @BindView(R.id.tv_nickname)
    TopView tvNickname;
    @BindView(R.id.et_nickname)
    AppCompatEditText etNickname;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nickname;
    }

    @Override
    public PersonalDocumentContract.Presenter createPresenter() {
        return new PersonalDocumentPresenter(this);
    }

    @Override
    public PersonalDocumentContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvNickname);

        if (!isEmpty(getIntent().getStringExtra("nickname")))
            etNickname.setText(getIntent().getStringExtra("nickname"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.rv_confirm)
    public void onViewClicked() {
        if (isFastClick() && !isEmpty(etNickname.getText().toString().trim())) {
            TreeMap<String, String> modifyNameMap = new TreeMap<>();
            modifyNameMap.put("nickname", etNickname.getText().toString().trim());
            getPresenter().getModifyName(modifyNameMap, false, false);
        }
    }

    @Override
    public void resultUploadHead(UploadHeadBean data) {

    }

    @Override
    public void resultModifyName(ModifyNameBean data) {
        if (data.getCode() == 200) {
            setResult(RESULT_OK, new Intent().putExtra("modify_nickname", etNickname.getText().toString().trim()));
            finish();
        } else {
            ToastUtil.showLongToast(data.getMessage());
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
    public void resultUserInfo(UserInfoBean data) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
