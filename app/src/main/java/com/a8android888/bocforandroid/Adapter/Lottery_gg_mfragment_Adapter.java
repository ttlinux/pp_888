package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.LogTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_gg_mfragment_Adapter extends BaseAdapter {
    private Context mContext;
    ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    String orderFlag;//订单标识 normal--常规订单
    SparseArray<View> views = new SparseArray<View>();
    SparseArray<CheckBox> checkboxs = new SparseArray<CheckBox>();

    public Lottery_gg_mfragment_Adapter(Context context, ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
        this.basefragments = basefragments;
        this.orderFlag = orderFlag;
    }


    public int getCount() {
        // TODO Auto-generated method stub
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
        if(basefragments==null)
            this.basefragments.clear();
        else
        {
            if(this.basefragments!=null && this.basefragments.size()>0)
            {
                for (int i = 0; i < basefragments.size(); i++) {
                    ArrayList<LotterycomitorderBean> beans=basefragments.get(i);
                    for (int k = 0; k <beans.size(); k++) {
                        beans.get(k).setSelected(this.basefragments.get(i).get(k).getSelected());
                    }
                }
            }
            else
            {

            }
            this.basefragments =basefragments;
        }

        views.clear();
        checkboxs.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (views.get(position) == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_zm1_6tmfragement, null);

            convertView.setTag(holder);
            views.put(position, convertView);
        } else {
            convertView = views.get(position);
            holder = (ViewHolder) convertView.getTag();
        }

        holder.zm = (TextView) convertView.findViewById(R.id.zm);
        holder.zm.setText("正码" + (position + 1));

        holder.layout1 = (LinearLayout) convertView.findViewById(R.id.layout1);
        if (holder.layout1.getChildCount() == 0) {
            AddBtn(holder.layout1, basefragments.get(position).size(), position);
        }

        setdata(holder.layout1, position);

        return convertView;
    }

    private void AddBtn(final LinearLayout layout, final int sizeint, final int pos) {
        for (int i = 0; i < sizeint; i++) {
            final int iii = i;
            View popupView = View.inflate(mContext, R.layout.item_tmfragement, null);
            TextView number = (TextView) popupView.findViewById(R.id.number);
            CheckBox peilv = (CheckBox) popupView.findViewById(R.id.peilv);
            if(checkboxs.get(pos*7+i)==null)
            {
                checkboxs.put(pos*7+i,peilv);
            }
//            number.setText(basefragments.get(pos).get(i).getNumber());
//            peilv.setText(basefragments.get(pos).get(i).getPl());
            peilv.setTag(iii);
//            peilv.setChecked(basefragments.get(pos).get(i).getSelected());
//            peilv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View buttonView) {
//                    CheckBox checkbox= (CheckBox)layout.getTag();
//                    if(checkbox!=null)
//                    {
//                        checkbox.setChecked(false);
//                        int indexc = Integer.valueOf(buttonView.getTag() + "");
//                        basefragments.get(pos).get(indexc).setSelected(false);
//                    }
//                    int index = Integer.valueOf(buttonView.getTag() + "");
//                    LogTools.e("index",index+"");
////                    for (int i = 0; i < basefragments.get(pos).size(); i++) {
////                        basefragments.get(pos).get(i).setSelected(false);
////                            CheckBox peilv = (CheckBox) layout.getChildAt(i).findViewById(R.id.peilv);
////                            peilv.setChecked(false);
////                    }
////                    ((CheckBox)buttonView).setChecked(true);
//                    basefragments.get(pos).get(index).setSelected(true);
//                    layout.setTag(buttonView);
//                }
//            });
            layout.addView(popupView);
        }
    }

    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        ArrayList<LotterycomitorderBean> beanArrayList=new ArrayList<LotterycomitorderBean>();
        for (int i = 0; i < basefragments.size(); i++) {
            ArrayList<LotterycomitorderBean> beans=basefragments.get(i);
            for (int k = 0; k <beans.size(); k++) {
                if(beans.get(k).getSelected())
                {
                    beanArrayList.add(beans.get(k));
                }
            }
        }
        return beanArrayList;
    }

    public void ResetState()
    {
        for (int i = 0; i < basefragments.size(); i++) {
            ArrayList<LotterycomitorderBean> beans=basefragments.get(i);
            for (int k = 0; k <beans.size(); k++) {
                beans.get(k).setSelected(false);
            }
        }
        views.clear();
        notifyDataSetChanged();
    }

    private void setdata(final LinearLayout layout,final int pos)
    {
        ArrayList<LotterycomitorderBean> beans=basefragments.get(pos);
        for (int i = 0; i <layout.getChildCount(); i++) {
            View view=(View)layout.getChildAt(i);
            TextView textview=(TextView)view.findViewById(R.id.number);
            CheckBox peilv=(CheckBox)view.findViewById(R.id.peilv);
            textview.setText(basefragments.get(pos).get(i).getNumber());
            peilv.setText(basefragments.get(pos).get(i).getPl());
            peilv.setChecked(basefragments.get(pos).get(i).getSelected());
            peilv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    LogTools.e("isChecked",isChecked?"YES":"NO");
                    int index = Integer.valueOf(buttonView.getTag() + "");
                    if (isChecked) {
//                        View view = (View) layout.getTag();
//                        if (view != null) {
//                            int findex = Integer.valueOf(view.getTag() + "");
//                            basefragments.get(pos).get(findex).setSelected(false);
//                            ((CheckBox) view).setChecked(false);
//                        }
                        for (int i = 0; i < 7; i++) {
                            if(i!=index)
                            {
                                checkboxs.get(pos*7+i).setChecked(false);
                                basefragments.get(pos).get(i).setSelected(true);
                            }
                        }
                        basefragments.get(pos).get(index).setSelected(true);
                        layout.setTag(buttonView);
                    }
                }
            });

        }
    }

    class ViewHolder {
        TextView zm;
        LinearLayout layout1;
    }

}
