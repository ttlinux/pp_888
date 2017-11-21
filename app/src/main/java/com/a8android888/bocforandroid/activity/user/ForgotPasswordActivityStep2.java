package com.a8android888.bocforandroid.activity.user;

import android.os.Bundle;
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
public class ForgotPasswordActivityStep2 extends BaseActivity implements View.OnClickListener{

    RelativeLayout toplayout;
    EditText newpassword,confirmpassword;
    TextView comit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_step_2);
        toplayout=FindView(R.id.toplayout);
        newpassword=FindView(R.id.newpassword);
        comit=FindView(R.id.comit);
        comit.setOnClickListener(this);
        confirmpassword=FindView(R.id.confirmpassword);
        toplayout.setBackgroundColor( getResources().getColor(R.color.usertopbagcolor));
        TextView textView=FindView(R.id.title);
        textView.setText("忘记密码");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.comit:
                resetPassword();
                break;
        }
    }

    private void resetPassword()
    {

        if(newpassword.getText().toString().length()==0)
        {
            ToastUtil.showMessage(this, "请输入新密码");
            return;
        }
        if(confirmpassword.getText().toString().length()==0)
        {
            ToastUtil.showMessage(this,"请输入确认密码");
            return;
        }
        if(confirmpassword.getText().toString().equalsIgnoreCase(newpassword.getText().toString()))
        {
            ToastUtil.showMessage(this,"两次密码输入不一致");
            return;
        }

        comit.setEnabled(false);
        RequestParams requestParams=new RequestParams();
        requestParams.put("account",getIntent().getStringExtra("Username"));
        requestParams.put("phone",getIntent().getStringExtra("phone"));
        Httputils.PostWithBaseUrl(Httputils.resetpassword,requestParams,new MyJsonHttpResponseHandler(this,true)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
            }
        });
    }
}
