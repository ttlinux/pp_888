package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.Bean.PaymentBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.TriangleView;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Lottery_comit_Order_Adapter extends BaseAdapter {

    private Context context;
    public ArrayList<LotterycomitorderBean> mList;
    SparseArray<MyTextWatcher> myTextWatchers = new SparseArray<>();
    ListView listView;

    public Lottery_comit_Order_Adapter(Context context, ArrayList<LotterycomitorderBean> mList,ListView listView) {

        this.context = context;
        this.mList = mList;
        this.listView=listView;
    }

    public void NotifyData(ArrayList<LotterycomitorderBean> payments) {
        this.mList = payments;
        for (int i = 0; i < mList.size(); i++) {
            HandleView(i);
        }
//        notifyDataSetChanged();
    }

    public void NotifyData(ArrayList<LotterycomitorderBean> payments, String jie) {
        this.mList = payments;
        for (int i = 0; i < mList.size(); i++) {
            mList.get(i).setXzje(jie + "");
        }
        for (int i = 0; i < mList.size(); i++) {
            HandleView(i);
        }
    }

    @Override
    public int getCount() {
        LogTools.e("getCount",mList.size()+ "");
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    int mTouchItemPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_lottery_comit_order, null);
            holder= new ViewHolder();
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
//        if (convertView== null) {
//            holder = new ViewHolder();
//
//
//            convertView.setTag(holder);
//        }   else{
//            holder= (ViewHolder) convertView.getTag();
//        }
//        holder.comitedit.setText(mList.get(position).getXzje());

        holder.image = (ImageView) convertView.findViewById(R.id.image);
        holder.comitname = (TextView) convertView.findViewById(R.id.comitname);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.zuidi = (TextView) convertView.findViewById(R.id.zuidi);
        holder.height = (TextView) convertView.findViewById(R.id.height);
        holder.image.setTag(position);
        holder.comitname.setText(mList.get(position).getItemtname());
        if (mList.get(position).getXzlxitemname().equalsIgnoreCase("")) {
            holder.name.setText(mList.get(position).getItemtname() + ":" + mList.get(position).getNumber());
        } else {
            holder.name.setText(mList.get(position).getXzlxitemname() + ":" + mList.get(position).getNumber());
        }
        holder.zuidi.setText("单注" + mList.get(position).getMinLimit() + "-" + mList.get(position).getMaxlimit() + "元");
        holder.height.setText("单期最高" + mList.get(position).getMaxPeriodLimit() + "元");
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idex = (Integer) v.getTag();
                mList.remove(idex);
                LotteryComitOrderActivity.oponclic.edit();
                notifyDataSetChanged();
                if (mList.isEmpty()) {
                    ((Activity) context).finish();
                }
            }
        });
        holder.comitedit = (EditText) convertView.findViewById(R.id.comitedit);

        holder.comitedit.setTag(position);

//        holder.comitedit.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                mTouchItemPosition = (Integer) view.getTag();
//                return false;
//            }
//        });


//        if (mTouchItemPosition == position) {
//            holder.comitedit.requestFocus();
//            holder.comitedit.setSelection( holder.comitedit.getText().length());
//        } else {
//            holder.comitedit.clearFocus();
//        }
        if (myTextWatchers.get(position) == null) {
            myTextWatchers.put(position, new MyTextWatcher(holder));
        }
        holder.comitedit.removeTextChangedListener(myTextWatchers.get(position));
        holder.comitedit.addTextChangedListener(myTextWatchers.get(position));

//        if(isshuru) {
        if (mList.get(position).getXzje().equalsIgnoreCase("0")) {
            holder.comitedit.setText("");
        } else
            holder.comitedit.setText(mList.get(position).getXzje());

        holder.comitedit.clearFocus();
