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
    private String streetManagerId; //街道负责人id
    private String streetManagerName; //街道负责人姓名
    private String businessManagerId; //业务负责人id
    private String businessManagerName; //业务负责人姓名

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

    public String getStreetManagerId() {
        return streetManagerId;
    }

    public void setStreetManagerId(String streetManagerId) {
        this.streetManagerId = streetManagerId;
    }

    public String getStreetManagerName() {
        return streetManagerName;
    }

    public void setStreetManagerName(String streetManagerName) {
        this.streetManagerName = streetManagerName;
    }

    public String getBusinessManagerId() {
        return businessManagerId;
    }

    public void setBusinessManagerId(String businessManagerId) {
        this.businessManagerId = businessManagerId;
    }

    public String getBusinessManagerName() {
        return businessManagerName;
    }

    public void setBusinessManagerName(String businessManagerName) {
        this.businessManagerName = businessManagerName;
    }

    public ActiveMatter() {
    }

    public ActiveMatter(Integer id, String matter, String matterDetails, String details, String dissatisfaction, String threshold, String street, Integer num, String matterStart, String streetManagerId, String streetManagerName, String businessManagerId, String businessManagerName) {
        this.id = id;
        this.matter = matter;
        this.matterDetails = matterDetails;
        this.details = details;
        this.dissatisfaction = dissatisfaction;
        this.threshold = threshold;
        this.street = street;
        this.num = num;
        this.matterStart = matterStart;
        this.streetManagerId = streetManagerId;
        this.streetManagerName = streetManagerName;
        this.businessManagerId = businessManagerId;
        this.businessManagerName = businessManagerName;
    }
}
