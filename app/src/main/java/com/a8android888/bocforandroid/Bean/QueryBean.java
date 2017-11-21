package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/10.
 */
public class QueryBean {

    String betTime;
    String betGameContent;
    String betWagersId,betIncome;
    double betIn,betUsrWin;
    boolean itemstate=false;
    ArrayList<detail> details;

    public ArrayList<detail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<detail> details) {
        this.details = details;
    }

    public String getBetIncome() {
        return betIncome;
    }

    public void setBetIncome(String betIncome) {
        this.betIncome = betIncome;
    }

    public String getBetGameContent() {
        return betGameContent;
    }

    public void setBetGameContent(String betGameContent) {
        this.betGameContent = betGameContent;
    }

    public String getBetWagersId() {
        return betWagersId;
    }

    public void setBetWagersId(String betWagersId) {
        this.betWagersId = betWagersId;
    }


    public double getBetIn() {
        return betIn;
    }

    public void setBetIn(double betIn) {
        this.betIn = betIn;
    }

    public String getBetTime() {
        return betTime;
    }

    public void setBetTime(String betTime) {
        this.betTime = betTime;
    }

    public double getBetUsrWin() {
        return betUsrWin;
    }

    public void setBetUsrWin(double betUsrWin) {
        this.betUsrWin = betUsrWin;
    }



    public boolean getItemstate() {
        return itemstate;
    }

    public void setItemstate(boolean itemstate) {
        this.itemstate = itemstate;
    }


    public static class detail
    {
        String color,key,value;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
