package com.a8android888.bocforandroid.activity.main.lottery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
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

import com.a8android888.bocforandroid.Adapter.Lottery_comit_Order_other_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.lotteryinterface.opertionclic;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpForLottery;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/4/11. 确认投注
 */
public class LotteryComitOrderActivityother extends BaseActivity implements View.OnClickListener {
    private TextView title, comit, lotterytv;
    ImageView back;
    ListView listView;
    Lottery_comit_Order_other_Adapter adapter;
    Intent intent;
    JSONArray jsonArray;
    public ArrayList<LotterycomitorderBean> mList3 = new ArrayList<LotterycomitorderBean>();
    BaseApplication baseApplication;
    ImageView image;
    TextView comitname, name, zuidi, height;
    EditText comitedit;
    String token;
    String pjString;
    TextView qs, time, zhushu, zongje;
    LinearLayout kjlayout;

    EditText comitedit2;

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
        baseApplication = (BaseApplication) this.getApplication();

        kjlayout = (LinearLayout) FindView(R.id.kjlayout);
        zhushu = (TextView) FindView(R.id.zhushu);
        zongje = (TextView) FindView(R.id.zongje);
        comitedit2 = (EditText) FindView(R.id.comitedit2);
        comitedit2.post(new Runnable() {
            @Override
            public void run() {
                KeyBoard(comitedit2, true, 100);
            }
        });


        qs = FindView(R.id.qs);
        qs.setText("当前第 " + baseApplication.getCurrentQs() + " 期");
        time = FindView(R.id.time);
        lotterytv = (TextView) FindView(R.id.lotterytv);
        final String usernames = ((BaseApplication) this.getApplication()).getBaseapplicationUsername();

        Httputils.GetBalance(usernames, new Httputils.BalanceListener() {
            @Override
            public void OnRecevicedata(String balanced) {
                lotterytv.setText("会员账号:" + usernames + "  余额：" + Httputils.setjiage(balanced) + "元");
                lotterytv.setTextColor(getResources().getColor(R.color.gray4));
            }
            @Override
            public void Onfail(String str) {

            }
        }, this);

        image = (ImageView) FindView(R.id.image);
        image.setVisibility(View.GONE);
        comitname = (TextView) FindView(R.id.comitname);
        name = (TextView) FindView(R.id.name);
        name.setVisibility(View.GONE);
        comitname.setText(intent.getStringExtra("gameitemname")/* + "   " + intent.getStringExtra("gamexzlxitemname")*//*  + "   "+ intent.getStringExtra("jsonNumber")*//* + "   " + intent.getStringExtra("pl")*/);
        if (intent.getStringExtra("gamexzlxitemname").equalsIgnoreCase("")) {
            name.setText(intent.getStringExtra("gameitemname") + ":" + intent.getStringExtra("jsonNumber"));
        } else {
            name.setText(intent.getStringExtra("gamexzlxitemname") + ":" + intent.getStringExtra("jsonNumber"));
        }

        zuidi = (TextView) FindView(R.id.zuidi);
        zuidi.setText("单注" + baseApplication.getMinLimit() + "-" + baseApplication.getMaxlimit());
        height = (TextView) FindView(R.id.height);
        height.setText("单期最高" + baseApplication.getMaxPeriodLimit());
        comitedit = (EditText) FindView(R.id.comitedit);

