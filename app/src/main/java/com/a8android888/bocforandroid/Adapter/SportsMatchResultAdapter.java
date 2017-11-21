package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.BKBean_Guoguan;
import com.a8android888.bocforandroid.Bean.BKBean_Normal;
import com.a8android888.bocforandroid.Bean.BKSportsResultBean;
import com.a8android888.bocforandroid.Bean.FTSportsResultBean;
import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Normal;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */
public class SportsMatchResultAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter{

    private Context context;
    ArrayList<?> ResultBeans;
    int type;
    SparseArray<View> Mainviews=new SparseArray<View>();
    SparseArray<View> views=new SparseArray<View>();

    public SportsMatchResultAdapter( Context context,ArrayList<?> ResultBeans,int type)
    {
        this.context=context;
        this.ResultBeans = ResultBeans;
        this.type=type;
    }

    public void NotifyAdapter(ArrayList<?> ResultBeans,int type)
    {
        this.ResultBeans = ResultBeans;
        this.type=type;
        views.clear();
        Mainviews.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


            if(type== BaseFragment.BALL_RESULT_TYPE.BasketBall_Result.value())
            {
                if(views.get((groupPosition+1)*childPosition)==null) {
                    convertView = View.inflate(context, R.layout.item_basketball_results, null);
                    views.put((groupPosition+1)*childPosition,convertView);
                }
                else
                {
                    convertView=views.get((groupPosition+1)*childPosition);
                }
            }
            else if(type== BaseFragment.BALL_RESULT_TYPE.FootBall_Result.value())
            {
                if(views.get((groupPosition+1)*childPosition)==null) {
                    convertView = View.inflate(context, R.layout.item_football_results, null);
                    views.put((groupPosition + 1) * childPosition, convertView);
                }
                else
                {
                    convertView=views.get((groupPosition+1)*childPosition);
                }
            }


        TextView title=(TextView)convertView.findViewById(R.id.title);
        TextView time=(TextView)convertView.findViewById(R.id.time);


        if(type== BaseFragment.BALL_RESULT_TYPE.BasketBall_Result.value())
        {
            BKSportsResultBean bkbean=(BKSportsResultBean)ResultBeans.get(groupPosition);
            BKSportsResultBean.SportsResultBean_item item=bkbean.getSportsResultBean_items().get(childPosition);
            String MatchRealTime=item.getMatchRealTime();
            title.setText(item.getTeamH()+" VS "+item.getTeamC());
            if(MatchRealTime.length()>6)
            time.setText(Html.fromHtml("<HTML>"+MatchRealTime.substring(5, MatchRealTime.length()).replace(" ","<br/>")+"<HTML>"));

            LinearLayout mainview=(LinearLayout)convertView.findViewById(R.id.mainview);
            ArrayList<TextView> textviews=new ArrayList<TextView>();
            for (int i = 0; i < mainview.getChildCount(); i++) {
                if(i==0)continue;
                TableRow tablerow=(TableRow)mainview.getChildAt(i);
                for (int k = 0; k < tablerow.getChildCount(); k++) {
                    if(k==0)continue;
                    TextView textview=(TextView)tablerow.getChildAt(k);
                    textviews.add(textview);
                }
            }
            /////////////////////////////////////////////////////////////
            String str[]=BKSportsResultBean.Getdata(ResultBeans,groupPosition,childPosition);
            if(str!=null && str.length==14)
            {
                for (int i = 0; i < textviews.size(); i++) {
                    textviews.get(i).setText(str[i]);
                    if((i==13 || i==12) && Httputils.isNumber(str[i]))
                    {
                        textviews.get(i).setTextColor(Color.RED);
                    }
                }
            }
        }
        else if(type== BaseFragment.BALL_RESULT_TYPE.FootBall_Result.value())
        {
            FTSportsResultBean bkbean=(FTSportsResultBean)ResultBeans.get(groupPosition);
            FTSportsResultBean.FTSportsResultBean_item item=bkbean.getFtSportsResultBean_items().get(childPosition);
            title.setText(item.getTeamH()+" VS "+item.getTeamC());
            String MatchRealTime=item.getMatchRealTime();
            if(MatchRealTime.length()>6)
            time.setText(Html.fromHtml("<HTML>"+MatchRealTime.substring(5, MatchRealTime.length()).replace(" ","<br/>")+"<HTML>"));

            LinearLayout mainview=(LinearLayout)convertView.findViewById(R.id.mainview);
            ArrayList<TextView> textviews=new ArrayList<TextView>();
            for (int i = 0; i < mainview.getChildCount(); i++) {
                if(i==0)continue;
                TableRow tablerow=(TableRow)mainview.getChildAt(i);
                for (int k = 0; k < tablerow.getChildCount(); k++) {
                    if(k==0)continue;
                    TextView textview=(TextView)tablerow.getChildAt(k);
                    textviews.add(textview);
                }
            }
            ///////////////////////////////////
            String str[]= FTSportsResultBean.Getdata(ResultBeans,groupPosition,childPosition);
            if(str!=null && str.length==4)
            {
                for (int i = 0; i < textviews.size(); i++) {
                    textviews.get(i).setText(str[i]);
                    if((i==2 || i==3) && Httputils.isNumber(str[i]))
                    {
                        textviews.get(i).setTextColor(Color.RED);
                    }
                }
            }
        }

        return views.get((groupPosition + 1) * childPosition);
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        if(ResultBeans.get(groupPosition) instanceof BKSportsResultBean)
        {
            return ((BKSportsResultBean) ResultBeans.get(groupPosition)).getSportsResultBean_items().size();
        }
        if(ResultBeans.get(groupPosition) instanceof FTSportsResultBean)
        {
            return ((FTSportsResultBean) ResultBeans.get(groupPosition)).getFtSportsResultBean_items().size();
        }
        return 0;
    }

    @Override
    public int getGroupCount() {
        return ResultBeans.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(Mainviews.get(groupPosition)==null)
        {
            convertView=View.inflate(context, R.layout.item_sports_maintitle,null);
            Mainviews.put(groupPosition,convertView);
        }
        else
        {
            convertView=Mainviews.get(groupPosition);
        }
//        convertView.setBackgroundColor(context.getResources().getColor(R.color.loess));
        convertView.findViewById(R.id.football).setVisibility(View.GONE);
        TextView title=(TextView)convertView.findViewById(R.id.title);
        title.setTextColor(Color.BLACK);
        ImageView football=(ImageView)convertView.findViewById(R.id.football);
        if(ResultBeans.get(groupPosition) instanceof BKSportsResultBean)
        {
            BKSportsResultBean bean=(BKSportsResultBean)ResultBeans.get(groupPosition);
            title.setText(bean.getTitle());
            football.setImageDrawable(context.getResources().getDrawable(R.drawable.basketball));
        }
        if(ResultBeans.get(groupPosition) instanceof FTSportsResultBean)
        {
            FTSportsResultBean bean=(FTSportsResultBean)ResultBeans.get(groupPosition);
            title.setText(bean.getTitle());
            football.setImageDrawable(context.getResources().getDrawable(R.drawable.football));
        }
        ImageView imageView=(ImageView)convertView.findViewById(R.id.triangle);
        if(isExpanded)
        {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_bot_selected));
        }
        else
        {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_right_selected));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
