package com.a8android888.bocforandroid.activity.user.UserMessageActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.BankListAdapter;
import com.a8android888.bocforandroid.Adapter.Change_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.CustomerActivity;
import com.a8android888.bocforandroid.activity.user.EditphoneActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.MyListView;
import com.a8android888.bocforandroid.view.MyStyleView;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/3.会员信息
 */
public class Userinformation extends BaseActivity implements View.OnClickListener{
    TextView title,username,name,money,email,qqnumber,phonenumber,dengji;
    View comit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userinformation);
        initview();

    }
    private void initview(){
        comit=FindView(R.id.comit);
        comit.setOnClickListener(this);
        title=(TextView)FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.memberinfo));
        username=(TextView)FindView(R.id.username);
        name=(TextView)FindView(R.id.name);
        money=(TextView)FindView(R.id.money);
        email=(TextView)FindView(R.id.email);
        qqnumber=(TextView)FindView(R.id.qqnumber);
        phonenumber=(TextView)FindView(R.id.phonenumber);
        phonenumber.setOnClickListener(this);
        dengji=(TextView)FindView(R.id.dengji);
//        lasttime=(TextView)FindView(R.id.lasttime);
//        lastip=(TextView)FindView(R.id.lastip);
//        listView=(MyListView)FindView(R.id.listview);

        getuserinfo();
    }
    /**
     * 获取会员信息
     */
    private void getuserinfo() {

        SharedPreferences sharedPreferences=((BaseApplication)getApplication()).getSharedPreferences();
        String UserInfo =sharedPreferences.getString(BundleTag.UserInfo,"");
        if(UserInfo!=null && !UserInfo.equalsIgnoreCase(""))
        {
            try {
                JSONObject UserInfoobj=new JSONObject(UserInfo);
                LogTools.e("UserInfoobj", UserInfoobj.toString());
                username.setText(UserInfoobj.optString("userName", ""));
                money.setText(UserInfoobj.optString("userMoney",""));
                name.setText(UserInfoobj.optString("userRealName", ""));
                email.setText(UserInfoobj.optString("userMail", ""));
                qqnumber.setText(UserInfoobj.optString("userQq", ""));
                phonenumber.setText(UserInfoobj.optString("userMobile", ""));
//               JSONObject level= new JSONObject(sharedPreferences.getString(BundleTag.level, ""));
                if(UserInfoobj.optJSONObject("typeDetail")!=null) {
                    dengji.setText(UserInfoobj.optJSONObject("typeDetail").optString("typeLevel", ""));
                }
//                lasttime.setText(UserInfoobj.optString("userLastLoginTime"));
//                lastip.setText(UserInfoobj.optString("userLastLoginIp"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    public boolean isEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        Pattern p = Pattern.compile(str);

        Matcher m = p.matcher(email);

        return m.matches();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comit:
                if(qqnumber.getText().toString().trim().length()<1&&email.getText().toString().trim().length()<1){
                   ToastUtil.showMessage(this,"请确认您要修改的邮箱或者QQ号是否输入");
                    return;
                }
                if(isEmail(email.getText().toString().trim())) {
                    comit.setEnabled(false);
                    edit();
                }
                else {
                    ToastUtil.showMessage(this,"电子邮箱不正确");
                }
                break;
            case R.id.phonenumber:
                SharedPreferences sharedPreferences=((BaseApplication)getApplication()).getSharedPreferences();
                Intent  intent = new Intent(this, EditphoneActivity.class);
                intent.putExtra(BundleTag.Phonenum, sharedPreferences.getString(BundleTag.Phonenum, ""));
                intent.putExtra(BundleTag.QQ, sharedPreferences.getString(BundleTag.QQ, ""));
                intent.putExtra(BundleTag.Email, sharedPreferences.getString(BundleTag.Email, ""));
                intent.putExtra(BundleTag.Weixin, sharedPreferences.getString(BundleTag.Weixin, ""));
                intent.putExtra(BundleTag.Tel, sharedPreferences.getString(BundleTag.Tel, ""));
                intent.putExtra(BundleTag.URL, sharedPreferences.getString(BundleTag.URL, ""));
                intent.putExtra(BundleTag.type,true);
                intent.putExtra("title","修改绑定手机");
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void edit()
    {

        RequestParams params=new RequestParams();
        params.put("userName", ((BaseApplication) Userinformation.this.getApplicationContext()).getBaseapplicationUsername());
       params.put("userQq",qqnumber.getText().toString().trim());
        params.put("userMail",email.getText().toString().trim());
        Httputils.PostWithBaseUrl(Httputils.changeUserBasicInfo, params, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
            ToastUtil.showMessage(context,jsonObject.optString("msg"));
                if (jsonObject.optString("errorCode","").equalsIgnoreCase("000000")){
                    finish();;
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
            }
        });
    }
}
