package com.a8android888.bocforandroid.activity.user.Message;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.MessageAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.Messagebean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.PublicDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/29.
 */
public class MessageActivity extends BaseActivity{

    ListView listview,listview2;
    SwipeRefreshLayout swipl,swipl2;
    ArrayList<Messagebean> messagebeans=new ArrayList<Messagebean>();
    ArrayList<Messagebean> messagebeans2=new ArrayList<Messagebean>();
    RadioGroup radiogroup;
    private MessageAdapter messageAdapter,messageAdapter2;
    int mtotalItemCount,mfirstVisibleItem,page=1;
    int mtotalItemCount2,mfirstVisibleItem2,page2=1;
    boolean hasdata=true,hasdata2=true;
    String type="1";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== BundleTag.ResultOk)
        {
            if(messageAdapter2!=null && data!=null)
            {
                int position=data.getIntExtra(BundleTag.Position,0);
                messagebeans2.get(position).setReadStatus(1);
                messageAdapter2.notifyadapter(messagebeans2);
                boolean status=false;
                for (Messagebean bean: messagebeans2)
                {
                    if(bean.getReadStatus()==0)
                    {
                        //有未读
                        status=true;
                    }
                    break;
                }
                SetradiobtnStatus(status);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        TextView title=FindView(R.id.title);
        title.setText(getString(R.string.membermessage));
        SystemMessage();
        UserMessage();
        HandlerRadioButton();

        PostData(true);
        PostData2(true);
    }

    private void UserMessage()
    {
        listview2=FindView(R.id.listview2);
        listview2.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mtotalItemCount2 - mfirstVisibleItem2 < 11 && scrollState == 0 && hasdata2) {
                    LogTools.e("runing", "runing");
                    page2++;
                    PostData2(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mfirstVisibleItem2 = firstVisibleItem;
                mtotalItemCount2 = totalItemCount;
            }
        });
        swipl2=FindView(R.id.swipl2);
        swipl2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PostData2(true);
            }
        });
    }

    private void SystemMessage()
    {
        listview=FindView(R.id.listview);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (mtotalItemCount - mfirstVisibleItem < 11 && scrollState == 0 && hasdata) {
                    page++;
                    PostData(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mfirstVisibleItem = firstVisibleItem;
                mtotalItemCount = totalItemCount;
            }
        });
        swipl=FindView(R.id.swipl);
        swipl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PostData(true);
            }
        });
    }

    private void HandlerRadioButton()
    {
        radiogroup=FindView(R.id.radiogroup);
        RadioButton radioButton=(RadioButton) radiogroup.getChildAt(0);
        RadioButton radioButton1=(RadioButton) radiogroup.getChildAt(1);
        radioButton.setText(getString(R.string.systemmessage));
        radioButton1.setText(getString(R.string.personalmessage));
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
                radioButton.setTextColor(getResources().getColor(R.color.white));
                if (checkedId == R.id.rb1) {
                    radioButton = (RadioButton) findViewById(R.id.rb2);
                    radioButton.setTextColor(getResources().getColor(R.color.black));
                    swipl.setVisibility(View.VISIBLE);
                    swipl2.setVisibility(View.GONE);
                } else {
                    radioButton = (RadioButton) findViewById(R.id.rb1);
                    radioButton.setTextColor(getResources().getColor(R.color.black));
                    swipl.setVisibility(View.GONE);
                    swipl2.setVisibility(View.VISIBLE);
                    if (messagebeans2.size() < 1 && hasdata2) {
                        LogTools.e("2222", "2222");
                        PostData2(true);
                    }
                }

            }
        });
    }

    private void PostData2(final boolean init)//用户信息
    {
        if(init){page2=1;

        }
        RequestParams requestParams=new RequestParams();
        requestParams.put("type", "2");
        requestParams.put("pageNo", page2 + "");
        requestParams.put("pageSize", "20");

        Httputils.PostWithBaseUrl(Httputils.UserMessage, requestParams, new MyJsonHttpResponseHandler(this,false) {
            @Override
            public synchronized void onSuccessOfMe(JSONObject response) {
                super.onSuccessOfMe(response);
                LogTools.e("response", response.toString());
                if (init) {
                    hasdata2 = true;
                    messagebeans2.clear();
                }
                JSONArray jsonArray = response.optJSONArray("datas");
                boolean unreadMsgFlag=false;

                if (jsonArray == null || jsonArray.length()==0) {
                    hasdata2 = false;
                    if(listview2.isShown() && page2>1)
                        ToastUtil.showMessage(MessageActivity.this, getString(R.string.nomoredata));
                    swipl.setRefreshing(false);
                    return;
                }

                if (jsonArray == null || jsonArray.length() < 1) {
                    hasdata2 = false;
                    if (messagebeans2.size() == 0 && listview2.isShown())
                        ToastUtil.showMessage(MessageActivity.this, getString(R.string.nomoredata));
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsobject = jsonArray.optJSONObject(i);
                        Messagebean messagebean = new Messagebean();
                        messagebean.setCreateTime(jsobject.optString("createTime", ""));
                        messagebean.setId(jsobject.optInt("id"));
                        messagebean.setMsgContent(jsobject.optString("msgContent", ""));
                        messagebean.setReadStatus(jsobject.optInt("readStatus"));
                        if(messagebean.getReadStatus()<1)
                            unreadMsgFlag=true;
//                        messagebean.setUserName(jsobject.optString("userName", ""));
                        messagebean.setMsgTitle(jsobject.optString("msgTitle", ""));
                        messagebeans2.add(messagebean);
                    }
                    SetradiobtnStatus(unreadMsgFlag);
                }

                if (messageAdapter2 == null) {
                    messageAdapter2 = new MessageAdapter(MessageActivity.this, messagebeans2, true, new MessageAdapter.OnDeleteListener() {
                        @Override
                        public void OnDelete(int id,int position) {
                            DeleteDMessage(id,position);
                        }
                    });
                    listview2.setAdapter(messageAdapter2);
                } else {
                    messageAdapter2.notifyadapter(messagebeans2);
                }
                swipl2.setRefreshing(false);

            }

//            @Override
//            public void onFailure(Throwable throwable, JSONObject jsonObject) {
//                super.onFailure(throwable, jsonObject);
//                swipl2.setRefreshing(false);
//                hasdata2 = false;
//            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipl.setRefreshing(false);
                hasdata2 = false;
            }
        });
    }

    private void PostData(final boolean init)//系统信息
    {
        if(init){page=1;
        }
        RequestParams requestParams=new RequestParams();
        requestParams.put("type", "1");
        requestParams.put("pageNo", page+"");
        requestParams.put("pageSize", "20");

        Httputils.PostWithBaseUrl(Httputils.UserMessage,requestParams,new MyJsonHttpResponseHandler(this,false)
        {
            @Override
            public synchronized  void onSuccessOfMe(JSONObject response) {
                super.onSuccessOfMe(response);
                LogTools.e("response222", response.toString());
                if(init)
                {
                    hasdata=true;
                    messagebeans.clear();
                }
                JSONArray jsonArray=response.optJSONArray("datas");
                if(jsonArray==null || jsonArray.length()==0)
                {
                    hasdata=false;
                    ToastUtil.showMessage(MessageActivity.this, getString(R.string.nomoredata));
                    swipl.setRefreshing(false);
                    return;
                }

                if(jsonArray==null || jsonArray.length()<1)
                {
                    hasdata=false;
                    if(messagebeans.size()==0)
                        ToastUtil.showMessage(MessageActivity.this, getString(R.string.nomoredata));
                }
                else
                {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsobject= jsonArray.optJSONObject(i);
                        Messagebean messagebean=new Messagebean();
                        messagebean.setCreateTime(jsobject.optString("createTime",""));
                        messagebean.setId(jsobject.optInt("id"));
                        messagebean.setMsgContent(jsobject.optString("msgContent",""));
                        messagebean.setReadStatus(jsobject.optInt("readStatus"));
//                        messagebean.setUserName(jsobject.optString("userName",""));
                        messagebean.setMsgTitle(jsobject.optString("msgTitle",""));
                        messagebeans.add(messagebean);
                    }

                }

                if(messageAdapter==null)
                {
                    messageAdapter=new MessageAdapter(MessageActivity.this,messagebeans,false);
                    listview.setAdapter(messageAdapter);
                }
                else
                {
                    messageAdapter.notifyadapter(messagebeans);
                }
                swipl.setRefreshing(false);

            }

//            @Override
//            public void onFailure(Throwable throwable, JSONObject jsonObject) {
//                super.onFailure(throwable, jsonObject);
//                swipl.setRefreshing(false);
//                hasdata=false;
//            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                swipl.setRefreshing(false);
                hasdata=false;
            }
        });
    }

    public void DeleteDMessage(int id,final int position) {
        RequestParams params = new RequestParams();
        params.put("userName",((BaseApplication)this.getApplication()).getUsername());
        params.put("code",id+"");
        Httputils.PostWithBaseUrl(Httputils.DeleteMessages,params, new MyJsonHttpResponseHandler(this,false) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("ddddjsonObject",jsonObject.toString());
                if(jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))
                {
                    messagebeans2.remove(position);
                    messageAdapter2.notifyadapter(messagebeans2);
                }
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String jsonObject) {
                super.onFailureOfMe(throwable, jsonObject);
                throwable.printStackTrace();
            }

        });
    }

    public void SetradiobtnStatus(boolean status)
    {
        RadioButton radioButton=(RadioButton) radiogroup.getChildAt(1);
        Drawable drawable = getResources().getDrawable(R.drawable.lottery_red);
        if(status)
        {
            drawable.setBounds(0, 0, ScreenUtils.getDIP2PX(this,10),ScreenUtils.getDIP2PX(this,10) );//必须设置图片大小，否则不显示
            radioButton.setCompoundDrawables(drawable, null, null, null);
        }
        else
        {
            radioButton.setCompoundDrawables(null, null, null, null);
        }
    }
}
