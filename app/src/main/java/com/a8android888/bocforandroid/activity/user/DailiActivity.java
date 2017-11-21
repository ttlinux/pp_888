package com.a8android888.bocforandroid.activity.user;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.AgentBean;
import com.a8android888.bocforandroid.Bean.ModuleBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.user.pay.Pay_formActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.WheelDialog;
import com.a8android888.bocforandroid.webActivity;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kankan.wheel.widget.WheelView;

/**
 * Created by Administrator on 2017/5/25.
 */
public class DailiActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back, line, stateimg;
    LinearLayout dailiappforlayout, dailiauditlayout;
    TextView title, dailiname, email, tuiguangaddr, dailiauditname, dailiaudittype, auditemail, imgtitle,hkfs;
    RadioButton /*dailitype, dailitypea*/ textView2,textView3;
    EditText edit;
    Button comit, changeinto;
    RelativeLayout addresslayout,accountlayoutX,accountlayout4,accountlayout5;
    ArrayList<AgentBean> gameBeanList;
    String url = "";
     ArrayList<PayTypeBean> PayTypeBeans=new ArrayList<PayTypeBean>();
     String paytype[];//银行支付列表方式
    int patypeSelectIndex=-1;//银行支付列表方式index
    View remarkline,applyline,accountline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daililayout);
        back = (ImageView) FindView(R.id.back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title = (TextView) FindView(R.id.title);
        title.setText("代理信息");
        appforview();
        auditview();
        module();
    }

    //代理信息
    private void auditview() {
//        textView2 = FindView(R.id.textView2);
        dailiauditlayout = (LinearLayout) FindView(R.id.dailiauditlayout);
        dailiauditlayout.setVisibility(View.GONE);
        stateimg = (ImageView) FindView(R.id.stateimg);
        imgtitle = (TextView) FindView(R.id.imgtitle);
        tuiguangaddr = (TextView) FindView(R.id.tuiguangaddr);
        dailiauditname = (TextView) FindView(R.id.dailiauditname);
        dailiaudittype = (TextView) FindView(R.id.dailiaudittype);
        auditemail = (TextView) FindView(R.id.auditemail);
        tuiguangaddr = (TextView) FindView(R.id.tuiguangaddr);
        addresslayout = (RelativeLayout) FindView(R.id.addresslayout);
        line = (ImageView) FindView(R.id.line);
        textView2 = (RadioButton) FindView(R.id.textView2);
        changeinto = (Button) FindView(R.id.changeinto);
        changeinto.setOnClickListener(this);
        accountlayout4=FindView(R.id.accountlayout4);
        accountlayout5=FindView(R.id.accountlayout5);
        accountlayoutX=FindView(R.id.accountlayoutX);
        remarkline=FindView(R.id.remarkline);
        applyline=FindView(R.id.applyline);
        accountline=FindView(R.id.accountline);
    }

    //申请代理
    private void appforview() {
        dailiappforlayout = (LinearLayout) FindView(R.id.dailiappforlayout);
        dailiname = (TextView) FindView(R.id.dailiname);
        dailiname.setText(((BaseApplication) this.getApplication()).getUsername());
        email = (TextView) FindView(R.id.email);
//        dailitype = (RadioButton) FindView(R.id.dailitype);
//        dailitypea = (RadioButton) FindView(R.id.dailitypea);
        edit = (EditText) FindView(R.id.edit);
        hkfs= (TextView) FindView(R.id.hkfs);
        hkfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBankPayType(true);
            }
        });
        textView3 = (RadioButton) FindView(R.id.textView);
        comit = (Button) FindView(R.id.comit);
        comit.setOnClickListener(this);

    }
    private void getBankPayType(final boolean show)
    {
        if(PayTypeBeans.size()>0 || paytype!=null)
        {
            if(show)
                ShowWheel();
            return;
        }
    }
    public void ShowWheel() {
        if (hkfs.getText().toString().trim().equalsIgnoreCase("")) {
            hkfs.setText(PayTypeBeans.get(0).getCodeShowName());
        }
        WheelDialog dialog = new WheelDialog(DailiActivity.this);
        dialog.setListener(new WheelDialog.OnChangeListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                LogTools.e("oldValue", oldValue + " " + newValue);
                patypeSelectIndex = newValue;
                hkfs.setText(PayTypeBeans.get(newValue).getCodeShowName());
            }
        });
        if(patypeSelectIndex<0)
        {
            patypeSelectIndex=0;
        }
        dialog.setStrs(paytype);
        dialog.show();
    }
    private void module() {//提交成功的状态
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", ((BaseApplication) this.getApplication()).getUsername());
        Httputils.PostWithBaseUrl(Httputils.selectAgentInfo, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("ddddddaaaavvv", jsonObject.toString());
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                JSONObject jsona = jsonObject.optJSONObject("datas");
                gameBeanList = new ArrayList<AgentBean>();
                JSONObject json = jsona.optJSONObject("agent");
                final AgentBean gameBean1 = new AgentBean();
                gameBean1.setAgentReportUrl(json.optString("agentReportUrl", ""));
                gameBean1.setName(json.optString("userName", ""));
                gameBean1.setAgentMail(json.optString("agentMail", ""));
                gameBean1.setStatus(json.optString("status", ""));
                gameBean1.setAgentType(json.optString("agentType", ""));
                gameBean1.setAgentUrl(json.optString("agentUrl", ""));
                gameBean1.setContent(json.optString("content", ""));
                gameBean1.setRemark(json.optString("remark", ""));

                gameBeanList.add(gameBean1);
                url = json.optString("agentUrl", "");
                JSONArray jsonArray = jsona.optJSONArray("agentType");
                if (jsona.optString("errorCode").equalsIgnoreCase("200032")) {
                    dailiappforlayout.setVisibility(View.VISIBLE);
                    dailiauditlayout.setVisibility(View.GONE);
                    HttpforNoticeinbottom.GetMainPageData(textView3,"applyAgent",DailiActivity.this);
                    paytype = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsob = jsonArray.optJSONObject(i);
                        PayTypeBean bean = new PayTypeBean();
                        bean.setCodeShowName(jsob.optString("typeName", ""));
                        paytype[i] = bean.getCodeShowName();
                        bean.setCodeValue(jsob.optString("agentId", ""));
                        PayTypeBeans.add(bean);
                    }
                } else {
                    dailiappforlayout.setVisibility(View.GONE);
                    dailiauditlayout.setVisibility(View.VISIBLE);
                    JSONObject jsonb = json.optJSONObject("type");
                    if (gameBeanList.get(0).getStatus().equalsIgnoreCase("-1")) {
                        stateimg.setBackgroundDrawable(DailiActivity.this.getResources().getDrawable(R.drawable.audit));
                        imgtitle.setText(json.optString("agentStatusDes", ""));
                        addresslayout.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
//                        if (gameBeanList.get(0).getAgentType().equalsIgnoreCase("1")) {
                            dailiaudittype.setText(jsonb.optString("typeName",""));
//                        }
//                        if (gameBeanList.get(0).getAgentType().equalsIgnoreCase("2")) {
//                            dailiaudittype.setText("退水代理");
//                        }
                        auditemail.setText(gameBeanList.get(0).getAgentMail());
                        applyline.setVisibility(View.VISIBLE);
                        accountlayout4.setVisibility(View.VISIBLE);
                        TextView applyforvalue=FindView(R.id.applyforvalue);
                        accountline.setVisibility(View.GONE);
                        accountlayoutX.setVisibility(View.GONE);
                        applyforvalue.setText(gameBeanList.get(0).getContent());
                        textView2.setVisibility(View.GONE);
//                        HttpforNoticeinbottom.GetMainPageData(textView2, "applyAgent", DailiActivity.this);
                    }
                    if (gameBeanList.get(0).getStatus().equalsIgnoreCase("-2")) {
                        stateimg.setBackgroundDrawable(DailiActivity.this.getResources().getDrawable(R.drawable.failure));
                        imgtitle.setText(json.optString("agentStatusDes", ""));
                        addresslayout.setVisibility(View.GONE);
                        line.setVisibility(View.GONE);
                        dailiauditname.setText(gameBeanList.get(0).getName());
                        accountline.setVisibility(View.GONE);
                        accountlayoutX.setVisibility(View.GONE);
                        applyline.setVisibility(View.VISIBLE);
//                        if (gameBeanList.get(0).getAgentType().equalsIgnoreCase("1")) {
//                            dailiaudittype.setText("退佣代理");
//                        }
//                        if (gameBeanList.get(0).getAgentType().equalsIgnoreCase("2")) {
//                            dailiaudittype.setText("退水代理");
//                        }
                        accountlayout4.setVisibility(View.VISIBLE);
                        accountlayout5.setVisibility(View.VISIBLE);
                        remarkline.setVisibility(View.VISIBLE);
                        TextView applyforvalue=FindView(R.id.applyforvalue);
                        TextView remarkvalue=FindView(R.id.remarkvalue);
                        remarkvalue.setText(gameBeanList.get(0).getRemark());
                        applyforvalue.setText(gameBeanList.get(0).getContent());

                        dailiaudittype.setText(jsonb.optString("typeName", ""));
                        auditemail.setText(gameBeanList.get(0).getAgentMail());

                        textView2.setVisibility(View.GONE);
//                        HttpforNoticeinbottom.GetMainPageData(textView2, "applyFail", DailiActivity.this);

                    }
                    if (gameBeanList.get(0).getStatus().equalsIgnoreCase("1") || gameBeanList.get(0).getStatus().equalsIgnoreCase("0")) {
                        if(gameBeanList.get(0).getStatus().equalsIgnoreCase("1")) {
                            stateimg.setBackgroundDrawable(DailiActivity.this.getResources().getDrawable(R.drawable.successful));
                            //成功的话多一个代理报表
                            RelativeLayout relativeLayout=FindView(R.id.accountlayout6);
                            View daililist=FindView(R.id.daililist);
                            daililist.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.VISIBLE);
                            relativeLayout.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DailiActivity.this, webActivity.class);
                                    intent.putExtra(BundleTag.title, "代理报表");
                                    intent.putExtra(BundleTag.IntentTag, true);
                                    intent.putExtra(BundleTag.URL, gameBean1.getAgentReportUrl());
                                    startActivity(intent);

                                }
                            });
