package com.a8android888.bocforandroid.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.ModuleBean;
import com.a8android888.bocforandroid.MainActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.WelcomeActivity;
import com.a8android888.bocforandroid.activity.main.ActicleListActivity;
import com.a8android888.bocforandroid.activity.main.BBNActivity;
import com.a8android888.bocforandroid.activity.main.CardActivity;
import com.a8android888.bocforandroid.activity.main.Electronic_gamesActivity;
import com.a8android888.bocforandroid.activity.main.JumpActivity;
import com.a8android888.bocforandroid.activity.main.Real_videoActivity;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_eventsActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragement;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.GestureLockViewGroup;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.a8android888.bocforandroid.view.ScollAlertdialog;
import com.a8android888.bocforandroid.view.Solve7PopupWindow;
import com.a8android888.bocforandroid.webActivity;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/3/30.登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private EditText password, name,code;
    TextView coded, forgetpassw, registered, title;
    Button comit;
    ImageView back;
    String staterecord;
    public static long autime = 0;
    public PublicDialog publicDialog;
    LinearLayout temp3;
    ArrayList<ArrayList<ModuleBean>> gameList = new ArrayList<ArrayList<ModuleBean>>();
    ImageDownLoader mImageDownLoader2;
    private ImageLoader mImageDownLoader;
    ArrayList<ModuleBean> gameBeanList;
    TextView notice;
    private ArrayAdapter<String> arrayAdapter;
    RadioGroup passwordgroup;
    RadioButton rb1, rb2;
    RelativeLayout passwlayout;
    View line;
    LinearLayout layout;
    GestureLockViewGroup mGestureLockViewGroup;
    boolean isReset = false;
    StringBuilder pass = new StringBuilder();
    String passd;
    String loginType = "1";
    SharedPreferences usernamelist;
    String arr[]=null;
    PopupWindow popwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.login);
        mImageDownLoader = ((BaseApplication) this.getApplication())
                .getImageLoader();
        mImageDownLoader2 = new ImageDownLoader(this);
        initview();
    }

    private void initview() {
        usernamelist=getSharedPreferences(BundleTag.UserNameList, Activity.MODE_PRIVATE);
        back = (ImageView) FindView(R.id.back);
        back.setOnClickListener(this);
        title = (TextView) FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.userlogin));
        name = FindView(R.id.name);
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && arr != null) {
                    openPopView((TextView)v, arr);
                }
                return false;
            }
        });

        String namelist=usernamelist.getString(BundleTag.UserNameList,"");
        LogTools.e("namelist",namelist);
        if(!namelist.equalsIgnoreCase(""))
        {
            String [] temp=namelist.split("\\|");
            arr=new String[temp.length+1];
            for (int q = 0; q < arr.length-1; q++) {
                arr[q]=temp[q];
            }
            arr[arr.length-1]="清除历史记录";
        }

//        openPopView(name,arr);

