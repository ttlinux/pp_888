package com.a8android888.bocforandroid.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_MainActivity;
import com.a8android888.bocforandroid.activity.main.game.GameFragment;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/25.
 */
public class Jumpdata {

    static ArrayList<GameBean> gameBeanList1 = new ArrayList<GameBean>();
    static  ArrayList<String> gameStringList = new ArrayList<String>();
    static ArrayList<String> gameflatList = new ArrayList<String>();
    static  String title="",flat="";
    //跳电子游戏时的数据
    public static void getdata(Context context,final String cateCode,final String pull) {
        RequestParams requestParams = new RequestParams();
//    requestParams.put("client",Httputils.client_server);
        Activity activity = null;
        if (!(context instanceof Activity)) {
            activity = ((BaseApplication) context.getApplicationContext()).getActivity();
            if (activity == null)
                return;
        } else {
            activity = (Activity) context;
        }
        Httputils.PostWithBaseUrl(Httputils.gameroomlist, null, new MyJsonHttpResponseHandler(activity, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("dddddd", jsonObject.toString());
                JSONObject json = jsonObject.optJSONObject("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
//            try {
//                JSONArray jsonArray = json.getJSONArray("slot");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsond = jsonArray.getJSONObject(i);
//                    GameBean gameBean1 = new GameBean();
//                    String game = jsond.optString("flatName");
//                    gameBean1.setName(game);
//                    gameBean1.setFlat(jsond.optString("flat"));
//                    gameBean1.setImg(jsond.optString("mobileIconUrl"));
//                    gameBean1.setGamecode(jsond.optString("gameCode"));
//                        gameBeanList.add(gameBean1);
//                        gameStringList.add(game);
//                        gameflatList.add(jsond.optString("flat"));
//                }
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("datas");
                    gameBeanList1 = new ArrayList<GameBean>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonda = jsonArray.getJSONObject(i);
                        if (jsonda.optString("menuCode", "").equalsIgnoreCase("electronic")) {
                            GameBean gameBean = new GameBean();
                            gameBean.setName(jsonda.optString("menuName", ""));
                            gameBean.setImg(jsonda.optString("bigPic", ""));
                            gameBean.setFlat(jsonda.optString("flat", ""));
                            gameBeanList1 = new ArrayList<GameBean>();
                            JSONArray jsonArray5 = jsonda.getJSONArray("flatMenuList");
                            for (int i1 = 0; i1 < jsonArray5.length(); i1++) {
                                JSONObject jsond = jsonArray5.getJSONObject(i1);
                                GameBean gameBean1 = new GameBean();
                                String game = jsond.optString("flatName", "");
                                gameBean1.setName(game);
                                gameBean1.setImg(jsond.optString("bigPic", "") + "?xxx=" + i1);
                                gameBean1.setGamecode(jsond.optString("gameCode", ""));
                                gameBean1.setFlat(jsond.optString("flatCode", ""));
                                if (jsond.optString("flatCode", "").equalsIgnoreCase(cateCode)) {
                                    title = jsond.optString("flatName", "");
                                    flat = jsond.optString("flatCode", "");
                                }
                                gameBeanList1.add(gameBean1);
                                if (!jsond.optString("flatCode", "").equalsIgnoreCase("ag")) {
                                    gameStringList.add(game);
                                    gameflatList.add(jsond.optString("flatCode"));
                                }
                            }
                        }
                    }
                    Intent intent = new Intent(context, GameFragment.class);
                    intent.putExtra("title", title);
                    intent.putStringArrayListExtra("list", gameStringList);
                    intent.putStringArrayListExtra("flatlist", gameflatList);
                    intent.putExtra("flat", flat);
                    if (pull.equalsIgnoreCase("pull")){
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }
    static  ArrayList<LotteryBean> gameBeanListlottery=new ArrayList<LotteryBean>();
    static ArrayList<String> gameStringListlottery=new ArrayList<String>();
    static ArrayList<String> gameflatListlottery=new ArrayList<String>();
    static  ArrayList<String> gamecodeListlottery=new ArrayList<String>();
    static String titlelottery="",codelottery="",idlottery="",defaultItemIdlottery="";
    //跳彩票时的数据
    public static void getdata2(Context context,final String cateCode,final String pull) {
        Activity activity = null;
        if (!(context instanceof Activity)) {
            activity = ((BaseApplication) context.getApplicationContext()).getActivity();
            if (activity == null)
                return;
        } else {
            activity = (Activity) context;
        }
        Httputils.PostWithBaseUrl(Httputils.lotterylist, null, new MyJsonHttpResponseHandler(activity,true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                JSONArray json = jsonObject.optJSONArray("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject jsond = json.getJSONObject(i);
                        LogTools.e("json", jsond + "");
                        LotteryBean gameBean1 = new LotteryBean();
                        gameBean1.setGameCode(jsond.optString("gameCode"));
                        gameBean1.setDefaultItemId(jsond.optString("defaultItemId"));
                        gameBean1.setGameId(jsond.optString("gameId"));
                        gameBean1.setGameName(jsond.optString("gameName"));
                        if(jsond.optString("gameCode", "").equalsIgnoreCase(cateCode)){
                            titlelottery=jsond.optString("gameName", "");
                            codelottery=jsond.optString("gameCode", "");
                            idlottery=jsond.optString("gameId", "");
                            defaultItemIdlottery=jsond.optString("defaultItemId", "");
                        }
                        gameBeanListlottery.add(gameBean1);
                        gameStringListlottery.add(jsond.optString("gameName"));
                        gameflatListlottery.add(jsond.optString("gameId"));
                        gamecodeListlottery.add(jsond.optString("gameCode"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent( context, Lottery_MainActivity.class);
                intent.putExtra("title",titlelottery);
                intent.putExtra("id", idlottery);
                intent.putExtra("code", codelottery);
                intent.putExtra("defaultItemId",defaultItemIdlottery);
                if (pull.equalsIgnoreCase("pull")){
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }
    //跳体育时的数据
    static   ArrayList<GameBean> gameBeansport=new ArrayList<GameBean>();
    static  ArrayList<String> gameStringListsport=new ArrayList<String>();
    static  ArrayList<String> gameflatListsport=new ArrayList<String>();
    static   String titlesport="",Platformsport="",listsport="";
    public static void getdatasport(Context context,final String cateCode,final String pull){

        RequestParams requestParams=new RequestParams();
        requestParams.put("client",Httputils.client_server);
        Activity activity = null;
        if (!(context instanceof Activity)) {
            activity = ((BaseApplication) context.getApplicationContext()).getActivity();
            if (activity == null)
                return;
        } else {
            activity = (Activity) context;
        }
        Httputils.PostWithBaseUrl(Httputils.gamelist, null, new MyJsonHttpResponseHandler(activity,true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("dddddd", jsonObject.toString());
                if(!jsonObject.optString("errorCode","").equalsIgnoreCase("000000"))return;
//                JSONObject json = jsonObject.optJSONObject("datas");
//                try {
//                    JSONArray jsonArray = json.getJSONArray("sport");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsond = jsonArray.getJSONObject(i);
//                        GameBean gameBean1 = new GameBean();
//                        String game = jsond.optString("flatName");
//                        gameBean1.setImg(jsond.optString("mobileIconUrl"));
//                        gameBean1.setName(game);
//                        gameBean1.setFlat(jsond.optString("flat"));
//                        gameBeanList.add(gameBean1);
//                        gameStringList.add(game);
//                        gameflatList.add(jsond.optString("flat"));
//                    }
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("datas");

                    gameBeansport = new ArrayList<GameBean>();
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonda = jsonArray.getJSONObject(i);
                        if( jsonda.optString("menuCode", "").equalsIgnoreCase("sport")) {
                            GameBean gameBean = new GameBean();
                            gameBean.setName(jsonda.optString("menuName", ""));
                            gameBean.setImg(jsonda.optString("bigPic", ""));
                            gameBean.setFlat(jsonda.optString("flat", ""));
                            gameBeansport = new ArrayList<GameBean>();
                            JSONArray jsonArray5 = jsonda.getJSONArray("flatMenuList");
                            for (int i1 = 0; i1 < jsonArray5.length(); i1++) {
                                JSONObject jsond = jsonArray5.getJSONObject(i1);
                                GameBean gameBean1 = new GameBean();
                                String game = jsond.optString("flatName", "");
                                gameBean1.setName(game);
                                gameBean1.setImg(jsond.optString("bigPic", ""));
                                gameBean1.setGamecode(jsond.optString("gameCode", ""));
                                gameBean1.setFlat(jsond.optString("flatCode", ""));
                                if(jsond.optString("gameCode", "").equalsIgnoreCase(cateCode)){
                                    titlesport=jsond.optString("flatName", "");
                                    Platformsport=jsond.optString("flatCode", "");
                                }
                                gameBeansport.add(gameBean1);
                                gameStringListsport.add(game);
                                gameflatListsport.add(jsond.optString("flatCode"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent( context, Sports_MainActivity.class);
                if (pull.equalsIgnoreCase("pull")){
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                intent.putExtra("title",titlesport);
                intent.putStringArrayListExtra("list", gameStringListsport);
                intent.putExtra(BundleTag.Platform, Platformsport);
                context.startActivity(intent);
            }

            @Override
            protected Object parseResponse(String s) throws JSONException {
                LogTools.e("dddddd111", s);
                return super.parseResponse(s);
            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }
}
