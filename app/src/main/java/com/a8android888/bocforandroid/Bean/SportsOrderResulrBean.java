package com.a8android888.bocforandroid.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */
public class SportsOrderResulrBean {


    /**
     * backWaterStatus : 0
     * betCanWin : 68.4
     * betIn : 10
     * betMatchIds : adc,apc,top
     * betMemberIpAddress : 127.0.0.1
     * betSportName : 今日|足球-综合过关:综合过关
     * betSportType : FT
     * betStatus : 17
     * betType : 1
     * betUserAgent : okmokm22
     * betUserName : okmokm
     * betWagersId : BPA160115231344362732
     * confirmTime : 2016-01-15 23:13:44
     * createTime : 2016-01-15 23:13:44
     * details : [{"betDx":"2.5","betDxH":"1","betIndex":"today:FT:p3:rq:H:f","betOdds":"2.04","betOddsDes":"让球 - <font color=\"red\">全场<\/font>","betOddsMinus":0,"betOddsName":"亚夫涅英","betRq":"0 / 0.5","betRqH":"0","betRqTeam":"H","betRqTeamH":"H","betStatus":0,"betTime":"2016-01-15 23:13:44","betVs":"亚夫涅英 <font class='radio'> 0 / 0.5 <\/font> 拿撒勒伊里特夏普尔","betWagersId":"BPA160115231344362732","btype":"rq","createTime":"2016-01-15 23:13:44","dtype":"rq","gid":"2162440","id":1667,"league":"以色列甲组联赛","matchId":"adc","matchTime":"2016-01-08 08:00:00","matchType":"FT","modifyTime":"2016-01-15 23:13:44","period":"f","rtype":"p3","rtypeName":"综合过关","selection":"H","status":0,"teamC":"拿撒勒伊里特夏普尔","teamH":"亚夫涅英","timeType":"today"},{"betDx":"2.5","betDxH":"1","betIndex":"today:FT:p3:dx:H:f","betOdds":"1.99","betOddsDes":"大小 - <font color=\"red\">全场<\/font>","betOddsMinus":0,"betOddsName":"大2.5","betRq":"0 / 0.5","betRqH":"0","betRqTeam":"C","betRqTeamH":"C","betStatus":0,"betTime":"2016-01-15 23:13:44","betVs":"比达耶特拉维夫拉姆拉 <font class='radio'> VS <\/font> 阿舒多","betWagersId":"BPA160115231344362732","btype":"dx","createTime":"2016-01-15 23:13:44","dtype":"dx","gid":"2162444","id":1668,"league":"以色列甲组联赛","matchId":"apc","matchTime":"2016-01-08 09:00:00","matchType":"FT","modifyTime":"2016-01-15 23:13:44","period":"f","rtype":"p3","rtypeName":"综合过关","selection":"H","status":0,"teamC":"阿舒多","teamH":"比达耶特拉维夫拉姆拉","timeType":"today"},{"betDx":"2.5","betDxH":"1","betIndex":"today:FT:p3:ds:H:f","betOdds":"1.93","betOddsDes":"单双 - <font color=\"red\">全场<\/font>","betOddsMinus":0,"betOddsName":"单","betRq":"0 / 0.5","betRqH":"0","betRqTeam":"H","betRqTeamH":"H","betStatus":0,"betTime":"2016-01-15 23:13:44","betVs":"夏普尔拉马甘吉夫塔伊姆 <font class='radio'> VS <\/font> 阿福拉哈普尔","betWagersId":"BPA160115231344362732","btype":"ds","createTime":"2016-01-15 23:13:44","dtype":"ds","gid":"2162448","id":1669,"league":"以色列甲组联赛","matchId":"top","matchTime":"2016-01-08 09:00:00","matchType":"FT","modifyTime":"2016-01-15 23:13:44","period":"f","rtype":"p3","rtypeName":"综合过关","selection":"H","status":0,"teamC":"阿福拉哈普尔","teamH":"夏普尔拉马甘吉夫塔伊姆","timeType":"today"}]
     * id : 1324
     * matchRtype : p3
     * modifyTime : 2016-01-15 23:13:44
     * orderTime : 2016-01-15 23:13:44
     * remark : 体育下注
     * status : 1
     * timeType : today
     * webFlat : a1
     * webRemark : a1
     */

