package com.a8android888.bocforandroid.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.LogTools;

/**
 * Created by Administrator on 2017/10/31.
 */
public class MyWebViewforPay extends WebView{

    Context context;
    public MyWebViewforPay(Context context) {
        super(context);
    }

    public MyWebViewforPay(Context context,String url) {
        super(context);
        init(context,url);
    }

    public MyWebViewforPay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyWebViewforPay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Context context,String url)
    {
        this.context=context;
        WebSettings webSettings = getSettings();
        webSettings.setDomStorageEnabled(true);
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        String appCacheDir = context.getApplicationContext()
                .getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 10);
        webSettings.setAllowFileAccess(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDatabaseEnabled(false);
        String databaseDir = context.getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(databaseDir);

        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(databaseDir);
//	        webSettings.setPluginsEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
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

        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
        getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        getSettings().setSupportZoom(true);//是否可以缩放，默认true
        getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
//        getSettings().setAppCacheEnabled(true);//是否使用缓存
//        getSettings().setDomStorageEnabled(true);//DOM Storage
        setWebChromeClient(mChromeClient);
        setWebViewClient(mWebViewClient);

        postUrl(url, null);
    }

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
        }

        @Override
        public void onHideCustomView() {
        }
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO 自动生成的方法存根
        }
    };

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);

            LogTools.e("網站aaa", url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogTools.e("網站aaa222", url);

            boolean iscons=!url.contains("http://") && !url.contains("https://");
            if( iscons)
            {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                context.startActivity(intent);
            }
        }


    };
}
