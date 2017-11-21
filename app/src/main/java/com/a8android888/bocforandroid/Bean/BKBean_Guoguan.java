package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/17.
 */
public class BKBean_Guoguan {

    private ArrayList<BKitemBean> bKitemBeans;

    public String title;

    static String homestr[];
    static String gueststr[];

    public ArrayList<BKitemBean> getbKitemBeans() {
        return bKitemBeans;
    }


    public static String[] GetBasketBallList(BKBean_Guoguan.BKitemBean bean)
    {
        String strp[]=new String[10];
        homestr=new String[10];
        strp[0]=bean.getIorMh();
        if(bean.getStrong().equalsIgnoreCase("H"))
        {
            strp[1]=bean.getRatio()+"#@"+bean.getIorPrh();
            strp[6]=bean.getIorPrc();
        }
        else
        {
            strp[6]=bean.getRatio()+"#@"+bean.getIorPrc();
            strp[1]=bean.getIorPrh();
        }

        strp[2]="大"+bean.getRatioO()+"#@"+ bean.getIorPouc();

//        RatioPouho主队大球数 IorPouho主队大球赔率 RatioPouhu主队小球数 IorPouhu主队小球赔率
//        RatioPouco客队大球数 IorPouco客队大球赔率 RatioPoucu客队小球数 IorPoucu客队小球赔率
        strp[3]="大"+bean.getRatioPouho()+"#@"+ bean.getIorPouho();
        strp[4]="小"+bean.getRatioPouhu()+"#@"+ bean.getIorPouhu();
        strp[5]= bean.getIorMc();
        strp[7]= "小"+bean.getRatioU()+"#@"+ bean.getIorPouh();
        strp[8]= "大"+bean.getRatioPouco()+"#@"+ bean.getIorPouco();
        strp[9]= "小"+bean.getRatioPoucu()+"#@"+ bean.getIorPoucu();

        homestr[0]=bean.getIorMh();
        homestr[1]=bean.getIorPrh();
        homestr[2]=bean.getIorPouc();
        homestr[3]=bean.getIorPouho();
        homestr[4]=bean.getIorPouhu();
        homestr[5]=bean.getIorMc();
        homestr[6]=bean.getIorPrc();
        homestr[7]=bean.getIorPouh();
        homestr[8]=bean.getIorPouco();
        homestr[9]=bean.getIorPoucu();

        return strp;
    }

    public static ArrayList<SportsOrderBean> GetBasketBallListTAG(BKBean_Guoguan.BKitemBean mbean,String timeType,String rType)
    {
        String gid=mbean.getGid();
        String gidm=mbean.getGidm();
        String m_timeType=timeType;
        String m_rType=rType;
        ArrayList<SportsOrderBean> sportsOrderBeans=new ArrayList<SportsOrderBean>();
        if(homestr==null || homestr.length!=10)return sportsOrderBeans;
        for (int i = 0; i <homestr.length; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setGidm(gidm);
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
                else if(index==3)bean.setTeamname("大"+mbean.getRatioPouho());
                else if(index==4)bean.setTeamname("小"+mbean.getRatioPouhu());
                else bean.setTeamname(bean.getTeamH());
            }
            if(4<i&&i<10)
            {
                bean.setSelection("C");
                bean.setTag2(mbean.getTeamC());
                if(index==2)bean.setTeamname("小"+mbean.getRatioU());
                else if(index==3)bean.setTeamname("大"+mbean.getRatioPouco());
                else if(index==4)bean.setTeamname("小"+mbean.getRatioPoucu());
                else bean.setTeamname(bean.getTeamc());
            }

            bean.setPeriod("f");
            sportsOrderBeans.add(bean);

        }
        return sportsOrderBeans;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setbKitemBeans(ArrayList<BKitemBean> bKitemBeans) {
        this.bKitemBeans = bKitemBeans;
    }



    public static class BKitemBean
    {