    private int backWaterStatus;
    private double betCanWin;
    private int betIn;
    private String betMatchIds;
    private String betMemberIpAddress;
    private String betSportName;
    private String betSportType;
    private int betStatus;
    private int betType;
    private String betUserAgent;
    private String betUserName;
    private String betWagersId;
    private String confirmTime;
    private String createTime;
    private int id;
    private String matchRtype;
    private String modifyTime;
    private String orderTime;
    private String remark;
    private int status;
    private String timeType;
    private String webFlat;
    private String webRemark;
    private List<DetailsBean> details;
    private String betIncome;
    //////////new
    private String betMatchContent;
    private String betMatchResult;
    private String betNetWin;
    private String betUsrWin;
    private String betVoidReason;
    ////////////new

    public String getBetVoidReason() {
        return betVoidReason;
    }

    public void setBetVoidReason(String betVoidReason) {
        this.betVoidReason = betVoidReason;
    }

    public String getBetUsrWin() {
        return betUsrWin;
    }

    public void setBetUsrWin(String betUsrWin) {
        this.betUsrWin = betUsrWin;
    }

    public String getBetNetWin() {
        return betNetWin;
    }

    public void setBetNetWin(String betNetWin) {
        this.betNetWin = betNetWin;
    }

    public String getBetMatchResult() {
        return betMatchResult;
    }

    public void setBetMatchResult(String betMatchResult) {
        this.betMatchResult = betMatchResult;
    }

    public String getBetMatchContent() {
        return betMatchContent;
    }

    public void setBetMatchContent(String betMatchContent) {
        this.betMatchContent = betMatchContent;
    }





    public String getBetIncome() {
        return betIncome;
    }

    public void setBetIncome(String betIncome) {
        this.betIncome = betIncome;
    }



    public int getBackWaterStatus() {
        return backWaterStatus;
    }

    public void setBackWaterStatus(int backWaterStatus) {
        this.backWaterStatus = backWaterStatus;
    }

    public double getBetCanWin() {
        return betCanWin;
    }

    public void setBetCanWin(double betCanWin) {
        this.betCanWin = betCanWin;
    }

    public int getBetIn() {
        return betIn;
    }

    public void setBetIn(int betIn) {
        this.betIn = betIn;
    }

    public String getBetMatchIds() {
        return betMatchIds;
    }

    public void setBetMatchIds(String betMatchIds) {
        this.betMatchIds = betMatchIds;
    }

    public String getBetMemberIpAddress() {
        return betMemberIpAddress;
    }

    public void setBetMemberIpAddress(String betMemberIpAddress) {
        this.betMemberIpAddress = betMemberIpAddress;
    }

    public String getBetSportName() {
        return betSportName;
    }

    public void setBetSportName(String betSportName) {
        this.betSportName = betSportName;
    }

    public String getBetSportType() {
        return betSportType;
    }

    public void setBetSportType(String betSportType) {
        this.betSportType = betSportType;
    }

    public int getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(int betStatus) {
        this.betStatus = betStatus;
    }

    public int getBetType() {
        return betType;
    }

    public void setBetType(int betType) {
        this.betType = betType;
    }

    public String getBetUserAgent() {
        return betUserAgent;
    }

    public void setBetUserAgent(String betUserAgent) {
        this.betUserAgent = betUserAgent;
    }

    public String getBetUserName() {
        return betUserName;
    }

    public void setBetUserName(String betUserName) {
        this.betUserName = betUserName;
    }

    public String getBetWagersId() {
        return betWagersId;
    }

