package com.a8android888.bocforandroid.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.WebAPPChooserDialog;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/22.
 */
public class getwebdataActivity {
  static   boolean iscilick=false;
    public static boolean GetData(Activity context,final String flat,final String gameCode,final String title) {
        RequestParams requestParams = new RequestParams();
//        if(intent.getStringExtra("flat").equalsIgnoreCase("bbin")){
//            requestParams.put("gameCode",intent.getStringExtra("gamecode"));
//        }
//        if(intent.getStringExtra("flat").equalsIgnoreCase("ag")){
//            requestParams.put("gameCode",intent.getStringExtra("gamecode"));
//        }
        requestParams.put("gameCode", gameCode);
        requestParams.put("userName", ((BaseApplication) context.getApplication()).getBaseapplicationUsername());
        requestParams.put("flat", flat);
        Httputils.PostWithBaseUrl(Httputils.gameloginlist, requestParams, new MyJsonHttpResponseHandler(context, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                String datas;
                LogTools.e("需要打开的网页", jsonObject.toString());
                if (jsonObject.optString("datas") == null || jsonObject.optString("datas").equalsIgnoreCase("null") || jsonObject.optString("datas").equalsIgnoreCase("") || jsonObject.optString("datas").length() < 5) {
                    ToastUtil.showMessage(context, jsonObject.optString("msg", ""));
                    iscilick = false;
                    return;
                }

                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000")){
                    iscilick = false;
                return;
                }
                datas = jsonObject.optString("datas", "");
//                if (flat.equalsIgnoreCase("sa")) {
//                    iscilick = true;
//                    String[] all = datas.split("\\^");
//                    LogTools.e("ddafdfadfAA" + all, "dddd");
//                    RequestParams requestParams = new RequestParams();
//                    requestParams.put("username", all[1]);
//                    requestParams.put("token", all[2]);
//                    requestParams.put("lobby", all[3]);
//                    requestParams.put("lang", all[4]);
//                    requestParams.put("returnurl", Httputils.BaseUrl);
//                    requestParams.put("mobile", "true");
//                    String postDate = "username=" + all[1] + "&token=" + all[2] + "&lobby=" + all[3] + "&lang=" + all[4] + "&returnurl=" + Httputils.BaseUrl + "&mobile=" + "true";
////                    mWebView.postUrl(all[0] + "?", EncodingUtils.getBytes(postDate, "utf-8"));
//                    LogTools.ee("ddafdfadfSS" + EncodingUtils.getBytes(postDate, "utf-8"), all[0] + "?" + requestParams + "");
//                } else {
                    iscilick = true;
                    Openurl(context,flat, datas,title);
                    LogTools.e("kkkxzcxzsad", datas);
//                }
            }
        });
        return iscilick;
    }
    private static void Openurl(Activity context,final String Url,final String flat,final String titleont) {
        SharedPreferences sharedPreferences = ((BaseApplication) context.getApplication()).getSharedPreferences();
        String title = sharedPreferences.getString(BundleTag.chooseBrowser, "");
        final WebAPPChooserDialog webAPPChooserDialog = new WebAPPChooserDialog(context, Url);
        webAPPChooserDialog.show();
        webAPPChooserDialog.setOnSelectDefalutWebListener(new WebAPPChooserDialog.OnSelectDefalutWebListener() {
            @Override
            public void OnDefalutselect() {
//                mWebView.loadUrl(Url);
                webAPPChooserDialog.dismiss();
//                ToastUtil.showMessage(gamewebActivity.this,"自己");
            }
        });
        webAPPChooserDialog.setTitle(title);
    }
}
