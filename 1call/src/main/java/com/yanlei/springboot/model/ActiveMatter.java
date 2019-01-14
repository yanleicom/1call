package com.yanlei.springboot.model;

/**
 * @Author: x
 * @Date: Created in 14:57 2018/10/29
 *
 *
 */
public class ActiveMatter {

    private Integer id;
    private String matter; //事项名称
    private String matterDetails; //事项详情(主要用于表达情况和条件)
    private String details; //事项条件的一定表达式
    private String dissatisfaction; //非量化指标
    private String threshold; //事项阀值
    private String street; //街道名 暂时默认选武林街道
    private Integer num;
    private String matterStart; //事项发生1do工单状态 1未发送 2已发送

    public String getMatterStart() {
        return matterStart;
    }

    public void setMatterStart(String matterStart) {
        this.matterStart = matterStart;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }

    public String getMatterDetails() {
        return matterDetails;
    }

    public void setMatterDetails(String matterDetails) {
        this.matterDetails = matterDetails;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDissatisfaction() {
        return dissatisfaction;
    }

    public void setDissatisfaction(String dissatisfaction) {
        this.dissatisfaction = dissatisfaction;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public ActiveMatter() {
    }

    public ActiveMatter(Integer id, String matter, String matterDetails, String details, String dissatisfaction, String threshold, String street) {
        this.id = id;
        this.matter = matter;
        this.matterDetails = matterDetails;
        this.details = details;
        this.dissatisfaction = dissatisfaction;
        this.threshold = threshold;
        this.street = street;
    }
}
