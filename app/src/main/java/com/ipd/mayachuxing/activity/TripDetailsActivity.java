package com.ipd.mayachuxing.activity;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import butterknife.BindView;

/**
 * Description ：行程详情
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class TripDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_trip_details)
    TopView tvTripDetails;
    @BindView(R.id.tv_start_location)
    SuperTextView tvStartLocation;
    @BindView(R.id.tv_end_location)
    SuperTextView tvEndLocation;
    @BindView(R.id.tv_start_time)
    SuperTextView tvStartTime;
    @BindView(R.id.tv_end_time)
    SuperTextView tvEndTime;
    @BindView(R.id.tv_use_time)
    SuperTextView tvUseTime;
    @BindView(R.id.tv_use_fee)
    SuperTextView tvUseFee;
    @BindView(R.id.tv_pay_type)
    SuperTextView tvPayType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trip_details;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvTripDetails);
    }

    @Override
    public void initData() {
        tvStartLocation.setRightString("上海市第一人民医院");
        tvEndLocation.setRightString("上海市虹口区海宁路100号");
        tvStartTime.setRightString("2019-06-18 09:23:45");
        tvEndTime.setRightString("2019-06-18 09:33:46");
        tvUseTime.setRightString("10分01秒");
        tvUseFee.setRightString("1元");
        tvPayType.setRightString("已支付");
    }

    @Override
    public void initListener() {

    }
}
