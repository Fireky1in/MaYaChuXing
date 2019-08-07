package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.CAMERA;
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_90;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：我要举报
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */

public class ReportActivity extends BaseActivity {

    @BindView(R.id.tv_report)
    TopView tvReport;
    @BindView(R.id.et_scanning)
    AppCompatEditText etScanning;
    @BindView(R.id.rb_malfunction_1)
    RadioButton rbMalfunction1;
    @BindView(R.id.rb_malfunction_2)
    RadioButton rbMalfunction2;
    @BindView(R.id.rb_malfunction_3)
    RadioButton rbMalfunction3;
    @BindView(R.id.rb_malfunction_4)
    RadioButton rbMalfunction4;
    @BindView(R.id.rb_malfunction_5)
    RadioButton rbMalfunction5;
    @BindView(R.id.rb_malfunction_6)
    RadioButton rbMalfunction6;
    @BindView(R.id.rb_malfunction_7)
    RadioButton rbMalfunction7;
    @BindView(R.id.rb_malfunction_8)
    RadioButton rbMalfunction8;
    @BindView(R.id.rb_malfunction_9)
    RadioButton rbMalfunction9;
    @BindView(R.id.rb_malfunction_10)
    RadioButton rbMalfunction10;
    @BindView(R.id.rb_malfunction_11)
    RadioButton rbMalfunction11;
    @BindView(R.id.iv_upload)
    RadiusImageView ivUpload;
    @BindView(R.id.et_content)
    MultiLineEditText etContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_report;
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
        ImmersionBar.setTitleBar(this, tvReport);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    Glide.with(this)
                            .load(PictureSelector.obtainMultipleResult(data).get(0).getCompressPath())
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                    ivUpload.setImageDrawable(resource);
                                }
                            });
                    break;
            }
        }
    }

    // 相机权限
    private void rxPermissionCamera() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    startActivityForResult(new Intent(ReportActivity.this, QRActivity.class), REQUEST_CODE_90);
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
    }

    @OnClick({R.id.iv_scanning, R.id.iv_upload, R.id.rv_report})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scanning:
                rxPermissionCamera();
                break;
            case R.id.iv_upload:
                PictureSelector.create(ReportActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)// 最大图片选择数量 int
                        .isCamera(true)
                        .compress(true)
                        .minimumCompressSize(100)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.rv_report:
                if (isFastClick()) {
                    finish();
                }
                break;
        }
    }
}
