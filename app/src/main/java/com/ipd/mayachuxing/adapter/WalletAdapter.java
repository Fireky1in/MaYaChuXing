package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.UserBalanceBean;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class WalletAdapter extends BaseQuickAdapter<UserBalanceBean.DataBean.ListBean, BaseViewHolder> {

    private SuperTextView tvWalletDetailedItem;

    public WalletAdapter(@Nullable List<UserBalanceBean.DataBean.ListBean> data) {
        super(R.layout.adapter_wallet, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBalanceBean.DataBean.ListBean item) {
        tvWalletDetailedItem = helper.getView(R.id.tv_wallet_detailed_item);
        tvWalletDetailedItem.setLeftTopString(item.getReason())
                .setLeftBottomString(item.getTime())
                .setRightString(item.getStaticX() + " " + item.getNum() + "元");
    }
}