//        }
        return convertView;
    }

    @SuppressWarnings("unused")
    private void HandleView(int position)
    {
        //得到第一个可显示控件的位置，
        int jposition=position+1;
        int visiblePosition = listView.getFirstVisiblePosition();
        LogTools.e("HandleView",visiblePosition+"  HandleView   "+jposition);
        //只有当要更新的view在可见的位置时才更新，不可见时，跳过不更新
        if (jposition - visiblePosition >= 0) {
            //得到要更新的item的view
            View convertView = listView.getChildAt(jposition - visiblePosition);
            if(convertView==null) {
                LogTools.e("HandleView","convertView is null");
                return;
            }
            //从view中取得holder
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if(holder==null) {
                LogTools.e("HandleView","holder is null");
                return;
            }
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.comitname = (TextView) convertView.findViewById(R.id.comitname);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.zuidi = (TextView) convertView.findViewById(R.id.zuidi);
            holder.height = (TextView) convertView.findViewById(R.id.height);
            holder.image.setTag(position);
            holder.comitname.setText(mList.get(position).getItemtname());
            if (mList.get(position).getXzlxitemname().equalsIgnoreCase("")) {
                holder.name.setText(mList.get(position).getItemtname() + ":" + mList.get(position).getNumber());
            } else {
                holder.name.setText(mList.get(position).getXzlxitemname() + ":" + mList.get(position).getNumber());
            }
            holder.zuidi.setText("单注" + mList.get(position).getMinLimit() + "-" + mList.get(position).getMaxlimit() + "元");
            holder.height.setText("单期最高" + mList.get(position).getMaxPeriodLimit() + "元");
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int idex = (Integer) v.getTag();
                    mList.remove(idex);
                    LotteryComitOrderActivity.oponclic.edit();
                    notifyDataSetChanged();
                    if (mList.isEmpty()) {
                        ((Activity) context).finish();
                    }
                }
            });
            holder.comitedit = (EditText) convertView.findViewById(R.id.comitedit);

            holder.comitedit.setTag(position);

            if (myTextWatchers.get(position) == null) {
                myTextWatchers.put(position, new MyTextWatcher(holder));
            }
            holder.comitedit.removeTextChangedListener(myTextWatchers.get(position));
            holder.comitedit.addTextChangedListener(myTextWatchers.get(position));

//        if(isshuru) {
            if (mList.get(position).getXzje().equalsIgnoreCase("0")) {
                holder.comitedit.setText("");
            } else
                holder.comitedit.setText(mList.get(position).getXzje());

            holder.comitedit.clearFocus();

        }
    }

    class ViewHolder
    {
        ImageView image;
        TextView comitname,zuidi,height,name;
        EditText comitedit;
    }

    class MyTextWatcher implements TextWatcher {
        @SuppressWarnings("unused")
        public MyTextWatcher(ViewHolder holder) {
            this. mHolder = holder;
        }
        private ViewHolder mHolder;
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            LogTools.e("mHolder->onTextChanged",s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
            LogTools.e("mHolder->beforeTextChanged",s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            LogTools.e("mHolder->afterTextChanged",s.toString());
            LogTools.e("mHolder",(Integer) mHolder.comitedit.getTag()+"");
            if (s.toString().trim() != null) {
                int position = (Integer) mHolder.comitedit.getTag();

                String   shuliang=s.toString();
                if(shuliang.equalsIgnoreCase(""))
                {
                    shuliang="0";
                }
                if(shuliang!= null ){
//                    LogTools.e("mHolder2222",shuliang);
//                    try
//                    {
                    int MinLimit=Integer.valueOf(mList.get(position).getMinLimit());
                    int shuliangnum=Integer.valueOf(shuliang);
                    int Maxlimit=Integer.valueOf(mList.get(position).getMaxlimit());
//                    LogTools.e("mHolder->ssss", shuliang + "-->" + MinLimit + "-->" + Maxlimit);

                    mList.get(position).setXzje(shuliang + "");
                    if(MinLimit<=shuliangnum&&shuliangnum<=Maxlimit) {

                    }
                    else {
//                        ToastUtil.showMessage(context,"请按单注和单期的要求输入金额");
                    }
                    LotteryComitOrderActivity.oponclic.edit();
//                    }
//                    catch (java.lang.NumberFormatException except)
//                    {
//                        LogTools.e("mList达到法定",except.getMessage());
//                    }
                }
                else {
//                    ToastUtil.showMessage(context,"请按单注和单期的要求输入金额");
                    return;
                }


            }
            /////////////////////////
        }
    }
}
