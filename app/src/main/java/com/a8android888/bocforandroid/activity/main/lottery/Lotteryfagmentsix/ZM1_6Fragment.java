package com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Lottery_tmfragment_Adapter;
import com.a8android888.bocforandroid.Adapter.Lottery_bsdw_6mfragment_Adapter;
import com.a8android888.bocforandroid.Adapter.Lottery_zm1_6mfragment_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5. 正码1-6
 */
public class ZM1_6Fragment extends BaseFragment implements View.OnClickListener {
    TextView reset,comit;
    private ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    private  ArrayList<ArrayList<CheckBox>> textlist;
    private SparseArray<LotterycomitorderBean> lotterycomitorderBeanSparseArray=new SparseArray<>();
    LinearLayout mainview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        setOpenRefresh(true);
        return View.inflate(getActivity(), R.layout.tmfragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View lsitview2 = FindView(R.id.lsitview2);
        lsitview2.setVisibility(View.GONE);
        View lsitview = FindView(R.id.lsitview);
        lsitview.setVisibility(View.GONE);
        ScrollView scrollview=FindView(R.id.mainview);
        scrollview.setVisibility(View.VISIBLE);
        mainview =(LinearLayout)scrollview.getChildAt(0);
        scrollview.setPadding(1, 1, 1, 1);
//        scrollview.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.comit_order_codebag));

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
//                if(adapter!=null )
//                {
//                    adapter.ClearRecordData();
//                    adapter.notifyStockdatalist(null);
//                }
                reset();
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
                    LogTools.e("ZM1T6", LotteryFragment1.getGanmeitmexzlxcode() + "");
                    basefragments = new ArrayList<ArrayList<LotterycomitorderBean>>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans1 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans2 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans3 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans4 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans5 = new ArrayList<LotterycomitorderBean>();
                    ArrayList<LotterycomitorderBean> lotterycomitorderBeans6 = new ArrayList<LotterycomitorderBean>();

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
                        if (gameBean1.getNumber().contains("红")) {
                            gameBean1.setTextcolor(0xffe81c1c);
                        } else if (gameBean1.getNumber().contains("绿")) {
                            gameBean1.setTextcolor(0xff048633);
                        } else if (gameBean1.getNumber().contains("蓝")) {
                            gameBean1.setTextcolor(0xff0e09c3);
                        } else {
                            gameBean1.setTextcolor(0xff000000);
                        }

                        if (LotteryFragment1.getGanmeitmexzlxcode().length() < 1) {
                            if (jsond.optString("id").indexOf("-ZM1") != -1)
                                lotterycomitorderBeans1.add(gameBean1);
                            if (jsond.optString("id").indexOf("-ZM2") != -1)
                                lotterycomitorderBeans2.add(gameBean1);
                            if (jsond.optString("id").indexOf("-ZM3") != -1)
                                lotterycomitorderBeans3.add(gameBean1);
                            if (jsond.optString("id").indexOf("-ZM4") != -1)
                                lotterycomitorderBeans4.add(gameBean1);
                            if (jsond.optString("id").indexOf("-ZM5") != -1)
                                lotterycomitorderBeans5.add(gameBean1);
                            if (jsond.optString("id").indexOf("-ZM6") != -1)
                                lotterycomitorderBeans6.add(gameBean1);
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
                    basefragments.add(lotterycomitorderBeans2);
                    basefragments.add(lotterycomitorderBeans3);
                    basefragments.add(lotterycomitorderBeans4);
                    basefragments.add(lotterycomitorderBeans5);
                    basefragments.add(lotterycomitorderBeans6);
//                    if (adapter == null) {
//                        adapter = new Lottery_zm1_6mfragment_Adapter(ZM1_6Fragment.this.getActivity(), basefragments, "normal");
////                    LogTools.e("basefragments",new Gson().toJson(basefragments));
////                    LogTools.e("basefragments2",new Gson().toJson(basefragments2));
//                        viewpager.setAdapter(adapter);
//                    } else {
//                        adapter.notifyStockdatalist(basefragments);
//                    }
                    CreateHead();

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
//                if(adapter!=null){
//                    adapter=null;
//                    basefragments.clear();
//                }
                reset();
                getdatalist();
                break;
            case R.id.comit:
                String Username =((BaseApplication)this.getActivity().getApplication()).getBaseapplicationUsername();
                if (Username == null || Username.equalsIgnoreCase("")) {
                    ToastUtil.showMessage(this.getActivity(), "未登录");
                    Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                    this.getActivity().startActivity(intent);
                    return;
                }
                StartActvity(GetResultJsonData());
                break;
        }
    }

    public void StartActvity(ArrayList<LotterycomitorderBean> beans)
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

