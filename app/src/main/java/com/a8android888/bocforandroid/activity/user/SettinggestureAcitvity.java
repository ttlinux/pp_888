package com.a8android888.bocforandroid.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.MainActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.aboutwebActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.GestureLockViewGroup;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
//设置手势密码

/**
 * Created by Administrator on 2017/5/15.//设置手势密码
 */
public class SettinggestureAcitvity extends Activity implements View.OnClickListener {
    TextView title, tv_state;
    ImageView back;
    StringBuilder gesturestatepass = new StringBuilder();
    GestureLockViewGroup mGestureLockViewGroup;
    int passwprd[] = {};
     boolean setonandoff=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setgesture);
        initview();
    }

    private void initview() {
        title = (TextView) findViewById(R.id.title);
        title.setText("设置手势密码");
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_state.setText("绘制新的手势密码");
        tv_state.setTextColor(Color.BLACK);
        back = (ImageView) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        initGesture();
    }

    private void initGesture() {
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
        mGestureLockViewGroup.setIsFrist(true);
//        mGestureLockViewGroup.setAnswer(new int[]{1, 2, 3, 5, 8, 9, 6});
        mGestureLockViewGroup.setUnMatchExceedBoundary(2);
        mGestureLockViewGroup.setLimitListener(4, new GestureLockViewGroup.onLimitListener() {
            @Override
            public void onLimit() {
                tv_state.setText("最少链接4个点，请重新输入");
                tv_state.setTextColor(getResources().getColor(R.color.red));
            }
        });
        mGestureLockViewGroup
                .setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {

                    @Override
                    public void onTouchEvent(MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN)
                            gesturestatepass.delete(0, gesturestatepass.length());
                    }

                    @Override
                    public void onUnmatchedExceedBoundary() {
                        mGestureLockViewGroup.setUnMatchExceedBoundary(1);
                    }

                    @Override
                    public void oncheck(int event) {
                        if (event == 1) {
                            tv_state.setText("再次绘制新的手势密码");
                            tv_state.setTextColor(Color.BLACK);
                            mGestureLockViewGroup.resetAndRefresh();
                        }
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {
                        if (!matched) {
                            mGestureLockViewGroup.setUnMatchExceedBoundary(1);
                            tv_state.setText("与上次绘制的不一致，请重新绘制");
                            tv_state.setTextColor(getResources().getColor(R.color.red));
                            tv_state.invalidate();
                            mGestureLockViewGroup.resetAndRefresh();
                        } else {
                            tv_state.setText("设置成功");
                            tv_state.setTextColor(getResources().getColor(R.color.red));
                            tv_state.invalidate();
                            editgesturestate();
                            mGestureLockViewGroup.resetAndRefresh();
                        }
//                        Toast.makeText(SettinggestureAcitvity.this, matched + "",
//                                Toast.LENGTH_SHORT).show();
                        gesturestatepass.delete(0, gesturestatepass.length());
                    }

                    @Override
                    public void onBlockSelected(int cId) {
                        gesturestatepass.append(String.valueOf(cId));
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("state", setonandoff);
                setResult(1111, intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("state", setonandoff);
            setResult(1111, intent);
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    //设置手势密码
    private void editgesturestate() {
        RequestParams params = new RequestParams();

        params.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
        params.put("gesturePwd", gesturestatepass.toString());
        Httputils.PostWithBaseUrl(Httputils.setgestures, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("设置手势密码", jsonObject.toString());
                setonandoff=false;
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    SharedPreferences sharedPreferences = ((BaseApplication) getApplication()).getSharedPreferences();
                    sharedPreferences.edit()
                            .putBoolean(BundleTag.setgesturestate, true).commit();
                    ToastUtil.showMessage(SettinggestureAcitvity.this, jsonObject.optString("msg", ""));
                    setonandoff=true;
                    Intent intent=new Intent();
                    intent.putExtra("state", setonandoff);
                    setResult(1111,intent);
                    finish();
                }
            }
        });
    }
    //
//    private void gesturestate()
//    {
//        RequestParams params=new RequestParams();
//        params.put("userName",name.getText().toString().trim());
//        Httputils.PostWithBaseUrl(Httputils.gesturestate,params,new MyJsonHttpResponseHandler(this,true){
//            @Override
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//            }
//
//            @Override
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//                LogTools.e("aaadddd123", jsonObject.toString());
//                JSONObject datas=jsonObject.optJSONObject("datas");
//                SharedPreferences sharedPreferences=((BaseApplication)getApplication()).getSharedPreferences();
//                sharedPreferences.edit()
//                        .putBoolean(BundleTag.setgesturestate, datas.optJSONObject("gesture").optBoolean("isSet"))
//                        .putString(BundleTag.gesturestate,datas.optJSONObject("gesture").optString("state"))
//                        .commit();
//                if(datas.optJSONObject("gesture").optBoolean("isSet")==false){
//                    ToastUtil.showMessage(LoginActivity.this,"你没有设置手势密码，请先用字符密码登录");
//                }
//            }
//        });
//    }
}
