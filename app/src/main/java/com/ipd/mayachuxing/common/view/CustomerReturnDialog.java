package com.ipd.mayachuxing.common.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
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
public abstract class CustomerReturnDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private String title;
    private AppCompatTextView tvTitle;
    private SuperButton btCancel, btConfirm;

    public CustomerReturnDialog(Activity activity, String title) {
        super(activity, R.style.MyDialogTheme);
        this.activity = activity;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_customer_return);

        tvTitle = (AppCompatTextView) findViewById(R.id.tv_title);
        btCancel = (SuperButton) findViewById(R.id.bt_cancel);
        btConfirm = (SuperButton) findViewById(R.id.bt_confirm);

        tvTitle.setText(title);

        btCancel.setOnClickListener(this);
        btConfirm.setOnClickListener(this);

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
                case R.id.bt_confirm:
                    confirm();
                    this.cancel();
                    break;
            }
        }
    }

    public abstract void confirm();
}
