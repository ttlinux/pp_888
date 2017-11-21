package com.a8android888.bocforandroid.activity.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Fragment.UserFragment;
import com.a8android888.bocforandroid.MainActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.aboutwebActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.FileUtils;
import com.a8android888.bocforandroid.util.GestureDialog;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.a8android888.bocforandroid.view.ScollAlertdialog;
import com.a8android888.bocforandroid.view.UPdataNoticeDialog;
import com.a8android888.bocforandroid.webActivity;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/5/15.
 */
public class SettingAcitvity extends BaseActivity implements View.OnClickListener {
    TextView title, exit, version, setgesturetv,cleansize;
    ImageView back;
    boolean state = false;
    SharedPreferences sharedPreferences;
    CheckBox set_push, set_gesture;
    RelativeLayout aboutlayout;
    RelativeLayout gesturelayout, setgesturelayout,cleanlayout;
    String Username = "";
    int isSetgesture=0;
    ImageHandler handler = new ImageHandler();
    //关于 isSetgesture
    //0 "isSet":false
    //1 isSet":true,"state":0
    //2 isSet":true,"state":1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msgsetting);
        initview();
        String aa="{\"datas\":{\"bType\":\"让球\",\"ballType\":\"足球\",\"gid\":\"2943270\",\"league\":\"墨西哥超级联赛\",\"matchPrice\":50000001,\"maxPrice\":20000000,\"minPrice\":10,\"odds\":\"1.09\",\"period\":\"全场\",\"ratio\":\"\",\"ratioC\":\"\",\"ratioH\":\"0\",\"selection\":\"阿苏尔\",\"team1\":\"克雷塔罗\",\"team2\":\"阿苏尔\",\"timeType\":\"滚球\"},\"errorCode\":\"000000\",\"msg\":\"操作成功\"}";
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(aa);
            String kk=jsonObject.getJSONObject("datas").getString("maxPrice");
            LogTools.e("kkkk",Integer.valueOf(kk)+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initview() {
        title = (TextView) findViewById(R.id.title);
        title.setText("设置");
        back = (ImageView) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        exit = (TextView) findViewById(R.id.exit);
        exit.setOnClickListener(this);
        gesturelayout = (RelativeLayout) findViewById(R.id.gesturelayout);
        gesturelayout.setOnClickListener(this);
        setgesturelayout = (RelativeLayout) findViewById(R.id.setgesturelayout);
        setgesturetv = (TextView) findViewById(R.id.setgesturetv);
        set_gesture = (CheckBox) findViewById(R.id.set_gesture);
        Username = ((BaseApplication) this.getApplication()).getBaseapplicationUsername();
        sharedPreferences = ((BaseApplication) getApplication()).getSharedPreferences();
        setgesturelayout.setVisibility(View.GONE);
        if (Username == null || Username.equalsIgnoreCase("")) {
            exit.setVisibility(View.GONE);
        }
        else
        {
            gesturestate();
        }
        set_push = (CheckBox) findViewById(R.id.set_push);
        set_push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPreferences.edit().putBoolean(BundleTag.Pullstate, true).commit();
                    set_push.setChecked(true);
                    JPushInterface.resumePush(SettingAcitvity.this);
                } else {
                    sharedPreferences.edit().putBoolean(BundleTag.Pullstate, false).commit();
                    set_push.setChecked(false);
                    JPushInterface.stopPush(SettingAcitvity.this);
                }
            }
        });

        if (isFrist()) {
            if (set_push.isChecked()) {
                sharedPreferences.edit().putBoolean(BundleTag.Pullstate, true).commit();
                set_push.setChecked(true);
                JPushInterface.resumePush(this);
            } else {
                sharedPreferences.edit().putBoolean(BundleTag.Pullstate, false).commit();
                set_push.setChecked(false);
                JPushInterface.stopPush(this);
            }
            sharedPreferences.edit().putBoolean(BundleTag.IS_PUSHDFRIST, false)
                    .commit();
            ;
        } else {
            set_push.setChecked(sharedPreferences.getBoolean(BundleTag.Pullstate, false));

        }
//        if (Username.length() > 1) {
//            if (isFrist2()) {
//                if (set_gesture.isChecked()) {
//                    set_gesture.setChecked(true);
//                } else {
//                    set_gesture.setChecked(false);
//                }
//            } else {
//                if (sharedPreferences.getString(BundleTag.isopenstate, "").equalsIgnoreCase("1")) {
//                    set_gesture.setChecked(true);
//                    gesturelayout.setVisibility(View.VISIBLE);
//                    setgesturetv.setText("修改手势密码");
//                } else {
//                    set_gesture.setChecked(false);
//                    gesturelayout.setVisibility(View.GONE);
//                }
//            }
//        }
        version = (TextView) findViewById(R.id.version);
        version.setText(Httputils.getVersion(SettingAcitvity.this));
        aboutlayout = (RelativeLayout) findViewById(R.id.aboutlayout);
        aboutlayout.setOnClickListener(this);
        cleanlayout = (RelativeLayout) findViewById(R.id.cleanlayout);
        cleanlayout.setOnClickListener(this);
        cleansize=(TextView)findViewById(R.id.cleansize);

        handler.sendEmptyMessage(ImageHandler.MSG_UPDATE_IMAGE) ;
    }
