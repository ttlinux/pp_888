package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.Query.QueryActivity;
import com.a8android888.bocforandroid.activity.user.Query.QueryDetailActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.json.JSONObject;

import java.util.ArrayList;

public class Query_Adapter extends BaseAdapter {
    private Context mContext;
    ArrayList<JSONObject> mList;
    ImageLoader imageDownloader;
    public Query_Adapter(Context context, ArrayList<JSONObject> list) {
        this.mContext = context;
        this.mList = list;
        imageDownloader = ((BaseApplication)((Activity)context).getApplication())
                .getImageLoader();
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
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
                    R.layout.change_item_layout, null);
            convertView.setTag(holder);
        }   else{
        holder= (ViewHolder) convertView.getTag();
    }
        holder.change_name = (TextView) convertView.findViewById(R.id.platformchange);
        holder.change_name.setText(mList.get(position).optString("flatName", ""));
        holder.change_name.setGravity(Gravity.LEFT | Gravity.CENTER);
        holder.change_name.setPadding(ScreenUtils.getDIP2PX(mContext, 20), 0, 0, 0);

        holder.change_balance = (TextView) convertView.findViewById(R.id.balance);
        holder.change_balance.setVisibility(View.GONE);

        holder.change_into = (Button) convertView.findViewById(R.id.changeinto);
        holder.change_into.setVisibility(View.GONE);

        holder.change_out = (Button) convertView.findViewById(R.id.changeout);
        holder.change_out.setTag(position);
        holder.change_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.valueOf(v.getTag() + "");
                Intent intent = new Intent();
                intent.setClass(mContext, QueryDetailActivity.class);
                intent.putExtra(BundleTag.Platform, mList.get(position).optString("flat", ""));
                intent.putExtra(BundleTag.PlatformName, mList.get(position).optString("flatName", ""));
                mContext.startActivity(intent);
            }
        });

        holder.title_img=(ImageView)convertView.findViewById(R.id.title_img);
        imageDownloader.displayImage( mList.get(position).optString("smallPic", ""),holder.title_img);

        LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)holder.change_out.getLayoutParams();
        layoutParams.weight=1.5f;
        holder.change_out.setLayoutParams(layoutParams);
//        holder.change_out.setPadding(10,5,10,5);
        holder.change_out.setText("查询");
        return convertView;
    }
        class ViewHolder {
        TextView change_name, change_balance;
        Button change_into,change_out;
            ImageView title_img;
    }
}
