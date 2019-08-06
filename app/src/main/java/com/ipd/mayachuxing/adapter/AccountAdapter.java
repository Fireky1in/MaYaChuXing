package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.TestBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class AccountAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

    private SuperTextView tvWalletDetailedItem;

    public AccountAdapter(@Nullable List<TestBean> data) {
        super(R.layout.adapter_wallet, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        tvWalletDetailedItem = helper.getView(R.id.tv_wallet_detailed_item);
        tvWalletDetailedItem.setLeftTopString("提现")
                .setLeftBottomString("2019-05-14 13:43")
                .setRightString("- 200元");
    }
}
