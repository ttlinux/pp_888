package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Game_Adapter;
import com.a8android888.bocforandroid.Adapter.Lottery_room_Adapter;
import com.a8android888.bocforandroid.Adapter.SportsAdapter;
import com.a8android888.bocforandroid.Adapter.lotteryorderAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.Bean.LotteryorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.a8android888.bocforandroid.view.WheelDialog2;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kankan.wheel.widget.WheelView;

/**
 * Created by Administrator on 2017/4/5. 投注记录
 */
public class Lotteryorder extends BaseFragment implements View.OnClickListener {
    private PopupWindow popupWindow = null;
    //    AnimatedExpandableListView expandablelistview;
    lotteryorderAdapter sportsAdapter;
    PullToRefreshExpandableListView expandablelistview;
    TextView lotterytv;
    RadioGroup titles_gp;
    String lotterytitle = "";
    ArrayList<LotteryBean> gameBeanList = new ArrayList<LotteryBean>();
    String gameballtype[];
    ArrayList<LotteryorderBean> gameorderBeanList = new ArrayList<LotteryorderBean>();
    int timeidex = 1;
    int pageindex = 1;
    int lotterytype=-1;
    RadioButton Recordbtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.lottery_order, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lotterytv = (TextView) FindView(R.id.lotterytv);
        lotterytv.setOnClickListener(this);

        titles_gp = FindView(R.id.titles_gp);
        titles_gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        timeidex = 1;
                        break;
                    case R.id.radioButton2:
                        timeidex = 7;
                        break;
                    case R.id.radioButton3:
                        timeidex = 30;
                        break;
                    case R.id.radioButton4:
                        timeidex = 60;
                        break;
                }
                LogTools.e("timeidex", timeidex + "");
                pageindex = 1;
                getdatalist(lotterytitle, timeidex, pageindex);
                if (Recordbtn != null) {
                    Recordbtn.setTextColor(Color.BLACK);
                }
                RadioButton radioButton = (RadioButton) titles_gp.findViewById(checkedId);
                radioButton.setTextColor(Color.WHITE);
                Recordbtn = radioButton;
            }
        });
        String titles[] = getResources().getStringArray(R.array.pay_record_titles);
        for (int i = 0; i < titles_gp.getChildCount(); i++) {

            RadioButton radioButton = (RadioButton) titles_gp.getChildAt(i);
            radioButton.setText(titles[i]);
            radioButton.setButtonDrawable(new ColorDrawable(0));
        }


        expandablelistview = FindView(R.id.expandablelistview);
        expandablelistview.getRefreshableView().setGroupIndicator(null);
        expandablelistview.getRefreshableView().setDivider(null);
        expandablelistview.getRefreshableView().setSelector(android.R.color.transparent);
//        expandablelistview.getRefreshableView().setOnGroupClickListener(LotteryFragment3.this);          //可以设置组点击监听

        expandablelistview.setPullToRefreshOverScrollEnabled(true);          //可刷新
        expandablelistview.setMode(PullToRefreshBase.Mode.BOTH);     //设置模式，此模式是可以上拉，
