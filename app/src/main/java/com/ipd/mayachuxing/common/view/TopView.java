package com.ipd.mayachuxing.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.activity.LoginActivity;
import com.ipd.mayachuxing.activity.MyAdoptActivity;
import com.ipd.mayachuxing.adapter.ShareActivity;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.SPUtil;

import static com.ipd.mayachuxing.common.config.IConstants.IS_LOGIN;
import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;
import static com.ipd.mayachuxing.utils.isClickUtil.isFastClick;

/**
 * Description : 公用标题栏
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/11/loading1
 */

public class TopView extends RelativeLayout implements View.OnClickListener {

    private TextView tvTopTitle;
    private LinearLayout llTopBack, llTopMy;
    private Button btTopCancel, btTopAdoption, btTopBank;
    private ImageButton ibTopShare;

    //各icon是否显示
    private Boolean isBack, isMy, isCancel, isAdoption, isBank, isShare;
    private Context mContext;

    public TopView(Context context) {
        super(context);
        initValues(context);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValues(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopView);
        tvTopTitle.setText(typedArray.getString(R.styleable.TopView_title));
        tvTopTitle.setTextColor(typedArray.getColor(R.styleable.TopView_title_color, getResources().getColor(R.color.black)));
        isBack = typedArray.getBoolean(R.styleable.TopView_is_back, false);
        isMy = typedArray.getBoolean(R.styleable.TopView_is_my, false);
        isCancel = typedArray.getBoolean(R.styleable.TopView_is_cancel, false);
        isAdoption = typedArray.getBoolean(R.styleable.TopView_is_adoption, false);
        isBank = typedArray.getBoolean(R.styleable.TopView_is_bank, false);
        isShare = typedArray.getBoolean(R.styleable.TopView_is_share, false);
        typedArray.recycle();

        llTopBack.setVisibility(isBack ? View.VISIBLE : View.GONE);
        llTopMy.setVisibility(isMy ? View.VISIBLE : View.GONE);
        btTopCancel.setVisibility(isCancel ? View.VISIBLE : View.GONE);
        btTopAdoption.setVisibility(isAdoption ? View.VISIBLE : View.GONE);
        btTopBank.setVisibility(isBank ? View.VISIBLE : View.GONE);
        ibTopShare.setVisibility(isShare ? View.VISIBLE : View.GONE);
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValues(context);
    }

    private void initValues(final Context context) {
        mContext = context;
        View.inflate(context, R.layout.top_view, this);
        tvTopTitle = (TextView) this.findViewById(R.id.tv_top_title);
        llTopBack = (LinearLayout) this.findViewById(R.id.ll_top_back);
        llTopMy = (LinearLayout) this.findViewById(R.id.ll_top_my);
        btTopCancel = (Button) this.findViewById(R.id.bt_top_cancel);
        btTopAdoption = (Button) this.findViewById(R.id.bt_top_adoption);
        btTopBank = (Button) this.findViewById(R.id.bt_top_bank);
        ibTopShare = (ImageButton) this.findViewById(R.id.ib_top_share);

        llTopBack.setOnClickListener(this);
        llTopMy.setOnClickListener(this);
        btTopCancel.setOnClickListener(this);
        btTopAdoption.setOnClickListener(this);
        btTopBank.setOnClickListener(this);
        ibTopShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_top_back:
                if (mContext instanceof Activity && isFastClick()) {
                    ((Activity) mContext).finish();
                    if (((Activity) mContext).getCurrentFocus() != null) {
                        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                break;
            case R.id.bt_top_cancel:
                break;
            case R.id.bt_top_adoption:
                ApplicationUtil.getContext().startActivity(new Intent(ApplicationUtil.getContext(), MyAdoptActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.bt_top_bank:
                break;
            case R.id.ib_top_share:
                if (isFastClick()) {
                    if (!isEmpty(SPUtil.get(ApplicationUtil.getContext(), IS_LOGIN, "") + "")) {
                        ApplicationUtil.getContext().startActivity(new Intent(ApplicationUtil.getContext(), ShareActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } else
                        ApplicationUtil.getContext().startActivity(new Intent(ApplicationUtil.getContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }
                break;
            default:
                break;
        }
    }
}
