package com.ipd.mayachuxing.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cursoradapter.widget.CursorAdapter;

import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.activity.SearchActivity;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchListView;

import static com.ipd.mayachuxing.utils.StringUtils.isEmpty;

public class SearchView extends LinearLayout {
    /**
     * 初始化成员变量
     */
    private Context context;

    // 搜索框组件
    private EditText et_search; // 搜索按键
    private TextView tv_clear;  // 删除搜索记录按键
    private LinearLayout search_block, llHistorySearch; // 搜索框布局
    private AppCompatButton searchBack; // 返回按键

    // ListView列表 & 适配器
    private SearchListView listView;
    private BaseAdapter adapter;

    // 数据库变量
    // 用于存放历史搜索记录
    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;

    // 回调接口
    private ICallBack mCallBack;// 搜索按键回调接口
    private bCallBack bCallBack; // 返回按键回调接口
    private bCallSearch bCallSearch; // 搜索实时监听回调接口
    private bHistoryRecordItem bHistoryRecordItem; // 历史记录item点击监听

    // 自定义属性设置
    // 1. 搜索字体属性设置：大小、颜色 & 默认提示
    private Float textSizeSearch;
    private int textColorSearch;
    private String textHintSearch;

    // 2. 搜索框设置：高度 & 颜色
    private int searchBlockHeight;
    private int searchBlockColor;

    /**
     * 构造函数
     * 作用：对搜索框进行初始化
     */
    public SearchView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs); // ->>关注a
        init();// ->>关注b
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        init();
    }

    /**
     * 关注a
     * 作用：初始化自定义属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {

        // 控件资源名称
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Search_View);

        // 搜索框字体大小（dp）
        textSizeSearch = typedArray.getDimension(R.styleable.Search_View_textSizeSearch, 20);

        // 搜索框字体颜色（使用十六进制代码，如#333、#8e8e8e）
        int defaultColor = context.getResources().getColor(R.color.colorText); // 默认颜色 = 灰色
        textColorSearch = typedArray.getColor(R.styleable.Search_View_textColorSearch, defaultColor);

        // 搜索框提示内容（String）
        textHintSearch = typedArray.getString(R.styleable.Search_View_textHintSearch);

        // 搜索框高度
        searchBlockHeight = typedArray.getInteger(R.styleable.Search_View_searchBlockHeight, 150);

        // 搜索框颜色
        int defaultColor2 = context.getResources().getColor(R.color.colorDefault); // 默认颜色 = 白色
        searchBlockColor = typedArray.getColor(R.styleable.Search_View_searchBlockColor, defaultColor2);

        // 释放资源
        typedArray.recycle();
    }

    /**
     * 关注b
     * 作用：初始化搜索框
     */
    private void init() {

        // 1. 初始化UI组件->>关注c
        initView();

        // 2. 实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(context);

        // 3. 第1次进入时查询所有的历史搜索记录
        queryData("");

        /**
         * "清空搜索历史"按钮
         */
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清空数据库->>关注2
                deleteData();
                // 模糊搜索空字符 = 显示所有的搜索历史（此时是没有搜索记录的）
                queryData("");

                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        /**
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);

                    if (!(mCallBack == null)) {
                        mCallBack.SearchAciton(et_search.getText().toString());
                    }

                    if (((SearchActivity) context).mCurrentTipList.size() > 0) {
                        boolean hasData = hasData(((SearchActivity) context).mCurrentTipList.get(0).getName());

                        if (!hasData) {
                            insertData(((SearchActivity) context).mCurrentTipList.get(0).getName(), ((SearchActivity) context).mCurrentTipList.get(0).getAddress(), ((SearchActivity) context).mCurrentTipList.get(0).getPoint().getLongitude(), ((SearchActivity) context).mCurrentTipList.get(0).getPoint().getLatitude());
                            queryData("");
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        /**
         * 搜索框的文本变化实时监听
         */
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                if (!(bCallSearch == null)) {
                    bCallSearch.SearchAciton(et_search.getText().toString());
                }
                llHistorySearch.setVisibility(GONE);

                if (isEmpty(et_search.getText().toString())) {
                    llHistorySearch.setVisibility(VISIBLE);
                    queryData("");
                }
//                // 每次输入后，模糊查询数据库 & 显示
//                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
//                String tempName = et_search.getText().toString();
//                queryData(tempName); // ->>关注1
            }
        });

        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取用户点击列表里的文字,并自动填充到搜索框内
                TextView tvLng = (TextView) view.findViewById(R.id.tv_lng);
                TextView tvLat = (TextView) view.findViewById(R.id.tv_lat);
                if (!isEmpty(tvLng.getText().toString().trim()) && !isEmpty(tvLat.getText().toString().trim()))
                    bHistoryRecordItem.HistoryRecordAciton(tvLng.getText().toString().trim(), tvLat.getText().toString().trim());
