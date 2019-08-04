package com.ipd.mayachuxing.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.ipd.mayachuxing.R;
import com.ipd.mayachuxing.base.BaseActivity;
import com.ipd.mayachuxing.base.BasePresenter;
import com.ipd.mayachuxing.base.BaseView;
import com.ipd.mayachuxing.utils.ApplicationUtil;
import com.ipd.mayachuxing.utils.ToastUtil;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xqrcode.ui.CaptureActivity;
import com.xuexiang.xqrcode.ui.CaptureFragment;
import com.xuexiang.xqrcode.util.QRCodeAnalyzeUtils;

/**
 * Description ：扫码开锁
 * Author ： rmy
 * Email ： 942685687@qq.com
 * Time ： 2019/8/4.
 */
public class QRActivity extends BaseActivity {

    //    @BindView(R.id.tv_qr)
//    TopView tvQr;
//    @BindView(R.id.preview_view)
//    SurfaceView previewView;
//    @BindView(R.id.viewfinder_view)
//    ViewfinderView viewfinderView;

    @Override
    public int getLayoutId() {
        return R.layout.layout_test;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
//        //防止状态栏和标题重叠
//        ImmersionBar.setTitleBar(this, tvQr);

        // 为二维码扫描界面设置定制化界面
        CaptureFragment captureFragment = XQRCode.getCaptureFragment(R.layout.activity_qr);

        captureFragment.setAnalyzeCallback(analyzeCallback);

        captureFragment.setCameraInitCallBack(new CaptureFragment.CameraInitCallBack() {
            @Override
            public void callBack(Exception e) {
                if (e != null) {
                    CaptureActivity.showNoPermissionTip(QRActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }
        });
        captureFragment.getChildFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    /**
     * 二维码解析回调函数
     */

    QRCodeAnalyzeUtils.AnalyzeCallback analyzeCallback = new QRCodeAnalyzeUtils.AnalyzeCallback() {

        @Override

        public void onAnalyzeSuccess(Bitmap bitmap, String result) {

            if (true) {

                ToastUtil.showLongToast("扫描结果:" + result);

            } else {

                Intent resultIntent = new Intent();

                Bundle bundle = new Bundle();

                bundle.putInt(XQRCode.RESULT_TYPE, XQRCode.RESULT_SUCCESS);

                bundle.putString(XQRCode.RESULT_DATA, result);

                resultIntent.putExtras(bundle);


            }

        }


        @Override

        public void onAnalyzeFailed() {

            Intent resultIntent = new Intent();

            Bundle bundle = new Bundle();

            bundle.putInt(XQRCode.RESULT_TYPE, XQRCode.RESULT_FAILED);

            bundle.putString(XQRCode.RESULT_DATA, "");

            resultIntent.putExtras(bundle);


        }

    };
}
