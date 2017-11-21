package com.a8android888.bocforandroid.BaseParent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.a8android888.bocforandroid.Bean.BallTypeBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Crasherr;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.NetBroadcastReceiver;
import com.a8android888.bocforandroid.util.NetUtil;
import com.a8android888.bocforandroid.util.Permission;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.util.ZipUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/3/28.
 */
public class BaseApplication extends Application implements NetBroadcastReceiver.NetEvevt {

    public ImageLoader imageloader = null;
    public DisplayImageOptions options = null;
    public ImageLoaderConfiguration config = null;
    public static String username;//用户名
    public static boolean NoticehasClick = false;
    public static NetBroadcastReceiver.NetEvevt evevt;
    public static String packagename;
    public JSONObject jsonObjectanimal;//生肖
    public Activity activity;
    public boolean isAppBackstage;

    public static String getBaseUrl() {
        return BaseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        BaseUrl = baseUrl;
    }

    public static  String BaseUrl="http://1b.ybapi.net/";//http://103.240.143.131:8083/ http://1b.ybapi.net/

    public static  String androidkey="203de7bac9f5fd836e131f30cba6b924afd5c60835f5a43739d472b739f9b9f0";//体育下单详情detail

    public static String getAndroidkey() {
        return androidkey;
    }

    public static void setAndroidkey(String androidkey) {
        BaseApplication.androidkey = androidkey;
    }

    public JSONObject getJsonObjectanimal() {
        return jsonObjectanimal;
    }

    public void setJsonObjectanimal(JSONObject jsonObjectanimal) {
        this.jsonObjectanimal = jsonObjectanimal;
    }
   public static Drawable qidong,logo;

    public static Drawable getLogo() {
        return logo;
    }

    public static void setLogo(Drawable logo) {
        BaseApplication.logo = logo;
    }

    public static Drawable getQidong() {
        return qidong;
    }

    public static void setQidong(Drawable qidong) {
        BaseApplication.qidong = qidong;
    }

    public String lastQs;//上一期期数
    public String lastResult;//上一期开奖结果
    public String currentQs;  //当前期期数
    public static long opentime;//开奖时间，单位秒
    public static long closetime;//封盘时间，单位秒
    public static long nowtime;//当前时间，单位秒
    public int status;//状态值，0表示正常，-1表示维护
    public String maxlimit;// 单注最大限额
    public String maxPeriodLimit;//  	单期最大限额
    public String minLimit;// 	单注最小限额
    SharedPreferences NOT_CountinueMode,CountinueMode,ssopenstate;

    public boolean getIsAppBackstage() {
        return isAppBackstage;
    }

    public Activity getActivity() {
        return activity;
    }

    public boolean isNoticehasClick() {
        return NoticehasClick;
    }

    public void setNoticehasClick(boolean noticehasClick) {
        NoticehasClick = noticehasClick;
    }

    public String getLastQs() {
        return lastQs;
    }

