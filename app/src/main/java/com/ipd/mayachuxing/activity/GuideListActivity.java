package com.ipd.mayachuxing.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.adapter.GuideHotAdapter;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.common.view.CustomLinearLayoutManager;
import com.ipd.mayachuxing.common.view.TopView;
import com.ipd.mayachuxing.utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.ipd.mayachuxing.common.config.IConstants.SERVICE_E_MALL;
import static com.ipd.mayachuxing.common.config.IConstants.SERVICE_PHONE;

/**
 * Description ：用户指南列表
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/18.
 */
public class GuideListActivity extends BaseActivity {

    @BindView(R.id.tv_guide_list)
    TopView tvGuideList;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.rv_guide_list)
    RecyclerView rvGuideList;

    private int type;
    private GuideHotAdapter guideHotAdapter;
    private List<String> guideItemList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_list;
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
        ImmersionBar.setTitleBar(this, tvGuideList);

        type = getIntent().getIntExtra("type", 0);
        tvTopTitle.setText(getIntent().getStringExtra("title"));
        guideItemList = getIntent().getStringArrayListExtra("guideItemList");

        //热点问题
        CustomLinearLayoutManager hotLayoutManager = new CustomLinearLayoutManager(this);
        hotLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);//方向
        rvGuideList.setLayoutManager(hotLayoutManager);
        rvGuideList.setNestedScrollingEnabled(false);
        rvGuideList.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvGuideList.setItemAnimator(new DefaultItemAnimator());//加载动画

        rvGuideList.setAdapter(guideHotAdapter = new GuideHotAdapter(guideItemList));
        guideHotAdapter.bindToRecyclerView(rvGuideList);
        guideHotAdapter.openLoadAnimation();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        switch (type) {
            case 0:
                guideHotAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", //"（详见app首页地图上的围栏范围）\n" +
                                        //   "\n" +
                                        //   "\n" +
                                        "为了方便用户的使用，及时地维护车辆，我们限定了小马的骑行范围，当超出运营范围时，车辆会无法结束订单，只有将车骑回运营范围时方可结束订单。"));
                                break;
                            case 1:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "\n" +
                                        "退款流程：\n" +
                                        "\n" +
                                        "①点击【我的钱包】；\n" +
                                        "\n" +
                                        "②点击【退押金】；\n" +
                                        "\n" +
                                        "③选择退押金原因（至少选择一项），点击【确认退押金】即可。"));
                                break;
                            case 2:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马骑行1元起步，每5分钟1元 ，不足5分钟按5分钟计算。"));
                                break;
                            case 3:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "①使用前请确认电量是否满足此次行程，骑行前请检查车辆刹车/灯光。\n" +
                                        "\n" +
                                        "②预约车辆直接开锁骑行，未预约车辆点击扫码用车，再开锁骑行。"));
                                break;
                            case 4:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "为了方便大家出行，小马特别开启了预约功能。\n" +
                                        "\n" +
                                        "您可以在APP上点击黄色车辆图标进行预约，预约为您保留10分钟，10分钟后会自动取消。\n" +
                                        "\n" +
                                        "每日可免费预约2次。当您到达车辆50米内，可以按寻车铃寻找车辆。"));
                                break;
                            case 5:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "临时锁车并未还车，将持续计费，临时锁车的收费方式与当地正常骑行收费方式一致。"));
                                break;
                            case 6:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马有指定的运营范围，如果超出范围无法将车骑回，需要由工作人员将车辆调回运营区域，则将收取100元/单的调度费。"));
                                break;
                            case 7:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马倡导文明骑行、规范停车，停车时需要将车停在停车点内，头朝外，与马路边缘呈90°摆放，不按规定停放一经发现将作出相对应的处罚。\n" +
                                        "\n" +
                                        "违停处罚标准：\n" +
                                        "\n" +
                                        "1. 首次违停，客服将致电上一位骑行者进行提醒，并收取10元违停费用；；\n" +
                                        "\n" +
                                        "\n" +
                                        "2. 第二次违停，客服将致电上一位骑行者进行违停警告，并收取20元违停费用；\n" +
                                        "\n" +
                                        "\n" +
                                        "3. 第三次违停，客服将发短信通知上一位骑行者进行违停警告，收取50元违停费用，同时冻结账户30天；\n" +
                                        "\n" +
                                        "\n" +
                                        "4. 严重违停行为将永久封号。\n" +
                                        "\n" +
                                        "\n" +
                                        "因乱停乱放引起的事故、纠纷等问题，上一位骑行者将承担主要责任。"));
                                break;
                            case 8:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马有指定的围栏区域（详见APP指示），您将车辆骑出围栏范围后，可以正常骑行，也可以临时锁车（临时锁车和正常骑行一样收费），但无法还车！\n" +
                                        "\n" +
                                        "如果您需要还车，请将车辆骑回原始围栏区域的停车点，如果您无法骑回，可以致电客服，由客服帮您还车，并安排工作人员将车辆调度回原围栏内，为此我们将扣除您100元/次的调度费。\n" +
                                        "\n" +
                                        "跨围栏不能使用。"));
                                break;
                            case 9:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "因为系统原因导致无法还车产生多余费用时，如果您无法第一时间联系到客服，有以下几种方式进行处理：\n" +
                                        "\n" +
                                        "\n" +
                                        "①72小时内任何时段拨打" + SERVICE_PHONE + "，告诉客服您的注册账号和使用车辆编号；\n" +
                                        "\n" +
                                        "\n" +
                                        "②在小马共享公众号留下您的注册账号和使用车辆编号；\n" +
                                        "\n" +
                                        "\n" +
                                        "️③在微博上私信小编留下您的注册账号和使用车辆编号；\n" +
                                        "\n" +
                                        "\n" +
                                        "️④发邮件至" + SERVICE_E_MALL + "，留下您的注册账号和使用车辆编号；\n" +
                                        "\n" +
                                        "\n" +
                                        "⑤客服将在24小时内为您处理，本次骑行费用也将全额返还。"));
                                break;
                        }
                    }
                });
                break;
            case 1:
                guideHotAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "为了方便大家更好地找车与还车，小马在大围栏内的很多小区门口、公共停车位等适合停车的区域画了好多个固定停车点，只有在停车点内才可以还车哦。详情请查看APP首页停车点。"));
                                break;
                            case 1:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "发现车辆损坏，建议您先还车，然后在APP页面右下角点击【联系客服】-【故障上报】-选择相应的问题并拍照上传，建议您就近选择其他车辆出行，我们会加急前往维修车辆。"));
                                break;
                            case 2:
                                startActivity(new Intent(GuideListActivity.this, CantUnlockActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "①预约成功后，您的APP上面会显示步行导航路线，在靠近车身50米以内时，您点击APP上面的寻车铃，电单车会发出铃声或亮灯，帮助您更快找到车。\n" +
                                        "\n" +
                                        "②电动车定位受环境影响会有飘移现象，找到车时请同时观察定位点周围，车辆集中区域请确认预约车辆与扫码开锁车辆是否是同一辆。"));
                                break;
                            case 4:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "小马为每一位骑行者投保了第三者责任险和骑行者意外险，请您放心骑行。如遇交通事故：\n" +
                                        "\n" +
                                        "①人员受伤，请联系交警（122），同时拨打" + SERVICE_PHONE + "客服电话进行备案。然后就医，请保存好交通事故单和就医凭证（所有发票和病例本）。\n" +
                                        "\n" +
                                        "②人员未受伤，车辆受损，车辆可行驶，拍照上报故障。\n" +
                                        "\n" +
                                        "③人员未受伤，车辆受损，车辆不可行驶，联系小马客服：" + SERVICE_PHONE + "。"));
                                break;
                            case 5:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "如果忘记还车，车费将会继续计费，（同时为未上锁状态）\n" +
                                        "\n" +
                                        "①如车还在您附近，请及时还车，\n" +
                                        "\n" +
                                        "②如果车已经被其他用户骑走，请及时联系客服（" + SERVICE_PHONE + "），客服将在车辆静止状态下为您还车，\n" +
                                        "\n" +
                                        "③由于未上锁造成的车辆遗失损坏，将追究最后用车人的责任。"));
                                break;
                        }
                    }
                });
                break;
            case 2:
                guideHotAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "①押金是为使用小马共享的保证金，账户在非骑行状态下，随时可以申请退还，退还之后如需要骑行，再次充值押金即可，目前缴纳押金有两种支付渠道：微信和支付宝，押金退还是原路退回。\n" +
                                        "②车费用于骑行费用扣除，可用于骑行结束后结算抵扣，后期充值车费请点击APP上我的钱包页面，进行点击充值，充值可选金额，车费不设置有效期。"));
                                break;
                            case 1:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "①确认你是否在APP内做了押金退款操作；\n" +
                                        "\n" +
                                        "②如果您用多个手机注册了小马，请先确认正确手机号登录；\n" +
                                        "\n" +
                                        "③你可以进入微信与支付宝账单进行查询，可能退款信息显示会有延迟，若还有疑问可拨打微信热线：95017，支付宝热线：95188，\n" +
                                        "\n" +
                                        "④如果押金是通过微信与支付宝绑定的银行卡支付，到达银行账户会有延迟，建议您7个工作日仍没收到押金到账信息时，请先手动查询银行卡余额。\n" +
                                        "\n" +
                                        "以上方法解决不了，可直接拨打客服电话：" + SERVICE_PHONE + "，或在APP上联系在线客服进行咨询。"));
                                break;
                            case 2:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "①为了向您提供更好的骑行体验，如在结单时余额不足，当前订单仍可以正常结束，但会导致您的账户余额为负值。\n" +
                                        "\n" +
                                        "②欠费状态下您无法开始下次骑行，也无法退回押金，当您再次充值时，系统会先扣除上次欠费。"));
                                break;
                        }
                    }
                });
                break;
            case 3:
                guideHotAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (position) {
                            case 0:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "①点击APP分享界面，分享各类活动给好友，邀请他手机注册参与活动，可获得相对应的骑行费用或骑行券奖励。（奖励不叠加）\n" +
                                        "\n" +
                                        "注：同一手机号，同一手机设备，同一支付帐号均视为一个用户。"));
                                break;
                            case 1:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "骑行需要符合电单车使用规则，如违规操作可能影响账户的继续使用：\n" +
                                        "\n" +
//                                        "①连续2次及以上违规停车将被罚款5元/次，并冻结账号3-7天；\n" +
//                                        "\n" +
//                                        "\n" +
                                        "①多次违反电单车使用规则到信用分降低至0时，账号将被冻结\n" +
                                        "\n" +
                                        "\n" +
                                        "③若骑行结束后还车计费出现异常，您可点击故障申报计费异常，系统将暂时性冻结您的账户，待系统问题解决后立即恢复使用。"));
                                break;
                            case 2:
                                startActivity(new Intent(GuideListActivity.this, GuideDetailsActivity.class).putExtra("title", guideItemList.get(position)).putExtra("content", "每个账号注册时初始信用分为100分，\n" +
                                        "\n" +
                                        "每次违规骑行扣除信用分2分，每次成功骑行信用分加1分，100分为满\n" +
                                        "\n" +
                                        "信用分低于0分账号将被冻结，如要骑行，请联系客服：" + SERVICE_PHONE + "为您处理"));
//                                        "\n" +
//                                        "信用分低于40分，价格提升100%，\n" +
//                                        "\n" +
//                                        "信用为0不可再使用，冻结14天。"));
                                break;
                        }
                    }
                });
                break;
        }
    }
}
