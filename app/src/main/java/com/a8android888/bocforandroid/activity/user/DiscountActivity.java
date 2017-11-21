package com.a8android888.bocforandroid.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.view.MyViewPager;

/**
 * Created by Administrator on 2017/3/29.优惠活动
 */
public class DiscountActivity extends BaseActivity{

    LinearLayout toplayout;
    MyViewPager myviewpager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_discount);
        toplayout=FindView(R.id.framelayout);
        myviewpager=FindView(R.id.myviewpager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
