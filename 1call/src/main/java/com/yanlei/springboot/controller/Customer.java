package com.yanlei.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.yanlei.springboot.service.CustomerService.CustomerService;
import com.yanlei.springboot.util.CreateOneDoUtil;
import com.yanlei.springboot.util.PageHelperUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.yanlei.springboot.controller.Exchange.msgUrl;

/**
 * @Author: x
 * @Date: Created in 16:54 2018/11/22
 */

@RestController
public class Customer {

    @Autowired
    CustomerService customerService;

    /**
    * @Author: x
    * @Date: Created in 17:00 2018/11/22
     * 主动办客服展示所有方案,办结方案,办理中方案
     * 根据方案状态 start 3 : 办理中方案 4 : 办结方案
     * 方案状态(1:待报批 , 2:已报批 , 3:已批准 , 4:已办结)',
     * 主动办客服只展示已审核通过的 状态为 3 或者 4
     *
    */

    @GetMapping("/getSchemeShowCustomer")
    public Map<String,Object> getSchemeShowCustomer(String pages, String rows, String start,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = PageHelperUtil.getPage(pages, rows);
        Map<String, Object> map = customerService.getSchemeShowCustomer(start);
        return map;
    }

    /**
    * @Author: x
    * @Date: Created in 14:03 2018/11/23
     * 主动办客服修改方案符合人员办理状态 修改为 2
     * 人员办理状态(1:办理中,2:已办结)
     * 回显方案状态为3  出现办理 !! 点击办结修改人员状态
    */
    @PostMapping("/updatePersonStart")
    public String updatePersonStart(Integer id,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        return customerService.updatePersonStart(id);
    }

    /**
    * @Author: x
    * @Date: Created in 14:05 2018/11/23
     * 方案日志查看 方案人数,通知人数,办理人数计算得出
     * 问题修改 4种状态对应 根据方案通知方式 查看人员通知状态
    */
    @GetMapping("/getSchemeLog")
    public String getSchemeLog(Integer id,HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        return customerService.getSchemeLog(id);
    }

    /**
    * @Author: x TODO 短信发送成功修改短信通知状态
    * @Date: Created in 11:35 2018/11/26
     * 短信发送 对接下城区短信平台
     * 提交成功后，返回字符串 result=0&msgid=-285380341214707712
     * 下发信息的目标手机号码,支持多个号码，号码用英文逗号分隔。 telephone
    */

    @GetMapping("/SendMessage")
    public String SendMessage() throws Exception {//,15268531097
        String s = CreateOneDoUtil.SendMessage(msgUrl, "17682355442,15145195692","短信通知测试内容xxxx");
        return s;
    }


    /**
    * @Author: x
    * @Date: Created in 16:52 2018/11/26
     * 1callAPP消息推送 , 接受手机号码 ,匹配要发送的信息
     * App发送成功修改APP状态
     * return : list 该手机需要发送的消息
    */
    @PostMapping("/getMsg")
    public String getMsg(String telephone){
           String msg = customerService.getMsg(telephone);
           return msg;
    }
}
