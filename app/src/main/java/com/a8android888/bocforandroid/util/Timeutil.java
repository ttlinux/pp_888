package com.a8android888.bocforandroid.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/29.
 */
public class Timeutil {

    public static  String getDay(String time,int day)//yyyy-MM-dd -1昨天 1 明天
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date= format.parse(time);
            Calendar   cal   =   Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, day);
            return format.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

}
