package com.a8android888.bocforandroid.activity.main.lottery.lotterypk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Lottery_pksm_mfragment_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5. 双面
 */
public class SMFragment extends BaseFragment implements View.OnClickListener {
    GridView viewpager;
    ListView listView;
    Lottery_pksm_mfragment_Adapter adapter;
    TextView reset,comit;
    private ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    List<String> list = new ArrayList<String>();
    List<String> tmlist = new ArrayList<String>();
    private ThreadTimer mTimer;
    public static   ArrayList<Object[]> chooselist;
    Boolean clickstate=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        setOpenRefresh(true);
        return View.inflate(getActivity(), R.layout.tmfragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager = (GridView) FindView(R.id.lsitview);
        viewpager.setVisibility(View.GONE);
        listView=(ListView)FindView(R.id.lsitview2);
        listView.setVisibility(View.VISIBLE);
        reset=(TextView)FindView(R.id.reset);
        reset.setOnClickListener(this);

        comit=(TextView)FindView(R.id.comit);
        comit.setOnClickListener(this);
        openTimer();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        LogTools.e("OnViewShowOrHide", state + " " + getClass().getName());
        if (state)
            ThreadTimer.Stop(getClass().getName());
        else
        {
            if(getActivity()!=null && getView()!=null )
            {
                ThreadTimer.Stop(getClass().getName());
                if(adapter!=null )
                    adapter.notifyStockdatalist(null);
                openTimer();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(BundleTag.RequestCode==requestCode)
        {

        }
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
    }

    private void getdatalist() {
        RequestParams params = new RequestParams();
        params.put("gameCode", LotteryFragment1.getGamecode());
        params.put("itemCode", LotteryFragment1.getGameitemcode());
        Httputils.PostWithBaseUrl(Httputils.refreshRate, params, new MyJsonHttpResponseHandler(this.getActivity(), Lottery_MainActivity.needdialog()) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("返回ZTM", jsonObject.toString());
//                JSONObject json = jsonObject.optJSONObject("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray jsonArray = jsonObject.optJSONArray("datas");
                    if (basefragments == null)
                        basefragments = new ArrayList<ArrayList<LotterycomitorderBean>>();
                    else
                        basefragments.clear();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans1 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans2 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans3 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans4 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans5 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans6 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans7 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans8 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans9 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans10 = new ArrayList<LotterycomitorderBean>();
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

                        if (LotteryFragment1.getGanmeitmexzlxcode().length() < 1) {
                            if (jsond.optString("id").indexOf("GJ") != -1)
                                lotterycomitorderBeans1.add(gameBean1);
                            if (jsond.optString("id").indexOf("YJ") != -1)
                                lotterycomitorderBeans2.add(gameBean1);
                            if (jsond.optString("id").indexOf("JJ") != -1)
                                lotterycomitorderBeans3.add(gameBean1);
                            if (jsond.optString("id").indexOf("D4M") != -1)
                                lotterycomitorderBeans4.add(gameBean1);
                            if (jsond.optString("id").indexOf("D5M") != -1)
                                lotterycomitorderBeans5.add(gameBean1);
                            if (jsond.optString("id").indexOf("D6M") != -1)
                                lotterycomitorderBeans6.add(gameBean1);
                            if (jsond.optString("id").indexOf("D7M") != -1)
                                lotterycomitorderBeans7.add(gameBean1);
                            if (jsond.optString("id").indexOf("D8M") != -1)
                                lotterycomitorderBeans8.add(gameBean1);
                            if (jsond.optString("id").indexOf("D9M") != -1)
                                lotterycomitorderBeans9.add(gameBean1);
                            if (jsond.optString("id").indexOf("D10M") != -1)
                                lotterycomitorderBeans10.add(gameBean1);


                        }

                    }
                    basefragments.add(lotterycomitorderBeans1);
                    basefragments.add(lotterycomitorderBeans2);
                    basefragments.add(lotterycomitorderBeans3);
                    basefragments.add(lotterycomitorderBeans4);
                    basefragments.add(lotterycomitorderBeans5);
                    basefragments.add(lotterycomitorderBeans6);
                    basefragments.add(lotterycomitorderBeans7);
                    basefragments.add(lotterycomitorderBeans8);
                    basefragments.add(lotterycomitorderBeans9);
                    basefragments.add(lotterycomitorderBeans10);
                    if (adapter == null) {
                        adapter = new Lottery_pksm_mfragment_Adapter(SMFragment.this.getActivity(), (ArrayList<ArrayList<LotterycomitorderBean>>) basefragments.clone(), LotteryFragment1.getGanmeitmexzlxcode());
//                    LogTools.e("basefragments",new Gson().toJson(basefragments));
//                    LogTools.e("basefragments2",new Gson().toJson(basefragments2));
                        listView.setAdapter(adapter);
                    } else {
                        adapter.notifyStockdatalist((ArrayList<ArrayList<LotterycomitorderBean>>) basefragments.clone());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reset:
        if(adapter!=null){
            adapter.ResetState();
        }
        break;
        case R.id.comit:
            String Username =((BaseApplication)this.getActivity().getApplication()).getBaseapplicationUsername();
            if (Username == null || Username.equalsIgnoreCase("")) {
                ToastUtil.showMessage(this.getActivity(), "未登录");
                Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                this.getActivity().startActivity(intent);
                return;
            }
            if(adapter!=null)
        StartActivity(adapter.GetSelectItem());
        break;
    }
}

    private void StartActivity(ArrayList<LotterycomitorderBean> beans)
    {
        if(beans.size()>0) {
            Intent intent = new Intent(getActivity(), LotteryComitOrderActivity.class);
            intent.putExtra("title", LotteryFragment1.getGamename());
            intent.putExtra("gamecode", LotteryFragment1.getGamecode());
            intent.putExtra("normal", "normal");
            intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
            intent.putExtra("gamexzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
            intent.putExtra("json", new Gson().toJson(beans));
            startActivityForResult(intent, BundleTag.RequestCode);
        }
        else{
            ToastUtil.showMessage(this.getActivity(), "至少选择一个");
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
