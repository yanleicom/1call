package com.yanlei.springboot.service.SchemeService.SchemeServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.yanlei.springboot.mapper.myData.MatterMapper;
import com.yanlei.springboot.mapper.myData.SchemeMapper;
import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.model.Integral;
import com.yanlei.springboot.model.SchemePerson;
import com.yanlei.springboot.service.SchemeService.SchemeService;
import com.yanlei.springboot.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.yanlei.springboot.controller.Exchange.*;

/**
 * @Author: x
 * @Date: Created in 17:30 2018/11/13
 */
@Service
@Transactional
@SuppressWarnings("all") //压制警告
public class SchemeServiceImpl implements SchemeService {
    private Logger logger = LoggerFactory.getLogger(SchemeService.class);

    @Autowired
    MatterMapper matterMapper;
    @Autowired
    SchemeMapper schemeMapper;

    @Override
    public String addActiveScheme(ActiveScheme activeScheme) {
        schemeMapper.addActiveScheme(activeScheme);
        Integer id = activeScheme.getId();
        if (id !=null){ //新建方案返回主键id
            //根据方案新建保存 最后查询符合人员信息添加到last_person表
            ActiveScheme activeScheme1 = schemeMapper.getSchemeById(id);
            if (StringUtils.isNotBlank(activeScheme1.getSpecialGroups())){
                List<String> result = Arrays.asList(StringUtils.split(activeScheme1.getSpecialGroups(),","));
                activeScheme1.setList(result); //设置条件
            }
            List<SchemePerson> schemePerson = schemeMapper.findSchemePerson(activeScheme1);
            if (schemePerson.size()>0 && schemePerson!=null){
                for (SchemePerson person : schemePerson) {
                    person.setsId(id); //设置方案id关联最后人信息
//                    person.setStart("1"); //设置人员办理状态(1:未通知,2:已通知(办理中),3:已办结)
                }
                int result =  schemeMapper.insertLastPerson(schemePerson);
                if (result == schemePerson.size())return "success";
            }
        }
        return "error";
    }

    @Override
    public List<ActiveMatter> findMatterName(ActiveMatter activeMatter) {
        return schemeMapper.findMatterName(activeMatter);
    }

    @Override
    public String getMatterPerson(ActiveScheme activeScheme) {
        if (activeScheme.getAmId() == null) return "请传入修改id!!!!!";
        ActiveMatter matterById = matterMapper.getMatterById(activeScheme.getAmId());
        if (matterById.getId()==null) return "无对应事项,请先新建事项";
        int count = schemeMapper.getCount(activeScheme.getAmId()); //第一次新建事项总人数
        if (count == 0 ) return "暂无对应人群";
//        int min = schemeMapper.getMin(activeScheme.getAmId()); //最小值
        if (StringUtils.isNotBlank(activeScheme.getSpecialGroups())){
            List<String> result = Arrays.asList(StringUtils.split(activeScheme.getSpecialGroups(),","));
            activeScheme.setList(result);
        }
        Integral integral = schemeMapper.getMatterPerson(activeScheme);
        Integer peopleNumber = integral.getPeopleNmber();//符合人数
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float)peopleNumber/(float)count*100);
        integral.setRatio(result+"%");
