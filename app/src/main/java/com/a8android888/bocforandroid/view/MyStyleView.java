package com.a8android888.bocforandroid.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.BallTypeBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.FastBlurUtility;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/6.
 */
public class MyStyleView extends RelativeLayout {

    Activity activity;
    WindowManager windowManager;
    RadioGroup listlayout;
    View view;
    boolean iscloseing=false;
    RelativeLayout relayout;
    OnItemClickListener onItemClickListener;

    public MyStyleView(Context context,ArrayList<BallTypeBean> list,OnItemClickListener onItemClickListener,int index) {
        super(context);
        Init((Activity)context,list,onItemClickListener,index);
    }

    public MyStyleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyStyleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    protected void Init(Activity activity,ArrayList<?> list,OnItemClickListener onItemClickListener,int index) {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        this.activity=activity;
//        this.setAlpha(0.5f);
        setBackground(new BitmapDrawable(FastBlurUtility.getBlurBackgroundDrawer(activity)));
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
         view= View.inflate(activity, R.layout.dialog_sports, null);
        listlayout=(RadioGroup)view.findViewById(R.id.listlayout);
                listlayout.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
        AddTextView(listlayout, list, onItemClickListener, index);

        ImageView imageView=(ImageView)view.findViewById(R.id.scale_img);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        this.addView(view);

         windowManager=activity.getWindowManager();
    }

    public void show()
    {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        //WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        lp.format = PixelFormat.A_8; // 设置图片格式，效果为背景透明
        windowManager.addView(this, lp);
        TranslateAnimation animation=new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.setFillBefore(true);
        animationSet.setFillAfter(true);
        animationSet.setDuration(500);
        view.startAnimation(animationSet);
    }

    public void close()
    {
        if(!iscloseing)
            iscloseing=true;
        else
            return;
        TranslateAnimation animation=new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_PARENT, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);

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
                iscloseing=false;
                windowManager.removeView(MyStyleView.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animationSet);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogTools.e("onKeyDown", "onKeyDown");
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            close();
        }
        return true;
    }

    public Bitmap myShot(Activity activity) {
        // 获取windows中最顶层的view
        View view = activity.getWindow().getDecorView();
        view.buildDrawingCache();
        // 获取状态栏高度
        Rect rect = new Rect();
        view.getWindowVisibleDisplayFrame(rect);
        int statusBarHeights = rect.top;
        Display display = activity.getWindowManager().getDefaultDisplay();
        // 获取屏幕宽和高

        int widths = display.getWidth();
        int heights = display.getHeight();
        // 允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        // 去掉状态栏
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, statusBarHeights, widths, heights
                - statusBarHeights);
        // 销毁缓存信息
        view.destroyDrawingCache();
        return bmp;
    }

    public interface OnItemClickListener
    {
        public void OnItemClick(int index,MyStyleView view);
    }


    public void AddTextView(final RadioGroup linearLayout,final ArrayList<?> list,final OnItemClickListener onItemClickListener,final int index)
    {
        int widthofword=getFontHeight(ScreenUtils.sp2px(activity,20))*2;
        for (int i = 0; i <list.size() ; i++) {
            RadioButton textView=new RadioButton(activity);

            RadioGroup.LayoutParams params= new RadioGroup.LayoutParams(widthofword+ScreenUtils.getDIP2PX(activity, 140), ViewGroup.LayoutParams.WRAP_CONTENT);

            params.topMargin=ScreenUtils.getDIP2PX(activity,20);
            params.gravity= Gravity.CENTER;
            textView.setButtonDrawable(new ColorDrawable(0x00000000));
            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0, ScreenUtils.getDIP2PX(activity, 10), 0, ScreenUtils.getDIP2PX(activity, 10));
            textView.setTextColor(0xFFFFFFFF);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textView.setSingleLine();
            textView.setBackground(activity.getResources().getDrawable(R.drawable.styleview_backgroud));
            textView.setMaxWidth(widthofword + ScreenUtils.getDIP2PX(activity, 140));
            if(list.get(i) instanceof BallTypeBean)
            {
                BallTypeBean bean=(BallTypeBean)list.get(i);
                textView.setText(bean.getrName());
            }
            textView.setTag(i);
            if(i==index)
            {
                textView.setChecked(true);
            }
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogTools.e("CompoundButton", "CompoundButton " + index);
                    RadioButton radioButton = (RadioButton) linearLayout.getChildAt(index);
                    radioButton.setChecked(false);
                    if (onItemClickListener != null) {
                        int tag = Integer.valueOf(v.getTag() + "");
                        onItemClickListener.OnItemClick(tag, MyStyleView.this);
                    }
                }
            });
//            textView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        LogTools.e("CompoundButton", "CompoundButton " + index);
//                        RadioButton radioButton = (RadioButton) linearLayout.getChildAt(index);
//                        radioButton.setChecked(false);
//                        if (onItemClickListener != null) {
//                            int tag = Integer.valueOf(buttonView.getTag() + "");
//                            onItemClickListener.OnItemClick(tag, MyStyleView.this);
//                        }
//                    }
//                }
//            });


            linearLayout.addView(textView);
        }

    }

    public int getFontHeight(float fontSize)   {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

}