//                            daililist
//                                    accountlayout6
                        }
                        if(gameBeanList.get(0).getStatus().equalsIgnoreCase("0"))
                            stateimg.setBackgroundDrawable(DailiActivity.this.getResources().getDrawable(R.drawable.dongjie));
                        imgtitle.setText(json.optString("agentStatusDes",""));
                        addresslayout.setVisibility(View.VISIBLE);
                        line.setVisibility(View.VISIBLE);
                        dailiauditname.setText(gameBeanList.get(0).getName());
                        dailiaudittype.setText(jsonb.optString("typeName", ""));
                        auditemail.setText(gameBeanList.get(0).getAgentMail());
                        tuiguangaddr.setText(gameBeanList.get(0).getAgentUrl());
                        accountlayout5.setVisibility(View.VISIBLE);
                        remarkline.setVisibility(View.VISIBLE);
                        TextView remarkvalue=FindView(R.id.remarkvalue);
                        remarkvalue.setText(gameBeanList.get(0).getRemark());
                        textView2.setVisibility(View.GONE);
//                        HttpforNoticeinbottom.GetMainPageData(textView2, "applySuccess", DailiActivity.this);
                    }
                }

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }

    private void comitdata() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("userName", ((BaseApplication) this.getApplication()).getUsername());
//        if (dailitype.isChecked()) {
//            requestParams.put("agentType", "1");
//        }
//        if (dailitypea.isChecked()) {
            requestParams.put("agentType", PayTypeBeans.get(patypeSelectIndex).getCodeValue());
