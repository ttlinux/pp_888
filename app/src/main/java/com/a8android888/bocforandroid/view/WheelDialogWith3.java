package com.a8android888.bocforandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.ToastUtil;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2017/4/26.
 */
public class WheelDialogWith3 extends Dialog{

    Context context;

    public void setListener(OnChangeListener listener) {
        this.listener = listener;
    }

    OnChangeListener listener;

    public String[][] getStrs() {
        return strs;
    }

    public void setStrs(String[][] strs) {
        this.strs = strs;
    }

    String strs[][];

    public int[] getIndex() {
        return index;
    }

    int[] index={0,1,2};
    int args[]={-1,-1,-1};

    public WheelDialogWith3(Context context) {
        super(context);
        InitView(context);
    }

    protected WheelDialogWith3(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        InitView(context);
    }

    public WheelDialogWith3(Context context, int theme) {
        super(context, theme);
        InitView(context);
    }

    private void InitView(Context context)
    {
        this.context=context;
        getContext().setTheme(R.style.loading_dialog);
    }

    public void SetInitnumber(int arg1,int arg2,int arg3)
    {
        args[0]=arg1;
        args[1]=arg2;
        args[2]=arg3;
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

        LinearLayout linearLayout=new LinearLayout(context);
        RelativeLayout.LayoutParams layoutParamsll= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsll.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        linearLayout.setLayoutParams(layoutParamsll);
        for (int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight=1;
            WheelView wheelView=new WheelView(context);
            wheelView.setTag(i);
            wheelView.setLayoutParams(params);
            wheelView.setVisibleItems(4);
            wheelView.SetNoBackgound();
            wheelView.SetMoreWheelbackgound();
//            wheelView.setCyclic(true);
            ArrayWheelAdapter arrayWheelAdapter=new ArrayWheelAdapter<String>(context, strs[i]);
            arrayWheelAdapter.setDEFAULT_TEXT_SIZE_TYPE_SP(15);
            wheelView.setViewAdapter(arrayWheelAdapter);
            wheelView.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    int tag=Integer.valueOf(wheel.getTag()+"");
                    index[tag]=newValue;
                    if(listener!=null)
                    {
                        if(index[0]==index[1]||index[0]==index[2]||index[1]==index[2]||index[1]==index[0]||index[2]==index[0]||index[2]==index[1]){
                            ToastUtil.showMessage(context,"特码包三号码不能两两相同");
                            return;
                        }
                        listener.onChanged(index[0],index[1],index[2]);
                    }
                }
            });
            if(args[i]>-1)
            wheelView.setCurrentItem(args[i]);
            linearLayout.addView(wheelView);

        }
        linearLayout.setBackground(context.getResources().getDrawable(kankan.wheel.R.drawable.test34));
        layout.addView(linearLayout);
        setContentView(layout);
    }

    public interface OnChangeListener
    {
        public void onChanged(int arg1, int arg2,int arg3);
    }
}
