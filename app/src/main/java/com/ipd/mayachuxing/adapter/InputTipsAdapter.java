package com.ipd.mayachuxing.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;

/**
 * 输入提示adapter，展示item名称和地址
 * Created by ligen on 16/11/25.
 */
public class InputTipsAdapter extends BaseQuickAdapter<Tip, BaseViewHolder> {

    private TextView tvAddress;

    public InputTipsAdapter(@Nullable List<Tip> data) {
        super(R.layout.adapter_inputtips, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Tip item) {
        helper.setText(R.id.name, item.getName());
        tvAddress = helper.getView(R.id.adress);
        String address = item.getAddress();
        if (isEmpty(address) || item.getPoint() == null || item.getPoint().getLongitude() <= 0 || item.getPoint().getLatitude() <= 0)
            tvAddress.setVisibility(GONE);
        else {
            tvAddress.setVisibility(VISIBLE);
            tvAddress.setText(address);
        }

//        L.i("title = " + item.getTitle());
//        helper.setText(R.id.name, item.getTitle());
//        tvAddress = helper.getView(R.id.adress);
//        String address = item.getSnippet();
//        if (isEmpty(address))
//            tvAddress.setVisibility(GONE);
//        else {
//            tvAddress.setVisibility(VISIBLE);
//            tvAddress.setText(address);
//        }
    }
}
