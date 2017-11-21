package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19. 半场/全场
 */
public class FootBallHq {

    private String title;

    public ArrayList<FootBallHq_item> getFootBallHq_items() {
        return footBallHq_items;
    }

    public void setFootBallHq_items(ArrayList<FootBallHq_item> footBallHq_items) {
        this.footBallHq_items = footBallHq_items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private ArrayList<FootBallHq_item> footBallHq_items;

    public static String[] GetBasketBallList(FootBallHq_item item)
    {
        String str[]=new String[9];
        str[0]=item.getIorFhh();
        str[1]=item.getIorFhn();
        str[2]=item.getIorFhc();

        str[3]=item.getIorFnh();
        str[4]=item.getIorFnn();
        str[5]=item.getIorFnc();

        str[6]=item.getIorFch();
        str[7]=item.getIorFcn();
        str[8]=item.getIorFcc();

        return str;
    }

    public static ArrayList<SportsOrderBean> GetBasketBallListTAG(FootBallHq_item item,String titles[],String data[],String timetype,String rtype)
    {
        String gid=item.getGid();
        String m_timetype=timetype;
        String m_rtype=rtype;
        ArrayList<SportsOrderBean> sportsOrderBeans=new ArrayList<SportsOrderBean>();
        if(data==null || data.length!=9)return sportsOrderBeans;
        for (int i = 0; i < data.length; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setrType(m_rtype);
            bean.setTimeType(m_timetype);

            bean.setArg(1);
            bean.setPeilv(data[i]);
            bean.setTeamH(item.getTeamH());
            bean.setTeamc(item.getTeamC());
            bean.setLeague(item.getLeague());
            bean.setTag1("半场/全场");


            bean.setbType("f");
            bean.setdType(titles[i]);
            bean.setPeriod("f");
            bean.setSelection(titles[i]);
            bean.setTeamname(bean.getdType().replace("N","和").replace("H",item.getTeamH()).replace("C",item.getTeamC()));
            sportsOrderBeans.add(bean);
        }

        return sportsOrderBeans;
    }

    public static class FootBallHq_item
    {

        /**
         * gid : 2694138
         * gnumC : 30151
         * gnumH : 30152
         * iorFcc : 6.9
         * iorFch : 23
         * iorFcn : 15
         * iorFhc : 33
         * iorFhh : 2.68
         * iorFhn : 15
         * iorFnc : 8.7
         * iorFnh : 4.35
         * iorFnn : 4.7
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
        private String iorFcc;
        private String iorFch;
        private String iorFcn;
        private String iorFhc;
        private String iorFhh;
        private String iorFhn;
        private String iorFnc;
        private String iorFnh;
        private String iorFnn;
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

        public String getIorFcc() {
            return iorFcc;
        }

        public void setIorFcc(String iorFcc) {
            this.iorFcc = iorFcc;
        }

        public String getIorFch() {
            return iorFch;
        }

        public void setIorFch(String iorFch) {
            this.iorFch = iorFch;
        }

        public String getIorFcn() {
            return iorFcn;
        }

        public void setIorFcn(String iorFcn) {
            this.iorFcn = iorFcn;
        }

        public String getIorFhc() {
            return iorFhc;
        }

        public void setIorFhc(String iorFhc) {
            this.iorFhc = iorFhc;
        }

        public String getIorFhh() {
            return iorFhh;
        }

        public void setIorFhh(String iorFhh) {
            this.iorFhh = iorFhh;
        }

        public String getIorFhn() {
            return iorFhn;
        }

        public void setIorFhn(String iorFhn) {
            this.iorFhn = iorFhn;
        }

        public String getIorFnc() {
            return iorFnc;
        }

        public void setIorFnc(String iorFnc) {
            this.iorFnc = iorFnc;
        }

        public String getIorFnh() {
            return iorFnh;
        }

        public void setIorFnh(String iorFnh) {
            this.iorFnh = iorFnh;
        }

        public String getIorFnn() {
            return iorFnn;
        }

        public void setIorFnn(String iorFnn) {
            this.iorFnn = iorFnn;
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
