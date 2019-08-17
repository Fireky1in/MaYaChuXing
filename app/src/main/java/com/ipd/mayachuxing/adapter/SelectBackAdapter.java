package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.BankListBean;

import java.util.List;

public class SelectBackAdapter extends BaseQuickAdapter<BankListBean.DataBean.ListBean, BaseViewHolder> {

    public SelectBackAdapter(@Nullable List<BankListBean.DataBean.ListBean> data) {
        super(R.layout.adapter_select_bank, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BankListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_bank_name, item.getBank())
                .setText(R.id.tv_bank_type, item.getStaticX())
                .setText(R.id.tv_bank_code, item.getCard())
                .addOnClickListener(R.id.tv_bank_del);
    }
}
