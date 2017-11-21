package com.a8android888.bocforandroid.Bean;

/**
 * Created by Administrator on 2017/4/10.
 */
public class LotteryBean {
   String gameCode;
    String gameName;
    String gameId;
    String qs;
    String openCode;
    String img;

    public String getBigBackgroundPic() {
        return bigBackgroundPic;
    }

    public void setBigBackgroundPic(String bigBackgroundPic) {
        this.bigBackgroundPic = bigBackgroundPic;
    }

    String bigBackgroundPic;
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    String openTime;

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public String getQs() {
        return qs;
    }

    public void setQs(String qs) {
        this.qs = qs;
    }

    public String getDefaultItemId() {
        return defaultItemId;
    }

    public void setDefaultItemId(String defaultItemId) {
        this.defaultItemId = defaultItemId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    String defaultItemId;
}