//                String name = textView.getText().toString();
//                String content = textView1.getText().toString();
//                et_search.setText(name);
            }
        });

        /**
         * 点击返回按键后的事件
         */
        searchBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 注：由于返回需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
                if (!(bCallBack == null)) {
                    bCallBack.BackAciton();
                }
                //根据输入的内容模糊查询商品，并跳转到另一个界面，这个根据需求实现
//                Toast.makeText(context, "返回到上一页", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 关注c：绑定搜索框xml视图
     */
    private void initView() {
        // 1. 绑定R.layout.search_layout作为搜索框的xml文件
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);

        // 2. 绑定搜索框EditText
        llHistorySearch = (LinearLayout) findViewById(R.id.ll_history_search);
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.setTextSize(textSizeSearch);
        et_search.setTextColor(textColorSearch);
        et_search.setHint(textHintSearch);

        // 3. 搜索框背景颜色
        search_block = (LinearLayout) findViewById(R.id.search_block);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) search_block.getLayoutParams();
        params.height = searchBlockHeight;
        search_block.setBackgroundColor(searchBlockColor);
        search_block.setLayoutParams(params);

        // 4. 历史搜索记录 = ListView显示
        listView = (SearchListView) findViewById(R.id.listView);

        // 5. 删除历史搜索记录 按钮
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        tv_clear.setVisibility(INVISIBLE);

        // 6. 返回按键
        searchBack = (AppCompatButton) findViewById(R.id.bt_cancel);
    }

    /**
     * 关注1
     * 模糊查询数据 & 显示到ListView列表上
     */
    public void queryData(String tempName) {
        // 1. 模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name,path,lng,lat from records where name like '%" + tempName + "%' order by id desc ", null);
        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
        adapter = new SimpleCursorAdapter(context, R.layout.simple_list_item_1, cursor, new String[]{"name", "path", "lng", "lat"},
                new int[]{R.id.text1, R.id.text2, R.id.tv_lng, R.id.tv_lat}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 3. 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if (tempName.equals("") && cursor.getCount() != 0) {
            tv_clear.setVisibility(VISIBLE);
        } else {
            tv_clear.setVisibility(INVISIBLE);
        }
    }

    /**
     * 关注2：清空数据库
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        tv_clear.setVisibility(INVISIBLE);
    }

    /**
     * 关注3
     * 检查数据库中是否已经有该搜索记录
     */
    public boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 关注4
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    public void insertData(String tempName, String path, double lng, double lat) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name,path,lng,lat) values('" + tempName + "', '" + path + "', " + lng + ", " + lat + ")");
        db.close();
    }

    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     */
    public void setOnClickSearch(ICallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    /**
     * 点击返回后的操作，用于接口回调
     */
    public void setOnClickBack(bCallBack bCallBack) {
        this.bCallBack = bCallBack;
    }

    /**
     * 搜索实时监听，用于接口回调
     */
    public void setOnClickSearch(bCallSearch bCallSearch) {
        this.bCallSearch = bCallSearch;
    }

    /**
     * 历史记录item点击监听
     */
    public void setOnHistoryRecordItemClick(bHistoryRecordItem bHistoryRecordItem) {
        this.bHistoryRecordItem = bHistoryRecordItem;
    }
}
