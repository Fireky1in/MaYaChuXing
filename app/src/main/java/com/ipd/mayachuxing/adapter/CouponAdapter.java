package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.CouponListBean;

import java.util.List;

public class CouponAdapter extends BaseQuickAdapter<CouponListBean.DataBean.ListBean, BaseViewHolder> {

    private int type;

    public CouponAdapter(@Nullable List<CouponListBean.DataBean.ListBean> data, int type) {//1. 侧边栏总的券数据, 2. 可用的券数据
        super(R.layout.adapter_coupon, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListBean.DataBean.ListBean item) {
        int useType = 3;
        if (type == 1) {
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
        }

        helper.setText(R.id.tv_coupon_use_money, item.getNum() + "")
                .setText(R.id.tv_coupon_condition_money, "满"+item.getConditions() + "元可用")
                .setImageResource(R.id.iv_coupon_type, type == 1 ? useType : 0)
                .setText(R.id.tv_coupon_end_time, item.getEnd_time());
    }
}
