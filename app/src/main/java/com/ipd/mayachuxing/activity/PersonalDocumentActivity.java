package com.ipd.mayachuxing.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.Nullable;

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
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.xuexiang.xui.widget.imageview.RadiusImageView;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ipd.mayachuxing.common.config.IConstants.REQUEST_CODE_92;

/**
 * Description ：个人资料
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/5.
 */
public class PersonalDocumentActivity extends BaseActivity {

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

    private String headImgUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_document;
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
        ImmersionBar.setTitleBar(this, tvPersonalDocument);
    }

    @Override
    public void initData() {
        rivHead.setImageResource(R.mipmap.ic_default_head);
        tvNickname.setRightString("一身诗意千寻瀑");
        tvName.setRightString("金岳霖");
        tvIuthentication.setRightString("已认证");
        tvPhone.setRightString("18321836625");
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
                                    headImgUrl = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
                                    rivHead.setImageDrawable(resource);
                                }
                            });
                    break;
                case REQUEST_CODE_92:
                    tvNickname.setRightString(data.getStringExtra("modify_nickname"));
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra("modify_head", headImgUrl));
        finish();
    }

    @OnClick({R.id.ll_head, R.id.tv_nickname, R.id.ll_top_back})
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
                setResult(RESULT_OK, new Intent().putExtra("modify_head", headImgUrl));
                finish();
                break;
        }
    }
}
