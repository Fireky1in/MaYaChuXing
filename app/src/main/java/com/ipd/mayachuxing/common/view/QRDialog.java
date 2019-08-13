package com.ipd.mayachuxing.common.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatTextView;

import com.ipd.mayachuxing.R;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：自定义Dialog
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/6/24.
 */
public abstract class QRDialog extends Dialog implements View.OnClickListener {
    private AppCompatTextView tvCarNum, tvRemainingDistance;
    private SuperButton btCancel, btUnlock;
    private Activity activity;
    private String carNum;

    public QRDialog(Activity activity, String carNum) {
        super(activity, R.style.MyDialogTheme);
        this.activity = activity;
        this.carNum = carNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_qr);

        tvCarNum = (AppCompatTextView) findViewById(R.id.tv_car_num);
        tvRemainingDistance = (AppCompatTextView) findViewById(R.id.tv_remaining_distance);
        btCancel = (SuperButton) findViewById(R.id.bt_cancel);
        btUnlock = (SuperButton) findViewById(R.id.bt_unlock);

        tvCarNum.setText(Html.fromHtml("车辆编号 <font color=\"#F5C636\">" + carNum + "</font>"));
        tvRemainingDistance.setText(Html.fromHtml("剩余续航里程 <font color=\"#FF5555\">" + 12 + "</font> km"));

        btCancel.setOnClickListener(this);
        btUnlock.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(false);//外部点击取消
    }

    /**
     * 设置dialog位于屏幕中部
     */
    private void setViewLocation() {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        // 设置显示位置
        onWindowAttributesChanged(lp);
    }


    @Override
    public void onClick(View v) {
        if (isFastClick()) {
            switch (v.getId()) {
                case R.id.bt_cancel:
                    this.cancel();
                    break;
                case R.id.bt_unlock:
                    unlock();
                    this.cancel();
                    break;
            }
        }
    }

    public abstract void unlock();
}
