package com.a8android888.bocforandroid.Bean;

/**
 * Created by Administrator on 2017/4/4.
 */
public class BankBean {
    String name;
    String img;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    boolean selected=false;

    public String getSmallimg() {
        return smallimg;
    }

    public void setSmallimg(String smallimg) {
        this.smallimg = smallimg;
    }

    String smallimg;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    String bankCode;


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
