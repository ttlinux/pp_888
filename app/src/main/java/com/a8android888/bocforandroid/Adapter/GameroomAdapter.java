package com.a8android888.bocforandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a8android888.bocforandroid.BaseParent.BaseApplication;
import com.a8android888.bocforandroid.Bean.GameBean;
import com.a8android888.bocforandroid.Bean.LotteryorderBean;
import com.a8android888.bocforandroid.R;
import com.a8android888.bocforandroid.activity.main.GetWebdata;
import com.a8android888.bocforandroid.activity.main.Sports.Sports_MainActivity;
import com.a8android888.bocforandroid.activity.main.game.GameFragment;
import com.a8android888.bocforandroid.activity.main.gamewebActivity;
import com.a8android888.bocforandroid.activity.main.lottery.Lottery_MainActivity;
import com.a8android888.bocforandroid.activity.user.LoginActivity;
import com.a8android888.bocforandroid.util.BundleTag;
import com.a8android888.bocforandroid.util.Httputils;
import com.a8android888.bocforandroid.util.ImageDownLoader;
import com.a8android888.bocforandroid.util.LogTools;
import com.a8android888.bocforandroid.util.ScreenUtils;
import com.a8android888.bocforandroid.util.ToastUtil;
import com.a8android888.bocforandroid.view.AnimatedExpandableListView;
import com.a8android888.bocforandroid.view.MyRelativeLayout;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/6.
 */
public class GameroomAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private Context context;
    ArrayList<ArrayList<GameBean>> mList;
    ArrayList<GameBean> titlelist;
    private ImageDownLoader mImageDownLoader2;
    private ImageLoader mImageDownLoader;
    int iconarr2[] = {R.drawable.micon9, R.drawable.micon10, R.drawable.micon6, R.drawable.micon7, R.drawable.micon5, R.drawable.micon8};

    public GameroomAdapter(Context context, ArrayList<ArrayList<GameBean>> list, ArrayList<GameBean> titlelist) {
        this.context = context;
        this.mList = list;
        this.titlelist = titlelist;
        LogTools.e("itemtaaaaa" + mList.size(), "tutle" + titlelist.size());
        mImageDownLoader2 = new ImageDownLoader(context);
        mImageDownLoader = ((BaseApplication) context.getApplicationContext())
                .getImageLoader();
    }


    @Override
    public View getRealChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolderitem holderd;
        final int index = childPosition;
        final int indexsatritem = 0;
        final int indexenditem = 0;
        if (convertView == null) {
            holderd = new ViewHolderitem();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_gameroom_title, null);
            convertView.setTag(holderd);
        } else {
            holderd = (ViewHolderitem) convertView.getTag();
        }
        LogTools.e("aaaaaaa","getRealChildView");
        holderd.itemimg = (ImageView) convertView.findViewById(R.id.img);
        holderd.srcid = (ImageView) convertView.findViewById(R.id.srcid);
        holderd.line = (View) convertView.findViewById(R.id.line);
        holderd.line.setVisibility(View.GONE);
//        mImageDownLoader2.showImageForlistview(context, mList.get(groupPosition).get(childPosition).getImg(), holderd.itemimg,2);
        mImageDownLoader.displayImage(mList.get(groupPosition).get(childPosition).getImg(), holderd.itemimg);
