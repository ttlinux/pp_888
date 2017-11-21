package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Lottery_comit_Order_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.lotteryinterface.opertionclic;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpForLottery;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.Getlistheight;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/4/11. 确认投注
 */
public class LotteryComitOrderActivity extends BaseActivity implements View.OnClickListener {
    private TextView title, comit, lotterytv, zhushu, zongje;
    ImageView back;
    ListView listView;
    Lottery_comit_Order_Adapter adapter;
    Intent intent;
    JSONArray jsonArray;
    public ArrayList<LotterycomitorderBean> mList3 = new ArrayList<LotterycomitorderBean>();
    private ArrayList<LotterycomitorderBean> mListd;
    BaseApplication baseApplication;
    TextView qs, time;
    LinearLayout kjlayout;
    EditText comitedit2;
    public static opertionclic oponclic;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ThreadTimer.Stop();
        setContentView(R.layout.lottery_comit_order);
        title = (TextView) FindView(R.id.title);
        intent = getIntent();
        title.setText(intent.getStringExtra("title"));


        back = (ImageView) FindView(R.id.back);
        back.setOnClickListener(this);
        comit = (TextView) FindView(R.id.comit);
        comit.setOnClickListener(this);
        listView = (ListView) FindView(R.id.listview);
        View headview=View.inflate(this,R.layout.activity_lottery_order_listview_head,null);
        listView.addHeaderView(headview);

        relativeLayout=FindView(R.id.layout);
        relativeLayout.setVisibility(View.GONE);
        baseApplication = (BaseApplication) this.getApplication();
        qs = FindView(R.id.qs);
        qs.setText("当前第 " + baseApplication.getCurrentQs() + " 期");
        kjlayout = (LinearLayout) FindView(R.id.kjlayout);
        zhushu = (TextView) FindView(R.id.zhushu);
        zongje = (TextView) FindView(R.id.zongje);
        comitedit2 = (EditText) FindView(R.id.comitedit2);
        comitedit2.requestFocus();
//        comitedit2.performClick();
        comitedit2.post(new Runnable() {
            @Override
            public void run() {
                KeyBoard(comitedit2, true, 100);
            }
        });

