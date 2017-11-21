package com.a8android888.bocforandroid.activity.main.Sports;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.SportsViewPagerAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.BaseParent.BaseFragmentActivity;
import com.a8android888.bocforandroid.Bean.BallTypeBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ThreadTimer;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.a8android888.bocforandroid.view.MyStyleView;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.a8android888.bocforandroid.webActivity;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/5.
 */
public class Sports_MainActivity extends BaseFragmentActivity implements View.OnClickListener{

    private TextView title,title2;
    private ViewPager viewpager;
    private ImageView moveline;
    private RadioGroup radiogroup;
    RadioButton recordbutton;
    int ScreenWidth;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private ArrayList<BaseFragment> basefragments;
    private int CurrentPage=0;
    ImageView imagetwo,imageone;
    ImageView triangle,triangle2;
    PopupWindow popupWindow,BallType_Window;
    View topview;
    static String titles[];
    static String titles_tag[];
    HashMap<String,ArrayList<BallTypeBean>> maps=new HashMap<String, ArrayList<BallTypeBean>>();
    public String CurrentTag="";//盘口类型 如:单式 波胆
    public int index;//盘口类型 如:单式 波胆 的排列顺序号
    public String PlatFormTag;//平台标识
    PopupWindow popupWindowforCover;
    RelativeLayout re_chooser2;
    private TextView Contenttext;
    String m_type="";
    String m_type_name="";
    boolean titleclickEnable=true;//有时候维护了就必须去除点击事件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_main);
        ThreadTimer.setLoop(false);
        InitView();
        Gettitls();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void InitView()
    {
        PlatFormTag=getIntent().getStringExtra(BundleTag.Platform);
        re_chooser2=FindView(R.id.re_chooser2);
        topview=FindView(R.id.topview);
        triangle=FindView(R.id.triangle);
        triangle2=FindView(R.id.triangle2);
        imageone=FindView(R.id.imageone);
        imageone.setOnClickListener(this);
        imagetwo=FindView(R.id.imagetwo);
        imagetwo.setOnClickListener(this);
        ScreenWidth = ScreenUtils.getScreenWH(this)[0];
        radiogroup=FindView(R.id.radiogroup);
        for (int i = 0; i < radiogroup.getChildCount(); i++) {
            radiogroup.getChildAt(i).setTag(i);
        }
        recordbutton=(RadioButton)radiogroup.getChildAt(1);
        title=FindView(R.id.title);
        title.setTag(0);
        title.setOnClickListener(this);
        title2=FindView(R.id.title2);
        title2.setTag(0);
        title2.setOnClickListener(this);


        moveline=FindView(R.id.moveline);
        moveline.post(new Runnable() {
            @Override
            public void run() {
                RelativeLayout.LayoutParams layoutparams=new RelativeLayout.LayoutParams(ScreenWidth / 5, moveline.getHeight());
                layoutparams.leftMargin=10;
                layoutparams.rightMargin=10;
                layoutparams.addRule(RelativeLayout.BELOW,R.id.radiogroup);
                moveline.setLayoutParams(layoutparams);
            }
        });
        viewpager=FindView(R.id.viewpager);
        basefragments=new ArrayList<BaseFragment>();
        basefragments.add(new SportFragment1());
        basefragments.add(new SportFragment2());
        basefragments.add(new SportFragment3());
        basefragments.add(new SportFragment4());
        basefragments.add(new SportFragment5());
        viewpager.setOffscreenPageLimit(5);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imageone:
                if(title.getText().toString().trim().contains("足球"))
                {
                    GetRuleData(0);
                }
                if(title.getText().toString().trim().contains("篮球"))
                {
                    GetRuleData(1);
                }
                break;
            case R.id.imagetwo:
//                BallType(String.valueOf(title.getTag()),true);
                //现在改成是购物车了
                BaseApplication baseApplication = (BaseApplication)getApplication();
                if (baseApplication.getBaseapplicationUsername() == null || baseApplication.getBaseapplicationUsername().equalsIgnoreCase("")) {
                    ToastUtil.showMessage(Sports_MainActivity.this, Sports_MainActivity.this.getString(R.string.pleaselongin2));
                    this.startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                Intent intent=new Intent();
                intent.setClass(Sports_MainActivity.this, SportOrderActivity.class);
                boolean iscontinue=false;
                if(CurrentTag.equalsIgnoreCase("bk_p3") || CurrentTag.equalsIgnoreCase("p3"))
                {
                    iscontinue=true;
                }
                intent.putExtra(BundleTag.Continue,iscontinue);
                startActivity(intent);
                break;
            case R.id.title:
                if(titleclickEnable)
                showMore(topview);
                break;
            case R.id.title2:
                BallType(String.valueOf(title.getTag()), true);
        }
    }

