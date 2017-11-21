package com.a8android888.bocforandroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/10/26.
 */
public class BaseView extends View {

    private Bitmap bitmap;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
//            WeakReference<Bitmap> weakReference=new WeakReference<Bitmap>(bitmap);
            Matrix matrix = new Matrix();
            float scale=getHeight()*0.01f/bitmap.getHeight()*100;
            matrix.postScale(scale, scale); //长和宽放大缩小的比例
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            canvas.drawBitmap(resizeBmp,getWidth()-resizeBmp.getWidth() , 0, null);
        }
    }

    public void setImage(Drawable drawable) {
        this.bitmap = ((BitmapDrawable) drawable).getBitmap();
        postInvalidate();
    }

    public void setImage(Bitmap bitmap)
    {
        this.bitmap = bitmap;
        postInvalidate();
    }
}
