package com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Lottery_tmfragment_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivityother;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.HttpForLottery;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5. 中&不中
 */
public class ZYBZFragment extends BaseFragment implements View.OnClickListener {
    GridView viewpager;
    Lottery_tmfragment_Adapter adapter;
    TextView reset,comit;
    private ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    List<String> list = new ArrayList<String>();
    List<String> tmlist = new ArrayList<String>();
    private ThreadTimer mTimer;
    public static   ArrayList<Object[]> chooselist;
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
        reset=(TextView)FindView(R.id.reset);
        reset.setOnClickListener(this);

        comit=(TextView)FindView(R.id.comit);
        comit.setOnClickListener(this);

    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if (state)
            ThreadTimer.Stop(getClass().getName());
        else
        {
            if(getActivity()!=null && getView()!=null )
            {
                ThreadTimer.Stop(getClass().getName());
                if(adapter!=null )
                {
                    adapter.ClearRecordData();
                    adapter.notifyStockdatalist(null);
                }

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
        Httputils.PostWithBaseUrl(Httputils.refreshRate, params, new MyJsonHttpResponseHandler(this.getActivity(), Lottery_MainActivity.needdialog()) {
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
                    LogTools.e("返回zmt", LotteryFragment1.getGanmeitmexzlxcode() + "");
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

                        if (LotteryFragment1.getGanmeitmexzlxcode().length() < 1) {
                            if (jsond.optString("id").indexOf("WBZ") != -1) {
                                lotterycomitorderBeans1.add(gameBean1);
                                LogTools.e("返回ZYBZ", lotterycomitorderBeans1.get(0).getUid().toString());
                            }
                        } else {
                            if (jsond.optString("id").indexOf(LotteryFragment1.getGanmeitmexzlxcode()) != -1) {
                                lotterycomitorderBeans1.add(gameBean1);
                                LogTools.e("返回ZTM" + LotteryFragment1.getGanmeitmexzlxcode(), lotterycomitorderBeans1.get(0).getUid().toString());
                            }
                        }

                    }

//                    for (int i = 0; i < list.size(); i++) {
//                        for (int i1 = 0; i1 < basefragments.size(); i1++) {
//                            if (list.get(i).equalsIgnoreCase(basefragments.get(i1).getNumber())) {
//                                LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
//                                gameBean1.setUid(basefragments.get(i1).getUid());
//                                gameBean1.setNumber(basefragments.get(i1).getNumber());
//                                gameBean1.setPl(basefragments.get(i1).getPl());
//                                basefragments2.add(gameBean1);
//                            }
//
//                        }
//                    }
                    StepComparator comparator = new StepComparator();
                    Collections.sort(lotterycomitorderBeans1, comparator);
                    basefragments.add(lotterycomitorderBeans1);
                    if (adapter == null) {
                        adapter = new Lottery_tmfragment_Adapter(ZYBZFragment.this.getActivity(), basefragments, "normal");
//                    LogTools.e("basefragments",new Gson().toJson(basefragments));
//                    LogTools.e("basefragments2",new Gson().toJson(basefragments2));
                        viewpager.setAdapter(adapter);
                    } else {
                        adapter.notifyStockdatalist(basefragments);
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
                StartActvity(adapter.GetSelectItem());
                break;
        }
    }

    public void StartActvity(ArrayList<LotterycomitorderBean> beans)
    {
        String multilen="0";
        if(LotteryFragment1.getGanmeitmexzlxname().equalsIgnoreCase("五不中")){
            multilen="5";
        }
        if(LotteryFragment1.getGanmeitmexzlxname().equalsIgnoreCase("七不中")){
            multilen="7";
        }
        if(LotteryFragment1.getGanmeitmexzlxname().equalsIgnoreCase("九不中")){
            multilen="9";
        }
        if(LotteryFragment1.getGanmeitmexzlxname().equalsIgnoreCase("四中一")){
            multilen="4";
        }
        if(LotteryFragment1.getGanmeitmexzlxname().equalsIgnoreCase("六中一")){
            multilen="6";
        }
        if(beans.size()>=Integer.valueOf(multilen)) {
            StringBuilder sdbNumber = new StringBuilder();
            StringBuilder sdbUid = new StringBuilder();
            String pl=adapter.GetSelectItem().get(0).getPl();
            for (int i = 0; i <adapter.GetSelectItem().size() ; i++) {
                if (adapter.GetSelectItem().get(i).getUid() != null) {
                    if (sdbUid.length() > 0) {
                        sdbUid.append(",").append(adapter.GetSelectItem().get(i).getUid());
                    } else {
                        sdbUid.append(adapter.GetSelectItem().get(i).getUid());
                    }
                }
                if (adapter.GetSelectItem().get(i).getNumber() != null) {
                    if (sdbNumber.length() > 0) {
                        sdbNumber.append(",").append(adapter.GetSelectItem().get(i).getNumber());
                    } else {
                        sdbNumber.append(adapter.GetSelectItem().get(i).getNumber());
                    }
                }
            }

            Intent intent = new Intent(getActivity(), LotteryComitOrderActivityother.class);
            intent.putExtra("title", LotteryFragment1.getGamename());
            intent.putExtra("gamecode", LotteryFragment1.getGamecode());
            intent.putExtra("normal", "mul");
            intent.putExtra("pl", pl);
            intent.putExtra("multilen",multilen);
            intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
            intent.putExtra("gameitemnamecode", LotteryFragment1.getGameitemcode());
            intent.putExtra("gamexzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
            intent.putExtra("gamexzlxitemcode", LotteryFragment1.getGanmeitmexzlxcode());
            intent.putExtra("jsonNumber", sdbNumber.toString());
            intent.putExtra("jsonUid", sdbUid.toString());
            startActivity(intent);
        }
        else{
            ToastUtil.showMessage(this.getActivity(), "至少选择" + multilen + "个");
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