    /**
     * 显示更多弹出框
     *
     * @param parent
     */
    private void showMore(View parent) {
        if(titles.length<1)return;
        final   LinearLayout linearLayout;
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

            linearLayout=(LinearLayout)popupView.findViewById(R.id.layout);
//            ScrollView relation=(ScrollView)popupView.findViewById(R.id.scrollview);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    popupWindow = null;

                }
            });
            for (int i = 0; i <titles.length; i++) {
                final   TextView textView = new TextView(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(this, 150), ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                params1.gravity = Gravity.CENTER;
                textView.setLayoutParams(params1);
                textView.setTag(i);
                textView.setBackgroundColor(this.getResources().getColor(R.color.loess));
                textView.setText(titles[i]);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView.setPadding(ScreenUtils.getDIP2PX(this, 40), ScreenUtils.getDIP2PX(this, 10), ScreenUtils.getDIP2PX(this, 40), ScreenUtils.getDIP2PX(this, 10));
                textView.setTextColor(this.getResources().getColor(R.color.white));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        popupWindow = null;
                        int index=Integer.valueOf(textView.getTag()+"");
                        if(!title.getText().toString().trim().equalsIgnoreCase(titles[index]))
                        {
                            Sports_MainActivity.this.index=0;
                        }
                        title.setText(titles[index]);
                        title.setTag(titles_tag[index]);
//                        BallType(titles_tag[index], false);
                        if(maps.get(title.getTag()+"")!=null && maps.get(title.getTag()+"").size()>0)
                        {
                            CurrentTag= maps.get(title.getTag()+"").get(0).getrType();
                            title2.setText(maps.get(title.getTag()+"").get(0).getrName());
                            basefragments.get(CurrentPage).clickfragment();
                            for (int i = 0; i < basefragments.size(); i++) {
                                if(CurrentPage==i)continue;
                                basefragments.get(i).setNeedCallBack(true);
                            }
                            LogTools.e("CurrentTag",CurrentTag);
                        }
                        else
                        {
                            CurrentTag=BundleTag.MotherFucker;
                            basefragments.get(CurrentPage).clickfragment();
                            for (int i = 0; i < basefragments.size(); i++) {
                                if(CurrentPage==i)continue;
                                basefragments.get(i).setNeedCallBack(true);
                            }
                            ToastUtil.showMessage(Sports_MainActivity.this, getString(R.string.projectnoexits));
                        }
                    }
                });
                    ImageView imageView = new ImageView(this);
                    LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(this,150), 1,Gravity.CENTER);
                    params1.gravity = Gravity.CENTER;
                    imageView.setLayoutParams(params12);
                    imageView.setBackgroundColor(this.getResources().getColor(R.color.gray5));
                    linearLayout.addView(imageView);
                    linearLayout.addView(textView);
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
//            popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, topview.getHeight());
            popupWindow.showAsDropDown(parent);
            Setstate(false);
        }
        else
        {
//            popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,topview.getHeight());//topview.getHeight()+topview.getHeight()/2
            popupWindow.showAsDropDown(parent);
            Setstate(false);
        }
    }

    private void showMore2(View parent,ArrayList<BallTypeBean> list)
    {
        if(list==null || list.size()==0)return;
        final   LinearLayout linearLayout;
        if (BallType_Window == null) {
            View popupView = View.inflate(this, R.layout.popupwindowlayout, null);
//            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            popupView.setFocusableInTouchMode(true);
            popupView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        BallType_Window.dismiss();
                        BallType_Window = null;
                        return true;
                    }
                    return false;
                }
            });
            if (BallType_Window == null)
                BallType_Window = new PopupWindow(popupView, ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT, false);

            linearLayout=(LinearLayout)popupView.findViewById(R.id.layout);
            linearLayout.removeAllViews();
