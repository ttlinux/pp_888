package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.game.GameFragment;
import com.a8android888.bocforandroid.activity.main.lottery.OnelottercenterActivity;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class Lottery_center_Adapter extends BaseAdapter {
    private Context mContext;
    List<LotteryBean> mList=new ArrayList<LotteryBean>();
    public int[]  imgd = new int[]{R.drawable.liuhecai, R.drawable.fucai, R.drawable.pailie,
            R.drawable.cqssc,  R.drawable.tjssc, R.drawable.xjssc, R.drawable.gdsf,R.drawable.tjsf,
            R.drawable.pk, R.drawable.xingyun, R.drawable.jianada};
    private ImageLoader mImageDownLoader;
    public Lottery_center_Adapter(Context context, List<LotteryBean> list) {
        this.mContext = context;
        this.mList = list;
        mImageDownLoader = ((BaseApplication)mContext.getApplicationContext())
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
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.lottery_center_item, null);
            convertView.setTag(holder);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.qs = (TextView) convertView.findViewById(R.id.qs);
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
        }   else{
        holder= (ViewHolder) convertView.getTag();
    }

        holder.name.setText( mList.get(position).getGameName());

        holder.qs.setText( "第"+ mList.get(position).getQs()+"期");

        holder.code.setText( mList.get(position).getOpenCode());
//
//        if(mList.get(position).getGameId().equalsIgnoreCase("1")){
//            holder.img.setBackgroundResource(imgd[0]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("2")){
//            holder.img.setBackgroundResource(imgd[1]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("3")){
//            holder.img.setBackgroundResource(imgd[2]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("4")){
//            holder.img.setBackgroundResource(imgd[3]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("6")){
//            holder.img.setBackgroundResource(imgd[4]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("7")){
//            holder.img.setBackgroundResource(imgd[5]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("8")){
//            holder.img.setBackgroundResource(imgd[6]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("9")){
//            holder.img.setBackgroundResource(imgd[7]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("10")){
//            holder.img.setBackgroundResource(imgd[8]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("11")){
//            holder.img.setBackgroundResource(imgd[9]);
//        }
//        if(mList.get(position).getGameId().equalsIgnoreCase("12")){
//            holder.img.setBackgroundResource(imgd[10]);
//        }
        mImageDownLoader.displayImage(mList.get(position).getImg(), holder.img);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(mContext, OnelottercenterActivity.class);
                    intent.putExtra("title", mList.get(position).getGameName());
                    intent.putExtra("id", mList.get(position).getGameId());
                    intent.putExtra("code", mList.get(position).getOpenCode());
                intent.putExtra("gamecode", mList.get(position).getGameCode());
                intent.putExtra("qs", mList.get(position).getQs());
                    mContext.startActivity(intent);
                }
        });
        return convertView;
    }
        class ViewHolder {
        TextView name,qs,code;
        ImageView img;
    }
}
