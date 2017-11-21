package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.Gravity;
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
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_yxztwtmfragment_Adapter extends BaseAdapter implements View.OnClickListener{
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    public List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
    String orderFlag;//订单标识 normal--常规订单
    SparseArray<View> linearrs=new SparseArray<View>();
    public static ArrayList<LotterycomitorderBean> mList3=new ArrayList<LotterycomitorderBean>();
    public Lottery_yxztwtmfragment_Adapter(Context context,ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
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

        if(basefragments==null || basefragments.size()<1)
        {
            mList.clear();
        }
        else
        {
            ArrayList<LotterycomitorderBean> mList2=new ArrayList<LotterycomitorderBean>();
            for (int i = 0; i < basefragments.size(); i++) {
                for (int i1 = 0; i1 <basefragments.get(i).size() ; i1++) {
                    LotterycomitorderBean bean=basefragments.get(i).get(i1);
                    mList2.add(bean);
                }
            }
            if(mList!=null && mList.size()>0)
            {
                for (int i = 0; i < mList2.size(); i++) {
                    mList2.get(i).setSelected(mList.get(i).getSelected());
                }
            }
            mList=(ArrayList<LotterycomitorderBean>)mList2.clone();
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
                    R.layout.item_yxztwtmfragement, null);
            convertView.setTag(holder);
            linearrs.put(position,convertView);

        }   else{
            convertView=linearrs.get(position);
            holder= (ViewHolder) convertView.getTag();
        }
        holder.number = (TextView) convertView.findViewById(R.id.number);
        holder.peilv = (CheckBox) convertView.findViewById(R.id.peilv);
        holder.group = (RadioGroup) convertView.findViewById(R.id.grouplayout);

        if (position == 0 ) {
            holder.group.setVisibility(View.VISIBLE);
        } else {
            holder.group.setVisibility(View.GONE);
        }

        holder.code=(LinearLayout)convertView.findViewById(R.id.code);
        if(holder.code.getChildCount()==0) {
            AddBtn(holder.code, mList.get(index).getDatacode().size(), index);
        }

        holder.peilv.setTag(index);
        holder.peilv .setOnClickListener(this);
        holder.peilv.setChecked(mList.get(position).getSelected());
        holder.peilv.setText(mList.get(position).getPl());
        holder.number.setText(mList.get(position).getNumber());

        if(Httputils.isNumber(mList.get(position).getNumber())) {
            holder.number.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            if (mList.get(position).getNumber().indexOf("红波") != -1) {
                holder.number.setTextColor(mContext.getResources().getColor(R.color.lottery));
            }
            else  if (mList.get(position).getNumber().indexOf("绿波") != -1) {
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
    private void  AddBtn(LinearLayout layout ,int sizeint,final int index) {
        LogTools.e("数据是多少" + index, sizeint + "");
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
//        if(mList.get(index).getSelected())
//        {
//            buttonView.setBackgroundColor(Color.rgb(212, 34, 66));
//        }
//        else
//        {
//            buttonView.setBackground(mContext.getResources().getDrawable(R.drawable.lottery_fragment_chobxbtn));
//        }
    }
    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        ArrayList<LotterycomitorderBean> beans=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i < mList.size(); i++) {
            if(mList.get(i).getSelected())
                beans.add(mList.get(i));
        }
        LogTools.e("beans", new Gson().toJson(beans));

        return beans;
    }


    public void ResetState()
    {
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setSelected(false);
        }
        linearrs.clear();
        notifyDataSetChanged();
    }


    class ViewHolder {
        TextView number;
        CheckBox peilv;
        RadioGroup group;
        LinearLayout code;
    }

}
