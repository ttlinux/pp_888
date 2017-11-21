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
import com.a8android888.bocforandroid.Bean.BKBean_Normal;
import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Normal;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
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
 * Created by Administrator on 2017/4/5. 滚球
 */
public class SportFragment1<SPORTDATA > extends BaseFragment{


    //roll re足球 re_main篮球
     int  delay=90000;
    AnimatedExpandableListView expandablelistview;
    SportsAdapter sportsAdapter;
    String type[]={"re","re_main"};
    ArrayList<SportsFootBallBean_Normal> sports2=new ArrayList<SportsFootBallBean_Normal>();
    ArrayList<BKBean_Normal> bkBeans=new ArrayList<BKBean_Normal>();
    boolean isRuning=false;
    PublicDialog publicDialog;
    RelativeLayout refresh;
    TextView minperiod;

    boolean SetSelected_Swicth=false;
//    int indexCount=0;

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
//        GetData();
//        indexCount=0;
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(state)
            ThreadTimer.Stop(getClass().getName());
        else
        {
            if(getView()!=null && getActivity()!=null)
            {
                minperiod.setText(delay/1000+"");
//                indexCount=0;
                GetData();
            }

        }
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
        String type="re";
        final String tag=((Sports_MainActivity)getActivity()).GetSportState();
        if(tag.contains("足球"))
        {
            type="re";
        }
        if(tag.contains("篮球"))
        {
            type="re_main";
        }

        if(publicDialog==null)
        {
            publicDialog=new PublicDialog(getActivity(),false);
        }
        publicDialog.show();
        RequestParams params=new RequestParams();
        params.put("timeType", "roll");
        params.put("rType", type);
        Httputils.PostWithBaseUrl(Httputils.SportsOdds, params, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
//                Wehu();
                isRuning = false;
                if (publicDialog != null && getActivity() != null) {
                    publicDialog.dismiss();
                }
                throwable.printStackTrace();
                minperiod.setText(delay / 1000 + "");
                OpenTimer();
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObjectsuccess", jsonObject.toString());
                if (publicDialog != null && getActivity() != null) {
                    publicDialog.dismiss();
                }
                isRuning = false;
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000"))
                {
                    sports2.clear();
                    bkBeans.clear();
                    SetAdapter(BALL_TYPE.FootBall_Re.value());
                    Wehu();
                    return;
                }

                sports2.clear();
                bkBeans.clear();
                int Adastyle = BALL_TYPE.FootBall_Re.value();
                if (tag.contains("足球")) {
                    FootBallJson(jsonObject);
                    if (sports2.size() < 1) {
                        ToastUtil.showMessage(getActivity(), getActivity().getString(R.string.projectnoexits));
                    }
                } else if (tag.contains("篮球")) {
                    Adastyle = BALL_TYPE.BasketBall_Re_Main.value();
                    BasketBallJson_Normal(jsonObject);
                    if (bkBeans.size() < 1) {
                        ToastUtil.showMessage(getActivity(), getActivity().getString(R.string.projectnoexits));
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
        });
    }

    private void OpenTimer()
    {
        ThreadTimer.Stop(getClass().getName());
        ThreadTimer.setListener(new ThreadTimer.OnActiviteListener() {
            @Override
            public void MinCallback(long limitmin) {
                minperiod.setText(limitmin / 1000 + "");
            }

            @Override
            public void DelayCallback() {

                GetData();
            }
        });
        ThreadTimer.Start(delay);
    }

    private void  SetAdapter(int type)
    {
//        if(sports2!=null || sports2.size()>0)////11111111111111
//        {
//            SortMethod.sortFor_Sports(sports2);
//        }
//        if(bkBeans!=null || bkBeans.size()>0)////11111111111111
//        {
//            SortMethod.sortFor_Sports(bkBeans);
//        }
        if(sportsAdapter==null)
        {
            if(type==BALL_TYPE.FootBall_Re.value())
                sportsAdapter=new SportsAdapter(getActivity(),sports2,type);
            else if(type==BALL_TYPE.BasketBall_Re_Main.value())
                sportsAdapter=new SportsAdapter(getActivity(),bkBeans,type);

            if(sportsAdapter!=null)
            expandablelistview.setAdapter(sportsAdapter);
        }
        else {
            if (type == BALL_TYPE.FootBall_Re.value())
                sportsAdapter.NotifyAdapter(sports2, type);
            else if (type == BALL_TYPE.BasketBall_Re_Main.value())
                sportsAdapter.NotifyAdapter(bkBeans, type);

//            if (!SetSelected_Swicth && sportsAdapter.getGroupCount()>0 && indexCount==0 )
//            {
//                for (int j = 0; j < count; j++) {
//                    if (j != groupPosition) {
//                        expandablelistview.collapseGroup(j);
//                    }
//                }
//                SetSelected_Swicth=true;
//                indexCount++;
//            }
        }
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
//        indexCount=0;
        GetData();
    }

    private void FootBallJson(JSONObject jsonObject)
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        if(jsonArray==null)return;
        for(int k=0;k<jsonArray.length();k++){
            SportsFootBallBean_Normal ballBean2 = new SportsFootBallBean_Normal();
            ArrayList<SportsFootBallBean_Normal.SportsFootBallBean_Normal_Item> sports = new ArrayList<SportsFootBallBean_Normal.SportsFootBallBean_Normal_Item>();
            JSONArray value = jsonArray.optJSONObject(k).optJSONArray("detailList");
            ballBean2.setTitle(jsonArray.optJSONObject(k).optString("leagueName"));
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
            sports2.add(ballBean2);
        }
    }

    private void BasketBallJson_Normal(JSONObject jsonObject)
    {
        JSONObject data=jsonObject.optJSONObject("datas");
        JSONArray jsonArray = data.optJSONArray("list");
        for(int k=0;k<jsonArray.length();k++){
            BKBean_Normal bkbean=new BKBean_Normal();
            JSONObject jsonObject1=jsonArray.optJSONObject(k);
            bkbean.setTitle(jsonObject1.optString("leagueName",""));
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
            bkBeans.add(bkbean);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(publicDialog!=null)
        {
            publicDialog.dismiss();
            publicDialog=null;
        }
    }

}
