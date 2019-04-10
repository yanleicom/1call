package com.yanlei.springboot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    public static Date getNixtTime(String day) throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date); //得到年月日
        Calendar cal=Calendar.getInstance();
        int month = cal.get(Calendar.MONTH)+2;//得到月份  +1是修改为中国系统时间 +2是下一个月的时间
        int year = cal.get(Calendar.YEAR);//得到月份
//        System.out.println(month);
//        System.out.println(year);
        String[] split = day.split("号");
        String s =  year+"-"+month+"-"+split[0]+" 18:00:00";
//        System.out.println(s);
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = format.parse(s);
//        System.out.print("Format To times:"+date);
        return date2;
    }


    public static String plusDay(int num){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        String currdate = format.format(d);
//        System.out.println("现在的日期是：" + currdate);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        d = ca.getTime();
        String enddate = format.format(d);
//        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }

    public static String getDateAndtime(String s) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String s = "星期日";
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_E");
//        System.out.println(dateFormat.toString());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String weekOfDate = WeekOfDateUtil.getWeekOfDate(date);
        Map<Object,String> map= new HashMap<>();
        map.put("星期一",1+"");
        map.put("星期二",2+"");
        map.put("星期三",3+"");
        map.put("星期四",4+"");
        map.put("星期五",5+"");
        map.put("星期六",6+"");
        map.put("星期日",7+"");
//        String s1 = dateFormat.format(System.currentTimeMillis()).split("_")[1];

        int i1 = Integer.parseInt(map.get(s));
        int i2 = Integer.parseInt(map.get(weekOfDate));
        int i = i1 - i2;
        boolean b = wholePoint();
        if (i > 0) {
            // 1 加一天
//            System.out.println(plusDay(i));
            return plusDay(i);
        } else if (i < 0){
            // -1 减1天 加7天
//            System.out.println(plusDay(7+i));
            return plusDay(7+i);
        } else {
            if (b) {
                //10点之后 +7 天
//                System.out.println(plusDay(0));
                return plusDay(7);
            } else {
                // 10点值前 +7天
//                System.out.println(plusDay(7));
                return plusDay(0);
            }
        }
    }


    public static boolean wholePoint() {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date ten = null;
            Date now = null;
            try {
                ten = format.parse("10:00");
//                System.out.println("ten"+ten);
                now = new Date();
//                System.out.println("now"+now);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return ten.before(now);
        }

}
