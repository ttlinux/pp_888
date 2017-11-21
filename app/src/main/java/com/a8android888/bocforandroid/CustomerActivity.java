package com.a8android888.bocforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;


/*
*客服中心
*
 */

public class CustomerActivity extends Activity implements View.OnClickListener {
    private ImageView back;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customerlayout);

        initview();
    }

    private void initview() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        title.setText(this.getResources().getText(R.string.customer));
//        getuserinfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }

    }

    /**
     * 获取会员信息
     */
    private void getuserinfo() {
        LogTools.e("getuserinfo", "getuserinfo");
        RequestParams params = new RequestParams();
        Httputils.PostWithBaseUrl(Httputils.UserInfo + ((BaseApplication) this.getApplication()).getBaseapplicationUsername(), params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("dddddd", jsonObject.toString());
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });

    }
}
