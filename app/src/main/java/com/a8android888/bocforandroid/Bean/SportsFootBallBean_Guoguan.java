package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */
public class SportsFootBallBean_Guoguan {

    private String title;

    private ArrayList<FootBallBean_Guoguan_item> footBallBean_guoguan_items=new ArrayList<FootBallBean_Guoguan_item>();

    static String homestr[];
    static String gueststr[];
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<FootBallBean_Guoguan_item> getFootBallBean_guoguan_items() {
        return footBallBean_guoguan_items;
    }

    public void setFootBallBean_guoguan_items(ArrayList<FootBallBean_Guoguan_item> footBallBean_guoguan_items) {
        this.footBallBean_guoguan_items = footBallBean_guoguan_items;
    }



    public static String[]  InitTexthomeTeam( FootBallBean_Guoguan_item item,int groupPosition,int childPosition)
    {
        String  strs[]=new String[12];
        homestr=new String[12];
        strs[0]=item.getIorMh();
        if(item.getStrong().equalsIgnoreCase("H"))
        {
            strs[1]=item.getRatio()+"#@"+item.getIorPrh();
            strs[5]=item.getIorPrc();
        }
        else
        {
            strs[1]=item.getIorPrh();
            strs[5]=item.getRatio()+"#@"+item.getIorPrc();
        }
//            ratioU  全场主队大球数
//            iorOuc  全场主队大球赔率
        strs[2]="大"+item.getRatioU()+"#@"+item.getIorPouc();
        strs[3]="单#@"+item.getIorPeoo();
        strs[4]=item.getIorMc();
        strs[6]="小"+item.getRatioO()+"#@"+item.getIorPouh();
        strs[7]="双#@"+item.getIorPeoe();
        strs[8]=item.getIorMn();
        strs[9]="";
        strs[10]="";
        strs[11]="";

        homestr[0]=item.getIorMh();
        homestr[1]=item.getIorPrh();
        homestr[2]=item.getIorPouc();
        homestr[3]=item.getIorPeoo();
        homestr[4]=item.getIorMc();
        homestr[5]=item.getIorPrc();
        homestr[6]=item.getIorPouh();
        homestr[7]=item.getIorPeoe();
        homestr[8]=item.getIorMn();
        homestr[9]="";
        homestr[10]="";
        homestr[11]="";


        return  strs;
    }

    public static String[] InitTextguestTeam(FootBallBean_Guoguan_item item,int groupPosition,int childPosition)
    {
        String  strs[]=new String[12];
        gueststr=new String[12];
        strs[0]=item.getIorHmh();
        if(item.getHstrong().equalsIgnoreCase("H"))
        {
            strs[1]=item.getHratio()+"#@"+item.getIorHprh();
            strs[5]=item.getIorHprc();
        }
        else
        {
            strs[1]=item.getIorHprh();
            strs[5]=item.getHratio()+"#@"+item.getIorHprc();
        }
        strs[2]="大"+item.getHratioU()+"#@"+item.getIorHpouc();
        strs[3]="";
        strs[4]=item.getIorHmc();
        strs[6]="小"+item.getHratioO()+"#@"+item.getIorHpouh();
        strs[7]="";
        strs[8]=item.getIorHmn();
        strs[9]="";
        strs[10]="";
        strs[11]="";

        gueststr[0]=item.getIorHmh();
        gueststr[1]=item.getIorHprh();
        gueststr[2]=item.getIorHpouc();
        gueststr[3]="";
        gueststr[4]=item.getIorHmc();
        gueststr[5]=item.getIorHprc();
        gueststr[6]=item.getIorHpouh();
        gueststr[7]="";
        gueststr[8]=item.getIorHmn();
        gueststr[9]="";
        gueststr[10]="";
        gueststr[11]="";
        return  strs;
    }


