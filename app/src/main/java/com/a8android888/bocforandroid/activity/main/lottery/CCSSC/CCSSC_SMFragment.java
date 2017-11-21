package com.a8android888.bocforandroid.activity.main.lottery.CCSSC;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.a8android888.bocforandroid.Adapter.TwoListAdapter;
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
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24.双面
 */
public class CCSSC_SMFragment extends BaseFragment implements View.OnClickListener{

    public SparseArray<ArrayList<LotterycomitorderBean>> sparseArray;
    private ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    private TwoListAdapter twoListAdapter;
    ListView lsitview2;
    String[] titles;
    RadioButton comit,reset;
    Boolean clickstate = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        setOpenRefresh(true);
        return View.inflate(getActivity(), R.layout.tmfragment, null);
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
                if(twoListAdapter!=null )
                    if(twoListAdapter!=null ){
                        twoListAdapter.ClearRecordData();
                        twoListAdapter.NotifyAdapter(null,new String[]{LotteryFragment1.getGanmeitmexzlxname()} );
                    }

                openTimer();
            }
        }
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
        clickstate=true;
        if(twoListAdapter!=null)
            twoListAdapter.NotifyAdapter(null,CCSSC_SMFragment.this.getResources().getStringArray(R.array.ccssc_shuangmian));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        comit=FindView(R.id.comit);
        reset=FindView(R.id.reset);
        reset.setOnClickListener(this);
        comit.setOnClickListener(this);

        lsitview2=FindView(R.id.lsitview2);
        lsitview2.setVisibility(View.VISIBLE);
        GridView lsitview=FindView(R.id.lsitview);
        lsitview.setVisibility(View.GONE);

        openTimer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void openTimer(){
        GetData();
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
                GetData();
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

    private void GetData()
    {
        RequestParams params = new RequestParams();
        params.put("gameCode", LotteryFragment1.getGamecode());
        params.put("itemCode", LotteryFragment1.getGameitemcode());
        LogTools.e("GetData", new Gson().toJson(params));
        final ArrayList<String> keys=new ArrayList<String>();
        Httputils.PostWithBaseUrl(Httputils.refreshRate, params, new MyJsonHttpResponseHandler(getActivity(), Lottery_MainActivity.needdialog()) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                ArrayList<LotterycomitorderBean> jsonjects = null;
                int index = 0;
                sparseArray=new SparseArray<ArrayList<LotterycomitorderBean>>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsobject = jsonArray.optJSONObject(i);

                    LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
                    gameBean1.setUid(jsobject.optString("id", ""));
                    gameBean1.setNumber(jsobject.optString("number", ""));
                    gameBean1.setPl(jsobject.optString("pl", "1"));

                    gameBean1.setMinLimit(jsobject.optString("minLimit", "1"));
                    gameBean1.setMaxlimit(jsobject.optString("maxLimit", "1"));
                    gameBean1.setMaxPeriodLimit(jsobject.optString("maxPeriodLimit", "1"));
                    gameBean1.setSelected(false);
//                    if (jsobject.optString("id").indexOf("DXDS-WW") != -1)
//                        lotterycomitorderBeans1.add(gameBean1);
//                    if (jsobject.optString("id").indexOf("DXDS-QW") != -1)
//                        lotterycomitorderBeans2.add(gameBean1);
//                    if (jsobject.optString("id").indexOf("DXDS-BW") != -1)
//                        lotterycomitorderBeans3.add(gameBean1);
//                    if (jsobject.optString("id").indexOf("DXDS-SW") != -1)
//                        lotterycomitorderBeans4.add(gameBean1);
//                    if (jsobject.optString("id").indexOf("DXDS-GW") != -1)
//                        lotterycomitorderBeans5.add(gameBean1);
//                    if (jsobject.optString("id").indexOf("DXDS-ZHLHH") != -1)
//                        lotterycomitorderBeans6.add(gameBean1);
                    String id = jsobject.optString("id", "").substring(0, 7);
                    int result = isexits(keys, id);
                    LogTools.e("result",result+"");
                    int iii=0;
                    if (id.indexOf("DXDS-WW") != -1)
                        iii=0;
                    if (id.indexOf("DXDS-QW") != -1)
                        iii=1;
                    if (id.indexOf("DXDS-BW") != -1)
                        iii=2;
                    if (id.indexOf("DXDS-SW") != -1)
                        iii=3;
                    if (id.indexOf("DXDS-GW") != -1)
                        iii=4;
                    if (id.indexOf("DXDS-ZH") != -1)
                        iii=5;
                    if (result < 0) {
                        keys.add(id);
                        jsonjects = new ArrayList<LotterycomitorderBean>();
                        sparseArray.put(iii, jsonjects);
                        sparseArray.get(iii).add(gameBean1);
                    } else {
                        sparseArray.get(iii).add(gameBean1);
                    }
                }
                SetAdapter();
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);

            }
        });
    }

    private int  isexits(ArrayList<String> keys,String key)
    {
        for (int i = 0; i < keys.size(); i++) {
            if(key.equalsIgnoreCase(keys.get(i)))
            {
                return i;
            }
        }
        return -1;
    }

    public void SetAdapter()
    {
        titles=CCSSC_SMFragment.this.getResources().getStringArray(R.array.ccssc_shuangmian);
        if(sparseArray==null)return;
        if(twoListAdapter==null)
        {
            twoListAdapter=new TwoListAdapter(getActivity(),sparseArray,titles);
            lsitview2.setAdapter(twoListAdapter);
        }
        else
            twoListAdapter.NotifyAdapter(sparseArray,CCSSC_SMFragment.this.getResources().getStringArray(R.array.ccssc_shuangmian));

    }

public void StartActivity(ArrayList<LotterycomitorderBean> beans)
        {
        if(beans.size()>0) {
        Intent intent = new Intent(getActivity(), LotteryComitOrderActivity.class);
        intent.putExtra("title", LotteryFragment1.getGamename());
        intent.putExtra("gamecode", LotteryFragment1.getGamecode());
        intent.putExtra("normal", "normal");
        intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
        intent.putExtra("gamexzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
        intent.putExtra("json", new Gson().toJson(beans));
        startActivity(intent);
        LogTools.e("dadfadfadsfads"+ LotteryFragment1.getGanmeitmexzlxname(),LotteryFragment1.getGameitemname() + "");
        }
        else{
        ToastUtil.showMessage(this.getActivity(), "至少选择一个");
        }
        }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.comit:
                String Username =((BaseApplication)this.getActivity().getApplication()).getBaseapplicationUsername();
                if (Username == null || Username.equalsIgnoreCase("")) {
                    ToastUtil.showMessage(this.getActivity(), "未登录");
                    Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                    this.getActivity().startActivity(intent);
                    return;
                }
                if(twoListAdapter!=null) {
                    StartActivity(twoListAdapter.GetSelectItem());
                }
                break;
            case R.id.reset:
                if(twoListAdapter!=null){
                    twoListAdapter.ResetState();
                }
                break;
        }
    }
}