//        }
        requestParams.put("content", edit.getText().toString().trim());
        requestParams.put("agentMail", email.getText().toString().trim());

        Httputils.PostWithBaseUrl(Httputils.saveAgentApply, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                LogTools.ee("ddddddaaaa", jsonObject.toString());
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                ToastUtil.showMessage(DailiActivity.this, jsonObject.optString("msg"));
                finish();
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                throwable.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.comit:
                if(patypeSelectIndex<0) {
                    ToastUtil.showMessage(this, "请选择代理类型");
                    return;
                }
                if (email.getText().toString().trim().length()<1) {
                    ToastUtil.showMessage(this, "请输入您的电子邮箱");
                    return;
                }
                if (edit.getText().toString().trim().length() < 1) {
                    ToastUtil.showMessage(this, "请输入您的申请理由");
                    return;
                }
                if (!isEmail(email.getText().toString().trim())) {
                    ToastUtil.showMessage(this, "电子邮箱不正确");
                    return;
                }
                comit.setEnabled(false);
                comitdata();
                break;
            case R.id.changeinto:
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", url);
// 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
//从剪贴板中获取ClipData数据：
//        ClipboardManager.getPrimaryClip();
                ToastUtil.showMessage(this,"已复制到粘贴板");
                break;
        }
    }

    public boolean isEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        Pattern p = Pattern.compile(str);

        Matcher m = p.matcher(email);

        return m.matches();

    }
    class PayTypeBean
    {
        public String getCodeShowName() {
            return codeShowName;
        }

        public void setCodeShowName(String codeShowName) {
            this.codeShowName = codeShowName;
        }

        public String getCodeValue() {
            return codeValue;
        }

        public void setCodeValue(String codeValue) {
            this.codeValue = codeValue;
        }

        private String codeShowName;
        private String codeValue;
    }
}
