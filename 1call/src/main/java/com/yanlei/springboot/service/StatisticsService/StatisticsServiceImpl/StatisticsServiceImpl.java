package com.yanlei.springboot.service.StatisticsService.StatisticsServiceImpl;

import com.alibaba.fastjson.JSON;
import com.yanlei.springboot.mapper.myData.SchemeMapper;
import com.yanlei.springboot.mapper.myData.StatisticsMapper;
import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.model.SchemePerson;
import com.yanlei.springboot.service.StatisticsService.StatisticsService;
import com.yanlei.springboot.util.Start;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;

/**
 * @Author: x
 * @Date: Created in 16:41 2019/1/8
 */
@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
    private Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);
    @Autowired
    StatisticsMapper statisticsMapper;
    @Autowired
    SchemeMapper schemeMapper;

    @Override
    public String getPersonCount() {
        Map<String,Object> map =new HashMap<>();

        //主动办方案数量 （包括未报批、已报批、已批准和已办结的方案）；(所有数量)
        Integer schemeCount = statisticsMapper.getSchemeCount();
        //主动办事项人数：指当前所有事项的所有人员数量；
        Integer matterCount = statisticsMapper.getMatterPerson();
        //主动办事项数量：指当前系统中所有事项的数量。(所有事项)
        Integer activeMatterCount = statisticsMapper.getMatterCount();

        //主动办办理人次 count
        Map<String,String> maps = new HashMap<>();
        maps.put("over",Start.SchemeStart.STARTFOUR.getValue());
        maps.put("examine",Start.SchemeStart.STARTTHREE.getValue());
//        Integer count = statisticsMapper.getSchemeOverById(Start.SchemeStart.STARTFOUR.getValue());
        Integer count = statisticsMapper.getSchemeOverById(maps);
        logger.info(count.toString());
        //审核通过方案
//        List<ActiveScheme> list = statisticsMapper.getSchemeThreeById(Start.SchemeStart.STARTTHREE.getValue());
//        logger.info(list.toString());
//        if (list.size()<0 || list==null){
//            map.put("activeMatterCount",activeMatterCount);
//            map.put("matterCount",matterCount);
//            map.put("schemeCount",schemeCount);
//            map.put("personCount",count);
//            return JSON.toJSONString(map);
//        }
//        Integer num = 0;
//        for (ActiveScheme activeScheme : list) {
//            List<SchemePerson> lastPerson = schemeMapper.getLastPerson(activeScheme.getId());
//            String[] split = activeScheme.getWaiterScheme().split(",");
//            if (split.length==2){
//                for (SchemePerson person : lastPerson) {
//                    if (person.getMsgStart()==null) break;
//                    if (person.getMsgStart().equals(Start.SchemeStart.STARTTWO.getValue())){
//                        num++;
//                    }
//                }
//            }else if (split.length==3 && split[2].equals(Start.contentStart.TELEPHONESTART.getValue())){
//                for (SchemePerson person : lastPerson) {
//                    if (person.getMsgStart()==null || person.getTelephoneStart()==null) break;
//                    if (person.getMsgStart().equals(Start.SchemeStart.STARTTWO.getValue() )&&
//                            person.getTelephoneStart().equals(Start.SchemeStart.STARTTWO.getValue())){
//                        num++;
//                    }
//                }
//            }else if (split.length==3 && split[2].equals(Start.contentStart.CUSTOMERSTART.getValue())){
//                for (SchemePerson person : lastPerson) {
//                    if (person.getMsgStart()==null || person.getCustomerStart()==null) break;
//                    if (person.getMsgStart().equals(Start.SchemeStart.STARTTWO.getValue()) &&
//                            person.getTelephoneStart().equals(Start.SchemeStart.STARTTWO.getValue())){
//                        num++;
//                    }
//                }
//            }else {
//                for (SchemePerson person : lastPerson) {
//                    if (person.getMsgStart()==null || person.getCustomerStart()==null||person.getTelephoneStart()==null) break;
//                    if (person.getMsgStart().equals(Start.SchemeStart.STARTTWO.getValue()) &&
//                            person.getTelephoneStart().equals(Start.SchemeStart.STARTTWO.getValue()) &&
//                            person.getCustomerStart().equals(Start.SchemeStart.STARTTWO.getValue())){
//                        num++;
//                    }
//                }
//            }
//        }

        map.put("activeMatterCount",activeMatterCount);
        map.put("matterCount",matterCount);
        map.put("schemeCount",schemeCount);
        map.put("personCount",count);
        return JSON.toJSONString(map);
    }

    @Override
    public String getTrends() {
        ActiveScheme activeScheme = statisticsMapper.getSchemeOver(Start.SchemeStart.STARTFOUR.getValue());
        if (activeScheme==null) return "暂无办结方案";
        String[] split = activeScheme.getWaiterScheme().split(",");
        List<String> list = new ArrayList<>();
        List<String> time = new ArrayList<>();
        Map<String,Object> map =new HashMap<>();
        SimpleDateFormat sdf= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        if (split.length<2)return "error";
            List<SchemePerson> lastPerson = schemeMapper.getLastPerson(activeScheme.getId());
            if (lastPerson.size()<=0) return "暂无对应人群";
            for (int i = 0; i < lastPerson.size(); i++) {
                String name = lastPerson.get(i).getName();
                for (int j = 0; j < split.length; j++) {
                    StringBuffer sbf = new StringBuffer();
                    StringBuffer sbf1 = new StringBuffer();
                    sbf.append("已").append(split[j]).append(name).append("办理").
                            append(activeScheme.getMatterName());
                    try {
                        sbf1.append(sdf.parse(activeScheme.getEndTime()+"").getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    logger.info("时间格式"+sbf1);
                    list.add(sbf.toString());
                    time.add(sbf1.toString());
                    if (list.size()==6){
                        map.put("name",list);
                        map.put("time",time);
                        return JSON.toJSONString(map);
                    }
            }
        }
        map.put("name",list);
        map.put("time",time);
        return JSON.toJSONString(map);
    }

    @Override
    public String getSchemePersonCount() {
        List<ActiveScheme> list = statisticsMapper.getScheme();
        if (list.size()<0) return "error";
        Map<String,Object> map =new HashMap<>();
        List<Object> year = new ArrayList<>();
        List<Object> month = new ArrayList<>();
        List<Object> personCount = new ArrayList<>();
        for (ActiveScheme activeScheme : list) {
            String matterName = activeScheme.getMatterName();
            String schemeName = activeScheme.getSchemeName();
            Integer integral = activeScheme.getIntegral();
            year.add(matterName);
            month.add(schemeName);
            personCount.add(integral);
        }
        map.put("year",year);
        map.put("month",month);
        map.put("personCount",personCount);
        return JSON.toJSONString(map);
    }

    @Override
    public String getSchemeStart() {
        List<ActiveScheme> list = statisticsMapper.getSchemeStart();
        if (list.size()<0) return "error";
        Integer num = 0;
        for (int i = 0; i < list.size(); i++) {
            Integer peopleNumber = list.get(i).getPeopleNumber();
            String schemeStart = list.get(i).getSchemeStart().toString();
            if (schemeStart.equals(Start.SchemeStart.STARTONE.getValue())){
                num=peopleNumber;
                list.remove(list.get(i));
                i--;
            }if (schemeStart.equals(Start.SchemeStart.STARTTWO.getValue())){
                list.get(i).setPeopleNumber(peopleNumber+=num);
                list.get(i).setNotice("未通知方案");
            }if (schemeStart.equals(Start.SchemeStart.STARTTHREE.getValue())){
                list.get(i).setNotice("已通知方案");
            }if (schemeStart.equals(Start.SchemeStart.STARTFOUR.getValue())){
                list.get(i).setNotice("已办结方案");
            }
        }
        return JSON.toJSONString(list);
    }

    @Override
    public String getMatterPerson() {
        List<ActiveMatter> matter = statisticsMapper.getMatter();
        Map<String,Object> map = new HashMap<>();
        List<Object> name = new ArrayList<>();
        List<Object> schemePerson = new ArrayList<>();
        List<Object> matterPserson = new ArrayList<>();
        if (matter.size()<0)return "暂无人群数据返回";
        for (ActiveMatter activeMatter : matter) {
            HashSet<SchemePerson> h = new HashSet();
            Integer id = activeMatter.getId();
            List<SchemePerson> list = statisticsMapper.getMatterPersonById(id);
            matterPserson.add(list.size());
//            logger.info(JSON.toJSONString(list));
            String matter1 = activeMatter.getMatter();
            name.add(matter1);
//            List<String> count = statisticsMapper.getSchemeByMatterName(matter1);
            List<SchemePerson> person = statisticsMapper.getMatterByName(matter1);
            h.addAll(person);
            person.removeAll(person);
            schemePerson.add(h.size());
            person.removeAll(h);

        }
        map.put("name",name);
        map.put("schemePerson",schemePerson);
        map.put("matterPserson",matterPserson);
        return JSON.toJSONString(map);
    }
}
