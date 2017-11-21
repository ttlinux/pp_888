package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/5. 玩法
 */
public class LotteryFragment4 extends BaseFragment{

    private WebView mWebView;
    RelativeLayout toplayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return View.inflate(getActivity(),R.layout.menuweb,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView();
    }
    private void findView()  {
        toplayout=(RelativeLayout)FindView(R.id.toplayout);
        toplayout.setVisibility(View.GONE);
        mWebView = (WebView) FindView(R.id.web);
        mWebView.getSettings().setBlockNetworkImage(false);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        String appCacheDir = LotteryFragment4.this.getActivity().getApplicationContext()
                .getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 10);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDatabaseEnabled(false);
        String databaseDir =LotteryFragment4.this.getActivity().getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(databaseDir);

        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(databaseDir);

//	        webSettings.setPluginsEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebChromeClient(mChromeClient);
        mWebView.setWebViewClient(mWebViewClient);


//        mWebView.loadUrl(url);
        GetData();
    }


    private void GetData()
    {
        RequestParams requestParams=new RequestParams();
        requestParams.put("ruleCode",LotteryFragment1.getGamecode());
        requestParams.put("ruleType","cp_rule_type");
        //"http://192.168.0.239:8391/"+
        Httputils.PostWithBaseUrl(Httputils.Rule, requestParams, new MyJsonHttpResponseHandler(getActivity(), true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject222", jsonObject.toString());
                if(jsonObject.optJSONObject("datas")==null)return;
                String ruleContent = jsonObject.optJSONObject("datas").optString("ruleContent", "");
                StringBuilder sb = new StringBuilder();
                sb.append("<html><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes\" />");
                sb.append(ruleContent);
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

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

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
    };


    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

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
    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
