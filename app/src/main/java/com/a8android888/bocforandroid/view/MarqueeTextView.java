package com.a8android888.bocforandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.widget.TextView;

import com.a8android888.bocforandroid.util.LogTools;

/**
 * Created by Administrator on 2017/5/5.
 */
public class MarqueeTextView extends TextView implements Runnable {

    private static final int MARQUEE_DELAY = 1200;
    private static final int MARQUEE_RESTART_DELAY = 10;

    private int currentScrollX;
    private boolean isStop = false;
    private int textWidth;
    private boolean isMeasure = false;
    private int mMarqueeRepeatLimit = 1;
    private int mMarqueeVelocity = 2;

    private OnMarqueeCompleteListener marqueeCompleteListener;

    public MarqueeTextView(Context context) {
        this(context, null);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        mMarqueeRepeatLimit = getMarqueeRepeatLimit();
    }

    public interface OnMarqueeCompleteListener {
        void onMarqueeComplete(MarqueeTextView view);
    }

    public OnMarqueeCompleteListener getMarqueeCompleteListener() {
        return marqueeCompleteListener;
    }

    public void setOnMarqueeCompleteListener(
            OnMarqueeCompleteListener marqueeCompleteListener) {
        this.marqueeCompleteListener = marqueeCompleteListener;
    }

    /**
     * 获取文字滚动的速度，每秒移动的像素
     * @return
     */
    public int getMarqueeVelocity() {
        return mMarqueeVelocity;
    }

    /**
     * 设置文字的滚动的速度
     * @param mMarqueeVelocity 每秒移动的像素
     */
    public void setMarqueeVelocity(int velocity) {
        this.mMarqueeVelocity = velocity ;
    }

    @Override
    @ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return true;
    }

    @Override
    @ViewDebug.ExportedProperty
    public boolean isSelected() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
//        startStopMarquee(selected);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
//        startStopMarquee(focused);
    }

    public void startStopMarquee(boolean bool){
        if(bool){
            startScroll();
        }else{
            stopScroll();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasure) {
            textWidth = getTextWidth();
            isMeasure = true;
        }
    }

    /**
     * 获取文字宽度
     */
    private int getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        return (int) paint.measureText(str);
    }

    /**
     * 获取文字宽度
     */
    private int getTextWidth(String text) {
        Paint paint = this.getPaint();
        return (int) paint.measureText(text);
    }
    @Override
    public void run() {
//        if(textWidth <= getWidth()){
//            if(null != marqueeCompleteListener){
//                marqueeCompleteListener.onMarqueeComplete(this);
//            }
//            return;
//        }

        if (isStop) {
            currentScrollX = 0;
            scrollTo(currentScrollX, 0);
            return;
        }

        currentScrollX += mMarqueeVelocity;
        scrollTo(currentScrollX, 0);


        if (textWidth != 0  && getScrollX() >= textWidth) {
            LogTools.e("mMarqueeRepeatLimit", mMarqueeRepeatLimit + "");
            mMarqueeRepeatLimit--;
            if(mMarqueeRepeatLimit <= 0){
                if(null != marqueeCompleteListener){
                    marqueeCompleteListener.onMarqueeComplete(this);
                }
                return;
            }
            currentScrollX = -getWidth();
        }

        postDelayed(this, MARQUEE_RESTART_DELAY);
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        textWidth = getTextWidth(text.toString());
    }

    /**
     *  开始滚动
     */
    private void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        this.invalidate();
        currentScrollX = -getWidth();
        scrollTo(currentScrollX, 0);
        postDelayed(this, MARQUEE_DELAY);
    }

    /**
     *  停止滚动
     */
    private void stopScroll() {
        isStop = true;
    }

    /**
     *  从头开始滚动
     */
    public void startFor0() {
        currentScrollX = 0;
        startScroll();
    }
}