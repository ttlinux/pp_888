package com.a8android888.bocforandroid.util;

import android.app.Activity;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/26.
 */
public class HttpForNotice {

    private static void HttpForNotice(Activity activity,String panelSn,final TextView textView)
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("panelSn",panelSn);
        Httputils.PostWithBaseUrl(Httputils.getWebPanel,requestParams,new MyJsonHttpResponseHandler(activity,false){

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    String str=jsonObject.optString("datas", "");
                    if(str!=null && !str.equalsIgnoreCase("null"))
                    textView.setText(str);
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }
}
