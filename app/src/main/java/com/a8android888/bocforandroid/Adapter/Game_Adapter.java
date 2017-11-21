package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.GetWebdata;
import com.a8android888.bocforandroid.activity.main.gamewebActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Game_Adapter extends BaseAdapter implements Filterable {
    private Context mContext;
    ArrayList<GameBean> mList;
    private ArrayFilter mFilter;
    private final Object mLock = new Object();
    private ArrayList<GameBean> mOriginalValues;
//    private ImageDownLoader mImageDownLoader;
    private ImageLoader mImageDownLoader;
    public Game_Adapter(Context context,ArrayList<GameBean> list) {
        this.mContext = context;
        this.mList = list;
//        mImageDownLoader = new ImageDownLoader(mContext);
        mImageDownLoader = ((BaseApplication)mContext.getApplicationContext())
                .getImageLoader();
    }

    public void NotifySetChange(ArrayList<GameBean> list)
    {
        this.mList = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView== null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.game_item, null);
            convertView.setTag(holder);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.gamename);
            holder.changebox = (ImageView) convertView.findViewById(R.id.scimg);
        }   else{
            holder= (ViewHolder) convertView.getTag();
        }

//        mImageDownLoader.showImage(mContext, mList.get(position).getImg(), holder.img, 1);
        mImageDownLoader.displayImage(mList.get(position).getImg(), holder.img);
        holder.name.setText(mList.get(position).getName());

        if(mList.get(position).getHavourite().equalsIgnoreCase("true")){
            holder.changebox.setImageDrawable(mContext.getResources().getDrawable(R.drawable.compu_img));
        }
        else {
            holder.changebox.setImageDrawable(mContext.getResources().getDrawable(R.drawable.compu_img_no));
        }
        holder.changebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = ((BaseApplication) mContext.getApplicationContext()).getBaseapplicationUsername();
                if (Username == null || Username.equalsIgnoreCase("")) {
                    ToastUtil.showMessage(mContext, "未登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                getdatalist(mList.get(position).getFlat(),mList.get(position).getGamecode(), holder.changebox,position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = ((BaseApplication) mContext.getApplicationContext()).getBaseapplicationUsername();
                if (Username == null || Username.equalsIgnoreCase("")) {
                    ToastUtil.showMessage(mContext, "未登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
//                Intent intent = new Intent(mContext, gamewebActivity.class);
//                intent.putExtra("title", mList.get(position).getName());
//                intent.putExtra("flat", mList.get(position).getFlat());
//                intent.putExtra("gamecode", mList.get(position).getGamecode());
//                mContext.startActivity(intent);
                GetWebdata.GetData((Activity) mContext, mList.get(position).getFlat(), mList.get(position).getGamecode(), mList.get(position).getName(),"nopull");
                return;
            }
        });
        return convertView;
    }
    public void NotifyData(ArrayList<GameBean> payments)
    {
        this.mList=payments;
        notifyDataSetChanged();
    }
    private void getdatalist(final String flat,String gameCode,final ImageView v, final int position){
        RequestParams params = new RequestParams();
        params.put("flat",flat);
        params.put("userName",((BaseApplication)mContext.getApplicationContext()).getBaseapplicationUsername());
        params.put("client", Httputils.client_server);
        params.put("gameCode", gameCode);
//        StringBuilder sb=new StringBuilder();
//        sb.append(flat);
//        sb.append("/" + ((BaseApplication) mContext.getApplicationContext()).getBaseapplicationUsername());
//        sb.append("/" + gameCode);
//        sb.append("/" + Httputils.client_server);

        Httputils.PostWithBaseUrl(Httputils.favourite , params, new MyJsonHttpResponseHandler((Activity)mContext,true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {

                super.onSuccessOfMe(jsonObject);
                LogTools.e("aaa", jsonObject.toString());
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                if( jsonObject.optString("msg").equalsIgnoreCase("操作成功")) {
                    v.setImageDrawable(mContext.getResources().getDrawable(R.drawable.compu_img));
                    ToastUtil.showMessage(mContext,"收藏成功");
                }
                else {

                    v.setImageDrawable(mContext.getResources().getDrawable(R.drawable.compu_img_no));
                    ToastUtil.showMessage(mContext, jsonObject.optString("msg"));
                    if(jsonObject.optString("msg").equalsIgnoreCase("取消成功")){
                        if(flat.equalsIgnoreCase("favourite")) {
                            mList.remove(position);
                        }
                        NotifyData(mList);
                    }
                }
            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
                return super.parseResponse(s);
            }
        });
    }





    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    class ViewHolder {
        TextView name, account;
        ImageView changebox;
        ImageView img;
    }
    /**
     * 过滤数据的类
     */
    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     * <p/>
     * 一个带有首字母约束的数组过滤器，每一项不是以该首字母开头的都会被移除该list。
     */
    private class ArrayFilter extends Filter {
        //执行刷选
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();//过滤的结果
            //原始数据备份为空时，上锁，同步复制原始数据
            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<GameBean>(mList);
                }
            }
            //当首字母为空时
            if (prefix == null || prefix.length() == 0) {
                ArrayList<GameBean> list;
                synchronized (mLock) {//同步复制一个原始备份数据
                    list = new ArrayList<GameBean>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();//此时返回的results就是原始的数据，不进行过滤
            } else {
                String prefixString = prefix.toString().trim().toLowerCase();//转化为小写

                ArrayList<GameBean> values;
                synchronized (mLock) {//同步复制一个原始备份数据
                    values = new ArrayList<GameBean>(mOriginalValues);
                }
                final int count = values.size();
                final ArrayList<GameBean> newValues = new ArrayList<GameBean>();

                for (int i = 0; i < count; i++) {
                    final GameBean value = values.get(i);//从List<GameBean>中拿到GameBean对象
//                    final String valueText = value.toString().toLowerCase();
                    final String valueText = value.getName().toString().trim().toLowerCase();//GameBean对象的name属性作为过滤的参数
                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString) || valueText.indexOf(prefixString.toString().trim()) != -1) {//第一个字符是否匹配
                        newValues.add(value);//将这个item加入到数组对象中
                    } else {//处理首字符是空格
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {//一旦找到匹配的就break，跳出for循环
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;//此时的results就是过滤后的List<GameBean>数组
                results.count = newValues.size();
            }
            return results;
        }

        //刷选结果
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            //noinspection unchecked
            mList = (ArrayList<GameBean>) results.values;//此时，Adapter数据源就是过滤后的Results
            if (results.count > 0) {
                notifyDataSetChanged();//这个相当于从mList中删除了一些数据，只是数据的变化，故使用notifyDataSetChanged()
            } else {
                /**
                 * 数据容器变化 ----> notifyDataSetInValidated

                 容器中的数据变化  ---->  notifyDataSetChanged
                 */
                notifyDataSetInvalidated();//当results.count<=0时，此时数据源就是重新new出来的，说明原始的数据源已经失效了
            }
        }
    }
}
