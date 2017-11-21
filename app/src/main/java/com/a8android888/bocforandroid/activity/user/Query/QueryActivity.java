package com.a8android888.bocforandroid.activity.user.Query;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Change_Adapter;
import com.a8android888.bocforandroid.Adapter.Query_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.view.PublicDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/30.注单查询
 */
public class QueryActivity extends BaseActivity implements View.OnClickListener{
    TextView title;
    ImageView back;
    ListView listView;
    Query_Adapter adapter;
    ArrayList<JSONObject> Jsonarray;
    PublicDialog publicDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change);
            initview();

    }
    private void initview(){
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        title=(TextView)FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.querytitle));
        listView=(ListView)FindView(R.id.listview);
        Platform();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }

    private void Platform()
    {
        if(publicDialog==null)
            publicDialog=new PublicDialog(this);
        publicDialog.show();
        Httputils.PostWithBaseUrl(Httputils.Platform,null,new MyJsonHttpResponseHandler(this,true){
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                if(publicDialog!=null && !isDestroyed())
                {
                    publicDialog.dismiss();
                }
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                if(publicDialog!=null && !isDestroyed())
                {
                    publicDialog.dismiss();
                }
                LogTools.e("jsonObject",jsonObject.toString());
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))return;
                JSONArray jsarr=jsonObject.optJSONArray("datas");
                Jsonarray=new ArrayList<JSONObject>();
                if(jsarr==null)return;
                for (int i = 0; i < jsarr.length(); i++) {
                    JSONObject jsobject= jsarr.optJSONObject(i);
                    if(jsobject.optString("flat","").equalsIgnoreCase("sb"))
                    {
                        continue;
                    }
                    if(jsobject.optString("flat","").equalsIgnoreCase("huangguan"))
                    {
                        continue;
                    }
                    if(jsobject.optString("flat","").equalsIgnoreCase("cp"))
                    {
                        continue;
                    }
                    Jsonarray.add(jsobject);
                }

                adapter=new Query_Adapter(QueryActivity.this,Jsonarray);
                listView.setAdapter(adapter);
            }
        });
    }
}
