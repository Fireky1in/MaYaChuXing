package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.TestBean;

import java.util.List;

public class SelectBackAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

    public SelectBackAdapter(@Nullable List<TestBean> data) {
        super(R.layout.adapter_select_bank, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        helper.setText(R.id.tv_bank_name, "中国银行")
                .setText(R.id.tv_bank_type, "储蓄卡")
                .setText(R.id.tv_bank_code, "6222 **** **** **** 888");
    }
}
