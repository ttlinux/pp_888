package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.SportsOrderResulrBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */
public class SportsOrderResultAdapter extends BaseAdapter {

    Context context;
    ArrayList<?> list;
    String[] titles;
    Html.ImageGetter imageGetter;
    int Realwidth;
    SparseArray<View> views=new SparseArray<>();
    HashMap<String,String> mapsdtype=new HashMap<>();
    HashMap<String,String> mapsballtype=new HashMap<>();

    public SportsOrderResultAdapter(Context context, ArrayList<?> list) {
        this.context = context;
        this.list = list;
        titles = context.getResources().getStringArray(R.array.sport_states);
        imageGetter = new Html.ImageGetter() {

            public Drawable getDrawable(String source) {
                Drawable able = new ColorDrawable(0xff000000);
                able.setBounds(0, 0, ScreenUtils.getScreenWH((Activity) SportsOrderResultAdapter.this.context)[0], 30);
                return able;
            }

            ;
        };
        Realwidth=ScreenUtils.getScreenWH((Activity)context)[0]-ScreenUtils.getDIP2PX(context,5)*2;

        mapsdtype.put("dy","独赢");
        mapsdtype.put("rq","让球");
        mapsdtype.put("dx","大小");
        mapsdtype.put("ds","单双");
        mapsdtype.put("dx_big","积分");
        mapsdtype.put("dx_small","积分");
        mapsdtype.put("rf","让分");
        mapsdtype.put("pd","波胆");
        // 啥都没有就写其他

        mapsballtype.put("ft","足球");
        mapsballtype.put("bk","篮球");
    }

    public void NotifySetChange(ArrayList<?> list,boolean clear) {
        this.list = list;
        if(clear)
        views.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SportsOrderResulrBean sportsOrderResulrBean=(SportsOrderResulrBean)list.get(position);
        if (views.get(position) == null) {
            if(sportsOrderResulrBean.getMatchRtype().equalsIgnoreCase("p3"))
            {
                convertView = View.inflate(context, R.layout.sportorder_item_chuanguan, null);
            }
            else
            {
                convertView = View.inflate(context, R.layout.sportorder_item_normal, null);
            }
            views.put(position, convertView);
        } else {
            convertView=views.get(position);
        }

        if(sportsOrderResulrBean.getMatchRtype().equalsIgnoreCase("p3"))//串关
        {
            ForMateLayout_chuanguan(convertView,sportsOrderResulrBean);
            SetText_chuanguan(convertView,sportsOrderResulrBean);
        }
        else//普通
        {
            FormatLayout(convertView);
            SetText_normal(convertView,sportsOrderResulrBean);
        }
        return convertView;
    }

    private void ForMateLayout_chuanguan(View convertView,SportsOrderResulrBean sportsOrderResulrBean)
    {
        List<SportsOrderResulrBean.DetailsBean> lists=   sportsOrderResulrBean.getDetails();
        if(lists!=null )
        {
            TextView orderid=(TextView)convertView.findViewById(R.id.orderid);
            orderid.setText("订单号:"+sportsOrderResulrBean.getBetWagersId());//"[串关] "+lists.size()+
            TextView balltype=(TextView)convertView.findViewById(R.id.balltype);

            balltype.setText(JudgeMentTag(sportsOrderResulrBean.getTimeType())+"-"+lists.size()+"串1");
        }

        FormatLayout(convertView);//格式化大小规格
        LinearLayout valuelayout=(LinearLayout)convertView.findViewById(R.id.value_layout);
        int state = Integer.valueOf(sportsOrderResulrBean.getBetStatus());
        String statestr="";
        if (state > 0 && state < 18) {
            statestr=titles[state - 1];
        }
        float betUsrWin=Float.valueOf(sportsOrderResulrBean.getBetUsrWin());
        int color=0xff000000;
        if(state==1 || state==2 || state==3 || state==4)
        {
            statestr=String.valueOf(betUsrWin);
            if(betUsrWin>=0)
                color=0xff018730;
            else
                color=0xffcf0000;
        }

//        String bettime="";
//        if(lists!=null && lists.size()>0 )
//        {
//            bettime=lists.get(0).getBetTime();
//        }
        String texts[]={sportsOrderResulrBean.getCreateTime(),sportsOrderResulrBean.getBetIn()+"",sportsOrderResulrBean.getBetCanWin()+"",statestr};//+" "+sportsOrderResulrBean.getBetUsrWin()
        int temp=0;

        for (int i = 0; i <valuelayout.getChildCount() ; i++) {
            if(valuelayout.getChildAt(i) instanceof TextView)
            {
                LogTools.e("ForMateLayout_chuanguan","ForMateLayout_chuanguan");
                TextView view =(TextView)valuelayout.getChildAt(i);
//                LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)view.getLayoutParams();
//                layoutParams.width=Realwidth/3;
//                view.setLayoutParams(layoutParams);
                view.setText(texts[temp++]);
                if(temp==4)
                {
                    view.setTextColor(color);
                }
//                view.getLayoutParams().width=Realwidth/3;
            }
        }
    }

