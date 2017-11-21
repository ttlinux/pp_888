package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.lotteryorderAdapter;
import com.a8android888.bocforandroid.Adapter.one_Lottery_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.LotteryorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5. 注单
 */
public class LotteryFragment3 extends BaseFragment{

    PullToRefreshExpandableListView expandablelistview;
//    AnimatedExpandableListView expandablelistview;
    View relayout,titles_gp;
    lotteryorderAdapter sportsAdapter;
    int pageindex=1;
    ArrayList<LotteryorderBean> gameorderBeanList=new ArrayList<LotteryorderBean>();
    List<String> data = new ArrayList<String>();
    String gamecode;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return View.inflate(getActivity(),R.layout.lottery_order,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        relayout=FindView(R.id.relayout);
        relayout.setVisibility(View.GONE);
        titles_gp=FindView(R.id.titles_gp);
        titles_gp.setVisibility(View.GONE);
        expandablelistview=FindView(R.id.expandablelistview);
        expandablelistview.getRefreshableView().setGroupIndicator(null);
        expandablelistview.getRefreshableView().setDivider(null);
        expandablelistview.getRefreshableView().setSelector(android.R.color.transparent);
//        expandablelistview.getRefreshableView().setOnGroupClickListener(LotteryFragment3.this);          //可以设置组点击监听

        expandablelistview.setPullToRefreshOverScrollEnabled(true);          //可刷新
        expandablelistview.setMode(PullToRefreshBase.Mode.BOTH);     //设置模式，此模式是可以上拉，
//        expandablelistview.setGroupIndicator(null);
        gamecode=((Lottery_MainActivity)getActivity()).getLotterycode();

        //上下拉监听，注意实现这个OnRefreshListener2类的对象
        expandablelistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                refreshView.onRefreshComplete();
                LogTools.e("ffafd", "下拉刷新");
                pageindex = 1;
                getdatalist(gamecode, 31,pageindex);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                LogTools.e("ffafda", "上拉加载更多");
                refreshView.onRefreshComplete();
                pageindex ++;
                getdatalist(gamecode, 31,pageindex);
            }
        });

//        expandablelistview.setOnGroupClickListener(new AnimatedExpandableListView.OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                // We call collapseGroupWithAnimation(int) and
//                // expandGroupWithAnimation(int) to animate group
//                // expansion/collapse.
//                if (expandablelistview.isGroupExpanded(groupPosition)) {
//                    expandablelistview.collapseGroupWithAnimation(groupPosition);
//                } else {
//                    expandablelistview.expandGroupWithAnimation(groupPosition);
//                }
//                return true;
//            }
//
//        });
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(!state)

            getdatalist(gamecode, 31,1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    //订单列表
    private void getdatalist(String code,int index,final int pageindexd) {
        String Username =((BaseApplication) LotteryFragment3.this.getActivity().getApplication()).getBaseapplicationUsername();
        if (Username == null || Username.equalsIgnoreCase("")) {
            Intent intent = new Intent(LotteryFragment3.this.getActivity(), LoginActivity.class);
            LotteryFragment3.this.getActivity().startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        String   endtime=Httputils.getStringtime();
        params.put("userName",((BaseApplication) this.getActivity().getApplicationContext()).getBaseapplicationUsername());
        params.put("beginTimeStr", Httputils.getBeforeDate(index) );
        params.put("endTimeStr",endtime);
        params.put("gameCode", code);
        params.put("pageNo", pageindexd+"");
        Httputils.PostWithBaseUrl(Httputils.lotteryorder, params, new MyJsonHttpResponseHandler(LotteryFragment3.this.getActivity(),true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {

                super.onSuccessOfMe(jsonObject);

                JSONObject json = jsonObject.optJSONObject("datas");
                if(pageindexd==1){
                    gameorderBeanList.clear();
                }
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray jsonArray = json.optJSONArray("list");
                    if(jsonArray.length()>0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsond = jsonArray.getJSONObject(i);
                            LotteryorderBean gameBean1 = new LotteryorderBean();
                            gameBean1.setGameName(jsond.optString("gameName"));
                            gameBean1.setItemCode(jsond.optString("itemCode"));
                            gameBean1.setItemName(jsond.optString("itemName"));
                            gameBean1.setRate(jsond.optString("rate"));
                            gameBean1.setBetNumber(jsond.optString("betNumber"));
                            gameBean1.setBetMoney(jsond.optString("betMoney"));
                            gameBean1.setBetTime(jsond.optString("betTime"));
                            gameBean1.setKyje(jsond.optString("kyje"));
                            gameBean1.setQs(jsond.optString("qs"));
                            gameBean1.setOrderStatus(jsond.optString("orderStatus"));
                            gameBean1.setOpenCode(jsond.optString("openCode"));
                            gameBean1.setBackWaterMoney(jsond.optString("backWaterMoney"));
                            gameorderBeanList.add(gameBean1);
                        }
                    }
                    else{
                        ToastUtil.showMessage(context,"没有注单");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if(sportsAdapter==null){
                    sportsAdapter = new lotteryorderAdapter(getActivity(), gameorderBeanList);
                    expandablelistview.getRefreshableView().setAdapter(sportsAdapter);
                }
                else
                    sportsAdapter.NotifyAdapter(gameorderBeanList);


            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
                LogTools.e("dddddd111", s);
                return super.parseResponse(s);
            }
        });
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
        gamecode=((Lottery_MainActivity)getActivity()).getLotterycode();
//        getdatalist(gamecode, 31);
    }

}
