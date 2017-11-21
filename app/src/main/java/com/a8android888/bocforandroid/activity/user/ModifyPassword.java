package com.a8android888.bocforandroid.activity.user;

import android.content.Intent;
import android.os.Bundle;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.R;

/**
 * Created by Administrator on 2017/3/29.
 */
public class ModifyPassword extends BaseActivity{

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
    }
}
