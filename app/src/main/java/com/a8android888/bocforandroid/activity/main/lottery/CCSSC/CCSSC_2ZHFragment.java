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
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.CCSSC_zxListAdapter;
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
 * Created by Administrator on 2017/4/24.二字组合
 */
public class CCSSC_2ZHFragment extends BaseFragment implements View.OnClickListener{

    public SparseArray<ArrayList<LotterycomitorderBean>> sparseArray;
    private CCSSC_zxListAdapter CCSSC_zxListAdapter;
    ListView lsitview2;
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
        lsitview2.setVisibility(View.VISIBLE);
        GridView lsitview=FindView(R.id.lsitview);
        lsitview.setVisibility(View.GONE);
        gamenametitletv = (TextView) FindView(R.id.gamenametitletv);

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
                String pl = "";
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
                    pl = jsobject.optString("pl");
//                    String id = jsobject.optString("id", "").substring(0, 7);
//                    int result = isexits(keys, id);
//                    if (result < 0) {
//                        keys.add(id);
//                        jsonjects = new ArrayList<LotterycomitorderBean>();
//                        sparseArray.put(keys.size() - 1, jsonjects);
//                        sparseArray.get(keys.size() - 1).add(gameBean1);
//                    } else {
//
//                        sparseArray.get(result).add(gameBean1);
//                    }
                    jsonstring.add(gameBean1);
                }
                gamenametitletv.setVisibility(View.GONE);
                sparseArray=new SparseArray<ArrayList<LotterycomitorderBean>>();
                if(LotteryFragment1.getGanmeitmexzlxname().length()<1){
                    gamenametitletv.setText(LotteryFragment1.getGameitemname() + "@" + pl);
                }
           else {
                    gamenametitletv.setText(LotteryFragment1.getGanmeitmexzlxname() + "@" + pl);
                }

                titles=CCSSC_2ZHFragment.this.getResources().getStringArray(R.array.ccssc_dingwei);
                for (int i2 = 0; i2 < titles.length; i2++) {
                    for (int i1 = 0; i1 < 10; i1++) {
                        LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
                        gameBean1.setNumber(i1 + "");
                        gameBean1.setPl(pl);
                        gameBean1.setUid(titles[i2]);
                        gameBean1.setSelected(false);
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
        titles =CCSSC_2ZHFragment.this.getResources().getStringArray(R.array.ccssc_dingwei);
        if(CCSSC_zxListAdapter==null)
        {
            CCSSC_zxListAdapter=new CCSSC_zxListAdapter(getActivity(),sparseArray,titles);
            lsitview2.setAdapter(CCSSC_zxListAdapter);
        }
        else
            CCSSC_zxListAdapter.NotifyAdapter(sparseArray);

    }

    public void StartActvity(ArrayList<LotterycomitorderBean> beans) {
        LogTools.e("beans",beans.size()+"");
        if (beans.size() > 0) {
            int index=0;
            int  index3=1;

            StringBuilder sdbNumber3 = new StringBuilder();
            for (int i1 = 0; i1 < titles.length; i1++) {
                StringBuilder sdbNumber = new StringBuilder();
                int  index2=0;
                for (int i = 0; i < beans.size(); i++) {
                    if (beans.get(i).getUid().equalsIgnoreCase(titles[i1])) {
                        index2++;
                        if (sdbNumber.length() > 0) {
                            sdbNumber.append(",").append(CCSSC_zxListAdapter.GetSelectItem().get(i).getNumber());
                        } else {

                            if (i1<titles.length){
                                if(index>0)
                                sdbNumber.append("|");
                                index=index+1;
                                LogTools.e("添加成功",index+"");
                            }
                            sdbNumber.append(CCSSC_zxListAdapter.GetSelectItem().get(i).getUid() + ":");
                            sdbNumber.append(CCSSC_zxListAdapter.GetSelectItem().get(i).getNumber());
                        }
                    }
                }
                if(index2!=0) {
                    index3 = index3 * index2;
                    LogTools.e("添加成功2" + index2, index3 + "");
                }
                sdbNumber3.append(sdbNumber.toString());
            }
            LogTools.e("beans2222", sdbNumber3.toString());
//            boolean state = false;
//            for (int i2 = 0; i2 < titles.length; i2++) {
//                if (sdbNumber3.toString().indexOf(titles[i2]) != -1)
//                    state = true;
//            }
            if(index==2){
                ArrayList<LotterycomitorderBean> jsonstringaa = new ArrayList<LotterycomitorderBean>();
                LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
                gameBean1.setNumber(sdbNumber3.toString());
                gameBean1.setPl(jsonstring.get(0).getPl());
                gameBean1.setUid(jsonstring.get(0).getUid());
                gameBean1.setMinLimit(jsonstring.get(0).getMinLimit());
                gameBean1.setMaxlimit(jsonstring.get(0).getMaxlimit());
                gameBean1.setMaxPeriodLimit(jsonstring.get(0).getMaxPeriodLimit());
                jsonstringaa.add(gameBean1);

                Intent intent = new Intent(getActivity(), LotteryComitOrderActivity.class);
                intent.putExtra("title", LotteryFragment1.getGamename());
                intent.putExtra("gamecode", LotteryFragment1.getGamecode());
                intent.putExtra("normal", "normal");
                intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
                intent.putExtra("gamexzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
                intent.putExtra("json", new Gson().toJson(jsonstringaa));
                intent.putExtra("index",index3+"");
                startActivity(intent);
                LogTools.e("dadfadfadsfads" + LotteryFragment1.getGanmeitmexzlxname(), LotteryFragment1.getGameitemname() + "");
            } else {
                ToastUtil.showMessage(this.getActivity(), "只能选择2行");
            }
        } else {
            ToastUtil.showMessage(this.getActivity(), "只能选择2行");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
}
