package com.yanlei.springboot.model;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: x
 * @Date: Created in 10:21 2018/11/13
 */
public class SchemePerson {

    private Integer id;
    private Integer sId;//随着方案id(同一方案id相同)
    private String name; //符合需求人员姓名
    private String idNumber; //身份证号码
    private String waiterName; //服务人员姓名
    private String address; //人员地址
    private String street; //所属街道
    private String community; //所属社区
    private String specialGroups; //特殊群体(军属,孤寡等等)
    private String telephone; //联系方式
    private String username; //1call账号
    private Integer integral; //积分
    private Integer pId; //主动事项id(外键关联)
    private Date insertTime; //人员插入时间
    private String source;//信息来源(人口信息库和街道两种)
    private String appStart; //人员办理状态 app状态
    private String msgStart; //人员办理状态 短信状态
    private String telephoneStart; //人员办理状态 电话状态
    private String customerStart; //人员办理状态 客服状态


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getSpecialGroups() {
        return specialGroups;
    }

    public void setSpecialGroups(String specialGroups) {
        this.specialGroups = specialGroups;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getAppStart() {
        return appStart;
    }

    public void setAppStart(String appStart) {
        this.appStart = appStart;
    }

    public String getMsgStart() {
        return msgStart;
    }

    public void setMsgStart(String msgStart) {
        this.msgStart = msgStart;
    }

    public String getTelephoneStart() {
        return telephoneStart;
    }

    public void setTelephoneStart(String telephoneStart) {
        this.telephoneStart = telephoneStart;
    }

    public String getCustomerStart() {
        return customerStart;
    }

    public void setCustomerStart(String customerStart) {
        this.customerStart = customerStart;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchemePerson that = (SchemePerson) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(idNumber, that.idNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, idNumber);
    }
}
