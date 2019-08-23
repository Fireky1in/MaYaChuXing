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

import androidx.appcompat.widget.AppCompatEditText;

import com.ipd.mayachuxing.R;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;

import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description ：自定义Dialog
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2019/6/24.
 */
public abstract class InputDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private AppCompatEditText etCarNum;
    private SuperButton btCancel, btConfirm;

    public InputDialog(Activity activity) {
        super(activity, R.style.MyDialogTheme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);

        etCarNum = (AppCompatEditText) findViewById(R.id.et_car_num);
        btCancel = (SuperButton) findViewById(R.id.bt_cancel);
        btConfirm = (SuperButton) findViewById(R.id.bt_confirm);

        btCancel.setOnClickListener(this);
        btConfirm.setOnClickListener(this);

        setViewLocation();
        setCanceledOnTouchOutside(true);//外部点击取消
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
                    cancelUseCar();
                    this.cancel();
                    break;
                case R.id.bt_confirm:
                    confirm(etCarNum.getText().toString().trim());
                    this.cancel();
                    break;
            }
        }
    }

    public abstract void cancelUseCar();

    public abstract void confirm(String str);
}
