package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.Bean.PaymentBean;
import com.a8android888.bocforandroid.Bean.PaymentBean3;
import com.a8android888.bocforandroid.Bean.QueryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.Query.QueryDetailActivity;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.view.TriangleView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */
public class QueryDetailAdapter extends BaseAdapter{

    private ArrayList<QueryBean> queryBeans=new ArrayList<QueryBean>();
    private Context context;
    Bitmap right,bottom;
    private int imageresoures[]={R.drawable.success,R.drawable.fail,R.drawable.authing};

    public QueryDetailAdapter(Context context,ArrayList<QueryBean> queryBeans)
    {
        this.context=context;
        this.queryBeans=queryBeans;
        int width= ScreenUtils.getDIP2PX(context, 10);
        int color=context.getResources().getColor(R.color.gray4);
        right= TriangleView.createBitmap(width, width, color, TriangleView.Right);
        bottom=TriangleView.createBitmap(width, width, color, TriangleView.Bottom);
    }

    public void NotifyData(ArrayList<QueryBean> queryBeans)
    {
        this.queryBeans=queryBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return queryBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null)
        {
            convertView=View.inflate(context, R.layout.item_payment3,null);
            holder=new ViewHolder();
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.triangle=(ImageView)convertView.findViewById(R.id.triangle);
        holder.bottomview=(RelativeLayout)convertView.findViewById(R.id.bottomview);
        holder.topview=(LinearLayout)convertView.findViewById(R.id.topview);
        holder.detail=(TextView)convertView.findViewById(R.id.detail);



        if(queryBeans.get(position).getItemstate())
        {
            holder.triangle.setImageBitmap(bottom);
            holder.bottomview.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.triangle.setImageBitmap(right);
            holder.bottomview.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView triangle = (ImageView) v.findViewById(R.id.triangle);
                if (queryBeans.get(position).getItemstate()) {
                    triangle.setImageBitmap(right);
                    queryBeans.get(position).setItemstate(false);
                    v.findViewById(R.id.bottomview).setVisibility(View.GONE);
                } else {
                    triangle.setImageBitmap(bottom);
                    queryBeans.get(position).setItemstate(true);
                    v.findViewById(R.id.bottomview).setVisibility(View.VISIBLE);
                }

            }
        });
        QueryBean paymentBean= queryBeans.get(position);
        TextView time=(TextView) holder.topview.getChildAt(0);
        time.setText(paymentBean.getBetTime().replace(" ","\n"));

        TextView betsum=(TextView) holder.topview.getChildAt(1);
        betsum.setText(paymentBean.getBetIn()+"");

        TextView validsum=(TextView) holder.topview.getChildAt(2);
        validsum.setText(paymentBean.getBetIncome());

        TextView betresult=(TextView) holder.topview.findViewById(R.id.betwin);
        betresult.setTextColor(paymentBean.getBetUsrWin() < 0 ? 0xffcf0000 : 0xff018730);
        betresult.setText(paymentBean.getBetUsrWin()+"");

        if(paymentBean.getDetails()==null) return convertView;
        holder.detail.setText("");
        for (int i = 0; i <paymentBean.getDetails().size(); i++) {
            QueryBean.detail detailbean= paymentBean.getDetails().get(i);
            holder.detail.append(detailbean.getKey() + ": ");
            try
            {
                ForegroundColorSpan redSpan1 = new ForegroundColorSpan(Integer.valueOf(detailbean.getColor(),16) | 0x00000000ff000000);
                SpannableStringBuilder builder = new SpannableStringBuilder(detailbean.getValue());
                builder.setSpan(redSpan1, 0, detailbean.getValue().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.detail.append(builder);
                holder.detail.append("\n");
            }
            catch (Exception except)
            {
                holder.detail.append("\n");
                continue;
            }
        }


        return convertView;
    }

    class ViewHolder
    {
        ImageView triangle;
        LinearLayout topview;
        RelativeLayout bottomview;
        TextView detail;
    }
}
