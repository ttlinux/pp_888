package com.a8android888.bocforandroid.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/29.
 */
public class Httputils {
    public static final String client_server="1";//		客户端类型(1:PC端 2:移动端)

    public  static String ImgLOGOUrl="http://103.243.181.58:8082/";//logo图前缀地址
//  public static  String BaseUrl="http://192.168.0.124:8080/";//http://192.168.0.239:8391/";//http://api.6820168.com/    http://192.168.0.113:8080/
    public static  String BaseUrl=BaseApplication.getBaseUrl();
        //BaseApplication.getBaseUrl();//http://103.240.143.131:8083/ http://1b.ybapi.net/


//    public static  String BaseUrl="http://api.6820168.com/";
    public static final String BaseUrl2="http://192.168.0.238:8083/";
    public static final String BaseUrl3="http://103.243.181.58:8890/";//线上模拟数据地址
    public static final String TestUrl="http://192.168.0.113:8080/";//线上模拟数据地址
    public  static final String changeUserBasicInfo="member/user/modify/";//会员资料修改提交
    public  static final String UserInfo="member/user/userInfo/";//会员信息
    public static final String UserMessage="member/message/list";//用户消息
    public static final String SystemMessage="member/user/systemMessage/";//系统信息
    public static final String Payrecord="member/record/deposit";//充值记录
    public static final String ReadMessagescount="member/message/count";//未读消息总条数
    public static final String messagedetail="member/message/detail";//获取消息详情
    public static final String DeleteMessages="member/message/delete";//删除消息
    public static final String WithdrawHistory="member/record/withdraw/";//提现记录
    public static final String article="main/article/detail";//文章id查询文章
    public static final String Withdraw="member/withdraw/submit/";//提现  提款到银行卡
    public static final String EduHistory="member/record/edu";//额度转换记录
    public static final String ChangePassword="member/password/login";//修改账户密码
    public static final String ChangeWithdrawPassword="member/password/withdraw";//修改取款密码
    public static final String PlatformList="electronic/edu/";//额度转换平台列表
    public static final String balancemList="flat/balance/";//获取游戏平台余额
    public static final String deposit="flat/deposit/";//额度转换平台充值
    public static final String selectUserLoginLog="member/user/log/";//用户登录日志
    public static final String withdraw="flat/withdraw/";//额度转换平台提款
    public static final String Mainpage="main/getSiteInfo";//首页列表
    public static final String resource="main/init/resource";//获取相关的资源图片
    public static final String gamelist="electronic/menu";//功能平台列表//flat/list/
    public static final String gameroomlist="electronic/menu";//游戏中心列表
    public static final String modulelist="main/module/list";//获取动态列表
    public static final String articlelist="main/articleType/list";//获取动态文章列表
    public static final String articleGroup="main/articleGroup/list";//获取动态所属组文章列表
    public static final String gameloginlist="flat/loginH5";//游戏平台登陆链接
    public static final String authorize="oath2/authorize";//登录鉴权
    public static final String home="main/home";//首页区域
    public static final String login="user/login";//登录
    public static final String bannerlist="main/banner/list";//轮播图
    public static final String register="user/register";//注册
    public static final String BindBank="member/bank/bind";//绑定银行卡
    public static final String favourite="electronic/favourite/";//遊戲收藏
    public static final String platformlist="electronic/gamelist";//电子平台游戏列表
    public static final String ball="sport/ball";//体育类球类分类
    public static final String type="sport/type/";//体育类可下单类型
    public static final String lotterylist="cp/game/list/";//彩票列表
    public static final String lotterycenter="cp/results/list/";//开奖中心（和单个彩票开奖列表）
    public static final String lotteryonecenter="cp/results/detail/list/";//开奖中心（和单个彩票开奖列表）
    public static final String lotteryorder="cp/order/list/";//彩票订单列表
    public static final String Platform="electronic/flat";//注单查询平台和额度转换平台列表
    public static final String flatRecord="sport/record";//体育注单查询详情
    public static final String Record="flat/record";//平台注单查询
    public static final String SelectBankList="member/bank/user";//用户点击[提现]
    public static final String SelectUserBankList="member/bank/list/";//加载收款银行信息
    public static final String platformbalance="flat/balance/";//获取用户游戏平台余额
    public static final String SportsOdds="sport/odds/";//体育赔率列表
    public static final String lotterytype="cp/item/list/";//盘口下,单类型列表
    public static final String CommitOrder="cp/order/saveOrder/";//彩票提交
    public static final String CommitGroupOrder="cp/order/getGroupOrderList/";//彩票提交组选
    public static final String CommitMultiorder="cp/order/goMultiGroupOrder/";//彩票提交多选组合
    public static final String CommitLMOrder="cp/order/goLmOrder/";//彩票提交连码
    public static final String templatelist="main/template/list/";//网页模板
    public static final String BallResult="sport/result/";//球类赛果
    public static final String sportorder="sport/order/";//球类赛果
    public static final String notice="main/getGonggaoInfo";//公告
    public static final String logout="user/logout";//退出
    public static final String selectUserBankCodeInfo="member/bank/list";//加载银行列表信息
    public static final String panel="main/panel";//开户协议
    public static final String onlineFastPay="member/pay/online/saoma";//获取支付列表
    public static final String selectIncFastPayList="member/pay/chuantong/bank/list";//获取公司快捷支付列表
    public static final String selectIncBankPayList="member/user/selectIncBankPayList";//获取公司银行卡支付列表
    public static final String saveAgentApply="member/agent/apply";//代理申请
    public static final String selectAgentInfo="member/agent/info";//查询用户代理信息
    public static final String saveIncFastPay="member/user/saveIncFastPay";//公司入款快捷支付提交
    public static final String saveIncBankPay="member/pay/chuantong/bank";//公司入款银行卡支付提交
    public static final String selectUserBalance="member/user/balance";//查询用户余额
    public static final String getMobileGongGao="main/getMobileGongGao";//3.5	查询移动端公告信息
    public static final String saveOperatorLog="commons/saveOperatorLog";//提交错误日志接口
    public static final String Rule="rules/query";//玩法规则
    public static final String MobileVersion="commons/getMobileVersion";//版本更新
    public static final String selectPayList="member/pay/online/saoma/list";//获取个人快捷支付列表
    public static final String selectPayList_traditional="member/pay/chuantong/saoma/list";//获取传统扫码支付列表
    public static final String saveScanCodePay="member/pay/chuantong/saoma";//1.29	快捷支付-传统扫码支付
    public static final String promolist="member/promo/list";//优惠活动
    public static final String getWebPanel="commons/getWebPanel";//7.5	查询面板信息

