package com.a8android888.bocforandroid.activity.main.Sports;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.SportsAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.BKBean_Guoguan;
import com.a8android888.bocforandroid.Bean.BKBean_Normal;
import com.a8android888.bocforandroid.Bean.FootBallHq;
import com.a8android888.bocforandroid.Bean.FootBallRuQiu;
import com.a8android888.bocforandroid.Bean.FootBallbodan;
import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Guoguan;
import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Normal;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.SortMethod;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/5. 今日
 */
public class SportFragment2 extends BaseFragment{


    AnimatedExpandableListView expandablelistview;
    SportsAdapter sportsAdapter;
    ArrayList<SportsFootBallBean_Normal> sports_Normal=new ArrayList<SportsFootBallBean_Normal>();
    ArrayList<SportsFootBallBean_Guoguan> sports_guoguan=new ArrayList<SportsFootBallBean_Guoguan>();
    ArrayList<BKBean_Guoguan> bkBeans_guoguan=new ArrayList<BKBean_Guoguan>();
    ArrayList<BKBean_Normal> bkBeans_normal=new ArrayList<BKBean_Normal>();
    ArrayList<FootBallbodan> footBallbodans=new ArrayList<FootBallbodan>();
    ArrayList<FootBallHq> footBallHqs=new ArrayList<FootBallHq>();
    ArrayList<FootBallRuQiu> footBallRuQius=new ArrayList<FootBallRuQiu>();
     int  delay=60000;
    RelativeLayout refresh;
    TextView minperiod;
    boolean isRuning=false;
    PublicDialog publicDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(),R.layout.fragment_sports,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandablelistview=FindView(R.id.expandablelistview);
        expandablelistview.setGroupIndicator(null);

