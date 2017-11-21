package com.a8android888.bocforandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.NoticelistAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/2.
 */
public class NoticeActivity extends BaseActivity{

    SwipeRefreshLayout swipeview;
    ListView listview;
    ArrayList<JSONObject> jsons=new ArrayList<JSONObject>();
    TextView title;
    NoticelistAdapter noadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticelist);

        title=FindView(R.id.title);
        title.setText(getString(R.string.sysnotice));
        listview=FindView(R.id.listview);
        swipeview=FindView(R.id.swipeview);

        swipeview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Getdata(true);
            }
        });

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        Getdata(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void Getdata(final boolean init)
    {
        RequestParams params=new RequestParams();
        Httputils.PostWithBaseUrl(Httputils.notice, params, new MyJsonHttpResponseHandler(this,true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                swipeview.setRefreshing(false);
                LogTools.e("jsonObject",jsonObject.toString());
                if (!jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) return;
                if (init) {
                    jsons.clear();
                    SetAdapter();
                }
                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            jsons.add(jsonArray.getJSONObject(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    SetAdapter();
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipeview.setRefreshing(false);
            }
        });
    }

    private void SetAdapter()
    {
        if(noadapter==null)
        {
            noadapter=new NoticelistAdapter(jsons,this);
            listview.setAdapter(noadapter);
        }
        else
        {
            noadapter.NotifyAdapter(jsons);
        }
    }
}
