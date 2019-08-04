package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.TestBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class AdoptAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

    private SuperTextView tvAdoptTop;

    public AdoptAdapter(@Nullable List<TestBean> data) {
        super(R.layout.adapter_adopt, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        tvAdoptTop = helper.getView(R.id.tv_adopt_top);
        tvAdoptTop.setCenterString("领养马亚")
                .setRightString("赚收益");
        helper.setText(R.id.tv_adopt_center, "领养金360天可退")
                .setText(R.id.tv_adopt_item_type, "已抢完")
                .setText(R.id.tv_adopt_activity_type, "已结束");
    }
}
