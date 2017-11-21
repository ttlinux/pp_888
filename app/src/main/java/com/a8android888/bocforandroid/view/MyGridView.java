package com.a8android888.bocforandroid.view;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

/**
 *
 * @author limingfang
 * @category
 *
 */
public class MyGridView extends GridView {


        public MyGridView(Context context) {
                super(context);
        }

        public MyGridView(Context context, AttributeSet attrs) {
                super(context, attrs);
        }

        public MyGridView(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                        View.MeasureSpec.AT_MOST);
                super.onMeasure(widthMeasureSpec, expandSpec);
        }



}
