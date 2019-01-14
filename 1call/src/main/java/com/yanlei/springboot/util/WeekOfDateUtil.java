package com.yanlei.springboot.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: x
 * @Date: Created in 11:17 2018/11/19
 */
public class WeekOfDateUtil {

    public static String getWeekOfDate(Date date) {
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
