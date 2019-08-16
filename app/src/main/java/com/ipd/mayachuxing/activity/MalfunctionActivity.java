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
import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_99;
import static com.ipd.mayachuxing.common.config.UrlConfig.BASE_LOCAL_URL;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：上报故障
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class MalfunctionActivity extends BaseActivity<FeedBackContract.View, FeedBackContract.Presenter> implements FeedBackContract.View {

    @BindView(R.id.tv_malfunction)
    TopView tvMalfunction;
    @BindView(R.id.et_scanning)
    AppCompatEditText etScanning;
    @BindView(R.id.rg_malfunction_type)
    GridRadioGroup rgMalfunctionType;
    //    @BindView(R.id.rb_malfunction_1)
//    RadioButton rbMalfunction1;
//    @BindView(R.id.rb_malfunction_2)
//    RadioButton rbMalfunction2;
//    @BindView(R.id.rb_malfunction_3)
//    RadioButton rbMalfunction3;
//    @BindView(R.id.rb_malfunction_4)
//    RadioButton rbMalfunction4;
//    @BindView(R.id.rb_malfunction_5)
//    RadioButton rbMalfunction5;
//    @BindView(R.id.rb_malfunction_6)
//    RadioButton rbMalfunction6;
//    @BindView(R.id.rb_malfunction_7)
//    RadioButton rbMalfunction7;
//    @BindView(R.id.rb_malfunction_8)
//    RadioButton rbMalfunction8;
//    @BindView(R.id.rb_malfunction_9)
//    RadioButton rbMalfunction9;
//    @BindView(R.id.rb_malfunction_10)
//    RadioButton rbMalfunction10;
//    @BindView(R.id.rb_malfunction_11)
//    RadioButton rbMalfunction11;
    @BindView(R.id.iv_upload)
    RadiusImageView ivUpload;
    @BindView(R.id.et_content)
    MultiLineEditText etContent;

    private String malfunctionType = "";//故障类型
    private String uploadImg = "";//后台返的图片URL

    @Override
    public int getLayoutId() {
        return R.layout.activity_malfunction;
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
        ImmersionBar.setTitleBar(this, tvMalfunction);
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
                case REQUEST_CODE_99:
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
                    startActivityForResult(new Intent(MalfunctionActivity.this, QRActivity.class), REQUEST_CODE_99);
                } else {
                    // 权限被拒绝
                    ToastUtil.showLongToast(R.string.permission_rejected);
                }
            }
        });
    }

    @OnClick({R.id.iv_scanning, R.id.iv_upload, R.id.rv_malfunction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_scanning:
                rxPermissionCamera();
                break;
            case R.id.iv_upload:
                PictureSelector.create(MalfunctionActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)// 最大图片选择数量 int
                        .isCamera(true)
                        .compress(true)
                        .minimumCompressSize(100)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.rv_malfunction:
                if (isFastClick()) {
                    TreeMap<String, String> canUnlockMap = new TreeMap<>();
                    canUnlockMap.put("item_no", etScanning.getText().toString().trim());
                    switch (rgMalfunctionType.flag) {
                        case 1:
                            malfunctionType = "无法启动";
                            break;
                        case 2:
                            malfunctionType = "车头歪了";
                            break;
                        case 3:
                            malfunctionType = "刹车不灵";
                            break;
                        case 4:
                            malfunctionType = "坐垫坏了";
                            break;
                        case 5:
                            malfunctionType = "挡泥板坏了";
                            break;
                        case 6:
                            malfunctionType = "转把坏了";
                            break;
                        case 7:
                            malfunctionType = "脚撑坏了";
                            break;
                        case 8:
                            malfunctionType = "刹车把坏了";
                            break;
                        case 9:
                            malfunctionType = "电量突然骤降";
                            break;
                        case 10:
                            malfunctionType = "车牌损坏";
                            break;
                        case 11:
                            malfunctionType = "其他";
                            break;
                    }
                    canUnlockMap.put("type", malfunctionType);
                    canUnlockMap.put("url", uploadImg);
                    canUnlockMap.put("supplement", etContent.getContentText().trim());
                    canUnlockMap.put("static", "1");
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
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public void resultFeedBack(FeedBackBean data) {
        if (data.getCode() == 200)
            finish();
        else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