//        name.setText(((BaseApplication) getApplication()).getUsername());

        password = (EditText) FindView(R.id.password);
        code = (EditText) FindView(R.id.code);
        coded = (TextView) FindView(R.id.coded);
        forgetpassw = (TextView) FindView(R.id.forgetpassw);
        forgetpassw.setOnClickListener(this);
        registered = (TextView) FindView(R.id.registered);
        registered.setOnClickListener(this);
        comit = (Button) FindView(R.id.comit);
        comit.setOnClickListener(this);
        notice = FindView(R.id.notice);
        HttpforNoticeinbottom.GetMainPageData(notice, "login", this);

        if (autime == 0) {
            autime = System.currentTimeMillis() / 1000;
        }
        temp3 = (LinearLayout) FindView(R.id.temp3);
        module();
        passwordgroup = (RadioGroup) FindView(R.id.passwordgroup);
        passwlayout = (RelativeLayout) FindView(R.id.passwlayout);
        line = (View) FindView(R.id.line);
        layout = (LinearLayout) FindView(R.id.layout);
        rb1 = (RadioButton) FindView(R.id.rb1);
        rb1.setChecked(true);
        rb1.setOnCheckedChangeListener(LoginActivity.this);
        rb2 = (RadioButton) FindView(R.id.rb2);
        rb2.setOnCheckedChangeListener(LoginActivity.this);
        initGesture();
        rb1.setChecked(true);
    }

    private void  openPopView(final TextView view,final String strs[])
    {
        LogTools.e("strs length", strs.length + " " + view.getWidth());
        if(popwindow==null) {
            int width=view.getWidth();
            LinearLayout ll=new LinearLayout(this);
            LinearLayout.LayoutParams laypa=new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setLayoutParams(laypa);
            ll.setBackground(getResources().getDrawable(R.drawable.rangle_noradiu_backgroud));

            for (int i = 0; i < strs.length; i++) {
                ImageView imageview=new ImageView(this);
                LinearLayout.LayoutParams imagell=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                imageview.setLayoutParams(imagell);
                imageview.setBackgroundColor(getResources().getColor(R.color.line));

                TextView textview=new TextView(this);
                LinearLayout.LayoutParams tvll=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textview.setText(strs[i]);
                textview.setLayoutParams(tvll);
                textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textview.setPadding(ScreenUtils.getDIP2PX(this, 10), ScreenUtils.getDIP2PX(this, 4), 0, ScreenUtils.getDIP2PX(this, 4));
                textview.setGravity(Gravity.LEFT);
                textview.setTag(i);
                textview.setFocusable(true);
                textview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closePopView();
                       int index=Integer.valueOf(v.getTag()+"");
                        LogTools.e("tvvvvv", index + "");
                        if(index<arr.length-1)
                        {
                            TextView tv=(TextView)v;
                            name.setText(tv.getText().toString());
                        }
                        else
                        {
                            usernamelist.edit().clear().commit();
                            arr=null;
                            popwindow=null;
                        }

                    }
                });

                ll.addView(imageview);
                ll.addView(textview);
            }
            popwindow = new PopupWindow(ll,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            popwindow.setWidth(width);
            popwindow.setFocusable(false);
            popwindow.setOutsideTouchable(true);
            popwindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            popwindow.setContentView(ll);
        }
        popwindow.update();
        popwindow.showAsDropDown(view);
//        popwindow.showAsDropDown(view,0,0);
    }

    private void  closePopView()
    {
        if(popwindow!=null)
        {
            popwindow.dismiss();
        }
    }

    private void initGesture() {
//        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gesturelock);
//        gestureEventListener();
//        gesturePasswordSettingListener();
//        gestureRetryLimitListener();

        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.id_gestureLockViewGroup);
//        mGestureLockViewGroup.setAnswer(new int[]{2, 4, 8, 6, 2, 5, 8, 4, 5, 6});
        mGestureLockViewGroup.setUnMatchExceedBoundary(4);
        mGestureLockViewGroup.setLimitListener(4, new GestureLockViewGroup.onLimitListener() {
            @Override
            public void onLimit() {
                ToastUtil.showMessage(LoginActivity.this, "最少链接4个点，请重新输入");
            }
        });
        mGestureLockViewGroup
                .setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {

                    @Override
                    public void onTouchEvent(MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN)
                            pass.delete(0, pass.length());
                    }

                    @Override
                    public void oncheck(int event) {
                    }

                    @Override
                    public void onUnmatchedExceedBoundary() {

                        finish();
                    }

                    @Override
                    public void onGestureEvent(boolean matched) {
                        passd = pass.toString();
                        mGestureLockViewGroup.resetAndRefresh();
                        if(name.getText().toString().equalsIgnoreCase(""))
                        {
                            ToastUtil.showMessage(LoginActivity.this,name.getHint().toString());
                            return;
                        }
                        authorize();
                        LogTools.e("dddddddddd11111" + pass.toString(), "passd = " + passd);
                        pass.delete(0, pass.length());

                    }

                    @Override
                    public void onBlockSelected(int cId) {
                        pass.append(String.valueOf(cId));
                    }
                });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb1:
                if (isChecked) {
                    comit.setEnabled(true);
                    loginType = "1";
                    layout.setVisibility(View.VISIBLE);
                    line.setVisibility(View.VISIBLE);
                    comit.setVisibility(View.VISIBLE);
                    passwlayout.setVisibility(View.VISIBLE);
                    mGestureLockViewGroup.setVisibility(View.GONE);
                }
                break;
            case R.id.rb2:
                if (isChecked) {
                    InputMethodManager m = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    m.hideSoftInputFromWindow(name.getWindowToken(), 0);
//                    name.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            KeyBoard(name, false, 100);
//                        }
//                    });
                    loginType = "2";
//                    SharedPreferences sharedPreferences = ((BaseApplication) getApplication()).getSharedPreferences();
//                    if (sharedPreferences.getString(BundleTag.isopenstate, "1").equalsIgnoreCase("0")) {
//                        ToastUtil.showMessage(this, "手势密码未开启，请登陆后到个人设置里面开启");
//                        return;
//                    }
                    mGestureLockViewGroup.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    comit.setVisibility(View.GONE);
                    passwlayout.setVisibility(View.GONE);

                }
                break;
        }
    }

    public static void KeyBoard(final EditText txtSearchKey, final boolean status, int second) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager)
                        txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (m.isActive()) {
                    m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                }
            }
        }, second);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comit:
                if (name.getText().toString().trim().length() < 1) {
                    ToastUtil.showMessage(this, name.getHint().toString());
                    return;
                }
                if (rb1.isChecked()) {

                    if (password.getText().toString().trim().length() < 1) {
                        ToastUtil.showMessage(this, getString(R.string.plzfillinpassword));
                        return;
                    }
                }
                if (rb2.isChecked()) {

                    if (passd.toString().length() < 1) {
                        ToastUtil.showMessage(this, getString(R.string.plzfillinpassword2));
                        return;
                    }
                }
                authorize();
                break;
            case R.id.back:
                if (MainActivity.isexit != 2) {
                    Intent intent2 = new Intent(LoginActivity.this, WelcomeActivity.class);
                    intent2.setAction(Intent.ACTION_MAIN);
                    intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    finish();
                } else {
                    setResult(112);
                    finish();
                }
                break;
            case R.id.registered:
                startActivity(new Intent(this, RegActivity.class));
                break;
            case R.id.forgetpassw:
//                ToastUtil.showMessage(this, "请联系24小时在线客服找回密码");
//                SharedPreferences sharedPreferences=((BaseApplication)getApplication()).getSharedPreferences();
//                Intent  intent = new Intent(this, EditphoneActivity.class);
//                intent.putExtra(BundleTag.Phonenum, sharedPreferences.getString(BundleTag.Phonenum, ""));
//                intent.putExtra(BundleTag.QQ, sharedPreferences.getString(BundleTag.QQ, ""));
//                intent.putExtra(BundleTag.Email, sharedPreferences.getString(BundleTag.Email, ""));
//                intent.putExtra(BundleTag.Weixin, sharedPreferences.getString(BundleTag.Weixin, ""));
//                intent.putExtra(BundleTag.Tel, sharedPreferences.getString(BundleTag.Tel, ""));
//                intent.putExtra(BundleTag.URL, sharedPreferences.getString(BundleTag.URL, ""));
//                intent.putExtra("title","找回密码");
//                startActivity(intent);
                startActivity(new Intent(this,ForgotPasswordActivity.class));
                break;
        }
    }

    private void authorize() {
        if (publicDialog == null) {
            publicDialog = new PublicDialog(this);
        }
        publicDialog.show();
        String keys[] = getResources().getStringArray(R.array.authorize);
        staterecord = UUID.randomUUID().toString().trim();
        LogTools.e("staterecord", staterecord);
        RequestParams params = new RequestParams();
        params.put("userName", name.getText().toString().trim());
        params.put("clientKey", Httputils.androidkey);//keys[0] /*unchange(keys[0])*/
        params.put("clientSecret", Httputils.androidsecret);//unchange(keys[1])  Httputils.androidsecret
        params.put("deviceId", android.os.Build.SERIAL);
        try {
            params.put("state", staterecord);//des加密就用这个 DesUtil.encrypt(staterecord,keys[2])
        } catch (Exception e) {
            e.printStackTrace();
        }
        comit.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.authorize, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                LogTools.e("Throwable", s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject tempjsobject = jsonObject.optJSONObject("datas");
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000") && staterecord.equalsIgnoreCase(tempjsobject.optString("state", ""))) {
                    autime = Long.valueOf(tempjsobject.optString("expires_in", ""));
                    String access_token = tempjsobject.optString("access_token", "");
                    SharedPreferences sharedPreferences = ((BaseApplication) getApplication()).getSharedPreferences();
                    sharedPreferences.edit().putString(BundleTag.Access_token, access_token).commit();
                    Login(access_token);
                } else {
                    ToastUtil.showMessage(LoginActivity.this, jsonObject.optString("msg", ""));
                    comit.setEnabled(true);
                }
            }
        });
    }

    //    "datas": {
