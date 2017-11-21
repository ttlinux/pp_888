package com.a8android888.bocforandroid.activity.main.lottery.fucai3D;

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
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.ESZHListAdapter;
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
 * Created by Administrator on 2017/4/24.二字三组合
 */
public class ESZHFragment extends BaseFragment implements View.OnClickListener{

    public SparseArray<ArrayList<LotterycomitorderBean>> sparseArray;
    private ESZHListAdapter ESZHListAdapter;
    ListView lsitview2;
    GridView lsitview;
    String[] titles;
    RadioButton comit,reset;
    Boolean clickstate = false;
    TextView gamenametitletv;
    ArrayList<LotterycomitorderBean> jsonstring = new ArrayList<LotterycomitorderBean>();
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
                if(ESZHListAdapter!=null ) {
                    ESZHListAdapter.ClearRecordData();
                    ESZHListAdapter.NotifyAdapter(null,null);
                }
                openTimer();
            }
        }
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        comit=FindView(R.id.comit);
        reset=FindView(R.id.reset);
        reset.setOnClickListener(this);
        comit.setOnClickListener(this);

        lsitview2=FindView(R.id.lsitview2);
        gamenametitletv = (TextView) FindView(R.id.gamenametitletv);
        lsitview =(GridView)FindView(R.id.lsitview);

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
                if (LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("EZZH") || LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("SZZH")) {

                    String pl = "";
                    String titlestring = "";
                    jsonstring.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsobject = jsonArray.optJSONObject(i);

                        if (jsobject.optString("id").indexOf(LotteryFragment1.getGanmeitmexzlxcode()) != -1) {
                            LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
                            gameBean1.setUid(jsobject.optString("id", ""));
                            gameBean1.setNumber(jsobject.optString("number", ""));
                            gameBean1.setPl(jsobject.optString("pl", "1"));
                            gameBean1.setMinLimit(jsobject.optString("minLimit", "1"));
                            gameBean1.setMaxlimit(jsobject.optString("maxLimit", "1"));
                            gameBean1.setMaxPeriodLimit(jsobject.optString("maxPeriodLimit", "1"));
                            gameBean1.setSelected(false);
                            pl = jsobject.optString("pl");

                            jsonstring.add(gameBean1);
                        }
                    }