        String text2 = "金额:" + Httputils.setjiage(String.valueOf(0)) + "元";
        SpannableStringBuilder builder3 = new SpannableStringBuilder(text2);
        ForegroundColorSpan redSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
        builder3.setSpan(redSpan3, 3, text2.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        zongje.setText(builder3);
        comitedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String index = comitedit.getText().toString().trim();
                if (s.toString() != null && !"".equals(s.toString())) {
                    if (Httputils.isNumber(index)) {
                        int xzjes = 0;
                        xzjes = xzjes + Integer.valueOf(comitedit.getText().toString().trim());
                        SpannableStringBuilder builder3;
                        String text2 = "金额:" + Httputils.setjiage(String.valueOf(xzjes * mList3.size())) + "元";
                        builder3 = new SpannableStringBuilder(text2);
                        ForegroundColorSpan redSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                        builder3.setSpan(redSpan3, 3, text2.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        zongje.setText(builder3);
                    }
                }
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
                comitedit.setText(comitedit2.getText().toString().trim());
                if (s.toString() != null && !"".equals(s.toString())) {
                    if (Httputils.isNumber(index)) {
                        int xzjes = 0;
                        xzjes = xzjes + Integer.valueOf(comitedit2.getText().toString().trim());
                        SpannableStringBuilder builder3;
                        String text2 = "金额:" + Httputils.setjiage(String.valueOf(xzjes * mList3.size())) + "元";
                        builder3 = new SpannableStringBuilder(text2);
                        ForegroundColorSpan redSpan3 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                        builder3.setSpan(redSpan3, 3, text2.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        zongje.setText(builder3);
                    }
                }
            }
        });
//        comitedit.setText(baseApplication.getMinLimit());
        kjlayout.setVisibility(View.VISIBLE);
        SpannableStringBuilder builder2;
        String text = "共0注";
        builder2 = new SpannableStringBuilder(text);
        ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
        builder2.setSpan(redSpan2, 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        zhushu.setText(builder2);
        LogTools.e("normal", intent.getStringExtra("normal"));
        if (intent.getStringExtra("normal").equalsIgnoreCase("mul")) {
            pjString = intent.getStringExtra("jsonUid");
            getMulti();
        } else {
            pjString = intent.getStringExtra("jsonNumber");
            if (intent.getStringExtra("normal").equalsIgnoreCase("lm")) {
                getLMMulti();
            } else {
                getGroupMulti();
            }
        }

/////////////////////////////////////////////////
        openTimer();
    }

