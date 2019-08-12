package com.ipd.mayachuxing.adapter;

import androidx.annotation.Nullable;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.utils.L;

import java.util.List;

public class PoiAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {

    public PoiAdapter(@Nullable List<PoiItem> data) {
        super(R.layout.adapter_poi, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        L.i("a = " + item.getAdCode() + ", b = " + item.getAdName() + ", item = " + item.getBusinessArea() + ", c = " + item.getDirection() + ", d=" + item.getPostcode() + ", e = " + item.getProvinceName() + ", f = " + item.getSnippet() + ", g = " + item.getTitle() + ", h = " + item.getTypeDes() + ", i = " + item.getWebsite() + ", j = " + item.getDistance() + ", k=" + item.getEnter() + ", l=" + item.getExit() + ", m = " + item.getPoiExtension().getmRating() + ", n = " + item.getPoiExtension().getOpentime());
        helper.setText(R.id.tv_title, "")
                .setText(R.id.tv_content, "");
    }
}
