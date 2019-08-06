package com.ipd.mayachuxing.activity;

import android.content.Intent;

import androidx.appcompat.widget.AppCompatEditText;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：昵称
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class NicknameActivity extends BaseActivity {

    @BindView(R.id.tv_nickname)
    TopView tvNickname;
    @BindView(R.id.et_nickname)
    AppCompatEditText etNickname;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nickname;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
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
            setResult(RESULT_OK, new Intent().putExtra("modify_nickname", etNickname.getText().toString().trim()));
            finish();
        }
    }
}
