package com.a8android888.bocforandroid.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.view.UPdataNoticeDialog;

/**
 * Created by Administrator on 2017/10/16.
 */
public class GestureDialog extends Dialog{

    private String title,message;
    OnSelectListener onSelectListener;

    public GestureDialog(Context context) {
        super(context);
        Initview(context);
    }

    protected GestureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        Initview(context);
    }

    public GestureDialog(Context context, int themeResId) {
        super(context, themeResId);
        Initview(context);
    }

    public void Initview(Context context)
    {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OnSelectListener getOnSelectListener() {
        return onSelectListener;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void show() {
        super.show();

        setContentView(R.layout.dialog_notice);
        TextView titleview=(TextView)findViewById(R.id.title);
        titleview.setText(title);
        TextView contentview=(TextView)findViewById(R.id.content);
        contentview.setText(message);
        Button confirm=(Button)findViewById(R.id.btn_cli);
        confirm.setText("设置");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSelectListener!=null)onSelectListener.select(true);
            }
        });
        Button cancel=(Button)findViewById(R.id.btn_cli2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onSelectListener!=null)onSelectListener.select(false);
            }
        });
    }

    public interface OnSelectListener
    {
        public void select(boolean select);
    }
}