//        mImageDownLoader.showImage(context,mList.get(groupPosition).get(childPosition).getImg(),holderd.itemimg, 1);
        holderd.xiala = (ImageView) convertView.findViewById(R.id.xiala);
        holderd.xiala.setVisibility(View.GONE);
        holderd.itemtitle = (TextView) convertView.findViewById(R.id.title);
        holderd.itemtitle.setText(mList.get(groupPosition).get(childPosition).getName());
        holderd.itemlayout = (MyRelativeLayout) convertView.findViewById(R.id.itemlayout);

        holderd.itemlinelayout = (LinearLayout) convertView.findViewById(R.id.itemlinelayout);
        holderd.itemlinelayout.setBackgroundColor(context.getResources().getColor(R.color.gray12));
        if (index == 0) {
            holderd.itemlinelayout.setPadding(ScreenUtils.getDIP2PX(context, 16), ScreenUtils.getDIP2PX(context, 16), ScreenUtils.getDIP2PX(context, 16), 0);
        } else if (index == mList.get(groupPosition).size() - 1) {
            holderd.itemlinelayout.setPadding(ScreenUtils.getDIP2PX(context, 16), 0, ScreenUtils.getDIP2PX(context, 16), ScreenUtils.getDIP2PX(context, 16));
        } else {
            holderd.itemlinelayout.setPadding(ScreenUtils.getDIP2PX(context, 16), 0, ScreenUtils.getDIP2PX(context, 16), 0);
        }
        mImageDownLoader2.SetSpecialView(context, mList.get(groupPosition).get(childPosition).getBigBackgroundPic(), holderd.itemlayout);

        LogTools.e("bigpic", mList.get(groupPosition).get(childPosition).getBigBackgroundPic());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titlelist.get(groupPosition).getFlat().equalsIgnoreCase("lottery")) {
                    Intent intent = new Intent(context, Lottery_MainActivity.class);
                    intent.putExtra("title", mList.get(groupPosition).get(index).getName());
                    intent.putExtra("code", mList.get(groupPosition).get(index).getFlat());
                    context.startActivity(intent);
                }
                if (titlelist.get(groupPosition).getFlat().equalsIgnoreCase("live")) {
                    String Username = ((BaseApplication) context.getApplicationContext()).getBaseapplicationUsername();
                    if (Username == null || Username.equalsIgnoreCase("")) {
                        ToastUtil.showMessage(context, "未登录");
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        return;
                    }
                    GetWebdata.GetData((Activity) context, mList.get(groupPosition).get(index).getFlat(), mList.get(groupPosition).get(index).getGamecode(), mList.get(groupPosition).get(index).getName(), "nopull");
                    return;
//                    Intent intent = new Intent(context, gamewebActivity.class);
//                    intent.putExtra("title", mList.get(groupPosition).get(index).getName());
//                    intent.putExtra("flat", mList.get(groupPosition).get(index).getFlat());
//                    intent.putExtra("gamecode", mList.get(groupPosition).get(index).getGamecode());
//                    context.startActivity(intent);
                }
                if (titlelist.get(groupPosition).getFlat().equalsIgnoreCase("electronic")) {

                    if (mList.get(groupPosition).get(index).getFlat().equalsIgnoreCase("ag")) {
                        String Username = ((BaseApplication) context.getApplicationContext()).getBaseapplicationUsername();
                        if (Username == null || Username.equalsIgnoreCase("")) {
                            ToastUtil.showMessage(context, "未登录");
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
                        GetWebdata.GetData((Activity) context, mList.get(groupPosition).get(index).getFlat(), mList.get(groupPosition).get(index).getGamecode(), mList.get(groupPosition).get(index).getName(), "nopull");
                        return;
//                        Intent intent = new Intent(context, gamewebActivity.class);
//                        intent.putExtra("title", mList.get(groupPosition).get(index).getName());
//                        intent.putExtra("flat", mList.get(groupPosition).get(index).getFlat());
//                        intent.putExtra("gamecode", mList.get(groupPosition).get(index).getGamecode());
//                        context.startActivity(intent);
                    } else {
                        ArrayList<String> gameStringList = new ArrayList<String>();
                        ArrayList<String> gameflatList = new ArrayList<String>();
                        for (int i = 0; i < mList.get(groupPosition).size(); i++) {
                            if (!mList.get(groupPosition).get(i).getFlat().equalsIgnoreCase("ag")) {
                                gameStringList.add(mList.get(groupPosition).get(i).getName());
                                gameflatList.add(mList.get(groupPosition).get(i).getFlat());
                            }
                        }
                        Intent intent = new Intent(context, GameFragment.class);
                        intent.putExtra("title", mList.get(groupPosition).get(index).getName());
                        intent.putStringArrayListExtra("list", gameStringList);
                        intent.putStringArrayListExtra("flatlist", gameflatList);
                        intent.putExtra("flat", mList.get(groupPosition).get(index).getFlat());
                        if (mList.get(groupPosition).get(index).getFlat().equalsIgnoreCase("ag"))
                            intent.putExtra("gamecode", mList.get(groupPosition).get(index).getGamecode());
                        context.startActivity(intent);
                    }

                }


                if (titlelist.get(groupPosition).getFlat().equalsIgnoreCase("sport")) {
                    if (mList.get(groupPosition).get(index).getFlat().equalsIgnoreCase("huangguan")) {

                        ArrayList<String> gameStringList = new ArrayList<String>();
                        ArrayList<String> gameflatList = new ArrayList<String>();
                        for (int i = 0; i < mList.get(groupPosition).size(); i++) {
                            gameStringList.add(mList.get(groupPosition).get(index).getName());
                            gameflatList.add(mList.get(groupPosition).get(index).getFlat());
                        }
                        Intent intent = new Intent(context, Sports_MainActivity.class);
                        intent.putExtra("title", mList.get(groupPosition).get(index).getName());
                        intent.putStringArrayListExtra("list", gameStringList);
                        intent.putExtra(BundleTag.Platform, mList.get(groupPosition).get(index).getFlat());
                        context.startActivity(intent);


                    } else {
                        String Username = ((BaseApplication) context.getApplicationContext()).getBaseapplicationUsername();
                        if (Username == null || Username.equalsIgnoreCase("")) {
                            ToastUtil.showMessage(context, "未登录");
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            return;
                        }
                        GetWebdata.GetData((Activity) context, mList.get(groupPosition).get(index).getFlat(), mList.get(groupPosition).get(index).getGamecode(), mList.get(groupPosition).get(index).getName(), "nopull");
                        return;
                    }


                }
                if (titlelist.get(groupPosition).getFlat().equalsIgnoreCase("card")) {
                    String Username = ((BaseApplication) context.getApplicationContext()).getBaseapplicationUsername();
                    if (Username == null || Username.equalsIgnoreCase("")) {
                        ToastUtil.showMessage(context, "未登录");
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        return;
                    }
                    GetWebdata.GetData((Activity) context, mList.get(groupPosition).get(index).getFlat(), mList.get(groupPosition).get(index).getGamecode(), mList.get(groupPosition).get(index).getName(), "nopull");
                    return;
//                    Intent intent = new Intent(context, gamewebActivity.class);
//                    intent.putExtra("title", mList.get(groupPosition).get(index).getName());
//                    intent.putExtra("flat", mList.get(groupPosition).get(index).getFlat());
//                    intent.putExtra("gamecode", mList.get(groupPosition).get(index).getGamecode());
//                    context.startActivity(intent);
                }
                if (titlelist.get(groupPosition).getFlat().equalsIgnoreCase("bbin")) {
                    String Username = ((BaseApplication) context.getApplicationContext()).getBaseapplicationUsername();
                    if (Username == null || Username.equalsIgnoreCase("")) {
                        ToastUtil.showMessage(context, "未登录");
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        return;
                    }
                    GetWebdata.GetData((Activity) context, mList.get(groupPosition).get(index).getFlat(), mList.get(groupPosition).get(index).getGamecode(), mList.get(groupPosition).get(index).getName(), "nopull");
                    return;
//                    Intent intent = new Intent(context, gamewebActivity.class);
//                    intent.putExtra("title", mList.get(groupPosition).get(index).getName());
//                    intent.putExtra("flat", mList.get(groupPosition).get(index).getFlat());
//                    intent.putExtra("gamecode", mList.get(groupPosition).get(index).getGamecode());
//                    context.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return mList.get(groupPosition).size();
    }

    @Override
    public int getGroupCount() {
        return titlelist.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return titlelist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_gameroom_title, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LogTools.e("aaaaaaa","getGroupView");
        holder.img = (ImageView) convertView.findViewById(R.id.img);
        holder.srcid = (ImageView) convertView.findViewById(R.id.srcid);
        holder.itemlayout = (RelativeLayout) convertView.findViewById(R.id.itemlayout);
        ImageView xiala = (ImageView) convertView.findViewById(R.id.xiala);

        if (isExpanded) {
            xiala.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_bot_selected));
        } else {
            xiala.setImageDrawable(context.getResources().getDrawable(R.drawable.small_triangle_right_selected));
        }
//            holder.img.setBackgroundResource(R.drawable.dkj);
        mImageDownLoader2.showImageForlistview(context,titlelist.get(groupPosition).getImg(),holder.img,2);
//        mImageDownLoader.displayImage(titlelist.get(groupPosition).getImg(), holder.img);
        mImageDownLoader2.SetSpecialView(context, titlelist.get(groupPosition).getBigBackgroundPic(), convertView);

        holder.title = (TextView) convertView.findViewById(R.id.title);
        holder.title.setText(titlelist.get(groupPosition).getName());
        return convertView;
    }

    class ViewHolder {
        TextView title;
        RelativeLayout itemlayout;

        ImageView img, srcid;
    }

    class ViewHolderitem {
        TextView itemtitle;
        ImageView itemimg, xiala, srcid;
        LinearLayout itemlinelayout;
        MyRelativeLayout itemlayout;
        View line;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}
