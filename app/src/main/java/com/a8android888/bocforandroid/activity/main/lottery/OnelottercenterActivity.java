package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.one_Lottery_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */
public class OnelottercenterActivity extends BaseActivity implements View.OnClickListener{
    ImageView back;
    TextView title,nameqs;
    ListView listView;
    Intent intent;
    String id,gamecode;
    int pageindex=1;
    LinearLayout code;
    one_Lottery_Adapter adapter;
    ArrayList<LotteryBean> gameBeanList=new ArrayList<LotteryBean>();
    PullToRefreshListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onelottercenter);
        initview();
    }

    private void initview() {
        intent=getIntent();
        id=intent.getStringExtra("id");
        gamecode=intent.getStringExtra("gamecode");
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        title=(TextView)FindView(R.id.title);
        title.setText(intent.getStringExtra("title"));
        nameqs=(TextView)FindView(R.id.nameqs);
        code=(LinearLayout)FindView(R.id.code);
        String data[]=Httputils.convertStrToArray(intent.getStringExtra("code"));
//        nameqs.setText(intent.getStringExtra("title")+"   第"+intent.getStringExtra("qs")+"期   "+"开奖结果");
//        //6合彩
//        if(id.equalsIgnoreCase("1")) {
//            for (int i = 0; i < data.length; i++) {
//                TextView imageView = new TextView(this);
//                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(80, 80, Gravity.CENTER);
//                params12.setMargins(10, 10, 10, 10);
//                params12.gravity = Gravity.CENTER;
//                imageView.setLayoutParams(params12);
//                imageView.setGravity(Gravity.CENTER);
//                imageView.setPadding(5, 5, 5, 5);
//                if (Httputils.useLoop(Httputils.lotterry6red, Integer.valueOf(data[i]))) {
//                    imageView.setBackgroundResource((R.drawable.lottery_red));
//                }
//                if (Httputils.useLoop(Httputils.lotterry6green, Integer.valueOf(data[i]))) {
//                    imageView.setBackgroundResource((R.drawable.lottery_green));
//                }
//                if (Httputils.useLoop(Httputils.lotterry6blue, Integer.valueOf(data[i]))) {
//                    imageView.setBackground(this.getResources().getDrawable(R.drawable.lottery_blue));
//                }
//                if (i == data.length-1) {
//
//                    TextView imageView2 = new TextView(this);
//                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
//                    params1.gravity = Gravity.CENTER;
//                    imageView2.setLayoutParams(params1);
//                    imageView2.setGravity(Gravity.CENTER);
//                    imageView2.setPadding(0, 5, 0, 5);
//                    imageView2.setText("+");
//                    imageView2.setTextColor(this.getResources().getColor(R.color.black));
//                    code.addView(imageView2);
//                }
//                imageView.setText(data[i]);
//                imageView.setTextColor(this.getResources().getColor(R.color.white));
//                code.addView(imageView);
//            }
//        }
//        //福彩3D
//            if(id.equalsIgnoreCase("2")||id.equalsIgnoreCase("3")||id.equalsIgnoreCase("4")||id.equalsIgnoreCase("6")
//                    ||id.equalsIgnoreCase("7")||id.equalsIgnoreCase("8")||id.equalsIgnoreCase("9")
//                    ){
//                for(int i=0;i<data.length;i++) {
//                    TextView imageView = new TextView(this);
//                    LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(80,80, Gravity.CENTER);
//                    params12.setMargins(10, 10, 10, 10);
//                    params12.gravity = Gravity.CENTER;
//                    imageView.setLayoutParams(params12);
//                    imageView.setGravity(Gravity.CENTER);
//                    imageView.setPadding(5, 5, 5, 5);
//                    imageView.setBackgroundResource((R.drawable.lottery_red));
//                    imageView.setText(data[i]);
//                    imageView.setTextColor(this.getResources().getColor(R.color.white));
//                    code.addView(imageView);
//
//                }
//        }
//        //PK10
//        if(id.equalsIgnoreCase("10")){
//            for(int i=0;i<data.length;i++) {
//                TextView imageView = new TextView(this);
//                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(80,80, Gravity.CENTER);
//                params12.setMargins(10, 10, 10, 10);
//                params12.gravity = Gravity.CENTER;
//                imageView.setLayoutParams(params12);
//                imageView.setGravity(Gravity.CENTER);
//                imageView.setPadding(5, 5, 5, 5);
//                if (Integer.valueOf(data[i])==1) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk1));
//                }
//                if (Integer.valueOf(data[i])==2) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk2));
//                }
//                if (Integer.valueOf(data[i])==3) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk3));
//                }
//                if (Integer.valueOf(data[i])==4) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk4));
//                }
//                if (Integer.valueOf(data[i])==5) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk5));
//                }
//                if (Integer.valueOf(data[i])==6) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk6));
//                }
//                if (Integer.valueOf(data[i])==7) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk7));
//                }
//                if (Integer.valueOf(data[i])==8) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk8));
//                }
//                if (Integer.valueOf(data[i])==9) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk9));
//                }
//                if (Integer.valueOf(data[i])==10) {
//                    imageView.setBackgroundResource((R.drawable.lottery_pk10));
//                }
//                imageView.setText(data[i]);
//                imageView.setTextColor(this.getResources().getColor(R.color.white));
//                code.addView(imageView);
//
//            }
//        }
//        //28
//        if(id.equalsIgnoreCase("11")||id.equalsIgnoreCase("12")) {
//            for (int i = 0; i < data.length; i++) {
//                TextView imageView = new TextView(this);
//                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(80, 80, Gravity.CENTER);
//                params12.setMargins(10, 10, 10, 10);
//                params12.gravity = Gravity.CENTER;
//                imageView.setLayoutParams(params12);
//                imageView.setGravity(Gravity.CENTER);
//                imageView.setPadding(5, 5, 5, 5);
//                if (Httputils.useLoop(Httputils.lotterry28red, Integer.valueOf(data[i]))) {
//                    imageView.setBackgroundResource((R.drawable.lottery_red));
//                }
//                if (Httputils.useLoop(Httputils.lotterry28green, Integer.valueOf(data[i]))) {
//                    imageView.setBackgroundResource((R.drawable.lottery_green));
//                }
//                if (Httputils.useLoop(Httputils.lotterry28blue, Integer.valueOf(data[i]))) {
//                    imageView.setBackground(this.getResources().getDrawable(R.drawable.lottery_blue));
//                }
//                if (Httputils.useLoop(Httputils.lotterry28gary, Integer.valueOf(data[i]))) {
//                    imageView.setBackground(this.getResources().getDrawable(R.drawable.lottery_gary));
//                }
//                 if(i!=0) {
//                     TextView imageView2 = new TextView(this);
//                     LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
//                     params1.gravity = Gravity.CENTER;
//                     imageView2.setLayoutParams(params1);
//                     imageView2.setGravity(Gravity.CENTER);
//                     imageView2.setPadding(0, 5, 0, 5);
//                     if (i == data.length - 1) {
//                         imageView2.setText("=");
//                     } else {
//                         imageView2.setText("+");
//                     }
//                     imageView2.setTextColor(this.getResources().getColor(R.color.black));
//                     code.addView(imageView2);
//                 }
//                imageView.setText(data[i]);
//                imageView.setTextColor(this.getResources().getColor(R.color.white));
//                code.addView(imageView);
//            }
//        }
        listView=(ListView)FindView(R.id.listview);
        listView.setVisibility(View.GONE);
        getdata(1);

        list=(PullToRefreshListView)FindView(R.id.listview2);
        list.setVisibility(View.VISIBLE);
        list.setMode(PullToRefreshBase.Mode.BOTH);
        list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.onRefreshComplete();
                    }
                }, 100);
                pageindex =1;
                getdata(pageindex);
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.onRefreshComplete();
                    }
                }, 100);
                pageindex++;
                getdata(pageindex);
            }
        });
    }
    private void setcodetop(String codedata){

        String data[]= Httputils.convertStrToArray(codedata);
        //6合彩
        if(gamecode.equalsIgnoreCase("HK6")) {
            code.removeAllViews();
            for (int i = 0; i < data.length; i++) {
                LogTools.e("开奖",data[i]+"");
                TextView imageView = new TextView(this);
                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(80, 80, Gravity.CENTER);
                params12.setMargins(10, 10, 10, 10);
                params12.gravity = Gravity.CENTER;
                imageView.setLayoutParams(params12);
                imageView.setGravity(Gravity.CENTER);
                imageView.setPadding(5, 5, 5, 5);
                if (Httputils.useLoop(Httputils.lotterry6red, Integer.valueOf(data[i]))) {
                    imageView.setBackgroundResource((R.drawable.lottery_red));
                }
                if (Httputils.useLoop(Httputils.lotterry6green, Integer.valueOf(data[i]))) {
                    imageView.setBackgroundResource((R.drawable.lottery_green));
                }
                if (Httputils.useLoop(Httputils.lotterry6blue, Integer.valueOf(data[i]))) {
                    imageView.setBackground(this.getResources().getDrawable(R.drawable.lottery_blue));
                }
                if (i == data.length-1) {

                    TextView imageView2 = new TextView(this);
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                    params1.gravity = Gravity.CENTER;
                    imageView2.setLayoutParams(params1);
                    imageView2.setGravity(Gravity.CENTER);
                    imageView2.setPadding(0, 5, 0, 5);
                    imageView2.setText("+");
                    imageView2.setTextColor(this.getResources().getColor(R.color.black));
                    code.addView(imageView2);
                }
                imageView.setText(data[i]);
                imageView.setTextColor(this.getResources().getColor(R.color.white));
                code.addView(imageView);
            }

        }
        //福彩3D
        if(gamecode.equalsIgnoreCase("FC3D")||gamecode.equalsIgnoreCase("PL3")||gamecode.equalsIgnoreCase("CQSSC")||gamecode.equalsIgnoreCase("TJSSC")
                ||gamecode.equalsIgnoreCase("XJSSC")||gamecode.equalsIgnoreCase("GDKLSF")||gamecode.equalsIgnoreCase("TJKLSF")){
            code.removeAllViews();
            for(int i=0;i<data.length;i++) {
                TextView imageView = new TextView(this);
                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(80,80, Gravity.CENTER);
                params12.setMargins(10, 10, 10, 10);
                params12.gravity = Gravity.CENTER;
                imageView.setLayoutParams(params12);
                imageView.setGravity(Gravity.CENTER);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setBackgroundResource((R.drawable.lottery_red));
                imageView.setText(data[i]);
                imageView.setTextColor(this.getResources().getColor(R.color.white));
                code.addView(imageView);

            }
        }
        //PK10
        if(gamecode.equalsIgnoreCase("BJPK10")){
            code.removeAllViews();
            for(int i=0;i<data.length;i++) {
                TextView imageView = new TextView(this);
                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(80,80, Gravity.CENTER);
                params12.setMargins(10, 10, 10, 10);
                params12.gravity = Gravity.CENTER;
                imageView.setLayoutParams(params12);
                imageView.setGravity(Gravity.CENTER);
                imageView.setPadding(5, 5, 5, 5);
                if (Integer.valueOf(data[i])==1) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk1));
                }
                if (Integer.valueOf(data[i])==2) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk2));
                }
                if (Integer.valueOf(data[i])==3) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk3));
                }
                if (Integer.valueOf(data[i])==4) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk4));
                }
                if (Integer.valueOf(data[i])==5) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk5));
                }
                if (Integer.valueOf(data[i])==6) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk6));
                }
                if (Integer.valueOf(data[i])==7) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk7));
                }
                if (Integer.valueOf(data[i])==8) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk8));
                }
                if (Integer.valueOf(data[i])==9) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk9));
                }
                if (Integer.valueOf(data[i])==10) {
                    imageView.setBackgroundResource((R.drawable.lottery_pk10));
                }
                imageView.setText(data[i]);
                imageView.setTextColor(this.getResources().getColor(R.color.white));
                code.addView(imageView);

            }
        }
        //28
        if(gamecode.equalsIgnoreCase("BJKL8")||gamecode.equalsIgnoreCase("CAKENO")) {
            code.removeAllViews();
            for (int i = 0; i < data.length; i++) {
                TextView imageView = new TextView(this);
                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(80, 80, Gravity.CENTER);
                params12.setMargins(10, 10, 10, 10);
                params12.gravity = Gravity.CENTER;
                imageView.setLayoutParams(params12);
                imageView.setGravity(Gravity.CENTER);
                imageView.setPadding(5, 5, 5, 5);
                if (Httputils.useLoop(Httputils.lotterry28red, Integer.valueOf(data[i]))) {
                    imageView.setBackgroundResource((R.drawable.lottery_red));
                }
                if (Httputils.useLoop(Httputils.lotterry28green, Integer.valueOf(data[i]))) {
                    imageView.setBackgroundResource((R.drawable.lottery_green));
                }
                if (Httputils.useLoop(Httputils.lotterry28blue, Integer.valueOf(data[i]))) {
                    imageView.setBackground(this.getResources().getDrawable(R.drawable.lottery_blue));
                }
                if (Httputils.useLoop(Httputils.lotterry28gary, Integer.valueOf(data[i]))) {
                    imageView.setBackground(this.getResources().getDrawable(R.drawable.lottery_gary));
                }
                if(i!=0) {
                    TextView imageView2 = new TextView(this);
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                    params1.gravity = Gravity.CENTER;
                    imageView2.setLayoutParams(params1);
                    imageView2.setGravity(Gravity.CENTER);
                    imageView2.setPadding(0, 5, 0, 5);
                    if (i == data.length - 1) {
                        imageView2.setText("=");
                    } else {
                        imageView2.setText("+");
                    }
                    imageView2.setTextColor(this.getResources().getColor(R.color.black));
                    code.addView(imageView2);
                }
                imageView.setText(data[i]);
                imageView.setTextColor(this.getResources().getColor(R.color.white));
                code.addView(imageView);
            }
        }


    }
    private void getdata(final int index) {
        RequestParams params = new RequestParams();
        params.put("gameCode", gamecode);
        params.put("pageNo", index + "");
        Httputils.PostWithBaseUrl(Httputils.lotteryonecenter, params, new MyJsonHttpResponseHandler(this,true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("json", jsonObject + "");
                JSONObject jsonObjectd = jsonObject.optJSONObject("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    if(index==1){
                        gameBeanList.clear();
                    }
                    JSONArray json = jsonObjectd.optJSONArray("list");
                    for (int i = 0; i < json.length(); i++) {
//                        if (i == 0) {
                        JSONObject jsond = json.getJSONObject(i);
//                        LotteryBean gameBean1 = new LotteryBean();
//                        gameBean1.setOpenTime(jsond.optString("openTime"));
//                        gameBean1.setGameName(jsond.optString("gameName"));
//                        gameBean1.setQs(jsond.optString("qs"));
//                        gameBean1.setOpenCode(jsond.optString("openCode"));
//                        gameBean1.setOpenTime(jsond.optString("openTime"));
//                        gameBeanList.add(gameBean1);
//                    }
                        if(index==1) {
                            if (i != 0) {
                                LotteryBean gameBean1 = new LotteryBean();
                                gameBean1.setOpenTime(jsond.optString("openTime"));
                                gameBean1.setGameName(jsond.optString("gameName"));
                                gameBean1.setQs(jsond.optString("qs"));
                                gameBean1.setOpenCode(jsond.optString("openCode"));
                                gameBean1.setOpenTime(jsond.optString("openTime"));
                                gameBeanList.add(gameBean1);

                            }
                            if (i == 0) {
                                setcodetop(jsond.optString("openCode"));
                                nameqs.setText(jsond.optString("gameName") + " " + "第" + jsond.optString("qs") + "期   开奖结果");
                            }
                        }
                        else {
                            LotteryBean gameBean1 = new LotteryBean();
                            gameBean1.setOpenTime(jsond.optString("openTime"));
                            gameBean1.setGameName(jsond.optString("gameName"));
                            gameBean1.setQs(jsond.optString("qs"));
                            gameBean1.setOpenCode(jsond.optString("openCode"));
                            gameBean1.setOpenTime(jsond.optString("openTime"));
                            gameBeanList.add(gameBean1);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(adapter==null){
                    adapter = new one_Lottery_Adapter(OnelottercenterActivity.this, gameBeanList);
                    list.setAdapter(adapter);
                }
                else
                    adapter.NotifyAdapter(gameBeanList);
            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
                LogTools.e("dddddd111", s);
                return super.parseResponse(s);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }
    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.back:

        finish();
        break;
}
    }
}
