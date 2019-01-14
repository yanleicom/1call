package com.yanlei.springboot.model;

/**
 * @Author: x
 * @Date: Created in 12:37 2018/11/14
 */
public class Integral {

    private Integer maxIntegral; //最大积分
    private Integer minIntegral; //最小积分
    private Integer peopleNmber;//符合人数
    private String ratio;//人员占比

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
}
