package com.a8android888.bocforandroid.Bean;

/**
 * Created by Administrator on 2017/4/21.
 */
public class SportsOrderBean {

    String gid,gidm,timeType,rType,bType,dType,selection,period;
    int arg;
    String peilv;

    String league;
    String teamH;
    String teamc;
    String radio;//赔率前面的字段 可能为空 填vs
    String tag1,tag2;//大小 上半场 // tag2可能为空
    String teamname;//主队 客场 和局
    int BallType;//什么球类 篮球0 足球1
    long Createtime=0;

    public long getCreatetime() {
        return Createtime;
    }

    public void setCreatetime(long createtime) {
        Createtime = createtime;
    }

    public int getBallType() {
        return BallType;
    }

    public void setBallType(int type) {
        BallType = type;
    }


    public String getGidm() {
        return gidm;
    }

    public void setGidm(String gidm) {
        this.gidm = gidm;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getTeamH() {
        return teamH;
    }

    public void setTeamH(String teamH) {
        this.teamH = teamH;
    }

    public String getTeamc() {
        return teamc;
    }

    public void setTeamc(String teamc) {
        this.teamc = teamc;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }


    public String getPeilv() {
        return peilv;
    }

    public void setPeilv(String peilv) {
        this.peilv = peilv;
    }

    public int getArg() {
        return arg;
    }

    public void setArg(int arg) {
        this.arg = arg;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getbType() {
        return bType;
    }

    public void setbType(String bType) {
        this.bType = bType;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }



}
