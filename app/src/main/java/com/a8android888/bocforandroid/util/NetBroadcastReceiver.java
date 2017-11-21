package com.a8android888.bocforandroid.util;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.Window;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;

/**
 * Created by cheng on 2016/11/28.
 */
public class NetBroadcastReceiver extends BroadcastReceiver {
    public NetEvevt evevt = BaseApplication.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型


            evevt.onNetChange(netWorkState);

//            else{
//            }
        }
        Log.e("dddaaa","没有网络");
    }
    /**
     * 用户是否退出登陆提示框
     */
    class CreatExitLoginDialog extends Dialog {
        public CreatExitLoginDialog(Context context) {
            super(context);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.networklayout);
        }
    }
    // 自定义接口
    public interface NetEvevt {
        public void onNetChange(int netMobile);
    }
}
