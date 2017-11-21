package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.view.BubbleView;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/2.
 */
public class NoticelistAdapter extends BaseAdapter{

    ArrayList<JSONObject> jsonObjects;
    Context context;

    public NoticelistAdapter(ArrayList<JSONObject> jsonObjects,Context context)
    {
        this.jsonObjects=jsonObjects;
        this.context=context;
    }

    public void NotifyAdapter(ArrayList<JSONObject> jsonObjects)
    {
        if(jsonObjects==null)this.jsonObjects.clear();
            else
            this.jsonObjects=jsonObjects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return jsonObjects.size();
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
        Holder holder;
        if(convertView==null)
        {
            holder=new Holder();
            convertView=View.inflate(context, R.layout.item_bubble_view,null);
//            convertView=MakeView();
            convertView.setTag(holder);
        }
        else
        {
            holder=(Holder)convertView.getTag();
        }


        holder.content=(TextView)convertView.findViewById(R.id.content);
        holder.title=(TextView)convertView.findViewById(R.id.title);
        holder.time=(TextView)convertView.findViewById(R.id.time);

        holder.content.setText(jsonObjects.get(position).optString("ggContent",""));
        holder.title.setText(jsonObjects.get(position).optString("ggName",""));
        holder.time.setText(jsonObjects.get(position).optString("createTime",""));
        return convertView;
    }

    class Holder
    {
        TextView title,time,content;
    }

    private BubbleView MakeView()
    {
        View view=View.inflate(context, R.layout.item_bubble_view,null);
        BubbleView bubbleView = new BubbleView(context);
        bubbleView.setOrientation(BubbleView.VERTICAL);
        ListView.LayoutParams params=new ListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.leftMargin= ScreenUtils.getDIP2PX(context, 20);
//        params.rightMargin=ScreenUtils.getDIP2PX(context,20);
//        params.topMargin=ScreenUtils.getDIP2PX(context,20);
        bubbleView.setLayoutParams(params);
        bubbleView.SetLayoutPosition(BubbleView.Side_Left, BubbleView.Style_Middle);
        bubbleView.setBotWidth(50);
        bubbleView.setBotHeight(50);

        bubbleView.SetViewStyle(true);
        bubbleView.SetViewColor(Color.GRAY);
        bubbleView.addView(view);


        return bubbleView;
    }
}
