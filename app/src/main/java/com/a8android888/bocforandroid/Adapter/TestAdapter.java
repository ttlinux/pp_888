package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.BKBean_Guoguan;
import com.a8android888.bocforandroid.Bean.BKBean_Normal;
import com.a8android888.bocforandroid.Bean.FootBallHq;
import com.a8android888.bocforandroid.Bean.FootBallRuQiu;
import com.a8android888.bocforandroid.Bean.FootBallbodan;
import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Guoguan;
import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Normal;
import com.a8android888.bocforandroid.Bean.SportsOrderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.Sports.SportOrderActivity;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.thirdparty.advrecyclerview.adapter.ItemViewTypeComposer;
import com.a8android888.bocforandroid.thirdparty.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.a8android888.bocforandroid.thirdparty.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */
public class TestAdapter extends AbstractExpandableItemAdapter<TestAdapter.MyGroupViewHolder, TestAdapter.MyChildViewHolder> {

    private Context context;
    private ArrayList<?> sportdatas;
    int type;
    String[] hbodan;
    String[] bodan;
    String[] hqtitles;
    String[] ruqiu;
    String[] rtype;
    String[] hqtitles_tag;
    String[] ruqiu_tag;
    Drawable shadow;
    int screenWidth;

    public TestAdapter(Context context, ArrayList<?> sportdatas, int type) {
        setHasStableIds(true);
        this.context = context;
        this.sportdatas = sportdatas;
        this.type = type;
        hbodan = context.getResources().getStringArray(R.array.hbodan);
        bodan = context.getResources().getStringArray(R.array.bodan);
        shadow = context.getResources().getDrawable(R.drawable.shadow_item);
        hqtitles = context.getResources().getStringArray(R.array.hqtitles);
        rtype = context.getResources().getStringArray(R.array.rtype);
        ruqiu = context.getResources().getStringArray(R.array.ruqiu);
        hqtitles_tag = context.getResources().getStringArray(R.array.hqtitles_tag);
        ruqiu_tag = context.getResources().getStringArray(R.array.ruqiu_tag);
        screenWidth = ScreenUtils.getScreenWH((Activity) context)[0];
    }

    private String getTimetype() {
        int index = ((Sports_MainActivity) context).GetCurrentPage();
        switch (index) {
            case 0:
                return "roll";
            case 1:
                return "today";
            case 2:
                return "tom";
        }
        return "";
    }
    private String getRtype() {
        return rtype[type];
    }

    public void NotifyAdapter(ArrayList<?> sportdatas, int type) {
        if (sportdatas == null)
            this.sportdatas.clear();
        else
        {
            this.sportdatas =(ArrayList)sportdatas.clone();
        }

//        if (this.type != type)
//            sparsearray.clear();
        this.type = type;

        LogTools.e("NotifyAdapter", sportdatas.size() + "");
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return sportdatas.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        if (type == BaseFragment.BALL_TYPE.FootBall_GuoGuan.value()
                || type == BaseFragment.BALL_TYPE.FootBall_Normal.value()
                || type == BaseFragment.BALL_TYPE.FootBall_BoDan.value()
                || type == BaseFragment.BALL_TYPE.FootBall_HBoDan.value()
                || type == BaseFragment.BALL_TYPE.FootBall_ZongRuQiu.value()
                || type == BaseFragment.BALL_TYPE.FootBall_BanQuan.value()
                || type == BaseFragment.BALL_TYPE.FootBall_Re.value()
                ) {
            if (sportdatas.get(groupPosition) instanceof SportsFootBallBean_Normal) {
                SportsFootBallBean_Normal bean = (SportsFootBallBean_Normal) sportdatas.get(groupPosition);
                return bean.getSportsFootBallBeanNormals().size();
            } else if (sportdatas.get(groupPosition) instanceof SportsFootBallBean_Guoguan) {
                SportsFootBallBean_Guoguan bean = (SportsFootBallBean_Guoguan) sportdatas.get(groupPosition);
                return bean.getFootBallBean_guoguan_items().size();
            } else if (sportdatas.get(groupPosition) instanceof FootBallHq) {
                FootBallHq bean = (FootBallHq) sportdatas.get(groupPosition);
                return bean.getFootBallHq_items().size();
            } else if (sportdatas.get(groupPosition) instanceof FootBallRuQiu) {
                FootBallRuQiu bean = (FootBallRuQiu) sportdatas.get(groupPosition);
                return bean.getFootBallRuQiu_items().size();
            } else if (sportdatas.get(groupPosition) instanceof FootBallbodan) {
                FootBallbodan bean = (FootBallbodan) sportdatas.get(groupPosition);
                return bean.getFootBallbodan_items().size();
            }

        }
        if (type == BaseFragment.BALL_TYPE.BasketBall_Normal.value()
                || type == BaseFragment.BALL_TYPE.BasketBall_GuoGuan.value()
                || type == BaseFragment.BALL_TYPE.BasketBall_Re_Main.value()
                ) {
            if (sportdatas.get(groupPosition) instanceof BKBean_Guoguan) {
                BKBean_Guoguan bean = (BKBean_Guoguan) sportdatas.get(groupPosition);
                return bean.getbKitemBeans().size();
            }
            if (sportdatas.get(groupPosition) instanceof BKBean_Normal) {
                BKBean_Normal bean = (BKBean_Normal) sportdatas.get(groupPosition);
                return bean.getBkBean_normal_items().size();
            }
        }
        return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (groupPosition+1)*childPosition;
    }

