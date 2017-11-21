package com.a8android888.bocforandroid.activity.main.Sports;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.SportsOrderResultAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.SportsOrderResulrBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.activity.user.RegActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.DatetimeDialog;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */
public class SportFragment5 extends BaseFragment implements View.OnClickListener{

    ListView listView;
    int mtotalItemCount,mfirstVisibleItem,page=1;
    boolean hasdata=true;
    SwipeRefreshLayout swipl;
    static String strs[]=new String[2];
    ArrayList<SportsOrderResulrBean> sportsbeans=new ArrayList<SportsOrderResulrBean>();
    DatetimeDialog datetimeDialog;
    SportsOrderResultAdapter sportadapter;
    LinearLayout nologinlayout;
    String Username;
    TextView btn_register,btn_login;
    RadioButton Recordbtn;
    static int SelectType=1;
    RadioGroup titles_gp;
    RelativeLayout maskview;
    PublicDialog publicDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_sports_order,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        page=1;
        InitView();
//        sports_orderlist_adapter=new Sports_orderlist_Adapter(getActivity());
//        listview.setAdapter(sports_orderlist_adapter);
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if( !state && getActivity()!=null)
        {
            BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();
                Username=baseApplication.getBaseapplicationUsername();
                if(Username!=null && !Username.equalsIgnoreCase("")) {
                    nologinlayout.setVisibility(View.GONE);
                    GetTypeForPost(true);
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();
        Username=baseApplication.getBaseapplicationUsername();

        if(Username==null || Username.equalsIgnoreCase(""))
        {
            nologinlayout.setVisibility(View.VISIBLE);
        }
        else
        {
            nologinlayout.setVisibility(View.GONE);
            GetTypeForPost(true);
        }
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
        if(getActivity()!=null)
        {
            BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();
            Username=baseApplication.getBaseapplicationUsername();
            if(Username!=null && !Username.equalsIgnoreCase(""))
                GetTypeForPost(true);

        }
    }

    private void InitView()
    {

        BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();
        Username=baseApplication.getBaseapplicationUsername();

        nologinlayout=FindView(R.id.nologinlayout);
        if(Username==null || Username.equalsIgnoreCase(""))
        {
            nologinlayout.setVisibility(View.VISIBLE);
        }
        else
        {
            nologinlayout.setVisibility(View.GONE);
        }

        maskview=FindView(R.id.maskview);
        btn_login=FindView(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_register=FindView(R.id.btn_register);
        btn_register.setOnClickListener(this);
        listView=FindView(R.id.listview);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mtotalItemCount - mfirstVisibleItem < 11 && scrollState == 0 && hasdata) {
                    LogTools.e("runing", "runing");
                    swipl.setRefreshing(true);
                    page++;
                    GetTypeForPost(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view.getChildAt(0)!=null && view.getChildAt(0).getTop()==0)
                {
                    if(swipl!=null)
                        swipl.setEnabled(true);
                }
                else
                {
                    if(swipl!=null)
                        swipl.setEnabled(false);
                }
                mfirstVisibleItem = firstVisibleItem;
                mtotalItemCount = totalItemCount;
            }
        });