//                    gamenametitletv.setVisibility(View.VISIBLE);
//                    gamenametitletv.setText(titlestring);
                    titles = new String[jsonstring.size()];
                    sparseArray=new SparseArray<ArrayList<LotterycomitorderBean>>();
                    for (int i2 = 0; i2 < jsonstring.size(); i2++) {
                        for (int i1 = 0; i1 < 10; i1++) {
                            titles[i2] =jsonstring.get(i2).getNumber() + "@" + jsonstring.get(i2).getPl();

                            LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
                            gameBean1.setNumber(i1 + "");
                            gameBean1.setPl(pl);
                            gameBean1.setSelected(false);
                            gameBean1.setUid(titles[i2] );
                            String id = titles[i2];
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

        lsitview.setNumColumns(sparseArray.size());
        if(ESZHListAdapter==null)
        {
            ESZHListAdapter=new ESZHListAdapter(getActivity(),sparseArray,titles);
            lsitview.setAdapter(ESZHListAdapter);
        }
        else{
            ESZHListAdapter.NotifyAdapter(sparseArray,titles);
        }


    }


    public void StartActvity(ArrayList<LotterycomitorderBean> beans) {
        if (beans.size() > 0) {
            int insize = 0;
            int index=1;
            StringBuilder sdbNumber = new StringBuilder();
            for (int i1 = 0; i1 < titles.length; i1++) {
                StringBuilder sdbNumber3 = new StringBuilder();
                for (int i = 0; i < beans.size(); i++) {
                    if (beans.get(i).getUid().equalsIgnoreCase(titles[i1])) {
                        if (sdbNumber3.length() > 0) {
                            sdbNumber3.append(ESZHListAdapter.GetSelectItem().get(i).getNumber());
                        }else {
                            if (i1 > 0 && i1 < titles.length) {
                                index = index + 1;
                                LogTools.e("添加成功", index + "");
                            }
                            sdbNumber3.append(ESZHListAdapter.GetSelectItem().get(i).getNumber());
                            LogTools.e("添加成功2", sdbNumber3.toString() + "");
                        }
                    }
                }
                sdbNumber.append(sdbNumber3);
            }
            LogTools.e("添加成功3",sdbNumber.toString()+"");
            if (LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("EZZH")) {
                if (beans.size() == 2) {
                    if (sdbNumber.toString().substring(0, 1).equalsIgnoreCase(sdbNumber.toString().substring(1, 2)))
                        insize = getindex("ZH-EZZH_SAME-SAME");

                    else {
                        insize = getindex("ZH-EZZH_DIF-DIF");
                    }
                } else {
                    ToastUtil.showMessage(this.getActivity(),  "有且仅选择" +jsonstring.size() + "个");
                    return;
                }
            }
            if (LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("SZZH")) {
                if (beans.size() == 3){
                    if (sdbNumber.toString().substring(0, 1).equalsIgnoreCase(sdbNumber.toString().substring(1, 2)) &&
                            sdbNumber.toString().substring(0, 1).equalsIgnoreCase(sdbNumber.toString().substring(2, 3)))
                    {
                        insize =  getindex("ZH-SZZH_SZ-SZ");
                    }
                    else if (!sdbNumber.toString().substring(0, 1).equalsIgnoreCase(sdbNumber.toString().substring(1, 2)) &&
                            !sdbNumber.toString().substring(0, 1).equalsIgnoreCase(sdbNumber.toString().substring(2, 3))
                            &&!sdbNumber.toString().substring(1, 2).equalsIgnoreCase(sdbNumber.toString().substring(2, 3))){
                        insize =  getindex("ZH-SZZH_ZU6-ZU6");
                    }
                    else {
                        insize = getindex("ZH-SZZH_ZU3-ZU3");
                    }
                }
                else {
                    ToastUtil.showMessage(this.getActivity(), "有且仅选择" +jsonstring.size() + "个");
                    return;
                }
            }  LogTools.e("this.getActivity()", sdbNumber.toString() + "insize" + insize);
            ArrayList<LotterycomitorderBean> jsonstringaa = new ArrayList<LotterycomitorderBean>();
            LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
            gameBean1.setNumber(sdbNumber.toString());
            gameBean1.setPl(jsonstring.get(insize).getPl());
            gameBean1.setUid(jsonstring.get(insize).getUid());
            gameBean1.setMinLimit(jsonstring.get(insize).getMinLimit());
            gameBean1.setMaxlimit(jsonstring.get(insize).getMaxlimit());
            gameBean1.setMaxPeriodLimit(jsonstring.get(insize).getMaxPeriodLimit());
            jsonstringaa.add(gameBean1);
//
            Intent intent = new Intent(getActivity(), LotteryComitOrderActivity.class);
            intent.putExtra("title", LotteryFragment1.getGamename());
            intent.putExtra("gamecode", LotteryFragment1.getGamecode());
            intent.putExtra("normal", "normal");
            intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
            intent.putExtra("gamexzlxitemname",jsonstring.get(insize).getNumber());
            intent.putExtra("json", new Gson().toJson(jsonstringaa));
            startActivity(intent);
            LogTools.e("dadfadfadsfads" + LotteryFragment1.getGameitemname(), LotteryFragment1.getGameitemname() + "");
        } else {
            ToastUtil.showMessage(this.getActivity(),  "有且仅选择" +jsonstring.size() + "个");
        }
    }
    private  int getindex(String uid){
        int index=0;
        for (int i = 0; i <jsonstring.size() ; i++) {
            if(uid.equalsIgnoreCase(jsonstring.get(i).getUid()))
                index=i;
        }
        return index;
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
                if(ESZHListAdapter!=null) {
                    StartActvity(ESZHListAdapter.GetSelectItem());
                }
                break;
            case R.id.reset:
                if(ESZHListAdapter!=null){
                    ESZHListAdapter.ResetState();
                }
                break;
        }
    }
}
