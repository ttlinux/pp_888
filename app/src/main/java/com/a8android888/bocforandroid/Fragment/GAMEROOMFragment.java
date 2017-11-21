package com.a8android888.bocforandroid.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.BBIN_Adapter;
import com.a8android888.bocforandroid.Adapter.GameroomAdapter;
import com.a8android888.bocforandroid.Adapter.SportsAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.JumpActivity;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.a8android888.bocforandroid.view.ScollAlertdialog;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/5.游戏大厅
 */
public class GAMEROOMFragment extends BaseFragment{
    private ImageView back;
    TextView title;
    AnimatedExpandableListView expandablelistview;
    ArrayList<GameBean> gameBeanList= new ArrayList<GameBean>(),gameBeanList6;
    ArrayList< ArrayList<GameBean>> gameList=new ArrayList< ArrayList<GameBean>>();
    GameroomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return View.inflate(getActivity(), R.layout.fragment_gameroom, null);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initview();
    }
    private void initview() {
        back=(ImageView)FindView(R.id.back);
        back.setVisibility(View.GONE);
        title=(TextView)FindView(R.id.title);
        title.setText(this.getResources().getText(R.string.gametitile));
        expandablelistview=FindView(R.id.expandablelistview);
        expandablelistview.setGroupIndicator(null);
        expandablelistview.setOnGroupClickListener(new AnimatedExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (expandablelistview.isGroupExpanded(groupPosition)) {
                    expandablelistview.collapseGroupWithAnimation(groupPosition);
                } else {
                    expandablelistview.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
        getdata();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        LogTools.e("hidden1", state ? "YES" : "NO");
        if(!state&&getActivity()!=null){
            if (!gameBeanList.isEmpty()) {
                gameList.clear();
                gameBeanList6.clear();
                gameBeanList.clear();
                getdata();
            }

        }
    }
    private  void getdata(){

        RequestParams requestParams=new RequestParams();
//        requestParams.put("client",Httputils.client_server);
        Httputils.PostWithBaseUrl(Httputils.gameroomlist, null, new MyJsonHttpResponseHandler(this.getActivity(), true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("ddddddaaaaaa", jsonObject.toString());
//                JSONObject json = jsonObject.optJSONObject("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("datas");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonda = jsonArray.getJSONObject(i);
                        GameBean gameBean = new GameBean();
                        gameBean.setName(jsonda.optString("menuName", ""));
                        gameBean.setImg(jsonda.optString("bigPic", ""));
                        gameBean.setFlat(jsonda.optString("menuCode", ""));
                        gameBean.setBigBackgroundPic(jsonda.optString("smallBackgroundPic", ""));
                        gameBeanList6 = new ArrayList<GameBean>();
                        JSONArray jsonArray5 = jsonda.getJSONArray("flatMenuList");
                        for (int i1 = 0; i1 < jsonArray5.length(); i1++) {
                            JSONObject jsond = jsonArray5.getJSONObject(i1);
                            GameBean gameBean1 = new GameBean();
                            String game = jsond.optString("flatName", "");
                            gameBean1.setName(game);
                            gameBean1.setImg(jsond.optString("smallPic", ""));
                            gameBean1.setBigBackgroundPic(jsond.optString("smallBackgroundPic", ""));
                            gameBean1.setGamecode(jsond.optString("gameCode", ""));
                            gameBean1.setFlat(jsond.optString("flatCode", ""));
                            gameBeanList6.add(gameBean1);
                        }
                        if (!gameBeanList6.isEmpty()) {
                            gameList.add(gameBeanList6);
                            gameBeanList.add(gameBean/*jsonda.optString("flatName")*/);
                        }
                    }
//                        if (jsonda.optString("flat").equalsIgnoreCase("card")) {
//                            JSONArray jsonArray1 = jsonda.getJSONArray("menuInfo");
//                            gameBeanList2 = new ArrayList<GameBean>();
//                            for (int i2 = 0; i2 < jsonArray1.length(); i2++) {
//                                JSONObject jsond = jsonArray1.getJSONObject(i2);
//                                GameBean gameBean1 = new GameBean();
//                                String game = jsond.optString("flatName");
//                                gameBean1.setName(game);
//                                gameBean1.setImg(jsond.optString("mobileIconUrl"));
//                                gameBean1.setFlat(jsond.optString("flat"));
//
//                                if (jsond.optString("flat").equalsIgnoreCase("ag")) {
//                                    gameBean1.setGamecode("6");
//                                }
//                                gameBeanList2.add(gameBean1);
//                            }
//                            if (!gameBeanList2.isEmpty()) {
//                                gameList.add(gameBeanList2);
//                                gameStringList.add("棋牌游戏");
//                            }
//                        }
//                        if (jsonda.optString("flat").equalsIgnoreCase("card")) {
//                            JSONArray jsonArray2 = jsonda.getJSONArray("menuInfo");
//                                gameBeanList3 = new ArrayList<GameBean>();
//                                for (int i3 = 0; i3 < jsonArray2.length(); i3++) {
//                                    JSONObject jsond = jsonArray2.getJSONObject(i3);
//                                    GameBean gameBean1 = new GameBean();
//                                    String game = jsond.optString("flatName");
//                                    gameBean1.setName(game);
//                                    gameBean1.setImg(jsond.optString("mobileIconUrl"));
//                                    gameBean1.setFlat(jsond.optString("flat"));
//                                    gameBeanList3.add(gameBean1);
//
//                                }
//                                if (!gameBeanList3.isEmpty()) {
//                                    gameList.add(gameBeanList3);
//                                    gameStringList.add("体育赛事");
//                                }
//                            }
//                        if (jsonda.optString("flat").equalsIgnoreCase("slot")) {
//                            JSONArray jsonArray3 = jsonda.getJSONArray("menuInfo");
//                                gameBeanList4 = new ArrayList<GameBean>();
//                                for (int i4 = 0; i4 < jsonArray3.length(); i4++) {
//                                    JSONObject jsond = jsonArray3.getJSONObject(i4);
//                                    GameBean gameBean1 = new GameBean();
//                                    String game = jsond.optString("flatName");
//                                    gameBean1.setName(game);
//                                    gameBean1.setImg(jsond.optString("mobileIconUrl"));
//                                    gameBean1.setFlat(jsond.optString("flat"));
//                                    if (jsond.optString("flat").equalsIgnoreCase("ag")) {
//                                        gameBean1.setGamecode("11");
//                                    }
//                                    gameBeanList4.add(gameBean1);
//                                }
//                                if (!gameBeanList4.isEmpty()) {
//                                    gameList.add(gameBeanList4);
//                                    gameStringList.add("电子游戏");
//                                }
//
//                            }
//                            if (jsonda.optString("flat").equalsIgnoreCase("live")) {
//                                JSONArray jsonArray4 = jsonda.getJSONArray("menuInfo");
//                                gameBeanList5 = new ArrayList<GameBean>();
//                                for (int i5 = 0; i5 < jsonArray4.length(); i5++) {
//                                    JSONObject jsond = jsonArray4.getJSONObject(i5);
//                                    GameBean gameBean1 = new GameBean();
//                                    String game = jsond.optString("flatName");
//                                    gameBean1.setName(game);
//                                    gameBean1.setImg(jsond.optString("mobileIconUrl"));
//                                    gameBean1.setFlat(jsond.optString("flat"));
//                                    if (jsond.optString("flat").equalsIgnoreCase("ag")) {
//                                        gameBean1.setGamecode("2");
//                                    }
//                                    gameBeanList5.add(gameBean1);
//                                }
//                                if (!gameBeanList5.isEmpty()) {
//                                    gameList.add(gameBeanList5);
//                                    gameStringList.add("真人视讯");
//                                }
//                            }
//                        if (jsonda.optString("flat").equalsIgnoreCase("bbin")) {
//                            JSONArray jsonArray6 = jsonda.getJSONArray("menuInfo");
//                                gameBeanList = new ArrayList<GameBean>();
//                                for (int i6 = 0; i6 < jsonArray6.length(); i6++) {
//                                    JSONObject jsond = jsonArray6.getJSONObject(i6);
//                                    GameBean gameBean1 = new GameBean();
//                                    String game = jsond.optString("flatName");
//                                    gameBean1.setName(game);
//                                    gameBean1.setGamecode(jsond.optString("flat"));
//                                    gameBean1.setImg(jsond.optString("mobileIconUrl"));
//                                    gameBean1.setFlat(("bbin"));
//                                    gameBeanList.add(gameBean1);
//                                }
//                                if (!gameBeanList.isEmpty()) {
//                                    gameList.add(gameBeanList);
//                                    gameStringList.add("BBIN游戏");
//                                }
//                            }
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (adapter == null) {
                    adapter = new GameroomAdapter(GAMEROOMFragment.this.getActivity(), gameList, gameBeanList);
                    expandablelistview.setAdapter(adapter);
                } else
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }


}