package com.a8android888.bocforandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.GetWebdata;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_MainActivity;
import com.a8android888.bocforandroid.activity.main.bbinwebActivity;
import com.a8android888.bocforandroid.activity.main.game.GameFragment;
import com.a8android888.bocforandroid.activity.main.gamewebActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class BBIN_Adapter extends BaseAdapter {
    private Context mContext;
    List<GameBean> mList=new ArrayList<GameBean>();
    ArrayList<String> mList2 ,mList3;
    private ImageLoader mImageDownLoader;
    private ImageDownLoader mImageDownLoader2;
    String type;//1是真人视讯2是电子游戏3是体育赛事4是棋牌游戏
    public BBIN_Adapter(Context context, List<GameBean> list, ArrayList<String> list2, ArrayList<String> list3, String type) {
        this.mContext = context;
        this.mList = list;
        this.mList2=list2;
        this.mList3=list3;
        this.type=type;
        mImageDownLoader2 = new ImageDownLoader(mContext);
        mImageDownLoader = ((BaseApplication)context.getApplicationContext())
                .getImageLoader();
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
                    R.layout.bbin_item, null);
            convertView.setTag(holder);
        }   else{
        holder= (ViewHolder) convertView.getTag();
    }
        holder.videoname = (TextView) convertView.findViewById(R.id.videoname);
        holder.videoname.setText( mList.get(position).getName());
        holder.videoimg = (ImageView) convertView.findViewById(R.id.videoimg);
//        mImageDownLoader.showImage(mContext, mList.get(position).getImg(), holder.videoimg, 1);
        ImageView srcid = (ImageView) convertView.findViewById(R.id.srcid);

        mImageDownLoader.displayImage(mList.get(position).getImg(), holder.videoimg);
        mImageDownLoader2.SetSpecialView(mContext, mList.get(position).getBigBackgroundPic(), convertView);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = ((BaseApplication) mContext.getApplicationContext()).getBaseapplicationUsername();
                if (Username == null || Username.equalsIgnoreCase("")) {
//                    ToastUtil.showMessage(mContext, "未登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                    return;
                }
                GetWebdata.GetData(mContext,mList.get(position).getFlat(),  mList.get(position).getGamecode(), mList.get(position).getName(), "nopull");
                return;
//                    Intent intent = new Intent(mContext, bbinwebActivity.class);
//                    intent.putExtra("title", mList.get(position).getName());
//                    intent.putExtra("flat", mList.get(position).getFlat());
//                    intent.putExtra("gamecode", mList.get(position).getGamecode());
//                    mContext.startActivity(intent);
            }
        });
        return convertView;
    }
        class ViewHolder {
        TextView videoname;
        ImageView videoimg;
    }
}
