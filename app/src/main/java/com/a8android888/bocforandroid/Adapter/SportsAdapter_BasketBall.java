package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.SportsFootBallBean_Normal;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */
public class SportsAdapter_BasketBall extends AnimatedExpandableListView.AnimatedExpandableListAdapter{

    private Context context;
    private ArrayList<?> sportdatas;

    public SportsAdapter_BasketBall(Context context,ArrayList<?> sportdatas)
    {
        this.context=context;
        this.sportdatas=sportdatas;
    }

    public void NotifyAdapter(ArrayList<?> sportdatas)
    {
        if(sportdatas==null)
            this.sportdatas=sportdatas;
        notifyDataSetChanged();
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView=View.inflate(context, R.layout.item_basketball_results,null);

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        if(sportdatas.get(groupPosition) instanceof SportsFootBallBean_Normal)
        {
            SportsFootBallBean_Normal bean=(SportsFootBallBean_Normal)sportdatas.get(groupPosition);
            return bean.getSportsFootBallBeanNormals().size();
        }
        return 0;
    }

    @Override
    public int getGroupCount() {
        return sportdatas.size();
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
        if(convertView==null)
            convertView=View.inflate(context, R.layout.item_sports_maintitle,null);

        TextView title=(TextView)convertView.findViewById(R.id.title);
        if(sportdatas.get(groupPosition) instanceof SportsFootBallBean_Normal)
        {
            SportsFootBallBean_Normal bean=(SportsFootBallBean_Normal)sportdatas.get(groupPosition);
            title.setText(bean.getTitle());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
