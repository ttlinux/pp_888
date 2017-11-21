package com.a8android888.bocforandroid.util;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/28.
 */
public class HttpforNoticeinbottom {

    public static final String noticeurl="commons/getMobileInformation";
    public static HashMap<String,Noticebean> hashMap=new HashMap<>();
    public static HashMap<String,String> hashMap2=new HashMap<>();

    public static void  GetMainPageData(final Activity act,boolean showdialog,final OnreceiveListener listener)
    {
        if(hashMap!=null && hashMap.size()>0 && listener!=null) {
            listener.hasdata(hashMap);
            return;
        }
        RequestParams requestParams=new RequestParams();
        Httputils.PostWithBaseUrl(noticeurl,requestParams,new MyJsonHttpResponseHandler(act,showdialog)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                if(listener!=null)listener.fail();
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("getMobileInformation",jsonObject.toString());
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    JSONArray array=jsonObject.optJSONArray("datas");
                    for (int i = 0; i <array.length(); i++) {
                        JSONObject jsobj= array.optJSONObject(i);
                        Noticebean bean=new Noticebean();
                        bean.setModuleDesc(jsobj.optString("moduleDesc", ""));
                        bean.setModuleType(jsobj.optString("moduleType", ""));
                        hashMap.put(bean.getModuleType(),bean);
                    }
                    if(listener!=null)listener.hasdata(hashMap);
                }
            }
        });
    }


    public static String  GetMainPageData(final TextView view,final String tag,Activity act)
    {
        return CurrentMethod(view,tag,act,null);
//        AbandonMethod(view, tag, act);
    }
    public static String  GetMainPageData(final TextView view,final String tag,ViewGroup parent,Activity act)
    {
        return CurrentMethod(view,tag,act,parent);
//        AbandonMethod(view, tag, act);
    }
    private static void AbandonMethod(final TextView view,final String tag,Activity act)
    {
        if(hashMap!=null && hashMap.size()>0 && view!=null && hashMap.get(tag)!=null) {
            view.setText(hashMap.get(tag).getModuleDesc());
            return;
        }
        RequestParams requestParams=new RequestParams();
        Httputils.PostWithBaseUrl(noticeurl,requestParams,new MyJsonHttpResponseHandler(act,false)
        {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                view.setText("");
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("getMobileInformation", jsonObject.toString());
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    JSONArray array=jsonObject.optJSONArray("datas");
                    for (int i = 0; i <array.length(); i++) {
                        JSONObject jsobj= array.optJSONObject(i);
                        Noticebean bean=new Noticebean();
                        bean.setModuleDesc(jsobj.optString("moduleDesc", ""));
                        bean.setModuleType(jsobj.optString("moduleType", ""));
                        hashMap.put(bean.getModuleType(),bean);
                    }
                    if(hashMap.get(tag)==null || hashMap.get(tag).getModuleDesc()==null) {
//                        view.setText("");
//                        view.setCompoundDrawables(null,null,null,null);
                        view.setVisibility(View.GONE);
                    }
                    else {
                        view.setVisibility(View.VISIBLE);
                        view.setText(hashMap.get(tag).getModuleDesc());
                    }
                }
            }
        });
    }

    private static String CurrentMethod(final TextView view,final String tag,Activity act,ViewGroup parent)
    {
        if(hashMap2!=null && hashMap2.size()>0 && view!=null) {
           if(hashMap2.get(tag)!=null && !hashMap2.get(tag).equalsIgnoreCase(""))
           {
               view.setVisibility(View.VISIBLE);
               view.setText(Html.fromHtml(hashMap2.get(tag)));
               if(parent!=null)
               {
                   parent.setVisibility(View.VISIBLE);
               }
           }
            else
           {
               view.setVisibility(View.GONE);
               if(parent!=null)
               {
                   parent.setVisibility(View.GONE);
               }
           }
        }
        if(hashMap2!=null)
        {
            return hashMap2.get(tag);
        }
        return null;
    }


    public interface OnreceiveListener
    {
        public void hasdata(HashMap<String,Noticebean> hashMap);
        public void fail();
    }

    public static class Noticebean
    {
        public String getModuleType() {
            return moduleType;
        }

        public void setModuleType(String moduleType) {
            this.moduleType = moduleType;
        }

        public String getModuleDesc() {
            return moduleDesc;
        }

        public void setModuleDesc(String moduleDesc) {
            this.moduleDesc = moduleDesc;
        }

        String moduleType;
        String moduleDesc;
    }
//        "datas": [
//        {
//            "moduleType": "register",
//                "moduleDesc": "注册信息提示"
//        },
//        {
//            "moduleType": "withdraw",
//                "moduleDesc": "申请提现提示"
//        },
//        {
//            "moduleType": "login",
//                "moduleDesc": "登录信息提示"
//        },
//        {
//            "moduleType": "compay",
//                "moduleDesc": "公司入款提示"
//        },
//        {
//            "moduleType": "applyAgent",
//                "moduleDesc": "申请代理提示"
//        },
//        {
//            "moduleType": "fastpay",
//                "moduleDesc": "快捷支付提示"
//        }
//        ],
//        "errorCode": "000000",
//            "msg": "操作成功"
//    }
static   String msg="";
    public static String Gettext(final String strmodel,final TextView textView,Activity activity)
    {

        RequestParams requestParams = new RequestParams();
        Httputils.PostWithBaseUrl(Httputils.UserFragment_Model,requestParams,new MyJsonHttpResponseHandler(activity,true)
        {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    return;
                }

                JSONObject datas = jsonObject.optJSONObject("datas");
                Double pay=Double.valueOf(datas.optString("eduMinPay",""))*0.01*100;
                String patstr=Httputils.Limit2(pay);
                msg=patstr;
                LogTools.e("msgmsg1",msg);
                textView.setText(String.format(strmodel,patstr));
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
        return  msg;
    }
}
