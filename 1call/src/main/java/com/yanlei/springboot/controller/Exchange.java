package com.yanlei.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.yanlei.springboot.model.User;
import com.yanlei.springboot.util.CookieUtil;
import com.yanlei.springboot.util.HttpDataUtil;
import com.yanlei.springboot.util.JsonMethod;
import com.yanlei.springboot.util.TelephoneUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: x
 * @Date: Created in 10:43 2018/11/9
 */
@Controller
public class Exchange {
    private Logger logger = LoggerFactory.getLogger(Exchange.class);

    private static final String createIdo = "https://tyhy.hzxc.gov.cn:28443/1do/do/createIdo";//测试环境

    public static final String  msgUrl = "http://218.108.106.128:8080/web/getsms"; //短信平台

    public static final String getTokenUrl = "http://59.202.68.26/openapi/acc/getToken"; //语音token获取

    public static final String planCallUrl = "http://59.202.68.26/openapi/plan/planSave"; //语音发送

    public static final String pswd = "LuXi_666";
    public static final String mishi = "TRS_Sign_LX_666";

    @GetMapping("/createOneDo")
    @ResponseBody
    public String createOneDo() throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("O_DESCRIBE","事项名称和详情");//事项名称和详情
        map.put("O_CUSTOMER","XzaqamVZPDtAR3WP");//发起人show_id
        map.put("O_CUSTOMER_NAME","刘佳民");//发起人姓名
        map.put("O_START_TIME",new Date());//开始时间
        map.put("O_EXECUTOR","297NKDKkDzHZe2da");//参与人show_id
        map.put("O_EXECUTOR_NAME","谢杰");//参与人姓名
        map.put("O_TYPE_ID","39509142708158464");//工单分类（1do分类）
        map.put("MESSAGE_ID","1508983949422");//来源消息id
        map.put("GROUPID","1100@ChatRoom");//来源群id
        map.put("SOURCE","2");//来源 1、call 或者oa 2、主动办 3、三实库 4、其他
        map.put("APARAMETER",222);//a参数（source为2即来源为主动办时表示工单id）事项id
        map.put("DPARAMETER","1");//D参数（source为2即来源为主动办时表示工单类型1事项2方案）
        JSONObject baseMap=new JSONObject();
        baseMap.put("BASE",map);
        String s = HttpDataUtil.JsonSMSPost(baseMap.toString(),createIdo);
        return s;
    }


    /**
    * @Author: x
    * @Date: Created in 16:18 2018/12/4
     * 对接嵌入1call系统 访问返回页面,cookie保存用户信息
     * @param
     */
    @RequestMapping("/oneCall")
    public String index(HttpServletRequest req , HttpServletResponse res ){
        Cookie name1 = CookieUtil.getCookieByName(req, "name");
        Cookie id = CookieUtil.getCookieByName(req, "id");
        if (name1 != null && id != null){
            return "onecall";
        }
        String name = req.getParameter("name");
        if (StringUtils.isNotBlank(name)){
            User user = JSONObject.parseObject(name, User.class);
            if (StringUtils.isNotBlank(user.getId()) && StringUtils.isNotBlank(user.getName())){
                CookieUtil.addCookie(res,"id",user.getId(),14400);
                CookieUtil.addCookie(res,"name",user.getName(),14400);
                logger.info("addCookieNmae ----> "+user.getName());
                logger.info("addCookieID ----> "+user.getId());
                return "onecall";
            }
        }
        return "error";
    }


    /**
    * @Author: x
    * @Date: Created in 14:17 2018/12/5
     * 语音对接获取token 注意token有效时间需要页面设置或者定时任务重新获取
    */


    @GetMapping("/getToken")
    @ResponseBody
    public String getToken(HttpServletRequest req){
        String tk = TelephoneUtil.getTokenParam(pswd,mishi);
        logger.info("tk-------------->>>>>"+tk);//,15145195692
        String callParam = TelephoneUtil.getCallParam(pswd, mishi, tk,"17682355442","语音测试内容xxxx","标题内容取方案名称");
        logger.info("callParam-------------------->>>>"+callParam);
        Map<String, Object> strMap = JsonMethod.readValue(callParam, Map.class);
        logger.info("strMap:::::"+strMap);
        String err = strMap.get("err").toString();
        if (err.equals("0") ) {
            return "success";
        }
        return callParam;
    }

}
