package com.a8android888.bocforandroid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.BannerBean;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.ModuleBean;
import com.a8android888.bocforandroid.activity.main.ActicleListActivity;
import com.a8android888.bocforandroid.activity.main.BBNActivity;
import com.a8android888.bocforandroid.activity.main.CardActivity;
import com.a8android888.bocforandroid.activity.main.Electronic_gamesActivity;
import com.a8android888.bocforandroid.activity.main.GetWebdata;
import com.a8android888.bocforandroid.activity.main.JumpActivity;
import com.a8android888.bocforandroid.activity.main.Jumpdata;
import com.a8android888.bocforandroid.activity.main.Real_videoActivity;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_eventsActivity;
import com.a8android888.bocforandroid.activity.main.game.GameFragment;
import com.a8android888.bocforandroid.activity.main.gamewebActivity;
import com.a8android888.bocforandroid.activity.main.lottery.LotteryFragement;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.webActivity;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/15.
 */
public class HandleNotificationMethod {

     public static final String http="http://";
    public void modeluonclick2(BannerBean bean,Context context) {
        JumpActivity.modeluonclick(context, bean.getLinkType(),
                bean.getOpenLinkType(), bean.getTypeCode(),
                bean.getLevel(), bean.getCateCode(),
                bean.getGameCode(), bean.getBannerName(),
                bean.getBannerName(),  bean.getLinkUrl()
                , bean.getArticleType(), bean.getArticleId(),"pull","1" ,bean.getLinkGroupId());
    }
}
