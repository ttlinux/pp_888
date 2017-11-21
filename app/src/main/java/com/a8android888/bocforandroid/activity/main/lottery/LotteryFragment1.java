package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.SportsViewPagerAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_1DWFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_1ZHFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_2DWFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_2HSFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_2ZHFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_3DWFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_KDFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_LHFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_SMFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_SZPFragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_ZX3Fragment;
import com.a8android888.bocforandroid.activity.main.lottery.CCSSC.CCSSC_ZX6Fragment;
import com.a8android888.bocforandroid.activity.main.lottery.Locky28.Canada28;
import com.a8android888.bocforandroid.activity.main.lottery.Locky28.Lucky28;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.BBFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.GGFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.HXFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.LMFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.LXFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.TMFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.TXTWFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.WLSFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.YXYZTWFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.ZM1_6Fragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.ZMFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.ZTMFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lotteryfagmentsix.ZYBZFragment;
import com.a8android888.bocforandroid.activity.main.lottery.fucai3D.BSGDWFragment;
import com.a8android888.bocforandroid.activity.main.lottery.fucai3D.DWFragment;
import com.a8android888.bocforandroid.activity.main.lottery.fucai3D.ESZHFragment;
import com.a8android888.bocforandroid.activity.main.lottery.fucai3D.HSFragment;
import com.a8android888.bocforandroid.activity.main.lottery.fucai3D.KDFragment;
import com.a8android888.bocforandroid.activity.main.lottery.fucai3D.ZHFragment;
import com.a8android888.bocforandroid.activity.main.lottery.fucai3D.ZX3Fragment;
import com.a8android888.bocforandroid.activity.main.lottery.fucai3D.ZX6Fragment;
import com.a8android888.bocforandroid.activity.main.lottery.klsf.KLD1_8Fragment;
import com.a8android888.bocforandroid.activity.main.lottery.klsf.ZHLHFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.D10MFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.D4MFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.D5MFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.D6MFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.D7MFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.D8MFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.D9MFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.GJFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.GYJHFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.JJFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.SMFragment;
import com.a8android888.bocforandroid.activity.main.lottery.lotterypk.YJFragment;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpForLottery;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.dialog;
import com.a8android888.bocforandroid.view.LotteryDialog;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/5. 投注
 */
public class LotteryFragment1 extends BaseFragment {
    ViewPager viewpager;
    TextView qs, time;
    private int CurrentPage = 0;
    private ArrayList<BaseFragment> basefragments;
    private static String gamecode = "";
//    private static String gameid = "";
//    private static String gameitemid = "";
    private static String gameitemcode = "";
    private ThreadTimer mTimer;
    BaseApplication baseApplication;
    LotteryDialog lotteryDialog;
    ImageView shadows;
    RelativeLayout shadows_layout;
    private int res[]={R.drawable.kaijiang,R.drawable.weihu,R.drawable.fengpan};

    public static String getGameitemname() {
        return gameitemname;
    }

    public static void setGameitemname(String gameitemname) {
        LotteryFragment1.gameitemname = gameitemname;
    }

    private static String gameitemname = "";
    private static String gamename = "";
    private static String ganmeitmexzlxname = "";
    private static String ganmeitmexzlxcode = "";

    public static String getGanmeitmexzlxcode() {
        return ganmeitmexzlxcode;
    }

    public static void setGanmeitmexzlxcode(String ganmeitmexzlxcode) {
        LotteryFragment1.ganmeitmexzlxcode = ganmeitmexzlxcode;
    }

    public static String getGanmeitmexzlxname() {
        return ganmeitmexzlxname;
    }

    public static void setGanmeitmexzlxname(String ganmeitmexzlxname) {
        LotteryFragment1.ganmeitmexzlxname = ganmeitmexzlxname;
    }

    public static String getGamename() {
        return gamename;
    }

    public static void setGamename(String gamename) {
        LotteryFragment1.gamename = gamename;
    }

    public static String getGamecode() {
        return gamecode;
    }

