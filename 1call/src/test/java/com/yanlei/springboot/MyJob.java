package com.yanlei.springboot;

import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.model.SchemePerson;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: x
 * @Date: Created in 10:13 2019/1/14
 */
public class MyJob {
    //版本1.0定时任务 针对所有人群
//    private String myJobAdd(ActiveScheme activeScheme, String dateNowStr) {
//        List<ActiveScheme> activeSchemes = schemeMapper.findWeekJob(activeScheme); //查询符合方案
//        if (activeSchemes.size() > 0) {
//            for (ActiveScheme scheme : activeSchemes) {
//                if (StringUtils.isNotBlank(scheme.getSpecialGroups())) {
//                    List<String> result = Arrays.asList(StringUtils.split(scheme.getSpecialGroups(), ","));
//                    scheme.setList(result);
//                }
//                SchemePerson lastPersonMaxTime = schemeMapper.getLastPersonMaxTime(scheme);//查询最大时间
//                scheme.setEndTime(lastPersonMaxTime.getInsertTime());
//                List<SchemePerson> schemePerson = schemeMapper.findAddSchemePerson(scheme); //根据时间查询出新增人
//                if (schemePerson.size()>0 && schemePerson!=null){
//                    StringBuffer buf = new StringBuffer();
//                    List<String> tel = new ArrayList<>();
//                    for (SchemePerson person : schemePerson) {
//                        scheme.setEndTime(null); //办结时间设为空
//                        String delimeter = "-";  // 指定分割字符
//                        String[] split = scheme.getSchemeName().split(delimeter);
//                        String name = split[0]; //方案名 %name%匹配获取方案id查询last_person人群信息
//                        scheme.setSchemeName(name+"-"+dateNowStr); //设置定时方案名称 + 时间戳 年月日 后缀
//                        scheme.setExecutionStart(null); //定时任务重复执行的任务不用定时
//                        scheme.setExecutionTime(null);
//                        scheme.setExecutionDate(null);
//                        scheme.setEndTime(null);
//                        scheme.setSchemeStart(3); //已批准状态
//                        scheme.setCreateTime(new Date()); //方案创建时间修改为当前时间
//                        schemeMapper.addActiveScheme(scheme); //生成新方案
//                        person.setsId(scheme.getId()); //设置方案id关联最后人信息
////                        person.setAppStart("1");//设置人员办理状态(1:未通知,2:已通知)
////                        person.setMsgStart("1");// app,短信,电话,客服 设为1
////                        person.setTelephoneStart("1");
////                        person.setCustomerStart("1");
//                        if (StringUtils.isNotBlank(person.getTelephone())){
//                            buf.append(person.getTelephone()).append(",");
//                            tel.add(person.getTelephone());
//                        }
//                    }
//                    if (tel.size()<0) continue;
//                    String content = scheme.getContent();
//                    if (StringUtils.isBlank(content) || content.length()<3) continue;
//                    String telephones = buf.substring(0, buf.length() - 1);
//                    schemeMapper.insertLastPerson(schemePerson);
//                    sendMsgAndTelephone(activeScheme, scheme, tel, telephones);
//                }
//            }
//            return " success ---->>> 新增定时任务执行成功";
//        }
//        return "error ====>>> 新增定时任务未匹配到符合方案";
//    }
//    private int myJobAll(ActiveScheme activeScheme, String dateNowStr) {
//        List<ActiveScheme> activeSchemes = schemeMapper.findWeekJob(activeScheme);
//        if (activeSchemes.size() > 0) {
//            for (ActiveScheme scheme : activeSchemes) {
//                if (StringUtils.isNotBlank(scheme.getSpecialGroups())) {
//                    List<String> result = Arrays.asList(StringUtils.split(scheme.getSpecialGroups(), ","));
//                    scheme.setList(result);
//                }
//                List<SchemePerson> schemePerson = schemeMapper.findSchemePerson(scheme); //查询符合人员
//                if (schemePerson.size() < 0) return -1;
//                scheme.setSchemeName(scheme.getSchemeName().split("-")[0] + "-" + dateNowStr);//方案名称加年月日区分
//                scheme.setExecutionStart(null); //定时任务重复执行的任务不用定时
//                scheme.setExecutionTime(null);
//                scheme.setExecutionDate(null);
//                scheme.setEndTime(null);
//                scheme.setSchemeStart(3); //已批准状态
//                scheme.setCreateTime(new Date()); //方案创建时间修改为当前时间
//                schemeMapper.addActiveScheme(scheme); //生成新方案
//                StringBuffer buf = new StringBuffer();
//                List<String> tel = new ArrayList<>();
//                for (SchemePerson person : schemePerson) {
//                    person.setsId(scheme.getId()); //设置方案id关联最后人信息
//                    person.setAppStart("1");//设置人员办理状态(1:未通知,2:已通知)
//                    person.setMsgStart("1");// app,短信,电话,客服 设为1
//                    person.setTelephoneStart("1");
//                    person.setCustomerStart("1");
//                    if (StringUtils.isNotBlank(person.getTelephone())){
//                        buf.append(person.getTelephone()).append(",");
//                        tel.add(person.getTelephone());
//                    }
//                }
//                if (tel.size()<0) return -1;
//                String telephones = buf.substring(0, buf.length() - 1);
////                    System.out.println(telephones);
//                schemeMapper.insertLastPerson(schemePerson);
//                //查看方案服务方式 ,调用短信平台接口发送短信,修改人员状态, 根据人员状态修改方案状态
//                sendMsgAndTelephone(activeScheme, scheme, tel, telephones);
//            }
//                return activeSchemes.size();
//            }
//            return -1;
//        }