    public static final String huikuanType="member/pay/huikuan/list";//汇款方式列表
    public static final String UserFragment_Model="member/resource";
    public static final String Sport_detail="sport/detail";//体育下单详情detail
    public static final String AuthorizeAct="activity/authorize";
    public static final String infomation="activity/authorize";//提交用户手机号码和联系人列表



    public static final String androidkey=BaseApplication.getAndroidkey();//体育下单详情detail
    public static final String androidsecret="d29d5926683e28dd5cbeae736f217d00";//体育下单详情detail
//    client.android.key=6524ee4deb5e785798f39f73afedfb7bafd5c60835f5a43739d472b739f9b9f0
public static    final String winningList="cp/order/winningList/";//	彩票中奖名单列表

    public static    final String refreshRate="cp/refreshRate/";//	彩票赔率
    public static    final String lotteryopen="cp/lottoGetGameInfo/";//	彩票开盘

//    client.android.secret=d29d5926683e28dd5cbeae736f217d00
    public static final String gesturestate="member/set/info";//手势的设置状态是否开启
    public static final String editgesturestate="member/password/gesture/state";//修改手势密码状态
    public static final String setgestures="member/password/gesture/set";//设置手势密码
    public static final String editgesturespassword="member/password/gesture";//1.14	修改手势密码
    public static final String checkgesturespassword="member/password/gesture/check";//1.17	验证手势密码
    public static final String bankremove="member/bank/remove";//银行卡解绑
    public static final String bankreupdate="member/bank/update";//银行卡修改
    public static final String AllPayType="member/pay/all/list";//所有支付方式
    public static final String forgotpassword="member/password/verify";
    public static final String resetpassword="member/password/login/reset";

    private static MyAsyncHttpClient client;

    private static int timeout=10000;//上线时记得改成10秒
    public    static final int[]  lotterry6red= {1,2,7,8,12,13,18,19,23,24,29,30,34,35,40,45,46};
    public  static final int[]  lotterry6green={5,6,11,16,17,21,22,27,28,32,33,38,39,43,44,49};
    public  static final int[]  lotterry6blue={3,4,9,10,14,15,20,25,26,31,36,37,41,42,47,48};

