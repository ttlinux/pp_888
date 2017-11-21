package com.a8android888.bocforandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * Created by gd on 2016/12/19.
 */
public class MyWebView extends WebView{

    float x,xx,y,yy;
    float textsize;
    WebSettings settings;
    public MyWebView(Context context) {
        super(context);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle, boolean privateBrowsing) {
        super(context, attrs, defStyle, privateBrowsing);
    }


    public void init()
    {
        settings=getSettings();
        textsize=settings.getTextZoom();
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()&event.ACTION_MASK) {
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        x = event.getX(0);
                        xx = event.getX(1);
                        y = event.getY(0);
                        yy = event.getY(1);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getPointerCount() == 2) {
                            float ax = event.getX(0) - event.getX(1);
                            float ay = event.getY(0) - event.getY(1);
                            float bx = x - xx;
                            float by = y - yy;
                            float verticalSpacing = (float) Math.sqrt(ax * ax + ay * ay);
                            float verticalSpacing2 = (float) Math.sqrt(bx * bx + by * by);
//						LogTools.e("verticalSpacing", "event.getY(1)"+verticalSpacing);
//						LogTools.e("verticalSpacing2", "yy"+verticalSpacing2);
                            if (Math.abs(verticalSpacing - verticalSpacing2) < 5) {
                                return true;
                            }
                            if (verticalSpacing2 < verticalSpacing) {
                                textsize = 5 + textsize;
                            } else {
                                textsize = textsize - 5;
                            }
                            if(textsize>130)textsize=130;
                            if(textsize<100)textsize=100;
                            settings.setTextZoom((int) textsize);
                            x = event.getX(0);
                            xx = event.getX(1);
                            y = event.getY(0);
                            yy = event.getY(1);
                            postInvalidate();
                        }
                        break;
                }
                return false;
            }
        });
    }
}
