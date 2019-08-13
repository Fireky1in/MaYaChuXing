package com.ipd.mayachuxing.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.MsgListBean;
import com.xuexiang.xui.widget.textview.ExpandableTextView;

import java.util.List;

import static android.text.TextUtils.TruncateAt.END;

public class MsgAdapter extends BaseQuickAdapter<MsgListBean.DataBean, BaseViewHolder> {

    private ExpandableTextView tvContent;

    public MsgAdapter(@Nullable List<MsgListBean.DataBean> data) {
        super(R.layout.adapter_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgListBean.DataBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        tvContent = helper.getView(R.id.tv_content);
        tvContent.setText(item.getNote());
        tvContent.setOnExpandStateChangeListener(new ExpandableTextView.OnExpandStateChangeListener() {
            @Override
            public void onExpandStateChanged(TextView textView, boolean isExpanded) {
                if (!isExpanded) {
                    textView.setLines(1);
                    textView.setEllipsize(END);
                }
            }
        });
    }
}
