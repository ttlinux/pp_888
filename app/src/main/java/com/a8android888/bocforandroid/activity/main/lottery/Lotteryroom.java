package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Lottery_room_Adapter;
import com.a8android888.bocforandroid.Adapter.MainPagerAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.BannerBean;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.JumpActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.a8android888.bocforandroid.view.Getlistheight;
import com.a8android888.bocforandroid.view.MarqueeTextView;
import com.a8android888.bocforandroid.view.MyViewPager;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/4/5.
 */
public class Lotteryroom extends BaseFragment {
    MyViewPager viewpager;
    GridView gridView;
    ArrayList<GameBean> gameBeanList = new ArrayList<GameBean>();
    ArrayList<String> gameStringList = new ArrayList<String>();
    ArrayList<String> gameflatList = new ArrayList<String>();
    ArrayList<String> gamecodeList = new ArrayList<String>();
    private ImageHandler handler = new ImageHandler(new WeakReference<Lotteryroom>(this));
    Lottery_room_Adapter adapter;
    private MarqueeTextView text;
    private ScrollView scrollview;
    int index = 0;
    String notice[] = null;
    RelativeLayout relayout, testlayout;
    ArrayList<BannerBean> BannerBeanList;

    LinearLayout linearLayout;
    ArrayList<ImageView> imageviews = new ArrayList<ImageView>();
    int recordindex = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.lotteryroom, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
    }

    private void initview() {


        relayout = FindView(R.id.relayout);
        testlayout = FindView(R.id.testlayout);
        gridView = (GridView) FindView(R.id.gridview);
        text = FindView(R.id.text);
        text.setOnMarqueeCompleteListener(new MarqueeTextView.OnMarqueeCompleteListener() {
            @Override
            public void onMarqueeComplete(MarqueeTextView view) {
                LogTools.e("onMarqueeComplete", "onMarqueeComplete");
                if (notice != null) {
                    if (index == notice.length - 1)
                        index = 0;
                    else
                        index++;
                    view.setText(Html.fromHtml(notice[index]));

                }
                view.startFor0();
            }
        });
        viewpager = FindView(R.id.viewpager);
        scrollview = (ScrollView) FindView(R.id.scrollview);
        viewpager.setOffscreenPageLimit(2);

        getdata();
        getwinningList();
        getbannerdata();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(ImageHandler.MSG_UPDATE_IMAGE);
        if (text != null)
            text.startStopMarquee(false);
        LogTools.e("huilai", "回来");
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if (state) {
            handler.removeMessages(ImageHandler.MSG_UPDATE_IMAGE);
        }
        if (!state && viewpager != null && viewpager.getAdapter() != null && viewpager.getAdapter().getCount() > 1) {
            handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AddButton(RelativeLayout relayout, int size) {
        imageviews.clear();
        linearLayout = new LinearLayout(getActivity());
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        params.bottomMargin=ScreenUtils.getDIP2PX(getActivity(),15);
//        params.setMargins(0,0,0,ScreenUtils.getDIP2PX(getActivity(),15));
//        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(0, 0, 0, ScreenUtils.getDIP2PX(getActivity(), 10));
        linearLayout.setLayoutParams(params);

        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 10;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.yindaocheck));
            imageviews.add(imageView);
            linearLayout.addView(imageView);
        }

        relayout.addView(linearLayout);
    }

    private void getdata() {

        Httputils.PostWithBaseUrl(Httputils.gameroomlist, null, new MyJsonHttpResponseHandler(this.getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("ddddddaaaaa", jsonObject.toString());
                JSONArray json = jsonObject.optJSONArray("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
//                    for (int i = 0; i < json.length(); i++) {
//                        JSONObject jsond = json.getJSONObject(i);
//                        LogTools.e("json", jsond + "");
//                        LotteryBean gameBean1 = new LotteryBean();
//                        gameBean1.setGameCode(jsond.optString("gameCode"));
//                        gameBean1.setDefaultItemId(jsond.optString("defaultItemId"));
//                        gameBean1.setGameId(jsond.optString("gameId"));
//                        gameBean1.setGameName(jsond.optString("gameName"));
//                        gameBean1.setBigBackgroundPic(jsond.optString("bigBackgroundPic", ""));
//                        gameBeanList.add(gameBean1);
//                        gameStringList.add(jsond.optString("gameName"));
//                        gameflatList.add(jsond.optString("gameId"));
//                        gamecodeList.add(jsond.optString("gameCode"));
//                    }
                    gameBeanList = new ArrayList<GameBean>();
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject jsonda = json.getJSONObject(i);
                        GameBean gameBean = new GameBean();
                        gameBean.setName(jsonda.optString("menuName", ""));
                        gameBean.setImg(jsonda.optString("bigPic", ""));
                        gameBean.setFlat(jsonda.optString("menuCode", ""));
                        gameBean.setBigBackgroundPic(jsonda.optString("bigBackgroundPic", ""));
                        if (jsonda.optString("menuCode", "").equalsIgnoreCase("lottery")) {
                            JSONArray jsonArray5 = jsonda.getJSONArray("flatMenuList");
                            for (int i1 = 0; i1 < jsonArray5.length(); i1++) {
                                JSONObject jsond = jsonArray5.getJSONObject(i1);
                                LogTools.e("qqqqqq", jsond.toString());
                                GameBean gameBean1 = new GameBean();
                                String game = jsond.optString("flatName", "");
                                gameBean1.setName(game);
                                gameBean1.setImg(jsond.optString("smallPic", ""));
                                gameBean1.setBigBackgroundPic(jsond.optString("bigBackgroundPic", ""));
                                gameBean1.setGamecode(jsond.optString("gameCode", ""));
                                gameBean1.setFlat(jsond.optString("flatCode", ""));
                                gameBeanList.add(gameBean1);
                            }
                        }
                    }
//                    if(!gameBeanList.isEmpty()&&gameBeanList.size()%4!=0){
////                        for(int ii=gameBeanList.size();ii<gameBeanList.size())
////                        LotteryBean gameBean1 = new LotteryBean();
////                        gameBean1.setGameCode("");
////                        gameBean1.setDefaultItemId("");
////                        gameBean1.setGameId("");
////                        gameBean1.setGameName("");
////                        gameBeanList.add(gameBean1);
////                        gameStringList.add("");
////                        gameflatList.add("");
////                        gamecodeList.add("");
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new Lottery_room_Adapter(getActivity(), gameBeanList);
                gridView.setAdapter(adapter);
                Getlistheight.setListViewHeightBasedOnChildren(gridView);

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }

    private void getbannerdata() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("code", "lottbanner");
        Httputils.PostWithBaseUrl(Httputils.bannerlist, requestParams, new MyJsonHttpResponseHandler(this.getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("moduless2", jsonObject.toString());

                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;

                String strs[] = {};
                JSONArray jsonArray5 = jsonObject.optJSONArray("datas");
                if (jsonArray5 != null && jsonArray5.length() > 0) {
                    if (BannerBeanList == null) {
                        BannerBeanList = new ArrayList<BannerBean>();
                    }
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
                        BannerBeanList.add(gameBean1);
                    }
                    AddButton(testlayout, jsonArray5.length());
                    LogTools.e("jsonArray5", jsonArray5.length() + "");
                    if (strs.length > 0) {
                        imageviews.get(0).setImageDrawable(getResources().getDrawable(R.drawable.yindaochedked));
                        recordindex = 0;
                    }
                    MainPagerAdapter adapter = new MainPagerAdapter(getActivity(), strs);
                    adapter.setOnClickItemListener(new MainPagerAdapter.OnClickItemListener() {
                        @Override
                        public void Onclick(int index) {
                            modeluonclick2(index);
                        }
                    });
                    viewpager.setAdapter(adapter);
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
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }

    private void getwinningList() {
        Httputils.PostWithBaseUrl(Httputils.winningList, null, new MyJsonHttpResponseHandler(this.getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                JSONArray json = jsonObject.optJSONArray("datas");
                LogTools.e("ddddaaaa", jsonObject.toString());
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
//                    String winniglist="";
//                    for (int i = 0; i < json.length(); i++) {
//                        JSONObject jsond = json.getJSONObject(i);
//                        LogTools.e("json", jsond + "");
//                        winniglist=winniglist+jsond.optString("remark");
//                    }
                JSONArray gglist = jsonObject.optJSONArray("datas");
                if (gglist != null && gglist.length() > 0) {

                    notice = new String[gglist.length()];
                    for (int i = 0; i < gglist.length(); i++) {
                        JSONObject temp = gglist.optJSONObject(i);
                        notice[i] = temp.optString("remark", "");
                    }
                    text.startStopMarquee(false);
                    text.setText(Html.fromHtml(notice[0]));
                    text.startFor0();
                }
//            text.setText(winniglist);imageUrl
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
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
        private WeakReference<Lotteryroom> weakReference;
        private int currentItem = 0;

        protected ImageHandler(WeakReference<Lotteryroom> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogTools.d("handleMessage", "receive message " + msg.what);
            Lotteryroom activity = weakReference.get();
            if (Lotteryroom.this.getActivity() == null) {
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

    public void modeluonclick2(int tag) {
        JumpActivity.modeluonclick(getActivity(), BannerBeanList.get(tag).getLinkType(),
                BannerBeanList.get(tag).getOpenLinkType(), BannerBeanList.get(tag).getTypeCode(),
                BannerBeanList.get(tag).getLevel(), BannerBeanList.get(tag).getCateCode(),
                BannerBeanList.get(tag).getGameCode(), BannerBeanList.get(tag).getBannerName(),
                BannerBeanList.get(tag).getBannerName(), BannerBeanList.get(tag).getLinkUrl()
                , BannerBeanList.get(tag).getArticleType(), BannerBeanList.get(tag).getArticleId(), "nopull", "1", BannerBeanList.get(tag).getLinkGroupId());
    }
}
