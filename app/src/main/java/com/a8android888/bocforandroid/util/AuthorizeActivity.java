package com.a8android888.bocforandroid.util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/15.
 */
public class AuthorizeActivity {

    public static void AuthorizeActivity(final Activity activity,String url,final Intent intent,final int type)
    {
        String username = ((BaseApplication)activity.getApplication()).getBaseapplicationUsername();
        RequestParams requestParams=new RequestParams();
        requestParams.put("userName",username);
        requestParams.put("deviceId",android.os.Build.SERIAL);
        requestParams.put("platformType","android");
        SharedPreferences sharedPreferences = ((BaseApplication) activity.getApplicationContext()).getSharedPreferences();
        String web_flag = sharedPreferences.getString(BundleTag.siteFlag, "");
        requestParams.put("webFlag",web_flag);
        requestParams.put("backUrl",url);
        Httputils.PostWithBaseUrl(Httputils.AuthorizeAct,requestParams,new MyJsonHttpResponseHandler(activity,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("AuthorizeAct",jsonObject.toString());
                if(jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                {
                    switch (type)
                    {
                        case 0:
                            intent.putExtra(BundleTag.URL, jsonObject.optJSONObject("datas").optString("backUrl",""));
                            activity.startActivity(intent);
                            break;
                        case 1:
                            Uri content_url = Uri.parse(jsonObject.optJSONObject("datas").optString("backUrl", ""));
                            intent.setData(content_url);
                            activity.startActivity(intent);
                            break;
                    }

                }
                else
                {
                    ToastUtil.showMessage(activity,jsonObject.optString("msg"));
                }
            }
        });
    }
}
