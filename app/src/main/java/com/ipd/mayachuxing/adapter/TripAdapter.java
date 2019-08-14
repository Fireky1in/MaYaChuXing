package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.TripListBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class TripAdapter extends BaseQuickAdapter<TripListBean.DataBean.ListBean, BaseViewHolder> {

    private SuperTextView tvTripItem;

    public TripAdapter(@Nullable List<TripListBean.DataBean.ListBean> data) {
        super(R.layout.adapter_trip, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TripListBean.DataBean.ListBean item) {
        tvTripItem = helper.getView(R.id.tv_trip_item);
        tvTripItem.setLeftTopString(item.getTime())
                .setLeftBottomString(item.getCreate_at())
                .setRightString(item.getMoney() + "元");
        helper.addOnClickListener(R.id.tv_trip_item);
    }
}
