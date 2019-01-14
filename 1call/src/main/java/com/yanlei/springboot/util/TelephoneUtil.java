package com.yanlei.springboot.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.yanlei.springboot.controller.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.yanlei.springboot.controller.Exchange.getTokenUrl;
import static com.yanlei.springboot.controller.Exchange.planCallUrl;

/**
 * @Author: x
 * @Date: Created in 14:27 2018/12/7
 * 语音品台接口对接
 */
public class TelephoneUtil {

    public static String publicKeyStr = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALrWTfWxNZPQwdGqTdZ4f+SanPurvCHV" +
            "Oufk5WApw0SCZVdnW2Z9d+zLs06qvGmST6UKKYRFAbmKxGVf0aAOcUECAwEAAQ==";

    public static String getTokenParam(String pswd,String mishi){
            HashMap<String, Object> baseMap = new HashMap<>();
            JSONObject map = new JSONObject();
            map.put("app", "11"); //应用id
            long timeStampSec = System.currentTimeMillis()/1000;
            String timestamp = String.format("%010d", timeStampSec);
            map.put("tm", timestamp);//时间戳 十位数
            map.put("dvc", "");//(选填)设备唯一性标识的32位hash值(如md5)
        String param = null;
        try {
            param = RSAUtil.encrypt(RSAUtil.loadPublicKey(publicKeyStr),pswd.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sha1 = Base64Util.getSha1(param); //键位排序
            StringBuffer sbf = new StringBuffer();
            sbf.append(sha1).append(timestamp).append(mishi);
            String sn = MD5.MD5Encode(sbf.toString());
//            System.out.println("sn参数加密后:"+sn);
            map.put("sn",sn); //加密sn
            map.put("param",baseMap);
            baseMap.put("pswd", param);
            String s = HttpDataUtil.JsonSMSPost(map.toString(),getTokenUrl);
            Map<String, Object> strMap = JsonMethod.readValue(s, Map.class);
            if (strMap!=null){
                String err = strMap.get("err").toString();
                if (err.toString().equals("0")) {
                    Map<String, Object> dataMap = (Map<String, Object>) strMap.get("data");
                    String tk = dataMap.get("tk").toString();
                    return tk;
                }
            }
        return "error";
    }

    public static String getCallParam(String pswd,String mishi,String tk,String phones,String content,String title){
        try {
            JSONObject map = new JSONObject();
            HashMap<String, Object> data = new HashMap<>();
            map.put("app", "11"); //应用id
            long timeStampSec = System.currentTimeMillis()/1000;
            String timestamp = String.format("%010d", timeStampSec);
            map.put("tm", timestamp);//时间戳 十位数
            map.put("dvc", "");//(选填)设备唯一性标识的32位hash值(如md5)
            map.put("tk",tk); //token获取
            map.put("param",data);
            data.put("id","0"); //提醒ID, 0=新增，不然为修改
            data.put("title",title); //标题
            data.put("type","CALL"); //SMS|CALL,暂只支持CALL
            data.put("text",content); //信息文本,CALL类型可自动转语音
//            data.put("phones","17682355442"); //电话号码(座机或手机)以逗号间隔的列表,不能有空格
            data.put("phones",phones); //电话号码(座机或手机)以逗号间隔的列表,不能有空格
//            data.put("cycle","N"); //多次任务的类型,N=一次性,D=每天,W=每周,M=每月
//            data.put("active","N"); //是否启用计划任务,Y=启用,N=禁用
            Map<String, Object> map1 = UUID.sortMapByKey(data);//对map的key键位排序
            StringBuffer sbf = new StringBuffer();
            Gson gson = new Gson();
            sbf.append(gson.toJson(map1)).append(timestamp).append(mishi);
            System.out.println("gson字符串后:"+gson.toJson(map1));
            System.out.println("对data排序后的字符串:"+sbf);
            System.out.println("字符串:"+sbf.toString());
            String sn = MD5.EncoderByMd5(sbf.toString());
            System.out.println("MD5后的sn参数:"+sn);
            map.put("sn",sn); //加密sn
            String s = HttpDataUtil.JsonSMSPost(map.toString(),planCallUrl);
//            Map<String, Object> strMap = JsonMethod.readValue(s, Map.class);
//            System.out.println(strMap);
//            String err = strMap.get("err").toString();
//            //todo err.equals("0") || err.equals("1000")
//            if (err.equals("0") ) {
//                return "success";
//            }
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
