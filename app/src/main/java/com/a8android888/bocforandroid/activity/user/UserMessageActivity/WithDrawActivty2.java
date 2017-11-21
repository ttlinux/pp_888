package com.a8android888.bocforandroid.activity.user.UserMessageActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/3. 银行卡信息
 */
public class WithDrawActivty2 extends BaseActivity implements View.OnClickListener{
    TextView title,bank;
    EditText banknumber,bankaddress,password;
    ImageView back;
    Intent intetn ;
    String bankId;
    Button jieban,edit;
    String bankTypestring = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw2);
        initview();
    }


    private void initview() {
        intetn=getIntent();
        title = (TextView) FindView(R.id.title);
        title.setText("银行卡信息");
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        bankId=intetn.getStringExtra("bankId");

        bank= (TextView) FindView(R.id.bank);
        bank.setText( intetn.getStringExtra("bankType"));
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent itent = new Intent(WithDrawActivty2.this, BindBankListActivity.class);
                startActivityForResult(itent, 112);
            }
        });

        banknumber= (EditText) FindView(R.id.banknumber);
        banknumber.setText( intetn.getStringExtra("bankCard"));

        bankaddress= (EditText) FindView(R.id.bankaddress);
        bankaddress.setText( intetn.getStringExtra("bankAddress"));

        password= (EditText) FindView(R.id.password);
        jieban=(Button)FindView(R.id.jieban);
        jieban.setOnClickListener(this);
        edit=(Button)FindView(R.id.edit);
        edit.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 112) {
            if (data != null) {
                String bankname = data.getStringExtra("test");
                bankTypestring = data.getStringExtra("bankType");
                bank.setText(bankname);
            }
        }
    }
    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.back:
        finish();
        break;
    case R.id.jieban:
        if (password.getText().toString().trim() == null || password.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, "请输入资金密码");
            return;
        }

        jieban.setEnabled(false);
        bankremove();
        break;
    case R.id.edit:
        if (bank.getText().toString().trim() == null || bank.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, "请选择银行");
            return;
        }
        if (banknumber.getText().toString().trim() == null || banknumber.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, "请输入银行卡号");
            return;
        }
        if (bankaddress.getText().toString().trim() == null || bankaddress.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, "请输入开户行地址");
            return;
        }
        if (password.getText().toString().trim() == null || password.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, "请输入资金密码");
            return;
        }
        edit.setEnabled(false);
        bankreupdate();
        break;
}
    }
 private  void bankremove(){
        RequestParams params=new RequestParams();
        params.put("userName",((BaseApplication)this.getApplication()).getBaseapplicationUsername());
        params.put("id",intetn.getStringExtra("bankId"));
       params.put("withdrawPwd",password.getText().toString().trim());
        Httputils.PostWithBaseUrl(Httputils.bankremove,params,new MyJsonHttpResponseHandler(this,true){
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                jieban.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                jieban.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(WithDrawActivty2.this,"解绑成功");
                    finish();
                }
            }
        });
 }
    private  void bankreupdate(){
        if(bankTypestring.equalsIgnoreCase(""))
            bankTypestring=bank.getText().toString().trim();
        RequestParams params=new RequestParams();
        params.put("userName",((BaseApplication)this.getApplication()).getBaseapplicationUsername());
        params.put("bankType",bankTypestring);
        params.put("bankCard",banknumber.getText().toString().trim());
        params.put("bankAddress",bankaddress.getText().toString().trim());
        params.put("withdrawPwd",password.getText().toString().trim());
        params.put("id",intetn.getStringExtra("bankId"));
        Httputils.PostWithBaseUrl(Httputils.bankreupdate,params,new MyJsonHttpResponseHandler(this,true){
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                edit.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                edit.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(WithDrawActivty2.this, "修改成功");
                    finish();
                }
                else
                    ToastUtil.showMessage(WithDrawActivty2.this, jsonObject.optString("msg"));

            }
        });
    }
}