    public static void setGamecode(String gamecode) {
        LotteryFragment1.gamecode = gamecode;
    }

//    public static String getGameid() {
//        return gameid;
//    }
//
//    public static void setGameid(String gameid) {
//        LotteryFragment1.gameid = gameid;
//    }

//    public static String getGameitemid() {
//        return gameitemid;
//    }
//
//    public static void setGameitemid(String gameitemid) {
//        LotteryFragment1.gameitemid = gameitemid;
//    }

    public static String getGameitemcode() {
        return gameitemcode;
    }

    public static void setGameitemcode(String gameitemcode) {
        LotteryFragment1.gameitemcode = gameitemcode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.lotteryfragement1_layout, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager = FindView(R.id.viewpager);
        qs = (TextView) FindView(R.id.qs);
        time = (TextView) FindView(R.id.time);
        lotteryDialog = new LotteryDialog(getActivity());
        shadows = FindView(R.id.shadows);
        shadows_layout=FindView(R.id.shadows_layout);
        //有用别删掉 挡住点击事件
        shadows_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setGamecode(((Lottery_MainActivity) getActivity()).getLotterycode());
//        setGameid(((Lottery_MainActivity) getActivity()).getLotteryid());
        setGamename(((Lottery_MainActivity) getActivity()).getLotteryname());
//        setGameitemid(((Lottery_MainActivity) getActivity()).getLotteryitmeid());
        setGameitemcode(((Lottery_MainActivity) getActivity()).getLotteryitmeidcode());
        setGameitemname(((Lottery_MainActivity) getActivity()).getLotteryitmeidname());
        setGanmeitmexzlxcode(((Lottery_MainActivity) getActivity()).getLotteryitmexzlxcode());
        setGanmeitmexzlxname(((Lottery_MainActivity) getActivity()).getLotteryitmexzlxname());

        Lottery_MainActivity mainActivity = (Lottery_MainActivity) getActivity();
        if (mainActivity != null) mainActivity.setNoOpenData(true);
        HttpForLottery.lotteryopendata(this.getActivity(), LotteryFragment1.getGamecode(),
                LotteryFragment1.getGameitemcode(), LotteryFragment1.getGameitemcode(), new HttpForLottery.OnDataReceviceListener() {
                    @Override
                    public void OnRecevice(int status) {
LogTools.e("点击返回了",status+"状态");
//                        gettime(baseApplication.getClosetime(), baseApplication.getOpentime());
                        if(status>1)
                        {
                            shadows_layout.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams relativeLayout=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            shadows_layout.setLayoutParams(relativeLayout);
                            shadows.setImageDrawable(getResources().getDrawable(res[1]));
                        }
                        else {
                            Lottery_MainActivity mainActivity = (Lottery_MainActivity) getActivity();
                            if (mainActivity != null) {
                                baseApplication = (BaseApplication) mainActivity.getApplication();
                                qs.setText("当前第 " + baseApplication.getCurrentQs() + " 期");
                                mainActivity.setNoOpenData(false);
//                            mainActivity.SetRadioGroupEnable();
                            }
                            getfragement();
                        }
                    }
                });
    }

