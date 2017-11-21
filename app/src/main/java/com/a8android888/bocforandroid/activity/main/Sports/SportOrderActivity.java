package com.a8android888.bocforandroid.activity.main.Sports;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.SportsOrderListAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.OrderJson;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.thirdparty.swipemenulistview.SwipeMenu;
import com.a8android888.bocforandroid.thirdparty.swipemenulistview.SwipeMenuCreator;
import com.a8android888.bocforandroid.thirdparty.swipemenulistview.SwipeMenuItem;
import com.a8android888.bocforandroid.thirdparty.swipemenulistview.SwipeMenuListView;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.HttpforNoticeinbottom;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MD5Util;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/20.
 */
public class SportOrderActivity extends BaseActivity{

    SwipeMenuListView dragSortListView;
    String data;
    static boolean Continue=false;
    SportsOrderListAdapter sportsOrderListAdapter;
//     HashMap<String, JSONObject> jsonObjects=new HashMap<String, JSONObject>();
    EditText money;
    Button comit;
    TextView winmoney,balltype;
    double peilv[];
    int arg[];
    LinearLayout bottomview;
    ArrayList<OrderJson> JSONarrs=new  ArrayList<OrderJson>();
    TextView balance,name,textmin,textmax;
    int min=-1,max=-1;
    JSONObject jsonObject_data=null;
    SharedPreferences NOT_CountinueMode,CountinueMode;
    TextView chuanvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_order);

        NOT_CountinueMode=getSharedPreferences(BundleTag.NOT_CountinueMode, Activity.MODE_PRIVATE);
        CountinueMode=getSharedPreferences(BundleTag.CountinueMode, Activity.MODE_PRIVATE);
        name=FindView(R.id.name);
        balance=FindView(R.id.balance);
        TextView textView=FindView(R.id.title);
        textView.setText("确认注单");

        comit=FindView(R.id.comit);
        comit.setVisibility(View.INVISIBLE);
        bottomview=FindView(R.id.bottomview);
        balltype=FindView(R.id.balltype);

        comit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GetOrder();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Intent intent=getIntent();
        data=intent.getStringExtra(BundleTag.OrderJson);
        if(data!=null)
        LogTools.e("datadatadata", data);
        Continue=intent.getBooleanExtra(BundleTag.Continue, false);
        SetBalance();//请求余额
        if(data==null)
        {
            //当是购物车点进来的时候
            SetArrdata();//数据缓存到内存
            if(JSONarrs.size()==0)
            {
                bottomview.setVisibility(View.GONE);
                balltype.setVisibility(View.GONE);
                ToastUtil.showMessage(SportOrderActivity.this,"暂无数据");
                return;
            }
            if(Continue && JSONarrs.size()<3)
            {
                bottomview.setVisibility(View.GONE);
            }
            else
            {
                comit.setVisibility(View.VISIBLE);
            }
            InitListview();//初始化listview
            if(JSONarrs.size()>0)
            {
                String maxstr=JSONarrs.get(0).getJsonObject().optString("maxPrice","");
                String minstr=JSONarrs.get(0).getJsonObject().optString("minPrice","");
                textmax.setText(getString(R.string.maxlimit).replace("%s", maxstr));
                textmin.setText(getString(R.string.minlimit).replace("%s", minstr));
            }
            setAdapter();
        }
        else
        {
            //下单点进来的
            if(!Continue)
            {
                NOT_CountinueMode.edit().clear().commit();
            }
            else
            {
                try {
                    JSONObject datajson=new JSONObject(data);
                    FilterCountinueMode(datajson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Sport_detail();//请求详情 初始化listview
        }


    }

    private void FilterCountinueMode(JSONObject data)//综合过关模式
    {
        Object[] objs=CountinueMode.getAll().values().toArray();
        if(objs.length<1)return;
        try {
            JSONObject json=new JSONObject ((String)objs[0]);
            JSONObject Realjson=json.optJSONObject(BundleTag.JsonObject);
            if(!Realjson.optString("BallType", "").equalsIgnoreCase(data.optString("BallType", "")))//球类覆盖球类
            {
                CountinueMode.edit().clear().commit();
            }
            if(!Realjson.optString("timeType", "").equalsIgnoreCase(data.optString("timeType", "")))//滚球今日早盘互相覆盖
            {
                CountinueMode.edit().clear().commit();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void ListviewFooter(View view)
    {
        textmin=(TextView)view.findViewById(R.id.textmin);
        textmax=(TextView)view.findViewById(R.id.textmax);
        winmoney=(TextView)view.findViewById(R.id.winmoney);
        money=(EditText)view.findViewById(R.id.money);

        if(Continue)
        {
            LinearLayout chuanguanlayout=(LinearLayout)view.findViewById(R.id.chuanguanlayout);
            chuanvalue=(TextView)view.findViewById(R.id.chuanvalue);
            chuanguanlayout.setVisibility(View.VISIBLE);
            double peilvs = 1;
            for (int i = 0; i < peilv.length; i++) {
                peilvs = peilvs * peilv[i];
            }
            chuanvalue.setText("@ "+Httputils.Limit2(peilvs));
        }


        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String moneystr = money.getText().toString().trim();
                if (moneystr.equalsIgnoreCase("")) {
                    winmoney.setText("");
                    return;
                }
                int moneyc;
                try {
                    moneyc = Integer.valueOf(moneystr);
                    if (!Continue) {
                        double money = (peilv[0] - arg[0]) * moneyc;
                        winmoney.setText(Httputils.Limit2(money) + "元");
                    } else {
                        double peilvs = 1;
                        for (int i = 0; i < peilv.length; i++) {
                            peilvs = peilvs * peilv[i];
                        }
                        peilvs = (peilvs - 1) * moneyc;

                        winmoney.setText(Httputils.Limit2(peilvs) + "元");
                    }

                } catch (Exception except) {
                    except.printStackTrace();
                    winmoney.setText("0元");
                }
            }
        });
    }

    private void SetBalance()
    {
        BaseApplication baseApplication=(BaseApplication)getApplication();
        String username=baseApplication.getBaseapplicationUsername();
        name.setText(username);
        Httputils.GetBalance(username, new Httputils.BalanceListener() {
            @Override
            public void OnRecevicedata(String balanced) {
                double blance = Double.valueOf(balanced);
                try {
                    blance = Double.valueOf(balanced);
                    balance.setText(blance + "");

                } catch (Exception exception) {

                }
            }
            @Override
            public void Onfail(String str) {

            }
        }, this);
    }

    private void setAdapter()
    {
        if(sportsOrderListAdapter==null)
        {
//            SetArrdata(jsonObjects);
            sportsOrderListAdapter=new SportsOrderListAdapter(this,JSONarrs);
            sportsOrderListAdapter.setDeleteItemListener(new SportsOrderListAdapter.DeleteItemListener() {
                @Override
                public void ondelete(int position) {
                    deletemethod(position);
                }
            });
            dragSortListView.setAdapter(sportsOrderListAdapter);
        }
        else
        {
//            SetArrdata(jsonObjects);
            sportsOrderListAdapter.NotifyAdapter(JSONarrs);
        }
    }

    private void SetArrdata()
    {
        JSONarrs.clear();
        SharedPreferences temp=Continue?CountinueMode:NOT_CountinueMode;
        Map<String,?> maps= temp.getAll();
        ArrayList<String> expireJsons=new ArrayList<>();
        LogTools.e("JSONarrs_maps", maps.size() + "");
            for (Map.Entry<String, ?> entry : maps.entrySet()) {
                //Map.entry<Integer,String> 映射项（键-值对）  有几个方法：用上面的名字entry
                //entry.getKey() ;entry.getValue(); entry.setValue();
                //map.entrySet()  返回此映射中包含的映射关系的 Set视图。
                try {
                    JSONObject jsonobj = new JSONObject((String) maps.get(entry.getKey()));
                    long time= jsonobj.optJSONObject(BundleTag.JsonObject).optLong("Createtime",0);
                    if(System.currentTimeMillis()-time>30*60*1000)
                    {
                        //超过半小时没动静 干掉
                        expireJsons.add(entry.getKey());
                        continue;
                    }
                    OrderJson orderjson = new OrderJson();
                    orderjson.setPosition(entry.getKey());
                    orderjson.setJsonObject(jsonobj);
                    JSONarrs.add(orderjson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        //删掉过期的
        for (int i = 0; i < expireJsons.size(); i++) {
            temp.edit().remove(expireJsons.get(i)).commit();
        }
        if(JSONarrs.size()>0)
        {
            JSONObject FJson= JSONarrs.get(0).getJsonObject();
            balltype.setText(FJson.optString("ballType", "") + " - " + FJson.optString("timeType", ""));
        }
        Collections.sort(JSONarrs, new MyComparator()) ;
    }
    class MyComparator implements Comparator {

        public int compare(Object o1,Object o2) {
            OrderJson json1=(OrderJson)o1;
            OrderJson json2=(OrderJson)o2;
            long time1= json1.getJsonObject().optJSONObject(BundleTag.JsonObject).optLong("Createtime",0);
            long time2= json2.getJsonObject().optJSONObject(BundleTag.JsonObject).optLong("Createtime",0);
            if(time1>time2)
                return 1;
            else
                return -1;
        }
    }

    private void GetOrder() throws JSONException {
        if(min<0 || max <0)
        {
            ToastUtil.showMessage(this,"下注金额限制数据缺失");
//            return;
        }
        String moneystr=money.getText().toString().trim();
        int moneyc;
        try
        {
            moneyc=Integer.valueOf(moneystr);
            if(min>0  &&  moneyc<min )
            {
                ToastUtil.showMessage(this,"下注金额必须大于等于"+min);
                return;
            }
            if(max>0  &&  moneyc>max )
            {
                ToastUtil.showMessage(this,"下注金额必须小于等于"+max);
                return;
            }
        }
        catch (Exception except)
        {
            except.printStackTrace();
            ToastUtil.showMessage(this, getString(R.string.orderlimit2));
            return;
        }

        if(JSONarrs.size()==0)
        {
            ToastUtil.showMessage(this, getString(R.string.tips));
            return;
        }
        if(JSONarrs.size()<3 && Continue)
        {
            ToastUtil.showMessage(this, getString(R.string.tips2));
            return;
        }
        RequestParams params=new RequestParams();
        if(JSONarrs.size()==1)
        {

            JSONObject jsonObject=JSONarrs.get(0).getJsonObject().optJSONObject(BundleTag.JsonObject);
            StringBuilder stringBuilder=new StringBuilder();
            params.put("gid", jsonObject.optString("gid", ""));
            stringBuilder.append(jsonObject.optString("gid", ""));
            stringBuilder.append("|");

            params.put("timeType", jsonObject.optString("timeType", ""));
            stringBuilder.append(jsonObject.optString("timeType", ""));
            stringBuilder.append("|");

            params.put("rType", jsonObject.optString("rType", ""));
            stringBuilder.append(jsonObject.optString("rType", ""));
            stringBuilder.append("|");

            params.put("bType", jsonObject.optString("bType", ""));
            stringBuilder.append(jsonObject.optString("bType", ""));
            stringBuilder.append("|");

            params.put("dType", jsonObject.optString("dType", ""));
            stringBuilder.append(jsonObject.optString("dType", ""));
            stringBuilder.append("|");

            params.put("selection", jsonObject.optString("selection", ""));
            stringBuilder.append(jsonObject.optString("selection", ""));
            stringBuilder.append("|");

            params.put("period", jsonObject.optString("period", ""));
            stringBuilder.append(jsonObject.optString("period", ""));
            stringBuilder.append("|");

            params.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
            stringBuilder.append(((BaseApplication) this.getApplication()).getBaseapplicationUsername());
            stringBuilder.append("|");

            params.put("money", Httputils.Limit2(Double.valueOf(money.getText().toString().trim()) * 1.00d));
            stringBuilder.append(Httputils.Limit2(Double.valueOf(money.getText().toString().trim()) * 1.00d));

            params.put("signature", MD5Util.sign(stringBuilder.toString(),Httputils.androidsecret));
        }
        else
        {
            String timetype=null;
            JSONArray jsonArray=new JSONArray();
            String rtype=null;
            for (int i = 0; i < JSONarrs.size(); i++) {
                JSONObject jsonObject=JSONarrs.get(i).getJsonObject().optJSONObject(BundleTag.JsonObject);
                if(timetype==null)timetype=jsonObject.optString("timeType","");
                if(rtype==null)rtype=jsonObject.optString("rType","");
                JSONObject temponject=new JSONObject();
                temponject.put("gid",jsonObject.optString("gid",""));
                temponject.put("btype",jsonObject.optString("bType",""));
                temponject.put("dtype",jsonObject.optString("dType",""));
                temponject.put("selection",jsonObject.optString("selection",""));
                temponject.put("period",jsonObject.optString("period",""));
                jsonArray.put(temponject);
            }
            params.put("timeType",timetype);
            params.put("userName", ((BaseApplication) this.getApplication()).getBaseapplicationUsername());
            params.put("money", Httputils.Limit2(Double.valueOf(money.getText().toString().trim()) * 1.00d));
            params.put("rType",rtype);
            params.put("p3Params", jsonArray.toString().trim());

            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(((BaseApplication) this.getApplication()).getBaseapplicationUsername());
            stringBuilder.append("|");

            stringBuilder.append(timetype);
            stringBuilder.append("|");

            stringBuilder.append(rtype);
            stringBuilder.append("|");

            stringBuilder.append(Httputils.Limit2(Double.valueOf(money.getText().toString().trim()) * 1.00d));
            stringBuilder.append("|");

            stringBuilder.append(jsonArray.toString().trim());

            params.put("signature",  MD5Util.sign(stringBuilder.toString(), Httputils.androidsecret));
        }

        comit.setEnabled(false);
        Httputils.PostWithBaseUrl(Httputils.sportorder, params, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                comit.setEnabled(true);
//                LogTools.e("jsonObject", s);
//                if(s!=null)
//                ToastUtil.showMessage(SportOrderActivity.this, getString(R.string.commitorderfail));
            }

            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                comit.setEnabled(true);
                LogTools.e("jsonObject", jsonObject.toString());
                if (jsonObject.optString("errorCode", "").equalsIgnoreCase("000000")) {
                    ToastUtil.showMessage(SportOrderActivity.this, getString(R.string.ordersuccess));
                    NOT_CountinueMode.edit().clear().commit();
                    CountinueMode.edit().clear().commit();
//                    setAdapter();
                    finish();
                } else {
                    ToastUtil.showMessage(SportOrderActivity.this, jsonObject.optString("msg", ""));
                    //getString(R.string.commitorderfail)
                }
            }
        });
    }


    private void Initlistview()
    {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getApplicationContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                // set item width
//                openItem.setWidth(ScreenUtils.getDIP2PX(SportOrderActivity.this,90));
//                // set item title
//                openItem.setTitle("Open");
//                // set item title fontsize
//                openItem.setTitleSize(18);
//                // set item title font color
//                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(ScreenUtils.getDIP2PX(SportOrderActivity.this, 90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        dragSortListView.setMenuCreator(creator);
        dragSortListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                jsonObjects.remove(position);
                deletemethod(position);
                return false;
            }
        });
    }


    public void deletemethod(int position)
    {
        if(!Continue)
        {
            NOT_CountinueMode.edit().remove(String.valueOf(JSONarrs.get(position).getPosition())).commit();
        }
        else
        {
            CountinueMode.edit().remove(String.valueOf(JSONarrs.get(position).getPosition())).commit();
        }
        JSONarrs.remove(position);
        setAdapter();
        if(Continue && JSONarrs.size()>2)
        {
            bottomview.setVisibility(View.VISIBLE);
        }
        else
        {
            bottomview.setVisibility(View.GONE);
        }
        if(JSONarrs.size()==0)
        {
            finish();
        }
    }


    private void Sport_detail()
    {
        if( data!=null)
        {
            try {
                jsonObject_data=new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {

        }


        LogTools.e("jsonObject333", jsonObject_data.toString());
        RequestParams requestParams=new RequestParams();
            requestParams.put("gid",jsonObject_data.optString("gid",""));
            requestParams.put("timeType",jsonObject_data.optString("timeType",""));
            requestParams.put("rType",jsonObject_data.optString("rType",""));
            requestParams.put("bType",jsonObject_data.optString("bType",""));
            requestParams.put("dType",jsonObject_data.optString("dType",""));
            requestParams.put("selection", jsonObject_data.optString("selection", ""));
            requestParams.put("period", jsonObject_data.optString("period", ""));


        Httputils.PostWithBaseUrl(Httputils.Sport_detail,requestParams,new MyJsonHttpResponseHandler(this,true){
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("jsonObject222", jsonObject.toString());
                if(jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                {
                    JSONObject datas=jsonObject.optJSONObject("datas");
                    SportOrderActivity.this.max=datas.optInt("maxPrice");
                    SportOrderActivity.this.min=datas.optInt("minPrice");
                    LogTools.e("max"+max,"min"+min);
//                    SportOrderActivity.this.min=(int)(Double.parseDouble(min));
//                    SportOrderActivity.this.max=Integer.parseInt(max);

                    try {
                        datas.put(BundleTag.JsonObject,jsonObject_data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(Continue)
                    {
                        SaveJsonobjectInCoutinueMode(datas);
                    }
                    else
                    {
                        SaveJsonobjectIn_NOT_CoutinueMode(datas);
                    }

                    SetArrdata();
                    LogTools.e("JSONarrs", JSONarrs.size() + "");
                    InitListview();
                    textmax.setText(getString(R.string.maxlimit).replace("%s", max+""));
                    textmin.setText(getString(R.string.minlimit).replace("%s", min+""));


                    if(Continue && JSONarrs.size()<3)
                    {
                        bottomview.setVisibility(View.GONE);
                    }
                    else
                    {
                        comit.setVisibility(View.VISIBLE);
                    }

                    Initlistview();
                    setAdapter();
                }
                else
                {
                    ToastUtil.showMessage(SportOrderActivity.this,jsonObject.optString("msg"));
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
            }
        });
    }

    private void SaveJsonobjectInCoutinueMode(JSONObject jsonobj)//过关保存到本地数据 json是 本地数据+服务器数据的json
    {
//         Map<String,?> maps= CountinueMode.getAll();
//        if(maps==null || maps.size()==0)
//        {
//            CountinueMode.edit().putString("0",jsonobj.toString()).commit();
//            return;
//        }
//        for (int i = 0; i <maps.size(); i++) {
//            String jsonstr=(String)maps.get(String.valueOf(i));
//            try {
//                JSONObject jsonObject=new JSONObject(jsonstr);
//                String Local_gidm=jsonObject.optJSONObject(BundleTag.JsonObject).optString("gidm", "");
//
//                if(Local_gidm.equalsIgnoreCase(Net_gidm))
//                {
//                    CountinueMode.edit().putString(String.valueOf(i),jsonobj.toString()).commit();
//                    return;
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        String Net_gidm=jsonobj.optJSONObject(BundleTag.JsonObject).optString("gidm", "");
        String value=CountinueMode.getString(Net_gidm,"");
        if(value!=null)
        {
            try {
                JSONObject valuestr=new JSONObject(value);
                long createtime=valuestr.optJSONObject(BundleTag.JsonObject).optLong("Createtime",0);
                jsonobj.optJSONObject(BundleTag.JsonObject).put("Createtime",createtime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        CountinueMode.edit().putString(Net_gidm, jsonobj.toString()).commit();
    }

    private void SaveJsonobjectIn_NOT_CoutinueMode(JSONObject jsonobj)//过关保存到本地数据 json是 本地数据+服务器数据的json
    {
        String Net_gidm=jsonobj.optJSONObject(BundleTag.JsonObject).optString("gidm", "");
        NOT_CountinueMode.edit().putString(Net_gidm,jsonobj.toString()).commit();
    }

    private void InitListview()
    {
//        final int argstr=Integer.valueOf(jsonObject_data.optString("arg", ""));
        peilv=new double[JSONarrs.size()];
        arg=new int[JSONarrs.size()];

        for (int i = 0; i < JSONarrs.size(); i++) {
            peilv[i]=Double.valueOf(JSONarrs.get(i).getJsonObject().optString("odds",""));
            arg[i]=Integer.valueOf(JSONarrs.get(i).getJsonObject().optJSONObject(BundleTag.JsonObject).optString("arg", ""));
            LogTools.e("peilv[i]arg[i]",peilv[i]+" "+arg[i]);
        }

        dragSortListView=FindView(R.id.dragSortListView);
        View view=View.inflate(SportOrderActivity.this,R.layout.sport_order_footer,null);
        dragSortListView.addFooterView(view);

        ListviewFooter(view);
    }
}
