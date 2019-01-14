package com.yanlei.springboot.util;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * @Author: x
 * @Date: Created in 14:57 2018/11/9
 */
public class CreateOneDoUtil {



    public static String createOneDo(String O_DESCRIBE, Integer id,String name,String showId) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("O_DESCRIBE",O_DESCRIBE);//事项名称和详情
        map.put("O_CUSTOMER",showId);//发起人show_id 297NKDKkDzHZe2da
        map.put("O_CUSTOMER_NAME",name);//发起人姓名 刘佳民
        map.put("O_START_TIME",new Date());//开始时间
        map.put("O_EXECUTOR","XzaqamVZPDtAR3WP");//参与人show_id
        map.put("O_EXECUTOR_NAME","谢杰");//参与人姓名
        map.put("O_TYPE_ID","39509142708158464");//工单分类（1do分类）
        map.put("MESSAGE_ID","1508983949422");//来源消息id
        map.put("GROUPID","1100@ChatRoom");//来源群id
        map.put("SOURCE","2");//来源 1、call 或者oa 2、主动办 3、三实库 4、其他
        map.put("APARAMETER",id);//a参数（source为2即来源为主动办时表示工单id）事项id
        map.put("DPARAMETER","1");//D参数（source为2即来源为主动办时表示工单类型1事项2方案）
        JSONObject baseMap=new JSONObject();
        baseMap.put("BASE",map);
        return baseMap.toString();
    }

    public static String createSchememOneDo(String O_DESCRIBE,String O_CUSTOMER_NAME,String O_EXECUTOR_NAME,Integer id,Integer peopleNmber,String name,String showId) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("O_DESCRIBE",O_DESCRIBE);//事项名称和详情
        map.put("O_CUSTOMER",showId);//发起人show_id "XzaqamVZPDtAR3WP"
        map.put("O_CUSTOMER_NAME",name);//发起人姓名 O_CUSTOMER_NAME
        map.put("O_START_TIME",new Date());//开始时间
        map.put("O_EXECUTOR","297NKDKkDzHZe2da");//参与人show_id
        map.put("O_EXECUTOR_NAME",O_EXECUTOR_NAME);//参与人姓名
        map.put("O_TYPE_ID","39509142708158464");//工单分类（1do分类）
        map.put("MESSAGE_ID","1508983949422");//来源消息id
        map.put("GROUPID","1100@ChatRoom");//来源群id
        map.put("SOURCE","2");//来源 1、call 或者oa 2、主动办 3、三实库 4、其他
        map.put("APARAMETER",id);//a参数（source为2即来源为主动办时表示工单id）id
        map.put("BPARAMETER",peopleNmber);//b参数（source为2即来源为主动办时表示人数) 人数
        map.put("CPARAMETER","2");//C参数（source为2即来源为主动办时表示工单状态) 2
        map.put("DPARAMETER","2");//D参数（source为2即来源为主动办时表示工单类型1事项2方案） 2
        JSONObject baseMap=new JSONObject();
        baseMap.put("BASE",map);
        return baseMap.toString();
    }

    /**
    * @Author: x
    * @Date: Created in 11:15 2018/11/26
     * 账号: xiachen1call
     * 密码：xc123456  用户密码,密码32位大写MD5加密。
     * 签名:【下城1call】
     * 子码: 3328
     * 短信消息需要加签名 extend = 子码
     * 提交成功后，返回字符串 result=0&msgid=-285380341214707712
     * result:提交结果，0成功，其它失败
     * msgid:短信唯一标识
    */

    public static String SendMessage(String url,String telephone,String content){
        Map<String,String> map =new HashMap<>();
        map.put("username","xiachen1call");
        String password = "xc123456";
        String s = MD5.MD5Encode(password).toUpperCase();
        map.put("password",s);
        map.put("mobile",telephone);
        map.put("content","【下城1call】"+content);
        map.put("extend","3328");
        String result = null;
        try {
            result = HttpClientUtil.doPost(url, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] split = result.split("&");
        String[] split1 = split[0].split("=");
        if (split1[1].equals("0"))return "success";
        return "error";
    }

    /**
     * @Author: x
     * @Date: Created in 10:21 2019/1/10
     * 积分接口修改 对接对接积分获取最大积分
     */
    private static final String MaxScoreUrl = "http://172.16.8.10/creditPoint/score/getMaxScore";
    private static final String ValidteList = "http://172.16.8.10/creditPoint/score/getValidteList";

    public static String getMaxScore(List<String> idNumber){
        JSONObject map = new JSONObject();
//        List<String> idNumber = new ArrayList<>();
//        idNumber.add("653101196612180050");
//        idNumber.add("65310119630908201X");
//        idNumber.add("341124197912120011");
//        idNumber.add("653101195906171227");
        map.put("cardIdList",idNumber);
        String s = HttpDataUtil.JsonSMSPost(map.toString(), MaxScoreUrl);
        Map map1 = JsonMethod.readValue(s, Map.class);
        String code = map1.get("code").toString();
        if (code.equals("200")){
            Map<String, Object> contentMap = (Map<String, Object>) map1.get("content");
            Map<String, Object> dataMap = (Map<String, Object>) contentMap.get("data");
            String maxScore = dataMap.get("maxScore").toString();
            return maxScore;
        }
        return s;
    }

    /**
     * @Author: x
     * @Date: Created in 10:51 2019/1/10
     * 积分接口对接 筛选某批用户中满足条件的所有用户
     */
    public static List getValidteList(List<String> idNumber,Integer integralQj){
        JSONObject map = new JSONObject();
//        List<String> idNumber = new ArrayList<>();
//        idNumber.add("653101196612180050");
//        idNumber.add("65310119630908201X");
//        idNumber.add("341124197912120011");
//        idNumber.add("653101195906171227");
        map.put("cardIdList",idNumber);
        map.put("minScore",integralQj);
        String s = HttpDataUtil.JsonSMSPost(map.toString(), ValidteList);
        Map map1 = JsonMethod.readValue(s, Map.class);
        String code = map1.get("code").toString();
        if (code.equals("200")){
            Map<String, Object> content = (Map<String, Object>) map1.get("content");
//            Integer totalCount = (Integer) content.get("totalCount");
            Map<String, List<String>> data = (Map<String, List<String>>) content.get("data");
            List<String> list = data.get("cardIdList");
            if (list.size()<=0) return null;
            return list;
        }
        return null;
    }

    public static Map getIntegralFoMaxAndIdNumber(List<String> idNumber,Integer integralQj){
        JSONObject map = new JSONObject();
        map.put("cardIdList",idNumber);
        String s = HttpDataUtil.JsonSMSPost(map.toString(), MaxScoreUrl);
        Map map1 = JsonMethod.readValue(s, Map.class);
        String code = map1.get("code").toString();
        Map<String,Object> integral = new HashMap<>();
        if (code.equals("200")) {
            Map<String, Object> contentMap = (Map<String, Object>) map1.get("content");
            Map<String, Object> dataMap = (Map<String, Object>) contentMap.get("data");
            String maxScore = dataMap.get("maxScore").toString();
            integral.put("code1",code);
            integral.put("maxIntegral",maxScore);
            map.put("minScore",integralQj);

            String ss = HttpDataUtil.JsonSMSPost(map.toString(), ValidteList);
            Map map2 = JsonMethod.readValue(ss, Map.class);
            String code2 = map2.get("code").toString();
            if (code2.equals("200")){
                Map<String, Object> content = (Map<String, Object>) map2.get("content");
                Map<String, List<String>> data = (Map<String, List<String>>) content.get("data");
                List<String> list = data.get("cardIdList");
                integral.put("code2",code2);
                integral.put("peopleNmber",list.size());
                integral.put("idNumber",list);
                return integral;
            }
        }
        return null;
    }

}