    public void gettime(long close, long nowtime) {
        BaseApplication.opentime = BaseApplication.nowtime + nowtime / 1000;
        BaseApplication.closetime = BaseApplication.nowtime + close / 1000;
        SpannableStringBuilder builder = new SpannableStringBuilder(qs.getText().toString().trim());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.lottery));
        if(qs.getText().toString().length()>0)
        {
            builder.setSpan(redSpan, 4, qs.getText().toString().length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            qs.setText(builder);
        }
//        LogTools.e("最后的时间" + baseApplication.getClosetime() + "开奖时间" + baseApplication.getOpentime(), "当前时间" + baseApplication.getNowtime());
        SpannableStringBuilder builder2;
        if (close <= 0) {
            String text = "距离开奖还有 " + Httputils.getStringToDate(nowtime);
            builder2 = new SpannableStringBuilder(text);
            ForegroundColorSpan redSpan2= new ForegroundColorSpan(getResources().getColor(R.color.lottery));
            builder2.setSpan(redSpan2,7,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            time.setText(builder2);
            if (nowtime == 0) {
                shadows_layout.setVisibility(View.GONE);
                RelativeLayout.LayoutParams relativeLayout=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                relativeLayout.addRule(RelativeLayout.BELOW,R.id.toplayout);
                shadows_layout.setLayoutParams(relativeLayout);
                RefreshOpenData();
            } else {
                shadows_layout.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams relativeLayout=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                relativeLayout.addRule(RelativeLayout.BELOW,R.id.toplayout);
                shadows_layout.setLayoutParams(relativeLayout);
                shadows.setImageDrawable(getResources().getDrawable(res[2]));
            }
        } else {
//            if (close == 0) {
//                shadows_layout.setVisibility(View.VISIBLE);
//                shadows.setText("正在封盘");
//            }
            String text = "距离封盘还有 " + Httputils.getStringToDate(close);
            builder2 = new SpannableStringBuilder(text);
            ForegroundColorSpan redSpan2= new ForegroundColorSpan(getResources().getColor(R.color.lottery));
            builder2.setSpan(redSpan2,7,text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            time.setText(builder2);
        }

    }

    public void RefreshOpenData() {
        if (getActivity() == null) return;
        HttpForLottery.lotteryopendata(this.getActivity(), LotteryFragment1.getGamecode(),
                LotteryFragment1.getGameitemcode(), LotteryFragment1.getGameitemcode(), new HttpForLottery.OnDataReceviceListener() {
                    @Override
                    public void OnRecevice(int status) {
                        if(status>1)
                        {
                            shadows_layout.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams relativeLayout=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            shadows_layout.setLayoutParams(relativeLayout);
                            shadows.setImageDrawable(getResources().getDrawable(res[1]));
                        }
                        else {
                            baseApplication = (BaseApplication) getActivity().getApplication();
                            qs.setText("当前第 " + baseApplication.getCurrentQs() + " 期");
                            basefragments.get(viewpager.getCurrentItem()).CallbackFunction();
                        }
//                        gettime(baseApplication.getClosetime(), baseApplication.getOpentime());
                    }
                });
    }

    private void getfragement() {
        String tag = getArguments().getString(BundleTag.FragmentTag, "HK6");
        basefragments = new ArrayList<BaseFragment>();
        if (tag.equalsIgnoreCase("HK6")) {
            ////////////////////////////////////////////////六合彩
            basefragments.add(new TMFragment());
            basefragments.add(new ZTMFragment());
            basefragments.add(new ZMFragment());
            basefragments.add(new ZM1_6Fragment());
            basefragments.add(new GGFragment());
            basefragments.add(new LMFragment());
            basefragments.add(new BBFragment());
            basefragments.add(new YXYZTWFragment());
            basefragments.add(new TXTWFragment());
            basefragments.add(new ZYBZFragment());
            basefragments.add(new WLSFragment());
            basefragments.add(new LXFragment());
            basefragments.add(new HXFragment());
        } else if (tag.equalsIgnoreCase("FC3D") || tag.equalsIgnoreCase("PL3")) {
            ////////////////////////////////////////////////福彩3d//////////排列3
            basefragments.add(new DWFragment());
            basefragments.add(new ZHFragment());
            basefragments.add(new HSFragment());
            basefragments.add(new ZX3Fragment());
            basefragments.add(new ZX6Fragment());
            basefragments.add(new KDFragment());
            basefragments.add(new BSGDWFragment());
            basefragments.add(new ESZHFragment());
        } else if (tag.equalsIgnoreCase("CQSSC") || tag.equalsIgnoreCase("TJSSC") || tag.equalsIgnoreCase("XJSSC")) {
//////////////////////////////重庆时时彩///////天津时时彩///////新疆时时彩
            basefragments.add(new CCSSC_SMFragment());
            basefragments.add(new CCSSC_SZPFragment());
            basefragments.add(new CCSSC_1DWFragment());
            basefragments.add(new CCSSC_2DWFragment());
            basefragments.add(new CCSSC_3DWFragment());
            basefragments.add(new CCSSC_1ZHFragment());
            basefragments.add(new CCSSC_2ZHFragment());
            basefragments.add(new CCSSC_2HSFragment());
            basefragments.add(new CCSSC_ZX3Fragment());
            basefragments.add(new CCSSC_ZX6Fragment());
            basefragments.add(new CCSSC_KDFragment());
            basefragments.add(new CCSSC_LHFragment());
        } else if (tag.equalsIgnoreCase("GDKLSF") || tag.equalsIgnoreCase("TJKLSF")) {
            /////////////////////////广东快乐十分/////天津快乐十分
            basefragments.add(new KLD1_8Fragment());
            basefragments.add(new ZHLHFragment());
        } else if (tag.equalsIgnoreCase("BJPK10")) {
            /////////////////////////北京PK拾
            basefragments.add(new SMFragment());
            basefragments.add(new GJFragment());
            basefragments.add(new YJFragment());
            basefragments.add(new JJFragment());
            basefragments.add(new D4MFragment());
            basefragments.add(new D5MFragment());
            basefragments.add(new D6MFragment());
            basefragments.add(new D7MFragment());
            basefragments.add(new D8MFragment());
            basefragments.add(new D9MFragment());
            basefragments.add(new D10MFragment());
            basefragments.add(new GYJHFragment());
        } else if (tag.equalsIgnoreCase("BJKL8")) {
            /////////////////////////幸运28
            basefragments.add(new Lucky28());
        } else if (tag.equalsIgnoreCase("CAKENO")) {
            /////////////////////////加拿大28
            basefragments.add(new Canada28());
        }
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(basefragments.size());
        SportsViewPagerAdapter sportsadapter = new SportsViewPagerAdapter(getChildFragmentManager(), basefragments);
        viewpager.setAdapter(sportsadapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CurrentPage = position;
                LogTools.e("dianjaa", "" + CurrentPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
//        closeTimer();
//        LogTools.e("ddaafafdafdsfsdfasd", "onPause");
    }

    private void closeTimer() {
        if (mTimer != null) {
            mTimer.Stop();
            mTimer = null;
        }
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void clickfragment() {
        super.clickfragment();
//        setGameitemid(((Lottery_MainActivity) getActivity()).getLotteryitmeid());
        setGameitemname(((Lottery_MainActivity) getActivity()).getLotteryitmeidname());
        setGameitemcode(((Lottery_MainActivity) getActivity()).getLotteryitmeidcode());
        setGanmeitmexzlxcode(((Lottery_MainActivity) getActivity()).getLotteryitmexzlxcode());
        setGanmeitmexzlxname(((Lottery_MainActivity) getActivity()).getLotteryitmexzlxname());
        int tag = ((Lottery_MainActivity) getActivity()).getLotteryitmextag();
        LogTools.e("点击选择了dianjbb", "" + tag);
        String tag2 = getArguments().getString(BundleTag.FragmentTag, "HK6");
        if (tag2.equalsIgnoreCase("TJKLSF") || tag2.equalsIgnoreCase("GDKLSF")) {
            if (tag > 7) {
                viewpager.setCurrentItem(1);
                basefragments.get(1).clickfragment();
            } else {
                viewpager.setCurrentItem(0);
                basefragments.get(0).clickfragment();
            }
        } else {
            if (LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("BSDW") || LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("BGDW")
                    || LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("SGDW") || LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("BSGDW")) {
                viewpager.setCurrentItem(6);
                basefragments.get(6).clickfragment();
            }
           else if (LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("EZZH") || LotteryFragment1.getGanmeitmexzlxcode().equalsIgnoreCase("SZZH")) {
                viewpager.setCurrentItem(7);
                basefragments.get(7).clickfragment();
            }
          else {
             viewpager.setCurrentItem(tag);
             basefragments.get(tag).clickfragment();
            }
        }
    }

}
