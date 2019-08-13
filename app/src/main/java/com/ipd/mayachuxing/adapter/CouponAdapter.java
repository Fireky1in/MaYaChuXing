package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.CouponListBean;

import java.util.List;

public class CouponAdapter extends BaseQuickAdapter<CouponListBean.DataBean, BaseViewHolder> {

    public CouponAdapter(@Nullable List<CouponListBean.DataBean> data) {
        super(R.layout.adapter_coupon, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListBean.DataBean item) {
        int useType = 0;
        switch (item.getStaticX()) {//0未使用 1已使用 2已过期
            case 0:
                useType = R.mipmap.ic_unused;
                break;
            case 1:
                useType = R.mipmap.ic_used;
                break;
            case 2:
                useType = R.mipmap.ic_expired;
                break;
        }
        helper.setText(R.id.tv_coupon_use_money, item.getNum() + "")
                .setText(R.id.tv_coupon_condition_money, item.getConditions())
                .setImageResource(R.id.iv_coupon_type, useType)
                .setText(R.id.tv_coupon_end_time, item.getEnd_time());
    }
}
