package com.a8android888.bocforandroid.util;

import android.app.Activity;
import android.content.Intent;
import android.widget.PopupWindow;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.LotterycomitorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/13.
 */
public class HttpForLottery {


    //    jsons:[{"uid":"TM-TM_A-02","rate":43,"label":"香港六合彩","label2":"特码A","xzje":10,"number":"02"},
//    {"uid":"TM-TM_A-01","rate":43,"label":"香港六合彩","label2":"特码A","xzje":10,"number":"01"}]
//    gameCode:HK6
//    orderFlag:normal
    private  OnDataReceviceListener listener;
    static Boolean backsta=null;
    //提交订单 常规订单
    public static void Commitorder(final Activity context, String gameCode,  String orderFlag, String jsons,final OnDataReceviceListenera listener) {
        String Username =((BaseApplication)context.getApplication()).getBaseapplicationUsername();

        if (Username == null || Username.equalsIgnoreCase("")) {
            backsta=false;
                Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ToastUtil.showMessage(context, "未登录");
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("gameCode", gameCode);
        requestParams.put("userName", Username);
        requestParams.put("orderFlag", orderFlag);
        requestParams.put("jsons", jsons);
//        requestParams.put("clientIp", "android");
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(gameCode);
        stringBuilder.append("|");
        stringBuilder.append(Username);
        stringBuilder.append("|");
        stringBuilder.append(orderFlag);
        stringBuilder.append("|");
        stringBuilder.append(jsons);
//        stringBuilder.append("|");
//        stringBuilder.append("android");
        requestParams.put("signature", MD5Util.sign(stringBuilder.toString(),Httputils.androidsecret));
                Httputils.PostWithBaseUrl(Httputils.CommitOrder, requestParams, new MyJsonHttpResponseHandler(context, true) {
                    @Override
                    public void onSuccessOfMe(JSONObject jsonObject) {
                        super.onSuccessOfMe(jsonObject);
//                LogTools,equals(this,jsonObject.toString());
                        if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                            ToastUtil.showMessage(context, context.getString(R.string.commitordersuccess));
                            backsta = true;
                        } else {
                            ToastUtil.showMessage(context, jsonObject.optString("msg"));
                            backsta = false;
                        }
                        if (listener != null) listener.OnRecevice(backsta);
                    }

                    @Override
                    public void onFailureOfMe(Throwable throwable, String s) {
                        super.onFailureOfMe(throwable, s);


                    }
                });
    }

