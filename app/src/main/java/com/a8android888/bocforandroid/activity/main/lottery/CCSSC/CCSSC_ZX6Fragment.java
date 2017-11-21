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

import com.a8android888.bocforandroid.Adapter.CCSSC_zxListAdapter;
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
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24.//一字定位
 */
public class CCSSC_ZX6Fragment extends BaseFragment implements View.OnClickListener{

    public SparseArray<ArrayList<LotterycomitorderBean>> sparseArray;
    private CCSSC_zxListAdapter CCSSC_zxListAdapter;
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
                if(CCSSC_zxListAdapter!=null )
                    if(CCSSC_zxListAdapter!=null ){
                        CCSSC_zxListAdapter.ClearRecordData();
                        CCSSC_zxListAdapter.NotifyAdapter(null);
                    }

                openTimer();
            }
        }
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

                    if (jsobject.optString("id").indexOf(LotteryFragment1.getGanmeitmexzlxcode()) != -1) {
                        String id = jsobject.optString("id", "").substring(0, 7);
                        int result = isexits(keys, id);
                        if (result < 0) {
                            keys.add(id);
                            jsonjects = new ArrayList<LotterycomitorderBean>();
                            sparseArray.put(keys.size() - 1, jsonjects);
                            sparseArray.get(keys.size() - 1).add(gameBean1);
                        } else {

                            sparseArray.get(result).add(gameBean1);
                        }
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
        titles = new String[]{LotteryFragment1.getGanmeitmexzlxname()};
        if(CCSSC_zxListAdapter==null)
        {
            CCSSC_zxListAdapter=new CCSSC_zxListAdapter(getActivity(),sparseArray,titles);
            lsitview2.setAdapter(CCSSC_zxListAdapter);
        }
        else
            CCSSC_zxListAdapter.NotifyAdapter(sparseArray);

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
                   if (CCSSC_zxListAdapter != null) {
                    StartActvity(CCSSC_zxListAdapter.GetSelectItem());
                }
                break;
            case R.id.reset:
                if(CCSSC_zxListAdapter!=null){
                    CCSSC_zxListAdapter.ResetState();
                }
                break;
        }
    }
    public void StartActvity(ArrayList<LotterycomitorderBean> beans)
    {
        String  multilen="6";
        if(beans.size()>=4) {
            StringBuilder sdbNumber = new StringBuilder();
            StringBuilder sdbUid = new StringBuilder();
            String pl=CCSSC_zxListAdapter.GetSelectItem().get(0).getPl();
            for (int i = 0; i <CCSSC_zxListAdapter.GetSelectItem().size() ; i++) {
                if (CCSSC_zxListAdapter.GetSelectItem().get(i).getUid() != null) {
                    if (sdbUid.length() > 0) {
                        sdbUid.append(",").append(CCSSC_zxListAdapter.GetSelectItem().get(i).getUid());
                    } else {
                        sdbUid.append(CCSSC_zxListAdapter.GetSelectItem().get(i).getUid());
                    }
                }
                if (CCSSC_zxListAdapter.GetSelectItem().get(i).getNumber() != null) {
                    if (sdbNumber.length() > 0) {
                        sdbNumber.append(",").append(CCSSC_zxListAdapter.GetSelectItem().get(i).getNumber());
                    } else {
                        sdbNumber.append(CCSSC_zxListAdapter.GetSelectItem().get(i).getNumber());
                    }
                }
            }

            Intent intent = new Intent(getActivity(), LotteryComitOrderActivityother.class);
            intent.putExtra("title", LotteryFragment1.getGamename());
            intent.putExtra("gamecode", LotteryFragment1.getGamecode());
            intent.putExtra("normal", "group");
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
            ToastUtil.showMessage(this.getActivity(), "至少选择4个");
        }
    }
}
