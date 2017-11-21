package com.a8android888.bocforandroid.activity.user.Change;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Change_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.PlatformBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.
 */
public class ChangeActivity extends BaseActivity implements View.OnClickListener{
    TextView title;
    ImageView back;
    ListView listView;
   Change_Adapter adapter;
    ArrayList<PlatformBean> platformBeans = new ArrayList<PlatformBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);
        initview();

    }
    private void initview(){
        title=(TextView)FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.changetitle));
        listView=(ListView)FindView(R.id.listview);
        GetPlatform();
    }

    @Override
    public void onClick(View v) {
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(platformBeans.size()>0)
        adapter.notifyData(platformBeans);
    }
    private void GetPlatform()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("userName",((BaseApplication)this.getApplicationContext()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.Platform, requestParams, new MyJsonHttpResponseHandler(this,true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                LogTools.e("ddddsfafadsfas",jsonObject.toString());
                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                if (jsonArray != null && jsonArray.length() > 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsobject = jsonArray.optJSONObject(i);
                        PlatformBean bean = new PlatformBean();
                        bean.setFlat(jsobject.optString("flat", ""));
//                        bean.setFlatDes(jsobject.optString("flatDes", ""));
                        bean.setFlatName(jsobject.optString("flatName", ""));
//                        bean.setFlatUrl(jsobject.optString("flatUrl", ""));
                        bean.setBigPic(jsobject.optString("bigPic", ""));
                        bean.setSmallPic(jsobject.optString("smallPic", ""));
                        bean.setState(false);
                        bean.setJine("余额");
                        platformBeans.add(bean);
                    }
                    if(adapter==null) {
                        adapter = new Change_Adapter(ChangeActivity.this, platformBeans);
                        listView.setAdapter(adapter);
                    }
                    else{
                        adapter.notifyData(platformBeans);
                    }
                }
            }
        });
    }

//    private void platformbalance()
//    {
//
//        Httputils.GetWithBaseUrl(Httputils.platformbalance,);
//    }
}
