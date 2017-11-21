package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.content.Intent;
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

public class Lottery_wls_mfragment_Adapter extends BaseAdapter implements View.OnClickListener{
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    public List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
    String orderFlag;//订单标识 normal--常规订单
    public static ArrayList<LotterycomitorderBean> mList3=new ArrayList<LotterycomitorderBean>();
    public Lottery_wls_mfragment_Adapter(Context context,ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
        this.basefragments=basefragments;
        this.orderFlag=orderFlag;
    }


    public int getCount() {
        // TODO Auto-generated method stub
        int count=0;
        ArrayList<LotterycomitorderBean> mList2=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i < basefragments.size(); i++) {
            count=count+basefragments.get(i).size();
            for (int i1 = 0; i1 <basefragments.get(i).size() ; i1++) {
                mList2.add(basefragments.get(i).get(i1))    ;
            }

        }
//        for (int i = 0; i < basefragments2.size(); i++) {
//            count=count+basefragments2.get(i).size();
//        }
        this.mList=mList2;
        LogTools.e("数据2", count + "");
        return count;
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
        this.basefragments=basefragments;
        ArrayList<LotterycomitorderBean> mList2=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i < basefragments.size(); i++) {
            for (int i1 = 0; i1 <basefragments.get(i).size() ; i1++) {
                mList2.add(basefragments.get(i).get(i1));
            }

        }
        this.mList=mList2;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final    int index=position;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tmfragement, null);

            convertView.setTag(holder);

        }   else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.number = (TextView) convertView.findViewById(R.id.number);
        holder.peilv = (CheckBox) convertView.findViewById(R.id.peilv);
        holder.group = (RadioGroup) convertView.findViewById(R.id.grouplayout);
        holder.layout=(LinearLayout)convertView.findViewById(R.id.layout);
        if (position == 0 || position == 1) {
            holder.group.setVisibility(View.VISIBLE);
        } else {
            holder.group.setVisibility(View.GONE);
        }
        holder.peilv.setTag(index);
        holder.peilv .setOnClickListener(this);
        holder.peilv.setChecked(mList.get(position).getSelected());
        holder.peilv.setText(mList.get(position).getPl());
        holder.number.setText(mList.get(position).getNumber());
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
            holder.number.setBackgroundColor(mContext.getResources().getColor(R.color.lotteryfragmenttitle));
        }
//        if(position==49){
//            holder.layout.setVisibility(View.GONE);
//        }

        return convertView;
    }

//    @Override
//    public void ON(CompoundButton buttonView, boolean isChecked) {
//        buttonView.getTag();
//            int index = Integer.valueOf(buttonView.getTag()+"");
//            if (buttonView.isChecked()==true) {
//                mList.get(index).setSelected(true);
////                LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
////                gameBean1.setUid(mList.get(index).getUid());
////                gameBean1.setPl(mList.get(index).getPl());
////                gameBean1.setNumber(mList.get(index).getNumber());
//                //                mList3.add(gameBean1);
//                JSONObject jsonObject= new JSONObject();
//                try {
//                    jsonObject.put("id",mList.get(index).getUid());
//                    jsonObject.put("pl",mList.get(index).getPl());
//                    jsonObject.put("number", mList.get(index).getNumber());
//                    jsonObject.put("xzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                jsonObjects.add(jsonObject);
//
//                LogTools.e("选择的是", jsonObjects.get(0).optString("number"));
//            }
//            else {
//                LogTools.e("选择的是", jsonObjects.get(0).optString("number"));
//                mList.get(index).setSelected(false);
//                jsonObjects.remove(index);
////                LogTools.e("选择的不是", mList3.get(0).getNumber());
//
//
//            }
//        }

    @Override
    public void onClick(View buttonView) {
        buttonView.getTag();
        int index = Integer.valueOf(buttonView.getTag()+"");
        if (buttonView.isSelected()==false) {
            mList.get(index).setSelected(true);
//                LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
//                gameBean1.setUid(mList.get(index).getUid());
//                gameBean1.setPl(mList.get(index).getPl());
//                gameBean1.setNumber(mList.get(index).getNumber());
            //                mList3.add(gameBean1);
            JSONObject jsonObject= new JSONObject();
            try {
                jsonObject.put("id",mList.get(index).getUid());
                jsonObject.put("pl",mList.get(index).getPl());
                jsonObject.put("number", mList.get(index).getNumber());
                jsonObject.put("xzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonObjects.add(jsonObject);

            LogTools.e("选择的是", jsonObjects.get(0).optString("number"));
        }
        else {
            LogTools.e("选择的是", jsonObjects.get(0).optString("number"));
            mList.get(index).setSelected(false);
            jsonObjects.remove(index);
//                LogTools.e("选择的不是", mList3.get(0).getNumber());


        }
    }

    class ViewHolder {
        TextView number;
        CheckBox peilv;
        RadioGroup group;
        LinearLayout layout;
    }

}