    public static String GetCommitJson(ArrayList<?> mList, String label, String label2) {
     String backstring="";
        if (mList != null && mList.size() > 0 && mList.get(0) instanceof LotterycomitorderBean) {
            JSONArray jsnarr = new JSONArray();
            for (int i = 0; i < mList.size(); i++) {
                LotterycomitorderBean bean = (LotterycomitorderBean) mList.get(i);
                    JSONObject jsonobj = new JSONObject();
//                {"uid":"TM-TM_A-02","rate":43,"label":"香港六合彩","label2":"特码A","xzje":10,"number":"02"}
                    try {
                        jsonobj.put("uid", bean.getUid());
                        jsonobj.put("rate", bean.getPl());
                        jsonobj.put("label", label);
                        jsonobj.put("label2", label2);
                        jsonobj.put("xzje",bean.getXzje());
                        jsonobj.put("number",bean.getNumber());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    jsnarr.put(jsonobj);
            }
            backstring=jsnarr.toString();
            return jsnarr.toString();
        }
        return backstring;
    }
    public static String GetCommitLMJson(String token, String xzje, String label2,String cateCode) {
        String backstring="";
            JSONArray jsnarr = new JSONArray();
                JSONObject jsonobj = new JSONObject();
//                [{"token":"b9229ed172cf280e61e928fdb162cc100a95770833f68a87","xzje":10,"typeCode":"LX","cateCode":"ELX"}]
                try {
                    jsonobj.put("token",token);
                    jsonobj.put("xzje", xzje);
                    jsonobj.put("typeCode", label2);
                    if(cateCode.equalsIgnoreCase("")||cateCode.length()<1){
                        jsonobj.put("cateCode","0");
                    }
                    else {
                        jsonobj.put("cateCode", cateCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsnarr.put(jsonobj);
            return jsnarr.toString();
    }
//开盘
    public static JSONObject lotteryopendata(final Activity context, String gameId, String itemId, final String gameitemcode,final OnDataReceviceListener listener) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("gameCode", gameId);
        requestParams.put("itemCode", itemId);
        Httputils.PostWithBaseUrl(Httputils.lotteryopen, requestParams, new MyJsonHttpResponseHandler(context,Lottery_MainActivity.needdialog()) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
LogTools.e("的點點滴滴",jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000"))
                {
                    if(listener!=null)listener.OnRecevice(2);//失败
                    return;
                }
                if (context!=null) {
                    JSONObject object = jsonObject.optJSONObject("datas");
                    if (object.optString("status","").equalsIgnoreCase("0")) {
                        BaseApplication baseApplication = (BaseApplication) context.getApplication();
                        baseApplication.setLastQs(object.optString("lastQs"));
                        baseApplication.setLastResult(object.optString("lastResult"));
                        baseApplication.setCurrentQs(object.optString("currentQs"));
                        baseApplication.setOpentime(object.optLong("opentime"));
                        baseApplication.setClosetime(object.optLong("closetime"));
                        baseApplication.setNowtime(object.optLong("nowtime"));
                        JSONObject object1 = object.optJSONObject("betInfo");
                        baseApplication.setMaxlimit(setmaxLimitdata(object1.optJSONObject("maxLimit"), gameitemcode));
                        baseApplication.setMinLimit(setmaxLimitdata(object1.optJSONObject("minLimit"), gameitemcode));
                        baseApplication.setMaxPeriodLimit(setmaxLimitdata(object1.optJSONObject("maxPeriodLimit"), gameitemcode));
                        JSONObject object2 = object.optJSONObject("animal");
                        baseApplication.setJsonObjectanimal(object2);
                        LogTools.e("返回的" + object.optLong("currentQs"), baseApplication.getJsonObjectanimal() + "");
                        if(listener!=null)listener.OnRecevice(1);//成功
                    } else {

                        Lottery_MainActivity mainActivity=(Lottery_MainActivity)context;
                        if(mainActivity!=null) {
                            PopupWindow popupWindow = mainActivity.getPopWindow();
                            if (popupWindow == null || !popupWindow.isShowing()) {
                                mainActivity.ShowPopWindow();
                                mainActivity.setContenttext("正在维护");
                            }
                        }
//                        Intent intenta = new Intent(context,
//                                dialog.class);
//                        intenta.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intenta);
                    }

                } else {
                    ToastUtil.showMessage(context, context.getString(R.string.getopendatafail));
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                if(listener!=null)listener.OnRecevice(2);//成功
            }
        });
        return null;
    }

    public static String setmaxLimitdata(JSONObject object, String gamecode) {
        String gamecodemax;
        gamecodemax = object.optString(gamecode);
        LogTools.e("返回的" + gamecode, gamecodemax);
        return gamecodemax;
    }


    public static interface OnDataReceviceListener
    {
        public void OnRecevice(int Status);
    }
    public static interface OnDataReceviceListenera
    {
        public void OnRecevice(Boolean state);
    }
    public static interface OnDataReceviceListenerd
    {
        public void OnRecevice(JSONObject jsonObject);
    }
    //提交订单 组选	下注号码，多个号码时用英文逗号分隔
    public static void CommitGrouporder( Activity context, String gameCode,  String itemCode, String xzlxCode,String multilen,String number ,final OnDataReceviceListenerd listener) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("gameCode", gameCode);
        requestParams.put("itemCode", itemCode);
        requestParams.put("xzlxCode", xzlxCode);
        requestParams.put("multilen", multilen);
        requestParams.put("number", number);
        Httputils.PostWithBaseUrl(Httputils.CommitGroupOrder, requestParams, new MyJsonHttpResponseHandler(context,true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(listener!=null)listener.OnRecevice(jsonObject);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);


            }
        });
    }

    //提交订单多选组合cids	 String	拼接字符串，多个号码的uid拼接起来。
    public static void CommitMultiorder( Activity context, String gameCode,String multilen,String cids ,final OnDataReceviceListenerd listener ) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("gameCode", gameCode);
        requestParams.put("multilen", multilen);
        requestParams.put("cids", cids);
        Httputils.PostWithBaseUrl(Httputils.CommitMultiorder, requestParams, new MyJsonHttpResponseHandler(context,true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(listener!=null)listener.OnRecevice(jsonObject);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);


            }
        });
    }
    //提交订单 连码
    public static void CommitLMorder( Activity context, String gameCode,String nums,String rrtype,String pabc,String dm1,String dm2,final OnDataReceviceListenerd listener) {
        RequestParams requestParams = new RequestParams();
        requestParams.put("gameCode", gameCode);
        requestParams.put("rrtype", rrtype);
        requestParams.put("nums", nums);
        requestParams.put("pabc", pabc);
        requestParams.put("dm1", dm1);
        requestParams.put("dm2", dm2);
        Httputils.PostWithBaseUrl(Httputils.CommitLMOrder, requestParams, new MyJsonHttpResponseHandler(context,true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(listener!=null)listener.OnRecevice(jsonObject);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);


            }
        });
    }
}
