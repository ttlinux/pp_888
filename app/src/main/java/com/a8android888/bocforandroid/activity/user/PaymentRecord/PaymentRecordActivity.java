package com.a8android888.bocforandroid.activity.user.PaymentRecord;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.PaymentAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.PaymentBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.DatetimeDialog;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.a8android888.bocforandroid.view.WheelDialog;
import com.a8android888.bocforandroid.view.WheelDialog2;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import kankan.wheel.widget.WheelView;


/**
 * Created by Administrator on 2017/3/30. 充值记录
 */
public class PaymentRecordActivity extends BaseActivity implements View.OnClickListener{

    RadioGroup titles_gp;
    SwipeRefreshLayout swipl;
    ListView listView;
    int mtotalItemCount,mfirstVisibleItem,page=1;
    boolean hasdata=true;
    PaymentAdapter paymentAdapter;
    ArrayList<PaymentBean> payments=new ArrayList<PaymentBean>();
    DatetimeDialog datetimeDialog;
    TextView recordView;
    int year,month,day;
    static String strs[]=new String[2];
    static int SelectType=1;
    RadioButton Recordbtn;
    RelativeLayout maskview;
    TextView choosetype;
    int patypeSelectIndex=-1;
    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView title=FindView(R.id.title);
        title.setText(getString(R.string.payrecord));

        String jsonarrstr=getIntent().getStringExtra(BundleTag.JsonObject);
        jsonArray=new JSONArray();
        if (jsonarrstr != null) {
            try {
                JSONArray jsonArray2 = new JSONArray(jsonarrstr);
                for (int i = 0; i < jsonArray2.length() + 1; i++) {
                    JSONObject jsonObject = new JSONObject();
                    if (i == 0) {
                        jsonObject.put("code", "");
                        jsonObject.put("name", "所有类型");
                    } else {
                        jsonObject.put("code", jsonArray2.optJSONObject(i - 1).optString("code"));
                        jsonObject.put("name", jsonArray2.optJSONObject(i - 1).optString("name"));
                    }
                    jsonArray.put(i,jsonObject);
                }
                LogTools.e("jsonarrstr3", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        choosetype=FindView(R.id.choosetype);
        choosetype.setText("所有类型");
        choosetype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WheelDialog2 dialog = new WheelDialog2(PaymentRecordActivity.this);
                dialog.setListener(new WheelDialog2.OnChangeListener() {
                    @Override
                    public void onChanged(WheelView wheel, int oldValue, int newValue) {
                        LogTools.e("oldValue", oldValue + " " + newValue);
                        patypeSelectIndex = newValue;
                        choosetype.setText(jsonArray.optJSONObject(patypeSelectIndex).optString("name"));
                        GetTypeForPost(true);
                    }
                });
                if(patypeSelectIndex<0)
                {
                    patypeSelectIndex=0;
                }
                if(jsonArray!=null && jsonArray.length()>0)
                {
                    dialog.setStrs(jsonArray);
                    dialog.setTitle("请滑动选择类型");
                    dialog.show();
                }
            }
        });

        Calendar c = Calendar.getInstance();
         year = c.get(Calendar.YEAR);
         month = c.get(Calendar.MONTH);
         day = c.get(Calendar.DAY_OF_MONTH);

        maskview=FindView(R.id.maskview);
        listView=FindView(R.id.listview);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                LogTools.e("mtotalItemCount",mtotalItemCount+" "+mfirstVisibleItem+" "+hasdata);
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
        params.put("userName", ((BaseApplication)this.getApplication()).getBaseapplicationUsername());
//        params.put("beginTime",startdate);//YYYY-MM-DD 2016-09-01
//        params.put("endTime", enddate);//YYYY-MM-DD 2016-10-10
        String iiii[]={"today","oneweek","onemonth","threemonth"};
        params.put("time",iiii[SelectType-1]+"");
        params.put("pageNo",page+"");
        params.put("pageSize","20");
        if(patypeSelectIndex>-1 && jsonArray!=null && jsonArray.length()>patypeSelectIndex)
        params.put("type",jsonArray.optJSONObject(patypeSelectIndex).optString("code",""));
        Httputils.PostWithBaseUrl(Httputils.Payrecord, params, new MyJsonHttpResponseHandler(this, false) {

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("response", jsonObject.toString());
                hasdata = true;
                swipl.setRefreshing(false);
                if (init) {
                    payments.clear();
                }
                JSONArray jsonArray = jsonObject.optJSONArray("datas");
                if(jsonArray==null || jsonArray.length()==0){
                    hasdata = false;
                    SetAdapter();
                    if(page>1)
                    ToastUtil.showMessage(PaymentRecordActivity.this, getString(R.string.nomoredata));
                    return;
                }

                if (jsonArray == null || jsonArray.length() < 1) {
                    hasdata = false;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsobj = jsonArray.optJSONObject(i);
                        PaymentBean bean = new PaymentBean();
                        bean.setHkModel(jsobj.optInt("hkModel", 5));
                        bean.setHkMoney(jsobj.optDouble("hkMoney", 100.00));
                        bean.setHkOrder(jsobj.optString("hkOrder", ""));
                        bean.setHkStatus(jsobj.optInt("hkStatus", 1));
                        bean.setHkTime(jsobj.optString("hkTime", ""));
                        bean.setHkType(jsobj.optString("hkType", ""));
                        bean.setRemark(jsobj.optString("remark", ""));
                        bean.setCreateTime(jsobj.optString("createTime", ""));
                        bean.setStatusDes(jsobj.optString("statusDes", ""));
//                        bean.setStatus(jsobj.optString("status", ""));
                        bean.setHkCheckStatus(jsobj.optInt("hkCheckStatus", 0));
                        bean.setHkCheckTime(jsobj.optString("hkCheckTime", ""));
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
        if (paymentAdapter == null) {
            paymentAdapter = new PaymentAdapter(PaymentRecordActivity.this, payments);
            listView.setAdapter(paymentAdapter);
        } else {
            paymentAdapter.NotifyData(payments);
        }
        CheckData();
    }
    @Override
    public void onClick(View v) {
        recordView=(TextView)v;
    }

    private void ShowDialog()
    {
        if(datetimeDialog==null)
        {
            datetimeDialog=new DatetimeDialog(this, new DatetimeDialog.OnSelectDialogListener() {
                @Override
                public void OnselectDate(int year, int mouth, int date) {
                    recordView.setText(year + "-" + (mouth+1) + "-" + date);
                    int tag=Integer.valueOf(recordView.getTag()+"") ;
                    strs[tag]=recordView.getText().toString().trim();
                }
            });
        }


        datetimeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(recordView.getText().toString().length()<5)
                {
                    recordView.setText(year+"-"+(month+1)+"-"+day);
                    int tag=Integer.valueOf(recordView.getTag()+"") ;
                    strs[tag]=recordView.getText().toString().trim();
                }
            }
        });

        datetimeDialog.setArg(recordView.getText().toString().trim());
        datetimeDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void CheckData()
    {
        if(listView==null || listView.getAdapter()==null || listView.getAdapter().getCount()==0)
        {
            maskview.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else
        {
            maskview.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
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
                calendar.add(calendar.MONTH, -1);  //设置为前1月
                StartDay=sdf.format(calendar.getTime());
                PostData(init,StartDay,EndDay);
                break;
            case 4:
                calendar.add(calendar.MONTH, -3);  //设置为前3月
                StartDay=sdf.format(calendar.getTime());
                PostData(init, StartDay, EndDay);
                break;
        }
        LogTools.e("StartDay",StartDay);
    }
}
