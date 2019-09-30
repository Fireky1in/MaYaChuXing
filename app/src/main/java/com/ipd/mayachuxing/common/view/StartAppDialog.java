package com.ipd.mayachuxing.common.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ipd.mayachuxing.R;

import java.util.ArrayList;

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
    private ViewPager vp;
    private ArrayList<View> pageview;

    public StartAppDialog(Activity activity) {
        super(activity, R.style.MyDialogTheme);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_viewpage);

        vp = (ViewPager) findViewById(R.id.vp);
//        tvOne = (TextView) findViewById(R.id.tv_one);
//        tvTwo = (TextView) findViewById(R.id.tv_two);
        ivClose = (AppCompatImageView) findViewById(R.id.iv_close);


        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.dialog_start_app, null);
        View view2 = inflater.inflate(R.layout.dialog_start_app_2, null);

        //将view装入数组
        pageview = new ArrayList<View>();
        pageview.add(view1);
        pageview.add(view2);

        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            //断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            //是从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }
        };
        //绑定适配器
        vp.setAdapter(mPagerAdapter);

//        tvOne.setText(Html.fromHtml("依法处 <font color=\"#F5C636\">" + "10元" + "</font>以上罚款！"));
//        tvTwo.setText(Html.fromHtml("<font color=\"#F5C636\">" + 1 + " </font>车限坐 <font color=\"#F5C636\">" + 1 + " </font> 人"));

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
