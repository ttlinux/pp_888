package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.view.Gravity;
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
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_zm1_6mfragment_Adapter extends BaseAdapter{
    private Context mContext;
    ArrayList<ArrayList<LotterycomitorderBean>> basefragments;
    public List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
    String orderFlag;//订单标识 normal--常规订单
    public static ArrayList<LotterycomitorderBean> mList3=new ArrayList<LotterycomitorderBean>();
    ArrayList<LotterycomitorderBean> recordlist=new ArrayList<LotterycomitorderBean>();
    public Lottery_zm1_6mfragment_Adapter(Context context,ArrayList<ArrayList<LotterycomitorderBean>> basefragments, String orderFlag) {
        this.mContext = context;
        this.basefragments=basefragments;
        this.orderFlag=orderFlag;
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
        if(basefragments==null || basefragments.size()<1)
            this.basefragments.clear();
        else
            this.basefragments=basefragments;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final    int index=position;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_zm1_6tmfragement, null);

            convertView.setTag(holder);

        }   else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.zm = (TextView) convertView.findViewById(R.id.zm);
        holder.layout1=(LinearLayout)convertView.findViewById(R.id.layout1);
        if(holder.layout1.getChildCount()==0) {
            holder.layout1.removeAllViews();
        AddBtn(holder.layout1, basefragments.get(index).size(), index);
        }
        holder.zm.setText("正码" + (index + 1));
        return convertView;
    }
    private void  AddBtn(LinearLayout layout ,int sizeint,final int pos) {

        for (int i = 0; i < sizeint; i++) {
            final int iii=i;
            View popupView = View.inflate(mContext, R.layout.item_tmfragement, null);
            TextView number = (TextView) popupView.findViewById(R.id.number);
            CheckBox peilv = (CheckBox) popupView.findViewById(R.id.peilv);
            RadioGroup group = (RadioGroup) popupView.findViewById(R.id.grouplayout);
            number.setText(basefragments.get(pos).get(i).getNumber());
//            if (i == 0 ) {
//              group.setVisibility(View.VISIBLE);
//            } else {
//               group.setVisibility(View.GONE);
//            }
            if(Httputils.isNumber(basefragments.get(pos).get(i).getNumber())) {
                number.setTextColor(mContext.getResources().getColor(R.color.white));
            }else {
                if (basefragments.get(pos).get(i).getNumber().indexOf("红波") != -1) {
                    number.setTextColor(mContext.getResources().getColor(R.color.lottery));
                }
              else  if (basefragments.get(pos).get(i).getNumber().indexOf("绿波") != -1) {
                    number.setTextColor(mContext.getResources().getColor(R.color.green));
                }
                else  if (basefragments.get(pos).get(i).getNumber().indexOf("蓝波") != -1) {
                    number.setTextColor(mContext.getResources().getColor(R.color.lotteryblue));
                }
                else
                number.setTextColor(mContext.getResources().getColor(R.color.black));
            }
            peilv.setText(basefragments.get(pos).get(i).getPl());
            peilv.setTag(iii);
            peilv.setChecked(basefragments.get(pos).get(i).getSelected());
            peilv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View buttonView) {
                    CheckBox box=(CheckBox)buttonView;
                    int index = Integer.valueOf(buttonView.getTag() + "");
                    if (box.isChecked() == true) {
                        basefragments.get(pos).get(index).setSelected(true);
                        recordlist.add(basefragments.get(pos).get(index));

                        LogTools.e("选择的是" + "pos" + index, new Gson().toJson(basefragments.get(pos).get(index)));
                    } else {
                        LogTools.e("选删除的是"  + "pos" + index, new Gson().toJson(basefragments.get(pos).get(index)));
                        basefragments.get(pos).get(index).setSelected(false);
                        recordlist.remove(basefragments.get(pos).get(index));

                    }
                }
            });
//            peilv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    int index = Integer.valueOf(buttonView.getTag() + "");
//                    if (buttonView.isChecked() == true) {
//                        basefragments.get(pos).get(index).setSelected(true);
//                        JSONObject jsonObject = new JSONObject();
//                        try {
//                            jsonObject.put("id", basefragments.get(pos).get(index).getUid());
//                            jsonObject.put("pl", basefragments.get(pos).get(index).getPl());
//                            jsonObject.put("number", basefragments.get(pos).get(index).getNumber());
//                            jsonObject.put("xzlxitemname", "正码" + (pos + 1));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        jsonObjects.add(jsonObject);
//
//                        LogTools.e("选择的是" + pos + "pos" + index, jsonObjects.toString());
//                    } else {
//                        LogTools.e("选择的是", jsonObjects.toString());
//                        basefragments.get(pos).get(index).setSelected(false);
//                        jsonObjects.remove(basefragments.get(pos).get(index));
//
//                    }
//                }
//            });
            layout.addView(popupView);
        }

    }
    public ArrayList<LotterycomitorderBean> GetSelectItem()
    {
        LogTools.e("GetSelectItem", new Gson().toJson(recordlist));
        return recordlist;
    }

    public void ClearRecordData()
    {
        recordlist.clear();
    }

    class ViewHolder {
        TextView zm;
        LinearLayout layout,layout1;
    }

}