    @Override
    public MyGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sports_maintitle, parent, false);
        return new MyGroupViewHolder(v);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v=null;
            if (type == BaseFragment.BALL_TYPE.FootBall_GuoGuan.value()
                    || type == BaseFragment.BALL_TYPE.FootBall_Normal.value()
                    || type == BaseFragment.BALL_TYPE.FootBall_Re.value()
                    ) {
                LogTools.e("layout", "1111111111111");
                 v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_football, parent, false);
            } else if (type == BaseFragment.BALL_TYPE.BasketBall_Normal.value()
                    || type == BaseFragment.BALL_TYPE.BasketBall_GuoGuan.value()
                    || type == BaseFragment.BALL_TYPE.BasketBall_Re_Main.value()
                    ) {
                LogTools.e("layout", "222222222222222");
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basketball, parent, false);
            } else if (type == BaseFragment.BALL_TYPE.FootBall_HBoDan.value() || type == BaseFragment.BALL_TYPE.FootBall_BoDan.value()) {
                LogTools.e("layout", "333333333333333");
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_football_bodan, parent, false);
            } else if (type == BaseFragment.BALL_TYPE.FootBall_BanQuan.value() || type == BaseFragment.BALL_TYPE.FootBall_ZongRuQiu.value()) {
                LogTools.e("layout", "44444444444444444");
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_football_ruqiu, parent, false);
            }

        return new MyChildViewHolder(v);
    }

    @Override
    public void onBindGroupViewHolder(MyGroupViewHolder holder, int groupPosition, @IntRange(from = ItemViewTypeComposer.MIN_WRAPPED_VIEW_TYPE, to = ItemViewTypeComposer.MAX_WRAPPED_VIEW_TYPE) int viewType) {

        holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_right_selected));
       if (type == BaseFragment.BALL_TYPE.FootBall_GuoGuan.value()
                || type == BaseFragment.BALL_TYPE.FootBall_Normal.value()
                || type == BaseFragment.BALL_TYPE.FootBall_BanQuan.value()
                || type == BaseFragment.BALL_TYPE.FootBall_HBoDan.value()
                || type == BaseFragment.BALL_TYPE.FootBall_BoDan.value()
                || type == BaseFragment.BALL_TYPE.FootBall_ZongRuQiu.value()
                || type == BaseFragment.BALL_TYPE.FootBall_Re.value()
                ) {
            if (sportdatas.get(groupPosition) instanceof SportsFootBallBean_Guoguan) {
                SportsFootBallBean_Guoguan bean = (SportsFootBallBean_Guoguan) sportdatas.get(groupPosition);
                holder.title.setText(bean.getTitle());
            } else if (sportdatas.get(groupPosition) instanceof SportsFootBallBean_Normal) {
                SportsFootBallBean_Normal bean = (SportsFootBallBean_Normal) sportdatas.get(groupPosition);
                holder.title.setText(bean.getTitle());
            } else if (sportdatas.get(groupPosition) instanceof FootBallbodan) {
                FootBallbodan bean = (FootBallbodan) sportdatas.get(groupPosition);
                holder.title.setText(bean.getTitle());
            } else if (sportdatas.get(groupPosition) instanceof FootBallHq) {
                FootBallHq bean = (FootBallHq) sportdatas.get(groupPosition);
                holder.title.setText(bean.getTitle());
            } else if (sportdatas.get(groupPosition) instanceof FootBallRuQiu) {
                FootBallRuQiu bean = (FootBallRuQiu) sportdatas.get(groupPosition);
                holder.title.setText(bean.getTitle());
            }
            holder.football.setImageDrawable(context.getResources().getDrawable(R.drawable.football));
        }
        if (type == BaseFragment.BALL_TYPE.BasketBall_Normal.value()
                || type == BaseFragment.BALL_TYPE.BasketBall_GuoGuan.value()
                || type == BaseFragment.BALL_TYPE.BasketBall_Re_Main.value()
                ) {
            if (sportdatas.get(groupPosition) instanceof BKBean_Guoguan) {
                BKBean_Guoguan bean = (BKBean_Guoguan) sportdatas.get(groupPosition);
                holder.title.setText(bean.getTitle());
            } else if (sportdatas.get(groupPosition) instanceof BKBean_Normal) {
                BKBean_Normal bean = (BKBean_Normal) sportdatas.get(groupPosition);
                holder.title.setText(bean.getTitle());
            }
            holder.football.setImageDrawable(context.getResources().getDrawable(R.drawable.basketball));
        }
    }

    @Override
    public void onBindChildViewHolder(MyChildViewHolder holder, int groupPosition, int childPosition, @IntRange(from = ItemViewTypeComposer.MIN_WRAPPED_VIEW_TYPE, to = ItemViewTypeComposer.MAX_WRAPPED_VIEW_TYPE) int viewType) {

        if (type == BaseFragment.BALL_TYPE.FootBall_Normal.value() || type == BaseFragment.BALL_TYPE.FootBall_Re.value())//足球单式
        {
            FootBallHandler_Normal(holder.convertView, groupPosition, childPosition);
        } else if (type == BaseFragment.BALL_TYPE.FootBall_GuoGuan.value())//足球过关
        {
            FootBallHandler_GuoGuan(holder.convertView, groupPosition, childPosition);
        } else if (type == BaseFragment.BALL_TYPE.FootBall_BoDan.value())//足球波胆
        {
            FootBallHandler_Bodan(holder.convertView, groupPosition, childPosition, false);
        } else if (type == BaseFragment.BALL_TYPE.FootBall_HBoDan.value())//足球半场波胆
        {
            FootBallHandler_Bodan(holder.convertView, groupPosition, childPosition, true);
        } else if (type == BaseFragment.BALL_TYPE.FootBall_ZongRuQiu.value())//足球 总入球
        {
            FootBallHandler_Ruqiu(holder.convertView, groupPosition, childPosition);
        } else if (type == BaseFragment.BALL_TYPE.FootBall_BanQuan.value())//足球 半场/全场
        {
            FootBallHandler_HQ(holder.convertView, groupPosition, childPosition);
        } else if (type == BaseFragment.BALL_TYPE.BasketBall_Normal.value() || type == BaseFragment.BALL_TYPE.BasketBall_Re_Main.value())//篮球单式
        {
            BasketBallHandler_NorMal(holder.convertView, groupPosition, childPosition);
        } else if (type == BaseFragment.BALL_TYPE.BasketBall_GuoGuan.value())//篮球过关
        {
            BasketBallHandler_GuoGuan(holder.convertView, groupPosition, childPosition);
        }


    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        if(expand)
        {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_bot_selected));
        }
        else
        {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_right_selected));
        }

        return true;
    }

    private String ToHtml(String color[], String... str) {
        if (str.length < 2) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("<HTML>");
        for (int i = 0; i < color.length; i++) {
            sb.append("<font color=\"#" + color[i] + "\">" + " " + str[i] + "</font>");
        }
        sb.append("</HTML>");
//        LogTools.e("ToHtml", sb.toString());
        return sb.toString();
    }

    private String ToHtml(String color, String str) {
        if (str.equalsIgnoreCase("")) return str;
        StringBuilder sb = new StringBuilder();
        sb.append("<HTML>");
        sb.append("<font color=\"#" + color + "\">" + " " + str + "</font>");
        sb.append("</HTML>");
        return sb.toString();
    }

    private void FootBallHandler_Normal(View convertView, int groupPosition, int childPosition)//足球 单式 滚球 ko
    {
        LogTools.e("FootBallHandler_Normal", "FootBallHandler_Normal");
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView Hteam = (TextView) convertView.findViewById(R.id.Hteam);
        TextView Cteam = (TextView) convertView.findViewById(R.id.Cteam);
        ImageView gun_icon=(ImageView)convertView.findViewById(R.id.gun_icon);
        String value[] = null;
        String value2[] = null;
        ArrayList<SportsOrderBean> sportsOrderBeans = new ArrayList<SportsOrderBean>();
        ArrayList<SportsOrderBean> sportsOrderBeans2 = new ArrayList<SportsOrderBean>();

        if (sportdatas.get(groupPosition) instanceof SportsFootBallBean_Normal) {
            SportsFootBallBean_Normal bean = (SportsFootBallBean_Normal) sportdatas.get(groupPosition);
            SportsFootBallBean_Normal.SportsFootBallBean_Normal_Item ballBean = bean.getSportsFootBallBeanNormals().get(childPosition);
//            LogTools.e("比分比分比分", bean.getSportsFootBallBeanNormals().get(childPosition).getRoll()+"");
            Object roll=bean.getSportsFootBallBeanNormals().get(childPosition).getRoll();
            if(roll!=null && Integer.valueOf(roll+"")>0)
            {
                gun_icon.clearAnimation();
                gun_icon.setVisibility(View.VISIBLE);
                Animation circle_anim = AnimationUtils.loadAnimation(context, R.anim.anim_round_rotate);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                circle_anim.setInterpolator(interpolator);
                gun_icon.startAnimation(circle_anim);
            }
            else
            {
                gun_icon.clearAnimation();
                gun_icon.setVisibility(View.GONE);
            }
            if (BaseFragment.BALL_TYPE.FootBall_Re.value()==type)
            {
                time.setText(Html.fromHtml(ballBean.getRetimeset()));
                time.append(" 比分:" + ballBean.getScoreH() + "-" + ballBean.getScoreC());
            }
            else
                time.setText(ballBean.getMatchTime());
//            vsteam.setText(ballBean.getTeamH() + " VS " + ballBean.getTeamC());
            int RecardC=0;
            if(!ballBean.getRedcardC().equalsIgnoreCase(""))
            {
                int  temp=Integer.valueOf(ballBean.getRedcardC());
                if(temp>0)
                    RecardC=temp;
            }

            int RecardH=0;
            if(!ballBean.getRedcardH().equalsIgnoreCase(""))
            {
                int  temp=Integer.valueOf(ballBean.getRedcardH());
                if(temp>0)
                    RecardH=temp;
            }
            Addicon(Hteam,Cteam,ballBean.getTeamH(),ballBean.getTeamC(),RecardH,RecardC);

            value = SportsFootBallBean_Normal.InitTexthomeTeam(ballBean, groupPosition, childPosition);
            value2 = SportsFootBallBean_Normal.InitTextguestTeam(ballBean, groupPosition, childPosition);
            sportsOrderBeans = SportsFootBallBean_Normal.InitTextHomeTeamTag(ballBean, getTimetype(), getRtype());
            sportsOrderBeans2 = SportsFootBallBean_Normal.InitTextguestTeamTag(ballBean, getTimetype(), getRtype());
        } else {
            return;
        }

        String color[] = {"000000", "eb3d00"};
/////////////////////////////////////////////////////////////////////
        if (value != null && value.length == 12) {
            LinearLayout AllMatch = (LinearLayout) convertView.findViewById(R.id.AllMatch);
            for (int i = 0; i < AllMatch.getChildCount(); i++) {
                TableRow tableRow = (TableRow) AllMatch.getChildAt(i);
                for (int j = 0; j < tableRow.getChildCount(); j++) {//多了个主客和
                    TextView textView = (TextView) tableRow.getChildAt(j);//多了个主客和
                    textView.setTag(sportsOrderBeans.get(i * 4 + j));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                    String temp = value[i * 4 + j];
                    if (temp.contains("#@")) {
                        textView.setText(Html.fromHtml(ToHtml(color, temp.split("#@"))));
                    } else {
                        textView.setText(Html.fromHtml(ToHtml("eb3d00", temp)));
                    }
                    if (!textView.getText().toString().equalsIgnoreCase(""))
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                LogTools.e("textView",new Gson().toJson(v.getTag()));
                                StartActivity(v, false);
                            }
                        });
                }
            }
        }
