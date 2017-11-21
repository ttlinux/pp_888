package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
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
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_klsf1_8_mfragment_Adapter extends BaseAdapter{
    private Context mContext;
    ArrayList<LotterycomitorderBean> mList;
    ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    String orderFlag;//标示是哪几个定位
    Activity activity;
    public static ArrayList<LotterycomitorderBean> mList3=new ArrayList<LotterycomitorderBean>();
    SparseArray<View> views=new SparseArray<View>();

    public Lottery_klsf1_8_mfragment_Adapter(Context context, ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
        this.basefragments=basefragments;
        this.orderFlag=orderFlag;
        this.activity=activity;
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
        return basefragments.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return basefragments.get(position);

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

            holder.layout1=(LinearLayout)convertView.findViewById(R.id.layout1);
            if(holder.layout1.getChildCount()==0)
            {
                holder.layout1.removeAllViews();
                AddBtn(holder.layout1, basefragments.get(index).size(), index);
            }
            convertView.setTag(holder);
            views.put(position,convertView);

        }   else{
            convertView=views.get(position);
            holder= (ViewHolder) convertView.getTag();
        }
        holder.zm = (TextView) convertView.findViewById(R.id.zm);


       if(position==0)
            holder.zm.setText("号码");
        if(position==1)
            holder.zm.setText("双面");
        return convertView;
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
                RadioGroup    group = (RadioGroup) popupView.findViewById(R.id.grouplayout);
                number.setText(basefragments.get(pos).get(j).getNumber());
                peilv.setTag(iii);
                peilv.setChecked(basefragments.get(pos).get(j).getSelected());
                peilv.setText(basefragments.get(pos).get(j).getPl());
                if(i==0){
                    group.setVisibility(View.VISIBLE);
                }
                else{
                    group.setVisibility(View.GONE);
                }
                peilv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View buttonView) {

                        int index = Integer.valueOf(buttonView.getTag() + "");
                        if (((CheckBox)buttonView).isChecked() == true) {
                            basefragments.get(pos).get(index).setSelected(true);
                        } else {
                            basefragments.get(pos).get(index).setSelected(false);
                        }
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
        for (int i = 0; i < basefragments.size(); i++) {
            ArrayList<LotterycomitorderBean> beans=basefragments.get(i);
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

}
