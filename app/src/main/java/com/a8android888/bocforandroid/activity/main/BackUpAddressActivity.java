package com.a8android888.bocforandroid.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;

/**
 * Created by Administrator on 2017/4/8.
 */
public class BackUpAddressActivity extends BaseActivity{

    TableLayout tablelayout;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);

        tablelayout=FindView(R.id.tablelayout);
        TextView title=FindView(R.id.title);
        title.setText(getIntent().getStringExtra("title"));

        String urls[]= getIntent().getStringArrayExtra(BundleTag.URL);
        String backup=getString(R.string.bakcupurl);
        for (int i = 0; i <urls.length; i++) {
            TableRow tablerow=new TableRow(this);
            TableLayout.LayoutParams layoutParams= new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.bottomMargin=1;
            tablerow.setLayoutParams(layoutParams);

            TextView textview=new TextView(this);
            TableRow.LayoutParams params= new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight=0.4f;
            textview.setLayoutParams(params);
            textview.setPadding(20, 20, 20, 20);
            textview.setGravity(Gravity.CENTER);
            textview.setBackgroundColor(0xFFFFFFFF);
            textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textview.setText(backup + (i+1));
            tablerow.addView(textview);

            TextView textview2=new TextView(this);
            params.weight=0.6f;
            params.leftMargin=1;
            textview2.setLayoutParams(params);
            textview2.setPadding(20, 20, 20, 20);
            textview2.setGravity(Gravity.CENTER);
            textview2.setBackgroundColor(0xFFFFFFFF);
            textview2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textview2.setText(urls[i]);
            tablerow.addView(textview2);

            tablelayout.addView(tablerow);
        }


    }
}
