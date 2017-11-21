package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.util.SparseArray;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_dwfragment_Adapter extends BaseAdapter implements View.OnClickListener{
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    public ArrayList<LotterycomitorderBean> jsonObjects = new ArrayList<LotterycomitorderBean>();
    String orderFlag;//订单标识 normal--常规订单
    public static ArrayList<LotterycomitorderBean> mList3=new ArrayList<LotterycomitorderBean>();
    public Lottery_dwfragment_Adapter(Context context,ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
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

    public int getCount() {
        // TODO Auto-generated method stub
//        int count=0;
//        ArrayList<LotterycomitorderBean> mList2=new ArrayList<LotterycomitorderBean>();
//        for (int i = 0; i < basefragments.size(); i++) {
//            count=count+basefragments.get(i).size();
//            for (int i1 = 0; i1 <basefragments.get(i).size() ; i1++) {
//                mList2.add(basefragments.get(i).get(i1))    ;
//            }
//
//        }
////        for (int i = 0; i < basefragments2.size(); i++) {
////            count=count+basefragments2.get(i).size();
////        }
//        this.mList=mList2;
//        LogTools.e("数据定位", count + "");
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
        holder.number.setTextColor(mContext.getResources().getColor(R.color.black));
        return convertView;
    }


    @Override
    public void onClick(View buttonView) {
        buttonView.getTag();
        int index = Integer.valueOf(buttonView.getTag()+"");
        if (buttonView.isSelected()==false) {
            mList.get(index).setSelected(true);
//            JSONObject jsonObject= new JSONObject();
//            try {
//                jsonObject.put("id",mList.get(index).getUid());
//                jsonObject.put("pl",mList.get(index).getPl());
//                jsonObject.put("number", mList.get(index).getNumber());
//                jsonObject.put("xzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            jsonObjects.add(mList.get(index));

//            LogTools.e("选择的是", jsonObjects.get(0).optString("number"));
        }
        else {
//            LogTools.e("选择的是", jsonObjects.get(0).optString("number"));
            mList.get(index).setSelected(false);
            jsonObjects.remove( mList.get(index));


        }
    }

    public void ResetState()
    {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(false);
        }
        jsonObjects.clear();
        notifyDataSetChanged();
    }
    public void ClearRecordData()
    {
        jsonObjects.clear();
    }
    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        return jsonObjects;
    }

    class ViewHolder {
        TextView number;
        CheckBox peilv;
        RadioGroup group;
        LinearLayout layout;
    }

}
