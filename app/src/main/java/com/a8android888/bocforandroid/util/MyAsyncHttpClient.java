package com.a8android888.bocforandroid.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Administrator on 2017/4/3.
 */
public class MyAsyncHttpClient extends AsyncHttpClient {

    public MyAsyncHttpClient(Context context)
    {
        if(context==null)
        {
            LogTools.e("错误提示","Context是空");
            return;
        }
        SharedPreferences sharedPreferences = ((BaseApplication) context.getApplicationContext()).getSharedPreferences();
        String web_flag = sharedPreferences.getString(BundleTag.siteFlag, "");
        this.addHeader("web-flag",web_flag);

        this.addHeader("device-type","android");
        this.addHeader("platform-type","android");
        String SERIAL=android.os.Build.SERIAL;
        this.addHeader("device-id",SERIAL);
        LogTools.e("device_id", SERIAL);
        this.addHeader("web-domain", sharedPreferences.getString(BundleTag.siteDomain, ""));
        LogTools.e("siteReserveDomain",  sharedPreferences.getString(BundleTag.siteDomain, ""));
//        this.addHeader("client_ip","127.0.0.1");
        String Access_token=(((BaseApplication)context.getApplicationContext())).getAccess_token();
        String Username=((BaseApplication)context.getApplicationContext()).getBaseapplicationUsername();
        if(Access_token!=null && !Access_token.equalsIgnoreCase(""))
        {
            LogTools.e("Access_token",Access_token);
            this.addHeader("access-token", Access_token);
        }

        if(Username!=null && !Username.equalsIgnoreCase(""))
        {
            LogTools.e("user_name",Username);
            this.addHeader("user-name",Username);
        }
        if(BaseApplication.packagename!=null && !BaseApplication.packagename.equalsIgnoreCase(""))
        {
            LogTools.e("packagenameheader",BaseApplication.packagename);
            this.addHeader("update-package",BaseApplication.packagename);
        }

    }
}