        comitedit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String index = comitedit2.getText().toString().trim();
                if (s.toString() != null && !"".equals(s.toString())) {
                    if (Httputils.isNumber(index)) {
                        adapter.NotifyData(mList3, index);
                        SpannableStringBuilder builder2;
                        String text="";
                        if(!intent.getStringExtra("normal").equalsIgnoreCase("cl")) {
                            text = "金额:" +Httputils.setjiage(String.valueOf( adapter.mList.size() * Integer.valueOf(index) ))+ "元";
                        }
                        else{
                            text = "金额:" + Httputils.setjiage(String.valueOf(Integer.valueOf(index)*(jsonArray.length()))) + "元";
                        }

                        builder2 = new SpannableStringBuilder(text);
                        ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                        builder2.setSpan(redSpan2, 3, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        zongje.setText(builder2);
                    }
//                    else{
//                        adapter.NotifyData(mList3, "0");
//                        zongje.setText(adapter.mList.size() * Integer.valueOf(0));
//                    }
                } else {
                    adapter.NotifyData(mList3, "0");
                    SpannableStringBuilder builder2;
                    String text="";
                    if(!intent.getStringExtra("normal").equalsIgnoreCase("cl")) {
                        text = "金额:" + Httputils.setjiage(String.valueOf( adapter.mList.size() * Integer.valueOf(0))) + "元";
                    }
                    else{
                        text = "金额:" + Httputils.setjiage(String.valueOf(Integer.valueOf(0)*(jsonArray.length()))) + "元";
                    }
                    builder2 = new SpannableStringBuilder(text);
                    ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                    builder2.setSpan(redSpan2, 3, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    zongje.setText(builder2);
                }
            }
        });


        time = FindView(R.id.time);
        lotterytv = (TextView) FindView(R.id.lotterytv);
        final String usernames = ((BaseApplication) this.getApplication()).getBaseapplicationUsername();
        Httputils.GetBalance(usernames, new Httputils.BalanceListener() {
            @Override
            public void OnRecevicedata(String balanced) {
                lotterytv.setText("会员账号:" + usernames + "  余额:" + Httputils.setjiage(balanced) + "元");
                lotterytv.setTextColor(getResources().getColor(R.color.gray4));
            }
            @Override
            public void Onfail(String str) {

            }
        }, this);
        try {
            jsonArray = new JSONArray(intent.getStringExtra("json"));
            if(!intent.getStringExtra("normal").equalsIgnoreCase("cl")) {
            for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    LotterycomitorderBean bean = new LotterycomitorderBean();
                    bean.setNumber(json.optString("number"));
                    bean.setUid(json.optString("uid"));
                    bean.setPl(json.optString("pl"));
                    if (intent.getStringExtra("gameitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gameitemname"))) {
                        bean.setItemtname(intent.getStringExtra("title"));
                    } else {
                        bean.setItemtname(intent.getStringExtra("gameitemname"));
                    }
                    bean.setMinLimit(json.optString("minLimit"));
                    bean.setMaxlimit(json.optString("maxlimit"));
                    bean.setXzje("0");
                    bean.setMaxPeriodLimit(json.optString("maxPeriodLimit"));
                    if (intent.getStringExtra("gamexzlxitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gamexzlxitemname"))) {
                        if (intent.getStringExtra("gameitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gameitemname"))) {
                            bean.setXzlxitemname(intent.getStringExtra("title"));
                        } else {
                            bean.setXzlxitemname(intent.getStringExtra("gameitemname"));
                        }
                    } else {
                        bean.setXzlxitemname(intent.getStringExtra("gamexzlxitemname"));
                    }
                    mList3.add(bean);
                }

            }
            else {
                String number="";
                String titlenumber="";
                String uid="";
                String pl="";
                String  minLimit="";
                String  maxlimit="";
                String  maxPeriodLimit="";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    if(json.optString("uid").indexOf("ZM1") != -1)
                    titlenumber="正码1";
                    if (json.optString("uid").indexOf("ZM2") != -1)
                        titlenumber="正码2";
                    if (json.optString("uid").indexOf("ZM3") != -1)
                        titlenumber="正码3";
                    if (json.optString("uid").indexOf("ZM4") != -1)
                        titlenumber="正码4";
                    if (json.optString("uid").indexOf("ZM5") != -1)
                        titlenumber="正码5";
                    if (json.optString("uid").indexOf("ZM6") != -1){
                        titlenumber="正码6";}
                    if(i<jsonArray.length()-1)
                    number=number+titlenumber+json.optString("number")+",";
                    else
                    number=number+titlenumber+json.optString("number");
                    uid=json.optString("uid");
                    pl=json.optString("pl");
                    minLimit=json.optString("minLimit");
                    maxlimit=json.optString("maxlimit");
                    maxPeriodLimit=json.optString("maxPeriodLimit");
                }
                LotterycomitorderBean bean = new LotterycomitorderBean();
                bean.setNumber(number);
                bean.setUid(uid);
                bean.setPl(pl);
                if (intent.getStringExtra("gameitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gameitemname"))) {
                    bean.setItemtname(intent.getStringExtra("title"));
                } else {
                    bean.setItemtname(intent.getStringExtra("gameitemname"));
                }
                bean.setMinLimit(minLimit);
                bean.setMaxlimit(maxlimit);
                bean.setXzje("0");
                bean.setMaxPeriodLimit(maxPeriodLimit);
                if (intent.getStringExtra("gamexzlxitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gamexzlxitemname"))) {
                    if (intent.getStringExtra("gameitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gameitemname"))) {
                        bean.setXzlxitemname(intent.getStringExtra("title"));
                    } else {
                        bean.setXzlxitemname(intent.getStringExtra("gameitemname"));
                    }
                } else {
                    bean.setXzlxitemname(intent.getStringExtra("gamexzlxitemname"));
                }
                mList3.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogTools.e("mList", "++++++" + new Gson().toJson(mList3));
