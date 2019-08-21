package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.ShareBean;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.xuexiang.xui.widget.imageview.RadiusImageView;

import java.util.List;

import static com.ipd.mayachuxing.common.config.UrlConfig.BASE_LOCAL_URL;

public class ShareAdapter extends BaseQuickAdapter<ShareBean.DataBean.UsersBean, BaseViewHolder> {

    public ShareAdapter(@Nullable List<ShareBean.DataBean.UsersBean> data) {
        super(R.layout.adapter_share, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareBean.DataBean.UsersBean item) {
        Glide.with(ApplicationUtil.getContext()).load(BASE_LOCAL_URL + item.getAvatar()).apply(new RequestOptions().placeholder(R.mipmap.ic_default_head)).into((RadiusImageView) helper.getView(R.id.riv_head));
        helper.setText(R.id.tv_phone, item.getPhone())
                .setText(R.id.tv_date, item.getDate());
    }
}
