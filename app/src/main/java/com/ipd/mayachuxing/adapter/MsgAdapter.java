package com.ipd.mayachuxing.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.bean.TestBean;
import com.xuexiang.xui.widget.textview.ExpandableTextView;

import java.util.List;

import static android.text.TextUtils.TruncateAt.END;

public class MsgAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

    private ExpandableTextView tvContent;

    public MsgAdapter(@Nullable List<TestBean> data) {
        super(R.layout.adapter_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        helper.setText(R.id.tv_title, "系统消息");
        tvContent = helper.getView(R.id.tv_content);
        tvContent.setText("亚马出行全面升级，多地覆盖亚马出行全面升级，多地覆盖亚马出行亚马出行全面升级，多地覆盖全面升级，多地覆盖");
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
