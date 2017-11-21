package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.UserMessageActivity.WithDrawActivty2;
import com.a8android888.bocforandroid.activity.user.WithDraw.WithDrawActivty;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */
public class withdrawListAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<JSONObject> jsonObjects;
    String type;//1是提现2是绑定银行卡
    private ImageLoader mImageDownLoader;
    public withdrawListAdapter(Context context, ArrayList<JSONObject> jsonObjects,String type)
    {
        this.context=context;
        this.jsonObjects=jsonObjects;
        this.type=type;
        mImageDownLoader = ((BaseApplication)context.getApplicationContext())
                .getImageLoader();
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
        final   JSONObject jsonObject;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_listwithdraw_layout, null);
            convertView.setTag(holder);
        }   else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.bankname=(TextView)convertView.findViewById(R.id.bankname);
        holder.banknumber=(TextView)convertView.findViewById(R.id.banknumber);
        holder.textView3=(TextView)convertView.findViewById(R.id.textView3);
        holder.img=(ImageView)convertView.findViewById(R.id.img);
        jsonObject=jsonObjects.get(position);
        holder.bankname.setText(jsonObject.optString("bankCnName",""));
//        holder.textView3.setText(jsonObject.optString("userRealName",""));
        holder.banknumber.setText(jsonObject.optString("bankCard",""));
        mImageDownLoader.displayImage(jsonObject.optString("bigPicUrl", ""),holder.img);
       convertView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(type.equalsIgnoreCase("1")) {
                   Intent intent = new Intent(context, WithDrawActivty.class);
                   intent.putExtra("bankId", jsonObject.optString("id", ""));
                   intent.putExtra("bankType", jsonObject.optString("bankCnName", ""));
                   intent.putExtra("userRealName", jsonObject.optString("userRealName", ""));
                   intent.putExtra("bankCard", jsonObject.optString("bankCard", ""));
                   intent.putExtra("bankAddress", jsonObject.optString("bankAddress", ""));
                   intent.putExtra("minMaxDes",jsonObject.optString("minMaxDes", ""));
                   intent.putExtra("minPay",jsonObject.optString("minPay", ""));
                   context.startActivity(intent);
               }
               else{
                   Intent intent = new Intent(context, WithDrawActivty2.class);
                   intent.putExtra("bankId", jsonObject.optString("id", ""));
                   intent.putExtra("bankType", jsonObject.optString("bankCnName", ""));
                   intent.putExtra("bankCode", jsonObject.optString("bankCode", ""));
                   intent.putExtra("userRealName", jsonObject.optString("userRealName", ""));
                   intent.putExtra("bankCard", jsonObject.optString("bankCard", ""));
                   intent.putExtra("bankAddress", jsonObject.optString("bankAddress", ""));
                   context.startActivity(intent);
               }
           }
       });
        return convertView;
    }

    class ViewHolder
    {
TextView bankname,banknumber,textView3;
ImageView img;
    }
}
