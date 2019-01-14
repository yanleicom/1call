package com.yanlei.springboot.service.CustomerService.CustomerServiceImpl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.yanlei.springboot.mapper.myData.CustomerMapper;
import com.yanlei.springboot.mapper.myData.SchemeMapper;
import com.yanlei.springboot.model.ActiveScheme;
import com.yanlei.springboot.model.SchemePerson;
import com.yanlei.springboot.service.CustomerService.CustomerService;
import com.yanlei.springboot.util.Start;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: x
 * @Date: Created in 17:09 2018/11/22
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    SchemeMapper schemeMapper;

    @Override
    public Map<String, Object> getSchemeShowCustomer(String start) {
        //主动办客服只有在选择服务方式为 : 主动办理服务 时才显示这类方案
        String waiterScheme = Start.contentStart.CUSTOMERSTART.getValue();
        Map<String,Object> maps = new HashMap<>();
        maps.put("start",start);
        maps.put("waiterScheme",waiterScheme);
        maps.put("dayu",Integer.parseInt(Start.contentStart.CODE_YES.getValue()));
        Page list =(Page) customerMapper.findAllInStart(maps);
        Map<String, Object> map = new HashMap<>();
        map.put("total", list.getTotal());
        map.put("rows", list.getResult());
        return map;
    }

    @Override
    public String updatePersonStart(Integer id) {
        int result = customerMapper.updatePersonStart(id);
        // 需要修改状态来查看方案,根据方案服务方式来查看人员通知状态,根据人员状态来修改方案为已办结
        if (result>0){
            ActiveScheme activeScheme = customerMapper.getActiveSchemeByPersonId(id);
            if (StringUtils.isNotBlank(activeScheme.getWaiterScheme())){
                String[] split = activeScheme.getWaiterScheme().split(",");
                List<SchemePerson> lastPerson = schemeMapper.getLastPerson(activeScheme.getId());
                if (lastPerson.size()<0 || lastPerson == null) return "error";
                int num = 0;
                if (split.length==3 && split[2].equals(Start.contentStart.CUSTOMERSTART.getValue())){
                    for (SchemePerson person : lastPerson) {
                        if (person.getMsgStart().equals(Start.contentStart.CODE_YES.getValue()) &&
                                person.getCustomerStart().equals(Start.contentStart.CODE_YES.getValue())){
                            num++;
                        }
                    }
                }else if (split.length == 4){
                    for (SchemePerson person : lastPerson) {
                        if (person.getMsgStart().equals(Start.contentStart.CODE_YES.getValue()) &&
                                person.getTelephoneStart().equals(Start.contentStart.CODE_YES.getValue()) &&
                                person.getCustomerStart().equals(Start.contentStart.CODE_YES.getValue())){
                            num++;
                        }
                    }
                }
                if (num == lastPerson.size()) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", activeScheme.getId());
                    map.put("start", Start.SchemeStart.STARTFOUR.getValue());
                    map.put("endTime",new Date());
                    schemeMapper.setSchemeStartOver(map);
                }
            }
            return "success";
        }
        return "error";
    }

    @Override
    public String getSchemeLog(Integer id) {
        //方案日志, 带街道
        ActiveScheme activeScheme = customerMapper.getSchemeLog(id);
        //最后办理人员信息 查询通知人员
        //人员办理状态(1:办理中,2:已办结)
        List<SchemePerson> lastPerson = schemeMapper.getLastPerson(id);
        Map<String,Object> map =new HashMap<>();
        int i = 0;
        String value = Start.contentStart.CODE_YES.getValue();
        //方案服务方式 按,分割
        String[] split = activeScheme.getWaiterScheme().split(",");
        if (split.length == 3 && lastPerson.size()>0 ){
            for (SchemePerson person : lastPerson) {
                if (person.getMsgStart().equals(value) && person.getCustomerStart().equals(value)){
                    i++;
                }
            }
            map.put("方案人数",lastPerson.size());
            map.put("办理人数",lastPerson.size());
            map.put("通知人数",i);
        }else if (split.length == 4 && lastPerson.size()>0 ){
            for (SchemePerson person : lastPerson) {
                if (person.getMsgStart().equals(value) && person.getTelephoneStart().equals(value) && person.getCustomerStart().equals(value)){
                    i++;
                }
            }
            map.put("方案人数",lastPerson.size());
            map.put("办理人数",lastPerson.size());
            map.put("通知人数",i);
        }else {
            map.put("方案人数",i);
            map.put("办理人数",i);
            map.put("通知人数",i);
        }
        map.put("schemeLog",activeScheme);
        return JSON.toJSONString(map);
    }

    @Override
    public String getMsg(String telephone) {
        Map<String,Object> map =new HashMap<>();
        if (StringUtils.isBlank(telephone)) {
            map.put("code",500);
            map.put("msg","请传入电话号码");
            return JSON.toJSONString(map);
        }
        List<String> ids = customerMapper.getActiveSchemeByIds(telephone);
        if (ids.size()!= 0 && ids!= null){
           List<SchemePerson> msg = customerMapper.getMsg(ids);
           List<String> list = new ArrayList<>();
           if (msg.size()!=0 && msg != null ){
               for (SchemePerson schemePerson : msg) {
                   schemePerson.setTelephone(telephone);
                   schemePerson.setAppStart(Start.contentStart.CODE_YES.getValue());
                   customerMapper.setMsgStart(schemePerson);
                   list.add(schemePerson.getName());
               }
               map.put("code",200);
               map.put("msg",list);
               return JSON.toJSONString(map);
           }
        }
        map.put("code",500);
        map.put("msg",null);
        return JSON.toJSONString(map);
    }
}