    public void setLastQs(String lastQs) {
        this.lastQs = lastQs;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public String getCurrentQs() {
        return currentQs;
    }

    public void setCurrentQs(String currentQs) {
        this.currentQs = currentQs;
    }

    public long getOpentime() {
        return opentime;
    }

    public void setOpentime(long opentime) {
        this.opentime = opentime;
    }

    public long getClosetime() {
        return closetime;
    }

    public void setClosetime(long closetime) {
        this.closetime = closetime;
    }

    public long getNowtime() {
        return nowtime;
    }

    public void setNowtime(long nowtime) {
        this.nowtime = nowtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(String maxlimit) {
        this.maxlimit = maxlimit;
    }

    public String getMaxPeriodLimit() {
        return maxPeriodLimit;
    }

    public void setMaxPeriodLimit(String maxPeriodLimit) {
        this.maxPeriodLimit = maxPeriodLimit;
    }

    public String getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(String minLimit) {
        this.minLimit = minLimit;
    }

    SharedPreferences sharedPreferences;

    ActivityLifecycleCallbacks callbacks;

    private RefWatcher mRefWatcher;
    /**
     * 网络类型
     */
    public static int netMobile;
    public ArrayList<BallTypeBean> ballTypeBeans;

    public ArrayList<BallTypeBean> getBallTypeBeans() {
        return ballTypeBeans;
    }

    public void setBallTypeBeans(ArrayList<BallTypeBean> ballTypeBeans) {
        this.ballTypeBeans = ballTypeBeans;
    }

    public static boolean isMNC() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public String getUsername() {
        String UserInfo = getSharedPreferences().getString(BundleTag.UserInfo, "");
        if (UserInfo == null || UserInfo.toString().equalsIgnoreCase("")) return "";
        try {
            JSONObject jsonObject = new JSONObject(UserInfo);
            return jsonObject.optString("userName", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return username;
    }

    public String getBaseapplicationUsername() {
        return username;
    }

    public String getAccess_token() {
        String Access_token = getSharedPreferences().getString(BundleTag.Access_token, "");
        return Access_token;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Httputils.BaseUrl= ZipUtils.unzip(getString(R.string.baseurl));
//        PackageManager pm =getPackageManager();
        if(!LogTools.isShow)
        {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            mRefWatcher = LeakCanary.install(this);
        }
        packagename = getPackageName();
        LogTools.e("packagename", packagename);
        setconfig(this);
//        getLocalIPAddress();
//        GetNetIp();
        evevt = this;
        inspectNet();
        sharedPreferences = getSharedPreferences("BiYing", Activity.MODE_PRIVATE);
        NOT_CountinueMode=getSharedPreferences(BundleTag.NOT_CountinueMode, Activity.MODE_PRIVATE);
        ssopenstate=getSharedPreferences(BundleTag.isopenstate, Activity.MODE_PRIVATE);
        CountinueMode=getSharedPreferences(BundleTag.CountinueMode, Activity.MODE_PRIVATE);
        ClearTiYuCache();
        //全局异常捕获初始化
        if (LogTools.isShow) {
            Crasherr crashHandler = Crasherr.getInstance();
            crashHandler.init(getApplicationContext());
        }


        //易博token和网络请求
        if(BaseApplication.packagename.indexOf("a8android888")!=-1||
                BaseApplication.packagename.indexOf("a8android986")!=-1||
                BaseApplication.packagename.indexOf("a8android988")!=-1){
//            setBaseUrl("http://1b.ybapi.net/");
//            setAndroidkey("203de7bac9f5fd836e131f30cba6b924afd5c60835f5a43739d472b739f9b9f0");
            /***测试的***/
//            setBaseUrl("http://api.6820168.com/");
            setBaseUrl("http://192.168.0.123:8083/");

            setAndroidkey("6524ee4deb5e785798f39f73afedfb7bafd5c60835f5a43739d472b739f9b9f0");

            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.a8android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.a8android888applogo));
        }
        //博发token和网络请求
        if(BaseApplication.packagename.indexOf("d2android888")!=-1||
                BaseApplication.packagename.indexOf("d2android914")!=-1){
            setBaseUrl("http://bf.ybapi.net/");
            setAndroidkey("313073f436d589c91d599a4f0da96ae3afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.d2android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.d2android888applogo));
        }
        //美高梅token和网络请求
        if(BaseApplication.packagename.indexOf("a1android888")!=-1){
            setBaseUrl("http://mgm.ybapi.net/");
            setAndroidkey("346c8cf03633fa55df220c1be09ed103afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.a1android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.a1android888applogo));
        }
        //澳门永利token和网络请求
        if(BaseApplication.packagename.indexOf("a3android888")!=-1){
            setBaseUrl("http://936.ybapi.net/");
            setAndroidkey("b442dc6499d60adea3e34c8575772b8aafd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.a3android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.a3android888applogo));
        }
        //威尼斯人token和网络请求
        if(BaseApplication.packagename.indexOf("a4android888")!=-1){
            setBaseUrl("http://bodog.ybapi.net/");
            setAndroidkey("5118e04e4f372ad303a86a6a8ce99286afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.a4android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.a4android888applogo));
        }
        //金沙城token和网络请求
        if(BaseApplication.packagename.indexOf("a6android888")!=-1) {
            setBaseUrl("http://blh.ybapi.net/");
            setAndroidkey("272bbe1042e91739eb48c658b7db1704afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.a6android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.a6android888applogo));
        }
        //澳门银河token和网络请求
        if(BaseApplication.packagename.indexOf("c8android888")!=-1){
            setBaseUrl("http://xh966.ybapi.net/");
            setAndroidkey("c901aba91aa9e3d79ef35872834bffd6afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.c8android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.c8android888applogo));
        }

        //澳门星际token和网络请求
        if(BaseApplication.packagename.indexOf("c3android888")!=-1){
            setBaseUrl("http://xj.ybapi.net/");
            setAndroidkey("eedf678753eede91abe3655b01bdf5a7afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.c3android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.c3android888applogo));
        }
        //金沙城token和网络请求
        if(BaseApplication.packagename.indexOf("c4android888")!=-1){
            setBaseUrl("http://jsc.ybapi.net/");
            setAndroidkey("c54bfd3d00593fe20bfaa4fdab24a71bafd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.a6android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.a6android888applogo));
        }
        //宝博token和网络请求
        if(BaseApplication.packagename.indexOf("c5android888")!=-1){
            setBaseUrl("http://tb.ybapi.net/");
            setAndroidkey("dcf9f62a7d7557916f8b6e026ad6593eafd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.c5android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.c5android888applogo));
        }

        //太阳城token和网络请求
        if(BaseApplication.packagename.indexOf("c6android888")!=-1){
            setBaseUrl("http://tyc.ybapi.net/");
            setAndroidkey("ecceefcaa038b257dc7acc80db93da6fafd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.c6android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.c6android888applogo));
        }
        //圣淘沙token和网络请求
        if(BaseApplication.packagename.indexOf("c7android888")!=-1){
            setBaseUrl("http://sts.ybapi.net/");
            setAndroidkey("f93f931f3549ffbb85761d02c8f24546afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.c7android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.c7android888applogo));
        }
        //永利高token和网络请求
        if(BaseApplication.packagename.indexOf("c9android888")!=-1){
            setBaseUrl("http://ylg.ybapi.net/");
            setAndroidkey("3d663f8a9c6065e14586e5e5bea31bd2afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.c9android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.c9android888applogo));
        }
        //威尼斯人a7token和网络请求
        if(BaseApplication.packagename.indexOf("a7android888")!=-1){
            setBaseUrl("http://vnsr.ybapi.net/");
            setAndroidkey("1add675d5dab42b84c5285eb2fdf329fafd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.a7android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.a7android888applogo));
        }
        //威尼斯人b0token和网络请求
        if(BaseApplication.packagename.indexOf("b0android888")!=-1){
            setBaseUrl("http://v388.ybapi.net/");
            setAndroidkey("436dc0c835cdce6a1e082c7a66279854afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.b0android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.b0android888applogo));
        }
        //久博d3token和网络请求
        if(BaseApplication.packagename.indexOf("d3android888")!=-1){
            setBaseUrl("http://9b.ybapi.net/");
            setAndroidkey("cabe32a645676071eaf3209f2b02d233afd5c60835f5a43739d472b739f9b9f0");
            setQidong(getApplicationContext().getResources().getDrawable(R.drawable.d3android888qidong));
            setLogo(getApplicationContext().getResources().getDrawable(R.drawable.d3android888applogo));
        }
        //推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
//        JPushInterface.setLatestNotificationNumber(this, 3);
        setActivityCallBack();
    }


    //修改每个包的极光推送key
    private void EditJPUSH_APPKEY(String key ) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String msg = appInfo.metaData.getString("JPUSH_APPKEY");
        LogTools.e("JPUSH_APPKEY", "before: " + msg);

        appInfo.metaData.putString("JPUSH_APPKEY", key);

        msg = appInfo.metaData.getString("JPUSH_APPKEY");
        LogTools.e("JPUSH_APPKEY", "after: " + msg);
    }
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public ImageLoader getImageLoader() {
        if (imageloader == null)
            imageloader = ImageLoader.getInstance();

        if (imageloader.isInited()) {
            return imageloader;
        }
        return imageloader;
    }

    @SuppressWarnings("deprecation")
    public void setconfig(Context co) {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.defaultimage) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.defaultimage)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.defaultimage) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 是否緩存都內存中
                .cacheOnDisc(true)// 是否緩存到sd卡上
                        // .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        // .decodingOptions(options)//设置图片的解码配置
                        // .delayBeforeLoading(int delayInMillis)//int
                        // delayInMillis为你设置的下载前的延迟时间
                        // 设置图片加入缓存前，对bitmap进行设置
                        // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
//                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
                .build();// 构建完成
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), "/BiYing/"+packagename.split("\\.")[1]);
        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheExtraOptions(1440, 2560) // max width, max
                        // height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
                        // .threadPriority(Thread.NORM_PRIORITY - 2)
                        // .denyCacheImageMultipleSizesInMemory()
                        // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You
                        // can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                        // .memoryCacheSize(2 * 1024 * 1024)
                        // .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        // .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                        // // connectTimeout (5 s), readTimeout (30 s)超时时间
                        // .writeDebugLogs() // Remove for release app
                        // .build();//开始构建
                .memoryCache(new WeakMemoryCache())
                .defaultDisplayImageOptions(options)
                .discCache(new UnlimitedDiscCache(cacheDir))//自定义缓存路径
                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
                .discCacheFileCount(60)// 缓存文件的最大个数
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(getApplicationContext());

        return isNetConnect();

    }

    public String getLocalIPAddress() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
//        setDip(intToIp(info.getIpAddress()));
        LogTools.v("macip", intToIp(info.getIpAddress()));
        return intToIp(info.getIpAddress());
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
        isNetConnect();

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtil.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtil.NETWORK_NONE) {
            ToastUtil.showLongTimeMessage(getApplicationContext(), "没有网络", 1000);
            return false;

        }
        return false;
    }

    public static String GetNetIp() {
        String IP = "";
        try {
            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setUseCaches(false);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();

// 将流转化为字符串
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));

                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString + "\n");
                }

                JSONObject jsonObject = new JSONObject(retJSON.toString());
                String code = jsonObject.getString("code");
                if (code.equals("0")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    IP = data.getString("ip") + "(" + data.getString("country")
                            + data.getString("area") + "区"
                            + data.getString("region") + data.getString("city")
                            + data.getString("isp") + ")";

                    Log.e("提示", "您的IP地址是：" + IP);
                } else {
                    IP = "";
                    Log.e("提示", "IP接口异常，无法获取IP地址！");
                }
            } else {
                IP = "";
                Log.e("提示", "网络连接异常，无法获取IP地址！");
            }
        } catch (Exception e) {
            IP = "";
            Log.e("提示", "获取IP地址时出现异常，异常信息是：" + e.toString());
        }
        LogTools.e("ipip", IP);
        return IP;
    }

    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    public void setActivityCallBack() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                LogTools.e("registersada","onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                BaseApplication.this.isAppBackstage=false;
//                LogTools.e("registersada","onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                BaseApplication.this.activity=activity;
//                LogTools.e("registersada","onActivityResumed"+activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
//                LogTools.e("registersada","onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (isApplicationBroughtToBackground(activity)) {
                    //app 进入后台
                    BaseApplication.this.isAppBackstage=true;
                }
//                LogTools.e("registersada","onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//                LogTools.e("registersada","onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
//                LogTools.e("registersada","onActivityDestroyed");
                if(mRefWatcher!=null)
                    mRefWatcher.watch(activity);
            }
        });
    }

    public void ClearTiYuCache()//清体育下单记录
    {
        NOT_CountinueMode.edit().clear().commit();
        CountinueMode.edit().clear().commit();
        ssopenstate.edit().clear().commit();
    }
}