//        integral.setMinIntegral(min);
        return JSON.toJSONString(integral);
    }


    @Override
    public ActiveScheme getSchemeById(Integer id) {
        ActiveScheme activeScheme = schemeMapper.getSchemeById(id);
        if (activeScheme.getPeopleNumber() == null){
            List<SchemePerson> lastPerson = schemeMapper.getLastPerson(activeScheme.getId());
            if (lastPerson.size()>0 && lastPerson!=null){
                activeScheme.setPeopleNumber(lastPerson.size());
                schemeMapper.addPeopleNumber(activeScheme);
            }else {
                activeScheme.setPeopleNumber(0);
            }
        }
        return activeScheme;
    }

    @Override
    public List<ActiveScheme> findAll() {
        return schemeMapper.findAll();
    }

    @Override
    public List<ActiveScheme> findAllInStart(String start) {
        return schemeMapper.findAllInStart(start);
    }

    @Override
    public Map<String, Object> getSchemePerson(String start, Integer id, String pages, String rows) {
        ActiveScheme activeScheme = schemeMapper.getSchemeById(id);
        String waiterScheme = activeScheme.getWaiterScheme();//服务方式 按,分割
        String[] split = waiterScheme.split(",");
        Map<String, Object> maps = new HashMap<>();
        maps.put("start",start);
        maps.put("id",id);
        Map<String, Object> map = new HashMap<>();
        if (split.length  == 1 ){
            if (split[0].equals(Start.contentStart.TELEPHONESTART.getValue())){
                Page page = PageHelperUtil.getPage(pages, rows);
                //服务方式 电话一种
                Page list = (Page) schemeMapper.getPersonToTelephone(maps);
                map.put("total", list.getTotal());
                map.put("rows", list.getResult());
            }else if (split[0].equals(Start.contentStart.CUSTOMERSTART.getValue())){
                Page page = PageHelperUtil.getPage(pages, rows);
                //服务方式 人工
                Page list = (Page) schemeMapper.getPersonToCustomer(maps);
                map.put("total", list.getTotal());
                map.put("rows", list.getResult());
            }
        }else if (split.length == 2 && split[0].equals(Start.contentStart.APPSTART.getValue())){ //默认二种通知方式 1callAPP通知,短信通知
            Page page = PageHelperUtil.getPage(pages, rows);
            //在这种通知方式中,以短信未准,只要短信接口不异常就任务已通知到,通知到就算办结
            Page list = (Page) schemeMapper.getPersonToAppAndMsg(maps);
            map.put("total", list.getTotal());
            map.put("rows", list.getResult());
        }else if (split.length == 2 && split[0].equals(Start.contentStart.TELEPHONESTART.getValue())){
            //服务方式为 语音加人工
            Page page = PageHelperUtil.getPage(pages, rows);
            Page list = (Page) schemeMapper.getPersonToTelephoneAndCustomer(maps);
            map.put("total", list.getTotal());
            map.put("rows", list.getResult());
        }else if (split.length == 3){ //三种方式 电话通知 或 者客服通知
            if (split[2].equals(Start.contentStart.TELEPHONESTART.getValue())){
                Page page = PageHelperUtil.getPage(pages, rows);
                Page list = (Page) schemeMapper.getAppAndMsgAndTelephone(maps); //电话通知
                map.put("total", list.getTotal());
                map.put("rows", list.getResult());
            }else if (split[2].equals(Start.contentStart.CUSTOMERSTART.getValue())){
                Page page = PageHelperUtil.getPage(pages, rows);
                Page list = (Page) schemeMapper.getAppAndMsgOrAndCustomer(maps); //客服通知
                map.put("total", list.getTotal());
                map.put("rows", list.getResult());
            }
        }else if (split.length == 4){ //四种服务方式全选
            Page page = PageHelperUtil.getPage(pages, rows);
            Page list = (Page) schemeMapper.getPersonToStartAll(maps);
            map.put("total", list.getTotal());
            map.put("rows", list.getResult());
        }
        return map;
    }


    @Override
    public List<SchemePerson> getSchemePerson2(Integer ids, String pages, String rows) {
        ActiveScheme activeScheme = schemeMapper.getSchemeById(ids);
        if (activeScheme != null){
//            Page page = PageHelperUtil.getPage(pages, rows);
            List<SchemePerson> lastPerson = schemeMapper.getLastPerson(ids);
            return lastPerson;
        }
        return null;
    }

    @Override
    public List<ActiveScheme> findAllQuartz(String start) {
        List<ActiveScheme> allQuartz = schemeMapper.findAllQuartz(start);
        for (ActiveScheme activeScheme : allQuartz) {
            int count = schemeMapper.getQuartzCount(activeScheme.getId());
            count+=1;//原方案为执行次数为1
            if (activeScheme.getExecutionTime().equals("每月一次")){
                try {
                    Date nixtTime = WeekOfDateUtil.getNixtTime(activeScheme.getExecutionDate());
                    activeScheme.setNextTime(nixtTime);
                    activeScheme.setQuartzCount(count);
                } catch (ParseException e) {
                }
            }else if (activeScheme.getExecutionTime().equals("每周一次")){
                //  需要解决定时任务下一次执行时间问题 已解决 但是转换时间在十点上有点问题
                try {
                    String dateAndtime = WeekOfDateUtil.getDateAndtime(activeScheme.getExecutionDate());
                    String[] split = dateAndtime.split(" ");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date parse = format.parse(split[0]+" 18:00:00");
                    activeScheme.setNextTime(parse);
                    activeScheme.setQuartzCount(count);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return allQuartz;
    }

    /**
    * @Author: x
    * @Date: Created in 17:42 2019/3/12
     * 细化需求 修改方案状态和第三方服务方式细化
    */
    public String setSchemeStart(ActiveScheme activeScheme) {
        int result = schemeMapper.setSchemeStart(activeScheme);
        Map<String, Object> map = new HashMap<>();
        if (result <= 0) return "error";
        map.put("code",200);
        map.put("msg",result);
        return JSON.toJSONString(map);

    }

    /**
    * @Author: x
    * @Date: Created in 17:22 2019/3/12
     * 方案通知 需求修改 分离修改状态和组合通知方式
    */
    @Override
    public String combination(Integer id) {
        Map<String, Object> map = new HashMap<>();
        ActiveScheme activeScheme1 = schemeMapper.getSchemeById(id);
        if (StringUtils.isNotBlank(activeScheme1.getWaiterScheme())) {
            try {
                String[] split = activeScheme1.getWaiterScheme().split(",");
                //获取方案对应的人即电话号码
                List<SchemePerson> lastPerson = schemeMapper.getLastPerson(activeScheme1.getId());
                StringBuffer buf = new StringBuffer();
                List<String> tel = new ArrayList<>();
                if (lastPerson.size() > 0 && lastPerson != null) {
                    for (SchemePerson person : lastPerson) {
                        if (StringUtils.isNotBlank(person.getTelephone()) && person.getMsgStart().equals(Start.contentStart.CODE_NO.getValue())) {
                            buf.append(person.getTelephone()).append(",");
                            tel.add(person.getTelephone());
                        }
                    }
                    if (tel.size() < 0) return "error no telephone";
                    String telephones = buf.substring(0, buf.length() - 1);
                    logger.info("telephones:::::::" + telephones);
                    //发送短信 修改人员通知状态
                    String content = activeScheme1.getContent();
                    if (content.length() < 10) return "error--->>语音不能为空~~~电话通知内容长度需要大于10";
                    //修改人员状态
                    HashMap<String, Object> overStart = new HashMap<>();
                    overStart.put("id", activeScheme1.getId());
                    overStart.put("tel", tel); //foreach 遍历取电话
                    overStart.put("startPerson", Start.contentStart.CODE_YES.getValue());
                    //修改方案状态;
                    overStart.put("start", Start.SchemeStart.STARTFOUR.getValue());
                    overStart.put("endTime", new Date());
                    //只选择一种方式 主动办电话服务
                    if (split.length == 1 && split[0].equals(Start.contentStart.TELEPHONESTART.getValue())) {
                        String tokenParam = TelephoneUtil.getTokenParam(pswd, mishi);
                        logger.info("tk:::::::" + tokenParam);
                        String callParam = TelephoneUtil.getCallParam(pswd, mishi, tokenParam, telephones, content, activeScheme1.getSchemeName());
                        logger.info("callStart:::::::" + callParam);
                        Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
                        String err = strMap.get("err").toString();
                        logger.info("err:::::::" + err);
                        if (err.equals("0")) {
                            int nums = schemeMapper.setLastPersonTelStart(overStart);
                            if (nums == lastPerson.size()) {
                                schemeMapper.setSchemeStartOver(overStart);
                            }
                        }
                    }else if (split.length==2 && split[0].equals(Start.contentStart.APPSTART.getValue()) &&
                            split[1].equals(Start.contentStart.MSGSTART.getValue())){
                        //App加短信
                        String s = CreateOneDoUtil.SendMessage(msgUrl, telephones,content);
                        logger.info("msgStart:::::::"+s);
                        if (s.equals("success")) {
                            int nums = schemeMapper.setLastPersonMsgStart(overStart);
                            if (nums == lastPerson.size()) {
                                schemeMapper.setSchemeStartOver(overStart);
                            }
                        }
                    }else if (split.length==2 && split[0].equals(Start.contentStart.TELEPHONESTART.getValue()) ){
                        //电话加人工
                        String tokenParam = TelephoneUtil.getTokenParam(pswd, mishi);
                        logger.info("tk:::::::" + tokenParam);
                        String callParam = TelephoneUtil.getCallParam(pswd, mishi, tokenParam, telephones, content, activeScheme1.getSchemeName());
                        logger.info("callStart:::::::" + callParam);
                        Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
                        String err = strMap.get("err").toString();
                        logger.info("err:::::::" + err);
                        if (err.equals("0")) {
                            int nums = schemeMapper.setLastPersonTelStart(overStart);
                            //涉及人工不用修改方案状态
                        }
                    }else if (split.length==3 && split[2].equals(Start.contentStart.TELEPHONESTART.getValue())){
                        // 电话加短信
                        String tokenParam = TelephoneUtil.getTokenParam(pswd, mishi);
                        logger.info("tk:::::::" + tokenParam);
                        String callParam = TelephoneUtil.getCallParam(pswd, mishi, tokenParam, telephones, content, activeScheme1.getSchemeName());
                        logger.info("callStart:::::::" + callParam);
                        Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
                        String err = strMap.get("err").toString();
                        logger.info("err:::::::" + err);
                        String s = CreateOneDoUtil.SendMessage(msgUrl, telephones,content);
                        logger.info("msgStart:::::::"+s);
                        if (s.equals("success") && err.equals("0")){
                            int nums = schemeMapper.setLastPersonMsgAndTelStart(overStart);
                            if (nums == lastPerson.size()) {
                                schemeMapper.setSchemeStartOver(overStart);
                            }
                        }
                    }else if (split.length==3 && split[2].equals(Start.contentStart.CUSTOMERSTART.getValue())){
                        //短信加人工
                        String s = CreateOneDoUtil.SendMessage(msgUrl, telephones,content);
                        logger.info("msgStart:::::::"+s);
                        int num= schemeMapper.setLastPersonMsgStart(overStart);
                        //涉及人工不用修改方案状态
                    }else if (split.length == 4 ){
                        String tokenParam = TelephoneUtil.getTokenParam(pswd, mishi);
                        logger.info("tk:::::::" + tokenParam);
                        String callParam = TelephoneUtil.getCallParam(pswd, mishi, tokenParam, telephones, content, activeScheme1.getSchemeName());
                        logger.info("callStart:::::::" + callParam);
                        Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
                        String err = strMap.get("err").toString();
                        logger.info("err:::::::" + err);
                        String s = CreateOneDoUtil.SendMessage(msgUrl, telephones,content);
                        logger.info("msgStart:::::::"+s);
                        if (s.equals("success") && err.equals("0")) {
                            int nums = schemeMapper.setLastPersonMsgAndTelStart(overStart);
                            //涉及人工不用修改方案状态
                        }
                    }
                }
                map.put("code",200);
                return JSON.toJSONString(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("code",500);
        return JSON.toJSONString(map);
    }


    @Override
    public void deleteScheme(Integer id) {
        schemeMapper.deleteScheme(id);
        schemeMapper.deleteLastPerson(id);
    }

    @Override
    public int updateScheme(ActiveScheme activeScheme) {
        if (StringUtils.isNotBlank(activeScheme.getSpecialGroups()) || activeScheme.getIntegralQj() != null) {
                schemeMapper.updateScheme(activeScheme);
            Integer id = activeScheme.getId();
            ActiveScheme activeScheme1 = schemeMapper.getSchemeById(id);
            if (StringUtils.isNotBlank(activeScheme1.getSpecialGroups())) {
                List<String> result = Arrays.asList(StringUtils.split(activeScheme1.getSpecialGroups(), ","));
                activeScheme1.setList(result); //设置条件
            }
            schemeMapper.deleteLastPerson(activeScheme1.getId());
            List<SchemePerson> schemePerson = schemeMapper.findSchemePerson(activeScheme1);
            if (schemePerson.size() > 0 && schemePerson != null) {
                for (SchemePerson person : schemePerson) {
                    person.setsId(id); //设置方案id关联最后人信息
//                    person.setStart("1");//设置人员办理状态(1:未通知,2:已通知(办理中),3:已办结)
                }
                int i = schemeMapper.insertLastPerson(schemePerson);
                activeScheme1.setPeopleNumber(i); //设置修改后符合人数
                schemeMapper.addPeopleNumber(activeScheme1); //执行修改
                if (i == schemePerson.size()) {
                    return 1;
                }
                return -1;
            }
        } else {
            return schemeMapper.updateScheme(activeScheme);
        }
        return -1;
    }

    @Override
    public int createSchemeOneDo(List<Integer> ids, String name, String showId) {
        int i = 0;
        for (Integer id : ids) {
            ActiveScheme activeScheme = schemeMapper.getSchemeById(id);
            if (StringUtils.isNotBlank(activeScheme.getSpecialGroups())){
                List<String> result = Arrays.asList(StringUtils.split(activeScheme.getSpecialGroups(),","));
                activeScheme.setList(result);
            }
//            Integral integral = schemeMapper.getMatterPerson(activeScheme);
            List<SchemePerson> lastPerson = schemeMapper.getLastPerson(activeScheme.getId());
            ActiveMatter activeMatter = matterMapper.getMatterById(activeScheme.getAmId());
            //街道负责人姓名
            String streetManagerName = activeMatter.getStreetManagerName();
            //街道负责人id
            String streetManagerId = activeMatter.getStreetManagerId();
            //业务负责人姓名
            String businessManagerName = activeMatter.getBusinessManagerName();
            //业务负责人id
            String businessManagerId = activeMatter.getBusinessManagerId();
//            if (StringUtils.isBlank(streetManagerName) || StringUtils.isBlank(streetManagerId) ||
//                StringUtils.isBlank(businessManagerName) || StringUtils.isBlank(businessManagerName)) return -100;
            //发送1do的参与人 -- 街道负责人放在前面，业务负责人放在后面  需要去重复
            businessManagerName = CreateOneDoUtil.removeDuplicates(streetManagerName,businessManagerName);
            businessManagerId = CreateOneDoUtil.removeDuplicates(streetManagerId,businessManagerId);

            if (StringUtils.isNotBlank(activeScheme.getSpecialGroups())){
                String O_DESCRIBE = "现申请服务事项主动办。 事项名称："+activeScheme.getMatterName()+"。"+"可办理人数："
                        +lastPerson.size()+"人。"+"服务方案："+activeScheme.getSchemeName()+"。"+
                        "服务方式："+activeScheme.getWaiterScheme()+"。";
                try {
                    String schememOneDo = CreateOneDoUtil.createSchememOneDo(O_DESCRIBE, activeScheme.getWorkName(),
                            activeScheme.getAgreeName(),activeScheme.getId(),lastPerson.size(),streetManagerName,streetManagerId,activeScheme.getSchemeName(),businessManagerName,businessManagerId);
                    String s = HttpDataUtil.JsonSMSPost(schememOneDo,createIdo);
                    logger.info("1do审核接口result:"+s);
                    JSONObject jsonObject = JSONObject.parseObject(s);
                    int code = Integer.parseInt(jsonObject.getString("code"));
                    if (code == 200){
                        activeScheme.setSchemeStart(2);
                        schemeMapper.setSchemeStart(activeScheme);
                        ++i;
                    }
                    if (i == ids.size()){
                        return i;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                String O_DESCRIBE = "现申请服务事项主动办。 事项名称："+activeScheme.getMatterName()+"。"+"可办理人数："
                        +lastPerson.size()+"人。"+"服务方案："+activeScheme.getSchemeName()+"。"+
                        "服务方式："+activeScheme.getWaiterScheme()+"。";
                try {
                    String schememOneDo = CreateOneDoUtil.createSchememOneDo(O_DESCRIBE, activeScheme.getWorkName(),
                            activeScheme.getAgreeName(),activeScheme.getId(),lastPerson.size(),streetManagerName,streetManagerId,activeScheme.getSchemeName(),businessManagerName,businessManagerId);
                    String s = HttpDataUtil.JsonSMSPost(schememOneDo,createIdo);
                    logger.info("1do审核接口result:"+s);
                    JSONObject jsonObject = JSONObject.parseObject(s);
                    int code = Integer.parseInt(jsonObject.getString("code"));
                    if (code == 200){
                        ++i;
                        activeScheme.setSchemeStart(2);
                        schemeMapper.setSchemeStart(activeScheme);
                    }
                    if (i == ids.size()){
                        return i;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }


    /**
    * @Author: x
    * @Date: Created in 9:26 2018/11/20
     *定时任务周查询 以当前时间查询出星期匹配数据
     * 匹配到的方案根据方案状态只有在通过领导审核状态下(即方案状态为 3 或4 )
     * 还需要根据方案设置针对人群判断是否是所有人群还是新增人群,
     * 生成新方案工单(不提交1do审核,根据审核通过时间和当前时间计算第几周)现在直接添加当前时间年月日区分
     * 所有人群: 根据特殊人群和积分区间查询事项人员清单,关联方案,事项添加到last_person表
     * 新增人群: 查询方案原人员,查询新方案匹配人员,比较查询出新人员添加到last_person 并 关联方案,事项
     *
     * 新增人群指的是之前所有这个方案定时执行过的人都不包含,所以需要去之前办理过的人员信息根据身份证匹配排除
     * 根据所有符合人员信息去重,最后查询到未办理过的人群
     *
    */
    /**
    * @Author: x
    * @Date: Created in 14:08 2018/11/21
     * 周定时任务 , 匹配针对所有人群
    */

    @Override
    public int myWeekJobAll() {
        ActiveScheme activeScheme = new ActiveScheme();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date); //得到年月日
        String weekOfDate = WeekOfDateUtil.getWeekOfDate(date);
        activeScheme.setExecutionDate(weekOfDate); //匹配星期几
        activeScheme.setExecutionTime("每周一次");
        activeScheme.setExecutionStart("所有人");
        return myJobAll2(activeScheme, dateNowStr);
    }

    @Override
    public int myMonthJobAll() {
        ActiveScheme activeScheme = new ActiveScheme();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date); //得到年月日
        Calendar cal=Calendar.getInstance();
        int day =cal.get(Calendar.DATE);
        activeScheme.setExecutionDate(day+"号"); //匹配几号
        activeScheme.setExecutionTime("每月一次");
        activeScheme.setExecutionStart("所有人");
        return myJobAll2(activeScheme, dateNowStr);
    }

    @Override
    public String myWeekJobAdd() {
        ActiveScheme activeScheme = new ActiveScheme();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date); //得到年月日
        String weekOfDate = WeekOfDateUtil.getWeekOfDate(date);
        activeScheme.setExecutionDate(weekOfDate); //匹配星期几
        activeScheme.setExecutionTime("每周一次");
        activeScheme.setExecutionStart("新增人群");
        return myJobAdd2(activeScheme, dateNowStr);
    }

    @Override
    public String myMonthJobAdd() {
        ActiveScheme activeScheme = new ActiveScheme();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date); //得到年月日
        Calendar cal=Calendar.getInstance();
        int day =cal.get(Calendar.DATE);
        activeScheme.setExecutionDate(day+"号"); //匹配几号
        activeScheme.setExecutionTime("每月一次");
        activeScheme.setExecutionStart("新增人群");
        return myJobAdd2(activeScheme, dateNowStr);
    }



    @Override
    public String getMatterPerson2(ActiveScheme activeScheme) {
//        if (StringUtils.isBlank(activeScheme.getSpecialGroups())) return "特殊人群字段为空";
        if (activeScheme.getAmId() == null) return "请传入修改id!!!!!";
        ActiveMatter matterById = matterMapper.getMatterById(activeScheme.getAmId());
        if (matterById.getId()==null) return "无对应事项,请先新建事项";
        int count = schemeMapper.getCount(activeScheme.getAmId()); //第一次新建事项总人数
        NumberFormat numberFormat = NumberFormat.getInstance();
//        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        if (count == 0 ) return "暂无对应人群";
        List<String> lists = new ArrayList<>();
//        int min = schemeMapper.getMin(activeScheme.getAmId()); //最小值 暂定为0
        if (StringUtils.isNotBlank(activeScheme.getSpecialGroups())){
            List<String> result = Arrays.asList(StringUtils.split(activeScheme.getSpecialGroups(),","));
            activeScheme.setList(result);
        }
        //todo 过滤特殊人群后符合人群的身份证数据 2.0修改 已修改
        List<String> idNumber = schemeMapper.getSchemeSpecialGroupsPerson(activeScheme);
        lists.addAll(idNumber);
        if (lists.size()<=0) return "暂无对应人群";
        logger.info(JSON.toJSONString(idNumber));
        if (activeScheme.getIntegralQj()==null) activeScheme.setIntegralQj(0);

        Map integralFoMaxAndIdNumber = CreateOneDoUtil.getIntegralFoMaxAndIdNumber(lists, activeScheme.getIntegralQj());
        if (integralFoMaxAndIdNumber == null) return "积分接口异常";
        Object maxIntegral = integralFoMaxAndIdNumber.get("maxIntegral");
        Integer peopleNmber = (Integer)integralFoMaxAndIdNumber.get("peopleNmber");
//        List idNumbers =(List) integralFoMaxAndIdNumber.get("idNumber");
        String r = numberFormat.format((float)peopleNmber/(float)count*100);
        integralFoMaxAndIdNumber.put("ratio",r+"%");
        integralFoMaxAndIdNumber.put("sumPerson",count);
        integralFoMaxAndIdNumber.put("idNumber",null);
        return JSON.toJSONString(integralFoMaxAndIdNumber);
    }



    //todo 新建方案对应人员修改  2.0修改 已修改
    @Override
    public String addActiveScheme1(ActiveScheme activeScheme, String name) {
        if (activeScheme.getAmId()==null) return "请传入修改id";
        ActiveMatter activeMatter = matterMapper.getMatterById(activeScheme.getAmId());
        if (activeMatter.getId() == null) return "没有关联事项";
        activeScheme.setMatterName(activeMatter.getMatter()); //事项名称 怕事项名称在保存是修改不完全匹配
        activeScheme.setCreateTime(new Date());//创建时间
        if (StringUtils.isEmpty(activeScheme.getWorkName()))activeScheme.setWorkName(name);
        if (StringUtils.isEmpty(activeScheme.getAgreeName()))activeScheme.setAgreeName(activeMatter.getStreetManagerName());
        activeScheme.setSchemeStart(1); //方案状态(1:待报批 , 2:已报批 , 3:已批准 , 4:已办结)',
        if (activeScheme.getIntegral() == null) activeScheme.setIntegral(0); //积分扣减 不传默认0分
        schemeMapper.addActiveScheme(activeScheme);
        Integer id = activeScheme.getId();
        if (id==null) return "新建方案失败";
        List<String> lists = new ArrayList<>();
        ActiveScheme activeScheme1 = schemeMapper.getSchemeById(id);
        if (StringUtils.isNotBlank(activeScheme1.getSpecialGroups())){
            List<String> result = Arrays.asList(StringUtils.split(activeScheme1.getSpecialGroups(),","));
            activeScheme1.setList(result);
        }
        //todo 过滤特殊人群后符合人群的身份证数据 2.0修改 已修改
        List<String> idNumber = schemeMapper.getSchemeSpecialGroupsPerson(activeScheme1);
        lists.addAll(idNumber);
        if (lists.size()<=0) return "新建方案成功,暂无对应人群";
        logger.info(JSON.toJSONString(idNumber));
        if (activeScheme1.getIntegralQj()==null) activeScheme1.setIntegralQj(0);
        Map integralFoMaxAndIdNumber = CreateOneDoUtil.getIntegralFoMaxAndIdNumber(lists, activeScheme1.getIntegralQj());
        if (integralFoMaxAndIdNumber.get("totalCount").toString().equals("0")) return "新建方案成功,暂无对应人群";
        List idNumbers =(List) integralFoMaxAndIdNumber.get("idNumber");
//        if (idNumbers.size()==0 || idNumbers == null) return "新建方案成功,暂无对应人群";
        activeScheme1.setList(idNumbers);
        List<SchemePerson> lastPerson = schemeMapper.getSchemePersonByIdNumber(activeScheme1);
        logger.info(JSON.toJSONString(lastPerson));
        if (lastPerson.size()>0 && lastPerson!=null){
            for (SchemePerson person : lastPerson) {
                person.setsId(id); //设置方案id关联最后人信息
//                    person.setStart("1"); //设置人员办理状态(1:未通知,2:已通知(办理中),3:已办结)
            }
            int result =  schemeMapper.insertLastPerson(lastPerson);
            if (result == lastPerson.size())return "success";
        }
        return "error";
    }

    @Override
    public int updateScheme2(ActiveScheme activeScheme) {
        if (StringUtils.isNotBlank(activeScheme.getSpecialGroups()) || activeScheme.getIntegralQj() != null
                || (activeScheme.getMaxTime()!=null && activeScheme.getMinTime()!=null)) {
            //方案已对应事项,不允许修改事项名称 ~!
            if (StringUtils.isNotBlank(activeScheme.getMatterName())) activeScheme.setMatterName(null);
            schemeMapper.updateScheme(activeScheme);
            Integer id = activeScheme.getId();
            ActiveScheme activeScheme1 = schemeMapper.getSchemeById(id);
            List<String> lists = new ArrayList<>();
            Map<String,Integer> map = new HashMap<>();
            map.put("id",id);
            if (StringUtils.isNotBlank(activeScheme1.getSpecialGroups())) {
                List<String> result = Arrays.asList(StringUtils.split(activeScheme1.getSpecialGroups(), ","));
                activeScheme1.setList(result); //设置条件
            }
            List<String> idNumber = schemeMapper.getSchemeSpecialGroupsPerson(activeScheme1);
            lists.addAll(idNumber);
            if (lists.size()<=0) {
                schemeMapper.deleteLastPerson(activeScheme1.getId());
                map.put("peopleNumber",0);
                schemeMapper.setSchemePersonNumber(map);
                return 1;
            }else {
                logger.info("符合人员身份证号"+JSON.toJSONString(idNumber));
                schemeMapper.deleteLastPerson(activeScheme1.getId());
                if (activeScheme1.getIntegralQj()==null) activeScheme1.setIntegralQj(0);
                Map integralFoMaxAndIdNumber = CreateOneDoUtil.getIntegralFoMaxAndIdNumber(lists, activeScheme1.getIntegralQj());
                List idNumbers =(List) integralFoMaxAndIdNumber.get("idNumber");
                if (idNumbers.size()==0) return -1;
                activeScheme1.setList(idNumbers);
                List<SchemePerson> lastPerson = schemeMapper.getSchemePersonByIdNumber(activeScheme1);
                logger.info("最终符合人员信息"+JSON.toJSONString(lastPerson));
                if (lastPerson.size()>0 && lastPerson!=null){
                    for (SchemePerson person : lastPerson) {
                        person.setsId(id); //设置方案id关联最后人信息
//                    person.setStart("1"); //设置人员办理状态(1:未通知,2:已通知(办理中),3:已办结)
                    }
                    int result =  schemeMapper.insertLastPerson(lastPerson);
                    map.put("peopleNumber",lastPerson.size());
                    schemeMapper.setSchemePersonNumber(map);
                    if (result == lastPerson.size())return 1;
                }
                return -1;
            }
        } else {
            return schemeMapper.updateScheme(activeScheme);
        }
    }



    /**
    * @Author: x
    * @Date: Created in 10:58 2019/1/11
     * 定时任务调度 方案自动执行
    */
    private String myJobAdd2(ActiveScheme activeScheme, String dateNowStr) {
        List<ActiveScheme> activeSchemes = schemeMapper.findWeekJob(activeScheme); //查询符合方案
        if (activeSchemes.size() > 0) {
            for (ActiveScheme scheme : activeSchemes) {
                List<String> lists = new ArrayList<>();
                if (StringUtils.isNotBlank(scheme.getSpecialGroups())) {
                    List<String> result = Arrays.asList(StringUtils.split(scheme.getSpecialGroups(), ","));
                    scheme.setList(result);
                }
                SchemePerson lastPersonMaxTime = schemeMapper.getLastPersonMaxTime(scheme);//查询最大时间
                scheme.setEndTime(lastPersonMaxTime.getInsertTime());
                //todo 需要修改对接积分接口 已对接
                List<String> idNumber = schemeMapper.findAddSchemePerson2(scheme); //根据时间查询出新增人
                if (idNumber.size()>0 && idNumber!=null){
                    lists.addAll(idNumber);
                    logger.info(JSON.toJSONString(idNumber));
                    if (scheme.getIntegralQj()==null) scheme.setIntegralQj(0);
                    Map integralFoMaxAndIdNumber = CreateOneDoUtil.getIntegralFoMaxAndIdNumber(lists, scheme.getIntegralQj());
                    List idNumbers =(List) integralFoMaxAndIdNumber.get("idNumber");
                    if (idNumbers.size()<=0) return "新建方案成功,暂无对应人群";
                    scheme.setList(idNumbers);
                    List<SchemePerson> lastPerson = schemeMapper.getSchemePersonByIdNumber(scheme);
                    StringBuffer buf = new StringBuffer();
                    List<String> tel = new ArrayList<>();

                    for (SchemePerson person : lastPerson) {
//                        person.setsId(scheme.getId()); //设置方案id关联最后人信息
                        if (StringUtils.isNotBlank(person.getTelephone())){
                            buf.append(person.getTelephone()).append(",");
                            tel.add(person.getTelephone());
                        }
                    }

                    scheme.setEndTime(null); //办结时间设为空
                    String delimeter = "-";  // 指定分割字符
                    String[] split = scheme.getSchemeName().split(delimeter);
                    String name = split[0]; //方案名 %name%匹配获取方案id查询last_person人群信息
                    scheme.setSchemeName(name+"-"+dateNowStr); //设置定时方案名称 + 时间戳 年月日 后缀
                    scheme.setExecutionStart(null); //定时任务重复执行的任务不用定时
                    scheme.setExecutionTime(null);
                    scheme.setExecutionDate(null);
                    scheme.setEndTime(null);
                    scheme.setSchemeStart(3); //已批准状态
                    scheme.setCreateTime(new Date()); //方案创建时间修改为当前时间
                    scheme.setAsId(scheme.getId()); //设置定时方案执行关联方案主键id
                    if (tel.size()<0) continue;
                    String content = scheme.getContent();
                    if (StringUtils.isBlank(content) || content.length()<3) continue;
                    String telephones = buf.substring(0, buf.length() - 1);
                    schemeMapper.addActiveScheme(scheme); //生成新方案
                    for (SchemePerson person : lastPerson) {
                        person.setsId(scheme.getId()); //设置方案id关联最后人信息
                    }
                    schemeMapper.insertLastPerson(lastPerson);
                    logger.info(scheme.getId()+"---");
                    sendMsgAndTelephone2(lastPerson, scheme, tel, telephones);
                }
            }
            return " success ---->>> 新增定时任务执行成功";
        }
        return "error ====>>> 新增定时任务未匹配到符合方案";
    }


    private void sendMsgAndTelephone2(List<SchemePerson> lastPersons, ActiveScheme scheme, List<String> tel, String telephones) {
        String[] split = scheme.getWaiterScheme().split(",");
        String content = scheme.getContent();
        if (content.length() <10 ) return; //语音内容需要大于10
        int number = 0;
        List<String> idNumber = new ArrayList<>();
        Integer id = scheme.getId();
        for (SchemePerson person : lastPersons) {
            idNumber.add(person.getIdNumber());
        }
        HashMap<String, Object> telMap = new HashMap<>();
        telMap.put("id", id); //foreach 遍历最后存入last_person对应id
        telMap.put("tel", idNumber); //foreach 遍历取电话换成身份证
        telMap.put("start", Start.contentStart.CODE_YES.getValue());//修改人员状态
        //修改方案状态;
        HashMap<String, Object> overStart = new HashMap<>();
        overStart.put("id",scheme.getId());
        overStart.put("start",Start.SchemeStart.STARTFOUR.getValue());
        overStart.put("endTime",new Date());
        if (split.length == 1 && split[0].equals(Start.contentStart.TELEPHONESTART.getValue())) {
            // 电话
            String tokenParam = TelephoneUtil.getTokenParam(pswd, mishi);
            String callParam = TelephoneUtil.getCallParam(pswd, mishi, tokenParam,telephones,content,scheme.getSchemeName());
            Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
            String err = strMap.get("err").toString();
            if (err.equals("0")) {
                int nums = schemeMapper.setLastPersonTelStart2(telMap);
            }
            List<SchemePerson> lastPerson = schemeMapper.getLastPerson2(telMap);
            for (SchemePerson person : lastPerson) {
                if (StringUtils.isNotBlank(person.getTelephone()) &&
                        person.getTelephoneStart().equals(Start.contentStart.CODE_YES.getValue())){
                    number++;
                }
            }
            if (number == lastPerson.size()){ //所有人员通知到,修改方案状态
                schemeMapper.setSchemeStartOver(overStart);
            }
        }else if (split.length == 2 && split[0].equals(Start.contentStart.TELEPHONESTART.getValue())){
            //电话加人工
            String tokenParam = TelephoneUtil.getTokenParam(pswd, mishi);
            String callParam = TelephoneUtil.getCallParam(pswd, mishi, tokenParam,telephones,content,scheme.getSchemeName());
            Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
            String err = strMap.get("err").toString();
            if (err.equals("0")) {
                int nums = schemeMapper.setLastPersonTelStart2(telMap);
            }
        }else if (split.length == 2 && split[0].equals(Start.contentStart.APPSTART.getValue())){
            //默认选择 App加短信
            String s = CreateOneDoUtil.SendMessage(msgUrl, telephones,content);
            if (s.equals("success")) {
                int num = schemeMapper.setLastPersonMsgStart2(telMap);
            }
            List<SchemePerson> lastPerson = schemeMapper.getLastPerson2(telMap);
            for (SchemePerson person : lastPerson) {
                if (StringUtils.isNotBlank(person.getTelephone()) &&
                        person.getMsgStart().equals(Start.contentStart.CODE_YES.getValue())){
                    number++;
                }
            }
            if (number == lastPerson.size()){ //所有人员通知到,修改方案状态
                schemeMapper.setSchemeStartOver(overStart);
            }
        }else if (split.length == 3 && split[2].equals(Start.contentStart.TELEPHONESTART.getValue())){
            //短信加电话
            String tokenParam = TelephoneUtil.getTokenParam(pswd, mishi);//电话
            String callParam = TelephoneUtil.getCallParam(pswd, mishi, tokenParam,telephones,content,scheme.getSchemeName());
            Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
            String err = strMap.get("err").toString();
            String s = CreateOneDoUtil.SendMessage(msgUrl, telephones,content);//短信
            if (s.equals("success") &&  err.equals("0")){
                int nums = schemeMapper.setLastPersonMsgAndTelStart2(telMap);
            }
            List<SchemePerson> lastPerson = schemeMapper.getLastPerson2(telMap);
            for (SchemePerson person : lastPerson) {
                if (StringUtils.isNotBlank(person.getTelephone()) &&
                        person.getMsgStart().equals(Start.contentStart.CODE_YES.getValue()) &&
                        person.getTelephoneStart().equals(Start.contentStart.CODE_YES.getValue())){
                    number++;
                }
            }
            if (number == lastPerson.size()){ //所有人员通知到,修改方案状态
                schemeMapper.setSchemeStartOver(overStart);
            }
        }else if (split.length == 3 && split[2].equals(Start.contentStart.CUSTOMERSTART.getValue())){
            //短信加人工
            String s = CreateOneDoUtil.SendMessage(msgUrl, telephones,content);//短信
            if (s.equals("success")) {
                int num = schemeMapper.setLastPersonMsgStart2(telMap);
            }
        }else if (split.length == 4) {
            //四种服务方式都选 已短信和电话为准
            String tokenParam = TelephoneUtil.getTokenParam(pswd, mishi);//电话
            String callParam = TelephoneUtil.getCallParam(pswd, mishi, tokenParam,telephones,content,scheme.getSchemeName());
            Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
            String err = strMap.get("err").toString();
            String s = CreateOneDoUtil.SendMessage(msgUrl, telephones,content);//短信
            if (s.equals("success") &&  err.equals("0")){
                int nums = schemeMapper.setLastPersonMsgAndTelStart2(telMap);
            }
        }
    }


    private int myJobAll2(ActiveScheme activeScheme, String dateNowStr) {
        List<ActiveScheme> activeSchemes = schemeMapper.findWeekJob(activeScheme);
        if (activeSchemes.size() > 0) {
            List<String> lists = new ArrayList<>();
            for (ActiveScheme scheme : activeSchemes) {
                if (StringUtils.isNotBlank(scheme.getSpecialGroups())) {
                    List<String> result = Arrays.asList(StringUtils.split(scheme.getSpecialGroups(), ","));
                    scheme.setList(result);
                }
                //todo 需要修改 对接积分接口 已对接
                List<String> idNumber = schemeMapper.findSchemePerson2(scheme); //查询符合人员身份证
                if (idNumber.size() < 0) return -1;
                lists.addAll(idNumber);
                if (lists.size()<=0) return -2;
                logger.info(JSON.toJSONString(idNumber));
                if (scheme.getIntegralQj()==null) scheme.setIntegralQj(0);
                Map integralFoMaxAndIdNumber = CreateOneDoUtil.getIntegralFoMaxAndIdNumber(lists, scheme.getIntegralQj());
                List idNumbers =(List) integralFoMaxAndIdNumber.get("idNumber");
                if (idNumbers.size()<=0) return -1;
                scheme.setList(idNumbers);
                List<SchemePerson> lastPerson = schemeMapper.getSchemePersonByIdNumber(scheme);
                scheme.setSchemeName(scheme.getSchemeName().split("-")[0] + "-" + dateNowStr);//方案名称加年月日区分
                scheme.setExecutionStart(null); //定时任务重复执行的任务不用定时
                scheme.setExecutionTime(null);
                scheme.setExecutionDate(null);
                scheme.setEndTime(null);
                scheme.setSchemeStart(3); //已批准状态
                scheme.setCreateTime(new Date()); //方案创建时间修改为当前时间
                scheme.setAsId(scheme.getId()); //设置定时方案执行关联方案主键id
                schemeMapper.addActiveScheme(scheme); //生成新方案
                StringBuffer buf = new StringBuffer();
                List<String> tel = new ArrayList<>();
                for (SchemePerson person : lastPerson) {
                    person.setsId(scheme.getId()); //设置方案id关联最后人信息
                    if (StringUtils.isNotBlank(person.getTelephone())){
                        buf.append(person.getTelephone()).append(",");
                        tel.add(person.getTelephone());
                    }
                }
                if (tel.size()<0) return -1;
                String telephones = buf.substring(0, buf.length() - 1);
//                    System.out.println(telephones);
                schemeMapper.insertLastPerson(lastPerson);
                //查看方案服务方式 ,调用短信平台接口发送短信,修改人员状态, 根据人员状态修改方案状态
                logger.info(scheme.getId()+"---");
                sendMsgAndTelephone2(lastPerson, scheme, tel, telephones);
            }
            return activeSchemes.size();
        }
        return -1;
    }

}
