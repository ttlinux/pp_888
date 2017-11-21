package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lottery_tmfragment_Adapter extends BaseAdapter implements View.OnClickListener{
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    public List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
    ArrayList<LotterycomitorderBean> recordlist=new ArrayList<LotterycomitorderBean>();
    String orderFlag;//订单标识 normal--常规订单
    public static ArrayList<LotterycomitorderBean> mList3=new ArrayList<LotterycomitorderBean>();
    int exactlycount;
    public Lottery_tmfragment_Adapter(Context context,ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
        this.orderFlag=orderFlag;
        if(mList==null)
            mList=new ArrayList<LotterycomitorderBean>();
        else
            mList.clear();

        for (int i = 0; i < basefragments.size(); i++) {
            for (int i1 = 0; i1 <basefragments.get(i).size() ; i1++) {
                mList.add(basefragments.get(i).get(i1))    ;
            }
        }
//        if(mList.size()>0 &&  mList.size()%2>0)
//        {
//            exactlycount=mList.size();
//            mList.add(new LotterycomitorderBean());
//        }
    }


    public int getCount() {
        // TODO Auto-generated method stub
//        int count=mList.size()%2;
//        return count==0?mList.size():1+mList.size();
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
    public void notifyStockdatalist(ArrayList<ArrayList<LotterycomitorderBean>> basefragments) {
        if(basefragments==null || basefragments.size()==0)
        {
            mList.clear();
        }
        else
        {
            ArrayList<LotterycomitorderBean> mList2=new ArrayList<LotterycomitorderBean>();
            for (int i = 0; i < basefragments.size(); i++) {
                for (int i1 = 0; i1 <basefragments.get(i).size() ; i1++) {
                    mList2.add(basefragments.get(i).get(i1));
                }

            }
            if(mList!=null && mList.size()>0)
            {
                for (int i = 0; i <mList2.size() ; i++) {
                    mList2.get(i).setSelected(mList.get(i).getSelected());
                }
            }
            this.mList=(ArrayList<LotterycomitorderBean>)mList2.clone();
        }
//        if(mList.size()>0 && mList.size()%2>0)
//        {
//            exactlycount=mList.size();
//            mList.add(new LotterycomitorderBean());
//        }
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final    int index=position;
        if (convertView== null) {
            LogTools.e("position", position+"");
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tmfragement, null);
            convertView.setTag(holder);
        }   else{
            holder= (ViewHolder) convertView.getTag();
        }
//        LinearLayout linearLayout=(LinearLayout)((LinearLayout)((LinearLayout)convertView).getChildAt(0)).getChildAt(1);
//        if(position>=exactlycount)
//        {
//            LogTools.e("exactlycount", exactlycount+"");
//            linearLayout.setVisibility(View.INVISIBLE);
//            return convertView;
//        }
//        else
//        {
//            linearLayout.setVisibility(View.VISIBLE);
//        }

//        if(position>=mList.size())
//        {
//            LinearLayout ll=(LinearLayout)convertView;
//            for (int i = 0; i <ll.getChildCount(); i++) {
//                View view=ll.getChildAt(i);
//                view.setVisibility(View.INVISIBLE);
//            }
//        }  else {
//            LinearLayout ll = (LinearLayout) convertView;
//            for (int i = 0; i < ll.getChildCount(); i++) {
//                View view = ll.getChildAt(i);
//                view.setVisibility(View.VISIBLE);
//            }
            holder.number = (TextView) convertView.findViewById(R.id.number);
            holder.peilv = (CheckBox) convertView.findViewById(R.id.peilv);
            holder.group = (RadioGroup) convertView.findViewById(R.id.grouplayout);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
        if(orderFlag.equalsIgnoreCase("txtw")){
            holder.group.setVisibility(View.GONE);
        }
        else {
            if (position == 0 || position == 1) {
                holder.group.setVisibility(View.VISIBLE);
            } else {
                holder.group.setVisibility(View.GONE);
            }
        }
            holder.peilv.setTag(index);
            holder.peilv.setOnClickListener(this);
            holder.peilv.setChecked(mList.get(position).getSelected());
            holder.peilv.setText(mList.get(position).getPl());
            holder.number.setText(mList.get(position).getNumber());
            if (Httputils.isNumber(mList.get(position).getNumber())) {
                holder.number.setTextColor(mContext.getResources().getColor(R.color.white));
                if (Httputils.useLoop(Httputils.lotterry6red, Integer.valueOf(mList.get(position).getNumber()))) {
                    holder.number.setBackgroundResource((R.drawable.lottery_red));
                }
                if (Httputils.useLoop(Httputils.lotterry6green, Integer.valueOf(mList.get(position).getNumber()))) {
                    holder.number.setBackgroundResource((R.drawable.lottery_green));
                }
                if (Httputils.useLoop(Httputils.lotterry6blue, Integer.valueOf(mList.get(position).getNumber()))) {
                    holder.number.setBackgroundResource((R.drawable.lottery_blue));
                }

            } else {
                holder.number.setBackgroundColor(mContext.getResources().getColor(R.color.userbagcolor));
                if (mList.get(position).getNumber().indexOf("红波") != -1) {
                    holder.number.setTextColor(mContext.getResources().getColor(R.color.lottery));
                } else if (mList.get(position).getNumber().indexOf("绿波") != -1) {
                    holder.number.setTextColor(mContext.getResources().getColor(R.color.green));
                } else if (mList.get(position).getNumber().indexOf("蓝波") != -1) {
                    holder.number.setTextColor(mContext.getResources().getColor(R.color.lotteryblue));
                } else
                    holder.number.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        return convertView;
    }

    @Override
    public void onClick(View buttonView) {
//        buttonView.getTag();
//        int index = Integer.valueOf(buttonView.getTag()+"");
//        if (buttonView.isSelected()==false) {
//            mList.get(index).setSelected(true);
//            JSONObject jsonObject= new JSONObject();
//            try {
//                jsonObject.put("id",mList.get(index).getUid());
//                jsonObject.put("pl",mList.get(index).getPl());
//                jsonObject.put("number", mList.get(index).getNumber());
//                jsonObject.put("xzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            jsonObjects.add(jsonObject);
//
//            LogTools.e("选择的是", jsonObjects.get(0).optString("number"));
//        }
//        else {
//            LogTools.e("选择的是", jsonObjects.get(0).optString("number"));
//            mList.get(index).setSelected(false);
//            jsonObjects.remove(index);
//
//        }
        CheckBox box=(CheckBox)buttonView;
        int index = Integer.valueOf(buttonView.getTag() + "");
        if (box.isChecked() == true) {
            mList.get(index).setSelected(true);
            recordlist.add(mList.get(index));

            LogTools.e("选择的是" + "pos" + index, new Gson().toJson(mList.get(index)));
        } else {
            LogTools.e("选删除的是"  + "pos" + index, new Gson().toJson(mList.get(index)));
            mList.get(index).setSelected(false);
            recordlist.remove(mList.get(index));

        }
    }
    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        LogTools.e("GetSelectItem", new Gson().toJson(recordlist));
        return recordlist;
    }

    public void ResetState()
    {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(false);
        }
        recordlist.clear();
        notifyDataSetChanged();
    }

    public void ClearRecordData()
    {
        recordlist.clear();
    }

    class ViewHolder {
        TextView number;
        CheckBox peilv;
        RadioGroup group;
        LinearLayout layout;
    }

}