//            ScrollView relation=(ScrollView)popupView.findViewById(R.id.scrollview);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BallType_Window.dismiss();
                    BallType_Window = null;

                }
            });
            for (int i = 0; i <list.size(); i++) {
                final   TextView textView = new TextView(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(this, 150), ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                params1.gravity = Gravity.CENTER;
                textView.setLayoutParams(params1);
                textView.setTag(i);
                textView.setBackgroundColor(this.getResources().getColor(R.color.loess));
                textView.setText(list.get(i).getrName());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView.setPadding(ScreenUtils.getDIP2PX(this, 40), ScreenUtils.getDIP2PX(this, 10), ScreenUtils.getDIP2PX(this, 40), ScreenUtils.getDIP2PX(this, 10));
                textView.setTextColor(this.getResources().getColor(R.color.white));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BallType_Window.dismiss();
                        BallType_Window = null;
                        Sports_MainActivity.this.index=Integer.valueOf(textView.getTag()+"");
                    CurrentTag= maps.get(title.getTag()+"").get(index).getrType();
                    LogTools.e("CurrentTag", CurrentTag);
                    title2.setText(((TextView) v).getText().toString());
                    basefragments.get(CurrentPage).clickfragment();
                    }
                });
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(this,150), 1,Gravity.CENTER);
                params1.gravity = Gravity.CENTER;
                imageView.setLayoutParams(params12);
                imageView.setBackgroundColor(this.getResources().getColor(R.color.gray5));
                linearLayout.addView(imageView);
                linearLayout.addView(textView);
            }
            BallType_Window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    Setstate2(true);
                }
            });
            BallType_Window.setFocusable(true);
            BallType_Window.setBackgroundDrawable(new BitmapDrawable());
            BallType_Window.setOutsideTouchable(true);
