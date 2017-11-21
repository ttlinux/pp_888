package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/19.
 */
public class FootBallbodan {

    private String title;

    public ArrayList<FootBallbodan_item> getFootBallbodan_items() {
        return footBallbodan_items;
    }

    public void setFootBallbodan_items(ArrayList<FootBallbodan_item> footBallbodan_items) {
        this.footBallbodan_items = footBallbodan_items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private ArrayList<FootBallbodan_item> footBallbodan_items;

    public static String[] GetHbodanData(FootBallbodan_item item)
    {
        String str[]=new String[17];
        str[0]=item.getIorH1c0();
        str[1]=item.getIorH0c1();
        str[2]=item.getIorH2c0();
        str[3]=item.getIorH0c2();
        str[4]=item.getIorH2c1();
        str[5]=item.getIorH1c2();

        str[6]=item.getIorH3c0();
        str[7]=item.getIorH0c3();

        str[8]=item.getIorH3c1();
        str[9]=item.getIorH1c3();

        str[10]=item.getIorH3c2();
        str[11]=item.getIorH2c3();

        str[12]=item.getIorH0c0();
        str[13]=item.getIorH1c1();

        str[14]=item.getIorH2c2();
        str[15]=item.getIorH3c3();

        str[16]=item.getIorOvh();
        return str;
    }

    public static String[] GetbodanData(FootBallbodan_item item)
    {
        String str[]=new String[26];
        str[0]=item.getIorH1c0();
        str[1]=item.getIorH0c1();
        str[2]=item.getIorH2c0();
        str[3]=item.getIorH0c2();
        str[4]=item.getIorH2c1();
        str[5]=item.getIorH1c2();

        str[6]=item.getIorH3c0();
        str[7]=item.getIorH0c3();

        str[8]=item.getIorH3c1();
        str[9]=item.getIorH1c3();

        str[10]=item.getIorH3c2();
        str[11]=item.getIorH2c3();

        str[12]=item.getIorH4c0();//4:0
        str[13]=item.getIorH0c4();

        str[14]=item.getIorH4c1();//4:1
        str[15]=item.getIorH1c4();

        str[16]=item.getIorH4c2();//4:2
        str[17]=item.getIorH2c4();

        str[18]=item.getIorH4c3();//4:3
        str[19]=item.getIorH3c4();

        str[20]=item.getIorH0c0();//0:0
        str[21]=item.getIorH1c1();//1:1
        str[22]=item.getIorH2c2();//2:2
        str[23]=item.getIorH3c3();//3:3
        str[24]=item.getIorH4c4();//4:4

        str[25]=item.getIorOvh();
        return str;
    }

    public static ArrayList<SportsOrderBean> GetbodanDataTAG(String[] titles, String[] data, FootBallbodan_item item,String timetype,String rtype)
    {
        String gid=item.getGid();
        String m_timetype=timetype;
        String m_rtype=rtype;

        String temp=titles[0];
        int tempcount=0;
        ArrayList<SportsOrderBean> beans=new ArrayList<SportsOrderBean>();
        if(data==null || data.length!=26)return beans;
        for (int i = 0; i < data.length; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setTimeType(m_timetype);
            bean.setrType(m_rtype);
            bean.setbType("pd");

            bean.setTeamH(item.getTeamH());
            bean.setTeamc(item.getTeamC());
            bean.setLeague(item.getLeague());
            bean.setTag1("波胆");
            bean.setTag2("全场");
            if(i<20)
            {
                if(i%2==0 && i!=0)
                {
                    tempcount++;
                    temp=titles[tempcount];
                }

                if(i%2==0)
                {
                    bean.setSelection("H");
                    bean.setTeamname(temp);
                    bean.setbType(temp.replace(":", "_"));
                    bean.setdType(temp.replace(":", "_"));
                }
                else {
                    bean.setSelection("C");
                    bean.setTeamname(upsidedown(temp));
                    String str=temp.replace(":","_");
                    StringBuilder sb=new StringBuilder();
                    for (int j = str.length()-1; j >-1; j--) {
                        sb.append(str.charAt(j));
                    }
                    bean.setbType(sb.toString());
                    bean.setdType(sb.toString());
                }

            }
            else
            {
                tempcount++;
                if(i==data.length-1)
                {
                    bean.setdType("other");
                    bean.setbType("other");
                    bean.setTeamname("其他比分");
                }
                    else
                {
                    bean.setdType(titles[tempcount].replace(":","_"));
                    bean.setbType(titles[tempcount].replace(":","_"));
                    bean.setTeamname(titles[tempcount]);
                }
                bean.setSelection("H");
            }

            bean.setPeriod("f");
            bean.setPeilv(data[i]);
            bean.setArg(1);
            beans.add(bean);
        }

        return beans;
    }

    public static ArrayList<SportsOrderBean> GetHbodanDataTAG(String[] Htitles, String[] data, FootBallbodan_item item,String timetype,String rtype)
    {
        String gid=item.getGid();
        String m_timetype=timetype;
        String m_rtype=rtype;

        String temp=Htitles[0];
        int tempcount=0;
        ArrayList<SportsOrderBean> beans=new ArrayList<SportsOrderBean>();
        if(data==null || data.length!=17)return beans;
        for (int i = 0; i < data.length; i++) {
            SportsOrderBean bean=new SportsOrderBean();
            bean.setGid(gid);
            bean.setTimeType(m_timetype);
            bean.setrType(m_rtype);


            bean.setTeamH(item.getTeamH());
            bean.setTeamc(item.getTeamC());
            bean.setLeague(item.getLeague());
            bean.setTag1("波胆");
            bean.setTag2("半场");
            if(i<12)
            {
                if(i%2==0 && i!=0)
                {
                    tempcount++;
                    temp=Htitles[tempcount];
                }
                bean.setbType(temp.replace(":","_"));
                bean.setdType(temp.replace(":","_"));
                if(i%2==0)
                {
                    bean.setSelection("H");
                    bean.setTeamname(temp);
                    bean.setbType(temp.replace(":", "_"));
                    bean.setdType(temp.replace(":", "_"));
                }
                else
                {
                    bean.setSelection("C");
                    bean.setTeamname(upsidedown(temp));
                    String str=temp.replace(":","_");
                    StringBuilder sb=new StringBuilder();
                    for (int j = str.length()-1; j >-1; j--) {
                        sb.append(str.charAt(j));
                    }
                    bean.setbType(sb.toString());
                    bean.setdType(sb.toString());
                }
            }
            else
            {
                tempcount++;
                if(i==data.length-1)
                {
                    bean.setdType("other");
                    bean.setbType("other");
                    bean.setTeamname("其他比分");
                }
                else
                {
                    bean.setdType(Htitles[tempcount].replace(":","_"));
                    bean.setbType(Htitles[tempcount].replace(":","_"));
                    bean.setTeamname(Htitles[tempcount]);
                }

                bean.setSelection("H");
            }


            bean.setPeriod("h");
            bean.setPeilv(data[i]);
            bean.setArg(1);
            beans.add(bean);
        }

        return beans;
    }


    private static String upsidedown(String str)
    {

        char temp;
        if(str!=null && str.length()==3)
        {
            char ch[]=str.toCharArray();
            temp=ch[0];
            ch[0]=ch[2];
            ch[2]=temp;
            return String.valueOf(ch);
        }
        return "";
    }
    public static class FootBallbodan_item
    {
        /**
         * gid : 2694139
         * gnumC : 30151
         * gnumH : 30152
         * iorH0c0 : 2.72
         * iorH0c1 : 5.1
         * iorH0c2 : 25
         * iorH0c3 : 141
         * iorH0c4 :
         * iorH1c0 : 3
         * iorH1c1 : 7.8
         * iorH1c2 : 38
         * iorH1c3 : 151
         * iorH1c4 :
         * iorH2c0 : 9.2
         * iorH2c1 : 23
         * iorH2c2 : 101
         * iorH2c3 : 151
         * iorH2c4 :
         * iorH3c0 : 40
         * iorH3c1 : 91
         * iorH3c2 : 151
         * iorH3c3 : 151
         * iorH3c4 :
         * iorH4c0 :
         * iorH4c1 :
         * iorH4c2 :
         * iorH4c3 :
         * iorH4c4 :
         * iorOvc : 101
         * iorOvh : 101
         * league : 德国丙组联赛
         * matchIndex : 1
         * matchPage : 1
         * matchTime : 2017-04-19 13:00:00
         * roll : 1
         * strong : H
         * tag : 1
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
        private String iorH0c0;
        private String iorH0c1;
        private String iorH0c2;
        private String iorH0c3;
        private String iorH0c4;
        private String iorH1c0;
        private String iorH1c1;
        private String iorH1c2;
        private String iorH1c3;
        private String iorH1c4;
        private String iorH2c0;
        private String iorH2c1;
        private String iorH2c2;
        private String iorH2c3;
        private String iorH2c4;
        private String iorH3c0;
        private String iorH3c1;
        private String iorH3c2;
        private String iorH3c3;
        private String iorH3c4;
        private String iorH4c0;
        private String iorH4c1;
        private String iorH4c2;
        private String iorH4c3;
        private String iorH4c4;
        private String iorOvc;
        private String iorOvh;
        private String league;
        private int matchIndex;
        private int matchPage;
        private String matchTime;
        private String roll;
        private String strong;
        private int tag;
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

        public String getIorH0c0() {
            return iorH0c0;
        }

        public void setIorH0c0(String iorH0c0) {
            this.iorH0c0 = iorH0c0;
        }

        public String getIorH0c1() {
            return iorH0c1;
        }

        public void setIorH0c1(String iorH0c1) {
            this.iorH0c1 = iorH0c1;
        }

        public String getIorH0c2() {
            return iorH0c2;
        }

        public void setIorH0c2(String iorH0c2) {
            this.iorH0c2 = iorH0c2;
        }

        public String getIorH0c3() {
            return iorH0c3;
        }

        public void setIorH0c3(String iorH0c3) {
            this.iorH0c3 = iorH0c3;
        }

        public String getIorH0c4() {
            return iorH0c4;
        }

        public void setIorH0c4(String iorH0c4) {
            this.iorH0c4 = iorH0c4;
        }

        public String getIorH1c0() {
            return iorH1c0;
        }

        public void setIorH1c0(String iorH1c0) {
            this.iorH1c0 = iorH1c0;
        }

        public String getIorH1c1() {
            return iorH1c1;
        }

        public void setIorH1c1(String iorH1c1) {
            this.iorH1c1 = iorH1c1;
        }

        public String getIorH1c2() {
            return iorH1c2;
        }

        public void setIorH1c2(String iorH1c2) {
            this.iorH1c2 = iorH1c2;
        }

        public String getIorH1c3() {
            return iorH1c3;
        }

        public void setIorH1c3(String iorH1c3) {
            this.iorH1c3 = iorH1c3;
        }

        public String getIorH1c4() {
            return iorH1c4;
        }

        public void setIorH1c4(String iorH1c4) {
            this.iorH1c4 = iorH1c4;
        }

        public String getIorH2c0() {
            return iorH2c0;
        }

        public void setIorH2c0(String iorH2c0) {
            this.iorH2c0 = iorH2c0;
        }

        public String getIorH2c1() {
            return iorH2c1;
        }

        public void setIorH2c1(String iorH2c1) {
            this.iorH2c1 = iorH2c1;
        }

        public String getIorH2c2() {
            return iorH2c2;
        }

        public void setIorH2c2(String iorH2c2) {
            this.iorH2c2 = iorH2c2;
        }

        public String getIorH2c3() {
            return iorH2c3;
        }

        public void setIorH2c3(String iorH2c3) {
            this.iorH2c3 = iorH2c3;
        }

        public String getIorH2c4() {
            return iorH2c4;
        }

        public void setIorH2c4(String iorH2c4) {
            this.iorH2c4 = iorH2c4;
        }

        public String getIorH3c0() {
            return iorH3c0;
        }

        public void setIorH3c0(String iorH3c0) {
            this.iorH3c0 = iorH3c0;
        }

        public String getIorH3c1() {
            return iorH3c1;
        }

        public void setIorH3c1(String iorH3c1) {
            this.iorH3c1 = iorH3c1;
        }

        public String getIorH3c2() {
            return iorH3c2;
        }

        public void setIorH3c2(String iorH3c2) {
            this.iorH3c2 = iorH3c2;
        }

        public String getIorH3c3() {
            return iorH3c3;
        }

        public void setIorH3c3(String iorH3c3) {
            this.iorH3c3 = iorH3c3;
        }

        public String getIorH3c4() {
            return iorH3c4;
        }

        public void setIorH3c4(String iorH3c4) {
            this.iorH3c4 = iorH3c4;
        }

        public String getIorH4c0() {
            return iorH4c0;
        }

        public void setIorH4c0(String iorH4c0) {
            this.iorH4c0 = iorH4c0;
        }

        public String getIorH4c1() {
            return iorH4c1;
        }

        public void setIorH4c1(String iorH4c1) {
            this.iorH4c1 = iorH4c1;
        }

        public String getIorH4c2() {
            return iorH4c2;
        }

        public void setIorH4c2(String iorH4c2) {
            this.iorH4c2 = iorH4c2;
        }

        public String getIorH4c3() {
            return iorH4c3;
        }

        public void setIorH4c3(String iorH4c3) {
            this.iorH4c3 = iorH4c3;
        }

        public String getIorH4c4() {
            return iorH4c4;
        }

        public void setIorH4c4(String iorH4c4) {
            this.iorH4c4 = iorH4c4;
        }

        public String getIorOvc() {
            return iorOvc;
        }

        public void setIorOvc(String iorOvc) {
            this.iorOvc = iorOvc;
        }

        public String getIorOvh() {
            return iorOvh;
        }

        public void setIorOvh(String iorOvh) {
            this.iorOvh = iorOvh;
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

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
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