//    // 更新后台数据
//     class MyThreadphone implements Runnable {
//        public void run() {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            LogTools.e("内存大小","aaaa");
//
//            handler.sendEmptyMessage(ImageHandler.MSG_UPDATE_IMAGE) ;
//        }
//    }
    public class ImageHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE = 1;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    LogTools.e("内存大小","dddd");
                    FileUtils fileUtils = new FileUtils(SettingAcitvity.this);
                    cleansize.setText(fileUtils.FormetFileSize()) ;
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * 判断是否是第一次进入界面
     */

    private boolean isFrist() {
        LogTools.e(sharedPreferences.getBoolean(BundleTag.IS_PUSHDFRIST, true) + "", "是否是第一次");
        return sharedPreferences.getBoolean(BundleTag.IS_PUSHDFRIST, true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1111) {
            boolean isRegistered=data.getBooleanExtra("state",false);
            LogTools.e("resultCode", isRegistered?"YYY":"NNNN");
            if(isRegistered)
            {
                editgesturestate(isRegistered);
            }
            else
            {
                set_gesture.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                ScollAlertdialog scollAlertdialog=new ScollAlertdialog(this,new ScollAlertdialog.OnItemClickListener(){
                    @Override
                    public void OnItemClick(int index, ScollAlertdialog view) {
                     if(index==0){
                            Logout();
                        }
                        view.close();
                    }
                });
                scollAlertdialog.setTitle("您确认要退出当前用户吗？");
                scollAlertdialog.show();

                break;
            case R.id.back:
                LogTools.e("state", state + "");
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("state", state + "");
                setResult(113, intent);
                finish();
                break;
            case R.id.aboutlayout:
                Intent intent2 = new Intent(this, aboutwebActivity.class);
                intent2.putExtra(BundleTag.URL, "m_about");
                intent2.putExtra("title", "关于我们");
                startActivity(intent2);
                break;
            case R.id.gesturelayout:
                startActivity(new Intent(SettingAcitvity.this, EditgestureAcitvity.class));
                break;
            case R.id.cleanlayout:
                ScollAlertdialog scollAlertdialogd=new ScollAlertdialog(this,new ScollAlertdialog.OnItemClickListener(){
                    @Override
                    public void OnItemClick(int index, ScollAlertdialog view) {
                        if(index==0){
                            FileUtils fileUtils = new FileUtils(SettingAcitvity.this);
                           fileUtils.deleteFile();
                            handler.sendEmptyMessage(ImageHandler.MSG_UPDATE_IMAGE) ;
                        }
                        view.close();
                    }
                });
                scollAlertdialogd.setTitle("您确认要清理缓存吗？");
                scollAlertdialogd.show();
                break;
        }
    }

    private void Logout() {

        RequestParams params = new RequestParams();
        params.put("userName", ((BaseApplication) SettingAcitvity.this.getApplication()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.logout, params, new MyJsonHttpResponseHandler(SettingAcitvity.this, true) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                state = false;
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                BaseApplication baseApplication = (BaseApplication) context.getApplication();
                baseApplication.setUsername("");
                state = true;
                ToastUtil.showMessage(SettingAcitvity.this, jsonObject.optString("msg", ""));
                finish();
                ((BaseApplication) context.getApplication()).ClearTiYuCache();
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClass(context, MainActivity.class);
                intent.putExtra(BundleTag.IntentTag, 0);
                context.startActivity(intent);

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    //修改手势状态
    private void editgesturestate(boolean isChecked) {
        RequestParams params = new RequestParams();

        params.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
        params.put("state", isChecked?"1":"0");
        Httputils.PostWithBaseUrl(Httputils.editgesturestate, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                ToastUtil.showMessage(SettingAcitvity.this, jsonObject.optString("msg"));
                if(isSetgesture==0)
                {
                    isSetgesture=2;
                }
                if (set_gesture.isChecked()) {
                    gesturelayout.setVisibility(View.VISIBLE);
                } else {
                    gesturelayout.setVisibility(View.GONE);
                }
            }
        });
    }
    //查询是否设置过

    private void gesturestate() {
        if (Username == null || Username.equalsIgnoreCase("")) return;
        RequestParams params = new RequestParams();
        params.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.gesturestate, params, new MyJsonHttpResponseHandler(this, true) {
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
                    boolean isSet=datas.optJSONObject("gesture").optBoolean("isSet");
                    int state=datas.optJSONObject("gesture").optInt("state");
                    if (!isSet || state<1) {
                        if(!isSet)
                            isSetgesture=0;
                        else
                            isSetgesture=1;
                        set_gesture.setChecked(false);
                        gesturelayout.setVisibility(View.GONE);
                    }
                    else
                    {
                        isSetgesture=2;
                        gesturelayout.setVisibility(View.VISIBLE);
                        set_gesture.setChecked(true);
                    }
                    setgesturelayout.setVisibility(View.VISIBLE);
                    set_gesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            LogTools.e("isChecked", isChecked ? "isChecked" : "NNNNN");
                            if(isChecked && isSetgesture==0)
                            Alertdialog();
                            if(isSetgesture>0)
                            {
                                editgesturestate(isChecked);
                            }
                        }
                    });
                }
            }
        });
    }

    private void Alertdialog() {
//        final GestureDialog dialog=new GestureDialog(this);
        final GestureDialog dialog = new GestureDialog(this, R.style.loading_dialog);
        dialog.setTitle("提示");
        dialog.setMessage("暂未设置手势密码,是否前往设置？");
        dialog.setOnSelectListener(new GestureDialog.OnSelectListener() {
            @Override
            public void select(boolean select) {
                if (select) {
                    startActivityForResult(new Intent(SettingAcitvity.this, SettinggestureAcitvity.class), 10);
                    dialog.dismiss();
                } else {
                    set_gesture.setChecked(false);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}
