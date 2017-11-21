package com.a8android888.bocforandroid.Bean;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
public class LotterycomitorderBean {
    String data;

    public List<String> getDatacode() {
        return datacode;
    }

    public void setDatacode(List<String> datacode) {
        this.datacode = datacode;
    }

    List<String> datacode;
    String uid;
    String pl;
    String xzje;//下注金额
    String itemtname;
    String xzlxitemname;
    String number;
    public String maxlimit;// 单注最大限额
    public String maxPeriodLimit;//  	单期最大限额
    int textcolor;

    public int getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(int textcolor) {
        this.textcolor = textcolor;
    }

    public String getXzje() {
        return xzje;
    }

    public void setXzje(String xzje) {
        this.xzje = xzje;
    }

    public String getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(String minLimit) {
        this.minLimit = minLimit;
    }

    public String getMaxPeriodLimit() {
        return maxPeriodLimit;
    }

    public void setMaxPeriodLimit(String maxPeriodLimit) {
        this.maxPeriodLimit = maxPeriodLimit;
    }

    public String getMaxlimit() {
        return maxlimit;
    }

    public void setMaxlimit(String maxlimit) {
        this.maxlimit = maxlimit;
    }

    public String minLimit;// 	单注最小限额

    public String getXzlxitemname() {
        return xzlxitemname;
    }

    public void setXzlxitemname(String xzlxitemname) {
        this.xzlxitemname = xzlxitemname;
    }

    public String getItemtname() {
        return itemtname;
    }

    public void setItemtname(String itemtname) {
        this.itemtname = itemtname;
    }

    boolean selected = false;

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }



}