//        "userInfo": {
//                     "unReadMsg": 0,
//                    "userLastLoginTime": "2017-04-04 19:42:31",
//                    "userMobile": "18707508894",
//                    "userQq": "877149698",
//                    "userName": "4318471pk",
//                    "realName": "",
//                    "userMoney": 0,
//                    "userLastLoginIp": "127.0.0.1",
//                    "userMail": ""
//        },
//        "bankList": [
//
//        ],
//        "level": {
//            "levelName": "普通会员",
//                    "levelUrl": "\/m\/site\/e0\/member\/vip.png"
//        }
//    },
    private void Login(String access_token) {
        if (name.getText().toString().trim().length() < 1) {
            ToastUtil.showMessage(this, getString(R.string.plzfillinname));
            return;
        }
        if (rb1.isChecked()) {

            if (password.getText().toString().trim().length() < 1) {
                ToastUtil.showMessage(this, getString(R.string.plzfillinpassword));
                return;
            }
        }
        if (rb2.isChecked()) {

            if (passd.toString().length() < 1) {
                ToastUtil.showMessage(this, getString(R.string.plzfillinpassword2));
                return;
            }
        }
        RequestParams params = new RequestParams();
        params.put("accessToken", access_token);
        if (rb1.isChecked()) {
            params.put("password", password.getText().toString().trim());
        }
        if (rb2.isChecked()) {
            params.put("password", passd.toString().trim());
        }
        params.put("userName", name.getText().toString().trim());
        params.put("loginType", loginType);
        Httputils.PostWithBaseUrl(Httputils.login, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                LogTools.e("onFailureOfMe", "fail");
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject datas = jsonObject.optJSONObject("datas");
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                ToastUtil.showMessage(LoginActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    SharedPreferences sharedPreferences = ((BaseApplication) getApplication()).getSharedPreferences();
                    ((BaseApplication) getApplication()).setUsername(datas.optString("userName", ""));
                    sharedPreferences.edit()
//                            .putString(BundleTag.level, datas.optJSONObject("typeDetail").toString())
                            .putString(BundleTag.UserInfo, datas.toString())
//                    .putString(BundleTag.BankList,datas.optJSONArray("bankList").toString())
//                            .putBoolean(BundleTag.setgesturestate, datas.optBoolean("isSet"))
//                            .putString(BundleTag.isopenstate, datas.optString("gesturePasswordStatus"))
                            .commit();
                    String namelist=usernamelist.getString(BundleTag.UserNameList,"");
                    if(!namelist.contains(name.getText().toString().trim()))
                    {
                        String middle=namelist.equalsIgnoreCase("")?"":"|";
                        String username=namelist+middle+name.getText().toString().trim();
                        if(username.split("\\|").length>4)
                        {
                            username=username.substring(username.indexOf("|", 2)+1,username.length());
                        }
                        usernamelist.edit().putString(BundleTag.UserNameList,username).commit();
                    }


//                    if( datas.optBoolean("isSet")&&datas.optString("gesturePasswordStatus").equalsIgnoreCase("1"))
//                        sharedPreferences.edit().putBoolean(BundleTag.issetstate, true).commit();
//                    else
//                        sharedPreferences.edit().putBoolean(BundleTag.issetstate, false).commit();
                    finish();
                } else {
                    if (publicDialog != null && !isDestroyed()) {
                        publicDialog.dismiss();
                    }
//                    authorize();
//                    ToastUtil.showMessage(LoginActivity.this, jsonObject.optString("msg", ""));
                }
            }
        });
    }

    private static String unchange(String str) {
        byte[] bstr = new byte[str.getBytes().length];
        for (int i = 0; i < bstr.length; i++) {
            bstr[i] = (byte) (str.getBytes()[i] - 1);
        }
        return new String(bstr);
    }

    private void module() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("regionCode", "login");
        Httputils.PostWithBaseUrl(Httputils.modulelist, requestParams, new MyJsonHttpResponseHandler(this, false) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("ddddddaaaa", jsonObject.toString());
//                JSONObject json = jsonObject.optJSONObject("datas");

                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("datas");
//                    JSONArray jsonArray = json.getJSONArray("moduleList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonda = jsonArray.getJSONObject(i);

                        gameBeanList = new ArrayList<ModuleBean>();
                        JSONArray jsonArray5 = jsonda.getJSONArray("article");
                        for (int k = 0; k < jsonArray5.length(); k++) {
                            JSONObject jsond = jsonArray5.getJSONObject(k);
                            ModuleBean gameBean1 = new ModuleBean();
                            gameBean1.setBigBackgroundPic(jsond.optString("backgroundPicUrl", ""));
                            gameBean1.setModuleId(jsonda.optString("moduleId", ""));
                            gameBean1.setRemark(jsonda.optString("remark", ""));
                            gameBean1.setModuleType(jsonda.optString("moduleType", ""));
                            gameBean1.setModuleName(jsonda.optString("moduleName", ""));
                            gameBean1.setModuleCode(jsonda.optString("moduleCode", ""));
                            gameBean1.setCreateTime(jsond.optString("createTime", ""));
                            gameBean1.setArticleBigImages(jsond.optString("articleBigImages", ""));
                            gameBean1.setArticleCode(jsond.optString("articleCode", ""));
                            gameBean1.setLinkType(jsond.optString("linkType", ""));
                            gameBean1.setLinkUrl(jsond.optString("linkUrl", ""));
                            gameBean1.setArticleSmallImages(jsond.optString("articleSmallImages", ""));
                            gameBean1.setArticleBigHeight(jsond.optString("articleBigHeight", ""));
                            gameBean1.setArticleSmallHeight(jsond.optString("articleSmallHeight", ""));
                            gameBean1.setArticleId(jsond.optString("articleId", ""));
                            gameBean1.setArticleName(jsond.optString("articleName", ""));
                            gameBean1.setArticleBigWidth(jsond.optString("articleBigWidth", ""));
                            gameBean1.setArticleTitle(jsond.optString("articleTitle", ""));
                            gameBean1.setArticleSmallWidth(jsond.optString("articleSmallWidth", ""));
                            gameBean1.setArticleSubTitle(jsond.optString("articleSubTitle", ""));
                            gameBean1.setArticleContent(jsond.optString("articleContent", ""));
                            gameBean1.setShowType(jsond.optString("showType", ""));
                            gameBean1.setArticleType(jsond.optString("articleType", ""));
//                            gameBean1.setInnerLink(jsond.optString("innerLink", ""));
//                            if(!gameBean1.getInnerLink().equalsIgnoreCase("")){
//                                JSONObject ajson = new JSONObject(jsond.optString("innerLink", ""));
                            gameBean1.setTypeCode(jsond.optString("typeCode", ""));
                            gameBean1.setGameCode(jsond.optString("gameCode", ""));
                            gameBean1.setCateCode(jsond.optString("cateCode", ""));
                            LogTools.e("dianji", jsond.optString("typeCode", ""));

//                            }
                            gameBeanList.add(gameBean1);

                        }
                        gameList.add(gameBeanList);
                    }
                    moduleview();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }

    private void moduleview() {

        for (int i = 0; i < gameList.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            int itemcount = gameList.get(i).size();
            for (int k = 0; k < gameList.get(i).size(); k++) {
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("1")) {
                    View popupView = View.inflate(this, R.layout.module1_layout, null);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
                    TextView title = (TextView) popupView.findViewById(R.id.title);
                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getBigBackgroundPic(), srcid);
                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setText(gameList.get(i).get(k).getArticleSubTitle());
                    final int indexa = i;
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });

                    linearLayout.addView(popupView);
                    ImageView line = new ImageView(this);
                    line.setBackgroundColor(getResources().getColor(R.color.line));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    linearLayout.addView(line);
                }

                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("2")) {
                    View popupView = View.inflate(this, R.layout.module2_layout, null);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
                    TextView title = (TextView) popupView.findViewById(R.id.title);
                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
// mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getBigBackgroundPic(), srcid);
                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setText(gameList.get(i).get(k).getArticleSubTitle());
                    final int indexa = i;
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);
                    ImageView line = new ImageView(this);
                    line.setBackgroundColor(getResources().getColor(R.color.line));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    linearLayout.addView(line);
                }
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("3")) {
                    View popupView = View.inflate(this, R.layout.module3_layout, null);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    TextView title = (TextView) popupView.findViewById(R.id.title);
                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
//                    title.setText( gameList.get(i).get(k).getArticleName());
                    contact.setText(gameList.get(i).get(k).getArticleTitle());
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getArticleSmallImages(), img);
                    final int indexa = i;
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);
                    ImageView line = new ImageView(this);
                    line.setBackgroundColor(getResources().getColor(R.color.line));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    linearLayout.addView(line);
                }
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("4")) {
                    View popupView = View.inflate(this, R.layout.module5_layout, null);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
                    TextView title = (TextView) popupView.findViewById(R.id.title);

                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);

                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setTag(k);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    mImageDownLoader2.showImage(this, gameList.get(i).get(k).getArticleSmallImages(), img, 0);
                    final int indexa = i;
                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);
                }
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("5")) {
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams1.leftMargin = 10;
                    layoutParams1.rightMargin = 10;
                    layoutParams1.topMargin = 5;
                    layoutParams1.bottomMargin = 5;
                    View popupView = View.inflate(this, R.layout.module4_layout, null);
                    popupView.setLayoutParams(layoutParams1);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);

                    TextView title = (TextView) popupView.findViewById(R.id.title);
                    title.setVisibility(GONE);
                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
                    contact.setVisibility(GONE);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);

                    mImageDownLoader2.showImage(this, gameList.get(i).get(k).getArticleSmallImages(), img, 0);
                    final int indexa = i;
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);

                }
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("6")) {
                    LogTools.e("hhhhhkaaadd", itemcount + "");
                    LinearLayout hor = new LinearLayout(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    hor.setLayoutParams(params);
                    hor.setOrientation(LinearLayout.HORIZONTAL);
                    int tempcount = itemcount - k * 3 > 3 ? (3 * (k + 1)) : itemcount;
                    for (int j = k * 3; j < tempcount; j++) {
                        final int iii = j;
                        View popupView = View.inflate(this, R.layout.real_video_item, null);
                        int width = ScreenUtils.getScreenWH(this)[0] / 3;
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupView.setLayoutParams(params2);
                        popupView.setTag(iii);
                        LogTools.e("hhhhhkaaavv", gameList.get(i).get(j).getArticleTitle() + "");
                        TextView videoname = (TextView) popupView.findViewById(R.id.videoname);
                        ImageView videoimg = (ImageView) popupView.findViewById(R.id.videoimg);
                        ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
//                        mImageDownLoader.displayImage(gameList.get(i).get(j).getBigBackgroundPic(), srcid);
                        mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(j).getBigBackgroundPic(), popupView);

                        mImageDownLoader.displayImage(gameList.get(i).get(j).getArticleSmallImages(), videoimg);
                        videoname.setText(gameList.get(i).get(j).getArticleTitle());
                        final int indexa = i;
                        popupView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final int tag = Integer.valueOf(v.getTag() + "");
                                modeluonclick(indexa, tag);
                            }
                        });
                        hor.addView(popupView);
                        ImageView line = new ImageView(this);
                        line.setBackgroundColor(getResources().getColor(R.color.gray1));
                        line.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.FILL_PARENT));
                        hor.addView(line);
                    }
                    linearLayout.addView(hor);
                    ImageView line = new ImageView(this);
                    line.setBackgroundColor(getResources().getColor(R.color.gray1));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    linearLayout.addView(line);
                }

            }
            temp3.addView(linearLayout);
            ImageView line = new ImageView(this);
            line.setBackgroundColor(getResources().getColor(R.color.gray1));
            line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 15));
            temp3.addView(line);
        }
    }

    public void modeluonclick(int indexa, int tag) {
        JumpActivity.modeluonclick(LoginActivity.this, gameList.get(indexa).get(tag).getLinkType(),
                gameList.get(indexa).get(tag).getOpenLinkType(), gameList.get(indexa).get(tag).getTypeCode(),
                gameList.get(indexa).get(tag).getLevel(), gameList.get(indexa).get(tag).getCateCode(),
                gameList.get(indexa).get(tag).getGameCode(), gameList.get(indexa).get(tag).getArticleName(),
                gameList.get(indexa).get(tag).getArticleTitle(), gameList.get(indexa).get(tag).getLinkUrl()
                , gameList.get(indexa).get(tag).getArticleType(), gameList.get(indexa).get(tag).getArticleId(), "nopull", "2", gameList.get(indexa).get(tag).getArticleId());

//        if (gameList.get(indexa).get(tag).getLinkType().equalsIgnoreCase("1")) {
//            Intent intent = new Intent(this, webActivity.class);
//            intent.putExtra(BundleTag.title, gameList.get(indexa).get(tag).getArticleTitle());
//            intent.putExtra(BundleTag.URL, gameList.get(indexa).get(tag).getLinkUrl());
//            intent.putExtra(BundleTag.IntentTag, true);
//            startActivity(intent);
//        }
//        if (gameList.get(indexa).get(tag).getLinkType().equalsIgnoreCase("2")) {
//            if (gameList.get(indexa).get(tag).getTypeCode().equalsIgnoreCase("sport")) {
//                startActivity(new Intent(this, Sports_eventsActivity.class));
//            }
//            if (gameList.get(indexa).get(tag).getTypeCode().equalsIgnoreCase("electronic")) {
//                startActivity(new Intent(this, Electronic_gamesActivity.class));
//            }
//            if (gameList.get(indexa).get(tag).getTypeCode().equalsIgnoreCase("lottery")) {
//                startActivity(new Intent(this, LotteryFragement.class));
//            }
//            if (gameList.get(indexa).get(tag).getTypeCode().equalsIgnoreCase("live")) {
//                startActivity(new Intent(this, Real_videoActivity.class));
//            }
//            if (gameList.get(indexa).get(tag).getTypeCode().equalsIgnoreCase("card")) {
//                startActivity(new Intent(this, CardActivity.class));
//            }
//            if (gameList.get(indexa).get(tag).getTypeCode().equalsIgnoreCase("bbin")) {
//                startActivity(new Intent(this, BBNActivity.class));
//            }
////                                Intent intent = new Intent(this, webActivity.class);
////                                intent.putExtra(BundleTag.title, gameList.get(indexa).get(tag).getArticleTitle());
////                                intent.putExtra(BundleTag.URL, gameList.get(indexa).get(tag).getLinkUrl());
////                                intent.putExtra(BundleTag.IntentTag, true);
////                                startActivity(intent);
//        }
//        if (gameList.get(indexa).get(tag).getLinkType().equalsIgnoreCase("3")) {
//            Intent intent = new Intent(this, ActicleListActivity.class);
//            intent.putExtra(BundleTag.title, gameList.get(indexa).get(tag).getRemark());
//            intent.putExtra(BundleTag.id, gameList.get(indexa).get(tag).getModuleType());
//            intent.putExtra(BundleTag.articleId, gameList.get(indexa).get(tag).getArticleId());
//            startActivity(intent);
//        }
//        if (gameList.get(indexa).get(tag).getLinkType().equalsIgnoreCase("4")) {
//
//
//            if (gameList.get(indexa).get(tag).getShowType().equalsIgnoreCase("2")) {
//                Intent intent4 = new Intent(this, webActivity.class);
//                intent4.putExtra(BundleTag.WebData, true);
//                intent4.putExtra(BundleTag.URL, gameList.get(indexa).get(tag).getArticleContent());
//                intent4.putExtra(BundleTag.title, gameList.get(indexa).get(tag).getArticleTitle());
//                startActivity(intent4);
//            }
//            if (gameList.get(indexa).get(tag).getShowType().equalsIgnoreCase("1")) {
//                Intent intent4 = new Intent(this, webActivity.class);
//                intent4.putExtra(BundleTag.WebData, true);
//                intent4.putExtra(BundleTag.isimg, true);
//                intent4.putExtra(BundleTag.URL, gameList.get(indexa).get(tag).getArticleBigImages());
//                intent4.putExtra(BundleTag.title, gameList.get(indexa).get(tag).getArticleTitle());
//                startActivity(intent4);
//            }
////                                Intent intent = new Intent(this, webActivity.class);
////                                intent.putExtra(BundleTag.title, gameList.get(indexa).get(tag).getArticleTitle());
////                                intent.putExtra(BundleTag.URL, gameList.get(indexa).get(tag).getLinkUrl());
////                                intent.putExtra(BundleTag.IntentTag, true);
////                                startActivity(intent);
//        }
    }


}
