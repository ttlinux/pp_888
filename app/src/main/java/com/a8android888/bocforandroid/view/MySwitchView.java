package com.a8android888.bocforandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.LogTools;

/**
 * Created by Administrator on 2017/3/29.
 */
public class MySwitchView extends RelativeLayout{

    RadioGroup radiogroup;

    public OnChangeListener getOnChangeListener() {
        return onChangeListener;
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    OnChangeListener onChangeListener;

    public MySwitchView(Context context) {
        super(context);
        init(context);
    }

    public MySwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySwitchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view=View.inflate(context, R.layout.switch_layout,null);
        addView(view,new RadioGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        radiogroup=(RadioGroup)findViewById(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=(RadioButton)findViewById(checkedId);
                radioButton.setTextColor(getResources().getColor(R.color.white));
                if(checkedId==R.id.rb1)
                {
                     radioButton=(RadioButton)findViewById(R.id.rb2);
                    radioButton.setTextColor(getResources().getColor(R.color.black));
                    if(onChangeListener!=null)
                        onChangeListener.OnChange(0);
                }
                else
                {
                    radioButton=(RadioButton)findViewById(R.id.rb1);
                    radioButton.setTextColor(getResources().getColor(R.color.black));
                    if(onChangeListener!=null)
                        onChangeListener.OnChange(1);
                }
            }
        });
    }

    public interface OnChangeListener
    {
        public void OnChange(int index);
    }
}
