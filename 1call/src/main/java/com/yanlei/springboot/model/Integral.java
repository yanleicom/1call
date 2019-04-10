package com.yanlei.springboot.model;

import java.util.Date;

/**
 * @Author: x
 * @Date: Created in 12:37 2018/11/14
 */
public class Integral {

    private Integer maxIntegral; //最大积分
    private Integer minIntegral; //最小积分
    private Integer peopleNmber;//符合人数
    private String ratio;//人员占比
    private Integer id;
    private String notice; //通知
    private Date time; //时间
    private String idNumber; //身份证 未加

    public Integer getMaxIntegral() {
        return maxIntegral;
    }

    public void setMaxIntegral(Integer maxIntegral) {
        this.maxIntegral = maxIntegral;
    }

    public Integer getMinIntegral() {
        return minIntegral;
    }

    public void setMinIntegral(Integer minIntegral) {
        this.minIntegral = minIntegral;
    }

    public Integer getPeopleNmber() {
        return peopleNmber;
    }

    public void setPeopleNmber(Integer peopleNmber) {
        this.peopleNmber = peopleNmber;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Integral(String notice, Date time) {
        this.notice = notice;
        this.time = time;
    }

    public Integral() {
    }
}
