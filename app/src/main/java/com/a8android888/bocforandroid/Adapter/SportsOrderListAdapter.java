package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a8android888.bocforandroid.Bean.OrderJson;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.LogTools;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/22.
 */
public class SportsOrderListAdapter extends BaseAdapter{

    Activity activity;
    ArrayList<OrderJson> jsonObjects;

    public DeleteItemListener getDeleteItemListener() {
        return deleteItemListener;
    }

    public void setDeleteItemListener(DeleteItemListener deleteItemListener) {
        this.deleteItemListener = deleteItemListener;
    }

    DeleteItemListener deleteItemListener;

    public SportsOrderListAdapter(Activity activity,ArrayList<OrderJson> jsonObjects)
    {
        this.activity=activity;
        this.jsonObjects=jsonObjects;
    }

    public void NotifyAdapter(ArrayList<OrderJson> jsonObjects)
    {
        this.jsonObjects=jsonObjects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return jsonObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;
        if(convertView==null)
        {
            viewholder=new Viewholder();
            convertView=View.inflate(activity, R.layout.item_sports_order,null);
            convertView.setTag(viewholder);
        }
        else
        {
            viewholder=(Viewholder)convertView.getTag();
        }

        JSONObject jsonObject=jsonObjects.get(position).getJsonObject();
        viewholder.title=(TextView)convertView.findViewById(R.id.title);
        viewholder.radiostr=(TextView)convertView.findViewById(R.id.radiostr);
        viewholder.teamh=(TextView)convertView.findViewById(R.id.teamh);
        viewholder.vs=(TextView)convertView.findViewById(R.id.vs);
        viewholder.teamc=(TextView)convertView.findViewById(R.id.teamc);
        viewholder.tag1=(TextView)convertView.findViewById(R.id.tag1);
        viewholder.tag2=(TextView)convertView.findViewById(R.id.tag2);
        TextView temp1=(TextView)convertView.findViewById(R.id.temp1);
        viewholder.deleteitem=(ImageView)convertView.findViewById(R.id.deleteitem);
        viewholder.deleteitem.setTag(position);
        viewholder.deleteitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteItemListener != null) {
                    deleteItemListener.ondelete(Integer.valueOf(v.getTag() + ""));
                }
            }
        });

        viewholder.title.setText(jsonObject.optString("league", ""));

//        viewholder.teamh.setText(jsonObject.optString("teamH", ""));
//        viewholder.teamc.setText(jsonObject.optString("teamc",""));

        String radio="";
        if(jsonObject.has("radio"))
        {
            radio=jsonObject.optString("radio","");
        }
        String teamc = jsonObject.optString("team2","");//客队
        String teamH = jsonObject.optString("team1","");//主队
//        if(jsonObject.has("selection") && jsonObject.optString("selection","").equalsIgnoreCase("C"))//客队
//        {
//            builder3 = new SpannableStringBuilder(teamc+" "+radio);
//            ForegroundColorSpan redSpan3 = new ForegroundColorSpan(activity.getResources().getColor(R.color.red));
//            builder3.setSpan(redSpan3, teamc.length(), builder3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    viewholder.teamh.setText(teamH);
//        viewholder.teamc.setText(builder3);
//        }
//        else if(jsonObject.has("selection") && jsonObject.optString("selection","").equalsIgnoreCase("H"))
//        {
//            builder3 = new SpannableStringBuilder(teamH+" "+radio);
//            ForegroundColorSpan redSpan3 = new ForegroundColorSpan(activity.getResources().getColor(R.color.red));
//            builder3.setSpan(redSpan3, teamH.length(), builder3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            viewholder.teamh.setText(builder3);
//            viewholder.teamc.setText(teamc);
//        }

        SpannableStringBuilder builderh = new SpannableStringBuilder(teamH+" "+jsonObject.optString("ratioH",""));
        ForegroundColorSpan redSpanh = new ForegroundColorSpan(activity.getResources().getColor(R.color.red));
        builderh.setSpan(redSpanh, teamH.length(), builderh.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewholder.teamh.setText(builderh);

        SpannableStringBuilder builderc = new SpannableStringBuilder(teamc+" "+jsonObject.optString("ratioC",""));
        ForegroundColorSpan redSpanc = new ForegroundColorSpan(activity.getResources().getColor(R.color.red));
        builderc.setSpan(redSpanc, teamc.length(), builderc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewholder.teamc.setText(builderc);

//        viewholder.vs.setText(vs);



        String pppp=jsonObject.optJSONObject(BundleTag.JsonObject).optString("period","");
        int BallType=Integer.valueOf(jsonObject.optJSONObject(BundleTag.JsonObject).optString("BallType",""));
        String temp2=jsonObject.optJSONObject(BundleTag.JsonObject).optString("bType","");
        LogTools.e("jsonObjecteee",pppp);
        if(BallType==0 && pppp.equalsIgnoreCase("f") && !temp2.equalsIgnoreCase("jf"))
        {
            viewholder.tag1.setText(jsonObject.optString("bType"));
            temp1.setVisibility(View.GONE);
        }
        else
        {
            viewholder.tag1.setText(jsonObject.optString("bType"));
            viewholder.tag2.setText(jsonObject.optString("period",""));
        }
        String radiostr=jsonObject.optString("selection","").replace("_", "-")+" @ "+jsonObject.optString("odds","");//+jsonObject.optString("ratio","")

        viewholder.radiostr.setText(radiostr);
        return convertView;
    }

    class Viewholder
    {
        TextView title,radiostr,teamh,vs,teamc,tag1,tag2;
        ImageView deleteitem;
    }

    public interface DeleteItemListener
    {
        public void ondelete(int position);
    }
}
