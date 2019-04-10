package com.yanlei.springboot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yanlei.springboot.util.MD5;
import com.yanlei.springboot.util.Start;
import com.yanlei.springboot.util.WeekOfDateUtil;
import org.junit.Test;
import org.omg.CORBA.MARSHAL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


/**
 * @Author: x
 * @Date: Created in 16:25 2018/11/23
 */

public class TestMD {

    @Test
    public void test(){
        String s = MD5.MD5Encode("xc123456").toUpperCase();
        System.out.println(s);
        String value = Start.SchemeStart.STARTFOUR.getValue();
        System.out.println(value);

        String details = "$$60岁以上至16岁以下**16岁以下小于16岁以下**$$16岁以下大于16岁以下**1是16岁以下**$$11小于2**33大于2**22非2**";
        StringBuffer sbf = new StringBuffer();
        if (details.contains("$$") ){
            String s1 = details.replace("$$", " ");
            System.out.println(s1);
            if (s1.contains("**")){
                String s2 = s1.replace("**", " ");
                sbf.append(s2);
            }
        }

        System.out.println(sbf);
    }

    public static void main(String[] args) {
        JSONObject baseMap=new JSONObject();
//        baseMap.put("ATTR",)
//        HashMap<String,Object> ATTR = new HashMap<>();
        List<Map<String,Object>> list= new ArrayList<>();
        //file 附件添加
        HashMap<String,Object> fileMap = new HashMap<>();
        fileMap.put("ATTR_NAME","测试方案名称");//方案名称
        fileMap.put("ATTR_PATH","/getSchemeById?id=id");//跳转路径
        fileMap.put("UPLOAD_USER","上传人员show_id");//上传人员show_id
        Date now=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileMap.put("UPLOAD_TIME",sdf.format(now));//上传时间  String类型
        fileMap.put("UPLOAD_USER_NAME","上传人员姓名");//上传人员姓名
        list.add(fileMap);
        baseMap.put("ATTR",list);
        System.out.println(JSON.toJSONString(baseMap));
    }

    @Test
    public void test1() throws ParseException {
        Date currentTime = new Date();
        System.out.println(currentTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        System.out.println(dateString);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date); //得到年月日
        Calendar cal=Calendar.getInstance();
        int month = cal.get(Calendar.MONTH)+1;//得到月份
        int year = cal.get(Calendar.YEAR);//得到月份
        System.out.println(month);
        System.out.println(year);
        String s =  year+"-"+month+"-"+"19"+" 10:00:00";
        System.out.println(s);
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = format.parse(s);
        System.out.print("Format To times:"+date);
    }

//    @Test
//    public void test2() throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd E ");
//        for (Date date : getWeekDay()) {
//            System.out.println(dateFormat.format(date));
////            System.out.println(date);
//            String format = dateFormat.format(date);
////            System.out.println(format);
//            String[] split = dateFormat.format(date).split(" ");
//            System.out.println(split[0]);
//            System.out.println(split[1]);
//
//        }
//    }

    @Test
    public void beforeTime() throws ParseException{
//        DateFormat d = new SimpleDateFormat("yyyy-MM-dd");
//// System.out.println(d.format(new Date()));
//        Calendar c = Calendar.getInstance();
//        c.setTime(d.parse("2019-02-20"));
//        c.set(Calendar.DATE, c.get(Calendar.DATE)+7);
//        String t = d.format(c.getTime());
//        System.out.println(t);
//        Date date = new Date();
//        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd_E", Locale.CHINA);
//        System.out.println(dateFm.format(date));
//        getWeekDate();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR,
                calendar.get(Calendar.WEEK_OF_YEAR) + 1);
        df = new SimpleDateFormat("yyyy-MM-dd 10:00:00");
        System.out.println("一个星期以后的时间：" + df.format(calendar.getTime()));

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,
                calendar.get(Calendar.DAY_OF_YEAR) + 1);
        df = new SimpleDateFormat("yyyy-MM-dd 10:00:00");
        System.out.println("一天以后的时间：" + df.format(calendar.getTime()));
    }

    @Test
    public void test4(){
        try {
            String ss = WeekOfDateUtil.getDateAndtime("星期二");
            System.out.println(ss);
            String[] split = ss.split(" ");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = format.parse(split[0]+" 10:00:00");
            System.out.println(parse);
//            boolean b = WeekOfDateUtil.wholePoint();
//            System.out.println(b);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