//        expandablelistview.setGroupIndicator(null);
        getdata();

        //上下拉监听，注意实现这个OnRefreshListener2类的对象
        expandablelistview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                refreshView.onRefreshComplete();
                LogTools.e("ffafd", "下拉刷新");
                pageindex = 1;
                getdatalist(lotterytitle, timeidex, pageindex);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                LogTools.e("ffafda", "上拉加载更多");
                refreshView.onRefreshComplete();
                pageindex++;
                getdatalist(lotterytitle, timeidex, pageindex);
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
        Recordbtn = ((RadioButton) titles_gp.getChildAt(0));
        Recordbtn.setChecked(true);
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lotterytv:
//                showMore(lotterytv, gameStringList);
                WheelDialog2 dialog = new WheelDialog2(getActivity());
                dialog.setListener(new WheelDialog2.OnChangeListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        LogTools.e("oldValue", oldValue + " " + newValue);
                        lotterytype = newValue;
                        lotterytv.setText(gameballtype[lotterytype]);
                        lotterytitle=gameBeanList.get(lotterytype).getGameCode();
                        pageindex = 1;
                        getdatalist(lotterytitle, timeidex, pageindex);
                    }
                });
                if (lotterytype < 0) {
                    lotterytype = 0;
                }
                if (gameballtype != null && gameballtype.length > 0) {
                    dialog.setStrs(gameballtype);
                    dialog.setTitle("请滑动选择彩种");
                    dialog.show();
                }
                break;
            default:
        }
    }

    private void getdata() {
        String Username = ((BaseApplication) Lotteryorder.this.getActivity().getApplication()).getBaseapplicationUsername();
        if (Username == null || Username.equalsIgnoreCase("")) {
            Intent intent = new Intent(Lotteryorder.this.getActivity(), LoginActivity.class);
            Lotteryorder.this.getActivity().startActivity(intent);
            return;
        }
        Httputils.PostWithBaseUrl(Httputils.lotterylist, null, new MyJsonHttpResponseHandler(this.getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("jsonjson", jsonObject.toString());
                JSONArray json = jsonObject.optJSONArray("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000") || json==null)
                    return;
                try {
                    gameballtype=new String[json.length()+1];
                    for (int i = 0; i < json.length()+1; i++) {
                        LotteryBean gameBean1 = new LotteryBean();
                        if (i == 0) {
                            gameBean1.setGameCode("");
                            gameBean1.setDefaultItemId("");
                            gameBean1.setGameId("");
                            gameBean1.setGameName("所有彩种");
                        } else {
                            JSONObject jsond = json.getJSONObject(i-1);
                            LogTools.e("json", jsond + "");
                            gameBean1.setGameCode(jsond.optString("gameCode"));
                            gameBean1.setDefaultItemId(jsond.optString("defaultItemId"));
                            gameBean1.setGameId(jsond.optString("gameId"));
                            gameBean1.setGameName(jsond.optString("gameName"));
                        }
                        gameBeanList.add(gameBean1);
                        gameballtype[i]=gameBean1.getGameName();
                    }
                    if (gameballtype!=null && gameballtype.length>0) {
//                        getdatalist(gameBeanList.get(0).getGameCode(), timeidex, pageindex);
                        lotterytv.setText(gameBeanList.get(0).getGameName());
                        lotterytitle = gameBeanList.get(0).getGameCode();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
                return super.parseResponse(s);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 显示更多弹出框
     *
     * @param parent
     */
    private void showMore(final TextView parent, ArrayList<String> data) {
//        final LinearLayout linearLayout;
//
//        if (popupWindow == null) {
//            View popupView = View.inflate(this.getActivity(), R.layout.popupwindowlayout, null);
////                    popupView.setFocusableInTouchMode(true);
//
//            popupView.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (keyCode == KeyEvent.KEYCODE_BACK) {
//                        popupWindow.dismiss();
//                        popupWindow = null;
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            if (popupWindow == null)
//                popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.FILL_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, false);
//            linearLayout = (LinearLayout) popupView.findViewById(R.id.layout);
//            linearLayout.setGravity(Gravity.CENTER);
////            ScrollView relation=(ScrollView)popupView.findViewById(R.id.scrollview);
//            linearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    popupWindow.dismiss();
//                    popupWindow = null;
//
//                }
//            });
//            for (int i = 0; i < data.size(); i++) {
//                ImageView imageView = new ImageView(this.getActivity());
//                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(this.getActivity(), 200), 1, Gravity.CENTER);
//                params12.gravity = Gravity.CENTER;
//                imageView.setLayoutParams(params12);
//                imageView.setBackgroundColor(this.getResources().getColor(R.color.white));
//                linearLayout.addView(imageView);
//                final TextView textView = new TextView(this.getActivity());
//                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(this.getActivity(), 200), ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
//                params1.gravity = Gravity.CENTER;
//                textView.setLayoutParams(params1);
//                textView.setBackgroundColor(this.getResources().getColor(R.color.topbagcolor));
//                textView.setTag(i);
//                textView.setText(gameBeanList.get(i).getGameName() + "");
//                textView.setGravity(Gravity.CENTER);
//                textView.setTextSize(15);
//                textView.setPadding(ScreenUtils.getDIP2PX(this.getActivity(), 40), ScreenUtils.getDIP2PX(this.getActivity(), 10), ScreenUtils.getDIP2PX(this.getActivity(), 40), ScreenUtils.getDIP2PX(this.getActivity(), 10));
//                textView.setTextColor(this.getResources().getColor(R.color.white));
//                textView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final int tag = Integer.valueOf(v.getTag() + "");
//                        parent.setText(textView.getText().toString().trim());
//                        popupWindow.dismiss();
//                        popupWindow = null;
//
//                        lotterytitle = gameStringList.get(tag);
//                        if (gameorderBeanList != null) {
//                            gameorderBeanList.clear();
//                        }
//                        getdatalist(lotterytitle, timeidex, pageindex);
//                    }
//                });
//                linearLayout.addView(textView);
//
//
//            }
//
//            popupWindow.setFocusable(true);
//            popupWindow.setBackgroundDrawable(new BitmapDrawable());
//            popupWindow.setOutsideTouchable(true);
//            popupWindow.showAsDropDown(parent);
//            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    popupWindow.dismiss();
//                    popupWindow = null;
//                }
//            });
//        }
    }

    //订单列表
    private void getdatalist(String code, int index, final int pageindexd) {
        String Username = ((BaseApplication) Lotteryorder.this.getActivity().getApplication()).getBaseapplicationUsername();
        if (Username == null || Username.equalsIgnoreCase("")) {
            Intent intent = new Intent(Lotteryorder.this.getActivity(), LoginActivity.class);
            Lotteryorder.this.getActivity().startActivity(intent);
            return;
        }
        RequestParams params = new RequestParams();
        String endtime = Httputils.getStringtime();
        params.put("userName", ((BaseApplication) this.getActivity().getApplicationContext()).getBaseapplicationUsername());
        params.put("beginTimeStr", Httputils.getBeforeDate(index));
        params.put("endTimeStr", endtime);
        params.put("gameCode", code);
        params.put("pageNo", pageindexd + "");
        Httputils.PostWithBaseUrl(Httputils.lotteryorder, params, new MyJsonHttpResponseHandler(this.getActivity(), true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("返回", jsonObject.toString());
                JSONObject json = jsonObject.optJSONObject("datas");
                if (pageindexd == 1) {
                    gameorderBeanList.clear();
                }
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray jsonArray = json.optJSONArray("list");
                    if (jsonArray.length() > 0) {
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
                            gameBean1.setBetUsrWin(jsond.optString("betUsrWin"));
                            gameorderBeanList.add(gameBean1);
                        }
                    } else {
                        gameorderBeanList.clear();
                        Setadapter();
                        ToastUtil.showMessage(context, "没有投注记录");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Setadapter();

            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
                LogTools.e("dddddd111", s);
                return super.parseResponse(s);
            }
        });
    }

    private void Setadapter()
    {
        if (sportsAdapter == null) {
            sportsAdapter = new lotteryorderAdapter(getActivity(), gameorderBeanList);
            expandablelistview.getRefreshableView().setAdapter(sportsAdapter);
        } else {
            sportsAdapter.NotifyAdapter(gameorderBeanList);
        }
    }
}
