package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.R;

public class Sport_Adapter extends BaseAdapter {
    private Context mContext;
    String[] mList;
    public Sport_Adapter(Context context, String[] list) {
        this.mContext = context;
        this.mList = list;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return mList.length;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList[position];
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
                    R.layout.sport_item, null);
            convertView.setTag(holder);
        }   else{
        holder= (ViewHolder) convertView.getTag();
    }
        holder.videoname = (TextView) convertView.findViewById(R.id.videoname);
        holder.videoname.setText(mList[position]);
        holder.videoimg = (ImageView) convertView.findViewById(R.id.videoimg);
        return convertView;
    }
        class ViewHolder {
        TextView videoname;
        ImageView videoimg;
    }
}
