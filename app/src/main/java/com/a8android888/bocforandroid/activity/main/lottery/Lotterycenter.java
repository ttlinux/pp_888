package com.a8android888.bocforandroid.activity.main.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.a8android888.bocforandroid.Adapter.Lottery_center_Adapter;
import com.a8android888.bocforandroid.Adapter.Lottery_room_Adapter;
import com.a8android888.bocforandroid.Adapter.SportsAdapter;
import com.a8android888.bocforandroid.BaseParent.BaseFragment;
import com.a8android888.bocforandroid.Bean.LotteryBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.MyJsonHttpResponseHandler;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/5.
 */
public class Lotterycenter extends BaseFragment{


    ListView listview;
    Lottery_center_Adapter adapter;
    ArrayList<LotteryBean> gameBeanList=new ArrayList<LotteryBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(),R.layout.lsitview_layout,null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listview=FindView(R.id.listview);
        getdata();
    }

    @Override
    public void OnViewShowOrHide(boolean state) {
        super.OnViewShowOrHide(state);
        if(!state&&getActivity()!=null){
            if (!gameBeanList.isEmpty()) {
                gameBeanList.clear();
                getdata();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void getdata() {

        Httputils.PostWithBaseUrl(Httputils.lotterycenter, null, new MyJsonHttpResponseHandler(this.getActivity(),true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.e("json", jsonObject + "");
                JSONArray json = jsonObject.optJSONArray("datas");
                if (!jsonObject.optString("errorCode").equalsIgnoreCase("000000"))
                    return;
                try {
                    for (int i = 0; i < json.length(); i++) {
                        JSONObject jsond = json.getJSONObject(i);
                        LotteryBean gameBean1 = new LotteryBean();
                        gameBean1.setGameCode(jsond.optString("gameCode"));
                        gameBean1.setDefaultItemId(jsond.optString("defaultItemId"));
                        gameBean1.setGameId(jsond.optString("gameId"));
                        gameBean1.setGameName(jsond.optString("gameName"));
                        gameBean1.setQs(jsond.optString("qs"));
                        gameBean1.setOpenCode(jsond.optString("openCode"));
                        gameBean1.setImg(jsond.optString("smallPic"));
                        gameBeanList.add(gameBean1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new Lottery_center_Adapter(Lotterycenter.this.getActivity(), gameBeanList);
                listview.setAdapter(adapter);

            }

            @Override
            public void onFailureOfMe(Throwable throwable, String s) {
                super.onFailureOfMe(throwable, s);
                throwable.printStackTrace();
            }
        });
    }
}
