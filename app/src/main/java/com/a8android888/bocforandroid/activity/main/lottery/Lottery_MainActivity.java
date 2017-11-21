package com.a8android888.bocforandroid.activity.main.lottery;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/5.
 */
public class Lottery_MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private TextView title, textView5;
    private ViewPager viewpager;
    private ImageView moveline;
    private RadioGroup radiogroup;
    RadioButton recordbutton,recordbutton2;
    int ScreenWidth;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private ArrayList<BaseFragment> basefragments;
    public  int CurrentPage = 0;
    ImageView imagetwo;
    ImageView triangle;
    PopupWindow popupWindowforCover;
    View topview;
    PublicDialog publicdialog;
    Intent intent;
    private static String lotteryname="";
    private static String lotterycode="";
//    private static String lotteryid="";
//    private static String lotteryitmeid="";
    private static String lotteryitmeidcode="";
    private static String lotteryitmeidname="";
    private static String lotteryitmexzlxname="";
    private static String lotteryitmexzlxcode="";
    private static int lotteryitmextag;
    private PopupWindow popupWindow;
    private TextView Contenttext;
    private Boolean setcilick=false;
    boolean noOpenData=true;

    public static int getLotteryitmextag() {
        return lotteryitmextag;
    }

    public static void setLotteryitmextag(int lotteryitmextag) {
        Lottery_MainActivity.lotteryitmextag = lotteryitmextag;
    }

    public static String getLotteryitmeidname() {
        return lotteryitmeidname;
    }

    public static void setLotteryitmeidname(String lotteryitmeidname) {
        Lottery_MainActivity.lotteryitmeidname = lotteryitmeidname;
    }


    public String getLotteryitmexzlxcode() {
        return lotteryitmexzlxcode;
    }

    public void setLotteryitmexzlxcode(String lotteryitmexzlxcode) {
        Lottery_MainActivity.lotteryitmexzlxcode = lotteryitmexzlxcode;
    }

    public String getLotteryitmexzlxname() {
        return lotteryitmexzlxname;
    }

    public void setLotteryitmexzlxname(String lotteryitmexzlxname) {
        Lottery_MainActivity.lotteryitmexzlxname = lotteryitmexzlxname;
    }

    ArrayList<BallTypeBean> ballTypeBeans ;
    final ArrayList<BallTypeBean> xzballTypeBeans = new ArrayList<BallTypeBean>();

    public String getLotteryitmeidcode() {
        return lotteryitmeidcode;
    }

    public void setLotteryitmeidcode(String lotteryitmeidcode) {
        Lottery_MainActivity.lotteryitmeidcode = lotteryitmeidcode;
    }
//
//    public String getLotteryitmeid() {
//        return lotteryitmeid;
//    }
//
//    public void setLotteryitmeid(String lotteryitmeid) {
//        Lottery_MainActivity.lotteryitmeid = lotteryitmeid;
//    }

    public String getLotteryname() {
        return lotteryname;
    }

    public void setLotteryname(String lotteryname) {
        Lottery_MainActivity.lotteryname = lotteryname;
    }

    public String getLotterycode() {
        return lotterycode;
    }

    public void setLotterycode(String lotterycode) {
        Lottery_MainActivity.lotterycode = lotterycode;
    }
