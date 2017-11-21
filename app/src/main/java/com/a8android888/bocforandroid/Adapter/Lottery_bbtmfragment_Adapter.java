package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_bbtmfragment_Adapter extends BaseAdapter implements View.OnClickListener{
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    public List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
    SparseArray<View> linearrs=new SparseArray<View>();
    String orderFlag;//订单标识 normal--常规订单
    public static ArrayList<LotterycomitorderBean> mList3=new ArrayList<LotterycomitorderBean>();
    public Lottery_bbtmfragment_Adapter(Context context, ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
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

            this.mList =(ArrayList<LotterycomitorderBean>)mList2.clone();
        }
        linearrs.clear();
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
           ViewHolder holder;
     final    int index=position;
        if (linearrs.get(position)== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_bbtmfragement, null);
            linearrs.put(position, convertView);
            holder.code=(LinearLayout)convertView.findViewById(R.id.code);
            if(holder.code.getChildCount()==0) {
                holder.code.removeAllViews();
                AddBtn(holder.code, mList.get(index).getDatacode().size(), index);
            }
            convertView.setTag(holder);
        }   else{
            convertView=linearrs.get(position);
            holder= (ViewHolder) convertView.getTag();
        }

        holder.layout=(LinearLayout)convertView.findViewById(R.id.layout);
        holder.layout.setOnClickListener(this);
        holder.layout.setTag(position);
        int color=mList.get(index).getSelected()?0x99D42242:0;
        View view=(View)holder.layout.findViewById(R.id.relayout);
        view.setBackgroundColor(color);

        holder.number = (TextView) convertView.findViewById(R.id.number);
        holder.peilv = (TextView) convertView.findViewById(R.id.peilv);



        holder.peilv.setText(mList.get(position).getPl());
        holder.number.setText(mList.get(position).getNumber());
            if (mList.get(position).getNumber().indexOf("红") != -1) {
                holder.number.setTextColor(mContext.getResources().getColor(R.color.lottery));
            }
        if (mList.get(position).getNumber().indexOf("绿") != -1) {
                holder.number.setTextColor(mContext.getResources().getColor(R.color.green));
            }
        if (mList.get(position).getNumber().indexOf("蓝") != -1) {
                holder.number.setTextColor(mContext.getResources().getColor(R.color.lotteryblue));
            }


//        holder.code
        return convertView;
    }
    private void  AddBtn(LinearLayout layout ,int sizeint,final int index) {
        LogTools.e("数据是多少"+index,sizeint+"");
        for (int i = 0; i < sizeint; i++) {
            TextView imageView = new TextView(mContext);
            LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(mContext, 30), ScreenUtils.getDIP2PX(mContext, 30), Gravity.CENTER);
            params12.setMargins(5, 5, 5, 5);
            params12.gravity = Gravity.CENTER;
            imageView.setLayoutParams(params12);
            imageView.setGravity(Gravity.CENTER);
            imageView.setPadding(5, 5, 5, 5);
            if (Httputils.useLoop(Httputils.lotterry6red, Integer.valueOf(mList.get(index).getDatacode().get(i)))) {
                imageView.setBackgroundResource((R.drawable.lottery_red));
            }
            if (Httputils.useLoop(Httputils.lotterry6green, Integer.valueOf(mList.get(index).getDatacode().get(i)))) {
                imageView.setBackgroundResource((R.drawable.lottery_green));
            }
            if (Httputils.useLoop(Httputils.lotterry6blue, Integer.valueOf(mList.get(index).getDatacode().get(i)))) {
                imageView.setBackgroundResource((R.drawable.lottery_blue));
            }
            imageView.setText(mList.get(index).getDatacode().get(i));
            imageView.setTextColor(mContext.getResources().getColor(R.color.white));
            layout.addView(imageView);
        }
    }
    @Override
    public void onClick(View buttonView) {

        int index = Integer.valueOf(buttonView.getTag()+"");
        mList.get(index).setSelected(!mList.get(index).getSelected());
        View view=(View)buttonView.findViewById(R.id.relayout);
        if(mList.get(index).getSelected())
        {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.line));

        }
        else
        {
            view.setBackgroundColor(0);
        }
    }

    class ViewHolder {
        TextView number;
        TextView peilv;
        LinearLayout code;
        LinearLayout layout;
    }

    public void ResetState()
    {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(false);
        }
        linearrs.clear();
        notifyDataSetChanged();
    }
    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        ArrayList<LotterycomitorderBean> beans=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).getSelected())
                beans.add(mList.get(i));
        }
        LogTools.e("beans",new Gson().toJson(beans));
        return beans;
    }
}
