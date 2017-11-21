package com.a8android888.bocforandroid.activity.main.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Game_Adapter;
import com.a8android888.bocforandroid.Adapter.Real_Video_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragmentActivity;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.PlatformBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/3.
 */
public class GameFragment extends BaseFragmentActivity implements View.OnClickListener {
    private ImageView back, mImageView,searchimg;
    private CheckBox spinner;
    private TextView allgame, hostgame, newsgame, mycollection;
    PullToRefreshGridView gridView;
    LinearLayout line;
    private RadioGroup myRadioGroup;
    EditText search;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    Intent intent;
    RadioButton recordbutton;
    WindowManager wm;
    private LinearLayout layout;
    private PopupWindow popupWindow = null;
    private int _id = 1000;
    private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
    private List<Map<String, Object>> titleList = new ArrayList<Map<String, Object>>();
    List<String> list = new ArrayList<String>();
    String gametype = "all";
    Game_Adapter adapter;
    RelativeLayout toplayout;
    String flat;
  ArrayList<GameBean> gameBeanList=new ArrayList<GameBean>();
    int page = 1;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game);
        wm = (WindowManager) this
                .getSystemService(this.WINDOW_SERVICE);
        getTitleInfo();

        initview();
    }

    private void getTitleInfo() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 0);
        map.put("title", this.getResources().getText(R.string.allgame));
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", 1);
        map.put("title", this.getResources().getText(R.string.hotgame));
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", 2);
        map.put("title", this.getResources().getText(R.string.newgame));
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", 3);
        map.put("title", this.getResources().getText(R.string.mycollection));
        titleList.add(map);
    }

    private void initview() {

        intent = getIntent();
        flat=intent.getStringExtra("flat");
        list=  intent.getStringArrayListExtra("flatlist");
        back = (ImageView) FindView(R.id.back);
        back.setOnClickListener(this);
        searchimg = (ImageView) FindView(R.id.searchimg);
        searchimg.setOnClickListener(this);
        spinner = (CheckBox) FindView(R.id.spinner);
        spinner.setText(intent.getStringExtra("title"));
        spinner.setOnClickListener(GameFragment.this);
        search = (EditText) FindView(R.id.search);
        gridView = (PullToRefreshGridView) FindView(R.id.gridview);
        gridView.setMode(PullToRefreshBase.Mode.BOTH);
        gridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<GridView> refreshView) {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.onRefreshComplete();
                    }
                }, 100);
                page = 1;
                getdatalist(flat);
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<GridView> refreshView) {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.onRefreshComplete();
                    }
                }, 100);
                page++;
                getdatalist(flat);
                LogTools.e("dddaa5465", "123456");
            }
        });
        toplayout=(RelativeLayout)FindView(R.id.toplayout);