    public    static final int[]  lotterry28red= {3,6,9,12,15,18,21,24};
    public  static final int[]  lotterry28green={1,4,7,10,16,19,22,25};
    public  static final int[]  lotterry28blue={2,5,8,11,17,20,23,26};
    public  static final int[]  lotterry28gary={0,13,14,27};
    public static boolean isShow = false;//
    public static String AndroidApkPath;
    public static String AndroidUpdate="";


    public interface BalanceListener
    {
        public void OnRecevicedata(String balance);
        public void Onfail(String str);
    }

    /**更新配置请求页面*/
    public static String httpurlAndroidUpdate(){
//        if(isShow){
//            AndroidUpdate="http://up.djr158.com/App/config.json";
//        }
//        else{
//            AndroidUpdate = "http://update.gdgccj.com/app/config.json";
//        }
        AndroidUpdate = "commons/getMobileVersion";
        return AndroidUpdate;
    }

    //配置apk下载地址
    public static String GetApkDownloadPath()
    {
//        if(isShow)
//        {
//            AndroidApkPath="http://jqd-download.oss-cn-hangzhou.aliyuncs.com/install/robot_zz.apk";
//        }
//        else
//        {
//            AndroidApkPath="http://jqd-download.oss-cn-hangzhou.aliyuncs.com/install/robot_gc.apk";
//        }

        return AndroidApkPath;
    }
    public static  void Post(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler)
    {
        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.post(url,requestParams,jsonHttpResponseHandler);
    }
    public static  void Post(String url,RequestParams requestParams,JsonHttpResponseHandler jsonHttpResponseHandler)
    {
        LogTools.e("URLPost",  url + new Gson().toJson(requestParams));
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(timeout);
        client.post(url,requestParams,jsonHttpResponseHandler);
    }
    public static String[] convertStrToArray(String str){
        String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[ \\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\= \\+ ] ";
//        LogTools.e("Stringcode",str);
        String aaa=str.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\\"", "").replaceAll("\\+", ",").replaceAll("=", ",");
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(aaa);
        String aa=m.replaceAll( ",").trim();
//        LogTools.e("Stringcode",aa);
        String[] strArray = null;
        strArray = aa.split(",");
//        LogTools.e("Stringcodeee", strArray.toString());
        //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }


    public static void GetBalance(String userName,final BalanceListener listener,Activity activity)
    {
        RequestParams params=new RequestParams();
        params.put("userName",userName);
        Httputils.PostWithBaseUrl(selectUserBalance,params,new MyJsonHttpResponseHandler(activity,false){

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                listener.Onfail(s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("yyyyy",jsonObject.toString());
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    if(listener!=null)
                    {
                        listener.OnRecevicedata((jsonObject.optString("datas", "")));
                    }
                }
            }
        });
    }

    public static boolean useLoop(int[] arr, int targetValue) {
        for (int s : arr) {
            if (s==targetValue) {
                return true;
            }
        }
        return false;
    }

    public static void Get(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler)
    {
        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        LogTools.e("url", url);
        client.get(url, requestParams, jsonHttpResponseHandler);
    }


    public static void PostWithBaseUrlTEST(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler) {

        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        LogTools.e("URL3333", TestUrl + url + new Gson().toJson(requestParams));
        client.post(TestUrl + url, requestParams, jsonHttpResponseHandler);
    }

    public static void PostWithBaseUrl3(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler) {

        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        LogTools.e("URL3333", BaseUrl3 + url + new Gson().toJson(requestParams));
        client.post(BaseUrl3 + url, requestParams, jsonHttpResponseHandler);
    }

    public static void PostWithBaseUrl(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler) {

        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        LogTools.e("URL", BaseUrl + url  + new Gson().toJson(requestParams));
        client.post(BaseUrl + url, requestParams, jsonHttpResponseHandler);
    }
    public static void PostWithBaseUrl(String url,RequestParams requestParams,MyJsonHttpResponseHandler2 jsonHttpResponseHandler) {
        LogTools.e("URL", BaseUrl + url  + new Gson().toJson(requestParams));
        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.post(BaseUrl + url, requestParams, jsonHttpResponseHandler);
    }
