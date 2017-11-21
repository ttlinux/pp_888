package com.a8android888.bocforandroid.activity.user;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/11/17.
 */
public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener{

    RelativeLayout toplayout;
    Drawable forbidden,normal;
    TextView getauthcode,comit;
    EditText accountval,phonenumber,authcode;
    Handler handler=new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what)
            {
                case 0:
                    second--;
                    if(second>0)
                    {
                        handler.sendEmptyMessageDelayed(0,1000);
                        getauthcode.setText(String.format(tempstr, String.valueOf(second)));
                    }
                    else
                    {
                        handler.sendEmptyMessageDelayed(1,1000);
                    }

                    break;
                case 1:
                    getauthcode.setText("获取验证码");
                    getauthcode.setBackground(normal);
                    break;
            }
        }
    };
    static int second=0;
    int count=60;
    String tempstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        tempstr=getResources().getString(R.string.authstr);
        normal=getResources().getDrawable(R.drawable.rangle_blue_coner);
        forbidden=getResources().getDrawable(R.drawable.rangle_gray_coner);

        toplayout=FindView(R.id.toplayout);
        getauthcode=FindView(R.id.getauthcode);

        if(second>0)
        {
            getauthcode.setText(String.format(tempstr, String.valueOf(second)));
            getauthcode.setBackground(forbidden);
            handler.sendEmptyMessageDelayed(0,1000);
        }
        else
        {
            getauthcode.setText("获取验证码");
            getauthcode.setBackground(normal);
            getauthcode.setEnabled(true);
        }

        getauthcode.setOnClickListener(this);
        comit=FindView(R.id.comit);
        comit.setOnClickListener(this);
        accountval=FindView(R.id.accountval);
        phonenumber=FindView(R.id.phonenumber);
        authcode=FindView(R.id.authcode);

        toplayout.setBackgroundColor( getResources().getColor(R.color.usertopbagcolor));
        TextView textView=FindView(R.id.title);
        textView.setText("忘记密码");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.comit:
                commit();
                break;
            case R.id.getauthcode:
                getauthcode();
                break;
        }
    }

    public void getauthcode()
    {
        getauthcode.setEnabled(false);
        second=count;
        getauthcode.setText(String.format(tempstr, String.valueOf(second)));
        getauthcode.setBackground(forbidden);
        handler.sendEmptyMessageDelayed(0,1000);

    }

   public void commit()
   {
       if(accountval.getText().toString().length()==0)
       {
           ToastUtil.showMessage(this,"请输入账号");
           return;
       }
       if(phonenumber.getText().toString().length()==0)
       {
           ToastUtil.showMessage(this,"请输入手机号码");
           return;
       }
       if(authcode.getText().toString().length()==0)
       {
           ToastUtil.showMessage(this,"请输入短信验证码");
           return;
       }
       comit.setEnabled(false);
       RequestParams requestParams=new RequestParams();
       requestParams.put("userName",accountval.getText().toString());
       requestParams.put("phone",phonenumber.getText().toString());
       requestParams.put("code",authcode.getText().toString());
       Httputils.PostWithBaseUrl(Httputils.forgotpassword,requestParams,new MyJsonHttpResponseHandler(this,true)
       {
           @Override
           public void onSuccessOfMe(JSONObject jsonObject) {
               super.onSuccessOfMe(jsonObject);
               comit.setEnabled(true);
               ToastUtil.showMessage(ForgotPasswordActivity.this, jsonObject.optString("msg", ""));
               if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
               {
                   Intent intent=new Intent(ForgotPasswordActivity.this,ForgotPasswordActivityStep2.class);
                   intent.putExtra("Username",accountval.getText().toString());
                   intent.putExtra("phone",phonenumber.getText().toString());
                   startActivity(intent);
                   finish();
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
