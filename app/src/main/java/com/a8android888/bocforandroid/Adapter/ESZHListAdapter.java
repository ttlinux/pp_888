package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.util.SparseArray;
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
import com.a8android888.bocforandroid.util.LogTools;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24.
 */
public class ESZHListAdapter extends BaseAdapter{

    Activity activity;
    SparseArray<?> sparseArray;
    String[] titles;
    ArrayList<LotterycomitorderBean> recordlist=new ArrayList<LotterycomitorderBean>();
    SparseArray<View> linearrs=new SparseArray<View>();

    public ESZHListAdapter(Activity activity, SparseArray<?> sparseArray, String[] titles)
    {
            this.activity=activity;
            this.sparseArray=sparseArray;
            this.titles=titles;
    }

    public void NotifyAdapter(SparseArray<?> sparseArray, String[] titles)
    {
        this.titles=titles;
        if(sparseArray==null || sparseArray.size()==0)
        {
            this.sparseArray.clear();
        }
        else
        {
            if(this.sparseArray!=null && this.sparseArray.size()>0)
            {
                for (int i = 0; i < sparseArray.size(); i++) {
                    ArrayList<LotterycomitorderBean> beans=(ArrayList<LotterycomitorderBean>)sparseArray.get(i);
                    ArrayList<LotterycomitorderBean> mbeans=(ArrayList<LotterycomitorderBean>)this.sparseArray.get(i);
                    for (int k = 0; k <beans.size() ; k++) {
                        beans.get(k).setSelected(mbeans.get(k).getSelected());
                    }
                }
            }
            this.sparseArray=sparseArray.clone();
        }
        linearrs.clear();
        notifyDataSetChanged();
    }
    public void ResetState()
    {
        for (int i = 0; i < sparseArray.size(); i++) {
            ArrayList<LotterycomitorderBean> beans=(ArrayList<LotterycomitorderBean>)sparseArray.get(i);
            for (int k = 0; k <beans.size(); k++) {
                beans.get(k).setSelected(false);
                LogTools.e("adfafaf12", beans.get(k).getSelected() + "");
            }
        }
        linearrs.clear();
        recordlist.clear();
        notifyDataSetChanged();
    }
    public void ClearRecordData()
    {
        recordlist.clear();
    }
    @Override
    public int getCount() {
        return sparseArray.size();
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
        if (linearrs.get(position)== null) {
            LogTools.e("getview","getview    "+position);
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.item_zm1_6tmfragement, null);
            linearrs.put(position, convertView);
            holder.layout1=(LinearLayout)convertView.findViewById(R.id.layout1);

            ArrayList<?> arrayList=(ArrayList<?>)sparseArray.get(position);
            if(holder.layout1.getChildCount()==0)
            {
                LogTools.e("getview333","getview333   "+position);
                holder.layout1.removeAllViews();
                AddBtn(holder.layout1, arrayList.size(), position);
            }
            convertView.setTag(holder);

        }   else{
            LogTools.e("getview222","getview222   "+position);
            convertView=linearrs.get(position);
            holder= (ViewHolder) convertView.getTag();
        }




        holder.zm = (TextView) convertView.findViewById(R.id.zm);
        holder.zm.setText(titles[position]);

        return linearrs.get(position);
    }

    private void Settext(LinearLayout linearLayout)
    {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
        }
    }

    private void  AddBtn(LinearLayout layout ,int sizeint,final int pos) {
//        int itemcount=sizeint;
//        int count=itemcount%2==0?itemcount/2:(itemcount/2)+1;
//        LinearLayout hora=new LinearLayout(mContext);
//        int width= (ScreenUtils.getScreenWH(activity)[0]-20)/2;
//        LinearLayout.LayoutParams paramsa=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        paramsa.topMargin=15;
//        paramsa.bottomMargin=15;
//        paramsa.weight=1;
//        hora.setOrientation(LinearLayout.VERTICAL);
//        hora.setLayoutParams(paramsa);
//        for (int i = 0; i < count; i++) {
//            LinearLayout hor=new LinearLayout(activity);
//            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            hor.setLayoutParams(params);
//            hor.setOrientation(LinearLayout.VERTICAL);
////
//            int tempcount=itemcount-i*2>2?(2*(i+1)):itemcount;
            for (int j = 0; j <sizeint; j++) {
                final ArrayList<LotterycomitorderBean> beans=(ArrayList<LotterycomitorderBean>)sparseArray.get(pos);
                final int iii=j;
                View popupView = View.inflate(activity, R.layout.item_eszhtmfragement, null);
                LinearLayout.LayoutParams params2 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.weight=1;
                popupView.setLayoutParams(params2);
                layout.addView(popupView);
                CheckBox peilv = (CheckBox) popupView.findViewById(R.id.peilv);
                peilv.setText(beans.get(j).getNumber());
                peilv.setTag(iii);
                peilv.setChecked(beans.get(j).getSelected());
                peilv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int index = Integer.valueOf(buttonView.getTag() + "");
                        if (isChecked == true) {
                            beans.get(index).setSelected(true);
                            recordlist.add(beans.get(index));
                            LogTools.e("选择的是" + pos + "pos" + index, new Gson().toJson(beans.get(index)));
                        } else {
                            beans.get(index).setSelected(false);
                            LogTools.e("选删除的是" + pos + "pos" + index, new Gson().toJson(beans.get(index)));
                            recordlist.remove(beans.get(index));
                        }
                    }
                });
//            if(layout.getChildCount()==1)
//            {
//                LinearLayout.LayoutParams params3 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                params3.weight=1;
//                TextView textView=new TextView(activity);
//                textView.setLayoutParams(params3);
//                hor.addView(textView);
//            }
//            }
//            layout.addView(hor);
        }

    }


    class ViewHolder {
        TextView zm;
        LinearLayout layout,layout1;
    }

    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        LogTools.e("GetSelectItem",new Gson().toJson(recordlist));
        return recordlist;
    }
}
