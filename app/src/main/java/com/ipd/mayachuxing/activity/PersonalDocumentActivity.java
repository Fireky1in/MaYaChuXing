package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
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
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.io.File;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_92;
import static com.ipd.mayachuxing.common.config.UrlConfig.BASE_LOCAL_URL;

/**
 * Description ：个人资料
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class PersonalDocumentActivity extends BaseActivity<PersonalDocumentContract.View, PersonalDocumentContract.Presenter> implements PersonalDocumentContract.View {

    @BindView(R.id.tv_personal_document)
    TopView tvPersonalDocument;
    @BindView(R.id.riv_head)
    RadiusImageView rivHead;
    @BindView(R.id.tv_nickname)
    SuperTextView tvNickname;
    @BindView(R.id.tv_name)
    SuperTextView tvName;
    @BindView(R.id.tv_iuthentication)
    SuperTextView tvIuthentication;
    @BindView(R.id.tv_phone)
    SuperTextView tvPhone;

    private boolean isModifyHead = false;
    private String picturePath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_document;
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
        ImmersionBar.setTitleBar(this, tvPersonalDocument);
    }

    @Override
    public void initData() {
        getPresenter().getUserInfo(false, false);
    }

    @Override
    public void initListener() {

    }

    public static RequestBody getImageRequestBody(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        return RequestBody.create(MediaType.parse(options.outMimeType), new File(filePath));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    picturePath = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
                    TreeMap<String, RequestBody> map = new TreeMap<>();
                    map.put("file\";filename=\"" + ".jpeg", getImageRequestBody(picturePath));
                    getPresenter().getUploadHead(map, false, false);
                    break;
                case REQUEST_CODE_92:
                    tvNickname.setRightString(data.getStringExtra("modify_nickname"));
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isModifyHead)
            setResult(RESULT_OK, new Intent().putExtra("modify_head", picturePath));
        finish();
    }

    @OnClick({R.id.ll_head, R.id.tv_nickname, R.id.ll_top_back, R.id.tv_iuthentication})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_head:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(1)// 最大图片选择数量 int
                        .isCamera(true)
                        .compress(true)
                        .minimumCompressSize(100)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tv_nickname:
                startActivityForResult(new Intent(this, NicknameActivity.class).putExtra("nickname", tvNickname.getRightString()), REQUEST_CODE_92);
                break;
            case R.id.ll_top_back:
                if (isModifyHead)
                    setResult(RESULT_OK, new Intent().putExtra("modify_head", picturePath));
                finish();
                break;
            case R.id.tv_iuthentication:
                if ("未认证".equals(tvIuthentication.getRightString())) {
                    startActivity(new Intent(this, IuthenticationActivity.class));
                    finish();
                }
                break;
        }
    }

    @Override
    public void resultUploadHead(UploadHeadBean data) {
        if (data.getCode() == 200) {
            isModifyHead = true;
            Glide.with(this)
                    .load(picturePath)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            rivHead.setImageDrawable(resource);
                        }
                    });
        } else {
            isModifyHead = false;
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
    public void resultModifyName(ModifyNameBean data) {

    }

    @Override
    public void resultUserInfo(UserInfoBean data) {
        if (data.getCode() == 200) {
            Glide.with(ApplicationUtil.getContext()).load(BASE_LOCAL_URL + data.getData().getHeaderUrl()).apply(new RequestOptions().placeholder(R.mipmap.ic_default_head)).into(rivHead);
            tvNickname.setRightString(data.getData().getNickname());
            tvName.setRightString(data.getData().getName());
            tvIuthentication.setRightString(data.getData().getIs_on());
            tvPhone.setRightString(data.getData().getPhone());
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
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
