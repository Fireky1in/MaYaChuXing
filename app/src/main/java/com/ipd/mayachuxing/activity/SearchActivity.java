package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.InputTipsAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.SearchView;
import com.ipd.mayachuxing.common.view.bCallBack;
import com.ipd.mayachuxing.common.view.bCallSearch;
import com.ipd.mayachuxing.common.view.bHistoryRecordItem;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import scut.carson_ho.searchview.ICallBack;

/**
 * Description ：搜索
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/7.
 */
public class SearchActivity extends BaseActivity implements Inputtips.InputtipsListener {

    @BindView(R.id.sv_search)
    SearchView svSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;

    public List<Tip> mCurrentTipList = new ArrayList<>();
    private InputTipsAdapter mIntipAdapter;
    private double lat;
    private double lng;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, svSearch);

        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setItemAnimator(new DefaultItemAnimator());//加载动画
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        // 设置点击键盘上的搜索按键后的操作（通过回调接口）
        svSearch.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                if (mCurrentTipList.size() > 0) {
                    lat = mCurrentTipList.get(0).getPoint().getLatitude();
                    lng = mCurrentTipList.get(0).getPoint().getLongitude();
                    setResult(RESULT_OK, new Intent().putExtra("lat", lat).putExtra("lng", lng));
                    finish();
                }
            }
        });

        // 设置点击返回按键后的操作（通过回调接口）
        svSearch.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

        // 搜索实时监听，用于接口回调
        svSearch.setOnClickSearch(new bCallSearch() {
            @Override
            public void SearchAciton(String newText) {
                if (!IsEmptyOrNullString(newText)) {
                    InputtipsQuery inputquery = new InputtipsQuery(newText, "");
                    Inputtips inputTips = new Inputtips(SearchActivity.this.getApplicationContext(), inputquery);
                    inputTips.setInputtipsListener(SearchActivity.this);
                    inputTips.requestInputtipsAsyn();
                } else {
                    if (mIntipAdapter != null && mCurrentTipList != null) {
                        mCurrentTipList.clear();
                        mIntipAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        // 历史记录item点击监听（通过回调接口）
        svSearch.setOnHistoryRecordItemClick(new bHistoryRecordItem() {
            @Override
            public void HistoryRecordAciton(String lng, String lat) {
                setResult(RESULT_OK, new Intent().putExtra("lat", Double.parseDouble(lat)).putExtra("lng", Double.parseDouble(lng)));
                finish();
            }
        });
    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) {// 正确返回
            mCurrentTipList.clear();
            mCurrentTipList = tipList;
            mIntipAdapter = new InputTipsAdapter(mCurrentTipList);
            rvSearch.setAdapter(mIntipAdapter);
            mIntipAdapter.notifyDataSetChanged();

            mIntipAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    boolean hasData = svSearch.hasData(mCurrentTipList.get(position).getName());
                    if (!hasData) {
                        svSearch.insertData(mCurrentTipList.get(position).getName(), mCurrentTipList.get(position).getAddress(), mCurrentTipList.get(position).getPoint().getLongitude(), mCurrentTipList.get(position).getPoint().getLatitude());
                        svSearch.queryData("");
                    }

                    if (mCurrentTipList.get(position).getPoint() != null) {
                        lat = mCurrentTipList.get(position).getPoint().getLatitude();
                        lng = mCurrentTipList.get(position).getPoint().getLongitude();
                        setResult(RESULT_OK, new Intent().putExtra("lat", lat).putExtra("lng", lng));
                        finish();
                    }
                }
            });
        } else {
            ToastUtil.showShortToast(rCode);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static boolean IsEmptyOrNullString(String s) {
        return (s == null) || (s.trim().length() == 0);
    }
}
