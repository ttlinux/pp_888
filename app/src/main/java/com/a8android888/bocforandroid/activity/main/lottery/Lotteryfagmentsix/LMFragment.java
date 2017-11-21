package com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Lottery_lmtmfragment_Adapter;
import com.a8android888.bocforandroid.Adapter.Lottery_zm1_6mfragment_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivityother;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.MyScrollView;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5. 连码
 */
public class LMFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    GridView viewpager;
    Lottery_lmtmfragment_Adapter adapter;
    RelativeLayout relayout;
    TextView reset, comit;
    private ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    ArrayList<String> list = new ArrayList<String>();
    private ThreadTimer mTimer;
    public static ArrayList<Object[]> chooselist;
    MyScrollView scrollview;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, danyi, daner;
    String multilen = "0", pabc = "1", dm1 = "", dm2 = "";
    int size = 3;
    LinearLayout layoudan1, layoudan2;
    RadioButton sanquanzhong, sanzhonger, erquanzhong, erzhongte, techun, fushi, dantuo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        setOpenRefresh(true);
        return View.inflate(getActivity(), R.layout.lmtmfragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager = (GridView) FindView(R.id.lsitview);
        reset = (TextView) FindView(R.id.reset);
        reset.setOnClickListener(this);
        scrollview = (MyScrollView) FindView(R.id.scrollview);
        scrollview.smoothScrollTo(0, 0);
        comit = (TextView) FindView(R.id.comit);
        comit.setOnClickListener(this);
        relayout=(RelativeLayout)FindView(R.id.relayout);
//        relayout.setFocusable(true);
//        relayout.setFocusableInTouchMode(true);
//        relayout.requestFocus();
        textView1 = (TextView) FindView(R.id.TextView1);
        textView2 = (TextView) FindView(R.id.TextView2);
        textView3 = (TextView) FindView(R.id.TextView3);
        textView4 = (TextView) FindView(R.id.TextView4);
        textView5 = (TextView) FindView(R.id.TextView5);
        textView6 = (TextView) FindView(R.id.TextView6);
        textView7 = (TextView) FindView(R.id.TextView7);
        layoudan1 = (LinearLayout) FindView(R.id.layoudan1);
        layoudan2 = (LinearLayout) FindView(R.id.layoudan2);

        dantuo = (RadioButton) FindView(R.id.dantuo);
        dantuo.setOnCheckedChangeListener(this);
        sanquanzhong = (RadioButton) FindView(R.id.sanquanzhong);
        sanquanzhong.setOnCheckedChangeListener(this);
        sanquanzhong.setChecked(true);
        sanzhonger = (RadioButton) FindView(R.id.sanzhonger);
        sanzhonger.setOnCheckedChangeListener(this);
        erquanzhong = (RadioButton) FindView(R.id.erquanzhong);
        erquanzhong.setOnCheckedChangeListener(this);
        erzhongte = (RadioButton) FindView(R.id.erzhongte);
        erzhongte.setOnCheckedChangeListener(this);
        techun = (RadioButton) FindView(R.id.techun);
        techun.setOnCheckedChangeListener(this);
        fushi = (RadioButton) FindView(R.id.fushi);
        fushi.setOnCheckedChangeListener(this);
        fushi.setChecked(true);
        danyi = (TextView) FindView(R.id.danyi);
        daner = (TextView) FindView(R.id.daner);

    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if (state)
            ThreadTimer.Stop(getClass().getName());
        else {
            if (getActivity() != null && getView() != null) {
                ThreadTimer.Stop(getClass().getName());
                if (adapter != null)
                    adapter.ResetState();
                openTimer();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
        public void clickfragment() {
        super.clickfragment();
    }


    private void getdatalist() {
        RequestParams params = new RequestParams();
        params.put("gameCode", LotteryFragment1.getGamecode());
        params.put("itemCode", LotteryFragment1.getGameitemcode());
        Httputils.PostWithBaseUrl(Httputils.refreshRate, params, new MyJsonHttpResponseHandler(LMFragment.this.getActivity(), Lottery_MainActivity.needdialog()) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("返回ZTM", jsonObject.toString());
//                JSONObject json = jsonObject.optJSONObject("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray jsonArray = jsonObject.optJSONArray("datas");
                    LogTools.e("ZM1T6", LotteryFragment1.getGanmeitmexzlxcode() + "");
                    basefragments = new ArrayList<ArrayList<LotterycomitorderBean>>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans1 = new ArrayList<LotterycomitorderBean>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsond = jsonArray.getJSONObject(i);
                        LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
                        gameBean1.setUid(jsond.optString("id"));
                        gameBean1.setNumber(jsond.optString("number"));
                        gameBean1.setPl(jsond.optString("pl"));
                        gameBean1.setMinLimit(jsond.optString("minLimit"));
                        gameBean1.setMaxlimit(jsond.optString("maxLimit"));
                        gameBean1.setMaxPeriodLimit(jsond.optString("maxPeriodLimit"));
                        gameBean1.setSelected(false);
                        lotterycomitorderBeans1.add(gameBean1);
                    }

                    basefragments.add(lotterycomitorderBeans1);
                    textView1.setText(basefragments.get(0).get(3).getPl());
                    textView2.setText(basefragments.get(0).get(4).getPl());
                    textView3.setText(basefragments.get(0).get(5).getPl());
                    textView4.setText(basefragments.get(0).get(0).getPl());
                    textView5.setText(basefragments.get(0).get(1).getPl());
                    textView6.setText(basefragments.get(0).get(2).getPl());
                    textView7.setText(basefragments.get(0).get(6).getPl());
                    if (adapter == null) {
                        adapter = new Lottery_lmtmfragment_Adapter
                                (LMFragment.this.getActivity());
                        adapter.setOnSelectListener(new Lottery_lmtmfragment_Adapter.OnSelectListener() {
                            @Override
                            public void OnSelected(ArrayList<LotterycomitorderBean> beans) {
                                SetShowData(beans);
                            }
                        });
                        viewpager.setAdapter(adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
                return super.parseResponse(s);
            }
        });
    }

    private void SetShowData(ArrayList<LotterycomitorderBean> beans)
    {
        LogTools.e("LotterycomitorderBean", new Gson().toJson(beans));

        if(beans.size()<1)
        {
            danyi.setText("");
            return;
        }
        if(pabc.equalsIgnoreCase("2"))
        {
            if(multilen.equalsIgnoreCase("0") || multilen.equalsIgnoreCase("1"))
            {
                danyi.setText(beans.get(0).getNumber());
                dm1=(beans.get(0).getNumber());
                if(beans.size()<2)
                {
                    daner.setText("");
                    return;
                }
                daner.setText(beans.get(1).getNumber());
                dm2=(beans.get(1).getNumber());
            }
            if(multilen.equalsIgnoreCase("2") || multilen.equalsIgnoreCase("3") || multilen.equalsIgnoreCase("4"))
            {
                danyi.setText(beans.get(0).getNumber());
                dm1=(beans.get(0).getNumber());
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sanquanzhong:
                if (isChecked) {
                    if(adapter!=null)adapter.ResetState();
                    dm1="";
                    dm2="";
                    multilen = "0";
                    size = 3;
                    if (dantuo.isChecked()) {
                        //胆拖
                        layoudan1.setVisibility(View.VISIBLE);
                        layoudan2.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.sanzhonger:
                if (isChecked) {
                    if(adapter!=null)adapter.ResetState();
                    multilen = "1";
                    dm1="";
                    dm2="";
                    size = 3;
                    if (dantuo.isChecked()) {

                        layoudan1.setVisibility(View.VISIBLE);
                        layoudan2.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.erquanzhong:
                if (isChecked) {
                    if(adapter!=null)adapter.ResetState();
                    multilen = "2";
                    dm1="";
                    dm2="";
                    size = 2;
                    if (dantuo.isChecked()) {
                        layoudan1.setVisibility(View.VISIBLE);
                        layoudan2.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.erzhongte:
                if (isChecked) {
                    if(adapter!=null)adapter.ResetState();
                    multilen = "3";
                    dm1="";
                    dm2="";
                    size = 2;
                        if (dantuo.isChecked()) {

                        layoudan1.setVisibility(View.VISIBLE);
                        layoudan2.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.techun:
                if (isChecked) {
                    if(adapter!=null)adapter.ResetState();
                    multilen = "4";
                    dm1="";
                    dm2="";
                    size = 2;
                    if (dantuo.isChecked()) {
                        layoudan1.setVisibility(View.VISIBLE);
                        layoudan2.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.fushi:
                if (isChecked) {
                    pabc = "1";
                    dm1="";
                    dm2="";
                    layoudan1.setVisibility(View.GONE);
                    layoudan2.setVisibility(View.GONE);
                        //复式
                        if(adapter!=null)adapter.ResetState();
                }
                break;
            case R.id.dantuo:
                if (isChecked) {
                    daner.setText("");
                    danyi.setText("");
                    dm1="";
                    dm2="";
                    pabc = "2";
                    if(adapter!=null)adapter.ResetState();
                    if (multilen.equalsIgnoreCase("0") || multilen.equalsIgnoreCase("1")) {
                        layoudan1.setVisibility(View.VISIBLE);
                        layoudan2.setVisibility(View.VISIBLE);
                    } else {
                        layoudan1.setVisibility(View.VISIBLE);
                        layoudan2.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                        if(adapter!=null)
                            adapter.ResetState();
                break;
            case R.id.comit:
                String Username =((BaseApplication)this.getActivity().getApplication()).getBaseapplicationUsername();
                if (Username == null || Username.equalsIgnoreCase("")) {
                    ToastUtil.showMessage(this.getActivity(), "未登录");
                    Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                    this.getActivity().startActivity(intent);
                    return;
                }
                StartActvity(adapter.GetSelectItem());
                break;
        }
    }

    public void StartActvity(ArrayList<LotterycomitorderBean> beans) {
        if (beans.size() >= size) {
            StringBuilder sdbNumber = new StringBuilder();
            String pl = adapter.GetSelectItem().get(0).getPl();
            String itemname="";
            for (int i = 0; i < adapter.GetSelectItem().size(); i++) {
                if (adapter.GetSelectItem().get(i).getNumber() != null) {
                    if (sdbNumber.length() > 0) {
                        sdbNumber.append(",").append(adapter.GetSelectItem().get(i).getNumber());
                    } else {
                        sdbNumber.append(adapter.GetSelectItem().get(i).getNumber());
                    }
                }
            }
      if(multilen.equalsIgnoreCase("0")){
          itemname="三全中";
      }
            if(multilen.equalsIgnoreCase("1")){
                itemname="三中二";
            }
            if(multilen.equalsIgnoreCase("2")){
                itemname="二全中 ";
            }
            if(multilen.equalsIgnoreCase("3")){
                itemname="二中特";
            }
            if(multilen.equalsIgnoreCase("4")){
                itemname="特串";
            }
            Intent intent = new Intent(getActivity(), LotteryComitOrderActivityother.class);
            intent.putExtra("title", LotteryFragment1.getGamename());
            intent.putExtra("gamecode", LotteryFragment1.getGamecode());
            intent.putExtra("normal", "lm");
            intent.putExtra("pl", pl);
            intent.putExtra("multilen", multilen);
            intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
            intent.putExtra("gameitemnamecode", LotteryFragment1.getGameitemcode());


            intent.putExtra("gamexzlxitemname", itemname);



            intent.putExtra("gamexzlxitemcode", LotteryFragment1.getGanmeitmexzlxcode());
            intent.putExtra("jsonNumber", sdbNumber.toString());
            intent.putExtra("dm2", dm2);
            intent.putExtra("pabc", pabc);
            intent.putExtra("dm1", dm1);
            startActivity(intent);
        } else {
            ToastUtil.showMessage(this.getActivity(), "至少选择" + size + "个");
        }
    }


    private void openTimer(){
        getdatalist();
        ThreadTimer.setListener2(new ThreadTimer.OnActiviteListener2() {
            @Override
            public void MinCallback(long limitmin, long close, long open) {
                LotteryFragment1 fragment1 = (LotteryFragment1) getParentFragment();
                if (open == 0) {
                    ThreadTimer.Stop();
                }
                fragment1.gettime(close, open);
            }

            @Override
            public void DelayCallback() {
                getdatalist();
            }
        });
        BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();

        long close=baseApplication.getClosetime()-baseApplication.getNowtime();
        long now=baseApplication.getOpentime()-baseApplication.getNowtime();
        ThreadTimer.Start(30 * 1000, close * 1000, now * 1000);
    }
    @Override
    public void CallbackFunction() {
        super.CallbackFunction();
        openTimer();
    }

    class StepComparator implements Comparator<LotterycomitorderBean> {

        /**
         * 如果o1小于o2,返回一个负数;如果o1大于o2，返回一个正数;如果他们相等，则返回0;
         */

        @Override
        public int compare(LotterycomitorderBean lhs, LotterycomitorderBean rhs) {
            if (Integer.valueOf(lhs.getNumber()) > Integer.valueOf(rhs.getNumber())) {
                return 1;
            }
            return -1;
        }
    }
}
