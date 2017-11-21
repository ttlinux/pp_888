package com.a8android888.bocforandroid.Pullreceived;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.BannerBean;
import com.a8android888.bocforandroid.MainActivity;
import com.a8android888.bocforandroid.activity.WelcomeActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HandleNotificationMethod;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.view.NoticeDialog;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.PushService;


/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogTools.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: ");

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LogTools.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LogTools.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                LogTools.e(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                LogTools.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
                HandlerData(bundle.getString(JPushInterface.EXTRA_EXTRA), context,false);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                LogTools.e(TAG + bundle.getString(JPushInterface.EXTRA_EXTRA),
                        "[MyReceiver] 用户点击打开了通知" + bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
                LogTools.e(TAG, "[MyReceiver] 用户点击打开了通知: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                HandlerData(bundle.getString(JPushInterface.EXTRA_EXTRA), context,true);


            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                LogTools.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                LogTools.e(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                LogTools.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
LogTools.e("eeeeee",e.toString());
        }

    }

    private void HandlerData(String NotificationString,Context context,boolean isclick)
    {
        LogTools.e("NotificationString",NotificationString);
        try {
            JSONObject jsonObject=new JSONObject(NotificationString);
          if(isclick) {
              if (MainActivity.isexit == 2) {
                  Intent intent = new Intent(context, WelcomeActivity.class);
                  intent.putExtra(BundleTag.JsonObject, jsonObject.toString());
                  intent.setAction(Intent.ACTION_MAIN);
                  intent.addCategory(Intent.CATEGORY_LAUNCHER);
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  context.startActivity(intent);
              } else {
                  new HandleNotificationMethod().modeluonclick2(BannerBean.HandleJson(jsonObject), context);

              }
          }
            else {
              LogTools.e("registersada",((BaseApplication) context.getApplicationContext()).getIsAppBackstage()+"");
               if (MainActivity.isexit != 2) {
                   if(! ((BaseApplication) context.getApplicationContext()).getIsAppBackstage()) {
                   JSONObject data = new JSONObject();
                   JSONObject datas = new JSONObject();
                   datas.put("gonggaoName", jsonObject.optString("pushTitle", ""));
                   datas.put("ganggaoContent", jsonObject.optString("pushContent", ""));
                   datas.put("linkUrl", jsonObject.optString("linkUrl", ""));
                   datas.put("imagesUrl", "");
                   datas.put("linkGroupId", jsonObject.optString("linkGroupId", ""));
                   datas.put("level", jsonObject.optString("level", ""));
                   datas.put("linkType", jsonObject.optString("linkType", ""));
                   datas.put("openLinkType", jsonObject.optString("openLinkType", ""));
                   datas.put("typeCode", jsonObject.optString("typeCode", ""));
                   datas.put("gonggaoType", "1");
                   datas.put("articleId", jsonObject.optString("articleId", ""));
                   datas.put("articleType", jsonObject.optString("articleType", ""));
                   datas.put("gameCode", jsonObject.optString("gameCode", ""));
                   datas.put("cateCode", jsonObject.optString("cateCode", ""));
                   data.put("datas", datas);
                   LogTools.e("NotificationString", data.toString());
                   NoticeDialog dialog = new NoticeDialog(((BaseApplication) context.getApplicationContext()).getActivity(),"pull");
                   dialog.setdata(data);
                   dialog.show();
                   ((BaseApplication) ((BaseApplication) context.getApplicationContext()).getActivity().getApplication()).setNoticehasClick(true);

               }
           }
             }
        } catch (JSONException e) {
            e.printStackTrace();
            LogTools.e("NotificationString", "errrrrrr");
        }

    }
}
