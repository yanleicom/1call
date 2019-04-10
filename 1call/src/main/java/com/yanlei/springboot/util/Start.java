package com.yanlei.springboot.util;

/**
 * @Author: x
 * @Date: Created in 14:02 2018/11/27
 */
public class Start {

    /**
    * @Author: x
    * @Date: Created in 14:07 2018/11/27
     * 组合通知方式 确定通知人是否已被通知到
    */

    public enum  contentStart{
        //1call app消息推送
        APPSTART("1callAPP通知"),
        //短信通知
        MSGSTART("短信通知服务"),
        //主动电话通知服务
        TELEPHONESTART("主动电话通知服务"),
        //主动办理服务
        CUSTOMERSTART("主动办理服务"),
        //未通知
        CODE_NO("1"),
        //已通知状态
        CODE_YES("2");

        private String value;

        contentStart(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    public enum SchemeStart{
        //方案状态(1:待报批 , 2:已报批 , 3:已批准 , 4:已办结)',
        STARTONE("1"),
        STARTTWO("2"),
        STARTTHREE("3"),
        STARTFOUR("4");

        private String value;

        public String getValue() {
            return value;
        }

        SchemeStart(String value) {
            this.value = value;
        }
    }
}