//
//    public String getLotteryid() {
//        return lotteryid;
//    }
//
//    public void setLotteryid(String lotteryid) {
//        Lottery_MainActivity.lotteryid = lotteryid;
//    }
@Override
protected void onDestroy() {
    super.onDestroy();
    if (lotteryname.length()>1) {
     lotteryname="";
     lotterycode="";
       lotteryitmeidcode="";
       lotteryitmeidname="";
      lotteryitmexzlxname="";
       lotteryitmexzlxcode="";
    }
}

    public static boolean needdialog()
    {
        return  false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lottery_activity_main);
        InitView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void InitView() {
        intent = getIntent();
//        ThreadTimer.setLoop(true);

        topview = FindView(R.id.topview);
        triangle = FindView(R.id.triangle);
        triangle.setVisibility(View.VISIBLE);
//        imagetwo=FindView(R.id.imagetwo);
//        imagetwo.setVisibility(View.GONE);
//        imagetwo.setOnClickListener(this);
        ScreenWidth = ScreenUtils.getScreenWH(this)[0];
        radiogroup = FindView(R.id.radiogroup);
        for (int i = 0; i < radiogroup.getChildCount(); i++) {
            radiogroup.getChildAt(i).setTag(i);
        }
        recordbutton = (RadioButton) radiogroup.getChildAt(0);
        recordbutton2=(RadioButton) radiogroup.getChildAt(1);
        title = FindView(R.id.title);
        title.setTag(0);
        title.setOnClickListener(this);
        textView5 = FindView(R.id.textView5);
        textView5.setText(intent.getStringExtra("title"));
        moveline = FindView(R.id.moveline);
        moveline.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(ScreenWidth / 4-ScreenWidth / 4/3, moveline.getHeight());
                layoutparams.leftMargin = ScreenWidth / 4/3/2-5;
                layoutparams.addRule(RelativeLayout.BELOW, R.id.radiogroup);
                moveline.setLayoutParams(layoutparams);
            }
        });
        LogTools.e("ccccc",intent.getStringExtra("code"));
        setLotterycode(intent.getStringExtra("code"));

        setLotteryname(intent.getStringExtra("title"));

        BallType(getLotterycode());
        viewpager = FindView(R.id.viewpager);

        basefragments = new ArrayList<BaseFragment>();
        BaseFragment fragment1 = new LotteryFragment1();
        Bundle bundle = new Bundle();
        bundle.putString(BundleTag.FragmentTag, intent.getStringExtra("code"));
        fragment1.setArguments(bundle);
        basefragments.add(fragment1);
        basefragments.add(new LotteryFragment2());
        basefragments.add(new LotteryFragment3());
        basefragments.add(new LotteryFragment4());
        viewpager.setOffscreenPageLimit(4);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CurrentPage = position;
                if (CurrentPage == 0) {
                    title.setVisibility(View.VISIBLE);
                    triangle.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.INVISIBLE);
                    triangle.setVisibility(View.INVISIBLE);
                }
                RadioButton radioButton = (RadioButton) radiogroup.getChildAt(CurrentPage);
                radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        recordbutton.setChecked(true);
        LogTools.e("ssssss","ddddddd");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageone:
                break;
            case R.id.imagetwo:

                break;
            case R.id.title:
                if(!noOpenData)//如果没有开盘数据不打开
                    showMore(topview);
                break;
        }
    }

    /**
     * 显示更多弹出框
     *
     * @param parent
     */
    private void showMore(View parent) {
        final LinearLayout linearLayout;
        if (ballTypeBeans.size() < 1) return;
        if (popupWindow == null) {
            View popupView = View.inflate(this, R.layout.popupwindowlayout, null);
//            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupView.setFocusableInTouchMode(true);
            popupView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        popupWindow.dismiss();
                        popupWindow = null;
                        return true;
                    }
                    return false;
                }
            });
            if (popupWindow == null)
                popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, false);
            linearLayout = (LinearLayout) popupView.findViewById(R.id.layout);
            RelativeLayout relation = (RelativeLayout) popupView.findViewById(R.id.relation);
            linearLayout .setPadding(10, 0,10, 0 );
            linearLayout.setBackgroundColor(this.getResources().getColor(R.color.userbagcolor));
            relation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    popupWindow = null;

                }
            });
            for (int i = 0; i < ballTypeBeans.size(); i++) {
                final TextView textView = new TextView(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                textView.setLayoutParams(params1);
                Drawable drawable= getResources().getDrawable(R.drawable.yuandian);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(drawable, null, null, null); //设置左图标
//                textView.setCompoundDrawablePadding(10);//设置图片和text之间的间距
                textView.setTag(i);
                textView.setTextColor(this.getResources().getColor(R.color.black));
                textView.setText(ballTypeBeans.get(i).getrName());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                textView.setPadding(ScreenUtils.getDIP2PX(this, 5), ScreenUtils.getDIP2PX(this, 10), ScreenUtils.getDIP2PX(this, 20), ScreenUtils.getDIP2PX(this, 2));
                linearLayout.addView(textView);
                LinearLayout hor = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.topMargin = 5;
                params.bottomMargin = 5;
                params.leftMargin =10;
                params.rightMargin =10;
                hor.setOrientation(LinearLayout.HORIZONTAL);
                hor.setLayoutParams(params);
                if (ballTypeBeans.get(i).getObject().length() > 0) {
                    AddBtn(hor, i);
                } else {
                    final TextView textView2 = new TextView(this);
                    int width = (ScreenUtils.getScreenWH(this)[0]) / 3 - 80;
                    LinearLayout.LayoutParams TE_params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                    TE_params.leftMargin = 20;
                    TE_params.rightMargin = 20;
                    TE_params.topMargin = 5;
                    TE_params.bottomMargin = 5;
                    textView2.setLayoutParams(TE_params);
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    textView2.setGravity(Gravity.CENTER);
                    textView2.setPadding(0, 10, 0, 10);
                    textView2.setTag(i);
                    if(ballTypeBeans.get(i).getSelected()) {
                        if (ballTypeBeans.get(i).getrName().equalsIgnoreCase(title.getText().toString().trim())) {
                            textView2.setBackgroundResource(R.color.lottery);
                            textView2.setTextColor(this.getResources().getColor(R.color.white));
                        } else {
                            textView2.setTextColor(this.getResources().getColor(R.color.gray5));
                            textView2.setBackgroundResource((R.drawable.comit_order_codebag));
                        }
                    }
                    else {
                        textView2.setTextColor(this.getResources().getColor(R.color.gray5));
                        textView2.setBackgroundResource((R.drawable.comit_order_codebag));
                    }
                    textView2.setText(ballTypeBeans.get(i).getrName());
                    textView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag = Integer.valueOf(v.getTag() + "");
                            popupWindow.dismiss();
                            popupWindow = null;

                            textView2.setTextColor(Lottery_MainActivity.this.getResources().getColor(R.color.lottery));
                            title.setText(textView2.getText().toString().trim());
                            textView2.setBackgroundResource((R.drawable.comit_order_codebagred));
//                            setLotteryitmeid(ballTypeBeans.get(tag).getItemId());
                            setLotteryitmeidcode(ballTypeBeans.get(tag).getItemCode());
                            setLotteryitmeidname(ballTypeBeans.get(tag).getrName());
                            setLotteryitmexzlxname("");
                            setLotteryitmexzlxcode("");
                            setLotteryitmextag(tag);
//                            setLotteryitmexzlxname(ballTypeBeans.get(tag).getrName());
//                            setLotteryitmexzlxcode(ballTypeBeans.get(tag).getItemCode());
                            basefragments.get(CurrentPage).clickfragment();
                            for (int i1 = 0; i1 < basefragments.size(); i1++) {
                                if (CurrentPage == i1) continue;
                                basefragments.get(i1).setNeedCallBack(true);
                            }
                            for(int iii=0;iii<ballTypeBeans.size();iii++){
                                if(iii==tag)
                                    ballTypeBeans.get(tag).setSelected(true);
                                else
                                    ballTypeBeans.get(iii).setSelected(false);
                            }
                            LogTools.e("点击选择了一个", ballTypeBeans.get(tag).getrName()+ "");
                        }
                    });
                    hor.addView(textView2);
                }
                linearLayout.addView(hor);
            }
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Setstate(true);
                }
            });
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            showAsDropDown(popupWindow, parent, 0,0);
            Setstate(false);
        } else {
           showAsDropDown(popupWindow,parent , 0, 0);
            Setstate(false);
        }
    }
    public static void showAsDropDown(PopupWindow pw, View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            // 7.1 版本处理
            if (Build.VERSION.SDK_INT == 25) {
                //【note!】Gets the screen height without the virtual key
                WindowManager wm = (WindowManager) pw.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                /*
                /*
                 * PopupWindow height for match_parent,
                 * will occupy the entire screen, it needs to do special treatment in Android 7.1
                */
                pw.setHeight(screenHeight - location[1] - anchor.getHeight() - yoff);
            }
            pw.showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, location[1] + anchor.getHeight() + yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

    private void AddBtn(LinearLayout buttonlist, final int pos) {
        int itemcount = ballTypeBeans.get(pos).getObject().length();
        int count = itemcount % 3 == 0 ? itemcount / 3 : (itemcount / 3) + 1;
        int width = (ScreenUtils.getScreenWH(this)[0] ) / 3 - 80;
        LinearLayout hora = new LinearLayout(this);
        LinearLayout.LayoutParams paramsa = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsa.topMargin = 15;
        paramsa.bottomMargin = 15;
        hora.setOrientation(LinearLayout.VERTICAL);
        hora.setLayoutParams(paramsa);

        for (int i = 0; i < count; i++) {
            LinearLayout hor = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = 15;
            params.bottomMargin = 15;
            hor.setLayoutParams(params);
            int tempcount = itemcount - i * 3 > 2 ? (3 * (i + 1)) : itemcount;

            for (int j = i * 3; j < tempcount; j++) {
                final TextView textView = new TextView(this);
                LinearLayout.LayoutParams TE_params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                TE_params.leftMargin = 20;
                TE_params.rightMargin = 20;
                TE_params.topMargin = 5;
                TE_params.bottomMargin = 5;
                textView.setLayoutParams(TE_params);
                textView.setGravity(Gravity.CENTER);
               if(ballTypeBeans.get(pos).getSelected()) {
                   if (ballTypeBeans.get(pos).getObject().optJSONObject(j).optString("xzlxName").equalsIgnoreCase(title.getText().toString().trim())) {
                       textView.setBackgroundResource(R.color.lottery);
                       textView.setTextColor(this.getResources().getColor(R.color.white));
                   }
                   else {
                       textView.setBackgroundResource((R.drawable.comit_order_codebag));
                       textView.setTextColor(this.getResources().getColor(R.color.gray5));
                   }
               }
               else {
                   textView.setBackgroundResource((R.drawable.comit_order_codebag));
                   textView.setTextColor(this.getResources().getColor(R.color.gray5));
               }
                textView.setPadding(0, 10, 0, 10);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                JSONObject jsonObject = ballTypeBeans.get(pos).getObject().optJSONObject(j);
                textView.setTag(j);
                textView.setText(ballTypeBeans.get(pos).getObject().optJSONObject(j).optString("xzlxName"));
//                LogTools.e("点击选择了欣赏", ballTypeBeans.get(pos).getObject().optJSONObject(j).optString("xzlxName"));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = Integer.valueOf(v.getTag() + "");
                        popupWindow.dismiss();
                        popupWindow = null;
                        title.setText(textView.getText().toString().trim());
                        textView.setBackgroundResource((R.drawable.comit_order_codebagred));

//                        setLotteryitmeid(ballTypeBeans.get(pos).getItemId());
                        setLotteryitmeidcode(ballTypeBeans.get(pos).getItemCode());
                        setLotteryitmeidname(ballTypeBeans.get(pos).getrName());
                        textView.setTextColor(Lottery_MainActivity.this.getResources().getColor(R.color.lottery));
                        setLotteryitmexzlxname(ballTypeBeans.get(pos).getObject().optJSONObject(tag).optString("xzlxName"));
                        setLotteryitmexzlxcode(ballTypeBeans.get(pos).getObject().optJSONObject(tag).optString("xzlxCode"));
                        setLotteryitmextag(pos);
                        basefragments.get(CurrentPage).clickfragment();
                        for (int i1 = 0; i1 < basefragments.size(); i1++) {
                            if (CurrentPage == i1) continue;
                            basefragments.get(i1).setNeedCallBack(true);
                        }
                        for(int iii=0;iii<ballTypeBeans.size();iii++){
                            if(iii==pos)
                                ballTypeBeans.get(pos).setSelected(true);
                            else
                                ballTypeBeans.get(iii).setSelected(false);
                        }
                        LogTools.e("点击选择了"+ballTypeBeans.get(pos).getrName(), ballTypeBeans.get(pos).getObject().optJSONObject(tag).optString("xzlxName") + "");
                    }
                });
                hor.addView(textView);
            }

            hora.addView(hor);
        }
        buttonlist.addView(hora);
    }

    private void Setstate(boolean state)//state true 没popview compu_img_up
    {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) triangle.getLayoutParams();
        if (state) {
            triangle.setImageDrawable(getResources().getDrawable(R.drawable.compu_img_up));
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.title);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.title);
            triangle.setLayoutParams(layoutParams);
        } else {
            triangle.setImageDrawable(getResources().getDrawable(R.drawable.compu_img_down));
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.title);
            triangle.setLayoutParams(layoutParams);
        }
    }


    private void BallType(String tag) {
        RequestParams params = new RequestParams();
        params.put("gameCode", tag);
        Httputils.PostWithBaseUrl(Httputils.lotterytype, params, new MyJsonHttpResponseHandler(Lottery_MainActivity.this,true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                ballTypeBeans = new ArrayList<BallTypeBean>();
                LogTools.e("返回aa", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000"))
                    return;

                JSONObject tempjsob = jsonObject.optJSONObject("datas");
//                setLotteryid(tempjsob.optString("gameCode"));
//                setLotteryitmeid(tempjsob.optString("defaultItemCode"));
                JSONArray jsonArray = tempjsob.optJSONArray("itemList");
                if (jsonArray != null)
                {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        BallTypeBean bean = new BallTypeBean();
                        JSONObject temp = jsonArray.optJSONObject(i);
                        bean.setItemCode(temp.optString("itemCode"));
                        bean.setItemId(temp.optString("itemId"));
                        bean.setrName(temp.optString("itemName"));
                        if(i==0) {
                            bean.setSelected(true);
                        }
                        else
                            bean.setSelected(false);
                        if (temp.optJSONArray("xzlxList") != null) {
                            bean.setObject(temp.optJSONArray("xzlxList"));
                        } else {
                            JSONArray jsonArr = new JSONArray();
                            bean.setObject(jsonArr);
                        }
//                    if(temp.optJSONArray("xzlxList")!=null){
//                        JSONArray jsonArr = temp.optJSONArray("xzlxList");
//                        for (int i1 = 0; i1 <jsonArr.length() ; i1++) {
//                            BallTypeBean bean1 = new BallTypeBean();
//                            JSONObject temp1 = jsonArr.optJSONObject(i1);
//                            bean1.setrName(ballTypeBeans.get(i).getrName());
//                            bean1.setXzlxcode(temp1.optString("xzlxCode"));
//                            bean1.setXzlxname(temp1.optString("xzlxName"));
//                            xzballTypeBeans.add(bean1);
//                            LogTools.e("返回aa啊啊", temp1.optString("xzlxName"));
//                        }
//                    }
                        ballTypeBeans.add(bean);
                    }

                if (ballTypeBeans.get(0).getObject().optJSONObject(0) == null) {
                    title.setText(ballTypeBeans.get(0).getrName());
                    setLotteryitmexzlxname("");
                    setLotteryitmexzlxcode("");
                } else {
                    title.setText(ballTypeBeans.get(0).getObject().optJSONObject(0).optString("xzlxName"));
                    setLotteryitmexzlxname(ballTypeBeans.get(0).getObject().optJSONObject(0).optString("xzlxName"));
                    setLotteryitmexzlxcode(ballTypeBeans.get(0).getObject().optJSONObject(0).optString("xzlxCode"));
                }
//                    setLotteryitmeid(ballTypeBeans.get(0).getItemId());
                    setLotteryitmeidcode(ballTypeBeans.get(0).getItemCode());
                    setLotteryitmeidname(ballTypeBeans.get(0).getrName());
                }
                else {
                    triangle.setVisibility(View.GONE);
                    title.setText(intent.getStringExtra("title"));
                    setLotteryitmexzlxname("");
                    setLotteryitmexzlxcode("");
                    setLotteryitmeidcode("TM");
                    setLotteryitmeidname("");

                }
                SportsViewPagerAdapter sportsadapter = new SportsViewPagerAdapter(getSupportFragmentManager(), basefragments);
                viewpager.setAdapter(sportsadapter);
                SetRadioGroupEnable();
            }
        });
    }

    public void ShowPopWindow()
    {
        if(popupWindowforCover==null)
        {
            RelativeLayout layout=new RelativeLayout(this);
            layout.setBackground(new ColorDrawable(0x99b8b8b8));
            layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Contenttext=new TextView(this);
            Contenttext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            Contenttext.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            Contenttext.setGravity(Gravity.CENTER);
            Contenttext.setText("");

            RelativeLayout.LayoutParams layoutParams1= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
            Contenttext.setLayoutParams(layoutParams1);
            layout.addView(Contenttext);

            popupWindowforCover=new PopupWindow(layout,ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, false);
        }
        popupWindowforCover.showAsDropDown(topview);
    }

    public void SetRadioGroupEnable()
    {
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();

                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) FindView(radioButtonId);
                int tag = Integer.valueOf(rb.getTag() + "");
                CurrentPage = tag;
                if (CurrentPage == 0) {
                    title.setVisibility(View.VISIBLE);
                    triangle.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.INVISIBLE);
                    triangle.setVisibility(View.INVISIBLE);
                }
                recordbutton.setTextColor(getResources().getColor(R.color.black));
                recordbutton = rb;
                recordbutton.setTextColor(getResources().getColor(R.color.lottery));
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation;
                translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setDuration(100);
                moveline.startAnimation(animationSet);//开始上面红色横条图片的动画切换
                int magin =ScreenWidth / 4-ScreenWidth / 4/3;
                mCurrentCheckedRadioLeft = magin * tag;//更新当前蓝色横条距离左边的距离
//                mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft, 0);
                RelativeLayout.LayoutParams layoutparams = (RelativeLayout.LayoutParams) moveline.getLayoutParams();
                layoutparams.leftMargin = ScreenWidth / 4/3/2-5;
//                layoutparams.rightMargin = 20;
                layoutparams.addRule(RelativeLayout.BELOW, R.id.radiogroup);
                moveline.setLayoutParams(layoutparams);
                viewpager.setCurrentItem(tag);
                LogTools.e("ssssss","ddddaaaaa");
            }
        });
    }

    public PopupWindow getPopWindow()
    {
        return popupWindowforCover;
    }

    public void setContenttext(String text)
    {
        Contenttext.setText(text);
    }

    public String getContextText()
    {
        return Contenttext.getText().toString().trim();
    }

    public void setNoOpenData(boolean noOpenData) {
        this.noOpenData = noOpenData;
    }

}
