package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.ModuleBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.JumpActivity;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.OnMultiClickListener;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static android.view.View.GONE;

public class acticlelist_Adapter extends BaseAdapter {
    private Context mContext;
    ArrayList<ModuleBean> mList = new ArrayList<ModuleBean>();
    String type;
    private ImageLoader mImageDownLoader;
    ImageDownLoader mImageDownLoader2;
    ArrayList<ModuleBean> gameBeanList2;

    public acticlelist_Adapter(Context context, ArrayList<ModuleBean> list, String type) {
        this.mContext = context;
        this.mList = list;
        this.type = type;
        mImageDownLoader = ((BaseApplication) context.getApplicationContext())
                .getImageLoader();
        mImageDownLoader2 = new ImageDownLoader(context);
    }

    public int getCount() {
        // TODO Auto-generated method stub+
        LogTools.ee("ddddddabbbb", mList.size() + type);
        if (type.equalsIgnoreCase("6")) {
            int vsize = mList.size() % 3 == 0 ? mList.size() / 3 : mList.size() / 3 + 1;
            return vsize;
        }
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

    public void NotifyAdapter(ArrayList<ModuleBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int index = position;
        if (type.equalsIgnoreCase("1")) {
            ViewHolder holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.module1_layout, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.contact = (TextView) convertView.findViewById(R.id.contact);
            mImageDownLoader.displayImage(mList.get(position).getArticleSmallImages(), holder.img);
//            ImageView srcid = (ImageView) convertView.findViewById(R.id.srcid);
//            mImageDownLoader.displayImage(mList.get(position).getBigBackgroundPic(), srcid);
            mImageDownLoader2.SetSpecialView(mContext, mList.get(position).getBigBackgroundPic(), convertView);
            holder.title.setText(mList.get(position).getArticleTitle());
            holder.contact.setText(mList.get(position).getArticleSubTitle());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modeluonclick(index);
                }
            });
            LogTools.ee("ddddddabbbb", mList.get(position).getArticleSmallImages());


        }
        if (type.equalsIgnoreCase("2")) {
            ViewHolder holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.module2_layout, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);

            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.contact = (TextView) convertView.findViewById(R.id.contact);
//            ImageView srcid = (ImageView) convertView.findViewById(R.id.srcid);
            mImageDownLoader2.SetSpecialView(mContext, mList.get(position).getBigBackgroundPic(), convertView);
//            mImageDownLoader.displayImage(mList.get(position).getBigBackgroundPic(), srcid);
            mImageDownLoader.displayImage(mList.get(position).getArticleSmallImages(), holder.img);
            holder.title.setText(mList.get(position).getArticleTitle());
            holder.contact.setText(mList.get(position).getArticleSubTitle());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modeluonclick(index);
                }
            });
        }
        if (type.equalsIgnoreCase("3")) {
            ViewHolder holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.module3_layout, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.contact = (TextView) convertView.findViewById(R.id.contact);
//            ImageView srcid = (ImageView) convertView.findViewById(R.id.srcid);
//            mImageDownLoader.displayImage(mList.get(position).getBigBackgroundPic(), srcid);
            mImageDownLoader2.SetSpecialView(mContext, mList.get(position).getBigBackgroundPic(), convertView);
            mImageDownLoader.displayImage(mList.get(position).getArticleSmallImages(), holder.img);
            holder.contact.setText(mList.get(position).getArticleSubTitle());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modeluonclick(index);
                }
            });
        }
        if (type.equalsIgnoreCase("4")) {
            ViewHolder holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.module5_layout, null);

            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.contact = (TextView) convertView.findViewById(R.id.contact);
//            ImageView srcid = (ImageView) convertView.findViewById(R.id.srcid);
//            mImageDownLoader.displayImage(mList.get(position).getBigBackgroundPic(), srcid);
            mImageDownLoader2.SetSpecialView(mContext,mList.get(position).getBigBackgroundPic(),convertView);
            mImageDownLoader2.showImage(mContext, mList.get(position).getArticleSmallImages(), holder.img, -1);
            holder.title.setText(mList.get(position).getArticleTitle());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modeluonclick(index);
                }
            });
        }
        if (type.equalsIgnoreCase("5")) {
            ViewHolder holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.module4_layout, null);

            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.contact = (TextView) convertView.findViewById(R.id.contact);
            holder.title.setVisibility(GONE);
            holder.contact.setVisibility(GONE);
//            ImageView srcid = (ImageView) convertView.findViewById(R.id.srcid);
//            mImageDownLoader.displayImage(mList.get(position).getBigBackgroundPic(), srcid);
            mImageDownLoader2.SetSpecialView(mContext,mList.get(position).getBigBackgroundPic(),convertView);
            mImageDownLoader2.showImage(mContext, mList.get(position).getArticleSmallImages(), holder.img, 0);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modeluonclick(index);
                }
            });


        }
        if (type.equalsIgnoreCase("6")) {
            int tempcount = (position + 1) * 3 > mList.size() ? mList.size() % 3 : 3;
            LinearLayout hor = new LinearLayout(mContext);
            hor.setOrientation(LinearLayout.HORIZONTAL);
            hor.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < tempcount; j++) {
                View popupView = View.inflate(mContext, R.layout.real_video_item, null);
                int width = ScreenUtils.getScreenWH((Activity) mContext)[0] / 3;
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupView.setLayoutParams(params2);
                popupView.setTag(j + position * 3);
                TextView videoname = (TextView) popupView.findViewById(R.id.videoname);
                ImageView videoimg = (ImageView) popupView.findViewById(R.id.videoimg);
//                ImageView srcid = (ImageView) convertView.findViewById(R.id.srcid);
//                mImageDownLoader.displayImage(mList.get(j).getBigBackgroundPic(), srcid);
                mImageDownLoader2.SetSpecialView(mContext,mList.get(position).getBigBackgroundPic(),popupView);
                mImageDownLoader.displayImage(mList.get(j).getArticleSmallImages(), videoimg);
                videoname.setText(mList.get(j).getArticleTitle());
                popupView.setOnClickListener(new OnMultiClickListener() {
                    @Override
                    public void onMultiClick(View v) {
                        final int tag = Integer.valueOf(v.getTag() + "");
                        modeluonclick(tag);
                    }
                });
                hor.addView(popupView);
            }
            convertView = hor;
        }


        return convertView;
    }

    public void modeluonclick(int position) {
        JumpActivity.modeluonclick(mContext, mList.get(position).getLinkType(),
                mList.get(position).getOpenLinkType(), mList.get(position).getTypeCode(),
                mList.get(position).getLevel(), mList.get(position).getCateCode(),
                mList.get(position).getGameCode(), mList.get(position).getArticleName(),
                mList.get(position).getArticleTitle(), mList.get(position).getLinkUrl()
                , mList.get(position).getArticleType(), mList.get(position).getArticleId(), "nopull", "2", mList.get(position).getArticleId());

    }

    class ViewHolder {
        TextView title, contact;
        ImageView img;
    }


}