    //版本1.0定时任务调度,针对新增人群
//    @Override
//    @Transactional
//    public String myAddJob() {
//        ActiveScheme activeScheme = new ActiveScheme();
//        Date date = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dateNowStr = sdf.format(date); //得到年月日
//        String weekOfDate = WeekOfDateUtil.getWeekOfDate(date);
//        activeScheme.setExecutionDate(weekOfDate); //匹配星期几
//        activeScheme.setExecutionTime("每周一次");
//        activeScheme.setExecutionStart("新增人群");
//        List<ActiveScheme> activeSchemes = schemeMapper.findWeekJob(activeScheme); //查询符合方案
//        if (activeSchemes.size() > 0) {
//            for (ActiveScheme scheme : activeSchemes) {
//                if (StringUtils.isNotBlank(scheme.getSpecialGroups())) {
//                    List<String> result = Arrays.asList(StringUtils.split(scheme.getSpecialGroups(), ","));
//                    scheme.setList(result);
//                }
//                SchemePerson lastPersonMaxTime = schemeMapper.getLastPersonMaxTime(scheme);//查询最大时间
//                scheme.setEndTime(lastPersonMaxTime.getInsertTime());
//                List<SchemePerson> schemePerson = schemeMapper.findAddSchemePerson(scheme); //根据时间查询出新增人
//                if (schemePerson.size()>0 && schemePerson!=null){
//                    for (SchemePerson person : schemePerson) {
//                        scheme.setEndTime(null); //办结时间设为空
//                        String delimeter = "-";  // 指定分割字符
//                        String[] split = scheme.getSchemeName().split(delimeter);
//                        String name = split[0]; //方案名 %name%匹配获取方案id查询last_person人群信息
//                        scheme.setSchemeName(name+"-"+dateNowStr); //设置定时方案名称 + 时间戳 年月日 后缀
//                        scheme.setExecutionStart(null); //定时任务重复执行的任务不用定时
//                        scheme.setExecutionTime(null);
//                        scheme.setExecutionDate(null);
//                        schemeMapper.addActiveScheme(scheme); //生成新方案
//                        person.setsId(scheme.getId()); //设置方案id关联最后人信息
//                    }
//                    schemeMapper.insertLastPerson(schemePerson);
//                }
//            }
//            return " success ---->>> 定时任务针对新增人群执行成功";
//        }
//        return "error ====>>> 定时任务针对新增人未匹配到新增人员";
//    }

    public static void main(String[] args) throws ParseException {
//        Date date = new Date("Mon Jan 14 10:12:20 CST 2019");
//        System.out.println(date.getTime());

        String dt="Mon Jan 14 10:12:20 CST 2019";
        SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date date = sdf1.parse(dt);
        long time = date.getTime();
        System.out.println(time);
//        System.out.println(sdf2.format(sdf1.parse(dt)));
    }
}
