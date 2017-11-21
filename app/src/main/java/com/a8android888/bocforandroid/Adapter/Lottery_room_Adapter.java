package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.BallTypeBean;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_MainActivity;
import com.a8android888.bocforandroid.activity.main.game.GameFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryroom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Lottery_room_Adapter extends BaseAdapter {
    private Context mContext;
    List<GameBean> mList=new ArrayList<GameBean>();
    private ImageLoader mImageDownLoader;
    private ImageDownLoader mImageDownLoader2;
    public Lottery_room_Adapter(Context context, List<GameBean> list) {
        this.mContext = context;
        this.mList = list;
             mImageDownLoader = ((BaseApplication)context.getApplicationContext())
                .getImageLoader();
        mImageDownLoader2 = new ImageDownLoader(mContext);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        int count=mList.size()%4;
        return count==0?mList.size():(4-count)+mList.size();
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
                    R.layout.real_video_item, null);
            convertView.setTag(holder);
            holder.videoimg = (ImageView) convertView.findViewById(R.id.videoimg);
            LinearLayout.LayoutParams lauout=new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(mContext, 50), ScreenUtils.getDIP2PX(mContext,50));
            holder.videoimg.setLayoutParams(lauout);
            holder.videoname = (TextView) convertView.findViewById(R.id.videoname);
        }
        else{
        holder= (ViewHolder) convertView.getTag();
    }
        if(position>=mList.size())
        {
            LinearLayout ll=(LinearLayout)convertView;
            for (int i = 0; i <ll.getChildCount(); i++) {
                View view=ll.getChildAt(i);
                view.setVisibility(View.INVISIBLE);
            }
        }
        else
        {
            LinearLayout ll=(LinearLayout)convertView;
            for (int i = 0; i <ll.getChildCount(); i++) {
                View view=ll.getChildAt(i);
                view.setVisibility(View.VISIBLE);
            }
            holder.videoname.setText(mList.get(position).getName());
            holder.srcid = (ImageView) convertView.findViewById(R.id.srcid);
            mImageDownLoader2.SetSpecialView(mContext, mList.get(position).getBigBackgroundPic(), convertView);

            mImageDownLoader.displayImage(mList.get(position).getImg(),  holder.videoimg);
//            mImageDownLoader.loadImage(mList.get(position).getImg(), new ImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String s, View view) {
//
//                }
//
//                @Override
//                public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//                }
//
//                @Override
//                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                    if (bitmap != null) {
//                        Drawable drawable = new BitmapDrawable(bitmap);
//                        //(int)(bitmap.getWidth()*1.5f)
//                        holder.videoimg.setBackgroundDrawable(drawable);
//                    }
//                }
//
//                @Override
//                public void onLoadingCancelled(String s, View view) {
//
//                }
//            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Lottery_MainActivity.class);
                    intent.putExtra("title", mList.get(position).getName());
                    intent.putExtra("code", mList.get(position).getGamecode());
                    mContext.startActivity(intent);

                }
            });
        }

        return convertView;
    }
        class ViewHolder {
        TextView videoname;
        ImageView videoimg,srcid;
    }

}