/////////////////////////////////////////////////////////////////////
        if (value2 != null && value2.length == 12) {
            LinearLayout HALFMatch = (LinearLayout) convertView.findViewById(R.id.HALFMatch);
            for (int i = 0; i < HALFMatch.getChildCount(); i++) {
                TableRow tableRow = (TableRow) HALFMatch.getChildAt(i);
                for (int j = 0; j < tableRow.getChildCount(); j++) {//多了个主客和
                    TextView textView = (TextView) tableRow.getChildAt(j);//多了个主客和
                    textView.setTag(sportsOrderBeans2.get(i * 4 + j));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                    String temp = value2[i * 4 + j];
                    if (temp.contains("#@")) {
                        textView.setText(Html.fromHtml(ToHtml(color, temp.split("#@"))));
                    } else {
                        textView.setText(Html.fromHtml(ToHtml("eb3d00", temp)));
                    }
                    if (!textView.getText().toString().equalsIgnoreCase(""))
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                            LogTools.e("textView",new Gson().toJson(v.getTag()));
                                StartActivity(v, false);
                            }
                        });

                }
            }
        }
/////////////////////////////////////////////////////////////////////
    }

    private void FootBallHandler_GuoGuan(View convertView, int groupPosition, int childPosition)//足球 过关 ko
    {
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView Hteam = (TextView) convertView.findViewById(R.id.Hteam);
        TextView Cteam = (TextView) convertView.findViewById(R.id.Cteam);
        ImageView gun_icon=(ImageView)convertView.findViewById(R.id.gun_icon);
        String value[] = null;
        String value2[] = null;
        ArrayList<SportsOrderBean> sportsOrderBeans = new ArrayList<SportsOrderBean>();
        ArrayList<SportsOrderBean> sportsOrderBeans2 = new ArrayList<SportsOrderBean>();

        if (sportdatas.get(groupPosition) instanceof SportsFootBallBean_Guoguan) {
            SportsFootBallBean_Guoguan bean = (SportsFootBallBean_Guoguan) sportdatas.get(groupPosition);
            SportsFootBallBean_Guoguan.FootBallBean_Guoguan_item ballBean = bean.getFootBallBean_guoguan_items().get(childPosition);
            time.setText(ballBean.getMatchTime());
            Object roll=bean.getFootBallBean_guoguan_items().get(childPosition).getRoll();
            if(roll!=null && Integer.valueOf(roll+"")>0)
            {
                Animation circle_anim = AnimationUtils.loadAnimation(context, R.anim.anim_round_rotate);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                circle_anim.setInterpolator(interpolator);
                gun_icon.setVisibility(View.VISIBLE);
                gun_icon.startAnimation(circle_anim);
            }
            else
            {
                gun_icon.clearAnimation();
                gun_icon.setVisibility(View.GONE);
            }

//            vsteam.setText(ballBean.getTeamH() + " VS " + ballBean.getTeamC());
            Addicon(Hteam,Cteam,ballBean.getTeamH(),ballBean.getTeamC(),0,0);
            value = SportsFootBallBean_Guoguan.InitTexthomeTeam(ballBean, groupPosition, childPosition);
            value2 = SportsFootBallBean_Guoguan.InitTextguestTeam(ballBean, groupPosition, childPosition);

            sportsOrderBeans = SportsFootBallBean_Guoguan.InitTextHomeTeamTag(ballBean, getTimetype(), getRtype());
            sportsOrderBeans2 = SportsFootBallBean_Guoguan.InitTextguestTeamTag(ballBean, getTimetype(), getRtype());
        }

        String color[] = {"000000", "eb3d00"};
/////////////////////////////////////////////////////////////////////
        if (value != null && value.length == 12) {
            LinearLayout AllMatch = (LinearLayout) convertView.findViewById(R.id.AllMatch);
            for (int i = 0; i < AllMatch.getChildCount(); i++) {
                TableRow tableRow = (TableRow) AllMatch.getChildAt(i);
                for (int j = 0; j < tableRow.getChildCount(); j++) {//多了个主客和 -1
                    TextView textView = (TextView) tableRow.getChildAt(j);//多了个主客和 +1
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                    String temp = value[i * 4 + j];
                    if (temp.contains("#@")) {
                        textView.setText(Html.fromHtml(ToHtml(color, temp.split("#@"))));
                    } else {
                        textView.setText(Html.fromHtml(ToHtml("eb3d00", temp)));
                    }
                    textView.setTag(sportsOrderBeans.get(i * 4 + j));
                    if (!textView.getText().toString().equalsIgnoreCase("")) {
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                LogTools.e("textView",new Gson().toJson(v.getTag()));
                                StartActivity(v, true);
                            }
                        });
                    }
                }
            }
        }
