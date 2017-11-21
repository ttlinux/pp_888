package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */
public class SportsFootBallBean_Normal {

    String title;
    ArrayList<SportsFootBallBean_Normal_Item> sportsFootBallBeanNormals;
    public static String homestr[];
    public static String gueststr[];

    public ArrayList<SportsFootBallBean_Normal_Item> getSportsFootBallBeanNormals() {
        return sportsFootBallBeanNormals;
    }

    public void setSportsFootBallBeanNormals(ArrayList<SportsFootBallBean_Normal_Item> sportsFootBallBeanNormals) {
        this.sportsFootBallBeanNormals = sportsFootBallBeanNormals;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public static String[]  InitTexthomeTeam( SportsFootBallBean_Normal_Item item,int groupPosition,int childPosition)
    {
            String  strs[]=new String[12];
            homestr=new String[12];

            strs[0]=item.getIorMh();
            if(item.getStrong().equalsIgnoreCase("H"))
            {
                strs[1]=item.getRatio()+"#@"+item.getIorRh();
                strs[5]=item.getIorRc();
            }
            else
            {
                strs[1]=item.getIorRh();
                strs[5]=item.getRatio()+"#@"+item.getIorRc();
            }
//            ratioU  全场主队大球数
//            iorOuc  全场主队大球赔率
            strs[2]="大"+item.getRatioU()+"#@"+item.getIorOuc();
            strs[3]="单#@"+item.getIorEoo();
            strs[4]=item.getIorMc();
            strs[6]="小"+item.getRatioO()+"#@"+item.getIorOuh();
            strs[7]="双#@"+item.getIorEoe();
            strs[8]=item.getIorMn();
            strs[9]="";
            strs[10]="";
            strs[11]="";

        homestr[0]=item.getIorMh();
        homestr[1]=item.getIorRh();
        homestr[2]=item.getIorOuc();
        homestr[3]=item.getIorEoo();
        homestr[4]=item.getIorMc();
        homestr[5]=item.getIorRc();
        homestr[6]=item.getIorOuh();
        homestr[7]=item.getIorEoe();
        homestr[8]=item.getIorMn();
        homestr[9]="";
        homestr[10]="";
        homestr[11]="";



        return  strs;
    }

    public static String[] InitTextguestTeam(SportsFootBallBean_Normal_Item item,int groupPosition,int childPosition)
    {
        String  strs[]=new String[12];
        gueststr=new String[12];
            strs[0]=item.getIorHmh();
            if(item.getHstrong().equalsIgnoreCase("H"))
            {
                strs[1]=item.getHratio()+"#@"+item.getIorHrh();
                strs[5]=item.getIorHrc();
            }
            else
            {
                strs[1]=item.getIorHrh();
                strs[5]=item.getHratio()+"#@"+item.getIorHrc();
            }
            strs[2]="大"+item.getHratioU()+"#@"+item.getIorHouc();
            strs[3]="";
            strs[4]=item.getIorHmc();
            strs[6]="小"+item.getHratioO()+"#@"+item.getIorHouh();
            strs[7]="";
            strs[8]=item.getIorHmn();
            strs[9]="";
            strs[10]="";
            strs[11]="";

        gueststr[0]=item.getIorHmh();
        gueststr[1]=item.getIorHrh();
        gueststr[2]=item.getIorHouc();
        gueststr[3]="";
        gueststr[4]=item.getIorHmc();
        gueststr[5]=item.getIorHrc();
        gueststr[6]=item.getIorHouh();
        gueststr[7]="";
        gueststr[8]=item.getIorHmn();
        gueststr[9]="";
        gueststr[10]="";
        gueststr[11]="";

        return  strs;
    }

    public static ArrayList<SportsOrderBean> InitTextHomeTeamTag(SportsFootBallBean_Normal_Item item,String timeType,String rType)
    {
        String gid=item.getGid();
        String m_timeType=timeType;
        String m_rType=rType;
        ArrayList<SportsOrderBean> sportsbeans=new ArrayList<SportsOrderBean>();
        if(homestr==null || homestr.length!=12)return sportsbeans;
        for (int i = 0; i < 12; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setTimeType(m_timeType);
            bean.setrType(m_rType);
            bean.setPeilv(homestr[i]);

            bean.setTeamH(item.getTeamH());
            bean.setTeamc(item.getTeamC());
            bean.setLeague(item.getLeague());
            bean.setTag2("全场");
            int index=0;
            switch (i%4)
            {
//                dy rf dx ds
                case 0:
                    bean.setbType("dy");
                    bean.setdType("dy");
                    bean.setArg(1);
                    bean.setTag1("独赢");
                break;
                case 1:
                    bean.setbType("rq");
                    bean.setdType("rq");
                    bean.setArg(0);
                    bean.setRadio(item.getRatio());
                    bean.setTag1("让球");
                    break;
                case 2:
                    bean.setbType("dx");
                    bean.setdType("dx");
                    bean.setArg(0);
                    bean.setTag1("大小");
                    index=2;
                    break;
                case 3:
                    bean.setbType("ds");
                    bean.setdType("ds");
                    bean.setArg(1);
                    bean.setTag1("单双");
                    index=3;
                    break;
            }
            if(i<4)
            {
                bean.setSelection("H");
                if(index==2)bean.setTeamname("大"+item.getRatioU());
                else if(index==3)bean.setTeamname("单");
                else bean.setTeamname(bean.getTeamH());
            }
            if(3<i&&i<8)
            {
                bean.setSelection("C");
                if(index==2)bean.setTeamname("小"+item.getRatioO());
                else if(index==3)bean.setTeamname("双");
                else bean.setTeamname(bean.getTeamc());
            }
            if(7<i&&i<12)
            {
                bean.setSelection("N");
                bean.setTeamname("和局");
            }

            bean.setPeriod("f");
            sportsbeans.add(bean);
        }
        return sportsbeans;
    }

    public static ArrayList<SportsOrderBean> InitTextguestTeamTag(SportsFootBallBean_Normal_Item item,String timeType,String rType)
    {
        String gid=item.getGid();
        String m_timeType=timeType;
        String m_rType=rType;
        ArrayList<SportsOrderBean> sportsbeans=new ArrayList<SportsOrderBean>();
        if(gueststr==null || gueststr.length!=12)return sportsbeans;
        for (int i = 0; i < 12; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setTimeType(m_timeType);
            bean.setrType(m_rType);
            bean.setPeilv(gueststr[i]);

            bean.setTeamH(item.getTeamH());
            bean.setTeamc(item.getTeamC());
            bean.setLeague(item.getLeague());
            bean.setTag2("上半场");
            int index=0;
            switch (i%4)
            {
//                dy rf dx ds
                case 0:
                    bean.setbType("dy");
                    bean.setdType("dy");
                    bean.setArg(1);
                    bean.setTag1("独赢");
                    break;
                case 1:
                    bean.setbType("rq");
                    bean.setdType("rq");
                    bean.setArg(0);
                    bean.setTag1("让分");
                    bean.setRadio(item.getRatio());
                    break;
                case 2:
                    bean.setbType("dx");
                    bean.setdType("dx");
                    bean.setArg(0);
                    bean.setTag1("大小");
                    index=2;
                    break;
                case 3:
                    bean.setbType("ds");
                    bean.setdType("ds");
                    bean.setArg(1);
                    bean.setTag1("单双");
                    index=3;
                    break;
            }
            if(i<4)
            {
                bean.setSelection("H");
                if(index==2)bean.setTeamname("大"+item.getHratioU());
                else if(index==3)bean.setTeamname("单");
                else bean.setTeamname(bean.getTeamH());
            }
            if(3<i&&i<8)
            {
                bean.setSelection("C");
                if(index==2)bean.setTeamname("小"+item.getHratioO());
                else if(index==3)bean.setTeamname("双");
                else bean.setTeamname(bean.getTeamc());
            }
            if(7<i&&i<12)
            {
                bean.setSelection("N");
                bean.setTeamname("和局");
            }
            bean.setPeriod("h");
            sportsbeans.add(bean);
        }
        return sportsbeans;
    }
    public static class SportsFootBallBean_Normal_Item
    {
        /**
         * centerTv :
         * eventid :
         * gid : 2691340
         * gnumC : 10681
         * gnumH : 10682
         * hgid : 2691341
         * hot :
         * hratio :
         * hratioO :
         * hratioU :
         * hstrong :
         * iorEoe :
         * iorEoo :
         * iorHmc :
         * iorHmh :
         * iorHmn :
         * iorHouc :
         * iorHouh :
         * iorHrc :
         * iorHrh :
         * iorMc : 25.00
         * iorMh : 1.06
         * iorMn : 7.40
         * iorOuc : 1.26
         * iorOuh : 0.56
         * iorRc : 0.52
         * iorRh : 1.32
         * lastestscoreC :
         * lastestscoreH : H
         * league : 哥伦比亚乙组联赛
         * matchIndex : 1
         * matchPage : 1
         * matchTime : 2017-04-10 21:00:00
         * more : 5
         * no1 :
         * no2 :
         * no3 :
         * play : Y
         * ratio : 0 / 0.5
         * ratioO : 1.5
         * ratioU : 1.5
         * redcardC : 0
         * redcardH : 0
         * retimeset : 2H^35
         * roll : null
         * scoreC : 0
         * scoreH : 1
         * strEven :
         * strOdd :
         * strong : H
         * teamC : 喀他基那
         * teamH : 佩雷拉
         * timer : 80
         * tmp1 :
         * tmp2 :
         * tmp3 :
         */

        private String centerTv;
        private String eventid;
        private String gid;
        private String gnumC;
        private String gnumH;
        private String hgid;
        private String hot;
        private String hratio;
        private String hratioO;
        private String hratioU;
        private String hstrong;
        private String iorEoe;
        private String iorEoo;
        private String iorHmc;
        private String iorHmh;
        private String iorHmn;
        private String iorHouc;
        private String iorHouh;
        private String iorHrc;
        private String iorHrh;
        private String iorMc;
        private String iorMh;
        private String iorMn;
        private String iorOuc;
        private String iorOuh;
        private String iorRc;
        private String iorRh;
        private String lastestscoreC;
        private String lastestscoreH;
        private String league;
        private int matchIndex;
        private int matchPage;
        private String matchTime;
        private String more;
        private String no1;
        private String no2;
        private String no3;
        private String play;
        private String ratio;
        private String ratioO;
        private String ratioU;
        private String redcardC;
        private String redcardH;
        private String retimeset;
        private Object roll;
        private String scoreC;
        private String scoreH;
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

        public String getHgid() {
            return hgid;
        }

        public void setHgid(String hgid) {
            this.hgid = hgid;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public String getHratio() {
            return hratio;
        }

        public void setHratio(String hratio) {
            this.hratio = hratio;
        }

        public String getHratioO() {
            return hratioO;
        }

        public void setHratioO(String hratioO) {
            this.hratioO = hratioO;
        }

        public String getHratioU() {
            return hratioU;
        }

        public void setHratioU(String hratioU) {
            this.hratioU = hratioU;
        }

        public String getHstrong() {
            return hstrong;
        }

        public void setHstrong(String hstrong) {
            this.hstrong = hstrong;
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

        public String getIorHmc() {
            return iorHmc;
        }

        public void setIorHmc(String iorHmc) {
            this.iorHmc = iorHmc;
        }

        public String getIorHmh() {
            return iorHmh;
        }

        public void setIorHmh(String iorHmh) {
            this.iorHmh = iorHmh;
        }

        public String getIorHmn() {
            return iorHmn;
        }

        public void setIorHmn(String iorHmn) {
            this.iorHmn = iorHmn;
        }

        public String getIorHouc() {
            return iorHouc;
        }

        public void setIorHouc(String iorHouc) {
            this.iorHouc = iorHouc;
        }

        public String getIorHouh() {
            return iorHouh;
        }

        public void setIorHouh(String iorHouh) {
            this.iorHouh = iorHouh;
        }

        public String getIorHrc() {
            return iorHrc;
        }

        public void setIorHrc(String iorHrc) {
            this.iorHrc = iorHrc;
        }

        public String getIorHrh() {
            return iorHrh;
        }

        public void setIorHrh(String iorHrh) {
            this.iorHrh = iorHrh;
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

        public String getIorOuc() {
            return iorOuc;
        }

        public void setIorOuc(String iorOuc) {
            this.iorOuc = iorOuc;
        }

        public String getIorOuh() {
            return iorOuh;
        }

        public void setIorOuh(String iorOuh) {
            this.iorOuh = iorOuh;
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

        public String getLastestscoreC() {
            return lastestscoreC;
        }

        public void setLastestscoreC(String lastestscoreC) {
            this.lastestscoreC = lastestscoreC;
        }

        public String getLastestscoreH() {
            return lastestscoreH;
        }

        public void setLastestscoreH(String lastestscoreH) {
            this.lastestscoreH = lastestscoreH;
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

        public String getRatioU() {
            return ratioU;
        }

        public void setRatioU(String ratioU) {
            this.ratioU = ratioU;
        }

        public String getRedcardC() {
            return redcardC;
        }

        public void setRedcardC(String redcardC) {
            this.redcardC = redcardC;
        }

        public String getRedcardH() {
            return redcardH;
        }

        public void setRedcardH(String redcardH) {
            this.redcardH = redcardH;
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
