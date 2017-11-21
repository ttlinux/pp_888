package com.a8android888.bocforandroid.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.JumpActivity;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/13.
 */
public class NoticeDialog extends Dialog{

    JSONObject jsonObject;
    Context context;
    private ImageLoader mImageDownLoader;
    String cilckname="";
    String ispull="";
    public NoticeDialog(Context context,String ispull) {
        super(context);
        this.context=context;
        this.ispull=ispull;
    }

    protected NoticeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context=context;
    }

    public NoticeDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
    }

    public void setdata(JSONObject jsonObject)
    {
        this.jsonObject=jsonObject;
    }

    @Override
    public void show() {
        super.show();
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        getContext().setTheme(R.style.loading_dialog);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x11b8b8b8));

        JSONObject datas=jsonObject.optJSONObject("datas");
        int type=datas.optInt("gonggaoType", 1);
        String title=datas.optString("gonggaoName", "");
        String ganggaoContent=datas.optString("ganggaoContent","");
        String linkurl=datas.optString("linkUrl","");
        String imagesUrl=datas.optString("imagesUrl","");
        String linkGroupId=datas.optString("linkGroupId", "");
        String level=datas.optString("level", "");
        String linktype=datas.optString("linkType", "");
        String openlinktype=datas.optString("openLinkType", "");
        String typecode=datas.optString("typeCode", "");
        String BannerType=datas.optString("gonggaoType", "");
        String ArticleId=datas.optString("articleId", "");
        String ArticleType=datas.optString("articleType", "");
        String gameCode=datas.optString("gameCode", "");
        String cateCode=datas.optString("cateCode", "");
        cilckname=datas.optString("linkName", "");
        LogTools.e("typeddd", type + "");
        switch (type)
        {
            case 1:
                Inittype1(linktype, openlinktype, title,ArticleId,ArticleType,level,
                        linkurl, typecode, BannerType, linkGroupId, ganggaoContent,cateCode,gameCode);
                break;
            case 2:
                Inittype2(linktype,openlinktype,title,ArticleId,ArticleType,level,
                linkurl,typecode,BannerType,linkGroupId,imagesUrl,cateCode,gameCode);

                break;
            case 0:
                dismiss();
                break;
        }
    }
//有链接跳转的
    public void Inittype1( final String linktype,final String openlinktype,final String title,final String ArticleId,final String ArticleType,final String level,
                           final String linkurl,final  String typecode,final String BannerType,final String LinkGroupId,final String ganggaoContent,final String catecode,final String gamecode)
    {
        setContentView(R.layout.dialog_notice);
        TextView titleview=(TextView)findViewById(R.id.title);
        titleview.setText(title);
        TextView contentview=(TextView)findViewById(R.id.content);
        contentview.setText(ganggaoContent);
        Button btn_cli2=(Button)findViewById(R.id.btn_cli2);
        btn_cli2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btn_cli=(Button)findViewById(R.id.btn_cli);
        if(!linktype.equalsIgnoreCase("0")) {
            if (cilckname.equalsIgnoreCase("")) {
                btn_cli.setText("确定");
            } else {
                btn_cli.setText(cilckname);
            }
        }
        else {
            btn_cli.setText("确定");
        }
        btn_cli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(!linktype.equalsIgnoreCase("0")) {
                    modeluonclick2(linktype, openlinktype, title, ArticleId, ArticleType, level,
                            linkurl, typecode, BannerType, LinkGroupId, catecode, gamecode);
                }
                else {
                    dismiss();
                }
            }
        });

        ImageView cross=(ImageView)findViewById(R.id.cross);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        RelativeLayout contentrelative=(RelativeLayout)findViewById(R.id.contentview);
        contentrelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
    }

    public void Inittype2( final String linktype,final String openlinktype,final String title,final String ArticleId,final String ArticleType,final String level,
                           final String linkurl,final  String typecode,final String BannerType,final String LinkGroupId,final String imagesUrl,final String catecode,final String gamecode)
    {

        RelativeLayout relativeLayout=new RelativeLayout(context);
        ViewGroup.LayoutParams layoutParams= new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(layoutParams);




        ImageView view=new ImageView(context);
        int screens[]=ScreenUtils.getScreenWH((Activity)context);
        final int leftMargin=ScreenUtils.getDIP2PX(context,70);
        RelativeLayout.LayoutParams layoutParams1= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        layoutParams1.leftMargin=leftMargin;
        view.setLayoutParams(layoutParams1);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        view.setId(123999);
        view.setAdjustViewBounds(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                modeluonclick2(linktype, openlinktype, title, ArticleId, ArticleType, level,
                        linkurl, typecode, BannerType, LinkGroupId, catecode, gamecode);

            }
        });
        BaseApplication baseApplication=(BaseApplication)((Activity)context).getApplication();
        mImageDownLoader = baseApplication.getImageLoader();
        mImageDownLoader.displayImage(imagesUrl, view, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View iview, Bitmap bitmap) {
                int width = ScreenUtils.getScreenWH((Activity) context)[0];
                width=(int)(width*0.01f*100*620/420);
                double height = 0.001 * width / bitmap.getWidth() * 1000.00 * bitmap.getHeight();
                ImageView image = (ImageView) iview;
                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                layoutParams1.leftMargin = leftMargin;
                image.setLayoutParams(layoutParams1);
                image.setAdjustViewBounds(true);
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                image.setImageBitmap(ImageDownLoader.getRoundedCornerBitmap(bitmap,20,ImageDownLoader.CORNER_ALL));
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

        ImageView Closeicon=new ImageView(context);
       Drawable drawable= context.getResources().getDrawable(R.drawable.cross);
        RelativeLayout.LayoutParams Closeicon_ll= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Closeicon_ll.addRule(RelativeLayout.ALIGN_RIGHT,123999);
        Closeicon_ll.addRule(RelativeLayout.ALIGN_TOP,123999);
        Closeicon_ll.rightMargin=-1*drawable.getIntrinsicWidth()/2-10;
        Closeicon_ll.topMargin=-1*drawable.getIntrinsicHeight()/2-10;
        Closeicon.setLayoutParams(Closeicon_ll);
        Closeicon.setPadding(10,10,10,10);
        Closeicon.setImageDrawable(drawable);
        Closeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        relativeLayout.addView(view);
        relativeLayout.addView(Closeicon);
        setContentView(relativeLayout);
    }
    public void modeluonclick2(final String linktype,final String openlinktype,final String tile,final String ArticleId,final String ArticleType,final String level,
                               final String linkurl,final  String typecode,final String BannerType,final String LinkGroupId,final String catecode,final String gamecode) {

        JumpActivity.modeluonclick(context, linktype,
                openlinktype, typecode,
                level, catecode,
                gamecode, tile,
                tile, linkurl
                , ArticleType, ArticleId, ispull, "1", LinkGroupId);
    }
}
