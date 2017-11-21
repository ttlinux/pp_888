package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.OnelottercenterActivity;

import java.util.ArrayList;
import java.util.List;

public class one_Lottery_Adapter extends BaseAdapter {
    private Context mContext;
    List<LotteryBean> mList=new ArrayList<LotteryBean>();
    public one_Lottery_Adapter(Context context, List<LotteryBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public void NotifyAdapter(List<LotteryBean> list)
    {
        this.mList = list;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.onelottery_item, null);
            convertView.setTag(holder);
            holder.qs = (TextView) convertView.findViewById(R.id.qs);
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.time = (TextView) convertView.findViewById(R.id.time);
        }   else{
        holder= (ViewHolder) convertView.getTag();
    }
        holder.time.setText( mList.get(position).getOpenTime());

        holder.qs.setText( "第"+mList.get(position).getQs()+"期");

        holder.code.setText( mList.get(position).getOpenCode());
        return convertView;
    }
        class ViewHolder {
        TextView time,qs,code;
    }
}
