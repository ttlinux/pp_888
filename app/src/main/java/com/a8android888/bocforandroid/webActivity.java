package com.a8android888.bocforandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.MainPagerAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.activity.WelcomeActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.view.MyViewPager;
import com.loopj.android.http.RequestParams;
import com.tencent.smtt.export.external.interfaces.WebResourceError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 *
 * @author limingfang
 * @category
 *
 */
public class webActivity extends BaseActivity implements OnClickListener {
    MyViewPager viewpager;
    private WebView mWebView;
    private TextView title;
    private ImageView back;
    Intent intent;
    RelativeLayout relayout;
    private ProgressBar progress;
    private ImageHandler handler = new ImageHandler(new WeakReference<webActivity>(webActivity.this));

    LinearLayout linearLayout;
    ArrayList<ImageView> imageviews=new ArrayList<ImageView>();
    int recordindex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuweb);
        if (getPhoneAndroidSDK() >= 14) {
            getWindow().setFlags(0x1000000, 0x1000000);
        }
        findView();
    }

    private void findView()  {
        relayout=FindView(R.id.relayout);
        title = (TextView) findViewById(R.id.title);
        intent = getIntent();
        title.setText(intent.getStringExtra(BundleTag.title));
        back = (ImageView) findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        mWebView = (WebView) findViewById(R.id.web);
        mWebView.getSettings().setBlockNetworkImage(false);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(100);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDomStorageEnabled(true);
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        String appCacheDir = this.getApplicationContext()
                .getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 10);
        webSettings.setAllowFileAccess(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDatabaseEnabled(false);
        String databaseDir = this.getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(databaseDir);

        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(databaseDir);
//	        webSettings.setPluginsEnabled(true);
        webSettings.setRenderPriority(RenderPriority.HIGH);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int mDensity = metrics.densityDpi;
//        if (mDensity == 120) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        }else if (mDensity == 160) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        }else if (mDensity == 240) {
//            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        }

        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        mWebView.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        mWebView.getSettings().setSupportZoom(true);//是否可以缩放，默认true
        mWebView.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mWebView.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
//        mWebView.getSettings().setAppCacheEnabled(true);//是否使用缓存
//        mWebView.getSettings().setDomStorageEnabled(true);//DOM Storage
        mWebView.setWebChromeClient(mChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
        if(intent.getBooleanExtra(BundleTag.WebData, false))//不用请求直接跳转
        {
            StringBuilder sb = new StringBuilder();
            String css=""  ;
            if(intent.getBooleanExtra(BundleTag.isimg,false)){
            css = "<style type=\"text/css\"> img {" +
                    "width:100%;" +
                    "height:auto;" +
                    "}" +
                    "body {" +
                    "margin-right:0px;" +
                    "margin-left:0px;" +
                    "margin-top:0px;" +
                    "font-size:25px;" +
                    "}" +
                    "</style>";
            sb.append("<html>"+css+"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />");

            sb.append("<img src="+intent.getStringExtra(BundleTag.URL) +">");
           }
            else{
                css = "<style type=\"text/css\"> img {" +
                        "width:100%;" +
                        "height:auto;" +
                        "}" +
                        "body {" +
                        "margin-right:5px;" +
                        "margin-left:5px;" +
                        "margin-top:5px;" +
                        "font-size:18px;" +
                        "}" +
                        "</style>";
                sb.append("<html>"+css+"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />");

                sb.append(intent.getStringExtra(BundleTag.URL));
           }
            sb.append("</html>");
            LogTools.e("sb.toString()",sb.toString());
            mWebView.loadDataWithBaseURL("",sb.toString(),"text/html","UTF-8","about:blank");
        }
        else  if(intent.getStringExtra(BundleTag.URL).equalsIgnoreCase("m_promos")){
            SharedPreferences sharedPreferences=((BaseApplication)this.getApplication()).getSharedPreferences();
            String promsList=sharedPreferences.getString(BundleTag.promsList, "");
            JSONArray jsonArray=null;
            try {
                jsonArray=new JSONArray(promsList.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String strs[] ={};
            if(jsonArray!=null) {
                strs = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    strs[i] = jsonArray.optJSONObject(i).optString("url", "");
                }
            }

            viewpager=FindView(R.id.viewpager);
            viewpager.setOffscreenPageLimit(2);
            viewpager.setAdapter(new MainPagerAdapter(this, strs));
            viewpager.setOnMeasureListener(new MyViewPager.onMeasureListener() {
                @Override
                public void onmeaure(int height) {
                    relayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
                }
            });
            AddButton(relayout, strs.length);
            imageviews.get(0).setImageDrawable(getResources().getDrawable(R.drawable.yindaochedked));
            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                //配合Adapter的currentItem字段进行设置。
                @Override
                public void onPageSelected(int arg0) {
                    imageviews.get(recordindex).setImageDrawable(getResources().getDrawable(R.drawable.yindaocheck));
                    recordindex=arg0;
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
        else
        {
            if(intent.getBooleanExtra(BundleTag.IntentTag,false))//不用请求直接跳转
            {

                String url=intent.getStringExtra(BundleTag.URL);
                if(url.indexOf("http")!=-1)
                    {
                        if(url.indexOf(".jpg")!=-1||url.indexOf(".png")!=-1){
                            StringBuilder sb = new StringBuilder();
                            String css = "<style type=\"text/css\"> img {" +
                                    "width:100%;" +
                                    "height:auto;" +
                                    "}" +
                                    "body {" +
                    "margin-right:0px;" +
                    "margin-left:0px;" +
                    "margin-top:0px;" +
                                    "font-size:25px;" +
                                    "}" +
                                    "</style>";
                            sb.append("<html>"+css+"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />");
                            if(intent.getBooleanExtra(BundleTag.isimg,false)){

                                sb.append("<img src="+intent.getStringExtra(BundleTag.URL) +">");
                            }
                            else{
                                sb.append(intent.getStringExtra(BundleTag.URL));
                            }
                            sb.append("</html>");
                            LogTools.e("sb.toString()",sb.toString());
                            mWebView.loadDataWithBaseURL("",sb.toString(),"text/html","UTF-8","about:blank");
                        }
                        else
                        {
                          if(intent.getBooleanExtra(BundleTag.saoma,false)){
                              webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
                              webSettings.setUseWideViewPort(false);
                            mWebView.loadUrl(intent.getStringExtra(BundleTag.URL));}
                            else{
                              LogTools.e("網站a", intent.getStringExtra(BundleTag.URL));
                              if(intent.getBooleanExtra(BundleTag.Method,true))
                              {
                                  mWebView.postUrl(intent.getStringExtra(BundleTag.URL),null);
                              }
                              else
                              mWebView.loadUrl(intent.getStringExtra(BundleTag.URL));
                          }
                        }

                }
               else{
                        mWebView.loadUrl("http://"+intent.getStringExtra(BundleTag.URL));
                };
            }
            else {
                GetData(intent.getStringExtra(BundleTag.URL));

            }

        }
        LogTools.e("網站",intent.getStringExtra(BundleTag.URL));
    }


    private void GetData(String url)
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("pageCode",url);
        Httputils.PostWithBaseUrl(Httputils.templatelist, requestParams, new MyJsonHttpResponseHandler(this,true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                String datas = jsonObject.optString("datas", "");
                StringBuilder sb = new StringBuilder();
                sb.append("<html><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />");
                sb.append(datas);
                sb.append("</html>");
                mWebView.loadDataWithBaseURL(null, sb.toString(), "text/html", "utf-8", null);
            }
        });
    }
    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            mWebView.loadUrl(url);

            LogTools.e("網站aaa", url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogTools.e("網站aaa222", url);
            boolean iscons=!url.contains("http://") && !url.contains("https://");
            if(intent.getBooleanExtra(BundleTag.Method,true) && iscons)
            {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            }
        }

    };


    private WebChromeClient mChromeClient = new WebChromeClient() {

        private View myView = null;
        private CustomViewCallback myCallback = null;

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }

        @Override
        public void onExceededDatabaseQuota(String url,
                                            String databaseIdentifier, long currentQuota,
                                            long estimatedSize, long totalUsedQuota,
                                            WebStorage.QuotaUpdater quotaUpdater) {

            quotaUpdater.updateQuota(estimatedSize * 2);
        }

        @Override
        public void onReachedMaxAppCacheSize(long spaceNeeded,
                                             long totalUsedQuota, WebStorage.QuotaUpdater quotaUpdater) {

            quotaUpdater.updateQuota(spaceNeeded * 2);
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null;
                return;
            }

            ViewGroup parent = (ViewGroup) mWebView.getParent();
            parent.removeView(mWebView);
            parent.addView(view);
            myView = view;
            myCallback = callback;
            mChromeClient = this;
        }

        @Override
        public void onHideCustomView() {
            if (myView != null) {
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                }

                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
                parent.addView(mWebView);
                myView = null;
            }
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO 自动生成的方法存根

            if(newProgress==100){
                progress.setVisibility(View.GONE);//加载完网页进度条消失
            }
            else{
                progress.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                progress.setProgress(newProgress);//设置进度值
            }

        }
    };


    @SuppressWarnings({ "deprecation", "static-access" })
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.back:
//                    Intent intent = new Intent(this,
//                            StartFragmentActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                if (MainActivity.isexit != 2) {
                    Intent intent2 = new Intent(this, WelcomeActivity.class);
                    intent2.setAction(Intent.ACTION_MAIN);
                    intent2.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    finish();
                }
                else {
                    finish();
                }
                break;
            default:
                break;
        }
    }
    public static int getPhoneAndroidSDK() {
        // TODO Auto-generated method stub
        int version = 0;
        try {
            version = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;

    }

    private void AddButton(RelativeLayout relayout, int size)
    {
        linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//        params.bottomMargin=ScreenUtils.getDIP2PX(getActivity(),15);
//        params.setMargins(0,0,0,ScreenUtils.getDIP2PX(getActivity(),15));
        linearLayout.setPadding(0,0,0, ScreenUtils.getDIP2PX(this, 10));
        linearLayout.setLayoutParams(params);

        for (int i = 0; i <size; i++) {
            ImageView imageView=new ImageView(this);
            LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin=10;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.yindaocheck));
            imageviews.add(imageView);

            linearLayout.addView(imageView);
        }

        relayout.addView(linearLayout);
    }
    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
        {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    };
    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }
    public  class ImageHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE  = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT   = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT  = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED  = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 3000;

        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<webActivity> weakReference;
        private int currentItem = 0;

        protected ImageHandler(WeakReference<webActivity> wk){
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogTools.d("handleMessage", "receive message " + msg.what);
            webActivity activity = weakReference.get();
            if (webActivity.this==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (activity.handler.hasMessages(MSG_UPDATE_IMAGE) ){
                activity.handler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    if(currentItem==activity.viewpager.getAdapter().getCount()-1)
                    {
                        currentItem=0;
                    }
                    else
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
