package com.a8android888.bocforandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.GameroomAdapter;
import com.a8android888.bocforandroid.Adapter.MainPagerAdapter;
import com.a8android888.bocforandroid.Adapter.MainbannerPagerAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.BannerBean;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.ModuleBean;
import com.a8android888.bocforandroid.activity.CustomerActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.NoticeActivity;
import com.a8android888.bocforandroid.activity.main.ActicleListActivity;
import com.a8android888.bocforandroid.activity.main.BBNActivity;
import com.a8android888.bocforandroid.activity.main.BackUpAddressActivity;
import com.a8android888.bocforandroid.activity.main.CardActivity;
import com.a8android888.bocforandroid.activity.main.Electronic_gamesActivity;
import com.a8android888.bocforandroid.activity.main.GetWebdata;
import com.a8android888.bocforandroid.activity.main.JumpActivity;
import com.a8android888.bocforandroid.activity.main.Jumpdata;
import com.a8android888.bocforandroid.activity.main.Real_videoActivity;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_MainActivity;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_eventsActivity;
import com.a8android888.bocforandroid.activity.main.game.GameFragment;
import com.a8android888.bocforandroid.activity.main.gamewebActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragement;
import com.a8android888.bocforandroid.activity.user.Change.ChangeActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.activity.user.RegActivity;
import com.a8android888.bocforandroid.activity.user.UserMessageActivity.BindBankActivity;
import com.a8android888.bocforandroid.activity.user.WithDraw.WithDrawActivty;
import com.a8android888.bocforandroid.activity.user.WithDraw.WithDrawListActivty;
import com.a8android888.bocforandroid.activity.user.pay.PayActivitty;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.MarqueeTextView;
import com.a8android888.bocforandroid.view.MyLinearLayout;
import com.a8android888.bocforandroid.view.MyRelativeLayout;
import com.a8android888.bocforandroid.view.MyViewPager;
import com.a8android888.bocforandroid.view.NoticeDialog;
import com.a8android888.bocforandroid.webActivity;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.a8android888.bocforandroid.view.MarqueeTextView.*;

/**
 * Created by Administrator on 2017/3/28.
 */
public class MainActivity2 extends BaseActivity implements View.OnClickListener {

