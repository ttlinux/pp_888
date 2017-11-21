package com.a8android888.bocforandroid.Bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/8.
 */
public class OrderJson {
    JSONObject jsonObject;
    String position;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
