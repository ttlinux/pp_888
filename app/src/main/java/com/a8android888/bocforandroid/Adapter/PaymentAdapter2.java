package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.PaymentBean;
import com.a8android888.bocforandroid.Bean.PaymentBean2;
import com.a8android888.bocforandroid.Bean.PaymentBean3;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.view.TriangleView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1. 提现记录
 */
public class PaymentAdapter2 extends BaseAdapter{


    private Context context;
    private ArrayList<PaymentBean2> payments;
    Bitmap right,bottom;
    String success,fail;
    private int imageresoures[]={R.drawable.success,R.drawable.fail,R.drawable.authing};

    public PaymentAdapter2(Context context,ArrayList<PaymentBean2> payments)
    {
        this.context=context;
        this.payments=payments;
        int width= ScreenUtils.getDIP2PX(context, 10);
        int color=context.getResources().getColor(R.color.gray4);
        right= TriangleView.createBitmap(width, width, color, TriangleView.Right);
        bottom=TriangleView.createBitmap(width, width, color, TriangleView.Bottom);
        success=context.getString(R.string.sucess);
        fail=context.getString(R.string.fail);
    }

    public void NotifyData(ArrayList<PaymentBean2> payments)
    {
        this.payments=payments;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return payments.size();
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
            convertView=View.inflate(context, R.layout.item_payment,null);
            holder=new ViewHolder();
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.firstview=(LinearLayout)convertView.findViewById(R.id.firstview);
        holder.triangle=(ImageView)convertView.findViewById(R.id.triangle);
        holder.state_image=(ImageView)convertView.findViewById(R.id.state_image);
        holder.secondview=(LinearLayout)convertView.findViewById(R.id.secondview);
        holder.bottomview=(RelativeLayout)convertView.findViewById(R.id.bottomview);
        holder.remark_layout=(LinearLayout)convertView.findViewById(R.id.remark_layout);
        holder.layout=(LinearLayout)convertView.findViewById(R.id.layout);
        View botview=convertView.findViewById(R.id.botview);
        botview.setVisibility(View.GONE);
        holder.layout.setVisibility(View.VISIBLE);



        holder.ptitle2=(TextView)convertView.findViewById(R.id.ptitle2);
        holder.state=(TextView)convertView.findViewById(R.id.state);
        holder.paydate=(TextView)convertView.findViewById(R.id.paydate);
        holder.remark_valuse=(TextView)convertView.findViewById(R.id.remark_valuse);
        holder.commitdate=(TextView)convertView.findViewById(R.id.commitdate);
        TextView textView3=(TextView)holder.secondview.getChildAt(0);
        TextView textView4=(TextView)holder.secondview.getChildAt(1);

        holder.ptitle2.setText(context.getString(R.string.withdorawtime));


        LinearLayout linearLayout=(LinearLayout)holder.firstview.getChildAt(0);
        TextView textView1=(TextView)linearLayout.getChildAt(0);//title
        TextView value=(TextView)linearLayout.getChildAt(1);//value
        TextView textView2=(TextView)holder.firstview.getChildAt(1);//订单号
        value.setTextColor(Color.rgb(0, 124, 222));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView triangle = (ImageView) v.findViewById(R.id.triangle);
                if (payments.get(position).getItemstate()) {
                    triangle.setImageBitmap(right);
                    payments.get(position).setItemstate(false);
                    v.findViewById(R.id.bottomview).setVisibility(View.GONE);
                } else {
                    triangle.setImageBitmap(bottom);
                    payments.get(position).setItemstate(true);
                    v.findViewById(R.id.bottomview).setVisibility(View.VISIBLE);
                }

            }
        });

        PaymentBean2 paymentBean= payments.get(position);
        value.setText("￥"+paymentBean.getUserWithdrawMoney());
        textView1.setText("");
        textView2.setText("订单号："+paymentBean.getUserOrder());
        String time[]=paymentBean.getCreateTime().split(" ");
        if(time.length>1){
            textView3.setText(time[1]);
            textView4.setText(time[0]);
        }
        holder.remark_valuse.setText(paymentBean.getRemark());
        holder.paydate.setText(paymentBean.getWithdrawTypeStr());
        if(paymentBean.getCreateTime()!=null && !paymentBean.getCreateTime().equalsIgnoreCase("null"))
        holder.commitdate.setText(paymentBean.getCreateTime());

        int Check_status=paymentBean.getCheck_status();
        int status=paymentBean.getStatus();
        TextView ptitle4=(TextView)convertView.findViewById(R.id.ptitle4);
        if(status*Check_status==1)
        {
            holder.remark_layout.setVisibility(View.VISIBLE);
            ptitle4.setText("审核时间:");
            holder.state.setTextColor(context.getResources().getColor(R.color.gray5));
            holder.state.setText(paymentBean.getCheckTime());
            holder.state_image.setImageDrawable(context.getResources().getDrawable(imageresoures[0]));
        }
        else if( Check_status==2)
        {
            holder.remark_layout.setVisibility(View.VISIBLE);
            ptitle4.setText("审核时间:");
            holder.state.setTextColor(context.getResources().getColor(R.color.gray5));
//            holder.state.setText("失败");
            holder.state.setText(paymentBean.getCheckTime());
            holder.state_image.setImageDrawable(context.getResources().getDrawable(imageresoures[1]));
        }
        else if(Check_status==1 && status==0)
        {
            holder.remark_layout.setVisibility(View.GONE);
            //待审核
            ptitle4.setText("");
            holder.state.setTextColor(context.getResources().getColor(R.color.gray5));
//            holder.state.setText("审核通过，未出款");
            holder.state.setText("正在审核中...");
            holder.remark_layout.setVisibility(View.GONE);
            holder.state_image.setImageDrawable(context.getResources().getDrawable(imageresoures[2]));
        }
        else
        {
            holder.remark_layout.setVisibility(View.GONE);
            //待审核
            ptitle4.setText("");
            holder.state.setTextColor(context.getResources().getColor(R.color.gray5));
//            holder.state.setText("审核中");
            holder.state.setText("正在审核中...");
            holder.remark_layout.setVisibility(View.GONE);
            holder.state_image.setImageDrawable(context.getResources().getDrawable(imageresoures[2]));
        }


        if(payments.get(position).getItemstate())
        {
            holder.triangle.setImageBitmap(bottom);
            holder.bottomview.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.triangle.setImageBitmap(right);
            holder.bottomview.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder
    {
        ImageView triangle,state_image;
        LinearLayout firstview,secondview,remark_layout,layout;
        TextView state,paycount,paydate,commitdate,ptitle1,ptitle2,ptitle3,remark_valuse;
        RelativeLayout bottomview;
    }

//    private void SetWidth(LinearLayout linearLayout)
//    {
//         ArrayList<LinearLayout> linearLayouts=new ArrayList<LinearLayout>();
//        for (int i = 0; i < linearLayout.getChildCount(); i++) {
//            if(i%2==0)
//            {
//                linearLayouts.add((LinearLayout)linearLayout.getChildAt(i));
//            }
//        }
//    }
}
