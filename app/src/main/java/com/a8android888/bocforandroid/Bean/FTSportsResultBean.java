package com.a8android888.bocforandroid.Bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */
public class FTSportsResultBean {

    private String title;

    public static String[] Getdata(ArrayList<?> data,int groupPosition, int childPosition)
    {
        String str[]=new String[4];
        if(data!=null && data.size()>0 && data.get(0) instanceof FTSportsResultBean)
        {
            FTSportsResultBean.FTSportsResultBean_item ftSportsResultBean_item=((FTSportsResultBean)data.get(groupPosition)).getFtSportsResultBean_items().get(childPosition);
//            hrScoreH hrScoreC
//            flScoreH flScoreC
            str[0]=ftSportsResultBean_item.getHrScoreHString()+"";
            str[1]=ftSportsResultBean_item.getHrScoreCString()+"";
            str[2]=ftSportsResultBean_item.getFlScoreHString()+"";
            str[3]=ftSportsResultBean_item.getFlScoreCString()+"";

            for (int i = 0; i < str.length; i++) {
                if(str[i].equalsIgnoreCase("null"))
                {
                    str[i]="";
                }
            }
        }
        return str;
    }

    public ArrayList<FTSportsResultBean_item> getFtSportsResultBean_items() {
        return ftSportsResultBean_items;
    }

    public void setFtSportsResultBean_items(ArrayList<FTSportsResultBean_item> ftSportsResultBean_items) {
        this.ftSportsResultBean_items = ftSportsResultBean_items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private ArrayList<FTSportsResultBean_item> ftSportsResultBean_items;

    public static class FTSportsResultBean_item
    {


        /**
         * flScoreC : 5
         * flScoreCCal :
         * flScoreH : 4
         * flScoreHCal :
         * gid : 2700408
         * hrScoreC : 2
         * hrScoreCCal :
         * hrScoreH : 2
         * hrScoreHCal :
         * id : 257370744
         * league : 欧洲冠军杯-特别投注
         * leg : 104126
         * legGid : 104126_2700408
         * matchIndex : 164
         * matchRealTime : 2017-04-12 12:45:00
         * matchSettled : 0
         * matchStatus : 1
         * matchTime : 04-12<br />12:45p
         * matchType : FT
         * searchTime : 1491969600000
         * teamC : 客场-星期三-3场赛事
         * teamH : 主场-星期三-3场赛事
         * tmp1 :
         * tmp2 :
         * tmp3 :
         * yaozheFlScoreC : null
         * yaozheFlScoreH : null
         * yaozheHrScoreC : null
         * yaozheHrScoreH : null
         */

        private String flScoreC;
        private String flScoreCCal;
        private String flScoreH;
        private String flScoreHCal;
        private String gid;
        private String hrScoreC;
        private String hrScoreCCal;
        private String hrScoreH;
        private String hrScoreHCal;
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
        private String teamC;
        private String teamH;
        private String tmp1;
        private String tmp2;
        private String tmp3;
        private String flScoreCString;
        private String flScoreHString;
        private String hrScoreCString;
        private String hrScoreHString;
        private Object yaozheFlScoreC;
        private Object yaozheFlScoreH;
        private Object yaozheHrScoreC;
        private Object yaozheHrScoreH;

        public String getHrScoreHString() {
            return hrScoreHString;
        }

        public void setHrScoreHString(String hrScoreHString) {
            this.hrScoreHString = hrScoreHString;
        }

        public String getFlScoreCString() {
            return flScoreCString;
        }

        public void setFlScoreCString(String flScoreCString) {
            this.flScoreCString = flScoreCString;
        }

        public String getFlScoreHString() {
            return flScoreHString;
        }

        public void setFlScoreHString(String flScoreHString) {
            this.flScoreHString = flScoreHString;
        }

        public String getHrScoreCString() {
            return hrScoreCString;
        }

        public void setHrScoreCString(String hrScoreCString) {
            this.hrScoreCString = hrScoreCString;
        }

        public String getFlScoreC() {
            return flScoreC;
        }

        public void setFlScoreC(String flScoreC) {
            this.flScoreC = flScoreC;
        }

        public String getFlScoreCCal() {
            return flScoreCCal;
        }

        public void setFlScoreCCal(String flScoreCCal) {
            this.flScoreCCal = flScoreCCal;
        }

        public String getFlScoreH() {
            return flScoreH;
        }

        public void setFlScoreH(String flScoreH) {
            this.flScoreH = flScoreH;
        }

        public String getFlScoreHCal() {
            return flScoreHCal;
        }

        public void setFlScoreHCal(String flScoreHCal) {
            this.flScoreHCal = flScoreHCal;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getHrScoreC() {
            return hrScoreC;
        }

        public void setHrScoreC(String hrScoreC) {
            this.hrScoreC = hrScoreC;
        }

        public String getHrScoreCCal() {
            return hrScoreCCal;
        }

        public void setHrScoreCCal(String hrScoreCCal) {
            this.hrScoreCCal = hrScoreCCal;
        }

        public String getHrScoreH() {
            return hrScoreH;
        }

        public void setHrScoreH(String hrScoreH) {
            this.hrScoreH = hrScoreH;
        }

        public String getHrScoreHCal() {
            return hrScoreHCal;
        }

        public void setHrScoreHCal(String hrScoreHCal) {
            this.hrScoreHCal = hrScoreHCal;
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

        public Object getYaozheFlScoreC() {
            return yaozheFlScoreC;
        }

        public void setYaozheFlScoreC(Object yaozheFlScoreC) {
            this.yaozheFlScoreC = yaozheFlScoreC;
        }

        public Object getYaozheFlScoreH() {
            return yaozheFlScoreH;
        }

        public void setYaozheFlScoreH(Object yaozheFlScoreH) {
            this.yaozheFlScoreH = yaozheFlScoreH;
        }

        public Object getYaozheHrScoreC() {
            return yaozheHrScoreC;
        }

        public void setYaozheHrScoreC(Object yaozheHrScoreC) {
            this.yaozheHrScoreC = yaozheHrScoreC;
        }

        public Object getYaozheHrScoreH() {
            return yaozheHrScoreH;
        }

        public void setYaozheHrScoreH(Object yaozheHrScoreH) {
            this.yaozheHrScoreH = yaozheHrScoreH;
        }
    }
}