    public void setBetWagersId(String betWagersId) {
        this.betWagersId = betWagersId;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchRtype() {
        return matchRtype;
    }

    public void setMatchRtype(String matchRtype) {
        this.matchRtype = matchRtype;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getWebFlat() {
        return webFlat;
    }

    public void setWebFlat(String webFlat) {
        this.webFlat = webFlat;
    }

    public String getWebRemark() {
        return webRemark;
    }

    public void setWebRemark(String webRemark) {
        this.webRemark = webRemark;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * betDx : 2.5
         * betDxH : 1
         * betIndex : today:FT:p3:rq:H:f
         * betOdds : 2.04
         * betOddsDes : 让球 - <font color="red">全场</font>
         * betOddsMinus : 0
         * betOddsName : 亚夫涅英
         * betRq : 0 / 0.5
         * betRqH : 0
         * betRqTeam : H
         * betRqTeamH : H
         * betStatus : 0
         * betTime : 2016-01-15 23:13:44
         * betVs : 亚夫涅英 <font class='radio'> 0 / 0.5 </font> 拿撒勒伊里特夏普尔
         * betWagersId : BPA160115231344362732
         * btype : rq
         * createTime : 2016-01-15 23:13:44
         * dtype : rq
         * gid : 2162440
         * id : 1667
         * league : 以色列甲组联赛
         * matchId : adc
         * matchTime : 2016-01-08 08:00:00
         * matchType : FT
         * modifyTime : 2016-01-15 23:13:44
         * period : f
         * rtype : p3
         * rtypeName : 综合过关
         * selection : H
         * status : 0
         * teamC : 拿撒勒伊里特夏普尔
         * teamH : 亚夫涅英
         * timeType : today
         */

        private String betDx;
        private String betDxH;
        private String betIndex;
        private String betOdds;
        private String betOddsDes;
        private int betOddsMinus;
        private String betOddsName;
        private String betRq;
        private String betRqH;
        private String betRqTeam;
        private String betRqTeamH;
        private int betStatus;
        private String betTime;
        private String betVs;
        private String betWagersId;
        private String btype;
        private String createTime;
        private String dtype;
        private String gid;
        private int id;
        private String league;
        private String matchId;
        private String matchTime;
        private String matchType;
        private String modifyTime;
        private String period;
        private String rtype;
        private String rtypeName;
        private String selection;
        private int status;
        private String teamC;
        private String teamH;
        private String timeType;
        ////new
        private String betScoreC;
        private String betScoreCCur;
        private String betScoreH;
        private String betScoreHCur;
        private String leg;
        private ScoreBean scoreBean;
        ////new

        public String getBetScoreCCur() {
            return betScoreCCur;
        }

        public void setBetScoreCCur(String betScoreCCur) {
            this.betScoreCCur = betScoreCCur;
        }

        public String getBetScoreH() {
            return betScoreH;
        }

        public void setBetScoreH(String betScoreH) {
            this.betScoreH = betScoreH;
        }

        public String getBetScoreHCur() {
            return betScoreHCur;
        }

        public void setBetScoreHCur(String betScoreHCur) {
            this.betScoreHCur = betScoreHCur;
        }

        public String getLeg() {
            return leg;
        }

        public void setLeg(String leg) {
            this.leg = leg;
        }

        public ScoreBean getScoreBean() {
            return scoreBean;
        }

        public void setScoreBean(ScoreBean scoreBean) {
            this.scoreBean = scoreBean;
        }

        public String getBetScoreC() {
            return betScoreC;
        }

        public void setBetScoreC(String betScoreC) {
            this.betScoreC = betScoreC;
        }




        public String getBetDx() {
            return betDx;
        }

        public void setBetDx(String betDx) {
            this.betDx = betDx;
        }

        public String getBetDxH() {
            return betDxH;
        }

        public void setBetDxH(String betDxH) {
            this.betDxH = betDxH;
        }

        public String getBetIndex() {
            return betIndex;
        }

        public void setBetIndex(String betIndex) {
            this.betIndex = betIndex;
        }

        public String getBetOdds() {
            return betOdds;
        }

        public void setBetOdds(String betOdds) {
            this.betOdds = betOdds;
        }

        public String getBetOddsDes() {
            return betOddsDes;
        }

        public void setBetOddsDes(String betOddsDes) {
            this.betOddsDes = betOddsDes;
        }

        public int getBetOddsMinus() {
            return betOddsMinus;
        }

        public void setBetOddsMinus(int betOddsMinus) {
            this.betOddsMinus = betOddsMinus;
        }

        public String getBetOddsName() {
            return betOddsName;
        }

        public void setBetOddsName(String betOddsName) {
            this.betOddsName = betOddsName;
        }

        public String getBetRq() {
            return betRq;
        }

        public void setBetRq(String betRq) {
            this.betRq = betRq;
        }

        public String getBetRqH() {
            return betRqH;
        }

        public void setBetRqH(String betRqH) {
            this.betRqH = betRqH;
        }

        public String getBetRqTeam() {
            return betRqTeam;
        }

        public void setBetRqTeam(String betRqTeam) {
            this.betRqTeam = betRqTeam;
        }

        public String getBetRqTeamH() {
            return betRqTeamH;
        }

        public void setBetRqTeamH(String betRqTeamH) {
            this.betRqTeamH = betRqTeamH;
        }

        public int getBetStatus() {
            return betStatus;
        }

        public void setBetStatus(int betStatus) {
            this.betStatus = betStatus;
        }

        public String getBetTime() {
            return betTime;
        }

        public void setBetTime(String betTime) {
            this.betTime = betTime;
        }

        public String getBetVs() {
            return betVs;
        }

        public void setBetVs(String betVs) {
            this.betVs = betVs;
        }

        public String getBetWagersId() {
            return betWagersId;
        }

        public void setBetWagersId(String betWagersId) {
            this.betWagersId = betWagersId;
        }

        public String getBtype() {
            return btype;
        }

        public void setBtype(String btype) {
            this.btype = btype;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDtype() {
            return dtype;
        }

        public void setDtype(String dtype) {
            this.dtype = dtype;
        }

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

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
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

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getRtype() {
            return rtype;
        }

        public void setRtype(String rtype) {
            this.rtype = rtype;
        }

        public String getRtypeName() {
            return rtypeName;
        }

        public void setRtypeName(String rtypeName) {
            this.rtypeName = rtypeName;
        }

        public String getSelection() {
            return selection;
        }

        public void setSelection(String selection) {
            this.selection = selection;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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




        public static class ScoreBean {

            /**
             * flScoreC : 2
             * flScoreH : 1
             * hrScoreC : 2
             * hrScoreH : 1
             * sportType : FT
             * stageC1 :
             * stageC2 :
             * stageC3 :
             * stageC4 :
             * stageCA :
             * stageCF :
             * stageCS :
             * stageCX :
             * stageH1 :
             * stageH2 :
             * stageH3 :
             * stageH4 :
             * stageHA :
             * stageHF :
             * stageHS :
             * stageHX :
             */

            private String flScoreC;
            private String flScoreH;
            private String hrScoreC;
            private String hrScoreH;
            private String sportType;
            private String stageC1;
            private String stageC2;
            private String stageC3;
            private String stageC4;
            private String stageCA;
            private String stageCF;
            private String stageCS;
            private String stageCX;
            private String stageH1;
            private String stageH2;
            private String stageH3;
            private String stageH4;
            private String stageHA;
            private String stageHF;
            private String stageHS;
            private String stageHX;

            public String getFlScoreC() {
                return flScoreC;
            }

            public void setFlScoreC(String flScoreC) {
                this.flScoreC = flScoreC;
            }

            public String getFlScoreH() {
                return flScoreH;
            }

            public void setFlScoreH(String flScoreH) {
                this.flScoreH = flScoreH;
            }

            public String getHrScoreC() {
                return hrScoreC;
            }

            public void setHrScoreC(String hrScoreC) {
                this.hrScoreC = hrScoreC;
            }

            public String getHrScoreH() {
                return hrScoreH;
            }

            public void setHrScoreH(String hrScoreH) {
                this.hrScoreH = hrScoreH;
            }

            public String getSportType() {
                return sportType;
            }

            public void setSportType(String sportType) {
                this.sportType = sportType;
            }

            public String getStageC1() {
                return stageC1;
            }

            public void setStageC1(String stageC1) {
                this.stageC1 = stageC1;
            }

            public String getStageC2() {
                return stageC2;
            }

            public void setStageC2(String stageC2) {
                this.stageC2 = stageC2;
            }

            public String getStageC3() {
                return stageC3;
            }

            public void setStageC3(String stageC3) {
                this.stageC3 = stageC3;
            }

            public String getStageC4() {
                return stageC4;
            }

            public void setStageC4(String stageC4) {
                this.stageC4 = stageC4;
            }

            public String getStageCA() {
                return stageCA;
            }

            public void setStageCA(String stageCA) {
                this.stageCA = stageCA;
            }

            public String getStageCF() {
                return stageCF;
            }

            public void setStageCF(String stageCF) {
                this.stageCF = stageCF;
            }

            public String getStageCS() {
                return stageCS;
            }

            public void setStageCS(String stageCS) {
                this.stageCS = stageCS;
            }

            public String getStageCX() {
                return stageCX;
            }

            public void setStageCX(String stageCX) {
                this.stageCX = stageCX;
            }

            public String getStageH1() {
                return stageH1;
            }

            public void setStageH1(String stageH1) {
                this.stageH1 = stageH1;
            }

            public String getStageH2() {
                return stageH2;
            }

            public void setStageH2(String stageH2) {
                this.stageH2 = stageH2;
            }

            public String getStageH3() {
                return stageH3;
            }

            public void setStageH3(String stageH3) {
                this.stageH3 = stageH3;
            }

            public String getStageH4() {
                return stageH4;
            }

            public void setStageH4(String stageH4) {
                this.stageH4 = stageH4;
            }

            public String getStageHA() {
                return stageHA;
            }

            public void setStageHA(String stageHA) {
                this.stageHA = stageHA;
            }

            public String getStageHF() {
                return stageHF;
            }

            public void setStageHF(String stageHF) {
                this.stageHF = stageHF;
            }

            public String getStageHS() {
                return stageHS;
            }

            public void setStageHS(String stageHS) {
                this.stageHS = stageHS;
            }

            public String getStageHX() {
                return stageHX;
            }

            public void setStageHX(String stageHX) {
                this.stageHX = stageHX;
            }
        }
    }
}