//            popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, topview.getHeight());
            BallType_Window.showAsDropDown(parent);
            Setstate2(false);
        }
        else
        {
//            popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,topview.getHeight());//topview.getHeight()+topview.getHeight()/2
            BallType_Window.showAsDropDown(parent);
            Setstate2(false);
        }
    }


    public String GetSportState()//获取标题
    {
        return title.getText().toString().trim();
    }

    public String GetSportFlat()//获取标题id
    {
        return title.getTag().toString().trim();
    }

    public String GetSportTag()//获取是什么盘口 比如单式，波胆
    {
        return CurrentTag;
    }

    public int GetCurrentPage()
    {
        return  CurrentPage;
    }

    private void Setstate(boolean state)//state true 没popview compu_img_up
    {
//        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) triangle.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        if(state)
        {
            triangle.setImageDrawable(getResources().getDrawable(R.drawable.compu_img_up));
//            layoutParams.width= WindowManager.LayoutParams.WRAP_CONTENT;
//            layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.bottomMargin=ScreenUtils.getDIP2PX(this,5);
            layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.title);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.title);
            triangle.setLayoutParams(layoutParams);
        }
        else
        {
            triangle.setImageDrawable(getResources().getDrawable(R.drawable.compu_img_down));
//            layoutParams.width= WindowManager.LayoutParams.WRAP_CONTENT;
//            layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.title);
            triangle.setLayoutParams(layoutParams);
        }
    }

    private void Setstate2(boolean state)//state true 没popview compu_img_up
    {
//        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) triangle2.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        if(state)
        {
            triangle2.setImageDrawable(getResources().getDrawable(R.drawable.compu_img_up));
//            layoutParams.width= WindowManager.LayoutParams.WRAP_CONTENT;
//            layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.bottomMargin=ScreenUtils.getDIP2PX(this,5);
            layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.title2);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.title2);
            triangle2.setLayoutParams(layoutParams);
        }
        else
        {
            triangle2.setImageDrawable(getResources().getDrawable(R.drawable.compu_img_down));
//            layoutParams.width= WindowManager.LayoutParams.WRAP_CONTENT;
//            layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.title2);
            triangle2.setLayoutParams(layoutParams);
        }
    }

    private void Gettitls()//足球篮球。。。。
    {
        Httputils.PostWithBaseUrl(Httputils.ball, null, new MyJsonHttpResponseHandler(this,true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    finish();
                    ToastUtil.showMessage(Sports_MainActivity.this,"数据出问题了");
                }
                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                titles = new String[jsonArray.length()];
                titles_tag = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsob = jsonArray.optJSONObject(i);
                    titles[i] = jsob.optString("rName", "");
                    titles_tag[i] = jsob.optString("rType", "");
                }
                if (titles.length > 1)
                    title.setText(titles[1]);

                if (titles_tag.length > 1)
                    title.setTag(titles_tag[1]);


                BallType("bk", false);
                BallType("ft", false);
            }
        });
    }


    //新的弹出选择球赛盘口类型
    private void BallType(final String tag,final boolean isShowDialog)//新的方法
    {
        LogTools.e("tag",tag);
        if(maps.get(tag)!=null && isShowDialog)
        {
//            final MyStyleView myStyleView=new MyStyleView(Sports_MainActivity.this, maps.get(tag), new MyStyleView.OnItemClickListener() {
//                @Override
//                public void OnItemClick(int index,MyStyleView View) {
//                    Sports_MainActivity.this.index=index;
//                    CurrentTag= maps.get(title.getTag()+"").get(index).getrType();
//                    LogTools.e("CurrentTag",CurrentTag);
//                    basefragments.get(CurrentPage).clickfragment();
//                    View.close();
//                }
//
//            },Sports_MainActivity.this.index);
//            myStyleView.show();
            showMore2(topview,maps.get(tag));
            return;
        }

        RequestParams params=new RequestParams();
        params.put("ballType",tag);
        Httputils.PostWithBaseUrl(Httputils.type, params, new MyJsonHttpResponseHandler(this,true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000"))
                    return;
//                JSONObject tempjsob = jsonObject.optJSONObject("datas");
//                if (tempjsob == null) return;
                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                if (jsonArray == null) return;
                if (jsonArray == null || jsonArray.length() < 1)
                {
                    ToastUtil.showMessage(Sports_MainActivity.this, getString(R.string.projectnoexits));
                    return;
                }
                ArrayList<BallTypeBean> ballTypeBeans = new ArrayList<BallTypeBean>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = jsonArray.optJSONObject(i);
                    if(temp.optString("rType", "").equalsIgnoreCase("re") ||temp.optString("rType", "").equalsIgnoreCase("re_main"))
                        continue;
                    BallTypeBean bean = new BallTypeBean();
                    bean.setrName(temp.optString("rName", ""));
                    bean.setrType(temp.optString("rType", ""));
                    ballTypeBeans.add(bean);
                }
                CurrentTag = ballTypeBeans.get(0).getrType();
                title2.setText(ballTypeBeans.get(0).getrName());
                maps.put(tag, ballTypeBeans);
                if(maps.size()>1 && viewpager.getAdapter()==null)
                {
                    SetListtenerforInit();
                }
                if (isShowDialog) {
//                    MyStyleView myStyleView = new MyStyleView(Sports_MainActivity.this, ballTypeBeans, new MyStyleView.OnItemClickListener() {
//                        @Override
//                        public void OnItemClick(int index, MyStyleView View) {
//                            Sports_MainActivity.this.index=index;
//                            CurrentTag= maps.get(title.getTag()+"").get(index).getrType();
//                            LogTools.e("CurrentTag",CurrentTag);
//                            View.close();
//                        }
//                    },0);
//                    myStyleView.show();
                    showMore2(topview,ballTypeBeans);
                }

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
//            Contenttext.setText("762389237");

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

    public PopupWindow getPopWindow()
    {
        return popupWindowforCover;
    }

    public void setContenttext(String text)
    {
        Contenttext.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void SetListtenerforInit()//
    {

        SportsViewPagerAdapter sportsadapter=new SportsViewPagerAdapter(getSupportFragmentManager(),basefragments);
        viewpager.setAdapter(sportsadapter);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CurrentPage = position;
                if (CurrentPage == 1 || CurrentPage == 2) {
//                    imagetwo.setVisibility(View.VISIBLE);
                    re_chooser2.setVisibility(View.VISIBLE);
                    if (maps.get(title.getTag() + "") != null && maps.get(title.getTag() + "").size() > 0) {
                        CurrentTag = maps.get(title.getTag() + "").get(0).getrType();
                        title2.setText(maps.get(title.getTag() + "").get(0).getrName());
                    }
                } else {
                    re_chooser2.setVisibility(View.GONE);
                }
                if (CurrentPage < 3)
                    imagetwo.setVisibility(View.VISIBLE);
                else
                    imagetwo.setVisibility(View.GONE);
                RadioButton radioButton = (RadioButton) radiogroup.getChildAt(CurrentPage);
                radioButton.setChecked(true);

                if (CurrentPage < 3) {
                    imageone.setVisibility(View.VISIBLE);
                } else {
//                    imageone.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();

                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) FindView(radioButtonId);
                int tag = Integer.valueOf(rb.getTag() + "");
                CurrentPage = tag;
                if (CurrentPage == 1 || CurrentPage == 2) {
                    if (maps.get(title.getTag() + "") != null && maps.get(title.getTag() + "").size() > 0) {
                        Sports_MainActivity.this.index = 0;
                        CurrentTag = maps.get(title.getTag() + "").get(0).getrType();
                        LogTools.e("CurrentTag", CurrentTag);
                    }
                    re_chooser2.setVisibility(View.VISIBLE);
                } else {
                    re_chooser2.setVisibility(View.GONE);
                }
                if (CurrentPage < 3) {
                    imageone.setVisibility(View.VISIBLE);
                    imagetwo.setVisibility(View.VISIBLE);
                } else {
//                    imageone.setVisibility(View.GONE);
                    imagetwo.setVisibility(View.GONE);
                }
                recordbutton.setTextColor(getResources().getColor(R.color.gray10));
                recordbutton = rb;
                recordbutton.setTextColor(getResources().getColor(R.color.red2));
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation;
                translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setDuration(100);
                moveline.startAnimation(animationSet);//开始上面红色横条图片的动画切换
                int magin = ScreenWidth / 5;
                mCurrentCheckedRadioLeft = magin * tag;//更新当前蓝色横条距离左边的距离
//                mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft, 0);
                RelativeLayout.LayoutParams layoutparams = (RelativeLayout.LayoutParams) moveline.getLayoutParams();
                layoutparams.leftMargin = 10;
                layoutparams.rightMargin = 10;
                layoutparams.addRule(RelativeLayout.BELOW, R.id.radiogroup);
                moveline.setLayoutParams(layoutparams);
                viewpager.setCurrentItem(tag);
            }
        });
        viewpager.setCurrentItem(1);
        /////////////////初始化
//        recordbutton.setChecked(true);
    }

    private void GetRuleData(int type)
    {

        switch (type)
        {
            case 0:
                m_type="football";
                m_type_name="足球";
                break;
            case 1:
                m_type="basketbalradiogroupl";
                m_type_name="篮球";
                break;
        }
        RequestParams requestParams=new RequestParams();
        requestParams.put("ruleCode", m_type);
        requestParams.put("ruleType", "sport_rule_type");

        Httputils.PostWithBaseUrl(Httputils.Rule, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("onSuccessOfMe", jsonObject.toString());
                Intent intent = new Intent(Sports_MainActivity.this, webActivity.class);
                intent.putExtra(BundleTag.title, m_type_name);
                intent.putExtra(BundleTag.WebData, true);
                intent.putExtra(BundleTag.URL, jsonObject.optJSONObject("datas").optString("ruleContent", ""));
                startActivity(intent);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    public void SettitleclickEnable(boolean titleclickEnable)
    {
        this.titleclickEnable=titleclickEnable;
    }

//    private void BallType(final String tag,final boolean isShowDialog)//单式 波胆什么鬼的 放弃使用
//    {
//        if(maps.get(tag)!=null && isShowDialog)
//        {
//            final MyStyleView myStyleView=new MyStyleView(Sports_MainActivity.this, maps.get(tag), new MyStyleView.OnItemClickListener() {
//                @Override
//                public void OnItemClick(int index,MyStyleView View) {
//                    Sports_MainActivity.this.index=index;
//                    CurrentTag= maps.get(title.getTag()+"").get(index).getrType();
//                    LogTools.e("CurrentTag",CurrentTag);
//                    basefragments.get(CurrentPage).clickfragment();
//                    View.close();
//                }
//
//            },Sports_MainActivity.this.index);
//            myStyleView.show();
//            return;
//        }
//
//        RequestParams params=new RequestParams();
//        params.put("ballType",tag);
//        Httputils.PostWithBaseUrl(Httputils.type, params, new MyJsonHttpResponseHandler(this,true) {
//            @Override
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//            }
//
//            @Override
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000"))
//                    return;
////                JSONObject tempjsob = jsonObject.optJSONObject("datas");
////                if (tempjsob == null) return;
//                JSONArray jsonArray = jsonObject.optJSONArray("datas");
//                if (jsonArray == null) return;
//                if (jsonArray == null || jsonArray.length() < 1)
//                {
//                    ToastUtil.showMessage(Sports_MainActivity.this, getString(R.string.projectnoexits));
//                    return;
//                }
//                ArrayList<BallTypeBean> ballTypeBeans = new ArrayList<BallTypeBean>();
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject temp = jsonArray.optJSONObject(i);
//                    if(temp.optString("rType", "").equalsIgnoreCase("re") ||temp.optString("rType", "").equalsIgnoreCase("re_main"))
//                        continue;
//                    BallTypeBean bean = new BallTypeBean();
//                    bean.setrName(temp.optString("rName", ""));
//                    bean.setrType(temp.optString("rType", ""));
//                    ballTypeBeans.add(bean);
//                }
//                CurrentTag = ballTypeBeans.get(0).getrType();
//                maps.put(tag, ballTypeBeans);
//                if(maps.size()>1 && viewpager.getAdapter()==null)
//                {
//                    SetListtenerforInit();
//                }
//                if (isShowDialog) {
//                    MyStyleView myStyleView = new MyStyleView(Sports_MainActivity.this, ballTypeBeans, new MyStyleView.OnItemClickListener() {
//                        @Override
//                        public void OnItemClick(int index, MyStyleView View) {
//                            Sports_MainActivity.this.index=index;
//                            CurrentTag= maps.get(title.getTag()+"").get(index).getrType();
//                            LogTools.e("CurrentTag",CurrentTag);
//                            View.close();
//                        }
//                    },0);
//                    myStyleView.show();
//                }
//
//            }
//        });
//    }
}
