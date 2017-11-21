package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class MainPagerAdapter extends PagerAdapter{

    String[] strs;
    Context co;
    ArrayList<RelativeLayout> relativeLayouts=new ArrayList<RelativeLayout>();
    public SparseArray<View> mListViews=new SparseArray<View>();
    private ImageLoader mImageDownLoader;
    OnClickItemListener onClickItemListener;

    public OnClickItemListener getOnClickItemListener() {
        return onClickItemListener;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public MainPagerAdapter(Context co,String[] strs)
    {
        this.co=co;
        this.strs=strs;
        mImageDownLoader = ((BaseApplication) ((Activity)co).getApplication())
                .getImageLoader();
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mListViews.get(position));
        mListViews.remove(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

            if(mListViews.get(position)==null)
            {
                RelativeLayout relativeLayout=new RelativeLayout(co);
                relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ImageView imageView=new ImageView(co);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setTag(position);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onClickItemListener!=null)onClickItemListener.Onclick(Integer.valueOf(v.getTag()+""));
                    }
                });
                mImageDownLoader.displayImage(strs[position], imageView, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        if(bitmap==null)return;
                        int width = ScreenUtils.getScreenWH((Activity) co)[0]+2;
                        double height = 0.001 * width / bitmap.getWidth() * 1000.00 * bitmap.getHeight();
                        LogTools.e("bitmap22", bitmap.getWidth() + " " + bitmap.getHeight() + " " + height);
                        ImageView image = (ImageView) view;
                        image.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, (int) height));
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {

                    }
                });
//            imageView.setImageDrawable(co.getResources().getDrawable(R.drawable.test));
                relativeLayout.addView(imageView);
                container.addView(relativeLayout);
                mListViews.put(position, relativeLayout);
            }


        return mListViews.get(position);
    }

    public interface OnClickItemListener
    {
        public void Onclick(int index);
    }
}
