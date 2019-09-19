package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.SidebarBean;

import java.util.List;

public class SidebarAdapter extends BaseQuickAdapter<SidebarBean, BaseViewHolder> {

    public SidebarAdapter(@Nullable List<SidebarBean> data) {
        super(R.layout.adapter_sidebar, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SidebarBean item) {
        if (item.isShow()) {
            helper.setBackgroundRes(R.id.ll_sidebar_horizontal, R.mipmap.bg_sidebar_horizontal)
                    .setVisible(R.id.iv_sidebar_vertical, true)
                    .setImageResource(R.id.iv_sidebar_icon, item.getIconSelect())
                    .setText(R.id.tv_sidebar_name, item.getName());
        } else {
            helper.setBackgroundRes(R.id.ll_sidebar_horizontal, 0)
                    .setVisible(R.id.iv_sidebar_vertical, false)
                    .setImageResource(R.id.iv_sidebar_icon, item.getIconUnselect())
                    .setText(R.id.tv_sidebar_name, item.getName());
        }
        if (helper.getAdapterPosition() == 0 || helper.getAdapterPosition() == 1)
            helper.setText(R.id.tv_sidebar_money, item.getMoney() + "元");
    }
}
