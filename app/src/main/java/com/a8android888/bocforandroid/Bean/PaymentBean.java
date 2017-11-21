package com.a8android888.bocforandroid.Bean;

/**
 * Created by Administrator on 2017/3/30. 充值记录
 */
public class PaymentBean {

    String hkTime;
    String hkType;
    String remark;
    String hkOrder;
    String statusDes;

    public String getHkCheckTime() {
        return hkCheckTime;
    }

    public void setHkCheckTime(String hkCheckTime) {
        this.hkCheckTime = hkCheckTime;
    }

    String hkCheckTime;

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public int getHkCheckStatus() {
        return hkCheckStatus;
    }

    public void setHkCheckStatus(int hkCheckStatus) {
        this.hkCheckStatus = hkCheckStatus;
    }

    int hkCheckStatus;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    String createTime;
    int hkStatus;

    public boolean getItemstate() {
        return itemstate;
    }

    public void setItemstate(boolean itemstate) {
        this.itemstate = itemstate;
    }

    boolean itemstate=false;

    public int getHkModel() {
        return hkModel;
    }

    public void setHkModel(int hkModel) {
        this.hkModel = hkModel;
    }

    public double getHkMoney() {
        return hkMoney;
    }

    public void setHkMoney(double hkMoney) {
        this.hkMoney = hkMoney;
    }

    public String getHkOrder() {
        return hkOrder;
    }

    public void setHkOrder(String hkOrder) {
        this.hkOrder = hkOrder;
    }

    public int getHkStatus() {
        return hkStatus;
    }

    public void setHkStatus(int hkStatus) {
        this.hkStatus = hkStatus;
    }

    public String getHkTime() {
        return hkTime;
    }

    public void setHkTime(String hkTime) {
        this.hkTime = hkTime;
    }

    public String getHkType() {
        return hkType;
    }

    public void setHkType(String hkType) {
        this.hkType = hkType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    int hkModel;
    double hkMoney;
}
