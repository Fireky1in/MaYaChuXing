package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.FeedBackBean;
import com.ipd.mayachuxing.bean.UploadImgBean;
import com.ipd.mayachuxing.common.view.GridRadioGroup;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.FeedBackContract;
import com.ipd.mayachuxing.presenter.FeedBackPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;
import okhttp3.RequestBody;

import static android.Manifest.permission.CAMERA;
import static com.ipd.mayachuxing.activity.PersonalDocumentActivity.getImageRequestBody;
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_100;
import static com.ipd.mayachuxing.common.config.UrlConfig.BASE_LOCAL_URL;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：我要举报
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */

public class ReportActivity extends BaseActivity<FeedBackContract.View, FeedBackContract.Presenter> implements FeedBackContract.View {

    @BindView(R.id.tv_report)
    TopView tvReport;
    @BindView(R.id.et_scanning)
    AppCompatEditText etScanning;
    @BindView(R.id.rg_report_type)
    GridRadioGroup rgReportType;
    @BindView(R.id.iv_upload)
    RadiusImageView ivUpload;
    @BindView(R.id.et_content)
    MultiLineEditText etContent;

    private String reportType = "";//举报类型
    private String uploadImg = "";//后台返的图片URL

    @Override
    public int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    public FeedBackContract.Presenter createPresenter() {
        return new FeedBackPresenter(this);
    }

    @Override
    public FeedBackContract.View createView() {
        return this;
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
                    TreeMap<String, RequestBody> map = new TreeMap<>();
                    map.put("file\";filename=\"" + ".jpeg", getImageRequestBody(PictureSelector.obtainMultipleResult(data).get(0).getCompressPath()));
                    getPresenter().getUploadImg(map, false, false);
                    break;
                case REQUEST_CODE_100:
                    etScanning.setText(data.getStringExtra("car_num"));
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
                    startActivityForResult(new Intent(ReportActivity.this, QRActivity.class), REQUEST_CODE_100);
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
                    TreeMap<String, String> canUnlockMap = new TreeMap<>();
                    canUnlockMap.put("item_no", etScanning.getText().toString().trim());
                    switch (rgReportType.flag) {
                        case 1:
                            reportType = "违停";
                            break;
                        case 2:
                            reportType = "加私锁";
                            break;
                        case 3:
                            reportType = "偷盗";
                            break;
                        case 4:
                            reportType = "恶意损坏";
                            break;
                        case 5:
                            reportType = "非法移车";
                            break;
                    }
                    canUnlockMap.put("type", reportType);
                    canUnlockMap.put("url", uploadImg);
                    canUnlockMap.put("supplement", etContent.getContentText().trim());
                    canUnlockMap.put("static", "2");
                    getPresenter().getFeedBack(canUnlockMap, false, false);
                }
                break;
        }
    }

    @Override
    public void resultUploadImg(UploadImgBean data) {
        if (data.getCode() == 200) {
            uploadImg = data.getData().getUrl();
            Glide.with(ApplicationUtil.getContext()).load(BASE_LOCAL_URL + data.getData().getUrl()).apply(new RequestOptions()).into(ivUpload);
        } else {
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
    public void resultFeedBack(FeedBackBean data) {
        if (data.getCode() == 200)
            finish();
        else {
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
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
