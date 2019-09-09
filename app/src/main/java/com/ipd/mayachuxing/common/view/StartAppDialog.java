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
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.ipd.mayachuxing.R;

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：自定义Dialog
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/6/24.
 */
public abstract class StartAppDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private TextView tvOne, tvTwo;
    private AppCompatImageView ivClose;

    public StartAppDialog(Activity activity) {
        super(activity, R.style.MyDialogTheme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_start_app);

        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        ivClose = (AppCompatImageView) findViewById(R.id.iv_close);

        tvOne.setText(Html.fromHtml("依法处 <font color=\"#F5C636\">" + "10元" + "</font>以上罚款！"));
        tvTwo.setText(Html.fromHtml("<font color=\"#F5C636\">" + 1 + " </font>车限坐 <font color=\"#F5C636\">" + 1 + " </font> 人"));

        ivClose.setOnClickListener(this);

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
            this.cancel();
        }
    }
}
