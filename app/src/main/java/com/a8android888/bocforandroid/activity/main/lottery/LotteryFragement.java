package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.SportsViewPagerAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.BaseParent.BaseFragmentActivity;
import com.a8android888.bocforandroid.Bean.BallTypeBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.view.MyStyleView;
import com.a8android888.bocforandroid.view.PublicDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */
public class LotteryFragement extends BaseFragmentActivity implements View.OnClickListener{

    private  TextView title;
    private RadioGroup radiogroup;
    int ScreenWidth;
    ImageView back;
    ImageView triangle;
    PublicDialog publicdialog;
    // 获取用户位置信息
    public  List<Fragment> listFragment = null;
    private  FragmentManager fm = null;
    private  int currFragmentIndex = 0;
    private  int fragmentViewId = -1;
    public FragmentTransaction mFragmentTransaction;
    public FragmentManager fragmentManager;
    int indexa=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_main);
        InitView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String Username = ((BaseApplication) LotteryFragement.this.getApplication()).getBaseapplicationUsername();
        if (Username == null || Username.equalsIgnoreCase("")) {
            radiogroup.getChildAt(0).performClick();
        }
        else
        {
            LogTools.e("indexa", indexa + "");
            Fragment fragment = listFragment.get(indexa);
            FragmentTransaction ft = getAnimFragmentTransaction(indexa);
            listFragment.get(currFragmentIndex).onPause();
            if (fragment.isAdded()) {
                // 启动目标tab的onResume()

                fragment.onResume();
            } else {
                ft.add(fragmentViewId, fragment);
            }
            // 显示目标tab
            setFragment(indexa);
            ft.commitAllowingStateLoss();
        }
    }

    private void InitView()
    {
        initFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(fragmentViewId, listFragment.get(0));
        ft.commit();
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        radiogroup=FindView(R.id.radiogroup);
        title=FindView(R.id.title);
        title.setText("购彩大厅");
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (group.getChildAt(i).getId() == checkedId) {
                        indexa=i;
                        if (i == 2) {
                            String Username = ((BaseApplication) LotteryFragement.this.getApplication()).getBaseapplicationUsername();
                            if (Username == null || Username.equalsIgnoreCase("")) {
                                Intent intent = new Intent( LotteryFragement.this, LoginActivity.class);
                                LotteryFragement.this.startActivityForResult(intent, 112);
                                return;
                            }
                        }

                        Fragment fragment = listFragment.get(i);
                        FragmentTransaction ft = getAnimFragmentTransaction(i);
                        listFragment.get(currFragmentIndex).onPause();
                        if (fragment.isAdded()) {
                            // 启动目标tab的onResume()

                            fragment.onResume();
                        } else {
                            ft.add(fragmentViewId, fragment);
                        }
                        // 显示目标tab
                        setFragment(i);
                        ft.commitAllowingStateLoss();
                    }

                }
            }
        });

    }

    /**
     * 初始化Fragment
     */
    @SuppressWarnings("unchecked")
    private void initFragment() {
        fragmentViewId = R.id.frameLayout;
        fm = this.getSupportFragmentManager();
        listFragment = new ArrayList<Fragment>();
//        MainFragment act = new MainFragment();
//        Bundle bunle = new Bundle();
//        bunle.putString("msg", msg);
//        bunle.putString("type", type);
//        act.setArguments(bunle);
//        listFragment.add(act);
        listFragment.add(new Lotteryroom());
        listFragment.add(new Lotterycenter());
        listFragment.add(new Lotteryorder());
    }
    public  void setFragment(int index) {
        for (int i = 0; i < listFragment.size(); i++) {
            Fragment fragment = listFragment.get(i);

            FragmentTransaction ft = getAnimFragmentTransaction(index);
            if (index == i) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }

        // 维护当前显示的listFragment的Fragment的index
        currFragmentIndex = index;

        title.setText(this.getResources().getStringArray(R.array.lotterytitle)[currFragmentIndex]);
    }

    /**
     * 获取一个带动画的FragmentTransaction
     */
    private  FragmentTransaction getAnimFragmentTransaction(int index) {
        FragmentTransaction ft = fm.beginTransaction();
//
//		// 设置切换动画
//		if (index > currFragmentIndex) {
//			ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
//		} else {
//			ft.setCustomAnimations(R.anim.slide_right_in,
//					R.anim.slide_right_out);
//		}

        return ft;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