//        getdatalist(flat);
        search.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    page = 1;
                    gametype = "search";
                    ((RadioButton)myRadioGroup.getChildAt(0)).setChecked(true);
                    getdatalist(flat);

                    return true;
                }
                return false;
            }

        });
        search.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ( s.toString().length()<1) {
                    gametype = "all";
                    page = 1;
//                    spinner.setText("");
                    getdatalist(flat);
LogTools.e("搜索的时候",temp.length()+"sss"+s.toString().length()+"");
                }
            }
        });

        layout = (LinearLayout) FindView(R.id.lay);
        mImageView = (ImageView) FindView(R.id.img1);
        mHorizontalScrollView = (HorizontalScrollView) FindView(R.id.horizontalScrollView);
        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(myRadioGroup);
        for (int i = 0; i < 4; i++) {
            Map<String, Object> map = titleList.get(i);
            RadioButton radio = new RadioButton(this);
            radio.setButtonDrawable(android.R.color.transparent);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth() / 4, LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setGravity(Gravity.CENTER);
            radio.setPadding(0, 10, 0, 10);
            radio.setTag(i);
            radio.setId(_id + i);
            radio.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            radio.setText(map.get("title") + "");
            if (i == 0) {
                radio.setChecked(true);
                radio.setTextColor(getResources().getColor(R.color.usertopbagcolor));
                recordbutton = radio;
            } else {
                radio.setTextColor(getResources().getColor(R.color.white));
            }

            myRadioGroup.addView(radio);

        }
        int magin = 0;
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth() / 4, 10);
        ll.leftMargin = magin;
        ll.rightMargin = magin;
        mImageView.setLayoutParams(ll);
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) FindView(radioButtonId);
                recordbutton.setTextColor(getResources().getColor(R.color.white));
                recordbutton = rb;
                recordbutton.setTextColor(getResources().getColor(R.color.usertopbagcolor));
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation;
                translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setDuration(100);
                mImageView.startAnimation(animationSet);//开始上面白色横条图片的动画切换
                int magin = wm.getDefaultDisplay().getWidth() / 4;
                mCurrentCheckedRadioLeft = magin * (radioButtonId - _id);//更新当前蓝色横条距离左边的距离
                mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft, 0);
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth() / 4, 10);
                ll.leftMargin = 0;
                ll.rightMargin = 0;
                mImageView.setLayoutParams(ll);
                if (checkedId == _id + 0) {
                    if(!gametype.equalsIgnoreCase("search")) {
                        gametype = "all";
                        page = 1;
//                    spinner.setText("");
                        getdatalist(flat);
                    }
                }
                if (checkedId == _id + 1) {
                    page =1;
                    gametype = "hot";
//                    spinner.setText("");
                    getdatalist(flat);
                }
                if (checkedId == _id + 2) {
                    page =1;
                    gametype = "new";
//                    spinner.setText("");
                    getdatalist(flat);
                }
                if (checkedId == _id + 3) {
                    page =1;
//                    spinner.setText("");
                        gametype="favourite";
                    getdatalist(flat);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getdatalist(flat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.searchimg:
                if(search.getText().toString().length()<1){
                    ToastUtil.showMessage(this,"请输入关键字搜索");
                    return;
                }
                page = 1;
                gametype = "search";
                ((RadioButton)myRadioGroup.getChildAt(0)).setChecked(true);
                getdatalist(flat);
                break;
            case R.id.spinner:
                if (popupWindow == null) {
                    showMore(spinner);
                } else {
                    popupWindow.dismiss();
                    popupWindow = null;
                }

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
        if (popupWindow == null) {
            View popupView = View.inflate(this, R.layout.popupwindowlayout, null);
            popupView.setFocusableInTouchMode(true);
            popupView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        popupWindow.dismiss();
                        popupWindow = null;
                        spinner.setChecked(false);
                        return true;
                    }
                    return false;
                }
            });
            if (popupWindow == null)
                popupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT, true);
            linearLayout = (LinearLayout) popupView.findViewById(R.id.layout);
            linearLayout.setGravity(Gravity.CENTER);
            RelativeLayout relation=(RelativeLayout)popupView.findViewById(R.id.relation);
            relation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinner.setChecked(false);
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            });
            int listsize = intent.getStringArrayListExtra("list").size();
            for ( int i = 0; i < listsize; i++) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams params12 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(this, 150), 1, Gravity.CENTER);
                params12.gravity = Gravity.CENTER;
                imageView.setLayoutParams(params12);
                imageView.setBackgroundColor(this.getResources().getColor(R.color.white));
                linearLayout.addView(imageView);
                final TextView textView = new TextView(this);
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ScreenUtils.getDIP2PX(this, 150), LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                params1.gravity = Gravity.CENTER;
                textView.setLayoutParams(params1);
                textView.setTag(i);
                textView.setBackgroundColor(this.getResources().getColor(R.color.topbagcolor));
                textView.setText(intent.getStringArrayListExtra("list").get(i).toString());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(15);
                textView.setPadding(ScreenUtils.getDIP2PX(this, 40), ScreenUtils.getDIP2PX(this, 10), ScreenUtils.getDIP2PX(this, 40), ScreenUtils.getDIP2PX(this, 10));
                textView.setTextColor(this.getResources().getColor(R.color.white));
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final   int tag = Integer.valueOf(v.getTag() + "");
                        spinner.setText(intent.getStringArrayListExtra("list").get(tag).toString());
                        spinner.setChecked(false);
                        popupWindow.dismiss();
                        popupWindow = null;
                        flat=list.get(tag).toString();
                        LogTools.e("flat是多少",flat);
//                        spinner.setText("");
                        page =1;
                        search.setText("");
                        getdatalist(flat);
                    }
                });
                linearLayout.addView(textView);


            }

            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.showAsDropDown(parent);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    popupWindow.dismiss();
                        popupWindow = null;
                        spinner.setChecked(false);
                }
            });
//                popupWindow.showAtLocation(parent,Gravity.CENTER,ScreenUtils.getDIP2PX(this, wm.getDefaultDisplay().getWidth()/2),ScreenUtils.getDIP2PX(this, toplayout.getHeight()));
//               popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE && !popupWindow.isFocusable()) {
//
//                            return true;
//                    }
//                    else {
//                        popupWindow.dismiss();
//                        popupWindow = null;
//                        spinner.setChecked(false);
//                    }
//                    return false;
//                }
//
//            });

        }
    }

    //遊戲列表
    private void getdatalist(String flatd) {
        RequestParams params = new RequestParams();
        params.put("flat",flatd );
        if(gametype.equalsIgnoreCase("search"))
        {
                    params.put("gameName",search.getText().toString().trim());
        }
            params.put("userName",((BaseApplication) this.getApplicationContext()).getBaseapplicationUsername());

        params.put("client", Httputils.client_server);
        params.put("code", gametype);
        params.put("pageNo", page + "");
        params.put("pageSize", "6");
        Httputils.PostWithBaseUrl(Httputils.platformlist, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {

                super.onSuccessOfMe(jsonObject);
                LogTools.e("aaa", jsonObject.toString());

                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                JSONObject json = jsonObject.optJSONObject("datas");
//                if(!gameBeanList.isEmpty()){
//                    gameBeanList.clear();
//                }

                if (page == 1) {
                    gameBeanList.clear();
                }
                try {
                    JSONArray jsonArray = json.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsond = jsonArray.getJSONObject(i);
                        GameBean gameBean1 = new GameBean();
                        String game = jsond.optString("cnName");
                        gameBean1.setEcname(jsond.optString("enName"));
                        gameBean1.setFlat(flat);
                        gameBean1.setGamecode(jsond.optString("gameCode", ""));
                        gameBean1.setHavourite(jsond.optString("havourite"));
                        gameBean1.setImg(jsond.optString("img"));
                        gameBean1.setName(game);
                        gameBeanList.add(gameBean1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(adapter==null)
                {
                    adapter = new Game_Adapter(GameFragment.this, gameBeanList);
                    gridView.setAdapter(adapter);
                }
                else
                {
                    adapter.NotifyData(gameBeanList);
                }

                if(gametype.equalsIgnoreCase("search")) {
                    gametype = "all";
                }
            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
                return super.parseResponse(s);
            }
        });
    }


}
