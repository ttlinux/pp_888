package com.a8android888.bocforandroid.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.ModuleBean;
import com.a8android888.bocforandroid.MainActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.WelcomeActivity;
import com.a8android888.bocforandroid.activity.main.ActicleListActivity;
import com.a8android888.bocforandroid.activity.main.BBNActivity;
import com.a8android888.bocforandroid.activity.main.CardActivity;
import com.a8android888.bocforandroid.activity.main.Electronic_gamesActivity;
import com.a8android888.bocforandroid.activity.main.JumpActivity;
import com.a8android888.bocforandroid.activity.main.Real_videoActivity;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_eventsActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragement;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MD5Util;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.a8android888.bocforandroid.webActivity;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import static android.view.View.GONE;

/**
 * Created by Administrator on 2017/3/30.
 */
public class RegActivity extends BaseActivity implements View.OnClickListener{
    private EditText name,password,code;
    TextView coded,forgetpassw,registered,title,regtext;
    String staterecord;
    public static long autime=0;
    Button comit;
    ImageView back;
    ArrayList<EditText> editTexts=new ArrayList<EditText>();
    LinearLayout mainview;
    View commit;
    LinearLayout temp3;
    ArrayList< ArrayList<ModuleBean>> gameList=new ArrayList< ArrayList<ModuleBean>>();
    ImageDownLoader mImageDownLoader2;
    private ImageLoader mImageDownLoader;
    ArrayList<ModuleBean> gameBeanList;
    CheckBox checkBox;
    PublicDialog publicDialog;
    RadioButton registernotice;
    SharedPreferences usernamelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg);
        mImageDownLoader = ((BaseApplication)this.getApplication())
                .getImageLoader();
        mImageDownLoader2=new ImageDownLoader(this);
        initview();

    }
    private void initview(){
        usernamelist=getSharedPreferences(BundleTag.UserNameList, Activity.MODE_PRIVATE);
        temp3=(LinearLayout)FindView(R.id.temp3);
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        title=(TextView)FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.userreg));
        comit=FindView(R.id.comit);
        comit.setOnClickListener(this);
        checkBox=FindView(R.id.checkBox);
        regtext=FindView(R.id.regtext);

        regtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegActivity.this,regwebActivity.class));
            }
        });
        registernotice=FindView(R.id.registernotice);
        HttpforNoticeinbottom.GetMainPageData(registernotice,"register",this);


        mainview=FindView(R.id.mainview);
        for (int i = 0; i < 14; i++) {
            if(i%2==0)
            {
                RelativeLayout relayout=(RelativeLayout)mainview.getChildAt(i);
                editTexts.add((EditText)relayout.getChildAt(2));
            }
        }
        module();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comit:
                Register();
                break;
            case R.id.back:
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
        }

    }

    public void Register()
    {
        if(!checkBox.isChecked())
        {
            ToastUtil.showMessage(this,getString(R.string.confirmdetail));
            return;
        }

        for (int i = 0; i < editTexts.size(); i++) {
            String str=editTexts.get(i).getText().toString();
            switch (i)
            {
                case 0:
                    if(str.length()<4 || str.length()>12) {
                        ToastUtil.showMessage(RegActivity.this, "请输入4-12位数字/字母作为帐号");
                        return;
                    }
                    else
                        break;
                case 1:
                    if(str.length()<6 || str.length()>12) {
                        ToastUtil.showMessage(RegActivity.this, "请输入6-12位数字/字母作为密码");
                        return;
                    }
                    else
                        break;
                case 2:
                    if(str.length()<6 && str.length()>12)
                    {
                        ToastUtil.showMessage(RegActivity.this, "请输入6-12位数字/字母作为密码");
                        return;
                    }
                    if(!str.equalsIgnoreCase(editTexts.get(1).getText().toString())) {
                        ToastUtil.showMessage(RegActivity.this, "两次输入的密码不一致");
                        return;
                    }
                case 3:
                    if(str.length()<1) {
                        ToastUtil.showMessage(RegActivity.this, "请输入真实姓名");
                        return;
                    }
                    else
                        break;
                case 4:
                    if(str.length()<1 || str.length()>15) {
                        ToastUtil.showMessage(RegActivity.this, "请输入正确的手机号码");
                        return;
                    }
                    else
                        break;
//                case 5:
//                    if(str.length()<6 || str.length()>12)
//                        ToastUtil.showMessage(RegActivity.this,"请输入QQ号码");
//                    return;
                case 6:
                    if(str.length()<4) {
                        ToastUtil.showMessage(RegActivity.this, "请输入4位数字作为资金密码");
                        return;
                    }
            }
        }
//        if(editTexts.get(editTexts.size()-1).getText().toString().trim().length()<4)
//        {
//            ToastUtil.showMessage(this,getString(R.string.toast3));
//            return;
//        }
        comit.setEnabled(false);
        RequestParams params=new RequestParams();
        params.put("userName",editTexts.get(0).getText().toString().trim());
        params.put("password1",editTexts.get(1).getText().toString().trim());
        params.put("password2",editTexts.get(2).getText().toString().trim());
//        params.put("userAgent",editTexts.get(3).getText().toString());
        params.put("realName",editTexts.get(3).getText().toString().trim());
        params.put("userMobile",editTexts.get(4).getText().toString().trim());
        params.put("userQq", editTexts.get(5).getText().toString().trim());
        params.put("withdrawPwd", editTexts.get(6).getText().toString().trim());

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(editTexts.get(0).getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(editTexts.get(1).getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(editTexts.get(2).getText().toString().trim());
        stringBuilder.append("|");
//        stringBuilder.append("");//userAgent
//        stringBuilder.append("|");
//        stringBuilder.append(editTexts.get(3).getText().toString().trim());
//        stringBuilder.append("|");
        stringBuilder.append(editTexts.get(4).getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(editTexts.get(5).getText().toString().trim());
        stringBuilder.append("|");
        stringBuilder.append(editTexts.get(6).getText().toString().trim());
        params.put("signature", MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));

        Httputils.PostWithBaseUrl(Httputils.register, params, new MyJsonHttpResponseHandler(this, true) {

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);

            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                ToastUtil.showMessage(RegActivity.this, jsonObject.optString("msg", ""));
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    authorize(editTexts.get(0).getText().toString().trim(),editTexts.get(1).getText().toString().trim());
                }
                else
                {
                    comit.setEnabled(true);
                }
            }
        });
    }
    private  void module(){
        RequestParams requestParams=new RequestParams();
        requestParams.put("regionCode", "register");
        Httputils.PostWithBaseUrl(Httputils.modulelist, requestParams, new MyJsonHttpResponseHandler(this,false) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("ddddddaaaa", jsonObject.toString());
//                JSONObject json = jsonObject.optJSONObject("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("datas");
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonda = jsonArray.getJSONObject(i);

                        gameBeanList = new ArrayList<ModuleBean>();
                        JSONArray jsonArray5 = jsonda.getJSONArray("article");
                        for (int k = 0; k < jsonArray5.length(); k++) {
                            JSONObject jsond = jsonArray5.getJSONObject(k);
                            ModuleBean gameBean1 = new ModuleBean();

                            gameBean1.setModuleId(jsonda.optString("moduleId", ""));
                            gameBean1.setRemark(jsonda.optString("remark", ""));
                            gameBean1.setModuleType(jsonda.optString("moduleType", ""));
                            gameBean1.setModuleName(jsonda.optString("moduleName", ""));
                            gameBean1.setModuleCode(jsonda.optString("moduleCode", ""));

                            gameBean1.setBigBackgroundPic(jsond.optString("backgroundPicUrl", ""));
                            gameBean1.setCreateTime(jsond.optString("createTime", ""));
                            gameBean1.setArticleBigImages(jsond.optString("articleBigImages", ""));
                            gameBean1.setArticleCode(jsond.optString("articleCode", ""));
                            gameBean1.setLinkType(jsond.optString("linkType", ""));
                            gameBean1.setLinkUrl(jsond.optString("linkUrl", ""));
                            gameBean1.setArticleSmallImages(jsond.optString("articleSmallImages", ""));
                            gameBean1.setArticleBigHeight(jsond.optString("articleBigHeight", ""));
                            gameBean1.setArticleSmallHeight(jsond.optString("articleSmallHeight", ""));
                            gameBean1.setArticleId(jsond.optString("articleId", ""));
                            gameBean1.setArticleName(jsond.optString("articleName", ""));
                            gameBean1.setArticleBigWidth(jsond.optString("articleBigWidth", ""));
                            gameBean1.setArticleTitle(jsond.optString("articleTitle", ""));
                            gameBean1.setArticleSmallWidth(jsond.optString("articleSmallWidth", ""));
                            gameBean1.setArticleSubTitle(jsond.optString("articleSubTitle", ""));
                            gameBean1.setArticleContent(jsond.optString("articleContent", ""));
                            gameBean1.setShowType(jsond.optString("showType", ""));
                            gameBean1.setArticleType(jsond.optString("articleType", ""));
//                            gameBean1.setInnerLink(jsond.optString("innerLink", ""));
//                            if(!gameBean1.getInnerLink().equalsIgnoreCase("")){
//                                JSONObject ajson = new JSONObject(jsond.optString("innerLink", ""));
                                gameBean1.setTypeCode(jsond.optString("typeCode", ""));
                                gameBean1.setGameCode(jsond.optString("gameCode", ""));
                                gameBean1.setCateCode(jsond.optString("cateCode", ""));
                                LogTools.e("dianji", jsond.optString("typeCode", ""));

//                            }
                            gameBeanList.add(gameBean1);

                        }
                        gameList.add(gameBeanList);
                    }
                    moduleview();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }

    private void moduleview(){
        for (int i = 0; i <gameList.size() ; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            int itemcount=gameList.get(i).size();
            for (int k = 0; k <gameList.get(i).size() ; k++) {
                if(gameList.get(i).get(k).getModuleType().equalsIgnoreCase("1")){
                    View popupView = View.inflate(this, R.layout.module1_layout, null);
                    popupView.setTag(k);
                    ImageView img=(ImageView)popupView.findViewById(R.id.img);
                    TextView title=(TextView)popupView.findViewById(R.id.title);
                    TextView contact=(TextView)popupView.findViewById(R.id.contact);
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getArticleSmallImages(), img);
                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setText(gameList.get(i).get(k).getArticleSubTitle());
                    final   int  indexa=i;
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    linearLayout.addView(popupView);
                    ImageView line = new ImageView(this);
                    line.setBackgroundColor(getResources().getColor(R.color.line));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    linearLayout.addView(line);
                }

                if(gameList.get(i).get(k).getModuleType().equalsIgnoreCase("2")){
                    View popupView = View.inflate(this, R.layout.module2_layout, null);
                    popupView.setTag(k);
                    ImageView img=(ImageView)popupView.findViewById(R.id.img);
                    TextView title=(TextView)popupView.findViewById(R.id.title);
                    TextView contact=(TextView)popupView.findViewById(R.id.contact);
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getArticleSmallImages(), img);
                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setText(gameList.get(i).get(k).getArticleSubTitle());
                    final   int  indexa=i;
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    linearLayout.addView(popupView);
                    ImageView line = new ImageView(this);
                    line.setBackgroundColor(getResources().getColor(R.color.line));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    linearLayout.addView(line);
                }
                if(gameList.get(i).get(k).getModuleType().equalsIgnoreCase("3")){
                    View popupView = View.inflate(this, R.layout.module3_layout, null);
                    popupView.setTag(k);
                    ImageView img=(ImageView)popupView.findViewById(R.id.img);
                    TextView title=(TextView)popupView.findViewById(R.id.title);
                    TextView contact=(TextView)popupView.findViewById(R.id.contact);
//                    title.setText( gameList.get(i).get(k).getArticleName());
                    contact.setText(gameList.get(i).get(k).getArticleTitle());
                    mImageDownLoader.displayImage(gameList.get(i).get(k).getArticleSmallImages(), img);
                    final   int  indexa=i;
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    linearLayout.addView(popupView);
                    ImageView line = new ImageView(this);
                    line.setBackgroundColor(getResources().getColor(R.color.line));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    linearLayout.addView(line);
                }
                if(gameList.get(i).get(k).getModuleType().equalsIgnoreCase("4")){
                    View popupView = View.inflate(this, R.layout.module5_layout, null);
                    popupView.setTag(k);
                    ImageView img=(ImageView)popupView.findViewById(R.id.img);
                    TextView title=(TextView)popupView.findViewById(R.id.title);
                    TextView contact=(TextView)popupView.findViewById(R.id.contact);
                    title.setText(gameList.get(i).get(k).getArticleTitle());
                    contact.setTag(k);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    //                    mImageDownLoader.displayImage(gameList.get(i).get(k).getArticleSmallImages(), img);
                    mImageDownLoader2.showImage(this,gameList.get(i).get(k).getArticleSmallImages(),img,0);
                    final   int  indexa=i;
                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);
                }
                if(gameList.get(i).get(k).getModuleType().equalsIgnoreCase("5")){
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams1.leftMargin = 10;
                    layoutParams1.rightMargin = 10;
                    layoutParams1.topMargin = 5;
                    layoutParams1.bottomMargin = 5;
                    View popupView = View.inflate(this, R.layout.module4_layout, null);
                    popupView.setLayoutParams(layoutParams1);
                    popupView.setTag(k);
                    ImageView img=(ImageView)popupView.findViewById(R.id.img);
                    TextView title=(TextView)popupView.findViewById(R.id.title);
                    title.setVisibility(GONE);
                    TextView contact=(TextView)popupView.findViewById(R.id.contact);
                    contact.setVisibility(GONE);
//                    mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView, 0);
                    ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                    mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(k).getBigBackgroundPic(), popupView);
                    mImageDownLoader2.showImage(this, gameList.get(i).get(k).getArticleSmallImages(), img, 0);
                    final   int  indexa=i;
                    popupView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int tag = Integer.valueOf(v.getTag() + "");
                            modeluonclick(indexa, tag);
                        }
                    });
                    linearLayout.addView(popupView);

                }
                if(gameList.get(i).get(k).getModuleType().equalsIgnoreCase("6")){
                    LogTools.e("hhhhhkaaadd", itemcount + "");
                    LinearLayout hor = new LinearLayout(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    hor.setLayoutParams(params);
                    hor.setOrientation(LinearLayout.HORIZONTAL);
                    int tempcount = itemcount - k * 3> 3 ? (3 * (k + 1)) : itemcount;
                    for (int j = k * 3; j < tempcount; j++) {
                        final int iii=j;
                        View popupView = View.inflate(this, R.layout.real_video_item, null);
                        int width=  ScreenUtils.getScreenWH(this)[0]/3;
                        LinearLayout.LayoutParams params2 =new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                        popupView.setLayoutParams(params2);
                        popupView.setTag(iii);
                        LogTools.e("hhhhhkaaavv", gameList.get(i).get(j).getArticleTitle() + "");
                        TextView videoname = (TextView) popupView.findViewById(R.id.videoname);
                        ImageView videoimg = (ImageView) popupView.findViewById(R.id.videoimg);
//                        mImageDownLoader2.showImagelayouot(this, gameList.get(i).get(j).getBigBackgroundPic(), popupView, 0);
                        ImageView srcid = (ImageView) popupView.findViewById(R.id.srcid);
                        mImageDownLoader2.SetSpecialView(this, gameList.get(i).get(j).getBigBackgroundPic(), popupView);
                        mImageDownLoader.displayImage(gameList.get(i).get(j).getArticleSmallImages(), videoimg);
                        videoname.setText(gameList.get(i).get(j).getArticleTitle());
                        final   int  indexa=i ;
                        popupView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final int tag = Integer.valueOf(v.getTag() + "");
                                modeluonclick(indexa, tag);
                            }
                        });
                        hor.addView(popupView);
                        ImageView line = new ImageView(this);
                        line.setBackgroundColor(getResources().getColor(R.color.gray1));
                        line.setLayoutParams(new ViewGroup.LayoutParams( 1,ViewGroup.LayoutParams.FILL_PARENT));
                        hor.addView(line);
                    }
                    linearLayout.addView(hor);
                    ImageView line = new ImageView(this);
                    line.setBackgroundColor(getResources().getColor(R.color.gray1));
                    line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                    linearLayout.addView(line);
                }

            }
            temp3.addView(linearLayout);
            ImageView line = new ImageView(this);
            line.setBackgroundColor(getResources().getColor(R.color.gray1));
            line.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT,15));
            temp3.addView(line);
        }
    }
    public void modeluonclick(int indexa,int tag){
        JumpActivity.modeluonclick(RegActivity.this, gameList.get(indexa).get(tag).getLinkType(),
                gameList.get(indexa).get(tag).getOpenLinkType(), gameList.get(indexa).get(tag).getTypeCode(),
                gameList.get(indexa).get(tag).getLevel(), gameList.get(indexa).get(tag).getCateCode(),
                gameList.get(indexa).get(tag).getGameCode(), gameList.get(indexa).get(tag).getArticleName(),
                gameList.get(indexa).get(tag).getArticleTitle(), gameList.get(indexa).get(tag).getLinkUrl()
                , gameList.get(indexa).get(tag).getArticleType(), gameList.get(indexa).get(tag).getArticleId(), "nopull", "2", gameList.get(indexa).get(tag).getArticleId());
    }
    private void authorize(final String username,final String password)
    {
        ToastUtil.showMessage(this,"正在登陆");
        if(publicDialog==null)
        {
            publicDialog=new PublicDialog(this);
        }
        publicDialog.show();
        String keys[]=getResources().getStringArray(R.array.authorize);
        staterecord= UUID.randomUUID().toString();
        LogTools.e("staterecord", staterecord);
        RequestParams params=new RequestParams();
        params.put("userName",username);
        params.put("clientKey",Httputils.androidkey/*unchange(keys[0]) keys[0]*/);
        params.put("clientSecret",Httputils.androidsecret);
        params.put("deviceId", android.os.Build.SERIAL);
        try {
            params.put("state",staterecord);//des加密就用这个 DesUtil.encrypt(staterecord,keys[2])
        } catch (Exception e) {
            e.printStackTrace();
        }

        Httputils.PostWithBaseUrl(Httputils.authorize, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                if (publicDialog != null && !isDestroyed()) {
                    publicDialog.dismiss();
                }
                LogTools.e("Throwable", s);
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject tempjsobject = jsonObject.optJSONObject("datas");
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000") && staterecord.equalsIgnoreCase(tempjsobject.optString("state", ""))) {
                    autime = Long.valueOf(tempjsobject.optString("expires_in", ""));
                    String access_token = tempjsobject.optString("access_token", "");
                    SharedPreferences sharedPreferences = ((BaseApplication) getApplication()).getSharedPreferences();
                    sharedPreferences.edit().putString(BundleTag.Access_token, access_token).commit();
                    Login(username ,password,access_token);
                } else {
                    ToastUtil.showMessage(RegActivity.this, jsonObject.optString("msg", ""));
                }
            }
        });
    }


    private void Login(final String username,String password,String access_token)
    {
        RequestParams params=new RequestParams();
        params.put("accessToken",access_token);
        params.put("password",password);
        params.put("userName",username);
        Httputils.PostWithBaseUrl(Httputils.login,params,new MyJsonHttpResponseHandler(this,true){
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
                if(publicDialog!=null && !isDestroyed())
                {
                    publicDialog.dismiss();
                }
                LogTools.e("onFailureOfMe","fail");
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject", jsonObject.toString());
                JSONObject datas=jsonObject.optJSONObject("datas");
                if(publicDialog!=null && !isDestroyed())
                {
                    publicDialog.dismiss();
                }
                ToastUtil.showMessage(RegActivity.this,jsonObject.optString("msg",""));
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    SharedPreferences sharedPreferences=((BaseApplication)getApplication()).getSharedPreferences();
                    ((BaseApplication)getApplication()).setUsername(datas.optString("userName", ""));
                    sharedPreferences.edit()
//                            .putString(BundleTag.level, datas.optJSONObject("typeDetail").toString())
                            .putString(BundleTag.UserInfo, datas.toString())
//                    .putString(BundleTag.BankList,datas.optJSONArray("bankList").toString())
                            .commit();

                    String namelist=usernamelist.getString(BundleTag.UserNameList,"");
                    if(!namelist.contains(username))
                    {
                        String middle=namelist.equalsIgnoreCase("")?"":"|";
                        String username=namelist+middle+name.getText().toString().trim();
                        if(username.split("\\|").length>4)
                        {
                            username=username.substring(username.indexOf("|", 2)+1,username.length());
                        }
                        usernamelist.edit().putString(BundleTag.UserNameList,username).commit();
                    }

                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setClass(context, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    if (publicDialog != null && !isDestroyed()) {
                        publicDialog.dismiss();
                    }
//                    authorize();
//                    ToastUtil.showMessage(LoginActivity.this, jsonObject.optString("msg", ""));
                }
            }
        });
    }

    private static String unchange(String str)
    {
        byte[] bstr=new byte[str.getBytes().length];
        for (int i = 0; i < bstr.length; i++) {
            bstr[i]=(byte) (str.getBytes()[i]-1);
        }
        return new String(bstr);
    }
}
