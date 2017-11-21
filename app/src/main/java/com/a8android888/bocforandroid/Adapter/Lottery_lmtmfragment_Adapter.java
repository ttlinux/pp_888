package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_lmtmfragment_Adapter extends BaseAdapter implements View.OnClickListener{
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    ArrayList<LotterycomitorderBean> mList_REcord=new ArrayList<LotterycomitorderBean>();
    String orderFlag;//订单标识 normal--常规订单
    int white,lotteryfragmenttitle2;

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    OnSelectListener onSelectListener;

    public Lottery_lmtmfragment_Adapter(Context context) {
        this.mContext = context;

        if(mList==null)
            mList=new ArrayList<LotterycomitorderBean>();
        else
            mList.clear();
        for (int i = 1; i < 50; i++) {
            LotterycomitorderBean bean=new LotterycomitorderBean();
            bean.setNumber(i+"");
            bean.setSelected(false);
            mList.add(bean);
        }

        white=mContext.getResources().getColor(R.color.white);
        lotteryfragmenttitle2=mContext.getResources().getColor(R.color.lotteryfragmenttitle2);
//        if(mList==null)
//            mList=new ArrayList<LotterycomitorderBean>();
//        else
//            mList.clear();
//
//        for (int i = 0; i < basefragments.size(); i++) {
//            for (int i1 = 0; i1 <basefragments.get(i).size() ; i1++) {
//                mList.add(basefragments.get(i).get(i1))    ;
//            }
//        }
    }

//    public void notifyAdapter(ArrayList<ArrayList<LotterycomitorderBean>> basefragments)
//    {
//        if(basefragments==null || basefragments.size()<1)
//        {
//            mList.clear();
//        }
//        else
//        {
//            ArrayList<LotterycomitorderBean> mList2=new ArrayList<LotterycomitorderBean>();
//            for (int i = 0; i < basefragments.size(); i++) {
//                for (int i1 = 0; i1 <basefragments.get(i).size() ; i1++) {
//                    mList2.add(basefragments.get(i).get(i1))    ;
//                }
//            }
//            if(mList!=null && mList.size()>0)
//            for (int i = 0; i < mList.size(); i++) {
//                mList2.get(i).setSelected(mList.get(i).getSelected());
//            }
//            mList=(ArrayList<LotterycomitorderBean>)mList2.clone();
//        }
//
//        notifyDataSetChanged();
//    }

    public int getCount() {
        // TODO Auto-generated method stub
//        }
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
           ViewHolder holder;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_lmtmfragement, null);
            convertView.setTag(holder);
        }   else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.number = (TextView) convertView.findViewById(R.id.number);
        holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
        holder.layout.setTag(position);
        holder.layout.setOnClickListener(this);
        holder.number.setText(mList.get(position).getNumber());

        holder.layout.setBackgroundColor(mList.get(position).getSelected()?lotteryfragmenttitle2:white);
        if(Httputils.isNumber(mList.get(position).getNumber())) {
            if (Httputils.useLoop(Httputils.lotterry6red, Integer.valueOf(mList.get(position).getNumber()))) {
                holder.number.setBackgroundResource((R.drawable.lottery_red));
            }
              if (Httputils.useLoop(Httputils.lotterry6green, Integer.valueOf(mList.get(position).getNumber()))) {
                holder.number.setBackgroundResource((R.drawable.lottery_green));
            }
               if (Httputils.useLoop(Httputils.lotterry6blue, Integer.valueOf(mList.get(position).getNumber()))) {
                holder.number.setBackgroundResource((R.drawable.lottery_blue));
            }

        }
        else {
            holder.number.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        }

        return convertView;
    }

    @Override
    public void onClick(View buttonView) {
        int index = Integer.valueOf(buttonView.getTag() + "");
        mList.get(index).setSelected(!mList.get(index).getSelected());
        boolean selected=mList.get(index).getSelected();
        buttonView.setBackgroundColor(selected ? lotteryfragmenttitle2 : white);
        if(selected)
        {
            mList_REcord.add(mList.get(index));
        }
        else
        {
            mList_REcord.remove(mList.get(index));
        }
        if(onSelectListener!=null)
        {
            onSelectListener.OnSelected(mList_REcord);
        }
//        if(mList.get(index).getSelected())
//        {
//            mList.get(index).setSelected(true);
//            recordlist.add(mList.get(index));
//            buttonView.setBackgroundColor(mContext.getResources().getColor(R.color.lotteryfragmenttitle2));
//
//            LogTools.e("选择的是" + "pos" + index, new Gson().toJson(mList.get(index)));
//        } else {
//            LogTools.e("选删除的是"  + "pos" + index, new Gson().toJson(mList.get(index)));
//            mList.get(index).setSelected(false);
//            recordlist.remove(mList.get(index));
//            buttonView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//
//        }
    }
    public  ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        return mList_REcord;
    }


    public void ResetState()
    {
        for (int i = 0; i <mList.size() ; i++) {
            mList.get(i).setSelected(false);
        }
        mList_REcord.clear();
        notifyDataSetChanged();
    }

    class ViewHolder {
        TextView number;
        LinearLayout layout;
    }

    public interface OnSelectListener
    {
        public void OnSelected(ArrayList<LotterycomitorderBean> beans);
    }
}
