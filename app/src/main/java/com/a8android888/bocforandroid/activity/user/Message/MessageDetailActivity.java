package com.a8android888.bocforandroid.activity.user.Message;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.view.BubbleView;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;

/**
 * Created by Administrator on 2017/3/30.
 */
public class MessageDetailActivity extends BaseActivity{


    String jsonobject;
    LinearLayout mainview;
    TextView title,content;
    PublicDialog dialog;
    int position;
    ImageView back;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);

        TextView Messagetitle=FindView(R.id.title);
        Messagetitle.setText("消息详情");
        back=(ImageView)findViewById(R.id.back);
        jsonobject=getIntent().getStringExtra(BundleTag.JsonObject);
        mainview= FindView(R.id.mainview);

        View view=View.inflate(this,R.layout.bubbleview_layout,null);
//        BubbleView bubbleView = new BubbleView(this);
//        bubbleView.setOrientation(BubbleView.VERTICAL);
//        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.leftMargin=ScreenUtils.getDIP2PX(this,20);
//        params.rightMargin=ScreenUtils.getDIP2PX(this,20);
//        params.topMargin=ScreenUtils.getDIP2PX(this,20);
//        bubbleView.setLayoutParams(params);
//        bubbleView.SetLayoutPosition(BubbleView.Side_Bot, BubbleView.Style_Middle);
//        bubbleView.setBotWidth(50);
//        bubbleView.setBotHeight(50);
//
//        bubbleView.SetViewStyle(true);
//        bubbleView.SetViewColor(Color.WHITE);
//        bubbleView.addView(view);

//        title=(TextView)bubbleView.findViewById(R.id.messagetitle);
//        content=(TextView)bubbleView.findViewById(R.id.messagecontent);
                title=(TextView)view.findViewById(R.id.messagetitle);
                content=(TextView)view.findViewById(R.id.messagecontent);

        try {
            JSONObject jsonObject=new JSONObject(jsonobject);
            LogTools.e("jsonObject", jsonObject.toString());
            title.setText(jsonObject.optString("msgTitle", ""));
            Drawable drawable = getResources().getDrawable(R.drawable.circle_green);
//            title.setCompoundDrawablesRelativeWithIntrinsicBounds(getResources().getDrawable(R.drawable.circle_green), null, null, null);
            drawable.setBounds(0, 0, ScreenUtils.getDIP2PX(this, 10), ScreenUtils.getDIP2PX(this, 10));//必须设置图片大小，否则不显示
            title.setCompoundDrawables(drawable, null, null, null);
            title.setCompoundDrawablePadding(ScreenUtils.getDIP2PX(this, 7));
            content.setText(jsonObject.optString("msgContent",""));
            position=getIntent().getIntExtra(BundleTag.Position,0);
            if(getIntent().getBooleanExtra(BundleTag.ChangeState,false) && getIntent().getBooleanExtra(BundleTag.type,false))
                PostReadState2(jsonObject.optString("id", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  mainview.addView(bubbleView);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin=ScreenUtils.getDIP2PX(this,20);
        layoutParams.leftMargin=ScreenUtils.getDIP2PX(this,10);
        layoutParams.rightMargin=ScreenUtils.getDIP2PX(this,10);
        mainview.addView(view,layoutParams);
    }


//    private void PostReadState(final String id)
//    {
//        if(dialog==null )
//        {
//            dialog=new PublicDialog(this);
//        }
//        dialog.show();
//        RequestParams requestParams=new RequestParams();
//        requestParams.put("userName", ((BaseApplication)this.getApplication()).getUsername());
//        requestParams.put("id", id);
//        Httputils.PostWithBaseUrl(Httputils.ReadMessages,requestParams,new MyJsonHttpResponseHandler(this,true){
//
//            @Override
//            public void onSuccessOfMe(JSONObject jsonObject) {
//                super.onSuccessOfMe(jsonObject);
//                LogTools.e("PostReadState", jsonObject.toString());
//                if(dialog!=null && !isDestroyed())
//                dialog.dismiss();
//                Intent intent=new Intent();
//                intent.putExtra(BundleTag.Position,position);
//                setResult(BundleTag.ResultOk,intent);
//            }
//
////            @Override
////            public void onFailure(Throwable throwable, JSONObject jsonObject) {
////                super.onFailure(throwable, jsonObject);
////                if(dialog!=null && !isDestroyed())
////                dialog.dismiss();
////            }
//
//            @Override
//            public void onFailureOfMe(Throwable throwable, String s) {
//                super.onFailureOfMe(throwable, s);
//                if(dialog!=null && !isDestroyed())
//                dialog.dismiss();
//            }
//        });
//    }

    private void PostReadState2(final String id)
    {
        if(dialog==null )
        {
            dialog=new PublicDialog(this);
        }
        dialog.show();
        RequestParams requestParams=new RequestParams();
        requestParams.put("userName", ((BaseApplication)this.getApplication()).getUsername());
        requestParams.put("code", id);
        Httputils.PostWithBaseUrl(Httputils.messagedetail,requestParams,new MyJsonHttpResponseHandler(this,true){

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
//                LogTools.e("PostReadState", jsonObject.toString());
                if(dialog!=null && !isDestroyed())
                    dialog.dismiss();
                Intent intent=new Intent();
                intent.putExtra(BundleTag.Position,position);
                setResult(BundleTag.ResultOk,intent);
            }

//            @Override
//            public void onFailure(Throwable throwable, JSONObject jsonObject) {
//                super.onFailure(throwable, jsonObject);
//                if(dialog!=null && !isDestroyed())
//                dialog.dismiss();
//            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                if(dialog!=null && !isDestroyed())
                    dialog.dismiss();
            }
        });
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(dialog!=null)
        {
            dialog.dismiss();
            dialog=null;
        }
    }
}