//    public static void PostWithBaseUrld(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler) {
//        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
//        client.setTimeout(timeout);
//        LogTools.e("getURL", "http://192.168.0.238:8083/Inter/" + url + new Gson().toJson(requestParams));
//        client.post("http://192.168.0.238:8083/Inter/" + url, requestParams, jsonHttpResponseHandler);
//    }
//    public static void GetWithBaseUrld(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler)
//    {
//        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
//        client.setTimeout(timeout);
//        LogTools.e("getURL", "http://192.168.0.238:8083/Inter/" + url);
//        client.get("http://192.168.0.238:8083/Inter/" + url, requestParams, jsonHttpResponseHandler);
//    }
public static void GETWithoutBASEURL(String url, RequestParams params,
                                     MyJsonHttpResponseHandler responseHandler) {
    AsyncHttpClient client1 = new AsyncHttpClient();
    client1.setTimeout(3000);
    client1.get(url, params, responseHandler);
    LogTools.e("getAbsoluteUrl(url)",""+url+params);
//		saveFile(""+BASE+url+params);
}

    public static void getWithoutBASEurl(String url, RequestParams params,
                                         MyJsonHttpResponseHandler responseHandler) {
        AsyncHttpClient client1 = new AsyncHttpClient();
        client1.setTimeout(3000);
        client1.get(url, params, responseHandler);
        LogTools.e("getAbsoluteUrl(url)", "" + url + params);
//		saveFile(""+BASE+url+params);
    }


    public static void GetWithBaseUrl(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler)
    {
        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.get(BaseUrl + url, requestParams, jsonHttpResponseHandler);
    }

    public static void PutWithBaseUrl(String url,RequestParams requestParams,MyJsonHttpResponseHandler jsonHttpResponseHandler)
    {
        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.put(BaseUrl + url, requestParams, jsonHttpResponseHandler);
    }
    public static void DeleteWithBaseUrl(Context context,String url,MyJsonHttpResponseHandler jsonHttpResponseHandler)
    {
        client=new MyAsyncHttpClient(jsonHttpResponseHandler.getContext());
        client.setTimeout(timeout);
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");

        client.delete(context, url, jsonHttpResponseHandler);
//        client.delete(BaseUrl + url, jsonHttpResponseHandler);
    }


//    /**
//     * Delete
//     * @param url 发送请求的URL
//     * @return 服务器响应字符串
//     * @throws Exception
//     */
//    public static String deleteRequest(final String url,final int id)
//            throws Exception
//    {
//
//        FutureTask<String> task = new FutureTask<String>(
//                new Callable<String>()
//                {
//                    @Override
//                    public String call() throws Exception
//                    {
//                        HttpDeleteWithBody httpDelete = new HttpDeleteWithBody(url);
//// json 处理
//                        httpDelete.setHeader("Content-Type", "application/x-www-form-urlencoded;");//or addHeader();
//                        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//
//                        NameValuePair pair1 = new BasicNameValuePair("userName", "duanaifei");
//                        NameValuePair pair2 = new BasicNameValuePair("id", id+"");
//
//                        pairs.add(pair1);
//                        pairs.add(pair2);
//                        httpDelete.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
//
//                        httpDelete.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
//
//                        httpDelete.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
//                        // 发送GET请求
//                        httpClient= new DefaultHttpClient();
//                        HttpResponse httpResponse = httpClient.execute(httpDelete);
//                        // 如果服务器成功地返回响应
//                        LogTools.e("codeccccc", httpResponse.getStatusLine()
//                                .getStatusCode()+ " " + httpResponse.getEntity().getContentType() + " " + httpResponse.getStatusLine().getReasonPhrase());
//
//                        LogTools.e("success", convertStreamToString(httpResponse.getEntity().getContent()));
//
//                        if (httpResponse.getStatusLine()
//                                .getStatusCode() == 204)
//                        {
//                            // 获取服务器响应字符串
//                            LogTools.e("success","success");
//                            return "success";
//                        }
//                        return null;
//                    }
//                });
//        new Thread(task).start();
//        return task.get();
//    }