        minperiod=FindView(R.id.minperiod);
        refresh=FindView(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
            }
        });
        expandablelistview.setOnGroupClickListener(new AnimatedExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (expandablelistview.isGroupExpanded(groupPosition)) {
                    expandablelistview.collapseGroupWithAnimation(groupPosition);
                } else {
                    expandablelistview.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
        expandablelistview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = expandablelistview.getExpandableListAdapter().getGroupCount();
                for (int j = 0; j < count; j++) {
                    if (j != groupPosition) {
                        expandablelistview.collapseGroup(j);
                    }
                }
//                expandablelistview.setSelectedGroup(groupPosition);
            }
        });

    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(!state )
        {
            minperiod.setText(delay/1000+"");
            GetData();
        }
        else
             ThreadTimer.Stop(getClass().getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Wehu()
    {
        if (getActivity() != null) {
            Sports_MainActivity mainActivity = (Sports_MainActivity) getActivity();
            PopupWindow popupWindow = mainActivity.getPopWindow();
            if (popupWindow == null || !popupWindow.isShowing()) {
                mainActivity.ShowPopWindow();
                mainActivity.setContenttext("正在维护");
                mainActivity.SettitleclickEnable(false);
            }
        }
    }

    public void GetData()
    {
        if(isRuning || getActivity()==null)
        {
            return;
        }
        isRuning=true;
        if(publicDialog==null)
        {
            publicDialog=new PublicDialog(getActivity(),false);
        }
        publicDialog.show();
        final String type=((Sports_MainActivity)getActivity()).GetSportTag();
        if(type==null|| type.equalsIgnoreCase(""))return;

        final String tag=((Sports_MainActivity)getActivity()).GetSportState();

        RequestParams params=new RequestParams();
        params.put("timeType","today");
        params.put("rType",type);
        Httputils.PostWithBaseUrl(Httputils.SportsOdds, params, new MyJsonHttpResponseHandler(getActivity(),true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                LogTools.e("jsonObject", "here the shit");
                isRuning = false;
                if (publicDialog != null && getActivity() != null) {
                    publicDialog.dismiss();
                }
                minperiod.setText(delay / 1000 + "");
                OpenTimer();
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
//                LogTools.ee("jsonObject", "here you go" + jsonObject);
                isRuning = false;
                if (publicDialog != null && getActivity() != null) {
                    publicDialog.dismiss();
                    if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000"))
                    {
                        ClearData();
                        SetAdapter(BALL_TYPE.FootBall_Normal.value());
                        Wehu();
                        return;
                    }
                    if (jsonObject.optJSONObject("datas")!=null && jsonObject.optJSONObject("datas").optJSONArray("list").length() == 0) {
                        ToastUtil.showMessage(getActivity(), getString(R.string.projectnoexits));
                    }
                    ClearData();
                    int Adastyle = BALL_TYPE.FootBall_Normal.value();
                    if (tag.contains("足球")) {
                        if (type.equalsIgnoreCase("p3")) {
                            Adastyle = BALL_TYPE.FootBall_GuoGuan.value();
                            FootBallJson_GuoGuan(jsonObject);
                        } else if (type.equalsIgnoreCase("hpd")) {
                            Adastyle = BALL_TYPE.FootBall_HBoDan.value();
                            FootBallJson_bodan(jsonObject);
                        } else if (type.equalsIgnoreCase("pd")) {
                            Adastyle = BALL_TYPE.FootBall_BoDan.value();
                            FootBallJson_bodan(jsonObject);
                        } else if (type.equalsIgnoreCase("t")) {
                            Adastyle = BALL_TYPE.FootBall_ZongRuQiu.value();
                            FootBallJson_RuQiu(jsonObject);
                        } else if (type.equalsIgnoreCase("f")) {
                            Adastyle = BALL_TYPE.FootBall_BanQuan.value();
                            FootBallJson_HQ(jsonObject);
                        } else {
                            Adastyle = BALL_TYPE.FootBall_Normal.value();
                            FootBallJson_Normal(jsonObject);
                        }
                    } else if (tag.contains("篮球")) {
                        if (type.equalsIgnoreCase("bk_p3")) {
                            Adastyle = BALL_TYPE.BasketBall_GuoGuan.value();
                            BasketBallJson_GuoGuan(jsonObject);
                        } else {
                            Adastyle = BALL_TYPE.BasketBall_Normal.value();
                            BasketBallJson_Normal(jsonObject);
                        }
                    } else {
                        SetAdapter(Adastyle);
                        ToastUtil.showMessage(getActivity(), getString(R.string.projectnoexits));
                        return;
                    }

                    SetAdapter(Adastyle);
                    JSONObject datas=jsonObject.optJSONObject("datas");
                    if(datas!=null && datas.has("refreshTime"))
                    {
                        try
                        {
                            delay=Integer.valueOf(datas.optString("refreshTime",""))*1000;
                        }
                        catch (Exception ex)
                        {
                            ToastUtil.showMessage(getActivity(),"服务器数据变更");
                        }
                    }
                    OpenTimer();
                }
            }
        });
    }

    private void  SetAdapter(int type)
    {
        SortData();
        if(sportsAdapter==null)
        {
            if(type==BALL_TYPE.FootBall_Normal.value())
                sportsAdapter=new SportsAdapter(getActivity(),sports_Normal,type);

            else if(type==BALL_TYPE.FootBall_GuoGuan.value())
                sportsAdapter=new SportsAdapter(getActivity(),sports_guoguan,type);

            else if(type==BALL_TYPE.BasketBall_GuoGuan.value())
                sportsAdapter=new SportsAdapter(getActivity(),bkBeans_guoguan,type);

            else if(type==BALL_TYPE.BasketBall_Normal.value())
                sportsAdapter=new SportsAdapter(getActivity(),bkBeans_normal,type);

            if(sportsAdapter!=null)
            expandablelistview.setAdapter(sportsAdapter);
        }
        else
        {
            if(type==BALL_TYPE.FootBall_Normal.value())
                sportsAdapter.NotifyAdapter(sports_Normal,type);

            else if(type==BALL_TYPE.FootBall_GuoGuan.value())
                sportsAdapter.NotifyAdapter(sports_guoguan,type);

            else if(type==BALL_TYPE.BasketBall_GuoGuan.value())
                sportsAdapter.NotifyAdapter(bkBeans_guoguan,type);

            else if(type==BALL_TYPE.BasketBall_Normal.value())
                sportsAdapter.NotifyAdapter(bkBeans_normal,type);
//////////////////////////////////////////////////////////////////////////
            else if(type==BALL_TYPE.FootBall_BanQuan.value())
                sportsAdapter.NotifyAdapter(footBallHqs,type);

            else if(type==BALL_TYPE.FootBall_ZongRuQiu.value())
                sportsAdapter.NotifyAdapter(footBallRuQius,type);

            else if(type==BALL_TYPE.FootBall_BoDan.value())
                sportsAdapter.NotifyAdapter(footBallbodans,type);

            else if(type==BALL_TYPE.FootBall_HBoDan.value())
                sportsAdapter.NotifyAdapter(footBallbodans,type);
        }
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
        GetData();
    }

    private void ClearData()
    {
        sports_Normal.clear();
        sports_guoguan.clear();
        bkBeans_guoguan.clear();
        bkBeans_normal.clear();
        footBallbodans.clear();
        footBallHqs.clear();
        footBallRuQius.clear();
    }

    private void OpenTimer()
    {
        ThreadTimer.Stop(getClass().getName());
        ThreadTimer.setListener(new ThreadTimer.OnActiviteListener() {
            @Override
            public void MinCallback(long limitmin) {
                minperiod.setText(limitmin/1000 + "");
            }

            @Override
            public void DelayCallback() {
                GetData();
            }
        });
        ThreadTimer.Start(delay);
    }

    private void FootBallJson_Normal(JSONObject jsonObject)//足球单式
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        for(int k=0;k<jsonArray.length();k++){
            SportsFootBallBean_Normal ballBean2 = new SportsFootBallBean_Normal();
            ArrayList<SportsFootBallBean_Normal.SportsFootBallBean_Normal_Item> sports = new ArrayList<SportsFootBallBean_Normal.SportsFootBallBean_Normal_Item>();
            JSONObject jsonObject1=jsonArray.optJSONObject(k);
            JSONArray value = jsonObject1.optJSONArray("detailList");
            ballBean2.setTitle(jsonObject1.optString("leagueName"));
            for (int i = 0; i < value.length(); i++) {
                try {
                    SportsFootBallBean_Normal.SportsFootBallBean_Normal_Item bean = new SportsFootBallBean_Normal.SportsFootBallBean_Normal_Item();
                    JSONObject tempjsonobject = value.getJSONObject(i);
                    bean.setCenterTv(tempjsonobject.optString("centerTv", ""));
                    bean.setEventid(tempjsonobject.optString("eventid", ""));
                    bean.setGid(tempjsonobject.optString("gid", ""));
                    bean.setGnumC(tempjsonobject.optString("gnumC", ""));
                    bean.setGnumH(tempjsonobject.optString("gnumH", ""));
                    bean.setHgid(tempjsonobject.optString("hgid", ""));
                    bean.setHot(tempjsonobject.optString("hot", ""));
                    bean.setHratio(tempjsonobject.optString("hratio", ""));
                    bean.setHratioO(tempjsonobject.optString("hratioO", ""));
                    bean.setHratioU(tempjsonobject.optString("hratioU", ""));
                    bean.setHstrong(tempjsonobject.optString("hstrong", ""));
                    bean.setIorEoe(tempjsonobject.optString("iorEoe", ""));
                    bean.setIorEoo(tempjsonobject.optString("iorEoo", ""));
                    bean.setIorHmc(tempjsonobject.optString("iorHmc", ""));
                    bean.setIorHmh(tempjsonobject.optString("iorHmh", ""));
                    bean.setIorHmn(tempjsonobject.optString("iorHmn", ""));
                    bean.setIorHouc(tempjsonobject.optString("iorHouc", ""));
                    bean.setIorHouh(tempjsonobject.optString("iorHouh", ""));
                    bean.setIorHrc(tempjsonobject.optString("iorHrc", ""));
                    bean.setIorHrh(tempjsonobject.optString("iorHrh", ""));
                    bean.setIorMc(tempjsonobject.optString("iorMc", ""));
                    bean.setIorMh(tempjsonobject.optString("iorMh", ""));
                    bean.setIorMn(tempjsonobject.optString("iorMn", ""));
                    bean.setIorOuc(tempjsonobject.optString("iorOuc", ""));
                    bean.setIorOuh(tempjsonobject.optString("iorOuh", ""));
                    bean.setIorRc(tempjsonobject.optString("iorRc", ""));
                    bean.setIorRh(tempjsonobject.optString("iorRh", ""));
                    bean.setLastestscoreC(tempjsonobject.optString("lastestscoreC", ""));
                    bean.setLastestscoreH(tempjsonobject.optString("lastestscoreH", ""));
                    bean.setLeague(tempjsonobject.optString("league", ""));
                    bean.setMatchIndex(tempjsonobject.optInt("matchIndex"));
                    bean.setMatchPage(tempjsonobject.optInt("matchPage"));
                    bean.setMatchTime(tempjsonobject.optString("matchTime", ""));
                    bean.setMore(tempjsonobject.optString("more", ""));
                    bean.setNo1(tempjsonobject.optString("no1", ""));
                    bean.setNo2(tempjsonobject.optString("no2", ""));
                    bean.setNo3(tempjsonobject.optString("no3", ""));
                    bean.setPlay(tempjsonobject.optString("play", ""));
                    bean.setRatio(tempjsonobject.optString("ratio", ""));
                    bean.setRatioO(tempjsonobject.optString("ratioO", ""));
                    bean.setRatioU(tempjsonobject.optString("ratioU", ""));
                    bean.setRedcardC(tempjsonobject.optString("redcardC", ""));
                    bean.setRedcardH(tempjsonobject.optString("redcardH", ""));
                    bean.setRetimeset(tempjsonobject.optString("retimeset", ""));
                    bean.setRoll(tempjsonobject.optString("roll", ""));
//                    LogTools.e("rollllll",tempjsonobject.optString("roll", ""));
                    bean.setScoreC(tempjsonobject.optString("scoreC", ""));
                    bean.setScoreH(tempjsonobject.optString("scoreH", ""));
                    bean.setStrEven(tempjsonobject.optString("strEven", ""));
                    bean.setStrOdd(tempjsonobject.optString("strOdd", ""));
                    bean.setStrong(tempjsonobject.optString("strong", ""));
                    bean.setTeamC(tempjsonobject.optString("teamC", ""));
                    bean.setTeamH(tempjsonobject.optString("teamH", ""));
                    bean.setTimer(tempjsonobject.optString("timer", ""));
                    bean.setTmp1(tempjsonobject.optString("tmp1", ""));
                    bean.setTmp2(tempjsonobject.optString("tmp2", ""));
                    bean.setTmp3(tempjsonobject.optString("tmp3", ""));
                    sports.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ballBean2.setSportsFootBallBeanNormals(sports);
            sports_Normal.add(ballBean2);
        }
    }

    private void FootBallJson_GuoGuan(JSONObject jsonObject)//足球过关
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        for(int k=0;k<jsonArray.length();k++){
            SportsFootBallBean_Guoguan ballBean2 = new SportsFootBallBean_Guoguan();
            ArrayList<SportsFootBallBean_Guoguan.FootBallBean_Guoguan_item> sports = new ArrayList<SportsFootBallBean_Guoguan.FootBallBean_Guoguan_item>();
            JSONObject jsonObject1=jsonArray.optJSONObject(k);
            JSONArray value = jsonObject1.optJSONArray("detailList");
            ballBean2.setTitle(jsonObject1.optString("leagueName"));
            for (int i = 0; i < value.length(); i++) {
                try {
                    SportsFootBallBean_Guoguan.FootBallBean_Guoguan_item bean = new SportsFootBallBean_Guoguan.FootBallBean_Guoguan_item();
                    JSONObject tempjsonobject = value.getJSONObject(i);
                    bean.setGid(tempjsonobject.optString("gid", ""));
                    bean.setGnumC(tempjsonobject.optString("gnumC", ""));
                    bean.setGnumH(tempjsonobject.optString("gnumH", ""));
                    bean.setHgid(tempjsonobject.optString("hgid", ""));
                    bean.setHratio(tempjsonobject.optString("hratio", ""));
                    bean.setHratioO(tempjsonobject.optString("hratioO", ""));
                    bean.setHratioU(tempjsonobject.optString("hratioU", ""));

                    bean.setHstrong(tempjsonobject.optString("hstrong", ""));
                    bean.setIorHmc(tempjsonobject.optString("iorHmc", ""));
                    bean.setIorHmh(tempjsonobject.optString("iorHmh", ""));
                    bean.setIorHmn(tempjsonobject.optString("iorHmn", ""));

                    bean.setIorMc(tempjsonobject.optString("iorMc", ""));
                    bean.setIorMh(tempjsonobject.optString("iorMh", ""));
                    bean.setIorMn(tempjsonobject.optString("iorMn", ""));

                    bean.setGidm(tempjsonobject.optString("gidm", ""));
                    bean.setIorHpouc(tempjsonobject.optString("iorHpouc", ""));
                    bean.setIorHpouh(tempjsonobject.optString("iorHpouh", ""));
                    bean.setIorHprc(tempjsonobject.optString("iorHprc", ""));
                    bean.setIorHprh(tempjsonobject.optString("iorHprh", ""));
                    bean.setIorPeoe(tempjsonobject.optString("iorPeoe", ""));
                    bean.setIorPeoo(tempjsonobject.optString("iorPeoo", ""));
                    bean.setIorPouc(tempjsonobject.optString("iorPouc", ""));
                    bean.setIorPouh(tempjsonobject.optString("iorPouh", ""));
                    bean.setIorPrc(tempjsonobject.optString("iorPrc", ""));
                    bean.setIorPrh(tempjsonobject.optString("iorPrh", ""));

                    bean.setLeague(tempjsonobject.optString("league", ""));
                    bean.setMatchIndex(tempjsonobject.optInt("matchIndex"));
                    bean.setMatchPage(tempjsonobject.optInt("matchPage"));
                    bean.setMatchTime(tempjsonobject.optString("matchTime", ""));
                    bean.setMore(tempjsonobject.optString("more", ""));
                    bean.setRatio(tempjsonobject.optString("ratio", ""));
                    bean.setRatioO(tempjsonobject.optString("ratioO", ""));
                    bean.setRatioU(tempjsonobject.optString("ratioU", ""));
                    bean.setRoll(tempjsonobject.optString("roll", ""));
                    bean.setStrong(tempjsonobject.optString("strong", ""));
                    bean.setTeamC(tempjsonobject.optString("teamC", ""));
                    bean.setTeamH(tempjsonobject.optString("teamH", ""));
                    bean.setTmp1(tempjsonobject.optString("tmp1", ""));
                    bean.setTmp2(tempjsonobject.optString("tmp2", ""));
                    bean.setTmp3(tempjsonobject.optString("tmp3", ""));
                    sports.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ballBean2.setFootBallBean_guoguan_items(sports);
            sports_guoguan.add(ballBean2);
        }
    }

    private void BasketBallJson_GuoGuan(JSONObject jsonObject)//篮球 过关
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        for(int k=0;k<jsonArray.length();k++){
            BKBean_Guoguan bkbean=new BKBean_Guoguan();
            JSONObject jsonObject1=jsonArray.optJSONObject(k);
            bkbean.setTitle(jsonObject1.optString("leagueName"));
            ArrayList<BKBean_Guoguan.BKitemBean> bkitems=new ArrayList<BKBean_Guoguan.BKitemBean>();
            JSONArray value = jsonObject1.optJSONArray("detailList");
            for (int i = 0; i < value.length(); i++) {
                JSONObject jsonobject=value.optJSONObject(i);
                BKBean_Guoguan.BKitemBean bkitembean=new BKBean_Guoguan.BKitemBean();
                bkitembean.setGid(jsonobject.optString("gid", ""));
                bkitembean.setGidm(jsonobject.optString("gidm", ""));
                bkitembean.setGnumC(jsonobject.optString("gnumC", ""));
                bkitembean.setGnumH(jsonobject.optString("gnumH", ""));
                bkitembean.setIorMc(jsonobject.optString("iorMc", ""));

                bkitembean.setIorMh(jsonobject.optString("iorMh", ""));
                bkitembean.setIorMn(jsonobject.optString("iorMn", ""));
                bkitembean.setIorPe(jsonobject.optString("iorPe", ""));
                bkitembean.setIorPo(jsonobject.optString("iorPo", ""));
                bkitembean.setIorPouc(jsonobject.optString("iorPouc", ""));
                bkitembean.setIorPouco(jsonobject.optString("iorPouco", ""));
                bkitembean.setIorPoucu(jsonobject.optString("iorPoucu", ""));
                bkitembean.setIorPouh(jsonobject.optString("iorPouh", ""));
                bkitembean.setIorPouho(jsonobject.optString("iorPouho", ""));
                bkitembean.setIorPouhu(jsonobject.optString("iorPouhu", ""));
                bkitembean.setIorPrc(jsonobject.optString("iorPrc", ""));
                bkitembean.setIorPrh(jsonobject.optString("iorPrh", ""));
                bkitembean.setIsmaster(jsonobject.optString("ismaster", ""));
                bkitembean.setLeague(jsonobject.optString("league", ""));
                bkitembean.setMatchIndex(jsonobject.optInt("matchIndex", 1));
                bkitembean.setMatchPage(jsonobject.optInt("matchPage", 1));
                bkitembean.setMatchTime(jsonobject.optString("matchTime", ""));
                bkitembean.setMore(jsonobject.optString("more", ""));
                bkitembean.setParMaxlimit(jsonobject.optString("parMaxlimit", ""));
                bkitembean.setParMinlimit(jsonobject.optString("parMinlimit", ""));
                bkitembean.setRatio(jsonobject.optString("ratio", ""));
                bkitembean.setRatioO(jsonobject.optString("ratioO", ""));
                bkitembean.setRatioPouco(jsonobject.optString("ratioPouco", ""));
                bkitembean.setRatioPoucu(jsonobject.optString("ratioPoucu", ""));
                bkitembean.setRatioPouho(jsonobject.optString("ratioPouho", ""));
                bkitembean.setRatioPouhu(jsonobject.optString("ratioPouhu", ""));
                bkitembean.setRatioU(jsonobject.optString("ratioU", ""));
                bkitembean.setRoll(jsonobject.optInt("roll", 0));
                bkitembean.setStrong(jsonobject.optString("strong", ""));
                bkitembean.setTeamC(jsonobject.optString("teamC", ""));
                bkitembean.setTeamH(jsonobject.optString("teamH", ""));
                bkitembean.setTimeType(jsonobject.optString("timeType", ""));
                bkitembean.setTmp1(jsonobject.optString("tmp1", ""));
                bkitembean.setTmp2(jsonobject.optString("tmp2", ""));
                bkitembean.setTmp3(jsonobject.optString("tmp3", ""));
                bkitems.add(bkitembean);
            }
            bkbean.setbKitemBeans(bkitems);
            bkBeans_guoguan.add(bkbean);
        }
    }

    private void BasketBallJson_Normal(JSONObject jsonObject)//篮球 单式
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        for(int k=0;k<jsonArray.length();k++){
            BKBean_Normal bkbean=new BKBean_Normal();
            JSONObject jsonObject1=jsonArray.optJSONObject(k);
            bkbean.setTitle(jsonObject1.optString("leagueName"));
            ArrayList<BKBean_Normal.BkBean_Normal_item> bkitems=new ArrayList<BKBean_Normal.BkBean_Normal_item>();
            JSONArray value = jsonObject1.optJSONArray("detailList");
            for (int i = 0; i < value.length(); i++) {
                JSONObject jsonobject=value.optJSONObject(i);
                BKBean_Normal.BkBean_Normal_item bkitembean=new BKBean_Normal.BkBean_Normal_item();

                bkitembean.setCenterTv(jsonobject.optString("centerTv", ""));
                bkitembean.setEventid(jsonobject.optString("eventid", ""));
                bkitembean.setGid(jsonobject.optString("gid", ""));
                bkitembean.setGidm(jsonobject.optString("gidm", ""));
                bkitembean.setGnumC(jsonobject.optString("gnumC", ""));
                bkitembean.setGnumH(jsonobject.optString("gnumH", ""));
                bkitembean.setHot(jsonobject.optString("hot", ""));
                bkitembean.setIorEoe(jsonobject.optString("iorEoe", ""));
                bkitembean.setIorEoo(jsonobject.optString("iorEoo", ""));
                bkitembean.setIorMc(jsonobject.optString("iorMc", ""));
                bkitembean.setIorMh(jsonobject.optString("iorMh", ""));
                bkitembean.setIorOuc(jsonobject.optString("iorOuc", ""));
                bkitembean.setIorOuco(jsonobject.optString("iorOuco", ""));
                bkitembean.setIorOucu(jsonobject.optString("iorOucu", ""));
                bkitembean.setIorOuh(jsonobject.optString("iorOuh", ""));
                bkitembean.setIorOuho(jsonobject.optString("iorOuho", ""));
                bkitembean.setIorOuhu(jsonobject.optString("iorOuhu", ""));
                bkitembean.setIorRc(jsonobject.optString("iorRc", ""));
                bkitembean.setIorRh(jsonobject.optString("iorRh", ""));
                bkitembean.setIsmaster(jsonobject.optString("ismaster", ""));
                bkitembean.setLastgoal(jsonobject.optString("lastgoal", ""));

                bkitembean.setLasttime(jsonobject.optString("lasttime", ""));
                bkitembean.setLeague(jsonobject.optString("league", ""));
                bkitembean.setMatchIndex(jsonobject.optInt("matchIndex", 1));
                bkitembean.setMatchPage(jsonobject.optInt("matchPage", 1));
                bkitembean.setMatchTime(jsonobject.optString("matchTime", ""));
                bkitembean.setMore(jsonobject.optString("more", ""));

                bkitembean.setNo1(jsonobject.optString("no1", ""));
                bkitembean.setNo2(jsonobject.optString("no2", ""));
                bkitembean.setNo3(jsonobject.optString("no3", ""));
                bkitembean.setNo4(jsonobject.optString("no4", ""));
                bkitembean.setNo5(jsonobject.optString("no5", ""));
                bkitembean.setNo6(jsonobject.optString("no6", ""));
                bkitembean.setNo7(jsonobject.optString("no7", ""));
                bkitembean.setNo8(jsonobject.optString("no8", ""));
                bkitembean.setNo9(jsonobject.optString("no9", ""));
                bkitembean.setNo10(jsonobject.optString("no10", ""));
                bkitembean.setNo11(jsonobject.optString("no11", ""));
                bkitembean.setNo12(jsonobject.optString("no12", ""));

                bkitembean.setNowsession(jsonobject.optString("nowsession", ""));
                bkitembean.setPlay(jsonobject.optString("play", ""));
                bkitembean.setRatio(jsonobject.optString("ratio", ""));
                bkitembean.setRatioO(jsonobject.optString("ratioO", ""));
                bkitembean.setRatioOuco(jsonobject.optString("ratioOuco", ""));
                bkitembean.setRatioOucu(jsonobject.optString("ratioOucu", ""));
                bkitembean.setRatioOuho(jsonobject.optString("ratioOuho", ""));
                bkitembean.setRatioOuhu(jsonobject.optString("ratioOuhu", ""));
                bkitembean.setRatioU(jsonobject.optString("ratioU", ""));
                bkitembean.setRetimeset(jsonobject.optString("retimeset", ""));
                bkitembean.setRoll(jsonobject.optString("roll", ""));
                bkitembean.setScoreC(jsonobject.optString("scoreC", ""));
                bkitembean.setScoreH(jsonobject.optString("scoreH", ""));
                bkitembean.setScorec(jsonobject.optString("scorec", ""));
                bkitembean.setScoreh(jsonobject.optString("scoreh", ""));

                bkitembean.setStrEven(jsonobject.optString("strEven", ""));
                bkitembean.setStrOdd(jsonobject.optString("strOdd", ""));
                bkitembean.setStrong(jsonobject.optString("strong", ""));
                bkitembean.setTeamC(jsonobject.optString("teamC", ""));
                bkitembean.setTeamH(jsonobject.optString("teamH", ""));
                bkitembean.setTimer(jsonobject.optString("timer", ""));

                bkitembean.setTmp1(jsonobject.optString("tmp1", ""));
                bkitembean.setTmp2(jsonobject.optString("tmp2", ""));
                bkitembean.setTmp3(jsonobject.optString("tmp3", ""));
                bkitems.add(bkitembean);
            }
            bkbean.setBkBean_normal_items(bkitems);
            bkBeans_normal.add(bkbean);
        }
    }

    private void FootBallJson_bodan(JSONObject jsonObject)//足球波蛋
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        for(int k=0;k<jsonArray.length();k++){
            FootBallbodan ballBean2 = new FootBallbodan();
            ArrayList<FootBallbodan.FootBallbodan_item> sports = new ArrayList<FootBallbodan.FootBallbodan_item>();
            JSONObject jsonObject1=jsonArray.optJSONObject(k);
            JSONArray value = jsonObject1.optJSONArray("detailList");
            ballBean2.setTitle(jsonObject1.optString("leagueName"));
            for (int i = 0; i < value.length(); i++) {
                try {
                    FootBallbodan.FootBallbodan_item bean = new FootBallbodan.FootBallbodan_item();
                    JSONObject tempjsonobject = value.getJSONObject(i);
                    bean.setGid(tempjsonobject.optString("gid", ""));
                    bean.setGnumC(tempjsonobject.optString("gnumC", ""));
                    bean.setGnumH(tempjsonobject.optString("gnumH", ""));
                    bean.setLeague(tempjsonobject.optString("league", ""));
                    bean.setMatchIndex(tempjsonobject.optInt("matchIndex"));
                    bean.setMatchPage(tempjsonobject.optInt("matchPage"));
                    bean.setMatchTime(tempjsonobject.optString("matchTime", ""));
                    bean.setRoll(tempjsonobject.optString("roll", ""));
                    bean.setStrong(tempjsonobject.optString("strong", ""));
                    bean.setTeamC(tempjsonobject.optString("teamC", ""));
                    bean.setTeamH(tempjsonobject.optString("teamH", ""));
                    bean.setTmp1(tempjsonobject.optString("tmp1", ""));
                    bean.setTmp2(tempjsonobject.optString("tmp2", ""));
                    bean.setTmp3(tempjsonobject.optString("tmp3", ""));

                    bean.setIorH0c0(tempjsonobject.optString("iorH0c0", ""));
                    bean.setIorH0c1(tempjsonobject.optString("iorH0c1", ""));
                    bean.setIorH0c2(tempjsonobject.optString("iorH0c2", ""));
                    bean.setIorH0c3(tempjsonobject.optString("iorH0c3", ""));
                    bean.setIorH0c4(tempjsonobject.optString("iorH0c4", ""));

                    bean.setIorH1c0(tempjsonobject.optString("iorH1c0", ""));
                    bean.setIorH1c1(tempjsonobject.optString("iorH1c1", ""));
                    bean.setIorH1c2(tempjsonobject.optString("iorH1c2", ""));
                    bean.setIorH1c3(tempjsonobject.optString("iorH1c3", ""));
                    bean.setIorH1c4(tempjsonobject.optString("iorH1c4", ""));

                    bean.setIorH2c0(tempjsonobject.optString("iorH2c0", ""));
                    bean.setIorH2c1(tempjsonobject.optString("iorH2c1", ""));
                    bean.setIorH2c2(tempjsonobject.optString("iorH2c2", ""));
                    bean.setIorH2c3(tempjsonobject.optString("iorH2c3", ""));
                    bean.setIorH2c4(tempjsonobject.optString("iorH2c4", ""));

                    bean.setIorH3c0(tempjsonobject.optString("iorH3c0", ""));
                    bean.setIorH3c1(tempjsonobject.optString("iorH3c1", ""));
                    bean.setIorH3c2(tempjsonobject.optString("iorH3c2", ""));
                    bean.setIorH3c3(tempjsonobject.optString("iorH3c3", ""));
                    bean.setIorH3c4(tempjsonobject.optString("iorH3c4", ""));

                    bean.setIorH3c0(tempjsonobject.optString("iorH3c0", ""));
                    bean.setIorH3c1(tempjsonobject.optString("iorH3c1", ""));
                    bean.setIorH3c2(tempjsonobject.optString("iorH3c2", ""));
                    bean.setIorH3c3(tempjsonobject.optString("iorH3c3", ""));
                    bean.setIorH3c4(tempjsonobject.optString("iorH3c4", ""));

                    bean.setIorH4c0(tempjsonobject.optString("iorH4c0", ""));
                    bean.setIorH4c1(tempjsonobject.optString("iorH4c1", ""));
                    bean.setIorH4c2(tempjsonobject.optString("iorH4c2", ""));
                    bean.setIorH4c3(tempjsonobject.optString("iorH4c3", ""));
                    bean.setIorH4c4(tempjsonobject.optString("iorH4c4", ""));

                    bean.setIorOvc(tempjsonobject.optString("iorOvc", ""));
                    bean.setIorOvh(tempjsonobject.optString("iorOvh", ""));
                    bean.setTag(tempjsonobject.optInt("tag", 1));
                    bean.setTimeType(tempjsonobject.optString("timeType", ""));
                    sports.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ballBean2.setFootBallbodan_items(sports);
            footBallbodans.add(ballBean2);
        }
    }

    private void FootBallJson_HQ(JSONObject jsonObject)//足球 全场/半场
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        for(int k=0;k<jsonArray.length();k++){
            FootBallHq ballBean2 = new FootBallHq();
            ArrayList<FootBallHq.FootBallHq_item> sports = new ArrayList<FootBallHq.FootBallHq_item>();
            JSONObject jsonObject1=jsonArray.optJSONObject(k);
            JSONArray value = jsonObject1.optJSONArray("detailList");
            ballBean2.setTitle(jsonObject1.optString("leagueName"));
            for (int i = 0; i < value.length(); i++) {
                try {
                    FootBallHq.FootBallHq_item bean = new FootBallHq.FootBallHq_item();
                    JSONObject tempjsonobject = value.getJSONObject(i);
                    bean.setGid(tempjsonobject.optString("gid", ""));
                    bean.setGnumC(tempjsonobject.optString("gnumC", ""));
                    bean.setGnumH(tempjsonobject.optString("gnumH", ""));
                    bean.setLeague(tempjsonobject.optString("league", ""));
                    bean.setMatchIndex(tempjsonobject.optInt("matchIndex"));
                    bean.setMatchPage(tempjsonobject.optInt("matchPage"));
                    bean.setMatchTime(tempjsonobject.optString("matchTime", ""));
                    bean.setRoll(tempjsonobject.optString("roll", ""));
                    bean.setStrong(tempjsonobject.optString("strong", ""));
                    bean.setTeamC(tempjsonobject.optString("teamC", ""));
                    bean.setTeamH(tempjsonobject.optString("teamH", ""));
                    bean.setTmp1(tempjsonobject.optString("tmp1", ""));
                    bean.setTmp2(tempjsonobject.optString("tmp2", ""));
                    bean.setTmp3(tempjsonobject.optString("tmp3", ""));

                    bean.setIorFcc(tempjsonobject.optString("iorFcc", ""));
                    bean.setIorFch(tempjsonobject.optString("iorFch", ""));
                    bean.setIorFcn(tempjsonobject.optString("iorFcn", ""));
                    bean.setIorFhc(tempjsonobject.optString("iorFhc", ""));
                    bean.setIorFhh(tempjsonobject.optString("iorFhh", ""));
                    bean.setIorFhn(tempjsonobject.optString("iorFhn", ""));
                    bean.setIorFnc(tempjsonobject.optString("iorFnc", ""));
                    bean.setIorFnh(tempjsonobject.optString("iorFnh", ""));
                    bean.setIorFnn(tempjsonobject.optString("iorFnn", ""));
                    bean.setTimeType(tempjsonobject.optString("timeType", ""));
                    sports.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ballBean2.setFootBallHq_items(sports);
            footBallHqs.add(ballBean2);
        }
    }

    private void FootBallJson_RuQiu(JSONObject jsonObject)//足球 总入球
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        for(int k=0;k<jsonArray.length();k++){
            FootBallRuQiu ballBean2 = new FootBallRuQiu();
            ArrayList<FootBallRuQiu.FootBallRuQiu_item> sports = new ArrayList<FootBallRuQiu.FootBallRuQiu_item>();
            JSONObject jsonObject1=jsonArray.optJSONObject(k);
            JSONArray value = jsonObject1.optJSONArray("detailList");
            ballBean2.setTitle(jsonObject1.optString("leagueName"));
            for (int i = 0; i < value.length(); i++) {
                try {
                    FootBallRuQiu.FootBallRuQiu_item bean = new FootBallRuQiu.FootBallRuQiu_item();
                    JSONObject tempjsonobject = value.getJSONObject(i);
                    bean.setGid(tempjsonobject.optString("gid", ""));
                    bean.setGnumC(tempjsonobject.optString("gnumC", ""));
                    bean.setGnumH(tempjsonobject.optString("gnumH", ""));
                    bean.setLeague(tempjsonobject.optString("league", ""));
                    bean.setMatchIndex(tempjsonobject.optInt("matchIndex"));
                    bean.setMatchPage(tempjsonobject.optInt("matchPage"));
                    bean.setMatchTime(tempjsonobject.optString("matchTime", ""));
                    bean.setRoll(tempjsonobject.optString("roll", ""));
                    bean.setStrong(tempjsonobject.optString("strong", ""));
                    bean.setTeamC(tempjsonobject.optString("teamC", ""));
                    bean.setTeamH(tempjsonobject.optString("teamH", ""));
                    bean.setTmp1(tempjsonobject.optString("tmp1", ""));
                    bean.setTmp2(tempjsonobject.optString("tmp2", ""));
                    bean.setTmp3(tempjsonobject.optString("tmp3", ""));
                    bean.setTimeType(tempjsonobject.optString("timeType", ""));

                    bean.setIorEven(tempjsonobject.optString("iorEven", ""));
                    bean.setIorMc(tempjsonobject.optString("iorMc", ""));
                    bean.setIorMh(tempjsonobject.optString("iorMh", ""));
                    bean.setIorMn(tempjsonobject.optString("iorMn", ""));
                    bean.setIorOdd(tempjsonobject.optString("iorOdd", ""));
                    bean.setIorOver(tempjsonobject.optString("iorOver", ""));
                    bean.setIorT01(tempjsonobject.optString("iorT01", ""));
                    bean.setIorT23(tempjsonobject.optString("iorT23", ""));
                    bean.setIorT46(tempjsonobject.optString("iorT46", ""));
                    sports.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ballBean2.setFootBallRuQiu_items(sports);
            footBallRuQius.add(ballBean2);
        }
    }
    //FT r hpd pd t f p3
    //Bk bk_r_main bk_p3


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(publicDialog!=null)
        {
            publicDialog.dismiss();
            publicDialog=null;
        }
    }

    private void SortData()
    {
//        if(sports_Normal!=null || sports_Normal.size()>0)////11111111111111
//        {
//            SortMethod.sortFor_Sports(sports_Normal);
//        }
//        if(sports_guoguan!=null || sports_guoguan.size()>0)////22222222222222222222222222
//        {
//            SortMethod.sortFor_Sports(sports_guoguan);
//        }
//        if(bkBeans_guoguan!=null || bkBeans_guoguan.size()>0)////3333333333333333
//        {
//            SortMethod.sortFor_Sports(bkBeans_guoguan);
//        }
//        if(bkBeans_normal!=null || bkBeans_normal.size()>0)////4444444444444444
//        {
//            SortMethod.sortFor_Sports(bkBeans_normal);
//        }
//        if(footBallbodans!=null || footBallbodans.size()>0)////555555555555555555
//        {
//            SortMethod.sortFor_Sports(footBallbodans);
//        }
//        if(footBallHqs!=null || footBallHqs.size()>0)////66666666666666666
//        {
//            SortMethod.sortFor_Sports(footBallHqs);
//        }
//        if(footBallRuQius!=null || footBallRuQius.size()>0)////7777777777777777777
//        {
//            SortMethod.sortFor_Sports(footBallRuQius);
//        }
    }
}