/////////////////////////////////////////////////////////////////////
        if (value2 != null && value2.length == 12) {
            LinearLayout HALFMatch = (LinearLayout) convertView.findViewById(R.id.HALFMatch);
            for (int i = 0; i < HALFMatch.getChildCount(); i++) {
                TableRow tableRow = (TableRow) HALFMatch.getChildAt(i);
                for (int j = 0; j < tableRow.getChildCount(); j++) {//多了个主客和 -1
                    TextView textView = (TextView) tableRow.getChildAt(j);//多了个主客和+1
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                    String temp = value2[i * 4 + j];
                    if (temp.contains("#@")) {
                        textView.setText(Html.fromHtml(ToHtml(color, temp.split("#@"))));
                    } else {
                        textView.setText(Html.fromHtml(ToHtml("eb3d00", temp)));
                    }
                    textView.setTag(sportsOrderBeans2.get(i * 4 + j));
                    if (!textView.getText().toString().equalsIgnoreCase("")) {
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                LogTools.e("textView", new Gson().toJson(v.getTag()));
                                StartActivity(v, true);
                            }
                        });
                    }
                }
            }
        }
/////////////////////////////////////////////////////////////////////
    }

    private void BasketBallHandler_GuoGuan(View convertView, int groupPosition, int childPosition)//篮球 过关 ko
    {
        String color[] = {"000000", "eb3d00"};

        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView Hteam = (TextView) convertView.findViewById(R.id.Hteam);
        TextView Cteam = (TextView) convertView.findViewById(R.id.Cteam);
        ImageView gun_icon=(ImageView)convertView.findViewById(R.id.gun_icon);
        String strs[] = null;
        ArrayList<SportsOrderBean> sportsOrderBeans = new ArrayList<SportsOrderBean>();

        if (sportdatas.get(groupPosition) instanceof BKBean_Guoguan) {
            BKBean_Guoguan bean = (BKBean_Guoguan) sportdatas.get(groupPosition);
            BKBean_Guoguan.BKitemBean bKitemBean = bean.getbKitemBeans().get(childPosition);
            Object roll=bean.getbKitemBeans().get(childPosition).getRoll();
            if(roll!=null && Integer.valueOf(roll+"")>0)
            {
                Animation circle_anim = AnimationUtils.loadAnimation(context, R.anim.anim_round_rotate);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                circle_anim.setInterpolator(interpolator);
                gun_icon.setVisibility(View.VISIBLE);
                gun_icon.startAnimation(circle_anim);
            }
            else
            {
                gun_icon.clearAnimation();
                gun_icon.setVisibility(View.GONE);
            }
            time.setText(bKitemBean.getMatchTime());
//            vsteam.setText(bKitemBean.getTeamH() + " VS " + bKitemBean.getTeamC());
            Addicon(Hteam,Cteam,bKitemBean.getTeamH(),bKitemBean.getTeamC(),0,0);
            strs = BKBean_Guoguan.GetBasketBallList(bKitemBean);
            sportsOrderBeans = BKBean_Guoguan.GetBasketBallListTAG(bKitemBean, getTimetype(), getRtype());
        }

        if (strs == null || strs.length < 1) return;
        ArrayList<TextView> textViews = new ArrayList<TextView>();
        TableRow tableRow = (TableRow) convertView.findViewById(R.id.bkitem1);
        TableRow tableRow2 = (TableRow) convertView.findViewById(R.id.bkitem2);

        for (int i = 0; i < tableRow.getChildCount(); i++) {
            LinearLayout lintemp = (LinearLayout) tableRow.getChildAt(i);
            for (int k = 0; k < lintemp.getChildCount(); k++) {
                textViews.add((TextView) lintemp.getChildAt(k));
            }
        }
        textViews.remove(0);
        int index = textViews.size();
        for (int i = 0; i < tableRow2.getChildCount(); i++) {
            LinearLayout lintemp = (LinearLayout) tableRow2.getChildAt(i);
            for (int k = 0; k < lintemp.getChildCount(); k++) {
                textViews.add((TextView) lintemp.getChildAt(k));
            }
        }
        textViews.remove(index);

        for (int i = 0; i < textViews.size(); i++) {
            String value = "";
            if (strs[i].contains("#@")) {
                value = ToHtml(color, strs[i].split("#@"));
            } else {
                value = ToHtml("eb3d00", strs[i]);
            }
            textViews.get(i).setText(Html.fromHtml(value));
            textViews.get(i).setTag(sportsOrderBeans.get(i));
            if (!textViews.get(i).getText().toString().equalsIgnoreCase("")) {
                textViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        LogTools.e("textView",new Gson().toJson(v.getTag()));
                        StartActivity(v, true);
                    }
                });
            }
        }
    }

    private void BasketBallHandler_NorMal(View convertView, int groupPosition, int childPosition)//篮球 单式 ko
    {
        String color[] = {"000000", "eb3d00"};

        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView Hteam = (TextView) convertView.findViewById(R.id.Hteam);
        TextView Cteam = (TextView) convertView.findViewById(R.id.Cteam);
        ImageView gun_icon=(ImageView)convertView.findViewById(R.id.gun_icon);
        String strs[] = null;
        ArrayList<SportsOrderBean> sportsOrderBeans = new ArrayList<SportsOrderBean>();

        if (sportdatas.get(groupPosition) instanceof BKBean_Normal) {
            BKBean_Normal bean = (BKBean_Normal) sportdatas.get(groupPosition);
            BKBean_Normal.BkBean_Normal_item bKitemBean = bean.getBkBean_normal_items().get(childPosition);

            Object roll=bean.getBkBean_normal_items().get(childPosition).getRoll();
            if(roll!=null && Integer.valueOf(roll+"")>0)
            {
                Animation circle_anim = AnimationUtils.loadAnimation(context, R.anim.anim_round_rotate);
                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
                circle_anim.setInterpolator(interpolator);
                gun_icon.setVisibility(View.VISIBLE);
                gun_icon.startAnimation(circle_anim);
            }
            else
            {
                gun_icon.clearAnimation();
                gun_icon.setVisibility(View.GONE);
            }
            if(BaseFragment.BALL_TYPE.BasketBall_Re_Main.value()==type)
            {
                String nowsession=bKitemBean.getNowsession();
                String lasttime=bKitemBean.getLasttime();
                String ScoreH=bKitemBean.getScoreH();
                String ScoreC=bKitemBean.getScoreC();
                if(TextUtils.isEmpty(nowsession) && TextUtils.isEmpty(lasttime)
                        && TextUtils.isEmpty(ScoreH) && TextUtils.isEmpty(ScoreC))
                {
                    time.setVisibility(View.GONE);
                }
                else
                {
                    time.setText(Html.fromHtml(bKitemBean.getNowsession() + bKitemBean.getLasttime()));
                    time.append(" 比分" + bKitemBean.getScoreH() + " : " + bKitemBean.getScoreC());
                }
            }
            else
                time.setText(bKitemBean.getMatchTime());

//            vsteam.setText(bKitemBean.getTeamH() + " VS " + bKitemBean.getTeamC());
            Addicon(Hteam,Cteam,bKitemBean.getTeamH(),bKitemBean.getTeamC(),0,0);
            strs = BKBean_Normal.GetBasketBallList(bKitemBean);
            sportsOrderBeans = BKBean_Normal.GetBasketBallListTAG(bKitemBean, getTimetype(), getRtype());
        } else {
            return;
        }

        if (strs == null || strs.length < 10) return;
        ArrayList<TextView> textViews = new ArrayList<TextView>();
        TableRow tableRow = (TableRow) convertView.findViewById(R.id.bkitem1);
        TableRow tableRow2 = (TableRow) convertView.findViewById(R.id.bkitem2);

        if (tableRow == null || tableRow2 == null) {
            LogTools.e("提示", "tableRow是空");
            return;
        }
        for (int i = 0; i < tableRow.getChildCount(); i++) {
            LinearLayout lintemp = (LinearLayout) tableRow.getChildAt(i);
            for (int k = 0; k < lintemp.getChildCount(); k++) {
                textViews.add((TextView) lintemp.getChildAt(k));
            }
        }
        textViews.remove(0);
        int index = textViews.size();
        for (int i = 0; i < tableRow2.getChildCount(); i++) {
            LinearLayout lintemp = (LinearLayout) tableRow2.getChildAt(i);
            for (int k = 0; k < lintemp.getChildCount(); k++) {
                textViews.add((TextView) lintemp.getChildAt(k));
            }
        }
        textViews.remove(index);

        for (int i = 0; i < textViews.size(); i++) {
            String value = "";
            if (strs[i].contains("#@")) {
                value = ToHtml(color, strs[i].split("#@"));
            } else {
                value = ToHtml("eb3d00", strs[i]);
            }
            textViews.get(i).setText(Html.fromHtml(value));
            textViews.get(i).setTag(sportsOrderBeans.get(i));
            if (!textViews.get(i).getText().toString().equalsIgnoreCase("")) {
                textViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        LogTools.e("textView",new Gson().toJson(v.getTag()));
                        StartActivity(v, false);
                    }
                });
            }
        }
    }

    private void FootBallHandler_Bodan(View convertView, int groupPosition, int childPosition, boolean ishalf)//足球 波胆 半波胆
    {
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView Hteam = (TextView) convertView.findViewById(R.id.Hteam);
        TextView Cteam = (TextView) convertView.findViewById(R.id.Cteam);
        TextView hteam = (TextView) convertView.findViewById(R.id.hteam);
        TextView cteam = (TextView) convertView.findViewById(R.id.cteam);

        LinearLayout mainview = (LinearLayout) convertView.findViewById(R.id.mainview);
        FootBallbodan.FootBallbodan_item bKitemBean = null;
        if (sportdatas.get(groupPosition) instanceof FootBallbodan) {
            FootBallbodan bean = (FootBallbodan) sportdatas.get(groupPosition);
            bKitemBean = bean.getFootBallbodan_items().get(childPosition);
            time.setText(bKitemBean.getMatchTime());
//            vsteam.setText(bKitemBean.getTeamH() + " VS " + bKitemBean.getTeamC());
            Addicon(Hteam,Cteam,bKitemBean.getTeamH(),bKitemBean.getTeamC(),0,0);
            hteam.setText(bKitemBean.getTeamH());
            cteam.setText(bKitemBean.getTeamC());
        } else {
            return;
        }

        if (mainview.getChildCount() == 0 || (mainview.getChildCount() > 17 && ishalf) || (mainview.getChildCount() < 26 && !ishalf)) {
            mainview.removeAllViews();
            ArrayList<TextView> textViews = new ArrayList<TextView>();
            if (ishalf) {
                //6 个后单条
                for (int i = 0; i < hbodan.length; i++) {
                    if (i > 5) {
                        mainview.addView(GetLinearView(false, textViews, hbodan[i]));
                    } else {
                        mainview.addView(GetLinearView(true, textViews, hbodan[i]));
                    }
                    ImageView imageview = new ImageView(context);
                    imageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    imageview.setBackgroundColor(context.getResources().getColor(R.color.line));
                    mainview.addView(imageview);
                }
            } else {
                //10 个后单条
                for (int i = 0; i < bodan.length; i++) {
                    if (i > 9) {
                        mainview.addView(GetLinearView(false, textViews, bodan[i]));
                    } else {
                        mainview.addView(GetLinearView(true, textViews, bodan[i]));
                    }
                    ImageView imageview = new ImageView(context);
                    imageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    imageview.setBackgroundColor(context.getResources().getColor(R.color.line));
                    mainview.addView(imageview);
                }
            }
            mainview.setTag(textViews);
        }

        ArrayList<TextView> textviews = (ArrayList<TextView>) mainview.getTag();
        ArrayList<SportsOrderBean> arrayList = null;
        String str[] = null;
        if (bKitemBean != null) {
            if (ishalf) {
                str = FootBallbodan.GetHbodanData(bKitemBean);
                arrayList = FootBallbodan.GetHbodanDataTAG(hbodan, str, bKitemBean, getTimetype(), getRtype());
            } else {
                str = FootBallbodan.GetbodanData(bKitemBean);
                arrayList = FootBallbodan.GetbodanDataTAG(bodan, str, bKitemBean, getTimetype(), getRtype());
            }
        }
        if (str != null && str.length > 0) {
            for (int i = 0; i < str.length; i++) {
                textviews.get(i).setText(str[i]);
                textviews.get(i).setTextColor(0xffeb3d00);
                textviews.get(i).setTag(arrayList.get(i));
                if (!textviews.get(i).getText().toString().equalsIgnoreCase("")) {
                    textviews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            LogTools.e("textview",new Gson().toJson(v.getTag()));
                            StartActivity(v, false);
                        }
                    });
                }
            }
        }
    }

    private void FootBallHandler_HQ(View convertView, int groupPosition, int childPosition)//足球 全场/半场
    {
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView Hteam = (TextView) convertView.findViewById(R.id.Hteam);
        TextView Cteam = (TextView) convertView.findViewById(R.id.Cteam);
        LinearLayout mainview = (LinearLayout) convertView.findViewById(R.id.mainview);
        String strs[] = null;
        ArrayList<SportsOrderBean> sportsOrderBeans = null;

        if (sportdatas.get(groupPosition) instanceof FootBallHq) {
            FootBallHq foothq = (FootBallHq) sportdatas.get(groupPosition);
            FootBallHq.FootBallHq_item item = foothq.getFootBallHq_items().get(childPosition);
            time.setText(item.getMatchTime());
//            vsteam.setText(item.getTeamH() + " VS " + item.getTeamC());
            Addicon(Hteam,Cteam,item.getTeamH(),item.getTeamC(),0,0);
            strs = FootBallHq.GetBasketBallList(item);
            sportsOrderBeans = FootBallHq.GetBasketBallListTAG(item, hqtitles_tag, strs, getTimetype(), getRtype());
        }

        if (mainview.getChildCount() == 0) {
            ArrayList<TextView> textviews = new ArrayList<TextView>();
            for (int i = 0; i < 6; i++) {
                if (i % 2 == 0) {
                    int k = (i / 2) * 3;
                    String temp[] = {hqtitles[k], hqtitles[k + 1], hqtitles[k + 2]};
                    mainview.addView(GetLinearView(null, shadow, 3, temp));//标题
                } else {
                    mainview.addView(GetLinearView(textviews, null, 3, null));//显示值
                }
            }
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
            imageview.setBackgroundColor(context.getResources().getColor(R.color.line));
            mainview.addView(imageview);
            mainview.setTag(textviews);
        }

        ArrayList<TextView> textviews = (ArrayList<TextView>) mainview.getTag();
        if (strs != null && strs.length == 9) {
            for (int i = 0; i < strs.length; i++) {
                textviews.get(i).setText(strs[i]);
                textviews.get(i).setTextColor(0xffeb3d00);
//                textviews.get(i).setTextColor(context.getResources().getColor(R.color.colorPrimary));
                textviews.get(i).setTag(sportsOrderBeans.get(i));
                if (!textviews.get(i).getText().toString().equalsIgnoreCase("")) {
                    textviews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            LogTools.e("textview",new Gson().toJson(v.getTag()));
                            StartActivity(v, false);
                        }
                    });
                }
            }
        }
    }

    private void FootBallHandler_Ruqiu(View convertView, int groupPosition, int childPosition)//足球 总入球
    {
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView Hteam = (TextView) convertView.findViewById(R.id.Hteam);
        TextView Cteam = (TextView) convertView.findViewById(R.id.Cteam);
        LinearLayout mainview = (LinearLayout) convertView.findViewById(R.id.mainview);
        String strs[] = null;
        ArrayList<SportsOrderBean> sportsOrderBeans = null;

        if (sportdatas.get(groupPosition) instanceof FootBallRuQiu) {
            FootBallRuQiu foothq = (FootBallRuQiu) sportdatas.get(groupPosition);
            FootBallRuQiu.FootBallRuQiu_item item = foothq.getFootBallRuQiu_items().get(childPosition);
            time.setText(item.getMatchTime());
//            vsteam.setText(item.getTeamH() + " VS " + item.getTeamC());
            Addicon(Hteam,Cteam,item.getTeamH(),item.getTeamC(),0,0);
            strs = FootBallRuQiu.GetBasketBallList(item);
            sportsOrderBeans = FootBallRuQiu.GetBasketBallListTAG(item, ruqiu_tag, strs, getTimetype(), getRtype());
        }

        if (mainview.getChildCount() == 0) {
            ArrayList<TextView> textviews = new ArrayList<TextView>();
            for (int i = 0; i < 2; i++) {
                if (i % 2 == 0) {
                    int k = (i % 2) * 4;
                    String temp[] = {ruqiu[k], ruqiu[k + 1], ruqiu[k + 2], ruqiu[k + 3]};
                    mainview.addView(GetLinearView(null, shadow, 4, temp));//标题
                } else {
                    mainview.addView(GetLinearView(textviews, null, 4, null));//显示值
                }
            }
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
            imageview.setBackgroundColor(context.getResources().getColor(R.color.line));
            mainview.addView(imageview);
            mainview.setTag(textviews);
        }

        ArrayList<TextView> textviews = (ArrayList<TextView>) mainview.getTag();
        if (strs != null && strs.length == 4) {
            for (int i = 0; i < strs.length; i++) {
                textviews.get(i).setText(strs[i]);
                textviews.get(i).setTextColor(0xffeb3d00);
                textviews.get(i).setTag(sportsOrderBeans.get(i));
                if (!textviews.get(i).getText().toString().equalsIgnoreCase("")) {
                    textviews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            LogTools.e("textview",new Gson().toJson(v.getTag()));
                            StartActivity(v, false);
                        }
                    });
                }

            }
        }
    }

    private LinearLayout GetLinearView(boolean isfull, ArrayList<TextView> textViews, String title) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (isfull)//多字段的
        {
            TextView textView1 = MakeTextView(0.5f / 5.1f, "");
            TextView textView2 = MakeTextView(0.8f / 5.1f, title);
            TextView textView3 = MakeTextView(1.5f / 5.1f, "");
            textViews.add(textView3);
            TextView textView4 = MakeTextView(0.8f / 5.1f, "");
            TextView textView5 = MakeTextView(1.5f / 5.1f, "");
            textViews.add(textView5);

            linearLayout.addView(textView1);
            linearLayout.addView(textView2);
            linearLayout.addView(textView3);
            linearLayout.addView(textView4);
            linearLayout.addView(textView5);
        } else//只有一个字段的
        {
            TextView textView1 = MakeTextView(0.5f / 5.1f, "");
            TextView textView2 = MakeTextView(0.8f / 5.1f, title);
            TextView textView3 = MakeTextView(3.8f / 5.1f, "");
            textViews.add(textView3);

            linearLayout.addView(textView1);
            linearLayout.addView(textView2);
            linearLayout.addView(textView3);
        }
        return linearLayout;
    }

    private LinearLayout GetLinearView(ArrayList<TextView> textViews, Drawable able, int count, String title[]) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        if (able != null)
            linearLayout.setBackground(able);

        for (int i = 0; i < count; i++) {
            if (title != null && title.length > i) {
                TextView editview = MakeTextView(1f / count, title[i]);
                linearLayout.addView(editview);
            } else {
                TextView editview = MakeTextView(1f / count, "");
                linearLayout.addView(editview);
                if (textViews != null) {
                    textViews.add(editview);
                }
            }

        }
        return linearLayout;
    }

    private TextView MakeTextView(float weight, String str) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (screenWidth * weight), ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.weight=weight;