//    public static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
//
//        public static final String METHOD_NAME = "DELETE";
//        public String getMethod() { return METHOD_NAME; }
//        public HttpDeleteWithBody(final String uri) {
//            super();
//            setURI(URI.create(uri));
//        }
//        public HttpDeleteWithBody(final URI uri) {
//            super();
//            setURI(uri);
//        }
//        public HttpDeleteWithBody() { super(); }
//
//    }


    public static String Limit2(double data){
        DecimalFormat df = new DecimalFormat("#0.00");
        return  df.format(data);
    }

    public static String convertStreamToString(InputStream is) {
        /*
          * To convert the InputStream to String we use the BufferedReader.readLine()
          * method. We iterate until the BufferedReader return null which means
          * there's no more data to read. Each line will appended to a StringBuilder
          * and returned as String.
          */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
    /***年月日 时分秒***/
    public static String getStringtime() {
        SimpleDateFormat df  =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        long diff = 0;
        String d1=df.format(date);
        try {
            diff = df.parse(d1).getTime() ;
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d1;

    }
    /***年月日时分秒***/
    @SuppressLint("SimpleDateFormat")
    public static String getStringToDate(long  diff) {
        String day=null;
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        String diffDaystring="天";
        String diffHourstirng="小时";
        String diffMinutestring="分";
        String diffSecondstring="秒";

        String diffdays=String.valueOf(diffDays);
        String diffhours=String.valueOf(diffHours);
        String diffminutes=String.valueOf(diffMinutes);
        String diffseconds=String.valueOf(diffSeconds);
        if(diffDays<1){
            diffDaystring="";
            diffdays="";
        }
         if(diffHours<1){
             if(diffDays<1){
             diffHourstirng="";
             diffhours="";
             }
        }
         if(diffMinutes<1){
             if(diffDays<1&&diffHours<1) {
                 diffMinutestring = "";
                 diffminutes = "";
             }
        }
            day = diffdays + diffDaystring + diffhours +diffHourstirng + diffminutes +diffMinutestring + diffseconds +diffSecondstring;
        return day;
    }
    /***年月日***/
    public static String getStringtimeday() {
        SimpleDateFormat df  =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date=new Date();
        long diff = 0;
        String d1=df.format(date);
        try {
            diff = df.parse(d1).getTime() ;
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d1;

    }

    /***年月日***/
    public static String getStringtimeymd() {
        SimpleDateFormat df  =  new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        long diff = 0;
        String d1=df.format(date);
        try {
            diff = df.parse(d1).getTime() ;
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return d1;

    }
    /***年月日时分秒***/
    @SuppressLint("SimpleDateFormat")
    public static String getStringToDate(long  endtime,long startime,int reshtime) {
        SimpleDateFormat  dateformat3 =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String timed= dateformat3.format(new Date(Long.valueOf(endtime + "000")));
        String stime= dateformat3.format(new Date(Long.valueOf(startime + "000")));
        return getStringtime(stime,timed, reshtime);
    }

    @SuppressLint("SimpleDateFormat")
    /***时间差***/
    public static String getStringtime(String  stime,String endtime,int reshtime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String day=null;
        Date d1 = null;
        Date d2 = null;
        Date d3= null;
        try {
            d1 = format.parse(stime);
            d2 = format.parse(endtime);
//            Calendar nowTime = Calendar.getInstance();
//            nowTime.add(Calendar.SECOND, reshtime);
//
//            String three_days_ago = format.format(nowTime.getTime());
//            d3=format.parse(three_days_ago);
            //毫秒ms
            long diff = d2.getTime() - d1.getTime()/*- d3.getTime()*/;

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            day=diffDays+"天"+diffHours+"时"+diffMinutes+"分"+diffSeconds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;

    }
    /**
     * 根据日期获得星期
     * @param date
     * @return
     */
    public static String getWeekOfDate() {

        SimpleDateFormat df  =  new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date=new Date();
        long diff = 0;
        String d1=df.format(date);
        String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysCode[intWeek];
    }
    /**
     * 获取某日期往前多少天的日期
     * @CreateTime
     * @Author PSY
     * @param nowDate
     * @param beforeNum
     * @return
     */
    public static String getBeforeDate(Integer beforeNum){
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar1.add(Calendar.DATE, -beforeNum);
        String three_days_ago = sdf1.format(calendar1.getTime());
        return (three_days_ago);//得到前beforeNum天的时间
    }
    /**
     * 获取某日期往前多少小时的时间
     * @CreateTime
     * @Author PSY
     * @param nowDate
     * @param beforeNum
     * @return
     */
    public static Date getBeforeHour(Date nowDate, Integer beforeNum){
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(nowDate);//把当前时间赋给日历
        calendar.add(Calendar.HOUR_OF_DAY, -beforeNum);  //设置为前beforeNum小时
        return calendar.getTime();   //得到前beforeNum小时的时间
    }

    /**
     * 判断是否是Integer类型
     * @author daichangfu
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        if(str!=null&&!"".equals(str.trim())){
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(str);
            Long number = 0l;
            if(isNum.matches()){
                number=Long.parseLong(str);
            }else{
                return false;
            }
            if(number>2147483647){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
    /*/保留2位小数***/
    public static String setjiage(String data){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(new BigDecimal(Double.valueOf(data.toString())));
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return context.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.version_name);
        }
    }

}
