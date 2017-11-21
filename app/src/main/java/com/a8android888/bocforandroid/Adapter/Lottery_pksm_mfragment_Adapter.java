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
import com.a8android888.bocforandroid.util.LogTools;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_pksm_mfragment_Adapter extends BaseAdapter{
    private Context mContext;
    ArrayList<ArrayList<LotterycomitorderBean>> mbasefragments;
    String orderFlag;//订单标识 normal--常规订单
    SparseArray<View> views=new SparseArray<View>();

    public Lottery_pksm_mfragment_Adapter(Context context, ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
        this.mbasefragments=basefragments;
        this.orderFlag=orderFlag;
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
//        LogTools.e("数据2", count + "");
        return mbasefragments.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mbasefragments.get(position);

    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public void notifyStockdatalist(ArrayList<ArrayList<LotterycomitorderBean>> basefragments) {

        if(basefragments==null || basefragments.size()==0)
            this.mbasefragments.clear();
        else
        {
            if(this.mbasefragments==null || this.mbasefragments.size()==0)
            {

            }
            else
            {
                for (int i = 0; i < basefragments.size(); i++) {
                    ArrayList<LotterycomitorderBean> beans=basefragments.get(i);
                    for (int k = 0; k <beans.size(); k++) {
                        beans.get(k).setSelected(this.mbasefragments.get(i).get(k).getSelected());
                    }
                }

            }
            this.mbasefragments=basefragments;
        }

        views.clear();
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final    int index=position;
        if (views.get(position)== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_zm1_6tmfragement, null);


//            holder.layout1=(LinearLayout)convertView.findViewById(R.id.layout1);

            convertView.setTag(holder);
            views.put(position,convertView);
        }   else{
            convertView=views.get(position);
            holder= (ViewHolder) convertView.getTag();
        }
        holder.zm = (TextView) convertView.findViewById(R.id.zm);

        holder.layout1=(LinearLayout)convertView.findViewById(R.id.layout1);
        if(holder.layout1.getChildCount()==0 && mbasefragments.size()>0) {
            AddBtn(holder.layout1, mbasefragments.get(index).size(), index);
        }

        if(mbasefragments.size()>0)
        setdata(holder.layout1,position);

        if(position==0) {
            holder.zm.setText("冠军");
        }
        if(position==1) {
            holder.zm.setText("亚军");
        }
        if(position==2) {
            holder.zm.setText("季军");
        }
        if(position==3) {
            holder.zm.setText("第四名");
        }
        if(position==4) {
            holder.zm.setText("第五名");
        }
        if(position==5) {
            holder.zm.setText("第六名");
        }  if(position==6) {
            holder.zm.setText("第七名");
        }
        if(position==7) {
            holder.zm.setText("第八名");
        }
        if(position==8) {
            holder.zm.setText("第九名");
        }   if(position==9) {
            holder.zm.setText("第十名");
        }


//        holder.layout1.addView( holder.layout);
//        holder.peilv.setTag(index);
//        holder.peilv .setOnCheckedChangeListener(this);
//        holder.peilv.setChecked(mList.get(position).getSelected());
//        holder.peilv.setText(mList.get(position).getPl());
//        holder.number.setText(mList.get(position).getNumber());
//        if(Httputils.isNumber(mList.get(position).getNumber())) {
//            if (Httputils.useLoop(Httputils.lotterry6red, Integer.valueOf(mList.get(position).getNumber()))) {
//                holder.number.setBackgroundResource((R.drawable.lottery_red));
//            }
//            if (Httputils.useLoop(Httputils.lotterry6green, Integer.valueOf(mList.get(position).getNumber()))) {
//                holder.number.setBackgroundResource((R.drawable.lottery_green));
//            }
//            if (Httputils.useLoop(Httputils.lotterry6blue, Integer.valueOf(mList.get(position).getNumber()))) {
//                holder.number.setBackgroundResource((R.drawable.lottery_blue));
//            }
//
//        }
//        else {
//            holder.number.setBackgroundColor(mContext.getResources().getColor(R.color.lotteryfragmenttitle));
//        }
//        if(position==49){
//            holder.layout.setVisibility(View.GONE);
//        }

        return views.get(position);
    }
    private void  AddBtn(LinearLayout layout ,int sizeint,final int pos) {
        int itemcount=sizeint;
        int count=itemcount%2==0?itemcount/2:(itemcount/2)+1;
//        LinearLayout hora=new LinearLayout(mContext);
//        int width= (ScreenUtils.getScreenWH(activity)[0]-20)/2;
//        LinearLayout.LayoutParams paramsa=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        paramsa.topMargin=15;
//        paramsa.bottomMargin=15;
//        paramsa.weight=1;
//        hora.setOrientation(LinearLayout.VERTICAL);
//        hora.setLayoutParams(paramsa);
        LogTools.e("++++++","++++++++++++");
        for (int i = 0; i < count; i++) {
            LinearLayout hor=new LinearLayout(mContext);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            hor.setLayoutParams(params);
            hor.setOrientation(LinearLayout.HORIZONTAL);

            int tempcount=itemcount-i*2>2?(2*(i+1)):itemcount;
            for (int j = i*2; j <tempcount; j++) {
                final int iii=j;
                View popupView = View.inflate(mContext, R.layout.item_tmfragement, null);
                LinearLayout.LayoutParams params2 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.weight=1;
                popupView.setLayoutParams(params2);
                TextView number = (TextView) popupView.findViewById(R.id.number);
                CheckBox peilv = (CheckBox) popupView.findViewById(R.id.peilv);
                RadioGroup group = (RadioGroup) popupView.findViewById(R.id.grouplayout);
//                number.setText(basefragments.get(pos).get(j).getNumber());
                peilv.setTag(iii);
                peilv.setChecked(mbasefragments.get(pos).get(j).getSelected());
//                peilv.setText(basefragments.get(pos).get(j).getPl());
//                if(i==0){
//                    group.setVisibility(View.VISIBLE);
//                }
//                else{
//                    group.setVisibility(View.GONE);
//                }



                peilv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View buttonView) {

                        int index = Integer.valueOf(buttonView.getTag() + "");
                        boolean state=((CheckBox)buttonView).isChecked();
                        mbasefragments.get(pos).get(index).setSelected(state);
                    }
                });
                hor.addView(popupView);
            }
