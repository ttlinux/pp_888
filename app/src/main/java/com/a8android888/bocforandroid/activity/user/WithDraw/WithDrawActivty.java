package com.a8android888.bocforandroid.activity.user.WithDraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.BankListAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MD5Util;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/3. 提现到银行卡
 */
public class WithDrawActivty extends BaseActivity implements View.OnClickListener {
    TextView bank, bankname, banknumber, bankaddress, title;
    TextView comit;
    ImageView back;
    EditText jine, mima;
    Intent intetn;
    String bankId;
    TextView notice;
    int minPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        initview();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initview() {
        intetn = getIntent();
        title = (TextView) FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.userwithdrawal));
        back = (ImageView) FindView(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        bankId = intetn.getStringExtra("bankId");
        notice = FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice, "withdraw", this);

        bank = (TextView) FindView(R.id.bank);
        bank.setText(this.getResources().getString(R.string.withdraw_bank) + "   " + intetn.getStringExtra("bankType"));

        bankname = (TextView) FindView(R.id.bankname);
        bankname.setText(this.getResources().getString(R.string.bankname) + "   " + intetn.getStringExtra("userRealName"));

        banknumber = (TextView) FindView(R.id.banknumber);
        banknumber.setText(this.getResources().getString(R.string.bank_account) + "   " + intetn.getStringExtra("bankCard"));

        bankaddress = (TextView) FindView(R.id.bankaddress);
        bankaddress.setText(this.getResources().getString(R.string.bank_address) + "   " + intetn.getStringExtra("bankAddress"));

        jine = (EditText) FindView(R.id.jine);
        mima = (EditText) FindView(R.id.mima);

        comit = (TextView) FindView(R.id.comit);
        comit.setOnClickListener(this);

        jine.setHint(intetn.getStringExtra("minMaxDes"));
        mima.setHint("请输入4位数字密码");
         minPay=Integer.valueOf(intetn.getStringExtra("minPay")) ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.comit:
                if (jine.getText().toString().trim() == null || jine.getText().toString().trim().length() < 1) {
                    ToastUtil.showMessage(this, "请输入提款金额");
                    return;
                }
                if (Integer.valueOf(jine.getText().toString().trim()) < minPay) {
                    ToastUtil.showMessage(this, jine.getHint().toString());
                    return;
                }
                if (mima.getText().toString().trim() == null || mima.getText().toString().trim().length() < 1) {
                    ToastUtil.showMessage(this, "请输入资金密码");
                    return;
                }

                comit.setEnabled(false);
                comitwith();
                break;
        }
    }

    private void comitwith() {
        String UserName = ((BaseApplication) this.getApplicationContext()).getBaseapplicationUsername();
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", UserName);
        requestParams.put("balance", jine.getText().toString().trim());
        requestParams.put("withdrawPwd", mima.getText().toString().trim());
        requestParams.put("bankCode", bankId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(UserName);
        stringBuilder.append("|");
        stringBuilder.append(jine.getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(mima.getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(bankId);
        requestParams.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));
        Httputils.PostWithBaseUrl(Httputils.Withdraw, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                ToastUtil.showMessage(WithDrawActivty.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    finish();
                }
            }
        });
    }
}
