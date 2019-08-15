package com.ipd.mayachuxing.common.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.activity.ApplyParkingSpotActivity;
import com.ipd.mayachuxing.activity.CantUnlockActivity;
import com.ipd.mayachuxing.activity.MalfunctionActivity;
import com.ipd.mayachuxing.activity.ReportActivity;

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：底部弹出Dialog
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/6/24.
 */
public abstract class CustomerServiceDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private LinearLayout llCantUnlock, llApplyParkingSpot, llMalfunction, llReport, llAdvisoryService;

    public CustomerServiceDialog(Activity activity) {
        super(activity, R.style.MyDialogTheme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_customer_service);

        llCantUnlock = (LinearLayout) findViewById(R.id.ll_can_t_unlock);
        llApplyParkingSpot = (LinearLayout) findViewById(R.id.ll_apply_parking_spot);
        llMalfunction = (LinearLayout) findViewById(R.id.ll_malfunction);
        llReport = (LinearLayout) findViewById(R.id.ll_report);
        llAdvisoryService = (LinearLayout) findViewById(R.id.ll_advisory_service);

        llCantUnlock.setOnClickListener(this);
        llApplyParkingSpot.setOnClickListener(this);
        llMalfunction.setOnClickListener(this);
        llReport.setOnClickListener(this);
        llAdvisoryService.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕底部
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = height;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }

    @Override
    public void onClick(View v) {
        if (isFastClick()) {
            switch (v.getId()) {
                case R.id.ll_can_t_unlock:
                    activity.startActivity(new Intent(activity, CantUnlockActivity.class));
                    this.cancel();
                    break;
                case R.id.ll_apply_parking_spot:
                    activity.startActivity(new Intent(activity, ApplyParkingSpotActivity.class));
                    this.cancel();
                    break;
                case R.id.ll_malfunction:
                    activity.startActivity(new Intent(activity, MalfunctionActivity.class));
                    this.cancel();
                    break;
                case R.id.ll_report:
                    activity.startActivity(new Intent(activity, ReportActivity.class));
                    this.cancel();
                    break;
                case R.id.ll_advisory_service:
                    new CallPhoneDialog(activity) {
                    }.show();
                    this.cancel();
                    break;
            }
        }
    }
}
