package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.List;

public class GuideHotAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private SuperTextView tvIssuesName;

    public GuideHotAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_guide, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        tvIssuesName = helper.getView(R.id.tv_issues_name);
        tvIssuesName.setLeftString(item);
        helper.addOnClickListener(R.id.tv_issues_name);
    }
}
