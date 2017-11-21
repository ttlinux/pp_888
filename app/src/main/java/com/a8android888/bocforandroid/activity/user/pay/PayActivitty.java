package com.a8android888.bocforandroid.activity.user.pay;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Pay_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.OnlinefastPayBean;
import com.a8android888.bocforandroid.Bean.PayBankCardBean;
import com.a8android888.bocforandroid.Bean.PaymentBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpForNotice;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.Getlistheight;
import com.a8android888.bocforandroid.view.MyListView;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.a8android888.bocforandroid.webActivity;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/1. 充值
 */
public class PayActivitty<T> extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private ImageView back;
    TextView title;
    RadioButton rb1, rb2;
    LinearLayout quick_paylayout;
    RelativeLayout into_paylayout;
    MyListView listView;
    Pay_Adapter adapter;
    ArrayList<PayBankCardBean.PayBankCardBean1> beans = new ArrayList<PayBankCardBean.PayBankCardBean1>();
    ImageLoader imageloader;
    TextView textView2;
//    ArrayList<JSONObject> incBankPayList=new ArrayList<JSONObject>();
//    ArrayList<JSONObject> thirdPayKjList=new ArrayList<JSONObject>();
//    ArrayList<String> bankTransferTypeList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        initview();
        initview2();
//        GetPayType();//在线二维码支付
//        GetPayType2();//传统二维码支付
//        GetincData();//银行卡
        //        GetincData(true);//快捷支付
        GetAllPayType();//所有支付方式

    }

    //快捷支付的
    private void initview() {
        imageloader = ((BaseApplication) getApplication()).getImageLoader();
        back = (ImageView) FindView(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title = (TextView) FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.userrecharge));
        rb1 = (RadioButton) FindView(R.id.rb1);
        rb1.setText("快捷支付");
        rb1.setOnCheckedChangeListener(this);
        rb2 = (RadioButton) FindView(R.id.rb2);
        rb2.setText("公司入款");
        rb2.setOnCheckedChangeListener(this);
        quick_paylayout = (LinearLayout) FindView(R.id.quick_paylayout);
        textView2 = FindView(R.id.textView2);

        HttpforNoticeinbottom.GetMainPageData(textView2, "fastpay", this);

    }

    //公司入款
    private void initview2() {

        listView = (MyListView) FindView(R.id.listview);
        listView.setFocusable(false);
        into_paylayout = (RelativeLayout) FindView(R.id.into_paylayout);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb1:
                if (isChecked) {
                    quick_paylayout.setVisibility(View.VISIBLE);
                    into_paylayout.setVisibility(View.GONE);
                    HttpforNoticeinbottom.GetMainPageData(textView2, "fastpay", this);
                }
                break;
            case R.id.rb2:
                if (isChecked) {
                    quick_paylayout.setVisibility(View.GONE);
                    into_paylayout.setVisibility(View.VISIBLE);
                    HttpforNoticeinbottom.GetMainPageData(textView2, "compay", this);
                }
                break;
        }
    }

    public void GetPayType()//获取在线快捷支付
    {
        RequestParams params = new RequestParams();
        params.put("userName", ((BaseApplication) getApplication()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.selectPayList, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("selectPayList", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONArray scanCodePayList = jsonObject.optJSONArray("datas");

//                    if(bankTransferTypeList!=null && incBankPayList!=null)
//                    setAdapter(incBankPayList,bankTransferTypeList);
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    public void GetPayType2()//获取传统二维码支付
    {
        RequestParams params = new RequestParams();
        params.put("userName", ((BaseApplication) getApplication()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.selectPayList_traditional, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("selectPayList2", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    JSONArray scanCodePayList = jsonObject.optJSONArray("datas");

                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    private void GetincData()
    {
        RequestParams params = new RequestParams();
        params.put("userName", ((BaseApplication) getApplication()).getBaseapplicationUsername());
//        String url=isfast?Httputils.selectIncFastPayList:Httputils.selectIncBankPayList;
        Httputils.PostWithBaseUrl(Httputils.selectIncFastPayList, params, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("GetincData", jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                JSONArray jsonArray = jsonObject.optJSONArray("datas");


            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);


            }
        });
    }

    private void analysis0(JSONArray scanCodePayList,String strs)//在线支付
    {
        ArrayList<OnlinefastPayBean> beans = new ArrayList<OnlinefastPayBean>();
        for (int i = 0; i < scanCodePayList.length(); i++) {
            JSONObject jsobj = scanCodePayList.optJSONObject(i);
            OnlinefastPayBean bean = new OnlinefastPayBean();
            bean.setBigPicUrl(jsobj.optString("bigPicUrl", ""));
            bean.setPayName(jsobj.optString("payName", ""));
//                        bean.setPayNo(jsobj.optString("payNo", ""));
//                        bean.setPayRname(jsobj.optString("payRname", ""));
//                        bean.setPicUrl(jsobj.optString("picUrl", ""));
            bean.setSmallPicUrl(jsobj.optString("smallPicUrl", ""));
            bean.setmaxPay(jsobj.optInt("payMaxEdu", 1000));
            bean.setMinEdu(jsobj.optInt("payMinEdu", 1));
            bean.setPayType(jsobj.optInt("payType", 1));
            bean.setMinMaxDes(jsobj.optString("minMaxDes", ""));
            bean.setModule(jsobj.optString("module", ""));
            JSONArray json_banklist=jsobj.optJSONArray("bank");
            if(json_banklist!=null && json_banklist.length()>0)
            {
               ArrayList<OnlinefastPayBean.BankInfo> bankinfos=new ArrayList<>();
                for (int j = 0; j < json_banklist.length(); j++) {
                    JSONObject jsonobj=json_banklist.optJSONObject(j);
                    OnlinefastPayBean.BankInfo bankinfo=new OnlinefastPayBean.BankInfo();
                    bankinfo.setBankCode(jsonobj.optString("bankCode"));
                    bankinfo.setBankImages(jsonobj.optString("bankImages"));
                    bankinfo.setBankName(jsonobj.optString("bankName"));
                    bankinfo.setClientType(jsonobj.optString("clientType"));
                    bankinfo.setThirdpayCode(jsonobj.optString("thirdpayCode"));
                    bankinfos.add(bankinfo);
                }
                bean.setBanklist(bankinfos);
            }

            File cacheDir = StorageUtils.getOwnCacheDirectory(
                    getApplicationContext(), "/BiYing/Cache");
//                        String imagedata=jsobj.optString("payPic", "");
//                        String image16path=cacheDir.getAbsolutePath()+"/"+imagedata.hashCode()+".biying";
//                        stringtoBitmap(imagedata, image16path);
//                        bean.setPayPic(image16path);
            String remark = jsobj.optString("remark", "");
            bean.setRemark(jsobj.optString("remark", ""));
            beans.add(bean);
        }
        if (beans.size() > 0)
            InitView0(beans, strs);
    }

    private void analysis1(JSONArray scanCodePayList,String strs)//在线扫码支付
    {
        ArrayList<OnlinefastPayBean> beans = new ArrayList<OnlinefastPayBean>();
        for (int i = 0; i < scanCodePayList.length(); i++) {
            JSONObject jsobj = scanCodePayList.optJSONObject(i);
            OnlinefastPayBean bean = new OnlinefastPayBean();
            bean.setBigPicUrl(jsobj.optString("bigPicUrl", ""));
            bean.setPayName(jsobj.optString("payName", ""));
//                        bean.setPayNo(jsobj.optString("payNo", ""));
//                        bean.setPayRname(jsobj.optString("payRname", ""));
//                        bean.setPicUrl(jsobj.optString("picUrl", ""));
            bean.setSmallPicUrl(jsobj.optString("smallPicUrl", ""));
            bean.setmaxPay(jsobj.optInt("payMaxEdu", 1000));
            bean.setMinEdu(jsobj.optInt("payMinEdu", 1));
            bean.setPayType(jsobj.optInt("payType", 1));
            bean.setMinMaxDes(jsobj.optString("minMaxDes", ""));
            bean.setModule(jsobj.optString("module", ""));
            JSONArray json_banklist=jsobj.optJSONArray("bank");
            if(json_banklist!=null && json_banklist.length()>0)
            {
                ArrayList<OnlinefastPayBean.BankInfo> bankinfos=new ArrayList<>();
                for (int j = 0; j < json_banklist.length(); j++) {
                    JSONObject jsonobj=json_banklist.optJSONObject(j);
                    OnlinefastPayBean.BankInfo bankinfo=new OnlinefastPayBean.BankInfo();
                    bankinfo.setBankCode(jsonobj.optString("bankCode"));
                    bankinfo.setBankImages(jsonobj.optString("bankImages"));
                    bankinfo.setBankName(jsonobj.optString("bankName"));
                    bankinfo.setClientType(jsonobj.optString("clientType"));
                    bankinfo.setThirdpayCode(jsonobj.optString("thirdpayCode"));
                    bankinfos.add(bankinfo);
                }
                bean.setBanklist(bankinfos);
            }

            File cacheDir = StorageUtils.getOwnCacheDirectory(
                    getApplicationContext(), "/BiYing/Cache");
//                        String imagedata=jsobj.optString("payPic", "");
//                        String image16path=cacheDir.getAbsolutePath()+"/"+imagedata.hashCode()+".biying";
//                        stringtoBitmap(imagedata, image16path);
//                        bean.setPayPic(image16path);
            String remark = jsobj.optString("remark", "");
            bean.setRemark(jsobj.optString("remark", ""));
            beans.add(bean);
        }
        if (beans.size() > 0)
            InitView1(beans, strs);
    }

    private void analysis2(JSONArray scanCodePayList,String strs)//传统扫码支付
    {
        ArrayList<OnlinefastPayBean> beans = new ArrayList<OnlinefastPayBean>();
        for (int i = 0; i < scanCodePayList.length(); i++) {
            JSONObject jsobj = scanCodePayList.optJSONObject(i);
            OnlinefastPayBean bean = new OnlinefastPayBean();
            bean.setBigPicUrl(jsobj.optString("bigPicUrl", ""));
            bean.setPayNname(jsobj.optString("payNname", ""));
            bean.setPayNo(jsobj.optString("payNo", ""));
            bean.setPayRname(jsobj.optString("payRname", ""));
            bean.setPicUrl(jsobj.optString("picUrl", ""));
            bean.setPayName(jsobj.optString("payName", ""));
            bean.setSmallPicUrl(jsobj.optString("smallPicUrl", ""));
            bean.setmaxPay(jsobj.optInt("maxPay", 100));
            bean.setMinEdu(jsobj.optInt("minPay", 1));
            bean.setPayType(jsobj.optInt("payType", 1));
            bean.setMinMaxDes(jsobj.optString("minMaxDes", ""));
            bean.setModule(jsobj.optString("module", ""));
            String remark = jsobj.optString("remark", "");
            bean.setRemark(remark);
            JSONArray json_banklist=jsobj.optJSONArray("bank");
            if(json_banklist!=null && json_banklist.length()>0)
            {
                ArrayList<OnlinefastPayBean.BankInfo> bankinfos=new ArrayList<>();
                for (int j = 0; j < json_banklist.length(); j++) {
                    JSONObject jsonobj=json_banklist.optJSONObject(j);
                    OnlinefastPayBean.BankInfo bankinfo=new OnlinefastPayBean.BankInfo();
                    bankinfo.setBankCode(jsonobj.optString("bankCode"));
                    bankinfo.setBankImages(jsonobj.optString("bankImages"));
                    bankinfo.setBankName(jsonobj.optString("bankName"));
                    bankinfo.setClientType(jsonobj.optString("clientType"));
                    bankinfo.setThirdpayCode(jsonobj.optString("thirdpayCode"));
                    bankinfos.add(bankinfo);
                }
                bean.setBanklist(bankinfos);
            }
//                        File cacheDir = StorageUtils.getOwnCacheDirectory(
//                                getApplicationContext(), "/BiYing/Cache");
//                        String imagedata=jsobj.optString("payPic", "");
//                        String image16path=cacheDir.getAbsolutePath()+"/"+imagedata.hashCode()+".biying";
//                        stringtoBitmap(imagedata, image16path);
//                        bean.setPayPic(image16path);
            beans.add(bean);
        }
        if (beans.size() > 0)
            Initview2(beans,strs);
    }

    private void analysis3(JSONArray jsonArray)//公司
    {
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsob = jsonArray.getJSONObject(i);
                    PayBankCardBean.PayBankCardBean1 bean = new PayBankCardBean.PayBankCardBean1();
                    bean.setBankAddress(jsob.optString("bankAddress", ""));
                    bean.setBankCard(jsob.optString("bankCard", ""));
                    bean.setBankCode(jsob.optString("bankCode", ""));
                    bean.setBankType(jsob.optString("bankType", ""));
                    bean.setBankUser(jsob.optString("bankUser", ""));
                    bean.setBigPic(jsob.optString("bigPic", ""));
                    bean.setMinEdu(jsob.optInt("minEdu", 100));
                    bean.setMaxPay(jsob.optInt("maxPay", 100)+"");
                    bean.setPayLink(jsob.optString("payLink", ""));
                    bean.setPayNo(jsob.optString("payNo", ""));
                    bean.setPayType(jsob.optString("payType", ""));
                    bean.setSmallPic(jsob.optString("smallPic", ""));
                    bean.setMinMaxDes(jsob.optString("minMaxDes", ""));
                    String remark = jsob.optString("remark", "");
                    bean.setRemark(jsob.optString("remark", ""));

                    beans.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            setAdapter(beans);
        }
    }

    private void GetAllPayType()
    {
        RequestParams params = new RequestParams();
        params.put("userName", ((BaseApplication) getApplication()).getBaseapplicationUsername());
        Httputils.PostWithBaseUrl(Httputils.AllPayType,params,new MyJsonHttpResponseHandler(this,false)
        {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("GetAllPayType", jsonObject.toString());
                JSONObject datas=jsonObject.optJSONObject("datas");

                JSONObject online=datas.optJSONObject("online");
                analysis0(online.optJSONArray("list"),online.optString("title"));

                JSONObject onlineSaoma=datas.optJSONObject("onlineSaoma");
                analysis1(onlineSaoma.optJSONArray("list"),onlineSaoma.optString("title"));

                JSONObject chuantongSaoma=datas.optJSONObject("chuantongSaoma");
                analysis2(chuantongSaoma.optJSONArray("list"),chuantongSaoma.optString("title"));

                JSONObject bank=datas.optJSONObject("bank");
                analysis3(bank.optJSONArray("list"));
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    private void setAdapter(ArrayList<PayBankCardBean.PayBankCardBean1> bankTransferTypeList) {
        if (adapter == null) {
            adapter = new Pay_Adapter(this, bankTransferTypeList);
            listView.setAdapter(adapter);
            Getlistheight.setListViewHeightBasedOnChildren(listView);
        } else {
            adapter.NotifyAdapter(bankTransferTypeList);
            Getlistheight.setListViewHeightBasedOnChildren(listView);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void InitView0(final ArrayList<OnlinefastPayBean> list2,String title)/////在线支付
    {
        LogTools.e("InitView0","InitView0");
        TextView title1 = FindView(R.id.title0);
        title1.setVisibility(View.VISIBLE);
        title1.setText(title);
        LinearLayout linearLayout1 = FindView(R.id.lineview0);
        for (int i = 0; i < list2.size(); i++) {
            final RelativeLayout view = (RelativeLayout) View.inflate(this, R.layout.item_temple_relation, null);
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    switch (list2.get(tag).getPayType()) {
                        case 1:
                            HttpforNoticeinbottom.hashMap2.put("onlineWx", list2.get(tag).getRemark());
                            break;
                        case 2:
                            HttpforNoticeinbottom.hashMap2.put("onlineAlipay", list2.get(tag).getRemark());
                            break;
                        default:
                            HttpforNoticeinbottom.hashMap2.put("onlineTenpay", list2.get(tag).getRemark());
                            break;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(BundleTag.Platform, list2.get(tag).getPayType() + "");
                    intent.putExtra(BundleTag.Data, new Gson().toJson(list2.get(tag)));
                    intent.putExtra(BundleTag.Jumpurl,true);
                    intent.setClass(PayActivitty.this, Pay_OnlineActivity.class);
                    startActivity(intent);
                }
            });
            RadioButton radioButton = (RadioButton) view.getChildAt(0);
            int tag = (int) view.getTag();
            radioButton.setText(list2.get(tag).getPayName());
            imageloader.displayImage(list2.get(i).getSmallPicUrl(), new ImageView(this), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view1, Bitmap bitmap) {
                    if (bitmap == null) return;
                    RadioButton radioButton = (RadioButton) view.getChildAt(0);
                    LogTools.e("bitmap", bitmap.getWidth() + " " + bitmap.getHeight());
                    Drawable drawable = new BitmapDrawable(bitmap);
                    drawable.setBounds(0, 0, ScreenUtils.getDIP2PX(PayActivitty.this, 30), ScreenUtils.getDIP2PX(PayActivitty.this, 30));//必须设置图片大小，否则不显示
                    radioButton.setCompoundDrawables(drawable, null, null, null);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

            linearLayout1.addView(view);
        }
    }

    private void InitView1(final ArrayList<OnlinefastPayBean> list2,String title)/////在线二维码支付
    {
        TextView title1 = FindView(R.id.title1);
        title1.setVisibility(View.VISIBLE);
        title1.setText(title);
        LinearLayout linearLayout1 = FindView(R.id.lineview1);
        for (int i = 0; i < list2.size(); i++) {
            final RelativeLayout view = (RelativeLayout) View.inflate(this, R.layout.item_temple_relation, null);
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    switch (list2.get(tag).getPayType()) {
                        case 1:
                            HttpforNoticeinbottom.hashMap2.put("onlineWx", list2.get(tag).getRemark());
                            break;
                        case 2:
                            HttpforNoticeinbottom.hashMap2.put("onlineAlipay", list2.get(tag).getRemark());
                            break;
                        default:
                            HttpforNoticeinbottom.hashMap2.put("onlineTenpay", list2.get(tag).getRemark());
                            break;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(BundleTag.Platform, list2.get(tag).getPayType() + "");
                    intent.putExtra(BundleTag.Data, new Gson().toJson(list2.get(tag)));
                    intent.setClass(PayActivitty.this, Pay_OnlineActivity.class);
                    startActivity(intent);
                }
            });
            RadioButton radioButton = (RadioButton) view.getChildAt(0);
            int tag = (int) view.getTag();
            radioButton.setText(list2.get(tag).getPayName());
            imageloader.displayImage(list2.get(i).getSmallPicUrl(), new ImageView(this), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view1, Bitmap bitmap) {
                    if (bitmap == null) return;
                    RadioButton radioButton = (RadioButton) view.getChildAt(0);
                    LogTools.e("bitmap", bitmap.getWidth() + " " + bitmap.getHeight());
                    Drawable drawable = new BitmapDrawable(bitmap);
                    drawable.setBounds(0, 0, ScreenUtils.getDIP2PX(PayActivitty.this, 30), ScreenUtils.getDIP2PX(PayActivitty.this, 30));//必须设置图片大小，否则不显示
                    radioButton.setCompoundDrawables(drawable, null, null, null);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });

            linearLayout1.addView(view);
        }
    }

    private void Initview2(ArrayList<OnlinefastPayBean> list2,String title)//传统二维码支付
    {
        TextView title2 = FindView(R.id.title2);
        title2.setVisibility(View.VISIBLE);
        title2.setText(title);
        for (int i = 0; i < list2.size(); i++) {
            final OnlinefastPayBean bean = list2.get(i);
            LinearLayout linearLayout2 = FindView(R.id.lineview2);
            final RelativeLayout view2 = (RelativeLayout) View.inflate(this, R.layout.item_temple_relation, null);
            view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (bean.getPayType()) {
                        case 1:
                            HttpforNoticeinbottom.hashMap2.put("traditWx", bean.getRemark());
                            break;
                        case 2:
                            HttpforNoticeinbottom.hashMap2.put("traditAlipay", bean.getRemark());
                            break;
                        default:
                            HttpforNoticeinbottom.hashMap2.put("traditTenpay", bean.getRemark());
                            break;
                    }
                    Intent intent = new Intent();
                    intent.putExtra(BundleTag.Data, new Gson().toJson(bean));
                    intent.setClass(PayActivitty.this, PayPersonalfastpay_QRCoder.class);
                    startActivity(intent);
                }
            });
            RadioButton radioButton = (RadioButton) view2.getChildAt(0);
            radioButton.setText(bean.getPayName());
            imageloader.displayImage(bean.getSmallPicUrl(), new ImageView(this), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view1, Bitmap bitmap) {
                    if (bitmap == null) return;
                    RadioButton radioButton = (RadioButton) view2.getChildAt(0);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    //(int)(bitmap.getWidth()*1.5f)

                    drawable.setBounds(0, 0, ScreenUtils.getDIP2PX(PayActivitty.this, 30), ScreenUtils.getDIP2PX(PayActivitty.this, 30));//必须设置图片大小，否则不显示
                    radioButton.setCompoundDrawables(drawable, null, null, null);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
            linearLayout2.addView(view2);
        }
    }

    public void stringtoBitmap(String string, String output) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        byte bitmapArray[] = null;

        try {
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap == null) return;
        try {
            FileOutputStream out = new FileOutputStream(new File(output));
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
