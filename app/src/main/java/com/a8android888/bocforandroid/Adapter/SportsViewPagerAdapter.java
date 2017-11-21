package com.a8android888.bocforandroid.Adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.a8android888.bocforandroid.BaseParent.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/5.
 */
public class SportsViewPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<BaseFragment> fragments;
    public SportsViewPagerAdapter(FragmentManager fm,ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
