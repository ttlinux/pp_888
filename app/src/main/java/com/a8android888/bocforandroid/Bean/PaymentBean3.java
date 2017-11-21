package com.a8android888.bocforandroid.Bean;

/**
 * Created by Administrator on 2017/4/1. 转换记录
 */
public class PaymentBean3 {

    private String eduOrder;
    private String eduForwardRemark;
    private String createTime;
    boolean itemstate=false;
    int eduStatus,eduType;
    private String flatName;
    private double eduPoints;
    private String remark;
    private String eduStatusDes;

    public String getEduStatusDes() {
        return eduStatusDes;
    }

    public void setEduStatusDes(String eduStatusDes) {
        this.eduStatusDes = eduStatusDes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFlatName() {
        return flatName;
    }

    public void setFlatName(String flatName) {
        this.flatName = flatName;
    }

    public int getEduType() {
        return eduType;
    }

    public void setEduType(int eduType) {
        this.eduType = eduType;
    }

    public int getEduStatus() {
        return eduStatus;
    }

    public void setEduStatus(int eduStatus) {
        this.eduStatus = eduStatus;
    }

    public double getEduPoints() {
        return eduPoints;
    }

    public void setEduPoints(double eduPoints) {
        this.eduPoints = eduPoints;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEduForwardRemark() {
        return eduForwardRemark;
    }

    public void setEduForwardRemark(String eduForwardRemark) {
        this.eduForwardRemark = eduForwardRemark;
    }

    public String getEduOrder() {
        return eduOrder;
    }

    public void setEduOrder(String eduOrder) {
        this.eduOrder = eduOrder;
    }

//    public String getEduStatusStr() {
//        return eduStatusStr;
//    }
//
//    public void setEduStatusStr(String eduStatusStr) {
//        this.eduStatusStr = eduStatusStr;
//    }

//    public String getEduTypeStr() {
//        return eduTypeStr;
//    }
//
//    public void setEduTypeStr(String eduTypeStr) {
//        this.eduTypeStr = eduTypeStr;
//    }

    public boolean getItemstate() {
        return itemstate;
    }

    public void setItemstate(boolean itemstate) {
        this.itemstate = itemstate;
    }


}