        /**
         * gid : 2144382
         * gidm : 325939
         * gnumC : 70147
         * gnumH : 70148
         * iorMc :
         * iorMh :
         * iorMn :
         * iorPe : 1.92
         * iorPo : 1.92
         * iorPouc : 1.67
         * iorPouco : 1.87
         * iorPoucu : 1.87
         * iorPouh : 2.07
         * iorPouho : 1.91
         * iorPouhu : 1.83
         * iorPrc : 2.03
         * iorPrh : 1.75
         * ismaster : Y
         * league : 纽西兰国家篮球联赛
         * matchIndex : 1
         * matchPage : 1
         * matchTime : 2017-04-16 23:00:00
         * more : 7
         * parMaxlimit : 10
         * parMinlimit : 3
         * ratio : 11
         * ratioO : 190.5
         * ratioPouco : 90
         * ratioPoucu : 90
         * ratioPouho : 102.5
         * ratioPouhu : 102.5
         * ratioU : 190.5
         * roll : 0
         * strong : H
         * teamC : 坎特伯雷公羊
         * teamH : 惠灵顿圣徒
         * timeType : today
         * tmp1 :
         * tmp2 :
         * tmp3 :
         */

        private String gid;
        private String gidm;
        private String gnumC;
        private String gnumH;
        private String iorMc;
        private String iorMh;
        private String iorMn;
        private String iorPe;
        private String iorPo;
        private String iorPouc;
        private String iorPouco;
        private String iorPoucu;
        private String iorPouh;
        private String iorPouho;
        private String iorPouhu;
        private String iorPrc;
        private String iorPrh;
        private String ismaster;
        private String league;
        private int matchIndex;
        private int matchPage;
        private String matchTime;
        private String more;
        private String parMaxlimit;
        private String parMinlimit;
        private String ratio;
        private String ratioO;
        private String ratioPouco;
        private String ratioPoucu;
        private String ratioPouho;
        private String ratioPouhu;
        private String ratioU;
        private int roll;
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

        public String getIorPe() {
            return iorPe;
        }

        public void setIorPe(String iorPe) {
            this.iorPe = iorPe;
        }

        public String getIorPo() {
            return iorPo;
        }

        public void setIorPo(String iorPo) {
            this.iorPo = iorPo;
        }

        public String getIorPouc() {
            return iorPouc;
        }

        public void setIorPouc(String iorPouc) {
            this.iorPouc = iorPouc;
        }

        public String getIorPouco() {
            return iorPouco;
        }

        public void setIorPouco(String iorPouco) {
            this.iorPouco = iorPouco;
        }

        public String getIorPoucu() {
            return iorPoucu;
        }

        public void setIorPoucu(String iorPoucu) {
            this.iorPoucu = iorPoucu;
        }

        public String getIorPouh() {
            return iorPouh;
        }

        public void setIorPouh(String iorPouh) {
            this.iorPouh = iorPouh;
        }

        public String getIorPouho() {
            return iorPouho;
        }

        public void setIorPouho(String iorPouho) {
            this.iorPouho = iorPouho;
        }

        public String getIorPouhu() {
            return iorPouhu;
        }

        public void setIorPouhu(String iorPouhu) {
            this.iorPouhu = iorPouhu;
        }

        public String getIorPrc() {
            return iorPrc;
        }

        public void setIorPrc(String iorPrc) {
            this.iorPrc = iorPrc;
        }

        public String getIorPrh() {
            return iorPrh;
        }

        public void setIorPrh(String iorPrh) {
            this.iorPrh = iorPrh;
        }

        public String getIsmaster() {
            return ismaster;
        }

        public void setIsmaster(String ismaster) {
            this.ismaster = ismaster;
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

        public String getParMaxlimit() {
            return parMaxlimit;
        }

        public void setParMaxlimit(String parMaxlimit) {
            this.parMaxlimit = parMaxlimit;
        }

        public String getParMinlimit() {
            return parMinlimit;
        }

        public void setParMinlimit(String parMinlimit) {
            this.parMinlimit = parMinlimit;
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

        public String getRatioPouco() {
            return ratioPouco;
        }

        public void setRatioPouco(String ratioPouco) {
            this.ratioPouco = ratioPouco;
        }

        public String getRatioPoucu() {
            return ratioPoucu;
        }

        public void setRatioPoucu(String ratioPoucu) {
            this.ratioPoucu = ratioPoucu;
        }

        public String getRatioPouho() {
            return ratioPouho;
        }

        public void setRatioPouho(String ratioPouho) {
            this.ratioPouho = ratioPouho;
        }

        public String getRatioPouhu() {
            return ratioPouhu;
        }

        public void setRatioPouhu(String ratioPouhu) {
            this.ratioPouhu = ratioPouhu;
        }

        public String getRatioU() {
            return ratioU;
        }

        public void setRatioU(String ratioU) {
            this.ratioU = ratioU;
        }

        public int getRoll() {
            return roll;
        }

        public void setRoll(int roll) {
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
