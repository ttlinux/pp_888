package com.a8android888.bocforandroid.Bean;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/8.
 */
public class BallTypeBean {


    public JSONArray getObject() {
        return jsonArray;
    }

    public void setObject(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray jsonArray;
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    String itemId,itemCode;
    String rName;
    String rType;
Boolean selected;
    String xzlxname;
    String xzlxcode;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getXzlxname() {
        return xzlxname;
    }

    public void setXzlxname(String xzlxname) {
        this.xzlxname = xzlxname;
    }

    public String getXzlxcode() {
        return xzlxcode;
    }

    public void setXzlxcode(String xzlxcode) {
        this.xzlxcode = xzlxcode;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

}
