package com.ipd.mayachuxing.common.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.RadioButton;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;

import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.utils.L;

public class GridRadioGroup extends GridLayout {

    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private boolean mProtectFromCheckedChange = false;
    private PassThroughHierarchyChangeListener mPassThroughListener;
    private int mCheckedId = -1;
    public int flag = 0;


    public GridRadioGroup(Context context) {
        super(context);
    }

    public GridRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof RadioButton) {
            final RadioButton button = (RadioButton) child;
            if (button.isChecked()) {
                mProtectFromCheckedChange = true;
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false);
                }
                mProtectFromCheckedChange = false;
                setCheckedId(button.getId());
            }
        }

        super.addView(child, index, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mCheckedId, true);
            mProtectFromCheckedChange = false;
            setCheckedId(mCheckedId);
        }
    }

    private void setCheckedStateForView(int viewId, boolean checked) {
        View checkedView = findViewById(viewId);
        if (checkedView != null && checkedView instanceof RadioButton) {
            ((RadioButton) checkedView).setChecked(checked);
        }
    }

    private void setCheckedId(@IdRes int id) {
        mCheckedId = id;

    }

    private class PassThroughHierarchyChangeListener implements
            ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void onChildViewAdded(View parent, View child) {
            if (parent == GridRadioGroup.this && child instanceof RadioButton) {
                int id = child.getId();
                if (id == View.NO_ID) {
                    id = View.generateViewId();
                    child.setId(id);
                }
                ((RadioButton) child).setOnCheckedChangeListener(
                        mChildOnCheckedChangeListener);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        public void onChildViewRemoved(View parent, View child) {
            if (parent == GridRadioGroup.this && child instanceof RadioButton) {
                ((RadioButton) child).setOnCheckedChangeListener(null);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }

    private void init() {
        mChildOnCheckedChangeListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }

    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked)
                switch (buttonView.getId()) {
                    case R.id.rb_fee_1:
                        flag = 1;
                        break;
                    case R.id.rb_fee_2:
                        flag = 2;
                        break;
                    case R.id.rb_fee_3:
                        flag = 3;
                        break;
                    case R.id.rb_fee_4:
                        flag = 4;
                        break;
                    case R.id.rb_fee_5:
                        flag = 5;
                        break;
                    case R.id.rb_fee_6:
                        flag = 6;
                        break;
                }
            if (mProtectFromCheckedChange) {
                return;
            }

            mProtectFromCheckedChange = true;
            if (mCheckedId != -1) {
                setCheckedStateForView(mCheckedId, false);
            }
            mProtectFromCheckedChange = false;

            int id = buttonView.getId();
            setCheckedId(id);
        }
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }
}
