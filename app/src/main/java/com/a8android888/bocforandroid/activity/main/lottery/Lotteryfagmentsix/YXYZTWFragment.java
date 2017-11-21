package com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Lottery_gg_mfragment_Adapter;
import com.a8android888.bocforandroid.Adapter.Lottery_tmfragment_Adapter;
import com.a8android888.bocforandroid.Adapter.Lottery_yxztwtmfragment_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.Getlistheight;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5. 一肖正特尾
 */
public class YXYZTWFragment extends BaseFragment implements View.OnClickListener {
    GridView viewpager;
    ListView listView;
    Lottery_tmfragment_Adapter adapter;
    Lottery_yxztwtmfragment_Adapter adapter2;
    TextView reset,comit;
    private ArrayList<ArrayList<LotterycomitorderBean>> basefragments,basefragments2;
    List<String> list = new ArrayList<String>();
    List<String> tmlist = new ArrayList<String>();
    private ThreadTimer mTimer;
    public static   ArrayList<Object[]> chooselist;
    ScrollView scrollview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        setOpenRefresh(true);
        return View.inflate(getActivity(), R.layout.yxztwtmfragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager = (GridView) FindView(R.id.lsitview);
        reset=(TextView)FindView(R.id.reset);
        reset.setOnClickListener(this);
        listView=(ListView)FindView(R.id.lsitview2);
        listView.setVisibility(View.VISIBLE);
        comit=(TextView)FindView(R.id.comit);
        comit.setOnClickListener(this);
        scrollview=(ScrollView)FindView(R.id.scrollview);
        scrollview.smoothScrollTo(0, 0);
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
                if(adapter2!=null )
                {
                    adapter2.notifyStockdatalist(null);
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
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray jsonArray = jsonObject.optJSONArray("datas");
                    basefragments = new ArrayList<ArrayList<LotterycomitorderBean>>();
                    basefragments2 = new ArrayList<ArrayList<LotterycomitorderBean>>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans1 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans2 = new ArrayList<LotterycomitorderBean>();
                    BaseApplication baseApplication = (BaseApplication) getActivity().getApplication();
                    JSONObject jsonObject1 = baseApplication.getJsonObjectanimal();
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
                            if (jsond.optString("id").indexOf("ZTWXZ") != -1)
                                lotterycomitorderBeans1.add(gameBean1);
                            if (jsond.optString("id").indexOf("YXXZ") != -1) {
                                List<String> data = new ArrayList<String>();
                                if (jsond.optString("id").indexOf("-YANG") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("YANG"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }

                                if (jsond.optString("id").indexOf("-GOU") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("GOU"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }
                                if (jsond.optString("id").indexOf("-ZHU") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("ZHU"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }
                                if (jsond.optString("id").indexOf("-LONG") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("LONG"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }

                                if (jsond.optString("id").indexOf("-HOU") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("HOU"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }

                                if (jsond.optString("id").indexOf("-TU") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("TU"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }
                                if (jsond.optString("id").indexOf("-SHE") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("SHE"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }

                                if (jsond.optString("id").indexOf("-NIU") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("NIU"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }

                                if (jsond.optString("id").indexOf("-JI") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("JI"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }
                                if (jsond.optString("id").indexOf("-MA") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("MA"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }
                                if (jsond.optString("id").indexOf("-SHU") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("SHU"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }
                                if (jsond.optString("id").indexOf("-HU") != -1) {
                                    String[] aa = Httputils.convertStrToArray(jsonObject1.optString("HU"));
                                    for (int i1 = 0; i1 < aa.length; i1++) {
                                        data.add(String.valueOf(aa[i1]));
                                    }
                                }
                                gameBean1.setDatacode(data);
                                lotterycomitorderBeans2.add(gameBean1);
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
                    basefragments.add(lotterycomitorderBeans1);
                    basefragments2.add(lotterycomitorderBeans2);
                    if (adapter == null) {
                        adapter = new Lottery_tmfragment_Adapter(YXYZTWFragment.this.getActivity(), basefragments, "normal");
                        viewpager.setAdapter(adapter);
                    } else {
                        adapter.notifyStockdatalist(basefragments);
                    }
                    Getlistheight.setListViewHeightBasedOnChildren(viewpager);

                    if (adapter2 == null) {
                        adapter2 = new Lottery_yxztwtmfragment_Adapter(YXYZTWFragment.this.getActivity(), basefragments2, "normal");
//                    LogTools.e("basefragments",new Gson().toJson(basefragments));
//                    LogTools.e("basefragments2",new Gson().toJson(basefragments2));
                        listView.setAdapter(adapter2);
                    } else {
                        adapter2.notifyStockdatalist(basefragments2);
                    }

                    Getlistheight.setListViewHeightBasedOnChildren(listView);
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
                if(adapter2!=null){
                    adapter2.ResetState();
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
                ArrayList<LotterycomitorderBean> beans=new ArrayList<LotterycomitorderBean>();
                for (int i = 0; i <adapter.GetSelectItem().size() ; i++) {
                    beans.add(adapter.GetSelectItem().get(i));
                }
                for (int i1 = 0; i1 <adapter2.GetSelectItem().size() ; i1++) {
                    beans.add(adapter2.GetSelectItem().get(i1));
                }
                StartActvity(beans);
                break;
        }
    }

    public void StartActvity(ArrayList<LotterycomitorderBean> beans) {
        if (beans.size() > 0) {

            Intent intent = new Intent(getActivity(), LotteryComitOrderActivity.class);
            intent.putExtra("title", LotteryFragment1.getGamename());
            intent.putExtra("gamecode", LotteryFragment1.getGamecode());
            intent.putExtra("normal", "normal");
            intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
            intent.putExtra("gamexzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
            intent.putExtra("json", new Gson().toJson(beans));
            startActivity(intent);
        } else {
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
