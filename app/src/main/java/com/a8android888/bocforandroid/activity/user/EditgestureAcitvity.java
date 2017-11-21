package com.a8android888.bocforandroid.activity.user;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MD5Util;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.GestureLockViewGroup;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
//设置手势密码

/**
 * Created by Administrator on 2017/5/15.//修改手势密码
 */
public class EditgestureAcitvity extends Activity implements View.OnClickListener {
    TextView title, tv_state;
    ImageView back;
    StringBuilder gesturestatepass = new StringBuilder();
    String oldpassword;
    GestureLockViewGroup mGestureLockViewGroup;
    int passwprd[] = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setgesture);
        initview();
    }

    private void initview() {
        title = (TextView) findViewById(R.id.title);
        title.setText("修改手势密码");
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_state.setText("请绘制原手势密码");
        tv_state.setTextColor(Color.BLACK);
        back = (ImageView) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        initGesture();
    }

    private void initGesture() {
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
//        mGestureLockViewGroup.setAnswer(new int[]{1, 2, 3, 5, 8, 9, 6});
        mGestureLockViewGroup.setIsFrist(true);
        mGestureLockViewGroup.setUnMatchExceedBoundary(3);
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
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            gesturestatepass.delete(0, gesturestatepass.length());
                            LogTools.e("设置手势密码4" + oldpassword, gesturestatepass + "");
                        }
//                        if (event.getAction() == MotionEvent.ACTION_UP) {
//                            if (gesturestatepass.length() < 4) {
//                                tv_state.setText("最少链接4个点，请重新输入");
//                                tv_state.setTextColor(getResources().getColor(R.color.red));
//                                mGestureLockViewGroup.resetAndRefresh();
//                            }
//                        }

                    }

                    @Override
                    public void oncheck(int event) {
                        LogTools.e("设置手势密码event", event + "");
                        if (event == 2) {
                            oldpassword = gesturestatepass.toString();
                            LogTools.e("设置手势密码3" + oldpassword, gesturestatepass + "");
                            tv_state.setText("绘制新的手势密码");
                            tv_state.setTextColor(Color.BLACK);
                            gesturestate();
                            mGestureLockViewGroup.resetAndRefresh();
                        }
                        if (event == 1) {
                            tv_state.setText("再次绘制新的手势密码");
                            tv_state.setTextColor(getResources().getColor(R.color.red));
                            mGestureLockViewGroup.resetAndRefresh();
                        }
                    }

                    @Override
                    public void onUnmatchedExceedBoundary() {

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
                            LogTools.e("设置手势密码0", matched + "");
                            editgesturestate();
                            mGestureLockViewGroup.resetAndRefresh();
                        }
                        LogTools.e("设置手势密码1", matched + "");
//                        Toast.makeText(EditgestureAcitvity.this, matched + "",
//                                Toast.LENGTH_SHORT).show();
                        gesturestatepass.delete(0, gesturestatepass.length());
                    }

                    @Override
                    public void onBlockSelected(int cId) {
                        gesturestatepass.append(String.valueOf(cId));
                        LogTools.e("设置手势密码2", gesturestatepass + "");
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    //设置新的手势密码
    private void editgesturestate() {
        RequestParams params = new RequestParams();

        params.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
        params.put("newPwd", gesturestatepass.toString());
        params.put("oldPwd", oldpassword.toString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((BaseApplication) this.getApplication()).getBaseapplicationUsername()).append("|");
        stringBuilder.append(gesturestatepass.toString()).append("|");
        stringBuilder.append(oldpassword.toString());
        params.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));
        Httputils.PostWithBaseUrl(Httputils.editgesturespassword, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("设置手势密码", jsonObject.toString());
                Toast.makeText(EditgestureAcitvity.this, jsonObject.optString("msg", "") + "",
                        Toast.LENGTH_SHORT).show();
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    finish();
                }
                if(jsonObject.optString("errorCode", "").equalsIgnoreCase("200014"))
                {
                    finish();
                }

            }
        });
    }

    //
    private void gesturestate() {
        RequestParams params = new RequestParams();
        params.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
        params.put("gesturePwd", gesturestatepass.toString());
        Httputils.PostWithBaseUrl(Httputils.checkgesturespassword, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("aaadddd123", jsonObject.toString());
                JSONObject datas = jsonObject.optJSONObject("datas");
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    tv_state.setText("绘制新的手势密码");
                    tv_state.setTextColor(Color.BLACK);
                    mGestureLockViewGroup.setIsFrist(true);
                } else {
                    mGestureLockViewGroup.setUnMatchExceedBoundary(3);
                    mGestureLockViewGroup.setIsFrist(true);
                    tv_state.setText("请绘制原手势密码");
                    tv_state.setTextColor(Color.BLACK);
                    Toast.makeText(EditgestureAcitvity.this, jsonObject.optString("msg", "") + "",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
