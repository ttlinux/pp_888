package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */
public class BKBean_Normal {

    private String title;

    static String homestr[];

    public ArrayList<BkBean_Normal_item> getBkBean_normal_items() {
        return bkBean_normal_items;
    }

    public void setBkBean_normal_items(ArrayList<BkBean_Normal_item> bkBean_normal_items) {
        this.bkBean_normal_items = bkBean_normal_items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private ArrayList<BkBean_Normal_item> bkBean_normal_items=new ArrayList<BkBean_Normal_item>();

    public static String[] GetBasketBallList(BKBean_Normal.BkBean_Normal_item bean)
    {
        String strp[]=new String[10];
        homestr=new String[10];
        strp[0]=bean.getIorMh();
        if(bean.getStrong().equalsIgnoreCase("H"))
        {
            strp[1]=bean.getRatio()+"#@"+bean.getIorRh();
            strp[6]=bean.getIorRc();
        }
        else
        {
            strp[1]=bean.getIorRh();
            strp[6]=bean.getRatio()+"#@"+bean.getIorRc();
        }



//        RatioPouho主队大球数 IorPouho主队大球赔率 RatioPouhu主队小球数 IorPouhu主队小球赔率
//        RatioPouco客队大球数 IorPouco客队大球赔率 RatioPoucu客队小球数 IorPoucu客队小球赔率
        strp[2]="大"+bean.getRatioO()+"#@"+ bean.getIorOuc();
        strp[3]="大"+bean.getRatioOuho()+"#@"+ bean.getIorOuho();
        strp[4]="小"+bean.getRatioOuhu()+"#@"+ bean.getIorOuhu();
        strp[5]= bean.getIorMc();
        strp[7]= "小"+bean.getRatioU()+"#@"+ bean.getIorOuh();
        strp[8]= "大"+bean.getRatioOuco()+"#@"+ bean.getIorOuco();
        strp[9]= "小"+bean.getRatioOucu()+"#@"+ bean.getIorOucu();

        homestr[0]=bean.getIorMh();
        homestr[1]=bean.getIorRh();
        homestr[2]=bean.getIorOuc();
        homestr[3]=bean.getIorOuho();
        homestr[4]=bean.getIorOuhu();
        homestr[5]=bean.getIorMc();
        homestr[6]=bean.getIorRc();
        homestr[7]=bean.getIorOuh();
        homestr[8]=bean.getIorOuco();
        homestr[9]=bean.getIorOucu();
        return strp;
    }

    public static ArrayList<SportsOrderBean> GetBasketBallListTAG(BkBean_Normal_item mbean,String timeType,String rType)
    {
        String gid=mbean.getGid();
        String m_timeType=timeType;
        String m_rType=rType;
        ArrayList<SportsOrderBean> sportsOrderBeans=new ArrayList<SportsOrderBean>();
        if(homestr==null || homestr.length!=10)return sportsOrderBeans;
        for (int i = 0; i <homestr.length; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setTimeType(m_timeType);
            bean.setrType(m_rType);
            bean.setPeilv(homestr[i]);

            bean.setLeague(mbean.getLeague());
            bean.setTeamH(mbean.getTeamH());
            bean.setTeamc(mbean.getTeamC());
            int index=0;
            switch (i%5)
            {
//                dy rf dx ds
                case 0:
                    bean.setbType("dy");
                    bean.setdType("dy");
                    bean.setArg(1);
                    bean.setTag1("独赢");
                    break;
                case 1:
                    bean.setbType("rf");
                    bean.setdType("rf");
                    bean.setArg(0);
                    bean.setTag1("让分");
                    bean.setRadio(mbean.getRatio());
                    break;
                case 2:
                    bean.setbType("dx");
                    bean.setdType("dx");
                    bean.setArg(0);
                    bean.setTag1("大小");
                    index=2;
                    break;
                case 3:
                    bean.setbType("jf");
                    bean.setdType("dx_big");
                    bean.setArg(0);
                    bean.setTag1("球队得分:大/小");
                    index=3;
                    break;
                case 4:
                    bean.setbType("jf");
                    bean.setdType("dx_small");
                    bean.setArg(0);
                    bean.setTag1("球队得分:大/小");
                    index=4;
                    break;
            }
            if(i<5)
            {
                bean.setSelection("H");
                bean.setTag2(mbean.getTeamH());
                if(index==2)bean.setTeamname("大"+mbean.getRatioO());
                else if(index==3)bean.setTeamname("大"+mbean.getRatioOuho());
                else if(index==4)bean.setTeamname("小"+mbean.getRatioOuhu());
                else bean.setTeamname(bean.getTeamH());
            }
            if(4<i&&i<10)
            {
                bean.setSelection("C");
                bean.setTag2(mbean.getTeamC());
                if(index==2)bean.setTeamname("小"+mbean.getRatioU());
                else if(index==3)bean.setTeamname("大"+mbean.getRatioOuco());
                else if(index==4)bean.setTeamname("小"+mbean.getRatioOucu());
                else bean.setTeamname(bean.getTeamc());
            }

            bean.setPeriod("f");
            sportsOrderBeans.add(bean);

        }
        return sportsOrderBeans;
    }

    public static class BkBean_Normal_item
    {

        /**
         * centerTv :
         * eventid :
         * gid : 2144074
         * gidm : 325890
         * gnumC : 10073
         * gnumH : 10074
         * hot :
         * iorEoe : 1.94
         * iorEoo : 1.94
         * iorMc :
         * iorMh :
         * iorOuc : 0.84
         * iorOuco : 0.85
         * iorOucu : 0.83
         * iorOuh : 0.84
         * iorOuho : 0.86
         * iorOuhu : 0.82
         * iorRc : 0.89
         * iorRh : 0.89
         * ismaster : Y
         * lastgoal : H
         * lasttime : 385
         * league : NBA季后赛
         * matchIndex : 1
         * matchPage : 1
         * matchTime : 2017-04-17 21:30:00
         * more : 22
         * no1 :
         * no10 :
         * no11 :
         * no12 :
         * no2 :
         * no3 :
         * no4 :
         * no5 :
         * no6 :
         * no7 :
         * no8 :
         * no9 :
         * nowsession : Q1
         * play : Y
         * ratio : 12.50
         * ratioO : 193.5
         * ratioOuco : 90.5
         * ratioOucu : 90.5
         * ratioOuho : 103
         * ratioOuhu : 103
         * ratioU : 193.5
         * retimeset : Start^09:57p
         * roll : null
         * scoreC : 10
         * scoreH : 17
         * scorec : 10
         * scoreh : 17
         * strEven : 双
         * strOdd : 单
         * strong : H
         * teamC : 孟菲斯灰熊
         * teamH : 圣安东尼奥马刺
         * timer : 09:57
         * tmp1 :
         * tmp2 :
         * tmp3 :
         */

        private String centerTv;
        private String eventid;
        private String gid;
        private String gidm;
        private String gnumC;
        private String gnumH;
        private String hot;
        private String iorEoe;
        private String iorEoo;
        private String iorMc;
        private String iorMh;
        private String iorOuc;
        private String iorOuco;
        private String iorOucu;
        private String iorOuh;
        private String iorOuho;
        private String iorOuhu;
        private String iorRc;
        private String iorRh;
        private String ismaster;
        private String lastgoal;
        private String lasttime;
        private String league;
        private int matchIndex;
        private int matchPage;
        private String matchTime;
        private String more;
        private String no1;
        private String no10;
        private String no11;
        private String no12;
        private String no2;
        private String no3;
        private String no4;
        private String no5;
        private String no6;
        private String no7;
        private String no8;
        private String no9;
        private String nowsession;
        private String play;
        private String ratio;
        private String ratioO;
        private String ratioOuco;
        private String ratioOucu;
        private String ratioOuho;
        private String ratioOuhu;
        private String ratioU;
        private String retimeset;
        private Object roll;
        private String scoreC;
        private String scoreH;
        private String scorec;
        private String scoreh;
        private String strEven;
        private String strOdd;
        private String strong;
        private String teamC;
        private String teamH;
        private String timer;
        private String tmp1;
        private String tmp2;
        private String tmp3;

        public String getCenterTv() {
            return centerTv;
        }

        public void setCenterTv(String centerTv) {
            this.centerTv = centerTv;
        }

        public String getEventid() {
            return eventid;
        }

        public void setEventid(String eventid) {
            this.eventid = eventid;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGidm() {
            return gidm;
        }

        public void setGidm(String gidm) {
            this.gidm = gidm;
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

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getIorEoe() {
            return iorEoe;
        }

        public void setIorEoe(String iorEoe) {
            this.iorEoe = iorEoe;
        }

        public String getIorEoo() {
            return iorEoo;
        }

        public void setIorEoo(String iorEoo) {
            this.iorEoo = iorEoo;
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

        public String getIorOuc() {
            return iorOuc;
        }

        public void setIorOuc(String iorOuc) {
            this.iorOuc = iorOuc;
        }

        public String getIorOuco() {
            return iorOuco;
        }

        public void setIorOuco(String iorOuco) {
            this.iorOuco = iorOuco;
        }

        public String getIorOucu() {
            return iorOucu;
        }

        public void setIorOucu(String iorOucu) {
            this.iorOucu = iorOucu;
        }

        public String getIorOuh() {
            return iorOuh;
        }

        public void setIorOuh(String iorOuh) {
            this.iorOuh = iorOuh;
        }

        public String getIorOuho() {
            return iorOuho;
        }

        public void setIorOuho(String iorOuho) {
            this.iorOuho = iorOuho;
        }

        public String getIorOuhu() {
            return iorOuhu;
        }

        public void setIorOuhu(String iorOuhu) {
            this.iorOuhu = iorOuhu;
        }

        public String getIorRc() {
            return iorRc;
        }

        public void setIorRc(String iorRc) {
            this.iorRc = iorRc;
        }

        public String getIorRh() {
            return iorRh;
        }

        public void setIorRh(String iorRh) {
            this.iorRh = iorRh;
        }

        public String getIsmaster() {
            return ismaster;
        }

        public void setIsmaster(String ismaster) {
            this.ismaster = ismaster;
        }

        public String getLastgoal() {
            return lastgoal;
        }

        public void setLastgoal(String lastgoal) {
            this.lastgoal = lastgoal;
        }

        public String getLasttime() {
            return lasttime;
        }

        public void setLasttime(String lasttime) {
            this.lasttime = lasttime;
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

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public String getNo1() {
            return no1;
        }

        public void setNo1(String no1) {
            this.no1 = no1;
        }

        public String getNo10() {
            return no10;
        }

        public void setNo10(String no10) {
            this.no10 = no10;
        }

        public String getNo11() {
            return no11;
        }

        public void setNo11(String no11) {
            this.no11 = no11;
        }

        public String getNo12() {
            return no12;
        }

        public void setNo12(String no12) {
            this.no12 = no12;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getNo3() {
            return no3;
        }

        public void setNo3(String no3) {
            this.no3 = no3;
        }

        public String getNo4() {
            return no4;
        }

        public void setNo4(String no4) {
            this.no4 = no4;
        }

        public String getNo5() {
            return no5;
        }

        public void setNo5(String no5) {
            this.no5 = no5;
        }

        public String getNo6() {
            return no6;
        }

        public void setNo6(String no6) {
            this.no6 = no6;
        }

        public String getNo7() {
            return no7;
        }

        public void setNo7(String no7) {
            this.no7 = no7;
        }

        public String getNo8() {
            return no8;
        }

        public void setNo8(String no8) {
            this.no8 = no8;
        }

        public String getNo9() {
            return no9;
        }

        public void setNo9(String no9) {
            this.no9 = no9;
        }

        public String getNowsession() {
            return nowsession;
        }

        public void setNowsession(String nowsession) {
            this.nowsession = nowsession;
        }

        public String getPlay() {
            return play;
        }

        public void setPlay(String play) {
            this.play = play;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getRatioO() {
            return ratioO;
        }

        public void setRatioO(String ratioO) {
            this.ratioO = ratioO;
        }

        public String getRatioOuco() {
            return ratioOuco;
        }

        public void setRatioOuco(String ratioOuco) {
            this.ratioOuco = ratioOuco;
        }

        public String getRatioOucu() {
            return ratioOucu;
        }

        public void setRatioOucu(String ratioOucu) {
            this.ratioOucu = ratioOucu;
        }

        public String getRatioOuho() {
            return ratioOuho;
        }

        public void setRatioOuho(String ratioOuho) {
            this.ratioOuho = ratioOuho;
        }

        public String getRatioOuhu() {
            return ratioOuhu;
        }

        public void setRatioOuhu(String ratioOuhu) {
            this.ratioOuhu = ratioOuhu;
        }

        public String getRatioU() {
            return ratioU;
        }

        public void setRatioU(String ratioU) {
            this.ratioU = ratioU;
        }

        public String getRetimeset() {
            return retimeset;
        }

        public void setRetimeset(String retimeset) {
            this.retimeset = retimeset;
        }

        public Object getRoll() {
            return roll;
        }

        public void setRoll(Object roll) {
            this.roll = roll;
        }

        public String getScoreC() {
            return scoreC;
        }

        public void setScoreC(String scoreC) {
            this.scoreC = scoreC;
        }

        public String getScoreH() {
            return scoreH;
        }

        public void setScoreH(String scoreH) {
            this.scoreH = scoreH;
        }

        public String getScorec() {
            return scorec;
        }

        public void setScorec(String scorec) {
            this.scorec = scorec;
        }

        public String getScoreh() {
            return scoreh;
        }

        public void setScoreh(String scoreh) {
            this.scoreh = scoreh;
        }

        public String getStrEven() {
            return strEven;
        }

        public void setStrEven(String strEven) {
            this.strEven = strEven;
        }

        public String getStrOdd() {
            return strOdd;
        }

        public void setStrOdd(String strOdd) {
            this.strOdd = strOdd;
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

        public String getTimer() {
            return timer;
        }

        public void setTimer(String timer) {
            this.timer = timer;
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
