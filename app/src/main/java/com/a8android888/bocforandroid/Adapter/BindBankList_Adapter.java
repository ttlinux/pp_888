package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.BankBean;
import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.UserMessageActivity.BindBankActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BindBankList_Adapter extends BaseAdapter {
    private Context mContext;
    List<BankBean> mList=new ArrayList<BankBean>();
    private ImageLoader mImageDownLoader;
    private int record=-1;
    public BindBankList_Adapter(Context context, List<BankBean> list) {
        this.mContext = context;
        this.mList = list;
        mImageDownLoader = ((BaseApplication)context.getApplicationContext())
                .getImageLoader();
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
        final int index =position;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_gameroom_title, null);
            convertView.setTag(holder);

        }   else{
        holder= (ViewHolder) convertView.getTag();
    }
        holder.videoimg = (ImageView) convertView.findViewById(R.id.img);
        holder.videoname = (TextView) convertView.findViewById(R.id.title);
        holder.xiala= (ImageView) convertView.findViewById(R.id.xiala);
        holder.xiala.setVisibility(View.GONE);
      holder.videoname.setText(mList.get(position).getName());
        holder.videoname.setTextColor(mList.get(position).isSelected()?Color.RED:Color.BLACK);
        mImageDownLoader.displayImage(mList.get(index).getSmallimg(), holder.videoimg);
   convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(record>-1)
                {
                    mList.get(record).setSelected(false);
                }
                mList.get(index).setSelected(true);
                record=index;
                Intent intent = new Intent(mContext, BindBankActivity.class);
                intent.putExtra("test", mList.get(index).getName());
                intent.putExtra("bankType", mList.get(index).getBankCode());
                ((Activity) mContext).setResult(112, intent);
                notifyDataSetChanged();
                ((Activity) mContext).finish();
            }
        });
        return convertView;
    }
        class ViewHolder {
        TextView videoname;
        ImageView videoimg,xiala;
    }

}
