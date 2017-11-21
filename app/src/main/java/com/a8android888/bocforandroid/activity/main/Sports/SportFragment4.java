package com.a8android888.bocforandroid.activity.main.Sports;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.SportsMatchResultAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.BKSportsResultBean;
import com.a8android888.bocforandroid.Bean.FTSportsResultBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.Timeutil;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.a8android888.bocforandroid.view.DatetimeDialog;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/4/5.
 */
public class SportFragment4 extends BaseFragment implements View.OnClickListener{

    AnimatedExpandableListView expandablelistview;
    boolean isRuning=false;
    PublicDialog publicDialog;
    SwipeRefreshLayout swipl;
    TextView startdate;
    DatetimeDialog datetimeDialog;
    int year,month,day;
    SportsMatchResultAdapter sportsMatchResultAdapter;
    ArrayList<BKSportsResultBean> BKSportsResultBeans =new ArrayList<BKSportsResultBean>();
    ArrayList<FTSportsResultBean> ftSportsResultBeans=new ArrayList<FTSportsResultBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_ball_result,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Initview();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(!state)
            GetData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
        GetData();
    }

    private void GetData()
    {
        if(isRuning)
        {
            return;
        }
        isRuning=true;
        if(publicDialog==null)
        {
            publicDialog=new PublicDialog(getActivity(),false);
        }
        publicDialog.show();
        String type="ft";
        final String tag=((Sports_MainActivity)getActivity()).GetSportState();
        if(tag==null|| tag.equalsIgnoreCase(""))return;

        if(tag.contains("足球"))
        {
            type="ft";
        }
        if(tag.contains("篮球"))
        {
            type="bk";
        }
        RequestParams params=new RequestParams();
        params.put("date",startdate.getText().toString().trim());
        params.put("ballType", type);
        Httputils.PostWithBaseUrl(Httputils.BallResult, params, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject4",jsonObject.toString());
                isRuning = false;
                if (publicDialog != null && getActivity() != null) {
                    publicDialog.dismiss();
                }
                swipl.setRefreshing(false);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }
                ftSportsResultBeans.clear();
                BKSportsResultBeans.clear();
                int Adastyle = BALL_RESULT_TYPE.FootBall_Result.value();
                if (tag.contains("足球")) {
                    FootBallJson(jsonObject);
                    if (ftSportsResultBeans.size() < 1) {
                        ToastUtil.showMessage(getActivity(), getActivity().getString(R.string.projectnoexits));
                    }
                } else if (tag.contains("篮球")) {
                    Adastyle = BALL_RESULT_TYPE.BasketBall_Result.value();
                    BasketBallJson(jsonObject);
                    if (BKSportsResultBeans.size() < 1) {
                        ToastUtil.showMessage(getActivity(), getActivity().getString(R.string.projectnoexits));
                    }
                }

                SetAdapter(Adastyle);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                isRuning = false;
                if (publicDialog != null && getActivity() != null) {
                    publicDialog.dismiss();
                }
                swipl.setRefreshing(false);
            }
        });

    }

    private void Initview()
    {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        expandablelistview=FindView(R.id.expandablelistview);
        expandablelistview.setGroupIndicator(null);
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
        swipl=FindView(R.id.swipl);
        swipl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetData();
            }
        });
        startdate=FindView(R.id.startdate);
        startdate.setText(Httputils.getStringtimeymd());
        RelativeLayout startdate_layout=FindView(R.id.startdate_layout);
        startdate_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////////////////////
                if (datetimeDialog == null) {
                    datetimeDialog = new DatetimeDialog(getActivity(), new DatetimeDialog.OnSelectDialogListener() {
                        @Override
                        public void OnselectDate(int year, int mouth, int date) {
                            startdate.setText(year + "-" + (mouth + 1) + "-" + date);
                        }
                    });
                }
                //////////////////////////////
                datetimeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (startdate.getText().toString().trim().length() < 5) {
                            startdate.setText(year + "-" + (month + 1) + "-" + day);
                        }
                        GetData();
                    }
                });

                datetimeDialog.setArg(startdate.getText().toString());
                datetimeDialog.show();
            }
        });
        ImageView imageView=FindView(R.id.lastday);
        imageView.setOnClickListener(this);
        ImageView imageView2=FindView(R.id.nextday);
        imageView2.setOnClickListener(this);
        /////////////////////////////////////////////
    }

    private void SetAdapter(int type)
    {
        if(sportsMatchResultAdapter==null)
        {
            if(BKSportsResultBeans==null)return;
            if(type==BALL_RESULT_TYPE.BasketBall_Result.value())
                sportsMatchResultAdapter=new SportsMatchResultAdapter(getActivity(),BKSportsResultBeans,type);

            else if(type==BALL_RESULT_TYPE.FootBall_Result.value())
                sportsMatchResultAdapter=new SportsMatchResultAdapter(getActivity(),ftSportsResultBeans,type);

            if(sportsMatchResultAdapter!=null)
            expandablelistview.setAdapter(sportsMatchResultAdapter);
        }
        else
        {
            if(type==BALL_RESULT_TYPE.BasketBall_Result.value())
                sportsMatchResultAdapter.NotifyAdapter(BKSportsResultBeans,type);

            else if(type==BALL_RESULT_TYPE.FootBall_Result.value())
                sportsMatchResultAdapter.NotifyAdapter(ftSportsResultBeans,type);
        }
    }

    private void BasketBallJson(JSONObject jsonObject)
    {
            JSONArray dataobj = jsonObject.optJSONArray("datas");

        for (int k = 0; k <dataobj.length(); k++) {
            BKSportsResultBean ballBean2 = new BKSportsResultBean();
            ArrayList<BKSportsResultBean.SportsResultBean_item> sports = new ArrayList<BKSportsResultBean.SportsResultBean_item>();

            JSONObject jsobj=dataobj.optJSONObject(k);
            ballBean2.setTitle(jsobj.optString("league",""));
            JSONArray result = jsobj.optJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                try {
                    BKSportsResultBean.SportsResultBean_item bean = new BKSportsResultBean.SportsResultBean_item();
                    JSONObject tempjsonobject = result.getJSONObject(i);
//                    bean.setGid(tempjsonobject.optString("gid", ""));
//                    bean.setId(tempjsonobject.optInt("id", 1));
                    bean.setLeague(tempjsonobject.optString("league", ""));
//                    bean.setLeg(tempjsonobject.optString("leg", ""));
//                    bean.setLegGid(tempjsonobject.optString("legGid", ""));
//                    bean.setMatchIndex(tempjsonobject.optInt("matchIndex", 1));
                    bean.setMatchRealTime(tempjsonobject.optString("matchRealTime", ""));
//                    bean.setMatchSettled(tempjsonobject.optInt("matchSettled", 1));
//                    bean.setMatchStatus(tempjsonobject.optInt("matchStatus", 1));
//                    bean.setMatchTime(tempjsonobject.optString("matchTime", ""));
//                    bean.setMatchType(tempjsonobject.optString("matchType", ""));
//                    bean.setSearchTime(tempjsonobject.optInt("searchTime", 100));
                    bean.setStageC1(tempjsonobject.optInt("stageC1", -1));
                    bean.setStageC2(tempjsonobject.optInt("stageC2", -1));
                    bean.setStageC3(tempjsonobject.optInt("stageC3", -1));
                    bean.setStageC4(tempjsonobject.optInt("stageC4", -1));

                    bean.setStageCA(tempjsonobject.optString("stageCA", ""));
                    bean.setStageCF(tempjsonobject.optInt("stageCF",-1));
                    bean.setStageCS(tempjsonobject.optInt("stageCS", -1));
                    bean.setStageCX(tempjsonobject.optInt("stageCX", -1));
                    bean.setStageH1(tempjsonobject.optInt("stageH1", -1));
                    bean.setStageH2(tempjsonobject.optInt("stageH2", -1));
                    bean.setStageH3(tempjsonobject.optInt("stageH3", -1));
                    bean.setStageH4(tempjsonobject.optInt("stageH4", -1));
                    bean.setStageHA(tempjsonobject.optString("stageHA", ""));

                    bean.setStageHF(tempjsonobject.optInt("stageHF", -1));
                    bean.setStageHS(tempjsonobject.optInt("stageHS", -1));
                    bean.setStageHX(tempjsonobject.optInt("stageHX", -1));
                    bean.setTeamC(tempjsonobject.optString("teamC", ""));
                    bean.setTeamH(tempjsonobject.optString("teamH", ""));
//                    bean.setTmp1(tempjsonobject.optString("tmp1", ""));
//                    bean.setTmp2(tempjsonobject.optString("tmp2", ""));
//                    bean.setTmp3(tempjsonobject.optString("tmp3", ""));
                    sports.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            ballBean2.setSportsResultBean_items(sports);
            BKSportsResultBeans.add(ballBean2);
        }




    }

    private void FootBallJson(JSONObject jsonObject)
    {
        JSONArray jsonArray = jsonObject.optJSONArray("datas");


        for (int k = 0; k <  jsonArray.length(); k++) {
            FTSportsResultBean ballBean2 = new FTSportsResultBean();
            try {
                JSONObject jsobj=jsonArray.getJSONObject(k);
                ballBean2.setTitle(jsobj.optString("league"));
                ArrayList<FTSportsResultBean.FTSportsResultBean_item> sports = new ArrayList<FTSportsResultBean.FTSportsResultBean_item>();
                JSONArray result=jsobj.optJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    try {
                        FTSportsResultBean.FTSportsResultBean_item bean = new FTSportsResultBean.FTSportsResultBean_item();
                        JSONObject tempjsonobject = result.getJSONObject(i);
                        LogTools.e("tempjsonobject", tempjsonobject.toString());
//                        bean.setGid(tempjsonobject.optString("gid", ""));
//                        bean.setId(tempjsonobject.optInt("id", 1));
                        bean.setLeague(tempjsonobject.optString("league", ""));
//                        bean.setLeg(tempjsonobject.optString("leg", ""));
//                        bean.setLegGid(tempjsonobject.optString("legGid", ""));
//                        bean.setMatchIndex(tempjsonobject.optInt("matchIndex", 1));
                        bean.setMatchRealTime(tempjsonobject.optString("matchRealTime", ""));
//                        bean.setMatchSettled(tempjsonobject.optInt("matchSettled", 1));
//                        bean.setMatchStatus(tempjsonobject.optInt("matchStatus", 1));
//                        bean.setMatchTime(tempjsonobject.optString("matchTime", ""));
//                        bean.setMatchType(tempjsonobject.optString("matchType", ""));
//                        bean.setSearchTime(tempjsonobject.optInt("searchTime", 100));
//                    "yaozheFlScoreC": null,
//                            "yaozheFlScoreH": null,
//                            "yaozheHrScoreC": null,
//                            "yaozheHrScoreH": null
                        bean.setFlScoreC(tempjsonobject.optString("flScoreC", ""));
//                        bean.setFlScoreCCal(tempjsonobject.optString("flScoreCCal", ""));
                        bean.setFlScoreH(tempjsonobject.optString("flScoreH", ""));
//                        bean.setFlScoreHCal(tempjsonobject.optString("flScoreHCal", ""));
                        bean.setHrScoreC(tempjsonobject.optString("hrScoreC", ""));
//                        bean.setHrScoreCCal(tempjsonobject.optString("hrScoreCCal", ""));
                        bean.setHrScoreH(tempjsonobject.optString("hrScoreH", ""));
//                        bean.setHrScoreHCal(tempjsonobject.optString("hrScoreHCal", ""));
                        bean.setFlScoreCString(tempjsonobject.optString("flScoreCString", ""));
                        bean.setFlScoreHString(tempjsonobject.optString("flScoreHString", ""));
                        bean.setHrScoreCString(tempjsonobject.optString("hrScoreCString", ""));
                        bean.setHrScoreHString(tempjsonobject.optString("hrScoreHString", ""));
//                        bean.setYaozheFlScoreC(tempjsonobject.optInt("yaozheFlScoreC", 1));
//                        bean.setYaozheFlScoreH(tempjsonobject.optInt("yaozheFlScoreH", 1));
//                        bean.setYaozheHrScoreC(tempjsonobject.optInt("yaozheHrScoreC", 1));
//                        bean.setYaozheHrScoreH(tempjsonobject.optInt("yaozheHrScoreH", 1));

                        bean.setTeamC(tempjsonobject.optString("teamC", ""));
                        bean.setTeamH(tempjsonobject.optString("teamH", ""));
//                        bean.setTmp1(tempjsonobject.optString("tmp1", ""));
//                        bean.setTmp2(tempjsonobject.optString("tmp2", ""));
//                        bean.setTmp3(tempjsonobject.optString("tmp3", ""));
                        sports.add(bean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                ballBean2.setFtSportsResultBean_items(sports);
                ftSportsResultBeans.add(ballBean2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.lastday:
                startdate.setText(Timeutil.getDay(startdate.getText().toString().trim(), -1));
                GetData();
                break;
            case R.id.nextday:
                startdate.setText(Timeutil.getDay(startdate.getText().toString().trim(), 1));
                GetData();
                break;
        }
    }
}
