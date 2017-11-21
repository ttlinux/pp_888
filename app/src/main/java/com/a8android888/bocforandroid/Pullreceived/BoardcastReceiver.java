package com.a8android888.bocforandroid.Pullreceived;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.jpush.android.service.PushService;

/**
 * Created by Administrator on 2017/9/30.
 */
public class BoardcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pushintent=new Intent(context,PushService.class);//启动极光推送的服务
        context.startService(pushintent);
    }
}
