package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class Lottery_pkfragment_Adapter extends BaseAdapter {
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    String orderFlag;//订单标识 normal--常规订单

    public Lottery_pkfragment_Adapter(Context context, ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
        this.orderFlag = orderFlag;

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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int index = position;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tmfragement, null);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.number = (TextView) convertView.findViewById(R.id.number);
        holder.peilv = (CheckBox) convertView.findViewById(R.id.peilv);
        holder.group = (RadioGroup) convertView.findViewById(R.id.grouplayout);
        holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
        if (position == 0 || position == 1) {
            holder.group.setVisibility(View.VISIBLE);
        } else {
            holder.group.setVisibility(View.GONE);
        }
        holder.peilv.setTag(index);
        holder.peilv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int tag = Integer.valueOf(buttonView.getTag() + "");
                mList.get(tag).setSelected(isChecked);
            }
        });
        holder.peilv.setChecked(mList.get(position).getSelected());
        holder.peilv.setText(mList.get(position).getPl());
        holder.number.setText(mList.get(position).getNumber());
        if(mList.size()<11) {
            if (Httputils.isNumber(mList.get(position).getNumber())) {
                if (Integer.valueOf(mList.get(position).getNumber()) == 1) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk1));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 2) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk2));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 3) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk3));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 4) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk4));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 5) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk5));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 6) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk6));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 7) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk7));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 8) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk8));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 9) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk9));
                }
                if (Integer.valueOf(mList.get(position).getNumber()) == 10) {
                    holder.number.setBackgroundResource((R.drawable.lottery_pk10));
                }
            }
            holder.number.setTextColor(mContext.getResources().getColor(R.color.white));
        }
        if(!Httputils.isNumber(mList.get(position).getNumber())) {
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
        }
        return convertView;
    }



    public class ViewHolder {
        TextView number;
        CheckBox peilv;
        RadioGroup group;
        LinearLayout layout;
    }

    public void ResetState()
    {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }

    public ArrayList<LotterycomitorderBean> getSelectedData()
    {
        ArrayList<LotterycomitorderBean> lotts=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i <mList.size() ; i++) {
            if(mList.get(i).getSelected())
            lotts.add(mList.get(i));
        }
        return lotts;
    }
}
