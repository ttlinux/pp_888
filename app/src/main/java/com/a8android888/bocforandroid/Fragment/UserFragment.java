package com.a8android888.bocforandroid.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.DailiActivity;
import com.a8android888.bocforandroid.activity.user.Message.MessageActivity;
import com.a8android888.bocforandroid.activity.user.Password.ModifyPassword;
import com.a8android888.bocforandroid.activity.user.PaymentRecord.ChangeRecordActvity;
import com.a8android888.bocforandroid.activity.user.PaymentRecord.PaymentRecordActivity;
import com.a8android888.bocforandroid.activity.user.Change.ChangeActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.activity.user.PaymentRecord.PaymentRecordActivity2;
import com.a8android888.bocforandroid.activity.user.Query.QueryActivity;
import com.a8android888.bocforandroid.activity.user.RegActivity;
import com.a8android888.bocforandroid.activity.user.SafeActivity;
import com.a8android888.bocforandroid.activity.user.SettingAcitvity;
import com.a8android888.bocforandroid.activity.user.UserMessageActivity.Userinformation;
import com.a8android888.bocforandroid.activity.user.WithDraw.WithDrawListActivty;
import com.a8android888.bocforandroid.activity.user.pay.PayActivitty;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.MyRadioButton;
import com.a8android888.bocforandroid.view.MyRelativeLayout;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/3/29.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ImageView back, loginimg, regimg, exitimg;
    private TextView title, textview1, textview2, textview3, textview4;
    RadioButton pay, tixian, change, msglayout, rechargelayout, withdrawallayout, conversionlayout,
            querylayout, userinforlayout, editlayout, bindbankcardlayout, safeinfolayout, daililayout;
    ImageView imagehead;
    LinearLayout loginlayout, nologinlayout;
    private String userRealname;
    private ImageLoader imageLoader;
    PublicDialog publicDialog;
    public static HashMap<String, String> hashMap = new HashMap<>();
    RadioGroup grpview;
    LinearLayout modellist;
    JSONArray memulistrecord, functionrecord;
    View Msglayout;//会员信息的view
    HashMap<String, JSONArray> paytype_args = new HashMap<String, JSONArray>();
    int moneyRefreshTime=60;
    ImageDownLoader Mydownloader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        return View.inflate(getActivity(), R.layout.fragment_user, null);

    }

    @Override
    public void onPause() {
        super.onPause();
        ThreadTimer.Stop(getClass().getName());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Mydownloader = new ImageDownLoader(getActivity());
        imageLoader = ((BaseApplication) getActivity().getApplication()).getImageLoader();
        loginimg = (ImageView) FindView(R.id.loginimg);
        loginimg.setOnClickListener(this);
        modellist = FindView(R.id.modellist);

        exitimg = FindView(R.id.exitimg);
        exitimg.setOnClickListener(this);
        regimg = (ImageView) FindView(R.id.regimg);
        regimg.setOnClickListener(this);

        imagehead = (ImageView) FindView(R.id.imagehead);
        imagehead.setOnClickListener(this);

        title = (TextView) FindView(R.id.title);
        title.setText("会员中心");
        textview1 = (TextView) FindView(R.id.textview1);
        textview1.setOnClickListener(this);
        textview3 = (TextView) FindView(R.id.textview3);
        textview3.setOnClickListener(this);
        textview4 = (TextView) FindView(R.id.textview4);
        textview2 = (TextView) FindView(R.id.textview2);
        grpview = FindView(R.id.grpview);


        loginlayout = (LinearLayout) FindView(R.id.loginlayout);
        nologinlayout = (LinearLayout) FindView(R.id.nologinlayout);

//        pay = (RadioButton) FindView(R.id.pay);
//        pay.setOnCheckedChangeListener(this);
//        tixian = (RadioButton) FindView(R.id.tixian);
//        tixian.setOnCheckedChangeListener(this);
//        change = (RadioButton) FindView(R.id.change);
//        change.setOnCheckedChangeListener(this);

//        msglayout = (RadioButton) FindView(R.id.msglayout);
//        msglayout.setOnCheckedChangeListener(this);
//        rechargelayout = (RadioButton) FindView(R.id.rechargelayout);
//        rechargelayout.setOnCheckedChangeListener(this);
//        withdrawallayout = (RadioButton) FindView(R.id.withdrawallayout);
//        withdrawallayout.setOnCheckedChangeListener(this);
//        conversionlayout = (RadioButton) FindView(R.id.conversionlayout);
//        conversionlayout.setOnCheckedChangeListener(this);
//        querylayout = (RadioButton) FindView(R.id.querylayout);
//        querylayout.setOnCheckedChangeListener(this);
//        userinforlayout = (RadioButton) FindView(R.id.userinforlayout);
//        userinforlayout.setOnCheckedChangeListener(this);
//        editlayout = (RadioButton) FindView(R.id.editlayout);
//        editlayout.setOnCheckedChangeListener(this);
//
//        bindbankcardlayout = (RadioButton) FindView(R.id.bindbankcardlayout);
//        bindbankcardlayout.setOnCheckedChangeListener(this);
//
//        safeinfolayout = (RadioButton) FindView(R.id.safeinfolayout);
//        safeinfolayout.setOnCheckedChangeListener(this);
//
//        daililayout = (RadioButton) FindView(R.id.daililayout);
//        daililayout.setOnCheckedChangeListener(this);

        exitimg.setVisibility(View.VISIBLE);
        exitimg.setImageDrawable(getResources().getDrawable(R.drawable.user_set));
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//
//    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        LogTools.e("hidden", state ? "YES" : "NO");
        if (state == false) {
//            textview2.setText("刷新中");
            CheckLogin();//如果列表什么都没有那就是初始化
        }
        else
            ThreadTimer.Stop(getClass().getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 113 && data != null) {
            if (data.getStringExtra("state").equalsIgnoreCase("true"))
                nologin();
        }
    }

    public void CheckLogin() {
        SharedPreferences sharedPreferences = ((BaseApplication) getActivity().getApplication()).getSharedPreferences();
        String data = sharedPreferences.getString(BundleTag.UserInfo, "");

        String username = ((BaseApplication) getActivity().getApplication()).getBaseapplicationUsername();
        if (username != null && !username.equalsIgnoreCase("")) {

            try {
                JSONObject jsonObject = new JSONObject(data);
                LogTools.e("aaaaaa", data.toString());
                String userNamed = jsonObject.optString("userRealName", "");
                double userMoney = jsonObject.optDouble("userMoney", 0.00);
                JSONObject jsonObject1 = jsonObject.optJSONObject("typeDetail");
//                final String levelName= jsonObject1.optString("typeName","");
                if (jsonObject1 != null) {
                    String levelUrl = jsonObject1.optString("typePicName", "");
                    imageLoader.displayImage(levelUrl, imagehead);
                }
                userRealname = userNamed;
                textview1.setText(getString(R.string.account) + ": " + jsonObject.optString("userName", ""));
                textview3.setVisibility(View.GONE);
                textview4.setVisibility(View.GONE);
//                textview2.setText(getString(R.string.balance) + ": " + userMoney + "  " +
//                        levelName);
//                if (Initview)
//                    ModelList();
//                else
                ModelList();//每次都刷新
//                    getuserinfo(((BaseApplication) this.getActivity().getApplication()).getBaseapplicationUsername());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
//            if (Initview)
//                ModelList();
//            else
            ModelList();//每次都刷新
            nologin();
        }
    }

    private void nologin() {
        /**未登录的情况**/
//        nologinlayout.setVisibility(View.VISIBLE);
//        loginlayout.setVisibility(View.GONE);
//        loginimg.setVisibility(View.VISIBLE);
//        regimg.setVisibility(View.VISIBLE);
//        exitimg.setVisibility(View.GONE);
//        loginimg.setBackground(this.getActivity().getResources().getDrawable(R.drawable.email));
//        regimg.setBackground(this.getActivity().getResources().getDrawable(R.drawable.email));
//        imagehead.setBackground(this.getActivity().getResources().getDrawable(R.drawable.user_placeholder_head));
        ((BaseApplication) getActivity().getApplication()).setUsername("");
        textview1.setText("登录");
        textview3.setText("注册");
        textview2.setText("");
        imagehead.setImageBitmap((Bitmap) BitmapFactory.decodeResource(
                this.getActivity().getResources(), R.drawable.user_placeholder_head));
    }


    /**
     * 获取会员信息
     */
    private void getuserinfo(final String name) {
        RequestParams params = new RequestParams();
        params.put("userName", name);
        Httputils.PostWithBaseUrl(Httputils.UserInfo, params, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("dddddd456", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONObject datas = jsonObject.optJSONObject("datas");
                    HandlerPersonalData(datas);
                }
//                JSONObject json = jsonObject.optJSONObject("datas");
//                String username = getActivity().getString(R.string.username);
//                String balance = getActivity().getString(R.string.balance);
//                textview1.setText(username + ":" + json.optString("userName"));
////                textview2.setText(balance + ":" + json.optString("userMoney"));
//                textview2.setText(balance+": "+"刷新中"+"  "+levelName);
            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
//                LogTools.e("dddddd111", s);
                return super.parseResponse(s);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });

    }

    @Override
    public void onClick(View v) {
        final String name = ((BaseApplication) this.getActivity().getApplication()).getBaseapplicationUsername();
        switch (v.getId()) {
            case R.id.textview1:
                if (name == null || name.equalsIgnoreCase(""))
                    startActivity(new Intent(this.getActivity(), LoginActivity.class));
                break;
            case R.id.textview3:
                startActivity(new Intent(this.getActivity(), RegActivity.class));
                break;
            case R.id.imagehead:
                if (name == null || name.equalsIgnoreCase(""))
                    startActivity(new Intent(this.getActivity(), LoginActivity.class));
                break;
            case R.id.regimg:
                startActivity(new Intent(this.getActivity(), RegActivity.class));
                break;
            case R.id.exitimg:
//                Logout();
                Intent intent = new Intent(this.getActivity(), SettingAcitvity.class);
                startActivityForResult(intent, 113);
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        buttonView.setChecked(false);
//        String Username =((BaseApplication)UserFragment.this.getActivity().getApplication()).getBaseapplicationUsername();
//        if(Username==null || Username.equalsIgnoreCase(""))
//        {
//            Intent intent = new Intent(UserFragment.this.getActivity(), LoginActivity.class);
//            UserFragment.this.getActivity().startActivity(intent);
//            return;
//        }
//        switch (buttonView.getId()) {
//            case R.id.msglayout:
//                startActivity(new Intent(getActivity(), MessageActivity.class));//会员消息
//                break;
//            case R.id.rechargelayout:
//                startActivity(new Intent(getActivity(), PaymentRecordActivity.class));//充值记录
//                break;
//            case R.id.withdrawallayout:
//                startActivity(new Intent(getActivity(), PaymentRecordActivity2.class));//提现记录
//                break;
//            case R.id.conversionlayout:
//                startActivity(new Intent(getActivity(), ChangeRecordActvity.class));//转换记录
//                break;
//            case R.id.querylayout:
//                startActivity(new Intent(getActivity(), QueryActivity.class));//注单查询
//
//                break;
//            case R.id.userinforlayout:
////                startActivity(new Intent(getActivity(), Userinformation.class));//用户信息
//                Intent itenta =new Intent(getActivity(), Userinformation.class);
//                itenta.putExtra("name",userRealname);
//                startActivity(itenta);
//                break;
//            case R.id.daililayout:
//                startActivity(new Intent(getActivity(), DailiActivity.class));//代理申请
//
//                break;
//            case R.id.editlayout:
//                startActivity(new Intent(getActivity(), ModifyPassword.class));//修改密码
//                break;
//            case R.id.pay:
////                startActivity(new Intent(getActivity(), PayActivitty.class));//充值
//                break;
//            case R.id.tixian:
////                startActivity(new Intent(getActivity(), WithDrawListActivty.class));//提现
////                Intent itent =new Intent(getActivity(), WithDrawListActivty.class);
////                itent.putExtra("name",userRealname);
////                itent.putExtra("type","1");
////                itent.putExtra("title",this.getResources().getText(R.string.userwithdrawal));
////                startActivity(itent);
//                break;
//            case R.id.bindbankcardlayout:
////                startActivity(new Intent(getActivity(), WithDrawListActivty.class));//绑定银行卡
//                Intent itent1 =new Intent(getActivity(), WithDrawListActivty.class);
//                itent1.putExtra("name",  userRealname);
//                itent1.putExtra("type","2");
//                itent1.putExtra("title","绑定银行卡");
//                startActivity(itent1);
//                break;
//            case R.id.safeinfolayout:
////                startActivity(new Intent(getActivity(), WithDrawListActivty.class));//安全信息
//                Intent itent2 =new Intent(getActivity(), SafeActivity.class);
//                itent2.putExtra("name",textview1.getText().toString().split(getString(R.string.account) + ": "));
//                startActivity(itent2);
//                break;
//            case R.id.change:
////                startActivity(new Intent(this.getActivity(), ChangeActivity.class));//转换
//                break;
//
//        }
    }

    private void Logout() {

        if (publicDialog == null) {
            publicDialog = new PublicDialog(getActivity());
        }
        publicDialog.show();
        RequestParams params = new RequestParams();
        params.put("userName", ((BaseApplication) UserFragment.this.getActivity().getApplicationContext()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.logout, params, new MyJsonHttpResponseHandler(getActivity(), true) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                publicDialog.dismiss();
                LogTools.e("jsonObject", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                nologin();
                BaseApplication baseApplication = (BaseApplication) context.getApplication();
                baseApplication.setUsername("");
                publicDialog.dismiss();
                ToastUtil.showMessage(getActivity(), jsonObject.optString("msg", ""));
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                publicDialog.dismiss();
                ToastUtil.showMessage(getActivity(), s);
            }
        });
    }

    private void ModelList()//请求动态数据
    {
        RequestParams requestParams = new RequestParams();
        final String name = ((BaseApplication) this.getActivity().getApplication()).getBaseapplicationUsername();
        if (name != null && !name.equalsIgnoreCase(""))
            requestParams.put("userName", name);

        Httputils.PostWithBaseUrl(Httputils.UserFragment_Model, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {
                    @Override
                    public void onSuccessOfMe(JSONObject jsonObject) {
                        super.onSuccessOfMe(jsonObject);
                        LogTools.ee("ModelList", jsonObject.toString());
                        if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                            return;
                        }

                        JSONObject datas = jsonObject.optJSONObject("datas");
                        JSONArray fundsList = datas.optJSONArray("fundsList");
                        JSONObject information = datas.optJSONObject("information");
                        /////////////////////////////////////
                        JSONObject select = datas.optJSONObject("select");
                        if (select.optJSONArray("huikuan") != null)
                            paytype_args.put("huikuan", select.optJSONArray("huikuan"));
                        if (select.optJSONArray("withdraw") != null)
                            paytype_args.put("withdraw", select.optJSONArray("withdraw"));
                        if (select.optJSONArray("flat") != null)
                            paytype_args.put("flat", select.optJSONArray("flat"));
                        ////////////////////////////////////////////
                        Iterator<String> keys = information.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            String value = information.optString(key, "");
                            HttpforNoticeinbottom.hashMap2.put(key, value);
                        }
//                        Double pay=Double.valueOf(datas.optString("eduMinPay",""))*0.01*100;
//                        String patstr=Httputils.Limit2(pay);
//                        HttpforNoticeinbottom.hashMap2.put("eduMinPay","最低%s金额需≥"+patstr+"元");
//                        LogTools.e("HttpforNoticei",HttpforNoticeinbottom.hashMap2.get("eduMinPay"));
                        JSONArray menuList = datas.optJSONArray("menuList");
                        JSONObject userInfo = datas.optJSONObject("userInfo");
                        LogTools.e("ModelList", datas.optString("msgCount", "0"));
                        try {
                            if (name == null || name.equalsIgnoreCase("") || Msglayout == null) {

                            } else {
                                moneyRefreshTime= Integer.valueOf(datas.optString("moneyRefreshTime", "0"));
                                int msgCount = Integer.valueOf(datas.optString("msgCount", "0"));
                                if (msgCount > 0) {
                                    Msglayout.findViewById(R.id.circle_red).setVisibility(View.VISIBLE);
                                } else {
                                    Msglayout.findViewById(R.id.circle_red).setVisibility(View.GONE);
                                }
                            }

                        } catch (Exception exception) {
                            ToastUtil.showMessage(getActivity(), "数据出错");
                        }


                        FunctionHandle(fundsList);
                        MenuListHandle(menuList);
                        if (name != null && !name.equalsIgnoreCase(""))
                        {
                            HandlerPersonalData(userInfo);
                            OpenTimer();
                        }


                    }

                    @Override
                    public void onFailureOfMe(Throwable throwable, String s) {
                        super.onFailureOfMe(throwable, s);
                    }
                }
        );
    }

    private void FunctionHandle(JSONArray jsonarray)//三个坑
    {
        if (functionrecord == null) {
            functionrecord = jsonarray;
        } else {
            boolean no_need_rebulid = false;
            no_need_rebulid = jsonarray.length() == functionrecord.length();
            for (int i = 0; i < jsonarray.length(); i++) {
                if (!functionrecord.optJSONObject(i).optString("menuCode", "")
                        .equalsIgnoreCase(jsonarray.optJSONObject(i).optString("menuCode", ""))) {
                    no_need_rebulid = false;
                    break;
                }
            }
            if (no_need_rebulid) {
                LogTools.e("ModelList", "ModelList111 no_refresh");
                return;//一样的就不管了
            }

        }
        if (jsonarray.length() > 3) {
            return;
        } else {
            for (int i = 0; i < jsonarray.length(); i++) {
                final JSONObject jsob = jsonarray.optJSONObject(i);
                final MyRadioButton radiobtn = (MyRadioButton) grpview.getChildAt(i * 2);
                radiobtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        buttonView.setChecked(false);
                        String Username = ((BaseApplication) UserFragment.this.getActivity().getApplication()).getBaseapplicationUsername();
                        if (Username == null || Username.equalsIgnoreCase("")) {
                            Intent intent = new Intent(UserFragment.this.getActivity(), LoginActivity.class);
                            UserFragment.this.getActivity().startActivity(intent);
                            return;
                        }
                        switch (jsob.optString("menuCode", "")) {
                            case "deposit":
                                Intent intent = new Intent(getActivity(), PayActivitty.class);
//                                if(paytype_args.get("huikuan")!=null)
//                                intent.putExtra(BundleTag.JsonObject,paytype_args.get("huikuan").toString());
                                startActivity(intent);//充值
                                break;
                            case "withdraw":
                                Intent itent = new Intent(getActivity(), WithDrawListActivty.class);
                                itent.putExtra("name", userRealname);
                                itent.putExtra("type", "1");
                                itent.putExtra("title", getResources().getString(R.string.userwithdrawal));
//                                if(paytype_args.get("withdraw")!=null)
//                                    itent.putExtra(BundleTag.JsonObject,paytype_args.get("withdraw").toString());
                                startActivity(itent);
                                break;
                            case "edu":
                                Intent intent1 = new Intent(getActivity(), ChangeActivity.class);
//                                if(paytype_args.get("flat")!=null)
//                                    intent1.putExtra(BundleTag.JsonObject,paytype_args.get("flat").toString());
                                startActivity(intent1);//转换
                                break;
                        }
                    }
                });
                radiobtn.setVisibility(View.VISIBLE);
                radiobtn.setText(jsob.optString("menuName", ""));
                Mydownloader.SetSpecialView(this.getContext(), jsob.optString("bigBackgroundPic", ""), radiobtn);

                imageLoader.loadImage(jsob.optString("bigPic", ""), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        if (bitmap != null) {
                            Drawable drawable = new BitmapDrawable(bitmap);
                            //(int)(bitmap.getWidth()*1.5f)
                            drawable.setBounds(0, 0, ScreenUtils.getDIP2PX(getActivity(), 40), ScreenUtils.getDIP2PX(getActivity(), 40));//必须设置图片大小，否则不显示
                            radiobtn.setCompoundDrawables(null, drawable, null, null);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });
            }
        }
    }

    private void MenuListHandle(JSONArray jsonarray)//列表
    {
        //判断数据一样的就不刷新了
//        boolean no_need_init = false;
//        if (memulistrecord == null) {
//            memulistrecord = jsonarray;
//        } else {
//            no_need_init = jsonarray.length() == memulistrecord.length();
//            for (int i = 0; i < jsonarray.length(); i++) {
//                JSONObject jsonobj = jsonarray.optJSONObject(i);
//                JSONArray jsonarraylist = jsonobj.optJSONArray("list");
//                JSONObject m_jsonobj2 = memulistrecord.optJSONObject(i);
//                JSONArray m_jsonarraylist2 = m_jsonobj2.optJSONArray("list");
//                no_need_init = m_jsonarraylist2.length() == jsonarraylist.length();
//                if (!no_need_init) break;
//                for (int k = 0; k < jsonarraylist.length(); k++) {
//                    JSONObject temp = jsonarraylist.optJSONObject(k);
//                    JSONObject m_temp2 = m_jsonarraylist2.optJSONObject(k);
//                    if (!temp.optString("menuCode", "").equalsIgnoreCase(m_temp2.optString("menuCode", ""))) {
//                        no_need_init = false;
//                        break;
//                    }
//                }
//            }
//            if (no_need_init) {
//                LogTools.e("ModelList", "ModelList222 no_refresh");
//                return;
//            } else {
//                modellist.removeAllViews();
//            }
//        }
        //判断数据一样的就不刷新了

        modellist.removeAllViews();
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobj = jsonarray.optJSONObject(i);
            JSONArray jsonarraylist = jsonobj.optJSONArray("list");
            for (int k = 0; k < jsonarraylist.length(); k++) {
                JSONObject temp = jsonarraylist.optJSONObject(k);
                final View view = View.inflate(getActivity(), R.layout.item_user_fragment, null);
                view.setTag(temp.optString("menuCode", ""));
                if (temp.optString("menuCode", "").equalsIgnoreCase("message")) {
                    Msglayout = view;
                }
                TextView title = (TextView) view.findViewById(R.id.msglayout);
                title.setText(temp.optString("menuName", ""));
                imageLoader.loadImage(temp.optString("bigPic", ""), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view2, Bitmap bitmap) {
                        if (bitmap != null) {
                            ImageView imageView = (ImageView) view.findViewById(R.id.img);
                            Drawable drawable = new BitmapDrawable(bitmap);
                            //(int)(bitmap.getWidth()*1.5f)
                            drawable.setBounds(0, 0, ScreenUtils.getDIP2PX(getActivity(), 30), ScreenUtils.getDIP2PX(getActivity(), 30));//必须设置图片大小，否则不显示
                            imageView.setImageDrawable(drawable);
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Username = ((BaseApplication) UserFragment.this.getActivity().getApplication()).getBaseapplicationUsername();
                        if (Username == null || Username.equalsIgnoreCase("")) {
                            Intent intent = new Intent(UserFragment.this.getActivity(), LoginActivity.class);
                            UserFragment.this.getActivity().startActivity(intent);
                            return;
                        }
                        JumptoNewPage(String.valueOf(v.getTag() + ""));
                    }
                });
                Mydownloader.SetSpecialView(this.getContext(),temp.optString("bigBackgroundPic", ""), view);

                modellist.addView(view);
                ImageView imageview = new ImageView(getActivity());
                imageview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
                imageview.setBackgroundColor(getResources().getColor(R.color.userbagcolor));
                modellist.addView(imageview);
            }
            if (i == jsonarray.length() - 1) break;
            ImageView imageview = new ImageView(getActivity());
            imageview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getDIP2PX(getActivity(), 10)));
            imageview.setBackgroundColor(getResources().getColor(R.color.userbagcolor));
            modellist.addView(imageview);
        }
        LogTools.e("刷新","刷新");
    }


    private void JumptoNewPage(String tag) {
        switch (tag) {
            case "message"://会员消息
                startActivity(new Intent(getActivity(), MessageActivity.class));//会员消息
                break;
            case "deposit"://充值记录
                Intent intent = new Intent(getActivity(), PaymentRecordActivity.class);
                if (paytype_args.get("huikuan") != null)
                    intent.putExtra(BundleTag.JsonObject, paytype_args.get("huikuan").toString());
                startActivity(intent);//充值记录
                break;
            case "withdraw"://提现记录
                Intent wintent = new Intent(getActivity(), PaymentRecordActivity2.class);
                if (paytype_args.get("withdraw") != null)
                    wintent.putExtra(BundleTag.JsonObject, paytype_args.get("withdraw").toString());
                startActivity(wintent);//提现记录
                break;
            case "edu"://额度转换记录
                Intent cintent = new Intent(getActivity(), ChangeRecordActvity.class);
                if (paytype_args.get("flat") != null)
                    cintent.putExtra(BundleTag.JsonObject, paytype_args.get("flat").toString());
                startActivity(cintent);//转换记录
                break;
            case "order"://注单记录
                startActivity(new Intent(getActivity(), QueryActivity.class));//注单查询
                break;
            case "bindbank"://银行卡绑定
                Intent itent1 = new Intent(getActivity(), WithDrawListActivty.class);
                itent1.putExtra("name", userRealname);
                itent1.putExtra("type", "2");
                itent1.putExtra("title", getString(R.string.Bankband));
                startActivity(itent1);
                break;
            case "userinfo"://会员资料
                Intent itenta = new Intent(getActivity(), Userinformation.class);
                itenta.putExtra("name", userRealname);
                startActivity(itenta);
                break;
            case "password"://修改密码
                startActivity(new Intent(getActivity(), ModifyPassword.class));//修改密码
                break;
            case "security"://安全信息
                Intent itent2 = new Intent(getActivity(), SafeActivity.class);
                itent2.putExtra("name", textview1.getText().toString().trim().split(getString(R.string.account) + ": "));
                startActivity(itent2);
                break;
            case "agent"://代理申请
                startActivity(new Intent(getActivity(), DailiActivity.class));//代理申请
                break;
        }
    }

    private void HandlerPersonalData(JSONObject datas) {
        SharedPreferences sharedPreferences = ((BaseApplication) getActivity().getApplication()).getSharedPreferences();
        sharedPreferences.edit()
                .putString(BundleTag.UserInfo, datas.toString())
//                            .putString(BundleTag.BankList,datas.optJSONArray("bankList").toString())
                .commit();
        if (datas.optJSONObject("typeDetail") != null) {
            imageLoader.displayImage(datas.optJSONObject("typeDetail").optString("typePicName", ""), imagehead);
            String text=getString(R.string.balance) + ": " + "%s" + "元  " +
                    datas.optJSONObject("typeDetail").optString("typeLevel", "");

            textview2.setText(String.format(text, Httputils.setjiage(datas.optString("userMoney", ""))));
            textview2.setTag(text);

        }

        textview1.setText(getString(R.string.account) + ": " + datas.optString("userName").toString());

        textview3.setVisibility(View.GONE);
        textview4.setVisibility(View.GONE);
        userRealname = datas.optString("userRealName").toString();
    }

    private void OpenTimer()
    {
        ThreadTimer.Stop(getClass().getName());
        ThreadTimer.setListener(new ThreadTimer.OnActiviteListener() {
            @Override
            public void MinCallback(long limitmin) {

            }

            @Override
            public void DelayCallback() {

                SetBalance();
            }
        });
        ThreadTimer.Start(moneyRefreshTime*1000);//moneyRefreshTime
    }

    private void SetBalance()//刷余额
    {
        BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();
        String username=baseApplication.getBaseapplicationUsername();
        Httputils.GetBalance(username, new Httputils.BalanceListener() {
            @Override
            public void OnRecevicedata(String balanced) {
                double blance = Double.valueOf(balanced);
                try {
                    blance = Double.valueOf(balanced);
                    String formattext=(String)textview2.getTag();
                    textview2.setText(String.format(formattext, blance));
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                OpenTimer();
            }

            @Override
            public void Onfail(String str) {
                OpenTimer();
            }
        }, getActivity());
    }
}
