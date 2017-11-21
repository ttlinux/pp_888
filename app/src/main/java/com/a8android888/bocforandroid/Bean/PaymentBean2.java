package com.a8android888.bocforandroid.Bean;

/**
 * Created by Administrator on 2017/4/1. 提现记录
 */
public class PaymentBean2 {

    private String checkTime;
    private String createTime;
    private String remark;
    private String userOrder;
    private String withdrawStatus;
    private String withdrawTypeStr;

    private int check_status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;
    public int getCheck_status() {
        return check_status;
    }

    public void setCheck_status(int check_status) {
        this.check_status = check_status;
    }



    public double getUserWithdrawMoney() {
        return userWithdrawMoney;
    }

    public void setUserWithdrawMoney(double userWithdrawMoney) {
        this.userWithdrawMoney = userWithdrawMoney;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserOrder() {
        return userOrder;
    }

    public void setUserOrder(String userOrder) {
        this.userOrder = userOrder;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getWithdrawTypeStr() {
        return withdrawTypeStr;
    }

    public void setWithdrawTypeStr(String withdrawTypeStr) {
        this.withdrawTypeStr = withdrawTypeStr;
    }

    private double userWithdrawMoney;

    public boolean getItemstate() {
        return itemstate;
    }

    public void setItemstate(boolean itemstate) {
        this.itemstate = itemstate;
    }

    boolean itemstate=false;
}
