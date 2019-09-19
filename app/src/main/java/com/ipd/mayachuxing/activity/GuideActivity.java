package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.Html;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.GuideAccountAdapter;
import com.ipd.mayachuxing.adapter.GuideBikeAdapter;
import com.ipd.mayachuxing.adapter.GuideHotAdapter;
import com.ipd.mayachuxing.adapter.GuideUseAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.CustomLinearLayoutManager;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.CALL_PHONE;
import static com.ipd.mayachuxing.common.config.IConstants.SERVICE_PHONE;

/**
 * Description ：用户指南
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/18.
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.tv_guide)
    TopView tvGuide;
    @BindView(R.id.rv_hot_issues)
    RecyclerView rvHotIssues;
    @BindView(R.id.rv_bike_issues)
    RecyclerView rvBikeIssues;
    @BindView(R.id.rv_account_issues)
    RecyclerView rvAccountIssues;
    @BindView(R.id.rv_use_issues)
    RecyclerView rvUseIssues;
    @BindView(R.id.tv_call_service)
    AppCompatTextView tvCallService;

    private GuideHotAdapter guideHotAdapter;
    private GuideBikeAdapter guideBikeAdapter;
    private GuideAccountAdapter guideAccountAdapter;
    private GuideUseAdapter guideUseAdapter;

    private List<String> guideItemList = new ArrayList<>();
    private List<String> guideItemList1 = new ArrayList<>();
    private List<String> guideItemList2 = new ArrayList<>();
    private List<String> guideItemList3 = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide;
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
        ImmersionBar.setTitleBar(this, tvGuide);

        //热点问题
        CustomLinearLayoutManager hotLayoutManager = new CustomLinearLayoutManager(this);
        hotLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvHotIssues.setLayoutManager(hotLayoutManager);
        rvHotIssues.setNestedScrollingEnabled(false);
        rvHotIssues.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvHotIssues.setItemAnimator(new DefaultItemAnimator());//加载动画

        guideItemList.add("已开通区域？");
        guideItemList.add("如何申请退押金？");
        guideItemList.add("车费说明？");
        guideItemList.add("用车说明");
//        guideItemList.add("预约说明");
//        guideItemList.add("临时锁车如何收费？");
//        guideItemList.add("什么是调度费？");
//        guideItemList.add("什么是违章停车费用？");
//        guideItemList.add("骑行超围栏了怎么办？");
//        guideItemList.add("系统故障当时联系不到客服怎么办？");
        rvHotIssues.setAdapter(guideHotAdapter = new GuideHotAdapter(guideItemList));
        guideHotAdapter.bindToRecyclerView(rvHotIssues);
        guideHotAdapter.openLoadAnimation();

        //骑行问题
        CustomLinearLayoutManager bikeLayoutManager = new CustomLinearLayoutManager(this);
        bikeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvBikeIssues.setLayoutManager(bikeLayoutManager);
        rvBikeIssues.setNestedScrollingEnabled(false);
        rvBikeIssues.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvBikeIssues.setItemAnimator(new DefaultItemAnimator());//加载动画

        guideItemList1.add("车在哪还？");
        guideItemList1.add("车辆故障了怎么办？");
        rvBikeIssues.setAdapter(guideBikeAdapter = new GuideBikeAdapter(guideItemList1));
        guideBikeAdapter.bindToRecyclerView(rvBikeIssues);
        guideBikeAdapter.openLoadAnimation();

        //账号问题
        CustomLinearLayoutManager accountLayoutManager = new CustomLinearLayoutManager(this);
        accountLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvAccountIssues.setLayoutManager(accountLayoutManager);
        rvAccountIssues.setNestedScrollingEnabled(false);
        rvAccountIssues.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvAccountIssues.setItemAnimator(new DefaultItemAnimator());//加载动画

        guideItemList2.add("押金与车费有什么不同？");
        guideItemList2.add("押金退款超过7个工作日还没到账，我该怎么做？");
        rvAccountIssues.setAdapter(guideAccountAdapter = new GuideAccountAdapter(guideItemList2));
        guideAccountAdapter.bindToRecyclerView(rvAccountIssues);
        guideAccountAdapter.openLoadAnimation();

        //APP使用
        CustomLinearLayoutManager useLayoutManager = new CustomLinearLayoutManager(this);
        useLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvUseIssues.setLayoutManager(useLayoutManager);
        rvUseIssues.setNestedScrollingEnabled(false);
        rvUseIssues.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvUseIssues.setItemAnimator(new DefaultItemAnimator());//加载动画

        guideItemList3.add("邀请好友");
        guideItemList3.add("用户提示被冻结");
        rvUseIssues.setAdapter(guideUseAdapter = new GuideUseAdapter(guideItemList3));
        guideUseAdapter.bindToRecyclerView(rvUseIssues);
        guideUseAdapter.openLoadAnimation();

        tvCallService.setText(Html.fromHtml("客服热线: <font color=\"#3F8AFE\">" + SERVICE_PHONE + "</font>"));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        guideHotAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", //"（详见app首页地图上的围栏范围）\n" +
                               // "\n" +
                               // "\n" +
                                "为了方便用户的使用，及时地维护车辆，我们限定了小马的骑行范围，当超出运营范围时，车辆会无法结束订单，只有将车骑回运营范围时方可结束订单。"));
                        break;
                    case 1:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "\n" +
                                "退款流程：\n" +
                                "\n" +
                                "①点击【我的钱包】；\n" +
                                "\n" +
                                "②点击【退押金】；\n" +
                                "\n" +
                                "③选择退押金原因（至少选择一项），点击【确认退押金】即可。"));
                        break;
                    case 2:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马骑行1元起步，每5分钟1元 ，不足5分钟按5分钟计算。"));
                        break;
                    case 3:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "①使用前请确认电量是否满足此次行程，骑行前请检查车辆刹车/灯光。\n" +
                                "\n" +
                                "②预约车辆直接开锁骑行，未预约车辆点击扫码用车，再开锁骑行。"));
                        break;
//                    case 4:
//                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "为了方便大家出行，小马特别开启了预约功能。\n" +
//                                "\n" +
//                                "您可以在APP上点击黄色车辆图标进行预约，预约为您保留10分钟，10分钟后会自动取消。\n" +
//                                "\n" +
//                                "每日可免费预约2次。当您到达车辆50米内，可以按寻车铃寻找车辆。"));
//                        break;
//                    case 5:
//                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "临时锁车并未还车，将持续计费，临时锁车的收费方式与当地正常骑行收费方式一致。"));
//                        break;
//                    case 6:
//                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马有指定的运营范围，如果超出范围无法将车骑回，需要由工作人员将车辆调回运营区域，则将收取100元/单的调度费。"));
//                        break;
//                    case 7:
//                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马倡导文明骑行、规范停车，停车时需要将车停在停车点内，头朝外，与马路边缘呈90°摆放，不按规定停放一经发现将作出相对应的处罚。\n" +
//                                "\n" +
//                                "违停处罚标准：\n" +
//                                "\n" +
//                                "1. 首次违停，客服将致电上一位骑行者进行提醒，并收取10元违停费用；；\n" +
//                                "2. 第二次违停，客服将致电上一位骑行者进行违停警告，并收取20元违停费用；\n" +
//                                "3. 第三次违停，客服将发短信通知上一位骑行者进行违停警告，收取50元违停费用，同时冻结账户30天；\n" +
//                                "4. 严重违停行为将永久封号。\n" +
//                                "因乱停乱放引起的事故、纠纷等问题，上一位骑行者将承担主要责任。"));
//                        break;
//                    case 8:
//                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马有指定的围栏区域（详见APP指示），您将车辆骑出围栏范围后，可以正常骑行，也可以临时锁车（临时锁车和正常骑行一样收费），但无法还车！如果您需要还车，请将车辆骑回原始围栏区域的停车点，如果您无法骑回，可以致电客服，由客服帮您还车，并安排工作人员将车辆调度回原围栏内，为此我们将扣除您100元/次的调度费。\n" +
//                                "\n" +
//                                "跨围栏不能使用。"));
//                        break;
//                    case 9:
//                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "因为系统原因导致无法还车产生多余费用时，如果您无法第一时间联系到客服，有以下几种方式进行处理：\n" +
//                                "①72小时内任何时段拨打4006076666，告诉客服您的注册账号和使用车辆编号；\n" +
//                                "②在小马共享公众号留下您的注册账号和使用车辆编号；\n" +
//                                "️③在微博上私信小编留下您的注册账号和使用车辆编号；\n" +
//                                "️④发邮件至kefu666@liubike.cn，留下您的注册账号和使用车辆编号；\n" +
//                                "⑤客服将在24小时内为您处理，本次骑行费用也将全额返还。"));
//                        break;
                }
            }
        });

        guideBikeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList1.get(position)).putExtra("content", "为了方便大家更好地找车与还车，小马在大围栏内的很多小区门口、公共停车位等适合停车的区域画了好多个固定停车点，只有在停车点内才可以还车哦。详情请查看APP首页停车点。"));
                        break;
                    case 1:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList1.get(position)).putExtra("content", "发现车辆损坏，建议您先还车，然后在APP页面右下角点击【联系客服】-【故障上报】-选择相应的问题并拍照上传，建议您就近选择其他车辆出行，我们会加急前往维修车辆。"));
                        break;
                }
            }
        });

        guideAccountAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList2.get(position)).putExtra("content", "①押金是为使用小马共享的保证金，账户在非骑行状态下，随时可以申请退还，退还之后如需要骑行，再次充值押金即可，目前缴纳押金有两种支付渠道：微信和支付宝，押金退还是原路退回。\n" +
                                "②车费用于骑行费用扣除，可用于骑行结束后结算抵扣，后期充值车费请点击APP上我的钱包页面，进行点击充值，充值可选金额，车费不设置有效期。"));
                        break;
                    case 1:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList2.get(position)).putExtra("content", "①确认你是否在APP内做了押金退款操作；\n" +
                                "\n" +
                                "②如果您用多个手机注册了小马，请先确认正确手机号登录；\n" +
                                "\n" +
                                "③你可以进入微信与支付宝账单进行查询，可能退款信息显示会有延迟，若还有疑问可拨打微信热线：95017，支付宝热线：95188，\n" +
                                "\n" +
                                "④如果押金是通过微信与支付宝绑定的银行卡支付，到达银行账户会有延迟，建议您7个工作日仍没收到押金到账信息时，请先手动查询银行卡余额。\n" +
                                "\n" +
                                "以上方法解决不了，可直接拨打客服电话：" + SERVICE_PHONE + "，或在APP上联系在线客服进行咨询。"));
                        break;
                }
            }
        });

        guideUseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList1.get(position)).putExtra("content", "①点击APP分享界面，分享各类活动给好友，邀请他手机注册参与活动，可获得相对应的骑行费用或骑行券奖励。（奖励不叠加）\n" +
                                "\n" +
                                "注：同一手机号，同一手机设备，同一支付帐号均视为一个用户。"));
                        break;
                    case 1:
                        startActivity(new Intent(GuideActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList1.get(position)).putExtra("content", "骑行需要符合电单车使用规则，如违规操作可能影响账户的继续使用：\n" +
                                "\n" +
//                                "①连续2次及以上违规停车将被罚款5元/次，并冻结账号3-7天；\n" +
//                                "\n" +
//                                "\n" +
                                "①多次违反电单车使用规则到信用分降低至0时，账号将被冻结\n" +
                                "\n" +
                                "\n" +
                                "③若骑行结束后还车计费出现异常，您可点击故障申报计费异常，系统将暂时性冻结您的账户，待系统问题解决后立即恢复使用。"));
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_hot_issues, R.id.tv_bike_issues, R.id.tv_account_issues, R.id.tv_use_issues, R.id.tv_call_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hot_issues:
                if (guideItemList.size() < 5) {
                    guideItemList.add("预约说明");
                    guideItemList.add("临时锁车如何收费？");
                    guideItemList.add("什么是调度费？");
                    guideItemList.add("什么是违章停车费用？");
                    guideItemList.add("骑行超围栏了怎么办？");
                    guideItemList.add("系统故障当时联系不到客服怎么办？");
                }
                startActivity(new Intent(this, GuideListActivity.class).putExtra("type", 0).putExtra("title", "热点问题").putStringArrayListExtra("guideItemList", (ArrayList<String>) guideItemList));
                break;
            case R.id.tv_bike_issues:
                if (guideItemList1.size() < 3) {
                    guideItemList1.add("车辆开不了锁怎么办？");
                    guideItemList1.add("如何找到预约车辆？");
                    guideItemList1.add("骑行中遇到交通事故怎么办？");
                    guideItemList1.add("忘了还车怎么办？");
                }
                startActivity(new Intent(this, GuideListActivity.class).putExtra("type", 1).putExtra("title", "骑行问题").putStringArrayListExtra("guideItemList", (ArrayList<String>) guideItemList1));
                break;
            case R.id.tv_account_issues:
                if (guideItemList2.size() < 3)
                    guideItemList2.add("骑行费用超过余额怎么办？");
                startActivity(new Intent(this, GuideListActivity.class).putExtra("type", 2).putExtra("title", "账号问题").putStringArrayListExtra("guideItemList", (ArrayList<String>) guideItemList2));
                break;
            case R.id.tv_use_issues:
                if (guideItemList3.size() < 3)
                    guideItemList3.add("信用分低有什么影响？");
                startActivity(new Intent(this, GuideListActivity.class).putExtra("type", 3).putExtra("title", "APP使用").putStringArrayListExtra("guideItemList", (ArrayList<String>) guideItemList3));
                break;
            case R.id.tv_call_service:
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + SERVICE_PHONE);//TODO  客服电话
                intent.setData(data);
                if (ActivityCompat.checkSelfPermission(this, CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;
        }
    }
}
