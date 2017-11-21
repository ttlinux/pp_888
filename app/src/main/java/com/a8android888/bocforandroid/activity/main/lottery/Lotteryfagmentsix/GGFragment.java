package com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Lottery_gg_mfragment_Adapter;
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
import com.a8android888.bocforandroid.util.ScreenUtils;
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
 * Created by Administrator on 2017/4/5. 过关
 */
public class GGFragment extends BaseFragment implements View.OnClickListener {
    GridView viewpager;
    ListView listview;
    ScrollView mainview;
//    Lottery_gg_mfragment_Adapter adapter;
    TextView reset, comit;
    private ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    List<String> list = new ArrayList<String>();
    List<String> tmlist = new ArrayList<String>();
    private ThreadTimer mTimer;
    public static ArrayList<Object[]> chooselist;
    private ArrayList<TextView> textviews=new ArrayList<TextView>();
    private ArrayList<CheckBox> checkboxs=new ArrayList<CheckBox>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        setOpenRefresh(true);
        return View.inflate(getActivity(), R.layout.tmfragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager = FindView(R.id.lsitview);
        listview = FindView(R.id.lsitview2);
        mainview = FindView(R.id.mainview);

        viewpager.setVisibility(View.GONE);
        listview.setVisibility(View.GONE);
        mainview.setVisibility(View.VISIBLE);
        reset = (TextView) FindView(R.id.reset);
        reset.setOnClickListener(this);

        comit = (TextView) FindView(R.id.comit);
        comit.setOnClickListener(this);


    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if (state)
            ThreadTimer.Stop(getClass().getName());
        else {
            if(((LinearLayout)mainview.getChildAt(0)).getChildCount()<1)AddBtn();
            if (getActivity() != null && getView() != null) {
                ThreadTimer.Stop(getClass().getName());
                ResetState();
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

                        if (jsond.optString("id").indexOf("ZM1") != -1)
                            lotterycomitorderBeans1.add(gameBean1);
                        if (jsond.optString("id").indexOf("ZM2") != -1)
                            lotterycomitorderBeans2.add(gameBean1);
                        if (jsond.optString("id").indexOf("ZM3") != -1)
                            lotterycomitorderBeans3.add(gameBean1);
                        if (jsond.optString("id").indexOf("ZM4") != -1)
                            lotterycomitorderBeans4.add(gameBean1);
                        if (jsond.optString("id").indexOf("ZM5") != -1)
                            lotterycomitorderBeans5.add(gameBean1);
                        if (jsond.optString("id").indexOf("ZM6") != -1)
                            lotterycomitorderBeans6.add(gameBean1);
                    }

                    basefragments.add(lotterycomitorderBeans1);
                    basefragments.add(lotterycomitorderBeans2);
                    basefragments.add(lotterycomitorderBeans3);
                    basefragments.add(lotterycomitorderBeans4);
                    basefragments.add(lotterycomitorderBeans5);
                    basefragments.add(lotterycomitorderBeans6);
//                    if (adapter == null) {
//                        adapter = new Lottery_gg_mfragment_Adapter(GGFragment.this.getActivity(), (ArrayList<ArrayList<LotterycomitorderBean>>)basefragments.clone(), "cl");
//                        viewpager.setAdapter(adapter);
//                    } else {
//                        adapter.notifyStockdatalist((ArrayList<ArrayList<LotterycomitorderBean>>)basefragments.clone());
//                    }
                    setdata();
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
        switch (v.getId()) {
            case R.id.reset:
                ResetState();
                break;
            case R.id.comit:
                String Username =((BaseApplication)this.getActivity().getApplication()).getBaseapplicationUsername();
                if (Username == null || Username.equalsIgnoreCase("")) {
                    ToastUtil.showMessage(this.getActivity(), "未登录");
                    Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                    this.getActivity().startActivity(intent);
                    return;
                }
                StartActvity(Getselectitem());
                break;
        }
    }

    public void StartActvity(ArrayList<LotterycomitorderBean> beans) {
        if (beans.size() >= 2) {
            Intent intent = new Intent(getActivity(), LotteryComitOrderActivity.class);
            intent.putExtra("title", LotteryFragment1.getGamename());
            intent.putExtra("gamecode", LotteryFragment1.getGamecode());
            intent.putExtra("normal", "cl");
            intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
            intent.putExtra("gamexzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
            intent.putExtra("json", new Gson().toJson(beans));
            LogTools.e("dadfadfadsfads" + LotteryFragment1.getGanmeitmexzlxname(), LotteryFragment1.getGameitemname() + "");
            startActivity(intent);
        } else {
            ToastUtil.showMessage(this.getActivity(), "至少选择2-6个");
        }
    }

    private void openTimer() {
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
        BaseApplication baseApplication = (BaseApplication) getActivity().getApplication();

        long close = baseApplication.getClosetime() - baseApplication.getNowtime();
        long now = baseApplication.getOpentime() - baseApplication.getNowtime();
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


    private void InitView() {

    }

    private void AddBtn() {
        LinearLayout mainLinear = (LinearLayout) mainview.getChildAt(0);
        for (int j = 0; j < 6; j++) {
            LinearLayout hor = new LinearLayout(getActivity());
            hor.setOrientation(LinearLayout.HORIZONTAL);
            hor.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                LinearLayout ver = new LinearLayout(getActivity());
                ver.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ver.setLayoutParams(params);
                ver.setBackground(getResources().getDrawable(R.color.line2));
//                View popupViewa = View.inflate(getActivity(), R.layout.item_zm1_6tmfragement, null);
//                TextView numbera = (TextView) popupViewa.findViewById(R.id.zm);
//                numbera.setText("正码" + (j * 2 + k + 1));
//                ver.addView(numbera);
                LinearLayout ver2 = new LinearLayout(getActivity());
                ver2.setOrientation(LinearLayout.HORIZONTAL);
                ver2.setBackground(getResources().getDrawable(R.drawable.comit_order_codebag));
                int pad1= ScreenUtils.getDIP2PX(getActivity(),3);
                ver2.setPadding(3, 1,3, 0);
                TextView texttitle=new TextView(getActivity());
                int pad= ScreenUtils.getDIP2PX(getActivity(),5);
                texttitle.setPadding(pad, pad, pad, pad);
                texttitle.setTextColor(0xFFFFFFFF);
                texttitle.setGravity(Gravity.CENTER);
                texttitle.setBackground(getResources().getDrawable(R.color.lotteryfragmenttitle2));
                texttitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                texttitle.setText("正码" + (j+1));
                ver2.addView(texttitle);
                ver.addView(ver2);
            int itemcount=7;
            int count=itemcount%2==0?itemcount/2:(itemcount/2)+1;
                for (int i = 0; i < count; i++) {
                    LinearLayout hor2=new LinearLayout(this.getActivity());
                    hor2.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams params4 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params4.weight=1;
                    hor2.setLayoutParams(params4);
                    int tempcount=itemcount-i*2>2?(2*(i+1)):itemcount;
                    for (int j1 = i*2; j1 <tempcount; j1++) {
                        View popupView = View.inflate(getActivity(), R.layout.item_tmfragement, null);
                        LinearLayout.LayoutParams params2 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params2.weight=1;
                        popupView.setLayoutParams(params2);
                        TextView number = (TextView) popupView.findViewById(R.id.number);
                        CheckBox peilv = (CheckBox) popupView.findViewById(R.id.peilv);
                        RadioGroup group = (RadioGroup) popupView.findViewById(R.id.grouplayout);
                        peilv.setTag(i);

//                    if (i == 0 ) {
//                       group.setVisibility(View.VISIBLE);
//                    } else {
//                     group.setVisibility(View.GONE);
//                    }
                        textviews.add(number);
                        checkboxs.add(peilv);
                        hor2.addView(popupView);
                    }
                    if(hor2.getChildCount()==1)
                    {
                        LinearLayout.LayoutParams params3 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        params3.weight=1;
                        params3.leftMargin=2;
                        params3.rightMargin=2;
                        TextView textView=new TextView(this.getActivity());
                        textView.setLayoutParams(params3);
                        textView.setBackgroundColor(this.getResources().getColor(R.color.white));
                        hor2.addView(textView);
                    }
                    ver.addView(hor2);
                }
                hor.addView(ver);
            mainLinear.addView(hor);
        }

    }


    private void setdata()
    {
        for (int i = 0; i < basefragments.size(); i++) {
            ArrayList<LotterycomitorderBean> beans=basefragments.get(i);
            for (int k = 0; k <beans.size(); k++) {
                LotterycomitorderBean bean = beans.get(k);
                textviews.get(i*beans.size()+k).setText(bean.getNumber());
//                if (bean.getNumber().indexOf("红") != -1) {
//                    textviews.get(i*beans.size()+k).setTextColor(this.getActivity().getResources().getColor(R.color.lottery));
//                }
//                if (bean.getNumber().indexOf("绿") != -1) {
//                    textviews.get(i*beans.size()+k).setTextColor(this.getActivity().getResources().getColor(R.color.green));
//                }
//                if (bean.getNumber().indexOf("蓝") != -1) {
//                    textviews.get(i * beans.size()+k).setTextColor(this.getActivity().getResources().getColor(R.color.lotteryblue));
//                }
                if(Httputils.isNumber(bean.getNumber())) {
                    textviews.get(i * beans.size()+k).setTextColor(this.getResources().getColor(R.color.white));
                }else {
                    if (bean.getNumber().indexOf("红波") != -1) {
                        textviews.get(i * beans.size()+k).setTextColor(this.getResources().getColor(R.color.lottery));
                    }
                    else  if (bean.getNumber().indexOf("绿波") != -1) {
                        textviews.get(i * beans.size()+k).setTextColor(this.getResources().getColor(R.color.green));
                    }
                    else  if (bean.getNumber().indexOf("蓝波") != -1) {
                        textviews.get(i * beans.size()+k).setTextColor(this.getResources().getColor(R.color.lotteryblue));
                    }
                    else
                        textviews.get(i * beans.size()+k).setTextColor(this.getResources().getColor(R.color.black));
                }
                checkboxs.get(i*beans.size()+k).setText(bean.getPl());
                checkboxs.get(i*beans.size()+k).setTag(i * beans.size() + k);
                checkboxs.get(i*beans.size() + k).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckBox checkbox = (CheckBox) v;
                        if (checkbox.isChecked()) {
                            int tag = Integer.valueOf(checkbox.getTag() + "");
                            int position = tag / 7;
                            for (int i = position * 7; i < (position + 1) * 7; i++) {
                                if (i == tag) continue;
                                checkboxs.get(i).setChecked(false);
                            }
                        }
                    }
                });
//                checkboxs.get(i*beans.size()+k).setTag(bean);
            }
        }
    }

    private void ResetState()
    {
        for (int i = 0; i < checkboxs.size(); i++) {
            checkboxs.get(i).setChecked(false);
        }
    }

    private ArrayList<LotterycomitorderBean> Getselectitem()
    {
        ArrayList<LotterycomitorderBean> beans=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i < checkboxs.size(); i++) {
            if(checkboxs.get(i).isChecked())
            {
                beans.add(basefragments.get(i/7).get(i%7));
            }
        }
        return beans;
    }
}
