package com.a8android888.bocforandroid.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.FastBlurUtility;
import com.a8android888.bocforandroid.util.LogTools;

import org.jivesoftware.smackx.bytestreams.ibb.packet.Close;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/27.
 */
public class ScollAlertdialog extends RelativeLayout {

    Activity activity;
    WindowManager windowManager;
    RadioGroup listlayout;
    View view;
    boolean iscloseing = false;
    RelativeLayout relayout;
    OnItemClickListener monItemClickListener;
    TextView title;
    TextView cancel, confirm;

    public interface OnItemClickListener {
        public void OnItemClick(int index, ScollAlertdialog view);
    }

    public ScollAlertdialog(Context context) {
        super(context);
    }

    public ScollAlertdialog(Context context, OnItemClickListener onItemClickListener) {
        super(context);
        if(context instanceof Activity)
        {
            this.activity=(Activity)context;
            Init(onItemClickListener);
        }

    }

    public ScollAlertdialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScollAlertdialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTitle(String text)
    {
        title.setText(text);
    }

    protected void Init(OnItemClickListener onItemClickListener) {
        monItemClickListener = onItemClickListener;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
//        this.setAlpha(0.5f);
        setBackground(new ColorDrawable(0x77D3D3D3));
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        view = View.inflate(activity, R.layout.scrollalertdialog, null);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        title= (TextView) view.findViewById(R.id.title);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monItemClickListener != null)
                    monItemClickListener.OnItemClick(1, ScollAlertdialog.this);
            }
        });
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monItemClickListener != null)
                    monItemClickListener.OnItemClick(0, ScollAlertdialog.this);
            }
        });
        RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        addView(view, rl);
        windowManager = activity.getWindowManager();
    }

    public void show() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明
        windowManager.addView(this, lp);
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(500);
        view.startAnimation(animationSet);
    }

    public void close() {
        if (!iscloseing)
            iscloseing = true;
        else
            return;
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                iscloseing = false;
                windowManager.removeView(ScollAlertdialog.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction()==KeyEvent.KEYCODE_BACK)
        {
            close();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
