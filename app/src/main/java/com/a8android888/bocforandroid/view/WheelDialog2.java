package com.a8android888.bocforandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.R;

import org.json.JSONArray;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2017/8/31.
 */
public class WheelDialog2 extends Dialog {

    private int oldValue=-1,newValue=-1;
    private String title="";
    Context context;
    public void setListener(OnChangeListener listener) {
        this.listener = listener;
    }

    OnChangeListener listener;

    public String[] getStrs() {
        return strs;
    }


    public void setStrs(String[] strs) {
        this.strs = strs;
    }

    public void setStrs(JSONArray strs) {
        this.strs=new String[strs.length()];
        for (int i = 0; i <strs.length(); i++) {
            this.strs[i]=strs.optJSONObject(i).optString("name","");
        }
    }

    String strs[];

    public WheelDialog2(Context context) {
        super(context);
        InitView(context);
    }

    protected WheelDialog2(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        InitView(context);
    }

    public WheelDialog2(Context context, int theme) {
        super(context, theme);
        InitView(context);
    }

    private void InitView(Context context)
    {
        this.context=context;
        getContext().setTheme(R.style.loading_dialog);
    }

    public void setTitle(String title)
    {
        this.title=title;
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
        layout.setBackground(new ColorDrawable(0));
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final WheelView wheelView=new WheelView(context);
        RelativeLayout.LayoutParams layoutParams1= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        wheelView.setLayoutParams(layoutParams1);
        wheelView.setVisibleItems(4);

//        wheelView.setCyclic(true);
        wheelView.setId(73624);
        wheelView.SetNoBackgound();
        ArrayWheelAdapter<String> arrayWheelAdapter=new ArrayWheelAdapter<String>(context, strs);
        arrayWheelAdapter.setDEFAULT_TEXT_SIZE_TYPE_SP(15);
        wheelView.setViewAdapter(arrayWheelAdapter);
        wheelView.setCurrentItem(0);
//        wheelView.addChangingListener(arrayWheelAdapter);
//        arrayWheelAdapter.InitTextColor(0);
//        wheelView.addScrollingListener(new OnWheelScrollListener() {
//            @Override
//            public void onScrollingStarted(WheelView wheel) {
//
//            }
//
//            @Override
//            public void onScrollingFinished(WheelView wheel) {
//
//            }
//        });
        wheelView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
//                LogTools.e("eeee",oldValue+" "+newValue);
                WheelDialog2.this.oldValue = oldValue;
                WheelDialog2.this.newValue = newValue;
            }
        });
        layout.addView(wheelView);

        RelativeLayout relativeLayout=new RelativeLayout(context);
        relativeLayout.setBackgroundColor(0xFFFFFFFF);
        RelativeLayout.LayoutParams layoutPa=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutPa.addRule(RelativeLayout.ABOVE,73624);
        relativeLayout.setLayoutParams(layoutPa);

        TextView textView=new TextView(context);
        RelativeLayout.LayoutParams layoutParams2=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
        layoutParams2.rightMargin=20;
        textView.setPadding(15, 15, 15, 15);
        textView.setLayoutParams(layoutParams2);
        textView.setText("确定");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null ) {
                    listener.onChanged(wheelView, oldValue<0?0:oldValue, newValue<0?0:newValue);
                }
                dismiss();
            }
        });

        TextView title=new TextView(context);
        RelativeLayout.LayoutParams ti_layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ti_layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        title.setLayoutParams(ti_layoutParams);
        title.setTextColor(context.getResources().getColor(R.color.gray4));
        title.setText(this.title);

        ImageView imageView=new ImageView(context);
        RelativeLayout.LayoutParams layoutParams3=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
        imageView.setBackgroundColor(context.getResources().getColor(R.color.gray5));
        imageView.setLayoutParams(layoutParams3);


        relativeLayout.addView(title);
        relativeLayout.addView(imageView);
        relativeLayout.addView(textView);
        relativeLayout.setBackground(context.getResources().getDrawable(R.drawable.test4));


        layout.addView(relativeLayout);
        setContentView(layout);
    }

    public interface OnChangeListener
    {
        public void onChanged(WheelView wheel, int oldValue, int newValue);
    }

}
