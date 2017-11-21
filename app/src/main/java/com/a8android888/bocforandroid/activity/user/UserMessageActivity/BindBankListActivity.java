package com.a8android888.bocforandroid.activity.user.UserMessageActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.BindBankList_Adapter;
import com.a8android888.bocforandroid.Adapter.withdrawListAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.BankBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.Getlistheight;
import com.a8android888.bocforandroid.view.MyListView;
import com.a8android888.bocforandroid.view.WheelDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kankan.wheel.widget.WheelView;

/**
 * Created by Administrator on 2017/4/5. 银行卡的列表
 */
public class BindBankListActivity extends BaseActivity implements View.OnClickListener{
    TextView title,textview2,nobanklist;
    ImageView back;
    MyListView lsitview;
    BindBankList_Adapter adapter;
    TextView comit;
    Intent inn;
    ArrayList<BankBean> liststring=new ArrayList<BankBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawlist);
        initview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initview() {
        inn=getIntent();
        title = (TextView) FindView(R.id.title);
        title.setText("银行信息");
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        lsitview=(MyListView)FindView(R.id.lsitview);
        comit=(TextView)FindView(R.id.comit);
        comit.setOnClickListener(this);
        comit.setVisibility(View.GONE);
        textview2=(TextView)FindView(R.id.textview2);
        textview2.setVisibility(View.GONE);
        nobanklist=(TextView)FindView(R.id.nobanklist);
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
//        params.put("userName",username);
        Httputils.PostWithBaseUrl(Httputils.selectUserBankCodeInfo,params,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    JSONArray jsonArray=jsonObject.optJSONArray("datas");
                    if(jsonArray!=null && jsonArray.length()>0)
                    {
                        for (int i = 0; i <jsonArray.length(); i++) {
                            try {

                                BankBean bean=new BankBean();
                                bean.setImg(jsonArray.getJSONObject(i).optString("bigPicUrl",""));
                                bean.setSmallimg(jsonArray.getJSONObject(i).optString("smallPicUrl",""));
                                bean.setName(jsonArray.getJSONObject(i).optString("bankCnName", ""));
                                bean.setBankCode(jsonArray.getJSONObject(i).optString("bankCode", ""));
                                liststring.add(bean);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter = new BindBankList_Adapter(BindBankListActivity.this, liststring);
                        lsitview.setAdapter(adapter);
//                        if(liststring.isEmpty()){
//                            nobanklist.setVisibility(View.VISIBLE);
//                        }
//                        else{
//                            nobanklist.setVisibility(View.GONE);
//                        }
                        Getlistheight.setListViewHeightBasedOnChildren(lsitview);
                    }

                }

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                LogTools.e("jsonObject", s);
            }
        });
    }
}
