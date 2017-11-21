package com.a8android888.bocforandroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.BaseParent.BaseFragmentActivity;
import com.a8android888.bocforandroid.Fragment.GAMEROOMFragment;
import com.a8android888.bocforandroid.Fragment.MainFragment;
import com.a8android888.bocforandroid.Fragment.UserFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotterycenter;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryorder;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryroom;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.Permission;
import com.a8android888.bocforandroid.util.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dalvik.system.DexFile;

public class MainActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener{

    FrameLayout layout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ArrayList<BaseFragment> bases=new ArrayList<BaseFragment>();
    RadioGroup bottomview;
    int tag=0;
    public static int isexit = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isexit = 1;
        setIsneedback(false);
        InitView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null && bases.size()>0)
        {
            int index=intent.getIntExtra(BundleTag.IntentTag,0);
            switch (index)
            {
                case 0:
                    setFragment(0);
                    break;
                case 1:
                    setFragment(1);
                    break;
                case 2:
                    setFragment(2);
                    break;
            }

        }
    }

    public void InitView()
    {
        layout=FindView(R.id.framelayout);
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();

        //获取com.a8android888.bocforandroid.Fragment下面的所有fragment
//        List<String > list=getClassName(getPackageName()+".Fragment");
//        for (String name:list) {
//            try {
//                BaseFragment basefragment=(BaseFragment)Class.forName(name).newInstance() ;
//                bases.add(basefragment);
////                fragmentTransaction.add(R.id.framelayout,basefragment);
//                fragmentTransaction.hide(basefragment);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        MainFragment act = new MainFragment();
//        Bundle bunle = new Bundle();
//        bunle.putString("msg", msg);
//        bunle.putString("type", type);
//        act.setArguments(bunle);
//        listFragment.add(act);
        bases.add(new MainFragment());
        fragmentTransaction.add(R.id.framelayout,bases.get(0));
        bases.add(new GAMEROOMFragment());
        fragmentTransaction.add(R.id.framelayout,bases.get(1));
        bases.add(new UserFragment());
        fragmentTransaction.add(R.id.framelayout, bases.get(2));

        if(bases.isEmpty())return;
        fragmentTransaction.hide(bases.get(0));
        fragmentTransaction.commitAllowingStateLoss();
        bottomview=(RadioGroup)findViewById(R.id.bottomview);
        bottomview.setOnCheckedChangeListener(this);
        ((RadioButton)bottomview.getChildAt(0)).performClick();
    }
    public  void setFragment(int index) {
        for (int i = 0; i < bases.size(); i++) {
            Fragment fragment = bases.get(i);

            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (index == i) {
                LogTools.e("showstateshowstate",i+"");
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commitAllowingStateLoss();
        }
    }
//    public void ShowFragment(String Classname)
//    {
//        fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.hide(bases.get(tag));
//        int count=0;
//        for (int i = 0; i < bases.size(); i++) {
//            String classname=getPackageName()+".Fragment."+Classname;
//            if(base.getClass().getName().equalsIgnoreCase(classname))
//            {
//                tag=count;
//                if(!bases.get(tag).isAdded())
//                {
//                    fragmentTransaction.add(R.id.framelayout,bases.get(tag));
//                }
//                fragmentTransaction.show(bases.get(tag));
//                break;
//            }
//            count++;
//        }
//        fragmentTransaction.commitAllowingStateLoss();
//    }

    public List<String > getClassName(String packageName){
        List<String >classNameList=new ArrayList<String >();
        try {

            DexFile df = new DexFile(this.getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = (String) enumeration.nextElement();
                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  classNameList;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.home){
            setFragment(0);
        }
        if(checkedId==R.id.game){
            setFragment(1);
        }
        if(checkedId==R.id.mine){
            setFragment(2);
        }

    }
    private long exitTime = 0;
    /**
     * 两次按返回键退出程序
     */
    private boolean mBackKeyPressed = false; // 记录是否有首次按键
    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            ToastUtil.showMessage(this, "再按一次退出程序");
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                // 延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                }
            }, 2000);
        } else {
            // 退出程序
            isexit = 2;
            finish();
            System.exit(0);
        }
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//            if((System.currentTimeMillis()-exitTime) > 2000){
//                ToastUtil.showMessage(getApplicationContext(), "再按一次退出程序");
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//                isexit = 2;
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
