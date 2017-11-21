package com.a8android888.bocforandroid.activity.user.Change;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Change_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.PlatformBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.UserMessageActivity.BindBankActivity;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MD5Util;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1.
 */
public class ChangeintoActivity extends BaseActivity implements View.OnClickListener{
    TextView title,changeintoout,zhuan,zhuan2;
    ImageView back,imageView;
    Button comit;
    Intent intent;
    String httpurl;
    EditText introduce;
//    public PublicDialog publicDialog;
String msg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeinto);
        initview();
    }
    private void initview(){
        intent=getIntent();
        back=(ImageView)FindView(R.id.back);
        imageView=(ImageView)FindView(R.id.imageView);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title=(TextView)FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.changetitle));
        changeintoout=(TextView)FindView(R.id.changeintoout);
        introduce=(EditText)FindView(R.id.introduce);
        zhuan=(TextView)FindView(R.id.zhuan);
        zhuan2=(TextView)FindView(R.id.zhuan2);
        if(intent.getStringExtra("type").equalsIgnoreCase("1")){
            zhuan.setText("转入金额");
            msg= HttpforNoticeinbottom.Gettext("最低转入金额需≥%s元", zhuan2, this);
            LogTools.e("msgmsg",msg);
        changeintoout.setText("由总账户转入到" + intent.getStringExtra("title") + "平台");
            httpurl=Httputils.deposit;
        }
        if(intent.getStringExtra("type").equalsIgnoreCase("2")){
            httpurl=Httputils.withdraw;
            zhuan.setText("转出金额");
            msg=  HttpforNoticeinbottom.Gettext("最低转出金额需≥%s元", zhuan2, this);
            LogTools.e("msgmsg",msg);
//            HttpforNoticeinbottom.GetMainPageData(zhuan2, "edu", this);
            changeintoout.setText("由"+intent.getStringExtra("title")+"平台"+"转出到总账户");
        }
        comit=(Button)FindView(R.id.comit);
        comit.setOnClickListener(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if(event.getAction()==KeyEvent.KEYCODE_BACK)
        {
            setResult(888);
            finish();
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                setResult(888);
                finish();
                break;
            case R.id.comit:
                if(introduce.getText().toString().trim().length()<1)
                {
                    ToastUtil.showMessage(this, "请填写金额");
               return;
                }
                else if(Integer.valueOf(introduce.getText().toString().trim())<10){
                    LogTools.e("msgmsg",msg);
                ToastUtil.showMessage(this,"最低金额需≥"+msg+"元");
                    return;
            }
               else {
                GetPlatform();
                }
                break;
        }
    }
    private void GetPlatform()
    {
//        if(publicDialog==null)
//        {
//            publicDialog=new PublicDialog(this);
//        }
//        publicDialog.show();

        Animation circle_anim = AnimationUtils.loadAnimation(this, R.anim.anim_round_rotate);
        LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);
        if (circle_anim != null) {
            imageView.startAnimation(circle_anim);  //开始动画
        }
        comit.setEnabled(false);
        RequestParams requestParams=new RequestParams();
        requestParams.put("userName", ((BaseApplication)this.getApplication()).getBaseapplicationUsername());
        requestParams.put("flat", intent.getStringExtra("flat"));
        requestParams.put("money", introduce.getText().toString().trim());

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(((BaseApplication)this.getApplication()).getBaseapplicationUsername());
        stringBuilder.append("|");
        stringBuilder.append(intent.getStringExtra("flat"));
        stringBuilder.append("|");
        stringBuilder.append(introduce.getText().toString().trim());
        requestParams.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));

        Httputils.PostWithBaseUrl(httpurl, requestParams, new MyJsonHttpResponseHandler(this,true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                if ( !isDestroyed()) {
                    imageView.clearAnimation();
                    comit.setEnabled(true);
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if( !isDestroyed())
                {
                    imageView.clearAnimation();
                    comit.setEnabled(true);
                }
                LogTools.e("ddddsfafadsfas", jsonObject.toString());
                ToastUtil.showMessage(ChangeintoActivity.this, jsonObject.optString("msg"));
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                            String index = intent.getStringExtra("index");
                    Change_Adapter.platformBeans.get(Integer.valueOf(index)).setState(true);
                     setResult(888);
                    finish();
                    imageView.clearAnimation();
                }

            }
        });
    }

}
