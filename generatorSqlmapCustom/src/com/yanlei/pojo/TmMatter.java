package com.yanlei.pojo;

import java.util.Date;

public class TmMatter {
    private Integer id;

    private String matterId;

    private String matterName;

    private Date createTime;

    private String matterType;

    private String matterDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatterId() {
        return matterId;
    }

    public void setMatterId(String matterId) {
        this.matterId = matterId == null ? null : matterId.trim();
    }

    public String getMatterName() {
        return matterName;
    }

    public void setMatterName(String matterName) {
        this.matterName = matterName == null ? null : matterName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMatterType() {
        return matterType;
    }

    public void setMatterType(String matterType) {
        this.matterType = matterType == null ? null : matterType.trim();
    }

    public String getMatterDetails() {
        return matterDetails;
    }

    public void setMatterDetails(String matterDetails) {
        this.matterDetails = matterDetails == null ? null : matterDetails.trim();
    }
}