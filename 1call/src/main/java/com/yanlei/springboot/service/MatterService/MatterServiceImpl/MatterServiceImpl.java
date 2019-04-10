package com.yanlei.springboot.service.MatterService.MatterServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.yanlei.springboot.model.SchemePerson;
import com.yanlei.springboot.mapper.oneCall.BusinessTypeMapper;
import com.yanlei.springboot.model.ActiveMatter;
import com.yanlei.springboot.mapper.myData.MatterMapper;
import com.yanlei.springboot.model.BusinessType;
import com.yanlei.springboot.service.MatterService.MatterService;
import com.yanlei.springboot.util.CreateOneDoUtil;
import com.yanlei.springboot.util.HttpDataUtil;
import com.yanlei.springboot.util.Start;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yanlei.springboot.controller.Exchange.createIdo;


/**
 * @Author: x
 * @Date: Created in 15:50 2018/10/29
 */
@Service
@Transactional
public class MatterServiceImpl implements MatterService {

    @Autowired
    MatterMapper matterMapper;
    @Autowired
    BusinessTypeMapper businessTypeMapper;

    @Override
    public String insertMatter(ActiveMatter activeMatter, String name, String showId) {
        /**
        * @Author: x
        * @Date: Created in 14:21 2019/1/10
         * 修改接口 细化需求 分离发送1do工单 版本2.0 有问题修改
        */
        String matter = activeMatter.getMatter();
        if (StringUtils.isEmpty(matter)) return "参数matter为空,请指定事项名称!!!";
        String streetManagerId = activeMatter.getStreetManagerId();
        if (StringUtils.isEmpty(streetManagerId)) return "请添加“街道负责人”!!!";
        String streetManagerName = activeMatter.getStreetManagerName();
        if (StringUtils.isEmpty(streetManagerName)) return "请添加“街道负责人”!!!";
        if (StringUtils.isEmpty(activeMatter.getStreet())) activeMatter.setStreet("武林");
        matterMapper.insertMatter(activeMatter);
        if (activeMatter.getId()!=null){
                return "success" ;
        }
        return "error";
    }

    @Override
    public int deleteMatter(int id) {
      return matterMapper.deleteMatter(id);
    }

    @Override
    public List<ActiveMatter> findAll() {
       return matterMapper.findAll();
    }

    @Override
    public ActiveMatter getMatterById(int id) {
        return matterMapper.getMatterById(id);

    }

    @Override
    public List<BusinessType> findNameFromBusinessType(ActiveMatter activeMatter) {
        return businessTypeMapper.findNameFromBusinessType(activeMatter);
    }

    @Override
    public void updateMatter(ActiveMatter activeMatter) {
        matterMapper.updateMatter(activeMatter);
    }

    @Override
    public List<SchemePerson> showMatterPerson(Integer id) {
        return matterMapper.showMatterPerson(id);
    }

    @Override
    public String createMatterOneDo(Integer idd, String name, String showId) {
        //根据id获取事项
        ActiveMatter activeMatter = matterMapper.getMatterById(idd);
//        //街道负责人姓名
//        String streetManagerName = activeMatter.getStreetManagerName();
//        //街道负责人id
//        String streetManagerId = activeMatter.getStreetManagerId();
//        //业务负责人姓名
//        String businessManagerName = activeMatter.getBusinessManagerName();
//        //业务负责人id
//        String businessManagerId = activeMatter.getBusinessManagerId();
        if (activeMatter.getMatterStart().equals(Start.contentStart.CODE_YES.getValue())) return "事项已发送1do,不可再次发送";
        Integer id = activeMatter.getId();//id
        String matter1 = activeMatter.getMatter();//事项名称
        String details = activeMatter.getDetails();//表达式
        StringBuffer sbf = new StringBuffer();
        if (details.contains("$$") ){//前端定义换行和子元素
            String s1 = details.replace("$$", " ");
            System.out.println(s1);
            if (s1.contains("**")){
                String s2 = s1.replace("**", " ");
                sbf.append(s2);
            }
        }
        String street = activeMatter.getStreet();//街道
        String O_DESCRIBE = "id:"+id+","+"事项名称:"+matter1+","
                +"申请条件:"+sbf+","+ "街道:"+street;

        String oneDo = null;
        //发送1do的参与人 -- 街道负责人放在前面，业务负责人放在后面  需要去重复
//        businessManagerName = CreateOneDoUtil.removeDuplicates(streetManagerName,businessManagerName);
//        businessManagerId = CreateOneDoUtil.removeDuplicates(streetManagerId,businessManagerId);
        try {
            oneDo = CreateOneDoUtil.createOneDo(O_DESCRIBE,id,name,showId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = HttpDataUtil.JsonSMSPost(oneDo,createIdo);
        JSONObject jsonObject = JSONObject.parseObject(s);
        int code = Integer.parseInt(jsonObject.getString("code"));
        if (code == 200){
            Map<String,Object> map =new HashMap<>();
            map.put("id",idd);
            map.put("start",Start.contentStart.CODE_YES.getValue());
            matterMapper.updateMatterStart(map);
            return "success" ;
        }
        return "error";
    }

}