    private void getMulti() {
        HttpForLottery.CommitMultiorder(this, intent.getStringExtra("gamecode"), intent.getStringExtra("multilen"), pjString,
                new HttpForLottery.OnDataReceviceListenerd() {
                    @Override
                    public void OnRecevice(JSONObject jsonObject) {
                        try {
                            LogTools.e("jsongngngng", jsonObject.toString());
                            if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                                return;
                            }

                            JSONObject object = jsonObject.optJSONObject("datas");
                            token = object.optString("token");
                            jsonArray = object.getJSONArray("orderList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                LotterycomitorderBean bean = new LotterycomitorderBean();
                                bean.setNumber(json.optString("number"));
                                bean.setPl(json.optString("rate"));
                                bean.setItemtname(json.optString("xzlxName"));
                                mList3.add(bean);

                            }

                            adapter = new Lottery_comit_Order_other_Adapter(LotteryComitOrderActivityother.this, mList3);
                            listView.setAdapter(adapter);
                            SpannableStringBuilder builder2;
                            String text = "共" + mList3.size() + "注";
                            builder2 = new SpannableStringBuilder(text);
                            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                            builder2.setSpan(redSpan2, 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            zhushu.setText(builder2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    private void getGroupMulti() {
        HttpForLottery.CommitGrouporder(this, intent.getStringExtra("gamecode"), intent.getStringExtra("gameitemnamecode"),
                intent.getStringExtra("gamexzlxitemcode"), intent.getStringExtra("multilen"), pjString,
                new HttpForLottery.OnDataReceviceListenerd() {
                    @Override
                    public void OnRecevice(JSONObject jsonObject) {
                        try {
                            LogTools.e("jsongngngng", jsonObject.toString());
                            if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                                return;
                            }

                            JSONObject object = jsonObject.optJSONObject("datas");
                            token = object.optString("token");
                            jsonArray = object.getJSONArray("orderList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                LotterycomitorderBean bean = new LotterycomitorderBean();
                                bean.setNumber(json.optString("number"));
                                bean.setPl(json.optString("rate"));
                                bean.setItemtname(json.optString("xzlxName"));
                                mList3.add(bean);

                            }
                            adapter = new Lottery_comit_Order_other_Adapter(LotteryComitOrderActivityother.this, mList3);
                            listView.setAdapter(adapter);
                            SpannableStringBuilder builder2;
                            String text = "共" + mList3.size() + "注";
                            builder2 = new SpannableStringBuilder(text);
                            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                            builder2.setSpan(redSpan2, 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            zhushu.setText(builder2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    private void getLMMulti() {
        HttpForLottery.CommitLMorder(this, intent.getStringExtra("gamecode"), pjString, intent.getStringExtra("multilen"),
                intent.getStringExtra("pabc"), intent.getStringExtra("dm1"), intent.getStringExtra("dm2"),
                new HttpForLottery.OnDataReceviceListenerd() {
                    @Override
                    public void OnRecevice(JSONObject jsonObject) {
                        try {
                            LogTools.e("jsongngngng", jsonObject.toString());
                            if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                                return;
                            }

                            JSONObject object = jsonObject.optJSONObject("datas");
                            token = object.optString("token");
                            jsonArray = object.getJSONArray("orderList");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                LotterycomitorderBean bean = new LotterycomitorderBean();
                                bean.setNumber(json.optString("number"));
                                bean.setPl(json.optString("rate"));
                                bean.setItemtname(json.optString("xzlxName"));
                                mList3.add(bean);

                            }
                            adapter = new Lottery_comit_Order_other_Adapter(LotteryComitOrderActivityother.this, mList3);
                            listView.setAdapter(adapter);
                            SpannableStringBuilder builder2;
                            String text = "共" + mList3.size() + "注";
                            builder2 = new SpannableStringBuilder(text);
                            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
                            builder2.setSpan(redSpan2, 1, text.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            zhushu.setText(builder2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.comit:
                int cunt = 0;
                String aa = (comitedit.getText().toString().trim());
                if (aa != null && !"".equalsIgnoreCase(aa) && Httputils.isNumber(aa)) {

                    cunt = Integer.valueOf(aa);
                } else {
                    ToastUtil.showMessage(this, "单注金额不符合要求");
                    return;
                }
                LogTools.e("下注金额是多少", cunt + "");

                if (Integer.valueOf(baseApplication.getMinLimit()) <= cunt && cunt <= Integer.valueOf(baseApplication.getMaxlimit())) {
                    comit.setClickable(false);
                    String json = HttpForLottery.GetCommitLMJson(token, cunt + "", intent.getStringExtra("gameitemnamecode"), intent.getStringExtra("gamexzlxitemcode"));
                    HttpForLottery.Commitorder(this, intent.getStringExtra("gamecode"), intent.getStringExtra("normal"), json.toString(), new HttpForLottery.OnDataReceviceListenera() {
                        @Override
                        public void OnRecevice(Boolean state) {
                            comit.setClickable(true);
                            if (state)
                                finish();
                        }
                    });
                } else {
                    ToastUtil.showMessage(this, "单注金额不符合要求");

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
            String text = "距离开盘还有 " + Httputils.getStringToDate(nowtime);
            builder2 = new SpannableStringBuilder(text);
            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
            builder2.setSpan(redSpan2, 6, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
            String text = "距离封盘还有 " + Httputils.getStringToDate(nowtime);
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
                        if (status > 1) {
//                            shadows_layout.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams relativeLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                            shadows_layout.setLayoutParams(relativeLayout);
//                            shadows.setImageDrawable(getResources().getDrawable(res[1]));
                        } else {
                            baseApplication = (BaseApplication) getApplication();
                            qs.setText("当前第 " + baseApplication.getCurrentQs() + " 期");
//                            basefragments.get(viewpager.getCurrentItem()).CallbackFunction();
                            openTimer();
                        }
                    }
                });
    }

    public static void KeyBoard(final EditText txtSearchKey, final boolean status, int second) {

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
