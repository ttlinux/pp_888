package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
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
import com.a8android888.bocforandroid.util.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24.
 */
public class Lottery_bsdw_6mfragment_Adapter extends BaseAdapter{

    Activity activity;
    SparseArray<?> sparseArray;
    String[] titles;
    ArrayList<LotterycomitorderBean> recordlist=new ArrayList<LotterycomitorderBean>();
    SparseArray<View> linearrs=new SparseArray<View>();

    public Lottery_bsdw_6mfragment_Adapter(Activity activity,SparseArray<?> sparseArray,String[] titles)
    {
        this.activity=activity;
        this.sparseArray=sparseArray.clone();
        this.titles=titles;
    }

    public void NotifyAdapter(SparseArray<?> sparseArray,String[] title)
    {
        this.titles=title;
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.item_zm1_6tmfragement, null);
            linearrs.put(position, convertView);

            holder.layout1=(LinearLayout)convertView.findViewById(R.id.layout1);
            ArrayList<?> arrayList=(ArrayList<?>)sparseArray.get(position);
            if(holder.layout1.getChildCount()==0)
            {
                holder.layout1.removeAllViews();
                AddBtn(holder.layout1, arrayList.size(), position);
            }
            convertView.setTag(holder);

        }   else{
            convertView=linearrs.get(position);
            holder= (ViewHolder) convertView.getTag();
        }
        holder.zm = (TextView) convertView.findViewById(R.id.zm);
        if (titles.length > 1) {
            holder.zm.setText(titles[position]);
        } else {
            holder.zm.setVisibility(View.GONE);
        }

        setdata(holder.layout1,position);

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
            LinearLayout hor=new LinearLayout(activity);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            hor.setLayoutParams(params);
            hor.setOrientation(LinearLayout.HORIZONTAL);

            int tempcount=itemcount-i*2>2?(2*(i+1)):itemcount;
            for (int j = i*2; j <tempcount; j++) {
                final ArrayList<LotterycomitorderBean> beans=(ArrayList<LotterycomitorderBean>)sparseArray.get(pos);
                final int iii=j;
                View popupView = View.inflate(activity, R.layout.item_bsdwtmfragement, null);
                LinearLayout.LayoutParams params2 =new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.weight=1;
                popupView.setLayoutParams(params2);
                TextView number = (TextView) popupView.findViewById(R.id.number);
                CheckBox peilv = (CheckBox) popupView.findViewById(R.id.peilv);
                RadioGroup group = (RadioGroup) popupView.findViewById(R.id.grouplayout);
//                number.setText(beans.get(j).getNumber());
                peilv.setTag(iii);
                peilv.setChecked(beans.get(j).getSelected());
                peilv.setText(beans.get(j).getPl());
                if(titles.length>1){
                    group.setVisibility(View.GONE);
                }
                else {
                    if (i == 0) {
                        group.setVisibility(View.VISIBLE);
                    } else {
                        group.setVisibility(View.GONE);
                    }
                }
                peilv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View buttonView) {
                        CheckBox box=(CheckBox)buttonView;
                        int index = Integer.valueOf(buttonView.getTag() + "");
                        if (box.isChecked() == true) {
                            beans.get(index).setSelected(true);
                            recordlist.add(beans.get(index));

                            LogTools.e("选择的是" + pos + "pos" + index, new Gson().toJson(beans.get(index)));
                        } else {
                            LogTools.e("选删除的是" + pos + "pos" + index, new Gson().toJson(beans.get(index)));
                            beans.get(index).setSelected(false);
                            recordlist.remove(beans.get(index));

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
                TextView textView=new TextView(activity);
                textView.setLayoutParams(params3);
                hor.addView(textView);
            }
            layout.addView(hor);
        }

    }

    public void ResetState()
    {
        ArrayList<LotterycomitorderBean> lotterycomitorderBeans=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i < sparseArray.size(); i++) {
            ArrayList<LotterycomitorderBean> beans=(ArrayList<LotterycomitorderBean>)sparseArray.get(i);
            for (int k = 0; k <beans.size(); k++) {
                beans.get(k).setSelected(false);
            }
        }
        recordlist.clear();
        notifyDataSetChanged();
    }
    public void ClearRecordData()
    {
        recordlist.clear();
    }
    private void setdata(LinearLayout layout,int pos)
    {
        ArrayList<LotterycomitorderBean> beans=(ArrayList<LotterycomitorderBean>)sparseArray.get(pos);
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
                        textview.setTextColor(activity.getResources().getColor(R.color.lottery));
                    }
                    else  if (bean.getNumber().indexOf("绿波") != -1) {
                        textview.setTextColor(activity.getResources().getColor(R.color.green));
                    }
                    else  if (bean.getNumber().indexOf("蓝波") != -1) {
                        textview.setTextColor(activity.getResources().getColor(R.color.lotteryblue));
                    }
                    else
                        textview.setTextColor(activity.getResources().getColor(R.color.black));
                }
            }

        }
    }


    class ViewHolder {
        TextView zm;
        LinearLayout layout,layout1;
    }

    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        LogTools.e("beans",new Gson().toJson(recordlist));
        return recordlist;
    }
}