//        layoutParams.leftMargin=2;
        TextView textView1 = new TextView(context);
        textView1.setLayoutParams(layoutParams);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        textView1.setText(str);
        textView1.setGravity(Gravity.CENTER);
        textView1.setPadding(0, ScreenUtils.getDIP2PX(context, 7), 0, ScreenUtils.getDIP2PX(context, 7));
//        textView1.setBackgroundColor(0x77123456 );
        return textView1;
    }


    private void StartActivity(View v, boolean iscontinue) {
        BaseApplication baseApplication = (BaseApplication) ((Activity) context).getApplication();
        if (baseApplication.getBaseapplicationUsername() == null || baseApplication.getBaseapplicationUsername().equalsIgnoreCase("")) {
            ToastUtil.showMessage(context, context.getString(R.string.pleaselongin2));
            context.startActivity(new Intent(context, LoginActivity.class));
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, SportOrderActivity.class);
        SportsOrderBean sportsOrderBean=(SportsOrderBean) v.getTag();
        int balltype=1;//按照标题的顺序 1是足球
        if(type==BaseFragment.BALL_TYPE.BasketBall_Re_Main.value() || type==BaseFragment.BALL_TYPE.BasketBall_Normal.value()
                || type==BaseFragment.BALL_TYPE.BasketBall_GuoGuan.value())
        {
            balltype=0;
        }
        sportsOrderBean.setBallType(balltype);
        sportsOrderBean.setCreatetime(System.currentTimeMillis());
        intent.putExtra(BundleTag.OrderJson, new Gson().toJson(sportsOrderBean));
        intent.putExtra(BundleTag.Continue, iscontinue);
        context.startActivity(intent);
    }

    private void Addicon(TextView textView,TextView textView2,String Hteam,String Cteam,int RedCardH,int RedCardC)
    {
        Drawable drawable= context.getResources().getDrawable(R.drawable.zu);
        drawable.setBounds(0, 0, ScreenUtils.sp2px(context, 16f), ScreenUtils.sp2px(context, 16f));
        textView.setCompoundDrawables(drawable, null, null, null); //设置左图标

        SpannableStringBuilder builder2;
        String RedCardH_str="";
        if(RedCardH>0)
        {
            RedCardH_str="  "+String.valueOf(RedCardH)+"  ";
        }
        builder2 = new SpannableStringBuilder(RedCardH_str+Hteam+" VS ");
        if(RedCardH>0) {
            ForegroundColorSpan whiteSpan2 = new ForegroundColorSpan(Color.WHITE);
            builder2.setSpan(whiteSpan2, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            BackgroundColorSpan redSpan2 = new BackgroundColorSpan(0xffB91514);
            builder2.setSpan(redSpan2, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setCompoundDrawablePadding(10);
        textView.setText(builder2);
///////////////////////////////////////////////////////////////////////////////
        Drawable ke= context.getResources().getDrawable(R.drawable.ke);
        ke.setBounds(0, 0, ScreenUtils.sp2px(context, 16f), ScreenUtils.sp2px(context, 16f));
        textView2.setCompoundDrawables(ke, null, null, null); //设置左图标

        SpannableStringBuilder builder3;
        String RedCardC_str="";
        if(RedCardC>0)
        {
            RedCardC_str=String.valueOf("  "+RedCardC+"  ");
        }

        builder3 = new SpannableStringBuilder(RedCardC_str+Cteam);
        if(RedCardC>0)
        {
            ForegroundColorSpan whiteSpan3 = new ForegroundColorSpan(Color.WHITE);
            builder3.setSpan(whiteSpan3, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            BackgroundColorSpan redSpan3 = new BackgroundColorSpan(0xffB91514);
            builder3.setSpan(redSpan3, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView2.setCompoundDrawablePadding(10);
        textView2.setText(builder3);
    }

    static abstract class MyBaseItem {
        public final long id;
        public final String text;

        public MyBaseItem(long id, String text) {
            this.id = id;
            this.text = text;
        }
    }

    static class MyGroupItem extends MyBaseItem {
        public final List<MyChildItem> children;

        public MyGroupItem(long id, String text) {
            super(id, text);
            children = new ArrayList<>();
        }
    }

    static class MyChildItem extends MyBaseItem {
        public MyChildItem(long id, String text) {
            super(id, text);
        }
    }

    public static class MyGroupViewHolder extends AbstractExpandableItemViewHolder {
        ImageView imageView;
        TextView title;
        ImageView football;
        public MyGroupViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.triangle);
             title = (TextView) itemView.findViewById(R.id.title);
             football = (ImageView) itemView.findViewById(R.id.football);
        }
    }

    public static class MyChildViewHolder extends AbstractExpandableItemViewHolder {
        View convertView;
        public MyChildViewHolder(View itemView) {
            super(itemView);
            convertView=itemView;
        }
    }
}
