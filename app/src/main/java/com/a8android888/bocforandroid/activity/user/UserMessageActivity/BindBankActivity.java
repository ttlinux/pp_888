package com.a8android888.bocforandroid.activity.user.UserMessageActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.WithDraw.WithDrawListActivty;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.util.verifyEditext;
import com.a8android888.bocforandroid.view.WheelDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kankan.wheel.widget.WheelView;

/**
 * Created by Administrator on 2017/4/5.
 */
public class BindBankActivity extends BaseActivity {

    LinearLayout mainview;
    TextView banktype;
    ArrayList<EditText> editTexts = new ArrayList<EditText>();
    static String banklist[];
    String bankTypestring = "";
    Intent inn;
    View confirmmodify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindbank);

        TextView textView = FindView(R.id.title);
        textView.setText(getString(R.string.Bankband));
        inn = getIntent();
//        banklist=getResources().getStringArray(R.array.banklist);
        mainview = FindView(R.id.mainview);
        LogTools.e("saafsdf123", inn.getStringExtra("name").toString());
        for (int i = 1; i < mainview.getChildCount(); i++) {
            if (i % 2 == 0) {
                LinearLayout lin = (LinearLayout) mainview.getChildAt(i);
                if (lin.getChildAt(2) instanceof EditText)
                    editTexts.add((EditText) lin.getChildAt(2));
            }
        }
        editTexts.get(1).setText(inn.getStringExtra("name").toString().trim() + "");
        editTexts.get(1).setFocusable(false);
        banktype = FindView(R.id.banktype);
        banktype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                Getdata();
                Intent itent = new Intent(BindBankActivity.this, BindBankListActivity.class);
                startActivityForResult(itent, 112);
            }
        });

        confirmmodify = FindView(R.id.confirmmodify);
        confirmmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindBank(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 112) {
            if (data != null) {
                String bankname = data.getStringExtra("test");
                bankTypestring = data.getStringExtra("bankType");
                banktype.setText(bankname);
            }
        }
    }

    private void BindBank(View v) {
        if(!new verifyEditext().BankCard(this,banktype.getText().toString(),
                editTexts.get(0).getText().toString(),
                editTexts.get(2).getText().toString(),
                editTexts.get(3).getText().toString()))
        {
            return;
        }

        SharedPreferences sharedPreferences = ((BaseApplication) getApplication()).getSharedPreferences();
        String username = sharedPreferences.getString(BundleTag.UserInfo, "");
        RequestParams params = new RequestParams();

        try {
            if (username == null || username.toString().equalsIgnoreCase("")) return;
            JSONObject jsonObject = new JSONObject(username);
            params.put("userName", jsonObject.optString("userName", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.put("bankType", bankTypestring);
        params.put("bankCard", editTexts.get(0).getText().toString().trim());
//        params.put("userRealName", editTexts.get(1).getText().toString());
        params.put("bankAddress", editTexts.get(2).getText().toString().trim());
        params.put("withdrawPwd", editTexts.get(3).getText().toString().trim());
        v.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.BindBank, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("usss", jsonObject.toString());
                confirmmodify.setEnabled(true);
                ToastUtil.showMessage(BindBankActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    setResult(111);
                    finish();
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                confirmmodify.setEnabled(true);
            }
        });
    }

    private void Getdata() {
        if (banklist != null && banklist.length > 0) {
            banktype.setText(banklist[0]);
            ShowWheel();
            return;
        }

        BaseApplication baseApplication = (BaseApplication) getApplication();
        String username = baseApplication.getBaseapplicationUsername();
        RequestParams params = new RequestParams();
//        params.put("userName",username);
        Httputils.PostWithBaseUrl(Httputils.selectUserBankCodeInfo, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONArray jsonArray = jsonObject.optJSONObject("datas").optJSONArray("list");
                    if (jsonArray != null && jsonArray.length() > 0) {
                        banklist = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                banklist[i] = jsonArray.getJSONObject(i).optString("codeShowName", "");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (banklist.length > 0)
                            banktype.setText(banklist[0]);
                        ShowWheel();
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

    public void ShowWheel() {
        WheelDialog dialog = new WheelDialog(BindBankActivity.this);
        dialog.setListener(new WheelDialog.OnChangeListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                LogTools.e("oldValue", oldValue + " " + newValue);
                banktype.setText(banklist[newValue]);
            }
        });
        dialog.setStrs(banklist);
        dialog.show();
    }


}