        swipl=FindView(R.id.swipl);
        swipl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTypeForPost(true);
            }
        });

        titles_gp=FindView(R.id.titles_gp);
        titles_gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radioButton1:
                        SelectType=1;
                        break;
                    case R.id.radioButton2:
                        SelectType=2;
                        break;
                    case R.id.radioButton3:
                        SelectType=3;
                        break;
                    case R.id.radioButton4:
                        SelectType=4;
                        break;
                }
                LogTools.e("Check", SelectType + "");
                /////////先判断登陆
                if(getActivity()!=null)
                {
                    BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();
                    Username=baseApplication.getBaseapplicationUsername();
                    if(Username!=null && !Username.equalsIgnoreCase(""))
                        GetTypeForPost(true);

                }
                //////////////////////
                if(Recordbtn!=null)
                {
                    Recordbtn.setTextColor(Color.BLACK);
                }
                RadioButton radioButton=(RadioButton)titles_gp.findViewById(checkedId);
                radioButton.setTextColor(Color.WHITE);
                Recordbtn=radioButton;
            }
        });
        String titles[]=getResources().getStringArray(R.array.pay_record_titles);
        for (int i = 0; i <titles_gp.getChildCount(); i++) {

            RadioButton radioButton=(RadioButton)titles_gp.getChildAt(i);
            radioButton.setText(titles[i]);
            radioButton.setButtonDrawable(new ColorDrawable(0));
        }

        Recordbtn=((RadioButton)titles_gp.getChildAt(SelectType-1));
        Recordbtn.setChecked(true);
    }

    private void PostData(final boolean init)
    {
        if(init)
        {
            page=1;
        }
        hasdata = false;//控制有没有数据和是否运行中
        String type="";
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
        RequestParams requestParams=new RequestParams();
        requestParams.put("userName", Username);
//        requestParams.put("beginTime", startdate.getText().toString());
//        requestParams.put("endTime", enddate.getText().toString());
        String iiii[]={"today","oneweek","onemonth","threemonth"};
        requestParams.put("time", iiii[SelectType - 1] + "");//iiii[SelectType-1]+""
//        String flat=((Sports_MainActivity)getActivity()).PlatFormTag;
//        requestParams.put("flat", flat);
        requestParams.put("ballType", type);//ft,bk
//        requestParams.put("type",type);
        requestParams.put("pageNo", page + "");
        requestParams.put("pageSize", "20");

        if(publicDialog==null)
        {
            publicDialog=new PublicDialog(getActivity(),false);
        }
        publicDialog.show();
        Httputils.PostWithBaseUrl(Httputils.flatRecord, requestParams, new MyJsonHttpResponseHandler(getActivity(), false) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (publicDialog != null && getActivity() != null) {
                    publicDialog.dismiss();
                }
                hasdata = true;
                LogTools.e("jsonObject", jsonObject.toString());
                swipl.setRefreshing(false);
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000")) {
                    sportsbeans.clear();
                    hasdata = false;
                    SetAdapter(init);
                    ToastUtil.showMessage(getActivity(), jsonObject.optString("msg",""));
                    return;
                }

                if (init) {
                    sportsbeans.clear();
                }
                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                if (jsonArray == null || jsonArray.length() == 0) {
                    hasdata = false;
                    SetAdapter(init);
                    if (page > 1)
                        ToastUtil.showMessage(getActivity(), getString(R.string.nomoredata));
                    return;
                }
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tempjson = jsonArray.optJSONObject(i);
                        SportsOrderResulrBean bean = new SportsOrderResulrBean();
//                        bean.setBackWaterStatus(tempjson.optInt("backWaterStatus", 0));
                        bean.setBetMatchContent(tempjson.optString("betMatchContent", ""));
                        bean.setBetMatchResult(tempjson.optString("betMatchResult", ""));
                        bean.setBetNetWin(tempjson.optString("betNetWin", ""));
                        bean.setBetUsrWin(tempjson.optString("betUsrWin", ""));
                        bean.setBetVoidReason(tempjson.optString("betVoidReason", ""));

                        bean.setBetCanWin(tempjson.optDouble("betCanWin", 1));
                        bean.setBetIn(tempjson.optInt("betIn", 10));
                        bean.setBetIncome(tempjson.optString("betIncome", ""));
                        bean.setBetMatchIds(tempjson.optString("betMatchIds", ""));
                        bean.setBetMemberIpAddress(tempjson.optString("betMemberIpAddress", ""));
                        bean.setBetSportName(tempjson.optString("betSportName", ""));
                        bean.setBetSportType(tempjson.optString("betSportType", ""));
                        bean.setBetStatus(tempjson.optInt("betStatus", 17));
                        bean.setBetType(tempjson.optInt("betType", 1));
                        bean.setBetUserAgent(tempjson.optString("betUserAgent", ""));
                        bean.setBetUserName(tempjson.optString("betUserName", ""));
                        bean.setBetWagersId(tempjson.optString("betWagersId", ""));
                        LogTools.e("setBetSportName", bean.getBetSportName() + " " + bean.getBetWagersId());
                        bean.setConfirmTime(tempjson.optString("confirmTime", ""));
                        bean.setCreateTime(tempjson.optString("createTime", ""));
                        JSONArray jsonarray2 = tempjson.optJSONArray("details");
                        if (jsonarray2 != null && jsonarray2.length() > 0) {

                            List<SportsOrderResulrBean.DetailsBean> beans = new ArrayList<SportsOrderResulrBean.DetailsBean>();
                            for (int k = 0; k < jsonarray2.length(); k++) {
//                                        "stageHA": "",
//                                        "stageHF":"",
//                                        "stageHS": "",
//                                        "stageHX": ""
                                SportsOrderResulrBean.DetailsBean deatilbean = new SportsOrderResulrBean.DetailsBean();
                                JSONObject tempjsonobject = jsonarray2.optJSONObject(k);
                                deatilbean.setBetScoreC(tempjsonobject.optString("betScoreC", ""));
                                deatilbean.setBetScoreCCur(tempjsonobject.optString("betScoreCCur", ""));
                                deatilbean.setBetScoreH(tempjsonobject.optString("betScoreH", ""));
                                deatilbean.setBetScoreHCur(tempjsonobject.optString("betScoreHCur", ""));
                                deatilbean.setLeg(tempjsonobject.optString("leg", ""));
                                /////score
                                JSONObject scoreobj = tempjsonobject.optJSONObject("score");
                                SportsOrderResulrBean.DetailsBean.ScoreBean scoreBean = new SportsOrderResulrBean.DetailsBean.ScoreBean();
                                scoreBean.setFlScoreC(scoreobj.optString("flScoreC", ""));
                                scoreBean.setFlScoreH(scoreobj.optString("flScoreH", ""));
                                scoreBean.setHrScoreC(scoreobj.optString("hrScoreC", ""));
                                scoreBean.setHrScoreH(scoreobj.optString("hrScoreH", ""));
                                scoreBean.setSportType(scoreobj.optString("sportType", ""));

                                scoreBean.setStageC1(scoreobj.optString("stageC1", ""));
                                scoreBean.setStageC2(scoreobj.optString("stageC2", ""));
                                scoreBean.setStageC3(scoreobj.optString("stageC3", ""));
                                scoreBean.setStageC4(scoreobj.optString("stageC4", ""));

                                scoreBean.setStageCA(scoreobj.optString("stageCA", ""));
                                scoreBean.setStageCF(scoreobj.optString("stageCF", ""));
                                scoreBean.setStageCS(scoreobj.optString("stageCS", ""));
                                scoreBean.setStageCX(scoreobj.optString("stageCX", ""));

                                scoreBean.setStageH1(scoreobj.optString("stageH1", ""));
                                scoreBean.setStageH2(scoreobj.optString("stageH2", ""));
                                scoreBean.setStageH3(scoreobj.optString("stageH3", ""));
                                scoreBean.setStageH4(scoreobj.optString("stageH4", ""));

                                scoreBean.setStageHA(scoreobj.optString("stageHA", ""));
                                scoreBean.setStageHF(scoreobj.optString("stageHF", ""));
                                scoreBean.setStageHS(scoreobj.optString("stageHS", ""));
                                scoreBean.setStageHX(scoreobj.optString("stageHX", ""));
                                /////score
                                deatilbean.setScoreBean(scoreBean);

                                deatilbean.setBetDx(tempjsonobject.optString("betDx", ""));
                                deatilbean.setBetDxH(tempjsonobject.optString("betDxH", ""));
                                deatilbean.setBetIndex(tempjsonobject.optString("betIndex", ""));
                                deatilbean.setBetOdds(tempjsonobject.optString("betOdds", ""));
                                deatilbean.setBetOddsDes(tempjsonobject.optString("betOddsDes", ""));
                                deatilbean.setBetOddsMinus(tempjsonobject.optInt("betOddsMinus", 0));
                                deatilbean.setBetOddsName(tempjsonobject.optString("betOddsName", ""));
                                deatilbean.setBetRq(tempjsonobject.optString("betRq", ""));
                                deatilbean.setBetRqH(tempjsonobject.optString("betRqH", ""));
                                deatilbean.setBetRqTeam(tempjsonobject.optString("betRqTeam", ""));
                                deatilbean.setBetRqTeamH(tempjsonobject.optString("betRqTeamH", ""));
                                deatilbean.setBetStatus(tempjsonobject.optInt("betStatus", 0));
                                deatilbean.setBetTime(tempjsonobject.optString("betTime", ""));
                                deatilbean.setBetVs(tempjsonobject.optString("betVs", ""));
                                deatilbean.setBetWagersId(tempjsonobject.optString("betWagersId", ""));
                                deatilbean.setBtype(tempjsonobject.optString("btype", ""));
                                deatilbean.setCreateTime(tempjsonobject.optString("createTime", ""));
                                deatilbean.setDtype(tempjsonobject.optString("dtype", ""));
                                deatilbean.setGid(tempjsonobject.optString("gid", ""));
                                deatilbean.setId(tempjsonobject.optInt("id", 1667));
                                deatilbean.setLeague(tempjsonobject.optString("league", ""));
                                deatilbean.setMatchId(tempjsonobject.optString("matchId", ""));
                                deatilbean.setMatchTime(tempjsonobject.optString("matchTime", ""));
                                deatilbean.setMatchType(tempjsonobject.optString("matchType", ""));
                                deatilbean.setModifyTime(tempjsonobject.optString("modifyTime", ""));
                                deatilbean.setPeriod(tempjsonobject.optString("period", ""));
                                deatilbean.setRtype(tempjsonobject.optString("rtype", ""));
                                deatilbean.setRtypeName(tempjsonobject.optString("rtypeName", ""));
                                deatilbean.setSelection(tempjsonobject.optString("selection", ""));
                                deatilbean.setStatus(tempjsonobject.optInt("status", 0));
                                deatilbean.setTeamC(tempjsonobject.optString("teamC", ""));
                                deatilbean.setTeamH(tempjsonobject.optString("teamH", ""));
                                deatilbean.setTimeType(tempjsonobject.optString("timeType", ""));
                                beans.add(deatilbean);
                            }
                            bean.setDetails(beans);
                        }
                        bean.setId(tempjson.optInt("id", 1324));
                        bean.setMatchRtype(tempjson.optString("matchRtype", ""));
                        bean.setModifyTime(tempjson.optString("modifyTime", ""));
                        bean.setOrderTime(tempjson.optString("orderTime", ""));
                        bean.setRemark(tempjson.optString("remark", ""));
                        bean.setStatus(tempjson.optInt("status", 1));
                        bean.setTimeType(tempjson.optString("timeType", ""));
                        bean.setWebFlat(tempjson.optString("webFlat", ""));
                        bean.setWebRemark(tempjson.optString("webRemark", ""));
                        sportsbeans.add(bean);
                    }

                } else {
                    hasdata = false;
                }
                ///////////////
                LogTools.e("sportsbeans", sportsbeans.size() + "");
                SetAdapter(init);

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                if (publicDialog != null && getActivity() != null) {
                    publicDialog.dismiss();
                }
                swipl.setRefreshing(false);
                page--;
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), BundleTag.RequestCode);
                break;
            case R.id.btn_register:
                startActivityForResult(new Intent(getActivity(), RegActivity.class), BundleTag.RequestCode);
                break;
        }
    }

    private void SetAdapter(boolean init)
    {
        if(sportadapter==null)
        {
            sportadapter=new SportsOrderResultAdapter(getActivity(),sportsbeans);
            listView.setAdapter(sportadapter);
        }
        else
        {
            sportadapter.NotifySetChange(sportsbeans,init);
        }
        CheckData();
    }

    private void GetTypeForPost(boolean init)
    {

        Calendar calendar = Calendar.getInstance(); //得到日历
        Date dNow = new Date();   //当前时间
        calendar.setTime(dNow);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String EndDay=sdf.format(dNow);
        String StartDay="";
        hasdata=true;//设置可以加载数据
        PostData(init);
//        switch (SelectType)
//        {
//            case 1:
//                PostData(init);
//                break;
//            case 2:
//                calendar.add(calendar.WEEK_OF_MONTH, -1);  //设置为前3月
//                StartDay=sdf.format(calendar.getTime());
//                PostData(init);
//                break;
//            case 3:
//                calendar.add(calendar.MONTH, -1);  //设置为前3月
//                StartDay=sdf.format(calendar.getTime());
//                PostData(init);
//                break;
//            case 4:
//                calendar.add(calendar.MONTH, -3);  //设置为前3月
//                StartDay=sdf.format(calendar.getTime());
//                PostData(init, StartDay, EndDay);
//                break;
//        }
    }

    private void CheckData()
    {
        if(listView==null || listView.getAdapter()==null || sportsbeans.size()==0)
        {
            maskview.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else
        {
            maskview.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}
