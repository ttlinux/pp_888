package com.a8android888.bocforandroid.activity.user.Password;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseFragmentActivity;
import com.a8android888.bocforandroid.MainActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.view.MySwitchView;

/**
 * Created by Administrator on 2017/3/29. 修改密码
 */
public class ModifyPassword extends BaseFragmentActivity{

    private FrameLayout framelayout;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private MySwitchView mySwitchView;
    MoneyPasswordFragment moneyPasswordFragment;
    NormalPasswordFragment normalPasswordFragment;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        TextView textView=FindView(R.id.title);
        textView.setText(R.string.modifypassword);

        framelayout=FindView(R.id.framelayout);
        mySwitchView=FindView(R.id.mySwitchView);
        mySwitchView.setOnChangeListener(new MySwitchView.OnChangeListener() {
            @Override
            public void OnChange(int index) {
                fragmentTransaction=fragmentManager.beginTransaction();
                if(index>0)
                    fragmentTransaction.setCustomAnimations(R.anim.slide_left_in,R.anim.slide_left_out);
                else
                    fragmentTransaction.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_right_out);
                switch (index)
                {
                    case 0:
                        fragmentTransaction.hide(moneyPasswordFragment);
                        fragmentTransaction.show(normalPasswordFragment);
                        break;
                    case 1:
                        fragmentTransaction.hide(normalPasswordFragment);
                        fragmentTransaction.show(moneyPasswordFragment);
                        break;
                }
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.slide_left_in,R.anim.slide_right_out);
         moneyPasswordFragment =new MoneyPasswordFragment();
         normalPasswordFragment=new NormalPasswordFragment();
        fragmentTransaction.add(R.id.framelayout,normalPasswordFragment);
        fragmentTransaction.add(R.id.framelayout, moneyPasswordFragment);
        fragmentTransaction.hide(moneyPasswordFragment);
        fragmentTransaction.show(normalPasswordFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