//            hora.addView(hor);
            if(hor.getChildCount()==1)
            {
                LinearLayout.LayoutParams params3 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params3.weight=1;
                TextView textView=new TextView(mContext);
                textView.setLayoutParams(params3);
                hor.addView(textView);
            }
            layout.addView(hor);
        }

    }

    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        ArrayList<LotterycomitorderBean> lotterycomitorderBeans=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i < mbasefragments.size(); i++) {
            ArrayList<LotterycomitorderBean> beans=mbasefragments.get(i);
            for (int k = 0; k <beans.size(); k++) {
                if(beans.get(k).getSelected())
                {
                    lotterycomitorderBeans.add(beans.get(k));
                }
            }
        }
        return lotterycomitorderBeans;
    }

    class ViewHolder {
        TextView zm;
        LinearLayout layout,layout1;
    }

    public void ResetState()
    {
        for (int i = 0; i < mbasefragments.size(); i++) {
            ArrayList<LotterycomitorderBean> beans=mbasefragments.get(i);
            for (int k = 0; k <beans.size(); k++) {
                beans.get(k).setSelected(false);
            }
        }
        views.clear();
        notifyDataSetChanged();
    }

    private void setdata(LinearLayout layout,int pos)
    {
        ArrayList<LotterycomitorderBean> beans=mbasefragments.get(pos);
        for (int i = 0; i <layout.getChildCount(); i++) {
            LinearLayout hor=(LinearLayout)layout.getChildAt(i);

            for (int k = 0; k <hor.getChildCount(); k++) {
                if(i==layout.getChildCount()-1 && hor.getChildAt(k) instanceof TextView)
                {
                    break;
                }
                else
                {
                    View view=hor.getChildAt(k);
                    int index=(i*2)+k;
                    LotterycomitorderBean bean=beans.get(index);
                    TextView textview=(TextView)view.findViewById(R.id.number);
                    CheckBox peilv=(CheckBox)view.findViewById(R.id.peilv);
                    textview.setText(bean.getNumber());
                    peilv.setText(bean.getPl());
                    peilv.setChecked(bean.getSelected());
                    if (bean.getNumber().indexOf("红波") != -1) {
                        textview.setTextColor(mContext.getResources().getColor(R.color.lottery));
                    }
                    else  if (bean.getNumber().indexOf("绿波") != -1) {
                        textview.setTextColor(mContext.getResources().getColor(R.color.green));
                    }
                    else  if (bean.getNumber().indexOf("蓝波") != -1) {
                        textview.setTextColor(mContext.getResources().getColor(R.color.lotteryblue));
                    }
                    else
                        textview.setTextColor(mContext.getResources().getColor(R.color.black));
                }
            }

        }
    }
}
