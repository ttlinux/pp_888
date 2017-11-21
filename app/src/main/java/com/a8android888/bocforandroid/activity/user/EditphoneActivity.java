package com.a8android888.bocforandroid.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.webActivity;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;


/*
*修改手机和密码
*
 */

public class EditphoneActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private TextView title;
    private TextView contact;
    View toplayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editphone);

        initview();
    }

    private void initview() {
        toplayout=FindView(R.id.toplayout);
        Boolean showcolor=getIntent().getBooleanExtra(BundleTag.type,false);
        toplayout.setBackgroundColor(showcolor?getResources().getColor(R.color.usertopbagcolor):getResources().getColor(R.color.topbagcolor));
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        contact = FindView(R.id.contact);
        contact.setOnClickListener(this);


        RadioGroup radioGroup = FindView(R.id.radiogroup);
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.setText(radioButton.getText().toString().trim() + intent.getStringExtra(BundleTag.QQ));

        RadioButton radioButton2 = (RadioButton) radioGroup.getChildAt(2);
        radioButton2.setText(radioButton2.getText().toString().trim() + intent.getStringExtra(BundleTag.Weixin));

        RadioButton radioButton4 = (RadioButton) radioGroup.getChildAt(4);
        radioButton4.setText(radioButton4.getText().toString().trim() + "+" + intent.getStringExtra(BundleTag.Tel) + "  +" + intent.getStringExtra(BundleTag.Phonenum));

        RadioButton radioButton6 = (RadioButton) radioGroup.getChildAt(6);
        radioButton6.setText(radioButton6.getText().toString().trim()+ intent.getStringExtra(BundleTag.Email));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact:
                Intent intent4 = new Intent(this, webActivity.class);
                intent4.putExtra(BundleTag.URL, getIntent().getStringExtra(BundleTag.URL));
                intent4.putExtra(BundleTag.IntentTag, true);
                intent4.putExtra("title", this.getResources().getText(R.string.contactoffice));
                startActivity(intent4);
                break;
        }

    }
}
