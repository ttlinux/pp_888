package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */
public class FootBallRuQiu {

    private String title;

    public ArrayList<FootBallRuQiu_item> getFootBallRuQiu_items() {
        return footBallRuQiu_items;
    }

    public void setFootBallRuQiu_items(ArrayList<FootBallRuQiu_item> footBallRuQiu_items) {
        this.footBallRuQiu_items = footBallRuQiu_items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private ArrayList<FootBallRuQiu_item> footBallRuQiu_items;

    public static String[] GetBasketBallList(FootBallRuQiu_item item)
    {
        String str[]=new String[4];
        str[0]=item.getIorT01();
        str[1]=item.getIorT23();
        str[2]=item.getIorT46();
        str[3]=item.getIorOver();
        return str;
    }

    public static ArrayList<SportsOrderBean> GetBasketBallListTAG(FootBallRuQiu_item item,String titles[],String data[],String timetype,String rtype)
    {
        String gid=item.getGid();
        String m_timetype=timetype;
        String m_rtype=rtype;
        ArrayList<SportsOrderBean> sportsOrderBeans=new ArrayList<SportsOrderBean>();
        if(data==null || data.length!=4)return sportsOrderBeans;
        for (int i = 0; i < data.length; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setTimeType(m_timetype);
            bean.setrType(m_rtype);

            bean.setbType("t");
            bean.setdType(titles[i]);
            bean.setSelection("N");
            bean.setPeriod("f");

            bean.setTeamc(item.getTeamC());
            bean.setTeamH(item.getTeamH());
            bean.setLeague(item.getLeague());
            bean.setTag1("总入球");
            bean.setTag2("全场");
            bean.setTeamname(bean.getdType().replace("up","或者以上"));

            bean.setPeilv(data[i]);
            bean.setArg(1);
            sportsOrderBeans.add(bean);
        }


        return sportsOrderBeans;
    }

    public static class FootBallRuQiu_item
    {

        /**
         * gid : 2694138
         * gnumC : 30151
         * gnumH : 30152
         * iorEven :
         * iorMc :
         * iorMh :
         * iorMn :
         * iorOdd :
         * iorOver : 41
         * iorT01 : 2.62
         * iorT23 : 1.79
         * iorT46 : 4.15
         * league : 德国丙组联赛
         * matchIndex : 1
         * matchPage : 1
         * matchTime : 2017-04-19 13:00:00
         * roll : 1
         * strong : H
         * teamC : 云达不莱梅青年队
         * teamH : 威恒
         * timeType : today
         * tmp1 :
         * tmp2 :
         * tmp3 :
         */

        private String gid;
        private String gnumC;
        private String gnumH;
        private String iorEven;
        private String iorMc;
        private String iorMh;
        private String iorMn;
        private String iorOdd;
        private String iorOver;
        private String iorT01;
        private String iorT23;
        private String iorT46;
        private String league;
        private int matchIndex;
        private int matchPage;
        private String matchTime;
        private String roll;
        private String strong;
        private String teamC;
        private String teamH;
        private String timeType;
        private String tmp1;
        private String tmp2;
        private String tmp3;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGnumC() {
            return gnumC;
        }

        public void setGnumC(String gnumC) {
            this.gnumC = gnumC;
        }

        public String getGnumH() {
            return gnumH;
        }

        public void setGnumH(String gnumH) {
            this.gnumH = gnumH;
        }

        public String getIorEven() {
            return iorEven;
        }

        public void setIorEven(String iorEven) {
            this.iorEven = iorEven;
        }

        public String getIorMc() {
            return iorMc;
        }

        public void setIorMc(String iorMc) {
            this.iorMc = iorMc;
        }

        public String getIorMh() {
            return iorMh;
        }

        public void setIorMh(String iorMh) {
            this.iorMh = iorMh;
        }

        public String getIorMn() {
            return iorMn;
        }

        public void setIorMn(String iorMn) {
            this.iorMn = iorMn;
        }

        public String getIorOdd() {
            return iorOdd;
        }

        public void setIorOdd(String iorOdd) {
            this.iorOdd = iorOdd;
        }

        public String getIorOver() {
            return iorOver;
        }

        public void setIorOver(String iorOver) {
            this.iorOver = iorOver;
        }

        public String getIorT01() {
            return iorT01;
        }

        public void setIorT01(String iorT01) {
            this.iorT01 = iorT01;
        }

        public String getIorT23() {
            return iorT23;
        }

        public void setIorT23(String iorT23) {
            this.iorT23 = iorT23;
        }

        public String getIorT46() {
            return iorT46;
        }

        public void setIorT46(String iorT46) {
            this.iorT46 = iorT46;
        }

        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public int getMatchIndex() {
            return matchIndex;
        }

        public void setMatchIndex(int matchIndex) {
            this.matchIndex = matchIndex;
        }

        public int getMatchPage() {
            return matchPage;
        }

        public void setMatchPage(int matchPage) {
            this.matchPage = matchPage;
        }

        public String getMatchTime() {
            return matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public String getRoll() {
            return roll;
        }

        public void setRoll(String roll) {
            this.roll = roll;
        }

        public String getStrong() {
            return strong;
        }

        public void setStrong(String strong) {
            this.strong = strong;
        }

        public String getTeamC() {
            return teamC;
        }

        public void setTeamC(String teamC) {
            this.teamC = teamC;
        }

        public String getTeamH() {
            return teamH;
        }

        public void setTeamH(String teamH) {
            this.teamH = teamH;
        }

        public String getTimeType() {
            return timeType;
        }

        public void setTimeType(String timeType) {
            this.timeType = timeType;
        }

        public String getTmp1() {
            return tmp1;
        }

        public void setTmp1(String tmp1) {
            this.tmp1 = tmp1;
        }

        public String getTmp2() {
            return tmp2;
        }

        public void setTmp2(String tmp2) {
            this.tmp2 = tmp2;
        }

        public String getTmp3() {
            return tmp3;
        }

        public void setTmp3(String tmp3) {
            this.tmp3 = tmp3;
        }
    }
}