    private TextView FormatLayout(View convertView)
    {
        TextView bettime=null;
        // 0.5 0.18 0.18 0.13
        float scale[]={0.35f, 0.22f, 0.22f, 0.21f};
        LinearLayout valuelayout=(LinearLayout)convertView.findViewById(R.id.value_layout);
        LinearLayout title_layout=(LinearLayout)convertView.findViewById(R.id.title_layout);

        int temp=0;
        for (int i = 0; i <valuelayout.getChildCount() ; i++) {
            if(valuelayout.getChildAt(i) instanceof TextView && scale.length>temp)
            {
//                LogTools.e("ForMateLayout_normal","ForMateLayout_normal");
                View view =valuelayout.getChildAt(i);
                LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)view.getLayoutParams();
                layoutParams.weight=scale[temp];
                layoutParams.width=(int)(Realwidth*scale[temp])-(temp==3?1:0);

                view.setLayoutParams(layoutParams);
                temp++;
            }
        }

        temp=0;
        for (int i = 0; i <title_layout.getChildCount() ; i++) {
            if(title_layout.getChildAt(i) instanceof TextView && scale.length>temp)
            {
                View view =title_layout.getChildAt(i);
                if(bettime==null)
                {
                    bettime=(TextView)view;
                }
                LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)view.getLayoutParams();
                layoutParams.weight=scale[temp];
                layoutParams.width=(int)(Realwidth*scale[temp])-(temp==3?1:0);
                layoutParams.gravity=Gravity.CENTER;
                view.setLayoutParams(layoutParams);
                ((TextView)view).setGravity(Gravity.CENTER);
                temp++;
            }
        }
        return bettime;
    }

    private void SetText_normal(View convertView,SportsOrderResulrBean sportsOrderResulrBean)
    {
        if(sportsOrderResulrBean.getDetails()==null || sportsOrderResulrBean.getDetails().size()==0)return;
        SportsOrderResulrBean.DetailsBean bean= sportsOrderResulrBean.getDetails().get(0);

        TextView orderid=(TextView)convertView.findViewById(R.id.orderid);
        orderid.setText("订单号:"+sportsOrderResulrBean.getBetWagersId());

        TextView bettype=(TextView)convertView.findViewById(R.id.bettype);
        String tag1="";
        switch (bean.getTimeType())
        {
            case "today":
                tag1="今日";
                break;
            case "roll":
                tag1="滚球";
                break;
            case "tom":
                tag1="早盘";
                break;
        }
        bettype.setText(tag1+"-"+bean.getRtypeName());//标题 订单号右边的


        LinearLayout value_layout=(LinearLayout)convertView.findViewById(R.id.value_layout);

        TextView time=(TextView)value_layout.getChildAt(1);
        time.setGravity(Gravity.CENTER);
        time.setText(bean.getBetTime());//下注时间
        TextView orderesult=(TextView)convertView.findViewById(R.id.orderesult);
        orderesult.setText("");
//        textview.append(Html.fromHtml(ToHtml(bean.getBetTime(), "#D6551E").toString()));
        orderesult.append(Html.fromHtml(ToHtml(bean.getLeague(), "#000000").toString()));
        if(bean.getTimeType().equalsIgnoreCase("roll"))
            orderesult.append(Html.fromHtml(ToHtmlNoBR(bean.getBetVs(), "#017ebe").toString()));
        else
            orderesult.append(Html.fromHtml(ToHtml(bean.getBetVs(), "#017ebe").toString()));
        if(tag1.equalsIgnoreCase("滚球"))
        {
            String score="(%s-%s)";
//            if (bean.getBetRqTeam().equalsIgnoreCase("C")) {
//            score= String.format(score,bean.getBetScoreCCur(),bean.getBetScoreHCur());
//        }else if (bean.getBetRqTeam().equalsIgnoreCase("H")) {
            score= String.format(score,bean.getBetScoreHCur(),bean.getBetScoreCCur());
//        }
            orderesult.append(Html.fromHtml(ToHtml(score, "#cf0000").toString()));
        }

        orderesult.append(Html.fromHtml(ToHtml(bean.getBetOddsDes(), "#cf0000").toString()));

        SpannableStringBuilder builder3 = new SpannableStringBuilder(bean.getBetOddsName() + " @ " + bean.getBetOdds()+"\n");

        ForegroundColorSpan redSpan1 = new ForegroundColorSpan(0xffcf0000);
        builder3.setSpan(redSpan1, 0, bean.getBetOddsName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan redSpan2 = new ForegroundColorSpan(0xffcf0000);
        builder3.setSpan(redSpan2, bean.getBetOddsName().length()+2, builder3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        orderesult.append(builder3);
//Html.fromHtml(ToHtml(bean.getBetOddsName() + " @ " + bean.getBetOdds(), "#000000").toString())
        String line4="";
        if(sportsOrderResulrBean.getStatus()!=2 && bean.getScoreBean()!=null)
        {
            if (bean.getTimeType().equalsIgnoreCase("roll")) {
                // 已经有了比赛结果
                // 这个判断的是下注的主队还是客队
//                if (bean.getBetRqTeam().equalsIgnoreCase("C")) {
//                    // 当前下注的是客队
//                    line4 = "全场(" + bean.getBetScoreCCur() + ":" + bean.getBetScoreHCur()+")";
//                } else if (bean.getBetRqTeam().equalsIgnoreCase("H")) {
//                    // 当前下注的是主队
                    line4 = "全场(" + bean.getBetScoreHCur() + ":" + bean.getBetScoreCCur()+")";
//                } else {
//                    // 还没有比赛结果
//                    // 不显示
//                }
            }
        }
        else if(sportsOrderResulrBean.getStatus()==2 && bean.getScoreBean()!=null)
        {
            // 判断是足球还是篮球
            if (bean.getMatchType().equalsIgnoreCase("ft")) {
//                if (bean.getBetRqTeam().equalsIgnoreCase("C") && bean.getBtype().equalsIgnoreCase("rq")) {
//                    line4 = "上半场(" + bean.getScoreBean().getHrScoreC() + ":" + bean.getScoreBean().getHrScoreH()+")";
//                    line4=line4+" 全场("+bean.getScoreBean().getFlScoreC() + ":" + bean.getScoreBean().getFlScoreH()+")";
//                } else {
                    line4 = "上半场(" + bean.getScoreBean().getHrScoreH() + ":" + bean.getScoreBean().getHrScoreC()+")";
                    line4=line4+" 全场("+bean.getScoreBean().getFlScoreH() + ":" + bean.getScoreBean().getFlScoreC()+")";
//                }
            }else if (bean.getMatchType().equalsIgnoreCase("bk")) {
//                if (bean.getBetRqTeam().equalsIgnoreCase("C") && bean.getBtype().equalsIgnoreCase("cf")) {
//                    line4 = "全场(" + bean.getScoreBean().getStageCF() + ":" + bean.getScoreBean().getStageHF()+")";
//                } else {
                    line4 = "全场(" + bean.getScoreBean().getStageHF() + ":" + bean.getScoreBean().getStageCF()+")";
//                }
            }
        }

        int state = Integer.valueOf(sportsOrderResulrBean.getBetStatus());
        String statestr="";
        if (state > 0 && state < 18) {
            statestr=titles[state - 1];
        }

        if(state==1 || state==2 || state==3|| state==4)
        {
//                textview.append(Html.fromHtml("<br>"));
            orderesult.append(Html.fromHtml(ToHtmlNoBR(line4,"#018730").toString()));
        }
        else
        {
            orderesult.append(Html.fromHtml(ToHtmlNoBR("比赛时间:"+bean.getMatchTime(),"#767676").toString()));
        }


//        textview.append(Html.fromHtml(ToHtml(sportsOrderResulrBean.getBetIn()+"", "#000000").toString()));

        TextView textview2=(TextView)value_layout.getChildAt(3);
        textview2.setText(sportsOrderResulrBean.getBetIn() + "");
        TextView textview3=(TextView)value_layout.getChildAt(5);
        textview3.setText(sportsOrderResulrBean.getBetCanWin()+"");
        TextView textview4=(TextView)value_layout.getChildAt(7);

        float betUsrWin=Float.valueOf(sportsOrderResulrBean.getBetUsrWin());
        int color=0xff000000;
        LogTools.e("state",state+" ---");
        if(state==1 || state==2 || state==3|| state==4)
        {
            LogTools.e("state222",state+" ---");
            statestr=String.valueOf(betUsrWin);
            if(betUsrWin>=0)
                color=0xff018730;
            else
                color=0xffcf0000;
        }
        textview4.setTextColor(color);
        textview4.setText(statestr);//+sportsOrderResulrBean.getBetUsrWin()
    }
    private void SetText_chuanguan(View convertView,SportsOrderResulrBean sportsOrderResulrBean)
    {
        LinearLayout content=(LinearLayout)convertView.findViewById(R.id.content);

        List<SportsOrderResulrBean.DetailsBean> lists=   sportsOrderResulrBean.getDetails();
        if(lists==null ||content.getChildCount()>0)return;

        for (int i = 0; i < lists.size(); i++) {
            SportsOrderResulrBean.DetailsBean bean=lists.get(i);
            TextView textview=new TextView(context);
            textview.setBackgroundColor(0xFFFFFFFF);
            LinearLayout.LayoutParams layoutparams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutparams.topMargin=1;
            textview.setLayoutParams(layoutparams);
            textview.setPadding(15,10,15,10);
            content.addView(textview);

            String balltype=mapsballtype.get(lists.get(i).getMatchType().toLowerCase());
            String dtype=mapsdtype.get(lists.get(i).getDtype().toLowerCase());
            if(balltype==null || balltype.equalsIgnoreCase(""))
                balltype="";
            if(dtype==null || dtype.equalsIgnoreCase(""))
                dtype="其他";
//            textview.append(Html.fromHtml(ToHtml(lists.get(i).getBetOddsName()+" @ "+lists.get(i).getBetOdds(),"#000000").toString()));
//            textview.append(Html.fromHtml(ToHtml(balltype+" "+dtype, "#000000").toString()));
            textview.append(Html.fromHtml(ToHtml(lists.get(i).getLeague(), "#000000").toString()));
            textview.append(Html.fromHtml(ToHtml(lists.get(i).getBetVs(), "#017ebe").toString()));
            textview.append(Html.fromHtml(ToHtml(lists.get(i).getBetOddsDes(), "#cf0000").toString()));

            SpannableStringBuilder builder3 = new SpannableStringBuilder(lists.get(i).getBetOddsName()+" @ "+lists.get(i).getBetOdds()+"\n");

            ForegroundColorSpan redSpan1 = new ForegroundColorSpan(0xffcf0000);
            builder3.setSpan(redSpan1, 0, bean.getBetOddsName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            ForegroundColorSpan redSpan2 = new ForegroundColorSpan(0xffcf0000);
            builder3.setSpan(redSpan2, bean.getBetOddsName().length() + 2, builder3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textview.append(builder3);

            String line4="";
            SportsOrderResulrBean.DetailsBean detailbean= sportsOrderResulrBean.getDetails().get(i);
            if(sportsOrderResulrBean.getStatus()!=2)
            {
                if(sportsOrderResulrBean.getTimeType().equalsIgnoreCase("roll"))
                {
//                    if(detailbean.getBetRqTeam().equalsIgnoreCase("C"))
//                        line4=detailbean.getBetScoreCCur()+":"+detailbean.getBetScoreHCur();
//                    else if(detailbean.getBetRqTeam().equalsIgnoreCase("H"))
                        line4=detailbean.getBetScoreHCur()+":"+detailbean.getBetScoreCCur();
                }
            }
            else
            {
                if(detailbean.getMatchType().equalsIgnoreCase("ft") && detailbean.getScoreBean()!=null)
                {
//                    if(detailbean.getBetRqTeam().equalsIgnoreCase("C"))
//                    {
//                        line4="上半场 "+detailbean.getScoreBean().getHrScoreC()+"-"+detailbean.getScoreBean().getHrScoreH();
//                        line4=line4+" 全场"+detailbean.getScoreBean().getFlScoreC()+"-"+detailbean.getScoreBean().getFlScoreH();
//                    }
//                    else if(detailbean.getBetRqTeam().equalsIgnoreCase("H"))
//                    {
                        line4="上半场 "+detailbean.getScoreBean().getHrScoreH()+"-"+detailbean.getScoreBean().getHrScoreC();
                        line4=line4+" 全场"+detailbean.getScoreBean().getFlScoreH()+"-"+detailbean.getScoreBean().getFlScoreC();
//                    }
                }
                else if(detailbean.getMatchType().equalsIgnoreCase("bk") && detailbean.getScoreBean()!=null)
                {
//                    if(detailbean.getBetRqTeam().equalsIgnoreCase("C"))
//                    {
//                        line4="全场 "+detailbean.getScoreBean().getStageCF()+"-"+detailbean.getScoreBean().getStageHF();
//                    }
//                    else if(detailbean.getBetRqTeam().equalsIgnoreCase("H"))
//                    {
                        line4="全场 "+detailbean.getScoreBean().getStageHF() +"-"+detailbean.getScoreBean().getStageCF();
//                    }
                }
            }
            int state = Integer.valueOf(sportsOrderResulrBean.getBetStatus());
            if(state==1 && state==2 && state==3 && state==4)
            {
                textview.append(Html.fromHtml(ToHtmlNoBR(line4, "#018730").toString()));
//                textview.append(Html.fromHtml("<br>"));
            }
            else
            {
                textview.append(Html.fromHtml(ToHtmlNoBR("比赛时间:" + lists.get(i).getMatchTime(),"#767676").toString()));
            }

        }

    }

    private StringBuilder ToHtml( String content, String color) {
        StringBuilder sb=new StringBuilder();
        sb.append("<font color=\'" + color + "\'>" + content);
        sb.append("</font><br>");
        return sb;
    }

    private StringBuilder ToHtmlNoBR(String content, String color) {
        StringBuilder sb=new StringBuilder();
        sb.append("<font color=\'" + color + "\'>" + content);
        sb.append("</font>");
        return sb;
    }


    private String JudgeMentTag(String tag) {
        if (tag.equalsIgnoreCase("today")) {
            return "今日";
        }
        if (tag.equalsIgnoreCase("roll")) {
            return "滚球";
        }
        if (tag.equalsIgnoreCase("tom")) {
            return "早盘";
        }
        return "";
    }

}
