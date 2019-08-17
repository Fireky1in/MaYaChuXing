package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.TestBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class MyAdoptAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

    private SuperTextView tvMyAdoptTop;

    public MyAdoptAdapter(@Nullable List<TestBean> data) {
        super(R.layout.adapter_my_adopt, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        tvMyAdoptTop = helper.getView(R.id.tv_my_adopt_top);
        tvMyAdoptTop.setCenterString("领养小马")
                .setRightString("赚收益");
        helper.setText(R.id.tv_my_adopt_center, "领养金360天可退")
                .setText(R.id.tv_my_adopt_item_type, "已领养");
    }
}