    LinearLayout temp1, temp2;
    MyViewPager viewpager;
    private MarqueeTextView textdd;
    ImageView appicon;
    private ImageLoader mImageDownLoader;
    private Intent intent;
    ImageView back;
    String urls[];
    private ImageHandler handler = new ImageHandler(new WeakReference<MainActivity2>(MainActivity2.this));
    int index = 0;
    String notice[] = null;
    TextView login, register;
    RelativeLayout relayout;
    LinearLayout linearLayout;
    ArrayList<ImageView> imageviews = new ArrayList<ImageView>();
    int recordindex = 0;
    ArrayList<ModuleBean> gameBeanList;
    ArrayList<GameBean> gameBeanList6 = new ArrayList<GameBean>();
    ;
    ArrayList<ArrayList<ModuleBean>> gameList = new ArrayList<ArrayList<ModuleBean>>();
    ImageDownLoader mImageDownLoader2;
    LinearLayout bottomview, noticeview;
    LinearLayout mainlayout;
    View popupView, popupViewgg, popupViewbottom, popupViewtemp1, popupViewtemp2;
    ArrayList<BannerBean> BannerBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        inintview();
    }

    public void inintview() {
        mImageDownLoader = ((BaseApplication) MainActivity2.this.getApplication())
                .getImageLoader();
        mImageDownLoader2 = new ImageDownLoader(MainActivity2.this);
        appicon = FindView(R.id.appicon);

        urls = getResources().getStringArray(R.array.urllist);

        back = FindView(R.id.back);
        back.setImageDrawable(null);
        back.setOnClickListener(null);

        mainlayout = (LinearLayout) FindView(R.id.mainlayout);
        popupView = View.inflate( MainActivity2.this, R.layout.mainlayut_banner, null);
        relayout = (RelativeLayout) popupView.findViewById(R.id.relayout);
        viewpager = (MyViewPager) popupView.findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(2);
        viewpager.setOnMeasureListener(new MyViewPager.onMeasureListener() {

            @Override
            public void onmeaure(int height) {

            }
        });

        popupViewbottom = View.inflate( MainActivity2.this, R.layout.mainlayut_bottom, null);
        bottomview = (LinearLayout) popupViewbottom.findViewById(R.id.bottomview);

        popupViewgg = View.inflate( MainActivity2.this, R.layout.mainlayut_gg, null);
        noticeview = (LinearLayout) popupViewgg.findViewById(R.id.noticeview);
        textdd = (MarqueeTextView) popupViewgg.findViewById(R.id.textdd);
        textdd.setOnClickListener(MainActivity2.this);
        textdd.setOnMarqueeCompleteListener(new OnMarqueeCompleteListener() {
            @Override
            public void onMarqueeComplete(MarqueeTextView view) {

                if (notice != null) {
                    if (index == notice.length - 1){
                        index = 0;}
                    else{
                        index++;}
                    view.setText(Html.fromHtml(notice[index]));
                    LogTools.e("onMarqueeComplete", "onMarqueeComplete" + notice[index]);
                }
                view.startFor0();
            }
        });


        login = FindView(R.id.login);
        login.setVisibility(View.GONE);
        login.setOnClickListener(MainActivity2.this);
        register = FindView(R.id.register);
        register.setVisibility(View.GONE);
        register.setOnClickListener(MainActivity2.this);

        popupViewtemp1 = View.inflate( MainActivity2.this, R.layout.mainlayut_tmep, null);
        temp1 = (LinearLayout) popupViewtemp1.findViewById(R.id.temp1);
        popupViewtemp2 = View.inflate( MainActivity2.this, R.layout.mainlayut_tmep2, null);
        temp2 = (LinearLayout) popupViewtemp2.findViewById(R.id.temp2);
        SharedPreferences sharedPreferences = ((BaseApplication) this.getApplication()).getSharedPreferences();
        JSONObject jsonObject = null;
        JSONObject object=new JSONObject();
        try {
            jsonObject = new JSONObject(sharedPreferences.getString(BundleTag.ggList, ""));
            object.put("datas",jsonObject);
            LogTools.e("zzzz2",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Boolean isc = ((BaseApplication) this.getApplication()).isNetConnect();
        if(!isc){
            if (!object.optString("datas").equalsIgnoreCase("null")&&object.optString("datas").length()>2) {
                JSONObject datas = jsonObject.optJSONObject("datas");
                if (!datas.optString("gonggaoType").equalsIgnoreCase("0")) {
                    NoticeDialog dialog = new NoticeDialog(this,"nopull");
                    dialog.setdata(object);
                    dialog.show();
                    ((BaseApplication) this.getApplication()).setNoticehasClick(true);
                }
            }
        }
        GetMainPageData();
        module2();
    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(ImageHandler.MSG_UPDATE_IMAGE);
        if (textdd != null)
            textdd.startStopMarquee(false);
        LogTools.e("huilai", "回来");
    }


    private void AddButton(RelativeLayout relayout, int size) {
        imageviews.clear();
        linearLayout = new LinearLayout(MainActivity2.this);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        linearLayout.setPadding(0, 0, 0, ScreenUtils.getDIP2PX(MainActivity2.this, 10));
        linearLayout.setLayoutParams(params);

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(MainActivity2.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 10;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.yindaocheck));
            imageviews.add(imageView);
            linearLayout.addView(imageView);
        }

        relayout.addView(linearLayout);
    }

    public void GetMainPageData() {
        SharedPreferences sharedPreferences = ((BaseApplication) MainActivity2.this.getApplication()).getSharedPreferences();
        String logouir = sharedPreferences.getString(BundleTag.logoUrl, "");
//
        mImageDownLoader.displayImage(logouir, back, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if(bitmap==null)return;
                Bitmap resizeBmp = bitmap;
                Matrix matrix = new Matrix();
                if(bitmap==null)return;
                float mah=ScreenUtils.getDIP2PX(MainActivity2.this, 28)*0.01f/bitmap.getHeight() *100;
                matrix.postScale(mah, mah); //长和宽放大缩小的比例
                resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ((ImageView)view).setImageBitmap(resizeBmp);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
        intent = new Intent(MainActivity2.this, CustomerActivity.class);
        intent.putExtra(BundleTag.Phonenum, sharedPreferences.getString(BundleTag.Phonenum, ""));
        intent.putExtra(BundleTag.QQ, sharedPreferences.getString(BundleTag.QQ, ""));
        intent.putExtra(BundleTag.Email, sharedPreferences.getString(BundleTag.Email, ""));
        intent.putExtra(BundleTag.Weixin, sharedPreferences.getString(BundleTag.Weixin, ""));
        intent.putExtra(BundleTag.Tel, sharedPreferences.getString(BundleTag.Tel, ""));
        intent.putExtra(BundleTag.URL, sharedPreferences.getString(BundleTag.URL, ""));
    }



    private void AddTempView2() {
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int itemcount = gameBeanList6.size();
        int count = itemcount % 2 == 0 ? itemcount / 2 : (itemcount / 2) + 1;
        for (int i = 0; i < count; i++) {
            LogTools.e("12345sss6", gameBeanList6.size() + "");
            LinearLayout linearLayout = new LinearLayout(MainActivity2.this);
            linearLayout.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            int tempcount = itemcount - i * 2 > 2 ? (2 * (i + 1)) : itemcount;
            for (int j = i * 2; j < tempcount; j++) {
                LinearLayout linearLayout1 = new LinearLayout(this);
                linearLayout1.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams linearLayout1Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout1Params.weight = 1;
                linearLayout1.setLayoutParams(linearLayout1Params);
                MyRelativeLayout relativeLayout = new MyRelativeLayout(this);
                relativeLayout.setGravity(Gravity.CENTER);
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                relativeLayout.setLayoutParams(layoutParams2);

                LinearLayout m_linearLayout = new LinearLayout(MainActivity2.this);
                m_linearLayout.setTag(j);
                m_linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int tag = Integer.valueOf(v.getTag() + "");
                        LogTools.e("tag"+gameBeanList6.size(),tag + "");
                        if (gameBeanList6.get(tag).getFlat().equalsIgnoreCase("live")) {
                            startActivity(new Intent(MainActivity2.this, Real_videoActivity.class));
                        }
                        if (gameBeanList6.get(tag).getFlat().equalsIgnoreCase("electronic")) {
                            startActivity(new Intent(MainActivity2.this, Electronic_gamesActivity.class));
                        }
                        if (gameBeanList6.get(tag).getFlat().equalsIgnoreCase("bbin")) {
                            startActivity(new Intent(MainActivity2.this, BBNActivity.class));
                        }
                        if (gameBeanList6.get(tag).getFlat().equalsIgnoreCase("sport")) {
                            startActivity(new Intent(MainActivity2.this, Sports_eventsActivity.class));
                        }
                        if (gameBeanList6.get(tag).getFlat().equalsIgnoreCase("lottery")) {
                            startActivity(new Intent(MainActivity2.this, LotteryFragement.class));
                        }
                        if (gameBeanList6.get(tag).getFlat().equalsIgnoreCase("card")) {
                            startActivity(new Intent(MainActivity2.this, CardActivity.class));
                        }
                    }
                });
                m_linearLayout.setGravity(Gravity.CENTER);
                int width = ScreenUtils.getScreenWH( MainActivity2.this)[0] / 2;
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params2.topMargin = ScreenUtils.getDIP2PX(MainActivity2.this, 10);
                ;
                params2.bottomMargin = ScreenUtils.getDIP2PX(MainActivity2.this, 10);
                ;
                m_linearLayout.setLayoutParams(params2);
                m_linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                ImageView image = new ImageView(MainActivity2.this);
                image.setLayoutParams(new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(MainActivity2.this, 40), ScreenUtils.getDIP2PX(MainActivity2.this, 40)));

                mImageDownLoader.displayImage(gameBeanList6.get(j).getImg(), image);
                m_linearLayout.addView(image);

                TextView textview = new TextView(MainActivity2.this);
                textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lay.leftMargin = ScreenUtils.getDIP2PX(MainActivity2.this, 15);
                textview.setLayoutParams(lay);
                textview.setTextColor(0xFF000000);
                textview.setText(gameBeanList6.get(j).getName());
                m_linearLayout.addView(textview);
                m_linearLayout.setPaddingRelative(ScreenUtils.getDIP2PX(MainActivity2.this, 8), ScreenUtils.getDIP2PX(MainActivity2.this, 8), ScreenUtils.getDIP2PX(MainActivity2.this, 8), ScreenUtils.getDIP2PX(MainActivity2.this, 8));

                ImageView line = new ImageView(MainActivity2.this);
                line.setBackgroundColor(getResources().getColor(R.color.line));
                line.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.FILL_PARENT));

                ImageView image2 = new ImageView(this);
                RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                image2.setLayoutParams(layoutParams3);
                mImageDownLoader2.SetSpecialView(this, gameBeanList6.get(j).getBigBackgroundPic(), linearLayout1);

                relativeLayout.addView(m_linearLayout);
                relativeLayout.addView(image2);
                linearLayout1.addView(relativeLayout);
                linearLayout.addView(linearLayout1);
                linearLayout.addView(line);
            }
            temp2.addView(linearLayout);
            ImageView line = new ImageView(MainActivity2.this);
            line.setBackgroundColor(getResources().getColor(R.color.line));
            line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
            temp2.addView(line);
        }

        mainlayout.addView(popupViewtemp2);
    }
    private void moduleview() {
        View popupViewtemp3 = View.inflate( MainActivity2.this, R.layout.mainlayut_tmep3, null);
        LinearLayout temp3 = (LinearLayout) popupViewtemp3.findViewById(R.id.temp3);
        for (int i = 0; i < gameList.size(); i++) {
            LinearLayout linearLayout = new LinearLayout(MainActivity2.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            int itemcount = gameList.get(i).size();
            for (int k = 0; k < gameList.get(i).size(); k++) {
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("1")) {
                    View popupView = View.inflate( MainActivity2.this, R.layout.module1_layout, null);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
                    TextView title = (TextView) popupView.findViewById(R.id.title);
                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getArticleSmallImages(), img);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this,gameList.get(i).get(k).getBigBackgroundPic(), popupView);

                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setText(gameList.get(i).get(k).getArticleSubTitle());
                    final int indexa = i;
                    popupView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });

                    linearLayout.addView(popupView);
                }

                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("2")) {
                    View popupView = View.inflate( MainActivity2.this, R.layout.module2_layout, null);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
                    TextView title = (TextView) popupView.findViewById(R.id.title);
                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getArticleSmallImages(), img);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);

                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setText(gameList.get(i).get(k).getArticleSubTitle());
                    final int indexa = i;
                    popupView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);
                }
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("3")) {
                    View popupView = View.inflate( MainActivity2.this, R.layout.module3_layout, null);

                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);

                    contact.setText(gameList.get(i).get(k).getArticleTitle());
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getArticleSmallImages(), img);
                    final int indexa = i;
                    popupView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);
                }
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("4")) {
                    View popupView = View.inflate( MainActivity2.this, R.layout.module5_layout, null);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
                    TextView title = (TextView) popupView.findViewById(R.id.title);
                    TextView contact = (TextView) popupView.findViewById(R.id.contact);
                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setTag(k);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    mImageDownLoader2.showImage(MainActivity2.this.getApplicationContext(), gameList.get(i).get(k).getArticleSmallImages(), img, -1);
                    final int indexa = i;
                    contact.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);
                }
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("5")) {
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    View popupView = View.inflate( MainActivity2.this, R.layout.module4_layout, null);
                    popupView.setLayoutParams(layoutParams1);
                    popupView.setTag(k);
                    ImageView img = (ImageView) popupView.findViewById(R.id.img);
                    RelativeLayout dd=(RelativeLayout)popupView.findViewById(R.id.layoutre);
                    dd.setVisibility(GONE);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this,gameList.get(i).get(k).getBigBackgroundPic(), popupView);

                    mImageDownLoader2.showImage(MainActivity2.this.getApplicationContext(), gameList.get(i).get(k).getArticleSmallImages(), img, 0);
                    final int indexa = i;
                    popupView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);

                }
                if (gameList.get(i).get(k).getModuleType().equalsIgnoreCase("6")) {
                    LogTools.e("hhhhhkaaadd", itemcount + "");
                    LinearLayout hor = new LinearLayout(MainActivity2.this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    hor.setLayoutParams(params);
                    hor.setOrientation(LinearLayout.HORIZONTAL);
                    int tempcount = itemcount - k * 3 > 3 ? (3 * (k + 1)) : itemcount;
                    int count = tempcount % 3 == 0 ? tempcount : (tempcount / 3 + 1) * 3;
                    for (int j = k * 3; j < count; j++) {
                        final int iii = j;
                        LinearLayout popupView = (LinearLayout) View.inflate( MainActivity2.this, R.layout.real_video_item, null);
                        TextView videoname = (TextView) popupView.findViewById(R.id.videoname);
                        ImageView videoimg = (ImageView) popupView.findViewById(R.id.videoimg);
                        if (j >= gameList.get(i).size()) {
                            for (int l = 0; l < popupView.getChildCount(); l++) {
                                popupView.getChildAt(l).setVisibility(View.INVISIBLE);
                            }

                        } else {
                            for (int l = 0; l < popupView.getChildCount(); l++) {
                                popupView.getChildAt(l).setVisibility(View.VISIBLE);
                            }
                            popupView.setTag(iii);
                            LogTools.e("hhhhhkaaavv", gameList.get(i).get(j).getArticleType());
//                            mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(j).getBigBackgroundPic(), popupView, 0);
                            ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                            mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(j).getBigBackgroundPic(), popupView);
                            mImageDownLoader.displayImage(gameList.get(i).get(j).getArticleSmallImages(), videoimg);
                            videoname.setText(gameList.get(i).get(j).getArticleTitle());
                            final int indexa = i;
                            popupView.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final int tag = Integer.valueOf(v.getTag() + "");
                                    modeluonclick(indexa, tag);
                                }
                            });
                        }
                        int width = ScreenUtils.getScreenWH( MainActivity2.this)[0] / 3;
                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupView.setLayoutParams(params2);
                        hor.addView(popupView);
                        ImageView line = new ImageView(MainActivity2.this);
                        line.setBackgroundColor(getResources().getColor(R.color.gray1));
                        line.setLayoutParams(new ViewGroup.LayoutParams(2, ViewGroup.LayoutParams.FILL_PARENT));
                        hor.addView(line);
                    }

                    linearLayout.addView(hor);
                    ImageView line = new ImageView(MainActivity2.this);
                    line.setBackgroundColor(getResources().getColor(R.color.gray1));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 2));
                    linearLayout.addView(line);

                }

            }
            temp3.addView(linearLayout);
            ImageView line = new ImageView(MainActivity2.this);
            line.setBackgroundColor(getResources().getColor(R.color.gray1));
            line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 20));
            temp3.addView(line);
        }
        mainlayout.addView(popupViewtemp3);
    }

    public void modeluonclick(int indexa, int tag) {
        JumpActivity.modeluonclick(MainActivity2.this, gameList.get(indexa).get(tag).getLinkType(),
                gameList.get(indexa).get(tag).getOpenLinkType(), gameList.get(indexa).get(tag).getTypeCode(),
                gameList.get(indexa).get(tag).getLevel(), gameList.get(indexa).get(tag).getCateCode(),
                gameList.get(indexa).get(tag).getGameCode(), gameList.get(indexa).get(tag).getArticleName(),
                gameList.get(indexa).get(tag).getArticleTitle(), gameList.get(indexa).get(tag).getLinkUrl()
                , gameList.get(indexa).get(tag).getArticleType(), gameList.get(indexa).get(tag).getArticleId(),"nopull","2",gameList.get(indexa).get(tag).getArticleId());
    }

    private void module2() {
                   /**/
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", ((BaseApplication) MainActivity2.this.getApplication()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.home, requestParams, new MyJsonHttpResponseHandler(MainActivity2.this, false) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("module2", jsonObject.toString());
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray json = jsonObject.getJSONArray("datas");
                    temp1.removeAllViews();
                    temp2.removeAllViews();
                    bottomview.removeAllViews();
                    mainlayout.removeAllViews();
                    BannerBeanList = new ArrayList<BannerBean>();
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject jsonda = json.getJSONObject(i);
                        //轮播图
                        if (jsonda.optString("regionType").equalsIgnoreCase("1")) {
                            String strs[] = {};
                            try {
                                JSONArray jsonArray5 = jsonda.getJSONArray("data");

                                if (!BannerBeanList.isEmpty()) {
                                    BannerBeanList.clear();

                                }
                                if (jsonArray5!=null&&jsonArray5.length()>0) {
                                    strs = new String[jsonArray5.length()];

                                    for (int k = 0; k < jsonArray5.length(); k++) {
                                        strs[k] = jsonArray5.optJSONObject(k).optString("imageUrl", "");
                                        BannerBean gameBean1 = new BannerBean();
                                        gameBean1.setBannerName(jsonArray5.optJSONObject(k).optString("bannerName", ""));
                                        gameBean1.setBannerType(jsonArray5.optJSONObject(k).optString("bannerType", ""));
                                        gameBean1.setCateCode(jsonArray5.optJSONObject(k).optString("cateCode", ""));
                                        gameBean1.setCreateTime(jsonArray5.optJSONObject(k).optString("createTime", ""));
                                        gameBean1.setGameCode(jsonArray5.optJSONObject(k).optString("gameCode", ""));
                                        gameBean1.setImageUrl(jsonArray5.optJSONObject(k).optString("imageUrl", ""));
                                        gameBean1.setLevel(jsonArray5.optJSONObject(k).optString("level", ""));
                                        gameBean1.setLinkGroupId(jsonArray5.optJSONObject(k).optString("linkGroupId", ""));
                                        gameBean1.setLinkName(jsonArray5.optJSONObject(k).optString("linkName", ""));
                                        gameBean1.setLinkType(jsonArray5.optJSONObject(k).optString("linkType", ""));
                                        gameBean1.setLinkUrl(jsonArray5.optJSONObject(k).optString("linkUrl", ""));
                                        gameBean1.setModifyTime(jsonArray5.optJSONObject(k).optString("modifyTime", ""));
                                        gameBean1.setOpenLinkType(jsonArray5.optJSONObject(k).optString("openLinkType", ""));
                                        gameBean1.setPlatformType(jsonArray5.optJSONObject(k).optString("platformType", ""));
                                        gameBean1.setTypeCode(jsonArray5.optJSONObject(k).optString("typeCode", ""));
                                        gameBean1.setArticleId(jsonArray5.optJSONObject(k).optString("articleId", ""));
                                        gameBean1.setArticleType(jsonArray5.optJSONObject(k).optString("articleType", ""));
//                                            }
                                        BannerBeanList.add(gameBean1);
                                    }
                                    RelativeLayout testlayout = (RelativeLayout) popupView.findViewById(R.id.testlayout);
                                    AddButton(testlayout, jsonArray5.length());
                                    LogTools.e("jsonArray5", jsonArray5.length() + "");
                                    if (strs.length > 0) {
                                        imageviews.get(0).setImageDrawable(getResources().getDrawable(R.drawable.yindaochedked));
                                        recordindex=0;
                                    }

                                    viewpager.setAdapter(new MainbannerPagerAdapter(MainActivity2.this, strs,BannerBeanList));

                                    viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                        //配合Adapter的currentItem字段进行设置。
                                        @Override
                                        public void onPageSelected(int arg0) {

                                            imageviews.get(recordindex).setImageDrawable(getResources().getDrawable(R.drawable.yindaocheck));
                                            recordindex = arg0;
                                            imageviews.get(recordindex).setImageDrawable(getResources().getDrawable(R.drawable.yindaochedked));
                                            handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
                                        }

                                        @Override
                                        public void onPageScrolled(int arg0, float arg1, int arg2) {
                                        }

                                        //覆写该方法实现轮播效果的暂停和恢复
                                        @Override
                                        public void onPageScrollStateChanged(int arg0) {
                                            switch (arg0) {
                                                case ViewPager.SCROLL_STATE_DRAGGING:
                                                    handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                                                    break;
                                                case ViewPager.SCROLL_STATE_IDLE:
                                                    handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    });

                                    handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                                    mainlayout.addView(popupView);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //公告
                        if (jsonda.optString("regionType").equalsIgnoreCase("2")) {
                            try {
                                JSONArray jsonArray5 = jsonda.getJSONArray("data");
                                if (jsonArray5 != null&&jsonArray5.length()>0) {
                                    notice = new String[jsonArray5.length()];
                                    for (int i1 = 0; i1 < jsonArray5.length(); i1++) {
                                        String str = jsonArray5.optJSONObject(i1).optString("ggName", "");
                                        notice[i1] = str;
                                    }
                                    textdd.startStopMarquee(false);
                                    textdd.setText(Html.fromHtml(notice[0]));
                                    textdd.startFor0();

                                    mainlayout.addView(popupViewgg);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //会员功能
                        if (jsonda.optString("regionType").equalsIgnoreCase("3")) {
                            try {
                                JSONArray jsonArray5 = jsonda.getJSONArray("data");

                                if (jsonArray5 != null&&jsonArray5.length()>0) {
                                    String arrs1[] = new String[jsonArray5.length()];
                                    for (int i3 = 0; i3 < jsonArray5.length(); i3++) {

                                        String str = jsonArray5.optJSONObject(i3).optString("menuName", "");
                                        arrs1[i3] = str;
                                        MyLinearLayout linearLayout1 = new MyLinearLayout(MainActivity2.this);
                                        linearLayout1.setGravity(Gravity.CENTER);
                                        LinearLayout.LayoutParams linearLayout1Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        linearLayout1Params.weight = 1;
                                        linearLayout1.setLayoutParams(linearLayout1Params);

                                        RelativeLayout relativeLayout = new RelativeLayout(MainActivity2.this);
                                        relativeLayout.setGravity(Gravity.CENTER);
                                        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        relativeLayout.setLayoutParams(layoutParams2);
                                        
                                        
                                        LinearLayout linearLayout = new LinearLayout(MainActivity2.this);
                                        linearLayout.setTag(i3);
                                        linearLayout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                final int tag = Integer.valueOf(v.getTag() + "");
                                                if (tag == 0) {
                                                    String Username = ((BaseApplication) MainActivity2.this.getApplication()).getBaseapplicationUsername();
                                                    if (Username == null || Username.equalsIgnoreCase("")) {
                                                        Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                                                        MainActivity2.this.startActivity(intent);
                                                        return;
                                                    }
                                                    startActivity(new Intent(MainActivity2.this, PayActivitty.class));
                                                }
                                                if (tag == 1) {
                                                    String Username = ((BaseApplication) MainActivity2.this.getApplication()).getBaseapplicationUsername();
                                                    if (Username == null || Username.equalsIgnoreCase("")) {
                                                        Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                                                        MainActivity2.this.startActivity(intent);
                                                        return;
                                                    }
                                                    SharedPreferences sharedPreferences = ((BaseApplication) MainActivity2.this.getApplication()).getSharedPreferences();
                                                    String UserInfo = sharedPreferences.getString(BundleTag.UserInfo, "");
                                                    JSONObject jsonObject = null;
                                                    try {
                                                        jsonObject = new JSONObject(UserInfo);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    String userName = jsonObject.optString("userRealName", "");

                                                    Intent itent = new Intent(MainActivity2.this, WithDrawListActivty.class);
                                                    itent.putExtra("name", userName);
                                                    itent.putExtra("type", "1");
                                                    itent.putExtra("title", MainActivity2.this.getResources().getText(R.string.userwithdrawal));
                                                    startActivity(itent);
                                                }
                                                if (tag == 2) {
                                                    String Username = ((BaseApplication) MainActivity2.this.getApplication()).getBaseapplicationUsername();
                                                    if (Username == null || Username.equalsIgnoreCase("")) {

                                                        Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                                                        MainActivity2.this.startActivity(intent);
                                                        return;
                                                    }
                                                    startActivity(new Intent(MainActivity2.this, ChangeActivity.class));
                                                }
                                                if (tag == 3) {
                                                    if (intent != null)
                                                        startActivity(intent);
                                                }
                                            }
                                        });
                                        linearLayout.setGravity(Gravity.CENTER);
                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        layoutParams.weight = 1;
                                        layoutParams.leftMargin = 5;
                                        layoutParams.rightMargin = 5;
                                        layoutParams.topMargin = 5;
                                        layoutParams.bottomMargin = 5;
                                        linearLayout.setLayoutParams(layoutParams);
                                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                                        linearLayout.setPaddingRelative(ScreenUtils.getDIP2PX(MainActivity2.this, 15), ScreenUtils.getDIP2PX(MainActivity2.this, 5), ScreenUtils.getDIP2PX(MainActivity2.this, 15), ScreenUtils.getDIP2PX(MainActivity2.this, 5));

                                        ImageView image = new ImageView(MainActivity2.this);
                                        image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        image.setAdjustViewBounds(true);
                                        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                        mImageDownLoader.displayImage(jsonArray5.optJSONObject(i3).optString("bigPic", ""), image);
                                        linearLayout.addView(image);

                                        TextView textview = new TextView(MainActivity2.this);

                                        textview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                        textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                        textview.setTextColor(0xFF000000);
                                        textview.setText(arrs1[i3]);
                                        linearLayout.addView(textview);

                                        ImageView image2 = new ImageView(MainActivity2.this);
                                        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                                        image2.setLayoutParams(layoutParams3);
                                        mImageDownLoader2.SetSpecialView(MainActivity2.this, jsonArray5.optJSONObject(i3).optString("bigBackgroundPic", ""), linearLayout1);
                                        relativeLayout.addView(image2);
                                        relativeLayout.addView(linearLayout);
                                        linearLayout1.addView(relativeLayout);
                                        temp1.addView(linearLayout1);
                                        
                                        ImageView line = new ImageView(MainActivity2.this);
                                        line.setBackgroundColor(getResources().getColor(R.color.line));
                                        line.setLayoutParams(new ViewGroup.LayoutParams(1, ViewGroup.LayoutParams.FILL_PARENT));
                                        temp1.addView(line);
                                    }

                                    mainlayout.addView(popupViewtemp1);
                                    ImageView line = new ImageView(MainActivity2.this);
                                    line.setBackgroundColor(getResources().getColor(R.color.line));
                                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,1));
                                    mainlayout.addView(line);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        //功能列表
                        if (jsonda.optString("regionType").equalsIgnoreCase("4")) {
                            try {
                                JSONArray jsonArray = jsonda.getJSONArray("data");
                                LogTools.e("dddaa", jsonda.getJSONArray("data").toString());
                                if (jsonArray != null&&jsonArray.length()>0) {
                                    if (!gameBeanList6.isEmpty()) {
                                        gameBeanList6.clear();

                                    }
                                    for (int i2 = 0; i2 < jsonArray.length(); i2++) {
                                        JSONObject jsondaw = jsonArray.getJSONObject(i2);
                                        LogTools.e("dddaa", jsondaw.optString("menuName", ""));
                                        GameBean gameBean = new GameBean();   gameBean.setBigBackgroundPic(jsondaw.optString("bigBackgroundPic", ""));
                                        gameBean.setName(jsondaw.optString("menuName", ""));
                                        gameBean.setImg(jsondaw.optString("bigPic", ""));
                                        gameBean.setFlat(jsondaw.optString("menuCode", ""));
                                        gameBeanList6.add(gameBean/*jsonda.optString("flatName")*/);
                                    }
                                    AddTempView2();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //功能列表
                        if (jsonda.optString("regionType").equalsIgnoreCase("5")) {
                            try {
                                JSONArray jsonArray = jsonda.getJSONArray("data");

                                if (jsonArray != null&&jsonArray.length()>0) {
                                    for (int i2 = 0; i2 < jsonArray.length(); i2++) {
                                        final JSONObject jsondaw = jsonArray.getJSONObject(i2);
                                        TextView textview = new TextView(MainActivity2.this);
                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                        layoutParams.weight = 1;
                                        textview.setLayoutParams(layoutParams);
                                        textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                                        textview.setTag(i2);
                                        textview.setTextColor(Color.WHITE);
                                        textview.setGravity(Gravity.CENTER);
                                        textview.setText(jsondaw.optString("pgTitle"));
                                        textview.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(MainActivity2.this, webActivity.class);
                                                intent.putExtra("title", jsondaw.optString("pgTitle"));
                                                intent.putExtra(BundleTag.URL, jsondaw.optString("pgSn"));
                                                startActivity(intent);
                                            }
                                        });
                                        bottomview.addView(textview);
                                    }
                                    mainlayout.addView(popupViewbottom);
                                } else {
                                    bottomview.setVisibility(GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //
                        if (jsonda.optString("regionType").equalsIgnoreCase("6")) {
                            try {

                                JSONArray jsonArray3 = jsonda.getJSONArray("data");
                                if (!gameList.isEmpty()) {
                                    gameList.clear();
                                    gameBeanList.clear();

                                }
                                LogTools.e("jsonArray6", jsonArray3.length() + "");
                                for (int i3 = 0; i3 < jsonArray3.length(); i3++) {
                                    JSONObject jsondaa = jsonArray3.getJSONObject(i3);
                                    gameBeanList = new ArrayList<ModuleBean>();
                                    JSONArray jsonArray5 = jsondaa.getJSONArray("article");
                                    try {
                                        if (jsonArray5 != null&&jsonArray5.length()>0) {

                                            for (int k = 0; k < jsonArray5.length(); k++) {
                                                JSONObject jsond = jsonArray5.getJSONObject(k);
                                                ModuleBean gameBean1 = new ModuleBean();

                                                gameBean1.setModuleId(jsondaa.optString("moduleId", ""));
                                                gameBean1.setRemark(jsondaa.optString("remark", ""));
                                                gameBean1.setModuleType(jsondaa.optString("moduleType", ""));
                                                gameBean1.setModuleName(jsondaa.optString("moduleName", ""));
                                                gameBean1.setModuleCode(jsondaa.optString("moduleCode", ""));
                                                gameBean1.setBigBackgroundPic(jsond.optString("backgroundPicUrl", ""));
                                                gameBean1.setCreateTime(jsond.optString("createTime", ""));
                                                gameBean1.setArticleBigImages(jsond.optString("articleBigImages", ""));
                                                gameBean1.setArticleCode(jsond.optString("articleCode", ""));
                                                gameBean1.setLinkType(jsond.optString("linkType", ""));
                                                gameBean1.setLinkUrl(jsond.optString("linkUrl", ""));
                                                gameBean1.setOpenLinkType(jsond.optString("openLinkType", ""));
                                                gameBean1.setArticleSmallImages(jsond.optString("articleSmallImages", ""));
                                                gameBean1.setArticleBigHeight(jsond.optString("articleBigHeight", ""));
                                                gameBean1.setArticleSmallHeight(jsond.optString("articleSmallHeight", ""));
                                                gameBean1.setArticleId(jsond.optString("articleId", ""));
                                                gameBean1.setArticleName(jsond.optString("articleName", ""));
                                                gameBean1.setArticleBigWidth(jsond.optString("articleBigWidth", ""));
                                                gameBean1.setArticleTitle(jsond.optString("articleTitle", ""));
                                                gameBean1.setArticleSmallWidth(jsond.optString("articleSmallWidth", ""));
                                                gameBean1.setArticleSubTitle(jsond.optString("articleSubTitle", ""));
                                                gameBean1.setArticleContent(jsond.optString("articleContent", ""));
                                                gameBean1.setShowType(jsond.optString("showType", ""));
                                                gameBean1.setArticleType(jsond.optString("articleType", ""));
                                                gameBean1.setTypeCode(jsond.optString("typeCode", ""));
                                                gameBean1.setGameCode(jsond.optString("gameCode", ""));
                                                gameBean1.setCateCode(jsond.optString("cateCode", ""));
                                                LogTools.e("dianji", jsond.optString("typeCode", ""));
                                                gameBeanList.add(gameBean1);

                                            }
                                            gameList.add(gameBeanList);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                moduleview();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textdd:
                startActivity(new Intent(MainActivity2.this, NoticeActivity.class));
                break;
            case R.id.register:
                startActivity(new Intent(MainActivity2.this, RegActivity.class));
                break;
            case R.id.login:
                startActivity(new Intent(MainActivity2.this, LoginActivity.class));
                break;
        }
    }

    public class ImageHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 3000;

        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<MainActivity2> weakReference;
        private int currentItem = 0;

        protected ImageHandler(WeakReference<MainActivity2> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogTools.d("handleMessage", "receive message " + msg.what);
            MainActivity2 activity = weakReference.get();
            if (MainActivity2.this == null) {
                //Activity已经回收，无需再处理UI了
                return;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (activity.handler.hasMessages(MSG_UPDATE_IMAGE)) {
                activity.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    if (currentItem == activity.viewpager.getAdapter().getCount() - 1) {
                        currentItem = 0;
                    } else
                        currentItem++;
                    activity.viewpager.setCurrentItem(currentItem);
                    //准备下次播放
                    activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    activity.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }
}
