package com.a8android888.bocforandroid.activity.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.Change_Adapter;
import com.a8android888.bocforandroid.Adapter.Real_Video_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.真人视讯
 */
public class Real_videoActivity extends BaseActivity implements View.OnClickListener{

private ImageView back;
    TextView title;
    GridView gridView;
    Real_Video_Adapter adapter;
    ArrayList<GameBean> gameBeanList=new ArrayList<GameBean>();
    ArrayList<String> gameStringList=new ArrayList<String>();
    ArrayList<String> gameflatList=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_video);
        initview();
    }

    private void initview() {
        back=(ImageView)FindView(R.id.back);
        back.setOnClickListener(this);
        title=(TextView)FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.realvideotitle));
        gridView=(GridView)FindView(R.id.gridview);
//        adapter=new Real_Video_Adapter(this,getResources().getStringArray(R.array.videolist));
//        gridView.setAdapter(adapter);
        getdata();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
break;
        }

    }
    private  void getdata(){

        RequestParams requestParams=new RequestParams();
//        requestParams.put("client",Httputils.client_server);
        Httputils.PostWithBaseUrl(Httputils.gameroomlist, null, new MyJsonHttpResponseHandler(this,true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("dddddd", jsonObject.toString());
                JSONObject json = jsonObject.optJSONObject("datas");
//                try {
//                    JSONArray jsonArray = json.getJSONArray("live");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsond = jsonArray.getJSONObject(i);
//
//                        GameBean gameBean1 = new GameBean();
//                        String game = jsond.optString("flatName");
//                        gameBean1.setName(game);
//                        gameBean1.setImg(jsond.optString("mobileIconUrl"));
//                        gameBean1.setFlat(jsond.optString("flat"));
//                            gameBean1.setGamecode(jsond.optString("gameCode"));
//                            gameBeanList.add(gameBean1);
//                            gameStringList.add(game);
//                            gameflatList.add(jsond.optString("flat"));
//                    }
//                }
//
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("datas");

                    gameBeanList = new ArrayList<GameBean>();
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonda = jsonArray.getJSONObject(i);
                        if( jsonda.optString("menuCode", "").equalsIgnoreCase("live")) {
                            GameBean gameBean = new GameBean();
                            gameBean.setName(jsonda.optString("menuName", ""));
                            gameBean.setImg(jsonda.optString("bigPic", ""));
                            gameBean.setFlat(jsonda.optString("flat", ""));
                            gameBeanList = new ArrayList<GameBean>();
                            JSONArray jsonArray5 = jsonda.getJSONArray("flatMenuList");
                            for (int i1 = 0; i1 < jsonArray5.length(); i1++) {
                                JSONObject jsond = jsonArray5.getJSONObject(i1);
                                GameBean gameBean1 = new GameBean();
                                String game = jsond.optString("flatName", "");
                                gameBean1.setName(game);
                                gameBean1.setBigBackgroundPic(jsond.optString("bigBackgroundPic", ""));
                                gameBean1.setImg(jsond.optString("bigPic", ""));
                                gameBean1.setGamecode(jsond.optString("gameCode", ""));
                                gameBean1.setFlat(jsond.optString("flatCode", ""));
                                gameBeanList.add(gameBean1);
                            gameStringList.add(game);
                            gameflatList.add(jsond.optString("flatCode"));
                            }
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new Real_Video_Adapter(Real_videoActivity.this, gameBeanList,gameStringList,gameflatList,"1");
                gridView.setAdapter(adapter);

            }
            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }
}