    public static ArrayList<SportsOrderBean> InitTextHomeTeamTag(FootBallBean_Guoguan_item item,String timeType,String rType)
    {
        String gid=item.getGid();
        String gidm=item.getGidm();
        String m_timeType=timeType;
        String m_rType=rType;
        ArrayList<SportsOrderBean> sportsbeans=new ArrayList<SportsOrderBean>();
        if(homestr==null || homestr.length!=12)return sportsbeans;
        for (int i = 0; i < 12; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setGidm(gidm);
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

    public static ArrayList<SportsOrderBean> InitTextguestTeamTag(FootBallBean_Guoguan_item item,String timeType,String rType)
    {
        String gid=item.getGid();
        String gidm=item.getGidm();
        String m_timeType=timeType;
        String m_rType=rType;
        ArrayList<SportsOrderBean> sportsbeans=new ArrayList<SportsOrderBean>();
        if(gueststr==null || gueststr.length!=12)return sportsbeans;
        for (int i = 0; i < 12; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setGidm(gidm);
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
    public static class FootBallBean_Guoguan_item
    {
        /**
         * gid : 2708142
         * gidm : 1643444
         * gnumC : 32471
         * gnumH : 32472
         * hgid : 2708143
         * hratio :
         * hratioO :
         * hratioU :
         * hstrong : H
         * iorHmc :
         * iorHmh :
         * iorHmn :
         * iorHpouc :
         * iorHpouh :
         * iorHprc :
         * iorHprh :
         * iorMc : 1.96
         * iorMh : 3.40
         * iorMn : 3.20
         * iorPeoe : 1.91
         * iorPeoo : 1.94
         * iorPouc : 1.95
         * iorPouh : 1.79
         * iorPrc : 1.95
         * iorPrh : 1.79
         * league : 哈萨克斯坦杯
         * matchIndex : 1
         * matchPage : 1
         * matchTime : 2017-04-19 04:00:00
         * more : 5
         * parMaxlimit : 10
         * parMinlimit : 3
         * ratio : 0.50
         * ratioO : 2 / 2.5
         * ratioU : 2 / 2.5
         * roll : 1
         * strong : C
         * teamC : 阿特劳
         * teamH : 凯兰[中]
         * timeType : today
         * tmp1 :
         * tmp2 :
         * tmp3 :
         */

        private String gid;
        private String gidm;
        private String gnumC;
        private String gnumH;
        private String hgid;
        private String hratio;
        private String hratioO;
        private String hratioU;
        private String hstrong;
        private String iorHmc;
        private String iorHmh;
        private String iorHmn;
        private String iorHpouc;
        private String iorHpouh;
        private String iorHprc;
        private String iorHprh;
        private String iorMc;
        private String iorMh;
        private String iorMn;
        private String iorPeoe;
        private String iorPeoo;
        private String iorPouc;
        private String iorPouh;
        private String iorPrc;
        private String iorPrh;
        private String league;
        private int matchIndex;
        private int matchPage;
        private String matchTime;
        private String more;
        private String parMaxlimit;
        private String parMinlimit;
        private String ratio;
        private String ratioO;
        private String ratioU;
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

        public String getHgid() {
            return hgid;
        }

        public void setHgid(String hgid) {
            this.hgid = hgid;
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

        public String getIorHpouc() {
            return iorHpouc;
        }

        public void setIorHpouc(String iorHpouc) {
            this.iorHpouc = iorHpouc;
        }

        public String getIorHpouh() {
            return iorHpouh;
        }

        public void setIorHpouh(String iorHpouh) {
            this.iorHpouh = iorHpouh;
        }

        public String getIorHprc() {
            return iorHprc;
        }

        public void setIorHprc(String iorHprc) {
            this.iorHprc = iorHprc;
        }

        public String getIorHprh() {
            return iorHprh;
        }

        public void setIorHprh(String iorHprh) {
            this.iorHprh = iorHprh;
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

        public String getIorPeoe() {
            return iorPeoe;
        }

        public void setIorPeoe(String iorPeoe) {
            this.iorPeoe = iorPeoe;
        }

        public String getIorPeoo() {
            return iorPeoo;
        }

        public void setIorPeoo(String iorPeoo) {
            this.iorPeoo = iorPeoo;
        }

        public String getIorPouc() {
            return iorPouc;
        }

        public void setIorPouc(String iorPouc) {
            this.iorPouc = iorPouc;
        }

        public String getIorPouh() {
            return iorPouh;
        }

        public void setIorPouh(String iorPouh) {
            this.iorPouh = iorPouh;
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

        public String getRatioU() {
            return ratioU;
        }

        public void setRatioU(String ratioU) {
            this.ratioU = ratioU;
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
