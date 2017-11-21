package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
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
import com.a8android888.bocforandroid.util.LogTools;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_zxfragment_Adapter extends BaseAdapter implements View.OnClickListener{
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    public List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
    String orderFlag;//订单标识 normal--常规订单
    public static ArrayList<LotterycomitorderBean> mList3=new ArrayList<LotterycomitorderBean>();
    ArrayList<LotterycomitorderBean> recordlist=new ArrayList<LotterycomitorderBean>();

    public Lottery_zxfragment_Adapter(Context context, ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
        this.basefragments=basefragments;
        this.orderFlag=orderFlag;

        if(mList==null)
            mList= new ArrayList<LotterycomitorderBean>();
        else
            mList.clear();

        for (int i = 0; i < basefragments.size(); i++) {
            for (int i1 = 0; i1 < basefragments.get(i).size(); i1++) {
                mList.add(basefragments.get(i).get(i1));
            }
        }
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
    public void notifyStockdatalist(ArrayList<ArrayList<LotterycomitorderBean>> basefragments) {
        if (basefragments == null || basefragments.size() == 0) {
            mList.clear();
        } else {
            ArrayList<LotterycomitorderBean> mList2 = new ArrayList<LotterycomitorderBean>();
            for (int i = 0; i < basefragments.size(); i++) {
                for (int i1 = 0; i1 < basefragments.get(i).size(); i1++) {
                    mList2.add(basefragments.get(i).get(i1));
                }
            }
            if(mList!=null && mList.size()>0)
            {
                for (int i = 0; i < mList.size(); i++) {
                    mList2.get(i).setSelected(mList.get(i).getSelected());
                }
            }

            this.mList = mList2;
        }

        notifyDataSetChanged();
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
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
           ViewHolder holder;
     final    int index=position;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_zxtmfragement, null);

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
        if(mList.get(position).getPl().equalsIgnoreCase("0")){
            holder. peilv.setTextSize(13);
            holder.peilv.setText("请选择");}
        else{
            holder.peilv.setText(mList.get(position).getPl());}
//        holder.peilv.setText(mList.get(position).getPl());
        holder.number.setText(mList.get(position).getNumber());
        if (mList.get(position).getNumber().indexOf("红波") != -1) {
            holder.number.setTextColor(mContext.getResources().getColor(R.color.lottery));
        }
        else if (mList.get(position).getNumber().indexOf("绿波") != -1) {
            holder.number.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        else  if (mList.get(position).getNumber().indexOf("蓝波") != -1) {
            holder.number.setTextColor(mContext.getResources().getColor(R.color.lotteryblue));
        }
        else
            holder.number.setTextColor(mContext.getResources().getColor(R.color.black));
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
    class ViewHolder {
        TextView number;
        CheckBox peilv;
        RadioGroup group;
        LinearLayout layout;
    }

}
