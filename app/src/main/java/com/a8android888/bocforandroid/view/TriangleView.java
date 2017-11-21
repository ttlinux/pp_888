package com.a8android888.bocforandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.ScreenUtils;

/**
 * Created by Administrator on 2017/3/30.
 */
public class TriangleView extends View {

    public static final int Top=0;
    public static final int Bottom=1;
    public static final int Left=2;
    public static final int Right=3;
    public static float Height=100,Width=50;
    int style;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    private static int color=Color.RED;

    public void setTriangleHeight(int height) {
        Height = height;
    }

    public void setTriangleWidth(int width) {
        Width = width;
    }

    public TriangleView(Context context) {
        super(context);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context, attrs);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        InitView(context,attrs);
    }

    private void InitView(Context context,AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TriangleView);
        color=typedArray.getColor(R.styleable.TriangleView_ShapeBackgroudColor, Color.GRAY);
//        Height= ScreenUtils.getDIP2PX(context,typedArray.getDimension(R.styleable.TriangleView_ShapeHeight, 100f));
//        Width=ScreenUtils.getDIP2PX(context, typedArray.getDimension(R.styleable.TriangleView_ShapeHeight, 50f));
        style=typedArray.getInteger(R.styleable.TriangleView_ShapeStyle,Top);
        typedArray.recycle();
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // 绘制这个三角形,你可以绘制任意多边形
        Height=getHeight();
        Width=getWidth();
        switch (style)
        {
            case Top:
                Top(canvas);
                break;
            case Bottom:
                Bottom(canvas);
                break;
            case Left:
                Left(canvas);
                break;
            case Right:
                Right(canvas);
                break;
        }
    }

    private static void Top(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(color);// 设置红色
        p.setStyle(Paint.Style.FILL);//设置填满
        Path path = new Path();
        path.moveTo(Width/2, 0);// 此点为多边形的起点
        path.lineTo(0, Height);
        path.lineTo(Width, Height);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
    }
    private static void Bottom(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(color);// 设置红色
        p.setStyle(Paint.Style.FILL);//设置填满
        Path path = new Path();
        path.moveTo(0, 0);// 此点为多边形的起点
        path.lineTo(Width, 0);
        path.lineTo(Width/2, Height);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
    }
    private static void Left(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(color);// 设置红色
        p.setStyle(Paint.Style.FILL);//设置填满
        Path path = new Path();
        path.moveTo(Width, 0);// 此点为多边形的起点
        path.lineTo(Width, Height);
        path.lineTo(0, Height/2);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
    }
    private static void Right(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(color);// 设置红色
        p.setStyle(Paint.Style.FILL);//设置填满
        Path path = new Path();
        path.moveTo(0, 0);// 此点为多边形的起点
        path.lineTo(0, Height);
        path.lineTo(Width, Height/2);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
    }


    public static Bitmap createBitmap(int w,int h,int mcolor,int style)
    {
        color=mcolor;
        Width=w;
        Height=h;
        //create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888 );//创建一个新的和SRC长度宽度一样的位图
        Canvas canvas = new Canvas( newb );

        switch (style)
        {
            case Top:
                Top(canvas);
                break;
            case Bottom:
                Bottom(canvas);
                break;
            case Left:
                Left(canvas);
                break;
            case Right:
                Right(canvas);
                break;
        }
        //draw src into
//        cv.drawBitmap(src, 0, 0, null);//在 0，0坐标开始画入src
//        //draw watermark into
//        cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, null);//在src的右下角画入水印
        //save all clip
        canvas.save(Canvas.ALL_SAVE_FLAG);//保存
        //store
        canvas.restore();//存储
        return newb;
    }
}

