package com.a8android888.bocforandroid.activity.user.Query;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.PaymentAdapter;
import com.a8android888.bocforandroid.Adapter.QueryDetailAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.PaymentBean;
import com.a8android888.bocforandroid.Bean.QueryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.DatetimeDialog;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/3.
 */
public class QueryDetailActivity extends BaseActivity implements View.OnClickListener{

    RadioGroup titles_gp;
    SwipeRefreshLayout swipl;
    ListView listView;
    int mtotalItemCount,mfirstVisibleItem,page=1;
    boolean hasdata=true;
    QueryDetailAdapter queryDetailAdapter;
    ArrayList<QueryBean> payments=new ArrayList<QueryBean>();
    static String strs[]=new String[2];
    static int SelectType=1;
    RadioButton Recordbtn;
    RelativeLayout maskview;
    LinearLayout bottomview;
    String[] bottomstrs=new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_detail);
        TextView title=FindView(R.id.title);
        title.setText(getIntent().getStringExtra(BundleTag.PlatformName));

        bottomview=FindView(R.id.bottomview);
        maskview=FindView(R.id.maskview);
        listView=FindView(R.id.listview);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mtotalItemCount - mfirstVisibleItem < 11 && scrollState == 0 && hasdata) {
                    LogTools.e("runing", "runing");
                    swipl.setRefreshing(true);
                    page++;
                    GetTypeForPost(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view.getChildAt(0)!=null && view.getChildAt(0).getTop()==0)
                {
                    if(swipl!=null)
                        swipl.setEnabled(true);
                }
                else
                {
                    if(swipl!=null)
                        swipl.setEnabled(false);
                }
                mfirstVisibleItem = firstVisibleItem;
                mtotalItemCount = totalItemCount;
            }
        });

        swipl=FindView(R.id.swipl);
        swipl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTypeForPost(true);
            }
        });

        titles_gp=FindView(R.id.titles_gp);
        titles_gp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radioButton1:
                        SelectType=1;
                        break;
                    case R.id.radioButton2:
                        SelectType=2;
                        break;
                    case R.id.radioButton3:
                        SelectType=3;
                        break;
                    case R.id.radioButton4:
                        SelectType=4;
                        break;
                }
                LogTools.e("Check",SelectType+"");
                GetTypeForPost(true);
                if(Recordbtn!=null)
                {
                    Recordbtn.setTextColor(Color.BLACK);
                }
                RadioButton radioButton=(RadioButton)titles_gp.findViewById(checkedId);
                radioButton.setTextColor(Color.WHITE);
                Recordbtn=radioButton;
            }
        });
        String titles[]=getResources().getStringArray(R.array.pay_record_titles);
        for (int i = 0; i <titles_gp.getChildCount(); i++) {

            RadioButton radioButton=(RadioButton)titles_gp.getChildAt(i);
            radioButton.setText(titles[i]);
            radioButton.setButtonDrawable(new ColorDrawable(0));
        }

        Recordbtn=((RadioButton)titles_gp.getChildAt(SelectType-1));
        Recordbtn.setChecked(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void PostData(final boolean init,String startdate,String enddate)
    {
        if(init)
        {
            page=1;
        }
        hasdata = false;//控制有没有数据和是否运行中
        RequestParams params=new RequestParams();
        params.put("userName",((BaseApplication)this.getApplication()).getBaseapplicationUsername());
        params.put("flat",getIntent().getStringExtra(BundleTag.Platform));
//        params.put("beginTime",startdate);//YYYY-MM-DD 2016-09-01
//        params.put("endTime",enddate);//YYYY-MM-DD 2016-10-10
        String iiii[]={"today","oneweek","onemonth","threemonth"};
        params.put("time",iiii[SelectType-1]+"");
        params.put("pageNo",page+"");
        params.put("pageSize","20");
        Httputils.PostWithBaseUrl(Httputils.Record, params, new MyJsonHttpResponseHandler(this, false) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("response", jsonObject.toString());
                hasdata = true;
                swipl.setRefreshing(false);
                if (init) {
                    payments.clear();
                }
                JSONObject datas = jsonObject.optJSONObject("datas");
                bottomstrs[0]=datas.optString("betIns","");
                bottomstrs[1]=datas.optString("betIncomes","");
                bottomstrs[2]=datas.optString("betUsrWins","");
                for (int i = 1; i < bottomview.getChildCount(); i++) {
                    TextView textview=(TextView)bottomview.getChildAt(i);
                    textview.setText(bottomstrs[i - 1]);
                    if(i>2)
                    {
                       textview.setTextColor(Integer.valueOf(bottomstrs[i - 1])< 0 ? 0xffcf0000 : 0xff018730);
                    }
                }

                JSONArray jsonArray = datas.optJSONArray("list");
                if(jsonArray==null || jsonArray.length()==0){
                    hasdata = false;
                    SetAdapter();
                    if (page>1)
                        ToastUtil.showMessage(QueryDetailActivity.this, getString(R.string.nomoredata));
                    return;
                }

                if (jsonArray == null || jsonArray.length() < 1) {
                    hasdata = false;

                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsobj = jsonArray.optJSONObject(i);
                        QueryBean bean = new QueryBean();
                        bean.setBetTime(jsobj.optString("betTime", ""));
                        try {
                            bean.setBetIn(Double.valueOf(jsobj.optString("betIn", "")));
                            bean.setBetUsrWin(Double.valueOf(jsobj.optString("betUsrWin", "")));
                        } catch (java.lang.NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                        bean.setBetGameContent(jsobj.optString("betContent", ""));
                        bean.setBetWagersId(jsobj.optString("betWagersId", ""));
                        bean.setBetIncome(jsobj.optString("betIncome", ""));
                        try {

                            JSONArray details=jsobj.getJSONArray("detail");
                            ArrayList<QueryBean.detail> detaillist=new ArrayList<QueryBean.detail>();
                            for (int j = 0; j <details.length(); j++) {
                                JSONObject detailobj=details.optJSONObject(j);
                                QueryBean.detail dbean=new QueryBean.detail();
                                dbean.setColor(detailobj.optString("color",""));
                                dbean.setKey(detailobj.optString("key",""));
                                dbean.setValue(detailobj.optString("value",""));
                                detaillist.add(dbean);
                            }
                            bean.setDetails(detaillist);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        payments.add(bean);
                    }
                }
                SetAdapter();
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String jsonObject) {
                super.onFailureOfMe(throwable, jsonObject);
                swipl.setRefreshing(false);
                page--;
            }

//            @Override
//            public void onFailure(Throwable throwable, String s) {
//                super.onFailure(throwable, s);
//                publicDialog.dismiss();
//            }
        });
    }

    public void SetAdapter()
    {
        if (queryDetailAdapter == null) {
            queryDetailAdapter = new QueryDetailAdapter(QueryDetailActivity.this, payments);
            listView.setAdapter(queryDetailAdapter);
        } else {
            queryDetailAdapter.NotifyData(payments);
        }
        CheckData();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void GetTypeForPost(boolean init)
    {

        Calendar calendar = Calendar.getInstance(); //得到日历
        Date dNow = new Date();   //当前时间
        calendar.setTime(dNow);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String EndDay=sdf.format(dNow);
        String StartDay="";
        hasdata=true;//设置可以加载数据
        switch (SelectType)
        {
            case 1:
                PostData(init,EndDay,EndDay);
                break;
            case 2:
                calendar.add(calendar.WEEK_OF_MONTH, -1);  //设置为前3月
                StartDay=sdf.format(calendar.getTime());
                PostData(init,StartDay,EndDay);
                break;
            case 3:
                calendar.add(calendar.MONTH, -1);  //设置为前3月
                StartDay=sdf.format(calendar.getTime());
                PostData(init,StartDay,EndDay);
                break;
            case 4:
                calendar.add(calendar.MONTH, -3);  //设置为前3月
                StartDay=sdf.format(calendar.getTime());
                PostData(init, StartDay, EndDay);
                break;
        }
    }

    private void CheckData()
    {
        if(listView==null || listView.getAdapter()==null || listView.getAdapter().getCount()==0)
        {
            maskview.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            bottomview.setVisibility(View.GONE);
        }
        else
        {
            maskview.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            bottomview.setVisibility(View.VISIBLE);
        }
    }
}
