package com.a8android888.bocforandroid.Bean;

/**
 * Created by Administrator on 2017/4/4.
 */
public class GameBean {
    String name;
   String flat;//平台标识
    String gamecode;//遊戲代碼
   String ecname;//英文名
    String img;
    String havourite;//收藏标示
String bigBackgroundPic;

    public String getBigBackgroundPic() {
        return bigBackgroundPic;
    }

    public void setBigBackgroundPic(String bigBackgroundPic) {
        this.bigBackgroundPic = bigBackgroundPic;
    }

    public String getHavourite() {
        return havourite;
    }

    public void setHavourite(String havourite) {
        this.havourite = havourite;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEcname() {
        return ecname;
    }

    public void setEcname(String ecname) {
        this.ecname = ecname;
    }

    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }
}
