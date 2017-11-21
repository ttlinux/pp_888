package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.Bean.LotteryorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class lotteryorderAdapter extends BaseExpandableListAdapter {

    private Context context;
    ArrayList<LotteryorderBean> mList;

    public lotteryorderAdapter(Context context,ArrayList<LotteryorderBean> list)
    {
        this.context=context;
        this.mList=list;
    }
    public void NotifyAdapter(ArrayList<LotteryorderBean> list)
    {
        this.mList = list;
        notifyDataSetChanged();
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolderitem holderd;
        if (convertView== null) {
            holderd = new ViewHolderitem();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_lotteryorder_layout, null);
            convertView.setTag(holderd);
            holderd.bettime = (TextView) convertView.findViewById(R.id.bettime);
            holderd.itemname = (TextView) convertView.findViewById(R.id.itemname);
            holderd.qs = (TextView) convertView.findViewById(R.id.qs);
            holderd.betnumber = (TextView) convertView.findViewById(R.id.betnumber);
            holderd.rate = (TextView) convertView.findViewById(R.id.rate);
            holderd.betmoney = (TextView) convertView.findViewById(R.id.betmoney);
            holderd.kyje = (TextView) convertView.findViewById(R.id.kyje);
            holderd.backwatermoney = (TextView) convertView.findViewById(R.id.backwatermoney);
            holderd.orderstatus = (TextView) convertView.findViewById(R.id.orderstatus);
            holderd.opencode = (TextView) convertView.findViewById(R.id.opencode);
        }   else{
            holderd= (ViewHolderitem) convertView.getTag();
        }

        holderd.bettime.setText(mList.get(groupPosition).getBetTime());

        holderd.itemname.setText(mList.get(groupPosition).getItemName());

        holderd.qs.setText(mList.get(groupPosition).getQs());

        holderd.betnumber.setText(mList.get(groupPosition).getBetNumber());

        holderd.rate.setText(mList.get(groupPosition).getRate());

        holderd.betmoney.setText(mList.get(groupPosition).getBetMoney());

        holderd.kyje.setText(mList.get(groupPosition).getKyje());

        holderd.backwatermoney.setText(mList.get(groupPosition).getBackWaterMoney());

        holderd.orderstatus.setText(mList.get(groupPosition).getOrderStatus());

        holderd.opencode.setText(mList.get(groupPosition).getOpenCode());
        return convertView;
    }
    // 返回组的个数
    @Override
    public int getGroupCount() {
        return mList.size();
    }
    //返回每组中items的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
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
        final ViewHolder holder;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_lotteryorder_maintitle, null);
            convertView.setTag(holder);
        }   else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.img = (ImageView) convertView.findViewById(R.id.img);
        if(mList.get(groupPosition).getOrderStatus().equalsIgnoreCase("未开奖")){
            holder.img.setImageBitmap((Bitmap) BitmapFactory.decodeResource(context.getResources(), R.drawable.dkj));
        }
        if(mList.get(groupPosition).getOrderStatus().equalsIgnoreCase("已中奖")){
            holder.img.setImageBitmap((Bitmap) BitmapFactory.decodeResource(context.getResources(), R.drawable.zj));
        }
        if(mList.get(groupPosition).getOrderStatus().equalsIgnoreCase("未中奖")){
            holder.img.setImageBitmap((Bitmap) BitmapFactory.decodeResource(context.getResources(), R.drawable.wzj));
        }
        if(mList.get(groupPosition).getOrderStatus().equalsIgnoreCase("取消")){
            holder.img.setImageBitmap((Bitmap) BitmapFactory.decodeResource(context.getResources(), R.drawable.qx));
        }
        holder.orderesult= (TextView) convertView.findViewById(R.id.orderesult);
        holder.lotteryname = (TextView) convertView.findViewById(R.id.lotteryname);
        holder.lotteryname.setText(mList.get(groupPosition).getGameName());
        holder.qstitle = (TextView) convertView.findViewById(R.id.qstitle);
        holder.qstitle.setText(mList.get(groupPosition).getQs());
        holder.jinetitle = (TextView) convertView.findViewById(R.id.jinetitle);
        holder.jinetitle.setText(mList.get(groupPosition).getBetMoney()+"元");

        holder.time = (TextView) convertView.findViewById(R.id.textView7);
        holder.time.setText(mList.get(groupPosition).getBetTime().substring(5, 16).replaceAll("-", ".").replaceAll(" ", "-"));
        holder.time.setTextColor(context.getResources().getColor(R.color.gray5));

        float betusrwin=0;
        try {
            betusrwin=Float.valueOf(mList.get(groupPosition).getBetUsrWin());
            holder.orderesult.setTextColor(betusrwin<0?0xffcf0000:0xff018730);
            holder.orderesult.setText(mList.get(groupPosition).getBetUsrWin());
        }
        catch (Exception except)
        {
            LogTools.e("数据出错",except.getMessage().toString());
        }

        holder.imageView3 = (ImageView) convertView.findViewById(R.id.imageView3);
        if(isExpanded)
        {
            holder.imageView3.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_bot_selected));
        }
        else
        {
            holder.imageView3.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_right_selected));
        }
        return convertView;
    }
    class ViewHolder {
        TextView lotteryname, qstitle,jinetitle,time,orderesult;
        ImageView img,imageView3;
    }
    class ViewHolderitem {
        TextView bettime, itemname,qs,betnumber,rate,betmoney,kyje,backwatermoney,orderstatus,opencode;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



}
