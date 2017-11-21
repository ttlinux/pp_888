package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.SafeBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.UserMessageActivity.BindBankActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SafekList_Adapter extends BaseAdapter {
    private Context mContext;
    List<SafeBean> mList=new ArrayList<SafeBean>();
    public SafekList_Adapter(Context context, List<SafeBean> list) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_safe_title, null);
            convertView.setTag(holder);

        }   else{
        holder= (ViewHolder) convertView.getTag();
    }
        holder.shebei = (TextView) convertView.findViewById(R.id.shebei);
        holder.ip= (TextView) convertView.findViewById(R.id.ip);
        holder.time= (TextView) convertView.findViewById(R.id.time);
        holder.time.setText(mList.get(position).getCreateTime());
        holder.shebei.setText(mList.get(position).getLoginDevice());
        holder.ip.setText(mList.get(position).getLogAddress()+"("+mList.get(position).getIpPosition()+")");
        return convertView;
    }
        class ViewHolder {
        TextView ip,shebei,time;
    }

}
