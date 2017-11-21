package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */
public class BKSportsResultBean {

    private String title;

    public static String[] Getdata(ArrayList<?> data,int groupPosition, int childPosition)
    {
        String str[]=new String[14];
        if(data!=null && data.size()>0 && data.get(0) instanceof BKSportsResultBean)
        {
            BKSportsResultBean.SportsResultBean_item bkSportsResultBean=((BKSportsResultBean)data.get(groupPosition)).getSportsResultBean_items().get(childPosition);
            str[0]=bkSportsResultBean.getStageH1()+"";
            str[2]=bkSportsResultBean.getStageH2()+"";
            str[4]=bkSportsResultBean.getStageH3()+"";
            str[6]=bkSportsResultBean.getStageH4()+"";

            str[8]=bkSportsResultBean.getStageHS()+"";//S
            str[10]=bkSportsResultBean.getStageHX()+"";//X
            str[12]=bkSportsResultBean.getStageHF()+"";//F

            str[1]=bkSportsResultBean.getStageC1()+"";
            str[3]=bkSportsResultBean.getStageC2()+"";
            str[5]=bkSportsResultBean.getStageC3()+"";
            str[7]=bkSportsResultBean.getStageC4()+"";

            str[9]=bkSportsResultBean.getStageCS()+"";
            str[11]=bkSportsResultBean.getStageCX()+"";
            str[13]=bkSportsResultBean.getStageCF()+"";

            for (int i = 0; i <str.length ; i++) {
                if(str[i].equalsIgnoreCase("null"))
                {
                    str[i]="";
                }
            }
        }
        return str;
    }

    public ArrayList<SportsResultBean_item> getSportsResultBean_items() {
        return sportsResultBean_items;
    }

    public void setSportsResultBean_items(ArrayList<SportsResultBean_item> sportsResultBean_items) {
        this.sportsResultBean_items = sportsResultBean_items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private ArrayList<SportsResultBean_item>  sportsResultBean_items;

    public static class SportsResultBean_item
    {

        /**
         * gid : 2141001
         * id : 59422114
         * league : 澳洲ACT篮球超级联赛1
         * leg : 101428
         * legGid : 101428_2141001
         * matchIndex : 1
         * matchRealTime : 2017-04-12 05:00:00
         * matchSettled : 0
         * matchStatus : 1
         * matchTime : 04-12<br />05:00a
         * matchType : BK
         * searchTime : 1491969600000
         * stageC1 : 30
         * stageC2 : 23
         * stageC3 : 24
         * stageC4 : 27
         * stageCA : null
         * stageCF : 104
         * stageCS : 53
         * stageCX : 51
         * stageH1 : 15
         * stageH2 : 16
         * stageH3 : 19
         * stageH4 : 21
         * stageHA : null
         * stageHF : 71
         * stageHS : 31
         * stageHX : 40
         * teamC : 维京
         * teamH : 韦斯顿
         * tmp1 :
         * tmp2 :
         * tmp3 :
         */

        private String gid;
        private int id;
        private String league;
        private String leg;
        private String legGid;
        private int matchIndex;
        private String matchRealTime;
        private int matchSettled;
        private int matchStatus;
        private String matchTime;
        private String matchType;
        private long searchTime;
        private int stageC1;
        private int stageC2;
        private int stageC3;
        private int stageC4;
        private Object stageCA;
        private int stageCF;
        private int stageCS;
        private int stageCX;
        private int stageH1;
        private int stageH2;
        private int stageH3;
        private int stageH4;
        private Object stageHA;
        private int stageHF;
        private int stageHS;
        private int stageHX;
        private String teamC;
        private String teamH;
        private String tmp1;
        private String tmp2;
        private String tmp3;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public String getLeg() {
            return leg;
        }

        public void setLeg(String leg) {
            this.leg = leg;
        }

        public String getLegGid() {
            return legGid;
        }

        public void setLegGid(String legGid) {
            this.legGid = legGid;
        }

        public int getMatchIndex() {
            return matchIndex;
        }

        public void setMatchIndex(int matchIndex) {
            this.matchIndex = matchIndex;
        }

        public String getMatchRealTime() {
            return matchRealTime;
        }

        public void setMatchRealTime(String matchRealTime) {
            this.matchRealTime = matchRealTime;
        }

        public int getMatchSettled() {
            return matchSettled;
        }

        public void setMatchSettled(int matchSettled) {
            this.matchSettled = matchSettled;
        }

        public int getMatchStatus() {
            return matchStatus;
        }

        public void setMatchStatus(int matchStatus) {
            this.matchStatus = matchStatus;
        }

        public String getMatchTime() {
            return matchTime;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public String getMatchType() {
            return matchType;
        }

        public void setMatchType(String matchType) {
            this.matchType = matchType;
        }

        public long getSearchTime() {
            return searchTime;
        }

        public void setSearchTime(long searchTime) {
            this.searchTime = searchTime;
        }

        public int getStageC1() {
            return stageC1;
        }

        public void setStageC1(int stageC1) {
            this.stageC1 = stageC1;
        }

        public int getStageC2() {
            return stageC2;
        }

        public void setStageC2(int stageC2) {
            this.stageC2 = stageC2;
        }

        public int getStageC3() {
            return stageC3;
        }

        public void setStageC3(int stageC3) {
            this.stageC3 = stageC3;
        }

        public int getStageC4() {
            return stageC4;
        }

        public void setStageC4(int stageC4) {
            this.stageC4 = stageC4;
        }

        public Object getStageCA() {
            return stageCA;
        }

        public void setStageCA(Object stageCA) {
            this.stageCA = stageCA;
        }

        public int getStageCF() {
            return stageCF;
        }

        public void setStageCF(int stageCF) {
            this.stageCF = stageCF;
        }

        public int getStageCS() {
            return stageCS;
        }

        public void setStageCS(int stageCS) {
            this.stageCS = stageCS;
        }

        public int getStageCX() {
            return stageCX;
        }

        public void setStageCX(int stageCX) {
            this.stageCX = stageCX;
        }

        public int getStageH1() {
            return stageH1;
        }

        public void setStageH1(int stageH1) {
            this.stageH1 = stageH1;
        }

        public int getStageH2() {
            return stageH2;
        }

        public void setStageH2(int stageH2) {
            this.stageH2 = stageH2;
        }

        public int getStageH3() {
            return stageH3;
        }

        public void setStageH3(int stageH3) {
            this.stageH3 = stageH3;
        }

        public int getStageH4() {
            return stageH4;
        }

        public void setStageH4(int stageH4) {
            this.stageH4 = stageH4;
        }

        public Object getStageHA() {
            return stageHA;
        }

        public void setStageHA(Object stageHA) {
            this.stageHA = stageHA;
        }

        public int getStageHF() {
            return stageHF;
        }

        public void setStageHF(int stageHF) {
            this.stageHF = stageHF;
        }

        public int getStageHS() {
            return stageHS;
        }

        public void setStageHS(int stageHS) {
            this.stageHS = stageHS;
        }

        public int getStageHX() {
            return stageHX;
        }

        public void setStageHX(int stageHX) {
            this.stageHX = stageHX;
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
