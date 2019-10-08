package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.ReturnDepositListBean;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class ReturnDepositAdapter extends BaseQuickAdapter<ReturnDepositListBean, BaseViewHolder> {

    SuperTextView tvReturnDepositItem;

    public ReturnDepositAdapter(@Nullable List<ReturnDepositListBean> data) {
        super(R.layout.adapter_return_deposit, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReturnDepositListBean item) {
        tvReturnDepositItem = helper.getView(R.id.tv_return_deposit_item);
        tvReturnDepositItem.setLeftString(item.getName());
        if (item.isShow())
            tvReturnDepositItem.setRightIcon(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_check_yellow));
        else
            tvReturnDepositItem.setRightIcon(ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_check_gray));

        helper.addOnClickListener(R.id.tv_return_deposit_item);
    }
}
