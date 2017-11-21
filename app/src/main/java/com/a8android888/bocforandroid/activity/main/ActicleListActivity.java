package com.a8android888.bocforandroid.activity.main;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Adapter.acticlelist_Adapter;
import com.a8android888.bocforandroid.BaseParent.BaseActivity;
import com.a8android888.bocforandroid.Bean.ModuleBean;
import com.a8android888.bocforandroid.R;
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
 * Created by Administrator on 2017/5/20.
 */
public class ActicleListActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView back;
    ListView listview;
    GridView gridView;
    ArrayList<ModuleBean> gameBeanList;
    acticlelist_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticlelist_main);
        title = (TextView) FindView(R.id.title);
        title.setText(getIntent().getStringExtra(BundleTag.title));
        back = (ImageView) FindView(R.id.back);
        back.setOnClickListener(this);
        listview=(ListView)FindView(R.id.lsitview2);
        gridView=(GridView)FindView(R.id.lsitview);
        acticle(getIntent().getStringExtra(BundleTag.id));
    }
    private  void acticle(String id){
        RequestParams requestParams=new RequestParams();
        String stringurl="";
        if(getIntent().getStringExtra(BundleTag.bannerList).equalsIgnoreCase("2")) {
            requestParams.put("articleType", id);
            requestParams.put("articleId", getIntent().getStringExtra(BundleTag.articleId));
            stringurl=Httputils.articlelist;
        }
        else {
            requestParams.put("articleType", id);
            requestParams.put("linkGroupId", getIntent().getStringExtra(BundleTag.articleId));
            stringurl=Httputils.articleGroup;
        }
        Httputils.PostWithBaseUrl(stringurl, requestParams, new MyJsonHttpResponseHandler(this, true) {
            @Override
            public void onSuccessOfMe(JSONObject jsonObject) {
                super.onSuccessOfMe(jsonObject);
                LogTools.ee("dddddafadfadf", jsonObject.toString());
                try {
                        gameBeanList = new ArrayList<ModuleBean>();
                        JSONArray jsonArray5 = jsonObject.getJSONArray("datas");
                        for (int k = 0; k < jsonArray5.length(); k++) {
                            JSONObject jsond = jsonArray5.getJSONObject(k);
                            ModuleBean gameBean1 = new ModuleBean();
                            gameBean1.setCreateTime(jsond.optString("createTime", ""));
                            gameBean1.setArticleBigImages(jsond.optString("articleBigImages", ""));
                            gameBean1.setArticleCode(jsond.optString("articleCode", ""));
                            gameBean1.setLinkType(jsond.optString("linkType", ""));
                            gameBean1.setLinkUrl(jsond.optString("linkUrl", ""));
                            gameBean1.setLevel(jsond.optString("level", ""));
                            gameBean1.setOpenLinkType(jsond.optString("openLinkType", ""));
                            gameBean1.setArticleSmallImages(jsond.optString("articleSmallImages", ""));
                            gameBean1.setArticleBigHeight(jsond.optString("articleBigHeight", ""));
                            gameBean1.setArticleSmallHeight(jsond.optString("articleSmallHeight", ""));
                            gameBean1.setArticleId(jsond.optString("articleId", ""));
                            gameBean1.setArticleName(jsond.optString("articleName", ""));
                            gameBean1.setBigBackgroundPic(jsond.optString("backgroundPicUrl", ""));
                            gameBean1.setArticleBigWidth(jsond.optString("articleBigWidth", ""));
                            gameBean1.setArticleTitle(jsond.optString("articleTitle", ""));
                            gameBean1.setArticleSmallWidth(jsond.optString("articleSmallWidth", ""));
                            gameBean1.setArticleSubTitle(jsond.optString("articleSubTitle", ""));
                            gameBean1.setArticleContent(jsond.optString("articleContent", ""));
                            gameBean1.setShowType(jsond.optString("showType", ""));
                            gameBean1.setArticleType(jsond.optString("articleType", ""));
                                gameBean1.setTypeCode(jsond.optString("typeCode", ""));
                                gameBean1.setGameCode(jsond.optString("gameCode", ""));
                                gameBean1.setCateCode(jsond.optString("cateCode", ""));
                                LogTools.e("dianji", jsond.optString("typeCode", ""));

//                            }
                            gameBeanList.add(gameBean1);

                        }
                    adapter=new acticlelist_Adapter(ActicleListActivity.this,gameBeanList,getIntent().getStringExtra(BundleTag.id));
                   listview.setAdapter(adapter);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
