package com.a8android888.bocforandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.R;

/**
 * Created by Administrator on 2017/4/26.
 */
public class LotteryDialog extends Dialog{

    Context context;
    TextView textView;
    public LotteryDialog(Context context) {
        super(context);
        Initview(context);
    }

    protected LotteryDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        Initview(context);
    }

    public LotteryDialog(Context context, int theme) {
        super(context, theme);
        Initview(context);
    }

    private void Initview(Context context)
    {
        this.context=context;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        getContext().setTheme(R.style.loading_dialog);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height= ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

        RelativeLayout layout=new RelativeLayout(context);
        layout.setBackground(new ColorDrawable(0x99b8b8b8));
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
         textView=new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams layoutParams1= new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
        textView.setLayoutParams(layoutParams1);
        layout.addView(textView);

        setContentView(layout);
    }

    public void setContextText(String str)
    {
        if(textView!=null)
            textView.setText(str);
    }

    public String getContextText()
    {
        return textView.getText().toString();
    }
}
