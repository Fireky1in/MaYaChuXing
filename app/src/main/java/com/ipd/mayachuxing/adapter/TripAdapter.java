package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.TestBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class TripAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

    private SuperTextView tvTripItem;

    public TripAdapter(@Nullable List<TestBean> data) {
        super(R.layout.adapter_trip, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        tvTripItem = helper.getView(R.id.tv_trip_item);
        tvTripItem.setLeftTopString("8分54秒")
                .setLeftBottomString("2019-05-14 13:43")
                .setRightString("1元");
    }
}
