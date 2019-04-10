package com.yanlei.springboot.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author: x
 * @Date: Created in 14:55 2018/11/13
 */
public class ActiveScheme {

    private Integer id;
    private Integer amId;//主动办方案外键id关联主动办事项主键id
    private String matterName;//事项名称
    private String schemeName;//方案名称
    //孤寡老人:1  残疾人员:2  优抚救助:3  拥军拥属:4  低保人员:5  失业人员:6  "失独人员":7
    private String specialGroups;//特殊群体(军属,孤寡等等)
    private String waiterScheme;//服务方式
    private Date createTime;//创建时间
    private String workName;//报批人姓名
    private String agreeName;//审批人姓名
    private String servicePlan;//服务方式(多选项)
    private Integer integralQj;//积分区间
    private Integer integral;//积分
    private String threshold;//阀值
    private String notice;//通知状态
    private String handle;//办理状态
    private String executionTime;//执行时间(每月一次,每周一次)
    private String executionDate;//执行日期(具体时间,几号,周几)
    private String executionStart;//执行状态(1:所有人群,2:新增人群)
    private String content;//通知内容
    private Integer schemeStart; //方案状态(1:待报批 , 2:已报批 , 3:已批准 , 4:已办结)
    private Integer peopleNumber; //符合人数
    private Date examineTime; //方案审核通过时间,在返回方案状态时携带过来
    private Date endTime; //方案办结时间(所有人员办理完成即办结)
    private List<String> list; //存入特殊群体 , 分割后 foreach查询条件
    private String street; //关联人员信息查看街道
    private Date minTime; //人员导入时间最小值
    private Date maxTime; //人员导入时间最大值
    private int asId; //定时执行方案关联主键id
    private Date nextTime; //下一次执行时间
    private int quartzCount; //定时任务执行的次数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmId() {
        return amId;
    }

    public void setAmId(Integer amId) {
        this.amId = amId;
    }

    public String getMatterName() {
        return matterName;
    }

    public void setMatterName(String matterName) {
        this.matterName = matterName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSpecialGroups() {
        return specialGroups;
    }

    public void setSpecialGroups(String specialGroups) {
        this.specialGroups = specialGroups;
    }

    public String getWaiterScheme() {
        return waiterScheme;
    }

    public void setWaiterScheme(String waiterScheme) {
        this.waiterScheme = waiterScheme;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getAgreeName() {
        return agreeName;
    }

    public void setAgreeName(String agreeName) {
        this.agreeName = agreeName;
    }

    public String getServicePlan() {
        return servicePlan;
    }

    public void setServicePlan(String servicePlan) {
        this.servicePlan = servicePlan;
    }

    public Integer getIntegralQj() {
        return integralQj;
    }

    public void setIntegralQj(Integer integralQj) {
        this.integralQj = integralQj;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    public String getExecutionStart() {
        return executionStart;
    }

    public void setExecutionStart(String executionStart) {
        this.executionStart = executionStart;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSchemeStart() {
        return schemeStart;
    }

    public void setSchemeStart(Integer schemeStart) {
        this.schemeStart = schemeStart;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }

    public int getAsId() {
        return asId;
    }

    public void setAsId(int asId) {
        this.asId = asId;
    }

    public Date getNextTime() {
        return nextTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public int getQuartzCount() {
        return quartzCount;
    }

    public void setQuartzCount(int quartzCount) {
        this.quartzCount = quartzCount;
    }
}
