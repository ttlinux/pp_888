package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.R;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */
public class BankListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<JSONObject> jsonObjects;
    String text1,text2,text3,text4;

    public BankListAdapter(Context context,ArrayList<JSONObject> jsonObjects)
    {
        this.context=context;
        this.jsonObjects=jsonObjects;
        text1=context.getString(R.string.withdraw_bank);
        text2=context.getString(R.string.bankname);
        text3=context.getString(R.string.bank_account);
        text4=context.getString(R.string.bank_address);
    }

    @Override
    public int getCount() {
        return jsonObjects.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_bankinfo, null);
            convertView.setTag(holder);
        }   else{
            holder= (ViewHolder) convertView.getTag();
        }
        LinearLayout linearLayout=(LinearLayout)convertView;
        TextView textView1=(TextView)linearLayout.getChildAt(0);
        TextView textView2=(TextView)linearLayout.getChildAt(1);
        TextView textView3=(TextView)linearLayout.getChildAt(2);
        TextView textView4=(TextView)linearLayout.getChildAt(3);

        JSONObject jsonObject=jsonObjects.get(position);
        textView1.setText(text1+jsonObject.optString("bankType",""));
        textView2.setText(text2+jsonObject.optString("userRealName",""));
        textView3.setText(text3+jsonObject.optString("bankCard",""));
        textView4.setText(text4+jsonObject.optString("bankAddress",""));
        return convertView;
    }

    class ViewHolder
    {

    }
}
