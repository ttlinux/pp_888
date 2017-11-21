package com.a8android888.bocforandroid.util;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/9/14.
 */
public class ToJson{

    public String JsontoString(Object anything,String cls)
    {
//        if(anything!=null)
//        {
//            Iterator iterator= anything.iterator();
//
//            while (iterator.hasNext())
//            {
//                iterator.next();
//            }
//        }
        if(!(anything instanceof ArrayList))return "";
        JSONArray jsonarray=new JSONArray();
        try {
            ArrayList arraylist=(ArrayList)anything;
            Class clss=Class.forName(cls);
            Iterator iterator=arraylist.iterator();
            while (iterator.hasNext())
            {
                JSONObject jsobject=new JSONObject();
                Object obj=iterator.next();
                for (int i = 0; i <clss.getDeclaredFields().length; i++) {
                    Field field = clss.getDeclaredFields()[i];
                    String key=field.getName();
                    String value="";
                    field.setAccessible(true);
                    try {
                        Object ovalue=field.get(obj);
                        if(ovalue==null)
                        {
                            continue;
                        }
                        else {
                            value = String.valueOf(field.get(obj));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    try {
                        jsobject.put(key,value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonarray.put(jsobject);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        LogTools.e("getFields",jsonarray.toString());
        return jsonarray.toString();
    }


}
