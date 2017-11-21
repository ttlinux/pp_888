package com.a8android888.bocforandroid.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.SafekList_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.SafeBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/5. 安全信息
 */
public class SafeActivity extends BaseActivity implements View.OnClickListener{
    TextView title;
    ImageView back;
    ListView lsitview;
    SafekList_Adapter adapter;
    TextView comit;
    Intent inn;
    ArrayList<SafeBean> liststring=new ArrayList<SafeBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safelist);
        initview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initview() {
        inn=getIntent();
        title = (TextView) FindView(R.id.title);
        title.setText("安全信息");
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        lsitview=(ListView)FindView(R.id.lsitview);
        comit=(TextView)FindView(R.id.comit);
        comit.setOnClickListener(this);
        comit.setVisibility(View.GONE);
        Getdata();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:

                break;
        }
    }
    private void Getdata()
    {
        RequestParams params=new RequestParams();
        params.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.selectUserLoginLog, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                    if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                        JSONArray jsonArray = jsonObject.optJSONArray("datas");
                        if (jsonArray != null && jsonArray.length() > 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                                SafeBean bean = new SafeBean();
                                bean.setCreateTime(jsonObject1.optString("createTime", ""));
                                bean.setIpPosition(jsonObject1.optString("ipPosition", ""));
                                bean.setLogAddress(jsonObject1.optString("logAddress", ""));
                                bean.setLoginDevice(jsonObject1.optString("loginDevice", ""));
                                liststring.add(bean);
                                LogTools.e("jsonObject", liststring.get(i).getIpPosition());
                            }
                        }
                    }
                    adapter = new SafekList_Adapter(SafeActivity.this, liststring);
                    lsitview.setAdapter(adapter);
                }
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                    LogTools.e("jsonObject", s);
            }
        });
    }
}