//        if (mList3.size() > 1) {
//            kjlayout.setVisibility(View.VISIBLE);
//        } else {
//            kjlayout.setVisibility(View.GONE);
//        }
        SpannableStringBuilder builder2;
        String text="";
        if(!intent.getStringExtra("normal").equalsIgnoreCase("cl")) {
            LogTools.e("gameitemname", "++++++" +intent.getStringExtra("index"));
            if(intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("佰拾个定位")
                    ||intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("拾个定位")||
                    intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("佰个定位")||
                    intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("佰拾定位")||
                    intent.getStringExtra("gameitemname").equalsIgnoreCase("二字定位")||
                    intent.getStringExtra("gameitemname").equalsIgnoreCase("三字定位")||
                    intent.getStringExtra("gameitemname").equalsIgnoreCase("二字组合"))
            {

                text = "共" + intent.getStringExtra("index") + "注";
            }
            else {
                text = "共" + mList3.size() + "注";
            }
        }
        else{
            text = "共" + mList3.size()*(jsonArray.length()) + "注";
        }
        builder2 = new SpannableStringBuilder(text);
        ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
        builder2.setSpan(redSpan2, 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        zhushu.setText(builder2);
        adapter = new Lottery_comit_Order_Adapter(LotteryComitOrderActivity.this, mList3,listView);
        listView.setAdapter(adapter);

        /////////////////////////////////////////////////
        openTimer();

        oponclic = new opertionclic() {

            @Override
            public void edit() {
                LogTools.e("mHolder->edit","mHolder->edit");

                    int xzjes = 0;
                    for (int i1 = 0; i1 < adapter.mList.size(); i1++) {
                        xzjes = xzjes + Integer.valueOf(adapter.mList.get(i1).getXzje());
                    }
                    SpannableStringBuilder builder3;
                String text2="";
                String text="";
                if(!intent.getStringExtra("normal").equalsIgnoreCase("cl")) {
                    if(intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("佰拾个定位")
                            ||intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("拾个定位")||
                            intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("佰个定位")||
                            intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("佰拾定位")||
                            intent.getStringExtra("gameitemname").equalsIgnoreCase("二字定位")
                            ||intent.getStringExtra("gameitemname").equalsIgnoreCase("三字定位")||
                            intent.getStringExtra("gameitemname").equalsIgnoreCase("二字组合"))
                    {
                        text2 = "金额:" + Httputils.setjiage(String.valueOf(xzjes*(Integer.valueOf(intent.getStringExtra("index"))))) + "元";
                        text = "共" + intent.getStringExtra("index") + "注";
                    }
                    else {
                        text2 = "金额:" + Httputils.setjiage(String.valueOf(xzjes)) + "元";
                        text = "共" + adapter.mList.size() + "注";
                    }
                }
                else{
                    text2 = "金额:" + Httputils.setjiage(String.valueOf(xzjes*(jsonArray.length()))) + "元";
                    text = "共" + adapter.mList.size()*(jsonArray.length()) + "注";
                }
                    builder3 = new SpannableStringBuilder(text2);
                    ForegroundColorSpan redSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                    builder3.setSpan(redSpan3, 3, text2.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    zongje.setText(builder3);

                    SpannableStringBuilder builder2;

                    builder2 = new SpannableStringBuilder(text);
                    ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                    builder2.setSpan(redSpan2, 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    zhushu.setText(builder2);
                }
        };
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (oponclic != null) {
            oponclic = null;
        }
    }
        @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.KEYCODE_BACK) {
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
                    mListd = adapter.mList;
                    int cunt = 0;
                    for (int i = 0; i < mListd.size(); i++) {
                        if (mListd.get(i).getXzje().equalsIgnoreCase("0")) {
                            ToastUtil.showMessage(this, "单注金额不符合要求");
                            return;
                        } else
                            cunt = cunt + Integer.valueOf(mListd.get(i).getXzje());
                    }
                    if (cunt > Integer.valueOf(baseApplication.getMaxPeriodLimit())) {
                        ToastUtil.showMessage(this, "单注金额不符合要求");
                    } else {
                        comit.setClickable(false);
                        String jsona="";
                        if(!intent.getStringExtra("normal").equalsIgnoreCase("cl")) {
                            jsona = HttpForLottery.GetCommitJson(adapter.mList, intent.getStringExtra("title"), intent.getStringExtra("gamexzlxitemname"));
                        }
                        else {
                            ArrayList<LotterycomitorderBean> mList3 = new ArrayList<LotterycomitorderBean>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = null;
                                try {
                                    json = jsonArray.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LotterycomitorderBean bean = new LotterycomitorderBean();
                                bean.setNumber(json.optString("number"));
                                bean.setUid(json.optString("uid"));
                                bean.setPl(json.optString("pl"));
                                if (intent.getStringExtra("gameitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gameitemname"))) {
                                    bean.setItemtname(intent.getStringExtra("title"));
                                } else {
                                    bean.setItemtname(intent.getStringExtra("gameitemname"));
                                }
                                bean.setMinLimit(json.optString("minLimit"));
                                bean.setMaxlimit(json.optString("maxlimit"));
                                bean.setXzje(mListd.get(0).getXzje());
                                bean.setMaxPeriodLimit(json.optString("maxPeriodLimit"));
                                if (intent.getStringExtra("gamexzlxitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gamexzlxitemname"))) {
                                    if (intent.getStringExtra("gameitemname") == null || "".equalsIgnoreCase(intent.getStringExtra("gameitemname"))) {
                                        bean.setXzlxitemname(intent.getStringExtra("title"));
                                    } else {
                                        bean.setXzlxitemname(intent.getStringExtra("gameitemname"));
                                    }
                                } else {
                                    bean.setXzlxitemname(intent.getStringExtra("gamexzlxitemname"));
                                }
                                mList3.add(bean);
                            }
                            jsona = HttpForLottery.GetCommitJson(mList3, intent.getStringExtra("title"), intent.getStringExtra("gamexzlxitemname"));
                        }
                        HttpForLottery.Commitorder(this, intent.getStringExtra("gamecode"), intent.getStringExtra("normal"), jsona.toString(), new HttpForLottery.OnDataReceviceListenera() {
                            @Override
                            public void OnRecevice(Boolean state) {
                                comit.setClickable(true);
                                if (state)
                                    finish();
                            }
                        });
                    }
                break;

        }
    }


    private void openTimer() {
        ThreadTimer.setListener2(new ThreadTimer.OnActiviteListener2() {
            @Override
            public void MinCallback(long limitmin, long close, long open) {
                if (open == 0) {
                    ThreadTimer.Stop();
                }
                SetTime(close, open);
            }

            @Override
            public void DelayCallback() {

            }
        });
        BaseApplication baseApplication = (BaseApplication) getApplication();

        long close = baseApplication.getClosetime() - baseApplication.getNowtime();
        long now = baseApplication.getOpentime() - baseApplication.getNowtime();
        ThreadTimer.Start(30 * 1000, close * 1000, now * 1000);
    }

    private void SetTime(long close, long nowtime) {
        BaseApplication.opentime = BaseApplication.nowtime + nowtime / 1000;
        BaseApplication.closetime = BaseApplication.nowtime + close / 1000;

        SpannableStringBuilder builder = new SpannableStringBuilder(qs.getText().toString().trim());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
        if (qs.getText().toString().length() > 0) {
            builder.setSpan(redSpan, 4, qs.getText().toString().length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            qs.setText(builder);
        }
        SpannableStringBuilder builder2;
        if (close <= 0) {
//            String text = "距离开盘还有" + Httputils.getStringToDate(nowtime);
//            builder2 = new SpannableStringBuilder(text);
//            builder2.setSpan(redSpan, 2, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            String text = "距离开盘还有 " + Httputils.getStringToDate(nowtime);
            builder2 = new SpannableStringBuilder(text);
            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
            builder2.setSpan(redSpan2, 7, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            time.setText(builder2);
            if (nowtime == 0) {
                RefreshOpenData();
            } else {
            }
        } else {
//            if (close == 0) {
//                shadows.setVisibility(View.VISIBLE);
//                shadows.setText("正在封盘");
//            }
            String text = "距离封盘还有 " + Httputils.getStringToDate(close);
            builder2 = new SpannableStringBuilder(text);
            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
            builder2.setSpan(redSpan2, 7, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            time.setText(builder2);
        }
    }


    public void RefreshOpenData() {
        if (isDestroyed()) return;
        HttpForLottery.lotteryopendata(this, LotteryFragment1.getGamecode(),
                LotteryFragment1.getGameitemcode(), LotteryFragment1.getGameitemcode(), new HttpForLottery.OnDataReceviceListener() {
                    @Override
                    public void OnRecevice(int status) {
                        if(status>1)
                        {
//                            shadows_layout.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams relativeLayout=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                            shadows_layout.setLayoutParams(relativeLayout);
//                            shadows.setImageDrawable(getResources().getDrawable(res[1]));
                        }
                        else {
                            baseApplication = (BaseApplication)getApplication();
                            qs.setText("当前第 " + baseApplication.getCurrentQs() + " 期");
//                            basefragments.get(viewpager.getCurrentItem()).CallbackFunction();
                            openTimer();
                        }
                    }
                });
    }


    public static void KeyBoard(final EditText txtSearchKey,final boolean status,int second)
    {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager)
                        txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (status) {
                    m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                }
            }
        }, second);

    }


}
