package com.a8android888.bocforandroid.activity.main.lottery.Locky28;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryComitOrderActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragment1;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.WheelDialog;
import com.a8android888.bocforandroid.view.WheelDialogWith3;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

import kankan.wheel.widget.WheelView;

/**
 * Created by Administrator on 2017/4/25.
 */
public class Lucky28 extends BaseFragment implements View.OnClickListener{

    LinearLayout mainview;
    public SparseArray<LotterycomitorderBean> sparseArray=new SparseArray<LotterycomitorderBean>();//数字
    public SparseArray<LotterycomitorderBean> lotterybeans=new SparseArray<LotterycomitorderBean>();//中文
    private ArrayList<TextView> textviews=new ArrayList<TextView>();
    private ArrayList<CheckBox> checkboxs=new ArrayList<CheckBox>();
    TextView Speacialtextview;
    private String index[][];
    private RadioButton reset,comit;
    int specialwidth;
    WheelDialogWith3 dialog;
    int indexlist[];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setResumeCallBack(true);
        return View.inflate(getActivity(), R.layout.tmfragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int pading=ScreenUtils.getDIP2PX(getActivity(), 10);
        specialwidth=(ScreenUtils.getScreenWH(getActivity())[0]-pading*2-2)/4;
        View lsitview2 = FindView(R.id.lsitview2);
        lsitview2.setVisibility(View.GONE);
        View lsitview = FindView(R.id.lsitview);
        lsitview.setVisibility(View.GONE);
        ScrollView scrollview=FindView(R.id.mainview);
        scrollview.setVisibility(View.VISIBLE);
        mainview =(LinearLayout)scrollview.getChildAt(0);
        scrollview.setPadding(1,1,1,1);
        scrollview.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.comit_order_codebag));
        reset=FindView(R.id.reset);
        reset.setOnClickListener(this);
        comit=FindView(R.id.comit);
        comit.setOnClickListener(this);

        InitTopview();
        InitBottomview();

        openTimer();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(state)
            ThreadTimer.Stop();
        else
        {
            if(getActivity()!=null && getView()!=null )
            {
                ThreadTimer.Stop(getClass().getName());
                for (int i = 0; i < checkboxs.size(); i++) {
                    checkboxs.get(i).setChecked(false);
                }
                openTimer();
            }
        }
    }

    @Override
    public void clickfragment() {
        super.clickfragment();
    }

    private void GetData()
    {
        RequestParams params = new RequestParams();
        params.put("gameCode", LotteryFragment1.getGamecode());
        params.put("itemCode", LotteryFragment1.getGameitemcode());
        Httputils.PostWithBaseUrl(Httputils.refreshRate, params, new MyJsonHttpResponseHandler(getActivity(), Lottery_MainActivity.needdialog()) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                if (getActivity() == null || !jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    LogTools.e("getActivity() == null", "getActivity() == null");
                    return;
                }

                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                ArrayList<LotterycomitorderBean> jsonjects = null;
                int count = 1;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsobject = jsonArray.optJSONObject(i);

                    LotterycomitorderBean gameBean1 = new LotterycomitorderBean();
                    gameBean1.setUid(jsobject.optString("id", ""));
                    gameBean1.setNumber(jsobject.optString("number", ""));
                    gameBean1.setPl(jsobject.optString("pl", "1"));
                    gameBean1.setMinLimit(jsobject.optString("minLimit", "1"));
                    gameBean1.setMaxlimit(jsobject.optString("maxLimit", "1"));
                    gameBean1.setMaxPeriodLimit(jsobject.optString("maxPeriodLimit", "1"));
                    gameBean1.setSelected(false);

                    if (Httputils.isNumber(gameBean1.getNumber())) {
                        int index = Integer.valueOf(gameBean1.getNumber());
                        sparseArray.put(index, gameBean1);
                    } else {
                        if (gameBean1.getNumber().equalsIgnoreCase("特码包三")) {
                            lotterybeans.put(0, gameBean1);
                        } else {
                            lotterybeans.put(count++, gameBean1);
                        }
                    }
                }
                Setdata();
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);

            }
        });
    }


    private void InitTopview() {
        Addtitles();
        for (int i = 0; i < 14; i++) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params=  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin=1;
            linearLayout.setLayoutParams(params);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.gray8));

            linearLayout.addView(MakeTextView(true, 0xFFFFFFFF));
            linearLayout.addView(MakeCheckbox());
            linearLayout.addView(MakeTextView(true, 0xFFFFFFFF));
            linearLayout.addView(MakeCheckbox());
            mainview.addView(linearLayout);
        }

    }

    private void InitBottomview() {
        RelativeLayout relativeLayout=new RelativeLayout(getActivity());
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        relativeLayout.setBackgroundColor(0xFFFFFFFF);

//        ImageView imageView=new ImageView(getActivity());
//        LinearLayout.LayoutParams layoutParams=  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,1);
//        layoutParams.topMargin=20;
//        imageView.setLayoutParams(layoutParams);
//        imageView.setBackgroundColor(getResources().getColor(R.color.gray8));
//        relativeLayout.addView(imageView);
        mainview.addView(relativeLayout);

//        Addtitles();
        MakespeacialViewIntoMainView();

        for (int i = 0; i < 7; i++) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params=  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin=1;
            linearLayout.setLayoutParams(params);
            linearLayout.setBackgroundColor(getResources().getColor(R.color.gray8));

            linearLayout.addView(MakeTextView(false,0xFFFFFFFF));
            linearLayout.addView(MakeCheckbox());
            linearLayout.addView(MakeTextView(false,0xFFFFFFFF));
            linearLayout.addView(MakeCheckbox());
            mainview.addView(linearLayout);
        }

    }

    private void Addtitles() {
        int color = getResources().getColor(R.color.lotteryfragmenttitle2);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setBackgroundColor(0xFFFFFFFF);

        String str[] = {"号码", "赔率", "号码", "赔率"};
        for (int i = 0; i < 4; i++) {
            TextView textview = new TextView(getActivity());
            LinearLayout.LayoutParams laypamars = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            laypamars.weight = 1;
            textview.setLayoutParams(laypamars);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textview.setBackgroundColor(color);
            textview.setText(str[i]);
            textview.setTextColor(0xFFFFFFFF);
            int pad = ScreenUtils.getDIP2PX(getActivity(), 5);
            textview.setPadding(pad, pad, pad, pad);
            textview.setGravity(Gravity.CENTER);
            linearLayout.addView(textview);
            if(i<3) {
                ImageView line = new ImageView(getActivity());
                line.setBackgroundColor(getResources().getColor(R.color.gray8));
                line.setLayoutParams(new ViewGroup.LayoutParams(2, ViewGroup.LayoutParams.FILL_PARENT));
                linearLayout.addView(line);
            }
        }
        mainview.addView(linearLayout);
    }

    private RelativeLayout MakeTextView(boolean isball,int color) {

        RelativeLayout relat=new RelativeLayout(getActivity());
        LinearLayout.LayoutParams laypamars = new LinearLayout.LayoutParams(specialwidth, ViewGroup.LayoutParams.FILL_PARENT);
        laypamars.weight = 1;
        laypamars.leftMargin = 1;
        relat.setLayoutParams(laypamars);
        relat.setBackground(new ColorDrawable(Color.rgb(217,216,216)));


        int pad = ScreenUtils.getDIP2PX(getActivity(), 4);
        TextView textview = new TextView(getActivity());
        int width=!isball? WindowManager.LayoutParams.WRAP_CONTENT:ScreenUtils.getDIP2PX(getActivity(), 30);
        RelativeLayout.LayoutParams laypamars2 = new RelativeLayout.LayoutParams(width, width);
        laypamars2.topMargin=pad;
        laypamars2.bottomMargin=pad;
        laypamars2.addRule(RelativeLayout.CENTER_IN_PARENT);
//        laypamars2.setMargins(5,5,5,5);
        textview.setLayoutParams(laypamars2);
        textview.setMinWidth(ScreenUtils.getDIP2PX(getActivity(), ScreenUtils.getDIP2PX(getActivity(), 30)));
        textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textview.setTextColor(color);
//        textview.setPadding(5, 5, 5, 5);
        textview.setGravity(Gravity.CENTER);
        textviews.add(textview);

        relat.addView(textview);
        return relat;
    }

    private RelativeLayout MakeCheckbox() {
        int pad = ScreenUtils.getDIP2PX(getActivity(), 4);
        RelativeLayout relativeLayout=new RelativeLayout(getActivity());
        relativeLayout.setBackgroundColor(0xFFFFFFFF);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(specialwidth, ViewGroup.LayoutParams.FILL_PARENT);
        layoutParams.weight=1;
        layoutParams.leftMargin=1;
        relativeLayout.setLayoutParams(layoutParams);
        int mar=ScreenUtils.getDIP2PX(getActivity(), 10);
        relativeLayout.setPadding(mar, 0, mar, 0);

        CheckBox check = new CheckBox(getActivity());
        check.setPadding(pad, pad, pad, pad);
        RelativeLayout.LayoutParams laypamars = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        laypamars.topMargin=ScreenUtils.getDIP2PX(getActivity(),4);
        laypamars.bottomMargin=ScreenUtils.getDIP2PX(getActivity(),4);
        laypamars.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        check.setLayoutParams(laypamars);
        check.setButtonDrawable(new ColorDrawable(0));
        check.setTextColor(0xFFFFFFFF);
        check.setGravity(Gravity.CENTER);
        check.setBackground(getResources().getDrawable(R.drawable.lottery_fragment_chobxbtn));
        checkboxs.add(check);
        relativeLayout.addView(check);
        return relativeLayout;
    }

    private TextView MakeSpeacialView()
    {

        int line2 = getResources().getColor(R.color.line2);
        int pad = ScreenUtils.getDIP2PX(getActivity(), 5);
         Speacialtextview = new TextView(getActivity());
        LinearLayout.LayoutParams laypamars = new LinearLayout.LayoutParams(specialwidth*2, ScreenUtils.getDIP2PX(getActivity(), 40));
        laypamars.leftMargin = 1;
        Speacialtextview.setLayoutParams(laypamars);
        Speacialtextview.setMinWidth(ScreenUtils.getDIP2PX(getActivity(), 40));
        Speacialtextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        Speacialtextview.setBackgroundColor(0xFFFFFFFF);
        Speacialtextview.setTextColor(line2);
//        Speacialtextview.setPadding(pad, pad, pad, pad);
        Speacialtextview.setGravity(Gravity.CENTER);
        Speacialtextview.setText(0+" "+1+" "+2);
        Speacialtextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wheel();
            }
        });
        return Speacialtextview;
    }

    private void MakespeacialViewIntoMainView()
    {
        int line2 = getResources().getColor(R.color.line2);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.gray8));

        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin=2;
        linearLayout.setLayoutParams(params);
        linearLayout.addView(MakeTextView(false, 0xFFFFFFFF));
        linearLayout.addView(MakeSpeacialView());
        linearLayout.addView(MakeCheckbox());

        mainview.addView(linearLayout);
    }

    private void Setdata()
    {
        for (int i = 0; i <sparseArray.size(); i++) {
            textviews.get(i).setText(sparseArray.get(i).getNumber());
            if (Httputils.useLoop(Httputils.lotterry28red, Integer.valueOf(sparseArray.get(i).getNumber()))) {
                textviews.get(i).setBackgroundResource((R.drawable.lottery_red));
            }
            if (Httputils.useLoop(Httputils.lotterry28green, Integer.valueOf(sparseArray.get(i).getNumber()))) {
                textviews.get(i).setBackgroundResource((R.drawable.lottery_green));
            }
            if (Httputils.useLoop(Httputils.lotterry28blue, Integer.valueOf(sparseArray.get(i).getNumber()))) {
                textviews.get(i).setBackground(this.getResources().getDrawable(R.drawable.lottery_blue));
            }
            if (Httputils.useLoop(Httputils.lotterry28gary, Integer.valueOf(sparseArray.get(i).getNumber()))) {
                textviews.get(i).setBackground(this.getResources().getDrawable(R.drawable.lottery_gary));
            }
            checkboxs.get(i).setText(sparseArray.get(i).getPl());
            checkboxs.get(i).setTag(sparseArray.get(i));
        }

        for (int i = sparseArray.size(); i < sparseArray.size()+lotterybeans.size(); i++) {
            textviews.get(i).setText(lotterybeans.get(i-sparseArray.size()).getNumber());
            checkboxs.get(i).setText(lotterybeans.get(i-sparseArray.size()).getPl());
            checkboxs.get(i).setTag(lotterybeans.get(i -sparseArray.size()));
            if (lotterybeans.get(i-sparseArray.size()).getNumber().indexOf("红波") != -1) {
                textviews.get(i).setTextColor(this.getResources().getColor(R.color.lottery));
            }
            else if (lotterybeans.get(i-sparseArray.size()).getNumber().indexOf("绿波") != -1) {
                textviews.get(i).setTextColor(this.getResources().getColor(R.color.green));
            }
            else  if (lotterybeans.get(i-sparseArray.size()).getNumber().indexOf("蓝波") != -1) {
                textviews.get(i).setTextColor(this.getResources().getColor(R.color.lotteryblue));
            }
            else
                textviews.get(i).setTextColor(this.getResources().getColor(R.color.black));
        }
    }

    private void Wheel()
    {

        if(dialog==null) {
            dialog = new WheelDialogWith3(this.getActivity());
            indexlist = new int[]{-1, -1, -1};
        }
        if(index==null)
        {
            index=new String[3][28];
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 28; k++) {
                    index[i][k]=k+"";
                }
            }
        }

        dialog.setListener(new WheelDialogWith3.OnChangeListener() {
            @Override
            public void onChanged(int arg1, int arg2, int arg3) {
                StringBuilder sb = new StringBuilder();
                sb.append(arg1 + " ");
                sb.append(arg2 + " ");
                sb.append(arg3);
                indexlist[0] = arg1;
                indexlist[1] = arg2;
                indexlist[2] = arg3;
                Speacialtextview.setText(sb.toString());
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface mdialog) {
                int list[] = ((WheelDialogWith3) mdialog).getIndex();
                StringBuilder sb = new StringBuilder();
                sb.append(list[0] + " ");
                sb.append(list[1] + " ");
                sb.append(list[2]);
                indexlist[0] = list[0];
                indexlist[1] = list[1];
                indexlist[2] = list[2];
                Speacialtextview.setText(sb.toString());
            }
        });
        if(indexlist[0]>-1){
            dialog.SetInitnumber(indexlist[0], indexlist[1], indexlist[2]);
        }
        else {
            dialog.SetInitnumber(0, 1, 2);
        }
        dialog.setStrs(index);
        dialog.show();
    }
    private void openTimer(){
//        if(mTimer==null){
//            mTimer=new ThreadTimer();
//            mTimer.setListener(new ThreadTimer.OnActiviteListener() {
//                @Override
//                public void Callback() {
//                    Log.e("higherLive", "刷新了");
//                    getdatalist();
//                }
//            });
//        }
//        mTimer.Start(30*1000);//定时30秒刷新一次
        GetData();
        ThreadTimer.setListener2(new ThreadTimer.OnActiviteListener2() {
            @Override
            public void MinCallback(long limitmin, long close, long open) {
                LotteryFragment1 fragment1 = (LotteryFragment1) getParentFragment();
                if (open == 0) {
                    ThreadTimer.Stop(Lucky28.this.getClass().getName());
                }
                fragment1.gettime(close, open);
            }

            @Override
            public void DelayCallback() {
                GetData();
            }
        });
        BaseApplication baseApplication=(BaseApplication)getActivity().getApplication();

        long close=baseApplication.getClosetime()-baseApplication.getNowtime();
        long now=baseApplication.getOpentime()-baseApplication.getNowtime();
        ThreadTimer.Start(30 * 1000, close * 1000, now * 1000);
    }
    @Override
    public void CallbackFunction() {
        super.CallbackFunction();
        openTimer();
    }

    public void StartActvity(ArrayList<LotterycomitorderBean> beans)
    {
        if(beans.size()>0) {
            Intent intent = new Intent(getActivity(), LotteryComitOrderActivity.class);
            intent.putExtra("title", LotteryFragment1.getGamename());
            intent.putExtra("gamecode", LotteryFragment1.getGamecode());
            intent.putExtra("normal", "normal");
            intent.putExtra("gameitemname", LotteryFragment1.getGameitemname());
            intent.putExtra("gamexzlxitemname", LotteryFragment1.getGanmeitmexzlxname());
            intent.putExtra("json", new Gson().toJson(beans));
            startActivity(intent);
//            LogTools.e("dadfadfadsfads"+ LotteryFragment1.getGanmeitmexzlxname(),LotteryFragment1.getGameitemname() + "");
        }
        else{
            ToastUtil.showMessage(this.getActivity(), "至少选择一个");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.reset:
                for (int i = 0; i < checkboxs.size(); i++) {
                    checkboxs.get(i).setChecked(false);
                }
                break;

            case R.id.comit:
                String Username =((BaseApplication)this.getActivity().getApplication()).getBaseapplicationUsername();
                if (Username == null || Username.equalsIgnoreCase("")) {
                    ToastUtil.showMessage(this.getActivity(), "未登录");
                    Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                    this.getActivity().startActivity(intent);
                    return;
                }
                if(indexlist!=null) {
                    if (indexlist[0] == indexlist[1] || indexlist[0] == indexlist[2] || indexlist[1] == indexlist[2] || indexlist[1] == indexlist[0] || indexlist[2] == indexlist[0] || indexlist[2] == indexlist[1]) {
                        ToastUtil.showMessage(this.getActivity(), "特码包三号码不能两两相同");
                        return;
                    }
                }
                ArrayList<LotterycomitorderBean> beans =new ArrayList<LotterycomitorderBean>();
                for (int i = 0; i < checkboxs.size(); i++) {
                    if(checkboxs.get(i).isChecked())
                    {
                        LotterycomitorderBean bean=(LotterycomitorderBean)checkboxs.get(i).getTag();
                        if(textviews.get(i).getText().toString().indexOf("特码")!=-1){
                           String number=Speacialtextview.getText().toString().replace(" ", "");
                            if (!number.toString().substring(0, 1).equalsIgnoreCase(number.toString().substring(1, 2)) &&
                                    !number.toString().substring(0, 1).equalsIgnoreCase(number.toString().substring(2, 3))
                                    &&!number.toString().substring(1, 2).equalsIgnoreCase(number.toString().substring(2, 3))) {
                            bean.setNumber(Speacialtextview.getText().toString().replace(" ", ","));
                            }
                            else{
                                ToastUtil.showMessage(this.getActivity(),"特码包三号码不能两两相同");
                                return;
                            }
                        }
                            beans.add(bean);


                    }
                }
                StartActvity(beans);
                break;
        }
    }

}