    private void CreateHead()
    {
        if(mainview.getChildCount()>0)
        {
            //加载数据 设置赔率
            SetPeilv();
            return;
        }
        textlist=new ArrayList<>();
        mainview.setBackground(new ColorDrawable(0xFFFFFFFF));

//        View popupView = View.inflate(getActivity(), R.layout.item_tmfragement, null);
//        View popupView2 = View.inflate(getActivity(), R.layout.item_tmfragement, null);
//        LinearLayout.LayoutParams viewlayout=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        viewlayout.weight=1;
//        popupView.setLayoutParams(viewlayout);
//        popupView2.setLayoutParams(viewlayout);
//        View grouplayout=popupView.findViewById(R.id.grouplayout);
//        grouplayout.setVisibility(View.VISIBLE);
//        View bottomview=popupView.findViewById(R.id.bottomview);
//        bottomview.setVisibility(View.GONE);
//        View grouplayout2=popupView2.findViewById(R.id.grouplayout);
//        grouplayout2.setVisibility(View.VISIBLE);
//        View bottomview2=popupView2.findViewById(R.id.bottomview);
//        bottomview2.setVisibility(View.GONE);
//
//
//        LinearLayout linearLayout=new LinearLayout(getActivity());
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.bottomMargin= ScreenUtils.getDIP2PX(getActivity(),10);
//        linearLayout.setLayoutParams(layoutParams);
//
//        linearLayout.addView(popupView);
//        linearLayout.addView(popupView2);
//        mainview.addView(linearLayout);
        CreateBodyAndSetTitle();
    }
    private void CreateBodyAndSetTitle()//画界面和设置号码文字
    {
        for (int i = 0; i <basefragments.size(); i++) {
            int size=basefragments.get(i).size();
            int addCount=size%2==0?size/2:size/2+1;
            //title标头
            TextView title=new TextView(getActivity());
            LinearLayout.LayoutParams tlayout=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tlayout.topMargin=ScreenUtils.getDIP2PX(getActivity(),10);
            title.setLayoutParams(tlayout);
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            title.setPadding(0, 10, 0, 10);
            title.setGravity(Gravity.CENTER);
            title.setTextColor(Color.WHITE);
            title.setBackgroundColor(0xffdadada);
            title.setText("正码 " + (i + 1));
            mainview.addView(title);
            ArrayList<CheckBox> textviews=new ArrayList<>();
            ArrayList<LotterycomitorderBean>  lotts= basefragments.get(i);
            for (int k = 0; k <addCount; k++) {
                int onceOrTwice=(k+1)*2<size?2:1;
                LinearLayout linearLayout=new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin=-1;
                linearLayout.setLayoutParams(layoutParams);
                for (int j = 0; j <2; j++) {
                    View popupView = View.inflate(getActivity(), R.layout.item_tmfragement, null);
                    LinearLayout.LayoutParams viewlayout=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    viewlayout.weight=1;
                    if(j==1)
                        viewlayout.leftMargin=-1;
                    popupView.setLayoutParams(viewlayout);
                    if(onceOrTwice==j)
                    {
                        popupView.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        TextView number=(TextView)popupView.findViewById(R.id.number);
                        number.setTextColor(lotts.get(k * 2 + j).getTextcolor());
                        number.setText(lotts.get(k * 2 + j).getNumber());

                        CheckBox peilv=(CheckBox) popupView.findViewById(R.id.peilv);
                        indexbean indexbean=new indexbean();
                        indexbean.setIndex(i);
                        indexbean.setPosition(k * 2 + j);
                        peilv.setTag(indexbean);
                        peilv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                indexbean indexbean = (indexbean) buttonView.getTag();
                                int index = indexbean.getIndex();
                                int position = indexbean.getPosition();
                                int rindex = (index + 1) * (position + 1);
                                if (isChecked) {
                                    lotterycomitorderBeanSparseArray.put(rindex,basefragments.get(index).get(position));
                                } else {
                                    lotterycomitorderBeanSparseArray.remove(rindex);
                                }
                            }
                        });
                        textviews.add(peilv);
                    }
                    linearLayout.addView(popupView);
                }
                mainview.addView(linearLayout);
            }
            textlist.add(textviews);
        }
        //加载数据 设置赔率
        SetPeilv();
    }


    //加载数据 设置赔率
    private void SetPeilv()
    {
        for (int i = 0; i <textlist.size(); i++) {
            ArrayList<CheckBox> textViews=textlist.get(i);
            ArrayList<LotterycomitorderBean> lotterycomitorderBeans= basefragments.get(i);
            for (int j = 0; j < textViews.size(); j++) {
                textViews.get(j).setText(lotterycomitorderBeans.get(j).getPl());
            }
        }
    }

    //设置
    private void reset()
    {
        if(textlist==null || textlist.size()<1)return;
        for (int i = 0; i <textlist.size(); i++) {
            ArrayList<CheckBox> textViews=textlist.get(i);
            for (int j = 0; j < textViews.size(); j++) {
                textViews.get(j).setChecked(false);
            }
        }
    }

    private ArrayList<LotterycomitorderBean> GetResultJsonData()
    {
        ArrayList<LotterycomitorderBean> lotterys=new ArrayList<>();
        for (int i = 0; i < lotterycomitorderBeanSparseArray.size(); i++) {
            lotterys.add(lotterycomitorderBeanSparseArray.valueAt(i));
        }
        return lotterys;
    }

    private class indexbean
    {
        int index;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        int position;
    }
}
