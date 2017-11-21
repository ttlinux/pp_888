package com.a8android888.bocforandroid.BaseParent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.a8android888.bocforandroid.MainActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.WelcomeActivity;
import com.a8android888.bocforandroid.util.LogTools;

/**
 * Created by Administrator on 2017/3/28.
 */
public class BaseActivity extends Activity{


    public boolean isneedback() {
        return isneedback;
    }

    public void setIsneedback(boolean isneedback) {
        this.isneedback = isneedback;
    }

    boolean isneedback=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        View view=FindView(R.id.back);
        if(view!=null && isneedback)
        {
            view.setVisibility(View.VISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    finish();
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public <T> T FindView(int id)
    {
        return (T)findViewById(id);
    }
}
