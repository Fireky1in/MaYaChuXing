package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.TestBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class CouponAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

    public CouponAdapter(@Nullable List<TestBean> data) {
        super(R.layout.adapter_coupon, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        helper.setText(R.id.tv_coupon_use_money, "50")
                .setText(R.id.tv_coupon_condition_money, "满200元可用")
                .setImageResource(R.id.iv_coupon_type, R.mipmap.ic_unused)
                .setText(R.id.tv_coupon_end_time, "2017-04-20");
    }
}
