package com.a8android888.bocforandroid.activity.user.WithDraw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.BankListAdapter;
import com.a8android888.bocforandroid.Adapter.withdrawListAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.UserMessageActivity.BindBankActivity;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.Getlistheight;
import com.a8android888.bocforandroid.view.MyListView;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/3. 提现到银行卡之前的选择银行卡列表
 */
public class WithDrawListActivty extends BaseActivity implements View.OnClickListener{
    TextView title;
    ImageView back;
    MyListView lsitview;
    withdrawListAdapter adapter;
    TextView comit;
    Intent inn;
    TextView textview2,nobanklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawlist);
        initview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==111)
        {
            comitwith();
        }
    }

    private void initview() {
        inn=getIntent();
        title = (TextView) FindView(R.id.title);
        title.setText(inn.getStringExtra("title"));
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        lsitview=(MyListView)FindView(R.id.lsitview);
        comit=(TextView)FindView(R.id.comit);
        comit.setOnClickListener(this);
        textview2=FindView(R.id.textview2);
//        textview2.setVisibility(View.GONE);
        nobanklist=FindView(R.id.nobanklist);
        HttpforNoticeinbottom.GetMainPageData(textview2, "bank", this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        comitwith();
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.back:
        finish();
        break;
    case R.id.comit:
        Intent itent =new Intent(WithDrawListActivty.this, BindBankActivity.class);
        itent.putExtra("name", inn.getStringExtra("name"));
        startActivityForResult(itent, 111);
//        startActivityForResult(new Intent(WithDrawListActivty.this, BindBankActivity.class),110);
        break;
}
    }
    private  void comitwith(){
      String   UserName = ((BaseApplication)this.getApplicationContext()).getBaseapplicationUsername();
       RequestParams requestParams=new RequestParams();
            requestParams.put("userName",UserName);
            Httputils.PostWithBaseUrl(Httputils.SelectBankList, requestParams, new MyJsonHttpResponseHandler(this,true) {
                @Override
                public void onFailureOfMe(Throwable throwable, String s) {
                    super.onFailureOfMe(throwable, s);
                }

                @Override
                public void onSuccessOfMe(JSONObject jsonObject) {
                    super.onSuccessOfMe(jsonObject);
                    LogTools.e("jsonsjosn",jsonObject.toString());
                    if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                        return;
                    JSONObject temp = jsonObject.optJSONObject("datas");
                    if(temp.optString("addFlag","").equalsIgnoreCase("true")){
                        comit.setVisibility(View.VISIBLE);
                    }
                    else {
                        comit.setVisibility(View.GONE); 
                    }
                    JSONArray jsonArray = temp.optJSONArray("list");
                    if(jsonArray.length()>0) {
                        ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsobject = jsonArray.optJSONObject(i);
                            jsonObjects.add(jsobject);
                            LogTools.e("dddd",jsobject.toString()+"");
                        }
                        adapter = new withdrawListAdapter(WithDrawListActivty.this, jsonObjects,inn.getStringExtra("type"));
                        lsitview.setAdapter(adapter);
                        Getlistheight.setListViewHeightBasedOnChildren(lsitview);
                        nobanklist.setVisibility(View.GONE);
                    }
                    else{
                        nobanklist.setVisibility(View.VISIBLE);
                    }
                }
            });
    }
}
