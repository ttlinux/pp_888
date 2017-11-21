package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.Messagebean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.Message.MessageDetailActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/29.
 */
public class MessageAdapter extends BaseAdapter{

    Context context;
    ArrayList<Messagebean> messagebeans;
    boolean hasdel;
    OnDeleteListener onDeleteListener;
    public MessageAdapter(Context context,ArrayList<Messagebean> messagebeans,boolean hasdel)
    {
        this.context=context;
        this.messagebeans=messagebeans;
        this.hasdel=hasdel;
    }

    public MessageAdapter(Context context,ArrayList<Messagebean> messagebeans,boolean hasdel,OnDeleteListener onDeleteListener)
    {
        this.context=context;
        this.messagebeans=messagebeans;
        this.hasdel=hasdel;
        this.onDeleteListener=onDeleteListener;
    }

    public void notifyadapter(ArrayList<Messagebean> messagebeans)
    {

        this.messagebeans=messagebeans;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return messagebeans.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            convertView=View.inflate(context, R.layout.item_message,null);
            viewHolder=new ViewHolder();
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasdel) {
                    Intent intent = new Intent();
                    intent.setClass(context, MessageDetailActivity.class);
                    intent.putExtra(BundleTag.JsonObject, new Gson().toJson(messagebeans.get(position)));
                    intent.putExtra(BundleTag.ChangeState, hasdel);
                    intent.putExtra(BundleTag.Position, position);
                    intent.putExtra(BundleTag.type, messagebeans.get(position).getReadStatus()<1);
                    ((Activity) context).startActivityForResult(intent, BundleTag.ResultOk);
                }
            }
        });
        viewHolder.title=(TextView)convertView.findViewById(R.id.title);
        viewHolder.time=(TextView)convertView.findViewById(R.id.time);
        viewHolder.del=(TextView)convertView.findViewById(R.id.del);
        viewHolder.content=(TextView)convertView.findViewById(R.id.content);
        viewHolder.imageview=(ImageView)convertView.findViewById(R.id.imageview);

        viewHolder.title.setText(messagebeans.get(position).getMsgTitle());
        String createtime= messagebeans.get(position).getCreateTime();
        viewHolder.time.setText(createtime.substring(0, createtime.length() - 3));
        if(!hasdel)
        {
            viewHolder.content.setMaxLines(Integer.MAX_VALUE);
        }
        viewHolder.content.setText(messagebeans.get(position).getMsgContent());

        if(!hasdel) {
            viewHolder.imageview.setImageDrawable(context.getResources().getDrawable(R.drawable.notice));
            viewHolder.del.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.content.setVisibility(View.GONE);
            viewHolder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteListener != null) {
                        onDeleteListener.OnDelete(messagebeans.get(position).getId(), position);
                    }
                }
            });
                if(messagebeans.get(position).getReadStatus()>0)
                {
                    Drawable drawable = context.getResources()
                            .getDrawable(R.drawable.message);
                    viewHolder.imageview.setImageDrawable(drawable);
                    viewHolder.del.setBackgroundResource(R.drawable.del_bg);
                }
                else
                {
                    Drawable drawable = context.getResources()
                            .getDrawable(R.drawable.unmessage);
                    viewHolder.imageview.setImageDrawable(drawable);
                    viewHolder.del.setBackgroundResource(R.drawable.del_bg_un);
                }

        }

        return convertView;
    }

    class ViewHolder
    {
        TextView title,time,del,content;
        ImageView imageview;

    }

    public interface OnDeleteListener
    {
        public void OnDelete(int id,int position);
    }
}
