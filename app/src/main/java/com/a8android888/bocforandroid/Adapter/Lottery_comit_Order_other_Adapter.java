package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Lottery_comit_Order_other_Adapter extends BaseAdapter{

    private Context context;
    public  ArrayList<LotterycomitorderBean> mList;
    Bitmap right,bottom;
    String success,fail;

    public Lottery_comit_Order_other_Adapter(Context context, ArrayList<LotterycomitorderBean> mList)
    {
        this.context=context;
        this.mList=mList;
    }

//    public void NotifyData(ArrayList<PaymentBean> payments)
//    {
//        this.payments=payments;
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            convertView=View.inflate(context, R.layout.item_lottery_comit_order_other,null);
            holder=new ViewHolder();
            holder.number=(TextView)convertView.findViewById(R.id.number);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.number2=(TextView)convertView.findViewById(R.id.number2);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.number.setText(mList.get(position).getNumber());
        SpannableStringBuilder builder3;
        String text2 = "赔率: " + mList.get(position).getPl();
        builder3 = new SpannableStringBuilder(text2);
        ForegroundColorSpan redSpan3 = new ForegroundColorSpan(context.getResources().getColor(R.color.red));
        builder3.setSpan(redSpan3, 4, text2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.number2.setText(builder3);
        holder.name.setText(mList.get(position).getItemtname());
        return convertView;
    }
    class ViewHolder
    {
        TextView name,number,number2;
    }
}
