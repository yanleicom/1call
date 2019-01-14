package com.yanlei.springboot;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: x
 * @Date: Created in 10:17 2018/11/22
 */

public class JobTest {

//    private String MyJobAdd(ActiveScheme activeScheme, String dateNowStr) {
//        List<ActiveScheme> activeSchemes = schemeMapper.findWeekJob(activeScheme);
//        if (activeSchemes.size() > 0) {
//            for (ActiveScheme scheme : activeSchemes) {
//                if (StringUtils.isNotBlank(scheme.getSpecialGroups())) {
//                    List<String> result = Arrays.asList(StringUtils.split(scheme.getSpecialGroups(), ","));
//                    scheme.setList(result);
//                }
//                List<SchemePerson> schemePerson = schemeMapper.findSchemePerson(scheme); //查询符合人员
//                String delimeter = "-";  // 指定分割字符
//                String[] split = scheme.getSchemeName().split(delimeter);
//                String name = split[0]; //方案名 %name%匹配获取方案id查询last_person人群信息
//                List<ActiveScheme> ids = schemeMapper.likeSchemeName(name); //查询到方案id
//                List<SchemePerson> person = new ArrayList<>();
//                Set<SchemePerson> set = new HashSet<>();
//                if (ids.size()>0){
//                    for (ActiveScheme id1 : ids) { //根据方案id查询所有已办理人员清单
//                        List<SchemePerson> lastPerson = schemeMapper.getLastPerson(id1.getId());
//                        person.addAll(lastPerson);
//                        set.addAll(lastPerson);
//                    }
//                }
//                //person在定时任务执行二次以上时会出现重复情况需要先去重在比较! 问题
////                person.contains()
//
//                if (schemePerson.size()>set.size()){
//                    scheme.setSchemeName(name+"-"+dateNowStr);
//                    scheme.setExecutionStart(null); //定时任务重复执行的任务不用定时
//                    scheme.setExecutionTime(null);
//                    scheme.setExecutionDate(null);
//                    schemeMapper.addActiveScheme(scheme); //生成新方案
//                    Map<String,String> map = new HashMap<>();  //根据身份证去重
//                    for (SchemePerson persons : schemePerson) {
//                        map.put(persons.getIdNumber().toString(),persons.getIdNumber());
//                    }
//                    for (SchemePerson persons : person) {
//                        if (map.containsKey(persons.getIdNumber())){
//                            map.remove(persons.getIdNumber());
//                        }
//                    } //根据最后的身份证查询符合人员信息添加到last_person 对应方案外键
//                    List<SchemePerson> intoLast = new ArrayList<>();
//                    for (SchemePerson schemePerson1 : schemePerson) {
//                        if (map.containsKey(schemePerson1.getIdNumber().toString())){
//                            intoLast.add(schemePerson1);
//                            schemePerson1.setsId(scheme.getId()); //设置方案id关联最后人信息
//                        }
//                    }
//                    int i = schemeMapper.insertLastPerson(intoLast);
//                }
//            }
//            return "月?周?定时任务针对新增人群执行成功 ---->>> 执行次数:"+activeSchemes.size();
//        }
//        return "暂无匹配到月?周?定时任务针对新增人群方案~~~";
//    }


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

}
