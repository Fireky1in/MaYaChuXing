package com.ipd.mayachuxing.activity;

import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.bean.TripDetailsBean;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.contract.TripDetailsContract;
import com.ipd.mayachuxing.presenter.TripDetailsPresenter;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：行程详情
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/6.
 */
public class TripDetailsActivity extends BaseActivity<TripDetailsContract.View, TripDetailsContract.Presenter> implements TripDetailsContract.View {

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

    private int tripId;//行程ID

    @Override
    public int getLayoutId() {
        return R.layout.activity_trip_details;
    }

    @Override
    public TripDetailsContract.Presenter createPresenter() {
        return new TripDetailsPresenter(this);
    }

    @Override
    public TripDetailsContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvTripDetails);

        tripId = getIntent().getIntExtra("trip_id", 0);
    }

    @Override
    public void initData() {
        TreeMap<String, String> tripDetailsMap = new TreeMap<>();
        tripDetailsMap.put("id", tripId + "");
        getPresenter().getTripDetails(tripDetailsMap, false, false);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void resultTripDetails(TripDetailsBean data) {
        if (data.getCode() == 200) {
            tvStartLocation.setRightString(data.getData().getOpen_address());
            tvEndLocation.setRightString(data.getData().getFinish_address());
            tvStartTime.setRightString(data.getData().getCreate_at());
            tvEndTime.setRightString(data.getData().getFinish_at());
            tvUseTime.setRightString(data.getData().getTime());
            tvUseFee.setRightString(data.getData().getMoney() + "元");//TODO 总金额？ 还要减优惠券?
            tvPayType.setRightString(data.getData().getPay_status());
        } else
            ToastUtil.showLongToast(data.getMessage());
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }
}
